package wg.application.entity;

import lombok.Data;

import java.util.List;

/*************************************************************
 * @Package wg.application.token.entity
 * @author wg
 * @date 2020/8/21 15:04
 * @version
 * @Copyright
 *************************************************************/
@Data
public class AuthorizerDetail {

    public List<String> authorityCodeList;
    public String userName;
    public String userId;
    public String deptId;
    public String depeName;
    public String operatorId;

}
