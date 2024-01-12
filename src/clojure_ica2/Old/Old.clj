(ns clojure_ica2.Old
  (:require
    [clojure_ica2.Adapters.CsvDataAdapter :as CsvDataAdapter]
    [clojure.string :as string :only [split]]
    [clojure_ica2.Core.Utils :as Utils]
   )
  )


(def isDebug true)
(def filePath "")
(defn prepare_travel_plan
  [departure destination people]
  (let [
        values (atom [])
          depdes (atom {})
          destinations (atom {})
          temp (atom {})
          departures (atom {})
          isFamily (atom false)
        avgPrices (atom [])
        family (get (clojure.string/split (get (get people 0) 0) #" ") 0)
        isFamily (atom true)
        response (atom 9999999999)
        ]
    ;(println *ns*)
    ;  (reset! avgPrices (conj avgPrices (Utils/averageByDepartureDestinationPrice values "Brno" "Rijeka")))


    (reset! values (CsvDataAdapter/adapter (get (string/split (str *ns*) #"\.") 0) filePath))
    ;(reset! avgPrices (conj @avgPrices (Utils/averageByDepartureDestinationPrice values departure destination)))

    (doseq [value people]
      ;(reset! avgPrices (conj @avgPrices (Utils/averageByYob values (get value 1))))
      (reset! avgPrices (conj @avgPrices (Utils/averageByDepartureDestinationPriceAndYob values departure destination (get value 1))))

      (if (not (= family (get (clojure.string/split (get value 0) #" ") 0)))
        (reset! isFamily false)
        )
      )

    (reset! response (float (/ (apply + @avgPrices) (count @avgPrices))))

    (if (= @isFamily true)
      (float (/ @response 1.2))
      (float (/ @response 1.2))
      )
    )
  )

;==========
;
;===INIT===
;
;==========
(def filePath "Datasets/sales_team_1.csv")
;;
;(prepare_travel_plan "a" "a" [])

;(println (your_engine/prepare-travel-plan "Prague" "Berlin" "g"))

