(ns PCU_SC_ICA_1_2023.Core.Core3)

(defn dfs [graph node endNode visited currentPath currentPathPrice price allPaths]
  ;; Add node to the visited set
  (reset! visited (conj @visited node))

  ;; Add node to the current path array
  (reset! currentPath (conj @currentPath node))
  (reset! currentPathPrice (conj @currentPathPrice price))

  (if (= node endNode)
    ; Add current path to the list of all possible paths
    (reset! allPaths (conj @allPaths {:path (vec @currentPath) :price (filter  #(not (= % 0)) @currentPathPrice)}))

    ; Start dfs by neighbors of the node that equal to end node
    (doseq [[connectionName, connectionEntity] (get graph node {})]

      ; if connected node is not in the set of the visited nodes
      (if (not (contains? @visited connectionName))

        ; then start dfs again, but start node now is the current connected node
        (dfs graph connectionName endNode visited currentPath currentPathPrice (:price connectionEntity) allPaths)
        )
      )
    )

  ; Remove node from visited set
  (reset! visited (disj @visited node))

  ; Remove last element from the current path
  (reset! currentPath (pop @currentPath))
  (reset! currentPathPrice (pop @currentPathPrice))
  )

(defn init [graph start-node end-node]
  (let [
        visited (atom #{})
        currentPath (atom [])
        currentPathPrice (atom [])
        allPaths (atom [])
        ]

    ; Init
    (dfs graph start-node end-node visited currentPath currentPathPrice 0 allPaths)

    (map (fn [path]
           {
            :path (:path path)
            :price (:price path)
            :length (count (:path path))
            :priceSum (apply + (:price path))
            }
           ) @allPaths )
    )
  )
