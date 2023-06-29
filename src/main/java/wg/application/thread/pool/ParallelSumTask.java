package wg.application.thread.pool;

import java.util.concurrent.RecursiveTask;

public class ParallelSumTask extends RecursiveTask<Integer> {
    private int[] numbers;
    private int start;
    private int end;
    
    public ParallelSumTask(int[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }
    
    @Override
    protected Integer compute() {
        if (end - start <= 2) { // 设置一个阈值，当任务足够小时直接计算结果
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;
        } else { // 将任务拆分成更小的子任务并行执行
            int mid = (start + end) / 2;
            ParallelSumTask leftTask = new ParallelSumTask(numbers, start, mid);
            ParallelSumTask rightTask = new ParallelSumTask(numbers, mid, end);
            
            leftTask.fork(); // 异步执行左侧子任务
            int rightResult = rightTask.compute(); // 同步执行右侧子任务
            
            int leftResult = leftTask.join(); // 等待并获取左侧子任务的结果
            
            return leftResult + rightResult;
        }
    }
}