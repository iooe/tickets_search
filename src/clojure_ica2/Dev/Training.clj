(ns clojure_ica2.Dev.Training
  (:require
    [clojure_ica2.Dev.CsvDataAdapter :as CsvDataAdapter]
    [clojure.string :as string :only [split]]
   )
  )

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

(defn averageByDepartureDestinationPrice [values]
  (let [
        depdes (atom {})
        temp (atom {})
        response (atom {})
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

    (doseq [value @depdes]
      (reset! response (assoc @response (get value 0) (float (median (get @depdes (get value 0))))))
      ;(reset! response (assoc @response (get value 0) (float (/ (apply + (get @depdes (get value 0))) (count (get @depdes (get value 0)))))))
      )

    @response
    )
  )


(defn averageByYob [values]

  (let [
        depdes (atom {})
        response (atom {})
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

    (doseq [value @depdes]
       (reset! response (assoc @response (get value 0) (float (median (get @depdes (get value 0))))))
      ;(reset! response (assoc @response (get value 0) (float (/ (apply + (get @depdes (get value 0))) (count (get @depdes (get value 0)))))))

      )

    @response
    )
  )


(defn averageByDepartureDestinationPriceAndYob [values]

  (let [
        depdes (atom {})
        temp (atom {})
        response (atom {})
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

    (doseq [value @depdes]
      (reset! response (assoc @response (get value 0) (float (median (get @depdes (get value 0))))))
      )

    @response
    )
  )

(defn run
  [filepath]
  (let [
        values (atom [])
        avgPrices (atom [])
        ]

    (reset! values (CsvDataAdapter/adapter (get (string/split (str *ns*) #"\.") 0) filepath))
    (println (conj @avgPrices (averageByYob values)))
    ;(println (conj @avgPrices (averageByDepartureDestinationPrice values)))
    )
  )

;==========
;
;===INIT===
;
;==========
(run "sales_team_1.csv")


