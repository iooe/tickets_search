(ns clojure_ica2.Main
  (:require
    [clojure.string :only [split]]
    [clojure_ica2.Store :as Store]
    [clojure_ica.API :as SearchEngineApi]
   )
  )

(defn prepare_travel_plan
  [departure destination people]

  (if (> (count people) 0)
    (do
      (let [
            suggestedPrices (atom [])
            lastNameOfTheFirstPassenger (get (clojure.string/split (get (get people 0) 0) #" ") 0)
            isFamily (atom true)
            finalSuggestedPrice (atom 9999999999)
            passengersType (atom "f")
            ]

        ; Get median prices depends on the passengers
        (doseq [value people]
          (reset! suggestedPrices (conj @suggestedPrices (Store/getMedianByYob (get value 1))))
          (reset! suggestedPrices (conj @suggestedPrices (Store/getMedianByDepDes departure destination)))

          ; If the passengers is\'t a family
          (if (not (= lastNameOfTheFirstPassenger (get (clojure.string/split (get value 0) #" ") 0)))
           (do
             (reset! isFamily false)
             (reset! passengersType "g")
             )
            )
          )

        ; Generate final suggested price based on the average value of suggested prices
        (reset! finalSuggestedPrice (float (/ (apply + @suggestedPrices) (count @suggestedPrices))))

        (if (= @isFamily true)
          (reset! finalSuggestedPrice (float (/ @finalSuggestedPrice (:f Store/passengersTypesPriceModifiers))))
          (reset! finalSuggestedPrice (float (/ @finalSuggestedPrice (:g Store/passengersTypesPriceModifiers))))
          )

        ; Looking for the best ticket based on the final suggested price
        ; debug: (println (SearchEngineApi/process departure destination @passengersType @finalSuggestedPrice)
        (:totalPrice (SearchEngineApi/process departure destination @passengersType @finalSuggestedPrice))
        )
      )
    ; Border case: If the people argument is empty
    (do
      (println "[people] argument is empty")
      9999999999
      )
    )
  )