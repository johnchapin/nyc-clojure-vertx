(ns net.boostrot.vertx.web.middleware
  (:require [compojure.api.json]))

(defn json-response-support
  [handler]
  (fn [request]
    (let [request (update-in request [:meta :produces]
                             concat ["application/json"])]
      (handler request))))

(defn json-support
  [handler]
  (-> handler
      json-response-support
      compojure.api.json/json-request-support))
