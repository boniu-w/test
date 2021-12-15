package wg.application.string;

import com.alibaba.fastjson.JSONObject;

public class StringTest {
    public static void main(String[] args) {
        String s = "getRiskAssessmentData".toLowerCase();
        System.out.println(s);

        String s1 = (String) null;
        System.out.println(s1.length()); // NullPointerException

        System.out.println("---------------------");
    }
}
