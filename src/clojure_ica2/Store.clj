(ns clojure_ica2.Store)

(def medianDepDes {
                   "KrakowZagreb" 808.0,
                   "PragueRijeka" 1020.0,
                   "HamburgRome" 808.0,
                   "ViennaWarsaw" 1111.0,
                   "BerlinNapoli" 1030.0,
                   "HamburgInnsbruck" 1212.0,
                   "InnsbruckWarsaw" 1212.0,
                   "BrnoRijeka" 1122.0
                   })


(def medianYob {1976 1040.0, 1984 1111.0, 1988 1111.0, 1973 1111.0, 1983 1070.5, 1981 1111.0, 1980 1030.0, 1985 1075.5, 1987 1127.5, 1977 1111.0, 1989 1111.0, 2018 808.0, 1992 1030.0, 2013 812.0, 1979 1122.0, 1993 1111.0, 1990 1111.0, 1982 1133.0, 1986 1075.5, 2017 808.0, 1975 1030.0, 2015 808.0, 2016 808.0, 1974 1111.0, 1978 1075.5, 2014 728.0, 1991 1133.0})
(defn getMedianByYob [value]
  (if (= nil (get medianYob value))
    ; border case, if there's no value in medianYob vector
    (/ (reduce + (map (fn [value] (get value 1)) (into [] medianYob))) (count medianYob))
    (get medianYob value)
    )
  )
(defn getMedianByDepDes [departure destination]
  (let [response (atom 9999999999)]
    (if (= (get medianDepDes (str departure destination)) nil)
      (do
        (reset! response (get medianDepDes (str destination departure)))
        )
      (do
        (reset! response (get medianDepDes (str departure destination)))
        )
      )

    (if (= @response nil)
      ; border case, if there's no value in response variable
      (/ (reduce + (map (fn [value] (get value 1)) (into [] medianDepDes))) (count medianDepDes))
      @response
      )
    )
  )

(def passengersTypesPriceModifiers {:f 1 :g 1.4})

