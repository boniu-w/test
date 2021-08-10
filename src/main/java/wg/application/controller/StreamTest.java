package wg.application.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wg
 * @Package wg.application.controller
 * @date 2020/4/21 14:12
 * @Copyright
 */
public class StreamTest {


    public static void main(String[] args) {
        List<Integer> myList = new ArrayList<>();
        for (int i = 0; i < 1E+7; i++) {
            myList.add(i);
        }
        long result = 0;
        long loopStartTime = System.currentTimeMillis();
        for (int i : myList) {
            if (i % 2 == 0) {
                result += i; // 所有偶数的和
            }
        }
        long loopEndTime = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("Loop total Time = " + (loopEndTime - loopStartTime));

        long streamStartTime = System.currentTimeMillis();
        System.out.println(myList.stream().filter(value -> value % 2 == 0).mapToLong(Long::valueOf).sum());
        long streamEndTime = System.currentTimeMillis();
        System.out.println("Stream total Time = " + (streamEndTime - streamStartTime));

        long parallelStreamStartTime = System.currentTimeMillis();
        System.out.println(myList.parallelStream().filter(value -> value % 2 == 0).mapToLong(Long::valueOf).sum());
        long parallelStreamEndTime = System.currentTimeMillis();
        System.out.println("Parallel Stream total Time = " + (parallelStreamEndTime - parallelStreamStartTime));
    }


}
