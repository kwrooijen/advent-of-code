(ns aoc-2020.day1
  (:require
   [aoc.util :refer [get-input]]
   [clojure.edn :as edn]
   [clojure.string :as string]))

(def input-ints
  (->> (get-input 2020 1)
       (string/split-lines)
       (mapv edn/read-string)))

(defn answer [& n]
  (when (= 2020 (apply + n))
    (apply * n)))

(defn part1 []
  (some identity
        (for [i1 input-ints
              i2 input-ints]
          (answer i1 i2))))

(defn part2 []
  (some identity
        (for [i1 input-ints
              i2 input-ints
              i3 input-ints]
          (answer i1 i2 i3))))


(comment
  (part1)
  (time
   (part2))
  ;;
  )
