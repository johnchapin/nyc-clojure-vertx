(ns net.boostrot.vertx.pso.domain
  (:require [schema.core :as s]
            [schema.macros :as sm]))

;; NB: Otherwise double-eval will throw errors.
(reset! sm/*use-potemkin* false)

(s/defschema PsoDimension
  {:tag String
   :minimum Double
   :maximum Double})

(s/defschema PsoSpec
  {:id Long
   :tag String
   :dimensions [PsoDimension]
   :comparator (s/enum "gt" "lt")})

(s/defschema NewPsoSpec
  (dissoc PsoSpec :id))
