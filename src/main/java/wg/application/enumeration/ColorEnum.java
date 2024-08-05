package wg.application.enumeration;

/************************************************************************
 * author wg
 * description ColorEnum 使用方法 获取 和 EnumTest 一样
 * createTime 9:39 2024/8/5
 * updateTime 9:39 2024/8/5
 ************************************************************************/
public enum ColorEnum {

    GREEN {
        @Override
        public String getColorCode() {
            return "#00FF00";
        }
    },
    RED {
        @Override
        public String getColorCode() {
            return "#FF0000";
        }
    };

    // Abstract method that each enum constant must implement
    public abstract String getColorCode();

    // Default method for common behavior
    public String getDefaultColor() {
        return this.name();
    }
}
