package org.youngmonkeys.personal.socket.plugin;

import com.tvd12.ezyfoxserver.ext.EzyAbstractPluginEntryLoader;
import com.tvd12.ezyfoxserver.ext.EzyPluginEntry;
import com.tvd12.ezyfoxserver.support.entry.EzyDefaultPluginEntry;

public class PluginEntryLoader extends EzyAbstractPluginEntryLoader {
    @Override
    public EzyPluginEntry load() {
        return new PluginEntry();
    }

    public static class PluginEntry extends EzyDefaultPluginEntry {
        @Override
        protected boolean allowRequest() {
            return false;
        }
    }
}
