package com.navercorp.pinpoint.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.navercorp.pinpoint.web.service.BusinessLogService;
/**
 * Created by Administrator on 2017/6/12.
 */
@Controller
public class BusinessLogController {

    @Autowired
    private BusinessLogService businessLogService;
    @RequestMapping(value = "/getBusinessLog")
    @ResponseBody
    public String GetBusinessLog(@RequestParam(value= "agentId",required=true) String agentId,
                   @RequestParam (value= "transactionId", required=true) String transactionId,
                   @RequestParam(value= "spanId" , required=false) String spanId,
                   @RequestParam(value="time" , required=true) long time ) {

        // you should implement the logic to retrieve your agent’s logs.
        String str = businessLogService.getBusinessLog(agentId,transactionId,spanId,time);
        return str;
    }
}
