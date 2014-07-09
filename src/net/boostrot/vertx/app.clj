(ns net.boostrot.vertx.app
  (:require [vertx.core :as vertx]
            [vertx.utils :as utils]
            [schema.macros :as sm]
            ))

;(let [{:keys [web pso] :as config} (vertx/config)]
  ;(vertx/deploy-verticle "web.clj" :config web)
  ;(deploy-worker-verticle "pso.clj" :config pso :instances 5))

;; TODO: Update Schema lib when this is fixed
(reset! sm/*use-potemkin* false)

;; TODO: PR for vertx/mod-lang-clojure
(extend-protocol utils/Decodeable
  java.util.ArrayList
  (decode [data]
    (vec (map utils/decode data))))

(defn init []
  (vertx/deploy-verticle "net/boostrot/vertx/web.clj")
  (vertx/deploy-verticle "net/boostrot/vertx/pso.clj"))
;(vertx/deploy-worker-verticle "pso.clj" :config pso :instances 5))
