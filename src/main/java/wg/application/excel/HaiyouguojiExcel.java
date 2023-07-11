package wg.application.excel;

import lombok.Data;
import wg.application.excel.annotation.Excel;

import java.io.Serializable;

/************************************************************************
 * author: wg
 * description: Haiyouguoji 
 * createTime: 16:30 2023/5/10
 * updateTime: 16:30 2023/5/10
 ************************************************************************/
@Data
public class HaiyouguojiExcel implements Serializable {
    
    @Excel(name = "所属页面")
    private String pageName;
    
    @Excel(name = "字段中文")
    private String field;
    
    @Excel(name = "字段英文")
    private String fieldEn;
    
    @Excel(name = "数据类型")
    private String fieldType;
    
    @Excel(name = "长度")
    private String length;
    
    @Excel(name = "数据表")
    private String dataTable;
    
    @Excel(name = "位数")
    private String bitLength;
    
    @Excel(name = "单位")
    private String unit;
    
    @Excel(name = "关系")
    private String relationShip;
    
    @Excel(name = "来源数据")
    private String source;
    //
    // private String dataIndex;
}
