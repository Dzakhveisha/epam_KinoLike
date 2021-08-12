package com.epam.jwd.web.command;

public class SimpleCommandResponse implements CommandResponse{

    private final String path;
    private final boolean isRedirect;

    public SimpleCommandResponse(String path, boolean isRedirect) {
        this.path = path;
        this.isRedirect = isRedirect;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean isRedirect() {
        return isRedirect;
    }
}
