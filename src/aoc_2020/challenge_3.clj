(ns aoc-2020.challenge-3
  (:require [aoc-2020.util :refer [input]]
            [clojure.string :as string]))

(def data
  (-> (input 3)
      (string/split-lines)))

(defn on-tree? [line x]
  (= \# (nth line (mod x (count line)))))

(defn tree-counting-reducer [steps acc line]
  (if (on-tree? line (:x acc))
    (-> acc
        (update :x + steps)
        (update :count inc))
    (update acc :x + steps)))

(defn count-steps [coll steps]
  (->> coll
       (reduce (partial tree-counting-reducer steps) {:x steps :count 0})
       :count))

(defn slope [coll s]
  (->> coll
       (drop s)
       (take-nth s)))

(defn part-1 []
  (count-steps (slope data 1) 3))

(defn part-2 []
  (* (count-steps (slope data 1) 1)
     (count-steps (slope data 1) 3)
     (count-steps (slope data 1) 5)
     (count-steps (slope data 1) 7)
     (count-steps (slope data 2) 1)))

(comment
  (part-1)
  (part-2)

  ;;
  )
