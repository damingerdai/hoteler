package org.daming.hoteler.mq.disruptor;

import com.lmax.disruptor.RingBuffer;
import org.daming.hoteler.pojo.HotelerMessage;

public class HotelerMessageProducer {

    private final RingBuffer<HotelerMessage> ringBuffer;

    public HotelerMessageProducer(RingBuffer<HotelerMessage> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(HotelerMessage message) {
        long sequence = ringBuffer.next();
        try {
            HotelerMessage ms = ringBuffer.get(sequence);
            ms.setContent(message.getContent());
            ms.setEvent(message.getEvent());
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
