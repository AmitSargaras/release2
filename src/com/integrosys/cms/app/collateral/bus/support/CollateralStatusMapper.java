package com.integrosys.cms.app.collateral.bus.support;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.integrosys.cms.app.collateral.trx.ICollateralTrxDAO;
import com.integrosys.cms.app.common.DomainObjectStatusMapper;
import com.integrosys.cms.app.common.StpTrxStatusReadyIndicator;

/**
 * <p>
 * Collateral Status Mapper to map the collateral transaction status to display
 * status, and also for the stp ready indicator
 * 
 * <p>
 * The status mapping is configured in
 * {@link #setCollateralTrxStatusDisplayMap(Map)}, which key is the transaction
 * status, value is the display status.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CollateralStatusMapper implements DomainObjectStatusMapper {

	private ICollateralTrxDAO collateralTrxJdbcDao;

	private Map collateralTrxStatusDisplayMap;

	public ICollateralTrxDAO getCollateralTrxJdbcDao() {
		return collateralTrxJdbcDao;
	}

	public Map getCollateralTrxStatusDisplayMap() {
		return collateralTrxStatusDisplayMap;
	}

	public void setCollateralTrxJdbcDao(ICollateralTrxDAO collateralTrxJdbcDao) {
		this.collateralTrxJdbcDao = collateralTrxJdbcDao;
	}

	/**
	 * The key is the workflow status (which directly grab from workflow value),
	 * the value is the status supposed to be displayed.
	 * 
	 * @param collateralTrxStatusDisplayMap the map, which key is the workflow
	 *        status, value is the display status
	 */
	public void setCollateralTrxStatusDisplayMap(Map collateralTrxStatusDisplayMap) {
		this.collateralTrxStatusDisplayMap = collateralTrxStatusDisplayMap;
	}

	public String mapStatus(Long key) {
		Map statusMap = mapStatus(new Long[] { key });

		return (String) statusMap.get(key);
	}

	public Map mapStatus(Long[] keys) {
		Map statusMap = getCollateralTrxJdbcDao().retrieveTrxStatusByRefIds(keys);

		Set entrySet = statusMap.entrySet();
		for (Iterator itr = entrySet.iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			StpTrxStatusReadyIndicator colStpTrxStatusReadyIndicator = (StpTrxStatusReadyIndicator) entry.getValue();

			String displayStatus = (String) getCollateralTrxStatusDisplayMap().get(
					colStpTrxStatusReadyIndicator.getTrxStatus());
			colStpTrxStatusReadyIndicator.setTrxStatus(displayStatus);

			entry.setValue(colStpTrxStatusReadyIndicator);
		}

		return statusMap;
	}

}
