/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemListMapper.java,v 1.7 2005/11/13 12:06:04 jtan Exp $
 */

package com.integrosys.cms.ui.diaryitem;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This mapper is responsible for mapping objects between the action form html
 * attributes to that of the business value object and vice versa. However, the
 * search results are mainly for pagination purposes.
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/11/13 12:06:04 $ Tag: $Name: $
 */
public class DiaryItemListMapper extends AbstractCommonMapper {

	public static String NO_FILTER = "All";

	public static String NO_SELECT = "no_select";

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this, "Entering method DiaryItemListMapper.mapFormToOB");

		DiaryItemForm form = (DiaryItemForm) cForm;
		DiaryItemSearchCriteria criteria = new DiaryItemSearchCriteria();
		ITeam team = (ITeam) inputs.get(IGlobalConstant.USER_TEAM);

		criteria.setTeam(team);
		criteria.setTeamTypeID(team.getTeamType().getTeamTypeID());
		criteria.setStartIndex(form.getStartIndex());
		criteria.setNItems(getDefaultPaginationCount());
		criteria.setCustomerIndex(form.getCustomerIndex());
		criteria.setAllowedCountries(team.getCountryCodes());
		criteria.setCountryFilter(form.getCountryFilter());

		if (form.getStartExpiryDate() != null) {
			criteria.setStartExpDate(DateUtil.convertDate(form.getStartExpiryDate()));
		}
		if (form.getEndExpiryDate() != null) {
			criteria.setEndExpDate(DateUtil.convertDate(form.getEndExpiryDate()));
		}

		return criteria;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam",
				GLOBAL_SCOPE }, });
	}

	/**
	 * helper method to get default pagination count from property file
	 * @return int - pagination count
	 */
	public static int getDefaultPaginationCount() {
		DefaultLogger.debug(DiaryItemListMapper.class.getName(), "Entering method getDefaultPaginationCount ");
		/**
		 * key for looking up pagination setting in property file
		 */
		final String PAGINATION_KEY = "customer.pagination.nitems";

		String noofItem = readPropertyFile(PAGINATION_KEY);

		if (noofItem == null) {
			throw new RuntimeException("Unable to obtain value from property file");
		}
		return Integer.parseInt(noofItem);
	}

	/**
	 * looks up entries in property file
	 * @param key
	 * @return String - value in key
	 */
	private static String readPropertyFile(String key) {
		if (key == null) {
			return null;
		}
		String retVal = PropertyManager.getValue(key).trim();
		return retVal;
	}

	public CommonForm mapOBToForm(CommonForm commonForm, Object o, HashMap hashMap) throws MapperException {
		/**
		 * No implementation required
		 */
		return commonForm;
	}

}
