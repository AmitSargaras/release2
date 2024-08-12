package com.integrosys.cms.app.tatduration.bus;

import java.util.List;

import org.hibernate.HibernateException;

import com.integrosys.cms.app.limit.bus.ILimitProfile;

/**
 * @author Cynthia
 * @since Aug 28, 2008
 */
public abstract class AbstractTatParamBusManager implements ITatParamBusManager {

	public ITatParamDAO tatParamDao;

	private ITatParamJdbc tatParamJdbc;

	// **************** Getter and Setter ******************
	public ITatParamDAO getTatParamDAO() {
		return tatParamDao;
	}

	public void setTatParamDAO(ITatParamDAO dao) {
		this.tatParamDao = dao;
	}

	public ITatParamJdbc getTatParamJdbc() {
		return tatParamJdbc;
	}

	public void setTatParamJdbc(ITatParamJdbc jdbc) {
		this.tatParamJdbc = jdbc;

	}// **************** Implementation Methods ******************

	public ITatParam create(ITatParam tatParam) throws TatParamException 
	{
		return getTatParamDAO().create(getEntityName(), tatParam);
	}

	public ITatParam update(ITatParam tatParam) throws TatParamException 
	{
		return getTatParamDAO().update(getEntityName(), tatParam);
	}

	public ITatParam getTatParamByApplicationType(String appType)throws TatParamException
	{
		return getTatParamDAO().getTatParamByApplicationType(appType);
	}
	
	public ITatParamItem getTatParamItem(Long tatParamItemId) throws TatParamException
	{
		return getTatParamDAO().getTatParamItem(tatParamItemId);
	}
	
	public List getTatParamList() throws TatParamException
	{
		return getTatParamJdbc().getAllTatParamList();
	}
	
	/*public String[] getTrxValueByLimitProfileID(long limitProfileID) throws TatParamException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
		String trxId = getTatParamJdbc().getTrxValueByLimitProfileID(limitProfileID);
		Date instructionDate = getTatParamJdbc().getDateOfInstructionToSolicitor(limitProfileID);
		String[] returnResult = { trxId, instructionDate != null ? sdf.format(instructionDate) : null };
		return returnResult;
	}*/

	/*public Date getDateOfInstructionToSolicitor(long limitProfileId) {
		return getTatParamJdbc().getDateOfInstructionToSolicitor(limitProfileId);
	}*/

	public void insertOrRemovePendingPerfectionCreditFolder(ITatParam tatParam, ILimitProfile limitProfile) {
		getTatParamJdbc().insertOrRemovePendingPerfectionCreditFolder(tatParam, limitProfile);
	}

	// **************** Abstract Methods ******************
	public abstract String getEntityName();

}
