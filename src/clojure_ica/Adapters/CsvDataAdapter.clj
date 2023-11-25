(ns clojure_ica.Adapters.CsvDataAdapter
  (:require [clojure.string :as string :only [upper-case]]
            [clojure_ica.Core.Entities :as Entities])
  (:import (java.io FileNotFoundException)))

(defn parse-number [s] (if (= s "") nil (read-string s)))

(defn parse-line [line]
  (reduce #(conj %1 (parse-number %2))
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


(defn updateConnections
  [response, keyName, connectionName, connectionPrice]

  (let [response (atom response)]

    (if (not (contains? @response keyName))
      (do
        (reset! response (assoc @response keyName {connectionName (Entities/->Connection connectionName connectionPrice)}))
        )
      (do
        (reset! response (assoc @response keyName (assoc (get @response keyName) connectionName (Entities/->Connection connectionName connectionPrice))))
        )
      )

    (if (not (contains? @response connectionName))
      (do
        (reset! response (assoc @response connectionName {keyName (Entities/->Connection keyName connectionPrice)}))
        )
      (do
        (reset! response (assoc @response connectionName (assoc (get @response connectionName) keyName (Entities/->Connection keyName connectionPrice))))
        )
      )
    @response
    )
  )
(defn adapter
  [namespace relativePath]

  (let [
          response (atom {})
          fileContent (line-by-line-parser namespace relativePath)
        ]

    ;src/clojure_ica

    (doseq [value fileContent]
      (let [
            keyName (string/upper-case (get value 0))
            connectionName (string/upper-case (get value 1))
            connectionPrice (get value 2)
            ]

        (reset! response (updateConnections @response, keyName, connectionName, connectionPrice))
        )
      )

    @response
    )
  )