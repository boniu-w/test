package wg.application;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayTest {

    @Test
    public void test(){
        String[] types = {"jpg", "txt", "xlsx", "png"};
        List<String> collect = Arrays.stream(types).collect(Collectors.toList());

        collect.add("aa");
        System.out.println(collect);

        collect.remove("aa");
        String[] array = collect.toArray(new String[collect.size()]);
        System.out.println(array.length);
    }
}
