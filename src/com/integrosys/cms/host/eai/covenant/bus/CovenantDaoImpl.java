package com.integrosys.cms.host.eai.covenant.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.support.PersistentDaoSupport;

/**
 * 
 * @author Thurein</br>
 * @since 1.1</br>
 * @version 1.0</br>
 * 
 */
public class CovenantDaoImpl extends PersistentDaoSupport implements ICovenantDao {

	public LimitProfile getLimitProfile(String LOSAANumber) {
		Map parameters = new HashMap();
		parameters.put("LOSAANumber", LOSAANumber);

		LimitProfile limitProfile = (LimitProfile) retrieveNonDeletedObjectByParameters(parameters,
				"updateStatusIndicator", LimitProfile.class);

		return limitProfile;
	}

	public SubProfile getSubProfile(String CIFId) {
		Map parameters = new HashMap();
		parameters.put("cifId", CIFId);

		SubProfile subProfile = (SubProfile) retrieveNonDeletedObjectByParameters(parameters, "updateStatusIndicator",
				SubProfile.class);
		return subProfile;
	}

	public RecurrentDoc getRecurrentDoc(long limitProfileID, long subPorfileID, Class recurrentClass) {
		Map parameters = new HashMap();
		parameters.put("CmsLimitProfileID", new Long(limitProfileID));
		parameters.put("CmsSubProfileID", new Long(subPorfileID));

		RecurrentDoc recurrentDoc = null;
		if (recurrentClass == RecurrentDoc.class) {
			recurrentDoc = (RecurrentDoc) retrieveObjectByParameters(parameters, recurrentClass, "actualRecurrent");
		}
		else if (recurrentClass == StageRecurrentDoc.class) {
			recurrentDoc = (RecurrentDoc) retrieveObjectByParameters(parameters, recurrentClass, "stageRecurrent");
		}

		return recurrentDoc;
	}

	public CovenantItem getConvenantItem(long CMSCovenantItemID, long recurrentDocId, Class covClass) {
		Map parameters = new HashMap();
		if (covClass.equals(CovenantItem.class)) {
			parameters.put("CMSCovenantItemID", new Long(CMSCovenantItemID));
			parameters.put("recurrentDocId", new Long(recurrentDocId));
		}
		else if (covClass.equals(StageConvenantItem.class)) {
			parameters.put("CMSconvenantItemRefID", new Long(CMSCovenantItemID));
			parameters.put("recurrentDocId", new Long(recurrentDocId));
		}

		CovenantItem covItem = (CovenantItem) retrieveObjectByParameters(parameters, covClass);
		return covItem;
	}

	public List getConvenantItemByRecurrentDocID(long recurrentDocID, Class covClass) {
		Map parameters = new HashMap();
		parameters.put("recurrentDocId", new Long(recurrentDocID));

		List covItems = retrieveObjectsListByParameters(parameters, covClass);
		return covItems;

	}

}
