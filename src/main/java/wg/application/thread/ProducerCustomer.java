package wg.application.thread;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * author wg
 * description ProducerCustomer 生产者 消费者
 * createTime 15:47 2025/2/7
 * updateTime 15:47 2025/2/7
 */
public class ProducerCustomer {

    private final Integer maxSize = 10;

    private ArrayBlockingQueue<Integer> arrayQueue = new ArrayBlockingQueue<>(maxSize);

    public static void main(String[] args) {
        ProducerCustomer producerCustomer = new ProducerCustomer();
        Customer customer = producerCustomer.new Customer();
        customer.start();

        for (int i = 0; i < 10; i++) {
            Producer producer = producerCustomer.new Producer();
            producer.start();
        }
        
    }

    // 生产者
    class Producer extends Thread {
        @Override
        public void run() {
            synchronized (arrayQueue) {
                if (arrayQueue.size() < maxSize) {
                    arrayQueue.add(arrayQueue.size() + 1);
                    System.out.println("已生产 = " + arrayQueue.size());
                    arrayQueue.notify();
                } else {
                    try {
                        arrayQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        arrayQueue.notify();
                    }
                }
            }
        }
    }

    // 消费者
    class Customer extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (arrayQueue) {
                    if (arrayQueue.isEmpty()) {
                        try {
                            arrayQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            arrayQueue.notify();
                        }
                    } else {
                        Integer poll = arrayQueue.poll();
                        System.out.println("已消费 队列 = " + poll);
                    }
                }
            }
        }
    }
}
