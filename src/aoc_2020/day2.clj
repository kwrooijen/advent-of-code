(ns aoc-2020.challenge-2
  (:require
   [aoc.util :refer [get-input]]
   [clojure.string :as string]
   [clojure.edn :as edn]))

(defn- string->password [s]
  (let [[amount character value] (string/split s #" ")
        [min max] (string/split amount #"-")]
    {:password/amount {:amount/min (edn/read-string min)
                       :amount/max (edn/read-string max)}
     :password/character (-> character
                             (string/replace #":" "")
                             (char-array)
                             (first))
     :password/value value}))

(defn valid-1? [{:password/keys [amount character value]}]
  (<= (:amount/min amount)
      (count (filter #{character} value))
      (:amount/max amount)))

(defn valid-2? [{:password/keys [amount character value]}]
  (let [max (dec (:amount/max amount))
        min (dec (:amount/min amount))]
    (and (>= (count value) max)
         (or (and (= character (nth value min))
                  (not= character (nth value max)))
             (and (not= character (nth value min))
                  (= character (nth value max)))))))

(def data
  (->> (get-input 2020 2)
       (string/split-lines)
       (mapv string->password)))

(defn part1 []
  (->> data
       (filter valid-1?)
       (count)))

(defn part2 []
  (->> data
       (filter valid-2?)
       (count)))

(comment
  (part1)
  (part2)

  ;;
  )
