(ns clojure-ICA_1.Core.Core
  (:require [clojure.string :as string :only [upper-case]])
  )

(defn process
  [allNodes, currentNode, fromNode, toNode, path, deadEnds]

 (let [
       isFoundResult (= currentNode toNode)
       isCompleted  (= (count path) 0)
       theLowestPrice (atom -1)
       theLowestPricedNode (atom nil)
       currentNodeConnections (get allNodes currentNode)
       ]

   (if (or (= isFoundResult true) (= isCompleted true))
     (do
       path
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

       ; if the lowest price is -1 that means that there no any suitable connection for the path building
       (if (= @theLowestPrice -1)
         ; then
         (process allNodes,
                      (get path (- (count path) 2)),               ; use the parent node as the next main node
                      fromNode,
                      toNode,
                      (into [] (drop-last 1 path))                 ; remove the last node from current path
                      (conj deadEnds currentNode)                  ; add current node to the deadEnds
                      )
         ; else
         (process allNodes,
                      @theLowestPricedNode,                         ; use the lowest priced connection of the current node as the next main node
                      fromNode,
                      toNode,
                      (conj path @theLowestPricedNode)              ; add the lowest priced connection of the current node to the path
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
  [nodes, fromNode, toNode]

  ; start recursive function
  (process nodes (string/upper-case fromNode) (string/upper-case fromNode) (string/upper-case toNode) [(string/upper-case fromNode)] [])
  )













