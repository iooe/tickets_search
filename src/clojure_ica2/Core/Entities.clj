(ns clojure_ica2.Core.Entities)

(defrecord Node [name])
(defrecord Connection [name price])
(defrecord Log [name yob departure destination paid])