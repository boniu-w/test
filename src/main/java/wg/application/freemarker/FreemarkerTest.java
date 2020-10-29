package wg.application.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/*************************************************************
 * @Package wg.application.freemarker
 * @author wg
 * @date 2020/9/30 17:01
 * @version
 * @Copyright
 *************************************************************/
//@RestController
//@RequestMapping(value = "/freemarkerTest")
public class FreemarkerTest {


    private static final String CLASS_PATH = "src\\main\\java\\wg\\application";

    //@RequestMapping(value = "/freemarkerTest1")
    public void freemarkerTest1() {

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);

        try {
            configuration.setDirectoryForTemplateLoading(new File("src\\main\\resources\\templates\\ftl"));

            HashMap<String, String> root = new HashMap<>();
            root.put("name","lilei");
            root.put("age","12");

            Template template = configuration.getTemplate("freemarker-test.html");

            OutputStreamWriter writer = new OutputStreamWriter(System.out);
            template.process(root,writer);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        //return Result.ok();
    }


}
