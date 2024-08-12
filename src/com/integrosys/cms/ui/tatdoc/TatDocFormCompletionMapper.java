package com.integrosys.cms.ui.tatdoc;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

/**
 * Mapper between the html form and object required inside the web command. This
 * mapper mainly to map the information required for document completion check.
 * 
 * @author Chong Jun Yong
 * 
 */
public class TatDocFormCompletionMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		TatDocForm form = (TatDocForm) cForm;
		return form.getOldDisDocComDateExit();
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		return null;
	}
}
