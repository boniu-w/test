package wg.application.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
    
    /************************************************************************
     * @author: wg
     * @description: fastjson 将 jsonstring -> JsonNode
     * @params:
     * @return:
     * @createTime: 10:00  2023/5/26
     * @updateTime: 10:00  2023/5/26
     ************************************************************************/
    public static JsonNode getJsonNode(String jsonStr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(jsonStr);
    }

    /**
     * @author: wg
     * @description: 无论对象 还是 list 对象 都可以转string
     * @params:
     * @return:
     * @createTime: 15:07  2024/5/22
     * @updateTime: 15:07  2024/5/22
     */
    public static <T> String toJsonString(T t) {
        // 使用 Jackson 的 ObjectMapper 将对象转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author: wg
     * @description: 转 对象
     * @params:
     * @return:
     * @createTime: 15:07  2024/5/22
     * @updateTime: 15:07  2024/5/22
     */
    public static <T> T toClass(String jsonStr, Class<T> tClass) {
        // 使用 Jackson 的 ObjectMapper 将 JSON 字符串转换为 Java 对象
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonStr, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author: wg
     * @description: 转 对象 list
     * @params:
     * @return:
     * @createTime: 15:07  2024/5/22
     * @updateTime: 15:07  2024/5/22
     */
    public static <T> List<T> toObjList(String jsonString, Class<T> tClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            return null;
        }
    }
}
