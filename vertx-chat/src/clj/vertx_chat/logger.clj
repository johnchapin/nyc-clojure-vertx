(ns vertx-chat.logger
  (:require [vertx.eventbus :as eb]
            [vertx.logging :as log]))

(def history (atom []))

(defn log-handler [m]
  (log/info m)
  (swap! history conj m))

(eb/on-message "chat" log-handler)

(defn history-handler [m]
  (when (number? m)
    (eb/reply (take-last m @history))))

(eb/on-message "history" history-handler)
