set -e
mvn package -Prelease-local,\!test -Dmaven.test.skip=true
ezy.sh export-java-docs
