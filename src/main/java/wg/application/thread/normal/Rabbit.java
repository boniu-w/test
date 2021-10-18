package wg.application.thread.normal;

public class Rabbit extends Animal implements Runnable {

    private Double step;
    private Double length;
    private boolean flag;

    {
        this.step = 3D;
        this.length = 0D;
        this.flag = true;
    }

    /**
     * 特征
     */
    @Override
    protected void features() {
        System.out.println("rabbit speed fast");
    }

    @Override
    public void run() {
        running();
    }

    public void running() {
        while (!flag) {
            return;
        }
        try {
            length += step;
            System.out.println("rabbit run " + length);
            Thread.sleep(1000);
            running();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止当前线程
     */
    public void stopCurrentThread() {
        this.flag = false;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }
}
