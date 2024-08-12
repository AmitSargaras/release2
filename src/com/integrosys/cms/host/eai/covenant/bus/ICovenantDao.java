package com.integrosys.cms.host.eai.covenant.bus;

import java.util.List;

import com.integrosys.cms.host.eai.core.IPersistentDao;
import com.integrosys.cms.host.eai.covenant.NoSuchCovenantException;
import com.integrosys.cms.host.eai.covenant.NoSuchRecurrentDocException;
import com.integrosys.cms.host.eai.customer.NoSuchCustomerException;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.limit.NoSuchLimitProfileException;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;

/**
 * 
 * @author Thurein</br>
 * @version 1.0</br>
 * @since 1.1 </br>All the common database transactions for the eai convenant
 *        module.
 */
public interface ICovenantDao extends IPersistentDao {

	public LimitProfile getLimitProfile(String LOSAANumber);

	public SubProfile getSubProfile(String CIFId);

	public RecurrentDoc getRecurrentDoc(long limitProfileID, long subPorfileID, Class recurrentClass);

	public List getConvenantItemByRecurrentDocID(long recurrentDocID, Class covClass);

	public CovenantItem getConvenantItem(long CMSCovenantItemID, long recurrentDocId, Class covClass);
}
