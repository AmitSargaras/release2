package com.integrosys.cms.app.limit.bus.support;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.integrosys.cms.app.common.DomainObjectStatusMapper;
import com.integrosys.cms.app.common.StpTrxStatusReadyIndicator;
import com.integrosys.cms.app.limit.bus.IFacilityJdbc;

/**
 * <p>
 * Facility status mapper to map the facility transaction status to display
 * status.
 * 
 * <p>
 * Status mapping is configured in {@link #setFacilityTrxStatusDisplayMap(Map)},
 * which key is the transaction status, value is the display status.
 * 
 * @author Chong Jun Yong
 * 
 */
public class FacilityStatusMapper implements DomainObjectStatusMapper {

	private IFacilityJdbc facilityJdbcDao;

	private Map facilityTrxStatusDisplayMap;

	public IFacilityJdbc getFacilityJdbcDao() {
		return facilityJdbcDao;
	}

	public Map getFacilityTrxStatusDisplayMap() {
		return facilityTrxStatusDisplayMap;
	}

	public void setFacilityJdbcDao(IFacilityJdbc facilityJdbcDao) {
		this.facilityJdbcDao = facilityJdbcDao;
	}

	/**
	 * The key is the workflow status (which directly grab from workflow value),
	 * the value is the status supposed to be displayed.
	 * 
	 * @param facilityTrxStatusDisplayMap the map, which key is the workflow
	 *        status, value is the display status
	 */
	public void setFacilityTrxStatusDisplayMap(Map facilityTrxStatusDisplayMap) {
		this.facilityTrxStatusDisplayMap = facilityTrxStatusDisplayMap;
	}

	public String mapStatus(Long key) {
		Map statusMap = mapStatus(new Long[] { key });

		return (String) statusMap.get(key);
	}

	public Map mapStatus(Long[] keys) {
		Map statusMap = getFacilityJdbcDao().retrieveTrxStatusByRefIds(keys);

		Set entrySet = statusMap.entrySet();
		for (Iterator itr = entrySet.iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			StpTrxStatusReadyIndicator facStpTrxStatusReadyIndicator = (StpTrxStatusReadyIndicator) entry.getValue();

			String displayStatus = (String) getFacilityTrxStatusDisplayMap().get(
					facStpTrxStatusReadyIndicator.getTrxStatus());
			facStpTrxStatusReadyIndicator.setTrxStatus(displayStatus);

			entry.setValue(facStpTrxStatusReadyIndicator);
		}

		return statusMap;
	}

}
