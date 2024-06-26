package wg.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import wg.application.config.DecimalSerializer;
import wg.application.design.observer.ObserverMsg;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table user
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class User implements Serializable, ObserverMsg {

    // 不管用
    // private Object writeReplace() throws ObjectStreamException {
    //     if (wealth.compareTo(new BigDecimal("1")) == 0) {
    //         return "/";
    //     } else {
    //         return wealth;
    //     }
    // }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated
     */
    @NotNull
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.age
     *
     * @mbg.generated
     */
    private Integer age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.birthday
     *
     * @mbg.generated
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.gender
     *
     * @mbg.generated
     */
    // @JsonSerialize(using = CustomerDecimalSerializer.class) // 500
    private String gender;

    @JsonSerialize(using = DecimalSerializer.class)
    // @JsonDeserialize(using = CustomerDecimalDeSerializer.class)
    private BigDecimal wealth;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.create_time
     *
     * @mbg.generated
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.update_time
     *
     * @mbg.generated
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.name
     *
     * @return the value of user.name
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.name
     *
     * @param name the value for user.name
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.age
     *
     * @return the value of user.age
     * @mbg.generated
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.age
     *
     * @param age the value for user.age
     * @mbg.generated
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.birthday
     *
     * @return the value of user.birthday
     * @mbg.generated
     */
    public LocalDateTime getBirthday() {
        return birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.birthday
     *
     * @param birthday the value for user.birthday
     * @mbg.generated
     */
    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.gender
     *
     * @return the value of user.gender
     * @mbg.generated
     */
    public String getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.gender
     *
     * @param gender the value for user.gender
     * @mbg.generated
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.create_time
     *
     * @return the value of user.create_time
     * @mbg.generated
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.create_time
     *
     * @param createTime the value for user.create_time
     * @mbg.generated
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.update_time
     *
     * @return the value of user.update_time
     * @mbg.generated
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.update_time
     *
     * @param updateTime the value for user.update_time
     * @mbg.generated
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getWealth() {
        return wealth;
    }

    public void setWealth(BigDecimal wealth) {
        this.wealth = wealth;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", wealth=" + wealth +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(age, user.age) && Objects.equals(birthday, user.birthday) && Objects.equals(gender, user.gender) && Objects.equals(wealth, user.wealth) && Objects.equals(createTime, user.createTime) && Objects.equals(updateTime, user.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, birthday, gender, wealth, createTime, updateTime);
    }

    /************************************************************************
     * @author: wg
     * @description:
     *     这个方法是在对象被垃圾回收器回收之前被调用。具体的时间点是不确定的，取决于垃圾回收器的运行状态和策略。一般来说，当一个
     * 对象变得不可达（即没有任何引用指向它）时，垃圾回收器就会将其标记为垃圾对象并在适当的时机回收它，回收之前会调用该对象的finalize()方法，
     * 在该方法中可以进行一些清理工作，类似于C++中的析构函数。但是，由于垃圾回收的时机是不确定的，因此finalize()方法并不能保证一定会被执行，
     * 也不能保证被执行的顺序。因此，不建议在该方法中编写任何关键性代码。在Java 9中，finalize()方法已经被标记为deprecated，建议使用其他方
     * 式进行清理工作，如使用try-with-resources结构、PhantomReference等
     *     一般情况下，即使你关闭了程序，finalize()方法也不会立即被调用。这是因为Java的垃圾回收机制并不是实时的，而是基于一些算法和策略来判断对
     * 象是否需要被回收。垃圾回收时机是由虚拟机根据当前的内存使用情况和垃圾回收算法等因素来决定的，因此finalize()方法的调用并不是实时的，也不
     * 能保证一定会被调用。另外，因为finalize()方法的调用是由垃圾回收器负责的，而Java虚拟机中有多种垃圾回收器，它们也可能存在一些差异，进一
     * 步影响finalize()方法的调用。
     *     因此，为了确保程序的正确性和可靠性，不要在finalize()方法中编写过于关键的代码，并且不要过度依赖finalize()方法的调用。
     * 可以采用try-with-resources结构、手动释放资源等方式，在不需要对象时主动关闭相应的资源。
     * @params:
     * @return:
     * @createTime: 14:33  2023/4/21
     * @updateTime: 14:33  2023/4/21
     ************************************************************************/
    @Override
    protected void finalize() throws Throwable {
        System.out.println("user 垃圾回收");
        super.finalize();
    }

    @Override
    public void update(String msg) {
        System.out.println("my friend update the Moments: " + msg);
    }
}