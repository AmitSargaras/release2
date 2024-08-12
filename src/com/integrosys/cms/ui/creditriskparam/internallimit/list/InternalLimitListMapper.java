/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/ExchangeRateListMapper.java,v 1.39 2005/08/05 10:12:13 hshii Exp $
 */
package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.OBInternalLimitParameter;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.39 $
 * @since $Date: 2005/08/05 10:12:13 $ Tag: $Name: $
 */
public class InternalLimitListMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object,
			HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "inside mapOBToForm.");
		InternalLimitListForm form = (InternalLimitListForm) aForm;
		ArrayList entryList = (ArrayList) object;
		if (entryList == null || entryList.size() == 0) {
			DefaultLogger.debug(this, "ob == null.");
			return form;
		}

		int size = entryList.size();
		String description[] = new String[size];
		String ilPercentage[] = new String[size];
		String currency[] = new String[size];
		String capitalFundAmount[] = new String[size];
		String status[] = new String[size];

		for (int index = 0; index < size; index++) {
			IInternalLimitParameter entry = (IInternalLimitParameter) entryList
					.get(index);
			//description[index] = entry.getDescription();
			ilPercentage[index] = String.valueOf(entry
					.getInternalLimitPercentage());
			//currency[index] = entry.getCurrency();
			capitalFundAmount[index] = String.valueOf(entry
					.getCapitalFundAmount());
			status[index] = entry.getStatus();
		}
		form.setDescription(description);
		form.setInternalLimitPercentage(ilPercentage);
		form.setCurrency(currency);
		form.setCapitalFundAmount(capitalFundAmount);
		form.setStatus(status);
		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap)
			throws MapperException {
		DefaultLogger.debug(this, "inside mapFormToOB.");
		InternalLimitListForm form = (InternalLimitListForm) aForm;
		String description[] = form.getDescription();
		String ilPercentage[] = form.getInternalLimitPercentage();
		String currency[] = form.getCurrency();
		String capitalFundAmount[] = form.getCapitalFundAmount();
		String status[] = form.getStatus();

		int length = description == null ? 0 : description.length;

		ArrayList entryList = new ArrayList();
		for (int index = 0; index < length; index++) {
			IInternalLimitParameter entry = new OBInternalLimitParameter();
			//entry.setDescription(description[index]);
			entry.setInternalLimitPercentage(Double
					.parseDouble(ilPercentage[index]));
			//entry.setCurrency(currency[index]);
			entry.setCapitalFundAmount(Double
					.parseDouble(capitalFundAmount[index]));
			entry.setStatus(status[index]);
			entryList.add(entry);
		}
		return entryList;
	}

}
