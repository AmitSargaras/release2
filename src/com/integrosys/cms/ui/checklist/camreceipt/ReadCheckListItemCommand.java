/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.camreceipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.checklist.ITagUntagImageConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jitendra $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/05/11 08:03:54 $ Tag: $Name: $
 */
public class ReadCheckListItemCommand extends AbstractCommand implements ICommonEventConstant, ITagUntagImageConstant {
	/**
	 * Default Constructor
	 */
	public ReadCheckListItemCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "index", "java.lang.String", REQUEST_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "docReceipt", "java.lang.String", SERVICE_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, ILimitProfile.class.getName(), GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, ICMSCustomer.class.getName() , GLOBAL_SCOPE }
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
		return (new String[][] { { "status", "java.lang.String", REQUEST_SCOPE },
				{ "monitorType", "java.lang.String", REQUEST_SCOPE },
				 { "index", "java.lang.String", REQUEST_SCOPE },
				 { "actualOb", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE },
				 { "stageOb", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", SERVICE_SCOPE },
				{ "isImageTagged", "java.lang.String", REQUEST_SCOPE },
				{ TAGGED_FILE_NAMES, String.class.getName(), REQUEST_SCOPE},
				{ UN_TAGGED_FILE_NAMES, String.class.getName(), REQUEST_SCOPE},
				{ TAG_UNTAG_IMAGE_DTL_LIST, List.class.getName(), SERVICE_SCOPE },
				{ IS_IMAGE_TAG_PENDING, Boolean.class.getName(), SERVICE_SCOPE },
				{ "isCustomerCorporate", Boolean.class.getName(), SERVICE_SCOPE },
				{"isSkipImgTag", "java.lang.String", SERVICE_SCOPE},
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			ICheckListItem actual = null;
			ICheckListItem stageOb = null;
			IImageTagProxyManager imageTagProxy = (IImageTagProxyManager) BeanHouse.get("imageTagProxy");
			ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			
			String event = (String) map.get("event");
			ICheckList checkList = (ICheckList) map.get("checkList");
			int index = Integer.parseInt((String) map.get("index"));
			ICheckListItem temp[] = checkList.getCheckListItemList();
			String status = "";
			if(event.equals("NOT_APPLICABLE")){
			temp[index].setItemStatus("NOT_APPLICABLE");
			status = "NOT_APPLICABLE";
			}else {
				 status = temp[index].getItemStatus();
			}
//			String monitorType = temp[index].getItem().getMonitorType();
//			if ((monitorType != null)
//					&& (monitorType.equals(ICMSConstant.INSURANCE_POLICY) || monitorType
//							.equals(ICMSConstant.PREMIUM_RECEIPT))) {
//				resultMap.put("monitorType", monitorType);
//			}
			if (checkListTrxVal.getCheckList() != null) {
				actual = getItem(checkListTrxVal.getCheckList().getCheckListItemList(), temp[index].getCheckListItemRef());
			}
			
			if (checkListTrxVal.getStagingCheckList() != null) {
				stageOb = getItem(checkListTrxVal.getStagingCheckList().getCheckListItemList(), temp[index].getCheckListItemRef());
			}
//			String skipImgTag = actual.getSkipImgTag();
			
			//long checklistId=checkList.getCheckListID();
			long checklistId=(checkListTrxVal.getCheckList()!=null)?checkListTrxVal.getCheckList().getCheckListID():0;
			long docCode= temp[index].getCheckListItemRef();
			String docReceipt = (String)map.get("docReceipt");
			String isImageTagged=null;
			if(null!=docReceipt && !"".equals(docReceipt))
				isImageTagged=isImageTagged(checklistId,docCode);

			String document_code=temp[index].getItemCode();
			String isSkipImgTag="";
			//if(null!=docReceipt && !"".equals(docReceipt))
				isSkipImgTag=isSkipImgTag(document_code);
			
			Map<Long, String> imageIdUnTaggedStatusMap = imageTagProxy.getImageIdTaggedStatusMap(String.valueOf(actual!= null ?actual.getCheckListItemID():""));
			
			IImageTagDetails tagDetails = CheckListHelper.populateImageTagDetails(checkList.getCheckListType(), limitProfile.getLEReference());
			List imageListFromTagDetails = imageTagProxy.getCustImageListByCriteria(tagDetails);
			
			String taggedFileNames = CheckListHelper.getTagUntaggedFileNameList(imageIdUnTaggedStatusMap, temp[index].getCheckListItemImageDetail(), true);
			String unTaggedFileNames = CheckListHelper.getTagUntaggedFileNameList(imageIdUnTaggedStatusMap, temp[index].getCheckListItemImageDetail(), false);

			boolean isActualCheckListItemTagged = (!imageIdUnTaggedStatusMap.isEmpty() && imageIdUnTaggedStatusMap.containsValue(TAGGED));
			
			boolean isImageTagPending = (isActualCheckListItemTagged || (temp[index].getCheckListItemImageDetail() == null && imageIdUnTaggedStatusMap.isEmpty() &&
											imageListFromTagDetails.isEmpty())) ? Boolean.FALSE : StringUtils.isBlank(taggedFileNames);
			if(imageListFromTagDetails.isEmpty()) {
				isImageTagPending = true;
			}

			DefaultLogger.info(this, "For checklist : "+temp[index].getItem().getItemCode() + " | " +temp[index].getCheckListItemID()+ 
					" | Actual CheckListItemID: "+ (actual != null ? actual.getCheckListItemID() : null) +" | isImageTagPending: "+ isImageTagPending+ 
					" | imageIdUnTaggedStatusMap : " + imageIdUnTaggedStatusMap + " | imageListFromTagDetails size: "+ imageListFromTagDetails.size() +
					" | isActualCheckListItemTagged "+ isActualCheckListItemTagged + " | taggedFileNames: "+taggedFileNames);
			
			boolean isCustomerCorporate = CheckListHelper.getAllowedCorporateEntities().contains(customer.getEntity());
			System.out.println("/camreceipt/ReadChecklistItemCommand.java=>isCustomerCorporate=>"+isCustomerCorporate);
			System.out.println("/camreceipt/ReadChecklistItemCommand.java=>customer.getEntity()=>"+customer.getEntity());
			resultMap.put("stageOb", stageOb);
			resultMap.put("actualOb", actual);
			resultMap.put("index", String.valueOf(index));
            resultMap.put("status", status);
			resultMap.put("checkListItem", CommonUtil.deepClone(temp[index]));
			resultMap.put("isImageTagged", isImageTagged);
			resultMap.put(TAGGED_FILE_NAMES, taggedFileNames);
			resultMap.put(UN_TAGGED_FILE_NAMES, unTaggedFileNames);
			resultMap.put(IS_IMAGE_TAG_PENDING,isImageTagPending);
			resultMap.put(TAG_UNTAG_IMAGE_DTL_LIST, null);
			resultMap.put("isCustomerCorporate", isCustomerCorporate);
			resultMap.put("isSkipImgTag", isSkipImgTag);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	private ICheckListItem getItem(ICheckListItem temp[], long itemRef) {
		ICheckListItem item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getCheckListItemRef() == itemRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}
	
/*	public String isSkipImgTag(String document_code) {
		String sql="select SKIP_IMG_TAG from CMS_DOCUMENT_GLOBALLIST where DOCUMENT_CODE = ?";
		return (String) getJdbcTemplate().queryForObject(sql, new Object[] { document_code }, String.class);
	}*/
	
	private String isSkipImgTag(String document_code) {
		String sql="select SKIP_IMG_TAG from CMS_DOCUMENT_GLOBALLIST where DOCUMENT_CODE = ?";
		DefaultLogger.debug(this, "isSkipImgTag :" + sql);
		DBUtil dbUtil=null;
		String skip="";
		ResultSet rs = null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, document_code);
			rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					skip= rs.getString("SKIP_IMG_TAG");
				}
			}
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
		return skip;
		
		
	}
	private String isImageTagged(long checklistId, long docCode) {
		
		String sql="select image_id from cms_image_tag_map where tag_id in " + 
				" (select id from cms_image_tag_details where " + 
				" DOC_DESC = (select TO_CHAR(doc_item_id) from cms_checklist_item where DOC_ITEM_REF='"+docCode+"' " + 
				" and checklist_id ='"+checklistId+"')) and untagged_status='N'";
		DefaultLogger.debug(this, "isImageTagged :" + sql);
		DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					return "YES";
				}
			}
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
		return "NO";
	}
}
