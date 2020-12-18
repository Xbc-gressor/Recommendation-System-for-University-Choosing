package com.ursys.web;

import com.alibaba.fastjson.JSON;
import com.ursys.myClass.MajorToSchool;
import com.ursys.myClass.SchoolToMajor;
import com.ursys.myClass.ScorelineYearScore;
import com.ursys.tool.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@WebServlet("/schoolToMajor")
public class SchoolToMajorServlet extends HttpServlet{
    private static final long serialVersionUID = 2L;

    // 年份文件层，一个文件中是所有的专业信息
    private static List<SchoolToMajor.Major> AppendYear(List<SchoolToMajor> list, int province, int type, String subFilepath, int year){
        String yearFilepath = subFilepath + "\\" + year + ".txt";
        File file = new File(yearFilepath);
        List<SchoolToMajor.Major> majorList = null;
        if (file.exists()) {
            // 文件存在，构造专业列表
            majorList = new ArrayList<SchoolToMajor.Major>();
            // 读取的文件中的所有内容
            Vector<String> fileContent = Tool.readTxtFile(yearFilepath);
            int ranking = 1;
            for (String text: fileContent){
                // 获取单项信息
                String[] inf = text.split(" ");
                String majorName = inf[0];
                String averageScore = inf[1].replace(".0", "");
                String maxScore = inf[2].replace(".0", "");
                // 向专业列表中添加元素
                majorList.add(new SchoolToMajor.Major(ranking++, majorName, averageScore, maxScore));
            }
        }
        return majorList;
    }

    // 文理科层，添加文理科数据，构造SchoolToMajor列表
    private static void AppendSub(List<SchoolToMajor> list, int province, String provinceFilepath, String type){
        String subFilepath = provinceFilepath + "\\" + type;
        File file = new File(subFilepath);
        // 传参为了构造SchoolToMajor对象
        int intType = ("理科".equals(type))? 1:2;
        if (file.exists()){
            for (int year = 2015; year < 2018; year++) {
                // 专业信息列表
                List<SchoolToMajor.Major> majorList = AppendYear(list, province, intType, subFilepath, year);
                if (majorList != null) // 说明文件存在
                    list.add(new SchoolToMajor(year, province, intType, majorList));
            }
        }

    }

    // 省份层传上去这一层的路径和下一层可能的路径添加
    private List<SchoolToMajor> BuildSchoolList(String filePath) throws IOException {
        List<SchoolToMajor> list = new ArrayList<>();

        for(int i = 1; i <= 33; i ++){
            String provinceFilePath = filePath + "\\" + i;
            File file = new File(provinceFilePath);
            if(file.exists()){
                // 添加理科
                AppendSub(list, i, provinceFilePath, "理科");
                // 添加文科
                AppendSub(list, i, provinceFilePath, "文科");
            }else {
                System.out.println(provinceFilePath);
            }
        }

        return list;
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SchoolToMajorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // 获取参数
        String param = request.getParameter("schoolname");

        // 转换成JSON
        System.out.println("查询学校: " + param);
        String dataPath = "E:\\大数据实训\\Admissions\\Admissions\\data";
        String filePath = dataPath + "\\学校查专业\\" + param;
        System.out.println(filePath);
        List<SchoolToMajor> scoreList = BuildSchoolList(filePath);
        System.out.println(scoreList.size());
        String mapJson = JSON.toJSONString(scoreList);
//		System.out.println(mapJson);

        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(mapJson);
    }
}
