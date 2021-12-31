package wg.application.entity;

import lombok.Data;

/*************************************************************
 * @Package wg.application.util
 * @author wg
 * @date 2020/10/27 16:26
 * @version
 * @Copyright
 *************************************************************/
@Data
public class ExcelParams {

    private int sheetIndex;
    private int titleIndex;
    private int contentIndex;

    private String exportFileName;
    private String exportSheetName;


}
