package wg.application.design.callback;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/************************************************************************
 * author: wg
 * description: CallBackMain 
 * createTime: 10:12 2024/4/1
 * updateTime: 10:12 2024/4/1
 ************************************************************************/
public class CallBackMain {
    // public static void main(String[] args) {
    //     // 创建事件发布者和事件监听器
    //     MyEventPublisher publisher = new MyEventPublisher();
    //     MyEventListener listener = new MyEventListener();
    //
    //     // 添加事件监听器
    //     publisher.addEventListener(listener);
    //
    //     // 触发事件
    //     publisher.raiseEvent();
    // }

    /**
     * 异步操作 的回调
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 异步操作
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000); // 模拟耗时操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 42;
        });

        // 注册回调函数
        future.thenAccept(result -> {
            System.out.println("Async operation completed with result: " + result);
        });

        // 阻塞等待异步操作完成
        future.get();
    }
}
