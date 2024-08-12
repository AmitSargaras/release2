package com.integrosys.cms.ui.checklist.facilityreceipt;

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
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.IImageTagMap;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.app.imageTag.bus.OBImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.OBImageTagMap;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This command creates a Image Tag
 * 
 * @author abhijit.rudrakshawar
 * 
 * 
 */

public class EditImageTagFacReceiptCommand extends AbstractCommand {
	
	private IImageTagProxyManager imageTagProxyManager;

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(
			IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}


	public String[][] getParameterDescriptor() {
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] {
				{"ImageTagMapObj","com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",FORM_SCOPE },
				{"theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{"IImageTagTrxValue","com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue",SERVICE_SCOPE },						
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "checkedImagesMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE}
		});
	}

	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] { 
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
				   });
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
		IImageTagProxyManager itmageTagProxyManager = (IImageTagProxyManager) BeanHouse.get("imageTagProxy");
		DefaultLogger.debug(this, "Enter in doExecute()");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		String[] imageId3=null;
		
//		String imageId = map.get("imageId").toString();
		//String[] imageId2 = commaSeparatedStringToStringArray(imageId);
//		
//		
//		HashMap checkedImagesMap = (HashMap) map.get("checkedImagesMap");
//		ArrayList imageId1 = new ArrayList();
		
//		String[] unCheckIdArray=null;
//		if(checkedImagesMap!=null){
//		imageId1.addAll(checkedImagesMap.keySet());
//		imageId3 = commaSeparatedStringToStringArray(imageId);
//		
//		if(imageId3!=null){
//		for(int ab=0;ab<imageId3.length;ab++){
//			if(!imageId1.contains(imageId3[ab])){
//				imageId1.add(imageId3[ab]);
//			}
//		}
//		}
		
		
		
		String imageId = "";
		String unCheckId =null;
		ArrayList sesionObImageTagAddList = (ArrayList)map.get("obImageTagAddList");
		ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
		ICheckList checkList = (ICheckList) map.get("checkListForm");
		String statusOfSubmit = (String)checkListTrxVal.getStatus();
		String statusOfSubmitTostate = (String)checkListTrxVal.getToState();
		String statusOfSubmitFromState = (String)checkListTrxVal.getFromState();
		System.out.println("In EditImageTagFacReceiptCommand = statusOfSubmit=>"+statusOfSubmit+" statusOfSubmitTostate=>"+statusOfSubmitTostate+" statusOfSubmitFromState=>"+statusOfSubmitFromState);
		try {
		
		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		
		ICheckListTrxValue checkListTrxValOld = proxy.getCheckListByTrxID(checkListTrxVal.getTransactionID());
		
	ArrayList imageId1 = new ArrayList();
		String facImageTagNames = "";
		String checklistItemRefNumber = "";
		String docDesc = "";
		String facilityId = "";
		boolean flag = false;
		boolean flag1 = false;
		for(ICheckListItem item : checkList.getCheckListItemList()) {
//			if(item.getFacImageTagUntagImgName() != null && !"".equals(item.getFacImageTagUntagImgName())) {
//				facImageTagNames = item.getFacImageTagUntagImgName();
//				checklistItemRefNumber = String.valueOf(item.getCheckListItemRef());
//				break;
//			}
		
//		if(item.getFacImageTagUntagStatus() != null && !"".equals(item.getFacImageTagUntagStatus())) {
		if(item.getItemStatus() != null && !"".equals(item.getItemStatus()) && ("UPDATE_RECEIVED".equals(item.getItemStatus()) || "PENDING_RECEIVED".equals(item.getItemStatus())) && 
				item.getFacImageTagUntagImgName() != null && !"".equals(item.getFacImageTagUntagImgName())) {
			facImageTagNames = item.getFacImageTagUntagImgName();
			checklistItemRefNumber = String.valueOf(item.getCheckListItemRef());
//			docDesc = item.getFacImageTagUntagId();
			docDesc = (String)item.getItem().getItemDesc();
			System.out.println("EditImageTagFacreceiptCommand => item.getItem().getItemDesc() =>docDesc=>"+docDesc);
			if(item.getFacImageTagUntagStatus() != null && !"".equals(item.getFacImageTagUntagStatus())) {
				flag1 = true;
			}
			item.setFacImageTagUntagId(null);
			item.setFacImageTagUntagStatus(null);
			flag= true;
		}
		}
		
		
		
		Long trxId = Long.valueOf(checkListTrxVal.getCurrentTrxHistoryID());
		trxId = trxId - 1;
		System.out.println("trxId=>"+trxId);
		String trxds1 = String.valueOf(trxId);
		System.out.println("trxds1=>"+trxds1);
		
		IImageTagTrxValue trxValueIn = (OBImageTagTrxValue) map.get("IImageTagTrxValue");
		boolean flagForImageTag = false;
		if(trxValueIn != null) {
		String statusOfTrxImage = trxValueIn.getStatus();
		String instance = trxValueIn.getInstanceName();
		String trxhistId = trxValueIn.getCurrentTrxHistoryID();
		
		if(trxds1.equals(trxhistId)) {
			flagForImageTag = true;
			System.out.println("Trx id is similar.");
			System.out.println("Editimagetagfacreceiptcmd = statusOfTrxImage=>"+statusOfTrxImage+" instance=>"+instance+" trxIdofimagetag=>"+trxhistId);
		}else {
			System.out.println("Trx id is not similar.");
			System.out.println("Editimagetagfacreceiptcmd = statusOfTrxImage=>"+statusOfTrxImage+" instance=>"+instance+" trxIdofimagetag=>"+trxhistId);
		}
		
		}
		
		
		
		OBCollateralCheckListOwner desc = (OBCollateralCheckListOwner) checkList.getCheckListOwner();
		facilityId = String.valueOf(desc.getCollateralID());
		ICheckListOwner limitId = checkList.getCheckListOwner();
		String limitsID = String.valueOf(limitId.getLimitProfileID());
		String custId = getCustId(limitsID);
		String checklistId = String.valueOf(checkList.getCheckListID());
		
		if(!"".equals(checklistItemRefNumber)) {
		String checklist_id = getCheckListIds(docDesc,checklistItemRefNumber);
		System.out.println("checkList.getCheckListID()=>"+checklistId+"   getCheckListIds(docDesc,checklistItemRefNumber)=>"+checklist_id);
		if(!checklistId.equals(checklist_id)) {
			if(checklist_id != null && !"".equals(checklist_id)) {
			checklistId = checklist_id;
//			checkList.setCheckListID(Long.valueOf(checklist_id));
			}
		}
		}
		//FACILITY_DOC
		String doctype = getDocType(facilityId);
		System.out.println("CreateImageTagAddFacReceiptCommand docType=>"+doctype+"  custId=>"+custId+" facilityId=>"+facilityId+" docdesc=>"+docDesc);
		String docDescId = getDocDesc(docDesc,checklistId);
		
		if(!"".equals(facImageTagNames)) {
		
		String[] facImageName1 = facImageTagNames.split(",");
		
		for(int i=0;i<facImageName1.length;i=i+3){
			imageId1.add(facImageName1[i+2]);
		}
		
		String[] imageId2 = new String[imageId1.size()] ;
		for(int y=0;y<imageId1.size();y++){
			imageId2[y]=(String) imageId1.get(y);
		}
//		IImageTagDetails imageTagDetails = (IImageTagDetails) map
//				.get("ImageTagMapObj");
		IImageTagDetails imageTagDetails = new OBImageTagDetails();
		
		//for fadility id use doc_desc(id) = CMS_IMAGE_TAG_DETAILS table
		
		//CMS_UPLOADED_IMAGES -> doc_item_id => cms_image_tag_details->doc_desc
		List imageDetailInfo = getInfoforImageDetails(checklistItemRefNumber);
		if(!imageDetailInfo.isEmpty()) {
			imageTagDetails.setCustId(imageDetailInfo.get(0).toString());
			imageTagDetails.setFacilityId(Long.valueOf(imageDetailInfo.get(1).toString()));
			imageTagDetails.setDocDesc(imageDetailInfo.get(2).toString());
			imageTagDetails.setDocType(imageDetailInfo.get(3).toString());
			imageTagDetails.setCategory("IMG_CATEGORY_FACILITY");
		}else {
			imageTagDetails.setCustId(custId);
			imageTagDetails.setFacilityId(Long.valueOf(facilityId));
			imageTagDetails.setDocDesc(docDescId);
			if(doctype != null && !"".equals(doctype)){
				imageTagDetails.setDocType(doctype);
			}else {
				doctype = "FACILITY_DOC";
				imageTagDetails.setDocType(doctype);
			}
//			imageTagDetails.setDocType(doctype);
			imageTagDetails.setCategory("IMG_CATEGORY_FACILITY");
		}
		
		IImageTagMap imageTagMapvalues = new OBImageTagMap();
		IImageTagTrxValue trxValueOut = new OBImageTagTrxValue();
		
		
		if(flag == true && facImageTagNames != null && !"".equals(facImageTagNames)) {
		if("DRAFT".equals(statusOfSubmit)) {
			
			
			
			ImageTagDaoImpl imgDao = new ImageTagDaoImpl();
//			ImageTagProxyManagerImpl imgProxyimpl = new ImageTagProxyManagerImpl();
			
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
		else {
			
			if(flagForImageTag == true) {
		
			
			if(trxValueIn.getLimitProfileReferenceNumber()== null){
				String customerId = Long.toString(trxValueIn.getCustomerID());
				String camId = CollateralDAOFactory.getDAO().getCamIdByCustomerID(customerId);
				trxValueIn.setLimitProfileReferenceNumber(camId);
			}
			
			trxValueOut = itmageTagProxyManager.makerUpdateRejectedImageTagDetails(ctx,trxValueIn, imageTagDetails);

			imageTagMapvalues.setTagId(Long.parseLong(trxValueOut.getStagingReferenceID()));
			for (int i = 0; i < imageId2.length; i++) {
				if (!(imageId2[i].trim().equals(""))) {
					imageTagMapvalues.setImageId(Long.parseLong(imageId2[i].trim()));
					itmageTagProxyManager.createImageTagMap(imageTagMapvalues);
				}
			}
			
			
			
			result.put("request.ITrxValue", trxValueOut);
			
			}else if(flag1 == true){
				

				ImageTagDaoImpl imgDao = new ImageTagDaoImpl();
//				ImageTagProxyManagerImpl imgProxyimpl = new ImageTagProxyManagerImpl();
				
//				IImageTagDetails existingTagDetails=getImageTagProxyManager().getExistingImageTag(imageTagDetails);
//				IImageTagDetails existingTagDetails = getExistingImageTag(imageTagDetails);
				IImageTagDetails existingTagDetails=itmageTagProxyManager.getExistingImageTag(imageTagDetails);
				
				if(existingTagDetails!=null){
//					trxValueIn=getImageTagProxyManager().getImageTagTrxByID((Long.toString(existingTagDetails.getId())));
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
//						trxValueOut = getImageTagProxyManager().makerUpdateImageTagDetails(ctx,trxValueIn, imageTagDetails);
						trxValueOut = itmageTagProxyManager.makerUpdateImageTagDetails(ctx,trxValueIn, imageTagDetails);
						imageTagMapvalues.setTagId(Long.parseLong(trxValueOut.getStagingReferenceID()));
						for (int i = 0; i < imageId2.length; i++) {
							if (!(imageId2[i].trim().equals(""))) {
								imageTagMapvalues.setImageId(Long.parseLong(imageId2[i].trim()));
//								getImageTagProxyManager().createImageTagMap(imageTagMapvalues);
								itmageTagProxyManager.createImageTagMap(imageTagMapvalues);
							}
						}
						result.put("request.ITrxValue", trxValueOut);
					}

				}else{
//					trxValueOut = getImageTagProxyManager().makerCreateImageTagDetails(ctx, imageTagDetails);
					trxValueOut = itmageTagProxyManager.makerCreateImageTagDetails(ctx, imageTagDetails);
					
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
				
			}
			}
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
					data1 = rs.getString("FACILITY_ID");
					imageDetailsInfoList.add(data1);
					data2 = rs.getString("DOC_DESC");
					imageDetailsInfoList.add(data2);
					data3 = rs.getString("DOC_TYPE");
					if((data == null || "".equals(data)) && (data1 == null || "".equals(data1)) && (data2 == null || "".equals(data2)) && (data3 == null || "".equals(data3))) {
//						imageDetailsInfoList.add("SECURITY_DOC");
					}else if(data != null && !"".equals(data) && data1 != null && !"".equals(data1) && data2 != null && !"".equals(data2) && (data3 == null || "".equals(data3))){
//					imageDetailsInfoList.add(data);
					imageDetailsInfoList.add("FACILITY_DOC");
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
		String sql="SELECT DISTINCT DOC_TYPE FROM cms_image_tag_details where FACILITY_ID ='"+limitId+"' " ; 
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
	
	private String getCheckListIds(String docDesc,String checklistIdRefNumber) {
		String docdesc = "";
		String data="";
		//TreeMap map= new TreeMap();
		//List imageDataList = new ArrayList();
		String sql="SELECT distinct CHECKLIST_ID FROM cms_checklist_item where DOC_DESCRIPTION = '"+docDesc+"' AND DOC_ITEM_REF = '"+checklistIdRefNumber+"' ";
		System.out.println("getCheckListIds(String docDesc,String checklistIdRefNumber) query =>"+sql);
				
		DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					docdesc=rs.getString("CHECKLIST_ID");
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
	
	
	
}
