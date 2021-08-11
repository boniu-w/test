package wg.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/*************************************************************
 * @Package wg.application.entity
 * @author wg
 * @date 2020/9/3 14:15
 * @version
 * @Copyright
 *************************************************************/
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Student {
    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]{1,2}$",message = "年龄是1-2位整数")
    private int age;

    private int id;
    private int sex;

    private String birthday;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student(String name, int age, int sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
