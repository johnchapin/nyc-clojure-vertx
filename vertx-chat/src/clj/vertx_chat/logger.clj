(ns vertx-chat.logger
  (:require [vertx-chat.logger.impl :refer [log-handler history-handler]]
            [vertx.eventbus :as eb]))

(eb/on-message "chat" log-handler)
(eb/on-message "history" history-handler)
