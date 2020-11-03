package wg.application.service.impl;

import org.springframework.stereotype.Service;
import wg.application.service.TestInterface;

/*************************************************************
 * @Package wg.application.service.impl
 * @author wg
 * @date 2020/6/22 9:45
 * @version
 * @Copyright
 *************************************************************/
@Service(value = "testImpl_1")
public class TestImpl_1 implements TestInterface {
    @Override
    public String getname() {
        return "1";
    }
}
