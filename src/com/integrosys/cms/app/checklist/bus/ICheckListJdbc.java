package com.integrosys.cms.app.checklist.bus;

import java.util.Map;

public interface ICheckListJdbc {
	
	public Map<String, String> getChecklistItemsCollateralType(Long checklistId);

}
