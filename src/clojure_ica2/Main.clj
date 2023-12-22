(ns clojure_ica2.Main
  (:require
    [clojure_ica2.Adapters.CsvDataAdapter :as CsvDataAdapter]
    [clojure.string :as string :only [split]]
            )
  )


(def isDebug true)
(def filePath "")
(defn prepare-travel-plan
  []
  (let [

        ]

    (run! println (CsvDataAdapter/adapter (get (string/split (str *ns*) #"\.") 0) filePath))

    )
  )

;==========
;
;===INIT===
;
;==========
(def filePath "Datasets/sales_team_1.csv")

(prepare-travel-plan)