# Notes

## Adding gdal.jar to a local repo

This assumes that you have downloaded, built, and compiled GDAL along with the Java SWIG bindings.

```
mkdir maven
mvn install:install-file -DgroupId=local -DartifactId=gdal \
    -Dversion=1.11.1 -Dpackaging=jar -Dfile=gdal.jar \
    -DlocalRepositoryPath=maven -DcreateChecksum=true
```

## Downloading ASTER and MODIS data

```
(cd resources/tmp/ && curl -O http://e4ftl01.cr.usgs.gov/ASTT/AST_L1AE.003/2014.11.20/AST_L1AE_00311202014164537_20141120112507_24093.hdf)
```