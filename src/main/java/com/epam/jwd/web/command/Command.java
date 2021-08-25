package com.epam.jwd.web.command;

/**
 * Interface of servlet command
 */
public interface Command {

    /**
     * Method is called by servlet when processing request
     *
     * @param request servlet request object.
     * @return result of command execution containing page path and routing type.
     */
    CommandResponse execute(CommandRequest request);

    /**
     * return servlet command matches name
     *
     * @param name name of servlet command
     * @return the command matches the name
     */
    static Command ofName(String name) {
        return AppCommand.of(name).getCommand();
    }

}
