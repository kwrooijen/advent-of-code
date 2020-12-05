(ns aoc.util
  (:require
   [clojure.java.io :as io]
   ;; [babashka.curl :as http]
   [clj-http.client :as http]))

(defn from-http [year i]
  (if-let [session-resource (io/resource "session")]
    (-> (str "https://adventofcode.com/" year "/day/")
        (str i "/input")
        (http/get
         {:cookies {"session" {:value (slurp session-resource)}}}
         ;; {:headers {"Cookie" (str "session=" (slurp session-resource))}}
         )
        :body)
    (throw (ex-data "No session file found in local/session"))))

(defn resource-file [year i]
  (str (System/getProperty "user.dir") "/resources/input/" year "/" i))

(defn get-input [year i]
  (if-let [r (-> (str "input/" year "/" i) (io/resource))]
    (slurp r)
    (let [result (from-http year i)]
      (spit (resource-file year i) result)
      result)))
