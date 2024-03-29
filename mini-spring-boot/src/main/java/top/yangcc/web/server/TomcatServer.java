package top.yangcc.web.server;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import top.yangcc.web.servlet.DispatcherServlet;

/**
 * 包装tomcat
 * 1 启动tomcat
 * 2 设置自己的Servlet
 * 3 开一个守护线程，让tomcat等待
 */
@SuppressWarnings("unused")
public class TomcatServer {

    private Tomcat tomcat;

    public void start() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.getConnector().setURIEncoding("UTF-8");
        //TODO 可配置
        tomcat.setPort(8080);
        tomcat.start();

        // new 一个标准的 context 容器并设置访问路径；
        // 同时为 context 设置生命周期监听器。
        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        // 新建一个 DispatcherServlet 对象，这个是我们自己写的 Servlet 接口的实现类，
        // 然后使用 `Tomcat.addServlet()` 方法为 context 设置指定名字的 Servlet 对象，
        // 并设置为支持异步。
        DispatcherServlet servlet = new DispatcherServlet();
        Tomcat.addServlet(context, "dispatcherServlet", servlet)
                .setAsyncSupported(true);


        context.addServletMappingDecoded("/", "dispatcherServlet");

        tomcat.getHost().addChild(context);


        // Tomcat 所有的线程都是守护线程，
        // 如果某一时刻所有的线程都是守护线程，那 JVM 会退出，
        // 因此，需要为 tomcat 新建一个非守护线程来保持存活，
        // 避免服务到这就 shutdown 了
        Thread tomcatAwaitThread = new Thread("tomcat_await_thread") {
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        tomcatAwaitThread.setDaemon(false);
        tomcatAwaitThread.start();

    }
}
