/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/ReadAssetGenChargeCommand.java,v 1.2 2005/04/19 12:09:45 hshii Exp $
 */
package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeHelper;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeStockDetailsHelperForm;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/04/19 12:09:45 $ Tag: $Name: $
 */
public class ReadAssetGenChargeCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "parentPageFrom", "java.lang.String", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				 { "no_template_gc", "java.lang.String", SERVICE_SCOPE },
				//{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				//{ "calculatedDP", "java.lang.String", SERVICE_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
//				{ "fundedShare", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, 
				{ "dpShare", "java.lang.String", REQUEST_SCOPE },});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
//				{ "fundedShare", "java.lang.String", SERVICE_SCOPE },
				{ "calculatedDP", "java.lang.String", SERVICE_SCOPE },
				{ "isEditable", "java.lang.String", SERVICE_SCOPE },
				{ "releasableAmount", "java.math.BigDecimal", SERVICE_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "dueDateList",  "java.util.List", SERVICE_SCOPE},
				{ "filterLocationList",  "java.util.List", SERVICE_SCOPE },
				{ "statementName",  "java.util.List", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "alertRequired",  "java.util.List", SERVICE_SCOPE },
				{ "isStockDetailsAdded",  "java.util.List", SERVICE_SCOPE },
				{ "dpShare", "java.lang.String", SERVICE_SCOPE },
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		if (map.get("no_template_gc")!=null &&((String) map.get("no_template_gc")).equals("true")) {
			result.put( "from_page", "" );
			result.put("tab", CollateralConstant.TAB_GENERAL);
//			result.put( "fundedShare", "" );
			
			result.put( "dpShare", "" );
			result.put( "calculatedDP", "" );
			result.put( "isEditable", "" );
			result.put( "releasableAmount", "");
			result.put( "calculatedDP", "");
			result.put( "remarks", "" );
			result.put( "dueDate", "" );
			result.put( "displayList",  "" );
			result.put( "dueDateList",  "");
			result.put( "filterLocationList", "" );
			result.put( "statementName", "" );
			result.put( "trxID", "" );
			result.put( "alertRequired","" );
			result.put( "isStockDetailsAdded", "" );
			
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return temp;
		}else{
			
		

		String event = (String) map.get("event");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		
		if ( event!=null && !event.endsWith("return")) {
			result.put("from_page", (String) map.get("event"));
		}
		else {
			DefaultLogger.debug(this, "<<<<<<<<<<< readAssetGenChargeCommand - event: " + event);
			ITrxContext ctx = itrxValue.getTrxContext();
            //Andy Wong, 11 Feb 2009: check for ctx not null to fix nullpointer
            if(ctx!=null) {
			    DefaultLogger.debug(this, "<<<<<<<<<<< readAssetGenChargeCommand - remarks: " + ctx.getRemarks());
			    result.put("remarks", ctx.getRemarks());
            }
		}
		String idStr = (String) map.get("trxID");
//		String fundedShareVal = (String) map.get("fundedShare");
		
		String fundedShareVal = (String) map.get("dpShare");
		String calculatedDPVal = (String) map.get("calculatedDP");
		
		String dueDateValue = (String) map.get("dueDate");
		
		String[] splittArray = null;
		 String selectedDueDate = "";
		 String selectedDocCode = "";
	    if (dueDateValue != null && !"".equalsIgnoreCase(dueDateValue)){
	         splittArray = dueDateValue.split(",");
	          selectedDueDate = (String) splittArray[0];
	          selectedDocCode = (String) splittArray[1];
	    }
		
	   
		
		/*
		 * if (event.equals(AssetGenChargeAction.EVENT_PREPARE_UPDATE) ||
		 * event.equals(AssetGenChargeAction.EVENT_PROCESS_UPDATE)) { boolean
		 * isCPC = false; ICommonUser user =
		 * (ICommonUser)map.get(IGlobalConstant.USER); ITeam userTeam =
		 * (ITeam)map.get(IGlobalConstant.USER_TEAM); TOP_LOOP: for(int
		 * i=0;i<userTeam.getTeamMemberships().length;i++){//parse team
		 * membership to validate user first for(int j=0;
		 * j<userTeam.getTeamMemberships()[i].getTeamMembers().length;j++){
		 * //parse team memebers to get the team member first..
		 * if(userTeam.getTeamMemberships
		 * ()[i].getTeamMembers()[j].getTeamMemberUser
		 * ().getUserID()==user.getUserID()){
		 * if(userTeam.getTeamMemberships()[i]
		 * .getTeamTypeMembership().getMembershipID
		 * ()==ICMSConstant.TEAM_TYPE_CPC_MAKER){ isCPC = true;
		 * DefaultLogger.debug(this, "User is cpc maker..."); break TOP_LOOP; }
		 * } } } if (!isCPC) { result.put("forwardPage",
		 * AssetGenChargeAction.EVENT_READ); } else { result.put("forwardPage",
		 * event); } }
		 */

		//Added by Anil =========Start===============
		BigDecimal calculatedDP= new BigDecimal(0);
		BigDecimal fundedShare= new BigDecimal(0);
		
		ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
		long customerID=0;
		try {
			String customerIDstr = collateralProxy.getCustomerIDByCollateralID(itrxValue.getCollateral().getCollateralID());
			if(customerIDstr!=null&&!"".equals(customerIDstr.trim())){
				customerID=Long.parseLong(customerIDstr);
			}
		} catch (CollateralException e) {
			e.printStackTrace();
		}
//		String hdfcBankShare=(String) map.get("fundedShare");
		
		String hdfcBankShare=(String) map.get("dpShare");
		/*if(customerID>0){
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		ICMSCustomer cust = custProxy.getCustomer(customerID);
		fundedSharePercent = cust.getFundedSharePercent();
		}*/
		if(hdfcBankShare!=null && !"".equals(hdfcBankShare.trim())){
//			result.put("fundedShare", hdfcBankShare);
			
			result.put("dpShare", hdfcBankShare);
			fundedShare= new BigDecimal(hdfcBankShare);
			
		}else{
			hdfcBankShare = "0";
//			result.put("fundedShare", "0");
			
			result.put("dpShare", "0");
			/*if(customerID==0){
				exceptionMap.put("fundedShareError", new ActionMessage("collatral.mapping.mandatory"));
			}else{
				exceptionMap.put("fundedShareError", new ActionMessage("customer.financialdetail.mandatory"));
			}*/
		}
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		Date dueDate=null;
		//==========================================
		String dueDateStr ="";		 
		String docCode ="";		 
		/*if("process".equals(event)){
			
		 IGeneralChargeDetails[] generalChargeDetailsStaging = ((IGeneralCharge) itrxValue.getStagingCollateral()).getGeneralChargeDetails();
//		 IGeneralChargeDetails[] generalChargeDetailsActual = ((IGeneralCharge) itrxValue.getCollateral()).getGeneralChargeDetails();
		 for (int k = 0; k < generalChargeDetailsStaging.length; k++) {
			IGeneralChargeDetails iGeneralChargeDetailsStg = generalChargeDetailsStaging[k];
			if(IGeneralChargeDetails.STATUS_PENDING.equals(iGeneralChargeDetailsStg.getStatus())){
				dueDate=iGeneralChargeDetailsStg.getDueDate();
				dueDateStr =iGeneralChargeDetailsStg.getDueDate()+","+iGeneralChargeDetailsStg.getDocCode();
				result.put("dueDate", dueDateStr);
				result.put("fundedShare", iGeneralChargeDetailsStg.getFundedShare());
				result.put("calculatedDP", iGeneralChargeDetailsStg.getCalculatedDP());
				break;
			}
		}
		}*/

		//==========================================
		
		//retrieving due dates
	    List retrivedDueDateList=(List)collateralProxy.getRecurrentDueDateListByCustomerAndCollatralID(customerID,0);
	    List dueDateList= new ArrayList();
	    if(retrivedDueDateList!=null && retrivedDueDateList.size()>0){
//	    	Collections.sort(retrivedDueDateList,Collections.reverseOrder());
	    	dueDateList=AssetGenChargeHelper.getDisplayDueDateList(retrivedDueDateList);
//	    	((LabelValueBean)dueDateList.get(0)).getValue();
//	    	dueDate = (Date) retrivedDueDateList.get(0);
	    /*	String dueDateStr = ((LabelValueBean)dueDateList.get(0)).getValue();
	    	if(dueDateStr!=null){
	    		dueDate=DateUtil.convertDate(locale,dueDateStr);
	    		result.put("dueDate", UIUtil.formatDate(dueDate));
	    	}*/
	    }
	    //IGeneralCharge newCollateral = (IGeneralCharge) itrxValue.getStagingCollateral();
	    IGeneralCharge actualCollateral = (IGeneralCharge) itrxValue.getCollateral();
		
		List displayList= new ArrayList();
		String isEditable="Y";
		String statementName ="";
		Date latestDueDate = null;
		String parentPageFrom=(String) map.get("parentPageFrom");
		if((("REJECTED".equals(itrxValue.getStatus()) ||"PENDING_UPDATE".equals(itrxValue.getStatus()) ||"DRAFT".equals(itrxValue.getStatus()) ||"ACTIVE".equals(itrxValue.getStatus())) 
				&& ("process".equals(event)
						||"process_return".equals(event)
						||"process_update".equals(event)
						||"prepare_close".equals(event)
						|| "track".equals(event) || "read".equals(event)
				
				))
		//	|| ("cancle_stock_detail".equals(event)/*&& "ASSET_PROCESS".equals(parentPageFrom)*/)
			
			){
			List existingDueDateList= new ArrayList();
			IGeneralChargeDetails[] actualgeneralChargeDetails = actualCollateral.getGeneralChargeDetails();
			if(actualgeneralChargeDetails!=null ){
				for (int i = 0; i < actualgeneralChargeDetails.length; i++) {
					IGeneralChargeDetails iGeneralChargeDetails = actualgeneralChargeDetails[i];
					if(iGeneralChargeDetails!=null && ("APPROVED").equalsIgnoreCase(iGeneralChargeDetails.getStatus())){
					existingDueDateList.add(iGeneralChargeDetails.getDueDate());
					}
				}
			}
			if(existingDueDateList.size()>=1){
				Collections.sort(existingDueDateList,Collections.reverseOrder());
				latestDueDate = (Date) existingDueDateList.get(0);
			}
		
			/*IGeneralChargeDetails[] actualGeneralChargeDetails = newCollateral.getGeneralChargeDetails();
			IGeneralChargeDetails actualChargeDetails;
			IGeneralChargeStockDetails[] actualGeneralChargeStockDetails;
			if(actualGeneralChargeDetails!=null){
				for (int i = 0; i < actualGeneralChargeDetails.length; i++) {
					actualChargeDetails = actualGeneralChargeDetails[i];
					 
					 if(actualChargeDetails!=null && actualChargeDetails.getDueDate().equals(latestDueDate)){
						 actualGeneralChargeStockDetails = actualChargeDetails.getGeneralChargeStockDetails();
							//
							result.put("calculatedDP", actualChargeDetails.getCalculatedDP());
							result.put("fundedShare", actualChargeDetails.getFundedShare());
							
						}
			
				}
			}*/
			
			IGeneralChargeDetails[] generalChargeDetails = actualCollateral.getGeneralChargeDetails();
			if(generalChargeDetails!=null){
			for (int k = 0; k < generalChargeDetails.length; k++) {
				IGeneralChargeDetails iGeneralChargeDetailsStg = generalChargeDetails[k];
				if(IGeneralChargeDetails.STATUS_PENDING.equals(iGeneralChargeDetailsStg.getStatus())	
				||IGeneralChargeDetails.STATUS_INSURANCE_UPDATED.equals(iGeneralChargeDetailsStg.getStatus())){
					dueDate=iGeneralChargeDetailsStg.getDueDate();
					docCode=iGeneralChargeDetailsStg.getDocCode();
					dueDateStr =DateUtil.formatDate(locale, iGeneralChargeDetailsStg.getDueDate())+","+iGeneralChargeDetailsStg.getDocCode();
					result.put("dueDate", dueDateStr);
//					result.put("fundedShare", iGeneralChargeDetailsStg.getFundedShare());
					
					//Uma Khot:Cam upload and Dp field calculation CR
					result.put("dpShare", iGeneralChargeDetailsStg.getDpShare());
					
					result.put("calculatedDP", iGeneralChargeDetailsStg.getCalculatedDP());
					//hdfcBankShare = iGeneralChargeDetailsStg.getFundedShare();
					break;
				}
				else if("ACTIVE".equals(itrxValue.getStatus()) && ("REJECTED".equals(itrxValue.getFromState()) || "PENDING_UPDATE".equals(itrxValue.getFromState()))){
					if(iGeneralChargeDetailsStg!=null && iGeneralChargeDetailsStg.getDocCode().equals(selectedDocCode))
					{
						dueDate=iGeneralChargeDetailsStg.getDueDate();
						docCode=iGeneralChargeDetailsStg.getDocCode();
						dueDateStr =DateUtil.formatDate(locale, iGeneralChargeDetailsStg.getDueDate())+","+iGeneralChargeDetailsStg.getDocCode();
						result.put("dueDate", dueDateStr);
//						result.put("fundedShare", iGeneralChargeDetailsStg.getFundedShare());
						
						//Uma Khot:Cam upload and Dp field calculation CR
						result.put("dpShare", iGeneralChargeDetailsStg.getDpShare());
						result.put("calculatedDP", iGeneralChargeDetailsStg.getCalculatedDP());
						break;
					}
				}
			}
		}
			
			try {
				statementName = collateralProxy.getStatementNameByDocCode(docCode);
			} catch (SearchDAOException e1) {
				e1.printStackTrace();
			} catch (CollateralException e1) {
				e1.printStackTrace();
			}
			if(dueDate!=null){
			HashMap distinctLocation=new HashMap();
			IGeneralChargeDetails[] existingGeneralChargeDetails = actualCollateral.getGeneralChargeDetails();
			IGeneralChargeDetails existingChargeDetails;
			IGeneralChargeStockDetails[] existingGeneralChargeStockDetails;
			if(existingGeneralChargeDetails!=null){
				for (int i = 0; i < existingGeneralChargeDetails.length; i++) {
					 existingChargeDetails = existingGeneralChargeDetails[i];
				 
//					if(existingChargeDetails!=null && existingChargeDetails.getDueDate().equals(dueDate)){
					if(existingChargeDetails!=null && null != existingChargeDetails.getDocCode() && existingChargeDetails.getDocCode().equals(docCode)){
						existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
						for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
							IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
							if(!distinctLocation.containsKey(Long.toString(existingStockDetails.getLocationId()))){
								distinctLocation.put(Long.toString(existingStockDetails.getLocationId()),Long.toString(existingStockDetails.getLocationId()));
							}
						}
						if(IGeneralChargeDetails.STATUS_PENDING.equals( existingChargeDetails.getStatus())){
							isEditable="Y";
						}else{
							isEditable="N";
						}
						break;
					}else{
						isEditable="Y";
					}
				}
			}else{
				isEditable="Y";
			}
			
					
			Collection values = distinctLocation.values();
			
			List addedLocations= new ArrayList();
			addedLocations.addAll(values);
			
			
			//Preparing Display OB
			BigDecimal totalLonableForDP= new BigDecimal(0);
			AssetGenChargeStockDetailsHelperForm helperFrom;
			ICityProxyManager proxy= (ICityProxyManager)BeanHouse.get("cityProxy");
			IGeneralChargeDetails[] newGCDetails = actualCollateral.getGeneralChargeDetails();
			for (Iterator iterator = addedLocations.iterator(); iterator.hasNext();) {
				long locationID = Long.parseLong((String)iterator.next());
				helperFrom= new AssetGenChargeStockDetailsHelperForm();
				helperFrom.setLocationID(Long.toString(locationID));
				String cityName="";
				try {
					if(ICMSConstant.CITY_VALUE_ALL.equals(String.valueOf(locationID))){
						cityName=ICMSConstant.CITY_LABEL_ALL;
					}else{
						ICityTrxValue cityById = proxy.getCityById(locationID);
						cityName = cityById.getActualCity().getCityName();
					}
				} catch (NoSuchGeographyException e) {
					e.printStackTrace();
				} catch (TrxParameterException e) {
					e.printStackTrace();
				} catch (TransactionException e) {
					e.printStackTrace();
				}
				helperFrom.setLocationName(cityName);
				helperFrom.setDueDate(dueDateStr);
				
				
				String calculatedDPValue = "";
				if(newGCDetails!=null){
					for (int i = 0; i < newGCDetails.length; i++) {
						existingChargeDetails = newGCDetails[i];
						if(existingChargeDetails!=null && null != existingChargeDetails.getDocCode()  && existingChargeDetails.getDocCode().equals(docCode)){
	//						BigDecimal totalLonable= new BigDecimal(0);
							BigDecimal totalLonableAsset= new BigDecimal(0);
							BigDecimal totalLonableLiability= new BigDecimal(0);
							existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
							for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
								IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
								if(locationID==(existingStockDetails.getLocationId())){
									if("CurrentAsset".equals(existingStockDetails.getStockType()) && ("YES".equals(existingStockDetails.getApplicableForDp()))){
										totalLonableAsset=totalLonableAsset.add(new BigDecimal(existingStockDetails.getLonable()));
									}else if("CurrentLiabilities".equals(existingStockDetails.getStockType()) && ("YES".equals(existingStockDetails.getApplicableForDp()))){
										totalLonableLiability=totalLonableLiability.add(new BigDecimal(existingStockDetails.getLonable()));
									}
								}
								
							}
							helperFrom.setTotalLonable((totalLonableAsset.subtract(totalLonableLiability)).toString());
							
							totalLonableForDP=totalLonableForDP.add(new BigDecimal(helperFrom.getTotalLonable()));
							break;
						}
					}
				}
				displayList.add(helperFrom);
			}
			
			
			//calculatedDP=totalLonableForDP.multiply(new BigDecimal(hdfcBankShare));
			//calculatedDP=calculatedDP.divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);
		}else{
			isEditable="Y";
		}
			//result.put("calculatedDP", calculatedDP.toString());
			result.put("displayList", displayList);
			//result.put("fundedShare", hdfcBankShare);
			result.put("statementName", statementName);
			result.put("isEditable", isEditable);
			
	}else if("read".equals(event)){
		result.put("displayList", displayList);
		result.put("calculatedDP", "0.00");
		result.put("statementName", "");
		result.put("isEditable", "N");
		result.put("isStockDetailsAdded", "N");
		result.put("dueDate", "");
	}else if("prepare_update".equals(event)){
		result.put("displayList", new ArrayList());
		result.put("dueDate", "");
		result.put("calculatedDP", "0.00");
		result.put("statementName", "");
		result.put("isEditable", "N");
		result.put("isStockDetailsAdded", "N");
		result.put("filterLocationList", new ArrayList());
	}else if("edit_prepare_stock_details".equals(event) 
			|| "maker_delete_list_insurance".equals(event) 
			|| "maker_edit_list_insurance".equals(event)
			|| "maker_create_insurance".equals(event)
			|| "view_stock_details".equals(event)
			|| "cancle_stock_detail".equals(event) 
			||"maker_edit_cancle_insurance".equals(event)
			||"maker_cancle_create_insurance".equals(event)
		|| "track_return".equals(event) 
		|| "close_return".equals(event)
		||"maker_cancle_create_insurance".equals(event) 
		|| "view_stock_details_track".equals(event) 
		|| "view_stock_details_close".equals(event)
		|| "cancle_stock_detail_close".equals(event)
		||"filter_locations".equals(event)){
	
		result.put("dueDate", dueDateValue);
		result.put("calculatedDP", calculatedDPVal);
//		result.put("fundedShare",fundedShareVal );
		
		result.put("dpShare",fundedShareVal );
	}
	else if("create_stock_detail".equals(event)){
		List existingDueDateList= new ArrayList();
		IGeneralChargeDetails[] actualgeneralChargeDetails = actualCollateral.getGeneralChargeDetails();
		if(actualgeneralChargeDetails!=null ){
			for (int i = 0; i < actualgeneralChargeDetails.length; i++) {
				IGeneralChargeDetails iGeneralChargeDetails = actualgeneralChargeDetails[i];
				if(iGeneralChargeDetails!=null && ("APPROVED").equalsIgnoreCase(iGeneralChargeDetails.getStatus())){
				existingDueDateList.add(iGeneralChargeDetails.getDueDate());
				}
			}
		}	
		
	}
	else if ("edit_stock_details".equals(event) && "ACTIVE".equals(itrxValue.getStatus()))
	{
		HashMap distinctLocation=new HashMap();
		IGeneralChargeDetails[] existingGeneralChargeDetails = actualCollateral.getGeneralChargeDetails();
		IGeneralChargeDetails existingChargeDetails;
		IGeneralChargeStockDetails[] existingGeneralChargeStockDetails;
		if(existingGeneralChargeDetails!=null){
			for (int i = 0; i < existingGeneralChargeDetails.length; i++) {
				 existingChargeDetails = existingGeneralChargeDetails[i];
//				if(existingChargeDetails!=null && existingChargeDetails.getDueDate().equals(dueDate)){
				if(existingChargeDetails!=null && null != existingChargeDetails.getDocCode()  && existingChargeDetails.getDocCode().equals(selectedDocCode)){
					existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
					for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
						IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
						if(!distinctLocation.containsKey(Long.toString(existingStockDetails.getLocationId()))){
							distinctLocation.put(Long.toString(existingStockDetails.getLocationId()),Long.toString(existingStockDetails.getLocationId()));
						}
					}
					
				}
			}
		}
		Collection values = distinctLocation.values();
		List addedLocations= new ArrayList();
		addedLocations.addAll(values);
		String fundedSharePercent = "";
		String calculatedDPValue = "";
		//Preparing Display OB
		BigDecimal totalLonableForDP= new BigDecimal(0);
		AssetGenChargeStockDetailsHelperForm helperFrom;
		ICityProxyManager proxy= (ICityProxyManager)BeanHouse.get("cityProxy");
		IGeneralChargeDetails[] newGCDetails = actualCollateral.getGeneralChargeDetails();
		
		if(newGCDetails!=null){
			for (int i = 0; i < newGCDetails.length; i++) {
				existingChargeDetails = newGCDetails[i];
				if(existingChargeDetails!=null && null != existingChargeDetails.getDocCode()  && existingChargeDetails.getDocCode().equals(selectedDocCode) /*&& existingChargeDetails.getDueDate().equals(DateUtil.convertDate(locale,selectedDueDate))*/){
                   existingChargeDetails.setStatus("PENDING");
				}
				newGCDetails[i]=existingChargeDetails;
			}
		}
		
		
		for (Iterator iterator = addedLocations.iterator(); iterator.hasNext();) {
			long locationID = Long.parseLong((String)iterator.next());
			helperFrom= new AssetGenChargeStockDetailsHelperForm();
			helperFrom.setLocationID(Long.toString(locationID));
			String cityName="";
			try {
				if(ICMSConstant.CITY_VALUE_ALL.equals(String.valueOf(locationID))){
					cityName=ICMSConstant.CITY_LABEL_ALL;
				}else{
					ICityTrxValue cityById = proxy.getCityById(locationID);
					cityName = cityById.getActualCity().getCityName();
				}
			} catch (NoSuchGeographyException e) {
				e.printStackTrace();
			} catch (TrxParameterException e) {
				e.printStackTrace();
			} catch (TransactionException e) {
				e.printStackTrace();
			}
			helperFrom.setLocationName(cityName);
			helperFrom.setDueDate(dueDateValue);
			
			if(newGCDetails!=null){
				for (int i = 0; i < newGCDetails.length; i++) {
					existingChargeDetails = newGCDetails[i];
					if(existingChargeDetails!=null && null != existingChargeDetails.getDocCode() && existingChargeDetails.getDocCode().equals(selectedDocCode)){
//						BigDecimal totalLonable= new BigDecimal(0);
						BigDecimal totalLonableAsset= new BigDecimal(0);
						BigDecimal totalLonableLiability= new BigDecimal(0);
						existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
						for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
							IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
							if(locationID==(existingStockDetails.getLocationId())){
								if("CurrentAsset".equals(existingStockDetails.getStockType()) && ("YES".equals(existingStockDetails.getApplicableForDp()))){
									totalLonableAsset=totalLonableAsset.add(new BigDecimal(existingStockDetails.getLonable()));
								}else if("CurrentLiabilities".equals(existingStockDetails.getStockType()) && ("YES".equals(existingStockDetails.getApplicableForDp()))){
									totalLonableLiability=totalLonableLiability.add(new BigDecimal(existingStockDetails.getLonable()));
								}
							}
						}
						//existingChargeDetails.setStatus("PENDING");
						helperFrom.setTotalLonable((totalLonableAsset.subtract(totalLonableLiability)).toString());
						totalLonableForDP=totalLonableForDP.add(new BigDecimal(helperFrom.getTotalLonable()));
						calculatedDPValue = existingChargeDetails.getCalculatedDP();
						//fundedSharePercent = existingChargeDetails.getFundedShare();
						
						//Uma Khot:Cam upload and Dp field calculation CR
						fundedSharePercent = existingChargeDetails.getDpShare();
						break;
					}
				}
			}
			displayList.add(helperFrom);
		}
		try {
			statementName = collateralProxy.getStatementNameByDocCode(selectedDocCode);
		} catch (SearchDAOException e1) {
			e1.printStackTrace();
		} catch (CollateralException e1) {
			e1.printStackTrace();
		}
		
		//calculatedDP=totalLonableForDP.multiply((fundedShare));
		//calculatedDP=calculatedDP.divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);
		result.put("dueDate", dueDateValue);
		result.put("calculatedDP", calculatedDPValue);
		//result.put("fundedShare", fundedSharePercent);
		
		result.put("dpShare", fundedSharePercent);
		result.put("statementName", statementName);
		
	}
		result.put("trxID", idStr);
		//For DP Alert
		BigDecimal releasedAmount=new BigDecimal(0);
		ILimit obLimit=new OBLimit();
		try {
			obLimit= collateralProxy.getReleasableAmountByCollateralID(itrxValue.getCollateral().getCollateralID());
			if(obLimit!=null){
				if(obLimit.getTotalReleasedAmount()!=null){
					releasedAmount =new BigDecimal(obLimit.getTotalReleasedAmount());		
				}
				result.put("alertRequired", obLimit.getIsDP());
			}
		} catch (SearchDAOException e) {
			e.printStackTrace();
		} catch (CollateralException e) {
			e.printStackTrace();
		}
		//Added by Anil =========End=================
		
		//result.put("alertRequired", obLimit.getIsDP());
		result.put("releasableAmount", releasedAmount);
		result.put("forwardPage", event);
		result.put("serviceColObj", itrxValue);
		result.put("tab", CollateralConstant.TAB_GENERAL);
		result.put("dueDateList", dueDateList);
		
//		result.put("filterLocationList", new ArrayList());
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
		}
	}
	

}
