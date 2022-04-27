package wg.application.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/*************************************************************
 * @Package wg.application.entity
 * @author wg
 * @date 2020/9/3 14:15
 * @version
 * @Copyright
 *************************************************************/
public class Student {

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

    // @Override
    // public String toString() {
    //     return "Student{" +
    //             "name='" + name + '\'' +
    //             ", age=" + age +
    //             ", id=" + id +
    //             ", sex=" + sex +
    //             ", birthday='" + birthday + '\'' +
    //             '}';
    // }
}
