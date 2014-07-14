(ns vertx-chat.web
  (:require [vertx.http :as http]
            [vertx.http.route :as route]
            [vertx.http.sockjs :as sockjs]))

(defn serve-file [req]
  (-> (http/server-response req)
      (http/send-file ,,, (-> (http/params req) :file))))

(let [http-server (http/server)]

  (http/on-request
    http-server
    (route/get "/:file" serve-file))

  (sockjs/bridge
    (sockjs/sockjs-server http-server)
    {:prefix "/eventbus"}
    [{:address "chat"}           ;; client -> eventbus
     {:address "history"}]
    [{:address "chat"}           ;; eventbus -> client
     {:address "history"}])

  (http/listen http-server 8080 "localhost"))
