(ns clojure_ica2.Core.Utils)

(defn averageByDepartureDestinationPrice [values departure destination]

  (let [
        depdes (atom {})
        temp (atom {})
        ]

    (doseq [value @values]
      (reset! temp (str (:departure value) (:destination value)))
      (if (contains? @depdes @temp)
        (do
          (reset! depdes (assoc @depdes @temp (conj (get @depdes @temp) (:paid value))))
          )
        (do
          (reset! depdes (assoc @depdes @temp [(:paid value)]))
          )
        )
      )

    ;;depdes

    (float (/ (apply + (get @depdes (str departure destination))) (count (get @depdes (str departure destination)))))
    )
  )

(defn averageByYob [values yob]

  (let [
        depdes (atom {})
        temp (atom 0000)
        ]

    (doseq [value @values]
      (reset! temp (:yob value))
      (if (contains? @depdes @temp)
        (do
          (reset! depdes (assoc @depdes @temp (conj (get @depdes @temp) (:paid value))))
          )
        (do
          (reset! depdes (assoc @depdes @temp [(:paid value)]))
          )
        )
      )


    (float (/ (apply + (get @depdes yob)) (count (get @depdes yob))))
    )
  )