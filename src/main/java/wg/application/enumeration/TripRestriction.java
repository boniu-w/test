package wg.application.enumeration;

/*************************************************************
 * @Package net.mingsoft.cms.constant
 * @author wg
 * @date 2020/9/11 16:34
 * @version
 * @Copyright
 *************************************************************/
public enum TripRestriction {

    MONDAY(1, new String[]{"0", "5"}),
    TUESDAY(2, new String[]{"1", "6"}),
    WEDNESDAY(3, new String[]{"2", "7"}),
    THURSDAY(4, new String[]{"3", "8"}),
    FRIDAY(5, new String[]{"4", "9"}),
    SATURDAY(6, new String[]{}),
    SUNDAY(7, new String[]{});


    private int code;
    private String[] tripRestriction;

    TripRestriction(int code, String[] tripRestriction) {
        this.code = code;
        this.tripRestriction = tripRestriction;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String[] getTripRestriction() {
        return tripRestriction;
    }

    public void setTripRestriction(String[] tripRestriction) {
        this.tripRestriction = tripRestriction;
    }
}
