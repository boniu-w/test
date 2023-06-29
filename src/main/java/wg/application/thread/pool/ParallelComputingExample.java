package wg.application.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/************************************************************************
 * author: wg
 * description: 并行计算 使用线程池的例子
 * createTime: 9:27 2023/6/27
 * updateTime: 9:27 2023/6/27
 ************************************************************************/
public class ParallelComputingExample {
    public static void main(String[] args) {
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        // 创建任务列表
        List<Future<Integer>> resultList = new ArrayList<>();
        
        // 提交任务给线程池执行
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            // 提交Callable任务
            Future<Integer> future = executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println("Task " + taskId + " is being executed by " + Thread.currentThread().getName());
                    // 执行具体的计算逻辑
                    return taskId * taskId;
                }
            });
            resultList.add(future);
        }
        
        // 等待所有任务完成并获取结果
        for (Future<Integer> future : resultList) {
            try {
                Integer result = future.get();
                System.out.println("Result: " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        
        // 关闭线程池
        executor.shutdown();
    }
    
    // public static void main(String[] args) {
    //     // 创建线程池
    //     ExecutorService executor = Executors.newFixedThreadPool(4);
    //
    //     // 创建任务列表
    //     List<Callable<Integer>> taskList = new ArrayList<>();
    //
    //     // 创建任务并添加到任务列表
    //     for (int i = 1; i <= 10; i++) {
    //         final int taskId = i;
    //         taskList.add(new Callable<Integer>() {
    //             @Override
    //             public Integer call() throws Exception {
    //                 System.out.println("Task " + taskId + " is being executed by " + Thread.currentThread().getName());
    //                 // 执行具体的计算逻辑
    //                 return taskId * taskId;
    //             }
    //         });
    //     }
    //
    //     try {
    //         // 提交任务列表并等待所有任务执行完毕
    //         List<Future<Integer>> resultList = executor.invokeAll(taskList);
    //
    //         // 输出结果
    //         for (Future<Integer> future : resultList) {
    //             Integer result = future.get();
    //             System.out.println("Result: " + result);
    //         }
    //     } catch (InterruptedException | ExecutionException e) {
    //         e.printStackTrace();
    //     }
    //
    //     // 关闭线程池
    //     executor.shutdown();
    // }
}
