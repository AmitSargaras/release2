/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/ReadReturnCollateralCommand.java,v 1.4 2003/08/30 04:13:39 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.cash;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.OBLien;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.IStpTransType;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.proxy.IStpSyncProxy;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/30 04:13:39 $ Tag: $Name: $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 12:13:00 PM
 * To change this template use Options | File Templates.
 */
public class OkReturnCollateralCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"form.depositObject", "java.util.HashMap", FORM_SCOPE},
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},
                { "theOBTrxContext",
					"com.integrosys.cms.app.transaction.OBTrxContext",
					FORM_SCOPE },
                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
                {"stpMode", "java.lang.String", REQUEST_SCOPE},
                {IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE},
                       
			//	{ "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				
				{ "collateralList", "java.util.List", SERVICE_SCOPE },
	            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", SERVICE_SCOPE },
	            { "countryNme", "java.lang.String", SERVICE_SCOPE },
	            {"systemName", "java.lang.String", REQUEST_SCOPE},
	            {"systemId", "java.lang.String", REQUEST_SCOPE},
        });
    }

    /**
     * Defines an two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},
                
              //  { "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				
				{ "collateralList", "java.util.List", REQUEST_SCOPE },
	            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", REQUEST_SCOPE },
	            { "countryNme", "java.lang.String", REQUEST_SCOPE },
	            {"systemName", "java.lang.String", REQUEST_SCOPE},
	            {"systemId", "java.lang.String", REQUEST_SCOPE},
	            {"form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE},
	           /* {"form.depositObject", "java.util.HashMap", FORM_SCOPE},
	            { "theOBTrxContext",
					"com.integrosys.cms.app.transaction.OBTrxContext",
					FORM_SCOPE },
					
					 {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
		                {"stpMode", "java.lang.String", REQUEST_SCOPE},
		                {IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE},
		*/           
					
        });
    }

    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap returnMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        HashMap resultMap = new HashMap();
        HashMap objMap = (HashMap) map.get("form.depositObject");
        OBCashDeposit obCashDep = (OBCashDeposit) objMap.get("deposit");
        String strStpMode = (String) map.get("stpMode");
        boolean stpMode = false; //Stp switch off
        if (StringUtils.isNotEmpty(strStpMode))
            stpMode = new Boolean(strStpMode).booleanValue();
        String indexID = (String) map.get("indexID");
        try {
            // Modified by KLYong: Stp FD Account Listing
            boolean isValidDeposit = false;
            obCashDep.getDepositAmount();
            
            String depositStr = String.valueOf(obCashDep.getDepositAmount().getAmount());
            DecimalFormat formatter = new DecimalFormat();
   		 //int parsedFromString = formatter.parse(value);
   		 try {
   			depositStr = formatter.parse(depositStr).toString();
   		} catch (Exception e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		
   		double depositValue = Double.parseDouble(depositStr);
		double lienTot =  0;
		
		List list1 = (List) map.get("lienList");
		if(list1 != null)
		{
		ILienMethod ilien1[] = new ILienMethod[list1.size()];
		
		if (list1 != null) {
			for (int i = 0; i < list1.size(); i++) {
				ILienMethod lienObj1 = new OBLien();
				lienObj1 = (ILienMethod) list1.get(i);
				//ilien1[i] = lienObj1;
				lienTot = lienTot + lienObj1.getLienAmount();
			}
		}
		if(list1.size()==0)
		{
			exceptionMap.put("lienTotal",   new ActionMessage("error.collateral.deposit.Lien.info.missing"));
		}
		 if(lienTot > depositValue)
		 {
			 resultMap.put("event",(String) map.get("event"));
 			resultMap.put("lienList", (List)map.get("lienList"));
 			resultMap.put("indexID", indexID);
 	        exceptionMap.put("lienTotal",  new ActionMessage("error.number.must.lessthan.lien","Deposit Amount"));
 	       result.put("subtype", map.get("subtype"));
 	        
 	        result.put("collateralList", map.get("collateralList"));
 	        result.put("systemBankBranch", map.get("systemBankBranch"));
 	        result.put("countryNme", map.get("countryNme"));
 			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
 			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
 			return returnMap;
		 }
		}
		else
		{
			exceptionMap.put("lienTotal",   new ActionMessage("error.collateral.deposit.Lien.info.missing"));
		}
           
	
            
            if (stpMode && obCashDep.getOwnBank()) {
                if (!StringUtils.isEmpty(obCashDep.getDepositRefNo())) {
                    ArrayList stpArrlist = new ArrayList();
                    ArrayList depositList = new ArrayList();
                    HashMap stpMapParam = new HashMap();
                    ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
                    stpMapParam.put(IStpConstants.RES_RECORD_RETURN, PropertyManager.getValue(IStpConstants.STP_SKT_HDR_NUMBER_RECORD));
                    ILimitProfile lmtProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

                    stpArrlist.add(user);
                    stpArrlist.add(stpMapParam);
                    stpArrlist.add(obCashDep);
                    try {
                        IStpSyncProxy stpProxy = (IStpSyncProxy) BeanHouse.get("stpSyncProxy");
                        depositList = (ArrayList) stpProxy.submitTask(IStpTransType.TRX_TYPE_SEARCH_FD_ACCT_LIST,
                                stpArrlist.toArray());
                    }
                    catch (StpCommonException e) {
                        exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, e.getErrorDesc()));
                    }

                    if (exceptionMap.isEmpty()) {
                        String accountNum = obCashDep.getDepositReceiptNo();

                        if (!StringUtils.isEmpty(accountNum)) { // Account number specified
                            for (int i = 0; i < depositList.size(); i++) {
                                OBCashDeposit respCashDep = (OBCashDeposit) depositList.get(i);
                                if (StringUtils.equals(respCashDep.getDepositReceiptNo(), accountNum)) {
                                    //Andy Wong, 6 July 2009: condition checking on hold status and col exists flag to allow pledge
                                    if (StringUtils.equals(respCashDep.getCollateralExists(), "N") &&
                                            ((StringUtils.equals("CC", lmtProfile.getApplicationType()) && (StringUtils.equals("C", respCashDep.getHoldStatus()) || StringUtils.isEmpty(respCashDep.getHoldStatus())))
                                                    ||
                                                    (!StringUtils.equals("CC", lmtProfile.getApplicationType()) && (StringUtils.equals("L", respCashDep.getHoldStatus()) || StringUtils.equals("M", respCashDep.getHoldStatus()) || StringUtils.isEmpty(respCashDep.getHoldStatus()))))) {
                                        obCashDep.setDepositAmount(respCashDep.getDepositAmount());
                                        obCashDep.setDepositMaturityDate(respCashDep.getDepositMaturityDate());
                                        obCashDep.setTenure(respCashDep.getTenure());
                                        obCashDep.setTenureUOM(respCashDep.getTenureUOM());
                                        obCashDep.setIssueDate(respCashDep.getIssueDate());
                                        obCashDep.setLien(respCashDep.getLien());
                                    } else {
                                        CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.FD_HOLD_STATUS);
                                        exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, "FD cannot be pledged. " + (commonCode.getCommonCodeLabel(respCashDep.getHoldStatus())!=null?commonCode.getCommonCodeLabel(respCashDep.getHoldStatus()):"Available")
                                                + ", Collateral Exists : " + (StringUtils.equals("Y", respCashDep.getCollateralExists()) ? "Yes" : "No")));
                                    }

                                    isValidDeposit = true; // If found and not hold by loan and credit card
                                    break;
                                }
                            }
                            if (!isValidDeposit && exceptionMap.isEmpty()) { // Not valid
                                exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, "Account number specified was not found."));
                            }
                        }
                    }
                }
            }
    
            
            ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
            if (itrxValue != null) {
	        	resultMap.put("serviceColObj", itrxValue);
	            String from_event = (String) map.get("from_event");
	            if ((from_event != null) && from_event.equals("read")) {
	            	resultMap.put("form.collateralObject", itrxValue.getCollateral());
	            } else {            	
	            	ICollateral col = itrxValue.getStagingCollateral();
	            	col.setCurrencyCode(itrxValue.getCollateral().getCurrencyCode());
	            	col.setCollateralLocation(itrxValue.getCollateral().getCollateralLocation());
	            	col.setSecurityOrganization(itrxValue.getCollateral().getSecurityOrganization());
	            	col.setSecPriority(itrxValue.getCollateral().getSecPriority());
	            	itrxValue.setStagingCollateral(col); 
	            	resultMap.put("form.collateralObject", itrxValue.getStagingCollateral());
	            }
	        }
            ICashCollateral iCash = (ICashCollateral) itrxValue.getStagingCollateral();

            //==============================Start===========================
    		//update by sachin p.
    		//duplicate fd receipt no validation 
    		  if (exceptionMap.isEmpty()) {
    			  int iCount = 0;
                String accountNum = obCashDep.getDepositRefNo();
                ICashDeposit[] cashDeposit = iCash.getDepositInfo();
                if (!StringUtils.isEmpty(accountNum)) { // Account number specified
                    for (int i = 0; i < cashDeposit.length; i++) {
                        OBCashDeposit respCashDep = (OBCashDeposit) cashDeposit[i];
                        if (StringUtils.equals(respCashDep.getDepositRefNo(), accountNum)) {
                        	//iCount++;
                        	if(iCash.getDepositInfo().length-1 >= Integer.parseInt(indexID))//edit
                        	{
                        	   if(i != Integer.parseInt(indexID))	
                        	     {
                        		   exceptionMap.put("depositRefNo", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, "Duplicate Deposit receipt Number."));
                        	     }
                        	}//edit
                        	if(iCash.getDepositInfo().length-1 < Integer.parseInt(indexID))//add
                        	{
                        		exceptionMap.put("depositRefNo", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, "Duplicate Deposit receipt Number."));
                        	}//add
                        	
                            } 
                        }
                    }
                    /*if (((iCount != 1) &&(iCash.getDepositInfo().length-1 >= Integer.parseInt(indexID))) || ((iCount > 0) && (iCash.getDepositInfo().length-1 < Integer.parseInt(indexID)))) { // Not valid
                        exceptionMap.put("depositRefNo", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, "Duplicate Deposit receipt Number."));
                    }*/
                    
                               
            
            if (exceptionMap.isEmpty()) {
            	if(iCash.getDepositInfo().length-1 < Integer.parseInt(indexID))
            	{
            	
                addDeposit(iCash, obCashDep);
                itrxValue.setStagingCollateral(iCash);
                result.put("serviceColObj", itrxValue);

                try {
                    CollateralValuator valuator = new CollateralValuator();
                  //  valuator.setCollateralCMVFSV(iCash);
                }
                catch (Exception e) {
                    DefaultLogger.warn(this, "Collateral ID: \t [" + iCash.getCollateralID() + "] \t" +
                            "Security Number: \t [" + iCash.getSCISecurityID() + "] \t " +
                            "[Error in calculating cmv and fsv]", e);
                }
                
            }
            	else if(iCash.getDepositInfo().length-1 >= Integer.parseInt(indexID))
            	{
            	
                editDeposit(iCash, obCashDep,Integer.parseInt(indexID));
                itrxValue.setStagingCollateral(iCash);
                result.put("serviceColObj", itrxValue);

                try {
                    CollateralValuator valuator = new CollateralValuator();
                  //  valuator.setCollateralCMVFSV(iCash);
                }
                catch (Exception e) {
                    DefaultLogger.warn(this, "Collateral ID: \t [" + iCash.getCollateralID() + "] \t" +
                            "Security Number: \t [" + iCash.getSCISecurityID() + "] \t " +
                            "[Error in calculating cmv and fsv]", e);
                }
                
            }	
        		
        	
            String event = (String) map.get("event");
    		
    		DefaultLogger.debug(this, "Inside doExecute() AddPrepareLienCommand "+event);
    		
    		
    		//ILienMethod obLien = (OBLien)map.get("OBLien");
    		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
    	//	ICashCollateral iCash = (ICashCollateral) (((ICollateralTrxValue) map.get("serviceColObj"))
        //            .getStagingCollateral());
    		List list = (List) map.get("lienList");
    		if(list != null)
    		{
    		ILienMethod ilien[] = new ILienMethod[list.size()];
    		
    		if (list != null) {
    			for (int i = 0; i < list.size(); i++) {
    				ILienMethod lienObj = new OBLien();
    				lienObj = (ILienMethod) list.get(i);
    				ilien[i] = lienObj;

    			}
    		}
    		//obLien.get
    		/*OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
    		ICashCollateral iCash = (ICashCollateral) (((ICollateralTrxValue) map.get("serviceColObj"))
                    .getStagingCollateral());
    	*/	//iCash.getDepositInfo()[0].setLien(ilien);
    		ICashDeposit[] cashDeposit1 = iCash.getDepositInfo();
    		ICashDeposit cash = cashDeposit1[Integer.parseInt(indexID)];
    		cash.setLien(ilien);
        }
    		else
    		{
    			
    			ICashDeposit[] cashDeposit2 = iCash.getDepositInfo();
    			ICashDeposit cash = cashDeposit2[Integer.parseInt(indexID)];
    			cash.setLien(null);
    		}
    		//iCash.setDepositInfo(cash)
    		
            //iCash.setDepositInfo(depositInfo);
    		
    	//	result.put("OBLien", obLien);
    		result.put("event",event);
    		result.put("lienList", list);
    		result.put("indexID", indexID);
    		
    		ICollateralTrxValue trxValue = new OBCollateralTrxValue();
    		trxValue.setTrxContext(ctx);
    		trxValue.setStagingCollateral(iCash);
    		
    		/*try {
    			ICollateralTrxValue	trx = CollateralProxyFactory.getProxy().createLien(trxValue);
    		} catch (CollateralException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}*/
         
                	  
                  
              }
    		  }
  	//==============================END =============================	
        
        }
        catch (Exception e) {
            CommandProcessingException cpe = new CommandProcessingException(
                    "failed to add deposit into cash collateral");
            cpe.initCause(e);
            throw cpe;
        }
      
		
		
		
		
		
        result.put("subtype", map.get("subtype"));
        
        result.put("collateralList", map.get("collateralList"));
        result.put("systemBankBranch", map.get("systemBankBranch"));
        result.put("countryNme", map.get("countryNme"));
        result.put("systemName", map.get("systemName"));
        result.put("systemId", (String) map.get("systemId"));
//		DefaultLogger.debug(this, "*******ResultMap is :" + map.get("subtype"));
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    public static void addDeposit(ICashCollateral iCash, ICashDeposit iCashDep) {
        ICashDeposit[] existingArray = iCash.getDepositInfo();
        int arrayLength = 0;
        if (existingArray != null) {
            arrayLength = existingArray.length;
        }

        ICashDeposit[] newArray = new ICashDeposit[arrayLength + 1];
        if (existingArray != null) {
            System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
        }
        newArray[arrayLength] = iCashDep;

        iCash.setDepositInfo(newArray);
    }
    public static void editDeposit(ICashCollateral iCash, ICashDeposit iCashDep , int index) {
        ICashDeposit[] existingArray = iCash.getDepositInfo();
        
        
        int arrayLength = 0;
        if (existingArray != null) {
            arrayLength = existingArray.length;
        }

        ICashDeposit[] newArray = new ICashDeposit[arrayLength];
        if (existingArray != null) {
            System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
        }
        newArray[index] = iCashDep;

        iCash.setDepositInfo(newArray);
    }
}
