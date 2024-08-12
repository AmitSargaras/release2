package com.integrosys.cms.ui.common.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.SelectTag;
import org.apache.struts.util.MessageResources;

import com.integrosys.base.uiinfra.common.MessageResourceUtils;

public class CommonOptionsHandlerTag extends TagSupport {
    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(Constants.Package
            + ".LocalStrings");
    	
	protected String labelPleaseSelect = "label.please.select";
	
	/**
	 * This is mandatory for if isDisplay is true
	 * The value 
	 */
	protected String value = null;
	
	/**
	 * The List to be excluded in the options list
	 */
	protected Collection excludeList = new ArrayList();
	
	/**
	 * Indicate whether this tag is use to display description or options list
	 */
	protected boolean display = false;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Collection getExcludeList() {
		return excludeList;
	}

	public void setExcludeList(Collection excludeList) {
		this.excludeList = excludeList;
	}

	public boolean getDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}
	
    /**
     * Should the label values be filtered for HTML sensitive characters?
     */
    protected boolean filter = true;
    
    /**
     * The style associated with this tag.
     */
    protected String style = null;
    
    /**
     * The named style class associated with this tag.
     */
    protected String styleClass = null;
    
    /**
     * Should include 'Please select' option?
     */
    protected boolean pleaseSelect = true;

	public boolean getFilter() {
		return filter;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public boolean getPleaseSelect() {
		return pleaseSelect;
	}

	public void setPleaseSelect(boolean pleaseSelect) {
		this.pleaseSelect = pleaseSelect;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}    
    
    /**
     * Add an option element to the specified StringBuffer based on the
     * specified parameters. <p> Note that this tag specifically does not
     * support the <code>styleId</code> tag attribute, which causes the HTML
     * <code>id</code> attribute to be emitted.  This is because the HTML
     * specification states that all "id" attributes in a document have to be
     * unique.  This tag will likely generate more than one
     * <code>option</code> element element, but it cannot use the same
     * <code>id</code> value.  It's conceivable some sort of mechanism to
     * supply an array of <code>id</code> values could be devised, but that
     * doesn't seem to be worth the trouble.
     *
     * @param sb      StringBuffer accumulating our results
     * @param value   Value to be returned to the server for this option
     * @param label   Value to be shown to the user for this option
     * @param matched Should this value be marked as selected?
     */
    protected void addOption(StringBuffer sb, String value, String label,
        boolean matched) {
        sb.append("<option value=\"");

        if (filter) {
            sb.append(TagUtils.getInstance().filter(value));
        } else {
            sb.append(value);
        }

        sb.append("\"");

        if (matched) {
            sb.append(" selected=\"selected\"");
        }

        if (style != null) {
            sb.append(" style=\"");
            sb.append(style);
            sb.append("\"");
        }

        if (styleClass != null) {
            sb.append(" class=\"");
            sb.append(styleClass);
            sb.append("\"");
        }

        sb.append(">");

        if (filter) {
            sb.append(TagUtils.getInstance().filter(label));
        } else {
            sb.append(label);
        }

        sb.append("</option>\r\n");
    }    
    
    protected void listOptions(Collection valueList, Collection labelList, 
    		StringBuffer sb) throws JspException {
    	
		// display the options of the currency list
        // Acquire the select tag we are associated with
        SelectTag selectTag =
            (SelectTag) pageContext.getAttribute(Constants.SELECT_KEY);

        if (selectTag == null) {
            throw new JspException(messages.getMessage("optionsTag.select"));
        }    
        
        Iterator valueItr = valueList.iterator();
        Iterator labelItr = labelList.iterator();
        
        if (pleaseSelect) {
        	String message = MessageResourceUtils.getInstance().getMessage(null, labelPleaseSelect, null, pageContext,null);            	
        	addOption(sb, "", message, selectTag.isMatched(""));
        }
        
        while (valueItr.hasNext()) {
        	String value = valueItr.next().toString();
        	addOption(sb, value, labelItr.next().toString(), selectTag.isMatched(value));
        }    	
    }
	
    protected void listSelectedOption(Collection valueList, Collection labelList, 
    		StringBuffer sb) throws JspException {
    	
		// display the options of the currency list
        // Acquire the select tag we are associated with
        SelectTag selectTag =
            (SelectTag) pageContext.getAttribute(Constants.SELECT_KEY);

        if (selectTag == null) {
            throw new JspException(messages.getMessage("optionsTag.select"));
        }    
        
        Iterator valueItr = valueList.iterator();
        Iterator labelItr = labelList.iterator();
        
		if (pleaseSelect) {
			String message = MessageResourceUtils.getInstance().getMessage(null, labelPleaseSelect, null, pageContext,null);            	
			addOption(sb, "", message, selectTag.isMatched(""));
		}
        
		//only show the selected code
        while (valueItr.hasNext()) {
        	String value = valueItr.next().toString();
			if (selectTag.isMatched(value) == true) {
				addOption(sb, value, labelItr.next().toString(), true);
				break;
			}
        }
    }

}
