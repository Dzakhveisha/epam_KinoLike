package com.epam.jwd.web.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ErrorTag extends TagSupport {
    static final Logger LOGGER = LogManager.getRootLogger();

    private static final String ERROR_TEMPLATE = "<div class=\"errorBox\"><p> %s </p><br><a href=\" %s \"> %s </a> </div>";
    private static final String BACK_EN = "back";
    private static final String BACK_RU_BE = "назад";
    private static final String ERROR_RU = "Ошибка! ";
    private static final String ERROR_EN = "Error! ";
    private static final String ERROR_BE = "Памылка! ";

    private String errorText;
    private String link;

    @Override
    public int doStartTag() throws JspException {
        String language = String.valueOf(pageContext.getSession().getAttribute("language"));
        String backText;
        String errorStr;
        switch (language){
            case "en":
                backText = BACK_EN;
                errorStr = ERROR_EN;
                break;
            case "be":
                backText = BACK_RU_BE;
                errorStr = ERROR_BE;
                break;
            case "ru":
            default:
                backText = BACK_RU_BE;
                errorStr = ERROR_RU;
        }
        try {
            pageContext.getOut().write(
                    String.format(ERROR_TEMPLATE, errorStr+errorText, link, backText));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
