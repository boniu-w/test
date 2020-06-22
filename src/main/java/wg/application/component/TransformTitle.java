package wg.application.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wg.application.service.AspectService;
import wg.application.service.impl.AspectServiceImpl;

/*************************************************************
 * @Package wg.application.component
 * @author wg
 * @date 2020/6/18 14:41
 * @version
 * @Copyright
 *************************************************************/
@Component
public class TransformTitle {

    @Autowired
    AspectService service;


    public void test(){
        service.add("////////");
    }
}
