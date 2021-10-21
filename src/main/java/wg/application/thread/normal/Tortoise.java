package wg.application.thread.normal;

public class Tortoise extends Animal implements Runnable {

    private Double step;
    private Double length;
    private Boolean flag;

    {
        this.step = 1D;
        this.length = 0D;
        this.flag = true;
    }

    @Override
    protected void features() {
        System.out.println("tortoise speed slow");
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
            System.out.println("tortoise run " + length);
            Thread.sleep(100);
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

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
