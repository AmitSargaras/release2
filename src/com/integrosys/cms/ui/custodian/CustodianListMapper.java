package com.integrosys.cms.ui.custodian;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.ui.customer.CustomerListMapper;
import com.integrosys.cms.ui.customer.CustomerSearchAction;

public class CustodianListMapper extends CustomerListMapper {
	/**
	 * Default Construtor
	 */
	public CustodianListMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this, "Inside mapForm to ob ");
		CustodianSearchForm aForm = (CustodianSearchForm) cForm;
		String event = aForm.getEvent();
		if (!CustomerSearchAction.EVENT_PREPARE.equals(event)) {

			CustomerSearchCriteria cSearch = new CustomerSearchCriteria();
			cSearch.setCustomerName(aForm.getCustomerName());
			cSearch.setAll(aForm.getAll());
			if (aForm.getGobutton().equals("2")) {
				cSearch.setLegalID(aForm.getLegalID());
				cSearch.setLeIDType(aForm.getLeIDType());
			}
			else if (aForm.getGobutton().equals("3")) {
				// cSearch.setIdCountry(aForm.getIdCountry());
				cSearch.setIdNO(aForm.getIdNO());
			}
			else if (aForm.getGobutton().equals("4")) {
				cSearch.setDocBarCode(aForm.getDocBarCode());
			}
			else {
				DefaultLogger.debug(this, "Empty criteria !");
			}

			String nItemsStr = PropertyManager.getValue("customer.pagination.nitems");
			int nItems = 20;
			if (null != nItemsStr) {
				try {
					nItems = Integer.parseInt(nItemsStr);
				}
				catch (NumberFormatException e) {
					nItems = 20;
				}
			}
			if (aForm.getNumItems() > 10) {
				cSearch.setNItems(aForm.getNumItems());
			}
			else {
				cSearch.setNItems(nItems);
			}

			cSearch.setStartIndex(aForm.getStartIndex());

			return cSearch;
		}
		else {
			return null;
		}
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		CustodianSearchForm aForm = (CustodianSearchForm) cForm;
		if (obj != null) {
			SearchResult sr = (SearchResult) obj;
			aForm.setCurrentIndex(sr.getCurrentIndex());
			aForm.setNumItems(sr.getNItems());
			// aForm.setStartIndex(sr.getStartIndex());

			aForm.setSearchResult(sr.getResultList());
		}
		else {
			aForm.setSearchResult(null);
			aForm.setCurrentIndex(0);
			aForm.setStartIndex(0);
			aForm.setNumItems(0);
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
