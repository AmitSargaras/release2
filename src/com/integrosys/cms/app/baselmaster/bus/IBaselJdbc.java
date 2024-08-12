package com.integrosys.cms.app.baselmaster.bus;

import com.integrosys.base.businfra.search.SearchResult;

public interface IBaselJdbc {
	
	public SearchResult getAllActualBasel() throws BaselMasterException ;
	
	public SearchResult getAllActualCommon() throws BaselMasterException ;
	
	

}
