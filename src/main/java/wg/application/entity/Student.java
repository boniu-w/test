package wg.application.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

/*************************************************************
 * @Package wg.application.entity
 * @author wg
 * @date 2020/9/3 14:15
 * @version
 * @Copyright
 * @description: 不序列化, 无法使用 redis 存储
 *************************************************************/
public class Student  implements Serializable {

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]{1,2}$", message = "年龄是1-2位整数")
    private Integer age;

    private Integer id;
    private Integer sex;

    private String birthday;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Student(String name, Integer age, Integer sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public Student(String name, Integer age, Integer id, Integer sex, String birthday) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.sex = sex;
        this.birthday = birthday;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                '}';
    }

    /************************************************************************
     * @author: wg
     * @description: lang3
     * @params:
     * @return:
     * @createTime: 16:11  2022/5/10
     * @updateTime: 16:11  2022/5/10
     ************************************************************************/
    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //
    //     if (o == null || getClass() != o.getClass()) return false;
    //
    //     Student student = (Student) o;
    //
    //     return new EqualsBuilder().append(name, student.name).append(age, student.age).append(id, student.id).append(sex, student.sex).append(birthday, student.birthday).isEquals();
    // }
    //
    // @Override
    // public int hashCode() {
    //     return new HashCodeBuilder(17, 37).append(name).append(age).append(id).append(sex).append(birthday).toHashCode();
    // }


    /************************************************************************
     * @author: wg
     * @description: guava
     * @params:
     * @return:
     * @createTime: 16:11  2022/5/10
     * @updateTime: 16:11  2022/5/10
     ************************************************************************/
    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (o == null || getClass() != o.getClass()) return false;
    //     Student student = (Student) o;
    //     return Objects.equal(name, student.name) && Objects.equal(age, student.age) && Objects.equal(id, student.id) && Objects.equal(sex, student.sex) && Objects.equal(birthday, student.birthday);
    // }
    //
    // @Override
    // public int hashCode() {
    //     return Objects.hashCode(name, age, id, sex, birthday);
    // }


    /************************************************************************
     * @author: wg
     * @description: java7+
     * @params:
     * @return:
     * @createTime: 16:12  2022/5/10
     * @updateTime: 16:12  2022/5/10
     ************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return name.equals(student.name) && age.equals(student.age) && id.equals(student.id) && sex.equals(student.sex) && birthday.equals(student.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, id, sex, birthday);
    }
}
