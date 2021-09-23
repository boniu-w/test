package wg.application.entity;

//import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import wg.application.util.Excel;

import java.io.Serializable;
import java.util.Objects;

//@TableName(value = "BANK_STATEMENT")
@Data
public class BankFlow implements Serializable {

    @Excel(name = "id")
    private String id;

    @Excel(name = "案件id")
    private String caseId;

    @Excel(name = "导入人id", width = 15)
    private String introductionId;

    @Excel(name = "交易主体", width = 15)
    private String transactionSubject;

    @Excel(name = "交易主体账号")
    private String accountSubject;

    @Excel(name = "交易主体卡号")
    private String cardEntity;

    @Excel(name = "收付标志")
    private String recoveryMark;

    @Excel(name = "交易时间")
    private java.sql.Timestamp transactionDate;

    @Excel(name = "交易对手")
    private String counterParty;

    @Excel(name = "交易对手账号")
    private String accountCounterparty;

    @Excel(name = "交易主体卡号")
    private String cardCounterparty;

    @Excel(name = "交易金额", width = 15)
    private float transactionAmount;

    @Excel(name = "摘要")
    private String abstractContent;

    @Excel(name = "交易后余额")
    private float balanceTransaction;

    @Excel(name = "交易主体归属行")
    private String transactionBank;

    @Excel(name = "交易对手归属行")
    private String counterpartyBank;

    @Excel(name = "交易地点")
    private String placeTransaction;

    @Excel(name = "交易方式")
    private String tradingPlace;

    @Excel(name = "交易流水号")
    private String transactionNumber;

    @Excel(name = "mac地址")
    private String mac;

    @Excel(name = "ip地址")
    private String ip;

    @Excel(name = "币种")
    private String currency;

    @Excel(name = "备注")
    private String temarks;

    @Excel(name = "交易机构号")
    private String tradingNo;

    @Excel(name = "柜员号")
    private String tellerNumber;

    @Excel(name = "对方机构号")
    private String institutionParty;

    private Integer deleteIdentifier;

    @Excel(name = "客户代码")
    private String customerCode;

    @Excel(name = "日志号")
    private String logNumber;

    @Excel(name = "apsh地点")
    private String apshPlace;

    @Excel(name = "交易对手客户代码")
    private String matcherCode;

    @Excel(name = "对手交易后余额")
    private String matcherBalance;

    @Excel(name = "交易主体证件号")
    private String subjectCredentials;

    @Excel(name = "交易对手证件号")
    private String adversaryCredentials;

    @Excel(name = "交易记录id")
    private String trancationRecordsId;

    @Excel(name = "报告机构")
    private String reportOrganization;

    @Excel(name = "涉外收支分类")
    private String sheWaiFenLei;

    @Excel(name = "代办人名称")
    private String agentName;

    @Excel(name = "代办人证件号")
    private String agentCredentials;

    @Excel(name = "凭证号码")
    private String voucherNumber;

    @Excel(name = "凭证类型")
    private String voucherType;

    private String excelName;

    private String tick;

    private String birthday;

    @Override
    public int hashCode() {
        return Objects.hash(id, caseId, introductionId, transactionSubject, accountSubject, cardEntity, recoveryMark, transactionDate, counterParty, accountCounterparty, cardCounterparty, transactionAmount, abstractContent, balanceTransaction, transactionBank, counterpartyBank, placeTransaction, tradingPlace, transactionNumber, mac, ip, currency, temarks, tradingNo, tellerNumber, institutionParty, deleteIdentifier, customerCode, logNumber, apshPlace, matcherCode, matcherBalance, subjectCredentials, adversaryCredentials, trancationRecordsId, reportOrganization, sheWaiFenLei, agentName, agentCredentials, voucherNumber, voucherType, excelName);
    }
}
