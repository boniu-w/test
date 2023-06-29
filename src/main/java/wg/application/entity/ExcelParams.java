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
    private Integer contentStartIndex;
    private Integer contentEndIndex;
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
    
    public Integer getContentStartIndex() {
        return contentStartIndex;
    }
    
    public void setContentStartIndex(Integer contentIndex) {
        this.contentStartIndex = contentIndex;
    }
    
    public Integer getContentEndIndex() {
        return contentEndIndex;
    }
    
    public void setContentEndIndex(Integer contentEndIndex) {
        this.contentEndIndex = contentEndIndex;
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
