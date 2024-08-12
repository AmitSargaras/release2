/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/list/SubLimitDeleteMapper.java,v 1.1 2005/10/14 06:31:46 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-27
 * @Tag 
 *      com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitDeleteMapper.
 *      java
 */
public class SubLimitDeleteMapper extends AbstractCommonMapper {

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.AbstractCommonMapper#mapOBToForm(com
	 * .integrosys.base.uiinfra.common.CommonForm, java.lang.Object,
	 * java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm aForm, Object obj, HashMap hashmap) throws MapperException {
		SubLimitListForm sllForm = (SubLimitListForm) aForm;
		HashMap sltMap = (HashMap) obj;
		DefaultLogger.debug(this, "Size of Map : " + (sltMap == null ? 0 : sltMap.size()));
		if ((sltMap != null) && !sltMap.isEmpty()) {
			String[] sltArray = new String[sltMap.size()];
			Iterator iterator = sltMap.values().iterator();
			int index = 0;
			while (iterator.hasNext()) {
				sltArray[index++] = (String) iterator.next();
			}
			sllForm.setChkDeletes(sltArray);
		}
		else {
			sllForm.setChkDeletes(new String[0]);
		}
		return sllForm;
	}

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.AbstractCommonMapper#mapFormToOB(com
	 * .integrosys.base.uiinfra.common.CommonForm, java.util.HashMap)
	 */
	public Object mapFormToOB(CommonForm aForm, HashMap hashmap) throws MapperException {

		// System.out.println(aForm);

		SubLimitListForm sllForm = (SubLimitListForm) aForm;
		String[] chkDelete = sllForm.getChkDeletes();
		DefaultLogger.debug(this, "Num of to be deleted slt : " + (chkDelete == null ? 0 : chkDelete.length));
		HashMap sltMap = new HashMap();
		if (chkDelete != null) {
			for (int index = 0; index < chkDelete.length; index++) {
				DefaultLogger.debug(this, " To delete : " + chkDelete[index]);
				int i = chkDelete[index].indexOf("_");
				String limitID = chkDelete[index].substring(0, i);
				String sltId = chkDelete[index].substring(i + 1);
				ArrayList sltList = (ArrayList) sltMap.get(limitID);
				if (sltList == null) {
					sltList = new ArrayList();
				}
				sltList.add(sltId);
				sltMap.put(limitID, sltList);
			}
		}
		return sltMap;
	}

}
