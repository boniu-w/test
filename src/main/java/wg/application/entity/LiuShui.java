package wg.application.entity;


import lombok.Data;

@Data
public class LiuShui {

  private long id;

  private String cardNo;
  private String jiaoYiHuMing;
  private String jiaoYiZhangHao;
  private java.sql.Timestamp jiaoYiShiJian;
  private String shouFuBiaoZhi;
  private double jiaoYiJinEr;
//  private double jiaoYiJinE;
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


  public double getJiaoYiJinEr() {
    return jiaoYiJinEr;
  }

  public void setJiaoYiJinEr(double jiao_yi_jin_e) {
    this.jiaoYiJinEr = jiao_yi_jin_e;
  }
}
