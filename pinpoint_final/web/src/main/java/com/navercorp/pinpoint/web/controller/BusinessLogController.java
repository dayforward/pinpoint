package com.navercorp.pinpoint.web.controller;


import com.navercorp.pinpoint.web.service.BusinessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Created by Administrator on 2017/6/12.
 */
@Controller
public class BusinessLogController {

   @Autowired
    private BusinessLogService businessLogService;
    @RequestMapping(value = "/getBusinessLog")
    @ResponseBody
    public String GetBusinessLog(@RequestParam (value= "transactionId", required=true) String transactionId,
                   @RequestParam(value= "spanId" , required=false) String spanId,
                   @RequestParam(value="time" , required=true) long time ) {

        // you should implement the logic to retrieve your agent’s logs.
        return businessLogService.getBusinessLog(transactionId,spanId,time);
    }
}
