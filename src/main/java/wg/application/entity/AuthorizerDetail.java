package wg.application.entity;


import java.util.List;

/*************************************************************
 * @Package wg.application.token.entity
 * @author wg
 * @date 2020/8/21 15:04
 * @version
 * @Copyright
 *************************************************************/
public class AuthorizerDetail {

    public List<String> authorityCodeList;
    public String userName;
    public String userId;
    public String deptId;
    public String depeName;
    public String operatorId;

    public List<String> getAuthorityCodeList() {
        return authorityCodeList;
    }

    public void setAuthorityCodeList(List<String> authorityCodeList) {
        this.authorityCodeList = authorityCodeList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDepeName() {
        return depeName;
    }

    public void setDepeName(String depeName) {
        this.depeName = depeName;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
