package com.integrosys.cms.ui.collateral.pledgor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.CollateralForm;

public class PledgorRemoveMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		CollateralForm form = (CollateralForm) cForm;
		List removeList = new ArrayList();
		if (form.getDeletePledgor() != null) {
			for (int i = 0; i < form.getDeletePledgor().length; i++) {
				removeList.add(form.getDeletePledgor()[i]);
			}
		}
		return removeList;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		// TODO Auto-generated method stub
		return null;
	}

}
