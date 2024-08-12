package com.integrosys.cms.ui.common.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

import com.integrosys.cms.ui.common.CurrencyList;

public class CurrencyTag extends CommonOptionsHandlerTag {

	private CurrencyList currencyList;	
	
	public CurrencyList getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(CurrencyList currencyList) {
		this.currencyList = currencyList;
	}

	/**
     * Process the start of this tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
    	if (display) {
    		try {    			
    			pageContext.getOut().print((value == null ? "-" : value));
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    			throw new JspException("IOException");
    		}    		
    	} else {
            
            Collection optionList = new ArrayList();
            if (currencyList == null)
            	currencyList = CurrencyList.getInstance();
            
            optionList.addAll(currencyList.getCurrencyLabels());
            if (excludeList != null && excludeList.size() > 0) {
            	optionList.removeAll(getExcludeList());
            }
            
            StringBuffer sb = new StringBuffer();
            
            listOptions(optionList, optionList, sb);
            
            TagUtils.getInstance().write(pageContext, sb.toString());
    	}
        return SKIP_BODY;
    }	    
}
