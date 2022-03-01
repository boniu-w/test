package wg.application.entity;

import wg.application.annotation.Excel;

public class Basic {
    @Excel(imageType = 1, name = "wg")
    private String iiii;

    public String getIiii() {
        return iiii;
    }

    public void setIiii(String iiii) {
        this.iiii = iiii;
    }
}
