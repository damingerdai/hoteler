package org.daming.hoteler.pojo.enums.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.daming.hoteler.pojo.enums.Enumerator;
import org.daming.hoteler.pojo.enums.RoomStatus;

import java.io.IOException;

/**
 * 基于jackson的RoomStatus序列化
 *
 * @author gming001
 * @create 2021-03-15 15:36
 **/
public class JacksonRoomStatusDeserializer extends JsonDeserializer<RoomStatus> {

    @Override
    public RoomStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        var id = jsonParser.getIntValue();
        System.out.println(id);
        return RoomStatus.getInstance(id);
    }
}
