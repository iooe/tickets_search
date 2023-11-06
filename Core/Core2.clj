(ns PCU_SC_ICA_1_2023.Core.Core2)

(defrecord Node [name])
(defrecord Edge [id node1 node2 supplies days])

(def all_nodes
  [
   (Node. "Independence City")
   (Node. "Chimney Rock")
   (Node. "Fort Laramie")
   (Node. "Pine Canyon")
   (Node. "Ambush!")
   (Node. "Trade Post")
   (Node. "Colorado River")
   (Node. "Donner Pass")
   (Node. "Salt Desert")
   (Node. "Oregon City")
   ]
  )

(def all_edges
  [
   (Edge. 1 "Independence City" "Chimney Rock" -30 7)
   (Edge. 2 "Independence City" "Fort Laramie" -20 10)
   (Edge. 3 "Chimney Rock" "Pine Canyon" -20 4)
   (Edge. 4 "Fort Laramie" "Pine Canyon" -10 5)
   (Edge. 5 "Pine Canyon" "Ambush!" -40 4)
   (Edge. 6 "Pine Canyon" "Trade Post" 40 10)
   (Edge. 7 "Ambush!" "Colorado River" -20 8)
   (Edge. 8 "Trade Post" "Colorado River" -30 10)
   (Edge. 9 "Colorado River" "Donner Pass" -25 12)
   (Edge. 10 "Colorado River" "Salt Desert" -20 8)
   (Edge. 11 "Donner Pass" "Oregon City" -10 6)
   (Edge. 12 "Salt Desert" "Oregon City" -20 4)
   ]
  )

(defn dfs-search
  [start-node target-node best-path best-food best-days]
  (let [visited (set [start-node])
        target-node-name (:name target-node)
        ]

    (defn dfs [current-node path consumed-food consumed-days]
      (when current-node
        (println (str "Visiting node: " (:name current-node)))
        (if (= (:name current-node) target-node-name)
          (do
            (println "We reach Oregon City!")
            (if (or (and (> consumed-food @best-food) (> consumed-days @best-days))
                    (empty? @best-path))
              (do
                (reset! best-path path)
                (reset! best-food consumed-food)
                (reset! best-days consumed-days)
                (println "Found a better path!"))
              (println "Current path is not better."))
            )
          (doseq [edge all_edges
                  :when (= (:node1 edge) (:name current-node))
                  :let [next-node (first (filter #(= (:name %) (:node2 edge)) all_nodes))]
                  :when (not (contains? visited next-node))]
            (dfs next-node
                 (conj path edge)
                 (+ consumed-food (:supplies edge))
                 (+ consumed-days (:days edge))
                 )))
        (println (str "Backtracking from node: " (:name current-node)))
        )
      )

    (dfs start-node [] 0 0)  ; Start DFS from the beginning

    (if (seq @best-path)
      (do
        (println "Best path found:")
        (doseq [edge @best-path]
          (println (str "From " (:node1 edge) " to " (:node2 edge) ", Supplies: " (:supplies edge) ", Days: " (:days edge))))
        (println (str "Total Consumed Food: " @best-food))
        (println (str "Total Consumed Days: " @best-days)))
      (println "No valid path found"))
    ))

(let [best-path-atom (atom [])
      best-food-atom (atom 0)
      best-days-atom (atom 0)]
  (dfs-search (first all_nodes) (last all_nodes) best-path-atom best-food-atom best-days-atom))
