(ns clojure_ica.Adapters.StringDataAdapter
  (:require [clojure.string :as string :only [upper-case, split]]
            [clojure_ica.Core.Entities :as Entities]))

(defn parse-number [s] (if (= s "") nil (read-string s)))




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
  [rawStringData]

  (let [
          response (atom {})
          fileContent (string/split rawStringData #"\n")
        ]

    ;src/clojure_ica

    (doseq [value fileContent]
      (let [
            formattedValue (string/split value #",")
            keyName (string/upper-case (get formattedValue 0))
            connectionName (string/upper-case (get formattedValue 1))
            connectionPrice (parse-number (get formattedValue 2))
            ]

        (reset! response (updateConnections @response, keyName, connectionName, connectionPrice))
        )
      )

    @response
    )
  )