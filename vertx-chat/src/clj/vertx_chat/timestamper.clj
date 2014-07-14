(ns vertx-chat.timestamper
  (:import [java.util Date])
  (:require [vertx.core]
            [vertx.eventbus :as eb]))

(vertx.core/periodic (* 1000 60 1)
  (eb/publish "chat" {:user "Timestamp" :message (str (Date.))}))
