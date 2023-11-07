(ns PCU_SC_ICA_1_2023.Core.Entities)

(defrecord Node [name])
(defrecord Edge [node1 node2 supplies])
(defrecord Connection [name price])