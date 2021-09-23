package wg.application.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wg
 * @Package wg.application.entity
 * @date 2020/4/15 14:18
 * @Copyright
 */
@Data
public class User {

    private Integer id;

    private String name;

    private Integer age;

    private Date birthday;

    private String key;

    private String value;

    private List<User> users;

    private Character gender;

    private Date createTime;

    private Date updateTime;

    //@Override
    //public boolean equals(Object o) {
    //    if (this == o) return true;
    //    if (o == null || getClass() != o.getClass()) return false;
    //    User user = (User) o;
    //    return Objects.equals(name, user.name) &&
    //      Objects.equals(age, user.age) &&
    //      Objects.equals(birthday, user.birthday);
    //}
    //
    //@Override
    //public int hashCode() {
    //    return Objects.hash(name, age, birthday);
    //}
}
