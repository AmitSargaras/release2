package com.integrosys.base.uiinfra.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;


import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;

public class MessageResourceUtils {
	
	/**
	 * Singleton Instance
	 */
    private static MessageResourceUtils instance = new MessageResourceUtils();
    
    private static List bundleList = null;
     
    private static String MESSAGE_RESOURCE_VALUE = PropertyManager.getValue("integro.message.resource.key", "message");
    
    /**
     * Returns the Singleton instance of TagUtils.
     */
    public static MessageResourceUtils getInstance() {
    	return instance;
    }
    
    /**
     * Retrieve the bundle key list
     * @param pageContext
     * @return List a list of bundle keys
     */
    public static List retrieveBundleList(PageContext pageContext) {
    	if (bundleList == null) {
    		bundleList = new ArrayList();
    		bundleList.addAll(retrieveBundleListByScope(pageContext, PageContext.REQUEST_SCOPE));
    		bundleList.addAll(retrieveBundleListByScope(pageContext, PageContext.APPLICATION_SCOPE));
    	}  
    	return bundleList;
    }	
    
    /**
     * Retrieve the bundle key list by scope
     * @param pageContext
     * @param scope
     * @return List a list of bundle keys of scope given
     */
    private static List retrieveBundleListByScope (PageContext pageContext, int scope) {
    	List returnList = new ArrayList();
    	Enumeration attEnum = pageContext.getAttributeNamesInScope(scope);
    	while (attEnum.hasMoreElements()) {
    		String value = (String)attEnum.nextElement();
    		if (value.startsWith(MESSAGE_RESOURCE_VALUE)) {
    			returnList.add(value);
    		}
    	}
    	
    	return returnList;
    }
    
    /**
     * Retrieve the message.
     * If the bundle is null, it will search through all the bundle list in available scopes.
     * @param key
     * @param bundle
     * @param pageContext
     * @param localeKey
     * @return
     * @throws JspException
     */
    public String getMessage(String key, 
    		String bundle, PageContext pageContext, String localeKey) 
    		throws JspException {
    	return getMessage(null, key, bundle, pageContext, localeKey);
    }
    
    /**
     * Retrieve the message.
     * If the bundle is null, it will search through all the bundle list in available scopes.
     * @param args
     * @param key
     * @param bundle
     * @param pageContext
     * @param localeKey
     * @return
     * @throws JspException
     */
    public String getMessage(Object[] args, String key, 
    		String bundle, PageContext pageContext, String localeKey) 
    		throws JspException {
    	if (key == null)
    		return "";
    	
    	key = key.trim();
    	
        String message = null;
        if (bundle == null) {
        	List bundleList = retrieveBundleList(pageContext);
        	Iterator itr = bundleList.iterator();
        	while (itr.hasNext()) {
        		String tempBundle = (String)itr.next();
       			message = TagUtils.getInstance().message(pageContext, tempBundle,
                        localeKey, key, args);
        		if (message != null)
        			break;
        	}
  
        	if (bundleList.size() == 0 && message == null) {
        		message = TagUtils.getInstance().message(pageContext, null,
                    localeKey, key, args);
        	}
        } else {
    		message = TagUtils.getInstance().message(pageContext, bundle,
                    localeKey, key, args);        	
        }
        
        return message;
    }   
    
    /**
     * Return true if a message string for the specified message key is
     * present for the specified <code>Locale</code> and bundle.
     * If bundle given is null, 
     * it will search through all the bundle list to check for its present
     * @param pageContext
     * @param bundle
     * @param locale
     * @param key
     * @return
     * @throws JspException
     */
    public boolean present (PageContext pageContext, String bundle, 
    		String locale, String key) throws JspException {
    	boolean isPresent = false;
    	
    	if (bundle == null) {
    		List bundleList = retrieveBundleList(pageContext);
    		Iterator itr = bundleList.iterator();
    		while (itr.hasNext() && !isPresent) {
    			String tempBundle = (String)itr.next();
    			isPresent = TagUtils.getInstance().present(pageContext, tempBundle, locale, key);
    		}
    		if (!isPresent) {
    			isPresent = TagUtils.getInstance().present(pageContext, null, locale, key);
    		}
    	} else{
    		isPresent = TagUtils.getInstance().present(pageContext, bundle, locale, key);
    	}
    	return isPresent;
    }
}
