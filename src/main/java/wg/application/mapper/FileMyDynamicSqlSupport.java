// package wg.application.mapper;
//
// import jakarta.annotation.Generated;
// import java.sql.JDBCType;
// import java.time.LocalDateTime;
// import org.mybatis.dynamic.sql.AliasableSqlTable;
// import org.mybatis.dynamic.sql.SqlColumn;
//
// public final class FileMyDynamicSqlSupport {
//     @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: file_my")
//     public static final FileMy fileMy = new FileMy();
//
//     /**
//      * Database Column Remarks:
//      *   id
//      */
//     @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: file_my.id")
//     public static final SqlColumn<Long> id = fileMy.id;
//
//     /**
//      * Database Column Remarks:
//      *   文件名
//      */
//     @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: file_my.file_name")
//     public static final SqlColumn<String> fileName = fileMy.fileName;
//
//     /**
//      * Database Column Remarks:
//      *   文件后缀
//      */
//     @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: file_my.suffix")
//     public static final SqlColumn<String> suffix = fileMy.suffix;
//
//     /**
//      * Database Column Remarks:
//      *   文件绝对路径
//      */
//     @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: file_my.absolute_path")
//     public static final SqlColumn<String> absolutePath = fileMy.absolutePath;
//
//     /**
//      * Database Column Remarks:
//      *   文件大小(k)
//      */
//     @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: file_my.length")
//     public static final SqlColumn<Long> length = fileMy.length;
//
//     /**
//      * Database Column Remarks:
//      *   sha256值
//      */
//     @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: file_my.sha256")
//     public static final SqlColumn<String> sha256 = fileMy.sha256;
//
//     /**
//      * Database Column Remarks:
//      *   创建时间
//      */
//     @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: file_my.create_time")
//     public static final SqlColumn<LocalDateTime> createTime = fileMy.createTime;
//
//     /**
//      * Database Column Remarks:
//      *   更新时间
//      */
//     @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: file_my.update_time")
//     public static final SqlColumn<LocalDateTime> updateTime = fileMy.updateTime;
//
//     /**
//      * Database Column Remarks:
//      *   删除标志位
//      */
//     @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: file_my.del_flag")
//     public static final SqlColumn<Boolean> delFlag = fileMy.delFlag;
//
//     @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: file_my")
//     public static final class FileMy extends AliasableSqlTable<FileMy> {
//         public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);
//
//         public final SqlColumn<String> fileName = column("file_name", JDBCType.VARCHAR);
//
//         public final SqlColumn<String> suffix = column("suffix", JDBCType.VARCHAR);
//
//         public final SqlColumn<String> absolutePath = column("absolute_path", JDBCType.VARCHAR);
//
//         public final SqlColumn<Long> length = column("length", JDBCType.BIGINT);
//
//         public final SqlColumn<String> sha256 = column("sha256", JDBCType.VARCHAR);
//
//         public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);
//
//         public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);
//
//         public final SqlColumn<Boolean> delFlag = column("del_flag", JDBCType.BIT);
//
//         public FileMy() {
//             super("file_my", FileMy::new);
//         }
//     }
// }