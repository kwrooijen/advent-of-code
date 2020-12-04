(ns aoc-2020.challenge-4
  (:require [aoc-2020.util :refer [input]]
            [clojure.edn :as edn]
            [clojure.set :refer [union]]
            [clojure.string :as string]))

(defn byr? [{:strs [byr]}]
  (and (some? byr)
       (<= 1920 (edn/read-string byr) 2002)))

(defn iyr? [{:strs [iyr]}]
  (and (some? iyr)
       (<= 2010 (edn/read-string iyr) 2020)))

(defn eyr? [{:strs [eyr]}]
  (and (some? eyr)
       (<= 2020 (edn/read-string eyr) 2030)))

(defn hgt? [{:strs [hgt]}]
  (when hgt
    (or (when (re-matches #"^[0-9]{3}cm$" hgt)
          (<= 150
              (edn/read-string (subs hgt 0 3))
              193))
        (when (re-matches #"^[0-9]{2}in$" hgt)
          (<= 59
              (edn/read-string (subs hgt 0 2))
              76)))))

(defn hcl? [{:strs [hcl]}]
  (when hcl
    (re-matches #"^\#[0-9a-f]{6}$" hcl)))

(defn ecl? [{:strs [ecl]}]
  (when ecl
    (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl)))

(defn pid? [{:strs [pid]}]
  (when pid
    (re-matches #"^[0-9]{9}$" pid)))

(defn cid? [{:strs [cid]}]
  (some? cid))

(def required-keys
  #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})

(def optional-keys
  #{"cid"})

(def required-validators
  [byr? iyr? eyr? hgt? hcl? ecl? pid?])

(def optional-validators
  [cid?])

(defn valid-keys? [m ks]
  (and (= (count m) (count ks))
       (every? ks (keys m))))

(defn valid-1? [m]
  (or (valid-keys? m required-keys)
      (valid-keys? m (union required-keys
                         optional-keys))))

(defn valid-validators? [m validators]
  (and (= (count m) (count validators))
       (every? (fn [v] (v m)) validators)))

(defn valid-2? [m]
  (or (valid-validators? m required-validators)
      (valid-validators? m (union required-validators
                                  optional-validators))))

(def data
  (->> (string/split (input 4) #"\n\n")
       (map #(string/replace % #"\n" " "))
       (mapv (fn [l] (apply hash-map (string/split l #"(\:|\s)"))))))

;; Part 1
(count (filter valid-1? data))
;; Part 2
(count (filter valid-2? data))
