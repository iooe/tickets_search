(ns PCU_SC_ICA_1_2023.Main
  (:require [PCU_SC_ICA_1_2023.Adapters.CsvDataAdapter :as CsvDataAdapter3]
            [PCU_SC_ICA_1_2023.Core.Core :as SearchEngineCore3]
            [clojure.string :as string :only [split]]
            )
  )


(def LIMITS_PRICE {"f" 700 "g" 1000})
;/
(def LIMITS_LENGTH {"f" 3 "g" 4})

(def isDebug true)
(def filePath "")
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

    ; Get all routes
    (reset! routes (SearchEngineCore3/init
                     (CsvDataAdapter3/adapter (get (string/split (str *ns*) #"\.") 0) filePath)
                     start
                     end
                     )
            )

    (if (= isDebug true)
      (do
        (println "====================")
        (println "ALL ROUTES")
        (run! println @routes))
      )

    ; Filter by price
    (reset! routes (filter #(<= (:priceSum %) @priceLimit) @routes))

    (if (= isDebug true)
      (do (println "====================")
          (println "FILTERED BY PRICE")
          (run! println @routes)
          )
      )

    ; Filter filtered data by length
    (reset! routes (filter #(<= (:flights %) @lengthLimit) @routes))

    (if (= isDebug true)
      (do
        (println "====================")
        (println "FILTERED BY PRICE AND LENGTH")
        (run! println @routes)
        )
      )

    ; Order routes by price
    (reset! routes (sort-by :priceSum @routes))

    (if (= isDebug true)
      (do
        (println "====================")
        (println "ORDERING BY PRICE IN ASC")
        (run! println @routes)
        )
      )

    (let
      [
       formattedListOfCities (atom "")
       route (last @routes)
       index (atom 0)
       ]

      (doseq [value (:path route)]

        (if (= (count @formattedListOfCities) 0)
          (do
            (reset! formattedListOfCities (str @formattedListOfCities value " (" (get (:price route) 0) ")"))
            )
          (do
            (if (> (- (count (:path route)) 1) @index)
              (reset! formattedListOfCities (str @formattedListOfCities "> " value " (" (get (:price route) @index) ")"))
              (reset! formattedListOfCities (str @formattedListOfCities "> " value))
              )
            )
          )

        (reset! index (inc @index))
        )

      (if (> (count (:path route)) 0)
        (do
          (println "Route with prices for segments:" @formattedListOfCities)
          (println "Total price:" (:priceSum route))
          (println "Flights:" (:flights route))
          )
        (do
          (println "No ticket")
          )
        )
      )
    )
  )

;==========
;
;===INIT===
;
;==========
(def filePath "Datasets/flights-ICA1.csv")
(def isDebug false)
;(prepare-travel-plan "Prague" "Berlin" "g")