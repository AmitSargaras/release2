package com.integrosys.cms.ui.fileUpload;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

public class ListFinWareFileCmd extends AbstractCommand implements ICommonEventConstant{
	
	private IFileUploadProxyManager fileUploadProxy;
	
	
	
public IFileUploadProxyManager getFileUploadProxy() {
		return fileUploadProxy;
	}

	public void setFileUploadProxy(IFileUploadProxyManager fileUploadProxy) {
		this.fileUploadProxy = fileUploadProxy;
	}

public ListFinWareFileCmd(){		
	}

public String[][] getParameterDescriptor() {
	return new String[][] {
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
			{ "totalCount", "java.lang.String", REQUEST_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE },
			{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
			  {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
			{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
			{ "locale", "java.util.Locale", REQUEST_SCOPE },
			{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
			
			
		};
}
   public String[][] getResultDescriptor() {
        return (new String[][]{
               
                { "event", "java.lang.String", REQUEST_SCOPE },
                { "preUpload", "java.lang.String", REQUEST_SCOPE },
                { "fileInfo", "java.uti.List", REQUEST_SCOPE },
		           
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
        });
    }
   
   public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
       String event=(String)map.get("event");
       boolean preUpload=false;
  	 List fileInfo=new ArrayList();
  	 Date date=new Date();
  	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
  	 String checkDate=df.format(date);
       //below code create master transaction
       try{
    	   IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
    	   if(fileUploadJdbc.getUplodCount("FINWARE_UPLOAD")>0){
    		   preUpload=true;
    	   }
    	 if(!preUpload){
    		 File path=new File("E:/FINWARE");
    		 File list[]=path.listFiles();
    	
    		 for(int i=list.length-1;i>=0;i--){ 
    			 File sortFile=list[i];
    			 if(sortFile.getName().contains(checkDate)){
    			 fileInfo.add(list[i]);
    			 }
    		 }
    		
    	 }
       }
       catch(FileUploadException ex){
    	   DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
       }  catch (Exception e) { 
			DefaultLogger.debug(this, "got exception in doExecute" + e);
	    e.printStackTrace();
	    throw (new CommandProcessingException(e.getMessage()));
	    }
       //end of master transaction
		resultMap.put("event", event);
		resultMap.put("fileInfo", fileInfo);
		resultMap.put("preUpload",String.valueOf(preUpload));
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
   


}
