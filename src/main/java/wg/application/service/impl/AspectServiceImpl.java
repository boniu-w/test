package wg.application.service.impl;

import org.springframework.stereotype.Service;
import wg.application.service.AspectService;

/**
 * @author wg
 * @Package wg.application.service.impl
 * @date 2020/4/28 13:44
 * @Copyright
 */
@Service
public class AspectServiceImpl implements AspectService {


    @Override
    public String add(String userName) {

        System.out.println("AspectServiceImpl userName: "+userName);
        return "\\\\";
    }
}
