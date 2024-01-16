(ns clojure_ica2.AnalysisService.CsvDataAdapter
  (:require [clojure.string :as string :only [upper-case]]
            [clojure_ica2.Core.Entities :as Entities])
  (:import (java.io FileNotFoundException)))

(defn parse-number [s] (if (= s "") nil (read-string s)))

(defn parse-line [line]
  (reduce #(conj %1 %2)
          []
          (clojure.string/split line #",")))


(def readerTries -1)
(defn line-by-line-parser [namespace file]
  (def readerTries (inc readerTries))

  (try
    (with-open [rdr (clojure.java.io/reader file)]
      (do
        (def readerTries -1)
        (reduce #(conj %1 (parse-line %2))
                []
                (line-seq rdr)
                )
        )
      )

    ; Run in repl fix
    (catch FileNotFoundException e
      (if (< readerTries 1)
        (line-by-line-parser namespace (str "src/" namespace "/" file))
        (println "CANT READ DATASET")
        )
      )
    )

  )

(defn adapter
  [namespace relativePath]

  (let [
        counter (atom 0)
          response (atom [])
          fileContent (line-by-line-parser namespace relativePath)
        ]

    (doseq [value fileContent]
      (let [

            ]
        (if (> @counter 0)
          (do
            (reset! response (conj @response (Entities/->Log (get value 0) (Integer/parseInt (re-find #"\A-?\d+" (get value 1))) (get value 2) (get value 3) (Integer/parseInt (re-find #"\A-?\d+" (get value 4))))))
            )
          )

        (reset! counter (+ @counter 1))
        )
      )

    @response
    )
  )