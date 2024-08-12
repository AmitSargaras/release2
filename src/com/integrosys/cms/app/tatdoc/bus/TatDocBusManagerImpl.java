package com.integrosys.cms.app.tatdoc.bus;

import java.util.Set;

import org.apache.commons.lang.Validate;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Sep 1, 2008 Time: 5:45:31 PM To
 * change this template use File | Settings | File Templates.
 */
public class TatDocBusManagerImpl extends AbstractTatDocBusManager {

	// **************** Abstract Methods ******************
	public String getEntityName() {
		return ITatDocDAO.ACTUAL_TAT_DOC;
	}

	public ITatDoc updateToWorkingCopy(ITatDoc workingCopy, ITatDoc imageCopy) throws TatDocException {
		Validate.notNull(imageCopy, "'imageCopy' must not be null");
		boolean isNullWorkingCopy = (workingCopy == null);

		Set replicatedStageDraftListSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getDraftListSet(), new String[] { "draftID" });

		ITatDoc updatedTatDoc;
		if (isNullWorkingCopy) {
			workingCopy = new OBTatDoc();
			AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "TatDocID", "DraftListSet", "DraftList",
					"VersionTime" });
			workingCopy.setDraftListSet(replicatedStageDraftListSet);
			updatedTatDoc = create(workingCopy);

		}
		else {

			Set mergedDraftListSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy
					.getDraftListSet(), replicatedStageDraftListSet, new String[] { "referenceID" },
					new String[] { "draftID" });

			workingCopy.getDraftListSet().clear();
			if (mergedDraftListSet != null) {
				workingCopy.getDraftListSet().addAll(mergedDraftListSet);
			}

			AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "TatDocID", "DraftListSet", "DraftList",
					"VersionTime" });
			updatedTatDoc = update(workingCopy);

		}

		return updatedTatDoc;
	}
}
