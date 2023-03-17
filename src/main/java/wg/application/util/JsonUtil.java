package wg.application.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/************************************************************************
 * author: wg
 * description: JsonUtil 
 * createTime: 11:35 2023/3/16
 * updateTime: 11:35 2023/3/16
 ************************************************************************/
public class JsonUtil {

    /************************************************************************
     * @author: wg
     * @description: jsonnode -> map
     * @params: JsonNode
     * @return: Map
     * @createTime: 13:57  2023/3/16
     * @updateTime: 13:57  2023/3/16
     ************************************************************************/
    public static Map<String, Object> convertJsonNodeToMap(JsonNode jsonNode) {
        Map<String, Object> map = new HashMap<>();

        if (jsonNode.isObject()) {
            Iterator<String> fieldNames = jsonNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode fieldValue = jsonNode.get(fieldName);
                if (fieldValue.isValueNode()) {
                    map.put(fieldName, fieldValue.asText());
                } else {
                    map.put(fieldName, convertJsonNodeToMap(fieldValue));
                }
            }
        } else if (jsonNode.isArray()) {
            for (int i = 0; i < jsonNode.size(); i++) {
                JsonNode arrayElement = jsonNode.get(i);
                if (arrayElement.isValueNode()) {
                    map.put(Integer.toString(i), arrayElement.asText());
                } else {
                    map.put(Integer.toString(i), convertJsonNodeToMap(arrayElement));
                }
            }
        }

        return map;
    }

    public static Map<String, Object> convertJsonNode2Map(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.convertValue(jsonNode, new TypeReference<Map<String, Object>>() {
        });

        return map;
    }

    public static void main(String[] args) {
        String jsonString = "{\"name\":\"John\", \"age\":30, \"city\":\"New York\"}";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> map = mapper.convertValue(jsonNode, new TypeReference<Map<String, Object>>() {
        });

        System.out.println(map);
    }

    /************************************************************************
     * @author: wg
     * @description: Map 转 jsonstring
     * @params:
     * @return:
     * @createTime: 10:33  2023/3/17
     * @updateTime: 10:33  2023/3/17
     ************************************************************************/
    public static String map2json(Map<String, Object> map) {
        // 创建一个 ObjectMapper 对象
        ObjectMapper mapper = new ObjectMapper();

        // 使用 ObjectMapper 的 writeValueAsString() 方法将 Map 转换成 JSON 字符串
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonString;
    }

    /************************************************************************
     * @author: wg
     * @description: 数组转 json
     * @params:
     * @return:
     * @createTime: 13:53  2023/3/17
     * @updateTime: 13:53  2023/3/17
     ************************************************************************/
    public static String arrayToJson(Object[] objArray) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(objArray);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
