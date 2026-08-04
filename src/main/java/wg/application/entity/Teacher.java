package wg.application.entity;

import lombok.Data;

import java.util.Objects;

/*************************************************************
 * @Package wg.application.entity
 * @author wg
 * @date 2020/9/3 14:16
 * @version
 * @Copyright
 *************************************************************/
// @Data
public class Teacher {
    private String name;
    private int age;
    private int id;

    public Teacher(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public Teacher() {
    }

    // @Override
    // public boolean equals(Object o) {
    //     if (o == null || getClass() != o.getClass()) return false;
    //     Teacher teacher = (Teacher) o;
    //     return age == teacher.age && id == teacher.id && Objects.equals(name, teacher.name);
    // }
    //
    // @Override
    // public int hashCode() {
    //     return Objects.hash(name, age, id);
    // }
}
