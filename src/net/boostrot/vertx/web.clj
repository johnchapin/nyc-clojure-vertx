(ns net.boostrot.vertx.web
  (:require [compojure.api.sweet :refer :all]
            [compojure.api.middleware :refer :all]
            [compojure.api.json]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.response]
            [compojure.core :refer [defroutes]]
            [ring.util.response]
            ;[ring.util.http-response :refer [response ok]]
            [ring.swagger.schema :as rs]

            ;; Vert.x
            [ring.adapter.vertx :as ring-vertx]
            [vertx.core :as vertx]
            [vertx.eventbus :as eb]
            [vertx.http :as http]
            [vertx.logging :as log]
            [vertx.shareddata :as shared]

            ;; App
            [net.boostrot.vertx.pso.domain :as pso.domain]
            [net.boostrot.vertx.web.middleware]

            ;; Misc
            [clojure.core.async :refer [chan go <!! <! >! take!]]
            [clojure.core.async.impl.channels]
            ))

;(def ^:dynamic *callback* identity)

;(defmacro with-callback [callback & forms]
  ;`(binding [*callback* ~callback]
     ;~@forms))

;(extend-protocol response/Renderable
  ;clojure.core.async.impl.channels.ManyToManyChannel
  ;(render [chan request]
    ;(assoc request :async (response/render (<!! chan) request))))

;(defn new-pso-spec [new-spec]
  ;(let [output (chan)]
    ;(eb/send
      ;"net.boostrot.pso/new" new-spec
      ;(fn [reply-msg]
        ;(let [resp (some->> reply-msg (rs/coerce! pso.domain/PsoSpec ,,,) ok)]
          ;(go (>! output resp)))))
    ;output))
(defn new-pso-spec [new-spec]
  (let [output (chan)]
    (eb/send
      "net.boostrot.pso/new" new-spec
      (fn [reply-msg]
        (let [response (rs/coerce! pso.domain/PsoSpec reply-msg)]
          (go (>! output response)))))
    output))

(defroutes* pso-routes
  (POST* "/pso" []
         :return pso.domain/PsoSpec
         :body [new-spec pso.domain/NewPsoSpec]
         :summary "Echos a PSO spec"
         (new-pso-spec new-spec))

  ;(GET* "/pso/:id" []
        ;:path-params [id :- Long]
        ;:return pso.domain/PsoResult
        ;:summary "Retrieve a PSO spec"
        ;(ok ...)
        ;)
  )

(defn middleware
  [handler]
  (-> handler
      ring.middleware.http-response/catch-response
      ;ring.swagger.middleware/catch-validation-errors
      ;compojure.api.middleware/ex-info-support
      net.boostrot.vertx.web.middleware/json-support
      ring.middleware.keyword-params/wrap-keyword-params
      ring.middleware.nested-params/wrap-nested-params
      ring.middleware.params/wrap-params))

(defroutes app
  (middleware
    (compojure.api.routes/with-routes
      (swagger-ui)
      (swagger-docs)
      (swaggered "test"
                 :description "Swagger test api"
                 (context "/api" []
                          pso-routes))
      (route/not-found "<h1>Page not found</h1>"))))

(extend-protocol compojure.response/Renderable
  clojure.core.async.impl.channels.ManyToManyChannel
  (render [chan request]
    (ring.util.response/response chan)))

;(extend-protocol clojure.java.io/IOFactory
  ;clojure.core.async.impl.channels.ManyToManyChannel
  ;(make-reader [chan opts]
    ;nil)
  ;(make-writer [chan opts]
    ;nil)
  ;(make-input-stream [chan opts]
    ;nil)
  ;(make-output-stream [chan opts]
    ;nil))

(defmethod ring-vertx/set-body
  clojure.core.async.impl.channels.ManyToManyChannel
  [response body]
  (go
    (ring-vertx/set-body (http/end response (<! body)))))

;(defn async-app [req callback]
  ;(when-let [resp (app req)]
    ;(if (instance? clojure.core.async.impl.channels.ManyToManyChannel resp)
      ;(take! resp callback)
      ;(callback resp))))

;; Start HTTP server

(ring-vertx/run-vertx-web (http/server) app "localhost" 8080)
