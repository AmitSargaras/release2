package com.integrosys.cms.app.excLineforstpsrm.bus;

import com.integrosys.base.businfra.search.SearchResult;

public interface IExcLineForSTPSRMJdbc {
	SearchResult getAll() throws ExcLineForSTPSRMException;
	boolean isExcluded(String lineCode, boolean isCheckedEnabled);
}