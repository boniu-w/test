package wg.application.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "liushui")
@Entity
@Data
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


}
