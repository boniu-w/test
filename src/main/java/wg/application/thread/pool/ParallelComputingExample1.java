package wg.application.thread.pool;

import java.util.concurrent.ForkJoinPool;

/************************************************************************
 * author: wg
 * description: ParallelComputingExample1 
 * createTime: 9:40 2023/6/27
 * updateTime: 9:40 2023/6/27
 ************************************************************************/
public class ParallelComputingExample1 {
    
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        // 创建 ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        
        // 创建并行计算任务
        ParallelSumTask task = new ParallelSumTask(numbers, 0, numbers.length);
        
        // 提交任务给 ForkJoinPool 执行
        int result = forkJoinPool.invoke(task);
        
        System.out.println("Sum: " + result);
        
        // 关闭 ForkJoinPool
        forkJoinPool.shutdown();
    }
}
