(defproject vertx-chat "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html" }
  :dependencies [[org.clojure/clojure "1.6.0"]
                 ;; NB: This is the most recent version of ClojureScript
                 ;;  that works with mod-lang-clojure 1.0.2
                 [org.clojure/clojurescript "0.0-2156"]
                 [reagent "0.4.0"]]
  :source-paths ["src/clj"]
  :cljsbuild {:builds
              [{:source-paths ["src/cljs"]
                :compiler {:output-to "resources/client.js"
                           :optimizations :whitespace
                           :pretty-print true
                           :preamble ["reagent/react.js"]
                           }}]}
  :plugins [[net.boostrot/lein-vertx "0.4.0-SNAPSHOT"]
            [lein-cljsbuild "1.0.2"]]
  :profiles {:provided
             {:dependencies [[io.vertx/clojure-api "1.0.2"]]}}
  :vertx {:main vertx-chat.core/init})
