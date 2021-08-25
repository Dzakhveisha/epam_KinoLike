package com.epam.jwd.web.filter;

import com.epam.jwd.web.command.AppCommand;
import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.model.UserRole;

import javax.management.relation.Role;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.epam.jwd.web.model.UserRole.UNAUTHORIZED;

/**
 *  Make command access checking
 */
@WebFilter(urlPatterns = "/controller")
public class RoleFilter implements Filter {

    private final Map<UserRole, Set<AppCommand>> commandsByRoles;

    public RoleFilter() {
        commandsByRoles = new EnumMap<>(UserRole.class);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        for (AppCommand command : AppCommand.values()) {
            for (UserRole allowedRole : command.getAvailableRoles()) {
                Set<AppCommand> commands = commandsByRoles.computeIfAbsent(allowedRole, k -> EnumSet.noneOf(AppCommand.class));
                commands.add(command);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        final AppCommand command = AppCommand.of(request.getParameter("command"));
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpSession session = req.getSession(false);
        UserRole curRole = session != null && session.getAttribute("role") != null
                ? (UserRole) session.getAttribute("role") : UNAUTHORIZED;
        final Set<AppCommand> allowedCommands = commandsByRoles.get(curRole);
        if (allowedCommands.contains(command)) {
            filterChain.doFilter(request,response);
        } else{
            ((HttpServletResponse)response).sendRedirect("/KinoLike/controller?command=error");
        }
    }

    @Override
    public void destroy() {

    }


}
