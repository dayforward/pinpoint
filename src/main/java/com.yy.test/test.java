//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yy.test;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(
        name = "test",
        urlPatterns = {"/test"}
)
public class test extends HttpServlet {
    public static Logger logger = Logger.getLogger(test.class);
    public test() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        PrintWriter out = response.getWriter();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            out.print("key = " + entry.getKey() + " ; ");
            out.print("value = " );
            for (String value : entry.getValue()){
                out.print(value + " ");
            }
            out.println();
        }
        logger.debug("this is a debug message");
        logger.info("this is a info message");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }



}
