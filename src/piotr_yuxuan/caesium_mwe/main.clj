(ns piotr-yuxuan.caesium-mwe.main
  (:require [org.clj-commons.byte-streams :as byte-streams]
            [com.github.lvh.caesium.crypto.box :as crypto])
  (:import (java.util Base64 Random))
  (:gen-class))

(defn example
  [^String decoded-key ^String secret-value]
  (->> (byte-streams/to-byte-array secret-value)
       (crypto/anonymous-encrypt decoded-key)
       (.encodeToString (Base64/getEncoder))))

(defn -main
  [& args]
  (println (example "decoded-key" "secret-value"))
  (System/exit 0))
