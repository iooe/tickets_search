(ns clojure_ica.API
  (:require
    [clojure_ica.Adapters.StringDataAdapter :as StringDataAdapter]
    [clojure_ica.Core.Core :as SearchEngineCore3]
    )
  )

(def datasetRaw "Krakov,Warsaw,100,\nKrakow,Warsaw,100,\nHamburg,Berlin,100,\nWarsaw,Berlin,300,\nPrague,Berlin,200,\nMunich,Berlin,100,\nMunich,Innsbruck,100,\nVienna,Innsbruck,200,\nVienna,Budapest,300,\nWarsaw,Budapest,400,\nZagreb,Budapest,200,\nVienna,Rome,400,\nNapoli,Rome,200,\nNapoli,Rijeka,100,\nVienna,Prague,200,\nVienna,Rijeka,400,\nRijeka,Zagreb,100,\nVienna,Zagreb,300,\nMunich,Zagreb,400,\nInnsbruck,Rome,400,\nBudapest,Rome,400,\nBudapest,Berlin,300,\nPrague,Brno,100,\nPrague,Budapest,300,\n")
(def LIMITS_PRICE {"f" 700 "g" 1000})
;/
(def LIMITS_LENGTH {"f" 3 "g" 4})

(def isDebug true)
(defn process
  [start end type desiredUserPrice]

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
                     (StringDataAdapter/adapter datasetRaw)
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
       route (atom (last @routes))
       index (atom 0)
       ]

      ; find suggested price
      (let [state (atom false) finalValue (atom nil) prevValue (atom nil) currentValue (atom nil)]
        ; find two suggested prices: the first is less or equal to desiredUserPrice and the second is the next one
        ; to the first in asc order
        (doseq [v @routes]
          (if (= @state false)
            (do
              (if (>= desiredUserPrice (:priceSum v))
                (do
                  (reset! prevValue v)
                  )
                (do
                  (reset! currentValue v)
                  (reset! state true)
                  )
                )
              )
            )
          )

        (if (= @prevValue nil)
          (reset! finalValue @route)
          (if (= @currentValue nil)
            (reset! finalValue @prevValue)
            (do
              (if (> (abs (- (:priceSum @currentValue) desiredUserPrice)) (abs (- (:priceSum @prevValue) desiredUserPrice)))
                (reset! finalValue @prevValue)
                (reset! finalValue @currentValue)
                )
              )
            )
          )

        (reset! route @finalValue)
        )

      (doseq [value (:path @route)]

        (if (= (count @formattedListOfCities) 0)
          (do
            (reset! formattedListOfCities (str @formattedListOfCities value " (" (get (:price @route) 0) ")"))
            )
          (do
            (if (> (- (count (:path @route)) 1) @index)
              (reset! formattedListOfCities (str @formattedListOfCities "> " value " (" (get (:price @route) @index) ")"))
              (reset! formattedListOfCities (str @formattedListOfCities "> " value))
              )
            )
          )

        (reset! index (inc @index))
        )

      (if (> (count (:path @route)) 0)
        (do
          ;(println "Route with prices for segments:" @formattedListOfCities)
          ;(println "Total price:" (:priceSum @route))
          ;(println "Flights:" (:flights @route))

          {
           :segmentsWithPrice @formattedListOfCities
           :totalPrice (:priceSum @route)
           :flights (:flights @route)
           }
          )
        (do
          (println "No ticket")
          )
        )
      )
    )
  )


(def isDebug false)
;(println (process "Prague" "Berlin" "g" 789))
;(println (process "Zagreb" "Krakow" "g" 789))