package com.integrosys.cms.app.tatdoc.bus;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;

/**
 * @author Cynthia Zhou
 * @since Sep 1, 2008
 */
public class TatDocBusManagerStagingImpl extends AbstractTatDocBusManager {

	public ITatDoc create(ITatDoc tatDoc) throws TatDocException {
		tatDoc = getTatDocDAO().create(getEntityName(), tatDoc);

		copyPrimaryKeyToReferenceId(tatDoc);

		return getTatDocDAO().update(getEntityName(), tatDoc);
	}

	public ITatDoc update(ITatDoc tatDoc) throws TatDocException {
		tatDoc = getTatDocDAO().update(getEntityName(), tatDoc);

		copyPrimaryKeyToReferenceId(tatDoc);

		return getTatDocDAO().update(getEntityName(), tatDoc);
	}

	protected void copyPrimaryKeyToReferenceId(ITatDoc tatDoc) {
		// Setting the reference ID
		Set updateDraftListRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(tatDoc
				.getDraftListSet(), "referenceID", Long.class, "draftID");

		if (tatDoc.getDraftListSet() != null && updateDraftListRefSet != null) {
			tatDoc.getDraftListSet().clear();
			tatDoc.getDraftListSet().addAll(updateDraftListRefSet);
		}
	}

	public String getEntityName() {
		return ITatDocDAO.STAGE_TAT_DOC;
	}

	public ITatDoc updateToWorkingCopy(ITatDoc workingCopy, ITatDoc imageCopy) throws TatDocException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented staging bus manager");
	}

}
