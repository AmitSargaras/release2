package com.integrosys.cms.ui.imageTag;
 
/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/CreateSampleTestCommand.java,v 1.3 2004/07/08 12:32:45 jtan Exp $
 */


import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.struts.util.LabelValueBean;

import com.ibm.mm.sdk.server.DKDatastoreICM;
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
import com.integrosys.cms.app.contentManager.ibmDb2Cm.connectionPool.DB2ConnectionPool;
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
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

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
				{ "pdfPagesMap", "java.util.HashMap", SERVICE_SCOPE },
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
			CommandValidationException {
		DefaultLogger.debug(this, "getUploadImageList() Inside command----------1.1.1-------->" +DateUtil.getDate().getTime());
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap collateralCodeMap = getCollateralInfo();
		String category=(String) map.get("category");
		String startIdx = (String) map.get("startIndex");
		String imageId =(String) map.get("imageId");
		String unCheckId =(String) map.get("unCheckId");
		String event = (String) map.get("event");
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
		long limitProfileID=0l;
		if(strLimitProfileId!=null){
			limitProfileID=Long.parseLong(strLimitProfileId);
		}
		List obImageTagAddList = new ArrayList();
		//retriving image list available for tagging
		obImageTagAddList =(List) map.get("obImageTagAddList");
		String HtcOrDb2cm = PropertyManager.getValue("cms.image.htcOrDB2CM");

		String url=PropertyManager.getValue("hcp.rest.url");
		System.out.println("url............"+url);
		DefaultLogger.debug(this, "selectedArrayMap.size()============ImageTagResultCommand============1====================>"+selectedArrayMap.size());
		try{
		if(details!=null){
			DefaultLogger.debug(this, "details.getCategory()============================================>"+details.getCategory());
			DefaultLogger.debug(this, "details.getSelectedArray()============================================>"+details.getSelectedArray());
			category=details.getCategory();
			String selectedArrayString=details.getSelectedArray();
			if(selectedArrayString!=null && !selectedArrayString.equals("") ){String[] selected=selectedArrayString.split("-");
				if(selected!=null){
				for(int k=0;k<selected.length;k++){
					OBImageUploadAdd uploadImage = new OBImageUploadAdd();
					if(obImageTagAddList!= null)
					{
				 uploadImage = (OBImageUploadAdd)obImageTagAddList.get(Integer.parseInt(selected[k])-1);
					}
				selectedArrayMap.put(String.valueOf(uploadImage.getImgId()),String.valueOf(uploadImage.getImgId()));
				}
				}}else{
				DefaultLogger.debug(this, "ImageTagResultCommand====================193========================>"+imageId);
			if(imageId!=null && !imageId.equals("")){
				String[] selected=imageId.split("-");
				if(selected!=null){
				for(int k=0;k<selected.length;k++){
					OBImageUploadAdd uploadImage = new OBImageUploadAdd();
					if(obImageTagAddList!= null)
					{
				 uploadImage = (OBImageUploadAdd)obImageTagAddList.get(Integer.parseInt(selected[k])-1);
					}
				selectedArrayMap.put(String.valueOf(uploadImage.getImgId()),String.valueOf(uploadImage.getImgId()));
				}
				}
			}
				
			}
			
			String unCheckArrayString=details.getUnCheckArray();
			if(unCheckArrayString!=null && !unCheckArrayString.equals("")){
				String[] unchecked=unCheckArrayString.split("-");
				if(unchecked!=null){
					for(int ak=0;ak<unchecked.length;ak++){
						OBImageUploadAdd uploadImage = new OBImageUploadAdd();
						if(obImageTagAddList!= null)
						{
					 uploadImage = (OBImageUploadAdd)obImageTagAddList.get(Integer.parseInt(unchecked[ak])-1);
						}
						selectedArrayMap.remove(String.valueOf(uploadImage.getImgId()));
					}
					}
			}else{
				DefaultLogger.debug(this, "ImageTagResultCommand====================214========================>"+unCheckId);
				if(unCheckId!=null && !unCheckId.equals("")){
					String[] unchecked=unCheckId.split("-");
					if(unchecked!=null){
						for(int ak=0;ak<unchecked.length;ak++){
							OBImageUploadAdd uploadImage = new OBImageUploadAdd();
							if(obImageTagAddList!= null)
							{
						 uploadImage = (OBImageUploadAdd)obImageTagAddList.get(Integer.parseInt(unchecked[ak])-1);
							}
							selectedArrayMap.remove(String.valueOf(uploadImage.getImgId()));
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
		
		result.put("custId", custId);
		if(!"".equals(category)){
		result.put("category", category);
		}
		
		 DKDatastoreICM connection = null;
			int downloadImageCount =0;
		try {
			List proxyMgr = new ArrayList();
			//retriving image list available for tagging
			if("paginate".equals(event)||"print_image".equals(event)){
				proxyMgr =(List) map.get("obImageTagAddList");
			}else{
				proxyMgr = (List) getImageTagProxyManager().getCustImageListByCriteria(details);
			}
			
			
			//************************************************************************************************************************************
			
			HashMap pdfPagesMap= new HashMap();
			
			
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
					 DefaultLogger.debug(this,"inside for Loop---------------->"+ a);
					String basePath=PropertyManager.getValue("contextPath");
					OBImageUploadAdd obImageUploadAdd= (OBImageUploadAdd) proxyMgr.get(a);
				
					if(selectedArrayMap.containsKey(String.valueOf(obImageUploadAdd.getImgId()))){
						String imagePath="";
							ContentManagerFactory contentManagerFactory = (ContentManagerFactory)BeanHouse.get("contentManagerFactory");
						
							try {
								DefaultLogger.debug(this,"before DB2CM call ---" + imagePath);
								Object[] params  = new Object[3];
								params[0] = obImageUploadAdd.getImageFilePath();
								params[1] = obImageUploadAdd.getImgFileName();
								params[2] = String.valueOf(obImageUploadAdd.getStatus());
								/* DefaultLogger.debug(this,"Retriving image from DB2CM");
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
								DefaultLogger.debug(this,"after DB2CM call ---" + imagePath);
							} catch (ContentManagerInitializationException e) {
								throw new CommandProcessingException(e.getMessage(),e);
							} catch (Exception e) {
								throw new CommandProcessingException(e.getMessage(),e);
							}	   
							DefaultLogger.debug(this,"imagePath ---" + imagePath);
							ByteArrayOutputStream output = new ByteArrayOutputStream();
							
							byte[] fileData;
							if(obImageUploadAdd.getImgFileName().toUpperCase().endsWith(".TIF")
									||obImageUploadAdd.getImgFileName().toUpperCase().endsWith(".TIFF")){
								DefaultLogger.debug(this,"inside if imagename");
							if(imagePath!=null){
								DefaultLogger.debug(this,"inside if Loop-------------->"+ a);
								try {
									
									//String pdfFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getImageFilePath().replace(' ', '_')+".pdf";
									String pdfFilePath="";
									if(null == obImageUploadAdd.getHCPStatus() || obImageUploadAdd.getHCPStatus().equals("N")) {
										pdfFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getImageFilePath().replace(' ', '_')+".pdf";
										}else {
										pdfFilePath=basePath+"/dmsImagesdownloads/"+obImageUploadAdd.getHCPFileName().split("[.]")[0]+".pdf";	
										}
									//String pdfFilePath="C:/"+imageName+".pdf";
									DefaultLogger.debug(this,"pdfFilePath---------------242----- ---" + pdfFilePath);
									OutputStream file = new FileOutputStream(new File(pdfFilePath));
									Document document = new Document(PageSize.A4, 50, 50, 50, 50);
									PdfWriter.getInstance(document, file);
									document.open();
									//document.add(new Paragraph("ImageName : "+imageName));
									com.lowagie.text.Image image = 
									com.lowagie.text.Image.getInstance(basePath+imagePath);
									//com.lowagie.text.Image image = 
									//com.lowagie.text.Image.getInstance(imagePath);
									float scaledHeight =Float.parseFloat(PropertyManager.getValue("integrosys.image.scaledHeight"));	
									float scaledWidth = Float.parseFloat(PropertyManager.getValue("integrosys.image.scaledWidth"));	
									
									if(image.getScaledHeight()> scaledHeight && image.getScaledWidth()>scaledWidth)
									{
									image.scalePercent(24f);
									image.scaleAbsoluteHeight(PageSize.A4.getHeight());
									image.scaleAbsoluteWidth(PageSize.A4.getWidth());
									image.setAbsolutePosition(0, 0);
									}
									document.add(image);
									document.close();
									file.close();
									fileData = IOUtils.toByteArray(new FileInputStream(new File(pdfFilePath)));
									output.write(fileData);
									DefaultLogger.debug(this,"done");
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}
							
							try {
								DefaultLogger.debug(this,"inside if loop ---------0:" + imagePath);
								
								
								String pdfFilePath = "";
								if(null == obImageUploadAdd.getHCPStatus() || obImageUploadAdd.getHCPStatus().equals("N")) {
								        pdfFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getImageFilePath().replace(' ', '_')+".pdf";
								       DefaultLogger.debug(this,"inside if loop -------1:" + pdfFilePath); 
								}else {
									 pdfFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getHCPFileName().split("[.]")[0]+".pdf";
									DefaultLogger.debug(this,"inside if loop -------1:" + pdfFilePath); 
								}
								      // DefaultLogger.debug(this,"inside if loop -------1:" + pdfFilePath); 
								String jpegFilePath ="";
								if(null == obImageUploadAdd.getHCPStatus() || obImageUploadAdd.getHCPStatus().equals("N")) {
									    jpegFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getImageFilePath().replace(' ', '_')+".jpeg";
									   DefaultLogger.debug(this,"inside if loop ---------2:" + jpegFilePath);
								}  else {
									  jpegFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getHCPFileName().split("[.]")[0]+".jpeg";
									 DefaultLogger.debug(this,"inside if loop ---------2:" + jpegFilePath);
									   }
									   DefaultLogger.debug(this,"inside if loop ---------2:" + jpegFilePath);
										
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
									        
									        pdfPagesMap.put(String.valueOf(obImageUploadAdd.getImgId()),new Integer(num));
									        int length;
									            length=num;
									        for (int i = 1; i <= num; i++) {
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
									                    
									             ImageIO.write(img, "png", new File(jpegFilePath+"_"+i+".jpeg"));        
									        }
									        
									       
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
							
							
							
							
							

							
							
							if(imagePath!=null){
								
								if(obImageUploadAdd.getImgFileName().toUpperCase().endsWith(".PDF")){
								try {
									DefaultLogger.debug(this,"inside if Loop----printimagecommand---------->"+ a);
									DefaultLogger.debug(this,"inside if loop ---------0:" + imagePath);
									
									String pdfFilePath = "";
									if(null == obImageUploadAdd.getHCPStatus() || obImageUploadAdd.getHCPStatus().equals("N")) {
									        pdfFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getImageFilePath().replace(' ', '_')+".pdf";
									       DefaultLogger.debug(this,"inside if loop -------1:" + pdfFilePath); 
									}else {
										 pdfFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getHCPFileName().split("[.]")[0]+".pdf";
										DefaultLogger.debug(this,"inside if loop -------1:" + pdfFilePath); 
									}
									      // DefaultLogger.debug(this,"inside if loop -------1:" + pdfFilePath); 
									String jpegFilePath ="";
									if(null == obImageUploadAdd.getHCPStatus() || obImageUploadAdd.getHCPStatus().equals("N")) {
										    jpegFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getImageFilePath().replace(' ', '_')+".jpeg";
										   DefaultLogger.debug(this,"inside if loop ---------2:" + jpegFilePath);
									}  else {
										  jpegFilePath=basePath+"/dmsImages/downloads/"+obImageUploadAdd.getHCPFileName().split("[.]")[0]+".jpeg";
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
										        
										        pdfPagesMap.put(String.valueOf(obImageUploadAdd.getImgId()),new Integer(num));
										        int length;
										            length=num;
										        for (int i = 1; i <= num; i++) {
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
										                    
										             ImageIO.write(img, "png", new File(jpegFilePath+"_"+i+".jpeg"));        
										        }
										        
										       
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
							}
					
					if(downloadImageCount == selectedArrayMap.size()){
						break;
					}
				}
			}
			
			
			
			
			
			
			
			//*************************************************************************************************************************************	
			result.put("pdfPagesMap", pdfPagesMap);
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
	

		List documentItemList = new ArrayList();	
		List facilityIdList = new ArrayList();	
		List secTypeList = new ArrayList();	
		List secSubtypeList = new ArrayList();	
		List securityIdList = new ArrayList();	
		
		if(details!=null && !"".equals(details.getDocType())){
			DefaultLogger.debug(this, "Got the form ");
			String docType = details.getDocType();
			 if(IImageTagConstants.CAM_DOC.equals(docType)){
				 //In case of doc type CAM  populate the documentItemList only
					try {
						CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
						if(camCheckList!=null){
							 	long camCheckListID = camCheckList.getCheckListID();
								ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
								ICheckList checkList = checkListTrxValue.getCheckList();
								ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
								for (int j = 0; j < checkListItemList.length; j++) {
									ICheckListItem iCheckListItem = checkListItemList[j];
									DefaultLogger.debug(this, "In Test 6. Got the item list ");
									DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
									DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
									DefaultLogger.debug(this, "Going out of Test 6. ");
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
									String label=iCheckListItem.getItemDesc();
									String value= String.valueOf(iCheckListItem.getCheckListItemID());
									LabelValueBean lvBean = new LabelValueBean(label,value);
									documentItemList.add(lvBean);
								}
						}/*else{
							//commented will be used to show proper message
							exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","CAM"));
						}*/
					} catch (CheckListException e) {
						e.printStackTrace();
					}
					
			}else if(IImageTagConstants.RECURRENTDOC_DOC.equals(docType)){
				 //In case of doc type CAM  populate the documentItemList only
				 try {
					 CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("REC",limitProfileID);
					 if(camCheckList!=null){
						 long camCheckListID = camCheckList.getCheckListID();
						 ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
						 ICheckList checkList = checkListTrxValue.getCheckList();
						 ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
						 for (int j = 0; j < checkListItemList.length; j++) {
							 ICheckListItem iCheckListItem = checkListItemList[j];
							 DefaultLogger.debug(this, "In Test 6. Got the item list ");
							 DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
							 DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
							 DefaultLogger.debug(this, "Going out of Test 6. ");
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
							 String label=iCheckListItem.getItemDesc();
							 String value= String.valueOf(iCheckListItem.getCheckListItemID());
							 LabelValueBean lvBean = new LabelValueBean(label,value);
							 documentItemList.add(lvBean);
						 }
					 }/*else{
							//commented will be used to show proper message
							exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","CAM"));
						}*/
				 } catch (CheckListException e) {
					 e.printStackTrace();
				 }
				 
			 }else if(IImageTagConstants.OTHER_DOC.equals(docType)){
				 //In case of doc type CAM  populate the documentItemList only
				 try {
					 CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("O",limitProfileID);
					 if(camCheckList!=null){
						 long camCheckListID = camCheckList.getCheckListID();
						 ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
						 ICheckList checkList = checkListTrxValue.getCheckList();
						 ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
						 for (int j = 0; j < checkListItemList.length; j++) {
							 ICheckListItem iCheckListItem = checkListItemList[j];
							 DefaultLogger.debug(this, "In Test 6. Got the item list ");
							 DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
							 DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
							 DefaultLogger.debug(this, "Going out of Test 6. ");
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
							 String label=iCheckListItem.getItemDesc();
							 String value= String.valueOf(iCheckListItem.getCheckListItemID());
							 LabelValueBean lvBean = new LabelValueBean(label,value);
							 documentItemList.add(lvBean);
						 }
					 }/*else{
							//commented will be used to show proper message
							exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","CAM"));
						}*/
				 } catch (CheckListException e) {
					 e.printStackTrace();
				 }
				 
			 }else if(IImageTagConstants.LAD_DOC.equals(docType)){
				 //In case of doc type CAM  populate the documentItemList only
				 try {
					 CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("O",limitProfileID);
					 if(camCheckList!=null){
						 long camCheckListID = camCheckList.getCheckListID();
						 ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
						 ICheckList checkList = checkListTrxValue.getCheckList();
						 ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
						 for (int j = 0; j < checkListItemList.length; j++) {
							 ICheckListItem iCheckListItem = checkListItemList[j];
							 DefaultLogger.debug(this, "In Test 6. Got the item list ");
							 DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
							 DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
							 DefaultLogger.debug(this, "Going out of Test 6. ");
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
							 String label=iCheckListItem.getItemDesc();
							 String value= String.valueOf(iCheckListItem.getCheckListItemID());
							 LabelValueBean lvBean = new LabelValueBean(label,value);
							 documentItemList.add(lvBean);
						 }
					 }/*else{
							//commented will be used to show proper message
							exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","CAM"));
						}*/
				 } catch (CheckListException e) {
					 e.printStackTrace();
				 }
				 
			 }
			 else if(IImageTagConstants.FACILITY_DOC.equals(docType)){
				// In case of doc type Facility populate the facilityIdList
				
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
				try {
					List lmtList = proxy.getLimitSummaryListByAA(Long.toString(limitProfileID));
					if(lmtList!=null && lmtList.size()>0){
						String label;
						String value;
						for (int i = 0; i < lmtList.size(); i++) {
							LimitListSummaryItemBase limitSummaryItem=(LimitListSummaryItemBase) lmtList.get(i);
							label=limitSummaryItem.getCmsLimitId() +" - "+limitSummaryItem.getProdTypeCode();
							value= limitSummaryItem.getCmsLimitId();
							LabelValueBean lvBean = new LabelValueBean(label,value);
							facilityIdList.add(lvBean);
						}
					}
				} catch (LimitException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				// if user has selected the facility id populate the documentItemList
				if(details.getFacilityId()!=0){
					try {
						CheckListSearchResult checkListSearchResult=checklistProxyManager.getCheckListByCollateralID(details.getFacilityId());
						if(checkListSearchResult!=null){
							long facilityCheckListID = checkListSearchResult.getCheckListID();
							
							if(facilityCheckListID!=ICMSConstant.LONG_INVALID_VALUE){
								ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(facilityCheckListID);
								ICheckList checkList = checkListTrxValue.getCheckList();
								ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
								for (int j = 0; j < checkListItemList.length; j++) {
									ICheckListItem iCheckListItem = checkListItemList[j];
									DefaultLogger.debug(this, "In Test 6. Got the item list ");
									DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
									DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
									DefaultLogger.debug(this, "Going out of Test 6. ");
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
									String label=iCheckListItem.getItemDesc();
									String value= String.valueOf(iCheckListItem.getCheckListItemID());
									LabelValueBean lvBean = new LabelValueBean(label,value);
									documentItemList.add(lvBean);
								}
							}
						}/*else{
						//commented will be used to show proper message
							exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","Facility"));
						}*/

					  } catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (CheckListException e) {
						e.printStackTrace();
					}		

				}
			}else if(IImageTagConstants.SECURITY_DOC.equals(docType)){
				//if doc type is Security  populate the secTypeList
				secTypeList=getSecurityTypeList();
				//check if secType is selected then populate the secSubtypeList 
				if(!"".equals(details.getSecType())){
					secSubtypeList= getSecuritySubtypeList(details.getSecType());
					//check if secSubtype is selected then populate the securityIdList 
					if(!"".equals(details.getSecSubtype())){
						HashMap lmtcolmap = new HashMap();
						ILimitProxy limitProxy = LimitProxyFactory.getProxy();
						ILimitProfile limitProfileOB=new OBLimitProfile();
						try {
							limitProfileOB = limitProxy.getLimitProfile(limitProfileID);
						} catch (LimitException e1) {
							e1.printStackTrace();
						}

						lmtcolmap = limitProxy.getCollateralLimitMap(limitProfileOB);
	
						Map sortedCollateralLimitMap = new TreeMap(new Comparator() {
							public int compare(Object thisObj, Object thatObj) {
								ICollateral thisCol = (ICollateral) thisObj;
								ICollateral thatCol = (ICollateral) thatObj;
	
								long thisValue = thisCol.getCollateralID();
								long thatValue = thatCol.getCollateralID();
	
								return (thisValue < thatValue ? -1
										: (thisValue == thatValue ? 0 : 1));
							}
						});
						sortedCollateralLimitMap.putAll(lmtcolmap);
						OBCollateral obcol = new OBCollateral();
						String secSubType = details.getSecSubtype();
						Set set = lmtcolmap.keySet();
						ICollateral[] cols = (ICollateral[]) set.toArray(new ICollateral[0]);
						Arrays.sort(cols, new CollateralComparator());
						Iterator i = Arrays.asList(cols).iterator();
						String label;
						String value;
						while (i.hasNext()) {
							obcol = ((OBCollateral) i.next());
							if (obcol.getCollateralSubType().getSubTypeCode().equals(secSubType)) {
								label = obcol.getCollateralID() + " - " + collateralCodeMap.get(obcol.getCollateralCode());
								value = String.valueOf(obcol.getCollateralID());
								LabelValueBean lvBean = new LabelValueBean(label,value);
								securityIdList.add(lvBean);
							}
	
						}
						//check if securityID is selected then populate the documentItemList	
						if(details.getSecurityId()!=0){
							try {
								HashMap checkListMap = this.checklistProxyManager.getAllCollateralCheckListSummaryList(theOBTrxContext, limitProfileID);
								long checkListID=ICMSConstant.LONG_INVALID_VALUE;
								if (checkListMap != null) {
									CollateralCheckListSummary[] colChkList = (CollateralCheckListSummary[]) checkListMap.get(ICMSConstant.NORMAL_LIST);
									if(colChkList!=null){
									for (int n = 0; n < colChkList.length; n++) {
										CollateralCheckListSummary collateralCheckListSummary = colChkList[n];
										if(collateralCheckListSummary.getCollateralID()==details.getSecurityId()){
											checkListID = collateralCheckListSummary.getCheckListID();
											if(checkListID!=ICMSConstant.LONG_INVALID_VALUE){
												ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(checkListID);
												ICheckList checkList = checkListTrxValue.getCheckList();
												ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
												for (int j = 0; j < checkListItemList.length; j++) {
													ICheckListItem iCheckListItem = checkListItemList[j];
													DefaultLogger.debug(this, "In Test 4. Got the item list ");
													DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
													DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
													DefaultLogger.debug(this, "Going out of Test 4. ");
//													String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
													String labelCC=iCheckListItem.getItemDesc();
													String valueCC= String.valueOf(iCheckListItem.getCheckListItemID());
													LabelValueBean lvBean = new LabelValueBean(labelCC,valueCC);
													documentItemList.add(lvBean);
												}
												
											}
											break;
										}
										
									}
									}
								}/*
								//commented will be used to show proper message
								  else{
									exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","CAM"));
								}*/
	
							} catch (CheckListException e) {
								e.printStackTrace();
							}
						}
						
					}
				}
			}
		}
		result.put("ImageTagMapObj", details);
		result.put("facilityIdList", facilityIdList);
		result.put("secTypeList", secTypeList);
		result.put("secSubtypeList", secSubtypeList);
		result.put("securityIdList", securityIdList);
		result.put("documentItemList", documentItemList);
		
		result.put("subfolderNameList", new ArrayList());
		result.put("docNameList", new ArrayList());
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "getUploadImageList() Inside command----------2.2.2-------->" +DateUtil.getDate().getTime());
		return returnMap;
	}

	private List getSecurityTypeList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) (SecurityTypeList.getInstance()
					.getSecurityTypeProperty());
			List valList = (List) (SecurityTypeList.getInstance()
					.getSecurityTypeLabel(null));
			for (int i = 0; i < idList.size(); i++) {
				String id = idList.get(i).toString();
				String val = valList.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getSecuritySubtypeList(String secTypeValue) {
		List lbValList = new ArrayList();
		try {
			if (secTypeValue != null) {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ICollateralSubType[] subtypeLst = helper.getSBMISecProxy()
						.getCollateralSubTypesByTypeCode(secTypeValue);
				if (subtypeLst != null) {
					for (int i = 0; i < subtypeLst.length; i++) {
						ICollateralSubType nextSubtype = subtypeLst[i];
						String id = nextSubtype.getSubTypeCode();
						String value = nextSubtype.getSubTypeName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
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
}
