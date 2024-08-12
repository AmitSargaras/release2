package com.integrosys.cms.ui.limit.facility.relationship;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class CustomerSearchMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this, "Inside mapForm to ob ");
		RelationshipForm form = (RelationshipForm) cForm;
		CustomerSearchCriteria cSearch = new CustomerSearchCriteria();
		cSearch.setCustomerName(form.getLegalName());
		if (FacilityMainAction.SEARCH_BY_LEGAL_ID.equals(form.getSearchButton())
				|| FacilityMainAction.SEARCH_BY_LEGAL_ID_WO_FRAME.equals(form.getSearchButton())) {
			cSearch.setLegalID(form.getLEReference());
		}
		else if (FacilityMainAction.SEARCH_BY_ID_NUMBER.equals(form.getSearchButton())
				|| FacilityMainAction.SEARCH_BY_ID_NUMBER_WO_FRAME.equals(form.getSearchButton())) {
			cSearch.setIDType(form.getIdType());
			cSearch.setIdNO(form.getIdNo());
			cSearch.setIssuedCountry(form.getIssuedCountry());
		}
		cSearch.setNItems(10);
		cSearch.setStartIndex(0);
		return cSearch;
	}

	public CommonForm mapOBToForm(CommonForm arg0, Object arg1, HashMap arg2) throws MapperException {
		// TODO Auto-generated method stub
		return null;
	}

}
