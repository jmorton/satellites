# satellites

An experimental project that aims to simplify working with satellite data and expose it through an HTTP API.  Ultimately, it will drive the API provided by satellites.io

## Dependencies

This project relies on Java SWIG binding for GDAL (with a dependency on HDF4) because it provides access to ASTER and MODIS data.  Getting this configured is non-trivial! Even though gdal.jar is included in a project's maven repository native libraries must be installed.  These libraries also have their own dependencies, which are well beyond what I want to document here.

* GDAL: http://download.osgeo.org/gdal/1.11.1/gdal-1.11.1.tar.gz
* HDF4: http://www.hdfgroup.org/ftp/HDF/HDF_Current/src/hdf-4.2.10.tar.gz
