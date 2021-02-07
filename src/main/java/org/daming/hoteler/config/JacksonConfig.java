package org.daming.hoteler.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.daming.hoteler.pojo.enums.Enumerator;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Jackson配置
 *
 * @author gming001
 * @create 2021-02-07 15:47
 **/
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer enumCustomizer(){
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.serializerByType(Enumerator.class, new JsonSerializer<Enumerator>() {
            @Override
            public void serialize(Enumerator value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(value.id());
            }
        });
    }

}
