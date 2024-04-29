package wg.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/*****************************************
 * description: 测试 @Resource
 * date: 17:35 2021/8/5
 * auth: wg
 *****************************************/
@Configuration
public class Animal {

    private String genus;

    /**
     * @author: wg
     * @description: 使用set 方法 创建一个bean 测试方法: wg/application/controller/Test.java:1593
     * @params:
     * @return:
     * @createTime: 15:25  2024/4/29
     * @updateTime: 15:25  2024/4/29
     */
    @Bean(name = "animal1")
    public Animal setAnimal() {
        Animal animal = new Animal();
        animal.setGenus("人");
        return animal;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getGenus() {
        return genus;
    }
}
