package com.epam.jwd.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class LanguageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String lang = request.getParameter("lang");
        if (lang != null) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            StringBuilder requestString = new StringBuilder(request.getContextPath() + "/controller?");
            for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                String key = param.getKey();
                String[] value = param.getValue();
                if (!key.equals("lang")) {
                    requestString.append(key).append("=").append(value[0]).append("&");
                }
            }
            requestString.deleteCharAt(requestString.length() - 1);
            HttpSession session = request.getSession();
            session.setAttribute("language", lang);
            response.sendRedirect(requestString.toString());
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
