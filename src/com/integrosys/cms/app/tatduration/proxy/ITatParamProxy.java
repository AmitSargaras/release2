package com.integrosys.cms.app.tatduration.proxy;

import java.util.List;

import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.ITatParamItem;
import com.integrosys.cms.app.tatduration.bus.TatParamException;
import com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Aug 27, 2008 Time: 4:43:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITatParamProxy 
{
	/**
	 * Maker creates tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @param tatdoc of ITatDoc type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	/*public ITatDocTrxValue makerCreateTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue, ITatDoc tatdoc)
			throws TatDocException;*/

	/**
	 * Maker saves tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @param tatdoc of ITatDoc type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	/*public ITatDocTrxValue makerSaveTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue, ITatDoc tatdoc)
			throws TatDocException;*/
	
	public ITatParamTrxValue makerSubmitTatParamDuration(ITrxContext ctx, ITatParamTrxValue trxValue, ITatParam tatParam) throws TatParamException;
	
	public ITatParamTrxValue checkerApproveTatParam(ITrxContext ctx, ITatParamTrxValue trxValue) throws TatParamException;
	
	public ITatParamTrxValue checkerRejectTatParam(ITrxContext anITrxContext, ITatParamTrxValue anITatParamTrxValue) throws TatParamException;
	
	public ITatParamTrxValue makerCloseTatParamDuration(ITrxContext anITrxContext, ITatParamTrxValue anITatParamTrxValue) throws TatParamException;
	
	/**
	 * Maker updates tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @param tatdoc of ITatDoc type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	/*public ITatDocTrxValue makerUpdateTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue, ITatDoc tatdoc)
			throws TatDocException;*/

	/**
	 * Checker approves tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
//	public ITatDocTrxValue checkerApproveTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException;

	/**
	 * Checker rejects tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
//	public ITatDocTrxValue checkerRejectTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException;

	/**
	 * Maker close (rejected) tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
//	public ITatDocTrxValue makerCloseTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException;

	/**
	 * Maker close (rejected) tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
//	public ITatDocTrxValue makerCloseUpdateTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException;

	/**
	 * Get the (default) actual tat doc transaction object via limit profile id
	 * @param limitProfileID - Limit Profile ID to retrieve the tat doc trxValue
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
//	public ITatDoc getTatDocByLimitProfileID(long limitProfileID) throws TatDocException;

//	public ITatDocTrxValue getTrxValueByLimitProfileID(long limitProfileID) throws TatDocException;

//	public Date getDateOfInstructionToSolicitor(long limitProfileId);

	/**
	 * Get the tat doc transaction object via primary key
	 * @param pk - Primary Key used to retrieve the trxValue
	 * @param from - indicate where the pk coming from (actual or staging)
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
//	public ITatDocTrxValue getTatDocTrxValue(long pk) throws TatDocException;

	/**
	 * Get the tat doc transaction object via primary key
	 * @param trxID - transaction ID
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
//	public ITatDocTrxValue getTatDocTrxValueByTrxID(String trxID) throws TatDocException;
	
	
	public List getTatParamList() throws TatParamException;
	
	public ITatParam getTatParamByApplicationType(String applicationType) throws TatParamException;
	
	public ITatParamItem getTatParamItem(Long tatParamItemId) throws TatParamException;

	public ITatParam getTatParam(Long tatParamId) throws TatParamException;
	
	public ITatParamTrxValue getTatParamByTrxID(long trxID) throws TatParamException;
	
	public ITatParamTrxValue getTatParamTrxValue(long tatParamId) throws TatParamException;
}
