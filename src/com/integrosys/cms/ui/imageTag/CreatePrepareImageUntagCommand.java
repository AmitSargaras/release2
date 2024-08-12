package com.integrosys.cms.ui.imageTag;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.collateral.bus.CollateralComparator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterJdbc;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.app.imageTag.bus.OBImageTagDetails;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListDAO;
import com.integrosys.cms.app.recurrent.bus.RecurrentDAOFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.SecurityTypeList;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

/**
 * This command creates a Image Tag
 * 
 * @author abhijit.rudrakshawar
 * 
 * 
 */

public class CreatePrepareImageUntagCommand extends AbstractCommand {
	private ICheckListProxyManager checklistProxyManager;
	
	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}
	
	public String[][] getParameterDescriptor() {
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "ImageTagMapObj", "com.integrosys.cms.app.imageTag.bus.IImageTagDetails", FORM_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				});
	}

	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] { 
				{ "documentItemList", "java.util.List", REQUEST_SCOPE },
				{ "facilityIdList", "java.util.List", REQUEST_SCOPE },
				{ "secTypeList", "java.util.List", REQUEST_SCOPE },
				{ "secSubtypeList", "java.util.List", REQUEST_SCOPE },
				{ "securityIdList", "java.util.List", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "ImageTagMapObj", "com.integrosys.cms.app.imageTag.bus.IImageTagDetails", FORM_SCOPE },
				{"bankList","java.util.List",SERVICE_SCOPE },
				{ "custIdList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "facilityCodeList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "otherDocList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "securityNameIdList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "otherSecDocList", "java.util.ArrayList", SERVICE_SCOPE },

				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws CommandProcessingException
	 *             on errors
	 * @throws CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		DefaultLogger.debug(this, "Enter in doExecute()");
		String strLimitProfileId=(String) map.get("custLimitProfileID");
		
		long profileID = Long.parseLong(strLimitProfileId);
		IImageTagDetails details=(OBImageTagDetails) map.get("ImageTagMapObj");
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		List documentItemList = new ArrayList();	
		List facilityIdList = new ArrayList();	
		List secTypeList = new ArrayList();	
		List secSubtypeList = new ArrayList();	
		List securityIdList = new ArrayList();	
		
		List bankList=new ArrayList();
		List<String> custIdList=new ArrayList<String>();
		List<String> facilityCodeList=new ArrayList<String>();
		List<String> otherDocList=new ArrayList<String>();
		List<String> securityNameIdList=new ArrayList<String>();
		List<String> otherSecDocList=new ArrayList<String>();
		
		if(details!=null && !"".equals(details.getDocType())){
			DefaultLogger.debug(this, "Got the form ");
			String docType = details.getDocType();
			 if(IImageTagConstants.CAM_DOC.equals(docType)){
				 //In case of doc type CAM  populate the documentItemList only
					try {
						CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("CAM",Long.parseLong(strLimitProfileId));
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
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
									String label=iCheckListItem.getItemDesc();
									String value= String.valueOf(iCheckListItem.getCheckListItemID());
									LabelValueBean lvBean = new LabelValueBean(label,value);
									documentItemList.add(lvBean);
								}
						}
					} catch (CheckListException e) {
						e.printStackTrace();
					}
					
			}else  if(IImageTagConstants.RECURRENTDOC_DOC.equals(docType)){
				 //In case of doc type CAM  populate the documentItemList only
				 try {
					 CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("REC",Long.parseLong(strLimitProfileId));
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
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
							 String label=iCheckListItem.getItemDesc();
							 String value= String.valueOf(iCheckListItem.getCheckListItemID());
							 LabelValueBean lvBean = new LabelValueBean(label,value);
							 documentItemList.add(lvBean);
						 }
					 }
				 } catch (CheckListException e) {
					 e.printStackTrace();
				 }
				 
			 }else if(IImageTagConstants.OTHER_DOC.equals(docType)){
				 //In case of doc type CAM  populate the documentItemList only
				 try {
					 CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("O",Long.parseLong(strLimitProfileId));
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
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
							 String label=iCheckListItem.getItemDesc();
							 String value= String.valueOf(iCheckListItem.getCheckListItemID());
							 LabelValueBean lvBean = new LabelValueBean(label,value);
							 documentItemList.add(lvBean);
						 }
					 }
				 } catch (CheckListException e) {
					 e.printStackTrace();
				 }
				 
			 }else if(IImageTagConstants.LAD_DOC.equals(docType)){
				 //In case of doc type CAM  populate the documentItemList only
				 try {
					 CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("LAD",Long.parseLong(strLimitProfileId));
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
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
							 String label=iCheckListItem.getItemDesc();
							 String value= String.valueOf(iCheckListItem.getCheckListItemID());
							 LabelValueBean lvBean = new LabelValueBean(label,value);
							 documentItemList.add(lvBean);
						 }
					 }
				 } catch (CheckListException e) {
					 e.printStackTrace();
				 }
				 
			 }else if(IImageTagConstants.FACILITY_DOC.equals(docType)){
				// In case of doc type Facility populate the facilityIdList
				
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
				try {
					List lmtList = proxy.getLimitSummaryListByAA(Long.toString(Long.parseLong(strLimitProfileId)));
					if(lmtList!=null && lmtList.size()>0){
						String label;
						String value;
						for (int i = 0; i < lmtList.size(); i++) {
							LimitListSummaryItemBase limitSummaryItem=(LimitListSummaryItemBase) lmtList.get(i);
							label=limitSummaryItem.getCmsLimitId() +" - "+limitSummaryItem.getProdTypeCode();
							value= limitSummaryItem.getCmsLimitId();
							LabelValueBean lvBean = new LabelValueBean(label,value);
							facilityIdList.add(lvBean);
						}
					}
				} catch (LimitException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				// if user has selected the facility id populate the documentItemList
				if(details.getFacilityId()!=0){
					try {
						CheckListSearchResult checkListSearchResult=checklistProxyManager.getCheckListByCollateralID(details.getFacilityId());
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
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
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

				}
			}else if(IImageTagConstants.SECURITY_DOC.equals(docType)){
				//if doc type is Security  populate the secTypeList
				secTypeList=getSecurityTypeList();
				//check if secType is selected then populate the secSubtypeList 
				if(!"".equals(details.getSecType())){
					secSubtypeList= getSecuritySubtypeList(details.getSecType());
					//check if secSubtype is selected then populate the securityIdList 
					if(!"".equals(details.getSecSubtype())){
						HashMap lmtcolmap = new HashMap();
						HashMap collateralCodeMap = getCollateralInfo();
						ILimitProxy limitProxy = LimitProxyFactory.getProxy();
						ILimitProfile limitProfileOB=new OBLimitProfile();
						try {
							limitProfileOB = limitProxy.getLimitProfile(profileID);
						} catch (LimitException e1) {
							e1.printStackTrace();
						}
						lmtcolmap = limitProxy.getCollateralLimitMap(limitProfileOB);
	
						Map sortedCollateralLimitMap = new TreeMap(new Comparator() {
							public int compare(Object thisObj, Object thatObj) {
								ICollateral thisCol = (ICollateral) thisObj;
								ICollateral thatCol = (ICollateral) thatObj;
	
								long thisValue = thisCol.getCollateralID();
								long thatValue = thatCol.getCollateralID();
	
								return (thisValue < thatValue ? -1
										: (thisValue == thatValue ? 0 : 1));
							}
						});
						sortedCollateralLimitMap.putAll(lmtcolmap);
						OBCollateral obcol = new OBCollateral();
						String secSubType = details.getSecSubtype();
						Set set = lmtcolmap.keySet();
						ICollateral[] cols = (ICollateral[]) set.toArray(new ICollateral[0]);
						Arrays.sort(cols, new CollateralComparator());
						Iterator i = Arrays.asList(cols).iterator();
						String label;
						String value;
						while (i.hasNext()) {
							obcol = ((OBCollateral) i.next());
							if (obcol.getCollateralSubType().getSubTypeCode().equals(secSubType)) {
								label = obcol.getCollateralID() + " - " + collateralCodeMap.get(obcol.getCollateralCode());
								value  = String.valueOf(obcol.getCollateralID());
								LabelValueBean lvBean = new LabelValueBean(label,value);
								securityIdList.add(lvBean);
							}
	
						}
						//check if securityID is selected then populate the documentItemList	
						if(details.getSecurityId()!=0){
							try {
								HashMap checkListMap = this.checklistProxyManager.getAllCollateralCheckListSummaryList(theOBTrxContext, Long.parseLong(strLimitProfileId));
								long checkListID=ICMSConstant.LONG_INVALID_VALUE;
								if (checkListMap != null) {
									CollateralCheckListSummary[] colChkList = (CollateralCheckListSummary[]) checkListMap.get(ICMSConstant.NORMAL_LIST);
									if(colChkList!=null){
									for (int n = 0; n < colChkList.length; n++) {
										CollateralCheckListSummary collateralCheckListSummary = colChkList[n];
										if(collateralCheckListSummary.getCollateralID()==details.getSecurityId()){
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
//													String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
													String labelCC=iCheckListItem.getItemDesc();
													String valueCC= String.valueOf(iCheckListItem.getCheckListItemID());
													LabelValueBean lvBean = new LabelValueBean(labelCC,valueCC);
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
						}
						
					}
				}
			}
			//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
			else if(IImageTagConstants.EXCHANGE_OF_INFO.equals(docType)){
				try{
				long subProfileId=theOBTrxContext.getCustomer().getCustomerID();
				IRecurrentCheckListDAO recurrentCheckListDAO = RecurrentDAOFactory.getRecurrentCheckListDAO();
				long recurrentDocId=recurrentCheckListDAO.getRecurrentDocId(profileID, subProfileId);
				documentItemList = recurrentCheckListDAO.getRecurrentDocIdDesc(recurrentDocId,"Annexure");
				}
				catch(Exception e){
					DefaultLogger.debug("Exception while retriving Doc Description for Exchange of Information:", e.getMessage());
					e.printStackTrace();
				}
				
			 }
			 	
			 	List<String> systemBankBranchName=new ArrayList<String>();
				List<String> otherBankBranchName=new ArrayList<String>();
				List<String> allBankBranchName=new ArrayList<String>();
				List<String> ifscBankBranchName=new ArrayList<String>();
			//	ImageTagMapForm imageTagMapForm=new ImageTagMapForm();
				
				ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
				String custId=details.getCustId();
				
				String systemBankId = imageTagDaoImpl.getSystemBankId(custId);
				DefaultLogger.debug(this,"systemBankId:"+systemBankId);
				String otherBankId = imageTagDaoImpl.getOtherBankId(custId);
				DefaultLogger.debug(this,"otherBankId:"+otherBankId);
				
				if(null!=systemBankId && !systemBankId.isEmpty()){
					if(systemBankId.lastIndexOf(",")!=-1){
					systemBankId=systemBankId.substring(0, systemBankId.lastIndexOf(","));
					}
					 systemBankBranchName = imageTagDaoImpl.getSystemBankBranchName(systemBankId);
				}
				if(null!=otherBankId && !otherBankId.isEmpty()){
					if(otherBankId.lastIndexOf(",")!=-1){
						otherBankId=otherBankId.substring(0, otherBankId.lastIndexOf(","));
					}
					 otherBankBranchName = imageTagDaoImpl.getOtherBankBranchName(otherBankId);
				}
				
				ifscBankBranchName = imageTagDaoImpl.getIFSCBankBranchName(custId);
				
				bankList.addAll(imageTagDaoImpl.populateBankList(systemBankBranchName));
				bankList.addAll(imageTagDaoImpl.populateBankList(otherBankBranchName));
				bankList.addAll(imageTagDaoImpl.populateBankList(ifscBankBranchName));
			
			//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
				
				
				custIdList = imageTagDaoImpl.getFacilityNames(custId);
						
				facilityCodeList = imageTagDaoImpl.getFacilityCodes(custId);
				
				otherDocList = imageTagDaoImpl.getOtherDocumentList();
				
				securityNameIdList = imageTagDaoImpl.getSecurityNameIds(custId);
				
				otherSecDocList = imageTagDaoImpl.getSecurityOtherDocumentList ();

						
		}
		result.put("ImageTagMapObj", details);
		result.put("limitProfileID", strLimitProfileId);
		result.put("facilityIdList", facilityIdList);
		result.put("secTypeList", secTypeList);
		result.put("secSubtypeList", secSubtypeList);
		result.put("securityIdList", securityIdList);
		result.put("documentItemList", documentItemList);
		
		result.put("bankList", bankList);
		result.put("custIdList", custIdList);
		result.put("facilityCodeList", facilityCodeList);
		result.put("otherDocList", otherDocList);
		result.put("securityNameIdList", securityNameIdList);
		result.put("otherSecDocList", otherSecDocList);
		
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return returnMap;
	}
	private List getSecurityTypeList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) (SecurityTypeList.getInstance()
					.getSecurityTypeProperty());
			List valList = (List) (SecurityTypeList.getInstance()
					.getSecurityTypeLabel(null));
			for (int i = 0; i < idList.size(); i++) {
				String id = idList.get(i).toString();
				String val = valList.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getSecuritySubtypeList(String secTypeValue) {
		List lbValList = new ArrayList();
		try {
			if (secTypeValue != null) {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ICollateralSubType[] subtypeLst = helper.getSBMISecProxy()
						.getCollateralSubTypesByTypeCode(secTypeValue);
				if (subtypeLst != null) {
					for (int i = 0; i < subtypeLst.length; i++) {
						ICollateralSubType nextSubtype = subtypeLst[i];
						String id = nextSubtype.getSubTypeCode();
						String value = nextSubtype.getSubTypeName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	public HashMap getCollateralInfo() {
		HashMap map = new HashMap();
		ICollateralNewMasterJdbc collateralNewMasterJdbc = (ICollateralNewMasterJdbc) BeanHouse.get("collateralNewMasterJdbc");
		SearchResult result = collateralNewMasterJdbc.getAllCollateralNewMaster();
		ArrayList list = (ArrayList) result.getResultList();
		for (int ab = 0; ab < list.size(); ab++) {
			ICollateralNewMaster newMaster = (ICollateralNewMaster) list.get(ab);
			map.put(newMaster.getNewCollateralCode(), newMaster.getNewCollateralDescription());

		}
		return map;
	}
}
