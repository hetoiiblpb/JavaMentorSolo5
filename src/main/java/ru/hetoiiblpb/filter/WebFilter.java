package ru.hetoiiblpb.filter;

import ru.hetoiiblpb.model.User;
import ru.hetoiiblpb.util.DBHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@javax.servlet.annotation.WebFilter(urlPatterns = "/admin/*")
public class WebFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        if (session.getAttribute("userSession") == null) {
            resp.getWriter().println("Сначала авторизуйтесь!");
            try {
                System.out.println("Ждём 5 сек");
                System.out.println("не авотризован");
                Thread.sleep(5000L);
                response.sendRedirect("/login");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            User user = (User) session.getAttribute("userSession");
            String currentRole =user.getRole();
            switch (currentRole) {
                case ("admin"): {
                    System.out.println("сессия админа");
                    chain.doFilter(req, resp);
                    break;
                }
                case ("user"): {
                    System.out.println("сессия юзера");
                    resp.getWriter().println("Вам сюда нельзя!!");
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect("/login");
                    break;
                }
                default: {
                    req.setCharacterEncoding(DBHelper.getProperties().getProperty("characterEncoding"));
                    System.out.println("непонятная сессия");
                    resp.getWriter().println("Ваш ранг не очевиден!");
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect("/login");
                    break;
                }
            }
        }
    }


    @Override
    public void destroy() {

    }
}
