package org.kosiuk.webApp.servletPaymentsApp.util.sessionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class SessionLocalizationUtil {

    /**
     * Get locale from session set by LocalizationFilter
     */
    public static Locale getLocaleFromSession(HttpServletRequest request) {
        String lang = (String) request.getSession().getAttribute("lang");
        return lang != null ? Locale.forLanguageTag(lang) : Locale.getDefault();
    }

}
