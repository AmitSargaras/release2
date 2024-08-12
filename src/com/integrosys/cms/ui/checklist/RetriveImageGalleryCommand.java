package com.integrosys.cms.ui.checklist;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;
import com.integrosys.cms.app.contentManager.factory.ContentManagerFactory;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.ui.checklist.camreceipt.CAMReceiptAction;
import com.integrosys.cms.ui.image.IImageUploadAdd;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

public class RetriveImageGalleryCommand extends AbstractCommand implements ICommonEventConstant, ITagUntagImageConstant {
	
	public String[][] getParameterDescriptor() {
        return (new String[][] {
        	{ "event", String.class.getName() , REQUEST_SCOPE },
			{ "selectedGalleryImageId", String.class.getName() , REQUEST_SCOPE },
        	{ ALL_IMAGES_UPLOAD_ADD_MAP, Map.class.getName() , SERVICE_SCOPE },
        	{ ALL_IMG_IDS, LinkedList.class.getName() , SERVICE_SCOPE },
        });
	}
	
	public String[][] getResultDescriptor() {
        return (new String[][] {
               { SELECTED_OB_IMAGE_UPLOAD, OBImageUploadAdd.class.getName() , REQUEST_SCOPE },
               { CURRENT_IMAGE_INDEX, Integer.class.getName() , REQUEST_SCOPE },
               { "output", ByteArrayOutputStream.class.getName() , REQUEST_SCOPE },
               { "event", String.class.getName() , REQUEST_SCOPE }
        });
    }
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			String event = (String) map.get("event");
			System.out.println("First Event=>"+event);
			String selectedGalleryImageId = (String) map.get("selectedGalleryImageId");
			LinkedList<Long> allImageIds = (LinkedList<Long>) map.get(ALL_IMG_IDS); 
			Map<Long, IImageUploadAdd> allImagesUploadAddMap = (Map<Long, IImageUploadAdd>) map.get(ALL_IMAGES_UPLOAD_ADD_MAP);
			
			OBImageUploadAdd selectedOBImageUpload = (OBImageUploadAdd) allImagesUploadAddMap.get(Long.valueOf(selectedGalleryImageId));
			int currentIndex = allImageIds.indexOf(Long.valueOf(selectedGalleryImageId));
			
			if(selectedOBImageUpload != null) {
				ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
				String typeOfDoc = imageTagDaoImpl.getEntryNameFromCode(selectedOBImageUpload.getCategory());
				selectedOBImageUpload.setTypeOfDocument(typeOfDoc);
				System.out.println("RetriveImageGalleryCommand.java => selectedOBImageUpload.setTypeOfDocument(typeOfDoc)=>"+typeOfDoc);
			}else {
				System.out.println("RetriveImageGalleryCommand.java => selectedOBImageUpload is null.");
			}
			
			DefaultLogger.info(this, "For event "+event + " | allImageIds :" +allImageIds + " | currentIndex : "+currentIndex + 
					" | Selected File Name: "+selectedOBImageUpload.getImgFileName() + " | Selected Image Id : "+selectedGalleryImageId );
			System.out.println("For event "+event + " | allImageIds :" +allImageIds + " | currentIndex : "+currentIndex + 
				" | Selected File Name: "+selectedOBImageUpload.getImgFileName() + " | Selected Image Id : "+selectedGalleryImageId );
			
//			if(event != null && (event.startsWith(CAMReceiptAction.EVENT_RETRIEVE_IMAGE_GALLERY_CHK) || "retrieve_image_gallery_chk_facility_checker_view".equals(event))) {
				ContentManagerFactory contentManagerFactory = (ContentManagerFactory) BeanHouse.get("contentManagerFactory");
				
				String pID= selectedOBImageUpload.getImageFilePath();
				String status= String.valueOf(selectedOBImageUpload.getStatus());
				String imageName=(String) selectedOBImageUpload.getImgFileName();
				String imagePath = "";
				
				try {
					Object[] params = new Object[3];
					params[0] = pID;
					params[1] = imageName;
					params[2] = status;

					if (null == selectedOBImageUpload.getHCPStatus() || selectedOBImageUpload.getHCPStatus().equals("N")) {
						imagePath = (String) contentManagerFactory.getContentManagerService().retrieveDocument(params);
						System.out.println("null == selectedOBImageUpload.getHCPStatus() || selectedOBImageUpload.getHCPStatus().equals(N) ==>imagePath=>"+imagePath);
					} else {
						System.out.println("inSide HTC..........");
						imagePath = (String) contentManagerFactory.getContentManagerService().get(
								selectedOBImageUpload.getHCPFileName(), String.valueOf(selectedOBImageUpload.getStatus()));
						System.out.println("imagePath..........imagePath"+imagePath);

					}
					System.out.println("pID=>"+pID+" status=>"+status+" imageName=>"+imageName);

				} catch (ContentManagerInitializationException e) {
					System.out.println("Exception 1 => e=>"+e);
					throw new CommandProcessingException(e.getMessage(), e);
				} catch (Exception e) {
					System.out.println("Exception 2 => e=>"+e);
					throw new CommandProcessingException(e.getMessage(), e);
				}	
				DefaultLogger.debug(this, "imagePath ---" + imagePath);

				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] fileData;
				if (!((imageName.toUpperCase()).endsWith(".PDF"))) {
					if (imagePath != null) {
						try {
							String basePath = PropertyManager.getValue("contextPath");
							String pdfFilePath = basePath + "/dmsImages/" + pID + ".pdf";
							OutputStream file = new FileOutputStream(new File(pdfFilePath));
							
							System.out.println("basePath=>"+basePath+" pdfFilePath=>"+pdfFilePath);
							Document document = new Document(PageSize.A4, 0, 0, 0, 0);

							PdfWriter.getInstance(document, file);
							document.open();
							com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(basePath + imagePath);
							System.out.println("basePath + imagePath ===>"+basePath + imagePath);
							float scaledHeight = new Float(842.0);
							float scaledWidth = new Float(595.0);
							if (image.getScaledHeight() > scaledHeight && image.getScaledWidth() > scaledWidth) {
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
							DefaultLogger.debug(this, "done");
						} catch (Exception e) {
							System.out.println("Exception inside if RetriveImageGallerycommand.java=>"+e);
							e.printStackTrace();
						}
					}
				} else {
					try {
						DefaultLogger.debug(this, "inside else imagename");
						System.out.println("inside else imagename");
						String basePath = PropertyManager.getValue("contextPath");
						String pdfFilePath = "";
						if (null == selectedOBImageUpload.getHCPStatus()
								|| selectedOBImageUpload.getHCPStatus().equals("N")) {
							pdfFilePath = basePath + "/dmsImages/downloads/" + pID + ".pdf";
						} else {
							pdfFilePath = basePath + "/dmsImages/downloads/"
									+ selectedOBImageUpload.getHCPFileName().split("[.]")[0] + ".pdf";
						}

						DefaultLogger.debug(this, "inside else pdfFilePath:" + pdfFilePath);
						System.out.println("basePath=>"+basePath+" pdfFilePath=>"+pdfFilePath);
						pdfFilePath = pdfFilePath.replace(' ', '_');
						DefaultLogger.debug(this, "inside else pdfFilePath:" + pdfFilePath);
						System.out.println("basePath=>"+basePath+" pdfFilePath after replace _ =>"+pdfFilePath);
						fileData = IOUtils.toByteArray(new FileInputStream(new File(pdfFilePath)));
						output.write(fileData);
						System.out.println("done");
//						System.out.println("output=>"+output);
						DefaultLogger.debug(this, "done");
					} catch (Exception e) {
						System.out.println("Exception inside else RetriveImageGallerycommand.java=>"+e);
						e.printStackTrace();
					}

				}

				resultMap.put("output", output);
//			}
			
			resultMap.put(SELECTED_OB_IMAGE_UPLOAD, selectedOBImageUpload);
			resultMap.put(CURRENT_IMAGE_INDEX, currentIndex);
			resultMap.put("event", event);
			
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught : ", e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
