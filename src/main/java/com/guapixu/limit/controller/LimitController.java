package com.guapixu.limit.controller;

import com.guapixu.limit.annotation.LimitCapacity;
import com.guapixu.limit.constant.ResultCode;
import com.guapixu.limit.pojo.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author lizx
 */
@RestController
@RequestMapping("/limit")
public class LimitController {


    @PostMapping("/limitCapacity")
    @LimitCapacity(time = 20L,timeUnit = TimeUnit.SECONDS,capacity = 5)
    public ResultVO limitCapacity(){
        return new ResultVO<>(ResultCode.SUCCESS);
    }
}
