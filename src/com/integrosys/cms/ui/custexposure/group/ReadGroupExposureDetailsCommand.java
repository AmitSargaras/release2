/* 
* Copyright Integro Technologies Pte Ltd
* $Header$
*/
package com.integrosys.cms.ui.custexposure.group;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custexposure.proxy.group.GroupExposureProxyFactory;
import com.integrosys.cms.app.custexposure.proxy.group.IGroupExposureProxy;
import com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue;
import com.integrosys.cms.app.custexposure.trx.group.OBGroupExposureTrxValue;
import com.integrosys.cms.app.custexposure.bus.group.IGroupExposure;
import com.integrosys.cms.app.custexposure.bus.group.OBGroupExposure;
import com.integrosys.cms.ui.custexposure.CustExposureUIHelper;

import com.integrosys.cms.app.custexposure.bus.group.IGroupExpCustGrp;
import com.integrosys.cms.app.custexposure.bus.group.OBGroupExpCustGrp;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custexposure.bus.group.IGroupExpBankEntity;
import com.integrosys.cms.app.custexposure.bus.ICustExposure;
import com.integrosys.cms.app.custexposure.bus.ICustExposureEntityRelationship;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.custexposure.bus.ILimitExposureProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import java.util.*;
import com.integrosys.cms.app.custexposure.bus.group.*;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.OBCMSLegalEntity;
import com.integrosys.cms.app.common.constant.ICMSConstant;
/**
* Describe this class.
* Purpose:This class implements command
* Description: For retrieving value in group svc
*
* @author $Grace Teh$<br>
* @version $Revision$
* @since $28 July 2008$
* Tag: $Name$
*/
public class ReadGroupExposureDetailsCommand extends AbstractCommand {

    public ReadGroupExposureDetailsCommand() {

    }

    /**
     * Defines a two dimensional array with the parameter list to be passed to
     * the doExecute method by a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
     public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"grpID", "java.lang.String", REQUEST_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"sub_profile_id", "java.lang.String", REQUEST_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
                {"GroupExposureTrxValue", "com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue", SERVICE_SCOPE},
                {CustExposureUIHelper.service_CustExposureSearchListObj, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {IGlobalConstant.GLOBAL_CUSTEXPOSURE_SEARCH_CRITERIA_OBJ, "com.integrosys.cms.app.custexposure.bus.CustExposureSearchCriteria", GLOBAL_SCOPE},
                {"startInd", "java.lang.String", REQUEST_SCOPE},
        });
    }

    /**
     * Defines a two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"GroupExposureTrxValue", "com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue", SERVICE_SCOPE},
                {"IGrpExposure", "com.integrosys.cms.app.custexposure.bus.group.IGroupExposure", FORM_SCOPE},
                {"session.IGrpExposure", "com.integrosys.cms.app.custexposure.bus.group.IGroupExposure", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"sub_profile_id", "java.lang.String", REQUEST_SCOPE},
                {"startInd", "java.lang.String", REQUEST_SCOPE},
                {"records_per_page", "java.lang.String", REQUEST_SCOPE},
           	    {"total_records", "java.lang.String", REQUEST_SCOPE},
           	    {"total_cust", "java.lang.String", REQUEST_SCOPE},
           	    {"endInd", "java.lang.String", REQUEST_SCOPE},
                {"grpID", "java.lang.String", REQUEST_SCOPE},
        }
        );
    }
        
    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here creation for Company Borrower is done.
     *
     * @param inputMap is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute()");

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();       
        String grpID = (String) inputMap.get("grpID");
        String event = (String) inputMap.get("event");
        String sub_profile_id_str = (String) inputMap.get("sub_profile_id");
        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap.get("theOBTrxContext");
        ICommonUser user = (ICommonUser) inputMap.get(IGlobalConstant.USER);
        
        int records_per_page = 5;
        int total_records = 0;
        int total_cust =0;
        int y=0;
        String index = (String)inputMap.get("startInd");
       
        if(index == null){
	       index = "0";
        }

        int endIndex = Integer.parseInt(index)+ records_per_page; 
        
        IGroupExposureProxy proxy = GroupExposureProxyFactory.getProxy();
        IGroupExposureTrxValue trxValue = (IGroupExposureTrxValue) inputMap.get("GroupExposureTrxValue");
        DefaultLogger.debug(this, "Inside trxValue"+trxValue);
        try {
	        DefaultLogger.debug(this,"@@@IGroupExposureTrxValue if not null::::::::" + trxValue);
	      if(trxValue == null){
	         if (grpID != null && !"".equals(grpID)){
                trxValue = proxy.getGroupExposure((Long.parseLong(grpID)));
                DefaultLogger.debug(this,"@@@IGroupExposureTrxValue if null::::::::" + trxValue);
            }  
          }
          
         // if (trxValue == null){
         //   trxValue = new OBGroupExposureTrxValue();
         //}
         
          IGroupExposure aIGrpExposure = trxValue.getGroupExposure();
            
          if(aIGrpExposure != null){
            IGroupExpCustGrp grpExpCustGrp = aIGrpExposure.getGroupExpCustGrp();

            IGroupExpBankEntity[] grpBankEntity = aIGrpExposure.getGroupExpBankEntity();
                
            ICustExposure[] custExposure =  aIGrpExposure.getGroupMemberExposure();  
            ILimitExposureProfile[] limitExposureProfile = null;
            ILimit[] limits = null;
            ILimitProfile limitProfile = null;
            ICMSCustomer customer = new  OBCMSCustomer();

            if(custExposure != null && custExposure.length > 0){
	            for(int b= Integer.parseInt(index); b<custExposure.length; b++){
	            limitExposureProfile = custExposure[b].getLimitExposureProfile();
	             //check total limit records
	              if(limitExposureProfile!=null){
		              for(int i=0; i<limitExposureProfile.length; i++){
	                 limitProfile = limitExposureProfile[i].getLimitProfile();	
	                 limits = limitProfile.getLimits();
				             if(limits != null && limits.length > 0){
					              for(int q=0; q<limits.length; q++){
					              if(limits[q].getLimitType().equals(ICMSConstant.CCC_OUTER_LIMIT)) {
						                 total_records ++;
					               }
				               }
				              }
			              }
			           }

              ICustExposureEntityRelationship[] entityRelationship = custExposure[b].getCustExposureEntityRelationship();
               
                customer = custExposure[b].getCMSCustomer() ;
                if(customer!=null){
	                //check total customer
	                total_cust ++;
                }
					        
                ICMSLegalEntity leEntity = new OBCMSLegalEntity();
                if(customer!=null){
                  customer.getCMSLegalEntity();
                  customer.getCustomerName();
                  customer.getCustomerID();
                }
                custExposure[b].setCMSCustomer(customer) ; 
                
               /* if(endIndex>total_cust){
	                endIndex=total_cust;
				        }
				        else{
					        endIndex = Integer.parseInt(index)+records_per_page;
				        }*/
             }
            }
             aIGrpExposure.setGroupMemberExposure(custExposure);
             trxValue.setGroupExposure(aIGrpExposure);
           }
        
            resultMap.put("event", event);
            resultMap.put("sub_profile_id", sub_profile_id_str);
            resultMap.put("grpID", grpID);
            resultMap.put("IGrpExposure", aIGrpExposure);  
            resultMap.put("session.IGrpExposure", aIGrpExposure);         
            resultMap.put("GroupExposureTrxValue", trxValue);
            resultMap.put("startInd",index);
            resultMap.put("records_per_page",String.valueOf(records_per_page));
            resultMap.put("endInd",String.valueOf(endIndex));
            resultMap.put("total_records",String.valueOf(total_records));
            resultMap.put("total_cust",String.valueOf(total_cust));

        } catch (Exception e) {
	          e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Existing doExecute()\n");
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    /**
     * @param msg
     */
    private void Debug(String msg) {
    	DefaultLogger.debug(this,ReadGroupExposureDetailsCommand.class.getName() + " = " + msg);
    }  
      
        
}