/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.checklist.facilityreceipt;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.proxy.SBCheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterJdbc;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.OBImageTagDetails;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListDAO;
import com.integrosys.cms.app.recurrent.bus.RecurrentDAOFactory;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;
import com.integrosys.cms.ui.imageTag.ImageTagException;

/**
 *$Author: Abhijit R $ Command for checker to read Image Tag Trx value
 */
public class CheckerReadImageTagFacReceiptCmd extends AbstractCommand implements
		ICommonEventConstant {

	private IImageTagProxyManager imageTagProxyManager;
	private ICollateralProxy collateralProxy;
	private ICheckListProxyManager checkListProxyManager;
	private ILimitProxy limitProxy;

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(
			IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	public ICollateralProxy getCollateralProxy() {
		return collateralProxy;
	}

	public void setCollateralProxy(ICollateralProxy collateralProxy) {
		this.collateralProxy = collateralProxy;
	}

	public ICheckListProxyManager getCheckListProxyManager() {
		return checkListProxyManager;
	}

	public void setCheckListProxyManager(
			ICheckListProxyManager checkListProxyManager) {
		this.checkListProxyManager = checkListProxyManager;
	}

	public ILimitProxy getLimitProxy() {
		return limitProxy;
	}

	public void setLimitProxy(ILimitProxy limitProxy) {
		this.limitProxy = limitProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerReadImageTagFacReceiptCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "TrxIdService", "java.lang.String", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "customerSearchCriteria","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",FORM_SCOPE },
				{ "custTypeTrxID", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] {
				{"OBImageTagDetails","com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",SERVICE_SCOPE },
				{"IImageTagTrxValue","com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue",SERVICE_SCOPE },
				{ "fromPage", "java.Lang.String", REQUEST_SCOPE },		
				{ "trxId", "java.lang.String", REQUEST_SCOPE },
				{ "TrxIdService", "java.lang.String", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "failitySourceID", "java.Lang.String", REQUEST_SCOPE },
				{ "camInfo", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", REQUEST_SCOPE }, 
				// code start:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
				{ "taggedImageIdSet", "java.util.HashSet", SERVICE_SCOPE },});
				// code end:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException, SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap collateralCodeMap = getCollateralInfo();
		IImageTagProxyManager itmageTagProxyManager = (IImageTagProxyManager) BeanHouse.get("imageTagProxy");
		ICollateralProxy icollateralProxy = (ICollateralProxy) BeanHouse.get("collateralProxy");
		ILimitProxy ilimitProxy = (ILimitProxy) BeanHouse.get("limitProxy");
		ICheckListProxyManager icheckListProxyManager = (ICheckListProxyManager) BeanHouse.get("checkListProxyManager");
		SBCheckListProxyManager icheckListProxyManager1 = (SBCheckListProxyManager) BeanHouse.get("slsbCheckListProxyManager");
		String custTypeTrxID = (String) map.get("custTypeTrxID");
		
		try {
			String startIdx = (String) map.get("startIndex");
			
			resultMap.put("startIndex", startIdx);
			CustomerSearchCriteria cSearch =(CustomerSearchCriteria) map.get("customerSearchCriteria");
			IImageTagDetails imageTag;
			List imageList = new ArrayList();
			IImageTagTrxValue trxValue = null;
			String imageCode = (String) (map.get("TrxId"));
			if(imageCode == null) {
//				imageCode = String.valueOf((Long.valueOf(custTypeTrxID) - 1));
				imageCode =getTrxIdforImageTag(custTypeTrxID);
				if("".equals(imageCode)) {
					imageCode = getTrxIdforImageTag1();
				}
			}
			
			String imageCodeService = (String) (map.get("TrxIdService"));
			
			if(imageCode!=null){
				if(!imageCode.equals("")){
					resultMap.put("TrxIdService", imageCode);
				}
			}
			if("".equals(imageCode)){
				if(imageCodeService!=null){
					if(!"".equals(imageCodeService)){
					imageCode=imageCodeService;
					}else{
						if(cSearch!=null){
						imageCode =cSearch.getIdNO();
						}
					}
				}else{
					if(cSearch!=null){
				imageCode =cSearch.getIdNO();
					}
				}
			}
			resultMap.put("trxId", imageCode);
			if(imageCode!=null){
				System.out.println("CheckerReadImageTagFacReceiptCmd.java  imageCode=>"+imageCode);
			// function to get Image Tag Trx value
			trxValue = (OBImageTagTrxValue) itmageTagProxyManager
					.getImageTagByTrxID(imageCode);
			System.out.println("trxValue=>"+trxValue);
			// function to get stging value of Image Tag trx value
			imageTag = (OBImageTagDetails) trxValue.getStagingImageTagDetails();
//			System.out.println("imageTag=>"+imageTag);
			if(imageTag != null) {
			//preparing ImageTagDetails object for view.
			if(IImageTagConstants.SECURITY_DOC.equals(imageTag.getDocType())){
				try {
					ICollateral collateral = icollateralProxy.getCollateral(imageTag.getSecurityId(), false);
					imageTag.setSecTypeLabel(collateral.getCollateralType().getTypeName());
					imageTag.setSecSubtypeLabel(collateral.getCollateralSubType().getSubTypeName());
					imageTag.setSecurityIdLabel(collateral.getCollateralID() + " - " + collateralCodeMap.get(collateral.getCollateralCode()));
				} catch (CollateralException e) {
					e.printStackTrace();
				}
				
			}else if(IImageTagConstants.FACILITY_DOC.equals(imageTag.getDocType())){
				//TODO retrive facility detail and map the the facility label
				try {
					ILimit limit = ilimitProxy.getLimit(imageTag.getFacilityId());
					DefaultLogger.debug(this,"Got Limit"+limit);
					imageTag.setFacilityIdLabel(limit.getLimitRef()+" - "+limit.getFacilityName());
					//for Displaying the source system in label, As one facility name can exist in multiple source system
					resultMap.put("failitySourceID", limit.getSourceId());
				} catch (LimitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			//else it will be CAM  no need to prepare for label
			try {
				if(IImageTagConstants.CAM_NOTE.equals(imageTag.getDocType())){
					String st[]=imageTag.getDocDesc().split("-");
					String label1=imageTag.getDocDesc();
					label1=label1.substring(0,13);
				//	ICheckListDAO checkListDAO = (ICheckListDAO)BeanHouse.get("collateralDao");
					ILimitProfile camInfo= CheckListDAOFactory.getCheckListDAO().retriveCam(label1.trim());
					
					resultMap.put("camInfo", camInfo);
					imageTag.setDocDescLabel(st[0]);
					
				}	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
				else if(IImageTagConstants.EXCHANGE_OF_INFO.equals(imageTag.getDocType())){
					IRecurrentCheckListDAO recurrentCheckListDAO = RecurrentDAOFactory.getRecurrentCheckListDAO();
					String recurrentDocDesc = recurrentCheckListDAO.getRecurrentDocDesc(Long.parseLong(imageTag.getDocDesc()), "Annexure");
					
					imageTag.setDocDescLabel(recurrentDocDesc);
				}
				//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
				else{
					ICheckListItem checkListItem = icheckListProxyManager1.getCheckListItemById(Long.parseLong(imageTag.getDocDesc()));
//					String label=checkListItem.getItemCode()+"("+checkListItem.getCheckListItemID()+")";
					String label=checkListItem.getItemDesc();
					imageTag.setDocDescLabel(label);
						
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (CheckListException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			//Retriving Image listing
//			imageList = getImageTagProxyManager().getTagImageList(Long.toString(imageTag.getId()));
			String fromPage=itmageTagProxyManager.getFromPageByImageTagMapByTagId(Long.toString(imageTag.getId()));
			
			imageList = itmageTagProxyManager.getStagingTagImageList(Long.toString(imageTag.getId()),IImageTagConstants.STATUS_ALL);

			ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
			String customerName ="";
			List customerList = customerDAO.searchCustomerByCIFNumber(imageTag.getCustId());
			if(customerList!=null && customerList.size()>0){
				OBCMSCustomer customer=(OBCMSCustomer)customerList.get(0);
				customerName=customer.getCustomerName();
			}
			imageTag.setCustomerNameLabel(customerName);
			
			resultMap.put("fromPage", fromPage);
			resultMap.put("obImageTagAddList", imageList);
			if(imageList!=null){
			SearchResult searchResult= new SearchResult(0, imageList.size(), imageList.size(), imageList);
			resultMap.put("searchResult", searchResult);
			}
			resultMap.put("IImageTagTrxValue", trxValue);
			resultMap.put("OBImageTagDetails", imageTag);
			
			// code start:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
			List<String> imageIdList =new ArrayList<String>();
			SearchResult searchResult=(SearchResult) resultMap.get("searchResult");
			if(null!=searchResult){
				Collection<OBImageUploadAdd> resultList = searchResult.getResultList();
			
					Iterator<OBImageUploadAdd> iterator = resultList.iterator();
					while(iterator.hasNext()){
						OBImageUploadAdd oBImageUploadAdd=	 iterator.next();
						imageIdList.add(String.valueOf(oBImageUploadAdd.getImgId()));
							
						}
					}
			
			Set<String> taggedImageIdSet=new HashSet<String>();
			if(null!=fromPage && IImageTagConstants.TAG.equals(fromPage)){
			List<String> tagIdList = itmageTagProxyManager.getTagId(imageTag.getCustId());
			//Set<String> taggedImageIdSet=new HashSet<String>();
			int batchSize=200;
			if(null!=tagIdList && null!=imageIdList){
				for(int i=0; i<imageIdList.size();i+=batchSize){
					List<String> imageIdListBatch=imageIdList.subList(i, i+batchSize > imageIdList.size()? imageIdList.size() : i+batchSize );
					for(int j=0 ; j< tagIdList.size() ; j+=batchSize){
					List<String> tagIdListBatch= tagIdList.subList(j, j+batchSize > tagIdList.size()? tagIdList.size() : j+batchSize );
					taggedImageIdSet.addAll(itmageTagProxyManager.getTaggedImageId(imageIdListBatch, tagIdListBatch));
					}
				}
			}
			}
			resultMap.put("taggedImageIdSet", taggedImageIdSet);
			// code end:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
			}
			}else{
				//throw (new ImageTagException("Image Code is null "));
				DefaultLogger.debug(this, "Image Code is null.");
			}
		} catch (ImageTagException e) {

			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
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
	
	private String getTrxIdforImageTag(String trxIdOfFacReceipt) {
		String trxIdforImgTag = "";
	
		String data="";
		String data2="";
		String sql1="";
		
		String sql = "SELECT CUR_TRX_HISTORY_ID FROM TRANSACTION WHERE TRANSACTION_ID = '"+trxIdOfFacReceipt+"'";
		
		
		DBUtil dbUtil=null;
		DBUtil dbUtil1=null;
		try {
			 dbUtil=new DBUtil();
			 dbUtil1=new DBUtil();
			 ResultSet rs1=null;
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					data=rs.getString("CUR_TRX_HISTORY_ID");
					data2= String.valueOf(Long.valueOf(data.trim()) - 1);
					//sql1="SELECT distinct UNTAGGED_STATUS FROM cms_image_tag_map where image_id = '"+data2+"'";
					sql1 = "SELECT TRANSACTION_ID FROM TRANSACTION WHERE CUR_TRX_HISTORY_ID = '"+data2+"'";
					
					dbUtil1.setSQL(sql1);
					rs1 = dbUtil1.executeQuery();
					while(rs1.next()){
						trxIdforImgTag=rs1.getString("TRANSACTION_ID");
					}
				}
			}
			rs.close();
			if(rs1 != null) {
				rs1.close();
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{ 
			try {
				dbUtil.close();
				if(dbUtil1 != null) {
					dbUtil1.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return trxIdforImgTag;
	}
	
private String getTrxIdforImageTag1() {
		
		String trxIdforImgTag = "";
		
		String sql = "SELECT MAX(TRANSACTION_ID) AS TRANSACTION_ID FROM TRANSACTION WHERE STATUS = 'REJECTED' AND TRANSACTION_TYPE = 'IMAGE_TAG'";
		DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					trxIdforImgTag=rs.getString("TRANSACTION_ID");
					//sql1="SELECT distinct UNTAGGED_STATUS FROM cms_image_tag_map where image_id = '"+data2+"'";
				}
			}
			rs.close();
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{ 
			try {
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return trxIdforImgTag;
	}
}
