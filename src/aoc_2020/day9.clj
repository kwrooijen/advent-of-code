(ns aoc-2020.day9
  (:require
   [aoc.util :refer [get-input]]
   [clojure.string :as string]))

(set! *print-namespace-maps* false)

(def data
  (->>
   (string/split (get-input 2020 9) #"\n")
   (mapv #(clojure.lang.BigInt/fromBigInteger (BigInteger. %)))))

(def groupable-data (drop 27 data))

(defn group [coll]
  (->> data
       (keep-indexed
        (fn [i x]
          (when (> (- i 26) 0)
            [x (subvec coll (- i 26) i)])))
       (vec)))


(def groups-25
  (group data))

(def not-corrupt
  (set (for [[v group] groups-25
             g1 group
             g2 group :when (= (+ g1 g2) v)]
         v)))

;; Part 1

(def corrupt-num
  (first (drop-while not-corrupt groupable-data)))

;; Part 2


(first
 (for [i (range (count data))
       length (range (count data))
       :when (and (= corrupt-num
                     (try (apply + (subvec data i length))
                          (catch Exception _ nil)))
                  (> (count (subvec data i length)) 0))]
   (let [v (sort (subvec data i length))]
     (+ (first v)
        (last v)))))
