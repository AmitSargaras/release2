package com.integrosys.cms.ui.imageTag;

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/CreateSampleTestCommand.java,v 1.3 2004/07/08 12:32:45 jtan Exp $
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
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
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;
import com.integrosys.cms.app.contentManager.factory.ContentManagerFactory;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.OBImageTagDetails;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.SecurityTypeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

/**
 * This Command Shows result set of Image Tag
 * 
 * @author Anil Pandey
 */

public class ImageTagResultDownloadCommand extends AbstractCommand {
	//private IImageTagProxyManager imageTagProxyManager;
	//private ICheckListProxyManager checklistProxyManager;
	
	/*public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		checkListProxyManager2 = checklistProxyManager;
	}*/

	/*public IImageTagProxyManager tagProxyManager {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}*/

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "ImageTagMapObj", "com.integrosys.cms.app.imageTag.bus.IImageTagDetails", FORM_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
			
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
//				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
//				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String",GLOBAL_SCOPE },
//				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ,"com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
//				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,"com.integrosys.cms.app.customer.bus.ICMSCustomer",GLOBAL_SCOPE },
//				{"customerSearchCriteria","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",FORM_SCOPE },
//				{"customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
//				{ "ImageTagAddObj","com.integrosys.cms.app.imageTag.bus.OBImageTagAdd",FORM_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custId", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
//				{IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",GLOBAL_SCOPE },
				{ "frompage", "java.lang.String", REQUEST_SCOPE },
//				{ "from", "java.lang.String", REQUEST_SCOPE },
//				{ "indicator", "java.lang.String", REQUEST_SCOPE }, 
				});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				//Added By Anil Start
				{ "custId", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "documentItemList", "java.util.List", REQUEST_SCOPE },
				{ "facilityIdList", "java.util.List", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE },
				{ "secTypeList", "java.util.List", REQUEST_SCOPE },
				{ "secSubtypeList", "java.util.List", REQUEST_SCOPE },
				{ "securityIdList", "java.util.List", REQUEST_SCOPE },
//				{ "securityOb", "java.util.List", REQUEST_SCOPE },
//				{ "customerObList", "java.util.Collection", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				
				{ "ImageTagMapObj", "com.integrosys.cms.app.imageTag.bus.IImageTagDetails", FORM_SCOPE },
				{ "subfolderNameList", "java.util.List", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "docNameList", "java.util.List", REQUEST_SCOPE },
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
			CommandValidationException {HashMap result = new HashMap();
			HashMap returnMap = new HashMap();
			DefaultLogger.debug(this, "Enter in doExecute()");
			//String pID=(String) map.get("pID");
			//String imageName=(String) map.get("imageName");
			//String status=(String) map.get("status");
			//DefaultLogger.debug(this,"doExecute ---" + pID + "    " + imageName + "      " + status);
			String category=(String) map.get("category");
			String startIdx = (String) map.get("startIndex");
			String imageId =(String) map.get("imageId");
			String unCheckId =(String) map.get("unCheckId");
			String event = (String) map.get("event");
			String HtcOrDb2cm = PropertyManager.getValue("cms.image.htcOrDB2CM");

			String url=PropertyManager.getValue("hcp.rest.url");
			System.out.println("url............"+url);
			HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
			if(selectedArrayMap==null){ 
			selectedArrayMap = new HashMap();
			}
			result.put("startIndex", startIdx);
			String custId = (String) map.get("custId");
			IImageTagDetails details=(OBImageTagDetails) map.get("ImageTagMapObj");
			details.setCustId(custId);
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
	 
			String strLimitProfileId=(String) map.get("custLimitProfileID");
			long limitProfileID=Long.parseLong(strLimitProfileId);
			DefaultLogger.debug(this, "selectedArrayMap.size()============ImageTagResultCommand============1====================>"+selectedArrayMap.size());
			try{
			if(details!=null){
				DefaultLogger.debug(this, "details.getCategory()============================================>"+details.getCategory());
				DefaultLogger.debug(this, "details.getSelectedArray()============================================>"+details.getSelectedArray());
				category=details.getCategory();
				String selectedArrayString=details.getSelectedArray();
				if(selectedArrayString!=null && !selectedArrayString.equals("") ){
				String[] selected=selectedArrayString.split("-");
				if(selected!=null){
				for(int k=0;k<selected.length;k++){
				selectedArrayMap.put(selected[k], selected[k]);
				}
				}
				}else{
					DefaultLogger.debug(this, "ImageTagResultCommand====================193========================>"+imageId);
				if(imageId!=null && !imageId.equals("")){
					String[] selected=imageId.split("-");
					if(selected!=null){
					for(int k=0;k<selected.length;k++){
					selectedArrayMap.put(selected[k], selected[k]);
					}
					}
				}
					
				}
				
				String unCheckArrayString=details.getUnCheckArray();
				if(unCheckArrayString!=null && !unCheckArrayString.equals("")){
					String[] unchecked=unCheckArrayString.split("-");
					if(unchecked!=null){
						for(int ak=0;ak<unchecked.length;ak++){
						selectedArrayMap.remove(unchecked[ak]);
						}
						}
				}else{
					DefaultLogger.debug(this, "ImageTagResultCommand====================214========================>"+unCheckId);
					if(unCheckId!=null && !unCheckId.equals("")){
					String[] unchecked=unCheckId.split("-");
					if(unchecked!=null){
						for(int ak=0;ak<unchecked.length;ak++){
						selectedArrayMap.remove(unchecked[ak]);
						}
						}
					}
					
				}
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			DefaultLogger.debug(this, "selectedArrayMap.size()============ImageTagResultCommand=============2===================>"+selectedArrayMap.size());
			result.put("selectedArrayMap", selectedArrayMap);
			Date date= new Date();
			ZipOutputStream out =null;
			String zipFileName=date.getTime()+".zip";
			List obImageTagAddList = new ArrayList();
			//retriving image list available for tagging
			obImageTagAddList =(List) map.get("obImageTagAddList");
			try {
			if(obImageTagAddList !=null){
				 out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File(zipFileName))));
				for(int a=0;a<obImageTagAddList.size();a++){
					OBImageUploadAdd obImageUploadAdd= (OBImageUploadAdd) obImageTagAddList.get(a);
					if(selectedArrayMap.containsKey(String.valueOf(obImageUploadAdd.getImgId()))){
						
						ContentManagerFactory contentManagerFactory = (ContentManagerFactory)BeanHouse.get("contentManagerFactory");
						String imagePath="";
							try {
								Object[] params  = new Object[3];
								params[0] = obImageUploadAdd.getImageFilePath();
								params[1] = obImageUploadAdd.getImgFileName();
								params[2] = String.valueOf(obImageUploadAdd.getStatus());
								//imagePath=(String)contentManagerFactory.getContentManagerService().retrieveDocument(params);
								
								if(null == obImageUploadAdd.getHCPStatus() || obImageUploadAdd.getHCPStatus().equals("N")) {
									imagePath=(String)contentManagerFactory.getContentManagerService().retrieveDocument(params);
									}else {
										System.out.println("inSide HTC..........");
										imagePath=(String)contentManagerFactory.getContentManagerService().get(obImageUploadAdd.getHCPFileName(),String.valueOf(obImageUploadAdd.getStatus()));
										System.out.println("imagePath..........imagePath");
									
									}
								
							} catch (ContentManagerInitializationException e) {
								throw new CommandProcessingException(e.getMessage(),e);
							} catch (Exception e) {
								throw new CommandProcessingException(e.getMessage(),e);
							}		
							DefaultLogger.debug(this,"imagePath ---" + imagePath);
						//Added by Abhijit R===============Start
						ByteArrayOutputStream output = new ByteArrayOutputStream();
						byte[] fileData;
						if(imagePath!=null){
							try {
								String basePath=PropertyManager.getValue("contextPath");
								//String pdfFilePath=basePath+"/dmsImages/"+pID+".pdf";
								//String pdfFilePath="C://"+imageName;
							
								
								/*OutputStream file = new FileOutputStream(new File(pdfFilePath));
								//Document document = new Document();
								//PdfWriter.getInstance(document, file);
								//document.open();
								//document.add(new Paragraph("ImageName : "+imageName));
								//com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(basePath+imagePath);
								com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(imagePath);
								image.scalePercent(24f);
								//document.add(image);
								//document.close();
								file.write(image);
								file.close();*/
								byte[] buffer = new byte[180240];
								out.setLevel(Deflater.DEFAULT_COMPRESSION);
								
								FileInputStream in = new FileInputStream(basePath+imagePath);
								out.putNextEntry(new ZipEntry(basePath+imagePath));
								int len;
								while ((len = in.read(buffer)) > 0){
								out.write(buffer, 0, len);
								}
								out.closeEntry();
								in.close();
								/*ZipEntry entry=new ZipEntry(basePath+imagePath);
								out.putNextEntry(entry);*/
					           
								/*fileData = IOUtils.toByteArray(new FileInputStream(new File(basePath+imagePath)));
								output.write(fileData);*/
								
								DefaultLogger.debug(this,"done");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}
					
					
				}
				
					out.close();
				
				
				
			}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			result.put("output", out);
			result.put("imageName", zipFileName);
			//Added by Abhijit R ===============End
			
			//result.put("imagePath", imagePath);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			DefaultLogger.debug(this, "Going out of doExecute()");
			return returnMap;
			}
}
