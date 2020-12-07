(ns aoc-2020.day7
  (:require
   [aoc.util :refer [get-input]]
   [clojure.string :as string]))

(set! *print-namespace-maps* false)

(def data (get-input 2020 7))

(defn bag->bag+types [bag]
  (let [[name bs] (string/split bag #"contain")
        contains
        (apply merge
               (mapv (fn [b]
                       (let [[c n] (string/split b #" " 2)
                             n (string/replace n #"\." "")
                             n (if (string/ends-with? n "bags") n (str n "s"))
                             ]
                         (if (= c "no")
                           {}
                           {
                            (string/replace n #"\." "") (Integer/parseInt c)})))
                     (string/split (string/trim bs) #", ")))]
    {:bag/name (string/trim name)
     :bag/contains contains}))

(defn loop-bags [req bags]
  (keep (fn [{:bag/keys [contains name]}]
            (and
             (some (set (keys contains)) req)
             name))

           bags))
(def bags
  (->> (string/split data #"\n" )
       (mapv bag->bag+types)))


;; Part 1

(loop [req #{"shiny gold bags"}
       last-count 0
       count1 1]
  (let [result (apply merge req (set (loop-bags req bags)))]
    (if (= count1 last-count)
      (dec (count req))
      (recur result
             count1
             (count result)))))

;; Part 2

(defn get-bag [n]
  (first (filter (comp #{n} :bag/name) bags )))

(defn bag-counter [bag]

  (reduce (fn [acc [n c]]

            (apply + acc c (repeatedly
                            c
                            #(bag-counter (get-bag n)))))
          0
          (:bag/contains bag)))

(loop [req #{"shiny gold bags"}
       last-count 0
       count1 1]
  (let [result (apply merge req (set (loop-bags req bags)))]
    (if (= count1 last-count)
      (dec (count req))
      (recur result
             count1
             (count result)))))

(bag-counter
 (get-bag "shiny gold bags"))
