package com.doris.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * desc:
 * author: ailinxi
 * date: 2018/9/11.
 */
@WebServlet(name="download",urlPatterns = "/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = req.getParameter("name");
        System.out.println();
        //!!!!!!!!!!!!!!!!!!!!这不是普通的文件拷贝，而是通知浏览器去下载，因此，需要设置contenttype
        String minetype = this.getServletContext().getMimeType(filename);
        resp.setContentType(minetype);
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-disposition","attachment;filename="+filename);
        //!!!!!!!!!!!!!!!!
        //获取服务器文件所在的绝对路径
        String realPath = this.getServletContext().getRealPath("/document/"+filename);
       //根据绝对路径才能创建File文件
        File file = new File(realPath);
        //读取文件，写入response中
        InputStream in = new FileInputStream(file);
        int i=-1;
        while ((i=in.read())!=-1){
            resp.getOutputStream().write(i);
        }
        in.close();
    }
}
