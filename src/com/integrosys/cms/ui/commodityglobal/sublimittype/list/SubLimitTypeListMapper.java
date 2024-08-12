/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/list/SubLimitTypeListMapper.java,v 1.1 2005/10/06 06:04:01 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-26
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimittype.list.SubLimitTypeMapper
 *      .java
 */
public class SubLimitTypeListMapper extends AbstractCommonMapper {

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.AbstractCommonMapper#mapOBToForm(com
	 * .integrosys.base.uiinfra.common.CommonForm, java.lang.Object,
	 * java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm commonform, Object obj, HashMap hashmap) throws MapperException {
		SubLimitTypeListForm sltForm = (SubLimitTypeListForm) commonform;
		sltForm.setChkDeletes(new String[0]);
		return sltForm;
	}

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.AbstractCommonMapper#mapFormToOB(com
	 * .integrosys.base.uiinfra.common.CommonForm, java.util.HashMap)
	 */
	public Object mapFormToOB(CommonForm commonform, HashMap paramMap) throws MapperException {
		SubLimitTypeListForm sltForm = (SubLimitTypeListForm) commonform;
		String[] chkDelete = sltForm.getChkDeletes();
		DefaultLogger.debug(this, "Num of to be deleted slt : " + (chkDelete == null ? 0 : chkDelete.length));

		HashMap indexMap = new HashMap();
		if (chkDelete != null) {
			for (int index = 0; index < chkDelete.length; index++) {
				indexMap.put(chkDelete[index], chkDelete[index]);
			}
		}
		return indexMap;
	}
}
