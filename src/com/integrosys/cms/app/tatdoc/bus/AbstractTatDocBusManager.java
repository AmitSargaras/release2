package com.integrosys.cms.app.tatdoc.bus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.TatParamException;

/**
 * @author Cynthia
 * @since Aug 28, 2008
 */
public abstract class AbstractTatDocBusManager implements ITatDocBusManager {

	public ITatDocDAO tatDocDao;

	private ITatDocJdbc tatDocJdbc;

	// **************** Getter and Setter ******************
	public ITatDocDAO getTatDocDAO() {
		return tatDocDao;
	}

	public void setTatDocDAO(ITatDocDAO dao) {
		this.tatDocDao = dao;
	}

	public ITatDocJdbc getTatDocJdbc() {
		return tatDocJdbc;
	}

	public void setTatDocJdbc(ITatDocJdbc jdbc) {
		this.tatDocJdbc = jdbc;

	}// **************** Implementation Methods ******************

	public ITatDoc create(ITatDoc tatDoc) throws TatDocException {
		return getTatDocDAO().create(getEntityName(), tatDoc);
	}

	public ITatDoc update(ITatDoc tatDoc) throws TatDocException {
		return getTatDocDAO().update(getEntityName(), tatDoc);
	}

	public ITatDoc getTatDocByID(long tatDocID) throws TatDocException {
		return getTatDocDAO().getTatDocByID(getEntityName(), tatDocID);
	}

	public ITatDoc getTatDocByLimitProfileID(long limitProfileID) throws TatDocException {
		ITatDoc tatDoc = getTatDocDAO().getTatDocByLimitProfileID(getEntityName(), limitProfileID);
        // Andy Wong, Aug 3, 2009: remove setting limit profile LI generatation date in Maintain TAT, LI generation only available in CMS
//		Date instructionDate = getTatDocJdbc().getDateOfInstructionToSolicitor(limitProfileID);
//		if (tatDoc != null) {
//			tatDoc.setSolicitorInstructionDate(instructionDate);
//		}
		return tatDoc;
	}

	public String[] getTrxValueByLimitProfileID(long limitProfileID) throws TatDocException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
		String trxId = getTatDocJdbc().getTrxValueByLimitProfileID(limitProfileID);
		Date instructionDate = getTatDocJdbc().getDateOfInstructionToSolicitor(limitProfileID);
		String[] returnResult = { trxId, instructionDate != null ? sdf.format(instructionDate) : null };
		return returnResult;
	}

	public Date getDateOfInstructionToSolicitor(long limitProfileId) {
		return getTatDocJdbc().getDateOfInstructionToSolicitor(limitProfileId);
	}

	public void insertOrRemovePendingPerfectionCreditFolder(ITatDoc tatDoc, ILimitProfile limitProfile) {
		getTatDocJdbc().insertOrRemovePendingPerfectionCreditFolder(tatDoc, limitProfile);
	}

	// **************** Abstract Methods ******************
	public abstract String getEntityName();
	
	public ITatParam getTatParamByApplicationType(String applicationType) throws TatParamException
	{
		return getTatDocDAO().getTatParamByAppType(applicationType);
	}
	
	public OBTatLimitTrack getTatStageTrackingListByLimitProfileId(long limitProfileId) throws TatDocException
	{
		return getTatDocJdbc().getTatStageTrackingListByLimitProfileId(limitProfileId);
	}
	
	public Long getNonWorkingDaysByBranch(String branch, Date startDate, Date endDate) throws TatDocException
	{
		Long nonWorkingNumber = getTatDocJdbc().getNonWorkingDayNumber(branch, startDate, endDate);
		return nonWorkingNumber;
	}
	
	public void commiTrackingList(OBTatLimitTrack trackOB)
	{
		getTatDocDAO().commitTatTrackingList(trackOB);
	}

}
