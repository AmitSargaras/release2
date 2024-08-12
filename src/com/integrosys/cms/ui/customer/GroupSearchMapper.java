/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/GroupSearchMapper.java,v 1.1 2003/09/04 09:18:52 lakshman Exp $
 */
package com.integrosys.cms.ui.customer;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.GroupSearchCriteria;

/**
 * Mapper class is used to map form values to objects and vice versa
 * @author $Author: lakshman $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/04 09:18:52 $ Tag: $Name: $
 */
public class GroupSearchMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public GroupSearchMapper() {
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
		GroupSearchForm aForm = (GroupSearchForm) cForm;
		String event = (String) inputs.get("event");
		DefaultLogger.debug(this, aForm.getNumItems() + "");
		DefaultLogger.debug(this, aForm.getStartIndex() + "");
		GroupSearchCriteria cSearch = new GroupSearchCriteria();
		// OBCMSCustomer obCustomer = new OBCMSCustomer();
		// OBCMSLegalEntity obLegalEntity = new OBCMSLegalEntity();
		if ((aForm.getGroupID() != null) && !aForm.getGroupID().equals("")) {
			Long ld = Long.valueOf(aForm.getGroupID());
			if (ld.longValue() > 0) {
				cSearch.setGroupID(aForm.getGroupID());
			}
		}
		cSearch.setGroupName(aForm.getGroupName());
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
		cSearch.setNItems(nItems);

		cSearch.setStartIndex(aForm.getStartIndex());
		return cSearch;
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
		GroupSearchForm aForm = (GroupSearchForm) cForm;
		if (obj != null) {
			SearchResult sr = (SearchResult) obj;
			aForm.setCurrentIndex(sr.getCurrentIndex());
			aForm.setNumItems(sr.getNItems());
			// aForm.setStartIndex(sr.getStartIndex());
			DefaultLogger.debug(this, "Before putting vector result");
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
