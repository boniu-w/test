package wg.application.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import wg.application.entity.UserOperationLog;
import wg.application.entity.example.UserOperationLogExample;

public interface UserOperationLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_operation_log
     *
     * @mbg.generated
     */
    long countByExample(UserOperationLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_operation_log
     *
     * @mbg.generated
     */
    int deleteByExample(UserOperationLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_operation_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_operation_log
     *
     * @mbg.generated
     */
    int insert(UserOperationLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_operation_log
     *
     * @mbg.generated
     */
    int insertSelective(UserOperationLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_operation_log
     *
     * @mbg.generated
     */
    List<UserOperationLog> selectByExample(UserOperationLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_operation_log
     *
     * @mbg.generated
     */
    UserOperationLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_operation_log
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") UserOperationLog record, @Param("example") UserOperationLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_operation_log
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") UserOperationLog record, @Param("example") UserOperationLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_operation_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserOperationLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_operation_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserOperationLog record);
}