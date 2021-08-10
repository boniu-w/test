package wg.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/*****************************************
 * description: 测试 @Resource
 * date: 17:35 2021/8/5
 * auth: wg
 *****************************************/
// @Configuration
public class Animal {

    private String genus;

    @Bean(name = "animal")
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
