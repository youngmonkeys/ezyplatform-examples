#
 # Copyright 2024 youngmonkeys.org
 # 
 # Licensed under the ezyplatform, Version 1.0.0 (the "License");
 # you may not use this file except in compliance with the License.
 # You may obtain a copy of the License at
 # 
 #     https://youngmonkeys.org/licenses/ezyplatform-1.0.0.txt
 # 
 # Unless required by applicable law or agreed to in writing, software
 # distributed under the License is distributed on an "AS IS" BASIS,
 # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 # See the License for the specific language governing permissions and
 # limitations under the License.
#

set -e
mvn -pl . clean install
mvn -pl book-store-admin-plugin clean install -Pexport,\!test
mvn -pl book-store-socket-app clean install -Pexport,\!test
mvn -pl book-store-socket-plugin clean install -Pexport,\!test
mvn -pl book-store-web-plugin clean install -Pexport,\!test
mvn -pl book-store-theme clean install -Pexport,\!test
ezy.sh package
ezy.sh export
