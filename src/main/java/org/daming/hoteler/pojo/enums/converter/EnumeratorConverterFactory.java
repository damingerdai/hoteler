package org.daming.hoteler.pojo.enums.converter;

import org.daming.hoteler.pojo.enums.Enumerator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 编码 -> 枚举 转化器工厂类
 *
 * @see https://xkcoding.com/2019/01/30/spring-boot-request-use-enums-params.html
 * @author gming001
 * @create 2021-02-07 22:03
 **/
public class EnumeratorConverterFactory implements ConverterFactory<Integer, Enumerator> {

    private static final Map<Class, Converter> CONVERTERS = new HashMap<>();

    /**
     * 获取一个从 Integer 转化为 T 的转换器，T 是一个泛型，有多个实现
     * @param aClass 转换后的类型
     * @param <T>
     * @return 返回一个转化器
     */
    @Override
    public <T extends Enumerator> Converter<Integer, T> getConverter(Class<T> aClass) {
        Converter<Integer, T> converter = CONVERTERS.get(aClass);
        if (converter == null) {
            converter = new EnumeratorConverter<>(aClass);
            CONVERTERS.put(aClass, converter);
        }
        return converter;
    }
}
