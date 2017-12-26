package com.mailsend;

import java.io.IOException;  
import java.io.PrintWriter;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
  
public class SendMail extends HttpServlet {  

	private static final long serialVersionUID = 1L;

public void doPost(HttpServletRequest request,  
 HttpServletResponse response)  
    throws ServletException, IOException {  
  System.out.println("enetered into servlet");
    response.setContentType("text/html");  
    PrintWriter out = response.getWriter();  
    String name=request.getParameter("name"); 
    String to=request.getParameter("to");  
    int no=Integer.parseInt(request.getParameter("no"));
    String companyName=request.getParameter("companyName");
    String subject=request.getParameter("subject");  
    String msg=request.getParameter("message");  
    Mailer.send(to, subject, msg,name,no,companyName);
   // out.print("message has been sent successfully");  
    request.getRequestDispatcher("/contact-us.html").forward(request, response);
    
    out.close();  
    }  
  
}  