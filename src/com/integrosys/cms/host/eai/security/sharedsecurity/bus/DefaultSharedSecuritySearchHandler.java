package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.collateral.bus.ICollateralSearchResult;

/**
 * Implementation of <tt>SharedSecuritySearchHandler</tt> which doesn't populate
 * any more details and also adding anymore search criteria.
 * @author Chong Jun Yong
 * 
 */
public class DefaultSharedSecuritySearchHandler extends AbstractSharedSecuritySearchHandler {

	protected final void doPopulateDetailItem(SharedSecurityResultItem result, ICollateralSearchResult security) {
	}

	protected final void doPrepareCollateralSearchCriteria(CollateralSearchCriteria criteria,
			SharedSecuritySearch searchCriteria) {
	}

}
