package wg.application.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/************************************************************************
 * @author: wg
 * @description: 解析html
 * @createTime: 11:37 2022/4/22
 * @updateTime: 11:37 2022/4/22
 ************************************************************************/
public class JsoupTest {

    public void test1() {
        try {
            Connection connect = Jsoup.connect("http://www.baidu.com");
            Document document = connect.get();
            System.out.println(document.title());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
