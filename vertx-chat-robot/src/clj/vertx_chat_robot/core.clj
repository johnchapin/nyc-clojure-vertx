(ns vertx-chat-robot.core
  (:require [vertx-chat-robot.shared :refer [robot-count]]
            [vertx.eventbus :as eb]
            [vertx.logging :as log]))

(let [components '("8B:;" "-~^º" "[]{}()DPO/\\|")]
  (defn random-emoticon []
    (apply str (map rand-nth components))))

(defn build-response [{:keys [user message]} robot-name]
  (cond
    (re-matches
      (re-pattern (format "(?i)^H(i|ola|owdy|ello).*%s.*" robot-name))
      message)
    (format "Greetings, %s." user)

    (re-matches
      (re-pattern (format "(?i)^(Goodbye|Adios).*%s.*" robot-name))
      message)
    (format "Goodbye, %s." user)

    (re-matches #"(?i).*robot.*" message)
    (random-emoticon)))

(defn message-handler [robot-name {:keys [user message] :as msg}]
  (when (and (not= user robot-name))
    (when-let [response (build-response msg robot-name)]
      (eb/publish "chat" {:user robot-name :message response}))))

(defn init []
  (let [robot-name (format "Robo%d" (swap! robot-count inc))]
    (log/info "Initializing " robot-name)
    (eb/on-message "chat" (partial message-handler robot-name))))
