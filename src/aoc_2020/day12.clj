(ns aoc-2020.day12
  (:require
   [aoc.util :refer [get-input]]
   [clojure.string :as string]))

(defn string->instruction [s]
  (let [[d a] (mapv (partial apply str) (split-at 1 s))]
    [(keyword d) (Integer/parseInt a)]))

(def data
  (->> (string/split (get-input 2020 12) #"\n")
       (mapv string->instruction)))

(def directions
  (cycle [:N :E :S :W]))

(def directions-reverse
  (cycle [:N :W :S :E]))

(defn new-direction [facing directions degrees]
  (-> (drop-while (comp not #{facing}) directions)
      (nth (quot degrees 90))))

(defn merge-upper [acc {:keys [N E S W]} a]
  (cond-> acc
    (> N S) (update :N + (* a (- N S)))
    (> S N) (update :S + (* a (- S N)))
    (> E W) (update :E + (* a (- E W)))
    (> W E) (update :W + (* a (- W E)))))

(defn shift-right [{:keys [N E S W]}]
  {:N W :E N :S E :W S})

(defn shift-left [{:keys [N E S W]}]
  {:N E :E S :S W :W N})

(defn regular-move [{:keys [facing] :as acc} [d a]]
  (condp = d
    :F (update acc facing + a)
    :L (assoc acc :facing (new-direction facing directions-reverse a))
    :R (assoc acc :facing (new-direction facing directions a))
    (update acc d + a)))

(defn waypoint-move [{:keys [waypoint] :as acc} [d a]]
  (condp = d
    :F (merge-upper acc waypoint a)
    :L (assoc acc :waypoint (nth (iterate shift-left waypoint) (quot a 90)))
    :R (assoc acc :waypoint (nth (iterate shift-right waypoint) (quot a 90)))
    (update-in acc [:waypoint d] + a)))

(defn run [f data]
  (reduce f
   {:N 0 :E 0 :S 0 :W 0 :facing :E :waypoint {:N 1 :E 10 :S 0 :W 0}}
   data))

(defn answer [{:keys [N E S W]}]
  (+ (apply - (reverse (sort [N S])))
     (apply - (reverse (sort [E W])))))

;; Part 1
(answer (run regular-move data))

;; Part 2
(answer (run waypoint-move data))
