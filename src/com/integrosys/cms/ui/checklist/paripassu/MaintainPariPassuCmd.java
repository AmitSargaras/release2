/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.checklist.paripassu;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.IBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSLegalEntity;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.otherbranch.bus.IOtherBranchDAO;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.ISystemBankBusManager;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
@author $Author: Abhijeet J$
* Command for Maintain Pari Passu Checklist
 */
public class MaintainPariPassuCmd extends AbstractCommand implements ICommonEventConstant {
	

	IOtherBranchDAO objOtherBranchDao;
	ISystemBankBusManager systemBankBusManager;
	
	IGeneralParamDao objGeneralParamDao;
	private ICheckListProxyManager checklistProxyManager;

	private ICheckListTemplateProxyManager checklistTemplateProxyManager;
	
	IRecurrentProxyManager recurrentProxyManager = (IRecurrentProxyManager) BeanHouse.get("recurrentProxy");


	public IOtherBranchDAO getObjOtherBranchDao() {
		return objOtherBranchDao;
	}

	public void setObjOtherBranchDao(IOtherBranchDAO objOtherBranchDao) {
		this.objOtherBranchDao = objOtherBranchDao;
	}
	public IGeneralParamDao getObjGeneralParamDao() {
		return objGeneralParamDao;
	}

	public void setObjGeneralParamDao(IGeneralParamDao objGeneralParamDao) {
		this.objGeneralParamDao = objGeneralParamDao;
	}

	/**
	 * Default Constructor
	 */
	
	
	public MaintainPariPassuCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	 public String[][] getParameterDescriptor() {
			return (new String[][] { { "checkListID", "java.lang.String", REQUEST_SCOPE },
					{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
					{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner", FORM_SCOPE },
					{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE }, { "orgCode", "java.lang.String", REQUEST_SCOPE },
					{ "secType", "java.lang.String", REQUEST_SCOPE }, { "secSubType", "java.lang.String", REQUEST_SCOPE },
					{ "dispatchToMaintain", "java.lang.String", REQUEST_SCOPE },
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }
						});
		}

	 public String[][] getResultDescriptor() {
		 

			return (new String[][] { { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
					{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
					{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner", FORM_SCOPE },
					{ "wip", "java.lang.String", REQUEST_SCOPE }, { "no_template", "java.lang.String", REQUEST_SCOPE },
					{ "frame", "java.lang.String", SERVICE_SCOPE }, { "docNos", "java.util.ArrayList", SERVICE_SCOPE },
//	                { "colowner", "com.integrosys.cms.app.custodian.bus.CollateralCustodianInfo", REQUEST_SCOPE },
	                { "isViewFlag", "java.lang.Boolean", REQUEST_SCOPE },
	                { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
					{"systembank","com.integrosys.cms.app.systemBank.bus.ISystemBank",REQUEST_SCOPE},
					{"otherbank","com.integrosys.cms.ui.otherbankbranch.IOtherBank",REQUEST_SCOPE},
					{"bankingMethod","java.lang.String", REQUEST_SCOPE}
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
			DefaultLogger.debug(this, "Inside doExecute()");
	        boolean isViewFlag = false;
	        Date docDueDate=new Date();
			String tCheckListID = (String) map.get("checkListID");
			long checkListID = Long.parseLong(tCheckListID);
			String secType = (String) map.get("secType");
			String secSubType = (String) map.get("secSubType");
			String limitBkgLoc = (String) map.get("limitBkgLoc");
			String orgCode = (String) map.get("orgCode");
			int tempLength=0;
			
			int countin=0;
			//Start:Code added for getting Values from General Param
			IGeneralParamEntry objGeneralParamEntry=getObjGeneralParamDao().getGeneralParamEntryByPrimaryKey("actualGeneralParamEntry",new Long(ICMSConstant.PARIPASSU_DUE_DATE_GENERAL_PARAM));
			//End  :Code added for getting Values from General Param
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			Date camDate=limit.getCamLoginDate();
			Date expDate=limit.getNextAnnualReviewDate();
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>camDate<<<<<<<<<<<<<1>>>>> " + camDate);
			docDueDate=CommonUtil.rollUpDateByMonths(camDate, Integer.parseInt(objGeneralParamEntry.getParamValue()));
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>docDueDate<<<<<<<<<<<<<2>>>>> " + docDueDate);
			long limitProfileID = limit.getLimitProfileID();
			String custCategory = "MAIN_BORROWER";
			String applicationType = "COM";
//			String tCollateralID = "200701010000130";
			long paripassuID = 0L;
			ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, paripassuID, custCategory,
					applicationType);
			//ICollateralCheckListOwner owner = (OBCollateralCheckListOwner) map.get("ownerObj");
			ICheckList checkList =  new OBCheckList();
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<3>>>>> ");
			OBCMSLegalEntity theOBCMSLegalEntity=(OBCMSLegalEntity) theOBTrxContext.getCustomer().getCMSLegalEntity();
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<4>>>>> ");
			String bankingMethod=theOBTrxContext.getCustomer().getBankingMethod();
			long subProfileId = theOBTrxContext.getCustomer().getCustomerID();
			if(null==bankingMethod) {
				System.out.println("***************inside MaintainPariPassuCmd****176*****inside 1st if**");
				bankingMethod=theOBCMSLegalEntity.getFinalBankMethodList();
				if(null==bankingMethod) {
				System.out.println("***************inside MaintainPariPassuCmd****179*****inside 2nd if**");
					String bankingType = null;
					try{
						bankingType = recurrentProxyManager.getBankingType(limitProfileID,subProfileId);
						if(null!=bankingType) {
						String[] str = bankingType.split("-");
						
						if(str.length>1){
							bankingMethod = str[0];
						}else{
							bankingMethod = str[0];
						}
						}
					}
					catch(Exception re)
					{
						throw new CommandProcessingException("failed to retrieve banking method for limit profile id ["
								+ limitProfileID + "], sub profile id [" + subProfileId + "]", re);
					}	
				}}
				if(bankingMethod==null) {
					bankingMethod="";
				}
				System.out.println("***************inside MaintainPariPassuCmd********202********** bankingMethod : "+bankingMethod);
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>bankingMethod<<<<<<<<<<<<<5>>>>> "+bankingMethod);
          ISystemBank objSystemBank=new OBSystemBank();
          ISystemBank objSystemBankNew=new OBSystemBank();
          DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<6>>>>>>>>>>>>>>> ");
          	IBankingMethod[] objBankingMethod= theOBCMSLegalEntity.getBankList();
          	DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>objBankingMethod<<<<<<<<<<<<<7>>>>>>>>>>> "+objBankingMethod);
          	IOtherBranch[] objOtherBranch=new OBOtherBranch[1];
          	if(objBankingMethod!=null) {
			ICheckListItem[] objCheckListItem=new ICheckListItem[objBankingMethod.length] ;
			//checkList.setCheckListType("PARIPASSU");
			
			 objOtherBranch=new OBOtherBranch[objBankingMethod.length];
			
			IOtherBranch[] objOtherBranchNew=null;
			int templength=0;
			for(int i=0;i<objBankingMethod.length;i++)
			{
				objCheckListItem[i]=new OBCheckListItem();
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>objBankingMethod[i].getBankType()<<<<<<<<<<<<<8>>>>>>>>>>>>>>> "+objBankingMethod[i].getBankType());
				if(objBankingMethod[i].getBankType() !=null) {
				if(objBankingMethod[i].getBankType().equals("S"))
					
				{
					try {
						objSystemBank=getSystemBankBusManager().getSystemBankById(objBankingMethod[i].getBankId());
						objCheckListItem[i].setItemCode("");
						objCheckListItem[i].setItemDesc(objSystemBank.getSystemBankName());
						objCheckListItem[i].setExpiryDate(expDate);
						objCheckListItem[i].setEffectiveDate(docDueDate);
					} catch (SystemBankException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TrxParameterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TransactionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
				objOtherBranch[i]=new OBOtherBranch();
				objOtherBranch[i]=getObjOtherBranchDao().getOtherBranchById(objBankingMethod[i].getBankId());
				objCheckListItem[i].setItemCode(objOtherBranch[i].getOtherBranchName());
				objCheckListItem[i].setItemDesc(objOtherBranch[i].getOtherBankCode().getOtherBankName());
				objCheckListItem[i].setExpiryDate(expDate);
				objCheckListItem[i].setEffectiveDate(docDueDate);
				}
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<9>>>>>>>>>>> ");
			}
			}
			
			try {
				//int wip = this.checklistProxyManager.allowCheckListTrx(owner);
				
				int wip =this.checklistProxyManager.allowCheckListTrx(owner);
				if (ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) {
					resultMap.put("wip", "wip");
				}
				else {
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>checkListID<<<<<<<<<<<<<10>>>>>>>>>>> "+checkListID);
					if (checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {

						if ((orgCode != null) && orgCode.equals("null")) {
							orgCode = null;
						}
					//	secType="CAM";
					//	secSubType="CAM";
/*						checkList = this.checklistProxyManager.getDefaultPariPassuCheckList(owner,secType, secSubType,"IN", "");
						checkList = linkInsuranceReceipt(checkList);*/
						checkList.setCheckListItemList(objCheckListItem);
						resultMap.put("checkListTrxVal", null);
						//resultMap.put("ownerObj", checkList.getCheckListOwner());
					}
					else {
						DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>checkListID<<<<<<<<<<<<<11>>>>>>>>>>> "+checkListID);
						ICheckListTrxValue checkListTrxVal = this.checklistProxyManager.getCheckList(checkListID);
						DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<12>>>>>>>>>>> ");
						checkList = checkListTrxVal.getCheckList();
                        int lth=0;
						ICheckListItem[] objchkListItem =checkList.getCheckListItemList();
						DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<13>>>>>>>>>>> ");
						templength=objchkListItem.length;
						for(int i=0;i<templength;i++)
						{
							DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<14>>>>>>>>>>> ");
						if(objchkListItem[i].getExpiryDate().compareTo(expDate)< 0)
						{
							lth++;
						}
					    }
						if(objBankingMethod.length>checkList.getCheckListItemList().length)
						{DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<15>>>>>>>>>>> ");
						for(int i=checkList.getCheckListItemList().length;i<objBankingMethod.length;i++)
						{
							lth++;
						}
						}
						ICheckListItem tempCheckListItem[]=new ICheckListItem[objchkListItem.length+lth] ;
						
						
						DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>checkListID<<<<<<<<<<<<<16>>>>>>>>>>> "+checkListID);
						if(objchkListItem!=null)
						{
							DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<17>>>>>>>>>>> ");
							for(int i=0;i<templength;i++)
							{
								tempCheckListItem[i]=objchkListItem[i];
							}
							DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<18>>>>>>>>>>> ");
							for(int i=0;i<templength;i++)
							{
							
							if(objchkListItem[i].getExpiryDate().compareTo(expDate)< 0)
							{
								tempCheckListItem[i+templength]=new OBCheckListItem();
								tempCheckListItem[i+templength].setItemCode(objchkListItem[i].getItemCode());
								tempCheckListItem[i+templength].setItemDesc(objchkListItem[i].getItemDesc());
								tempCheckListItem[i+templength].setExpiryDate(expDate);
								tempCheckListItem[i+templength].setEffectiveDate(docDueDate);									
							}
							
							}
						}DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<19>>>>>>>>>>> ");
						
						if(objBankingMethod.length>checkList.getCheckListItemList().length)
						{
						Map temphashMap=new HashMap();
						for(int cnt=0;cnt<objchkListItem.length;cnt++)
						{
							temphashMap.put(objchkListItem[cnt].getItemCode(), objchkListItem[cnt].getItemDesc());
						}
						objOtherBranchNew=new OBOtherBranch[objBankingMethod.length-checkList.getCheckListItemList().length];
						for(int i=checkList.getCheckListItemList().length;i<objBankingMethod.length;i++)
						{

							objCheckListItem[i]=new OBCheckListItem();

							while(countin<objBankingMethod.length)
							{
								objOtherBranchNew[tempLength]=new OBOtherBranch();
								objOtherBranchNew[tempLength]=getObjOtherBranchDao().getOtherBranchById(objBankingMethod[countin].getBankId());
								if(!temphashMap.containsKey(objOtherBranchNew[tempLength].getOtherBranchName()))
								{
									countin++;
									break;
								}
								countin++;

							}
							tempCheckListItem[i]=new OBCheckListItem();
							tempCheckListItem[i].setItemCode(objOtherBranchNew[tempLength].getOtherBranchName());
							tempCheckListItem[i].setItemDesc(objOtherBranchNew[tempLength].getOtherBankCode().getOtherBankName());
							tempCheckListItem[i].setExpiryDate(expDate);
							tempCheckListItem[i].setEffectiveDate(docDueDate);
							tempLength++;
							}


						

						}
						checkList.setCheckListItemList(tempCheckListItem);
/*						if (checkList.getTemplateID() <= 0) {
							DefaultLogger.warn(this, "There is template id for checklist id [" + checkListID
									+ "], retrieving template id");

							ITemplate template = this.checklistTemplateProxyManager.getPariPassuTemplate(secType,
									secSubType, limitBkgLoc);
							if (template != null) {
								checkList.setTemplateID(template.getTemplateID());
							}
						}*/
						DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<20>>>>>>>>>>> ");
						resultMap.put("ownerObj", checkList.getCheckListOwner());
						resultMap.put("checkListTrxVal", checkListTrxVal);
					}

					if (checkList.getCheckListItemList() != null) {
						Arrays.sort(checkList.getCheckListItemList());
					}
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<21>>>>>>>>>>> ");
					String checkListStatus = checkList.getCheckListStatus();
					// perform sorting only if checklist status is not NEW
					if ((checkListStatus == null)
							|| ((checkListStatus != null) && !checkListStatus.equals(ICMSConstant.STATE_CHECKLIST_NEW))) {
						ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
						checkList.setCheckListItemList(sortedItems);
					}
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<22>>>>>>>>>>> ");
					String dispatchToMaintain = ("Y".equals(map.get("dispatchToMaintain")) || "Y".equals(checkList
							.getDisableCollaborationInd())) ? "Y" : "N";
					checkList.setDisableCollaborationInd(dispatchToMaintain);
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<23>>>>>>>>>>> ");
					// CR-236
					String event = (String) map.get("event");
					if ("delete".equals(event)) {
						((OBCheckList) checkList).setObsolete(ICMSConstant.TRUE_VALUE);
					}else if("view".equals(event)){
	                    isViewFlag = true;
	                }
					resultMap.put("checkList", checkList);
	                resultMap.put("isViewFlag",new Boolean(isViewFlag));
				}
				resultMap.put("frame", "true");// used to hide frames when user
				// comes from to do list

				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<24>>>>>>>>>>> ");
				LimitDAO limitDao = new LimitDAO();
				try {
				String migratedFlag = "N";	
				boolean status = false;	
				 status = limitDao.getCAMMigreted("CMS_CHECKLIST",checkListID,"CHECKLIST_ID");
				
				if(status)
				{
					migratedFlag= "Y";
				}
				resultMap.put("migratedFlag", migratedFlag);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			catch (CheckListException ex) {
				throw new CommandProcessingException("fail to maintain security checklist", ex);
			}
          	}
			DefaultLogger.debug(this, "Going out of doExecute()");
			resultMap.put("systembank", objSystemBank);
			resultMap.put("otherbank", objOtherBranch);
			resultMap.put("bankingMethod", bankingMethod);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
	    }

		public ISystemBankBusManager getSystemBankBusManager() {
			return systemBankBusManager;
		}

		public void setSystemBankBusManager(ISystemBankBusManager systemBankBusManager) {
			this.systemBankBusManager = systemBankBusManager;
		}

		public ICheckListProxyManager getChecklistProxyManager() {
			return checklistProxyManager;
		}

		public void setChecklistProxyManager(
				ICheckListProxyManager checklistProxyManager) {
			this.checklistProxyManager = checklistProxyManager;
		}

		public void setChecklistTemplateProxyManager(
				ICheckListTemplateProxyManager checklistTemplateProxyManager) {
			this.checklistTemplateProxyManager = checklistTemplateProxyManager;
		}

		private ICheckList linkInsuranceReceipt(ICheckList checkList) throws CheckListException {
			ICheckListItem[] existingItems = checkList.getCheckListItemList();

			HashMap receiptMap = CheckListHelper.getPremiumReceiptMap();

			for (int i = existingItems.length - 1; i >= 0; i--) {
				ICheckListItem parentItem = existingItems[i];
				if (parentItem.getItem().getMonitorType() != null) {
					if (parentItem.getItem().getMonitorType().equals(ICMSConstant.INSURANCE_POLICY)
							&& !CheckListHelper.isExpired(parentItem.getItem())) {
						String childCode = (String) receiptMap.get(parentItem.getItem().getItemCode());
						if (childCode == null) {
							continue; // no receipt tied to the policy, so no need
							// to spawn.
						}
						ICheckListItem childItem = CheckListHelper.getPremiumReceiptItem(childCode, existingItems);
						if (childItem != null) {
							long ref = this.checklistProxyManager.generateCheckListItemSeqNo();
							parentItem.setCheckListItemRef(ref);
							childItem.setParentCheckListItemRef(ref);
						}
					}
				}
			}
			return checkList;
		}

}
