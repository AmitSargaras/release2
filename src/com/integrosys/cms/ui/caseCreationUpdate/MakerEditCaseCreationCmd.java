
package com.integrosys.cms.ui.caseCreationUpdate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreation;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreationDao;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation;
import com.integrosys.cms.app.caseCreationUpdate.proxy.ICaseCreationProxyManager;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;
import com.integrosys.cms.app.caseCreationUpdate.trx.OBCaseCreationTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.user.app.bus.OBCommonUser;

/**
@author $Author: Abhijit R$
* Command for edit CaseCreation
 */
public class MakerEditCaseCreationCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICaseCreationProxyManager caseCreationProxy;

	public ICaseCreationProxyManager getCaseCreationProxy() {
		return caseCreationProxy;
	}

	public void setCaseCreationProxy(ICaseCreationProxyManager caseCreationProxy) {
		this.caseCreationProxy = caseCreationProxy;
	}
	
	
	/**
	 * Default Constructor
	 */
	
	
	public MakerEditCaseCreationCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	        		{"ICaseCreationTrxValue", "com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser",GLOBAL_SCOPE},
					{CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String",GLOBAL_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"currRemarks", "java.lang.String", REQUEST_SCOPE},
	                {"removedSrNum", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	                {"status", "java.lang.String", REQUEST_SCOPE},
	                { "hiddenItemID", "java.lang.String", REQUEST_SCOPE },
	                {"searchResultCaseCreation", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
	        		{ "caseCreationUpdateObj", "com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation", FORM_SCOPE }
	               
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] { 
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
					   });
		}
	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			try {
				String teamTypeMemID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
				 OBCommonUser user = (OBCommonUser) map.get(IGlobalConstant.USER);	
				OBCaseCreation caseCreationUpdate = (OBCaseCreation) map.get("caseCreationUpdateObj");
				String event = (String) map.get("event");
				String status = (String) map.get("status");
				String currRemarks = (String) map.get("currRemarks");
				String removedSrNum = (String) map.get("removedSrNum");
				  IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
	                IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
	    			Date  applicationDate= new Date(generalParamEntries.getParamValue());
				if(currRemarks !=null){
					caseCreationUpdate.setPrevRemarks(caseCreationUpdate.getCurrRemarks());
					caseCreationUpdate.setCurrRemarks(currRemarks);
				}
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ICaseCreationTrxValue trxValueIn = (OBCaseCreationTrxValue) map.get("ICaseCreationTrxValue");
				ICaseCreationDao caseCreationDao=(ICaseCreationDao)BeanHouse.get("caseCreationDao");
				SearchResult searchResultCaseCreation=(SearchResult)map.get("searchResultCaseCreation");
				ICaseCreationTrxValue trxValueOut = new OBCaseCreationTrxValue();
				Collection list=searchResultCaseCreation.getResultList();
				String hiddenItemID = (String) map.get("hiddenItemID");
				StringTokenizer st = new StringTokenizer(hiddenItemID, ",");
				HashMap hm = new HashMap();
				while (st.hasMoreTokens()) {
					String key = st.nextToken();
					hm.put(key, key);
				}
				
				boolean isCPUT=false;
				boolean isBRANCH=false;
				boolean updateCase=false;   
				    if(user.getTeamTypeMembership().getMembershipID()==ICMSConstant.TEAM_TYPE_BRANCH_MAKER
				    		|| user.getTeamTypeMembership().getMembershipID()==ICMSConstant.TEAM_TYPE_BRANCH_MAKER_WFH){
				    	isBRANCH=true;
				    }else if(user.getTeamTypeMembership().getMembershipID()==ICMSConstant.CPU_MAKER
				    		||user.getTeamTypeMembership().getMembershipID()==ICMSConstant.CPU_ADMIN_MAKER 
				    		||user.getTeamTypeMembership().getMembershipID()==ICMSConstant.TEAM_TYPE_SC_MAKER_WFH 
				    		||user.getTeamTypeMembership().getMembershipID()==ICMSConstant.TEAM_TYPE_SSC_MAKER
				    		||user.getTeamTypeMembership().getMembershipID()==ICMSConstant.TEAM_TYPE_SSC_MAKER_WFH
				    		||user.getTeamTypeMembership().getMembershipID()==ICMSConstant.TEAM_TYPE_SSC_CHECKER
				    		||user.getTeamTypeMembership().getMembershipID()==ICMSConstant.TEAM_TYPE_SSC_CHECKER_WFH){
				    	isCPUT=true;
				    }
				if(trxValueIn.getFromState().equals("PENDING_PERFECTION")){
					trxValueOut = getCaseCreationProxy().makerUpdateSaveCreateCaseCreation(ctx, trxValueIn, caseCreationUpdate);
				}else{
					if ((event.equals("maker_edit_caseCreationUpdate"))||event.equals("maker_delete_caseCreationUpdate")||event.equals("maker_save_update")||event.equals("maker_edit_caseCreationUpdate_branch_menu")||event.equals("maker_edit_caseCreationUpdate_cput_menu")) {
						if(trxValueIn.getStatus().equals("ACTIVE")){
						if(isBRANCH){
							trxValueOut = getCaseCreationProxy().makerUpdateCaseCreationBranch(ctx, trxValueIn, caseCreationUpdate);
							updateCase=true;
						}else{
							trxValueOut = getCaseCreationProxy().makerUpdateCaseCreation(ctx, trxValueIn, caseCreationUpdate);
							updateCase=true;
						}
						}else{
							resultMap.put("wip", "wip");
						}
					} else {
						// event is  maker_confirm_resubmit_edit
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getCaseCreationProxy().makerEditRejectedCaseCreation(ctx, trxValueIn, caseCreationUpdate);
						updateCase=true;
						
					} 
				}
				List<String> srNumList = new ArrayList();
				if(removedSrNum != null && !"".equals(removedSrNum)) {
					 srNumList= Arrays.asList(removedSrNum.split(","));
				} 
				System.out.println("removedSrNum =>"+removedSrNum+" ** updateCase=>"+updateCase+" ** event=>"+event);
				
				int count = 0;
				boolean flag = false;
				int num = 1;
				String strNum = "";
				
				String[] docBarcodeArray = caseCreationUpdate.getUpdatedDocBarcodes();
				String[] boxBarCodeArray = caseCreationUpdate.getUpdatedBoxBarCodes();
				String[] documentAmountArray = caseCreationUpdate.getUpdatedDocAmounts();
				String[] fileBarCodeArray = caseCreationUpdate.getUpdatedFileBarCodes();
				String[] lotNumbersArray = caseCreationUpdate.getUpdatedLotNumbers();
				String[] placeOfExecutionArray = caseCreationUpdate.getUpdatedPlaceOfExecutions();
				String[] rackNumberArray = caseCreationUpdate.getUpdatedRackNumbers();
				String[] retrievaldateArray = caseCreationUpdate.getUpdatedRetrievaldates();
				String[] stampDutyArray = caseCreationUpdate.getUpdatedStampDutys();
				String[] submittedToArray = caseCreationUpdate.getUpdatedSubmittedTos();
				String[] userNameArray = caseCreationUpdate.getUpdatedUserNames();
				String[] vaultLocationArray = caseCreationUpdate.getUpdatedVaultLocations();
				String[] vaultNumberArray = caseCreationUpdate.getUpdatedVaultNumbers();
				String[] vaultReceiptDateArray = caseCreationUpdate.getUpdatedVaultReceiptDates();
				String[] checkBoxValuesArray = caseCreationUpdate.getCheckBoxValues();
//				String[] receivedDateArray = caseCreationUpdate.getUpdatedReceivedDates();
				SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
				
				
				if(updateCase){
					
					Iterator itrNew = list.iterator();
					while (itrNew.hasNext()) {
						ICaseCreation item = (ICaseCreation) itrNew.next();
						
								ICaseCreation creation =new com.integrosys.cms.app.caseCreation.bus.OBCaseCreation();
								creation.setChecklistitemid(item.getChecklistitemid());
								//creation.setRemark(remarks);
								creation.setCaseDate(item.getCaseDate());
								creation.setStatus(item.getStatus());
								System.out.println("item.getStatus()=>"+item.getStatus()+" ** item.getChecklistitemid()=>"+item.getChecklistitemid()+" trxValueOut=>"+trxValueOut+" ** status=>"+status+" ** count=>"+count);
								creation.setCasecreationid(trxValueOut.getStagingCaseCreation().getId()); 
								creation.setRequestedDate(item.getRequestedDate());
								creation.setDispatchedDate(item.getDispatchedDate());
								creation.setReceivedDate(item.getReceivedDate());
								creation.setLimitProfileId(trxValueOut.getCaseCreation().getLimitProfileId());
								
								creation.setDocumentBarCode(item.getDocumentBarCode());
								creation.setBoxBarCode(item.getBoxBarCode());
								creation.setDocumentAmount(item.getDocumentAmount());
								creation.setFileBarCode(item.getFileBarCode());
								creation.setLotNumber(item.getLotNumber());
								creation.setPlaceOfExecution(item.getPlaceOfExecution());
								creation.setRackNumber(item.getRackNumber());
								creation.setRetrievaldate(item.getRetrievaldate());
								creation.setStampDuty(item.getStampDuty());
								creation.setSubmittedTo(item.getSubmittedTo());
								creation.setUserName(item.getUserName());
								creation.setVaultLocation(item.getVaultLocation());
								creation.setVaultNumber(item.getVaultNumber());
								creation.setVaultReceiptDate(item.getVaultReceiptDate());
								
								if (hm.containsKey(String.valueOf(item.getChecklistitemid()))) {
									System.out.println("Inside if hm.containsKey = item.getChecklistitemid()=>"+item.getChecklistitemid()+" ** status=>"+status);
									creation.setStatus(status);
									if("1".equalsIgnoreCase(status)){
										creation.setRequestedDate(applicationDate);
									}
									if("2".equalsIgnoreCase(status)){
										creation.setDispatchedDate(applicationDate);
									}
									if("3".equalsIgnoreCase(status)){
										creation.setReceivedDate(applicationDate);
									}
								}
								
								flag = false;
								strNum = Integer.toString(num);
//								removedSrNum
								for(int i=0;i<srNumList.size();i++) {
								if(strNum.equals(srNumList.get(i)) ) {
									flag = true;
								}
								}
								num++;
								System.out.println("Removed sr num flag =>"+flag);
								
								if("3".equalsIgnoreCase(status)){
									System.out.println("Inside 3 status = "+status);
									if(vaultLocationArray != null) {
										if(!"".equals(vaultLocationArray[count])) {
										creation.setVaultLocation(vaultLocationArray[count]);
										}
									}
									if(stampDutyArray != null) {
										if(!"".equals(stampDutyArray[count])) {
										creation.setStampDuty(stampDutyArray[count]);
										}
									}
									if(placeOfExecutionArray != null) {
										if(!"".equals(placeOfExecutionArray[count])) {
										creation.setPlaceOfExecution(placeOfExecutionArray[count]);
										}
									}
									if(vaultNumberArray != null) {
										if(!"".equals(vaultNumberArray[count])) {
										creation.setVaultNumber(vaultNumberArray[count]);
										}
									}
									if(boxBarCodeArray != null) {
										if(!"".equals(boxBarCodeArray[count])) {
										creation.setBoxBarCode(boxBarCodeArray[count]);
										}
									}
									if(fileBarCodeArray != null) {
										if(!"".equals(fileBarCodeArray[count])) {
										creation.setFileBarCode(fileBarCodeArray[count]);
										}
									}
									/*if(rackNumberArray != null) {
										creation.setRackNumber(rackNumberArray[count]);
									}*/
									if(lotNumbersArray != null) {
										if(!"".equals(lotNumbersArray[count])) {
										creation.setLotNumber(lotNumbersArray[count]);
										}
									}
									/*if(receivedDateArray != null) {
										if(!"".equals(receivedDateArray[count].trim())) {
											creation.setReceivedDate(relationshipDateFormat.parse(receivedDateArray[count]));
										}
									}*/
									if(vaultLocationArray != null) {
										if(!"".equals(vaultLocationArray[count])) {
										creation.setVaultLocation(vaultLocationArray[count]);
										}
									}
									creation.setUpdatedVaultLocations(caseCreationUpdate.getUpdatedVaultLocations());
									creation.setUpdatedBoxBarCodes(caseCreationUpdate.getUpdatedBoxBarCodes());
									creation.setUpdatedFileBarCodes(caseCreationUpdate.getUpdatedFileBarCodes());
									creation.setUpdatedLotNumbers(caseCreationUpdate.getUpdatedLotNumbers());
									creation.setUpdatedPlaceOfExecutions(caseCreationUpdate.getUpdatedPlaceOfExecutions());
//									creation.setUpdatedRackNumbers(caseCreationUpdate.getUpdatedRackNumbers());
									creation.setUpdatedStampDutys(caseCreationUpdate.getUpdatedStampDutys());
									creation.setUpdatedVaultNumbers(caseCreationUpdate.getUpdatedVaultNumbers());
//									creation.setUpdatedReceivedDates(caseCreationUpdate.getUpdatedReceivedDates());
									
									
									
								}
								
								if("6".equalsIgnoreCase(status)){
									System.out.println("Inside 6 status = "+status);
									/*if(docBarcodeArray != null) {
									System.out.println("Inside 6 status = "+status);
									if(docBarcodeArray != null) {
										creation.setDocumentBarCode(docBarcodeArray[count]);
									}*/
									if(fileBarCodeArray != null) {
										if(!"".equals(fileBarCodeArray[count])) {
										creation.setFileBarCode(fileBarCodeArray[count]);
										}
									}
									if(boxBarCodeArray != null) {
										if(!"".equals(boxBarCodeArray[count])) {
										creation.setBoxBarCode(boxBarCodeArray[count]);
										}
									}
									/*if(rackNumberArray != null) {
										creation.setRackNumber(rackNumberArray[count]);
									}*/
									if(lotNumbersArray != null) {
										if(!"".equals(lotNumbersArray[count])) {
										creation.setLotNumber(lotNumbersArray[count]);
										}
									}
									if(vaultLocationArray != null) {
										if(!"".equals(vaultLocationArray[count])) {
										creation.setVaultLocation(vaultLocationArray[count]);
										}
									}
									creation.setUpdatedBoxBarCodes(caseCreationUpdate.getUpdatedBoxBarCodes());
									/*creation.setUpdatedDocBarcodes(caseCreationUpdate.getUpdatedDocBarcodes());*/
									creation.setUpdatedFileBarCodes(caseCreationUpdate.getUpdatedFileBarCodes());
									creation.setUpdatedLotNumbers(caseCreationUpdate.getUpdatedLotNumbers());
//									creation.setUpdatedRackNumbers(caseCreationUpdate.getUpdatedRackNumbers());
									creation.setUpdatedVaultLocations(caseCreationUpdate.getUpdatedVaultLocations());
								}
								
								if("8".equalsIgnoreCase(status) || "9".equalsIgnoreCase(status)){
									System.out.println("Inside 8 or 9 status = "+status);
									if(retrievaldateArray != null) {
										if(!"".equals(retrievaldateArray[count])) {
										creation.setRetrievaldate(relationshipDateFormat.parse(retrievaldateArray[count]));
										}
										//limit.setEscodLevelOneDelayDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getEscodL1().trim()));
									}
									if(userNameArray != null) {
										if(!"".equals(userNameArray[count])) {
										creation.setUserName(userNameArray[count]);
										}
									}
									if(submittedToArray != null) {
										if(!"".equals(submittedToArray[count])) {
										creation.setSubmittedTo(submittedToArray[count]);
										}
									}
									
									creation.setUpdatedRetrievaldates(caseCreationUpdate.getUpdatedRetrievaldates());
									creation.setUpdatedSubmittedTos(caseCreationUpdate.getUpdatedSubmittedTos());
									creation.setUpdatedUserNames(caseCreationUpdate.getUpdatedUserNames());
									
								//	Note: Remark is manadatory and document name(description) is mandatory
									
								}
								if("7".equalsIgnoreCase(status)){
									System.out.println("Inside 7 status = "+status);
									/*if(docBarcodeArray != null) {
									System.out.println("Inside 7 status = "+status);
									if(docBarcodeArray != null) {
										creation.setDocumentBarCode(docBarcodeArray[count]);
									}*/
									/*if(fileBarCodeArray != null) {
										creation.setFileBarCode(fileBarCodeArray[count]);
									}*/
									if(vaultNumberArray != null) {
										if(!"".equals(vaultNumberArray[count])) {
										creation.setVaultNumber(vaultNumberArray[count]);
										}
									}
									if(vaultReceiptDateArray != null) {
										if(!"".equals(vaultReceiptDateArray[count])) {
											creation.setVaultReceiptDate(relationshipDateFormat.parse(vaultReceiptDateArray[count]));
										}
									}
									if(vaultLocationArray != null) {
										if(!"".equals(vaultLocationArray[count])) {
										creation.setVaultLocation(vaultLocationArray[count]);
										}
									}
									if(rackNumberArray != null) {
										if(!"".equals(rackNumberArray[count])) {
										creation.setRackNumber(rackNumberArray[count]);
										}
									}
									/*creation.setUpdatedDocBarcodes(caseCreationUpdate.getUpdatedDocBarcodes());*/
//									creation.setUpdatedFileBarCodes(caseCreationUpdate.getUpdatedFileBarCodes());
									creation.setUpdatedVaultNumbers(caseCreationUpdate.getUpdatedVaultNumbers());
									creation.setUpdatedVaultReceiptDates(caseCreationUpdate.getUpdatedVaultReceiptDates());
									creation.setUpdatedVaultLocations(caseCreationUpdate.getUpdatedVaultLocations());
									creation.setUpdatedRackNumbers(caseCreationUpdate.getUpdatedRackNumbers());
								}
								
								if(checkBoxValuesArray != null) {
									if("checked".equals(checkBoxValuesArray[count])) {
										creation.setStatus(status);
									}else {
										creation.setStatus(item.getStatus());
									}
								}
								
								/*if(docBarcodeArray != null) {
									creation.setDocumentBarCode(docBarcodeArray[count]);
								}
								if(boxBarCodeArray != null) {
									creation.setBoxBarCode(boxBarCodeArray[count]);
								}
								if(documentAmountArray != null) {
									creation.setDocumentAmount(documentAmountArray[count]);
								}
								if(fileBarCodeArray != null) {
									creation.setFileBarCode(fileBarCodeArray[count]);
								}
								if(lotNumbersArray != null) {
									creation.setLotNumber(lotNumbersArray[count]);
								}
								if(placeOfExecutionArray != null) {
									creation.setPlaceOfExecution(placeOfExecutionArray[count]);
								}
								if(rackNumberArray != null) {
									creation.setRackNumber(rackNumberArray[count]);
								}
								if(retrievaldateArray != null) {
									creation.setRetrievaldate(relationshipDateFormat.parse(retrievaldateArray[count]));
								}
								if(stampDutyArray != null) {
									creation.setStampDuty(stampDutyArray[count]);
								}
								if(submittedToArray != null) {
									creation.setSubmittedTo(submittedToArray[count]);
								}
								if(userNameArray != null) {
									creation.setUserName(userNameArray[count]);
								}
								if(vaultLocationArray != null) {
									creation.setVaultLocation(vaultLocationArray[count]);
								}
								if(vaultNumberArray != null) {
									creation.setVaultNumber(vaultNumberArray[count]);
								}
								if(vaultReceiptDateArray != null) {
									creation.setVaultReceiptDate(relationshipDateFormat.parse(vaultReceiptDateArray[count]));
								}*/
								
								/*creation.setUpdatedBoxBarCodes(caseCreationUpdate.getUpdatedBoxBarCodes());
								creation.setUpdatedDocAmounts(caseCreationUpdate.getUpdatedDocAmounts());
								creation.setUpdatedDocBarcodes(caseCreationUpdate.getUpdatedDocBarcodes());
								creation.setUpdatedFileBarCodes(caseCreationUpdate.getUpdatedFileBarCodes());
								creation.setUpdatedLotNumbers(caseCreationUpdate.getUpdatedLotNumbers());
								creation.setUpdatedPlaceOfExecutions(caseCreationUpdate.getUpdatedPlaceOfExecutions());
								creation.setUpdatedRackNumbers(caseCreationUpdate.getUpdatedRackNumbers());
								creation.setUpdatedRetrievaldates(caseCreationUpdate.getUpdatedRetrievaldates());
								creation.setUpdatedStampDutys(caseCreationUpdate.getUpdatedStampDutys());
								creation.setUpdatedSubmittedTos(caseCreationUpdate.getUpdatedSubmittedTos());
								creation.setUpdatedUserNames(caseCreationUpdate.getUpdatedUserNames());
								creation.setUpdatedVaultLocations(caseCreationUpdate.getUpdatedVaultLocations());
								creation.setUpdatedVaultNumbers(caseCreationUpdate.getUpdatedVaultNumbers());
								creation.setUpdatedVaultReceiptDates(caseCreationUpdate.getUpdatedVaultReceiptDates());*/
								
								
								caseCreationDao.createCaseCreation("stageCaseCreation", creation);
							if(flag == false) {
								count++;
							}
							//itemList.add(item);
							
							
							
							//com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreationDao iCaseCreationDao = (com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreationDao) BeanHouse.get("caseCreationUpdateDao");
						}
					}

					resultMap.put("request.ITrxValue", trxValueOut);
				
			}catch (CaseCreationException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
			catch (TransactionException e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				throw (new CommandProcessingException(e.getMessage()));
			}catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
	    }


}
