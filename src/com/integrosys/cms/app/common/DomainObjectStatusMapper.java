package com.integrosys.cms.app.common;

import java.util.Map;

/**
 * Provided the key(s) of the domain object, to come out the status of the
 * object and also the stp ready indicator
 * 
 * @author Chong Jun Yong
 * 
 */
public interface DomainObjectStatusMapper {
	/**
	 * To retrieve the domain object status provided the key of the object
	 * 
	 * @param key the key of the domain object
	 * @return domain object status for the object of the provided key.
	 */
	public String mapStatus(Long key);

	/**
	 * To retrieve the domain object status map, which key is the key of the
	 * object, value is the instance of {@link StpTrxStatusReadyIndicator}
	 * 
	 * @param keys list of key
	 * @return map with key is the key of the object, value is the instance of
	 *         {@link StpTrxStatusReadyIndicator}
	 */
	public Map mapStatus(Long[] keys);
}
