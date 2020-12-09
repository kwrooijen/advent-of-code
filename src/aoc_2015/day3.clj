(ns aoc-2015.day3
  (:require
   [aoc.util :refer [get-input]]))

(defn new-pos [{:keys [pos]} direction]
  (let [[x y] pos]
    (condp = direction
      \^  [x (inc y)]
      \>  [(inc x) y]
      \v  [x (dec y)]
      \<  [(dec x) y])))

(defn deliver [directions]
  (reduce (fn [state direction]
            (-> state
                (assoc :pos (new-pos state direction))
                (update :visited conj (new-pos state direction))))
          {:pos [0 0] :visited [[0 0]]}
          directions))

(defn drop-nth [n coll]
  (lazy-seq
    (when-let [s (seq coll)]
      (concat (take (dec n) (rest s))
              (drop-nth n (drop n s))))))

;; Part 1


(->> (get-input 2015 3)
     (deliver)
     :visited
     (set)
     (count))

;; Part 2

(count
 (set
  (concat
         (->> (get-input 2015 3)
              (take-nth 2)
              (deliver)
              :visited)
         (->> (get-input 2015 3)
              (drop-nth 2)
              (deliver)
              :visited))))
