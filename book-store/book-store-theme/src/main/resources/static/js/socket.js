/*!
 * Copyright 2024 youngmonkeys.org
 * 
 * Licensed under the ezyplatform, Version 1.0.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     https://youngmonkeys.org/licenses/ezyplatform-1.0.0.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

EzyLogger.debug = true;

const ZONE_NAME = "book-store";
const APP_NAME = "book-store";

var handshakeHandler = new EzyHandshakeHandler();
handshakeHandler.getLoginRequest = function(context) {
    return [ZONE_NAME, "Guest", "123456", []];
}

var userLoginHandler = new EzyLoginSuccessHandler();
userLoginHandler.handleLoginSuccess = function() {
    this.client.send(EzyCommand.APP_ACCESS, [APP_NAME]);
}

var accessAppHandler = new EzyAppAccessHandler();
accessAppHandler.postHandle = function(app, data) {
    console.log("setup socket client completed");
}

var disconnectionHandler = new EzyDisconnectionHandler();
disconnectionHandler.preHandle = function(event) {
}

var config = new EzyClientConfig;
config.zoneName = ZONE_NAME;
var clients = EzyClients.getInstance();
var client = clients.newDefaultClient(config);
var setup = client.setup;
setup.addEventHandler(EzyEventType.DISCONNECTION, disconnectionHandler);
setup.addDataHandler(EzyCommand.HANDSHAKE, handshakeHandler);
setup.addDataHandler(EzyCommand.LOGIN, userLoginHandler);
setup.addDataHandler(EzyCommand.APP_ACCESS, accessAppHandler);
var setupApp = setup.setupApp(APP_NAME);
setupApp.addDataHandler("spin", function(app, data) {
    prize = data.result;
    playGame.prototype.spin();
});

client.connect("ws://127.0.0.1:2208/ws");

var sendSpinRequest = function() {
    var app = client.getApp();
    if(app != null) {
        app.send("spin");
    }
}
