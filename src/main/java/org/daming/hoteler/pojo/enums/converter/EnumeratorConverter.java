package org.daming.hoteler.pojo.enums.converter;

import org.daming.hoteler.pojo.enums.Enumerator;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 通用Enumerator转换器
 *
 * @see https://xkcoding.com/2019/01/30/spring-boot-request-use-enums-params.html
 * @author gming001
 * @create 2021-02-07 21:52
 **/
public class EnumeratorConverter<T extends Enumerator> implements Converter<Integer, T> {

    private Map<Integer, T> enumMap = new HashMap<>();

    public EnumeratorConverter(Class<T> enumType) {
        super();
        T[] enums = enumType.getEnumConstants();
        for (T e: enums) {
            enumMap.put(e.id(), e);
        }
    }

    @Override
    public T convert(Integer source) {
        T t = enumMap.get(source);
        if (Objects.isNull(t)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }
}
