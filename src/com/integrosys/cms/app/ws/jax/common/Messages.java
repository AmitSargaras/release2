package com.integrosys.cms.app.ws.jax.common;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.integrosys.cms.app.ws.common.CLIMSWebService;

public class Messages {	
    

    private final ResourceBundle resourceBundle;
    private static final ResourceBundle commonResourceBundle = 
    		ResourceBundle.getBundle(CLIMSResourceBundle.COMMON_RESOURCE_BUNDLE.bundleName);;
    private static Map<CLIMSResourceBundle, Messages> bundalMap = new HashMap<CLIMSResourceBundle, Messages>();
    

    
    public static enum CLIMSResourceBundle{
    	COMMON_RESOURCE_BUNDLE("ApplicationResources"),
    	CAM_RESOURCE_BUNDLE("ApplicationResources-manualinput"),
    	PARTY_RESOURCE_BUNDLE("ApplicationResources-manualinput"),
    	SECURITY_RESOURCE_BUNDLE("ApplicationResources-collateral"),
    	DOCUMENTS_RESOURCE_BUNDLE("ApplicationResources-documentationchecklist"),
    	WEBSERVICE_RESOURCE_BUNDLE("WebServiceResources"),
    	UDF_RESOURCE_BUNDLE("ApplicationResources-manualinput");
    	
    	private String bundleName;
    	
    	CLIMSResourceBundle(String bundleName){
    		this.bundleName = bundleName;
    	}
    }
    
    private Messages(CLIMSResourceBundle resourceBundle){
    	if(resourceBundle.equals(CLIMSResourceBundle.COMMON_RESOURCE_BUNDLE))
    	{
    		this.resourceBundle = commonResourceBundle;
    	}else{
    		this.resourceBundle = ResourceBundle.getBundle(resourceBundle.bundleName);
    	}
    }
    
    
    public static Messages getInstance(CLIMSWebService serviceName) {
		switch (serviceName) {
		case CAM:
			return getInstance(CLIMSResourceBundle.CAM_RESOURCE_BUNDLE);
		case PARTY:
			return getInstance(CLIMSResourceBundle.PARTY_RESOURCE_BUNDLE);
		case FACILITY:
			return getInstance(CLIMSResourceBundle.CAM_RESOURCE_BUNDLE);
		case DESCRIPENCY:
			return getInstance(CLIMSResourceBundle.CAM_RESOURCE_BUNDLE);
		case SECURITY:
			return getInstance(CLIMSResourceBundle.CAM_RESOURCE_BUNDLE);
		case UPDATE_SECURITY:
			return getInstance(CLIMSResourceBundle.CAM_RESOURCE_BUNDLE);
		case SECURITYSEARCH:
			return getInstance(CLIMSResourceBundle.SECURITY_RESOURCE_BUNDLE);
		case DOCUMENTS:
			return getInstance(CLIMSResourceBundle.DOCUMENTS_RESOURCE_BUNDLE);
		case DIGITALLIBRARY:
			return getInstance(CLIMSResourceBundle.CAM_RESOURCE_BUNDLE);
		case UDF:
			return getInstance(CLIMSResourceBundle.UDF_RESOURCE_BUNDLE);
		default:
			break;
		}
		return null;
	}
    
    public static Messages getInstance(CLIMSResourceBundle resourceBundle){
    	if(bundalMap.containsKey(resourceBundle)){
    		return bundalMap.get(resourceBundle);
    	}	
    	synchronized (Messages.class){
    		if(bundalMap.containsKey(resourceBundle)){
    			return bundalMap.get(resourceBundle);
        	}
    		Messages messages = new Messages(resourceBundle);
    		bundalMap.put(resourceBundle, messages);
    		return messages;
		}
    }
    
   
    
    public String getString(String key) {
    	String value = "";
        try {
        	if(resourceBundle.containsKey(key)){
        		value = resourceBundle.getString(key);
        	}else{
        		value = commonResourceBundle.getString(key);
        	}
        	
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
        return value.replaceAll("\\<.*?>","");
    }
    
    public String getString(String key, Object... params  ) {
    	String value = "";
        try {
        	if(resourceBundle.containsKey(key)){
        		value =  MessageFormat.format(resourceBundle.getString(key), params);
        	}else{
        		value =  MessageFormat.format(commonResourceBundle.getString(key), params);
        	}
        	
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
        return value.replaceAll("\\<.*?>","");
    }
      
    
    public static void main(String[] args) {
		Messages msg = Messages.getInstance(CLIMSResourceBundle.COMMON_RESOURCE_BUNDLE);
		System.out.println( msg.getString("label.global.userfile.format"));
	}
}
