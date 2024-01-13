(ns clojure_ica2.Main
  (:require
    [clojure.string :only [split]]
    [clojure_ica2.Store :as Store]
   )
  )

(defn prepare_travel_plan
  [departure destination people]

  (if (= (count people) 0)
    (do
      (let [
            avgPrices (atom [])
            family (get (clojure.string/split (get (get people 0) 0) #" ") 0)
            isFamily (atom true)
            response (atom 9999999999)
            ]

        (doseq [value people]
          (reset! avgPrices (conj @avgPrices (Store/getMedianByYob (get value 1))))
          (reset! avgPrices (conj @avgPrices (Store/getMedianByDepDes departure destination)))

          (if (not (= family (get (clojure.string/split (get value 0) #" ") 0)))
            (reset! isFamily false)
            )
          )

        (reset! response (float (/ (apply + @avgPrices) (count @avgPrices))))

        (if (= @isFamily true)
          (float (/ @response 1))
          (float (/ @response 1.4))
          )
        )
        (println "[people] argument is empty")
      )
    )
  )