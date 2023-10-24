(ns clojure-ICA_1.Main
  (:require [clojure-ICA_1.Adapters.CsvDataAdapter :as CsvDataAdapter])
  (:require [clojure-ICA_1.Core.Core :as SearchEngineCore])
  )

(println (SearchEngineCore/init
           (CsvDataAdapter/adapter "Datasets/flights-ICA1.csv")
           "Krakov"
           "Budapest"
           )
         )


;(println (CsvDataAdapter/adapter "Datasets/flights-ICA1.csv"))

;(println (contains? (CsvDataAdapter/adapter "Datasets/flights-ICA1.csv") (string/upper-case "Prague")))

(def GRAPH_STRUCTURE {
                      "Ore mine"   [
                                    {
                                     "price" 9
                                     "connection"  "Blacksmith"
                                     }
                                    {
                                     "price" 2
                                     "connection"  "Tavern"
                                     }
                                    ]

                      "Blacksmith" [
                                    {
                                     "price" 9
                                     "connection"  "Ore mine"
                                     }
                                    {
                                     "price" 4
                                     "connection"  "Well"
                                     }
                                    ]

                      "Tavern"     [
                                    {
                                     "price" 2
                                     "connection"  "Ore mine"
                                     }
                                    {
                                     "price" 3
                                     "connection"  "Well"
                                     }
                                    {
                                     "price" 5
                                     "connection"  "House"
                                     }
                                    ]
                      "Well"       [
                                    {
                                     "price" 4
                                     "connection"  "Blacksmith"
                                     }
                                    {
                                     "price" 3
                                     "connection"  "Tavern"
                                     }
                                    {
                                     "price" 6
                                     "connection"  "Statue"
                                     }
                                    {
                                     "price" 8
                                     "connection"  "Church"
                                     }
                                    ]
                      "House"      [
                                    {
                                     "price" 5
                                     "connection"  "Tavern"
                                     }
                                    {
                                     "price" 6
                                     "connection"  "Statue"
                                     }
                                    ]
                      "Church"     [
                                    {
                                     "price" 8
                                     "connection"  "Well"
                                     }
                                    ]
                      "Foundry"    [
                                    {
                                     "price" 4
                                     "connection"  "Statue"
                                     }
                                    ]
                      "Statue"     [
                                    {
                                     "price" 4
                                     "connection"  "Foundry"
                                     }
                                    {
                                     "price" 6
                                     "connection"  "Well"
                                     }
                                    {
                                     "price" 6
                                     "connection"  "House"
                                     }
                                    ]
                      })

;(println (SearchEngineCore/init GRAPH_STRUCTURE "Ore mine" "Foundry"))
