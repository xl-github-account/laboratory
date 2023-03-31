package priv.xl.springboot.reactor.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.xl.springboot.core.web.http.HttpResult;

/**
 * Description:
 * Email: xulei@voxelume.com
 *
 * @author lei.xu
 * @since 2023/2/15 1:29 下午
 * © Copyright 川谷汇（北京）数字科技有限公司 Corporation All Rights Reserved.
 */
@Api(tags = "测试接口")
@RestController
@RequestMapping("/reactor/test")
public class TestController {

    @GetMapping("/future")
    public HttpResult test01() {
        return HttpResult.success();
    }

}
