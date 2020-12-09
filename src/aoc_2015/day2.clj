(ns aoc-2015.day2
  (:require
   [aoc.util :refer [get-input]]
   [clojure.string :as string]))

(def data
  (->> (string/split (get-input 2015 2) #"\n")
       (mapv #(string/split % #"x"))
       (mapv #(mapv (fn [x] (Integer/parseInt x)) %))))

(defn calc-necessary [[l w h]]
  (+ (min
      (* l w)
      (* w h)
      (* h l))
     (* 2 l w)
     (* 2 w h)
     (* 2 h l)))

(defn calc-ribbon [[l w h]]
  (let [[a b] (drop-last (sort [l w h]))]
    (+ a a b b
       (* l w h))))

;; Part 1
(->> data
     (mapv calc-necessary)
     (apply +))

;; Part 2
(->> data
     (mapv calc-ribbon)
     (apply +))
