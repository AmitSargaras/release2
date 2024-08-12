/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
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
				{ "checkListTrxVal", ICheckListTrxValue.class.getName() , SERVICE_SCOPE },
				{ "docReceipt", "java.lang.String", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, ILimitProfile.class.getName(), GLOBAL_SCOPE }
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
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", SERVICE_SCOPE },
				{ TAGGED_FILE_NAMES, String.class.getName(), REQUEST_SCOPE},
				{ UN_TAGGED_FILE_NAMES, String.class.getName(), REQUEST_SCOPE},
				{ TAG_UNTAG_IMAGE_DTL_LIST, List.class.getName(), SERVICE_SCOPE },
				{ IS_IMAGE_TAG_PENDING, Boolean.class.getName(), SERVICE_SCOPE },
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
		ICheckListItem actual = null;
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ICheckList checkList = (ICheckList) map.get("checkList");
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			IImageTagProxyManager imageTagProxy = (IImageTagProxyManager) BeanHouse.get("imageTagProxy");
			ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long index2 = Long.parseLong((String) map.get("index"));
			int index=0;
			ICheckListItem temp[] = checkList.getCheckListItemList();
			for(int i=0;i<temp.length;i++){
				if(temp[i].getCheckListItemID()==index2){
					index=i;
				}
			}
			String status = temp[index].getItemStatus();
			
			if (checkListTrxVal.getCheckList() != null) {
				actual = CheckListHelper.getItem(checkListTrxVal.getCheckList().getCheckListItemList(), temp[index].getCheckListItemRef());
			}
//			String skipImgTag = actual.getSkipImgTag();
			String docReceipt = (String)map.get("docReceipt");
			String document_code=temp[index].getItemCode();
			String isSkipImgTag="";
//			if(null!=docReceipt && !"".equals(docReceipt))
				isSkipImgTag=isSkipImgTag(document_code);
			
			
			
//			String monitorType = temp[index].getItem().getMonitorType();
//			if ((monitorType != null)
//					&& (monitorType.equals(ICMSConstant.INSURANCE_POLICY) || monitorType
//							.equals(ICMSConstant.PREMIUM_RECEIPT))) {
//				resultMap.put("monitorType", monitorType);
//			}

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
			
            resultMap.put("status", status);
			resultMap.put("checkListItem", CommonUtil.deepClone(temp[index]));
			resultMap.put(TAGGED_FILE_NAMES, taggedFileNames);
			resultMap.put(UN_TAGGED_FILE_NAMES, unTaggedFileNames);
			resultMap.put(IS_IMAGE_TAG_PENDING, isImageTagPending);
			resultMap.put(TAG_UNTAG_IMAGE_DTL_LIST, null);
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
	
	private String isSkipImgTag(String document_code) {
		String sql="select SKIP_IMG_TAG from CMS_DOCUMENT_GLOBALLIST where DOCUMENT_CODE = ?";
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
	
}
