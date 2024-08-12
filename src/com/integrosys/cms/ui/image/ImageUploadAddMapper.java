package com.integrosys.cms.ui.image;


import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
/**
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/03 02:00:58 $ Tag: $Name: $
 */
public class ImageUploadAddMapper extends AbstractCommonMapper {
	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm sForm, HashMap map)
			throws MapperException {
		// TODO Auto-generated method stub
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT_CREATE);
		DefaultLogger.debug(this, " >>user in mapper event<< " + event);
		ImageUploadAddForm aForm = (ImageUploadAddForm) sForm;
		
		try {
			OBImageUploadAdd obImageUploadAdd = new OBImageUploadAdd();
			if (aForm.getFormFile() != null) {
				obImageUploadAdd.setFormFile(aForm.getFormFile());
			}
			if (aForm.getImgId() != null) {
				obImageUploadAdd.setImgId(Long.parseLong(aForm.getImgId()));
			}
			if (aForm.getImgFileName() != null) {
			obImageUploadAdd.setImgFileName(aForm.getImgFileName());
			}
			if (aForm.getImgSize() != null) {
			obImageUploadAdd.setImgSize(Long.parseLong(aForm.getImgSize()));
			}
			if (aForm.getCustId() != null) {
			obImageUploadAdd.setCustId(aForm.getCustId());
			}
			if (aForm.getCustName() != null) {
			obImageUploadAdd.setCustName(aForm.getCustName());
			}
			
			if (aForm.getSelectedArray() != null) {
				obImageUploadAdd.setSelectedArray(aForm.getSelectedArray());
				}
			if (aForm.getUnCheckArray() != null) {
					obImageUploadAdd.setUnCheckArray(aForm.getUnCheckArray());
				}
			
			if (aForm.getImgDepricated() != null) {
			obImageUploadAdd.setImgDepricated(aForm.getImgDepricated());
			}
			if (aForm.getImageFilePath() != null) {
			obImageUploadAdd.setImageFilePath(aForm.getImageFilePath());
			}
			//Added By Anil
			
			if(aForm.getFacilityName() != null) {
				obImageUploadAdd.setFacilityName(aForm.getFacilityName());
			}
			
			if(aForm.getFacilityDocName() != null) {
				obImageUploadAdd.setFacilityDocName(aForm.getFacilityDocName());
			}
			
			if(aForm.getOtherDocName() != null) {
				obImageUploadAdd.setOtherDocName(aForm.getOtherDocName());
			}
			
			if(aForm.getSecurityNameId() != null) {
				obImageUploadAdd.setSecurityNameId(aForm.getSecurityNameId());
			}
			
			if(aForm.getSecurityNameId() != null && !"".equals(aForm.getSecurityNameId())) {
				String securityNameId = aForm.getSecurityNameId();
				int first = securityNameId.indexOf("-");
				String secId = securityNameId.substring(0,first);
				String subtype = securityNameId.substring(first+1);
				
				obImageUploadAdd.setSecurityIdi(secId);
				obImageUploadAdd.setSubTypeSecurity(subtype);
				aForm.setSecurityIdi(secId);
				aForm.setSubTypeSecurity(subtype);
			}
			
			if(aForm.getSecurityDocName() != null) {
				obImageUploadAdd.setSecurityDocName(aForm.getSecurityDocName());
			}
			
			if(aForm.getOtherSecDocName() != null) {
				obImageUploadAdd.setOtherSecDocName(aForm.getOtherSecDocName());
			}
			
			if(aForm.getTypeOfDocument() != null) {
				obImageUploadAdd.setTypeOfDocument(aForm.getTypeOfDocument());
			}
			
			if(aForm.getHasCam() != null) {
				obImageUploadAdd.setHasCam(aForm.getHasCam());
			}
			
			if(aForm.getHasFacility()!=null && "on".equals(aForm.getHasFacility()))
				obImageUploadAdd.setHasFacility("Y");
			else
				obImageUploadAdd.setHasFacility("N");
			
			if(aForm.getHasSecurity()!=null && "on".equals(aForm.getHasSecurity()))
				obImageUploadAdd.setHasSecurity("Y");
			else
				obImageUploadAdd.setHasSecurity("N");
			
			if(aForm.getCategory()!=null){
				obImageUploadAdd.setCategory(aForm.getCategory());
				
				//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
//				if((IImageTagConstants.IMG_CATEGORY_EXCH_INFO).equals(aForm.getCategory())){
				if((ICMSConstant.IMAGE_CATEGORY_EXCHANGE_OF_INFO).equals(aForm.getCategory()) 
						|| ICMSConstant.CODE_IMG_CATEGORY_EXCH_INFO.equals(aForm.getCategory())){
				obImageUploadAdd.setBank(aForm.getBank());
				}
				//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
				
				if(aForm.getHasSubfolder()!=null && "on".equals(aForm.getHasSubfolder()))
					obImageUploadAdd.setHasSubfolder("Y");
				else
					obImageUploadAdd.setHasSubfolder("N");

				if(aForm.getSubfolderName()!=null)
					obImageUploadAdd.setSubfolderName(aForm.getSubfolderName());
				
				if(aForm.getDocumentName()!=null)
					obImageUploadAdd.setDocumentName(aForm.getDocumentName());
				
				if("save_image_details".equals(aForm.getEvent()) || "view_uploaded_image_listing_search".equals(aForm.getEvent())) {//retrieveViewImageGallary view_uploaded_image_listing
					ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
					String categoryCode = imageTagDaoImpl.getCategorycode(aForm.getCategory());
					obImageUploadAdd.setCategory(categoryCode);
				}
				
				if("view_uploaded_image_listing_search".equals(aForm.getEvent())) {
					obImageUploadAdd.setTypeOfDocument(aForm.getCategory());
					aForm.setTypeOfDocument(aForm.getCategory());
				}
				
				
				if(aForm.getStatementTyped()!=null)
					obImageUploadAdd.setStatementTyped(aForm.getStatementTyped());
				if(aForm.getCamDocName() != null)
					obImageUploadAdd.setCamDocName(aForm.getCamDocName());
				if(aForm.getOthersDocsName() != null)
					obImageUploadAdd.setOthersDocsName(aForm.getOthersDocsName());
				if(aForm.getStatementDocName() != null)
					obImageUploadAdd.setStatementDocName(aForm.getStatementDocName());
				
			}
			if (aForm.getStatus() == 0) {
				obImageUploadAdd.setStatus(1);
			}
			else {
				obImageUploadAdd.setStatus(aForm.getStatus());
			}
			obImageUploadAdd.setError(aForm.getError());
			obImageUploadAdd.setImageFile(aForm.getImageFile());
			DefaultLogger.debug(this, " >>user in mapper mapFormToOB<< "+ obImageUploadAdd);
		//	obImageUploadAdd.setFileUpload(aForm.getFileUpload());
			return obImageUploadAdd;
		} catch (Exception ex) {
			DefaultLogger.debug(this, " >>Error in mapper mapFormToOB<< ");
			throw new MapperException("failed to map form to ob of", ex);
		}
	}
	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)
			throws MapperException {
		DefaultLogger.debug(this,"*************Inside Map OB to Form ,there is mapping not define.");
		
	ImageUploadAddForm form= (ImageUploadAddForm) cForm;
	IImageUploadAdd ob= (IImageUploadAdd) obj;
		if(ob.getImgId()!= 0)
			form.setImgId(String.valueOf(ob.getImgId()));
		
		if(ob.getImgSize()!=0)
			form.setImgSize(String.valueOf(ob.getImgSize()));		
		form.setFormFile(ob.getFormFile());
		form.setImgFileName(ob.getImgFileName());
		form.setCustId(ob.getCustId());		
		form.setCustName(ob.getCustName());		
		form.setImgDepricated(ob.getImgDepricated());
		form.setImageFilePath(ob.getImageFilePath());
		form.setCategory(ob.getCategory());		
		//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
		form.setBank(ob.getBank());	
		
		//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
		form.setFacilityName(ob.getFacilityName());
		
		form.setSubfolderName(ob.getSubfolderName());
		
		form.setDocumentName(ob.getDocumentName());

		if(ob.getHasSubfolder()!=null && "Y".equals(ob.getHasSubfolder()))
			form.setHasSubfolder("on");	

		if (ob.getStatus() == 0) {
			form.setStatus(1);
		}
		else {
			form.setStatus(ob.getStatus());
		}
		form.setError(ob.getError());
		return cForm;
	}

}
