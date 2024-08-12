package com.integrosys.cms.app.checklist.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Nov 17, 2005 Time: 7:29:15 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EBStgDocumentshareBean extends EBDocumentshareBean {

	public EBStgDocumentshareBean() {
	}

	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_STAGING_CHECKLIST_ITEM_SHARE;
	}

}
