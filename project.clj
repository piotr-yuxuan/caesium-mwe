(defproject com.github.piotr-yuxuan/caesium-mwe (-> "./resources/caesium-mwe.version" slurp .trim)
  :description "GraalVM native compilation issues with JNI Clojure library caesium-mwe"
  :url "https://github.com/piotr-yuxuan/caesium-mwe"
  :license {:name "European Union Public License 1.2 or later"
            :url "https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12"
            :distribution :repo}
  :scm {:name "git"
        :url "https://github.com/piotr-yuxuan/caesium-mwe"}
  :pom-addition [:developers [:developer
                              [:name "胡雨軒 Петр"]
                              [:url "https://github.com/piotr-yuxuan"]]]
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [caesium "0.14.0"]]
  :main piotr-yuxuan.caesium-mwe.main
  :aot :all
  :jvm-opts ["-Dclojure.compiler.direct-linking=true"]
  :profiles {:github {:github/topics ["github" "libsodium" "caesium" "graalvm" "native"
                                      "native-exec" "compilation" "crypto"]}
             :provided {:dependencies [[org.clojure/clojure "1.10.3"]]}
             :dev {:global-vars {*warn-on-reflection* true}
                   :plugins [[lein-shell "0.5.0"]]}
             :uberjar-clj-easy {:dependencies [[com.github.clj-easy/graal-build-time "0.1.4"]]}
             :uberjar-initialize {}}

  :aliases {"uberjar-clj-easy" ["with-profile" "+uberjar-clj-easy" "uberjar"]
            "native-clj-easy" ["shell"
                               "native-image"
                               "--no-fallback"
                               "-jar" "./target/${:uberjar-name:-${:name}-${:version}-standalone.jar}"
                               "-H:Name=./target/${:name}-${:version}"]

            "uberjar-initialize" ["with-profile" "+uberjar-initialize" "uberjar"]
            "native-initialize" ["shell"
                                 "native-image"
                                 "--no-fallback"
                                 "--initialize-at-build-time"
                                 "-jar" "./target/${:uberjar-name:-${:name}-${:version}-standalone.jar}"
                                 "-H:Name=./target/${:name}-${:version}"]})
