package com.ursys.web;

import com.alibaba.fastjson.JSON;
import com.ursys.myClass.Recommend;
import com.ursys.myClass.ScorelineYearScore;
import com.ursys.tool.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

@WebServlet("/recommend")
public class RecommendServlet extends HttpServlet{
    private static final long serialVersionUID = 5L;

    private Recommend.SomeScore BuildSomeScore(int score, String filePath){
        Vector<String> stringList = Tool.readTxtFile(filePath);
        List<Recommend.SomeScore.infOfScore> infList = new ArrayList<>();
        for(String s: stringList){
            String[] infs = s.split(" ");
            infList.add(new Recommend.SomeScore.infOfScore(infs[0], infs[1], infs[2]));
        }

        // 只剩下ranking没设置
        Recommend.SomeScore someScore = new Recommend.SomeScore();
        someScore.setScore(score);
        someScore.setInfList(infList);
        someScore.setNum(infList.size());

        return someScore;
    }

    private List<Recommend.SomeScore> BuildScoreList(int score, String dirPath, String appendPath) {
        List<Recommend.SomeScore> scoreList = new ArrayList<>();
        int sNum = 3, beNum = 4;
        int min = 162, max = 905;
        List<Recommend.SomeScore> sList = new ArrayList<>();
        List<Recommend.SomeScore> beList = new ArrayList<>();
        int i = score - 1, j = score;
        // 试探小于的
        while (i >= min && sNum > 0){
            String filePath = dirPath + i + appendPath;
            File file = new File(filePath);
            if (file.exists()){
                sList.add(BuildSomeScore(i, filePath));
                sNum -= 1;
            }
            i -= 1;
        }
        // 试探大于的
        while (j <= max && beNum > 0){
            String filePath = dirPath + j + appendPath;
            File file = new File(filePath);
            if (file.exists()){
                beList.add(BuildSomeScore(j, filePath));
                beNum -= 1;
            }
            j += 1;
        }
        // 小的还不够，拿大的补
        while (j <= max && sNum > 0){
            String filePath = dirPath + j + appendPath;
            File file = new File(filePath);
            if (file.exists()){
                beList.add(BuildSomeScore(j, filePath));
                sNum -= 1;
            }
            j += 1;
        }

        // 大的还不够，拿小的补
        while (i >= min && beNum > 0){
            String filePath = dirPath + i + appendPath;
            File file = new File(filePath);
            if (file.exists()){
                sList.add(BuildSomeScore(i, filePath));
                beNum -= 1;
            }
            i -= 1;
        }

        int ranking = 1;
        for (int k = beList.size()-1; k >= 0; k--) {
            beList.get(k).setRanking(ranking++);
            scoreList.add(beList.get(k));
        }
        for (Recommend.SomeScore someScore : sList) {
            someScore.setRanking(ranking++);
            scoreList.add(someScore);
        }

        return scoreList;
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendServlet() {
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
        // 获取参数，成绩、省份、文理科
        System.out.println("aaaaa");
        String province = request.getParameter("province");
        String type = request.getParameter("type");
        String score = request.getParameter("score");

        // 转换成JSON
        System.out.printf("省份: %s 文理: %s 分数: %s\n", province, type, score);
        // 根目录
        String dataPath = "E:\\大数据实训\\Admissions\\Admissions\\data";
        String dirPath = dataPath + "\\志愿推荐\\";
        String appendPath = "\\" + province + "\\" + (("1".equals(type))?"理科": "文科") + ".txt";
        Recommend recommend = new Recommend();
        // 用正则来匹配，只允许出现数字
        String pattern = "[1-9]+[0-9]*";
        boolean isNumber = Pattern.matches(pattern, score);
        if (!isNumber && !"0".equals(score)){
            System.out.println("不对劲");
            returnJson(response, recommend, 101);
            return;
        }
        int intScore = Integer.parseInt(score);

        if ("8".equals(province)){ // 江苏不超过485
            if (intScore > 485){
                returnJson(response, recommend, 202);
                return;
            }
        }else if ("16".equals(province)){ // 海南不超过940
            if (intScore > 940){
                returnJson(response, recommend, 202);
                return;
            }
        }else if ("9".equals(province)){ // 上海不超过660
            if (intScore > 660){
                returnJson(response, recommend, 202);
                return;
            }
        }
        else if (intScore > 750){ // 全国卷不超过750
            returnJson(response, recommend, 202);
            return;
        }


        recommend.setScoreList(BuildScoreList(intScore, dirPath, appendPath));
        recommend.setYourScore(intScore);
        recommend.setProvince(Integer.parseInt(province));
        recommend.setType(Integer.parseInt(type));
        returnJson(response, recommend, 200);

    }

    private void returnJson(HttpServletResponse response, Recommend recommend, int status) throws IOException {
        // 设置状态
        recommend.setStatus(status);
        // 转化成json返回
        String mapJson = JSON.toJSONString(recommend);
        System.out.println(mapJson);
        response.setContentType("text/json;charset=utf-8");
        // 传回前端
        PrintWriter out = response.getWriter();
        out.write(mapJson);

    }

}
