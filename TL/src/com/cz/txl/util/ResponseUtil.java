package com.cz.txl.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

//ͳһ����ҳ����н������Ĺ�����
public class ResponseUtil {
	public static void write(HttpServletResponse response,Object result) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();
		out.close();
	}
}
