set -e
mvn -pl . clean install
mvn -pl sweet-bakery-sdk clean install
mvn -pl sweet-bakery-admin-plugin clean install -Pexport,\!test
mvn -pl sweet-bakery-web-plugin clean install -Pexport,\!test
mvn -pl sweet-bakery-theme clean install -Pexport,\!test
ezy.sh package
ezy.sh export
