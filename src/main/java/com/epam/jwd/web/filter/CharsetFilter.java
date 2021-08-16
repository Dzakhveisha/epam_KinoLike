package com.epam.jwd.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter that sets right encoding so that every
 * character is shown in correct way.
 */
public class CharsetFilter implements Filter {

    private static final String CONTENT_TYPE_PARAMETER = "contentType";
    private static final String ENCODING_PARAMETER = "encoding";
    private String encoding;
    private String contentType;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(ENCODING_PARAMETER);
        contentType = filterConfig.getInitParameter(CONTENT_TYPE_PARAMETER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        response.setContentType(contentType);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
