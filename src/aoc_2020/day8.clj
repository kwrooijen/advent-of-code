(ns aoc-2020.day8
  (:require
   [aoc.util :refer [get-input]]
   [clojure.string :as string]))

(set! *print-namespace-maps* false)

(def data (get-input 2020 8))

(def operations
  (mapv
   #(let [[op c] (string/split % #" ")]
      [(keyword op) (Integer/parseInt c)])
   (string/split data #"\n")))

;; Part 1

(defmulti handle-operation
  (fn [_state op _arg]
    op))

(defmethod handle-operation :nop [state _op _arg] state)

(defmethod handle-operation :jmp [state _op arg]
  (update state :instruction + (dec arg)))

(defmethod handle-operation :acc [state _op arg]
  (update state :acc + arg))

(defn get-instruction [{:keys [instruction operations]}]
  (if (= instruction (count operations))
    [:exit []]
    (get operations instruction)))

(defn interpret [operations]
  (loop [state {:instruction 0 :callstack {} :acc 0 :operations operations}]
    (let [[op arg] (get-instruction state)]
      (cond
        (get (:callstack state) (:instruction state)) {:inifite-loop (:acc state)}
        (= op :exit) {:exit (:acc state)}
        :else (-> state
                  (update :callstack assoc (:instruction state) true)
                  (handle-operation op arg)
                  (update :instruction inc)
                  (recur))))))

;; Part 1
(interpret operations)

;; Part 2
(->> operations
     (keep-indexed
      (fn [i [op _arg]]
        (condp = op
          :jmp (:exit (interpret (assoc-in operations [i 0] :nop)))
          :nop (:exit (interpret (assoc-in operations [i 0] :jmp)))
          nil)))
     (first))
