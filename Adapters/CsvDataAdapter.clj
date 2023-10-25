(ns clojure-ICA_1.Adapters.CsvDataAdapter
  (:require [clojure.string :as string :only [upper-case]])
  )

(defn parse-number [s] (if (= s "") nil (read-string s)))

(defn parse-line [line]
  (reduce #(conj %1 (parse-number %2))
          []
          (clojure.string/split line #",")))

(defn line-by-line-parser [file]
  (with-open [rdr (clojure.java.io/reader file)]
    (reduce #(conj %1 (parse-line %2))
            []
            (line-seq rdr))))


(defn updateConnections
  [response, keyName, connectionName, connectionPrice]

  (let [response (atom response)]

    (if (not (contains? @response keyName))
      (do
        (reset! response (assoc @response keyName [{"connection" connectionName "price" connectionPrice}]))
        )
      (do
        (reset! response (assoc @response keyName (conj (get @response keyName) {"connection" connectionName "price" connectionPrice})))
        )
      )

    (if (not (contains? @response connectionName))
      (do
        (reset! response (assoc @response connectionName [{"connection" keyName "price" connectionPrice}]))
        )
      (do
        (reset! response (assoc @response connectionName (conj (get @response connectionName) {"connection" keyName "price" connectionPrice})))
        )
      )
    @response
    )
  )
(defn adapter
  [absolutePath]

  (let [
          response (atom {})
          fileContent (line-by-line-parser absolutePath)
        ]

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


;(process "C:\\Users\\Admin\\Documents\\ClojureProjects\\clojure-test\\src\\clojure_ICA_1\\flights-ICA1.csv")
