(ns clojure_ica2.Core.Utils)
(defn mean [coll]
  (let [sum (apply + coll)
        count (count coll)]
    (if (pos? count)
      (/ sum count)
      0)))
(defn median [coll]
  (let [sorted (sort coll)
        cnt (count sorted)
        halfway (quot cnt 2)]
    (if (odd? cnt)
      (nth sorted halfway) ; (1)
      (let [bottom (dec halfway)
            bottom-val (nth sorted bottom)
            top-val (nth sorted halfway)]
        (mean [bottom-val top-val])))))

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

    (if (= (get @depdes (str departure destination)) nil)
      (do
        (if (= (get @depdes (str destination departure)) nil)
          -1
          (float (median (get @depdes (str destination departure))))
          )
        )
      (do
        (float (median   (get @depdes (str departure destination))))
        )
      )
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

    ;(float (/ (apply + (get @depdes yob)) (count (get @depdes yob))))

    (float (median (get @depdes yob)))
    )
  )

(defn averageByDepartureDestinationPriceAndYob [values departure destination yob]

  (let [
        depdes (atom {})
        temp (atom {})
        ]

    (doseq [value @values]
      (reset! temp (str (:departure value) (:destination value) (:yob value)))
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
    (if (= (get @depdes (str departure destination yob)) nil)
      (do
        (if (= (get @depdes (str destination departure yob)) nil)
          -1
          (float (median (get @depdes (str destination departure yob))))
          )
        )
      (do
        (float (median   (get @depdes (str departure destination yob))))
        )
      )
    )
  )