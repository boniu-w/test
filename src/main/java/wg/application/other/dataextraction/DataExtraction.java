package wg.application.other.dataextraction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/************************************************************************
 * @author: wg
 * @description: 数据扒取
 * @createTime: 14:24 2022/11/15
 * @updateTime: 14:24 2022/11/15
 ************************************************************************/
public class DataExtraction {
    public static void main(String[] args) throws Exception {
        //第1页地址
        String url = "https://search.jd.com/Search?keyword=手机&wq=手机&page=1";
        //发送http请求
        Document document = Jsoup.connect(url).get();
        //在id=J_goodsList的div下，获取所有带有data-sku属性的li标签
        Elements lis = document.select("div[id=J_goodsList] li[data-sku]");
        lis.forEach(li -> {
            //获取商品sku
            String sku = li.attr("data-sku");
            //获取商品name
            String name = li.select("div[class='p-name p-name-type-2'] a em").text();
            //获取商品图片地址
            String img = li.select("div[class=p-img] a img[data-lazy-img]").attr("data-lazy-img");
            System.out.println(String.format("%s, %s, %s", sku, name, img));
        });
    }
}
