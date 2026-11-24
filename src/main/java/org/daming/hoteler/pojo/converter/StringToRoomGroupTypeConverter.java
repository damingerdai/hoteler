package org.daming.hoteler.pojo.converter;

import org.daming.hoteler.pojo.enums.RoomGroupType;
import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRoomGroupTypeConverter implements Converter<String, RoomGroupType> {

    @Override
    public RoomGroupType convert(@NonNull String source) {
        if (source.isBlank()) {
            return null;
        }
        return RoomGroupType.fromCode(source.trim());
    }
}
