package org.youngmonkeys.personal.socket.app;

import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntryLoader;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;
import com.tvd12.ezyfoxserver.support.entry.EzyDefaultAppEntry;

public class AppEntryLoader extends EzyAbstractAppEntryLoader {

    @Override
    public EzyAppEntry load() throws Exception {
        return new AppEntry();
    }

    public static class AppEntry extends EzyDefaultAppEntry {}
}
