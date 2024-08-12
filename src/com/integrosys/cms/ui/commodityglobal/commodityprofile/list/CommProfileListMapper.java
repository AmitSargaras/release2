/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/list/CommProfileListMapper.java,v 1.2 2004/06/04 05:11:33 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.list;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:33 $ Tag: $Name: $
 */

public class CommProfileListMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		CommProfileListForm aForm = (CommProfileListForm) cForm;

		if (aForm.getEvent().equals(CommProfileListAction.EVENT_DELETE)) {
			IProfileTrxValue trxValue = (IProfileTrxValue) inputs.get("commProfileTrxValue");
			IProfile[] staging = trxValue.getStagingProfile();

			String[] chkDelete = aForm.getChkDeletes();

			if (chkDelete != null) {
				if (chkDelete.length <= staging.length) {
					int numDelete = 0;
					for (int i = 0; i < chkDelete.length; i++) {
						if (Integer.parseInt(chkDelete[i]) < staging.length) {
							numDelete++;
						}
					}
					if (numDelete != 0) {
						IProfile[] newList = new IProfile[staging.length - numDelete];
						int i = 0, j = 0;
						while (i < staging.length) {
							if ((j < chkDelete.length) && (Integer.parseInt(chkDelete[j]) == i)) {
								j++;
							}
							else {
								newList[i - j] = staging[i];
							}
							i++;
						}
						trxValue.setStagingProfile(newList);
					}
				}
			}
			return trxValue;
		}

		return inputs;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		CommProfileListForm aForm = (CommProfileListForm) cForm;

		aForm.setChkDeletes(new String[0]);

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "commProfileTrxValue",
				"com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue", SERVICE_SCOPE }, });
	}
}
