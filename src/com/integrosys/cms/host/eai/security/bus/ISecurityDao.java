package com.integrosys.cms.host.eai.security.bus;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.integrosys.cms.host.eai.core.IPersistentDao;

/**
 * Persistent Dao used for security.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface ISecurityDao extends IPersistentDao {

	/** entity name for trade in information in actual */
	public static final String ENTITY_NAME_ACTUAL_TRADE_IN = "actualTradeIn";

	/** entity name for trade in information in staging */
	public static final String ENTITY_NAME_STAGE_TRADE_IN = "stageTradeIn";

	/**
	 * To search collateral provided the security [parameter name, value] pair
	 * map and pledgor [parameter name, value] pair map.
	 * 
	 * @param securityParameters key is the parameter name, value is the value
	 *        for the parameter, belong to the security
	 * @param pledgorParameters key is the parameter name, value is the value
	 *        for the parameter, belong to the pledgor
	 * @return a list of security message body, contain the approved security
	 *         and the pledgor (repeated)
	 * @see com.integrosys.cms.host.eai.security.SecurityMessageBody
	 */
	public List searchCollateralByCollateralAndPledgor(Map securityParameters, Map pledgorParameters);

	/**
	 * To search a list of property security using the list of the cms
	 * collateral ids and property [parameter name, value] pair map
	 * 
	 * @param cmsCollateralIdList list of cms collateral ids, can be null if
	 *        just wan to match others parameter
	 * @param propertyParameters key is the parameter name, value is the value
	 *        for the parameter, belong to the property security
	 * @return a list of property security matched the criteria passed in
	 * @see com.integrosys.cms.host.eai.security.bus.property.PropertySecurity
	 */
	public List searchPropertyCollaterals(List cmsCollateralIdList, Map propertyParameters);

	/**
	 * To retrieve list of pledgor based on the cms pledgor ids given.
	 * 
	 * @param cmsPledgorIds a collection of cms pledgor ids.
	 * @return list of pledgors or empty list if not matched.
	 */
	public List retrievePledgorsByCmsPledgorIds(Collection cmsPledgorIds);

	/**
	 * Based on the source security id, and source id, to retrieve a list of
	 * share security results which having CMS collateral key to process
	 * further.
	 * @param sourceSecurityId source security id
	 * @param sourceId source id of the securitys
	 * @return list of shared security in CMS
	 */
	public List retrieveSharedSecurityListBySourceSecurityId(String sourceSecurityId, String sourceId);

	/**
	 * To retrieve collateral parameter using the collateral subtype and country
	 * code provided. Both information must be provided.
	 * @param subType collateral subtype id, AB100, PT700, etc.
	 * @param countryCode country code of the collateral, MY, SG, etc.
	 * @return the collateral parameter of the collateral subtype and country.
	 */
	public CollateralParameter findCollateralParameterBySubTypeAndCountryCode(String subType, String countryCode);

	/**
	 * Find the security source id if cms security id is provided from the
	 * source.
	 * @param cmsSecurityId cms security id
	 * @return the source id of the security of cms security id provided.
	 */
	public String findSecuritySourceIdByCmsSecurityId(final long cmsSecurityId);

	public List retrieveTradeInInformationByCmsSecurityId(long securityId, boolean isActual);
	
	public String findSecuritySubTypeIdByCmsSecurityId(final long cmsSecurityId);
	
	public Object findSecurityByMappingId(final long mappingId);

}
