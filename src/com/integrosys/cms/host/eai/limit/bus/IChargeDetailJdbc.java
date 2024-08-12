package com.integrosys.cms.host.eai.limit.bus;

import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

import com.integrosys.cms.host.eai.limit.LimitProfileMismatchException;
import com.integrosys.cms.host.eai.limit.NoSuchLimitSecMapException;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;

/**
 * Jdbc call to persistent storage to deal with limit sec map, limit charge map
 * 
 * @author Chong Jun Yong
 * @since 13.08.2008
 */
public interface IChargeDetailJdbc {
	/**
	 * @param limitProfileId cms limit profile id
	 * @return a list of limit security map belong the limit profile
	 */
	public LimitSecurityMap[] getLimitSecurityMapByLimitProfileId(long limitProfileId);

	/**
	 * @param limitProfileId cms limit profile id
	 * @return list of charge id of limit security map belong to the limit
	 *         profile
	 */
	public Long[] getLimitSecurityMapIdByLimitProfileId(long limitProfileId);

	/**
	 * @param limitProfileId cms limit profile id
	 * @return list of limit charge map that belong to the limit profile
	 */
	public LimitChargeMap[] getLimitChargeMapByLimitProfileId(long limitProfileId);

	public Long getCmsChargeDetailIdByOldCmsChargeDetailId(long chargeDetailId);

	public String getSecurityCurrencyCodeByCmsSecurityId(long cmsSecurityId);

	/**
	 * Retrieve CMS Security Id based on the LOS Security Provided in the Charge
	 * Detail. This search could happend when the Limit Security Map is not
	 * created at the first place. So rather throw
	 * <code>NoSuchSecurityException</code>, in later stage throw
	 * <code>NoSuchLimitSecMapException</code> is more meaningful.
	 * @param losSecurityId LOS Security Id
	 * @param sourceId Source Id
	 * @return CMS Security internal key
	 * @throws IncorrectResultSizeDataAccessException if there are more than 1
	 *         record belong to the LOS Security Id and Source Id provided.
	 * @throws NoSuchSecurityException if Security of LOS Security Id provided
	 *         is not created.
	 */
	public long getCmsSecurityIdByLosSecurityId(String losSecurityId, String sourceId) throws NoSuchSecurityException;

	/**
	 * Retrieve List of Staging Charge Detail key by providing the Actual Charge
	 * Detail key. only the latest staging records will be retrieved.
	 * 
	 * @param actualChargeDetailIds List of Actual Charge Detail key
	 * @return List of Staging Charge Detail key
	 */
	public List retrieveStageChargeDetailIdListByActualChargeDetailIdList(List actualChargeDetailIds);

	/**
	 * Retrieve charge id which is the key of the limit security map, provided
	 * the CMS limit, collateral, AA internal key.
	 * 
	 * @param cmsLimitId CMS Limit internal key
	 * @param cmsSecurityId CMS Collateral internal key
	 * @param cmsLimitProfileId CMS AA internal key
	 * @param isActual to indicate whether this is from actual or staging table
	 * @return the key of the limit security map pertaining to the info supplied
	 * @throws NoSuchLimitSecMapException if the for this information, there is
	 *         no limit security map found
	 * @throws LimitProfileMismatchException if the CMS Limit internal key
	 *         provided is actually belong to another AA but not the AA with CMS
	 *         AA internal key provided.
	 */
	public long retrieveChargeIdByCmsLimitIdAndCmsSecurityIdAndCmsLimitProfileId(long cmsLimitId, long cmsSecurityId,
			long cmsLimitProfileId, boolean isActual) throws NoSuchLimitSecMapException, LimitProfileMismatchException;

	/**
	 * Retrieve the latest staging charge detail key providing the actual charge
	 * detail key
	 * 
	 * @param actualChargeDetailId actual charge detail key
	 * @return latest staging charge detail key
	 */
	public long retrieveStageChargeDetailIdByActualChargeDetailId(long actualChargeDetailId);
}
