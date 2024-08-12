package com.integrosys.cms.app.collateralrocandinsurance.bus;

import com.integrosys.base.businfra.search.SearchResult;

public interface ICollateralRocJdbc {

	SearchResult getAllCollateralRoc()throws CollateralRocException;
}
