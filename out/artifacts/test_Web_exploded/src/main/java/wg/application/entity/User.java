package wg.application.entity;

import lombok.Data;

import javax.persistence.Transient;

/**
 * @author wg
 * @Package wg.application.entity
 * @date 2020/4/15 14:18
 * @Copyright
 */
@Data
public class User {

    private String name;
    private String age;

    @Transient
    private String birthday;
}
