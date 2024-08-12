package com.integrosys.cms.ui.checklist.facilityreceipt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;
import com.integrosys.cms.app.contentManager.factory.ContentManagerFactory;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.IImageTagDao;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;


public class RetriveImageTagUntagFacRecCommand extends AbstractCommand {
	
	private ContentManagerFactory contentManagerFactory;

	public ContentManagerFactory getContentManagerFactory() {
		return contentManagerFactory;
	}

	public void setContentManagerFactory(ContentManagerFactory contentManagerFactory) {
		this.contentManagerFactory = contentManagerFactory;
	}

	public String[][] getParameterDescriptor() {
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] {
				{ "pID", "java.lang.String", REQUEST_SCOPE },
				{ "imageName", "java.lang.String", REQUEST_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
	/*			{ "checkListID", "java.lang.String", REQUEST_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE },
				{ "secSubType", "java.lang.String", REQUEST_SCOPE },
				{ "secName", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRef", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "legalID", "java.lang.String", REQUEST_SCOPE },
				{ "legalConstitution", "java.lang.String", REQUEST_SCOPE },*/
				
				{ "count", "java.lang.String", REQUEST_SCOPE },
				
				{ "tagDetailList", "java.util.List", SERVICE_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] { 
				{ "imagePath", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "checkListID", "java.lang.String", REQUEST_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				
			/*	{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE },
				{ "secSubType", "java.lang.String", REQUEST_SCOPE },
				{ "secName", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRef", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "legalID", "java.lang.String", REQUEST_SCOPE },
				{ "legalConstitution", "java.lang.String", REQUEST_SCOPE },*/
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE},
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE }, 
				{ "count", "java.lang.String", REQUEST_SCOPE },
				{ "tagDetailList", "java.util.List", SERVICE_SCOPE },
				{ "output", "java.io.ByteArrayOutputStream", REQUEST_SCOPE },
				{ "pID", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "imageName", "java.lang.String", REQUEST_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
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
		DefaultLogger.debug(this, "Enter in doExecute()");
		String pID=(String) map.get("pID");
		String count=(String) map.get("count");
		String index=(String) map.get("index");
		ICheckList checkList = (ICheckList) map.get("checkList");
		String imageName=(String) map.get("imageName");
		String status=(String) map.get("status");
		DefaultLogger.debug(this,"doExecute ---" + pID + "    " + imageName + "      " + status);
		System.out.println("RetriveImageTagUntagFacRecCommand.java =>doExecute ---pID=>" + pID + "   imageName=>" + imageName + "      status=>" + status);
		String imagePath="";
		String category=(String) map.get("category");
		String checklistId ="";
		String custId = "";
		List checkListIdLists = new ArrayList();
		List obImageUploadList = new ArrayList();
	
		String custLimitProfileID=(String) map.get("custLimitProfileID");
		String event=(String) map.get("event");
		
		List tagDetailList =(List) map.get("tagDetailList");
		boolean flag = true;
		tagDetailList = new ArrayList();;
		if(tagDetailList == null || tagDetailList.isEmpty()) {
			flag = false;
			count = null;
		}
		System.out.println("RetriveImageTagUntagFacRecCommand.java => flag=>"+flag);
		String imageId =(String) map.get("imageId");
		ICheckListItem[] itList =  checkList.getCheckListItemList();
		
		for(int i=0;i<itList.length;i++)
			{
			if(!"".equals(itList[i].getFacImageTagUntagImgName()) && itList[i].getFacImageTagUntagImgName() != null ){
				String facImgName =(String) itList[i].getFacImageTagUntagImgName();
				String[] facNameArr = facImgName.split(",");
				if((facNameArr[2].trim()).equals(imageId)) {
				checklistId = String.valueOf(itList[i].getCheckListItemID());
				}
				//checkListIdLists.add(checklistId);
				System.out.println("RetriveImageTagUntagFacRecCommand.java =>checklistId=>"+checklistId);
			}
			}
		if(category == null || "".equals(category) ){
			category ="IMG_CATEGORY_FACILITY";
		}
		
		List imageInfoList = new ArrayList(); 
		System.out.println("RetriveImageTagUntagFacRecCommand.java =>imageId=>"+imageId+" imageName=>"+imageName);
		imageInfoList = (ArrayList) getImageInfoList(imageId,imageName);
		if(!imageInfoList.isEmpty() && imageInfoList != null) {
			custId = (String) imageInfoList.get(0);
			pID = (String) imageInfoList.get(1);
			status = (String) imageInfoList.get(2);
			System.out.println("RetriveImageTagUntagFacRecCommand ==> imageInfoList is not empty and null =>custId=>"+custId+" pID=>"+pID+" status=>"+status);
		}
		//checklist id for CUS0000960 => (id = 20121127000013211) 20121127000013211
		IImageTagDao tagDao =(IImageTagDao)BeanHouse.get("imageTagDao");
		
//		if(checkListIdLists != null && !checkListIdLists.isEmpty()) {
//			for(int i=0;i<checkListIdLists.size();i++) {
		IImageTagDetails existingTagDetails=tagDao.getCustImageListForView("actualOBImageTagDetails", custId, IImageTagConstants.FACILITY_DOC,checklistId);
//				IImageTagDetails existingTagDetails=tagDao.getCustImageListForView("actualOBImageTagDetails", custId, IImageTagConstants.FACILITY_DOC,(String)checkListIdLists.get(i));
		if(existingTagDetails!=null){
//			if(tagDetailList == null || tagDetailList.isEmpty()) {
				//tagDetailList=tagDao.getTagImageList("actualOBImageTagMap",String.valueOf(existingTagDetails.getId()), "N");
				System.out.println("RetriveImageTagUntagFacRecCommand => tagDetailList is null or empty");
				tagDetailList = tagDao.getTagUntagImageList("actualOBImageTagMap",String.valueOf(existingTagDetails.getId()), "N",category);
				//resultMap.put("tagDetailList", tagDetailList);getTagUntagImageList
//			}
		}
//			}
//		}
		
		
		String id1="";
		String imgName1 = "";
		
		OBImageUploadAdd img1 = null;
		OBImageUploadAdd img = null;
		if(!tagDetailList.isEmpty() && tagDetailList != null) {
			if((flag == false && count == null) || (flag != false && count == null)) {
		for(int i=0;i<tagDetailList.size();i++) {
		 img1 = (OBImageUploadAdd) tagDetailList.get(i);
		 id1 = String.valueOf(img1.getImgId());
		 imgName1 = img1.getImgFileName();
		 System.out.println("tagDetailList.size()=>"+tagDetailList.size()+" id1="+id1+" imgName1="+imgName1+" imageName="+imageName);
//		 if(imageId.equals(id1)) {
		 if(imageName.equals(imgName1)) {
			 img = (OBImageUploadAdd) tagDetailList.get(i);
			 category = img.getCategory();
			 count = String.valueOf(i);
			 pID = img.getImageFilePath();
			 System.out.println("Inside if=>category="+category+" pID="+pID+" imageId="+imageId+" id1="+id1);
		 }
		}
			}else {
				img = (OBImageUploadAdd) tagDetailList.get(Integer.parseInt(count));
				category = img.getCategory();
				 pID = img.getImageFilePath();
				 imageId = String.valueOf(img.getImgId());
				 System.out.println("Inside else=>category="+category+" pID="+pID+" imageId="+imageId);
			}
		}
		result.put("tagDetailList", tagDetailList);
		result.put("count", count);
		result.put("category", category);
		result.put("pID", pID);
		result.put("status", status);
		result.put("imageName", imageName);
		result.put("imageId", imageId);
//		OBImageUploadAdd img = (OBImageUploadAdd) tagDetailList.get(Integer.parseInt(count));
		 
		pID= img.getImageFilePath();
		status= String.valueOf(img.getStatus());
		imageName=(String) img.getImgFileName();
		IImageTagDetails imageTagDetails = (IImageTagDetails) map.get("ImageTagMapObj");
		String HtcOrDb2cm = PropertyManager.getValue("cms.image.htcOrDB2CM");

		String url=PropertyManager.getValue("hcp.rest.url");
		System.out.println("RetriveImageTagUntagFacRecCommand.java url............"+url);
		
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		if(selectedArrayMap==null){ 
		selectedArrayMap = new HashMap();
		}
		String unCheckId =(String) map.get("unCheckId");
				
					DefaultLogger.debug(this, "ImageTagResultCommand====================193========================>"+imageId);
					System.out.println("RetriveImageTagUntagFacRecCommand =>ImageTagResultCommand====================193========================>"+imageId);
				if(imageId!=null && !imageId.equals("")){
					String[] selected=imageId.split("-");
					if(selected!=null){
					for(int k=0;k<selected.length;k++){
					selectedArrayMap.put(selected[k], selected[k]);
					}
					}
				}
					
					DefaultLogger.debug(this, "ImageTagResultCommand====================214========================>"+unCheckId);
					System.out.println("ImageTagResultCommand====================214========================>"+unCheckId);
					if(unCheckId!=null && !unCheckId.equals("")){
					String[] unchecked=unCheckId.split("-");
					if(unchecked!=null){
						for(int ak=0;ak<unchecked.length;ak++){
						selectedArrayMap.remove(unchecked[ak]);
						}
						}
					}
		
		DefaultLogger.debug(this,"doExecute ---" + pID + "    " + imageName + "      " + status);
		System.out.println("RetriveImageTagUntagFacRecCommand => doExecute ---pID=>" + pID + "    imageName=>" + imageName + "      status=>" + status);
		/*if(count!=null && !count.equals("")&& Integer.parseInt(count)%2==0){
			imagePath="C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\Sample Pictures"+"\\"+imageName;
		}
		else{
			imagePath="C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\Sample Pictures"+"\\"+imageName;
		}*/

			try {
				Object[] params  = new Object[3];
				params[0] = pID;
				params[1] = imageName;
				params[2] = status;
				//imagePath=(String)getContentManagerFactory().getContentManagerService().retrieveDocument(params);
				if(null == img.getHCPStatus() || img.getHCPStatus().equals("N")) {
					System.out.println("Not inSide HTC..........");
					imagePath=(String)getContentManagerFactory().getContentManagerService().retrieveDocument(params);
					System.out.println("imagePath..........imagePath=>"+imagePath);
					}else {
						System.out.println("inSide HTC..........");
						imagePath=(String)getContentManagerFactory().getContentManagerService().get(img.getHCPFileName(),String.valueOf(img.getStatus()));
						System.out.println("imagePath..........imagePath");
					
					}
				
			} catch (ContentManagerInitializationException e) {
				throw new CommandProcessingException(e.getMessage(),e);
			} catch (Exception e) {
				throw new CommandProcessingException(e.getMessage(),e);
			}	
			DefaultLogger.debug(this,"imagePath ---" + imagePath);
		//Added by Anil===============Start
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] fileData;
		if(!((imageName.toUpperCase()).endsWith(".PDF"))){
		if(imagePath!=null){
			try {
				String basePath=PropertyManager.getValue("contextPath");
				//String pdfFilePath=basePath+"/dmsImages/"+pID+".pdf";
				String pdfFilePath="";
				if(null == img.getHCPStatus() || img.getHCPStatus().equals("N")) {
					pdfFilePath=basePath+"/dmsImages/"+pID+".pdf";
					}else {
					pdfFilePath=basePath+"/dmsImages/"+img.getHCPFileName().split("[.]")[0]+".pdf";	
					}
				//String pdfFilePath="C:/"+imageName+".pdf";
				OutputStream file = new FileOutputStream(new File(pdfFilePath));
				Document document = new Document(PageSize.A4, 0, 0, 0, 0);
				PdfWriter.getInstance(document, file);
				document.open();
				//document.add(new Paragraph("ImageName : "+imageName));
				com.lowagie.text.Image image = 
					com.lowagie.text.Image.getInstance(basePath+imagePath);
			//	com.lowagie.text.Image image = 
			//	com.lowagie.text.Image.getInstance(imagePath);
				//float scaledHeight =Float.parseFloat(PropertyManager.getValue("integrosys.image.scaledHeight"));	
				//float scaledWidth = Float.parseFloat(PropertyManager.getValue("integrosys.image.scaledWidth"));	
				float scaledHeight =new Float(842.0);	
				float scaledWidth = new Float(595.0);	
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
				System.out.println("RetriveImageTagUntagFacRecCommand.java => Done");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		}
		else {
			try{
				DefaultLogger.debug(this,"inside else imagename");
				System.out.println("RetriveImageTagUntagFacRecCommand => inside else imagename");
				String basePath=PropertyManager.getValue("contextPath");
				//String pdfFilePath=basePath+"/dmsImages/downloads/"+pID+".pdf";
				String pdfFilePath="";
				if(null == img.getHCPStatus() || img.getHCPStatus().equals("N")) {
					pdfFilePath=basePath+"/dmsImages/downloads/"+pID+".pdf";
					}else {
					pdfFilePath=basePath+"/dmsImages/downloads/"+img.getHCPFileName().split("[.]")[0]+".pdf";	
					}
				DefaultLogger.debug(this,"inside else pdfFilePath:"+pdfFilePath);
				System.out.println("RetriveImageTagUntagFacRecCommand => inside else pdfFilePath:"+pdfFilePath);
				pdfFilePath=pdfFilePath.replace(' ', '_');
				DefaultLogger.debug(this,"inside else pdfFilePath:"+pdfFilePath);
				System.out.println("RetriveImageTagUntagFacRecCommand ::=> inside else pdfFilePath:"+pdfFilePath);
				fileData = IOUtils.toByteArray(new FileInputStream(new File(pdfFilePath)));
				output.write(fileData);
				DefaultLogger.debug(this,"done");
				System.out.println("RetriveImageTagUntagFacRecCommand => done");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
}
		result.put("selectedArrayMap", selectedArrayMap);
		result.put("event", event);
		result.put("output", output);
		//Added by Anil ===============End
		
		result.put("imagePath", imagePath);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return returnMap;
	}
	
	
	
	private List getImageInfoList(String imgId, String imgName) {
		List imageDataList = new ArrayList();
		String data = "";
		String data1 = "";
		String data2 = "";
		
		String sql = "SELECT CUST_ID,IMG_PATH,STATUS FROM CMS_UPLOADED_IMAGES WHERE IMG_ID = '"+imgId+"' AND IMG_FILENAME = '"+imgName+"'";
		System.out.println("RetriveImageTagUntagFacRecCommand.java =>getImageInfoList() sql =>"+sql);
		DBUtil dbUtil=null;
		try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				data=rs.getString("CUST_ID");
				data1=rs.getString("IMG_PATH");
				data2=rs.getString("STATUS");
				imageDataList.add(data);
				imageDataList.add(data1);
				imageDataList.add(data2);
				System.out.println("RetriveImageTagUntagFacRecCommand.java =>getImageInfoList()  data=>"+data+" data1=>"+data1+" data2=>"+data2);
			}
		}
		rs.close();
		dbUtil.close();
		}catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return imageDataList;
	}
}
