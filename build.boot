(def project 'com.grzm/logback-main-discriminator)
(def version "0.1.0")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]
                            [ch.qos.logback/logback-classic "1.1.8"]])

(task-options!
  aot {:namespace #{'com.grzm.logback.MainDiscriminator}}
  pom {:project     project
       :version     version
       :description "Logback Discrimator based on application main class"
       :url         "https://github.com/grzm/logback-main-discriminator"
       :scm         {:url "https://github.com/grzm/logback-main-discriminator"}
       :license     {"MIT"
                     "https://opensource.org/licenses/MIT"}})

(deftask build
  "Build and install the project locally."
  []
  (comp (aot) (pom) (jar) (install)))

(task-options!
  push
  {:repo "clojars"
   :gpg-sign false})

(deftask deploy []
  (do
    (config-repos!)
    (comp (pom) (jar) (push))))
