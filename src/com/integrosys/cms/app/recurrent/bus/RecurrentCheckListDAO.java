/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/RecurrentCheckListDAO.java,v 1.11 2005/01/24 05:15:35 ckchua Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * DAO for recurrent checklist
 * @author $Author: ckchua $
 * @version $Revision: 1.11 $
 * @since $Date: 2005/01/24 05:15:35 $ Tag: $Name: $
 */

public class RecurrentCheckListDAO implements IRecurrentCheckListDAO, ICMSTrxTableConstants {
	private DBUtil dbUtil = null;

	private static final String SELECT_STAGE_RECURRENT_CHECKLIST = "SELECT STAGE_RECURRENT_DOC.RECURRENT_DOC_ID, STAGE_RECURRENT_DOC.CMS_LSP_LMT_PROFILE_ID,"
			+ " STAGE_RECURRENT_DOC.CMS_LMP_SUB_PROFILE_ID, TRANSACTION.TRANSACTION_ID, TRANSACTION.STATUS "

			+ " FROM STAGE_RECURRENT_DOC, STAGE_RECURRENT_DOC_ITEM, TRANSACTION WHERE TRANSACTION.STAGING_REFERENCE_ID = STAGE_RECURRENT_DOC.RECURRENT_DOC_ID "

			+ " AND TRANSACTION.STATUS <> 'CLOSED' AND TRANSACTION.TRANSACTION_TYPE = 'RECURRENT_CHECKLIST'"
			
			+ " AND STAGE_RECURRENT_DOC.RECURRENT_DOC_ID = STAGE_RECURRENT_DOC_ITEM.RECURRENT_DOC_ID";

	private static final String SELECT_STAGE_RECURRENT_CHECKLIST_ITEM = "SELECT TRANS_HISTORY.TRANSACTION_ID, TRANS_HISTORY.TRANSACTION_DATE, TRANS_HISTORY.TRANSACTION_TYPE, "
			+ " STAGE_RECURRENT_DOC_ITEM.RECURRENT_ITEM_REF_ID, STAGE_RECURRENT_DOC_ITEM.FREQUENCY, "
			+ " STAGE_RECURRENT_DOC_ITEM.FREQUENCY_UNIT, STAGE_RECURRENT_DOC_ITEM.DUE_DATE "
			+ " FROM TRANS_HISTORY, STAGE_RECURRENT_DOC, STAGE_RECURRENT_DOC_ITEM "
			+ " WHERE TRANS_HISTORY.STAGING_REFERENCE_ID = TO_CHAR(STAGE_RECURRENT_DOC.RECURRENT_DOC_ID) "
			+ " AND STAGE_RECURRENT_DOC.RECURRENT_DOC_ID = STAGE_RECURRENT_DOC_ITEM.RECURRENT_DOC_ID "
			+ " AND TRANS_HISTORY.FROM_STATE = 'PENDING_UPDATE' AND TRANS_HISTORY.STATUS = 'ACTIVE' "
			+ " AND TRANS_HISTORY.TRANSACTION_TYPE = 'RECURRENT_CHECKLIST' AND STAGE_RECURRENT_DOC_ITEM.RECURRENT_ITEM_REF_ID = ?";

	private static final String SELECT_STAGE_CONVENANT = "SELECT TRANS_HISTORY.TRANSACTION_ID, TRANS_HISTORY.TRANSACTION_DATE, TRANS_HISTORY.TRANSACTION_TYPE, "
			+ " STAGE_CONVENANT.CONVENANT_REF_ID, STAGE_CONVENANT.FREQUENCY, "
			+ " STAGE_CONVENANT.FREQUENCY_UNIT, STAGE_CONVENANT.DUE_DATE "
			+ " FROM TRANS_HISTORY, STAGE_RECURRENT_DOC, STAGE_CONVENANT "
			+ " WHERE TRANS_HISTORY.STAGING_REFERENCE_ID = TO_CHAR(STAGE_RECURRENT_DOC.RECURRENT_DOC_ID) "
			+ " AND STAGE_RECURRENT_DOC.RECURRENT_DOC_ID = STAGE_CONVENANT.RECURRENT_DOC_ID "
			+ " AND TRANS_HISTORY.FROM_STATE = 'PENDING_UPDATE' AND TRANS_HISTORY.STATUS = 'ACTIVE' "
			+ " AND TRANS_HISTORY.TRANSACTION_TYPE = 'RECURRENT_CHECKLIST' AND STAGE_CONVENANT.CONVENANT_REF_ID = ?";

	/**
	 * To get the list of recurrent checklist item history based on the item
	 * reference
	 * @param anItemReference of long type
	 * @return IRecurrentCheckListItem[] - the list of recurrent checklist items
	 * @throw SearchDAOException on errors
	 */
	public IRecurrentCheckListItem[] getRecurrentItemHistory(long anItemReference) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			//DefaultLogger.debug(this, SELECT_STAGE_RECURRENT_CHECKLIST_ITEM);
			dbUtil.setSQL(SELECT_STAGE_RECURRENT_CHECKLIST_ITEM);
			dbUtil.setLong(1, anItemReference);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			IRecurrentCheckListItem recItem = null;
			while (rs.next()) {
				recItem = new OBRecurrentCheckListItem();
				recItem.setFrequency(rs.getInt(ITMTBL_FREQUENCY));
				recItem.setFrequencyUnit(rs.getString(ITMTBL_FREQUENCY_UNIT));
				IItem item = new OBItem();
				item.setExpiryDate(rs.getDate(ITMTBL_DUE_DATE));
				recItem.setItem(item);
				// recItem.setDateReceived(rs.getDate(ITMTBL_DATE_RECEIVED));
				resultList.add(recItem);
			}
			return (IRecurrentCheckListItem[]) resultList.toArray(new IRecurrentCheckListItem[0]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getRecurrentItemHistory", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getRecurrentItemHistory", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getRecurrentItemHistory", ex);
			}
		}
	}

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param anICheckListOwner of ICheckListOwner
	 * @param aStatusList of String[] type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 */
	public RecurrentSearchResult[] getCheckList(long aLimitProfileID, long aSubProfileID, String[] aStatusList)
			throws SearchDAOException {
		String subSql = getSearchString(aLimitProfileID, aSubProfileID, aStatusList);
		String sql = null;
		if (!isEmpty(subSql)) {
			sql = SELECT_STAGE_RECURRENT_CHECKLIST + " AND " + subSql;
		}
		else {
			sql = SELECT_STAGE_RECURRENT_CHECKLIST;
		}
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			RecurrentSearchResult checkList = null;
			while (rs.next()) {
				checkList = new RecurrentSearchResult();
				checkList.setCheckListID(rs.getLong(RECTBL_RECURRENT_DOC_ID));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString(TRXTBL_STATUS));
				resultList.add(checkList);
			}
			return (RecurrentSearchResult[]) resultList.toArray(new RecurrentSearchResult[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckList", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckList", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckList", ex);
			}
		}
	}
	
	
	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param anICheckListOwner of ICheckListOwner
	 * @param aStatusList of String[] type
	 * @param annexureId of String type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 */
	public RecurrentSearchResult[] getCheckListByAnnexureId(long aLimitProfileID, long aSubProfileID, String[] aStatusList, String annexureType)
			throws SearchDAOException {
		String subSql = getSearchString(aLimitProfileID, aSubProfileID, aStatusList);
		subSql += " AND STAGE_RECURRENT_DOC_ITEM.DOC_TYPE = '"+annexureType+"' "; 
		String sql = null;
		if (!isEmpty(subSql)) {
			sql = SELECT_STAGE_RECURRENT_CHECKLIST + " AND " + subSql;
		}
		else {
			sql = SELECT_STAGE_RECURRENT_CHECKLIST;
		}
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			RecurrentSearchResult checkList = null;
			while (rs.next()) { 
				checkList = new RecurrentSearchResult();
				checkList.setCheckListID(rs.getLong(RECTBL_RECURRENT_DOC_ID));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString(TRXTBL_STATUS));
				resultList.add(checkList);
			}
			return (RecurrentSearchResult[]) resultList.toArray(new RecurrentSearchResult[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckList", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckList", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckList", ex);
			}
		}
	}

	public IRecurrentCheckListItem getRecurrentCheckListItem(long anItemReference) throws SearchDAOException {
		String sql = "SELECT MAX(RECURRENT_ITEM_ID) RECURRENT_ITEM_ID FROM STAGE_RECURRENT_DOC_ITEM WHERE RECURRENT_ITEM_REF_ID = ?";
		try {
			dbUtil = new DBUtil();
			//DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, anItemReference);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			IRecurrentCheckListItem item = null;
			while (rs.next()) {
				item = new OBRecurrentCheckListItem();
				item.setCheckListItemID(rs.getLong("RECURRENT_ITEM_ID"));
			}
			return item;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getRecurrentCheckListItem", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getRecurrentCheckListItem", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckList", ex);
			}
		}
	}

	// cr 26
	public IConvenant getConvenant(long anItemReference) throws SearchDAOException {
		String sql = "SELECT MAX(CONVENANT_ID) CONVENANT_ID FROM STAGE_CONVENANT WHERE CONVENANT_REF_ID = "
				+ anItemReference;
		try {
			dbUtil = new DBUtil();
			//DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			IConvenant item = null;
			while (rs.next()) {
				item = new OBConvenant();
				item.setConvenantID(rs.getLong("CONVENANT_ID"));
			}
			rs.close();
			return item;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getConvenant", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getConvenant", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getConvenant", ex);
			}
		}
	}

	// cr 26
	public IConvenant[] getConvenantHistory(long anItemReference) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			//DefaultLogger.debug(this, SELECT_STAGE_CONVENANT);
			dbUtil.setSQL(SELECT_STAGE_CONVENANT);
			dbUtil.setLong(1, anItemReference);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			IConvenant recItem = null;
			while (rs.next()) {
				recItem = new OBConvenant();
				recItem.setFrequency(rs.getInt(ITMTBL_FREQUENCY));
				recItem.setFrequencyUnit(rs.getString(ITMTBL_FREQUENCY_UNIT));
				// IItem item = new OBItem();
				// item.setExpiryDate(rs.getDate(ITMTBL_DUE_DATE));
				recItem.setInitialDueDate(rs.getDate(ITMTBL_DUE_DATE));
				// recItem.setItem(item);
				// recItem.setDateReceived(rs.getDate(ITMTBL_DATE_RECEIVED));
				resultList.add(recItem);
			}
			return (IConvenant[]) resultList.toArray(new IConvenant[0]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getConvenantHistory", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getRecurrentItemHistory", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getRecurrentItemHistory", ex);
			}
		}
	}

	private String getSearchString(long aLimitProfileID, long aSubProfileID, String[] aStatusList)
			throws SearchDAOException {
		boolean addSql = false;
		StringBuffer strBuffer = new StringBuffer();
		if ((aStatusList != null) && (aStatusList.length > 0)) {
			addSql = true;
			strBuffer.append(ICMSTrxTableConstants.TRX_TBL_NAME);
			strBuffer.append(".");
			strBuffer.append(ICMSTrxTableConstants.TRXTBL_STATUS);
			strBuffer.append(" IN ('");
			for (int ii = 0; ii < aStatusList.length; ii++) {
				strBuffer.append(aStatusList[ii]);
				if (ii != aStatusList.length - 1) {
					strBuffer.append("', '");
				}
			}
			strBuffer.append("')");
		}

		if (aLimitProfileID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			if (addSql) {
				strBuffer.append(" AND ");
			}
			addSql = true;
			strBuffer.append(SRETBL_LIMIT_PROFILE_ID_PREF);
			strBuffer.append(" = ");
			strBuffer.append(aLimitProfileID);
		}

		if (aSubProfileID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			if (addSql) {
				strBuffer.append(" AND ");
			}
			addSql = true;
			strBuffer.append(SRETBL_BORROWER_ID_PREF);
			strBuffer.append(" = ");
			strBuffer.append(aSubProfileID);
			return strBuffer.toString();
		}
		return strBuffer.toString();
	}

	/**
	 * Utilty method to check if a string value is null or empty
	 * @param aValue - String
	 * @return boolean - true if empty and false otherwise
	 */
	private boolean isEmpty(String aValue) {
		if ((aValue != null) && (aValue.trim().length() > 0)) {
			return false;
		}
		return true;
	}
	
	public long getRecurrentDocId(long limitProfileId, long subProfileId)throws SearchDAOException,RemoteException{
		long recurrentDocId = 0l;
		ResultSet rs =null;
		try{
			
			String sql = "SELECT RECURRENT_DOC_ID FROM CMS_RECURRENT_DOC WHERE ";
			if (limitProfileId != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					sql +=" CMS_LSP_LMT_PROFILE_ID = "+limitProfileId;
			}

			if (subProfileId != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				sql +=" AND CMS_LMP_SUB_PROFILE_ID = "+subProfileId;
			}
			try{
				dbUtil = new DBUtil();
				DefaultLogger.debug(this, sql);
				dbUtil.setSQL(sql);
				 rs = dbUtil.executeQuery();
				
				while (rs.next()) { 
					recurrentDocId = rs.getLong(RECTBL_RECURRENT_DOC_ID);
				}	
			}
			catch (SQLException e) {
				throw new SearchDAOException("SQLException in getRecurrentDocId", e);
			}finally{
				finalize(dbUtil, rs);
			}
		}
		catch (SearchDAOException re) {
			re.printStackTrace();
		}
		return recurrentDocId;
	}
	
	public String getBankingType(long limitProfileId, long subProfileId)throws SearchDAOException,RemoteException{
		String bankingMethod = null;
		String bankingType = null;
		long mainProfileId = 0l;
		ResultSet rs=null;
		try{
			
			String sql = "SELECT bank.CMS_BANKING_METHOD_NAME, pf.CMS_LE_MAIN_PROFILE_ID  FROM CMS_BANKING_METHOD_CUST bank,SCI_LE_SUB_PROFILE pf\r\n" + 
					"WHERE pf.CMS_LE_SUB_PROFILE_ID=bank.CMS_LE_SUB_PROFILE_ID AND ";


			if (subProfileId != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				sql +=" pf.CMS_LE_SUB_PROFILE_ID = "+subProfileId;
			}
			
			try{
				dbUtil = new DBUtil();
				DefaultLogger.debug(this, sql);
				dbUtil.setSQL(sql);
				rs = dbUtil.executeQuery();
				
				while (rs.next()) { 
					bankingMethod = rs.getString("CMS_BANKING_METHOD_NAME");
					mainProfileId = rs.getLong("CMS_LE_MAIN_PROFILE_ID");
				}	
			}
			catch (SQLException e) {
				throw new SearchDAOException("SQLException in getBankingType", e);
			} 	finally{
				finalize(dbUtil, rs);
			}

			if(bankingMethod != null && !bankingMethod.equalsIgnoreCase("SOLE"))
			{
				String sql1 = "SELECT CMS_LE_NODAL, CMS_LE_LEAD FROM SCI_LE_BANKING_METHOD WHERE CMS_LE_BANK_TYPE ='S' AND " +
						" CMS_LE_MAIN_PROFILE_ID = "+mainProfileId;
				
				try{
					dbUtil = new DBUtil();
					DefaultLogger.debug(this, sql1);
					dbUtil.setSQL(sql1);
					 rs = dbUtil.executeQuery();
					
					String nodal = null;
					String lead = null;
					
					while (rs.next()) { 
						nodal = rs.getString("CMS_LE_NODAL");
						lead = rs.getString("CMS_LE_LEAD");
					}
					bankingType = bankingMethod;
					
					if( (bankingMethod.equalsIgnoreCase("MULTIPLE") || "OUTSIDEMULTIPLE".equalsIgnoreCase(bankingMethod)) && nodal != null){
						bankingType = bankingType+"-"+nodal;
					}
					else if( (bankingMethod.equalsIgnoreCase("CONSORTIUM") || "OUTSIDECONSORTIUM".equalsIgnoreCase(bankingMethod)) && lead != null)
					{
						bankingType = bankingType+"-"+lead;
					}
					
				}catch (SQLException e) {
					throw new SearchDAOException("SQLException in getBankingType", e);
				}	finally{
					finalize(dbUtil, rs);
				}

			}
			else
			{
				bankingType = bankingMethod;
			}
		}
		catch (SearchDAOException re) {
			throw new SearchDAOException("SQLException in getBankingType", re);
		}
		return bankingType;
	}
	
	public void insertAnnexures(ILimitProfile aLimitProfile)
		throws SearchDAOException,RemoteException
	{
		long limitProfileId = aLimitProfile.getLimitProfileID();
		long subProfileId = aLimitProfile.getCustomerID();
		String bankingMethod = null;
		String bankingType = null;
		String nodal = null;
		String lead = null;
		long mainProfileId = 0l;
		try{
			//********* getting Banking Method and Main Profile ID by Customer ID
			String sql = "SELECT BANKING_METHOD, CMS_LE_MAIN_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE ";

			if (subProfileId != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				sql +=" CMS_LE_SUB_PROFILE_ID = "+subProfileId;
			}
			ResultSet rs = null;
			try{
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				 rs = dbUtil.executeQuery();
				
				while (rs.next()) { 
					bankingMethod = rs.getString("BANKING_METHOD");
					mainProfileId = rs.getLong("CMS_LE_MAIN_PROFILE_ID");
				}	
			}
			catch (SQLException se) {
				throw new SearchDAOException("SQLException in insertAnnexures", se);
			} 	
			finally{
				finalize(dbUtil, rs);
			}

			if(bankingMethod != null && !bankingMethod.equalsIgnoreCase("SOLE"))
			{
				String sql1 = "SELECT CMS_LE_NODAL, CMS_LE_LEAD FROM SCI_LE_BANKING_METHOD WHERE CMS_LE_BANK_TYPE ='S' AND " +
						" CMS_LE_MAIN_PROFILE_ID = "+mainProfileId;
				try{
					dbUtil = new DBUtil();
					dbUtil.setSQL(sql1);
					rs = dbUtil.executeQuery();
					
					while (rs.next()) { 
						nodal = rs.getString("CMS_LE_NODAL");
						lead = rs.getString("CMS_LE_LEAD");
					}
					
					if(nodal != null){
						bankingType = nodal;
					}
					else if(lead != null){
						bankingType = lead;
					} 
					
				}catch (SQLException e) {
					throw new SearchDAOException("SQLException in insertAnnexures", e);
				} 
				finally{
					finalize(dbUtil, rs);
				}
			}
		}
		catch (SearchDAOException re) {
			throw new SearchDAOException("SQLException in insertAnnexures", re);
		}

		long recurrentDocID = getRecurrentDocId(limitProfileId,subProfileId);
		long stageRecurrentDocID = 0l;
		
		if(recurrentDocID == 0){
			recurrentDocID = getSequence("RECURRENT_DOC_SEQ");
			try{
				String insertIntoRecurrentDocTable = "INSERT INTO CMS_RECURRENT_DOC (RECURRENT_DOC_ID,CMS_LMP_SUB_PROFILE_ID,VERSION_TIME,CMS_LSP_LMT_PROFILE_ID) " +
						" VALUES ("+recurrentDocID+","+subProfileId+","+123465+","+limitProfileId+")";
				
				dbUtil = new DBUtil();
				dbUtil.setSQL(insertIntoRecurrentDocTable);
				dbUtil.executeUpdate();
				stageRecurrentDocID = getSequence("RECURRENT_DOC_SEQ");

				String insertIntoStageRecurrentDocTable = "INSERT INTO STAGE_RECURRENT_DOC (RECURRENT_DOC_ID,CMS_LMP_SUB_PROFILE_ID,VERSION_TIME,CMS_LSP_LMT_PROFILE_ID) " +
						" VALUES ("+stageRecurrentDocID+","+subProfileId+","+123465+","+limitProfileId+")";
				
				dbUtil.setSQL(insertIntoStageRecurrentDocTable);
				dbUtil.executeUpdate();
				
			}
			catch(SQLException e)
			{
				throw new SearchDAOException("SQLException in insertAnnexures", e);
			}finally{
				finalize(dbUtil, null);
			}
		}
		
		DateFormat df = new SimpleDateFormat("dd-MMM-yy");
		
		try{
			Date camExpiryDate = aLimitProfile.getNextAnnualReviewDate();
			String camExpDateStr = null;
			try{
				camExpDateStr = df.format(camExpiryDate); 
			}
			catch (Exception e) 
			{
				throw new SearchDAOException("SQLException in insertAnnexures", e);
			}

			long id = 0;
			long idStage = 0;
			
			//******** Inserting Annexures depending on Banking Method for either MULTIPLE or CONSORTIUM
			if(bankingMethod != null && !bankingMethod.equalsIgnoreCase("SOLE"))
			{   
				dbUtil= new DBUtil();
				//********* getting Staging Recurrent DOC ID by limit profile ID and customer ID
				long stageRecurrentDocId = 0l;

				String sql = "SELECT MAX(RECURRENT_DOC_ID) AS DOC_ID FROM STAGE_RECURRENT_DOC WHERE ";
				
				if (limitProfileId != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
						sql +=" CMS_LSP_LMT_PROFILE_ID = "+limitProfileId;
				}

				if (subProfileId != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					sql +=" AND CMS_LMP_SUB_PROFILE_ID = "+subProfileId;
				}
				dbUtil.setSQL(sql);
				ResultSet rs3 = dbUtil.executeQuery();
				
				while (rs3.next()) { 
					stageRecurrentDocId = rs3.getLong("DOC_ID");
				}
				
				if(stageRecurrentDocId == 0){
					stageRecurrentDocId = stageRecurrentDocID;
				}
				finalize(dbUtil, rs3);
				//*************** Inserting Annexures into Actual CMS_RECURRENT_DOC_ITEM table
				
				id = getSequence("RECURRENT_CHECKLIST_ITEM_SEQ");
				
				dbUtil= new DBUtil();

				String insertAnnexure1Query = "INSERT INTO CMS_RECURRENT_DOC_ITEM " +
					" (RECURRENT_ITEM_ID, RECURRENT_ITEM_REF_ID, RECURRENT_ITEM_DESC, FREQUENCY, FREQUENCY_UNIT, DUE_DATE, RECURRENT_DOC_ID, LAST_DOC_ENTRY_DATE, DOC_TYPE)"+
					" VALUES("+(id)+","+(id+2)+",'Annexure1',1,'Y','"+camExpDateStr+"',"+recurrentDocID+", '"+df.format(new Date())+"','Annexure')";
				dbUtil.setSQL(insertAnnexure1Query);
				dbUtil.executeUpdate();
				
				id = getSequence("RECURRENT_CHECKLIST_ITEM_SEQ");
				
				String insertAnnexure3Query = "INSERT INTO CMS_RECURRENT_DOC_ITEM " +
					" (RECURRENT_ITEM_ID, RECURRENT_ITEM_REF_ID, RECURRENT_ITEM_DESC, FREQUENCY, FREQUENCY_UNIT, DUE_DATE, RECURRENT_DOC_ID, LAST_DOC_ENTRY_DATE, DOC_TYPE)"+
					" VALUES("+(id)+","+(id+2)+",'Annexure3',1,'Y','"+camExpDateStr+"',"+recurrentDocID+", '"+df.format(new Date())+"','Annexure')";
				
				dbUtil.setSQL(insertAnnexure3Query);
				dbUtil.executeUpdate();
				//*************** Inserting Annexures into Staging STAGE_RECURRENT_DOC_ITEM table
				
				idStage = getSequence("RECURRENT_CHECKLIST_ITEM_SEQ");
				
				String insertStageAnnexure1Query = "INSERT INTO STAGE_RECURRENT_DOC_ITEM " +
					" (RECURRENT_ITEM_ID, RECURRENT_ITEM_REF_ID, RECURRENT_ITEM_DESC, FREQUENCY, FREQUENCY_UNIT, DUE_DATE, RECURRENT_DOC_ID, LAST_DOC_ENTRY_DATE, DOC_TYPE)"+
					" VALUES("+(idStage)+","+(idStage)+",'Annexure1',1,'Y','"+camExpDateStr+"',"+stageRecurrentDocId+", '"+df.format(new Date())+"','Annexure')";
			
				dbUtil.setSQL(insertStageAnnexure1Query);
				dbUtil.executeUpdate();
				
				idStage = getSequence("RECURRENT_CHECKLIST_ITEM_SEQ");
				
				String insertStageAnnexure3Query = "INSERT INTO STAGE_RECURRENT_DOC_ITEM " +
					" (RECURRENT_ITEM_ID, RECURRENT_ITEM_REF_ID, RECURRENT_ITEM_DESC, FREQUENCY, FREQUENCY_UNIT, DUE_DATE, RECURRENT_DOC_ID, LAST_DOC_ENTRY_DATE, DOC_TYPE)"+
					" VALUES("+(idStage)+","+(idStage)+",'Annexure3',1,'Y','"+camExpDateStr+"',"+stageRecurrentDocId+", '"+df.format(new Date())+"','Annexure')";
				
				dbUtil.setSQL(insertStageAnnexure3Query);
				dbUtil.executeUpdate();
				finalize(dbUtil,null);
				
				long actualSubItemId = 0;
				long stageSubItemId = 0;
				
				//*************** Inserting Annexures into Actual CMS_RECURRENT_DOC_SUB_ITEM table
				
				actualSubItemId = getSequence("REC_CHECKLIST_SUB_ITEM_SEQ");
				
				dbUtil= new DBUtil();
				String insertAnnexure1IntoSubItemQuery = "INSERT INTO CMS_RECURRENT_DOC_SUB_ITEM " +
					" (SUB_ITEM_ID, SUB_ITEM_REF_ID, DUE_DATE, STATUS, RECURRENT_ITEM_ID) "+
					" VALUES("+(actualSubItemId)+","+(actualSubItemId+2)+",'"+camExpDateStr+"','PENDING',"+(id-1)+")";
			
				dbUtil.setSQL(insertAnnexure1IntoSubItemQuery);
				dbUtil.executeUpdate();
				
				actualSubItemId = getSequence("REC_CHECKLIST_SUB_ITEM_SEQ");
				String insertAnnexure3IntoSubItemQuery = "INSERT INTO CMS_RECURRENT_DOC_SUB_ITEM " +
					" (SUB_ITEM_ID, SUB_ITEM_REF_ID, DUE_DATE, STATUS, RECURRENT_ITEM_ID) "+
					" VALUES("+(actualSubItemId)+","+(actualSubItemId+2)+",'"+camExpDateStr+"','PENDING',"+(id)+")";
				
				dbUtil.setSQL(insertAnnexure3IntoSubItemQuery);
				dbUtil.executeUpdate();
				finalize(dbUtil,null);
				//*************** Inserting Annexures into Staging STAGE_RECURRENT_DOC_SUB_ITEM table
				
				stageSubItemId = getSequence("REC_CHECKLIST_SUB_ITEM_SEQ");
				
				dbUtil=new DBUtil();
				String insertStageAnnexure1IntoSubItemQuery = "INSERT INTO STAGE_RECURRENT_DOC_SUB_ITEM " +
					" (SUB_ITEM_ID, SUB_ITEM_REF_ID, DUE_DATE, STATUS, RECURRENT_ITEM_ID) "+
					" VALUES("+(stageSubItemId)+","+(stageSubItemId)+",'"+camExpDateStr+"','PENDING',"+(idStage-1)+")";
			
				dbUtil.setSQL(insertStageAnnexure1IntoSubItemQuery);
				dbUtil.executeUpdate();
				
				stageSubItemId = getSequence("REC_CHECKLIST_SUB_ITEM_SEQ");
				
				String insertStageAnnexure3IntoSubItemQuery = "INSERT INTO STAGE_RECURRENT_DOC_SUB_ITEM " +
					" (SUB_ITEM_ID, SUB_ITEM_REF_ID, DUE_DATE, STATUS, RECURRENT_ITEM_ID) "+
					" VALUES("+(stageSubItemId)+","+(stageSubItemId)+",'"+camExpDateStr+"','PENDING',"+(idStage)+")";
				
				dbUtil.setSQL(insertStageAnnexure3IntoSubItemQuery);
				dbUtil.executeUpdate();
				finalize(dbUtil,null);
				//******** Inserting Annexure 2 for HDFC BANK as NODAL/LEAD in Banking method
				if(bankingType != null && bankingType.equalsIgnoreCase("Y"))
				{
					Date curDate = new Date();
					String yr = Integer.toString(curDate.getYear()).substring(1, 3);
					int year = Integer.parseInt(yr);
					try{
						if(curDate.after(df.parse("01-Jan-"+year))){
							camExpDateStr="01-Apr-"+year;
						}
						if(curDate.after(df.parse("01-Apr-"+year))){
							camExpDateStr="01-Jul-"+year;
						}
						if(curDate.after(df.parse("01-Jul-"+year))){
							camExpDateStr="01-Oct-"+year;
						}
						if(curDate.after(df.parse("01-Oct-"+year))){
							camExpDateStr="01-Jan-"+(year+1);
						}
					}
					catch(Exception e)
					{
//						System.out.println("Error while checking dates");
						e.printStackTrace();
					}	
					//****** Inserting record into Actual CMS_RECURRENT_DOC_ITEM table
					
					dbUtil= new DBUtil();
					actualSubItemId = getSequence("REC_CHECKLIST_SUB_ITEM_SEQ");
					
					String insertAnnexure2Query = "INSERT INTO CMS_RECURRENT_DOC_ITEM " +
						" (RECURRENT_ITEM_ID, RECURRENT_ITEM_REF_ID, RECURRENT_ITEM_DESC, FREQUENCY, FREQUENCY_UNIT, DUE_DATE, RECURRENT_DOC_ID, LAST_DOC_ENTRY_DATE, DOC_TYPE)"+
						" VALUES("+(actualSubItemId)+","+(actualSubItemId+1)+",'Annexure2',1,'Y','"+camExpDateStr+"',"+recurrentDocID+", '"+df.format(new Date())+"','Annexure')";
					dbUtil.setSQL(insertAnnexure2Query);
					dbUtil.executeUpdate();
					
					//****** Inserting record into Staging STAGE_RECURRENT_DOC_ITEM table
					stageSubItemId = getSequence("REC_CHECKLIST_SUB_ITEM_SEQ");
					
					String insertStageAnnexure2Query = "INSERT INTO STAGE_RECURRENT_DOC_ITEM " +
						" (RECURRENT_ITEM_ID, RECURRENT_ITEM_REF_ID, RECURRENT_ITEM_DESC, FREQUENCY, FREQUENCY_UNIT, DUE_DATE, RECURRENT_DOC_ID, LAST_DOC_ENTRY_DATE, DOC_TYPE)"+
						" VALUES("+(stageSubItemId)+","+(stageSubItemId)+",'Annexure2',1,'Y','"+camExpDateStr+"',"+stageRecurrentDocId+", '"+df.format(new Date())+"','Annexure')";
				
					dbUtil.setSQL(insertStageAnnexure2Query);
					dbUtil.executeUpdate();
					
					//*************** Inserting Annexures into Staging STAGE_RECURRENT_DOC_SUB_ITEM table
					
					long actSubItemId = getSequence("REC_CHECKLIST_SUB_ITEM_SEQ");
					
					String insertActaulAnnexure2IntoSubItemQuery = "INSERT INTO CMS_RECURRENT_DOC_SUB_ITEM " +
						" (SUB_ITEM_ID, SUB_ITEM_REF_ID, DUE_DATE, STATUS, RECURRENT_ITEM_ID) "+
						" VALUES("+(actSubItemId)+","+(actSubItemId+1)+",'"+camExpDateStr+"','PENDING',"+(actualSubItemId)+")";
				
					dbUtil.setSQL(insertActaulAnnexure2IntoSubItemQuery);
					dbUtil.executeUpdate();
					
					long stagSubItemId = getSequence("REC_CHECKLIST_SUB_ITEM_SEQ");
					
					String insertStageAnnexure2IntoSubItemQuery = "INSERT INTO STAGE_RECURRENT_DOC_SUB_ITEM " +
						" (SUB_ITEM_ID, SUB_ITEM_REF_ID, DUE_DATE, STATUS, RECURRENT_ITEM_ID) "+
						" VALUES("+(stagSubItemId)+","+(stagSubItemId)+",'"+camExpDateStr+"','PENDING',"+(stageSubItemId)+")";
					
					dbUtil.setSQL(insertStageAnnexure2IntoSubItemQuery);
					dbUtil.executeUpdate();
					finalize(dbUtil, null);
				}
				
				//************ getting max Transaction ID from Transaction Table 
				String transInsertQuery = null;
				long transId = 0;

				transId = getSequence("TRX_SEQ"); 
				
				//****** Inserting record into Transaction table
				transInsertQuery = "INSERT INTO TRANSACTION " +
						" (TRANSACTION_ID,FROM_STATE,TRANSACTION_TYPE,CREATION_DATE,TRANSACTION_DATE,REFERENCE_ID,STATUS,STAGING_REFERENCE_ID, " +
						" CUSTOMER_ID,LIMIT_PROFILE_ID,TRANSACTION_SUBTYPE) " +
						" VALUES ("+transId+",'ACTIVE','RECURRENT_CHECKLIST','"+df.format(new Date())+"','"+df.format(new Date())+"',"+recurrentDocID+",'ACTIVE'," +
						" "+stageRecurrentDocId+","+subProfileId+","+limitProfileId+",'REC_CHECKLIST_ANN')";
				
				dbUtil=new DBUtil();
				dbUtil.setSQL(transInsertQuery);
				dbUtil.executeUpdate();
				finalize(dbUtil,null);
			}
		}
		catch(SQLException se) {
			throw new SearchDAOException("SQLException in insertAnnexures", se);
		}
		finally {
			try {
				finalize(dbUtil,null);
			}
			catch (Exception ex) {
				throw new SearchDAOException("SQLException in insertAnnexures", ex);
			}
		}
	}
	
	private long getSequence(String seqName){
		long seqId=0l;
		try
		{
			seqId = Long.parseLong((new SequenceManager()).getSeqNum(seqName, true));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return seqId;
	}
	private void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	public List getRecurrentDocIdDesc(long recurrentDocId, String docType){
		List documentItemIdDesc = new ArrayList();
		String sql="select recurrent_item_id,recurrent_item_desc from cms_recurrent_doc_item where recurrent_doc_id='"+recurrentDocId+"' AND doc_type='"+docType+"'";
		try{
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		while(null!=rs && rs.next()){
			String recurrenItemId= rs.getString("recurrent_item_id");
			String recurrentItemDesc=rs.getString("recurrent_item_desc");
			LabelValueBean lvBean = new LabelValueBean(recurrentItemDesc,recurrenItemId);
			documentItemIdDesc.add(lvBean);
		}
		}
		catch(Exception e){
			DefaultLogger.debug(this,"ERRROR:"+e.getMessage());
		}
		return documentItemIdDesc;
	}
	
	public IRecurrentCheckListSubItem getRecurrentDocStatusDate(long recurrentItemId){
		OBRecurrentCheckListSubItem obRecurrentCheckListSubItem = new OBRecurrentCheckListSubItem();
		String sql="select due_date, rec_date,status from cms_recurrent_doc_sub_item where recurrent_item_id ="+recurrentItemId;
		try{
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		while(null!=rs && rs.next()){
			Date dueDate= rs.getDate("due_date");
			Date recDate=rs.getDate("rec_date");
			String status=rs.getString("status");
			
			obRecurrentCheckListSubItem.setDueDate(dueDate);
			obRecurrentCheckListSubItem.setReceivedDate(recDate);
			obRecurrentCheckListSubItem.setStatus(status);
			
		}
		}
		catch(Exception e){
			DefaultLogger.debug(this,"ERRROR:"+e.getMessage());
		}
		
		return obRecurrentCheckListSubItem;
		
	}
	
	public String getRecurrentDocDesc(long recurrentItemId, String docType){
	String recurrentDocDesc="";;
	String sql="select recurrent_item_desc from cms_recurrent_doc_item where recurrent_item_id='"+recurrentItemId+"' And doc_type='"+docType+"'";
	try{
	dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	while(null!=rs && rs.next()){
		 recurrentDocDesc=rs.getString("recurrent_item_desc");
		
	}
	}
	catch(Exception e){
		DefaultLogger.debug(this,"ERRROR:"+e.getMessage());
	}
	return recurrentDocDesc;
	}
	//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
}
