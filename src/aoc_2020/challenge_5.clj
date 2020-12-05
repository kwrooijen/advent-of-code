(ns src.aoc-2020.challenge-5
  (:require
   [aoc-2020.util :refer [input]]
   [clojure.string :as string]))

(def data (input 5))

(defn take-upper-lower-half [char [lower upper] r]
  (let [half (Math/ceil (/ (- upper lower) 2))]
    (if (= r char)
      [lower (- upper half)]
      [(+ lower half) upper])))

(defn find-row-col [s]
  (let [[row-chars col-chars] (split-at 7 s)
        [row-min row-max] (reduce (partial take-upper-lower-half \F) [0 127] row-chars)
        [col-min col-max] (reduce (partial take-upper-lower-half \L) [0 7] col-chars)]
    [(if (= (last row-chars) \F) row-min row-max)
     (if (= (last col-chars) \L) col-min col-max)]))

(defn id [[row col]]
  (+ (* row 8) col))

;; Part 1

(->> (string/split-lines data)
     (mapv (comp id find-row-col))
     (apply max)
     (int))

;; Part 2

(->> (string/split-lines data)
     (mapv (comp id find-row-col))
     (sort)
     (partition 2 1)
     (some (fn [[a b]] (when-not (= (inc a) b) (inc a))))
     (int))
