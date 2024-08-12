package com.integrosys.cms.app.tatduration.bus;


/**
 * @author Clarence Kwok
 * @since Sep 1, 2008
 */
public class TatParamBusManagerStagingImpl extends AbstractTatParamBusManager {

	public ITatParam create(ITatParam tatParam) throws TatParamException 
	{
		tatParam = getTatParamDAO().create(getEntityName(), tatParam);
		TatParamReplicationUtils.copyStagingPrimaryKeyToCmsRefId(tatParam);
		return getTatParamDAO().update(getEntityName(), tatParam);
	}

	public ITatParam update(ITatParam tatParam) throws TatParamException
	{
		tatParam = getTatParamDAO().update(getEntityName(), tatParam);
		TatParamReplicationUtils.copyStagingPrimaryKeyToCmsRefId(tatParam);
		return getTatParamDAO().update(getEntityName(), tatParam);
	}

	/*public void copyPrimaryKeyToCmsRefId(ITatParam tatParam)
	{
		// Setting the reference ID
		Set updateDraftListRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey
		(tatParam.getTatParamItemList(), "cmsRefID", Long.class, "tatParamItemId");

		if (tatParam.getTatParamItemList() != null && updateDraftListRefSet != null)
		{
			tatParam.getTatParamItemList().clear();
			tatParam.getTatParamItemList().addAll(updateDraftListRefSet);
		}
	}*/
	

	public String getEntityName() 
	{
		return ITatParamDAO.STAGE_TAT_PARAM;
	}
	
	public ITatParam getTatParam(Long tatParamId) throws TatParamException
	{
		return getTatParamDAO().getTatParam(getEntityName(), tatParamId);
	}

	public ITatParam updateToWorkingCopy(ITatParam workingCopy, ITatParam imageCopy) throws TatParamException 
	{
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented staging bus manager");
	}

}
