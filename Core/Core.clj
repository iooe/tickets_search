(ns PCU_SC_ICA_1_2023.Core.Core
  (:require [clojure.string :as string :only [upper-case]])
  )

(def maxPrice 10001000)

(def maxPathLenght 5)

(defn process
  [allNodes, currentNode, fromNode, toNode, path, price, deadEnds]

  (let [
        isFoundResult (= currentNode toNode)
        isCompleted (= (count path) 0)
        theLowestPrice (atom -1)
        theLowestPricedNode (atom nil)
        currentNodeConnections (get allNodes currentNode)
        ]

    (if (or (= isFoundResult true) (= isCompleted true))
      (do
        {
         "path"  path,
         "price" price
         }
        )
      (do
        (doseq [node currentNodeConnections]
          (if
            (and
              ; if path not contains (get node "node")
              (not (= (some #(= (get node "connection") %) path) true))
              ; if deadEnds not contains (get node "node")
              (not (= (some #(= (get node "connection") %) deadEnds) true))
              ; if theLowestPricedNode is nil, or we found a new lowest price
              (or
                (= @theLowestPricedNode nil)
                (< (get node "price") @theLowestPrice)
                )
              )
            ; if we complete statements, then update theLowestPricedNode and theLowestPrice
            (do
              (reset! theLowestPricedNode (get node "connection"))
              (reset! theLowestPrice (get node "price"))
              )
            )
          )

        (println "in process" price)

        (if (> (+ (apply + price) @theLowestPrice) maxPrice)
          (println "BACK BECAUSE OUT OF THE MAX PRICE")
          )
        ; if the lowest price is -1 that means that there no any suitable connection for the path building
        (if (or
              (= @theLowestPrice -1)
              (> (+ (apply + price) @theLowestPrice) maxPrice)
              (> (+ (count path) 1) maxPathLenght))
          ; then
          (process allNodes,
                   (get path (- (count path) 2)),           ; use the parent node as the next main node
                   fromNode,
                   toNode,
                   (into [] (drop-last 1 path))             ; remove the last node from current path
                   (into [] (drop-last 1 price))
                   (conj deadEnds currentNode)              ; add current node to the deadEnds
                   )
          ; else
          (process allNodes,
                   @theLowestPricedNode,                    ; use the lowest priced connection of the current node as the next main node
                   fromNode,
                   toNode,
                   (conj path @theLowestPricedNode)         ; add the lowest priced connection of the current node to the path
                   (conj price @theLowestPrice)
                   deadEnds
                   )
          )
        )
      )
    )
  )


(defn init
  ; A map where every key is a node and every key's value is a vector that contains node's connections.
  ; Connections represents as a price and a node attributes.
  ;
  ; {"string": [{"price": int, "node": string}]}, string, string
  [nodes, fromNode, toNode, maxPrice]

  (def maxPrice maxPrice)
  ; start recursive function
  (process nodes (string/upper-case fromNode) (string/upper-case fromNode) (string/upper-case toNode) [(string/upper-case fromNode)] [] [])
  )













