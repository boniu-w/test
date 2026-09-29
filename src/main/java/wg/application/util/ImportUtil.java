package wg.application.util;

import cn.hutool.core.util.ArrayUtil;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import wg.application.entity.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ImportUtil {

    /**
     * @author wg
     * @description 检查一致性时用
     * @date 2026/9/10 18:01
     */
    public enum CongruenceEnum {
        MONTH("month", "month", "月份", "month"),
        ASSOCIATED_MONTH("associatedMonth", "associated_month", "所属月份", "associated month");

        private final String fieldNameVO;
        private final String fieldNameDatasource;
        private final String descriptionCN;
        private final String descriptionEN;

        CongruenceEnum(String fieldNameVO, String fieldNameDatasource, String descriptionCN, String descriptionEN) {
            this.fieldNameVO = fieldNameVO;
            this.fieldNameDatasource = fieldNameDatasource;
            this.descriptionCN = descriptionCN;
            this.descriptionEN = descriptionEN;
        }

        public String getFieldNameVO() {
            return fieldNameVO;
        }

        public String getFieldNameDatasource() {
            return fieldNameDatasource;
        }

        public String getDescriptionCN() {
            return descriptionCN;
        }

        public String getDescriptionEN() {
            return descriptionEN;
        }
    }

    /**
     * 读取上传的 Excel 文件并解析为指定类型的 Excel 列表。
     * <p>
     * 读取表头作为字段映射，解析正文内容到 Excel，并执行必填字段校验；
     * 若存在校验错误，将错误信息写入 Excel 后随结果返回。
     *
     * @param multipartFile 上传的 Excel 文件
     * @param tClass        目标 Excel 类型
     * @param <T>           Excel 泛型类型
     * @return 解析结果上下文（包含 Excel 列表、错误信息及工作簿）
     * @throws Exception 文件读取或解析失败时抛出
     */
    public static <T> ExcelContext<T> readExcel(MultipartFile multipartFile, Class<T> tClass, ValidateConfig<?> validateConfig) throws Exception {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return new ExcelContext<>();
        }
        Workbook workbook = ExcelUtil.initWorkbook(multipartFile);
        ExcelParams params = new ExcelParams();
        params.setSheetIndex(0);
        params.setContentStartIndex(1);
        // 读取第1行表头
        params.setTitleIndex(0);
        String[] row1Headers = ExcelUtil.readExcelTitle(workbook, params, tClass);
        Map<Integer, Map<String, Object>> contentMap = ExcelUtil.readExcelContent(workbook, row1Headers, params);
        Map<String, Map<String, String>> importReplaceMap = ExcelUtil.getImportReplaceMap(tClass);
        ExcelContext<T> excelContext = validateData(contentMap, validateConfig);
        List<T> dtos = ExcelUtil.toObject(tClass, contentMap, importReplaceMap);
        excelContext.setList(dtos);
        excelContext.setWorkbook(workbook);
        // 如果有校验错误，将错误信息写入 Excel 最后一列之后，并标红出错单元格
        LinkedHashMap<Integer, String> errorMap = excelContext.getErrorMap();
        if (errorMap != null && !errorMap.isEmpty()) {
            writeErrorToWorkbook(workbook, errorMap, row1Headers, excelContext.getErrorFieldMap());
        }

        return excelContext;
    }

    public static <T> ExcelContext<T> readExcel(MultipartFile multipartFile,
                                                Class<T> tClass,
                                                ValidateConfig<?> validateConfig,
                                                ExcelParams params) throws Exception {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return new ExcelContext<>();
        }
        Workbook workbook = ExcelUtil.initWorkbook(multipartFile);
        String[] row1Headers = ExcelUtil.readExcelTitle(workbook, params, tClass);
        Map<Integer, Map<String, Object>> contentMap = ExcelUtil.readExcelContent(workbook, row1Headers, params);
        Map<String, Map<String, String>> importReplaceMap = ExcelUtil.getImportReplaceMap(tClass);
        ExcelContext<T> excelContext = validateData(contentMap, validateConfig);
        validateReplaceFields(contentMap, excelContext.errorMap, excelContext.errorFieldMap, importReplaceMap, validateConfig.replaceFields);
        List<T> dtos = ExcelUtil.toObject(tClass, contentMap, importReplaceMap);
        excelContext.setList(dtos);
        excelContext.setWorkbook(workbook);
        // 如果有校验错误，将错误信息写入 Excel 最后一列之后，并标红出错单元格
        LinkedHashMap<Integer, String> errorMap = excelContext.getErrorMap();
        if (errorMap != null && !errorMap.isEmpty()) {
            int sheetIndex = params.getSheetIndex() == null ? 0 : params.getSheetIndex();
            int titleRowIndex = params.getTitleIndex() == null ? 0 : params.getTitleIndex();
            writeErrorToWorkbook(workbook, sheetIndex, titleRowIndex, errorMap, row1Headers, excelContext.getErrorFieldMap());
        }

        return excelContext;
    }

    /**
     * 校验 Excel 正文数据，并将校验错误信息封装到上下文。
     *
     * @param contentMap 以行号为键、字段值 Map 为值的正文数据
     * @param <T>        DTO 泛型类型
     * @return 包含错误信息的 ExcelContext
     */
    private static <T> ExcelContext<T> validateData(Map<Integer, Map<String, Object>> contentMap, ValidateConfig<?> config) {
        ExcelContext<T> context = getContext();
        LinkedHashMap<Integer, String> errorMap = new LinkedHashMap<>();
        Map<Integer, Set<String>> errorFieldMap = new LinkedHashMap<>();

        if (config.getRequiredFields() != null) {
            validateRequiredField(contentMap, errorMap, errorFieldMap, config.getRequiredFields());
        }
        if (config.getFormatMap() != null && !config.getFormatMap().isEmpty()) {
            validatePattern(contentMap, errorMap, errorFieldMap, config.getFormatMap());
        }
        if (config.getRepeatFields() != null) {
            validateRepeatField(contentMap, errorMap, errorFieldMap, config.getRepeatFields());
        }
        if (cn.hutool.core.map.MapUtil.isNotEmpty(config.getDatasourceMap())) {
            validateDatasource(contentMap, errorMap, errorFieldMap, config.getDatasourceMap());
        }
        // 验证 数据库存在性
        if (!ObjectUtils.isEmpty(config.getExistenceClass())) {
            validateExistence2(contentMap, errorMap, errorFieldMap, config.getExistenceClass());
        }
        // 验证 日期字段
        if (ArrayUtil.isNotEmpty(config.getDateFieldsMap())) {
            validateDateFields(contentMap, errorMap, errorFieldMap, config.getDateFieldsMap());
        }
        if (ArrayUtil.isNotEmpty(config.getDateFields())) {
            validateDateFields(contentMap, errorMap, errorFieldMap, config.getDateFields());
        }
        // 验证 字典项
        if (config.getMappings() != null && !config.getMappings().isEmpty()) {
            validateDictFields2(contentMap, errorMap, errorFieldMap, config);
        } else if (ArrayUtil.isNotEmpty(config.getDictFields())) {
            validateDictFields(contentMap, errorMap, errorFieldMap, config);
        }
        // 验证 整数项
        if (ArrayUtil.isNotEmpty(config.getIntegerFields())) {
            validateIntegerFields(contentMap, errorMap, errorFieldMap, config);
        }
        // 数字项
        if (ArrayUtil.isNotEmpty(config.getNumFields())) {
            validateNumFields(contentMap, errorMap, errorFieldMap, config);
        }

        // 枚举字段-相当于字典, 只不过不在字典表里
        if (cn.hutool.core.map.MapUtil.isNotEmpty(config.getEnumFields())) {
            validateEnumFields(contentMap, errorMap, errorFieldMap, config.getEnumFields());
        }
        // 一致性 验证
        if (!CollectionUtils.isEmpty(config.getCongruenceEnumList())) {
            validateCongruence(contentMap, errorMap, errorFieldMap, config.getCongruenceEnumList());
        }

        context.setErrorMap(errorMap);
        context.setErrorFieldMap(errorFieldMap);
        return context;
    }

    private static void validateDictFields(Map<Integer, Map<String, Object>> contentMap,
                                           LinkedHashMap<Integer, String> errorMap,
                                           Map<Integer, Set<String>> errorFieldMap,
                                           ValidateConfig<?> config) {
        if (contentMap == null || contentMap.isEmpty() || config == null) {
            return;
        }

        Map<String, FieldDictConfig> fieldConfigMap = buildFieldConfigMap(config);
        if (fieldConfigMap.isEmpty()) {
            return;
        }

        Map<Integer, Map<String, String>> rowFieldErrors = contentMap.entrySet().stream()
                .filter(entry -> !MapUtil.isAllEmptyValue(entry.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> validateRow(entry.getValue(), fieldConfigMap)
                ))
                .entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // 合并错误信息，并记录错误字段
        rowFieldErrors.forEach((rowNum, fieldErrors) -> {
            String errorMsg = String.join("; ", fieldErrors.values()) + ";";
            errorMap.merge(rowNum, errorMsg, (old, newVal) -> old + newVal);
            errorFieldMap.computeIfAbsent(rowNum, k -> new LinkedHashSet<>()).addAll(fieldErrors.keySet());
        });
    }

    /**
     * 验证单行数据，返回 字段 -> 错误信息 的映射。
     */
    private static Map<String, String> validateRow(Map<String, Object> rowData,
                                                   Map<String, FieldDictConfig> fieldConfigMap) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();

        for (Map.Entry<String, FieldDictConfig> fieldEntry : fieldConfigMap.entrySet()) {
            String dictField = fieldEntry.getKey();
            FieldDictConfig fieldConfig = fieldEntry.getValue();

            Object excelFieldObj = rowData.get(dictField);
            if (ObjectUtils.isEmpty(excelFieldObj)) {
                continue;
            }

            String valueStr = excelFieldObj.toString().trim();
            if (valueStr.isEmpty()) {
                continue;
            }

            DictItem matched = matchDictItem(valueStr, fieldConfig);
            if (matched == null) {
                // fieldErrors.put(dictField, String.format("字段[%s]的值[%s]字典项与数据库不符", dictField, valueStr));
                fieldErrors.put(dictField, String.format("[%s]与不存在于数据库中", valueStr));
            } else {
                rowData.put(dictField + "Id", matched.getId());
            }
        }

        return fieldErrors;
    }

    /**
     * 匹配字典项
     */
    private static DictItem matchDictItem(String valueStr, FieldDictConfig config) {
        if (valueStr == null || valueStr.isEmpty()) {
            return null;
        }

        // 1. 精确匹配（优先）
        DictItem matched = tryExactMatch(valueStr, config);
        if (matched != null) {
            return matched;
        }

        // 2. 忽略大小写匹配
        matched = tryCaseInsensitiveMatch(valueStr, config);
        if (matched != null) {
            return matched;
        }

        // 3. 可以继续添加其他匹配策略（如去除空格、包含匹配等）
        return null;
    }


    private static DictItem tryExactMatch(String valueStr, FieldDictConfig config) {
        if (config.enMap != null) {
            DictItem matched = config.enMap.get(valueStr);
            if (matched != null) {
                return matched;
            }
        }
        if (config.cnMap != null) {
            return config.cnMap.get(valueStr);
        }
        return null;
    }

    private static DictItem tryCaseInsensitiveMatch(String valueStr, FieldDictConfig config) {
        // 英文字典忽略大小写
        if (config.enMap != null) {
            for (Map.Entry<String, DictItem> entry : config.enMap.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(valueStr)) {
                    return entry.getValue();
                }
            }
        }
        // 中文字典忽略大小写（如果中文也需要）
        if (config.cnMap != null) {
            for (Map.Entry<String, DictItem> entry : config.cnMap.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(valueStr)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 构建字段配置
     */
    private static Map<String, FieldDictConfig> buildFieldConfigMap(ValidateConfig<?> config) {
        Map<String, Map<String, DictItem>> dictMapEN = config.getDictMapEN();
        Map<String, Map<String, DictItem>> dictMapCN = config.getDictMapCN();
        String[] dictFields = config.getDictFields();

        if (dictFields == null) {
            return Collections.emptyMap();
        }

        Map<String, FieldDictConfig> result = new HashMap<>();
        for (String dictField : dictFields) {
            Map<String, DictItem> enMap = getMapSafely(dictMapEN, dictField);
            Map<String, DictItem> cnMap = getMapSafely(dictMapCN, dictField);
            if (enMap != null || cnMap != null) {
                result.put(dictField, new FieldDictConfig(enMap, cnMap));
            }
        }
        return result;
    }

    /**
     * 安全获取 Map 中的字典表, 避免外层 Map 为 null 时抛 NPE。
     * <p>
     * 全局字典表结构为: 字典类型码(dictType) -> 标签(中文/英文) -> DictItem。
     *
     * @param dictMap 全局字典表(可能为 null)
     * @param key     字典类型码(dictType)
     * @return 该字典类型码对应的 标签 -> DictItem 映射; dictMap 为 null 或 key 不存在时返回 null
     */
    private static Map<String, DictItem> getMapSafely(Map<String, Map<String, DictItem>> dictMap, String key) {
        return dictMap != null ? dictMap.get(key) : null;
    }

    /**
     * 校验字典字段(基于 FieldDictMapping 配置, 与 DictItemValidator 对齐)。
     * <p>
     * 根据 config.getMappings() 里声明的映射关系, 将 Excel 内容字段(fieldName)的字典值
     * 与字典表(CN/EN, 按 dictType 取值)进行匹配:
     * <ul>
     *   <li>匹配成功: 将字典项 id 写回 rowData(idFieldName; 多值场景额外写回 idFieldName+Value 数组)</li>
     *   <li>匹配失败: 记录中英双语错误信息到 errorMap(按行号), 并标记出错字段到 errorFieldMap(用于标红单元格)</li>
     * </ul>
     * 支持单值、多值(分号分隔)、模糊匹配(子串包含)三种模式, 由 FieldDictMapping 控制。
     * <p>
     * 注意: 本方法直接修改入参 contentMap(回写字典 id) 与 errorMap/errorFieldMap(写入错误),
     * 校验结果供后续 ExcelUtil.toObject 转换 DTO 及 writeErrorToWorkbook 标红使用。
     *
     * @param contentMap    Excel 正文数据, key=行号, value=该行字段值 Map(key=字段名, value=单元格值); 可被修改(回写字典 id)
     * @param errorMap      错误信息集合, key=行号, value=错误信息串; 本方法会把字典校验错误追加进去(不会覆盖已有错误)
     * @param errorFieldMap 出错字段集合, key=行号, value=出错字段名集合; 用于定位并标红 Excel 单元格
     * @param config        校验配置, 需包含 mappings(字段字典映射列表)及 dictMapCN/dictMapEN(全局字典表)
     */
    private static void validateDictFields2(Map<Integer, Map<String, Object>> contentMap,
                                            LinkedHashMap<Integer, String> errorMap,
                                            Map<Integer, Set<String>> errorFieldMap,
                                            ValidateConfig<?> config) {
        // 入口防御: contentMap(Excel 正文数据) / config(校验配置) 为空时无数据可校验, 直接返回
        if (contentMap == null || contentMap.isEmpty() || config == null) {
            return;
        }

        // 1. 根据 config.getMappings() 构建 字段名 -> 字段字典配置 的映射
        //    (key=Excel 内容字段名, value=FieldDictConfig, 含 CN/EN 字典表、id 回写字段名、多值/模糊开关)
        Map<String, FieldDictConfig> fieldConfigMap = buildFieldConfigMap2(config);
        // 2. 没有任何有效字典映射时无需校验, 直接返回
        if (fieldConfigMap.isEmpty()) {
            return;
        }

        // 3. 逐行校验 Excel 正文数据
        for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
            Integer rowNum = entry.getKey();       // Excel 行号(用于错误定位)
            Map<String, Object> rowData = entry.getValue(); // 该行数据: key=字段名, value=单元格值
            // 跳过空行/整行为空的行, 不产生任何校验动作
            if (rowData == null || MapUtil.isAllEmptyValue(rowData)) {
                continue;
            }

            // 4. 校验该行所有字典字段, 返回 字段名 -> 错误信息 的映射
            //    注意: validateRow2 内部匹配成功时会把字典 id 直接回写到 rowData, 供后续转换 DTO 使用
            Map<String, String> fieldErrors = validateRow2(rowData, fieldConfigMap);
            // 该行无错误则跳过
            if (fieldErrors.isEmpty()) {
                continue;
            }

            // 5. 合并错误信息: 一行多个字段出错时用 "; " 拼接, 并以 ";" 结尾
            //    merge 保证同一行已被其他校验器(必填/格式等)记录过错误时是追加而不是覆盖
            String errorMsg = String.join("; ", fieldErrors.values()) + ";";
            errorMap.merge(rowNum, errorMsg, (old, n) -> old + n);
            // 6. 记录出错字段名集合, 供 writeErrorToWorkbook 把对应单元格标红
            errorFieldMap.computeIfAbsent(rowNum, k -> new LinkedHashSet<>()).addAll(fieldErrors.keySet());
        }
    }

    /**
     * 根据 FieldDictMapping 配置构建字段字典配置映射。
     * <p>
     * 遍历 config.getMappings(), 对每条映射:
     * <ul>
     *   <li>跳过 fieldName 或 dictType 为空的无效映射</li>
     *   <li>按 dictType 从 config.getDictMapEN()/getDictMapCN() 中取出对应的 标签 -> DictItem 字典表</li>
     *   <li>该 dictType 在 CN/EN 表中均不存在时跳过(无法校验)</li>
     *   <li>有效映射以 Excel 内容字段名(fieldName)为 key 存入结果, 并携带 idFieldName、multiValue、fuzzy 配置</li>
     * </ul>
     * 结果使用 LinkedHashMap, 保证字段校验顺序与配置声明顺序一致。
     *
     * @param config 校验配置, 需包含 mappings 及 dictMapCN/dictMapEN
     * @return key=Excel 内容字段名(fieldName), value=字段字典配置; 无有效映射时返回空 Map
     */
    private static Map<String, FieldDictConfig> buildFieldConfigMap2(ValidateConfig<?> config) {
        // 取出全局字典表: 外层 key=字典类型码(dictType), 内层 key=字典项标签(中文/英文), value=DictItem
        Map<String, Map<String, DictItem>> dictMapEN = config.getDictMapEN();
        Map<String, Map<String, DictItem>> dictMapCN = config.getDictMapCN();
        List<FieldDictMapping> mappings = config.getMappings();
        // 没有声明任何映射关系时直接返回空 Map(调用方会跳过校验)
        if (mappings == null || mappings.isEmpty()) {
            return Collections.emptyMap();
        }

        // 遍历每条映射, 组装成校验所需的字段配置
        // 用 LinkedHashMap 保证字段校验顺序与配置声明顺序一致(错误信息顺序稳定)
        Map<String, FieldDictConfig> result = new LinkedHashMap<>();
        for (FieldDictMapping mapping : mappings) {
            // 跳过空映射或缺少关键字段(fieldName/dictType)的映射
            if (mapping == null || StringUtil.isBlank(mapping.getFieldName()) || StringUtil.isBlank(mapping.getDictType())) {
                continue;
            }
            // 按 dictType 从 CN/EN 全局字典表中取出对应字典
            // 注意: key 是 mapping.getDictType(), 而不是 mapping.getFieldName()
            Map<String, DictItem> enMap = getMapSafely(dictMapEN, mapping.getDictType());
            Map<String, DictItem> cnMap = getMapSafely(dictMapCN, mapping.getDictType());
            // 该字典类型在 CN/EN 表中都不存在, 无法校验, 跳过
            if (enMap == null && cnMap == null) {
                continue;
            }
            // 以 Excel 内容字段名为 key 存入, 并携带 id 回写字段名、多值开关、模糊匹配开关
            result.put(mapping.getFieldName(), new FieldDictConfig(
                    enMap, cnMap, mapping.getIdFieldName(), mapping.isMultiValue(), mapping.isFuzzy()));
        }
        return result;
    }

    /**
     * 校验单行数据中的所有字典字段。
     * <p>
     * 遍历 fieldConfigMap 中的每个字段:
     * <ul>
     *   <li>单元格值为空/纯空格时跳过(必填性由 validateRequiredField 单独负责)</li>
     *   <li>根据 FieldDictConfig.multiValue 分流到单值校验 validateSingleValue2 或多值校验 validateMultiValue2</li>
     * </ul>
     * 匹配成功的字典 id 会被回写到 rowData(idFieldName 对应 key)。
     *
     * @param rowData        行数据(Excel 内容), key=字段名, value=单元格值; 可被修改(回写字典 id)
     * @param fieldConfigMap key=Excel 字段名, value=字段字典配置
     * @return 该行 字段名 -> 错误信息 的映射(LinkedHashMap, 顺序稳定); 无错误时返回空 Map
     */
    private static Map<String, String> validateRow2(Map<String, Object> rowData,
                                                    Map<String, FieldDictConfig> fieldConfigMap) {
        // 收集该行所有字典字段的错误: key=Excel 字段名, value=错误信息(LinkedHashMap 保证顺序)
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (Map.Entry<String, FieldDictConfig> fieldEntry : fieldConfigMap.entrySet()) {
            String dictField = fieldEntry.getKey();      // Excel 内容字段名
            FieldDictConfig fieldConfig = fieldEntry.getValue(); // 该字段的字典配置

            // 从行数据中取出该字段的单元格值
            Object excelFieldObj = rowData.get(dictField);
            // 空值不校验(是否必填由 validateRequiredField 负责), 跳过
            if (ObjectUtils.isEmpty(excelFieldObj)) {
                continue;
            }
            // 转字符串并去掉首尾空格; 全空(空串/纯空格)同样跳过
            String valueStr = excelFieldObj.toString().trim();
            if (StringUtil.isBlank(valueStr)) {
                continue;
            }

            // 根据配置区分单值/多值校验:
            // - 单值: 整串作为一个字典值匹配
            // - 多值: 按分号拆分后逐项匹配(如 "原因A;原因B")
            if (fieldConfig.multiValue) {
                validateMultiValue2(rowData, dictField, valueStr, fieldConfig, fieldErrors);
            } else {
                validateSingleValue2(rowData, dictField, valueStr, fieldConfig, fieldErrors);
            }
        }
        return fieldErrors;
    }

    /**
     * 校验单值字典字段: 匹配成功回写 id, 失败记录错误。
     * <p>
     * 将 valueStr 作为一个完整的字典值调用 matchDictItem2 匹配:
     * <ul>
     *   <li>匹配成功: 将 DictItem.id 回写到 rowData(fieldConfig.idFieldName 为 key)</li>
     *   <li>匹配失败: 通过 formatDictError2 生成中英双语错误信息, 放入 fieldErrors</li>
     * </ul>
     *
     * @param rowData     行数据, 匹配成功时回写 id, 即 rowData.put(idFieldName, id)
     * @param dictField   Excel 内容字段名(出错时作为 fieldErrors 的 key)
     * @param valueStr    已去首尾空格的单元格值
     * @param fieldConfig 该字段的字典配置(含 CN/EN 字典表、idFieldName、fuzzy 开关)
     * @param fieldErrors 该行错误收集容器, key=字段名, value=错误信息; 有错误时写入
     */
    private static void validateSingleValue2(Map<String, Object> rowData,
                                             String dictField,
                                             String valueStr,
                                             FieldDictConfig fieldConfig,
                                             Map<String, String> fieldErrors) {
        // 1. 用完整的值串去字典表中匹配(CN 精确 -> EN 精确 -> CN+EN 拼接 -> 可选模糊)
        DictItem matched = matchDictItem2(valueStr, fieldConfig);
        if (matched == null) {
            // 2. 匹配失败: 记录错误信息(中英双语, 附带可提供的合法值列表)
            fieldErrors.put(dictField, formatDictError2(Collections.singletonList(valueStr), fieldConfig));
        } else {
            // 3. 匹配成功: 把字典项 id 回写到 rowData, key 为映射配置的 idFieldName
            //    后续 ExcelUtil.toObject 会把该 id 字段映射到 DTO 的数据库字段
            rowData.put(fieldConfig.idFieldName, matched.getId());
        }
    }

    /**
     * 校验多值字典字段(分号分隔): 逐项匹配, 成功回写 id 数组和值数组, 未匹配的记录错误。
     * <p>
     * 处理流程:
     * <ul>
     *   <li>1. 统一半角化(兼容全角分号/全角字符), 按 ";" 拆分为多个字典值, 空白项跳过</li>
     *   <li>2. 逐项调用 matchDictItem2 匹配: 成功收集 id 与原始值, 失败收集到 unmatched</li>
     *   <li>3. 存在未匹配项时, 将所有未匹配值一次性写入 fieldErrors(formatDictError2 格式化)</li>
     *   <li>4. 只要匹配到至少一项, 就回写两个数组到 rowData:
     *       idFieldName -> id 数组, idFieldName+Value -> 与 id 顺序对应的原始值数组</li>
     * </ul>
     *
     * @param rowData     行数据, 匹配成功时回写 id 数组(idFieldName)与值数组(idFieldName+Value)
     * @param dictField   Excel 内容字段名(出错时作为 fieldErrors 的 key)
     * @param valueStr    已去首尾空格的单元格值(多值, 分号分隔)
     * @param fieldConfig 该字段的字典配置(含 CN/EN 字典表、idFieldName、fuzzy 开关)
     * @param fieldErrors 该行错误收集容器, key=字段名, value=错误信息; 有未匹配项时写入
     */
    private static void validateMultiValue2(Map<String, Object> rowData,
                                            String dictField,
                                            String valueStr,
                                            FieldDictConfig fieldConfig,
                                            Map<String, String> fieldErrors) {
        // 三个收集器:
        // ids          - 匹配成功的字典项 id 列表(回写用)
        // matchedValues- 匹配成功的原始值列表(回写用, 与 ids 一一对应)
        // unmatched    - 未匹配上的值列表(用于报错)
        List<String> ids = new ArrayList<>();
        List<String> matchedValues = new ArrayList<>();
        List<String> unmatched = new ArrayList<>();

        // 1. 先统一半角化(兼容全角分号/全角字符), 再按分号拆分为多个字典值
        String[] parts = StringUtil.toHalfWidth(valueStr).split(";");
        // 2. 逐项匹配: 空白项跳过; 匹配成功进 ids/matchedValues, 失败进 unmatched
        for (String part : parts) {
            String p = part.trim();
            if (StringUtil.isBlank(p)) {
                continue;
            }
            DictItem matched = matchDictItem2(p, fieldConfig);
            if (matched == null) {
                unmatched.add(p);
            } else {
                ids.add(matched.getId());
                matchedValues.add(p);
            }
        }

        // 3. 只要有任一项未匹配, 就把所有未匹配值一起报错(避免一次只报一个值)
        if (!unmatched.isEmpty()) {
            fieldErrors.put(dictField, formatDictError2(unmatched, fieldConfig));
        }
        // 4. 只要匹配到至少一项, 就回写:
        //    idFieldName       -> id 数组(供 DTO 多值 id 字段)
        //    idFieldName+Value -> 原始值数组(与 id 数组按顺序对应, 供 DTO 多值 value 字段)
        if (!ids.isEmpty()) {
            rowData.put(fieldConfig.idFieldName, ids.toArray(new String[0]));
            rowData.put(fieldConfig.idFieldName + "Value", matchedValues.toArray(new String[0]));
        }
    }

    /**
     * 匹配字典项, 匹配前统一半角化、去首尾空格。
     * <p>
     * 按以下策略依次匹配, 命中即返回(不再尝试后续策略):
     * <ol>
     *   <li>中文标签精确匹配(lookupNormalized2: 先直接 get, 再逐 key 半角化比较)</li>
     *   <li>英文标签精确匹配(同上)</li>
     *   <li>CN+EN 拼接匹配: 用 "中文标签+英文标签" 拼接串与输入比较, 兼容用户同时填写中英文(如 "泵 Pump")</li>
     *   <li>子串模糊匹配: 仅当 fieldConfig.fuzzy 为 true 时执行, 任一标签包含输入值即命中(如输入 "泵" 命中 "离心泵")</li>
     * </ol>
     *
     * @param valueStr    待匹配的字典值(非空, 调用方保证)
     * @param fieldConfig 字段字典配置(含 CN/EN 字典表与 fuzzy 开关)
     * @return 匹配到的 DictItem; 所有策略均未命中时返回 null(表示匹配失败)
     */
    private static DictItem matchDictItem2(String valueStr, FieldDictConfig fieldConfig) {
        // 空值直接返回未匹配
        if (StringUtil.isBlank(valueStr)) {
            return null;
        }
        // 统一归一化: 全角转半角 + 去首尾空格(消除全角/半角、大小写之外的格式差异)
        String normalized = StringUtil.toHalfWidth(valueStr).trim();

        // 匹配策略依次为(命中即返回):
        // 1. 中文标签精确匹配(先直接 get, 再逐 key 半角化比较)
        DictItem item = lookupNormalized2(fieldConfig.cnMap, normalized);
        if (item != null) {
            return item;
        }
        // 2. 英文标签精确匹配
        item = lookupNormalized2(fieldConfig.enMap, normalized);
        if (item != null) {
            return item;
        }

        // 3. CN+EN 拼接匹配: 兼容用户同时填写了中英文标签的情况(如 "泵 Pump")
        if (fieldConfig.cnMap != null) {
            for (DictItem dictItem : fieldConfig.cnMap.values()) {
                // 拼接 中文标签 + 英文标签 后统一半角化去空格, 与输入比较
                String combined = StringUtil.toHalfWidth(safe(dictItem.getCnLabel()) + safe(dictItem.getEnLabel())).trim();
                if (normalized.equals(combined)) {
                    return dictItem;
                }
            }
        }

        // 4. 子串模糊匹配: 仅当配置开启 fuzzy 时才执行
        //    任一标签"包含"输入值即命中(如输入 "泵" 能命中标签 "离心泵")
        if (fieldConfig.fuzzy) {
            DictItem fuzzyItem = lookupFuzzy2(fieldConfig.cnMap, normalized);
            if (fuzzyItem != null) {
                return fuzzyItem;
            }
            fuzzyItem = lookupFuzzy2(fieldConfig.enMap, normalized);
            if (fuzzyItem != null) {
                return fuzzyItem;
            }
        }
        // 所有策略均未命中, 返回 null 表示匹配失败
        return null;
    }

    /**
     * 子串模糊匹配: 任一标签包含输入即命中(跳过完全相同者)。
     * <p>
     * 遍历字典标签(先半角化+去空格保证口径一致), 跳过与输入完全相同的标签
     * (精确匹配已由 lookupNormalized2 在前置步骤处理, 此处避免重复命中),
     * 返回第一个"包含"输入值的标签对应的 DictItem。
     *
     * @param map             字典表, key=标签(中文或英文), value=DictItem; 为 null 或空时直接返回 null
     * @param normalizedValue 已半角化+去首尾空格的输入值
     * @return 第一个标签包含输入值的 DictItem; 未命中返回 null
     */
    private static DictItem lookupFuzzy2(Map<String, DictItem> map, String normalizedValue) {
        // 字典表为空时无可匹配项
        if (map == null || map.isEmpty()) {
            return null;
        }
        // 遍历字典标签, 逐个判断是否"包含"输入值
        for (Map.Entry<String, DictItem> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key == null) {
                continue;
            }
            // 标签同样先半角化+去空格, 保证比较口径一致
            String normalizedKey = StringUtil.toHalfWidth(key).trim();
            // 跳过与输入完全相同的标签(精确匹配已在前面步骤处理过, 避免重复命中)
            if (normalizedKey.equals(normalizedValue)) {
                continue;
            }
            // 标签包含输入值即命中, 返回第一个命中的字典项(标签唯一时无歧义)
            if (normalizedKey.contains(normalizedValue)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 归一化精确匹配: 先直接取值, 再逐 key 半角化+去空格后比较。
     * <p>
     * 两步策略:
     * <ol>
     *   <li>快速路径: 直接用归一化后的输入值作为 key 查询字典表(绝大多数场景命中)</li>
     *   <li>兜底路径: 字典 key 本身含全角字符或多余空格时, 逐 key 半角化+去空格后与输入比较</li>
     * </ol>
     *
     * @param map             字典表, key=标签(中文或英文), value=DictItem; 为 null 或空时直接返回 null
     * @param normalizedValue 已半角化+去首尾空格的输入值
     * @return 精确匹配到的 DictItem; 未命中返回 null
     */
    private static DictItem lookupNormalized2(Map<String, DictItem> map, String normalizedValue) {
        // 字典表为空时无可匹配项
        if (map == null || map.isEmpty()) {
            return null;
        }
        // 1. 快速路径: 直接用归一化后的值作为 key 查询(大多数情况下命中)
        DictItem item = map.get(normalizedValue);
        if (item != null) {
            return item;
        }
        // 2. 兜底路径: 若字典 key 本身含全角字符或多余空格, 逐 key 半角化+去空格后比较
        for (Map.Entry<String, DictItem> entry : map.entrySet()) {
            if (entry.getKey() == null) {
                continue;
            }
            if (normalizedValue.equals(StringUtil.toHalfWidth(entry.getKey()).trim())) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * null 转空串, 避免拼接出现 "null"。
     *
     * @param s 原始字符串, 可为 null
     * @return s 为 null 时返回空串 "", 否则原样返回
     */
    private static String safe(String s) {
        // 空转空串: 字符串拼接时避免 null 被序列化成 "null", 如 cnLabel+enLabel 场景
        return s == null ? "" : s;
    }

    /**
     * 格式化字典错误信息(中英双语)。
     * <p>
     * 格式: [未匹配值1; 未匹配值2]Dictionary item does not match the database(字典项与数据库不符 )
     * 若存在可提供的合法值, 追加提示: ，Available values include(可提供的值包括) : [合法值1; 合法值2]
     *
     * @param values      未匹配上的值列表(单值场景 1 个元素, 多值场景多个元素)
     * @param fieldConfig 字段字典配置, 用于收集可提供的合法值列表
     * @return 中英双语错误信息串
     */
    private static String formatDictError2(List<String> values, FieldDictConfig fieldConfig) {
        // 1. 收集该字典所有可提供的合法标签(CN/EN 去重合并), 用于在错误提示里展示, 方便用户改正
        List<String> available = collectAvailableValues2(fieldConfig);
        // 2. 组装中英双语错误信息:
        //    固定提示 未匹配的值列表 + 字典项与数据库不符
        String message = "[" + String.join("; ", values) + "]Dictionary item does not match the database(字典项与数据库不符 ) ";
        // 3. 有可提供的合法值列表时, 追加提示(否则省略)
        if (!available.isEmpty()) {
            message += "，Available values include(可提供的值包括) : [" + String.join("; ", available) + "]";
        }
        return message;
    }

    /**
     * 收集可提供的字典值(CN/EN 去重合并)。
     * <p>
     * 依次收集中文标签(cnMap)与英文标签(enMap)的 key, 使用 LinkedHashSet 去重并保持
     * 插入顺序(先中文后英文), 用于在错误提示中展示所有合法取值。
     *
     * @param fieldConfig 字段字典配置(含 CN/EN 字典表)
     * @return 去重后的合法标签列表(ArrayList, 便于 String.join); 无任何标签时返回空 List
     */
    private static List<String> collectAvailableValues2(FieldDictConfig fieldConfig) {
        // LinkedHashSet: 去重 + 保持插入顺序(先中文后英文)
        Set<String> available = new LinkedHashSet<>();
        // 收集中文标签
        if (fieldConfig.cnMap != null) {
            for (String key : fieldConfig.cnMap.keySet()) {
                if (!StringUtil.isBlank(key)) {
                    available.add(key);
                }
            }
        }
        // 收集英文标签(与中文重复的会自动去重)
        if (fieldConfig.enMap != null) {
            for (String key : fieldConfig.enMap.keySet()) {
                if (!StringUtil.isBlank(key)) {
                    available.add(key);
                }
            }
        }
        // 转成 ArrayList 返回, 便于后续 String.join 使用
        return new ArrayList<>(available);
    }

    // 验证 日期字段, 有严格的格式要求
    private static void validateDateFields(Map<Integer, Map<String, Object>> contentMap,
                                           LinkedHashMap<Integer, String> errorMap,
                                           Map<Integer, Set<String>> errorFieldMap,
                                           Map<String, DateTimeFormatter> dateTimeFormatterMap) {
        LinkedHashMap<Integer, String> formatFieldMap = new LinkedHashMap<>();
        for (Map.Entry<String, DateTimeFormatter> formatEntry : dateTimeFormatterMap.entrySet()) {
            String formatField = formatEntry.getKey();
            for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
                Integer key = entry.getKey(); // 第几行
                Map<String, Object> value = entry.getValue();
                if (MapUtil.isAllEmptyValue(value)) continue;
                Object formatFieldObj = value.get(formatField);
                StringBuilder errorMes = new StringBuilder();
                if (!ObjectUtils.isEmpty(formatFieldObj) && !(formatFieldObj instanceof Date)) {
                    String valueStr = formatFieldObj.toString().trim();
                    if (!DateUtil.isValidDateTime(valueStr) && !DateUtil.isValidDate(valueStr)) {
                        errorMes.append("The ").append(formatField)
                                .append(" not a valid date([").append(valueStr).append("] 不是有效的日期);");
                    }
                }

                if (errorMes.length() > 0) {
                    formatFieldMap.merge(key, errorMes.toString(), (oldVal, newVal) -> oldVal + newVal);
                    errorFieldMap.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(formatField);
                }
            }
        }

        for (Map.Entry<Integer, String> entry : formatFieldMap.entrySet()) {
            errorMap.merge(entry.getKey(), entry.getValue(), (oldVal, newVal) -> oldVal + newVal);
        }
    }

    /**
     * @param
     * @return
     * @description 日期字段, 不分localdate, localdatetime, 只要是日期就行
     * @author wg
     * @date 2026/9/21 14:45
     */
    private static void validateDateFields(Map<Integer, Map<String, Object>> contentMap,
                                           LinkedHashMap<Integer, String> errorMap,
                                           Map<Integer, Set<String>> errorFieldMap,
                                           String[] dateFields) {
        LinkedHashMap<Integer, String> fieldMap = new LinkedHashMap<>();
        for (String field : dateFields) {
            for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
                Integer key = entry.getKey(); // 第几行
                Map<String, Object> value = entry.getValue();
                if (MapUtil.isAllEmptyValue(value)) continue;
                Object fieldObj = value.get(field);
                StringBuilder errorMes = new StringBuilder();
                if (!ObjectUtils.isEmpty(fieldObj)) {
                    if (!DateUtil.isDate(fieldObj.toString())) {
                        errorMes.append("The [" + fieldObj + "] not a date([" + fieldObj + "]不是日期);");
                    }
                }

                if (errorMes.length() > 0) {
                    fieldMap.merge(key, errorMes.toString(), (oldVal, newVal) -> oldVal + newVal);
                    errorFieldMap.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(field);
                }
            }
        }

        for (Map.Entry<Integer, String> entry : fieldMap.entrySet()) {
            errorMap.merge(entry.getKey(), entry.getValue(), (oldVal, newVal) -> oldVal + newVal);
        }
    }

    /**
     * 验证存在性
     * 验证contentmap 里某些字段的值是否存在于数据库中
     *
     */
    private static void validateExistence(Map<Integer, Map<String, Object>> contentMap,
                                          LinkedHashMap<Integer, String> errorMap,
                                          Map<Integer, Set<String>> errorFieldMap,
                                          ExistenceClass existenceClass) {
        List<OrgDTO> orgList = existenceClass.getOrgList();
        List<AssetRegisterDTO> assetList = existenceClass.getAssetList();
        List<FacilityDTO> facilityDTOList = existenceClass.getFacilityDTOList();
        List<EquipmentDTO> equipmentList = existenceClass.getEquipmentList();
        String[] fields = existenceClass.getFields();
        if (CollectionUtils.isEmpty(facilityDTOList)) return;

        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        Map<String, FacilityDTO> facilityDTOMap = facilityDTOList.stream()
                .filter(e -> StringUtil.isNotBlank(e.getFacilityCode()))
                .collect(Collectors.toMap(FacilityDTO::getFacilityCode, Function.identity(), (oldVal, newVal) -> newVal));
        for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
            Integer lineNo = entry.getKey();
            Map<String, Object> lineMap = entry.getValue();
            StringBuilder errorMes = new StringBuilder();

            String excelFacilityCode = null;
            String excelFacilityName = null;
            String facilityId = null;
            String assetRegisterId = null;
            String orgId = null;
            String assetEnName = null;
            String xbdFacilityType = null;
            boolean matched = false;

            for (Map.Entry<String, Object> lineValEntry : lineMap.entrySet()) {
                String fieldName = lineValEntry.getKey();
                Object fieldValue = lineValEntry.getValue();
                if (ObjectUtils.isEmpty(fieldValue)) continue;
                if ("facilityCodeName".equalsIgnoreCase(fieldName)) {
                    String[] parts = splitStr(fieldValue.toString().trim());
                    excelFacilityCode = parts[0];
                    excelFacilityName = parts[1];

                    FacilityDTO facilityDTO = facilityDTOMap.get(excelFacilityCode);
                    if (facilityDTO == null) {
                        errorMes.append("The facilityCode does not in database(设施编码在数据库中不存在);");
                        continue;
                    }
                    String dbFacilityName = facilityDTO.getFacilityName();
                    facilityId = facilityDTO.getId();
                    orgId = facilityDTO.getOrgId();
                    assetRegisterId = facilityDTO.getAssetRegisterId();
                    assetEnName = facilityDTO.getAssetEnName();
                    xbdFacilityType = facilityDTO.getXbdFacilityType();
                    if (StringUtil.isBlank(dbFacilityName)) {
                        errorMes.append("The facilityCode does not in database(设施编码在数据库中不存在);");
                    } else if (!ObjectUtils.nullSafeEquals(dbFacilityName, excelFacilityName)) {
                        errorMes.append("The facilityCode does not correspond to the facility name(设施名称与设施编码不对应, 与设施编码对应的设施名称是: ")
                                .append(dbFacilityName)
                                .append(" );");
                    } else {
                        matched = true;
                    }
                }
                if ("facilityCode".equalsIgnoreCase(fieldName)) {
                    // String[] parts = splitStr(fieldValue.toString().trim());
                    // excelFacilityCode = parts[0];
                    // excelFacilityName = parts[1];

                    excelFacilityCode = fieldValue.toString().trim();

                    FacilityDTO facilityDTO = facilityDTOMap.get(excelFacilityCode);
                    if (facilityDTO == null) {
                        errorMes.append("The facilityCode does not in database(设施编码在数据库中不存在);");
                        continue;
                    }
                    // String dbFacilityName = facilityDTO.getFacilityName();
                    // facilityId = facilityDTO.getId();
                    // orgId = facilityDTO.getOrgId();
                    // assetRegisterId = facilityDTO.getAssetRegisterId();
                    // assetEnName = facilityDTO.getAssetEnName();
                    // xbdFacilityType = facilityDTO.getXbdFacilityType();
                    // if (StringUtil.isBlank(dbFacilityName)) {
                    //     errorMes.append("The facilityCode does not in database(设施编码在数据库中不存在);");
                    // } else if (!ObjectUtils.nullSafeEquals(dbFacilityName, excelFacilityName)) {
                    //     errorMes.append("The facilityCode does not correspond to the facility name(设施名称与设施编码不对应, 与设施编码对应的设施名称是: ")
                    //             .append(dbFacilityName)
                    //             .append(" );");
                    // } else {
                    //     matched = true;
                    // }
                }
            }

            if (matched) {
                lineMap.put("facilityCode", excelFacilityCode);
                lineMap.put("facilityName", excelFacilityName);
                lineMap.put("facilityId", facilityId);
                lineMap.put("orgId", orgId);
                lineMap.put("assetRegisterId", assetRegisterId);
                lineMap.put("assetEnName", assetEnName);
                lineMap.put("xbdFacilityType", xbdFacilityType);
            }

            if (errorMes.length() > 0) {
                linkedHashMap.merge(lineNo, errorMes.toString(), (oldVal, newVal) -> oldVal + newVal);
                errorFieldMap.computeIfAbsent(lineNo, k -> new LinkedHashSet<>()).add("facilityCodeName");
            }
        }

        if (ArrayUtil.isNotEmpty(fields)) {
            for (String field : fields) {

            }
        }

        for (Map.Entry<Integer, String> entry : linkedHashMap.entrySet()) {
            errorMap.merge(entry.getKey(), entry.getValue(), (oldVal, newVal) -> oldVal + newVal);
        }
    }

    private static void validateExistence2(Map<Integer, Map<String, Object>> contentMap,
                                           LinkedHashMap<Integer, String> errorMap,
                                           Map<Integer, Set<String>> errorFieldMap,
                                           ExistenceClass existenceClass) {
        List<AssetRegisterDTO> assetList = existenceClass.getAssetList();
        List<FacilityDTO> facilityDTOList = existenceClass.getFacilityDTOList();
        List<EquipmentDTO> equipmentList = existenceClass.getEquipmentList();
        String[] fields = existenceClass.getFields();
        // 没有任何可校验的数据源时直接返回, 避免把数据误判为“不存在”
        if (CollectionUtils.isEmpty(facilityDTOList)
                && CollectionUtils.isEmpty(assetList)
                && CollectionUtils.isEmpty(equipmentList)) {
            return;
        }

        Set<String> fieldSet = ArrayUtil.isEmpty(fields)
                ? Collections.emptySet()
                : new HashSet<>(Arrays.asList(fields));

        // 资产: 按中文名/英文名建立索引
        List<AssetRegisterDTO> safeAssetList = CollectionUtils.isEmpty(assetList) ? Collections.emptyList() : assetList;
        Map<String, AssetRegisterDTO> assetRegisterDTOMapCN = safeAssetList.stream()
                .filter(e -> StringUtil.isNotBlank(e.getAssetName()))
                .collect(Collectors.toMap(AssetRegisterDTO::getAssetName, Function.identity(), (oldVal, newVal) -> newVal));
        Map<String, AssetRegisterDTO> assetRegisterDTOMapEN = safeAssetList.stream()
                .filter(e -> StringUtil.isNotBlank(e.getAssetEnName()))
                .collect(Collectors.toMap(AssetRegisterDTO::getAssetEnName, Function.identity(), (oldVal, newVal) -> newVal));

        // 设施: 按设施编码/设施名称建立索引
        List<FacilityDTO> safeFacilityList = CollectionUtils.isEmpty(facilityDTOList) ? Collections.emptyList() : facilityDTOList;
        Map<String, FacilityDTO> facilityCodeMap = safeFacilityList.stream()
                .filter(e -> StringUtil.isNotBlank(e.getFacilityCode()))
                .collect(Collectors.toMap(FacilityDTO::getFacilityCode, Function.identity(), (oldVal, newVal) -> newVal));
        Map<String, FacilityDTO> facilityNameMap = safeFacilityList.stream()
                .filter(e -> StringUtil.isNotBlank(e.getFacilityName()))
                .collect(Collectors.toMap(FacilityDTO::getFacilityName, Function.identity(), (oldVal, newVal) -> newVal));

        // 设备: 按设备编码/设备名称建立索引
        List<EquipmentDTO> safeEquipmentList = CollectionUtils.isEmpty(equipmentList) ? Collections.emptyList() : equipmentList;
        Map<String, EquipmentDTO> equipmentCodeMap = safeEquipmentList.stream()
                .filter(e -> StringUtil.isNotBlank(e.getEquipmentCode()))
                .collect(Collectors.toMap(EquipmentDTO::getEquipmentCode, Function.identity(), (oldVal, newVal) -> newVal));
        Map<String, EquipmentDTO> equipmentNameMap = safeEquipmentList.stream()
                .filter(e -> StringUtil.isNotBlank(e.getEquipmentName()))
                .collect(Collectors.toMap(EquipmentDTO::getEquipmentName, Function.identity(), (oldVal, newVal) -> newVal));

        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
            Integer lineNo = entry.getKey();
            Map<String, Object> lineMap = entry.getValue();
            if (MapUtil.isAllEmptyValue(lineMap)) {
                continue;
            }

            StringBuilder errorMes = new StringBuilder();
            Set<String> rowErrorFields = new LinkedHashSet<>();

            // 1. 校验资产(中文名/英文名), 匹配成功返回资产 id 供回写
            String assetRegisterId = validateAsset(fieldSet, lineMap, assetRegisterDTOMapCN, assetRegisterDTOMapEN, errorMes, rowErrorFields);
            // 2. 校验设施编码/设施名称, 匹配成功返回 FacilityDTO 供回写
            FacilityDTO facilityDTO = validateFacility(fieldSet, lineMap, facilityCodeMap, facilityNameMap, errorMes, rowErrorFields);
            // 3. 校验设备编码/设备名称, 匹配成功返回 EquipmentDTO 供回写
            EquipmentDTO equipmentDTO = validateEquipment(fieldSet, lineMap, equipmentCodeMap, equipmentNameMap, errorMes, rowErrorFields);

            // 回写派生的数据库字段(有错误时最终会因 errorMap 不为空而拦截导入)
            if (facilityDTO != null) {
                lineMap.put("facilityId", facilityDTO.getId());
                lineMap.put("orgId", facilityDTO.getOrgId());
                // assetRegisterId 优先采用资产名校验结果, 未校验资产名时回退到设施所属资产
                if (StringUtil.isBlank(assetRegisterId)) {
                    lineMap.put("assetRegisterId", facilityDTO.getAssetRegisterId());
                }
                lineMap.put("xbdFacilityType", facilityDTO.getXbdFacilityType());
            } else if (StringUtil.isNotBlank(assetRegisterId)) {
                lineMap.put("assetRegisterId", assetRegisterId);
            }
            if (equipmentDTO != null) {
                lineMap.put("equipmentId", equipmentDTO.getId());
                lineMap.put("xbdEquipmentType", equipmentDTO.getXbdEquipmentType());
            }

            if (errorMes.length() > 0) {
                linkedHashMap.merge(lineNo, errorMes.toString(), (oldVal, newVal) -> oldVal + newVal);
                if (!rowErrorFields.isEmpty()) {
                    errorFieldMap.computeIfAbsent(lineNo, k -> new LinkedHashSet<>()).addAll(rowErrorFields);
                } else {
                    errorFieldMap.computeIfAbsent(lineNo, k -> new LinkedHashSet<>()).add("facilityCodeName");
                }
            }
        }

        for (Map.Entry<Integer, String> entry : linkedHashMap.entrySet()) {
            errorMap.merge(entry.getKey(), entry.getValue(), (oldVal, newVal) -> oldVal + newVal);
        }
    }

    /**
     * 校验资产名称(支持中文名/英文名), 匹配成功返回资产 id, 失败记录错误。
     */
    private static String validateAsset(Set<String> fieldSet,
                                        Map<String, Object> lineMap,
                                        Map<String, AssetRegisterDTO> assetRegisterDTOMapCN,
                                        Map<String, AssetRegisterDTO> assetRegisterDTOMapEN,
                                        StringBuilder errorMes,
                                        Set<String> rowErrorFields) {
        String assetRegisterId = null;
        String[] assetFields = {"assetName", "assetEnName"};
        for (String field : assetFields) {
            if (!fieldSet.contains(field)) {
                continue;
            }
            String value = toStringValue(lineMap.get(field));
            if (StringUtil.isBlank(value)) {
                continue;
            }
            AssetRegisterDTO assetDTO = assetRegisterDTOMapCN.get(value);
            if (assetDTO == null) {
                assetDTO = assetRegisterDTOMapEN.get(value);
            }
            if (assetDTO == null) {
                errorMes.append("The assetName does not in database(资产名在数据库中不存在);");
                rowErrorFields.add(field);
            } else {
                assetRegisterId = assetDTO.getId();
            }
        }
        return assetRegisterId;
    }

    /**
     * 校验设施编码/设施名称: 优先用设施编码定位, 再校验设施名称与编码的一致性; 匹配成功返回 FacilityDTO。
     */
    private static FacilityDTO validateFacility(Set<String> fieldSet,
                                                Map<String, Object> lineMap,
                                                Map<String, FacilityDTO> facilityCodeMap,
                                                Map<String, FacilityDTO> facilityNameMap,
                                                StringBuilder errorMes,
                                                Set<String> rowErrorFields) {
        FacilityDTO facilityDTO = null;

        if (fieldSet.contains("facilityCode")) {
            String code = toStringValue(lineMap.get("facilityCode"));
            if (StringUtil.isNotBlank(code)) {
                facilityDTO = facilityCodeMap.get(code);
                if (facilityDTO == null) {
                    errorMes.append("The facilityCode does not in database(设施编码在数据库中不存在);");
                    rowErrorFields.add("facilityCode");
                }
            }
        }

        if (fieldSet.contains("facilityName")) {
            String name = toStringValue(lineMap.get("facilityName"));
            if (StringUtil.isNotBlank(name)) {
                if (facilityDTO != null) {
                    // 设施编码已匹配, 校验设施名称与编码是否对应
                    if (!ObjectUtils.nullSafeEquals(facilityDTO.getFacilityName(), name)) {
                        errorMes.append("The facilityCode does not correspond to the facility name(设施名称与设施编码不对应, 与设施编码对应的设施名称是: ")
                                .append(facilityDTO.getFacilityName()).append(" );");
                        rowErrorFields.add("facilityName");
                    }
                } else {
                    FacilityDTO byName = facilityNameMap.get(name);
                    if (byName == null) {
                        errorMes.append("The facility name does not in database(设施名称在数据库中不存在);");
                        rowErrorFields.add("facilityName");
                    } else {
                        facilityDTO = byName;
                    }
                }
            }
        }

        return facilityDTO;
    }

    /**
     * 校验设备编码/设备名称: 优先用设备编码定位, 再校验设备名称与编码的一致性; 匹配成功返回 EquipmentDTO。
     */
    private static EquipmentDTO validateEquipment(Set<String> fieldSet,
                                                  Map<String, Object> lineMap,
                                                  Map<String, EquipmentDTO> equipmentCodeMap,
                                                  Map<String, EquipmentDTO> equipmentNameMap,
                                                  StringBuilder errorMes,
                                                  Set<String> rowErrorFields) {
        EquipmentDTO equipmentDTO = null;

        if (fieldSet.contains("equipmentCode")) {
            String code = toStringValue(lineMap.get("equipmentCode"));
            if (StringUtil.isNotBlank(code)) {
                equipmentDTO = equipmentCodeMap.get(code);
                if (equipmentDTO == null) {
                    errorMes.append("The equipment code does not in database(设备编码在数据库中不存在);");
                    rowErrorFields.add("equipmentCode");
                }
            }
        }

        if (fieldSet.contains("equipmentName")) {
            String name = toStringValue(lineMap.get("equipmentName"));
            if (StringUtil.isNotBlank(name)) {
                if (equipmentDTO != null) {
                    // 设备编码已匹配, 校验设备名称与编码是否对应
                    if (!ObjectUtils.nullSafeEquals(equipmentDTO.getEquipmentName(), name)) {
                        errorMes.append("The equipment code does not correspond to the equipment name(设备名称与设备编码不对应, 与设备编码对应的设备名称是: ")
                                .append(equipmentDTO.getEquipmentName()).append(" );");
                        rowErrorFields.add("equipmentName");
                    }
                } else {
                    EquipmentDTO byName = equipmentNameMap.get(name);
                    if (byName == null) {
                        errorMes.append("The equipment name does not in database(设备名称在数据库中不存在);");
                        rowErrorFields.add("equipmentName");
                    } else {
                        equipmentDTO = byName;
                    }
                }
            }
        }

        return equipmentDTO;
    }

    /**
     * 对象转字符串并去除首尾空格, 便于后续比较。
     */
    private static String toStringValue(Object value) {
        return value == null ? null : value.toString().trim();
    }

    // String input = "UGD-KF-LOP01(乌干达KF油田项目长输油气管道)";
    private static String[] splitStr(String input) {
        String[] result = new String[2];
        if (StringUtil.isBlank(input)) return result;

        int leftIndex = input.lastIndexOf("(");
        int rightIndex = input.lastIndexOf(")");

        // 核心：必须判断括号是否存在，防止越界异常
        if (leftIndex != -1 && rightIndex != -1 && leftIndex < rightIndex) {
            String part1 = input.substring(0, leftIndex).trim(); // 截取从 0 到 左括号
            String part2 = input.substring(leftIndex + 1, rightIndex).trim(); // 截取 左括号+1 到 右括号

            result[0] = part1;
            result[1] = part2;
        }
        return result;
    }

    /**
     * 校验必填字段是否为空，并将错误信息合并到 errorMap。
     *
     * @param contentMap     以行号为键、字段值 Map 为值的正文数据
     * @param errorMap       用于收集校验错误信息的 Map
     * @param requiredFields 需要校验的必填字段集合
     */
    private static void validateRequiredField(Map<Integer, Map<String, Object>> contentMap,
                                              LinkedHashMap<Integer, String> errorMap,
                                              Map<Integer, Set<String>> errorFieldMap,
                                              String[] requiredFields) {
        LinkedHashMap<Integer, String> requiredFieldMap = new LinkedHashMap<>();
        for (String requiredField : requiredFields) {
            for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
                Integer key = entry.getKey(); // 第几行
                Map<String, Object> value = entry.getValue();
                if (MapUtil.isAllEmptyValue(value)) continue;
                Object requiredFieldObj = value.get(requiredField);
                StringBuilder errorMes = new StringBuilder();
                if (ObjectUtils.isEmpty(requiredFieldObj)) {
                    errorMes.append("The " + RequiredFieldEnum.valueOf(requiredField).descriptionEN + " cannot be empty(" + RequiredFieldEnum.valueOf(requiredField).descriptionCN + "不能为空);");
                }

                if (errorMes.length() > 0) {
                    requiredFieldMap.merge(key, errorMes.toString(), (oldVal, newVal) -> oldVal + newVal);
                    errorFieldMap.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(requiredField);
                }
            }
        }

        for (Map.Entry<Integer, String> entry : requiredFieldMap.entrySet()) {
            errorMap.merge(entry.getKey(), entry.getValue(), (oldVal, newVal) -> oldVal + newVal);
        }
    }

    public static void validateRequiredField(Map<Integer, Map<String, Object>> contentMap,
                                             LinkedHashMap<Integer, String> errorMap,
                                             Map<Integer, Set<String>> errorFieldMap,
                                             List<RequiredFieldEnum> requiredFieldEnumList) {
        if (CollectionUtils.isEmpty(requiredFieldEnumList) || contentMap == null || contentMap.isEmpty()) {
            return;
        }

        LinkedHashMap<Integer, String> requiredFieldMap = new LinkedHashMap<>();
        for (RequiredFieldEnum fieldEnum : requiredFieldEnumList) {
            String field = fieldEnum.getFieldNameVO();
            String fieldEn = fieldEnum.getDescriptionEN();
            String fieldCn = fieldEnum.getDescriptionCN();
            for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
                Integer key = entry.getKey(); // 第几行
                Map<String, Object> value = entry.getValue();
                if (MapUtil.isAllEmptyValue(value)) continue;
                Object requiredFieldObj = value.get(field);
                StringBuilder errorMes = new StringBuilder();
                if (ObjectUtils.isEmpty(requiredFieldObj)) {
                    errorMes.append("The ").append(fieldEn)
                            .append(" cannot be empty(").append(fieldCn).append("不能为空);");
                    errorFieldMap.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(field);
                }

                if (errorMes.length() > 0) {
                    requiredFieldMap.merge(key, errorMes.toString(), (oldVal, newVal) -> oldVal + newVal);
                }
            }
        }

        for (Map.Entry<Integer, String> entry : requiredFieldMap.entrySet()) {
            errorMap.merge(entry.getKey(), entry.getValue(), (oldVal, newVal) -> oldVal + newVal);
        }
    }

    /**
     * 校验重复项：当某字段的值在多行中重复出现时，为所有重复行添加错误信息。
     * 唯一性
     *
     * @param contentMap   以行号为键、字段值 Map 为值的正文数据
     * @param errorMap     用于收集校验错误信息的 Map
     * @param repeatFields 需要校验重复的字段集合
     */
    private static void validateRepeatField(Map<Integer, Map<String, Object>> contentMap,
                                            LinkedHashMap<Integer, String> errorMap,
                                            Map<Integer, Set<String>> errorFieldMap,
                                            String[] repeatFields) {
        for (String repeatField : repeatFields) {
            // 字段值 -> 该值出现的所有行号
            Map<String, List<Integer>> valueRowsMap = new HashMap<>();
            for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
                Integer rowNum = entry.getKey(); // 第几行
                Map<String, Object> value = entry.getValue();
                if (MapUtil.isAllEmptyValue(value)) continue;
                Object repeatFieldObj = value.get(repeatField);
                if (ObjectUtils.isEmpty(repeatFieldObj)) continue;
                String repeatValue = repeatFieldObj.toString().trim();
                valueRowsMap.computeIfAbsent(repeatValue, k -> new ArrayList<>()).add(rowNum);
            }

            String message = "The " + UniquenessFieldEnum.valueOf(repeatField).en
                    + " is duplicated(" + UniquenessFieldEnum.valueOf(repeatField).cn + "有重复);";
            for (List<Integer> rows : valueRowsMap.values()) {
                if (rows.size() > 1) {
                    for (Integer rowNum : rows) {
                        errorMap.merge(rowNum, message, (oldVal, newVal) -> oldVal + newVal);
                        errorFieldMap.computeIfAbsent(rowNum, k -> new LinkedHashSet<>()).add(repeatField);
                    }
                }
            }
        }
    }

    private static Map<String, Pattern> getFormatMap() {
        Map<String, Pattern> formatMap = new HashMap<>();
        Pattern YYYY_MM_PATTERN = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])$");
        formatMap.put(FormatFieldEnum.valueOf("month").getEn(), YYYY_MM_PATTERN);

        return formatMap;
    }

    public static void validatePattern(Map<Integer, Map<String, Object>> contentMap,
                                       LinkedHashMap<Integer, String> errorMap,
                                       Map<Integer, Set<String>> errorFieldMap,
                                       Map<String, Pattern> formatMap) {
        LinkedHashMap<Integer, String> formatFieldMap = new LinkedHashMap<>();
        for (Map.Entry<String, Pattern> formatEntry : formatMap.entrySet()) {
            String formatField = formatEntry.getKey();
            Pattern pattern = formatEntry.getValue();
            FormatFieldEnum fieldEnum = enumValueOrNull(FormatFieldEnum.class, formatField);
            String fieldEn = fieldEnum != null ? fieldEnum.en : formatField;
            String fieldCn = fieldEnum != null ? fieldEnum.cn : formatField;
            for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
                Integer key = entry.getKey(); // 第几行
                Map<String, Object> value = entry.getValue();
                if (MapUtil.isAllEmptyValue(value)) continue;
                Object formatFieldObj = value.get(formatField);
                StringBuilder errorMes = new StringBuilder();
                if (!ObjectUtils.isEmpty(formatFieldObj)) {
                    if (!validPattern(formatFieldObj.toString().trim(), pattern)) {
                        errorMes.append("The ").append(fieldEn)
                                .append(" format is incorrect(").append(fieldCn).append("格式不正确);");
                        errorFieldMap.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(fieldEn);
                    }
                }

                if (errorMes.length() > 0) {
                    formatFieldMap.merge(key, errorMes.toString(), (oldVal, newVal) -> oldVal + newVal);
                }
            }
        }

        for (Map.Entry<Integer, String> entry : formatFieldMap.entrySet()) {
            errorMap.merge(entry.getKey(), entry.getValue(), (oldVal, newVal) -> oldVal + newVal);
        }
    }

    private static boolean validPattern(String dateStr, Pattern pattern) {
        // 1. 空值判断
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }

        String trimmedDate = dateStr.trim();

        // 2. 正则校验格式
        if (!pattern.matcher(trimmedDate).matches()) {
            return false;
        }

        return true;
    }

    /**
     * 创建一个空的 ExcelContext。
     *
     * @param <T> DTO 泛型类型
     * @return 新的 ExcelContext 实例
     */
    private static <T> ExcelContext<T> getContext() {
        return new ExcelContext<>();
    }

    /**
     * 将校验错误信息写入工作簿最后一列之后的新列，并标红正文里有错误的单元格。
     *
     * @param workbook      Excel 工作簿
     * @param errorMap      以行号为键、错误信息为值的错误集合
     * @param headers       表头字段名数组（下标即列索引）
     * @param errorFieldMap 以行号为键、出错字段名集合为值的集合
     */
    private static void writeErrorToWorkbook(Workbook workbook, LinkedHashMap<Integer, String> errorMap,
                                             String[] headers, Map<Integer, Set<String>> errorFieldMap) {
        writeErrorToWorkbook(workbook, 0, 0, errorMap, headers, errorFieldMap);
    }

    /**
     * 将校验错误信息写入指定 sheet 最后一列之后的新列，并标红正文里有错误的单元格。
     *
     * @param workbook      Excel 工作簿
     * @param sheetIndex    目标 sheet 索引
     * @param errorMap      以行号为键、错误信息为值的错误集合
     * @param headers       表头字段名数组（下标即列索引）
     * @param errorFieldMap 以行号为键、出错字段名集合为值的集合
     */
    public static void writeErrorToWorkbook(Workbook workbook, int sheetIndex, LinkedHashMap<Integer, String> errorMap,
                                            String[] headers, Map<Integer, Set<String>> errorFieldMap) {
        writeErrorToWorkbook(workbook, sheetIndex, 0, errorMap, headers, errorFieldMap);
    }

    /**
     * 将校验错误信息写入指定 sheet 最后一列之后的新列，并标红正文里有错误的单元格。
     *
     * @param workbook      Excel 工作簿
     * @param sheetIndex    目标 sheet 索引
     * @param titleRowIndex 表头所在行索引（错误信息列标题写入该行）
     * @param errorMap      以行号为键、错误信息为值的错误集合
     * @param headers       表头字段名数组（下标即列索引）
     * @param errorFieldMap 以行号为键、出错字段名集合为值的集合
     */
    public static void writeErrorToWorkbook(Workbook workbook, int sheetIndex, int titleRowIndex,
                                            LinkedHashMap<Integer, String> errorMap,
                                            String[] headers, Map<Integer, Set<String>> errorFieldMap) {
        if (errorMap == null || errorMap.isEmpty()) {
            return;
        }

        Sheet sheet = workbook.getSheetAt(sheetIndex);

        // 找到最后一列的索引作为新列位置
        Row headerRow1 = sheet.getRow(titleRowIndex);
        int lastCol1 = headerRow1 == null ? -1 : headerRow1.getLastCellNum();
        int errorCol = Math.max(lastCol1, 0);
        if (errorCol < 0) {
            errorCol = 0;
        }

        // 构建 字段名 -> 列索引 映射（表头数组下标即列索引）
        Map<String, Integer> fieldColumnMap = new HashMap<>();
        if (headers != null) {
            for (int i = 0; i < headers.length; i++) {
                if (headers[i] != null) {
                    fieldColumnMap.put(headers[i], i);
                }
            }
        }

        // 正文有错误的单元格字体：黑色
        Font errorCellFont = workbook.createFont();
        errorCellFont.setColor(IndexedColors.BLACK.getIndex());

        // 错误信息列：红色字体（仅文字说明）
        CellStyle errorInfoStyle = workbook.createCellStyle();
        Font errorInfoFont = workbook.createFont();
        errorInfoFont.setColor(IndexedColors.RED.getIndex());
        errorInfoStyle.setFont(errorInfoFont);

        // 在错误信息列写入表头
        Cell headerCell = getOrCreateCell(sheet, titleRowIndex, errorCol);
        headerCell.setCellValue("Error Info(错误信息)");
        headerCell.setCellStyle(errorInfoStyle);

        // 在对应数据行写入错误信息，并把正文里有错误的单元格标红
        for (Map.Entry<Integer, String> entry : errorMap.entrySet()) {
            Integer rowNum = entry.getKey();
            String errorMsg = entry.getValue();

            // 1. 标记正文里有错误的单元格：红色背景、黑色字体
            if (errorFieldMap != null) {
                Set<String> fields = errorFieldMap.get(rowNum);
                if (fields != null) {
                    for (String field : fields) {
                        Integer col = fieldColumnMap.get(field);
                        if (col != null) {
                            Cell cell = getOrCreateCell(sheet, rowNum, col);
                            cell.setCellStyle(createErrorCellStyle(workbook, cell.getCellStyle(), errorCellFont));
                        }
                    }
                }
            }

            // 2. 在错误信息列写入错误描述
            if (errorMsg == null || errorMsg.isEmpty()) {
                continue;
            }
            Cell errorCell = getOrCreateCell(sheet, rowNum, errorCol);
            errorCell.setCellValue(errorMsg);
            errorCell.setCellStyle(errorInfoStyle);
        }
    }

    /**
     * 基于原单元格样式创建错误样式：保留原边框/对齐/格式，仅叠加红色背景与黑色字体。
     */
    private static CellStyle createErrorCellStyle(Workbook workbook, CellStyle existingStyle, Font errorFont) {
        CellStyle style = workbook.createCellStyle();
        if (existingStyle != null) {
            ExcelUtil.copyStyle(existingStyle, style);
        }
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(errorFont);
        return style;
    }

    /**
     * 获取指定行列的单元格，不存在则创建。
     *
     * @param sheet  目标工作表
     * @param rowNum 行号
     * @param colNum 列号
     * @return 对应位置的单元格
     */
    private static Cell getOrCreateCell(Sheet sheet, int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }
        return cell;
    }

    /**
     * 将工作簿以 xlsx 格式输出到 HTTP 响应，触发浏览器下载。
     *
     * @param workbook Excel 工作簿
     * @param fileName 下载文件名（不含扩展名）
     * @param response HTTP 响应对象
     * @throws IOException 输出流操作失败时抛出
     */
    public static void exportWorkbook(Workbook workbook, String fileName, HttpServletResponse response) throws IOException {
        try (ServletOutputStream out = response.getOutputStream()) {
            String name = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + name + ".xlsx");

            workbook.write(out);
            out.flush();
        }
    }

    /**
     * 校验数据库数据与excel数据是否重复
     *
     * @param map <要检查的字段名(英文), <值, entity>>
     */
    private static <E> void validateDatasource(Map<Integer, Map<String, Object>> contentMap,
                                               LinkedHashMap<Integer, String> errorMap,
                                               Map<Integer, Set<String>> errorFieldMap,
                                               HashMap<String, Map<String, E>> map) {
        LinkedHashMap<Integer, String> multipartDataMap = new LinkedHashMap<>();
        for (String fieldName : map.keySet()) {
            Map<String, E> eMap = map.get(fieldName);
            for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
                Integer key = entry.getKey(); // 第几行
                Map<String, Object> value = entry.getValue();
                if (MapUtil.isAllEmptyValue(value)) continue;
                Object excelObj = value.get(fieldName);
                StringBuilder errorMes = new StringBuilder();
                if (!ObjectUtils.isEmpty(excelObj)) {
                    if (eMap.get(excelObj.toString().trim()) != null) {
                        errorMes.append("The " + UniquenessFieldEnum.valueOf(fieldName).en + " Duplicate with database data(" + UniquenessFieldEnum.valueOf(fieldName).cn + "与数据库数据重复);");
                    }
                }

                if (errorMes.length() > 0) {
                    multipartDataMap.merge(key, errorMes.toString(), (oldVal, newVal) -> oldVal + newVal);
                    errorFieldMap.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(fieldName);
                }
            }
        }

        for (Map.Entry<Integer, String> entry : multipartDataMap.entrySet()) {
            errorMap.merge(entry.getKey(), entry.getValue(), (oldVal, newVal) -> oldVal + newVal);
        }
    }

    /**
     * 验证整数项
     */
    private static void validateIntegerFields(Map<Integer, Map<String, Object>> contentMap,
                                              LinkedHashMap<Integer, String> errorMap,
                                              Map<Integer, Set<String>> errorFieldMap,
                                              ValidateConfig<?> config) {
        String[] fields = config.getIntegerFields();
        LinkedHashMap<Integer, String> fieldMap = new LinkedHashMap<>();
        for (String field : fields) {
            for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
                Integer key = entry.getKey(); // 第几行
                Map<String, Object> value = entry.getValue();
                if (MapUtil.isAllEmptyValue(value)) continue;
                Object fieldObj = value.get(field);
                StringBuilder errorMes = new StringBuilder();
                if (!ObjectUtils.isEmpty(fieldObj)) {
                    if (!StringUtil.isNumber(fieldObj)) {
                        errorMes.append("The [" + fieldObj + "] not a number([" + fieldObj + "]不是数字);");
                    } else {
                        if (!StringUtil.isInteger(fieldObj.toString().trim())) {
                            errorMes.append("The [" + fieldObj + "] not a integer([" + fieldObj + "]不是整数);");
                        }
                    }
                }

                if (errorMes.length() > 0) {
                    fieldMap.merge(key, errorMes.toString(), (oldVal, newVal) -> oldVal + newVal);
                    errorFieldMap.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(field);
                }
            }
        }

        for (Map.Entry<Integer, String> entry : fieldMap.entrySet()) {
            errorMap.merge(entry.getKey(), entry.getValue(), (oldVal, newVal) -> oldVal + newVal);
        }
    }

    /**
     * 验证数字项
     */
    private static void validateNumFields(Map<Integer, Map<String, Object>> contentMap,
                                          LinkedHashMap<Integer, String> errorMap,
                                          Map<Integer, Set<String>> errorFieldMap,
                                          ValidateConfig<?> config) {
        String[] numFields = config.getNumFields();
        LinkedHashMap<Integer, String> fieldMap = new LinkedHashMap<>();
        for (String field : numFields) {
            for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
                Integer key = entry.getKey(); // 第几行
                Map<String, Object> value = entry.getValue();
                if (MapUtil.isAllEmptyValue(value)) continue;
                Object fieldObj = value.get(field);
                StringBuilder errorMes = new StringBuilder();
                if (!ObjectUtils.isEmpty(fieldObj)) {
                    if (!isNumberOrPercentage(fieldObj)) {
                        errorMes.append("The [" + fieldObj + "] not a number([" + fieldObj + "]不是数字);");
                    }
                }

                if (errorMes.length() > 0) {
                    fieldMap.merge(key, errorMes.toString(), (oldVal, newVal) -> oldVal + newVal);
                    errorFieldMap.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(field);
                }
            }
        }

        for (Map.Entry<Integer, String> entry : fieldMap.entrySet()) {
            errorMap.merge(entry.getKey(), entry.getValue(), (oldVal, newVal) -> oldVal + newVal);
        }
    }

    /**
     * 判断是否是数字，允许以 % 结尾（例如 90%、90 %、全角 90％）
     */
    private static boolean isNumberOrPercentage(Object val) {
        if (StringUtil.isNumber(val)) {
            return true;
        }
        // 统一将全角字符转为半角，兼容全角百分号 ％ 和全角数字
        String str = StringUtil.toHalfWidth(String.valueOf(val)).trim();
        if (str.endsWith("%")) {
            str = str.substring(0, str.length() - 1).trim();
            return StringUtil.isNumber(str);
        }
        return false;
    }

    /**
     * 按 index 字段排序，index 为空的排在最后。
     *
     * @param excels      待排序列表
     * @param indexGetter 获取 index 字段的函数
     * @param <T>         元素类型
     * @param <U>         index 字段类型
     * @return 排序后的列表
     */
    public static <T, U extends Comparable<? super U>> List<T> sortList(List<T> excels, Function<T, U> indexGetter) {
        if (CollectionUtils.isEmpty(excels)) {
            return Collections.emptyList();
        }
        Map<Boolean, List<T>> collect = excels.stream()
                .collect(Collectors.partitioningBy(e -> indexGetter.apply(e) != null));
        List<T> hasIndex = collect.get(true);
        List<T> noIndex = collect.get(false);
        hasIndex.sort(Comparator.comparing(indexGetter, Comparator.nullsLast(Comparator.naturalOrder())));

        List<T> result = new ArrayList<>(hasIndex.size() + noIndex.size());
        result.addAll(hasIndex);
        result.addAll(noIndex);
        return result;
    }

    /**
     * 构建月份字段的格式校验正则映射。
     *
     * @return 字段名 -> 正则表达式 的映射
     */
    public static Map<String, Pattern> getYearMonthFormatMap() {
        Map<String, Pattern> formatMap = new HashMap<>();
        Pattern YYYY_MM_PATTERN = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])$");
        formatMap.put(FormatFieldEnum.valueOf("month").getEn(), YYYY_MM_PATTERN);
        return formatMap;
    }

    @Data
    public static class ExcelContext<T> {
        Map<String, Map<String, DictItem>> dictMapCN;
        Map<String, Map<String, DictItem>> dictMapEN;
        LinkedHashMap<Integer, String> errorMap;
        Map<Integer, Set<String>> errorFieldMap;
        List<T> list;
        Workbook workbook;
        List<DictItem> dictItem;
        List<OrgDTO> org;
        List<AssetRegisterDTO> asset;
        List<EquipmentDTO> equipment;
    }

    /**
     * Excel 导入校验配置。
     */
    @Data
    public static class ValidateConfig<E> {
        /**
         * 必填字段
         */
        private String[] requiredFields;
        /**
         * 格式校验：字段 -> 正则
         */
        private Map<String, Pattern> formatMap;
        /**
         * 校验重复字段
         */
        private String[] repeatFields;
        /**
         * 字段在数据库中的值
         */
        private HashMap<String, Map<String, E>> datasourceMap;

        private ExistenceClass existenceClass;

        private Map<String, Map<String, DictItem>> dictMapCN;
        private Map<String, Map<String, DictItem>> dictMapEN;
        private Map<String, DictItem> cnMap;
        private Map<String, DictItem> enMap;
        /**
         * 字典字段映射列表(Excel 字段 -> 字典类型码 -> 回写字段)
         */
        private List<FieldDictMapping> mappings;
        /**
         * 日期字段, 有严格的格式限制
         */
        private Map<String, DateTimeFormatter> dateFieldsMap;

        private String[] dictFields;

        private String[] numFields;
        private String[] integerFields;
        private Map<String, List<String>> enumFields;

        // 一致性 字段
        private List<CongruenceEnum> congruenceEnumList;

        // 日期字段, 部分localdate, localdatetime, 只要是日期就行
        private String[] dateFields;

        private String[] replaceFields;
    }

    public enum RequiredFieldEnum {
        assetName("assetName", "assetName", "海外资产名称", "asset name"),
        facilityCode("facilityCode", "facility_code", "设施编号", "facility code"),
        facilityName("facilityName", "facility_name", "设施名称", "facility name"),
        equipmentCode("equipmentCode", "equipment_code", "设备编号", "equipment code"),
        equipmentName("equipmentName", "equipment_name", "设备名称", "equipment name"),
        inspectionType("inspectionType", "inspection_type", "检验检测类型", "inspection type"),
        procurementProject("procurementProject", "procurement_project", "采购项目名称", "procurement project name"),
        packageNo("packageNo", "package_no", "采购包/采购编号", "package Number"),
        sourceStage("sourceStage", "source_stage", "采办进程", "source stage"),
        currentStageOwner("currentStageOwner", "current_stage_owner", "当前阶段负责人", "current stage owner"),
        orderNo("orderNo", "order_no", "工单号", "order number"),
        associatedMonth("associatedMonth", "associated_month", "所属月份", "associated month"),
        createdOn("createdOn", "created_on", "建立时间", "create on"),
        basicFinishDate("basicFinishDate", "basic_finish_date", "计划完成时间", "planned finish date"),
        indicatorName("indicatorName", "indicator_name", "指标名称", "indicator name"),
        month("month", "month", "月份", "month"),
        monthlyRate("monthlyRate", "monthly_rate", "当月(%)", "monthlyRate"),
        ytdRate("ytdRate", "ytd_rate", "YTD(%)", "ytdRate");

        private final String fieldNameVO;
        private final String fieldNameDatasource;
        private final String descriptionCN;
        private final String descriptionEN;

        RequiredFieldEnum(String fieldNameVO, String fieldNameDatasource, String descriptionCN, String descriptionEN) {
            this.fieldNameVO = fieldNameVO;
            this.fieldNameDatasource = fieldNameDatasource;
            this.descriptionCN = descriptionCN;
            this.descriptionEN = descriptionEN;
        }

        public String getFieldNameVO() {
            return fieldNameVO;
        }

        public String getFieldNameDatasource() {
            return fieldNameDatasource;
        }

        public String getDescriptionCN() {
            return descriptionCN;
        }

        public String getDescriptionEN() {
            return descriptionEN;
        }
    }

    public enum FormatFieldEnum {
        associatedMonth("所属月份", "associatedMonth"),
        month("月份", "month");
        String cn;
        String en;

        public String getEn() {
            return en;
        }

        public String getCn() {
            return cn;
        }

        FormatFieldEnum(String cn, String en) {
            this.cn = cn;
            this.en = en;
        }
    }

    public enum UniquenessFieldEnum {
        procurementProject("采办包名称", "procurementProject"),
        month("月份", "month");
        String cn;
        String en;

        public String getEn() {
            return en;
        }

        public String getCn() {
            return cn;
        }

        UniquenessFieldEnum(String cn, String en) {
            this.cn = cn;
            this.en = en;
        }
    }

    @Data
    public static class ExistenceClass {
        List<OrgDTO> orgList;
        List<AssetRegisterDTO> assetList;
        List<FacilityDTO> facilityDTOList;
        List<EquipmentDTO> equipmentList;
        String[] fields;
    }

    /**
     * 字典字段配置(校验所需的内聚数据结构)。
     * <p>
     * 由 buildFieldConfigMap2 根据 FieldDictMapping 组装:
     * enMap/cnMap 为按 dictType 取出的字典表, idFieldName 为匹配成功后回写 id 的字段名,
     * multiValue 控制单值/多值(分号分隔)校验, fuzzy 控制是否启用子串模糊匹配。
     */
    private static class FieldDictConfig {
        /**
         * 英文标签字典表, key=英文标签, value=DictItem; 可为 null
         */
        private final Map<String, DictItem> enMap;
        /**
         * 中文标签字典表, key=中文标签, value=DictItem; 可为 null
         */
        private final Map<String, DictItem> cnMap;
        /**
         * 匹配成功后回写字典 id 的字段名(多值场景同时回写 idFieldName+Value 数组); 可为 null
         */
        private final String idFieldName;
        /**
         * 是否多值字段(单元格值按分号拆分为多个字典值逐项匹配)
         */
        private final boolean multiValue;
        /**
         * 是否启用子串模糊匹配(标签包含输入值即命中)
         */
        private final boolean fuzzy;

        /**
         * 简化构造(旧字典字段校验用): 无 id 回写、单值、不模糊。
         *
         * @param enMap 英文标签字典表
         * @param cnMap 中文标签字典表
         */
        FieldDictConfig(Map<String, DictItem> enMap, Map<String, DictItem> cnMap) {
            this(enMap, cnMap, null, false, false);
        }

        /**
         * 完整构造(FieldDictMapping 校验用)。
         *
         * @param enMap       英文标签字典表
         * @param cnMap       中文标签字典表
         * @param idFieldName 回写字典 id 的字段名
         * @param multiValue  是否多值(分号分隔)
         * @param fuzzy       是否启用子串模糊匹配
         */
        FieldDictConfig(Map<String, DictItem> enMap, Map<String, DictItem> cnMap, String idFieldName, boolean multiValue, boolean fuzzy) {
            this.enMap = enMap;
            this.cnMap = cnMap;
            this.idFieldName = idFieldName;
            this.multiValue = multiValue;
            this.fuzzy = fuzzy;
        }
    }

    /**
     * @description 检查一致性, 比如 所属月份, 表内必须一致
     * @author wg
     * @date 2026/9/10 17:29
     */
    public static void validateCongruence(Map<Integer, Map<String, Object>> contentMap,
                                          LinkedHashMap<Integer, String> errorMap,
                                          Map<Integer, Set<String>> errorFieldMap,
                                          List<CongruenceEnum> congruenceEnumList) {
        if (CollectionUtils.isEmpty(congruenceEnumList) || contentMap == null || contentMap.isEmpty()) {
            return;
        }

        for (CongruenceEnum congruenceEnum : congruenceEnumList) {
            String field = congruenceEnum.getFieldNameVO();
            // 以该列第一个非空值作为基准，其余非空值必须与其一致
            String referenceValue = null;
            for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
                Integer rowNum = entry.getKey();
                Map<String, Object> value = entry.getValue();
                if (MapUtil.isAllEmptyValue(value)) continue;
                Object fieldObj = value.get(field);
                if (ObjectUtils.isEmpty(fieldObj)) continue;
                String fieldValue = fieldObj.toString().trim();

                if (referenceValue == null) {
                    referenceValue = fieldValue;
                    continue;
                }
                if (referenceValue.equals(fieldValue)) {
                    continue;
                }

                String message = "The " + congruenceEnum.getFieldNameDatasource()
                        + " is inconsistent(" + congruenceEnum.getDescriptionCN() + "不一致);";
                errorMap.merge(rowNum, message, (oldVal, newVal) -> oldVal + newVal);
                errorFieldMap.computeIfAbsent(rowNum, k -> new LinkedHashSet<>()).add(field);
            }
        }
    }

    /**
     * 校验指定 sheet 中某字段的值必须存在于给定集合中（用于跨 sheet 校验，如 sheet2 的字段值必须在 sheet1 中存在）。
     * 校验失败时将错误信息写入对应 sheet，并返回以行号为键的错误信息。
     *
     * @param workbook         Excel 工作簿
     * @param sheetIndex       目标 sheet 索引
     * @param params           读取该 sheet 的 ExcelParams
     * @param tClass           该 sheet 对应的 Excel 类型
     * @param field            需要校验的字段名
     * @param allowedValues    合法值集合
     * @param fieldDescription 字段中文描述（用于错误提示）
     * @return 校验错误信息（行号 -> 错误信息），为空表示全部通过
     */
    public static <T> LinkedHashMap<Integer, String> validateFieldExistsInSheet(Workbook workbook,
                                                                                int sheetIndex,
                                                                                ExcelParams params,
                                                                                Class<T> tClass,
                                                                                String field,
                                                                                Set<String> allowedValues,
                                                                                String fieldDescription) throws Exception {
        String[] titleArray = ExcelUtil.readExcelTitle(workbook, params, tClass);
        Map<Integer, Map<String, Object>> contentMap = ExcelUtil.readExcelContent(workbook, titleArray, params);
        LinkedHashMap<Integer, String> errorMap = new LinkedHashMap<>();
        Map<Integer, Set<String>> errorFieldMap = new LinkedHashMap<>();
        if (CollectionUtils.isEmpty(contentMap) || CollectionUtils.isEmpty(allowedValues)) {
            return errorMap;
        }

        for (Map.Entry<Integer, Map<String, Object>> entry : contentMap.entrySet()) {
            Integer rowNum = entry.getKey();
            Map<String, Object> value = entry.getValue();
            if (MapUtil.isAllEmptyValue(value)) continue;
            Object fieldObj = value.get(field);
            if (ObjectUtils.isEmpty(fieldObj)) continue;
            String fieldValue = fieldObj.toString().trim();
            if (allowedValues.contains(fieldValue)) continue;

            String message = "The [" + fieldValue + "] does not exist in sheet1("
                    + fieldDescription + "[" + fieldValue + "]在sheet1中不存在);";
            errorMap.merge(rowNum, message, (oldVal, newVal) -> oldVal + newVal);
            errorFieldMap.computeIfAbsent(rowNum, k -> new LinkedHashSet<>()).add(field);
        }

        if (!errorMap.isEmpty()) {
            int titleRowIndex = params.getTitleIndex() == null ? 0 : params.getTitleIndex();
            writeErrorToWorkbook(workbook, sheetIndex, titleRowIndex, errorMap, titleArray, errorFieldMap);
        }
        return errorMap;
    }

    /**
     * 安全获取枚举值：名称不存在或为空时返回 null，避免 valueOf 抛 IllegalArgumentException。
     */
    private static <E extends Enum<E>> E enumValueOrNull(Class<E> enumType, String name) {
        if (name == null) {
            return null;
        }
        try {
            return Enum.valueOf(enumType, name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 验证枚举字段
     * Map<字段名, 字段名允许的值list> enumList
     */
    private static void validateEnumFields(Map<Integer, Map<String, Object>> contentMap,
                                           LinkedHashMap<Integer, String> errorMap,
                                           Map<Integer, Set<String>> errorFieldMap,
                                           Map<String, List<String>> enumList) {
        if (CollectionUtils.isEmpty(enumList) || contentMap == null || contentMap.isEmpty()) {
            return;
        }

        for (Map.Entry<String, List<String>> entry : enumList.entrySet()) {
            String fieldName = entry.getKey();
            List<String> allowedValues = entry.getValue();
            Set<String> allowedSet = allowedValues == null ? Collections.emptySet()
                    : allowedValues.stream().filter(Objects::nonNull).map(String::trim).collect(Collectors.toSet());

            for (Map.Entry<Integer, Map<String, Object>> contentEntry : contentMap.entrySet()) {
                Integer rowNum = contentEntry.getKey();
                Map<String, Object> value = contentEntry.getValue();
                if (MapUtil.isAllEmptyValue(value)) continue;
                Object fieldObj = value.get(fieldName);
                if (ObjectUtils.isEmpty(fieldObj)) continue;
                String fieldValue = fieldObj.toString().trim();

                if (allowedSet.contains(fieldValue)) {
                    continue;
                }

                String allowedStr = allowedSet.isEmpty() ? "" : String.join(",", allowedSet);
                String message = "The [" + fieldValue + "] not in enum list of [" + fieldName + "]([" + fieldValue + "]不在[" + fieldName + "]允许范围内, 允许值: " + allowedStr + ");";
                errorMap.merge(rowNum, message, (oldVal, newVal) -> oldVal + newVal);
                errorFieldMap.computeIfAbsent(rowNum, k -> new LinkedHashSet<>()).add(fieldName);
            }
        }
    }

    /**
     * 验证表内 replace 字段的值是否在允许的取值范围内
     *
     * @param content       key=行号, value=行数据(Excel 内容, key=字段名)
     * @param errorMap      key=行号, value=错误信息串(会合并到已有错误信息后)
     * @param errorFieldMap key=行号, value=出错字段名集合(用于标红单元格)
     * @param replaceMap    key=字段名, value=(Excel 展示值 -> 数据库值)
     * @param fields        要校验的字段名
     */
    public static void validateReplaceFields(
            Map<Integer, Map<String, Object>> content,
            LinkedHashMap<Integer, String> errorMap,
            Map<Integer, Set<String>> errorFieldMap,
            Map<String, Map<String, String>> replaceMap,
            String[] fields) {
        if (content == null || content.isEmpty() || replaceMap == null || replaceMap.isEmpty() || ArrayUtil.isEmpty(fields)) {
            return;
        }

        // 仅校验 fields 指定的字段, 而不是遍历 replaceMap 里的所有字段
        for (String fieldName : fields) {
            if (StringUtil.isBlank(fieldName)) {
                continue;
            }
            Map<String, String> replaceValues = replaceMap.get(fieldName);
            if (replaceValues == null || replaceValues.isEmpty()) {
                continue;
            }
            Set<String> replaceVals = replaceValues.keySet();

            for (Map.Entry<Integer, Map<String, Object>> contentEntry : content.entrySet()) {
                Integer rowIndex = contentEntry.getKey();
                Map<String, Object> objectMap = contentEntry.getValue();
                if (objectMap == null || objectMap.isEmpty()) {
                    continue;
                }
                Object objVal = objectMap.get(fieldName);
                if (ObjectUtils.isEmpty(objVal) || StringUtil.isBlank(objVal.toString())) {
                    continue;
                }
                String cellValue = objVal.toString().trim();
                if (replaceVals.contains(cellValue)) {
                    continue;
                }

                String message = buildReplaceErrorMessage(cellValue, fieldName, replaceVals);
                errorMap.merge(rowIndex, message, (oldVal, newVal) -> oldVal + newVal);
                errorFieldMap.computeIfAbsent(rowIndex, k -> new LinkedHashSet<>()).add(fieldName);
            }
        }
    }

    /**
     * 构建 replace 字段校验错误信息(中英双语, 与 ImportUtil 其他校验信息格式保持一致)
     *
     * @param cellValue   实际填写的值(错误值)
     * @param fieldName   字段名
     * @param replaceVals 允许的取值集合
     * @return 错误信息串
     */
    private static String buildReplaceErrorMessage(String cellValue, String fieldName, Set<String> replaceVals) {
        List<String> allowedValues = new ArrayList<>();
        if (replaceVals != null) {
            for (String value : replaceVals) {
                if (StringUtil.isBlank(value)) {
                    continue;
                }
                // 清理换行与引号, 便于在错误提示中展示
                String cleaned = value.replace("\n", " ").replace("\r", " ").replace("\"", "").trim();
                if (!allowedValues.contains(cleaned)) {
                    allowedValues.add(cleaned);
                }
            }
        }

        String allowedStr = String.join("、", allowedValues);
        return "The [" + cellValue + "] not in replace range of [" + fieldName
                + "]([" + cellValue + "]不在[" + fieldName + "]允许范围内, 允许值: " + allowedStr + ");";
    }

    public static class FieldDictMapping {
        /**
         * Excel 内容字段名, 如 systemTypeValue
         */
        private String fieldName;
        /**
         * 字典类型码, 如 xeq_system
         */
        private String dictType;
        /**
         * 回写 id 的字段名, 如 systemType
         */
        private String idFieldName;
        /**
         * 是否多值(分号分隔), 如根本原因
         */
        private boolean multiValue;
        /**
         * 是否启用子串模糊匹配(标签包含输入即命中), 如关键设备类型
         */
        private boolean fuzzy;

        /**
         * 无参构造
         */
        public FieldDictMapping() {
        }

        /**
         * 构造映射(默认非模糊匹配)
         *
         * @param fieldName   Excel 内容字段名
         * @param dictType    字典类型码
         * @param idFieldName 回写 id 的字段名
         * @param multiValue  是否多值(分号分隔)
         */
        public FieldDictMapping(String fieldName, String dictType, String idFieldName, boolean multiValue) {
            this(fieldName, dictType, idFieldName, multiValue, false);
        }

        /**
         * 构造映射
         *
         * @param fieldName   Excel 内容字段名
         * @param dictType    字典类型码
         * @param idFieldName 回写 id 的字段名
         * @param multiValue  是否多值(分号分隔)
         * @param fuzzy       是否启用子串模糊匹配
         */
        public FieldDictMapping(String fieldName, String dictType, String idFieldName, boolean multiValue, boolean fuzzy) {
            this.fieldName = fieldName;
            this.dictType = dictType;
            this.idFieldName = idFieldName;
            this.multiValue = multiValue;
            this.fuzzy = fuzzy;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getDictType() {
            return dictType;
        }

        public void setDictType(String dictType) {
            this.dictType = dictType;
        }

        public String getIdFieldName() {
            return idFieldName;
        }

        public void setIdFieldName(String idFieldName) {
            this.idFieldName = idFieldName;
        }

        public boolean isMultiValue() {
            return multiValue;
        }

        public void setMultiValue(boolean multiValue) {
            this.multiValue = multiValue;
        }

        public boolean isFuzzy() {
            return fuzzy;
        }

        public void setFuzzy(boolean fuzzy) {
            this.fuzzy = fuzzy;
        }
    }
}
