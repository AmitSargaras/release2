/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/CustomerListMapper.java,v 1.8 2005/09/02 07:03:34 hshii Exp $
 */

package com.integrosys.cms.ui.imageTag;

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
 * @author abhijit.rudrakshawar
 */
public class ImageTagListMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public ImageTagListMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

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
	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {
		DefaultLogger.debug(this, "Inside mapForm to ob ");
		ImageTagForm aForm = (ImageTagForm) cForm;
		String event = aForm.getEvent();
		if("checker_process_edit".equalsIgnoreCase(event)){
			CustomerSearchCriteria cSearch = new CustomerSearchCriteria();
			cSearch.setIdNO(aForm.getIdNO());
			

			return cSearch;
		}else{
		if (!ImageTagAction.EVENT_PREPARE.equals(event)) {

			CustomerSearchCriteria cSearch = new CustomerSearchCriteria();
			DefaultLogger.debug(this, "getGobutton value---->"
					+ aForm.getGobutton());
			cSearch.setCustomerName(aForm.getCustomerName());
			cSearch.setAll(aForm.getAll());
			if(aForm.getGobutton()!=null){
			if (aForm.getGobutton().equals("2")) {
//				DefaultLogger.debug(this, "aForm.getLegalID() value---->"
//						+ aForm.getLegalID());
				DefaultLogger.debug(this, "aForm.getLeIDType() value---->"
						+ aForm.getLeIDType());
				cSearch.setLegalID(aForm.getLegalID());
				cSearch.setLeIDType(aForm.getLeIDType());
			} else if (aForm.getGobutton().equals("3")) {
				// cSearch.setIdCountry(aForm.getIdCountry());
				cSearch.setIdNO(aForm.getIdNO());
			} else {
				DefaultLogger.debug(this, "Empty criteria !");
			}
			}
			String nItemsStr = PropertyManager
					.getValue("customer.pagination.nitems");
			int nItems = 20;
			if (null != nItemsStr) {
				try {
					nItems = Integer.parseInt(nItemsStr);
				} catch (NumberFormatException e) {
					nItems = 20;
				}
			}
			if (aForm.getNumItems() > 10) {
				cSearch.setNItems(aForm.getNumItems());
			} else {
				cSearch.setNItems(nItems);
			}

			cSearch.setStartIndex(aForm.getStartIndex());

			return cSearch;
		} else {
			CustomerSearchCriteria cSearch = new CustomerSearchCriteria();
			cSearch.setIdNO(aForm.getIdNO());
			

			return cSearch;
		}
		}
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
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map)
			throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		ImageTagForm aForm = (ImageTagForm) cForm;
		if (obj != null) {
			SearchResult sr = (SearchResult) obj;
			aForm.setCurrentIndex(sr.getCurrentIndex());
			aForm.setNumItems(sr.getNItems());

			aForm.setSearchResult(sr.getResultList());
		} else {
			aForm.setSearchResult(null);
			aForm.setCurrentIndex(0);
			aForm.setStartIndex(0);
			aForm.setNumItems(0);
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
