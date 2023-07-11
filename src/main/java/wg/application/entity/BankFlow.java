package wg.application.entity;

//import com.baomidou.mybatisplus.annotation.TableName;

import wg.application.excel.annotation.Excel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

//@TableName(value = "BANK_STATEMENT")
public class BankFlow implements Serializable {
    private static final Long serialVersionUID = 1L;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getIntroductionId() {
        return introductionId;
    }

    public void setIntroductionId(String introductionId) {
        this.introductionId = introductionId;
    }

    public String getTransactionSubject() {
        return transactionSubject;
    }

    public void setTransactionSubject(String transactionSubject) {
        this.transactionSubject = transactionSubject;
    }

    public String getAccountSubject() {
        return accountSubject;
    }

    public void setAccountSubject(String accountSubject) {
        this.accountSubject = accountSubject;
    }

    public String getCardEntity() {
        return cardEntity;
    }

    public void setCardEntity(String cardEntity) {
        this.cardEntity = cardEntity;
    }

    public String getRecoveryMark() {
        return recoveryMark;
    }

    public void setRecoveryMark(String recoveryMark) {
        this.recoveryMark = recoveryMark;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCounterParty() {
        return counterParty;
    }

    public void setCounterParty(String counterParty) {
        this.counterParty = counterParty;
    }

    public String getAccountCounterparty() {
        return accountCounterparty;
    }

    public void setAccountCounterparty(String accountCounterparty) {
        this.accountCounterparty = accountCounterparty;
    }

    public String getCardCounterparty() {
        return cardCounterparty;
    }

    public void setCardCounterparty(String cardCounterparty) {
        this.cardCounterparty = cardCounterparty;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getAbstractContent() {
        return abstractContent;
    }

    public void setAbstractContent(String abstractContent) {
        this.abstractContent = abstractContent;
    }

    public float getBalanceTransaction() {
        return balanceTransaction;
    }

    public void setBalanceTransaction(float balanceTransaction) {
        this.balanceTransaction = balanceTransaction;
    }

    public String getTransactionBank() {
        return transactionBank;
    }

    public void setTransactionBank(String transactionBank) {
        this.transactionBank = transactionBank;
    }

    public String getCounterpartyBank() {
        return counterpartyBank;
    }

    public void setCounterpartyBank(String counterpartyBank) {
        this.counterpartyBank = counterpartyBank;
    }

    public String getPlaceTransaction() {
        return placeTransaction;
    }

    public void setPlaceTransaction(String placeTransaction) {
        this.placeTransaction = placeTransaction;
    }

    public String getTradingPlace() {
        return tradingPlace;
    }

    public void setTradingPlace(String tradingPlace) {
        this.tradingPlace = tradingPlace;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTemarks() {
        return temarks;
    }

    public void setTemarks(String temarks) {
        this.temarks = temarks;
    }

    public String getTradingNo() {
        return tradingNo;
    }

    public void setTradingNo(String tradingNo) {
        this.tradingNo = tradingNo;
    }

    public String getTellerNumber() {
        return tellerNumber;
    }

    public void setTellerNumber(String tellerNumber) {
        this.tellerNumber = tellerNumber;
    }

    public String getInstitutionParty() {
        return institutionParty;
    }

    public void setInstitutionParty(String institutionParty) {
        this.institutionParty = institutionParty;
    }

    public Integer getDeleteIdentifier() {
        return deleteIdentifier;
    }

    public void setDeleteIdentifier(Integer deleteIdentifier) {
        this.deleteIdentifier = deleteIdentifier;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getLogNumber() {
        return logNumber;
    }

    public void setLogNumber(String logNumber) {
        this.logNumber = logNumber;
    }

    public String getApshPlace() {
        return apshPlace;
    }

    public void setApshPlace(String apshPlace) {
        this.apshPlace = apshPlace;
    }

    public String getMatcherCode() {
        return matcherCode;
    }

    public void setMatcherCode(String matcherCode) {
        this.matcherCode = matcherCode;
    }

    public String getMatcherBalance() {
        return matcherBalance;
    }

    public void setMatcherBalance(String matcherBalance) {
        this.matcherBalance = matcherBalance;
    }

    public String getSubjectCredentials() {
        return subjectCredentials;
    }

    public void setSubjectCredentials(String subjectCredentials) {
        this.subjectCredentials = subjectCredentials;
    }

    public String getAdversaryCredentials() {
        return adversaryCredentials;
    }

    public void setAdversaryCredentials(String adversaryCredentials) {
        this.adversaryCredentials = adversaryCredentials;
    }

    public String getTrancationRecordsId() {
        return trancationRecordsId;
    }

    public void setTrancationRecordsId(String trancationRecordsId) {
        this.trancationRecordsId = trancationRecordsId;
    }

    public String getReportOrganization() {
        return reportOrganization;
    }

    public void setReportOrganization(String reportOrganization) {
        this.reportOrganization = reportOrganization;
    }

    public String getSheWaiFenLei() {
        return sheWaiFenLei;
    }

    public void setSheWaiFenLei(String sheWaiFenLei) {
        this.sheWaiFenLei = sheWaiFenLei;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentCredentials() {
        return agentCredentials;
    }

    public void setAgentCredentials(String agentCredentials) {
        this.agentCredentials = agentCredentials;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getTick() {
        return tick;
    }

    public void setTick(String tick) {
        this.tick = tick;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caseId, introductionId, transactionSubject, accountSubject, cardEntity, recoveryMark, transactionDate, counterParty, accountCounterparty, cardCounterparty, transactionAmount, abstractContent, balanceTransaction, transactionBank, counterpartyBank, placeTransaction, tradingPlace, transactionNumber, mac, ip, currency, temarks, tradingNo, tellerNumber, institutionParty, deleteIdentifier, customerCode, logNumber, apshPlace, matcherCode, matcherBalance, subjectCredentials, adversaryCredentials, trancationRecordsId, reportOrganization, sheWaiFenLei, agentName, agentCredentials, voucherNumber, voucherType, excelName);
    }
}
