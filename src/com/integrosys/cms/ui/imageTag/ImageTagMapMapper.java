package com.integrosys.cms.ui.imageTag;

/**
 * Mapper class is used to map form values to objects and vice versa
 * 
 * @author abhijit.rudrakshawar
 */
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.app.imageTag.bus.OBImageTagDetails;

public class ImageTagMapMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm sForm, HashMap map)
			throws MapperException {
		// TODO Auto-generated method stub
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		
		ImageTagMapForm aForm = (ImageTagMapForm) sForm;
		try {
			OBImageTagDetails obImageTagMap = new OBImageTagDetails();
			if(aForm.getId()!=null && (!aForm.getId().equals("")))
            {
				obImageTagMap.setId(Long.parseLong(aForm.getId()));
            }

			obImageTagMap.setCategory(aForm.getCategory());
			//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
			obImageTagMap.setBank(aForm.getBank());
			//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
			obImageTagMap.setFacilityName(aForm.getFacilityName());
			obImageTagMap.setFacilityDocName(aForm.getFacilityDocName());
			obImageTagMap.setOtherDocName(aForm.getOtherDocName());
			obImageTagMap.setSecurityNameId(aForm.getSecurityNameId());
			obImageTagMap.setSecurityDocName(aForm.getSecurityDocName());
			obImageTagMap.setOtherSecDocName(aForm.getOtherSecDocName());
			obImageTagMap.setTypeOfDocument(aForm.getTypeOfDocument());
			obImageTagMap.setCamDocName(aForm.getCamDocName());
			obImageTagMap.setStatementDocName(aForm.getStatementDocName());
			obImageTagMap.setOthersDocsName(aForm.getOthersDocsName());
			//Added By Prachit
			obImageTagMap.setDocumentName(aForm.getDocumentName());
			obImageTagMap.setSubfolderName(aForm.getSubfolderName());
			obImageTagMap.setSelectedArray(aForm.getSelectedArray());
			obImageTagMap.setUnCheckArray(aForm.getUnCheckArray());
			obImageTagMap.setCustId(aForm.getCustId());
			obImageTagMap.setDocType(aForm.getDocType());
			
			if(IImageTagConstants.CAM_DOC.equals(aForm.getDocType())
			||IImageTagConstants.RECURRENTDOC_DOC.equals(aForm.getDocType())
			||IImageTagConstants.OTHER_DOC.equals(aForm.getDocType())
			||IImageTagConstants.LAD_DOC.equals(aForm.getDocType())
			||IImageTagConstants.CAM_NOTE.equals(aForm.getDocType())
			){
				obImageTagMap.setDocDesc(aForm.getDocDesc());
				obImageTagMap.setCamDate(aForm.getCamDate());
				obImageTagMap.setCamType(aForm.getCamType());
				obImageTagMap.setExpiryDate(aForm.getExpiryDate());
				
			}else if(IImageTagConstants.FACILITY_DOC.equals(aForm.getDocType())){
				if(aForm.getFacilityId()!=null&&!"".equals(aForm.getFacilityId()))
				obImageTagMap.setFacilityId(Long.parseLong(aForm.getFacilityId()));
				obImageTagMap.setDocDesc(aForm.getDocDesc());
			}else if(IImageTagConstants.SECURITY_DOC.equals(aForm.getDocType())){
				obImageTagMap.setSecType(aForm.getSecType());
				obImageTagMap.setSecSubtype(aForm.getSecSubtype());
				if(aForm.getSecurityId()!=null && !"".equals(aForm.getSecurityId()))
				obImageTagMap.setSecurityId(Long.parseLong(aForm.getSecurityId()));
				obImageTagMap.setDocDesc(aForm.getDocDesc());
			}else if(IImageTagConstants.EXCHANGE_OF_INFO.equals(aForm.getDocType())){
				obImageTagMap.setDocDesc(aForm.getDocDesc());
				
			}
			
			if(aForm.getCategory() != null && !"".equals(aForm.getCategory())) {
				aForm.setTypeOfDocument(aForm.getCategory());
				obImageTagMap.setTypeOfDocument(aForm.getCategory());
			}
			
//			if("list_tag_page".equals(aForm.getEvent())) {
			
				ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
				String categoryCode = imageTagDaoImpl.getCategorycode(aForm.getCategory());
				obImageTagMap.setCategory(categoryCode);
				aForm.setCategory(categoryCode);
//			}
			
			
			DefaultLogger.debug(this, " >>user in mapper mapFormToOB<< "+ obImageTagMap);
			return obImageTagMap;
		} catch (Exception ex) {
			throw new MapperException("failed to map form to ob of", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap input)
			throws MapperException {
		DefaultLogger.debug(this,"*************Inside Map OB to Form ,no need to mapping");
		ImageTagMapForm form =(ImageTagMapForm)cForm;
		OBImageTagDetails obTagDetails=(OBImageTagDetails) obj;
		form.setCategory(obTagDetails.getCategory()==null?"":obTagDetails.getCategory());
		form.setDocumentName(obTagDetails.getDocumentName()==null?"":obTagDetails.getDocumentName());
		form.setSubfolderName(obTagDetails.getSubfolderName()==null?"":obTagDetails.getSubfolderName());
		
		form.setCustId(obTagDetails.getCustId());
		form.setDocType(obTagDetails.getDocType());
		form.setCustomerNameLabel(obTagDetails.getCustomerNameLabel());
		if(IImageTagConstants.CAM_DOC.equals(obTagDetails.getDocType())
			||IImageTagConstants.RECURRENTDOC_DOC.equals(obTagDetails.getDocType())
			||IImageTagConstants.OTHER_DOC.equals(obTagDetails.getDocType())||IImageTagConstants.LAD_DOC.equals(obTagDetails.getDocType())
					||IImageTagConstants.EXCHANGE_OF_INFO.equals(obTagDetails.getDocType())){
			form.setDocDesc(obTagDetails.getDocDesc());
			form.setDocDescLabel(obTagDetails.getDocDescLabel());
		}
		else if(IImageTagConstants.FACILITY_DOC.equals(obTagDetails.getDocType())){
				form.setFacilityId(String.valueOf(obTagDetails.getFacilityId()));
				form.setDocDesc(obTagDetails.getDocDesc());
				form.setFacilityIdLabel(String.valueOf(obTagDetails.getFacilityIdLabel()));
				form.setDocDescLabel(obTagDetails.getDocDescLabel());
		}else if(IImageTagConstants.SECURITY_DOC.equals(obTagDetails.getDocType())){
			form.setSecType(obTagDetails.getSecType());
			form.setSecSubtype(obTagDetails.getSecSubtype());
			form.setSecurityId(String.valueOf(obTagDetails.getSecurityId()));
			form.setDocDesc(obTagDetails.getDocDesc());
			form.setSecTypeLabel(obTagDetails.getSecTypeLabel());
			form.setSecSubtypeLabel(obTagDetails.getSecSubtypeLabel());
			form.setSecurityIdLabel(String.valueOf(obTagDetails.getSecurityIdLabel()));
			form.setDocDescLabel(obTagDetails.getDocDescLabel());
		}else if(IImageTagConstants.CAM_NOTE.equals(obTagDetails.getDocType())){
			form.setCamDate(obTagDetails.getCamDate());
			form.setCamType(obTagDetails.getCamType());
			form.setExpiryDate(obTagDetails.getExpiryDate());
		}

		return form;
	}

}
