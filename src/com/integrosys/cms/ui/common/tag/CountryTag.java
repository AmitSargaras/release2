package com.integrosys.cms.ui.common.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

import com.integrosys.cms.ui.common.CountryList;


public class CountryTag extends CommonOptionsHandlerTag {

    
    private CountryList countryList;
    

	
	/**
     * Process the start of this tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        if (countryList == null)
        	countryList = CountryList.getInstance();    	
    	
    	if (display) {
    		try {
    			String cty = countryList.getCountryName(value);
    			pageContext.getOut().print((cty == null ? "-" : cty));
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    			throw new JspException("IOException");
    		}    		
    	} else {
            
            Collection valueList = new ArrayList();

            
            valueList.addAll(countryList.getCountryValues());
            if (excludeList != null && excludeList.size() > 0) {
            	valueList.removeAll(getExcludeList());
            }
            
            Collection labelList = new ArrayList();
            Iterator itr = valueList.iterator();
            while (itr.hasNext()) {
            	labelList.add(countryList.getCountryName(itr.next().toString()));
            }
            
            StringBuffer sb = new StringBuffer();
            
            listOptions(valueList, labelList, sb);
            
            TagUtils.getInstance().write(pageContext, sb.toString());
    	}
        return SKIP_BODY;
    }		
    
}
