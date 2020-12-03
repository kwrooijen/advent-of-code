(ns aoc-2020.challenge-1
  (:require [clojure.edn :as edn]
            [clojure.string :as string]
            [aoc-2020.util :refer [input]]))

(def input-ints
  (->> (input 1)
       (string/split-lines)
       (mapv edn/read-string)
       (set)))

(defn reducer [v _acc n]
  (when (= 2020 (+ v n))
    (reduced (* v n))))


(defn matching-input [v]
  (reduce (partial reducer v)
          (disj input-ints v)))

(comment
  (->> input-ints
       (map matching-input)
       (some identity))

  ;;
  )
