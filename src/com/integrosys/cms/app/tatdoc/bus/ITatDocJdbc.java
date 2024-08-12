package com.integrosys.cms.app.tatdoc.bus;

import java.util.Date;
import java.util.List;

import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack;

/**
 * @author Cynthia
 * @since Sep 1, 2008
 */
public interface ITatDocJdbc {

	// **************** DAO Methods ******************
	/**
	 * Gets the date of instruction to solicitor from the AA table
	 * @param limitProfileID - limit profile id
	 * @return Date - date of instruction to solicitor
	 */
	public Date getDateOfInstructionToSolicitor(long limitProfileID);

	public String getTrxValueByLimitProfileID(long limitProfileID);

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
	public void insertOrRemovePendingPerfectionCreditFolder(ITatDoc tatDoc, ILimitProfile limitProfile);
	
	public OBTatLimitTrack getTatStageTrackingListByLimitProfileId(long limitProfileID);
	
	public Long getNonWorkingDayNumber(String branch, Date startDate, Date endDate);
}
