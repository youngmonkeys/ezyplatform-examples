mvn -pl . clean install & ^
mvn -pl book-store-sdk clean install & ^
mvn -pl book-store-admin-plugin clean install -Pexport,\!test & ^
mvn -pl book-store-socket-plugin clean install -Pexport,\!test & ^
mvn -pl book-store-socket-app clean install -Pexport,\!test & ^
mvn -pl book-store-web-plugin clean install -Pexport,\!test & ^
mvn -pl book-store-theme clean install -Pexport,\!test & ^
ezy.bat package & ^
ezy.bat export
