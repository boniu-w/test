package wg.application.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import wg.application.entity.Picture;
import wg.application.entity.PictureExample;

public interface PictureMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated
     */
    long countByExample(PictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated
     */
    int deleteByExample(PictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated
     */
    int insert(Picture record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated
     */
    int insertSelective(Picture record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated
     */
    List<Picture> selectByExample(PictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated
     */
    Picture selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Picture record, @Param("example") PictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Picture record, @Param("example") PictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Picture record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table picture
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Picture record);
}