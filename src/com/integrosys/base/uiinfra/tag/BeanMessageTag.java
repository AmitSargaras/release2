package com.integrosys.base.uiinfra.tag;

import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

import com.integrosys.base.uiinfra.common.MessageResourceUtils;

public class BeanMessageTag extends org.apache.struts.taglib.bean.MessageTag {

    /**
     * Process the start tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        String key = this.key;

        if (key == null) {
            // Look up the requested property value
            Object value =
                TagUtils.getInstance().lookup(pageContext, name, property, scope);

            if ((value != null) && !(value instanceof String)) {
                JspException e =
                    new JspException(messages.getMessage("message.property", key));

                TagUtils.getInstance().saveException(pageContext, e);
                throw e;
            }

            key = (String) value;
        }

        // Construct the optional arguments array we will be using
        Object[] args = new Object[] { arg0, arg1, arg2, arg3, arg4 };
        
        String message = MessageResourceUtils.getInstance().getMessage(args, key, bundle, pageContext,localeKey);
        
        if (message == null) {
            Locale locale =
                TagUtils.getInstance().getUserLocale(pageContext, this.localeKey);
            String localeVal =
                (locale == null) ? "default locale" : locale.toString();
            JspException e =
                new JspException(messages.getMessage("message.message",
                        "\"" + key + "\"",
                        "\"" + ((bundle == null) ? "(default bundle)" : bundle)
                        + "\"", localeVal));

            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        TagUtils.getInstance().write(pageContext, message);

        return (SKIP_BODY);        
    }
    

    
    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
        arg0 = null;
        arg1 = null;
        arg2 = null;
        arg3 = null;
        arg4 = null;
        bundle = null;
        key = null;
        name = null;
        property = null;
        scope = null;
        localeKey = Globals.LOCALE_KEY;
    }    
}
