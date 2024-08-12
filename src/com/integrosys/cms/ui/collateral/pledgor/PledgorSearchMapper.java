package com.integrosys.cms.ui.collateral.pledgor;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;

public class PledgorSearchMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this, "Inside mapForm to ob ");
		PledgorForm form = (PledgorForm) cForm;
		CustomerSearchCriteria cSearch = new CustomerSearchCriteria();
		cSearch.setCustomerName(form.getCustomerName());
		if ("2".equals(form.getSearchButton())) {
			cSearch.setLegalID(form.getLegalID());
		}
		else if ("3".equals(form.getSearchButton())) {
			cSearch.setIDType(form.getIDType());
			cSearch.setIdNO(form.getIdNO());
			cSearch.setIssuedCountry(form.getIssuedCountry());
		}
		cSearch.setNItems(10);
		cSearch.setStartIndex(0);
		return cSearch;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		// TODO Auto-generated method stub
		return null;
	}

}
