(ns aoc-2020.challenge-2
  (:require [aoc-2020.util :refer [input]]
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

(defn valid? [{:password/keys [amount character value]}]
  (<= (:amount/min amount)
      (count (filter #{character} value))
      (:amount/max amount)))

(def data
  (->> (input 2)
       (string/split-lines)
       (mapv string->password)))

(comment
  (->> data
       (filter valid?)
       (count))
  ;;
  )
