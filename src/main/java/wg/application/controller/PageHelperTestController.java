package wg.application.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2021/1/20 12:06
 * @version
 * @Copyright
 *************************************************************/
public class PageHelperTestController {

    public void test(){

        PageHelper.startPage(1,10);
        PageInfo<Object> pageInfo = new PageInfo<>();

    }


}
