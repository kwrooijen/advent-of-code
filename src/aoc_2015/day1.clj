(ns aoc-2015.day1
  (:require
   [aoc.util :refer [get-input]]))

(def data (get-input 2015 1))

;; part 1

(reduce (fn [floor i] (if (= i \() (inc floor) (dec floor))) 0 data)

;; part 2

(reduce (fn [[position floor] i]
          (cond
            (= i \()
            [(inc position) (inc floor)]
            (and (= i \))
                 (= floor 0))
            (reduced position)
            :else
            [(inc position) (dec floor)])) [1 0] data)
