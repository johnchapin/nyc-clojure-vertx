(ns vertx-chat.client
  (:require [vertx.client.eventbus :as eb]
            [reagent.core :as reagent :refer [atom]]))

(def messages (atom (vector)))

(defn pretty-message [{:keys [user message]}]
  (str user " : " message "\n"))

(defn pretty-messages [msgs]
  (apply str (map pretty-message msgs)))

(defn messages-component []
  [:div
   [:textarea#messages {:id "messages"
                        :rows 20
                        :readOnly true
                        :value (pretty-messages @messages)}]])

(defn submit-message [eb user message]
  (.log js/console message)
  (when-not (or (empty? user) (empty? message))
    (eb/publish eb "chat" {:user user :message message})))

(defn input-component [eb]
  (let [message (atom "Message")
        user (atom "Handle")]
    (fn []
      [:div
       [:input#user {:type "text"
                     :value @user
                     :on-change #(reset! user (-> % .-target .-value))}]
       [:input#message {:type "text"
                        :value @message
                        :on-change #(reset! message (-> % .-target .-value))
                        :on-key-up #(case (.-which %)
                                      13 (do
                                           (submit-message eb @user @message)
                                           (reset! message ""))
                                      nil)}]])))

(defn main-component [eb]
  [:div
   [messages-component]
   [input-component eb]])

(defn chat-handler [m]
  (swap! messages conj m))

(defn history-handler [m]
  (when-not (empty? m)
    (reset! messages (vec m))))

(defn on-startup [eb]
  (eb/on-message eb "chat" chat-handler)
  (eb/send eb "history" 5 history-handler))

(defn init-eb []
  (eb/eventbus "http://localhost:8080/eventbus"))

(defn init []
  (let [eb (init-eb)]

    (reagent/render-component [main-component eb]
                              (.-body js/document))

    (eb/on-open eb on-startup)))

(set! (.-onload js/window) init)
