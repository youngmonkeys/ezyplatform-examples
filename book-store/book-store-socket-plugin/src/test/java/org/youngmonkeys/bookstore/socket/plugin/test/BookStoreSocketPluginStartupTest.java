package org.youngmonkeys.bookstore.socket.plugin.test;

import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.constant.EzyMaxRequestPerSecondAction;
import com.tvd12.ezyfoxserver.embedded.EzyEmbeddedServer;
import com.tvd12.ezyfoxserver.ext.EzyPluginEntry;
import com.tvd12.ezyfoxserver.setting.*;
import org.youngmonkeys.bookstore.socket.plugin.PluginEntryLoader;

public class BookStoreSocketPluginStartupTest {

    private static final String PLUGIN_NAME = "book-store";
    private static final String ZONE_NAME = "book-store";

    public static void main(String[] args) throws Exception {

        EzyPluginSettingBuilder pluginSettingBuilder = new EzyPluginSettingBuilder()
            .name(PLUGIN_NAME)
            .addListenEvent(EzyEventType.USER_LOGIN)
            .entryLoader(DecoratedPluginEntryLoader.class);

        EzyUserManagementSettingBuilder userManagementSettingBuilder = new EzyUserManagementSettingBuilder()
            .allowChangeSession(true)
            .allowGuestLogin(false)
            .guestNamePrefix("Guest#")
            .maxSessionPerUser(1)
            .userMaxIdleTimeInSecond(15)
            .userNamePattern("^[a-z0-9_.]{3,36}$");

        EzyZoneSettingBuilder zoneSettingBuilder = new EzyZoneSettingBuilder()
            .name(ZONE_NAME)
            .userManagement(userManagementSettingBuilder.build())
            .plugin(pluginSettingBuilder.build());

        EzySocketSettingBuilder socketSettingBuilder = new EzySocketSettingBuilder()
            .sslActive(true);

        EzyUdpSettingBuilder udpSettingBuilder = new EzyUdpSettingBuilder()
            .active(true);

        EzySessionManagementSettingBuilder sessionManagementSettingBuilder =
            new EzySessionManagementSettingBuilder()
                .sessionMaxRequestPerSecond(
                    new EzySessionManagementSettingBuilder.EzyMaxRequestPerSecondBuilder()
                        .value(60)
                        .action(EzyMaxRequestPerSecondAction.DISCONNECT_SESSION)
                        .build()
                );

        EzySimpleSettings settings = new EzySettingsBuilder()
            .socket(socketSettingBuilder.build())
            .udp(udpSettingBuilder.build())
            .zone(zoneSettingBuilder.build())
            .sessionManagement(sessionManagementSettingBuilder.build())
            .build();

        EzyEmbeddedServer server = EzyEmbeddedServer.builder()
            .settings(settings)
            .build();
        server.start();
    }

    public static class DecoratedPluginEntryLoader extends PluginEntryLoader {

        @Override
        public EzyPluginEntry load() {
            return new PluginEntry() {
                @Override
                protected String[] getScanablePackages() {
                    return new String[] { "org.youngmonkeys.bookstore" };
                }
            };
        }
    }
}
