(ns com.grzm.logback.MainDiscriminator
  (:gen-class
   :extends ch.qos.logback.core.sift.AbstractDiscriminator
   :state state
   :init init
   :main false))

(def discriminating-key "mainClassName")

(def default-value "default")

(def discriminating-value (atom default-value))

(defn main-class-name []
  (->> (Thread/getAllStackTraces)
       .values
       (filter #(and (seq %) (= "main" (.getMethodName (last %)))))
       first
       last
       .getClassName))

(defn -init []
  [[] discriminating-value])

(defn -getDiscriminatingValue [this e]
  @(.state this))

(defn -getKey [this]
  discriminating-key)

(defn set-value
  ([]
   (set-value (main-class-name)))
  ([v]
   (reset! discriminating-value v)))
