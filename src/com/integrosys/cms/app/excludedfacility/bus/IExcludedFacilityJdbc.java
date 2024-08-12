package com.integrosys.cms.app.excludedfacility.bus;

import com.integrosys.base.businfra.search.SearchResult;

public interface IExcludedFacilityJdbc {
	
	SearchResult getAllExcludedFacility()throws ExcludedFacilityException;
}
