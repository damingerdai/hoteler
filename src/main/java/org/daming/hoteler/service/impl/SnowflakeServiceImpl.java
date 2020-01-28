package org.daming.hoteler.service.impl;

import org.daming.hoteler.service.ISnowflakeService;
import org.daming.hoteler.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SnowflakeServiceImpl implements ISnowflakeService, InitializingBean {

    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public long nextId() {
        return snowflakeIdWorker.nextId();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (Objects.isNull(snowflakeIdWorker)) {
            throw new Exception("snowflakeIdWorker is required");
        }
    }

    public SnowflakeServiceImpl() {
        super();
        this.snowflakeIdWorker = new SnowflakeIdWorker(1L, 1L);
    }


}
