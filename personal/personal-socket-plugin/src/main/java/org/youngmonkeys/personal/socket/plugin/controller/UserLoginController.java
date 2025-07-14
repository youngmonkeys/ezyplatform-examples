package org.youngmonkeys.personal.socket.plugin.controller;

import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import org.youngmonkeys.ezyplatform.socket.controller.SocketLoginController;
import org.youngmonkeys.ezyplatform.socket.service.SocketAdminService;
import org.youngmonkeys.ezyplatform.socket.service.SocketUserService;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_LOGIN;

@EzyEventHandler(USER_LOGIN)
public class UserLoginController extends SocketLoginController {

    public UserLoginController(
        SocketAdminService adminService,
        SocketUserService userService
    ) {
        super(adminService, userService);
    }
}
