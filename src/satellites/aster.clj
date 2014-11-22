(ns satellites.aster
  (:require [satellites.gdal :as gdal :refer :all]
            [satellites.img :as img] :reload))

(defn tir-bands [ds]
  (let [sds     (gdal/subdatasets ds)
        img-rgx (fn [[k v]] (seq? (re-seq #"ImageData" v)))
        tir     (filter img-rgx sds)]
    tir))

(def file "resources/tmp/AST_L1AE_00310312014170929_20141031130318_21387.hdf")
(def hdf (open file))
(def tir (tir-bands hdf))
(def vnir-1 (open (get (subdatasets hdf) "SUBDATASET_8_NAME")))
(def vnir-2 (open (get (subdatasets hdf) "SUBDATASET_17_NAME")))
(def vnir-3 (open (get (subdatasets hdf) "SUBDATASET_26_NAME")))

(def vnir-img (let [r1 (region (band vnir-1 1))
                    r2 (region (band vnir-2 1))
                    r3 (region (band vnir-3 1))
                    {w :x h :y} (xyz vnir-1)]
                (img/raster-to-img r3 w h)))
