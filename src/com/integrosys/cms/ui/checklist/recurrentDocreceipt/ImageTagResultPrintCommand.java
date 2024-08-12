package com.integrosys.cms.ui.checklist.recurrentDocreceipt;
 
/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/CreateSampleTestCommand.java,v 1.3 2004/07/08 12:32:45 jtan Exp $
 */

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;
import com.integrosys.cms.app.contentManager.factory.ContentManagerFactory;
import com.integrosys.cms.app.contentManager.ibmDb2Cm.connectionPool.DB2ConnectionPool;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.ibm.mm.sdk.server.DKDatastoreICM;
/**
 * This Command Shows result set of Image Tag
 * 
 * @author Anil Pandey
 */

public class ImageTagResultPrintCommand extends AbstractCommand {
	private IImageTagProxyManager imageTagProxyManager;
	private ICheckListProxyManager checklistProxyManager;
	
	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "ImageTagMapObj", "com.integrosys.cms.app.imageTag.bus.IImageTagDetails", FORM_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "tagDetailList", "java.util.ArrayList", SERVICE_SCOPE },
			
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
//				{ "securityOb", "java.util.List", REQUEST_SCOPE },
//				{ "customerObList", "java.util.Collection", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
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
		DefaultLogger.debug(this, "getUploadImageList() Inside command----------1.1.1-------->" +DateUtil.getDate().getTime());
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		String category=(String) map.get("category");
		String startIdx = (String) map.get("startIndex");
		String imageId =(String) map.get("imageId");
		String unCheckId =(String) map.get("unCheckId");
		String event = (String) map.get("event");
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		if(selectedArrayMap==null){ 
		selectedArrayMap = new HashMap();
		}
		List tagDetailList = new ArrayList();
		//retriving image list available for tagging
		tagDetailList =(List) map.get("tagDetailList");
		result.put("startIndex", startIdx);
		String custId = (String) map.get("custId");
//		IImageTagDetails details=(OBImageTagDetails) map.get("ImageTagMapObj");
//		details.setCustId(custId);
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		String HtcOrDb2cm = PropertyManager.getValue("cms.image.htcOrDB2CM");

		String url=PropertyManager.getValue("hcp.rest.url");
		System.out.println("url............"+url);
		
		DefaultLogger.debug(this, "selectedArrayMap.size()============PrintImageCommand============1====================>"+selectedArrayMap.size());
		try{
				DefaultLogger.debug(this, "PrintImageCommand====================193========================>"+imageId);
			if(imageId!=null && !imageId.equals("")){
				String[] selected=imageId.split("-");
				if(selected!=null){
				for(int k=0;k<selected.length;k++){
					OBImageUploadAdd uploadImage = new OBImageUploadAdd();
					if(tagDetailList!= null)
					{
				 uploadImage = (OBImageUploadAdd)tagDetailList.get(Integer.parseInt(selected[k])-1);
					}
				selectedArrayMap.put(String.valueOf(uploadImage.getImgId()),String.valueOf(uploadImage.getImgId()));
				}
				}
			}
				DefaultLogger.debug(this, "PrintImageCommand====================214========================>"+unCheckId);
				if(unCheckId!=null && !unCheckId.equals("")){
					String[] unchecked=unCheckId.split("-");
					if(unchecked!=null){
						for(int ak=0;ak<unchecked.length;ak++){
							OBImageUploadAdd uploadImage = new OBImageUploadAdd();
							if(tagDetailList!= null)
							{
						 uploadImage = (OBImageUploadAdd)tagDetailList.get(Integer.parseInt(unchecked[ak])-1);
							}
							selectedArrayMap.remove(String.valueOf(uploadImage.getImgId()));
						}
						}
				}
				
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		DefaultLogger.debug(this, "selectedArrayMap.size()============PrintImageCommand=============2===================>"+selectedArrayMap.size());
		result.put("selectedArrayMap", selectedArrayMap);
		
		result.put("custId", custId);
		if(!"".equals(category)){
		result.put("category", category);
		}
		
		 DKDatastoreICM connection = null;
			int downloadImageCount =0;
		
		try {
			List proxyMgr = new ArrayList();
			//retriving image list available for tagging
				proxyMgr =(List) map.get("tagDetailList");
			//************************************************************************************************************************************
			
				
				 //TODO : Create connection
				 try{
					 DefaultLogger.debug(this,"Opening connection in printing operation of image" + connection);
					  //connection = (DKDatastoreICM) DB2ConnectionPool.getConnectionPoolInstance().borrowObject();
					  DefaultLogger.debug(this,"connection opened in printing operation of image" + connection);
				 }catch(Exception e){
					 e.printStackTrace();
				 }
				 
				 DefaultLogger.debug(this,"db2cm connectin " + connection);
				
				
				
				if(proxyMgr !=null){
				for(int a=0;a<proxyMgr.size();a++){
					String basePath=PropertyManager.getValue("contextPath");
					OBImageUploadAdd obImageUploadAdd= (OBImageUploadAdd) proxyMgr.get(a);
					
					if(selectedArrayMap.containsKey(String.valueOf(obImageUploadAdd.getImgId()))){
						ContentManagerFactory contentManagerFactory = (ContentManagerFactory)BeanHouse.get("contentManagerFactory");
						String imagePath="";
							try {
								Object[] params  = new Object[3];
								params[0] = obImageUploadAdd.getImageFilePath();
								params[1] = obImageUploadAdd.getImgFileName();
								params[2] = String.valueOf(obImageUploadAdd.getStatus());
								/*DefaultLogger.debug(this,"Retriving image from DB2CM");
								imagePath=(String)contentManagerFactory.getContentManagerService().retrieveDocumentOnly(params,connection);
								 DefaultLogger.debug(this,"image retrieved from DB2CM");
								 */
								 if(null == obImageUploadAdd.getHCPStatus() || obImageUploadAdd.getHCPStatus().equals("N")) {
									 DefaultLogger.debug(this,"Retriving image from DB2CM");
										imagePath=(String)contentManagerFactory.getContentManagerService().retrieveDocumentOnly(params,connection);
										 DefaultLogger.debug(this,"image retrieved from DB2CM");
										 
										}else {
											System.out.println("inSide HTC..........");
											imagePath=(String)contentManagerFactory.getContentManagerService().get(obImageUploadAdd.getHCPFileName(),String.valueOf(obImageUploadAdd.getStatus()));
											System.out.println("imagePath..........imagePath");
										
										}
							downloadImageCount++;
							} catch (ContentManagerInitializationException e) {
								throw new CommandProcessingException(e.getMessage(),e);
							} catch (Exception e) {
								throw new CommandProcessingException(e.getMessage(),e);
							}		
							DefaultLogger.debug(this,"imagePath ---" + imagePath);
							ByteArrayOutputStream output = new ByteArrayOutputStream();
							
							if(imagePath!=null){
								try {
									DefaultLogger.debug(this,"inside if loop ---------0:" + imagePath);
									
									String pdfFilePath = "";
									if(null == obImageUploadAdd.getHCPStatus() || obImageUploadAdd.getHCPStatus().equals("N")) {
									        pdfFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getImageFilePath().replace(' ', '_')+".pdf";
									       DefaultLogger.debug(this,"inside if loop -------1:" + pdfFilePath); 
									}else {
										 pdfFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getHCPFileName().replace(' ', '_')+".pdf";
										DefaultLogger.debug(this,"inside if loop -------1:" + pdfFilePath); 
									}
									      // DefaultLogger.debug(this,"inside if loop -------1:" + pdfFilePath); 
									String jpegFilePath ="";
									if(null == obImageUploadAdd.getHCPStatus() || obImageUploadAdd.getHCPStatus().equals("N")) {
										    jpegFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getImageFilePath().replace(' ', '_')+".jpeg";
										   DefaultLogger.debug(this,"inside if loop ---------2:" + jpegFilePath);
									}  else {
										  jpegFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getHCPFileName().replace(' ', '_')+".jpeg";
										 DefaultLogger.debug(this,"inside if loop ---------2:" + jpegFilePath);
										   }
											String pdfname=pdfFilePath;
											   
									        
										    try {
										    	BufferedImage img=null;
										        File file = new File(pdfname);
										        RandomAccessFile raf;
										        raf = new RandomAccessFile(file, "r");
										        FileChannel channel = raf.getChannel();
										        MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
										        PDFFile pdffile = new PDFFile(buf);
										        // draw the first page to an image
										        int num = pdffile.getNumPages();
										        int length;
										            length=num;
										        for (int i = 0; i <= num; i++) {
										            PDFPage page = pdffile.getPage(i);
										            //get the width and height for the doc at the default zoom
										            int width = (int) page.getBBox().getWidth();
										            int height = (int) page.getBBox().getHeight();
										            Rectangle rect = new Rectangle(0, 0, width, height);
										            int rotation = page.getRotation();
										            Rectangle rect1 = rect;
										            if (rotation == 180) {
										                rect1 = new Rectangle(0, 0, rect.height, rect.width);
										            }
										            //generate the image
										                    img = (BufferedImage) page.getImage(
										                    // rect.width, rect.height, //width & height
										                    800,1200,
										                    rect1, // clip rect
										                    null, // null for the ImageObserver
										                    true, // fill background with white
										                    true // block until drawing is done
										                    );
										        }
										        DefaultLogger.debug(this,"before image write ---------3:" + jpegFilePath);
										        ImageIO.write(img, "png", new File(jpegFilePath));
										        DefaultLogger.debug(this,"after image write ---------4:" + jpegFilePath);
										        
										    } catch (FileNotFoundException e1) {
										        System.err.println(e1.getLocalizedMessage());
										    } catch (IOException e) {
										        System.err.println(e.getLocalizedMessage());
										    }
											DefaultLogger.debug(this,"done");
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
							}
					
					if(downloadImageCount == selectedArrayMap.size()){
						break;
					}
				}
			}
				
				
				
			
			//*************************************************************************************************************************************	
			
			result.put("obImageTagAddList", proxyMgr);
			SearchResult searchResult= new SearchResult(0, proxyMgr.size(), proxyMgr.size(), proxyMgr);
			result.put("searchResult", searchResult);
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			throw (new CommandProcessingException(e.getMessage()));
		}finally{
			//TODO : Close connection
			try{
				DefaultLogger.debug(this,"Closing connection in printing operation of image "+ connection);
				DB2ConnectionPool.getConnectionPoolInstance().returnObject(connection);
				DefaultLogger.debug(this,"connection closed in printing operation of image "+ connection);
			}catch(Exception e){
				 e.printStackTrace();
				 DefaultLogger.debug(this,e);
			 }
		
				
			DefaultLogger.debug(this,"single connection has retrieved > " + downloadImageCount+" < no of images");
		}
	

		
		
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "getUploadImageList() Inside command----------2.2.2-------->" +DateUtil.getDate().getTime());
		return returnMap;
	}


}
