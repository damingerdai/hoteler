package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.HotelerMessage;

/**
 * @author gming001
 * @version 2023-07-23 17:52
 */
public interface IEventService {

    void publishEvent(HotelerMessage message) throws HotelerException;
    
    void receiveEvent(HotelerMessage message) throws HotelerException;
}
