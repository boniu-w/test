package wg.application.enumeration;

/************************************************************************
 * author wg
 * description Test 
 * createTime 9:39 2024/8/5
 * updateTime 9:39 2024/8/5
 ************************************************************************/
public class Test {

    public static void main(String[] args) {
        String defaultColor = ColorEnum.RED.getDefaultColor();
        System.out.println("defaultColor = " + defaultColor);

        String colorCode = ColorEnum.RED.getColorCode();
        System.out.println("colorCode = " + colorCode);
    }
}
