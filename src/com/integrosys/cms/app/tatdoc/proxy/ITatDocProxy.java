package com.integrosys.cms.app.tatdoc.proxy;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.tatdoc.bus.*;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.TatParamException;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Aug 27, 2008 Time: 4:43:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITatDocProxy {

	/**
	 * Maker creates tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @param tatdoc of ITatDoc type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	public ITatDocTrxValue makerCreateTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue, ITatDoc tatdoc)
			throws TatDocException;

	/**
	 * Maker saves tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @param tatdoc of ITatDoc type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	public ITatDocTrxValue makerSaveTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue, ITatDoc tatdoc)
			throws TatDocException;

	/**
	 * Maker updates tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @param tatdoc of ITatDoc type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	public ITatDocTrxValue makerUpdateTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue, ITatDoc tatdoc)
			throws TatDocException;

	/**
	 * Checker approves tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	public ITatDocTrxValue checkerApproveTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException;

	/**
	 * Checker rejects tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	public ITatDocTrxValue checkerRejectTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException;

	/**
	 * Maker close (rejected) tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	public ITatDocTrxValue makerCloseTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException;

	/**
	 * Maker close (rejected) tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	public ITatDocTrxValue makerCloseUpdateTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException;

	/**
	 * Get the (default) actual tat doc transaction object via limit profile id
	 * @param limitProfileID - Limit Profile ID to retrieve the tat doc trxValue
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	public ITatDoc getTatDocByLimitProfileID(long limitProfileID) throws TatDocException;

	public ITatDocTrxValue getTrxValueByLimitProfileID(long limitProfileID) throws TatDocException;

	public Date getDateOfInstructionToSolicitor(long limitProfileId);

	/**
	 * Get the tat doc transaction object via primary key
	 * @param pk - Primary Key used to retrieve the trxValue
	 * @param from - indicate where the pk coming from (actual or staging)
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	public ITatDocTrxValue getTatDocTrxValue(long pk) throws TatDocException;

	/**
	 * Get the tat doc transaction object via primary key
	 * @param trxID - transaction ID
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	public ITatDocTrxValue getTatDocTrxValueByTrxID(String trxID) throws TatDocException;
	
	public ITatParam getTatParamByApplicationType(String applicationType) throws TatParamException;
	
	public OBTatLimitTrack getTatStageTrackingListByLimitProfileId(long limitProfileId, String applicationType) throws TatDocException;

	public void commitTatTrackingList(OBTatLimitTrack trackOB) throws TatDocException;
	
	public Long getNonWorkingDaysByBranch(String branch, Date startDate, Date endDate) throws TatDocException;
}
