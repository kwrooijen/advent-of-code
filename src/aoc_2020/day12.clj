(ns aoc-2020.day12
  (:require
   [aoc.util :refer [get-input]]
   [clojure.string :as string]))

(defn string->instruction [s]
  (let [[d a] (split-at 1 s)]
    [(keyword (apply str d))
     (Integer/parseInt
      (apply str a))]))

(def data
  (->> (string/split (get-input 2020 12) #"\n")
       (mapv string->instruction)))

(def directions
  (cycle [:N :E :S :W]))

(def directions-reverse
  (cycle [:N :W :S :E]))

(defn new-direction [facing directions degrees]
  (->> directions
       (drop-while (comp not #{facing}))
       (drop 1)
       (take (quot degrees 90))
       (last)))

(defn run-route [data]
  (reduce
   (fn [{:keys [facing] :as acc} [d a]]
     (condp = d
       :F (update acc facing + a)
       :L (assoc acc :facing (new-direction facing directions-reverse a))
       :R (assoc acc :facing (new-direction facing directions a))
       :N (update acc :N + a)
       :E (update acc :E + a)
       :S (update acc :S + a)
       :W (update acc :W + a)))
   {:N 0 :E 0 :S 0 :W 0 :facing :E}
   data))

(defn shift-right [{:keys [N E S W]}]
  {:N W :E N :S E :W S})

(defn shift-left [{:keys [N E S W]}]
  {:N E :E S :S W :W N})

(defn merge-upper [acc {:keys [N E S W]} a]
  (cond-> acc
    (> N S) (update :N + (* a (- N S)))
    (> S N) (update :S + (* a (- S N)))
    (> E W) (update :E + (* a (- E W)))
    (> W E) (update :W + (* a (- W E)))))

(defn run-waypoint [data]
  (reduce
   (fn [{:keys [waypoint] :as acc} [d a]]
     (condp = d
       :F (merge-upper acc waypoint a)
       :L (assoc acc :waypoint (nth (iterate shift-left waypoint) (quot a 90)))
       :R (assoc acc :waypoint (nth (iterate shift-right waypoint) (quot a 90)))
       :N (update-in acc [:waypoint :N] + a)
       :E (update-in acc [:waypoint :E] + a)
       :S (update-in acc [:waypoint :S] + a)
       :W (update-in acc [:waypoint :W] + a)))
   {:N 0 :E 0 :S 0 :W 0
    :waypoint {:N 1 :E 10 :S 0 :W 0}}
   data))

(defn answer [{:keys [N E S W]}]
  (apply +
         [(apply - (reverse (sort [N S])))
          (apply - (reverse (sort [E W])))]))

;; Part 1
(answer (run-route data))

;; Part 2
(answer (run-waypoint data))
