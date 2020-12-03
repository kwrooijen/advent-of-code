(ns aoc-2020.util
  (:require
   [clojure.java.io :as io]))

(defn input [i]
  (-> (str "input/" i)
      (io/resource)
      (slurp)))
