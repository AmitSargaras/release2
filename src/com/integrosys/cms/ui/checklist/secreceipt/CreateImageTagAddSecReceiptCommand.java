package com.integrosys.cms.ui.checklist.secreceipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.IImageTagMap;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.app.imageTag.bus.OBImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.OBImageTagMap;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;
import com.integrosys.cms.ui.imageTag.ImageTagException;

public class CreateImageTagAddSecReceiptCommand extends AbstractCommand {
	
	private IImageTagProxyManager imageTagProxyManager;

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(
			IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	private ICheckListProxyManager checkListProxyManager;
	
	public ICheckListProxyManager getCheckListProxyManager() {
		return checkListProxyManager;
	}

	public void setCheckListProxyManager(ICheckListProxyManager checkListProxyManager) {
		this.checkListProxyManager = checkListProxyManager;
	}
	
	public String[][] getParameterDescriptor() {
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] {
				{"ImageTagMapObj","com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",FORM_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE}
		});
	}

	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] {
				{"ImageTagAddObj","com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",FORM_SCOPE },
				{ "imageTagAddForm","com.integrosys.cms.ui.imageTag.ImageTagMapForm",FORM_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", FORM_SCOPE },
				{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
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
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		String imageId = "";
		String unCheckId =null;
		ArrayList sesionObImageTagAddList = (ArrayList)map.get("obImageTagAddList");
		ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
		ICheckList checkList = (ICheckList) map.get("checkListForm");
		
		try {
		
//			ICheckListProxyManager icheckListProxyManager=	getCheckListProxyManager();
		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		// DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>Updating" +
		// checkList);
		
		
		ICheckListTrxValue checkListTrxValOld = proxy.getCheckListByTrxID(checkListTrxVal.getTransactionID());
		
		
//	if(checkList.getCheckListItemList().length!=checkListTrxValOld.getStagingCheckList().getCheckListItemList().length){
//			
//			HashMap docNos = new HashMap();
//			HashMap docStageMap = new HashMap();
//			HashMap docActualMap = new HashMap();
//			int counter=1;
//			ICheckListItem[] itemListStage = checkList.getCheckListItemList();
//			for (int count = 0; count < itemListStage.length; count++) {
//				ICheckListItem item = itemListStage[count];
//				long docNoLong = item.getCheckListItemRef();
//				String docNo = String.valueOf(docNoLong);
//				if(docStageMap.containsKey(docNo)){
//					docNoLong=docNoLong+counter;
//					counter++;
//					docNo = String.valueOf(docNoLong);
//				}
//				docStageMap.put(docNo,item); 
//			}
//			ICheckListItem[] itemListActual = checkListTrxVal.getCheckList().getCheckListItemList();
//			for (int count = 0; count < itemListActual.length; count++) {
//				ICheckListItem item = itemListActual[count];
//				long docNoLong = item.getCheckListItemRef();
//				String docNo = String.valueOf(docNoLong);
//				docActualMap.put(docNo,item); 
//			}
//			ArrayList resultListStage = new ArrayList();
//			ArrayList resultListActual = new ArrayList();
//			resultListStage.addAll(docStageMap.values());
//			resultListActual.addAll(docActualMap.values());
//			ICheckListItem[] itemList = checkListTrxValOld.getStagingCheckList().getCheckListItemList();
//			for (int count = 0; count < itemList.length; count++) {
//				ICheckListItem item = itemList[count];
//				long docNoLong = item.getCheckListItemRef();
//				String docNo = String.valueOf(docNoLong);
//				
//				if(docStageMap.containsKey(docNo)){
//					//resultListStage.add(docStageMap.get(docNo));
//				}else{
//					resultListStage.add(item);
//				}
//				
//				//docNos.put(docNo,docNo); 
//			}
//			
//			ICheckListItem[] itemListActual2 = checkListTrxValOld.getCheckList().getCheckListItemList();
//			for (int count2 = 0; count2 < itemListActual2.length; count2++) {
//				ICheckListItem item2 = itemListActual2[count2];
//				long docNoLong2 = item2.getCheckListItemRef();
//				String docNo2 = String.valueOf(docNoLong2);
//				
//				if(docActualMap.containsKey(docNo2)){
//					//resultListActual.add(docActualMap.get(docNo2));
//				}else{
//					resultListActual.add(item2);
//				}
//			}
//			
//			
//		checkListTrxVal.getStagingCheckList().setCheckListItemList((ICheckListItem[]) resultListStage.toArray(new ICheckListItem[resultListStage.size()]));
//		checkListTrxVal.getCheckList().setCheckListItemList((ICheckListItem[]) resultListActual.toArray(new ICheckListItem[resultListActual.size()]));
//			
//		//checkListTrxVal = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkListTrxVal.getStagingCheckList());
//		
//		checkList=checkListTrxVal.getStagingCheckList();
//		}
	
	ArrayList imageId1 = new ArrayList();
		String secImageTagNames = "";
		String checklistItemRefNumber = "";
		String docDesc = "";
		String facilityId = "";
		for(ICheckListItem item : checkList.getCheckListItemList()) {
//			if(item.getSecImageTagUntagImgName() != null && !"".equals(item.getSecImageTagUntagImgName())) {
//				secImageTagNames = item.getSecImageTagUntagImgName();
//				checklistItemRefNumber = String.valueOf(item.getCheckListItemRef());
//				break;
//			}
			
			if(item.getSecImageTagUntagStatus() != null && !"".equals(item.getSecImageTagUntagStatus())) {
				secImageTagNames = item.getSecImageTagUntagImgName();
				checklistItemRefNumber = String.valueOf(item.getCheckListItemRef());
				docDesc = item.getSecImageTagUntagId();
				item.setSecImageTagUntagId(null);
				item.setSecImageTagUntagStatus(null);
			}
			
		}
		
		OBCollateralCheckListOwner desc = (OBCollateralCheckListOwner) checkList.getCheckListOwner();
		facilityId = String.valueOf(desc.getCollateralID());
		ICheckListOwner limitId = checkList.getCheckListOwner();
		String limitsID = String.valueOf(limitId.getLimitProfileID());
		String custId = getCustId(limitsID);
		String checklistId = String.valueOf(checkList.getCheckListID());
		System.out.println("CreateImagetagsecReceiptCommand = checkList.getCheckListID()==>"+checklistId);
		//FACILITY_DOC
		String doctype = getDocType(facilityId);
		System.out.println("CreateImageTagAddSecReceiptCommand docType=>"+doctype+"  custId=>"+custId+" facilityId=>"+facilityId+" docdesc=>"+docDesc);
		String docDescId = getDocDesc(docDesc,checklistId);
		
		
		if(!"".equals(secImageTagNames)) {
		String[] secImageName1 = secImageTagNames.split(",");
		
		for(int i=0;i<secImageName1.length;i=i+3){
			imageId1.add(secImageName1[i+2]);
		}
		
		
		String[] imageId2=null;
		String[] imageId3=null;
		String[] imageId4=null;
		String[] unCheckIdArray=null;
		//String[] imageId2 = commaSeparatedStringToStringArray(imageId);
		boolean isFirstPage=false;
		//HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		HashMap selectedArrayMap = null;
//		ArrayList imageId1 = new ArrayList();
		if(selectedArrayMap!=null && !selectedArrayMap.isEmpty()){
			if(selectedArrayMap.size()!=0){
				isFirstPage=true;
			if(imageId1!=null){
				DefaultLogger.debug(this, "imageId1.size()============CreateImageTagAddCommand===============2=================>"+imageId1.size());
			}
			if(imageId1.get(0).equals("")){
				isFirstPage=false;
			}
			}
		}
		if(isFirstPage){
			
			DefaultLogger.debug(this, "imageId1.size()============CreateImageTagAddCommand===============3=================>"+imageId1.size());
			 imageId2 = new String[imageId1.size()] ;
			for(int y=0;y<imageId1.size();y++){
				imageId2[y]=(String) imageId1.get(y);
			}	
		}else{
			 imageId2 = new String[imageId1.size()] ;
			 for(int ab=0;ab<imageId1.size();ab++){
					String imgId= String.valueOf(imageId1.get(ab));
					imageId2[ab]=imgId;
				}
		}
		
		IImageTagDetails imageTagDetails = new OBImageTagDetails();
		
		//for fadility id use doc_desc(id) = CMS_IMAGE_TAG_DETAILS table
		
		//CMS_UPLOADED_IMAGES -> doc_item_id => cms_image_tag_details->doc_desc
		String securityImg = ICMSConstant.IMAGE_CATEGORY_SECURITY;
		List imageDetailInfo = getInfoforImageDetails(checklistItemRefNumber);
		if(!imageDetailInfo.isEmpty()) {
			imageTagDetails.setCustId(imageDetailInfo.get(0).toString());
			imageTagDetails.setSecurityId(Long.valueOf(imageDetailInfo.get(1).toString()));
			imageTagDetails.setDocDesc(imageDetailInfo.get(2).toString());
			imageTagDetails.setDocType(imageDetailInfo.get(3).toString());
			imageTagDetails.setCategory(securityImg);
		}else {
			imageTagDetails.setCustId(custId);
			imageTagDetails.setSecurityId(Long.valueOf(facilityId));
			imageTagDetails.setDocDesc(docDescId);
			if(doctype != null && !"".equals(doctype)) {
				imageTagDetails.setDocType(doctype);
			}else {
				doctype= "SECURITY_DOC";
				imageTagDetails.setDocType(doctype);
			}
//			imageTagDetails.setDocType(doctype);
			imageTagDetails.setCategory(securityImg);
		}
		IImageTagMap imageTagMapvalues = new OBImageTagMap();
		IImageTagTrxValue trxValueIn = new OBImageTagTrxValue();
		IImageTagTrxValue trxValueOut = new OBImageTagTrxValue();
		
//		try {
			
			if(imageId2!=null){
				DefaultLogger.debug(this, "imageId2.length============CreateImageTagAddCommand===============4====== final size ===========>"+imageId2.length);
			}
			ImageTagDaoImpl imgDao = new ImageTagDaoImpl();
//			ImageTagProxyManagerImpl imgProxyimpl = new ImageTagProxyManagerImpl();
			
			IImageTagProxyManager itmageTagProxyManager = (IImageTagProxyManager) BeanHouse.get("imageTagProxy");
//			IImageTagDetails existingTagDetails=getImageTagProxyManager().getExistingImageTag(imageTagDetails);
//			IImageTagDetails existingTagDetails = getExistingImageTag(imageTagDetails);
			IImageTagDetails existingTagDetails=itmageTagProxyManager.getExistingImageTag(imageTagDetails);
			
			if(existingTagDetails!=null){
//				trxValueIn=getImageTagProxyManager().getImageTagTrxByID((Long.toString(existingTagDetails.getId())));
				trxValueIn=itmageTagProxyManager.getImageTagTrxByID((Long.toString(existingTagDetails.getId())));
				
				if(trxValueIn.getLimitProfileReferenceNumber()== null){
					String customerId = Long.toString(trxValueIn.getCustomerID());
					String camId = CollateralDAOFactory.getDAO().getCamIdByCustomerID(customerId);
					trxValueIn.setLimitProfileReferenceNumber(camId);
				}	
				
				if(!(trxValueIn.getStatus().equals("ACTIVE")))
				{
					result.put("wip", "wip");
				}else{
//					trxValueOut = getImageTagProxyManager().makerUpdateImageTagDetails(ctx,trxValueIn, imageTagDetails);
					trxValueOut = itmageTagProxyManager.makerUpdateImageTagDetails(ctx,trxValueIn, imageTagDetails);
					imageTagMapvalues.setTagId(Long.parseLong(trxValueOut.getStagingReferenceID()));
					for (int i = 0; i < imageId2.length; i++) {
						if (!(imageId2[i].trim().equals(""))) {
							imageTagMapvalues.setImageId(Long.parseLong(imageId2[i].trim()));
//							getImageTagProxyManager().createImageTagMap(imageTagMapvalues);
							itmageTagProxyManager.createImageTagMap(imageTagMapvalues);
						}
					}
					result.put("request.ITrxValue", trxValueOut);
				}

			}else{
//				trxValueOut = getImageTagProxyManager().makerCreateImageTagDetails(ctx, imageTagDetails);
				trxValueOut = itmageTagProxyManager.makerCreateImageTagDetails(ctx, imageTagDetails);
				
				imageTagMapvalues.setTagId(Long.parseLong(trxValueOut.getStagingReferenceID()));
				for (int i = 0; i < imageId2.length; i++) {
					if (!(imageId2[i].trim().equals(""))) {
						imageTagMapvalues.setImageId(Long.parseLong(imageId2[i].trim()));
//						getImageTagProxyManager().createImageTagMap(imageTagMapvalues);
						itmageTagProxyManager.createImageTagMap(imageTagMapvalues);
					}
				}
				result.put("request.ITrxValue", trxValueOut);
			}
			
		}
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return returnMap;
	}

	private String[] commaSeparatedStringToStringArray(String aString) {
		String[] splittArray = null;
		if (aString != null && !aString.equalsIgnoreCase("")) {
			splittArray = aString.split("-");
		}
		return splittArray;
	}
	
	
	
	/*private IImageTagDetails getExistingImageTag(IImageTagDetails imageTagDetails)
			throws ImageTagException {
		String docType = imageTagDetails.getDocType();
		String sql="";
		String data="";
		OBImageTagDetails ob = null;
		sql = "SELECT ID,VERSION_TIME,CUST_ID,SECURITY_ID,FACILITY_ID,DOC_TYPE,DOC_DESC,BANK_NAME FROM CMS_IMAGE_TAG_DETAILS  WHERE ";
		if(docType!=null)
			sql = sql +(" DOC_DESC ='"+imageTagDetails.getDocDesc()+"'");
		
		if(IImageTagConstants.SECURITY_DOC.equals(docType)){
			sql = sql + ("AND SECURITY_ID = "+imageTagDetails.getSecurityId());
		}else if(IImageTagConstants.FACILITY_DOC.equals(docType)){
			sql = sql + ("AND FACILITY_ID = "+imageTagDetails.getFacilityId());
		}
		
		// Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II 
		else if(IImageTagConstants.EXCHANGE_OF_INFO.equals(docType)){
			sql = sql + ("AND BANK_NAME = '"+imageTagDetails.getBank()+"'");
		} // Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II 
		try{
			
			DBUtil dbUtil=null;
				 dbUtil=new DBUtil();
				dbUtil.setSQL(sql);
				ResultSet rs = dbUtil.executeQuery();
				if(null!=rs){
					while(rs.next()){
						ob = new OBImageTagDetails();
					
						data=rs.getString("ID");
						ob.setId(Long.valueOf(data.trim()));
						data = rs.getString("VERSION_TIME");
						ob.setVersionTime(Long.valueOf(data.trim()));
						data = rs.getString("CUST_ID");
						ob.setCustId(data);
						data = rs.getString("SECURITY_ID");
						ob.setSecurityId(Long.valueOf(data.trim()));
						data = rs.getString("FACILITY_ID");
						ob.setFacilityId(Long.valueOf(data.trim()));
						data = rs.getString("DOC_TYPE");
						ob.setDocType(data);
						data = rs.getString("DOC_DESC");
						ob.setDocDesc(data);
						data = rs.getString("BANK_NAME");
						ob.setBank(data);
						}
					rs.close();
					dbUtil.close();
					return ob;
				}else{
					if(dbUtil != null) {
						dbUtil.close();
					}
					return null;
				}
		}catch (Exception e) {
			e.printStackTrace();
			throw new ImageTagException("Error reterving existing image tag details", e);
		}
	}*/
	
	
	private List getInfoforImageDetails(String docRefId) {
		List imageDetailsInfoList = new ArrayList();
		String data="";
		String data1="";
		String data2="";
		String data3="";
		//TreeMap map= new TreeMap();
		//List imageDataList = new ArrayList();
		String sql="SELECT * " + 
				"    FROM cms_image_tag_details " + 
				"    WHERE DOC_DESC = " + 
				"      (SELECT TO_CHAR(doc_item_id) " + 
				"      FROM cms_checklist_item " + 
				"        WHERE DOC_ITEM_REF = '"+docRefId+"' " + 
				"      )";
		System.out.println("getInfoforImageDetails method sql=>"+sql);
		DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					data=rs.getString("CUST_ID");
					imageDetailsInfoList.add(data);
					data1 = rs.getString("SECURITY_ID");
					imageDetailsInfoList.add(data1);
					data2 = rs.getString("DOC_DESC");
					imageDetailsInfoList.add(data2);
					data3 = rs.getString("DOC_TYPE");
					if((data == null || "".equals(data)) && (data1 == null || "".equals(data1)) && (data2 == null || "".equals(data2)) && (data3 == null || "".equals(data3))) {
//						imageDetailsInfoList.add("SECURITY_DOC");
					}else if(data != null && !"".equals(data) && data1 != null && !"".equals(data1) && data2 != null && !"".equals(data2) && (data3 == null || "".equals(data3))){
//					imageDetailsInfoList.add(data);
					imageDetailsInfoList.add("SECURITY_DOC");
					}else {
						imageDetailsInfoList.add(data3);
					}
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
		return imageDetailsInfoList;
	}
	
	
	private String getCustId(String limitId) {
		String custId = "";
		String data="";
		//TreeMap map= new TreeMap();
		//List imageDataList = new ArrayList();
		String sql="select LLP_LE_ID from SCI_LSP_LMT_PROFILE where CMS_LSP_LMT_PROFILE_ID = '"+limitId+"' " ;
		System.out.println("getCustId() custid query=>"+sql);
		DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					custId=rs.getString("LLP_LE_ID");
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
		return custId;
	}
	
	private String getDocDesc(String docDesc,String checklistId) {
		String docdesc = "";
		String data="";
		//TreeMap map= new TreeMap();
		//List imageDataList = new ArrayList();
		String sql="SELECT distinct DOC_ITEM_ID FROM cms_checklist_item where DOC_DESCRIPTION = '"+docDesc+"' AND CHECKLIST_ID = '"+checklistId+"' ";
		System.out.println("getDocDesc(String docDesc,String checklistId) query =>"+sql);
				
		DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					docdesc=rs.getString("DOC_ITEM_ID");
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
		return docdesc;
	}
	
	private String getDocType(String limitId) {
		String doctype = "";
		String data="";
		//TreeMap map= new TreeMap();
		//List imageDataList = new ArrayList();
		String sql="SELECT DISTINCT DOC_TYPE FROM cms_image_tag_details where SECURITY_ID ='"+limitId+"' " ; 
				System.out.println("getDocType() doctype query=>"+sql);
		DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					doctype=rs.getString("DOC_TYPE");
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
		return doctype;
	}



}
