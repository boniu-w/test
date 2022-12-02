package wg.application.datastructure.huangxingduilie;

import java.util.Arrays;

public class CircularQueue<T> {
    //属性
    private int front;//队头指针
    private int rear;//队尾指针
    private int maxSize;//队列最大容量
    private int size;//队列数据个数
    private Object[] queue;//队列数组

    //空参构造器
    public CircularQueue() {
        front = -1;
        rear = -1;
        maxSize = 16;
    }

    //指定容量构造器
    public CircularQueue(int maxSize) {
        this();
        this.maxSize = maxSize;
    }

    //获取队列数据个数
    public int getSize() {
        return size;
    }

    //添加数据
    public void add(T t) {
        //懒汉式：要添加数据了才创建数组
        if (queue == null) {
            queue = new Object[maxSize];
        }
        //判断队列是否已满
        if (size >= maxSize) {
            throw new RuntimeException("队列已满");
        }
        //走到这说明队列没满，考虑边界情况
        if (rear == maxSize - 1) {
            rear = -1;//循环到队伍开头，形成闭环
        }
        //真正添加数据的操作
        queue[++rear] = t;
        //数据个数加一
        size++;
    }

    //弹出数据
    public T pop() {
        //判断队列是否为空
        if (queue == null || queue.length == 0 || size == 0) {
            throw new RuntimeException("队列为空");
        }
        //边界情况
        if (front == maxSize - 1) {
            front = -1;
        }
        T t = (T) queue[++front];
        queue[front] = null;
        size--;
        return t;
    }

    //重写toString()
    @Override
    public String toString() {
        return "CircularQueue{" +
                "queue=" + Arrays.toString(queue) +
                '}';
    }
}
