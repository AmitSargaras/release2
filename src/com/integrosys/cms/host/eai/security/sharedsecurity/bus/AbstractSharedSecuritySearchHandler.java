package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.ICollateralSearchResult;
import com.integrosys.cms.app.collateral.bus.OBCollateralSearchResult;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;

/**
 * Abstract implementation of the shared security search handler to do the
 * preparation of the general data, let sub class to do the actual sub type
 * casting or searching.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class AbstractSharedSecuritySearchHandler implements SharedSecuritySearchHandler {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ICollateralDAO collateralJdbcDao;

	private ISecurityDao securityDao;

	public ICollateralDAO getCollateralJdbcDao() {
		return collateralJdbcDao;
	}

	public ISecurityDao getSecurityDao() {
		return securityDao;
	}

	public void setCollateralJdbcDao(ICollateralDAO collateralJdbcDao) {
		this.collateralJdbcDao = collateralJdbcDao;
	}

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public final SharedSecurityResultItem[] searchAndGenerateSharedSecurity(SharedSecuritySearch searchCriteria) {
		CollateralSearchCriteria collateralSearchCriteria = prepareCollateralSearchCriteria(searchCriteria);

		SearchResult searchResult = getCollateralJdbcDao().searchCollateral(collateralSearchCriteria);
		Collection collateralSearchResults = searchResult.getResultList();

		logger.info("Number of search result ["
				+ ((collateralSearchResults != null) ? collateralSearchResults.size() : 0) + "] for criteria ["
				+ collateralSearchCriteria + "]");

		if (collateralSearchResults != null && !collateralSearchResults.isEmpty()) {
			return generateSharedSecurityResult(collateralSearchResults);
		}

		return new SharedSecurityResultItem[0];
	}

	/**
	 * Generate result items required to sent back to client.
	 * @param collateralSearchResults a collection of
	 *        <tt>OBCollateralSearchResult</tt>
	 * @return array of result items.
	 */
	protected final SharedSecurityResultItem[] generateSharedSecurityResult(Collection collateralSearchResults) {
		SharedSecurityResultItem items[] = new SharedSecurityResultItem[0];

		for (Iterator itr = collateralSearchResults.iterator(); itr.hasNext();) {
			OBCollateralSearchResult searchResult = (OBCollateralSearchResult) itr.next();

			long cmsCollateralId = searchResult.getCollateralID();
			ICollateralPledgor[] pledgors = getCollateralJdbcDao().getCollateralPledgors(cmsCollateralId);
			if (pledgors.length > 0) {
				for (int i = 0; i < pledgors.length; i++) {
					SharedSecurityResultItem item = doGenerateSharedSecurityResult(searchResult, pledgors[i]);
					items = (SharedSecurityResultItem[]) ArrayUtils.add(items, item);
				}
			}
			else {
				SharedSecurityResultItem item = doGenerateSharedSecurityResult(searchResult, null);
				items = (SharedSecurityResultItem[]) ArrayUtils.add(items, item);
			}
		}

		return items;
	}

	protected final SharedSecurityResultItem doGenerateSharedSecurityResult(OBCollateralSearchResult searchResult,
			ICollateralPledgor pledgor) {
		SharedSecurityResultItem item = new SharedSecurityResultItem();
		item.setCmsSecurityId(searchResult.getCollateralID());
		item.setCountry(searchResult.getSecurityLocation().getCountryCode());

		if (pledgor != null) {
			item.setPledgorLegalName(pledgor.getPledgorName());
			item.setIdNumber(pledgor.getPlgIdNumText());
		}

		doPopulateDetailItem(item, searchResult);

		return item;
	}

	/**
	 * Based on the shared security search criteria, to construct a collateral
	 * search criteria which is ready to interface with <tt>ICollateralDAO</tt>
	 * @param searchCriteria shared security search criteria, must not be null
	 * @return collateral search criteria ready to interface with
	 *         <tt>ICollateralDAO</tt>
	 */
	protected final CollateralSearchCriteria prepareCollateralSearchCriteria(SharedSecuritySearch searchCriteria) {
		CollateralSearchCriteria criteria = new CollateralSearchCriteria();
		criteria.setAdvanceSearch(true);
		criteria.setSecurityType(searchCriteria.getSecurityType().getStandardCodeValue());
		criteria.setSecuritySubType(searchCriteria.getSecuritySubType().getStandardCodeValue());
		criteria.setCustomerName(searchCriteria.getPledgorLegalName());
		criteria.setIdNO(searchCriteria.getIdNumber());
		criteria.setSecurityLoc(searchCriteria.getCountry());
		criteria.setRequiredPagination(false);

		doPrepareCollateralSearchCriteria(criteria, searchCriteria);

		return criteria;
	}

	/**
	 * Fill in the security subtype specific search criteria into the collateral
	 * search criteria supplied.
	 * @param criteria the collateral search criteria to be filled in
	 * @param searchCriteria the search criteria having security subtype
	 *        specific info.
	 */
	protected abstract void doPrepareCollateralSearchCriteria(CollateralSearchCriteria criteria,
			SharedSecuritySearch searchCriteria);

	/**
	 * Populate the item from the collateral search result object to the shared
	 * security result. collateral search result will only have the basic
	 * information, sub type info need subclass to do another inquiry to
	 * retrieve.
	 * 
	 * @param result the share security result item to be manipulated
	 * @param security the collateral search result
	 */
	protected abstract void doPopulateDetailItem(SharedSecurityResultItem result, ICollateralSearchResult security);
}
