package com.integrosys.cms.app.bankingArrangementFacExclusion.bus;

import com.integrosys.base.businfra.search.SearchResult;

public interface IBankingArrangementFacExclusionJdbc {
	SearchResult getAll() throws BankingArrangementFacExclusionException;
	boolean isExcluded(String system, String facCat, String facName, boolean isCheckedEnabled);
}