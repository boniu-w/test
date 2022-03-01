package wg.application.component;

import wg.application.service.impl.AspectServiceImpl;

/*************************************************************
 * @Package wg.application.component
 * @author wg
 * @date 2020/6/18 14:51
 * @version
 * @Copyright
 *************************************************************/
public class DecipherPhone {

    AspectServiceImpl aspectService = new AspectServiceImpl();

    public String test(){
        String add = aspectService.add("97987");
        return add;
    }
}
