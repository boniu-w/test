package wg.application.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "liushui")
@Entity
public class LiuShui {

  @Id
  private long id;

  private String cardNo;
  private String jiaoYiHuMing;
  private String jiaoYiZhangHao;
  private java.sql.Timestamp jiaoYiShiJian;
  private String shouFuBiaoZhi;
  private double jiao_yi_jin_e;
  private double yu_e;
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


  public double getJiao_yi_jin_e() {
    return jiao_yi_jin_e;
  }

  public void setJiao_yi_jin_e(double jiao_yi_jin_e) {
    this.jiao_yi_jin_e = jiao_yi_jin_e;
  }

  public double getYu_e() {
    return yu_e;
  }

  public void setYu_e(double yu_e) {
    this.yu_e = yu_e;
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

  @Override
  public String toString() {
    return "LiuShui{" +
      "id=" + id +
      ", cardNo='" + cardNo + '\'' +
      ", jiaoYiHuMing='" + jiaoYiHuMing + '\'' +
      ", jiaoYiZhangHao='" + jiaoYiZhangHao + '\'' +
      ", jiaoYiShiJian=" + jiaoYiShiJian +
      ", shouFuBiaoZhi='" + shouFuBiaoZhi + '\'' +
      ", jiao_yi_jin_e=" + jiao_yi_jin_e +
      ", yu_e=" + yu_e +
      ", duiShouZhangHao='" + duiShouZhangHao + '\'' +
      ", duiShouHuMing='" + duiShouHuMing + '\'' +
      ", duiShouKaiHuHang='" + duiShouKaiHuHang + '\'' +
      ", zhaiYao='" + zhaiYao + '\'' +
      ", biZhong='" + biZhong + '\'' +
      ", jiaoYiWangDian='" + jiaoYiWangDian + '\'' +
      ", jiaoYiChangSuo='" + jiaoYiChangSuo + '\'' +
      ", jiaoYiFaShengDi='" + jiaoYiFaShengDi + '\'' +
      ", jiaoYiShiFouChengGong='" + jiaoYiShiFouChengGong + '\'' +
      ", ip='" + ip + '\'' +
      ", mac='" + mac + '\'' +
      ", beiZhu='" + beiZhu + '\'' +
      '}';
  }
}
