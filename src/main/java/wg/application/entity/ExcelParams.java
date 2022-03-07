package wg.application.entity;


/*************************************************************
 * @Package wg.application.util
 * @author wg
 * @date 2020/10/27 16:26
 * @version
 * @Copyright
 *************************************************************/
public class ExcelParams {

    private Integer sheetIndex;
    private Integer titleIndex;
    private Integer contentIndex;

    private String exportFileName;
    private String exportSheetName;

    public Integer getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public Integer getTitleIndex() {
        return titleIndex;
    }

    public void setTitleIndex(Integer titleIndex) {
        this.titleIndex = titleIndex;
    }

    public Integer getContentIndex() {
        return contentIndex;
    }

    public void setContentIndex(Integer contentIndex) {
        this.contentIndex = contentIndex;
    }

    public String getExportFileName() {
        return exportFileName;
    }

    public void setExportFileName(String exportFileName) {
        this.exportFileName = exportFileName;
    }

    public String getExportSheetName() {
        return exportSheetName;
    }

    public void setExportSheetName(String exportSheetName) {
        this.exportSheetName = exportSheetName;
    }
}
