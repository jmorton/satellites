(ns satellites.img
  (:import [javax.imageio ImageIO IIOImage]
           [java.awt.image BufferedImage]
           [java.io ByteArrayOutputStream ByteArrayInputStream File]
           [java.nio.file Files Path]))

;; converts an encoded image into bytes (useful for output to stream)
(defn img-to-bytes [img type]
  "Create a byte array for an image.  The byte array itself is *not* a raster."
  (let [output  (ByteArrayOutputStream. )]
    (ImageIO/write img type output)
    (.toByteArray output)))

;; converts a raster to an image format (jpeg, png, etc...)
(defn raster-to-img [raster width height]
  "Create buffered image from raster."
  (let [img (java.awt.image.BufferedImage. width height BufferedImage/TYPE_INT_RGB)
        ;; my attempt to make an RGB image from multiple rasters.  I don't
        ;; quite know what I'm doing though.
        ;red     r1
        ;blue   (map #(bit-shift-left % 8) r2)
        ;gree   (map #(bit-shift-left % 16) r3)
        ;abgr   (map bit-and r b g)
        ]
    (.setRGB img 0 0 width height (int-array raster) 0 width)
    img))

;; converts a raster into encoded array of bytes
(defn raster-to-bytes [raster width height type]
  ;; TODO
  )

(defn random-color [n]
  (take n (repeatedly #(rand-int 0xffffff))))

(defn random-img [width height]
  (let [img (java.awt.image.BufferedImage. width height BufferedImage/TYPE_INT_RGB)
        colors (random-color (* width height))]
    (.setRGB img 0 0 width height (int-array colors) 0 width)
    img))
 
