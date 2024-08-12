package com.integrosys.base.uiinfra.tag;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.MessageResourceUtils;


public class HtmlErrorsTag extends org.apache.struts.taglib.html.ErrorsTag {

    /**
     * Render the specified error messages if there are any.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        // Were any error messages specified?
        ActionMessages errors = null;

        try {
            errors =
                TagUtils.getInstance().getActionMessages(pageContext, name);
        } catch (JspException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        if ((errors == null) || errors.isEmpty()) {
            return (EVAL_BODY_INCLUDE);
        }
        
        String thisBundle = bundle;
        if (thisBundle == null)
        	thisBundle = PropertyManager.getValue("integro.message.resource.errors");        

        boolean headerPresent =
            MessageResourceUtils.getInstance().present(pageContext, thisBundle, locale,
                getHeader());

        boolean footerPresent =
        	MessageResourceUtils.getInstance().present(pageContext, thisBundle, locale,
                getFooter());

        boolean prefixPresent =
        	MessageResourceUtils.getInstance().present(pageContext, thisBundle, locale,
                getPrefix());

        boolean suffixPresent =
        	MessageResourceUtils.getInstance().present(pageContext, thisBundle, locale,
                getSuffix());

        // Render the error messages appropriately
        StringBuffer results = new StringBuffer();
        boolean headerDone = false;
        String message = null;
        Iterator reports =
            (property == null) ? errors.get() : errors.get(property);

        while (reports.hasNext()) {
            ActionMessage report = (ActionMessage) reports.next();

            if (!headerDone) {
                if (headerPresent) {
                    message =
                    	MessageResourceUtils.getInstance().getMessage(getHeader(), thisBundle,
                            pageContext, locale);

                    results.append(message);
                }

                headerDone = true;
            }

            if (prefixPresent) {
                message =
                	MessageResourceUtils.getInstance().getMessage(getPrefix(), thisBundle,
                            pageContext, locale);

                results.append(message);
            }

            if (report.isResource()) {
                message =
                	MessageResourceUtils.getInstance().getMessage(report.getValues(), report.getKey(), 
                			thisBundle, pageContext, locale);                	                
            } else {
                message = report.getKey();
            }

            if (message != null) {
                results.append(message);
            }

            if (suffixPresent) {
                message =
                	MessageResourceUtils.getInstance().getMessage(getSuffix(), thisBundle,
                            pageContext, locale);
                	
                results.append(message);
            }
        }

        if (headerDone && footerPresent) {
            message =
            	MessageResourceUtils.getInstance().getMessage(getFooter(), thisBundle,
                        pageContext, locale);
            	
            results.append(message);
        }

        TagUtils.getInstance().write(pageContext, results.toString());

        return (EVAL_BODY_INCLUDE);
    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
        bundle = null;
        locale = Globals.LOCALE_KEY;
        name = Globals.ERROR_KEY;
        property = null;
        header = null;
        footer = null;
        prefix = null;
        suffix = null;
    }
}
