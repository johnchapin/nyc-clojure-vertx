(ns vertx-chat.logger-test
  (:require [vertx-chat.logger.impl :as logger]
            [vertx.eventbus :as eb]
            [vertx.testtools]
            [clojure.test :refer [deftest is use-fixtures]]))

(use-fixtures :each vertx.testtools/as-embedded)

(deftest test-history-handler
  (let [expected ["foo" "bar"]]
    ;; Fixture data
    (reset! logger/history expected)

    ;; Register handler
    (eb/on-message "history" logger/history-handler)

    ;; Send message to handler under test
    (eb/send "history" 2 (fn [r]
                           (vertx.testtools/test-complete
                             (is (= expected r)))))))
