package wg.application.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "liushui")
public class Liushui {

  private long id;
  private String cardNo;
  private String jiaoYiHuMing;
  private String jiaoYiZhangHao;
  private java.sql.Timestamp jiaoYiShiJian;
  private String shouFuBiaoZhi;
  private double jiaoYiJinE;
  private double yuE;
  private String duiShouZhangHao;
  private String duiShouHuMing;
  private String duiShouKaiHuHang;
  private String zhaiYao;
  private String biZhong;
  private String jiaoYiWangDian;
  private String jiaoYiChangSuo;
  private String jiaoYiFaShengDi;
  private String jiaoYiShiFouChengGong;
  private String ip;
  private String mac;
  private String beiZhu;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getCardNo() {
    return cardNo;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }


  public String getJiaoYiHuMing() {
    return jiaoYiHuMing;
  }

  public void setJiaoYiHuMing(String jiaoYiHuMing) {
    this.jiaoYiHuMing = jiaoYiHuMing;
  }


  public String getJiaoYiZhangHao() {
    return jiaoYiZhangHao;
  }

  public void setJiaoYiZhangHao(String jiaoYiZhangHao) {
    this.jiaoYiZhangHao = jiaoYiZhangHao;
  }


  public java.sql.Timestamp getJiaoYiShiJian() {
    return jiaoYiShiJian;
  }

  public void setJiaoYiShiJian(java.sql.Timestamp jiaoYiShiJian) {
    this.jiaoYiShiJian = jiaoYiShiJian;
  }


  public String getShouFuBiaoZhi() {
    return shouFuBiaoZhi;
  }

  public void setShouFuBiaoZhi(String shouFuBiaoZhi) {
    this.shouFuBiaoZhi = shouFuBiaoZhi;
  }


  public double getJiaoYiJinE() {
    return jiaoYiJinE;
  }

  public void setJiaoYiJinE(double jiaoYiJinE) {
    this.jiaoYiJinE = jiaoYiJinE;
  }


  public double getYuE() {
    return yuE;
  }

  public void setYuE(double yuE) {
    this.yuE = yuE;
  }


  public String getDuiShouZhangHao() {
    return duiShouZhangHao;
  }

  public void setDuiShouZhangHao(String duiShouZhangHao) {
    this.duiShouZhangHao = duiShouZhangHao;
  }


  public String getDuiShouHuMing() {
    return duiShouHuMing;
  }

  public void setDuiShouHuMing(String duiShouHuMing) {
    this.duiShouHuMing = duiShouHuMing;
  }


  public String getDuiShouKaiHuHang() {
    return duiShouKaiHuHang;
  }

  public void setDuiShouKaiHuHang(String duiShouKaiHuHang) {
    this.duiShouKaiHuHang = duiShouKaiHuHang;
  }


  public String getZhaiYao() {
    return zhaiYao;
  }

  public void setZhaiYao(String zhaiYao) {
    this.zhaiYao = zhaiYao;
  }


  public String getBiZhong() {
    return biZhong;
  }

  public void setBiZhong(String biZhong) {
    this.biZhong = biZhong;
  }


  public String getJiaoYiWangDian() {
    return jiaoYiWangDian;
  }

  public void setJiaoYiWangDian(String jiaoYiWangDian) {
    this.jiaoYiWangDian = jiaoYiWangDian;
  }


  public String getJiaoYiChangSuo() {
    return jiaoYiChangSuo;
  }

  public void setJiaoYiChangSuo(String jiaoYiChangSuo) {
    this.jiaoYiChangSuo = jiaoYiChangSuo;
  }


  public String getJiaoYiFaShengDi() {
    return jiaoYiFaShengDi;
  }

  public void setJiaoYiFaShengDi(String jiaoYiFaShengDi) {
    this.jiaoYiFaShengDi = jiaoYiFaShengDi;
  }


  public String getJiaoYiShiFouChengGong() {
    return jiaoYiShiFouChengGong;
  }

  public void setJiaoYiShiFouChengGong(String jiaoYiShiFouChengGong) {
    this.jiaoYiShiFouChengGong = jiaoYiShiFouChengGong;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }


  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }


  public String getBeiZhu() {
    return beiZhu;
  }

  public void setBeiZhu(String beiZhu) {
    this.beiZhu = beiZhu;
  }

}
