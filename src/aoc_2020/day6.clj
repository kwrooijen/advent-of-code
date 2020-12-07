(ns aoc-2020.challenge-6
  (:require
   [aoc.util :refer [get-input]]
   [clojure.string :as string]))

(def data (get-input 2020 6))

;; part one
(->> (string/split data #"\n\n")
     (mapv #(string/split % #"\n"))
     (mapv (partial reduce into #{}))
     (mapv count)
     (apply +))

;; part two

(defn uniques [[h & rest]]
  (->> h
       (mapv (fn [x] [x (every? (partial some #{x}) rest)]))
       (filter last)
       (mapv first)
       count))

(->> (string/split data #"\n\n")
     (mapv #(string/split % #"\n"))
     (mapv (partial (partial mapv (comp set seq))))
     (mapv uniques)
     (apply +))
