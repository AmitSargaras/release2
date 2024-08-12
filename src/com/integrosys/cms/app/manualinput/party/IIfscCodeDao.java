package com.integrosys.cms.app.manualinput.party;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.customer.bus.IIfscMethod;

public interface IIfscCodeDao {
	
	static final String STAGE_IFSC_CODE_NAME = "stageIfsccode";
	static final String ACTUAL_IFSC_CODE_NAME = "actualIfsccode";

	IIfscMethod createStageIfscCode(IIfscMethod ifscList);
	void createActualIfscCode(String[] ifscList,String referenceId);
	public SearchResult getIfscCodeList(String custID);
	public SearchResult getStageIfscCodeList(String custID);
	public void disableActualIfscCode(String referenceId);

}
