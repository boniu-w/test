package wg.application.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/************************************************************************
 * author: wg
 * description: CustomerDecimalDeSerializer 反序列化器
 * createTime: 15:01 2023/4/13
 * updateTime: 15:01 2023/4/13
 ************************************************************************/
public class DecimalDeSerializer extends JsonDeserializer<BigDecimal> {
    
    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getValueAsString();
        if ("/".equals(value)) {
            return new BigDecimal("-99999.99");
        } else {
            return new BigDecimal(value);
        }
    }
}
