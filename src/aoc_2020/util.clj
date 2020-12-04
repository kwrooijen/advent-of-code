(ns aoc-2020.util
  (:require
   [clj-http.client :as http]
   [clojure.java.io :as io]))

(defn from-http [i]
  (if-let [session-resource (io/resource "session")]
    (-> "https://adventofcode.com/2020/day/"
        (str i "/input")
        (http/get {:cookies {"session" {:value (slurp session-resource)}}})
        :body)
    (throw (ex-data "No session file found in local/session"))))

(defn resource-file [i]
  (str (System/getProperty "user.dir") "/resources/input/" i))

(defn input [i]
  (if-let [r (-> (str "input/" i) (io/resource))]
    (slurp r)
    (let [result (from-http i)]
      (spit (resource-file i) result)
      result)))
