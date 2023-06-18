package top.yangcc.web.servlet;

import jakarta.servlet.*;

import java.io.IOException;

public class DispatcherServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        //TODO 在这里路由
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        //TODO 在这里放入关闭操作


    }
}
