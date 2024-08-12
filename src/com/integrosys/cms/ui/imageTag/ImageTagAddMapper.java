package com.integrosys.cms.ui.imageTag;
/**
 *@author abhijit.rudrakshawar
 *$ Mapper for Image Tag
 */
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.OBImageTagDetails;

public class ImageTagAddMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm sForm, HashMap map)
			throws MapperException {
		
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT_CREATE);
		DefaultLogger.debug(this, " >>user in mapper event<< " + event);
		ImageTagAddForm aForm = (ImageTagAddForm) sForm;
		try {
			OBImageUploadAdd obImageUploadAdd = new OBImageUploadAdd();
			obImageUploadAdd.setCustId(aForm.getCustId());
			obImageUploadAdd.setCustName(aForm.getCustName());
			obImageUploadAdd.setImgDepricated(aForm.getImgDepricated());
			DefaultLogger.debug(this, " >>user in mapper mapFormToOB<< "
					+ obImageUploadAdd);
			return obImageUploadAdd;
		} catch (Exception ex) {
			throw new MapperException("failed to map form to ob of", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap arg2)
			throws MapperException {
		DefaultLogger.debug(this,
				"*************Inside Map OB to Form ,no need to mapping");
		ImageTagAddForm form = (ImageTagAddForm) cForm;
		OBImageTagDetails obImageUploadAdd = (OBImageTagDetails) obj;
		form.setCustId(obImageUploadAdd.getCustId());
		form.setSecurityId(Long.toString(obImageUploadAdd.getSecurityId()));

		return cForm;
	}

}
