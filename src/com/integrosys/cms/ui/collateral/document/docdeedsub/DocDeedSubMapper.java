/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docgencredit/PrepareDocGenCreditCommandHelper.java,v 1.2 2003/07/18 11:11:36 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.document.docdeedsub;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.document.DocumentMapper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/18 11:11:36 $ Tag: $Name: $
 */
public class DocDeedSubMapper extends DocumentMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		Object obj = DocDeedSubMapperHelper.getObject(inputs);
		super.mapFormToOB(cForm, inputs, obj);
		return DocDeedSubMapperHelper.mapFormToOB(cForm, inputs, obj);
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		super.mapOBToForm(cForm, obj, inputs);
		DocDeedSubMapperHelper.mapOBToForm((DocDeedSubForm) cForm, obj, inputs);
		return cForm;
	}
}
