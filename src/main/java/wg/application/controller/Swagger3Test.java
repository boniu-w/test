package wg.application.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/************************************************************************
 * author: wg
 * description: Swagger3Test
 * createTime: 11:31 2022/12/29
 * updateTime: 11:31 2022/12/29
 ************************************************************************/
@RestController
@RequestMapping(value = "/swagger3test")
public class Swagger3Test {

    @GetMapping(value = "/test")
    @Parameters({
            @Parameter(name = "X-Auth-Token", in = ParameterIn.HEADER, required = true, description = "认证token"),
            @Parameter(name = "id", required = true, description = "id")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", required = true, value = "认证token")
    })
    public void test(@RequestParam Map<String, String> parameterMap) {
        parameterMap.forEach((k, v) -> System.out.println(k));
    }
}
