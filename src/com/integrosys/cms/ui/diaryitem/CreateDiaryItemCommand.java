/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/CreateDiaryItemCommand.java,v 1.3 2004/07/08 12:32:45 jtan Exp $
 */
package com.integrosys.cms.ui.diaryitem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.IDiaryItemJdbc;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.ftp.FTPSClient;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.batch.common.filereader.PoiExcel;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This command creates a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/08 12:32:45 $ Tag: $Name: $
 */

public class CreateDiaryItemCommand extends DiaryItemsCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "diaryItemObj", "com.integrosys.cms.app.diary.bus.OBDiaryItem", FORM_SCOPE },
		{IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
		{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },});
		
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		CMSFtpClient ftpClient; 
		final String FTP_DIARY_UPLOAD = "ftp.diary.upload.remote.dir";
		IDiaryItemProxyManager proxyMgr = getDiaryItemProxyManager();
		IDiaryItemJdbc diaryItemJdbc=(IDiaryItemJdbc)BeanHouse.get("diaryItemJdbc");
		IDiaryItem item = (IDiaryItem) map.get("diaryItemObj");
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		item.setMakerId(user.getLoginID());
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		Date dateApplication=new Date();
		long ladGenIndicator=0l;
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				dateApplication=new Date(generalParamEntries[i].getParamValue());
			}
		}
		//item.setMakerDateTime(DateUtil.getDate());
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		String diarySequence = diaryItemJdbc.getDiarySequence();
		item.setDiaryNumber(Long.parseLong(dateFormat.format(dateApplication).concat("000"+diarySequence)));
		item.setIsDelete("N");
		if("N".equals(item.getDropLineOD())) {
			item.setUploadFileError("N");
		}
		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		
		long teamID = team.getTeamID();
		
		if(item.getOdScheduleUploadFile() != null) {
		ODScheduleUploadExcelToDB od = new ODScheduleUploadExcelToDB();
		try { 
			if(item.getOdScheduleUploadFile().getFileName().endsWith(".xlsx") || item.getOdScheduleUploadFile().getFileName().endsWith(".XLSX")){
				item.setUploadFileError(od.readExcelData(item.getOdScheduleUploadFile(),item.getDiaryNumber(),teamID,item.getCustomerSegment(),item));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		IDiaryItem newItem = proxyMgr.createDiaryItem(item);
		
		if(item.getOdScheduleUploadFile() != null) {
			ODScheduleUploadExcelToDB od = new ODScheduleUploadExcelToDB();
			try { 
				if(item.getOdScheduleUploadFile().getFileName().endsWith(".xlsx") || item.getOdScheduleUploadFile().getFileName().endsWith(".XLSX")){
					Long itemid = diaryItemJdbc.getItemid(item.getDiaryNumber());
					diaryItemJdbc.updateItemId(item.getDiaryNumber(),itemid);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
