package wg.application.string;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @param
 * @return
 * @author wg
 * @description stringbuffer test
 * @createTime 15:04 2025/12/22
 * @updateTime 15:04 2025/12/22
 */
public class StringBufferTest {

    private static final StringBuffer logBuffer = new StringBuffer();
    private static final StringBuilder logBuilder = new StringBuilder();

    // 测试 StringBuffer（线程安全）
    static class LogBufferTask implements Runnable {
        private final String threadName;
        private final Random random = new Random();

        public LogBufferTask(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 5; i++) {
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(5));
                    synchronized (logBuffer) { // 实际不需要，因为 StringBuffer 自带同步
                        logBuffer.append("[").append(System.currentTimeMillis()).append("] ")
                                .append("线程：").append(threadName).append(" ")
                                .append("执行第").append(i).append("次操作，状态：成功\n");
                    }
                }
            } catch (Exception e) {
                logBuffer.append("[").append(System.currentTimeMillis()).append("] ")
                        .append("线程：").append(threadName).append(" 异常: ").append(e.getMessage()).append("\n");
                e.printStackTrace();
            }
        }
    }

    // 测试 StringBuilder（非线程安全）
    static class LogBuilderTask implements Runnable {
        private final String threadName;
        private final Random random = new Random();

        public LogBuilderTask(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 5; i++) {
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(5));
                    // 注意：这里没有同步！
                    logBuilder.append("[").append(System.currentTimeMillis()).append("] ")
                            .append("线程：").append(threadName).append(" ")
                            .append("执行第").append(i).append("次操作，状态：成功\n");
                }
            } catch (Exception e) {
                // 捕获所有异常，包括 ArrayIndexOutOfBoundsException 等
                e.printStackTrace();
                logBuilder.append("[").append(System.currentTimeMillis()).append("] ")
                        .append("线程：").append(threadName).append(" 异常: ").append(e.getMessage()).append("\n");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 清空（虽然首次运行不需要）
        logBuffer.setLength(0);
        logBuilder.setLength(0);

        ExecutorService executor = Executors.newFixedThreadPool(6);

        // 先测试 StringBuilder（非线程安全）
        System.out.println("=== 开始测试 StringBuilder（非线程安全）===");
        for (int i = 1; i <= 3; i++) {
            executor.submit(new LogBuilderTask("BuilderThread-" + i));
        }

        // 再测试 StringBuffer（线程安全）
        System.out.println("=== 开始测试 StringBuffer（线程安全）===");
        for (int i = 1; i <= 3; i++) {
            executor.submit(new LogBufferTask("BufferThread-" + i));
        }

        executor.shutdown();
        if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }

        // 输出结果
        System.out.println("\n===== StringBuilder 日志（可能错乱）=====");
        System.out.println(logBuilder.toString());

        System.out.println("\n===== StringBuffer 日志（应完整有序）=====");
        System.out.println(logBuffer.toString());

        // 可选：检查 StringBuilder 是否出现异常格式（如缺少 ] 或 \n）
        // 或统计行数是否为 15（3线程×5次）
    }
}
