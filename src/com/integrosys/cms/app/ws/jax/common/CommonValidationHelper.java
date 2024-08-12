package com.integrosys.cms.app.ws.jax.common;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterException;
import com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.proxy.IFacilityNewMasterProxyManager;

public class CommonValidationHelper {
	
	private IFacilityNewMasterProxyManager  facilityNewMasterProxy;
	
	public IFacilityNewMasterProxyManager getFacilityNewMasterProxy() {
		return facilityNewMasterProxy;
	}

	public void setFacilityNewMasterProxy(
			IFacilityNewMasterProxyManager facilityNewMasterProxy) {
		this.facilityNewMasterProxy = facilityNewMasterProxy;
	}

	public StringBuffer isDataProper(OBFacilityNewMaster facilityMasterOB) {
		try {
		MasterAccessUtility accessUtility= (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		StringBuffer errorMsg = null;
		OBCommonCodeEntry otherSystem;
		IFacilityNewMasterProxyManager facilityNewMasterProxy= (IFacilityNewMasterProxyManager)BeanHouse.get("facilityNewMasterProxy");
			//CategoryCode == SYSTEM
			otherSystem = (OBCommonCodeEntry) accessUtility.getObjByEntityNameAndCPSId("actualEntryCode", String.valueOf(facilityMasterOB.getNewFacilitySystem()),"SYSTEM");
		
        if(otherSystem!=null && !"".equals(otherSystem)){
     	   facilityMasterOB.setNewFacilitySystem(otherSystem.getEntryCode());
        }
        else{
     	   //throw new FacilityNewMasterException("System_ID is Invalid");	     
     	   if(errorMsg==null){
     		   errorMsg = new StringBuffer();
     		   errorMsg.append("System_ID is Invalid") ;
     	   }
     	   else{
     		   errorMsg.append(";System_ID is Invalid") ;
     	   }
        }
			try {
				boolean isDuplicateFacName = facilityNewMasterProxy.isFacilityNameUnique(facilityMasterOB.getNewFacilityName());
				boolean isDuplicateSystemLineNo = facilityNewMasterProxy.isUniqueCode(facilityMasterOB.getLineNumber(),facilityMasterOB.getNewFacilitySystem());
				boolean isFacilityCpsIdUnique = facilityNewMasterProxy.isFacilityCpsIdUnique(facilityMasterOB.getCpsId());
				if(facilityMasterOB.getNewFacilityType()!=null &&
						!(facilityMasterOB.getNewFacilityType().equalsIgnoreCase("FUNDED")
						||facilityMasterOB.getNewFacilityType().equalsIgnoreCase("NON_FUNDED")
						||facilityMasterOB.getNewFacilityType().equalsIgnoreCase("MEMO_EXPOSURE")))
				{
					//throw new FacilityNewMasterException("FACILITY_TYPE is Invalid");
					 if(errorMsg==null){
						 errorMsg = new StringBuffer();
             		   errorMsg.append("FACILITY_TYPE is Invalid") ;
             	   }
             	   else{
             		   errorMsg.append(";FACILITY_TYPE is Invalid") ;
             	   }
				}
				if(facilityMasterOB.getPurpose()!=null &&
						!(facilityMasterOB.getPurpose().equalsIgnoreCase("WORKING_CAPITAL")
						||facilityMasterOB.getPurpose().equalsIgnoreCase("BLANK")
						||facilityMasterOB.getPurpose().equalsIgnoreCase("OTHERS")))
				{
					//throw new FacilityNewMasterException("FACILITY_FBTYPE is Invalid");
					 if(errorMsg==null){
						 errorMsg = new StringBuffer();
             		   errorMsg.append("FACILITY_FBTYPE is Invalid") ;
             	   }
             	   else{
             		   errorMsg.append(";FACILITY_FBTYPE is Invalid") ;
             	   }
				}
				
				if(isDuplicateSystemLineNo){
					//throw new FacilityNewMasterException("Facility Line No.-System Name combination is duplicate.");
					 if(errorMsg==null){
						 errorMsg = new StringBuffer();
             		   errorMsg.append("Facility Line No.-System Name combination is duplicate") ;
             	   }
             	   else{
             		   errorMsg.append(";Facility Line No.-System Name combination is duplicate") ;
             	   }
				}
				if(isDuplicateFacName){
					//throw new FacilityNewMasterException("Facility Master Name is Duplicate.");
					 if(errorMsg==null){
						 errorMsg = new StringBuffer();
             		   errorMsg.append("Facility Master Name is Duplicate") ;
             	   }
             	   else{
             		   errorMsg.append(";Facility Master Name is Duplicate") ;
             	   }
				}
				if(isFacilityCpsIdUnique){
					//throw new FacilityNewMasterException("Facility Master is Duplicate.");
					 if(errorMsg==null){
						 errorMsg = new StringBuffer();
             		   errorMsg.append("Facility Master is Duplicate") ;
             	   }
             	   else{
             		   errorMsg.append(";Facility Master is Duplicate") ;
             	   }
				}
			} catch (FacilityNewMasterException e) {
				throw new FacilityNewMasterException(e.getMessage());
			}
			catch(Exception e){
				throw new FacilityNewMasterException("Error in retiving facility code.");
			}
		return	errorMsg;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return	null;
	}
	

}
