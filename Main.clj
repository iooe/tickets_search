(ns clojure-ICA_1.Main
  (:require [clojure-ICA_1.Adapters.CsvDataAdapter :as CsvDataAdapter])
  (:require [clojure-ICA_1.Core.Core :as SearchEngineCore])
  )


(println (SearchEngineCore/init
           (CsvDataAdapter/adapter "Datasets/flights-ICA1.csv")
           "Krakov"
           "Napoli"
           1300
           )
         )
