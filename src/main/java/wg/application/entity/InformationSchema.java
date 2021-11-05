package wg.application.entity;


public class InformationSchema {

    private String tableName;
    private String columnName;
    private String referencedTableName;
    private String referencedColumnName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getReferencedTableName() {
        return referencedTableName;
    }

    public void setReferencedTableName(String referencedTableName) {
        this.referencedTableName = referencedTableName;
    }

    public String getReferencedColumnName() {
        return referencedColumnName;
    }

    public void setReferencedColumnName(String referencedColumnName) {
        this.referencedColumnName = referencedColumnName;
    }

    @Override
    public String toString() {
        return "InformationSchema{" +
                "tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", referencedTableName='" + referencedTableName + '\'' +
                ", referencedColumnName='" + referencedColumnName + '\'' +
                '}';
    }
}
