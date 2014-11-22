(defproject satellites "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://github.com/jmorton/satellites"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring "1.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [compojure "1.2.1"]
                 [http-kit "2.1.16"]
                 [enlive "1.1.5"]
                 [local/gdal "1.11.1"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler satellites.server/handler}
  :repositories {"local" ~(str (.toURI (java.io.File. "maven")))}
  :jvm-opts ["-Djava.library.path=/usr/local/lib/"])

