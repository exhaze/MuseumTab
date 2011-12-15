package com.jes.museumtab.backend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;

public class ServletCreateExhibit extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().println("ServletCreateExhibit");
		super.doGet(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		
		System.out.println("Creating new exhibit ");
		
		String name = checkNull(req.getParameter("name"));
		String desc = checkNull(req.getParameter("desc"));
		
		Dao.INSTANCE.add(name, desc);
		
		resp.sendRedirect("/MuseumApp.jsp");
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}
