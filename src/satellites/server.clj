(ns satellites.server
  (:require [clojure.java.io :as io]
            [org.httpkit.server :as server]
            [ring.middleware.defaults :refer :all]
            [ring.util.response :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [satellites.gdal :as gdal]
            [satellites.img  :as img]
            [satellites.aster :as aster]
            :reload)
  (:import  [java.lang Integer]))

(defroutes app

  (GET "/" [] (str "<html><img src='/aster.jpg'></html>"))

  (GET "/aster.jpg" {{w :w h :h} :params}
       (let [img (img/img-to-bytes aster/vnir-img "jpg")]
         {:body (io/input-stream img) :status 200}))

  (GET "/random.jpg" {{w :w h :h} :params}
        (let [raster (img/random-img (Integer/parseInt w)
                                     (Integer/parseInt h))
              image  (img/raster-to-img raster "jpg")
              body   (io/input-stream image)]
              {:body body :status 200})))

(def handler
  (wrap-defaults #'app site-defaults))

(defn start []
  (server/run-server #'handler {:port 5678 }))


