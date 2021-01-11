package org.daming.hoteler.base.context;

import org.daming.hoteler.pojo.HotelerContext;

/**
 * Hoteler Local Thread Context Holder
 *
 * @author gming001
 * @create 2021-01-09 23:02
 **/
public class ThreadLocalContextHolder {

    private static final ThreadLocal<HotelerContext> THREAD_WITH_CONTEXT = ThreadLocal.withInitial(HotelerContext::new);

    private ThreadLocalContextHolder() {}

    public static void put(HotelerContext context) {
        THREAD_WITH_CONTEXT.set(context);
    }

    public static HotelerContext get() {
        return THREAD_WITH_CONTEXT.get();
    }

    public static void clean() {
        THREAD_WITH_CONTEXT.remove();
    }
}
