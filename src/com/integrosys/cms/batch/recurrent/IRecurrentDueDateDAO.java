package com.integrosys.cms.batch.recurrent;

import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;

/**
 * Jdbc Dao to retrieve list of recurrent / covenant that required to generate
 * new sub item.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface IRecurrentDueDateDAO {
	/**
	 * Retrieve list of recurrent item that required to generate new sub item
	 * based on the country code supplied
	 * 
	 * @param countryCode country code that credit application belongs to
	 * @return recurrent items
	 */
	public IRecurrentCheckListItem[] getRecurrentCheckListItemList(String countryCode);

	/**
	 * Retrieve list of covenant that required to generate new sub item based on
	 * the country code supplied
	 * 
	 * @param countryCode country code that credit application belongs to
	 * @return covenant items
	 */
	public IConvenant[] getCovenantList(String countryCode);
}
