package wg.application.entity;


/*************************************************************
 * @Package wg.application.util
 * @author wg
 * @date 2020/10/27 16:26
 * @version
 * @Copyright
 *************************************************************/
public class ExcelParams {

    private int sheetIndex;
    private int titleIndex;
    private int contentIndex;

    private String exportFileName;
    private String exportSheetName;

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public int getTitleIndex() {
        return titleIndex;
    }

    public void setTitleIndex(int titleIndex) {
        this.titleIndex = titleIndex;
    }

    public int getContentIndex() {
        return contentIndex;
    }

    public void setContentIndex(int contentIndex) {
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
