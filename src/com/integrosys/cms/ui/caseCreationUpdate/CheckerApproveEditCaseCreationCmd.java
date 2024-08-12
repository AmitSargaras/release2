package com.integrosys.cms.ui.caseCreationUpdate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreation;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreationDao;
import com.integrosys.cms.app.caseCreation.bus.OBCaseCreation;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.caseCreationUpdate.proxy.ICaseCreationProxyManager;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;
import com.integrosys.cms.app.caseCreationUpdate.trx.OBCaseCreationTrxValue;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Abhijit R $
 * Command for checker to approve edit .
 */

public class CheckerApproveEditCaseCreationCmd extends AbstractCommand implements ICommonEventConstant {


	private ICaseCreationProxyManager caseCreationProxy;


	public ICaseCreationProxyManager getCaseCreationProxy() {
		return caseCreationProxy;
	}

	public void setCaseCreationProxy(
			ICaseCreationProxyManager caseCreationProxy) {
		this.caseCreationProxy = caseCreationProxy;
	}

	public static final String DOC_BAR_CODE_PREFIX = "doc.bar.code.prefix";
	/**
	 * Default Constructor
	 */
	public CheckerApproveEditCaseCreationCmd() {
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"ICaseCreationTrxValue", "com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue", SERVICE_SCOPE},
				{"searchResultCaseCreation", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
				{"receivedList", "java.util.List", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		}
		);
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		}
		);
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
		HashMap checklistItemMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// CaseCreation Trx value
			 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
             IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
 			Date  applicationDate= new Date(generalParamEntries.getParamValue());
			boolean isStatusClosed=true;
			ICaseCreationTrxValue trxValueIn = (OBCaseCreationTrxValue) map.get("ICaseCreationTrxValue");
			SearchResult searchResultCaseCreation = (SearchResult) map.get("searchResultCaseCreation");
			Collection list = searchResultCaseCreation.getResultList();
			String dateFormat = "yyyyMMdd";
			ILimitDAO dao = LimitDAOFactory.getDAO();
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String docBarCodePrefix = bundle.getString(DOC_BAR_CODE_PREFIX);
			String docBarCodeStage = "";
			// Added by Pramod : To get the available checklist items in map
			List receivedLists=(List)map.get("receivedList");
			if(receivedLists!=null){
			ICheckListItem[] actualItems = new OBCheckListItem[receivedLists.size()];
		    for(int i=0;i<receivedLists.size();i++){
		    	actualItems[i]=(ICheckListItem)receivedLists.get(i);
		    	checklistItemMap.put(String.valueOf(actualItems[i].getCheckListItemID()),actualItems[i]);
		    }
		    }
		    
		    // end
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			Iterator itr = list.iterator();
			System.out.println("Inside CheckerApproveEditCaseCreationCmd.");
			
			while (itr.hasNext()) {
				
				ICaseCreation itemold = (ICaseCreation) itr.next();
				if(itemold!=null){
				if(checklistItemMap.containsKey(String.valueOf(itemold.getChecklistitemid()))){	
					System.out.println(" Inside checklistItemMap.containsKey(String.valueOf(itemold.getChecklistitemid()) if condition.");
				/*if(itemold.getStatus().trim().equalsIgnoreCase("3")||itemold.getStatus().trim().equalsIgnoreCase("5")){*/
					if(itemold.getStatus().trim().equalsIgnoreCase("7")||itemold.getStatus().trim().equalsIgnoreCase("5")){
					//isStatusClosed=true;
				}else{
					isStatusClosed=false;
				}
				}
				System.out.println(" itemold.getChecklistitemid()=>"+itemold.getChecklistitemid()+" ** itemold.getStatus()=>"+itemold.getStatus()+" ** isStatusClosed=>"+isStatusClosed);
			}
			}
			if(trxValueIn.getCaseCreation()!=null){
			if(isStatusClosed){
				trxValueIn.getCaseCreation().setStatus("Closed");
				trxValueIn.getStagingCaseCreation().setStatus("Closed");
			}else{
				trxValueIn.getCaseCreation().setStatus("Open");
				trxValueIn.getStagingCaseCreation().setStatus("Open");
			}
			}else{
				if(isStatusClosed){
					trxValueIn.getStagingCaseCreation().setStatus("Closed");
				}else{
					trxValueIn.getStagingCaseCreation().setStatus("Open");
				}
			}
			
			// Function  to approve updated CaseCreation Trx
			boolean isNew= false;
			if(!trxValueIn.getStatus().equals("PENDING_UPDATE")){
				isNew=true;
			}
			ICaseCreationTrxValue trxValueOut = getCaseCreationProxy().checkerApproveCaseCreation(ctx, trxValueIn);
			ICaseCreationDao caseCreationDao=(ICaseCreationDao)BeanHouse.get("caseCreationDao");

			Iterator itrNew = list.iterator();
			while (itrNew.hasNext()) {
				ICaseCreation item = (ICaseCreation) itrNew.next();
				
			
				if(isNew){
					System.out.println("isNew=>"+isNew);
					if(trxValueOut!=null){
						if(trxValueOut.getCaseCreation()!=null){
						ICaseCreation creation =new OBCaseCreation();
						creation.setChecklistitemid(item.getChecklistitemid());
						creation.setRemark(remarks);
						creation.setCaseDate(applicationDate);
						creation.setStatus("1");
						creation.setLimitProfileId(trxValueOut.getCaseCreation().getLimitProfileId());
						creation.setCasecreationid(trxValueOut.getCaseCreation().getId());
						creation.setRequestedDate(applicationDate);
						caseCreationDao.createCaseCreation("actualCaseCreation", creation);
						
					}
				}
				}else{
					if(trxValueOut!=null){
						if(trxValueOut.getCaseCreation()!=null){
						boolean modified=false;
							ICaseCreation creation =caseCreationDao.getCaseCreation("actualCaseCreation",trxValueOut.getCaseCreation().getId(),item.getChecklistitemid());
						if(!creation.getStatus().equalsIgnoreCase(item.getStatus())){
							modified=true;
						}
						System.out.println("IsNew false=> trxValueOut.getCaseCreation().getId()=>"+trxValueOut.getCaseCreation().getId()+" ** item.getChecklistitemid()=>"+item.getChecklistitemid()+" ** item.getStatus()=>"+item.getStatus()+" ** modified=>"+modified);
						creation.setStatus(item.getStatus());
						//creation.setLimitProfileId(trxValueOut.getCaseCreation().getLimitProfileId());
						//creation.setCasecreationid(trxValueOut.getCaseCreation().getId());
						if(modified){
						if(item.getStatus().equals("1")){
							creation.setRequestedDate(item.getRequestedDate());
						}
						if(item.getStatus().equals("2")){
							creation.setDispatchedDate(item.getDispatchedDate());
						}
						if(item.getStatus().equals("3")){
							creation.setReceivedDate(item.getReceivedDate());
						}
						if(item.getStatus().equals("4")){
							creation.setWrongReqDate(applicationDate);
						}
						
						}
						if("3".equals(item.getStatus())){
							creation.setReceivedDate(item.getReceivedDate());
						}
//						creation.setDocumentBarCode(item.getDocumentBarCode());
						
						if("6".equals(item.getStatus()) || "7".equals(item.getStatus())){
							if(item.getDocumentBarCode() != null) {
								if(!item.getDocumentBarCode().contains(docBarCodePrefix)) {
							
							Date date=new Date();
							String fileDate = toDateFormat(date,dateFormat);
							String seqNo = dao.getSeqNoForDocBarCode();
							String docBarCode  = docBarCodePrefix + fileDate + seqNo;
							docBarCodeStage = docBarCode;
							creation.setDocumentBarCode(docBarCode);
							}
							}else {
								
								Date date=new Date();
								String fileDate = toDateFormat(date,dateFormat);
								String seqNo = dao.getSeqNoForDocBarCode();
								String docBarCode  = docBarCodePrefix + fileDate + seqNo;
								docBarCodeStage = docBarCode;
								creation.setDocumentBarCode(docBarCode);
							}
						}
						
						
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
						caseCreationDao.updateCaseCreation("actualCaseCreation", creation);
						
					}
						if(trxValueOut.getStagingCaseCreation()!=null){
							ArrayList creationList =caseCreationDao.getStageCaseCreation("stageCaseCreation", item.getChecklistitemid());
							for(int k=0;k<creationList.size();k++){
								
								ICaseCreation creation=(ICaseCreation)creationList.get(k);
							creation.setStatus(item.getStatus());
							//creation.setLimitProfileId(trxValueOut.getCaseCreation().getLimitProfileId());
							//creation.setCasecreationid(trxValueOut.getCaseCreation().getId());
							if(item.getStatus().equals("1")){
								creation.setRequestedDate(item.getRequestedDate());
							}
							if(item.getStatus().equals("2")){
								creation.setDispatchedDate(item.getDispatchedDate());
							}
							if(item.getStatus().equals("3")){
								creation.setReceivedDate(item.getReceivedDate());
							}
							if(item.getStatus().equals("4")){
								creation.setWrongReqDate(applicationDate);
							}
//							creation.setDocumentBarCode(item.getDocumentBarCode());
							if("6".equals(item.getStatus()) || "7".equals(item.getStatus())){
								if(item.getDocumentBarCode() != null) {
									if(!item.getDocumentBarCode().contains(docBarCodePrefix)) {
								
								creation.setDocumentBarCode(docBarCodeStage);
								}
								}
							}
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
							caseCreationDao.updateCaseCreation("stageCaseCreation", creation);
							}
						}
						
				}
				}
			}
			System.out.println("CheckerApproveEditCaseCreationCmd => Done.");
			
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (CaseCreationException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	
	
	
	public static String toDateFormat(Date date, String formatter) throws Exception {
		try{
			if(date == null )
				return "";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatter);
			return dateFormat.format(date);
		}
		catch(Exception e){
			DefaultLogger.debug("","FCUBSLimitFileUploadJob toDateFormat"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

}



