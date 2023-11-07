(ns PCU_SC_ICA_1_2023.Core.Core2
  (:require [PCU_SC_ICA_1_2023.Core.Entities :as Entities]
            [clojure.string :as string :only [upper-case]])
  )


(def all_nodes
  [
   (Entities/->Node "Independence City")
   (Entities/->Node "Chimney Rock")
   (Entities/->Node "Fort Laramie")
   (Entities/->Node "Pine Canyon")
   (Entities/->Node "Ambush!")
   (Entities/->Node "Trade Post")
   (Entities/->Node "Colorado River")
   (Entities/->Node "Donner Pass")
   (Entities/->Node "Salt Desert")
   (Entities/->Node "Oregon City")
   ]
  )

(def all_edges
  [
   (Entities/->Edge "Independence City" "Chimney Rock" 0)
   (Entities/->Edge "Independence City" "Fort Laramie" 1)
   (Entities/->Edge "Chimney Rock" "Pine Canyon" 2)
   (Entities/->Edge "Fort Laramie" "Pine Canyon" 3)
   (Entities/->Edge "Pine Canyon" "Ambush!" 4)
   (Entities/->Edge "Pine Canyon" "Trade Post" 5)
   (Entities/->Edge "Ambush!" "Colorado River" 6)
   (Entities/->Edge "Trade Post" "Colorado River" 7)
   (Entities/->Edge "Colorado River" "Donner Pass" 8)
   (Entities/->Edge "Colorado River" "Salt Desert" 9)
   (Entities/->Edge "Donner Pass" "Oregon City" 10)
   (Entities/->Edge "Salt Desert" "Oregon City" 11)
   ]
  )

(def paths [])
(defn dfs-search
  [start-node target-node best-path best-food]

  (let [visited (set [start-node])
        target-node-name (:name target-node)
        ]

    (defn dfs [current-node path consumed-food]

      (when current-node

        (println (str "Visiting node: " (:name current-node)))

        (if (= (:name current-node) target-node-name)
          (do
            (def paths (conj paths path))

            (println "We reach Oregon City!")
            (if (or (and (> consumed-food @best-food))
                    (empty? @best-path))
              (do

                (reset! best-path path)
                (reset! best-food consumed-food)
                (println "Found a better path!"))
              (do

                (println "Current path is not better.")
                )
              )
            )

         (do
           (println "<<<" current-node)
           (doseq [edge all_edges
                   :when (= (:node1 edge) (:name current-node))
                   :let [next-node (first (filter #(= (:name %) (:node2 edge)) all_nodes))]
                   :when (not (contains? visited next-node))]

             (println ">>>" next-node)

             ;(println "path" (conj path edge))
             (dfs next-node
                  (conj path edge)
                  (+ consumed-food (:supplies edge))
                  )
             )
           )
          )
        (println (str "Backtracking from node: " (:name current-node)))
        )
      )

    (dfs start-node [] 0)                                   ; Start DFS from the beginning

    (if (seq @best-path)
      (do
        (println "Best path found:")

        (doseq [edge @best-path]
          (println (str "From " (:node1 edge) " to " (:node2 edge) ", Supplies: " (:supplies edge)))
          )

        (println (str "Total Consumed Food: " @best-food))
        (run! println paths)
        )
      (println "No valid path found")
      )
    )
  )

;(let [best-path-atom (atom [])
;      best-food-atom (atom 0)]
;  (dfs-search (first all_nodes) (last all_nodes) best-path-atom best-food-atom)
;
;  )


(defn init
  ; A map where every key is a node and every key's value is a vector that contains node's connections.
  ; Connections represents as a price and a node attributes.
  ;
  ; {"string": [{"price": int, "node": string}]}, string, string
  [nodes, edges, fromNode, toNode, maxPrice]
  (def all_nodes nodes)
  (def all_edges edges)

 ; (println "=========================all_nodes")
 ;(run! println nodes)
 ; (println toNode)

  (let [best-path-atom (atom [])
        best-food-atom (atom 0)]
    (dfs-search (Entities/->Node (string/upper-case fromNode)) (Entities/->Node (string/upper-case toNode)) best-path-atom best-food-atom)
    )
  )
