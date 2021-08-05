package wg.application.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;

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

    private String key;
    private String value;
    private List<User> users;

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
