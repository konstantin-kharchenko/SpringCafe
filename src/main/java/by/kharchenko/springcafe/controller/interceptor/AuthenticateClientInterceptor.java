package by.kharchenko.springcafe.controller.interceptor;

import by.kharchenko.springcafe.model.entity.Role;
import by.kharchenko.springcafe.model.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;

/*
 * This interceptor needed to control url for client role
 */
public class AuthenticateClientInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");
        if (role == null) {
            response.sendRedirect("/home");
            return false;
        }
        if (role != Role.CLIENT) {
            BigInteger id = (BigInteger) session.getAttribute("id_user");
            response.sendRedirect("/admin/" + id.toString());
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
