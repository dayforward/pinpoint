package com.navercorp.pinpoint.web.controller;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BusienssLogControllerTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-web.xml","servlet-context.xml");
		BusinessLogController businessLogController = context.getBean("businessLogController", BusinessLogController.class);
		businessLogController.GetBusinessLog("agentId", "testTransactionId", "testSpanId", 0);
	}
}
