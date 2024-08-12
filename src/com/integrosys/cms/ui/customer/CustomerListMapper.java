/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/CustomerListMapper.java,v 1.8 2005/09/02 07:03:34 hshii Exp $
 */

package com.integrosys.cms.ui.customer;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;

/**
 * Mapper class is used to map form values to objects and vice versa
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/09/02 07:03:34 $ Tag: $Name: $
 */
public class CustomerListMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CustomerListMapper() {
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
		CustomerSearchForm aForm = (CustomerSearchForm) cForm;
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
				 cSearch.setFacilitySystem(aForm.getFacilitySystem());
				 cSearch.setFacilitySystemID(aForm.getFacilitySystemID());
				cSearch.setIdNO(aForm.getIdNO());
			}
			else if (aForm.getGobutton().equals("4")) {
				cSearch.setAaNumber(aForm.getAaNumber());
			}
			else if (aForm.getGobutton().equals("5")) {
				cSearch.setLegalID(aForm.getLegalID());
				cSearch.setLeIDType(aForm.getLeIDType());
				cSearch.setFacilitySystem(aForm.getFacilitySystem());
				 cSearch.setFacilitySystemID(aForm.getFacilitySystemID());
				cSearch.setIdNO(aForm.getIdNO());
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
		CustomerSearchForm aForm = (CustomerSearchForm) cForm;
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
