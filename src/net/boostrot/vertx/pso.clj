(ns net.boostrot.vertx.pso
  (:require [net.boostrot.vertx.pso.domain :as domain]
            [vertx.eventbus :as eb]
            [vertx.logging :as log]
            [schema.core :as s]
            ))

(def address-prefix "net.boostrot.pso")

  ;(:require [pso.swarm]
            ;[pso.dimension]))

;(def dimensions [(pso.dimension/build :numerator 0 100)
                 ;(pso.dimension/build :denominator 1 100)])

;(loop [swarm (pso.swarm/build dimensions / (comparator >) 30)
       ;generations 100]
  ;(if (zero? generations)
    ;(get-in swarm [:best-particle :result :position])
    ;(recur (pso.swarm/update swarm) (dec generations))))

;; TODO: (s/defn new-pso-handler [spec :- domain/PsoSpec]
(let [id-counter (atom 0)
      cache (atom {})]

  (defn get-swarm [id]
    (eb/reply
      (get @cache id :not-found)))

  (s/defn new-swarm [new-spec :- domain/NewPsoSpec]
    (log/info :pso/new-spec new-spec)
    (let [id (swap! id-counter inc)
          spec (assoc new-spec :id id)]
      (log/info :pso/spec spec)
      (swap! cache assoc id spec)
      (eb/reply spec))))
        ;(log/info :pso/spec spec)
        ;(s/validate domain/PsoSpec
                    ;(get (swap! cache assoc id spec) id))))))

(eb/on-message (str address-prefix "/new") new-swarm)
(eb/on-message (str address-prefix "/get") get-swarm)
