package com.integrosys.cms.app.tatduration.bus;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.tatdoc.bus.TatDocException;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Aug 28, 2008 Time: 9:39:12 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ITatParamBusManager {

	/**
	 * Create a tat document
	 * @param tatDoc - ITatDoc
	 * @return ITatDoc - the tat document being created
	 * @throws TatDocException;
	 */
	public ITatParam create(ITatParam tatParam) throws TatParamException;

	/**
	 * Update a tat document
	 * @param tatDoc - ITatDoc
	 * @return ITatDoc - the tat document being updated
	 * @throws TatDocException
	 */
	public ITatParam update(ITatParam tatParam) throws TatParamException;

	/**
	 * Get a tat document by ID
	 * @param tatDocID - long
	 * @return ITatDoc - the tat document
	 * @throws TatDocException
	 */
//	public ITatParamStage getTatParamStageByID(Long tatParamID) throws TatParamException;

	/**
	 * Get the (default) tat doc transaction object via limit profile id
	 * @param limitProfileID - Limit Profile ID to retrieve the tat doc trxValue
	 * @return ITatDoc - the interface representing the tat document trx obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	
	public ITatParam getTatParamByApplicationType(String appType)throws TatParamException;
	
	public List getTatParamList() throws TatParamException;
	
	public ITatParamItem getTatParamItem(Long tatParamItemId) throws TatParamException;
	
	public ITatParam getTatParam(Long tatParamId) throws TatParamException;

	/**
	 * Update from a image copy to a working copy, will be used for updating
	 * actual copy using staging copy.
	 * 
	 * This normally will get called from transaction engine, operation which is
	 * checker approve update.
	 * 
	 * @param workingCopy working copy, which in cms context is actual copy
	 * @param imageCopy image copy, which in cms context is staging copy
	 * @return the updated working copy
	 * @throws TatDocException
	 */
	public ITatParam updateToWorkingCopy(ITatParam workingCopy, ITatParam imageCopy) throws TatParamException;

	/**
	 * <p>
	 * Insert into pending perfection credit folder if there is <b>no tatDoc</b>
	 * supplied, or the tatDoc supplied doesn't have value from
	 * {@link ITatDoc#getDocCompletionDate()}, (if there is no entry in the
	 * folder for the limit profile supplied).
	 * <p>
	 * Remove the entry from pending perfection credit folder, only if the
	 * tatDoc supplied have the value from
	 * {@link ITatDoc#getDocCompletionDate()}, (also if there entry in the
	 * folder for the limit profile supplied).
	 * @param tatDoc TAT document of the limit profile supplied, can be null if
	 *        there is no tat maintained for insert usage.
	 * @param limitProfile limit profile instance, mainly need the AA's PK, Host
	 *        AA number, and LOS AA number.
	 */
	public void insertOrRemovePendingPerfectionCreditFolder(ITatParam tatParam, ILimitProfile limitProfile);

}
