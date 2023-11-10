(ns PCU_SC_ICA_1_2023.Main
  (:require [PCU_SC_ICA_1_2023.Adapters.CsvDataAdapter :as CsvDataAdapter3]
            [PCU_SC_ICA_1_2023.Core.Core :as SearchEngineCore3])
  )


(println "List of all routes")

(def LIMITS_PRICE {"f" 700 "g" 1000})
(def LIMITS_LENGTH {"f" 3 "g" 4})

(defn prepare-travel-plan
  [start end type]
  (let [
        type type
        priceLimit (atom 0)
        lengthLimit (atom 0)
        routes (atom [])
        ]

    (reset! priceLimit (get LIMITS_PRICE type))
    (reset! lengthLimit (get LIMITS_LENGTH type))

    (println "====================")
    (println "ALL ROUTES")
    (reset! routes (SearchEngineCore3/init
                     (CsvDataAdapter3/adapter "Datasets/flights-ICA1.csv")
                     start
                     end
                     )
            )
    (run! println @routes)

    (println "====================")
    (println "FILTERED BY PRICE")
    (reset! routes (filter #(<= (:priceSum %) @priceLimit) @routes))
    (run! println (filter #(<= (:priceSum %) @priceLimit) @routes))

    (println "====================")
    (println "FILTERED BY PRICE AND LENGTH")

    (reset! routes (filter #(<= (:length %) @lengthLimit) @routes))
    (run! println (filter #(<= (:length %) @lengthLimit) @routes))
    )
  )

(prepare-travel-plan "Prague" "Berlin" "g")