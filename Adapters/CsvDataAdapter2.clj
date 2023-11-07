(ns PCU_SC_ICA_1_2023.Adapters.CsvDataAdapter2
  (:require [PCU_SC_ICA_1_2023.Core.Entities :as Entities]
            [clojure.string :as string :only [upper-case]])
  )


(defrecord Node [name])
(defrecord Edge [node1 node2 supplies])

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

(defn adapter
  [absolutePath]

  (let [
        response (atom {})
        nodes (atom [])
        edges (atom [])
        fileContent (line-by-line-parser absolutePath)
        hashmap (atom {})
        ]

    (doseq [value fileContent]
      (let [
            keyName (string/upper-case (get value 0))
            connectionName (string/upper-case (get value 1))
            connectionPrice (get value 2)
            ]

        (if (not (contains? @hashmap keyName))
          (do
            (reset! nodes (conj @nodes (Entities/->Node keyName)))
            (reset! hashmap (assoc @hashmap keyName true))
            )
          )

        (if (not (contains? @hashmap connectionName))
          (do
            (reset! nodes (conj @nodes (Entities/->Node connectionName)))
            (reset! hashmap (assoc @hashmap connectionName true))
            )
          )

        (reset! edges (conj @edges (Entities/->Edge keyName connectionName connectionPrice)))
        (reset! edges (conj @edges (Entities/->Edge connectionName keyName connectionPrice)))

        )
      )

    {"nodes" @nodes "edges" @edges}
    )
  )


;(process "C:\\Users\\Admin\\Documents\\ClojureProjects\\clojure-test\\src\\PCU_SC_ICA_1_2023\\flights-ICA1.csv")
