/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;

import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRMJdbc;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.json.command.PrepareSendReceivePartyCommand;
import com.integrosys.cms.app.json.dao.ScmPartyDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBOtherCovenant;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

import com.integrosys.cms.ui.ecbf.counterparty.ClimesToECBFHelper;

import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails.IOtherCovenantDetailsDAO;



/**
 * Describe this class. Purpose: for checker to approve the transaction
 * Description: command that let the checker to approve the transaction that
 * being make by the maker
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class CheckerApproveAADetailCommand extends AbstractCommand implements ICommonEventConstant {

	

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
			
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE },
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, ICommonUser.class.getName(), GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, String.class.getName(), GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }		
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
		return (new String[][] { { IGlobalConstant.REQUEST_ERROR_MSG, "java.lang.String", REQUEST_SCOPE },
				//{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult", REQUEST_SCOPE },
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult", SERVICE_SCOPE },
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here approval for Interest Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		ILimitProfileTrxValue limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
		ICMSTrxResult res = null;

		List otherCovenantDetailsList;
		String event;
		event = (String) map.get("event");
		String othercovenantID = null;

		ICommonUser user=(ICommonUser)map.get(IGlobalConstant.USER);
		String teamTypeMembershipID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
		boolean isLoaAuthorizer = String.valueOf(ICMSConstant.TEAM_TYPE_SSC_CHECKER).equals(teamTypeMembershipID) || 
				String.valueOf(ICMSConstant.CPU_MAKER_CHECKER).equals(teamTypeMembershipID);
		

		Date oldExpiryDate=null;
		Date newExpiryDate=null;
		
		//added by santosh for ubs CR
		Date oldExtendedNextReviewDate=null;
		Date newExtendedNextReviewDate=null;
		long limitProfileId=0;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		System.out.println("Inside CheckerApproveAADetailCommand.java..");
		 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		 IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		 IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		 
		if(null!=limitProfileTrxVal.getLimitProfile()) {
			System.out.println("CheckerApproveAADetailCommand.java=>L168----------->"+trxContext);
			limitProfileId = limitProfileTrxVal.getLimitProfile().getLimitProfileID();
			System.out.println("CheckerApproveAADetailCommand.java=>limitProfileId-----------L168>"+limitProfileId);
			oldExtendedNextReviewDate = limitProfileTrxVal.getLimitProfile().getExtendedNextReviewDate();
			newExtendedNextReviewDate = limitProfileTrxVal.getStagingLimitProfile().getExtendedNextReviewDate();
			
			oldExpiryDate = limitProfileTrxVal.getLimitProfile().getNextAnnualReviewDate();
			newExpiryDate = limitProfileTrxVal.getStagingLimitProfile().getNextAnnualReviewDate();
			
			if((oldExpiryDate == null && newExpiryDate != null) || 
						(oldExpiryDate != null && newExpiryDate != null && compareDate(locale,oldExpiryDate,newExpiryDate))) {
				DefaultLogger.debug(this, "CheckerApproveAADetailCommand.java=>Updating Sanctioned Amount Flag from addCAMDetails for partyId :"+limitProfileTrxVal.getLimitProfile().getLEReference() );
				CustomerDAOFactory.getDAO().updateSanctionedAmountUpdatedFlag(limitProfileTrxVal.getLimitProfile().getLEReference(), ICMSConstant.YES);
				
			}


		}
		System.out.println("CheckerApproveAADetailCommand.java=>limitProfileId-----------L183>"+limitProfileId+"***");
		System.out.println("CheckerApproveAADetailCommand.java..oldExtendedNextReviewDate=>"+oldExtendedNextReviewDate+"***newExtendedNextReviewDate=>"+newExtendedNextReviewDate+"***oldExpiryDate=>"+oldExpiryDate+"***newExpiryDate=>"+newExpiryDate+"***");
		//end santosh
		//DefaultLogger.debug(this, "Inside doExecute()  = " + trxContext);
		boolean sendReqToecbf = false;
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			//DefaultLogger.debug(this, " >>>Debug:::CheckerApproveAADetailCommand Before reject TrxValue = "	+ AccessorUtil.printMethodValue(limitProfileTrxVal));
			//added by santosh for ubs CR
			System.out.println("CheckerApproveAADetailCommand.java=>limitProfileId-----------L193>"+limitProfileId);
			if(limitProfileId!=0 && null!=oldExtendedNextReviewDate && null!=newExtendedNextReviewDate ) {
				System.out.println("CheckerApproveAADetailCommand.java=>limitProfileId-----------L194>"+limitProfileId+"**going to compareDate(locale,oldExtendedNextReviewDate,newExtendedNextReviewDate)..");
				if(compareDate(locale,oldExtendedNextReviewDate,newExtendedNextReviewDate)) {
					System.out.println("CheckerApproveAADetailCommand.java=>CAM Extention date changed");
				//CAM Extention date changed
					if(isActive(limitProfileId)) {
						System.out.println("CheckerApproveAADetailCommand.java=>limitProfileId is Active-----------L199>"+limitProfileId);		
						if (limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_PENDING_DELETE)) {
							System.out.println("CheckerApproveAADetailCommand.java=>limitProfileTrxVal-----------L201>"+limitProfileTrxVal);
							res = proxy.checkerApproveDeleteLimitProfile(trxContext, limitProfileTrxVal);
							System.out.println("CheckerApproveAADetailCommand.java=>After res = proxy.checkerApproveDeleteLimitProfile(trxContext, limitProfileTrxVal)");
						}
						else {
							//update ram rating statement after changing ram rating year-Ram rating CR. 
							updateRAMRatingStatementStatus(generalParamEntries,limitProfileTrxVal);
							System.out.println("CheckerApproveAADetailCommand.java=>limitProfileTrxVal-----------L207>"+limitProfileTrxVal);
							System.out.println("CheckerApproveAADetailCommand.java=>trxContext-----------L208>"+trxContext);
							res = proxy.checkerApproveUpdateLimitProfile(trxContext, limitProfileTrxVal);
							
							System.out.println("CheckerApproveAADetailCommand.java=>After res-----------L211>"+res);
							
							sendReqToecbf = true;
							try {
								MILimitUIHelper helper = new MILimitUIHelper();
								SBMILmtProxy lmtProxy = helper.getSBMILmtProxy();
								ILimitDAO dao1 = LimitDAOFactory.getDAO();

								ResourceBundle bundle = ResourceBundle.getBundle("ofa");
//								String lineNumbers = split(bundle.getString("ubs.limit.upload.lineNo"));
								System.out.println("CheckerApproveAADetailCommand.java=>Going for split( dao1.getExclusionLine())..");
								String lineNumbers = split( dao1.getExclusionLine());
								System.out.println("CheckerApproveAADetailCommand.java=>After split( dao1.getExclusionLine())..limitProfileId=>"+limitProfileId+"***lineNumbers=>"+lineNumbers+"****");
								String facilitySystem = "'" + bundle.getString("fcubs.systemName") + "','"+ bundle.getString("ubs.systemName") + "'";
								List<String> lmtIdList = dao1.getlmtId(limitProfileId, facilitySystem, lineNumbers);
								System.out.println("CheckerApproveAADetailCommand.java=>After dao1.getlmtId(limitProfileId, facilitySystem, lineNumbers)=>lmtIdList=>"+lmtIdList);
								Date d = DateUtil.getDate();
								String dateFormat = "yyMMdd";
								SimpleDateFormat s = new SimpleDateFormat(dateFormat);
								String date = s.format(d);

								Date appDate = null;
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
								for (int i = 0; i < generalParamEntries.length; i++) {
									if (generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")) {
										System.out.println("CheckerApproveAADetailCommand.java=>generalParamEntries[i].getParamValue():"
												+ generalParamEntries[i].getParamValue());
										appDate = new Date(generalParamEntries[i].getParamValue());
									}
								}
								System.out.println("CheckerApproveAADetailCommand.java=>appDate=>"+appDate+"****date=>"+date);
								Map<String, String> availableActual = new HashMap<String, String>();
								Map<String, String> expiryActual = new HashMap<String, String>();
								Map<String, String> availableExpiryActual = new HashMap<String, String>();

								for (int k = 0; k < lmtIdList.size(); k++) {
									System.out.println("CheckerApproveAADetailCommand.java=>Going to getMapLineDetails()=> lmtIdList.size()=>"+lmtIdList.size()+"***lmtIdList.get(k)=>"+lmtIdList.get(k));
									ILimitTrxValue lmtTrxObj = lmtProxy.searchLimitByLmtId(lmtIdList.get(k));
									getMapLineDetails(lmtTrxObj, dao1, date, appDate, availableActual, expiryActual,availableExpiryActual);
									System.out.println("CheckerApproveAADetailCommand.java=>After getMapLineDetails()...");
								}
								
								if (availableActual.size() > 0) {
									System.out.println("CheckerApproveAADetailCommand.java=>Going to dao1.updateStageActualLine L260...");
									dao1.updateStageActualLine("Yes", "PENDING", newExtendedNextReviewDate,availableActual, "availableActual");
								}
								if (expiryActual.size() > 0) {
									System.out.println("CheckerApproveAADetailCommand.java=>Going to dao1.updateStageActualLine L264...");
									dao1.updateStageActualLine("", "PENDING", newExtendedNextReviewDate, expiryActual,"expiryActual");
								}
								if (availableExpiryActual.size() > 0) {
									System.out.println("CheckerApproveAADetailCommand.java=>Going to dao1.updateStageActualLine L268...");
									dao1.updateStageActualLine("Yes", "PENDING", newExtendedNextReviewDate,availableExpiryActual, "availableExpiryActual");
								}
								System.out.println("CheckerApproveAADetailCommand.java=>Going to PSR limit conditions L271...");
								//for PSR limit
								Map<String,String> expiryActualForPSR=new HashMap<String,String>();
								String facilitySystemForPSR = "'" + bundle.getString("psr.systemName") + "'";
								List<String> psrlmtIdList=dao1.getPSRlmtId(limitProfileId,facilitySystemForPSR);
								
								for(int p=0;p<psrlmtIdList.size();p++){
									ILimitTrxValue lmtTrxObj = lmtProxy.searchLimitByLmtId(psrlmtIdList.get(p));
									getMapPSRLineDetails(lmtTrxObj,dao1,date,appDate,expiryActualForPSR);
								}
								
								if(expiryActualForPSR.size()>0){
									dao1.updatePSRStageActualLine("","PENDING",newExtendedNextReviewDate,expiryActualForPSR,"expiryActualForPSR");
								}
								//end PSR limit

							} 
							catch (Exception e) {
								System.out.println("Exception CheckerApproveAADetailCommand.java=> L289...e=>"+e);
								e.printStackTrace();
								DefaultLogger.debug(this, e.getMessage());
							}
						}
						resultMap.put("request.ITrxResult", res);
						DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> Transaction Object >> "+res.getTrxValue());
					} 
					else {
						System.out.println("Inside else-----------L201>"+limitProfileTrxVal);
						resultMap.put("deactiveFacility", "deactive");
					}
				}
				//CAM Extention date not changed
				else {
					System.out.println("Exception CheckerApproveAADetailCommand.java=> L304...CAM Extention date not changed..");
					if (limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_PENDING_DELETE)) {
						res = proxy.checkerApproveDeleteLimitProfile(trxContext, limitProfileTrxVal);
					}
					else {
                       //update ram rating statement after changing ram rating year-Ram rating CR. 
						updateRAMRatingStatementStatus(generalParamEntries,limitProfileTrxVal);
						
						res = proxy.checkerApproveUpdateLimitProfile(trxContext, limitProfileTrxVal);
						sendReqToecbf = true;
					}
					resultMap.put("request.ITrxResult", res);
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> Transaction Object >> "+res.getTrxValue());
			   }
			}
			else {
				System.out.println("CheckerApproveAADetailCommand.java=> L320..");
				if (limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_PENDING_DELETE)) {
					res = proxy.checkerApproveDeleteLimitProfile(trxContext, limitProfileTrxVal);
				}
				else {
					res = proxy.checkerApproveUpdateLimitProfile(trxContext, limitProfileTrxVal);
					sendReqToecbf = true;
				}
				resultMap.put("request.ITrxResult", res);
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> Transaction Object >> "+res.getTrxValue());
		   }
			System.out.println("CheckerApproveAADetailCommand.java=> L331..sendReqToecbf=>"+sendReqToecbf);
		//end santosh
			if(sendReqToecbf) {
				ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
				try {
					ClimesToECBFHelper.sendRequest(customer);
				} catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.error(this, "Exception caught inside sendRequest while sending data to ecbf party webservice with error: " + e.getMessage(), e);
				}
			}
		}
		catch (LimitException e) {
			System.out.println("Exception CheckerApproveAADetailCommand.java=> L344..e=>"+e);
			if ((e.getErrorCode() != null) && e.getErrorCode().equals(LimitException.ERR_CANNOT_DELETE_LMT_PROFILE)) {
				System.out.println("Exception CheckerApproveAADetailCommand.java=> L346..");
				resultMap.put(IGlobalConstant.REQUEST_ERROR_MSG, "The record can not be deleted");

			}
			else if ((e.getErrorCode() != null) && e.getErrorCode().equals(LimitException.ERR_DUPLICATE_AA_NUM)) {
				System.out.println("Exception CheckerApproveAADetailCommand.java=> L351..");
				resultMap.put(IGlobalConstant.REQUEST_ERROR_MSG,
						"CAM Number is duplicate. The record can not be approved");

			}
			else {
				System.out.println("Exception CheckerApproveAADetailCommand.java=> L357..");
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				e.printStackTrace();
				throw (new CommandProcessingException(e.getMessage()));
			}
		}catch (Exception e) {
			System.out.println("Exception CheckerApproveAADetailCommand.java=> L363..e=>"+e);
			e.printStackTrace();
		}


		IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO)BeanHouse.get("otherCoveantDeatilsDAO");
		
		OBOtherCovenant ob,ob2;
		otherCovenantDetailsList = (List)map.get("otherCovenantDetailsList");
		if(null != res)
		{
		OBLimitProfileTrxValue oblimitprofiletrxvalue1= (OBLimitProfileTrxValue) res.getTrxValue();
		
		
		List otherCovenantDetailsActualList = null;
		String refid = oblimitprofiletrxvalue1.getReferenceID();
		if (refid != null) {
			try {
				otherCovenantDetailsActualList = othercovenantdetailsdaoimpl
						.getOtherCovenantDetailsActual(refid);
				System.out.println(
						"otherCovenantDetailsActualList----------------------->>>>>>>"
								+ otherCovenantDetailsActualList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(otherCovenantDetailsList !=null)
		{
			for (int i = 0; i < otherCovenantDetailsList.size(); i++) {
				ob = (OBOtherCovenant) otherCovenantDetailsList.get(i);
				if (otherCovenantDetailsActualList != null) {
					for (int j = 0; j < otherCovenantDetailsActualList.size(); j++) {
						ob2 = (OBOtherCovenant) otherCovenantDetailsActualList.get(j);
						if (null != ob.getPreviousStagingId() && null != ob.getIsUpdate()) {
							if ((ob.getPreviousStagingId().equals(ob2.getPreviousStagingId())) && ob.getIsUpdate().equals("Y")) {
								// set all updated values
								ob2.setCustRef(ob.getCustRef());
								ob2.setCovenantCondition(
										ob.getCovenantCondition());
								ob2.setCompiled(ob.getCompiled());
								ob2.setCovenantCategory(
										ob.getCovenantCategory());
								ob2.setAdvised(ob.getAdvised());
								ob2.setCovenantDescription(
										ob.getCovenantDescription());
								ob2.setRemarks(ob.getRemarks());
								ob2.setTargetDate(ob.getTargetDate());
								System.out.println("LIMIT PROFILE ID "
										+ ob2.getStagingRefid());
								ob2.setStatus(ob.getStatus());
								
								
								/*if(ob.getStatus() != "INACTIVE")
								ob2.setStatus("ACTIVE");*/
								ob2.setPreviousStagingId((ob.getPreviousStagingId()));
								try {
									othercovenantdetailsdaoimpl
											.updateOtherCovenantDetailsActual(
													ob2);
								} catch (Exception e) 
								{
									e.printStackTrace();
									System.out.println("Excpetion while updating in actual Other Covenant"+ e.getMessage());
								}
								if(ob2.getStatus().equalsIgnoreCase("ACTIVE"))
										{
								List OtherCovenantValues=othercovenantdetailsdaoimpl.getOtherCovenantDetailsValuesStaging(ob.getOtherCovenantId()+"");
								othercovenantdetailsdaoimpl.deleteOtherCovenantValues(ob2.getPreviousStagingId());
								
								
								if (OtherCovenantValues != null) {
									for (int m = 0; m < OtherCovenantValues.size(); m++) {
										OBOtherCovenant ob1 = (OBOtherCovenant) OtherCovenantValues.get(m);
										ob1.setMonitoringResponsibiltyValue(ob1.getMonitoringResponsibiltyValue());
										ob1.setFacilityNameValue(ob1.getFacilityNameValue());
										ob1.setPreviousStagingId(ob2.getPreviousStagingId());
										ob1.setStagingRefid(ob2.getOtherCovenantId()+"");
										ob1.setCustRef(ob.getCustRef());
										othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);
									}
								}
										}
								else if (ob2.getStatus().equalsIgnoreCase("INACTIVE"))
								{
									List OtherCovenantActualValues = othercovenantdetailsdaoimpl.getOtherCovenantDetailsValuesActualList(ob2.getPreviousStagingId());
									if(OtherCovenantActualValues != null && !OtherCovenantActualValues.isEmpty())
									{
									for (int y = 0; y < OtherCovenantActualValues.size(); y++)
									{
										OBOtherCovenant obv = (OBOtherCovenant) OtherCovenantActualValues.get(y);
										obv.setStatus("INACTIVE");
										//TO SET value as inactive
										othercovenantdetailsdaoimpl.updateOtherCovenantDetailsActualValues(obv);
										
									}
									}
									
								}
								
							}
						 else if ( !(ob.getPreviousStagingId().equals(ob2.getPreviousStagingId())) && ob.getIsUpdate().equals("N")) {
							 String IdForOtherCovenantValues = ob.getOtherCovenantId()+"";
							 ob.setOtherCovenantId(Long.parseLong(othercovenantdetailsdaoimpl.getOtherCovenantDetailsActualIdFromSeq()));
							// ADD NEW DATA
							ob = (OBOtherCovenant) otherCovenantDetailsList
									.get(i);
							ob.setStagingRefid(
									oblimitprofiletrxvalue1.getReferenceID());
							/*if(ob.getStatus() != "INACTIVE")
							ob.setStatus("ACTIVE");*/
							try {
								othercovenantdetailsdaoimpl
										.insertOtherCovenantDetailsActual(ob);
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println("Excpetion while adding in actual Other Covenant" + e.getMessage());
							}
							List OtherCovenantValues=othercovenantdetailsdaoimpl.getOtherCovenantDetailsValuesStaging(IdForOtherCovenantValues);
							//othercovenantdetailsdaoimpl.deleteOtherCovenantValues(ob2.getOtherCovenantId()+"");
							if (OtherCovenantValues != null) {
								for (int m = 0; m < OtherCovenantValues.size(); m++) {
									OBOtherCovenant ob1 = (OBOtherCovenant) OtherCovenantValues.get(m);
									ob1.setMonitoringResponsibiltyValue(ob1.getMonitoringResponsibiltyValue());
									ob1.setFacilityNameValue(ob1.getFacilityNameValue());
									ob1.setStagingRefid(ob.getOtherCovenantId()+"");
									ob1.setPreviousStagingId(ob.getPreviousStagingId());
									ob1.setCustRef(ob.getCustRef());
									othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);
								}
							}
							/*String monResp=ob.getMonitoringResponsibilityList1();
							String[] MRArr = monResp.split(",");
							ArrayList monRespList = new ArrayList();
							for(int m=0; m<MRArr.length;m++) {
								String[] MRArr1 =MRArr[m].split("-");
								monRespList.add(MRArr1[0]);
							}
							for(int l=0;l<monRespList.size();l++) {
								OBOtherCovenant ob1 = new OBOtherCovenant();
								ob1.setMonitoringResponsibiltyValue((String)monRespList.get(l));
								ob1.setStagingRefid(ob2.getOtherCovenantId()+"");
								ob1.setCustRef(ob.getCustRef());
								othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);;
							}
							String facilityName=ob.getFinalfaciltyName();
							String[] FNLArr = facilityName.split(",");
							ArrayList facNameList = new ArrayList();
							for(int p=0; p<FNLArr.length;p++) {
								String[] FNLArr1 =FNLArr[p].split("-");
								facNameList.add(FNLArr1[1]);
							}
							for(int n=0;n<facNameList.size();n++) {
								OBOtherCovenant ob1 = new OBOtherCovenant();
								ob1.setFacilityNameValue((String)facNameList.get(n));
								ob1.setStagingRefid(ob.getOtherCovenantId()+"");
								ob1.setCustRef(ob.getCustRef());
								othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);;
							}*/
						}
						}
					}
				} else {
					String StagingOCid=ob.getOtherCovenantId()+"";
					ob.setOtherCovenantId(Long.parseLong(othercovenantdetailsdaoimpl.getOtherCovenantDetailsActualIdFromSeq()));
					ob.setStagingRefid(
							oblimitprofiletrxvalue1.getReferenceID());
					ob.setStatus("ACTIVE");
					try {
						othercovenantdetailsdaoimpl
								.insertOtherCovenantDetailsActual(ob);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(
								"Excpetion while adding in actual Other Covenant"
										+ e.getMessage());
					}
					List OtherCovenantValues=othercovenantdetailsdaoimpl.getOtherCovenantDetailsValuesStaging(StagingOCid);
					
					
					if (OtherCovenantValues != null) {
						for (int m = 0; m < OtherCovenantValues.size(); m++) {
							OBOtherCovenant ob1 = (OBOtherCovenant) OtherCovenantValues.get(m);
							ob1.setMonitoringResponsibiltyValue(ob1.getMonitoringResponsibiltyValue());
							ob1.setStagingRefid(ob.getOtherCovenantId()+"");
							ob1.setFacilityNameValue(ob1.getFacilityNameValue());
							ob1.setPreviousStagingId(ob.getPreviousStagingId());
							ob1.setCustRef(ob.getCustRef());
							othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);
						}
					}
					/*if (OtherCovenantValues != null) {
						for (int m = 0; m < OtherCovenantValues.size(); m++) {
							OBOtherCovenant ob1 = (OBOtherCovenant) OtherCovenantValues.get(m);
							ob1.setFacilityNameValue(ob1.getFacilityNameValue());
							ob1.setStagingRefid(ob.getOtherCovenantId()+"");
							ob1.setCustRef(ob.getCustRef());
							othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);
						}
					}*/
					/*String monResp=ob.getMonitoringResponsibilityList1();
					String[] MRArr = monResp.split(",");
					ArrayList monRespList = new ArrayList();
					for(int m=0; m<MRArr.length;m++) {
						String[] MRArr1 =MRArr[m].split("-");
						monRespList.add(MRArr1[0]);
					}
					for(int l=0;l<monRespList.size();l++) {
						OBOtherCovenant ob1 = new OBOtherCovenant();
						ob1.setMonitoringResponsibiltyValue((String)monRespList.get(l));
						ob1.setStagingRefid(ob.getOtherCovenantId()+"");
						ob1.setCustRef(ob.getCustRef());
						othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);;
					}
					String facilityName=ob.getFinalfaciltyName();
					String[] FNLArr = facilityName.split(",");
					ArrayList facNameList = new ArrayList();
					for(int p=0; p<FNLArr.length;p++) {
						String[] FNLArr1 =FNLArr[p].split("-");
						facNameList.add(FNLArr1[0]);
					}
					for(int n=0;n<facNameList.size();n++) {
						OBOtherCovenant ob1 = new OBOtherCovenant();
						ob1.setFacilityNameValue((String)facNameList.get(n));
						ob1.setStagingRefid(ob.getOtherCovenantId()+"");
						ob1.setCustRef(ob.getCustRef());
						othercovenantdetailsdaoimpl.insertActualsOtherCovenantDetailsValues(ob1);;
					}*/
				}
			}
		}
		/*if(otherCovenantDetailsList !=null)
		{	
			for(int i=0;i<otherCovenantDetailsList.size();i++) 
			{	
				ob=(OBOtherCovenant)otherCovenantDetailsList.get(i);
				ob.setStagingRefid(oblimitprofiletrxvalue1.getReferenceID());
				ob.setPreviousStagingId(Long.toString(ob.getOtherCovenantId()));
				ob.setStatus("ACTIVE");
				try
				{
				othercovenantdetailsdaoimpl.insertOtherCovenantDetailsActual(ob);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			
			}
		}*/
		}
	
		DefaultLogger.debug(this, "Going out of doExecute()");
		System.out.println("Going Out CheckerApproveAADetailCommand.java doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		boolean update =getUpdateTableCAM();
		if(update){
			updateDuplicateCAMError();
		}
		
		//SCM Interface 
		IJsInterfaceLog log = new OBJsInterfaceLog();
		ScmPartyDao scmPartyDao = (ScmPartyDao)BeanHouse.get("scmPartyDao");
		String custId = trxContext.getCustomer().getCifId();
		String mainScmFlag = scmPartyDao.getBorrowerScmFlag(custId);
		String stgScmFlag = scmPartyDao.getBorrowerScmFlagforCAM(custId);
		System.out.println("*****Inside CheckerApproveAADetailCommand****612***caling getLatestOperationStatus() for CAM SCM Interface***** party id :  "+custId);
		String latestOperationStatus = scmPartyDao.getLatestOperationStatus(custId,"CAM");
		System.out.println("*****Inside  CheckerApproveAADetailCommand***614****operation is"+latestOperationStatus);
		DefaultLogger.debug(this, "Customer Id in CAM "+custId);
		DefaultLogger.debug(this, "Main table scm Flag "+mainScmFlag);
		DefaultLogger.debug(this, "Last updated scm flag from log table "+stgScmFlag);
		
		try {
			DefaultLogger.debug(this, "Going to call SCM webservice");
			PrepareSendReceivePartyCommand scmWsCall = new PrepareSendReceivePartyCommand();
			log.setModuleName("CAM");
			log.setIs_udf_upload("N");
			if(latestOperationStatus!=null && latestOperationStatus.equalsIgnoreCase("A")) {
				DefaultLogger.debug(this, "Sending operation as A because this party is not available at SCM "+latestOperationStatus);
				if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("Yes")) {
					scmWsCall.scmWebServiceCall(custId, "A", "Y",log);
				}else if(mainScmFlag.equalsIgnoreCase("No")&&stgScmFlag.equalsIgnoreCase("Yes")) {
					scmWsCall.scmWebServiceCall(custId, "A","Y", log);
				}else {
					DefaultLogger.debug(this, "Inside else as both are no. Need not call the service "+mainScmFlag+" "+stgScmFlag);
				}
			}else {
			if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("Yes")) {
				DefaultLogger.debug(this, "Inside if because both are Yes "+mainScmFlag+" "+stgScmFlag);
				scmWsCall.scmWebServiceCall(custId, "U", "Y",log);
			}else if(mainScmFlag.equalsIgnoreCase("No")&&stgScmFlag.equalsIgnoreCase("Yes")) {
				DefaultLogger.debug(this, "Inside if because Stg is Yes "+mainScmFlag+" "+stgScmFlag);
				scmWsCall.scmWebServiceCall(custId, "U","Y", log);
			}else if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("No")) {
				DefaultLogger.debug(this, "Inside if because Stg is No.Need to send once "+mainScmFlag+" "+stgScmFlag);
				scmWsCall.scmWebServiceCall(custId, "U","N", log);
			}else {
				DefaultLogger.debug(this, "Inside else as both are no. Need not call the service "+mainScmFlag+" "+stgScmFlag);
			}
			}
			
		}catch(Exception e) {
			DefaultLogger.debug(this, "error in SCM webservice "+e);
		}
		
		return returnMap;
	}
	
private void updateDuplicateCAMError(){
		
		DBUtil dbUtil = null;
		
		String sql = "SELECT MIN(a.cms_lsp_lmt_profile_id) as oldId,  a.llp_le_id as custId,  MAX(a.cms_lsp_lmt_profile_id) as newId  "+
		" FROM SCI_LSP_LMT_PROFILE a WHERE a.llp_le_id IN  ( SELECT DISTINCT (o.llp_le_id)  FROM SCI_LSP_LMT_PROFILE o  INNER JOIN    "+
		"  (SELECT llp_le_id,      COUNT(*) AS dupeCount FROM SCI_LSP_LMT_PROFILE    GROUP BY llp_le_id    HAVING COUNT(*) > 1    ) oc  " +
		" ON o.llp_le_id = oc.llp_le_id  ) GROUP BY a.llp_le_id ";

		
		try {
			ResultSet rsOldIdNewId=null;
			HashMap oldIdNewIdMap = new HashMap();
			ArrayList oldIdList= new ArrayList();
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
//			int  rs = dbUtil.executeUpdate();
//			dbUtil.commit();
			
			rsOldIdNewId = dbUtil.executeQuery();
			  while(rsOldIdNewId.next()){
				  oldIdList.add(String.valueOf(rsOldIdNewId.getLong("oldId")));
				  oldIdNewIdMap.put(String.valueOf(rsOldIdNewId.getLong("oldId")),String.valueOf( rsOldIdNewId.getLong("newId")));
			  }
			if(oldIdList!=null && oldIdList.size()>0){
				for (int i = 0; i < oldIdList.size(); i++) {
					String oldId=(String)oldIdList.get(i);
					String newId=(String)oldIdNewIdMap.get(oldId);
					String oldTransactionId="";
					String sqlTransaction = "select reference_id, transaction_id,staging_reference_id from transaction where reference_id= "+oldId+" and transaction_type='LIMITPROFILE' ";
					ResultSet rsOldIdTransId=null;
					dbUtil.setSQL(sqlTransaction);
					rsOldIdTransId= dbUtil.executeQuery();
					 while(rsOldIdTransId.next()){
						 oldTransactionId=rsOldIdTransId.getString("transaction_id");
					  }
					String deleteQueryOldId=" delete from sci_lsp_lmt_profile where cms_lsp_lmt_profile_id= "+oldId;
					String updateQueryOldId=" update sci_lsp_lmt_profile set cms_lsp_lmt_profile_id= "+oldId+" where cms_lsp_lmt_profile_id= "+newId;
					String deleteTransactionQueryOldId=" delete from transaction where reference_id= "+oldId+" and transaction_type='LIMITPROFILE' ";
					String updateTransactionQueryNewId=" update transaction set transaction_id= "+oldTransactionId+" , reference_id= "+oldId+" where reference_id= "+newId+" and transaction_type='LIMITPROFILE' ";
					String updateTrancHistoryNewId=" update trans_history set transaction_id= "+oldTransactionId+" , reference_id= "+oldId+" where reference_id= "+newId+" and transaction_type='LIMITPROFILE' ";
					dbUtil.setSQL(deleteQueryOldId);
					dbUtil.executeUpdate();
					dbUtil.commit();
					dbUtil.setSQL(updateQueryOldId);
					dbUtil.executeUpdate();
					dbUtil.commit();
					dbUtil.setSQL(deleteTransactionQueryOldId);
					dbUtil.executeUpdate();
					dbUtil.commit();
					dbUtil.setSQL(updateTransactionQueryNewId);
					dbUtil.executeUpdate();
					dbUtil.commit();
					dbUtil.setSQL(updateTrancHistoryNewId);
					dbUtil.executeUpdate();
					dbUtil.commit();
				}
				
				
			}
			
			
		}
		catch (SQLException ex) {
			// do nothing
		}
		catch (Exception ex) {
			// do nothing
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				// do nothing
			}
		}
		
	}
	
	private boolean getUpdateTableCAM(){
		
		DBUtil dbUtil = null;
		
		String sql = "SELECT MIN(a.cms_lsp_lmt_profile_id),  a.llp_le_id,  MAX(a.cms_lsp_lmt_profile_id)FROM SCI_LSP_LMT_PROFILE a "+
		"WHERE a.llp_le_id IN  ( SELECT DISTINCT (o.llp_le_id)  FROM SCI_LSP_LMT_PROFILE o  INNER JOIN    (SELECT llp_le_id,      COUNT(*) AS dupeCount "+
		"FROM SCI_LSP_LMT_PROFILE    GROUP BY llp_le_id    HAVING COUNT(*) > 1    ) oc  ON o.llp_le_id = oc.llp_le_id  ) GROUP BY a.llp_le_id ";
		
		DefaultLogger.debug(this, "--------1---------"+sql);
		ResultSet rs=null;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
		
			  rs = dbUtil.executeQuery();
			  if(rs.next()){
				  return true;
			  }else{
				  return false;
			  }
		}
		catch (SQLException ex) {
			// do nothing
		}
		catch (Exception ex) {
			// do nothing
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				// do nothing
			}
		}
		return false;
		
	}
	
	//Start Santosh UBS LIMIT
		public static boolean compareDate(Locale locale, Date oldExtendedNextReviewDate, Date newExtendedNextReviewDate) {

			if (oldExtendedNextReviewDate != null) {
				String originalDate = DateUtil.formatDate(locale, oldExtendedNextReviewDate);
				String newDate=DateUtil.formatDate(locale, newExtendedNextReviewDate);
				
				if (originalDate.equals(newDate)) {
					return false;
				}
			}
			return true;
		}
		
		public void updateLineExpiryDate(long limitProfileId,Date newExtendedNextReviewDate) throws Exception{
			
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			ILimitDAO dao1 = LimitDAOFactory.getDAO();
			//String lineNumbers = split(bundle.getString("ubs.limit.upload.lineNo"));
			String lineNumbers = split(dao1.getExclusionLine());
			
			String facilitySystem = "'"+bundle.getString("fcubs.systemName")+"','"+bundle.getString("ubs.systemName")+"'";
			String status =ICMSConstant.FCUBS_STATUS_PENDING;
			String rejectedReason="";
			DBUtil dbUtil = null;
			
			
		String sql ="update SCI_LSP_SYS_XREF limit set limit.DATE_OF_RESET = ? , limit.CORE_STP_REJECTED_REASON = ? , limit.STATUS= ?  where limit.CMS_LSP_SYS_XREF_ID IN (SELECT map.cms_lsp_sys_xref_id"
							+" FROM sci_lsp_lmts_xref_map map WHERE map.cms_lsp_appr_lmts_id IN ( SELECT facility.cms_lsp_appr_lmts_id FROM sci_lsp_appr_lmts facility"
							+" WHERE facility.cms_limit_profile_id = '"+limitProfileId+"' AND   facility.facility_system IN ("+facilitySystem+") AND  "+lineNumbers
							+"  facility.is_adhoc = 'N'))";
			try {
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				dbUtil.setTimestamp(1, new Timestamp(newExtendedNextReviewDate.getTime()));
				dbUtil.setString(2, rejectedReason);
				dbUtil.setString(3, status);
				dbUtil.executeUpdate();
				dbUtil.commit();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
			}
			finally {
				if(null!=dbUtil )
					dbUtil.close();
			}
		}
		
		public String split(String lineNumbers) {
		       String[] array = lineNumbers.split(",");
		       
		       lineNumbers=" ";
		       for(int i=0;i<array.length;i++) {
		    	   if(i==(array.length-1)){
		        lineNumbers=lineNumbers+"facility.line_no NOT LIKE '%"+array[i]+"%'";
		    	   }else{
		    		   lineNumbers=lineNumbers+"facility.line_no NOT LIKE '%"+array[i]+"%' and ";   
		    	   }
		       }
		      
		       return lineNumbers;
		}
		
		public boolean isActive(long limitProfileId) throws Exception{
			DBUtil dbUtil = null;
			ResultSet rs=null;
//			String sql ="select status from TRANS_HISTORY where tr_history_id= (select max(tr_history_id) "
//				+ " from TRANS_HISTORY where REFERENCE_ID IN (select CMS_LSP_APPR_LMTS_ID from sci_lsp_appr_lmts where cms_limit_profile_id='"+limitProfileId+"'))";

			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String facilitySystem = "'"+bundle.getString("fcubs.systemName")+"','"+bundle.getString("ubs.systemName")+"'";
			
			String sql ="select count(1)  from transaction where REFERENCE_ID IN (select CMS_LSP_APPR_LMTS_ID from sci_lsp_appr_lmts "
						+" where facility_system in ("+facilitySystem+") and cms_limit_profile_id='"+limitProfileId+"') and status not in ('ACTIVE','DELETED','CLOSED')";
			
			System.out.println("CheckerApproveAADetailCommand.java=>boolean isActive(long limitProfileId)=>sql---->>>>>>>"+sql);
			try {
				dbUtil = new DBUtil();
//				String isActive="";
				
				int isActive=0;
				
				dbUtil.setSQL(sql);
				rs= dbUtil.executeQuery();
				
				if(rs.next())
//				 isActive=rs.getString(1);
//				
//				if("ACTIVE".equals(isActive) || "".equals(isActive))
					
				
				isActive=rs.getInt(1);
				System.out.print("CheckerApproveAADetailCommand.java=>ISACTIVE=>"+isActive);
				if(isActive==0){
				String sql1="  select count(1)  from transaction where STAGING_REFERENCE_ID IN (select CMS_LSP_APPR_LMTS_ID from stage_limit "+
					" where facility_system in ("+facilitySystem+") and cms_limit_profile_id='"+limitProfileId+"') and status not in ('ACTIVE','DELETED','CLOSED')";
				System.out.println("CheckerApproveAADetailCommand.java=>L875=>sql1---->>>>>>>"+sql1);
				int stageCount=0;	
				dbUtil.setSQL(sql1);
				rs= dbUtil.executeQuery();
				
				if(rs.next()){
					stageCount = rs.getInt(1);
					System.out.println("CheckerApproveAADetailCommand.java=>boolean isActive(long limitProfileId)---->>>>>>>stageCount=>"+stageCount);
					if(stageCount>0){
						return false;
					}else{
				
				
				String sql2=" select count(1) from SCI_LSP_SYS_XREF where status='PENDING'  and facility_system in ("+facilitySystem+")"
				+" and CMS_LSP_SYS_XREF_ID IN (SELECT map.cms_lsp_sys_xref_id  FROM sci_lsp_lmts_xref_map map WHERE map.cms_status='ACTIVE' and map.cms_lsp_appr_lmts_id IN ("
				+" SELECT facility.cms_lsp_appr_lmts_id FROM sci_lsp_appr_lmts facility  WHERE facility.facility_system in ("+facilitySystem+") and facility.CMS_LIMIT_STATUS='ACTIVE' and  facility.cms_limit_profile_id = '"+limitProfileId+"'))";
					
				
				System.out.println("CheckerApproveAADetailCommand.java=>L892=>sql2---->>>>>>>"+sql2);
					int pendingCount=0;	
					dbUtil.setSQL(sql2);
					rs= dbUtil.executeQuery();
					
					if(rs.next()){
						pendingCount = rs.getInt(1);
						System.out.println("CheckerApproveAADetailCommand.java=>boolean isActive(long limitProfileId)---->>>>>>>pendingCount=>"+pendingCount);
						if(pendingCount>0){
							return false;
						}else{
							return true;	
						}
					}
					return true;
					
				}
				}
				return true;
				}
				else 
					return false;
			}
			catch (Exception ex) {
				System.out.println("Exception in CheckerApproveAADetailCommand.java=>boolean isActive(long limitProfileId)-->>>>>>>ex=>"+ex);
				ex.printStackTrace();
				throw ex;
			}
			finally {
				if( null != rs) {
					rs.close();
				}
				if(null!=dbUtil )
					dbUtil.close(true);
			}
		}
		//End Santosh UBS LIMIT
		
		public void getMapLineDetails(ILimitTrxValue lmtTrxObj, ILimitDAO dao1, String date, Date appDate, Map<String, String> availableActual, Map<String, String> expiryActual, Map<String, String> availableExpiryActual){
			ILimit limit = lmtTrxObj.getLimit();
			ILimit stagingLimit = lmtTrxObj.getStagingLimit();
			System.out.println("CheckerApproveAADetailCommand.java=>Inside getMapLineDetails()...");
			if(null!= limit  && null!= limit.getLimitSysXRefs() && null!= stagingLimit  && null!=stagingLimit.getLimitSysXRefs()) {
				System.out.println("CheckerApproveAADetailCommand.java=> getMapLineDetails()=>limit and limit.getLimitSysXRefs() is not null.");
				boolean isSblcSecGtZero = false;
				boolean isSblc = false;
				IExcLineForSTPSRMJdbc excLineForSTPSRMJdbc = (IExcLineForSTPSRMJdbc) BeanHouse.get("excLineForSTPSRMJdbc");
				boolean isExcluded = excLineForSTPSRMJdbc.isExcluded(limit.getLineNo(), true);
				System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()...isExcluded=>"+isExcluded+"***limit.getLineNo()=>"+limit.getLineNo()+"***");
				if(limit.getCollateralAllocations() != null && limit.getCollateralAllocations().length > 0) {
					System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()...limit.getCollateralAllocations() IS NOT NULL AND LENGTH MORE THAN 0=>"+limit.getCollateralAllocations().length);
					for(int i = 0; i <limit.getCollateralAllocations().length; i++) {
						if(isSblcSecGtZero) {
							System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()...isSblcSecGtZero=>"+isSblcSecGtZero);
							break;
						}
						if(ICMSConstant.HOST_STATUS_DELETE.equals(limit.getCollateralAllocations()[i].getHostStatus())) {
							System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()...limit.getCollateralAllocations()[i].getHostStatus()=>"+limit.getCollateralAllocations()[i].getHostStatus()+"***CpsSecurityId=>"+limit.getCollateralAllocations()[i].getCpsSecurityId()+"**ChargeID=>"+limit.getCollateralAllocations()[i].getChargeID());
							continue;
						}
						
						ICollateral collateral = limit.getCollateralAllocations()[i].getCollateral();
						String subType = collateral.getCollateralSubType().getSubTypeCode();
						if(ICMSConstant.COLTYPE_GUARANTEE_SBLC_DIFFCCY.equals(subType) || 
								ICMSConstant.COLTYPE_GUARANTEE_SBLC_SAMECCY.equals(subType)) {
							isSblc = true;
							System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()...isSblc=>"+isSblc);
							if(collateral.getCMV() != null && collateral.getCMV().getAmountAsBigDecimal().compareTo(BigDecimal.ZERO) > 0) {
								isSblcSecGtZero = true;
							}
							System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()...isSblcSecGtZero=>"+isSblcSecGtZero);
						}
					}
				}
				
				ILimitSysXRef[] limitSysXRefsStage = stagingLimit.getLimitSysXRefs();
				int StageXreflength = limitSysXRefsStage.length;
				ILimitSysXRef[] limitSysXRefs = limit.getLimitSysXRefs();
				for (int i = 0; i < limitSysXRefs.length; i++) {
					ILimitSysXRef iLimitSysXRef = limitSysXRefs[i];
					ICustomerSysXRef customerSysXRef = iLimitSysXRef.getCustomerSysXRef();
					String releasedAmount = customerSysXRef.getReleasedAmount();
					String status = customerSysXRef.getStatus();
					System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()...releasedAmount=>"+releasedAmount+"***status=>"+status+"**");
					if(null!=releasedAmount && !"0".equals(releasedAmount) && null != status && !"HIDE".equalsIgnoreCase(status)){
						System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()...customerSysXRef.getCloseFlag()=>"+customerSysXRef.getCloseFlag()+"**");
						if(null== customerSysXRef.getCloseFlag() || "N".equals(customerSysXRef.getCloseFlag())){
							boolean expired=false;
							System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()...customerSysXRef.getDateOfReset()=>"+customerSysXRef.getDateOfReset()+"**appDate=>"+appDate);
							if(null!=customerSysXRef.getDateOfReset() &&  null!=appDate){
								//SimpleDateFormat sdf=new SimpleDateFormat("dd/MMM/yyyy");
								//sdf.parse(DateUtil.formatDate(aLocale, customerSysXRef.getDateOfReset())
								
								if(customerSysXRef.getDateOfReset().compareTo(appDate) <0 || customerSysXRef.getDateOfReset().compareTo(appDate)==0) {
									expired=true;
								}
								System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L994=>expired=>"+expired);
							}else if(null==customerSysXRef.getDateOfReset()){
									expired=true;
								
							}
							System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L999=>expired=>"+expired+"**isExcluded=>"+isExcluded+"**isSblc=>"+isSblc+"**isSblcSecGtZero=>"+isSblcSecGtZero);
							System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1000=>limit.getIsAdhoc()=>"+limit.getIsAdhoc()+"**customerSysXRef.getAvailable()=>"+customerSysXRef.getAvailable()+"**");
							if(isExcluded || (isSblc && isSblcSecGtZero)) {
								System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1002");
								continue;
							}else if("Y".equals(limit.getIsAdhoc()) && "No".equals(customerSysXRef.getAvailable())){
								System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1005");
								String sorceRefNo=generateSourceNo(dao1,date);
								if(ICMSConstant.FCUBS_STATUS_REJECTED.equals(customerSysXRef.getStatus())){
									System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1008");
								for(int k=0; k<StageXreflength; k++){
									if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
										System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1011=>iLimitSysXRef.getSID()=>"+iLimitSysXRef.getSID()+"**customerSysXRef.getXRefID()=>"+customerSysXRef.getXRefID()+"**sorceRefNo=>"+sorceRefNo+"**");
										availableActual.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+customerSysXRef.getAction());
										break;
									}
								}
								}else{
									System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1016");
								for(int k=0; k<StageXreflength; k++){
									if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
										System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1020=>iLimitSysXRef.getSID()=>"+iLimitSysXRef.getSID()+"**customerSysXRef.getXRefID()=>"+customerSysXRef.getXRefID()+"**sorceRefNo=>"+sorceRefNo+"**");
										availableActual.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
										
										break;
									}
								}
								}
								}else if("N".equals(limit.getIsAdhoc()) && "No".equals(customerSysXRef.getAvailable()) && expired){
									
									System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1027");
									String sorceRefNo=generateSourceNo(dao1,date);
									if(ICMSConstant.FCUBS_STATUS_REJECTED.equals(customerSysXRef.getStatus())){
										System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1030");
									for(int k=0; k<StageXreflength; k++){
										if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
											System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1035=>iLimitSysXRef.getSID()=>"+iLimitSysXRef.getSID()+"**customerSysXRef.getXRefID()=>"+customerSysXRef.getXRefID()+"**sorceRefNo=>"+sorceRefNo+"**");
											availableExpiryActual.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+customerSysXRef.getAction());
											break;
										}
									}
									}else{
										System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1038");
									for(int k=0; k<StageXreflength; k++){
										if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
											System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1044=>iLimitSysXRef.getSID()=>"+iLimitSysXRef.getSID()+"**customerSysXRef.getXRefID()=>"+customerSysXRef.getXRefID()+"**sorceRefNo=>"+sorceRefNo+"**");
											availableExpiryActual.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
											break;
										}
									}
									}
									}else if("N".equals(limit.getIsAdhoc()) && "No".equals(customerSysXRef.getAvailable()) && !expired){
										System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1047");
										
										String sorceRefNo=generateSourceNo(dao1,date);
										if(ICMSConstant.FCUBS_STATUS_REJECTED.equals(customerSysXRef.getStatus())){
											System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1051");
											for(int k=0; k<StageXreflength; k++){
											if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
												System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1058=>iLimitSysXRef.getSID()=>"+iLimitSysXRef.getSID()+"**customerSysXRef.getXRefID()=>"+customerSysXRef.getXRefID()+"**sorceRefNo=>"+sorceRefNo+"**");
												availableActual.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+customerSysXRef.getAction());
												break;
											}
										}
										}else{
											System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1059");
											for(int k=0; k<StageXreflength; k++){
											if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
												System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1067=>iLimitSysXRef.getSID()=>"+iLimitSysXRef.getSID()+"**customerSysXRef.getXRefID()=>"+customerSysXRef.getXRefID()+"**sorceRefNo=>"+sorceRefNo+"**");
												availableActual.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
												break;
											}
										}
										}
										}else if("N".equals(limit.getIsAdhoc()) && ( null==customerSysXRef.getAvailable() || "Yes".equals(customerSysXRef.getAvailable())) && !expired){
											System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1062");
											String sorceRefNo=generateSourceNo(dao1,date);
											if(ICMSConstant.FCUBS_STATUS_REJECTED.equals(customerSysXRef.getStatus())){
												System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1071");
												for(int k=0; k<StageXreflength; k++){
												if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
													System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1080=>iLimitSysXRef.getSID()=>"+iLimitSysXRef.getSID()+"**customerSysXRef.getXRefID()=>"+customerSysXRef.getXRefID()+"**sorceRefNo=>"+sorceRefNo+"**");
													expiryActual.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+customerSysXRef.getAction());
													break;
												}
											}
											}else{
												System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1079");
												for(int k=0; k<StageXreflength; k++){
												if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
													System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1089=>iLimitSysXRef.getSID()=>"+iLimitSysXRef.getSID()+"**customerSysXRef.getXRefID()=>"+customerSysXRef.getXRefID()+"**sorceRefNo=>"+sorceRefNo+"**");
													expiryActual.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
													break;
												}
											}
										}
									}else if("N".equals(limit.getIsAdhoc()) && ( null==customerSysXRef.getAvailable() || "Yes".equals(customerSysXRef.getAvailable())) && expired){
										System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1080");
										String sorceRefNo=generateSourceNo(dao1,date);
										if(ICMSConstant.FCUBS_STATUS_REJECTED.equals(customerSysXRef.getStatus())){
											System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1091");
											for(int k=0; k<StageXreflength; k++){
											if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
												System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1102=>iLimitSysXRef.getSID()=>"+iLimitSysXRef.getSID()+"**customerSysXRef.getXRefID()=>"+customerSysXRef.getXRefID()+"**sorceRefNo=>"+sorceRefNo+"**");
												expiryActual.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+customerSysXRef.getAction());
												break;
											}
										}
										}else{
											System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1099");
											for(int k=0; k<StageXreflength; k++){
											if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
												System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1111=>iLimitSysXRef.getSID()=>"+iLimitSysXRef.getSID()+"**customerSysXRef.getXRefID()=>"+customerSysXRef.getXRefID()+"**sorceRefNo=>"+sorceRefNo+"**");
												expiryActual.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
												break;
											}
										}
									}
									}
							System.out.println("CheckerApproveAADetailCommand.java=>getMapLineDetails()..L1098");
							}
						}
					}
				}
			}

		public String generateSourceNo(ILimitDAO dao1, String date) {
			String tempSourceRefNo="";
			 tempSourceRefNo=""+dao1.generateSourceSeqNo();
			
			 int len=tempSourceRefNo.length();
			 String concatZero="";
			if(null!=tempSourceRefNo && len!=5){
				
				for(int m=5;m>len;m--){
					concatZero="0"+concatZero;
				}

			}
			tempSourceRefNo=concatZero+tempSourceRefNo;
			
			String sorceRefNo=ICMSConstant.FCUBS_CAD+date+tempSourceRefNo;
			return sorceRefNo;
		}
		
		//update ram rating statement after changing ram rating year-Ram rating CR. 
		private void updateRAMRatingStatementStatus(IGeneralParamEntry[] generalParamEntries, ILimitProfileTrxValue limitProfileTrxVal) {
			ILimitDAO dao = LimitDAOFactory.getDAO();
			Date dateApplication=new Date();
			for(int j=0;j<generalParamEntries.length;j++){
				if(generalParamEntries[j].getParamCode().equals("APPLICATION_DATE")){
					dateApplication=new Date(generalParamEntries[j].getParamValue());
				}
			}
			DateFormat formatter = new SimpleDateFormat("yyyy");
			String oldRamRatingYear=limitProfileTrxVal.getLimitProfile().getRamRatingYear();
			String ramRatingYear=limitProfileTrxVal.getStagingLimitProfile().getRamRatingYear();
			String checkListId=dao.getChecklistId(limitProfileTrxVal.getLimitProfile().getLimitProfileID());
			
			String customerFyClosure=dao.getCustomerFyClosure(limitProfileTrxVal.getLimitProfile().getLimitProfileID());
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateApplication);
			cal.add(Calendar.MONTH, 8);
			Date newDate=cal.getTime();
	        String year= formatter.format(newDate);
	        if(ramRatingYear!=null && oldRamRatingYear!=null && !ramRatingYear.equals(oldRamRatingYear)) {
	        	int oldRAMYear=Integer.valueOf(oldRamRatingYear);
	        	int newRAMYear=Integer.valueOf(ramRatingYear);
	        	OBDocumentItem ramRatingChecklist = new OBDocumentItem();
	        	if(newRAMYear>oldRAMYear) {
	        		try {
	        			ramRatingChecklist=dao.getAllRamratingDocument();
	        			String docRefId=new SimpleDateFormat("yyyyMMdd").format(new Date())+dao.getDocSeqId();
	        			String docId=new SimpleDateFormat("yyyyMMdd").format(new Date())+dao.getDocSeqId();
	        			//Disable old RAM statement where status=Received
	        			dao.disableRAMChecklistDetails(checkListId);
	        			//update Ram statement status as Received
	        			dao.updateRAMChecklistDetails(checkListId);
	        			//create new Ram statement with pending status
	        			dao.insertRAMStatement(customerFyClosure,docId,ramRatingChecklist,checkListId,docRefId,ramRatingYear);
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	        	}
	       } 
	      // dao.updateChecklistDetails(checkListId,todayYear,ramRatingYear);
		}
		
		public void getMapPSRLineDetails(ILimitTrxValue lmtTrxObj, ILimitDAO dao1, String date, Date appDate, Map<String, String> expiryActualForPSR){
			
			ILimit limit = lmtTrxObj.getLimit();
			ILimit stagingLimit = lmtTrxObj.getStagingLimit();
				
			if(limit != null && limit.getLimitSysXRefs()!=null) {
				
				ILimitSysXRef[] limitSysXRefsStage = stagingLimit.getLimitSysXRefs();
				int StageXreflength = limitSysXRefsStage.length;
				ILimitSysXRef[] limitSysXRefs = limit.getLimitSysXRefs();
				
				for (int i = 0; i < limitSysXRefs.length; i++) {
					ILimitSysXRef iLimitSysXRef = limitSysXRefs[i];
					ICustomerSysXRef customerSysXRef = iLimitSysXRef.getCustomerSysXRef();
					String releasedAmount = customerSysXRef.getReleasedAmount();
					String status = customerSysXRef.getStatus();
					
					if(null!=releasedAmount && !"0".equals(releasedAmount) && null != status && !"HIDE".equalsIgnoreCase(status)){
						if(null== customerSysXRef.getCloseFlag() || "N".equals(customerSysXRef.getCloseFlag())){
							boolean expired=false;
							if(null!=customerSysXRef.getDateOfReset() &&  null!=appDate){
								if(customerSysXRef.getDateOfReset().compareTo(appDate) <0 || customerSysXRef.getDateOfReset().compareTo(appDate)==0)
									expired=true;
							}else if(null==customerSysXRef.getDateOfReset()){
									expired=true;
								
							}
							
							if("N".equals(limit.getIsAdhoc()) && ( null==customerSysXRef.getAvailable() || "Yes".equals(customerSysXRef.getAvailable())) && !expired){
											
											String sorceRefNo=generateSourceNo(dao1,date);
											if(ICMSConstant.PSR_STATUS_REJECTED.equals(customerSysXRef.getStatus())){
												for(int k=0; k<StageXreflength; k++){
												if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
													expiryActualForPSR.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+customerSysXRef.getAction());
													break;
												}
											}
											}else{
												for(int k=0; k<StageXreflength; k++){
												if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
													expiryActualForPSR.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+ICMSConstant.PSRLIMIT_ACTION_MODIFY);
													break;
												}
											}
										}
									}else if("N".equals(limit.getIsAdhoc()) && ( null==customerSysXRef.getAvailable() || "Yes".equals(customerSysXRef.getAvailable())) && expired){
										
										String sorceRefNo=generateSourceNo(dao1,date);
										if(ICMSConstant.PSR_STATUS_REJECTED.equals(customerSysXRef.getStatus())){
											for(int k=0; k<StageXreflength; k++){
											if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
												expiryActualForPSR.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+customerSysXRef.getAction());
												break;
											}
										}
										}else{
											for(int k=0; k<StageXreflength; k++){
											if(iLimitSysXRef.getSID()==limitSysXRefsStage[k].getSID()){
												expiryActualForPSR.put(String.valueOf(customerSysXRef.getXRefID()),String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())+","+sorceRefNo+","+ICMSConstant.PSRLIMIT_ACTION_MODIFY);
												break;
											}
										}
									}
									}
							}
						}
					}
				}
			}
		
}
