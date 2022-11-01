package org.daming.hoteler.base.support;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.SerializationUtils;

/**
 * @author gming001
 * @version 2022-10-31 20:32
 */
public class ByteArrayRedisSerializer implements RedisSerializer<Object> {

    private ClassLoader classLoader;

    /**
     * @see org.springframework.data.redis.serializer.RedisSerializer#serialize(Object)
     * @param obj object to serialize. Can be {@literal null}.
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        return SerializationUtils.serialize(obj);
    }

    /**
     * @see org.springframework.data.redis.serializer.RedisSerializer#deserialize(byte[])
     * @param bytes object binary representation. Can be {@literal null}.
     * @return
     * @throws SerializationException
     */
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        var obj = SerializationUtils.deserialize(bytes);
        System.out.println(obj);
        System.out.println(obj.getClass());
        return SerializationUtils.deserialize(bytes);
    }

    @Override
    public boolean canSerialize(Class<?> type) {
        System.out.println(type);
        System.out.println(RedisSerializer.super.canSerialize(type));
        return RedisSerializer.super.canSerialize(type);
    }

    @Override
    public Class<?> getTargetType() {
        System.out.println( RedisSerializer.super.getTargetType());
        return RedisSerializer.super.getTargetType();
    }
}
