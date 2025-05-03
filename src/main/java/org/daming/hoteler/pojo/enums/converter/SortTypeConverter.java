package org.daming.hoteler.pojo.enums.converter;

import org.daming.hoteler.pojo.enums.SortType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SortTypeConverter implements Converter<String, SortType>  {
    @Override
    public SortType convert(String source) {
        if (!StringUtils.hasText(source)) {
            return null;
        }
        var low = source.toLowerCase();
        if (low.equals("desc")) {
            return SortType.DESC;
        } else if (low.equals("asc")) {
            return SortType.ASC;
        }
        return null;
    }

//    @Override
//    public <U> Converter<String, U> andThen(Converter<? super OrderType, ? extends U> after) {
//        return Converter.super.andThen(after);
//    }
}
