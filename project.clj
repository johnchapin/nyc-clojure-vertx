(defproject net.boostrot/nyc-clojure-vertx "0.1.0-SNAPSHOT"
  :description "Vert.x for NYC Clojure Meetup, July 2014"
  :url "http://github.com/johnchapin/nyc-clojure-vertx"
  :license {:name "The MIT License"}

  :dependencies [
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/core.async "0.1.303.0-886421-alpha"]

                 ;; NB: Vert.x API comes from lein-vertx plugin
                 [net.boostrot/ring-vertx-adapter "0.2.0-SNAPSHOT"]

                 ;; Web
                 [compojure "1.1.8"]
                 [javax.servlet/servlet-api "2.5"]
                 [metosin/compojure-api "0.13.1"]
                 [metosin/ring-http-response "0.4.0"]
                 [metosin/ring-swagger-ui "2.0.16-2"]
                 [prismatic/schema "0.2.4"]
                 [ring/ring "1.3.0"]

                 ;; App
                 [net.boostrot/pso "0.1.0"]

                 ;; Misc
                 [org.clojure/data.json "0.2.5"]

                 ]

  :plugins [
            [net.boostrot/lein-vertx "0.4.0-SNAPSHOT"]
            ]

  :vertx {:main net.boostrot.vertx.app/init}
  )
