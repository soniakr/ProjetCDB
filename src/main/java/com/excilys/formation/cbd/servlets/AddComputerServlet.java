package com.excilys.formation.cbd.servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Computer;

/**
 * Servlet implementation class AddComputerServlet
 */
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Computer computer=new Computer(request.getParameter("computerName"),
				LocalDate.parse(request.getParameter("introduced")),
				LocalDate.parse(request.getParameter("discontinued")),
				Long.parseLong(request.getParameter("companyId")));
		
		
		doGet(request, response);
	}

}
