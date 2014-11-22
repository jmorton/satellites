(ns satellites.gdal
  (:require [clojure.pprint :refer [pprint pp]] :reload)
  (:require [satellites.img :as img] :reload)
  (:import (org.gdal.gdal gdal))
  (:import (org.gdal.gdalconst gdalconst)))
 
(gdal/AllRegister)

(defprotocol DatasetLike
  (metadata [f] [f domain])
  (subdatasets [f] [f & names])
  (xyz [r] "Get dimensions.  x: width, y: height, z: band count")
  (band [r n] "Get specific band")
  (bands [r] "Get all bands")
  (projection [r] "Shape of raster data"))

(defprotocol BandLike
  (unit [b] "Unit of measure (i.e. meters)")
  (region [b] [b x1 y1 x2 y2] "seq of raster")
  (img [b] [b x1 xy1 x2 y2] "img of raster"))

(defrecord Band [band]
  BandLike
  (unit [this]
    (.GetUnitType band))
  (region [this xoff yoff xsize ysize]
    (let [size   (* xsize ysize)
          buffer (byte-array size)
          _      (.ReadRaster band xoff yoff xsize ysize buffer)]
      (seq buffer)))
  (region [this]
    (let [xsize  (.GetXSize band)
          ysize  (.GetYSize band)]
      (region this 0 0 xsize ysize))))

(defrecord Dataset [ds]
  DatasetLike
  (metadata [this]
    (into {} (.GetMetadata_Dict ds)))
  (metadata [this domain]
    (into {} (.GetMetadata_Dict ds domain)))
  (subdatasets [this]
    (metadata this "SUBDATASETS"))
  (subdatasets [this & names]
    ((subdatasets this) names))
  (xyz [this]
    {:x (.GetRasterXSize ds)
     :y (.GetRasterYSize ds)
     :z (.GetRasterCount ds)})
  (bands [this]
    (let [count (.GetRasterCount ds)]
      ))
  (band [this n]
    (->Band (.GetRasterBand ds n)))
  (projection [r]
    "wut"))

(defn open [path]
  (->Dataset (gdal/Open path)))
