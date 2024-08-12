package com.integrosys.cms.ui.todo;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.ui.workspace.WorkspaceForm;

public class ToDoMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		WorkspaceForm wForm = (WorkspaceForm) cForm;
		return wForm.getSelectedItems();
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		WorkspaceForm wForm = (WorkspaceForm) cForm;
		CMSTrxSearchCriteria criteria = (CMSTrxSearchCriteria) obj;

		wForm.setSortBy(criteria.getSortBy());
		wForm.setFilterByType(criteria.getFilterByType());
		wForm.setFilterByValue(criteria.getFilterByValue());

		return wForm;
	}

}