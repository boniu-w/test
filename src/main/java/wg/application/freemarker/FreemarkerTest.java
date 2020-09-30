package wg.application.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Version;
import wg.application.vo.Result;

import java.io.File;

/*************************************************************
 * @Package wg.application.freemarker
 * @author wg
 * @date 2020/9/30 17:01
 * @version
 * @Copyright
 *************************************************************/
public class FreemarkerTest {


    private static final String CLASS_PATH = "src\\main\\java\\wg\\application";

    public Result freemarkerTest1() {

        Configuration configuration = new Configuration();
        Version version2329 = Configuration.VERSION_2_3_29;

        //configuration.setDirectoryForTemplateLoading(new File(CLASS_PATH));

        return Result.ok();
    }


}
