/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/list/SubLimitTypeListForm.java,v 1.1 2005/10/06 06:04:01 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.list;

import java.io.Serializable;

import com.integrosys.cms.ui.commodityglobal.sublimittype.SLTUIConstants;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimittype.list.SubLimitTypeItemForm
 *      .java
 */
public class SubLimitTypeListForm extends TrxContextForm implements Serializable {

	private String[] chkDeletes;

	/**
	 * @return Returns the chkDeletes.
	 */
	public String[] getChkDeletes() {
		return chkDeletes;
	}

	/**
	 * @param chkDeletes The chkDeletes to set.
	 */
	public void setChkDeletes(String[] chkDeletes) {
		this.chkDeletes = chkDeletes;
	}

	public String[][] getMapper() {
		String[][] input = { { SLTUIConstants.AN_OB_TRX_CONTEXT, SLTUIConstants.CN_TRX_CONTEXT_MAPPER },
				{ SLTUIConstants.AN_OB_MAP, SLTUIConstants.CN_SLT_LIST_MAPPER } };
		return input;
	}
}
