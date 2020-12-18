package com.ursys.web;

import com.ursys.tool.Tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BrochureServlet
 */
@WebServlet("/brochure")
public class BrochureServlet extends HttpServlet {
	private static final long serialVersionUID = 4L;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrochureServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.println("hello world!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		String schoolname = request.getParameter("schoolname");
		String dataPath = "E:\\大数据实训\\Admissions\\Admissions\\data";
		String filePath = dataPath + "\\招生简章.txt";
		Vector<String> brochure = Tool.readTxtFile(filePath);
		//System.out.println(schoolname);
		for(int i = 0; i < brochure.size(); i++){
			String[] tmp_brochure = brochure.get(i).split("\t");
			String tmp_schoolname = tmp_brochure[0].replace("(", "（").replace(")", "）");
			if(tmp_schoolname.equals(schoolname)){
				System.out.println(tmp_brochure[1]);

				PrintWriter out = response.getWriter();
				out.write(tmp_brochure[1].replace("<br></br><br></br>", "<br></br>"));
				return;
			}
		}
		PrintWriter out = response.getWriter();
		out.write("None");
	}

}
