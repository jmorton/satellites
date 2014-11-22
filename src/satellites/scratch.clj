(ns satellites.scratch
    (:require [net.cgrand.enlive-html :as html]))

(defn random-bytes [n]
  (byte-array (take n (repeatedly #(rand-int 0 255)))))

(defn random-tiff [path x y]
  (let [driver (gdal/GetDriverByName "GTiff")
        tiff   (.Create driver (str path ".tiff") x y 1 gdalconst/GDT_Byte)
        band   (.GetRasterBand tiff 1)
        vals   (byte-array (take (* x y) (repeatedly #(rand-int 255))))]
    (.WriteRaster band 0 0 x y vals)
    (.SetColorInterpretation band gdalconst/GCI_GrayIndex)
    (.FlushCache tiff)
    tiff))

(defn random-jpeg [path x y]
  (let [driver (gdal/GetDriverByName "JPEG")
        tiff   (random-tiff path x y)
        jpeg   (.CreateCopy driver (str path ".jpeg") tiff 0)
        bytes  (byte-array (* x y))]
    (.FlushCache jpeg)
    (.ReadRaster jpeg 0 0 x y x y 1 bytes (int-array [1]))
    bytes))

(defn example []
  (let [path "resources/AST_L1AE_00310242014045229_20141024001140_15451.hdf"
        hdf (open path)
        sub (open ((subdatasets hdf) "SUBDATASET_7_NAME"))
        b   (band sub 1)]
    sub))

(html/defsnippet base-listing "base.html"
  [:ul :li] [[key value]]
  [:li] (html/content value))

(html/deftemplate base-page "base.html" [hdf-file]
  [:title] (html/content (:title hdf-file "Some HDF File"))
  [:h1] (html/content (:foo hdf-file))
  [:ul] (html/content (map base-listing (gdal/metadata hdf-file))))

