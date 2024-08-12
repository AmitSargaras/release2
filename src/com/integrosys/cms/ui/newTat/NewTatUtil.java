/**
 * 
 */
package com.integrosys.cms.ui.newTat;

import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.ITatMasterJdbc;
import com.integrosys.cms.app.user.bus.StdUserDAO;
import com.integrosys.component.user.app.bus.UserException;

/**
 * @author abhijit.rudrakshawar
 *
 */
public class NewTatUtil {

	
	public static String getStatusName(String statusCode, String ftnrValue){
		String status = "";
		if(ftnrValue==null){
			ftnrValue="";
			}
		if(statusCode==null){
			statusCode="";
		}
		
		if(!"FTNR".equals(ftnrValue)){
		
		if("DOCUMENT_SUBMITTED".equals(statusCode)){
			status = "Document Submitted";
		}else if("CLOSED".equals(statusCode)){
			status = "Document Closed";
		}else if("DEFERRAL_RAISED".equals(statusCode)){
			status = "Deferral Raised";
		}else if("DOCUMENT_RECEIVED".equals(statusCode)){
			status = "Document Received";
		}else if("DOCUMENT_SCANNED".equals(statusCode)){
			status = "Document Scanned";
		}else if("CLIMS_UPDATED".equals(statusCode)){
			status = "Clims Updated";
		}else if("DEFERRAL_APPROVED".equals(statusCode)){
			status = "Deferral Approved";
		}else if("DEFERRAL_CLEARANCE".equals(statusCode)){
			status = "Document Re-Submitted";
		}else if("LIMIT_RELEASED".equals(statusCode)){
			status = "Limit Released";
		}
		
		}else{
		
			if("DOCUMENT_SUBMITTED".equals(statusCode)){
				status = "Document Re-Submitted";
			}else if("CLOSED".equals(statusCode)){
				status = "Document Closed";
			}else if("DEFERRAL_RAISED".equals(statusCode)){
				status = "Deferral Raised";
			}else if("DOCUMENT_RECEIVED".equals(statusCode)){
				status = "Document Re-Received";
			}else if("DOCUMENT_SCANNED".equals(statusCode)){
				status = "Document Re-Scanned";
			}else if("CLIMS_UPDATED".equals(statusCode)){
				status = "Clims Updated";
			}else if("DEFERRAL_APPROVED".equals(statusCode)){
				status = "Deferral Approved";
			}else if("DEFERRAL_CLEARANCE".equals(statusCode)){
				status = "Document Re-Submitted";
			}else if("LIMIT_RELEASED".equals(statusCode)){
				status = "Limit Released";
			}
				
		}
		return status; 
	}
	
	public static String getUserName(String userCode){
		String userName = "";
		
		try {
			userName=	(new StdUserDAO()).getUserNameByLoginID(userCode);
			
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return userName; 
	}
	
	 public static String getTatBurst(String event) {
	     ITatMasterJdbc tatMasterJdbc =(ITatMasterJdbc) BeanHouse.get("tatMasterJdbc"); 
	     INewTatMaster tatMaster= tatMasterJdbc.getTatEvent(event);
	     String tatBurst="0";
	     if(tatMaster!=null){
	    	 String hrs=tatMaster.getTimingHours();
	    	 int hrsInt=Integer.parseInt(hrs);
	    	 String mins=tatMaster.getTimingMin();
	    	 int minInt = Integer.parseInt(mins);
	    	 int minsHr=0;
	    	 if(hrsInt>0){
	    		 minsHr=hrsInt*60;
	    		 minInt=minInt+minsHr;
	    	 }
	    	 tatBurst=String.valueOf(minInt);
	     }
	     
	     
		 return tatBurst;
	}
	 
	 public static HashMap getTatTimings(String event) {
		 HashMap map=new HashMap();
	     ITatMasterJdbc tatMasterJdbc =(ITatMasterJdbc) BeanHouse.get("tatMasterJdbc"); 
	     INewTatMaster tatMaster= tatMasterJdbc.getTatEvent(event);
	     String tatBurst="0";
	     if(tatMaster!=null){
	    	 String intime=tatMaster.getStartTime();
	    	 String outTime=tatMaster.getEndTime();
	    	 map.put("in", intime);
	    	 map.put("out", outTime);
	     }
	     
	     
		 return map;
	}
		
}
