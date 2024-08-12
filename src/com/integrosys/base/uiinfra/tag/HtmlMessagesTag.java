package com.integrosys.base.uiinfra.tag;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;
import javax.servlet.jsp.JspException;
import com.integrosys.base.uiinfra.common.MessageResourceUtils;

public class HtmlMessagesTag extends org.apache.struts.taglib.html.MessagesTag {
    /**
     * Construct an iterator for the specified collection, and begin looping
     * through the body once per element.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        // Initialize for a new request.
        processed = false;

        // Were any messages specified?
        ActionMessages messages = null;

        // Make a local copy of the name attribute that we can modify.
        String name = this.name;

        if ((message != null) && "true".equalsIgnoreCase(message)) {
            name = Globals.MESSAGE_KEY;
        }

        try {
            messages =
                TagUtils.getInstance().getActionMessages(pageContext, name);
        } catch (JspException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        // Acquire the collection we are going to iterate over
        this.iterator =
            (property == null) ? messages.get() : messages.get(property);

        // Store the first value and evaluate, or skip the body if none
        if (!this.iterator.hasNext()) {
            return SKIP_BODY;
        }

        // process the first message
        processMessage((ActionMessage) iterator.next());

        if ((header != null) && (header.length() > 0)) {
            String headerMessage =
            	MessageResourceUtils.getInstance().getMessage(
            			header, bundle, pageContext, locale);

            if (headerMessage != null) {
                TagUtils.getInstance().write(pageContext, headerMessage);
            }
        }

        // Set the processed variable to true so the
        // doEndTag() knows processing took place
        processed = true;

        return (EVAL_BODY_TAG);
    }
    
    /**
     * Process a message.
     */
    private void processMessage(ActionMessage report)
        throws JspException {
        String msg = null;

        if (report.isResource()) {
            msg = MessageResourceUtils.getInstance().getMessage(
            		report.getValues(), report.getKey(), bundle, pageContext, locale);

            if (msg == null) {
                String bundleName = (bundle == null) ? "default" : bundle;

                msg = messageResources.getMessage("messagesTag.notfound",
                        report.getKey(), bundleName);
            }
        } else {
            msg = report.getKey();
        }

        if (msg == null) {
            pageContext.removeAttribute(id);
        } else {
            pageContext.setAttribute(id, msg);
        }
    }  
    
    /**
     * Clean up after processing this enumeration.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
        if (processed && (footer != null) && (footer.length() > 0)) {
            String footerMessage =
            	MessageResourceUtils.getInstance().getMessage(
            			footer, bundle, pageContext, locale);            	

            if (footerMessage != null) {
                TagUtils.getInstance().write(pageContext, footerMessage);
            }
        }

        return EVAL_PAGE;
    }
    
}
