(ns aoc-2020.day11
  (:require
   [aoc.util :refer [get-input]]
   [clojure.string :as string]))

(def data
  (->> (string/split (get-input 2020 11) #"\n")))

(defn- cols-rows [lines]
  [(count (first lines))
   (count lines)])

(defn- get-tile
  ([lines x y] (get-tile lines x y nil))
  ([lines x y default]
   (get-in lines [y x] default)))

(defn- free? [lines x y]
  (= (get-tile lines x y) \L))

(defn- occupied? [lines x y]
  (= (get-tile lines x y) \#))

(def adjacents [[0 1] [1 0] [-1 0] [0 -1] [1 1] [-1 -1] [-1 1] [1 -1]])

(defn- first-in-sight [lines x y xx yy]
  (reduce
   (fn [{:keys [xxx yyy tile]} _]
     (if (or (#{\L \#} tile)
             (nil? tile))
       (reduced tile)
       {:xxx (+ xxx xx)
        :yyy (+ yyy yy)
        :tile (get-tile lines (+ xx xxx) (+ yy yyy))}))
   {:xxx (+ x xx)
    :yyy (+ y yy)
    :tile (get-tile lines (+ xx x) (+ yy y))}
   (range)))

(defn- occupied-adjacent? [lines x y {:keys [los?]}]
  (>= (count
       (filter
        (fn [[xx yy]]
          (if los?
            (= \# (first-in-sight lines x y xx yy))
            (occupied? lines (+ xx x) (+ yy y))))
        adjacents))
      (if los? 5 4)))

(defn- free-adjacent? [lines x y {:keys [los?]}]
  (every? (fn [[xx yy]]
            (let [fis (if los?
                        (first-in-sight lines x y xx yy)
                        (get-tile lines (+ x xx) (+ y yy)))]
              (or (nil? fis)
                  (#{\L \.} fis))))
          adjacents))

(defn- occupy
  ([lines] (occupy lines {}))
  ([lines opts]
   (let [[cols rows] (cols-rows lines)]
     (mapv (partial apply str)
           (partition
            cols
            (for [y (range rows)
                  x (range cols)]
              (cond
                (and (free? lines x y)
                     (free-adjacent? lines x y opts)) \#

                (and (occupied? lines x y)
                     (occupied-adjacent? lines x y opts)) \L

                :else (get-tile lines x y))))))))

(defn- count-occupied-seats [lines]
  (->> lines
       (apply str)
       (filter #{\#})
       (count)))

(defn- repeatedly-occupy [data opts]
  (reduce (fn [acc _]
            (let [result (occupy acc opts)]
              (if (= acc result)
                (reduced (count-occupied-seats result))
                result)))
          data
          (range)))

;; Part 1
(repeatedly-occupy data {})

;; Part 2
(repeatedly-occupy data {:los? true})
