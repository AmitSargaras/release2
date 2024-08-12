package com.integrosys.cms.ui.image;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;

public class ImageUploadMapper extends AbstractCommonMapper {

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm
	 *            is of type CommonForm
	 * @throws com.integrosys.base.uiinfra.exception.MapperException
	 *             on errors
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm arg0, HashMap arg1)
			throws MapperException {
		ImageUploadForm aForm = (ImageUploadForm) arg0;
		CustomerSearchCriteria cSearch = new CustomerSearchCriteria();
		if (aForm != null) {
			cSearch.setCustomerName(aForm.getCustomerName());
			cSearch.setLegalID(aForm.getLegalID());
			cSearch.setLeIDType(aForm.getLeIDType());
			cSearch.setIdNO(aForm.getIdNO());
		}
		return cSearch;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm
	 *            is of type CommonForm
	 * @param obj
	 *            is of type Object
	 * @throws com.integrosys.base.uiinfra.exception.MapperException
	 *             on errors
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm arg0, Object arg1, HashMap arg2)
			throws MapperException {
		ImageUploadForm aForm = (ImageUploadForm) arg0;
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