package org.daming.hoteler.pojo.enums.converter;

import org.daming.hoteler.pojo.enums.RoomStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * RoomStatus的转换器
 *
 * @author gming001
 * @create 2021-03-15 16:07
 **/
@Component
public class RoomStatusConverter implements Converter<String, RoomStatus> {

    @Override
    public RoomStatus convert(String s) {
        try {
            var id = Integer.parseInt(s);
            return RoomStatus.getInstance(id);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
