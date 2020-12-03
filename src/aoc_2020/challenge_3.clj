(ns aoc-2020.challenge-3
  (:require [aoc-2020.util :refer [input]]
            [clojure.string :as string]))

(def data
  (-> (input 3)
      (string/split-lines)))

(defn on-tree? [line x]
  (= \# (nth line (mod x (count line)))))

(defn tree-counting-reducer [acc line]
  (if (on-tree? line (:x acc))
    (-> acc
        (update :x + 3)
        (update :count inc))
    (update acc :x + 3)))

(comment
  (->> (rest data)
       (reduce tree-counting-reducer {:x 3 :count 0})
       :count)
  ;;
  )
