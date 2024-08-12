/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.otherreceipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * @author $Author: jitendra $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/05/11 08:03:54 $ Tag: $Name: $
 */
public class ReadCheckListItemCommand extends AbstractCommand implements ICommonEventConstant {
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
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "monitorType", "java.lang.String", REQUEST_SCOPE },
				{ "actualOb", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE },
				{ "stageOb", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", SERVICE_SCOPE }, 
				{"isSkipImgTag", "java.lang.String", SERVICE_SCOPE},
				
		}
		);
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
			ICheckList checkList = (ICheckList) map.get("checkList");
			int index = Integer.parseInt((String) map.get("index"));
			ICheckListItem temp[] = checkList.getCheckListItemList();
			String status = temp[index].getItemStatus();
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
			
			
			String docReceipt = (String)map.get("docReceipt");
			String document_code=temp[index].getItemCode();
			String isSkipImgTag="";
//			if(null!=docReceipt && !"".equals(docReceipt))
				isSkipImgTag=isSkipImgTag(document_code);
			
			
			resultMap.put("stageOb", stageOb);
			resultMap.put("actualOb", actual);
			resultMap.put("isSkipImgTag", isSkipImgTag);
			resultMap.put("index", String.valueOf(index));
            resultMap.put("status", status);
			resultMap.put("checkListItem", CommonUtil.deepClone(temp[index]));
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
