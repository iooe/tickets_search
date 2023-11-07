(ns PCU_SC_ICA_1_2023.Main
  (:require [PCU_SC_ICA_1_2023.Adapters.CsvDataAdapter3 :as CsvDataAdapter3])
  (:require [PCU_SC_ICA_1_2023.Core.Core3 :as SearchEngineCore3])
  )


(run! println (SearchEngineCore3/init
           (CsvDataAdapter3/adapter "Datasets/flights-ICA1.csv")
           "PRAGUE"
           "ZAGREB"
           )
         )
