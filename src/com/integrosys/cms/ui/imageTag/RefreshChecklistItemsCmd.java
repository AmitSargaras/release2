/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.imageTag;

/**
 * 
 * 
 * @author abhijit.rudrakshawar
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListDAO;
import com.integrosys.cms.app.recurrent.bus.RecurrentDAOFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshChecklistItemsCmd extends AbstractCommand {

	private IImageTagProxyManager imageTagProxyManager;

	private ICheckListProxyManager checklistProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
//				{ IGlobalConstant.USER_TEAM,"com.integrosys.component.bizstructure.app.bus.ITeam",GLOBAL_SCOPE },
				{ "securityId", "java.lang.String", REQUEST_SCOPE },
				{ "facilityId", "java.lang.String", REQUEST_SCOPE },
				{ "docTypeCode", "java.lang.String", REQUEST_SCOPE },
//				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
//				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
//				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String",GLOBAL_SCOPE },
//				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ,"com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
//				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,"com.integrosys.cms.app.customer.bus.ICMSCustomer",GLOBAL_SCOPE },
//				{ "securitySubType", "java.lang.String", REQUEST_SCOPE },
//				{"customerSearchCriteria","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",FORM_SCOPE },
//				{"customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
//				{ "ImageTagAddObj","com.integrosys.cms.app.imageTag.bus.OBImageTagAdd",FORM_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custId", "java.lang.String", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
//				{IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",GLOBAL_SCOPE },
//				{ "frompage", "java.lang.String", REQUEST_SCOPE },
//				{ "from", "java.lang.String", REQUEST_SCOPE },
//				{ "indicator", "java.lang.String", REQUEST_SCOPE }, 
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
//				{IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",GLOBAL_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "documentItemList", "java.util.List", REQUEST_SCOPE },
//				{ "customerList","com.integrosys.base.businfra.search.SearchResult",FORM_SCOPE },
//				{ "customerList","com.integrosys.base.businfra.search.SearchResult",SERVICE_SCOPE },
//				{ "customerList","com.integrosys.base.businfra.search.SearchResult",REQUEST_SCOPE },
//				{ "secType", "java.lang.String", REQUEST_SCOPE },
//				{"customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
//				{ "ImageTagAddObj","com.integrosys.cms.app.imageTag.bus.OBImageTagAdd",FORM_SCOPE },
//				{ "imageTagAddForm","com.integrosys.cms.ui.imageTag.ImageTagAddForm",FORM_SCOPE },
//				{ "obImageTagAddList", "java.util.ArrayList", FORM_SCOPE },
//				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
//				{ "obImageTagAddList", "java.util.ArrayList", REQUEST_SCOPE },
//				{ "securityOb", "java.util.HashMap", REQUEST_SCOPE },
//				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
//				{ "secTypeList", "java.util.List", REQUEST_SCOPE },
//				{ "secSubtypeList", "java.util.List", REQUEST_SCOPE },
//				{ "securityIdList", "java.util.List", REQUEST_SCOPE },
//				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String",GLOBAL_SCOPE },
//				{ "customerObList", "java.util.Collection", REQUEST_SCOPE },
//				{ "startIndex", "java.lang.String", GLOBAL_SCOPE },
//				{IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",GLOBAL_SCOPE },
//				{ "from", "java.lang.String", REQUEST_SCOPE },
//				{"imageTagProxyManager","com.integrosys.cms.app.customer.bus.IImageTagProxyManager",SERVICE_SCOPE }
				{"docType","java.lang.String", SERVICE_SCOPE},

		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();

		/*ILimitProfile limitProfileOB = (ILimitProfile) map
				.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		if (limitProfileOB == null) {
				throw new CommandProcessingException("ILimitProfile is null in session!");
			}*/
//============================================Required values for processing=========================>
/*		ILimitProfile limitProfileOB=new OBLimitProfile();
		try {
			limitProfileOB = limitProxy.getLimitProfile(profileID);
		} catch (LimitException e1) {
			e1.printStackTrace();
		}
*/
		String strLimitProfileId=(String) map.get("custLimitProfileID");
		long limitProfileID = Long.parseLong(strLimitProfileId);
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		String collatralId=(String) map.get("securityId");
		String facilityId=(String) map.get("facilityId");
		String docType=(String) map.get("docTypeCode");
		List documentItemList = new ArrayList();
//===========================================Processing starts as per doc type==========================
		if(IImageTagConstants.SECURITY_DOC.equals(docType) && ! ("".equals(collatralId))){
		//			For doc type security 
		try {
			HashMap checkListMap = this.checklistProxyManager.getAllCollateralCheckListSummaryList(theOBTrxContext, limitProfileID);
			long checkListID=ICMSConstant.LONG_INVALID_VALUE;
			if (checkListMap != null) {
				CollateralCheckListSummary[] colChkList = (CollateralCheckListSummary[]) checkListMap.get(ICMSConstant.NORMAL_LIST);
				if(colChkList!=null){
				for (int i = 0; i < colChkList.length; i++) {
					CollateralCheckListSummary collateralCheckListSummary = colChkList[i];
					if(collateralCheckListSummary.getCollateralID()==Long.parseLong(collatralId)){
						checkListID = collateralCheckListSummary.getCheckListID();
						if(checkListID!=ICMSConstant.LONG_INVALID_VALUE){
							ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(checkListID);
							ICheckList checkList = checkListTrxValue.getCheckList();
							ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
							for (int j = 0; j < checkListItemList.length; j++) {
								ICheckListItem iCheckListItem = checkListItemList[j];
								DefaultLogger.debug(this, "In Test 4. Got the item list ");
								DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
								DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
								DefaultLogger.debug(this, "Going out of Test 4. ");
//								String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
								String label=iCheckListItem.getItemDesc();
								String value= String.valueOf(iCheckListItem.getCheckListItemID());
								LabelValueBean lvBean = new LabelValueBean(label,value);
								documentItemList.add(lvBean);
							}
							
						}
						break;
					}
					
				}
				}
			}

		} catch (CheckListException e) {
			e.printStackTrace();
		}
		}else if(IImageTagConstants.FACILITY_DOC.equals(docType) && ! ("".equals(facilityId))){
			//			For doc type facility			
		  try {
			CheckListSearchResult checkListSearchResult=checklistProxyManager.getCheckListByCollateralID(Long.parseLong(facilityId));
			if(checkListSearchResult!=null){
				long facilityCheckListID = checkListSearchResult.getCheckListID();
				
				if(facilityCheckListID!=ICMSConstant.LONG_INVALID_VALUE){
					ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(facilityCheckListID);
					ICheckList checkList = checkListTrxValue.getCheckList();
					ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
					for (int j = 0; j < checkListItemList.length; j++) {
						ICheckListItem iCheckListItem = checkListItemList[j];
						DefaultLogger.debug(this, "In Test 6. Got the item list ");
						DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
						DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
						DefaultLogger.debug(this, "Going out of Test 6. ");
//						String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
						String label=iCheckListItem.getItemDesc();
						String value= String.valueOf(iCheckListItem.getCheckListItemID());
						LabelValueBean lvBean = new LabelValueBean(label,value);
						documentItemList.add(lvBean);
					}
				}				
			}

		  } catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (CheckListException e) {
			e.printStackTrace();
		}		
		}else if(IImageTagConstants.CAM_DOC.equals(docType) ){
			//			For doc type CAM			
			try {
				CheckListSearchResult camCheckList= CheckListDAOFactory.getCheckListDAO().getCAMCheckListByCategoryAndProfileIDMaintain("CAM",limitProfileID);
				if(camCheckList!=null){
				long camCheckListID = camCheckList.getCheckListID();
				
					ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
					ICheckList checkList = checkListTrxValue.getCheckList();
					ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
					for (int j = 0; j < checkListItemList.length; j++) {
						ICheckListItem iCheckListItem = checkListItemList[j];
						DefaultLogger.debug(this, "In Test 6. Got the item list ");
						DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
						DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
						DefaultLogger.debug(this, "Going out of Test 6. ");
//						String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
						String label=iCheckListItem.getItemDesc();
						String value= String.valueOf(iCheckListItem.getCheckListItemID());
						LabelValueBean lvBean = new LabelValueBean(label,value);
						documentItemList.add(lvBean);
					}
				}
			} catch (CheckListException e) {
				e.printStackTrace();
			}
		}else if(IImageTagConstants.RECURRENTDOC_DOC.equals(docType) ){
			//			For doc type Recurrent documents			
			try {
				CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("REC",limitProfileID);
				if(camCheckList!=null){
					long camCheckListID = camCheckList.getCheckListID();
					
					ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
					ICheckList checkList = checkListTrxValue.getCheckList();
					ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
					for (int j = 0; j < checkListItemList.length; j++) {
						ICheckListItem iCheckListItem = checkListItemList[j];
						DefaultLogger.debug(this, "In Test 6. Got the item list ");
						DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
						DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
						DefaultLogger.debug(this, "Going out of Test 6. ");
//						String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
						String label=iCheckListItem.getItemDesc();
						String value= String.valueOf(iCheckListItem.getCheckListItemID());
						LabelValueBean lvBean = new LabelValueBean(label,value);
						documentItemList.add(lvBean);
					}
				}
			} catch (CheckListException e) {
				e.printStackTrace();
			}
		}else if(IImageTagConstants.OTHER_DOC.equals(docType) ){
			//			For doc type Other Documents
			try {
				CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("O",limitProfileID);
				if(camCheckList!=null){
					long camCheckListID = camCheckList.getCheckListID();
					
					ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
					ICheckList checkList = checkListTrxValue.getCheckList();
					ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
					for (int j = 0; j < checkListItemList.length; j++) {
						ICheckListItem iCheckListItem = checkListItemList[j];
						DefaultLogger.debug(this, "In Test 6. Got the item list ");
						DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
						DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
						DefaultLogger.debug(this, "Going out of Test 6. ");
//						String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
						String label=iCheckListItem.getItemDesc();
						String value= String.valueOf(iCheckListItem.getCheckListItemID());
						LabelValueBean lvBean = new LabelValueBean(label,value);
						documentItemList.add(lvBean);
					}
				}
			} catch (CheckListException e) {
				e.printStackTrace();
			}
		}else if(IImageTagConstants.LAD_DOC.equals(docType) ){
			//			For doc type LAD			
			try {
				CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("LAD",limitProfileID);
				if(camCheckList!=null){
				long camCheckListID = camCheckList.getCheckListID();
				
					ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
					ICheckList checkList = checkListTrxValue.getCheckList();
					ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
					for (int j = 0; j < checkListItemList.length; j++) {
						ICheckListItem iCheckListItem = checkListItemList[j];
						DefaultLogger.debug(this, "In Test 6. Got the item list ");
						DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
						DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
						DefaultLogger.debug(this, "Going out of Test 6. ");
//						String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
						String label=iCheckListItem.getItemDesc();
						String value= String.valueOf(iCheckListItem.getCheckListItemID());
						LabelValueBean lvBean = new LabelValueBean(label,value);
						documentItemList.add(lvBean);
					}
				}
			} catch (CheckListException e) {
				e.printStackTrace();
			}
		}else if(IImageTagConstants.CAM_NOTE.equals(docType) ){
			//			For doc type CAM			
			try {
				
				HashMap camCheckListMap = CheckListDAOFactory.getCheckListDAO().getBulkCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
				ArrayList camCheckListArray = new ArrayList();
				camCheckListArray=(ArrayList) camCheckListMap.get("NORMAL_LIST");
				
				if(camCheckListArray!=null){
					for (int j = 0; j < camCheckListArray.size(); j++) {
						CheckListSearchResult checkListSearchResult =(CheckListSearchResult) camCheckListArray.get(j);
						DefaultLogger.debug(this, "In Test 6. Got the item list ");
						DefaultLogger.debug(this, "=="+checkListSearchResult.getCamNumber());
						DefaultLogger.debug(this, "=="+checkListSearchResult.getCamType()+"-"+checkListSearchResult.getCamNumber()+"-"+checkListSearchResult.getCamDate());
						DefaultLogger.debug(this, "Going out of Test 6. ");
//						String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
						String label=checkListSearchResult.getCamNumber();
						String value= checkListSearchResult.getCamNumber()+"-"+checkListSearchResult.getCamType()+"-"+checkListSearchResult.getCamDate();
						LabelValueBean lvBean = new LabelValueBean(label,value);
						documentItemList.add(lvBean);
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
		else if(IImageTagConstants.EXCHANGE_OF_INFO.equals(docType)){
			try{
			long subProfileId=theOBTrxContext.getCustomer().getCustomerID();
			IRecurrentCheckListDAO recurrentCheckListDAO = RecurrentDAOFactory.getRecurrentCheckListDAO();
			long recurrentDocId=recurrentCheckListDAO.getRecurrentDocId(limitProfileID, subProfileId);
			documentItemList = recurrentCheckListDAO.getRecurrentDocIdDesc(recurrentDocId,"Annexure");
			//LabelValueBean lvBean = new LabelValueBean(IImageTagConstants.EXCHANGE_OF_INFO,IImageTagConstants.EXCHANGE_OF_INFO);
			//documentItemList.add(lvBean);
			}
			catch(Exception e){
				DefaultLogger.debug("Exception while retriving Doc Description for Exchange of Information:", e.getMessage());
				e.printStackTrace();
			}
		 }
		//Added by Uma Khot:End: Phase 3 CR:tag scanned images of Annexure II
		//=====================================================
		result.put("documentItemList", documentItemList);		
		result.put("custLimitProfileID", strLimitProfileId);
		result.put("docType", docType);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return returnMap;
	}
}
