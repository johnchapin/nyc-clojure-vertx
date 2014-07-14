(ns vertx-chat.core
  (:require [vertx.core]))

(defn init []
  ;; Start other verticles
  (vertx.core/deploy-verticle "vertx_chat/logger.clj")
  (vertx.core/deploy-verticle "vertx_chat/timestamper.clj")
  (vertx.core/deploy-verticle "vertx_chat/web.clj"))
