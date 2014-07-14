(defproject net.boostrot/vertx-chat-robot "0.1.0-SNAPSHOT"
  :description "Sample Vert.x / Clojure application"
  :url "http://github.com/johnchapin/nyc-clojure-vertx/vertx-chat-robot"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html" }

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.async "0.1.303.0-886421-alpha"]]

  :source-paths ["src/clj"]

  ;; NB: Need to use our own version of lein-vertx to get official Clojure
  ;;  support
  :plugins [[net.boostrot/lein-vertx "0.4.0-SNAPSHOT"]]

  :profiles {:provided
             {:dependencies [[io.vertx/clojure-api "1.0.2"]]}}

  ;; lein-vertx
  :vertx {:main vertx-chat-robot.core/init})
