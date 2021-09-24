package wg.application.config;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import wg.application.dao.UserMapper;
import wg.application.entity.User;
import wg.application.entity.UserExample;

import java.util.List;

// @Configuration
// @ComponentScan(value = "wg.application", includeFilters = {
//         @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Mapper.class})})
public class DaoConfiguration {

    @Bean
    public UserMapper invokeImplController() {

        return new UserMapper(){
            @Override
            public long countByExample(UserExample example) {
                return 0;
            }

            @Override
            public int deleteByExample(UserExample example) {
                return 0;
            }

            @Override
            public int deleteByPrimaryKey(Integer id) {
                return 0;
            }

            @Override
            public int insert(User record) {
                return 0;
            }

            @Override
            public int insertSelective(User record) {
                return 0;
            }

            @Override
            public List<User> selectByExample(UserExample example) {

                return null;
            }

            @Override
            public User selectByPrimaryKey(Integer id) {
                return null;
            }

            @Override
            public int updateByExampleSelective(User record, UserExample example) {
                return 0;
            }

            @Override
            public int updateByExample(User record, UserExample example) {
                return 0;
            }

            @Override
            public int updateByPrimaryKeySelective(User record) {
                return 0;
            }

            @Override
            public int updateByPrimaryKey(User record) {
                return 0;
            }
        };
    }
}