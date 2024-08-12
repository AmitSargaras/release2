package com.integrosys.cms.ui.imageTag;

/**
 * Mapper class is used to map form values to objects and vice versa
 * 
 * @author abhijit.rudrakshawar
 */
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.imageTag.bus.IImageTag;
import com.integrosys.cms.app.imageTag.bus.OBImageTag;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ImageTagMapper extends AbstractCommonMapper {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "OBImageTag","com.integrosys.cms.app.imageTag.bus.OBImageTag",SERVICE_SCOPE }, });
	}

	public Object mapFormToOB(CommonForm arg0, HashMap arg1) throws MapperException {
		ImageTagForm aForm = (ImageTagForm) arg0;
		IImageTag imageTag=null;
		try{
		     imageTag= new OBImageTag();
			imageTag.setCustomerName(aForm.getCustomerName());
			imageTag.setLegalName(aForm.getLegalName());
			imageTag.setSubProfileID(aForm.getSubProfileID());
			imageTag.setIdNO(aForm.getIdNO());
			
			
		
		return imageTag;
		}catch (Exception e) {
			throw new MapperException("failed to map form to ob of imageTag item", e);
		}
		
	}

	public CommonForm mapOBToForm(CommonForm arg0, Object arg1, HashMap arg2) throws MapperException {
		ImageTagForm aForm = (ImageTagForm) arg0;
		CustomerSearchCriteria cSearch = (CustomerSearchCriteria) arg1;
		if ((aForm == null) || (cSearch == null)) {
			return aForm;
		}
		aForm.setCustomerName(cSearch.getCustomerName());
		aForm.setLegalID(cSearch.getLegalID());
		aForm.setLeIDType(cSearch.getLeIDType());
		aForm.setIdNO(cSearch.getIdNO());
		return aForm;
	}
	
	

}