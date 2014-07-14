(ns vertx-chat.web
  (:require [vertx.filesystem :as fs]
            [vertx.http :as http]
            [vertx.http.route :as route]
            [vertx.http.sockjs :as sockjs]
            ))

(defn serve-file [req]
  (if-let [file (or (-> req http/params :file)
                    "/index.html")]
    (-> req http/server-response (http/send-file ,,, file))
    (-> req http/server-response (http/end ,,, "file-not-found"))))

(let [http-server (http/server)]

  (http/on-request
    http-server
    (route/get "/:file" serve-file))

  (sockjs/bridge
    (sockjs/sockjs-server http-server)
    {:prefix "/eventbus"}
    [{}]
    [{}])

  (http/listen http-server 8080 "localhost"))
