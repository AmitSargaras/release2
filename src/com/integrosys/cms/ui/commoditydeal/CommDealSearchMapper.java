/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommDealSearchMapper.java,v 1.3 2004/06/07 03:34:31 pooja Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;

/**
 * Description
 * 
 * @author $Author: pooja $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/07 03:34:31 $ Tag: $Name: $
 */
public class CommDealSearchMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CommDealSearchMapper() {
		DefaultLogger.debug(this, "Inside constructor");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {});
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
		CommDealSearchForm aForm = (CommDealSearchForm) cForm;
		CommodityDealSearchCriteria cSearch = new CommodityDealSearchCriteria();
		if ((aForm.getDealNo() != null) && !aForm.getDealNo().equals("")) {
			cSearch.setDealNo(aForm.getDealNo());
		}
		DefaultLogger.debug(this, "Going out of mapForm to ob ");
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
		CommDealSearchForm aForm = (CommDealSearchForm) cForm;
		if (obj != null) {
			SearchResult sr = (SearchResult) obj;
			aForm.setCurrentIndex(sr.getCurrentIndex());
			aForm.setNumItems(sr.getNItems());
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
