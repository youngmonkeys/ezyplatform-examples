mvn -pl . clean install & ^
mvn -pl personal-sdk clean install & ^
mvn -pl personal-admin-plugin clean install -Pexport,\!test & ^
mvn -pl personal-web-plugin clean install -Pexport,\!test & ^
mvn -pl personal-theme clean install -Pexport,\!test & ^
ezy.bat package & ^
ezy.bat export
