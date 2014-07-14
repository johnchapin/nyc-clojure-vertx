(defproject net.boostrot/vertx-chat "0.1.0-SNAPSHOT"
  :description "Sample Vert.x / Clojure application"
  :url "http://github.com/johnchapin/nyc-clojure-vertx/vertx-chat"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html" }

  :dependencies [[org.clojure/clojure "1.6.0"]
                 ;; NB: This is the most recent version of ClojureScript
                 ;;  that works with mod-lang-clojure 1.0.2's eventbus
                 ;;  wrapper.
                 [org.clojure/clojurescript "0.0-2156"]
                 ;; NB: This is the most recent version of Reagent that we
                 ;;  can use with Clojurescript 0.0-2156
                 [reagent "0.4.0"]]

  ;; NB: Need to include resources here so lein-vertx will package HTML/CSS/JS
  ;;  with our module.
  :source-paths ["src/clj" "resources"]

  ;; NB: Need to use our own version of lein-vertx to get official Clojure
  ;;  support
  :plugins [[net.boostrot/lein-vertx "0.4.0-SNAPSHOT"]
            [lein-cljsbuild "1.0.2"]]

  :profiles {:provided
             {:dependencies [[io.vertx/clojure-api "1.0.2"]]}}

  ;; lein-cljsbuild
  :cljsbuild {:builds
              [{:source-paths ["src/cljs"]
                :compiler {:output-to "resources/client.js"
                           :optimizations :whitespace
                           :pretty-print true
                           :preamble ["reagent/react.js"]
                           }}]}

  ;; lein-vertx
  :vertx {:main vertx-chat.core/init})
