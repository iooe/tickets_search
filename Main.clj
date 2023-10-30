(ns PCU_SC_ICA_1_2023.Main
  (:require [PCU_SC_ICA_1_2023.Adapters.CsvDataAdapter :as CsvDataAdapter])
  (:require [PCU_SC_ICA_1_2023.Core.Core :as SearchEngineCore])
  )


(println (SearchEngineCore/init
           (CsvDataAdapter/adapter "Datasets/flights-ICA1.csv")
           "Krakov"
           "Napoli"
           1300
           )
         )
