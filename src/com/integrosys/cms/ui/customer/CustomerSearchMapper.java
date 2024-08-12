package com.integrosys.cms.ui.customer;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;

public class CustomerSearchMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm arg0, HashMap arg1) throws MapperException {
		CustomerSearchForm aForm = (CustomerSearchForm) arg0;
		CustomerSearchCriteria cSearch = new CustomerSearchCriteria();
		if (aForm != null) {
			cSearch.setCustomerName(aForm.getCustomerName());
			cSearch.setLegalID(aForm.getLegalID());
			cSearch.setLeIDType(aForm.getLeIDType());
			cSearch.setIdNO(aForm.getIdNO());
			cSearch.setAaNumber(aForm.getAaNumber());
			cSearch.setFacilitySystem(aForm.getFacilitySystem());
			cSearch.setFacilitySystemID(aForm.getFacilitySystemID());
		}
		return cSearch;
	}

	public CommonForm mapOBToForm(CommonForm arg0, Object arg1, HashMap arg2) throws MapperException {
		CustomerSearchForm aForm = (CustomerSearchForm) arg0;
		CustomerSearchCriteria cSearch = (CustomerSearchCriteria) arg1;
		if ((aForm == null) || (cSearch == null)) {
			return aForm;
		}
		aForm.setCustomerName(cSearch.getCustomerName());
		aForm.setLegalID(cSearch.getLegalID());
		aForm.setLeIDType(cSearch.getLeIDType());
		aForm.setIdNO(cSearch.getIdNO());
		aForm.setAaNumber(cSearch.getAaNumber());
		aForm.setFacilitySystem(cSearch.getFacilitySystem());
		aForm.setFacilitySystemID(cSearch.getFacilitySystemID());
		return aForm;
	}

}