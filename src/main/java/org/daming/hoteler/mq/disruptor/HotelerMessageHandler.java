package org.daming.hoteler.mq.disruptor;

import com.lmax.disruptor.EventHandler;
import org.daming.hoteler.pojo.HotelerMessage;

public class HotelerMessageHandler implements EventHandler<HotelerMessage> {

    @Override
    public void onEvent(HotelerMessage message, long l, boolean b) throws Exception {

    }
}
