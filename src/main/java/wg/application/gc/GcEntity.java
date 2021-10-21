package wg.application.gc;

import java.math.BigDecimal;

public class GcEntity {
    private BigDecimal money;

    public GcEntity(BigDecimal money) {
        this.money = money;
    }

    public GcEntity() {
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    protected void finalize() throws Throwable {
        if (money.doubleValue() <= 0D) {
            System.out.println("gc dead : " + money);
        } else {
            System.out.println("gc success : " + money);
        }

        super.finalize();
    }
}
