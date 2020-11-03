package wg.application.enumeration;


import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wg
 * @Package org.jeecg.modules.app.enumeration
 * @date 2020/4/16 9:51
 * @Copyright
 */
@Getter
public enum Title {


    TRANSACTION_SUBJECT("transaction_subject", "交易主体"),
    ACCOUNT_SUBJECT("account_subject", "交易主体账号"),
    CARD_ENTITY("card_entity", "交易主体卡号"),
    RECOVERY_MARK("recovery_mark", "收付标志"),
    TRANSACTION_DATE("transaction_date", "交易日期"),
    COUNTER_PARTY("counter_party", "交易对手"),
    ACCOUNT_COUNTERPARTY("account_counterparty", "交易对手账号"),
    CARD_COUNTERPARTY("card_counterparty", "交易对手卡号"),
    TRANSACTION_AMOUNT("transaction_amount", "交易金额"),
    ABSTRACT_CONTENT("abstract_content", "摘要"),
    BALANCE_TRANSACTION("balance_transaction", "交易后余额"),
    TRANSACTION_BANK("transaction_bank", "交易主体归属行"),
    COUNTERPARTY_BANK("counterparty_bank", "交易对手归属行"),
    PLACE_TRANSACTION("place_transaction", "交易地点"),
    TRADING_PLACE("trading_place", "交易方式"),
    TRANSACTION_NUMBER("transaction_number", "交易流水号"),
    MAC("mac", "MAC地址"),
    IP("ip", "IP地址"),
    CURRENCY("currency", "币种"),
    TEMARKS("temarks", "备注"),
    TRADING_NO("trading_no", "交易机构号"),
    TELLER_NUMBER("teller_number", "柜员号"),
    INSTITUTION_PARTY("institution_party", "对方机构号"),
    SUCCESSFUL_NOT("successful_not", "交易是否成功"),
    LOG_NUMBER("log_number", "日志号"),
    CUSTOMER_CODE("customer_code", "客户代码"),
    APSH_PLACE("apsh_place", "APSH地点线索"),
    MATCHER_CODE("matcher_code", "交易对手客户代码"),
    MATCHER_BALANCE("matcher_balance", "对手交易后余额"),
    SUBJECT_CREDENTIALS("subject_credentials", "交易主体证件号"),
    ADVERSARY_CREDENTIALS("adversary_credentials", "交易对手证件号"),
    TRANSACTION_RECORDS_ID("transaction_records_id", "交易记录ID"),
    REPORT_ORGANIZATION("report_organization", "报告机构"),
    SHE_WAI_FEN_LEI("she_wai_fen_lei", "涉外收支分类"),
    AGENT_NAME("agent_name", "代办人名称"),
    AGENT_CREDENTIALS("agent_credentials", "代办人证件号码"),
    VOUCHER_NUMBER("voucher_number", "凭证号码"),
    VOUCHER_TYPE("voucher_type", "凭证类型");


    private String fileCode;
    private String fileName;

    private static final Map<String, String> titleMap = new HashMap<>();

    static {
        for (Title title : EnumSet.allOf(Title.class)) {

            titleMap.put(title.getFileCode(), title.getFileName());
        }

    }


    Title(String fileCode, String fileName) {
        this.fileCode = fileCode;
        this.fileName = fileName;
    }

    //public String getFileCode() {
    //    return fileCode;
    //}
    //
    //
    //public String getFileName() {
    //    return fileName;
    //}


    public static Map<String, String> getTitleMap() {
        return titleMap;
    }
}
