package com.integrosys.cms.app.tatduration.bus;

import java.util.Set;

import org.apache.commons.lang.Validate;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Sep 1, 2008 Time: 5:45:31 PM To
 * change this template use File | Settings | File Templates.
 */
public class TatParamBusManagerImpl extends AbstractTatParamBusManager 
{

	// **************** Abstract Methods ******************
	public String getEntityName() 
	{
		return ITatParamDAO.ACTUAL_TAT_PARAM;
	}
	
	public ITatParam getTatParam(Long tatParamId) throws TatParamException
	{
		return getTatParamDAO().getTatParam(getEntityName(), tatParamId);
	}

	public ITatParam updateToWorkingCopy(ITatParam workingCopy, ITatParam imageCopy) throws TatParamException 
	{
		Validate.notNull(imageCopy, "'imageCopy' must not be null");
		boolean isNullWorkingCopy = (workingCopy == null);

		Set replicatedTatParamItemListSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getTatParamItemList(), new String[] { "tatParamItemId" });

		ITatParam updatedTatParam;
		if (isNullWorkingCopy)
		{
			workingCopy = new OBTatParam();
			AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "tatParamId", "tatParamItemList", "versionTime" });
			workingCopy.setTatParamItemList(replicatedTatParamItemListSet);
			updatedTatParam = create(workingCopy);

		}
		else {

			Set mergedParamItemListSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(
					workingCopy.getTatParamItemList(), replicatedTatParamItemListSet, new String[] { "cmsRefID" },
					new String[] { "tatParamItemId" });

			workingCopy.getTatParamItemList().clear();
			if (mergedParamItemListSet != null) 
			{
				workingCopy.getTatParamItemList().addAll(mergedParamItemListSet);
			}

			AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "tatParamId", "tatParamItemList", "versionTime" });
			updatedTatParam = update(workingCopy);
		}

		return updatedTatParam;
	}

}
