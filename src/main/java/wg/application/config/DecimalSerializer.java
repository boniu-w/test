package wg.application.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/************************************************************************
 * @author: wg
 * @description: 自定义序列化器, 将bigdecimal 转换为 string
 * @params:
 * @return:
 * @createTime: 14:56  2023/4/13
 * @updateTime: 14:56  2023/4/13
 ************************************************************************/
public class DecimalSerializer extends JsonSerializer<BigDecimal> {
    
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value.compareTo(new BigDecimal("-99999.99")) == 0) {
            gen.writeString("/");
        } else {
            gen.writeNumber(value);
        }
    }
}
