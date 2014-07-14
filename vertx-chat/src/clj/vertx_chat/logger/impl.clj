(ns vertx-chat.logger.impl
  (:require [vertx.eventbus :as eb]
            [vertx.logging :as log]))

(def history (atom []))

(defn log-handler [m]
  (log/info m)
  (swap! history conj m))

(defn history-handler [m]
  (when (number? m)
    (eb/reply (take-last m @history))))
