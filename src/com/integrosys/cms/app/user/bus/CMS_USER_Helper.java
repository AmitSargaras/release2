package com.integrosys.cms.app.user.bus;

import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.component.login.app.LoginConstant;
import com.integrosys.component.user.app.bus.UserException;
import com.integrosys.component.user.app.constant.UserConstant;

public class CMS_USER_Helper {
	private DBUtil dbUtil;
	private String USER_TABLE = PropertyManager.getValue("component.user.table.user");
	private String AUTHENTICATION_TABLE = PropertyManager.getValue("component.user.table.authentication");
	private String STAGE_USER_TABLE = PropertyManager.getValue("component.user.table.stage.user");
	private String TRANSACTION_TABLE = PropertyManager.getValue("component.user.table.transaction");
	
	public void dormantUserAccounts(String exclSuperUsers, int daysDormant) throws UserException {
		dormantActualUser(exclSuperUsers, daysDormant);
		dormantStageUser(exclSuperUsers, daysDormant);
		dormantUserAuthentication(exclSuperUsers);
	}

	public void dormantActualUser(String exclSuperUsers, int daysDormant) throws UserException {
		String userSQL = " UPDATE " + USER_TABLE + "    SET status = '" + UserConstant.STATUS_DORMANT + "' "
				+ "  WHERE user_id IN ( " + "        SELECT user_id" + "          FROM " + USER_TABLE + " usr, "
				+ AUTHENTICATION_TABLE + " auth" + "         WHERE usr.login_id = auth.login_id"
				+ " 		    AND (select trunc(TO_TIMESTAMP(gp.param_value,'DD/MM/YYYY')) from  cms_general_param gp where gp.param_code='APPLICATION_DATE')  -  trunc(auth.last_access_time) >= " + daysDormant
				+ "      		AND usr.STATUS = '" + UserConstant.STATUS_ACTIVE + "' ";
		if ((exclSuperUsers != null) && !"".equals(exclSuperUsers)) {
			userSQL += "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) ) ";
		}
		else {
			userSQL += "      )";
		}

		try {
			dbUtil = new DBUtil();
			try {
				dbUtil.setSQL(userSQL);
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query", e);
			}
			dbUtil.executeUpdate();
			dbUtil.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Exception at dormantUserAccounts() method", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new UserException("Exception at dormantUserAccounts() method", e);
			}
		}
	}

	public void dormantUserAuthentication(String exclSuperUsers) throws UserException {
		String userSQL = "UPDATE " + AUTHENTICATION_TABLE + "   SET status = '" + LoginConstant.DORMANT + "' "
				+ " WHERE LOGIN_ID IN ( " + "   SELECT LOGIN_ID    " + "     FROM " + USER_TABLE + " usr "
				+ "    WHERE usr.STATUS = '" + UserConstant.STATUS_DORMANT + "' ";
		if ((exclSuperUsers != null) && !"".equals(exclSuperUsers)) {
			userSQL += "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  )) ";
		}
		else {
			userSQL += "      )";
		}

		try {
			dbUtil = new DBUtil();
			try {
				dbUtil.setSQL(userSQL);
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query", e);
			}
			dbUtil.executeUpdate();
			dbUtil.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Exception at dormantUserAccounts() method", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new UserException("Exception at dormantUserAccounts() method", e);
			}
		}
	}

	public void dormantStageUser(String exclSuperUsers, int daysDormant) throws UserException {

		String userSQL = "UPDATE " + STAGE_USER_TABLE + "    SET status = '" + UserConstant.STATUS_DORMANT + "' "
				+ " WHERE user_id in ( " + "    SELECT stg.USER_ID " + "    FROM " + USER_TABLE + " usr, "
				+ STAGE_USER_TABLE + " stg, " + AUTHENTICATION_TABLE + " auth, " + TRANSACTION_TABLE + " trx "
				+ "    WHERE usr.USER_ID = trx.REFERENCE_ID " + "      AND stg.USER_ID = trx.STAGING_REFERENCE_ID  "
				+ "           AND usr.login_id = auth.login_id"
				+ " 		    AND (select trunc(TO_TIMESTAMP(gp.param_value,'DD/MM/YYYY')) from  cms_general_param gp where gp.param_code='APPLICATION_DATE')  -  trunc(auth.last_access_time) >= " + daysDormant
				+ "      		AND usr.STATUS = '" + UserConstant.STATUS_ACTIVE + "' ";
		if ((exclSuperUsers != null) && !"".equals(exclSuperUsers)) {
			userSQL += "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) ) ";
		}
		else {
			userSQL += "      )";
		}

		try {
			dbUtil = new DBUtil();
			try {
				dbUtil.setSQL(userSQL);
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query", e);
			}
			dbUtil.executeUpdate();
			dbUtil.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Exception at dormantUserAccounts() method", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new UserException("Exception at dormantUserAccounts() method", e);
			}
		}
	}
	
	//by Shiv 101212
	public void newDormantUserAccounts(String exclSuperUsers, int daysDormant) throws UserException {
		newDormantActualUser(exclSuperUsers, daysDormant);
		newDormantStageUser(exclSuperUsers, daysDormant);
		newDormantUserAuthentication(exclSuperUsers);
	}
	
	public void newDormantActualUser(String exclSuperUsers, int daysDormant) throws UserException {
		String userSQL = " UPDATE " + USER_TABLE + "    SET status = 'NR' "
				+ "  WHERE user_id IN ( " + "        SELECT usr.user_id" + "          FROM " + USER_TABLE + " usr, "
				+ AUTHENTICATION_TABLE + " auth, " + TRANSACTION_TABLE + " trans " + "         WHERE usr.login_id = auth.login_id"
				+ "     AND usr.user_id = trans.reference_id AND auth.last_access_time is null "
				+ " 		    AND (select trunc(TO_TIMESTAMP(gp.param_value,'DD/MM/YYYY')) from  cms_general_param gp where gp.param_code='APPLICATION_DATE')  -  trunc(trans.CREATION_DATE) >= " + daysDormant
				+ "      		AND usr.STATUS = '" + UserConstant.STATUS_ACTIVE + "' ";
		if ((exclSuperUsers != null) && !"".equals(exclSuperUsers)) {
			userSQL += "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) ) ";
		}
		else {
			userSQL += "      )";
		}

		try {
			dbUtil = new DBUtil();
			try {
				dbUtil.setSQL(userSQL);
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query", e);
			}
			dbUtil.executeUpdate();
			dbUtil.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Exception at dormantUserAccounts() method", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new UserException("Exception at dormantUserAccounts() method", e);
			}
		}
	}
	
	public void newDormantUserAuthentication(String exclSuperUsers) throws UserException {
		String userSQL = "UPDATE " + AUTHENTICATION_TABLE + "   SET status = '" + LoginConstant.DORMANT + "' "
				+ " WHERE LOGIN_ID IN ( " + "   SELECT LOGIN_ID    " + "     FROM " + USER_TABLE + " usr "
				+ "    WHERE usr.STATUS = 'NR' ";
		if ((exclSuperUsers != null) && !"".equals(exclSuperUsers)) {
			userSQL += "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  )) ";
		}
		else {
			userSQL += "      )";
		}

		try {
			dbUtil = new DBUtil();
			try {
				dbUtil.setSQL(userSQL);
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query", e);
			}
			dbUtil.executeUpdate();
			dbUtil.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Exception at dormantUserAccounts() method", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new UserException("Exception at dormantUserAccounts() method", e);
			}
		}
	}

	public void newDormantStageUser(String exclSuperUsers, int daysDormant) throws UserException {

		String userSQL = "UPDATE " + STAGE_USER_TABLE + "    SET status = 'NR' "
				+ " WHERE user_id in ( " + "    SELECT stg.USER_ID " + "    FROM " + USER_TABLE + " usr, "
				+ STAGE_USER_TABLE + " stg, " + AUTHENTICATION_TABLE + " auth, " + TRANSACTION_TABLE + " trx "
				+ "    WHERE usr.USER_ID = trx.REFERENCE_ID " + "      AND stg.USER_ID = trx.STAGING_REFERENCE_ID  "
				+ "           AND usr.login_id = auth.login_id AND auth.last_access_time is null "
				+ " 		    AND (select trunc(TO_TIMESTAMP(gp.param_value,'DD/MM/YYYY')) from  cms_general_param gp where gp.param_code='APPLICATION_DATE')  -  trunc(trx.CREATION_DATE)  >= " + daysDormant
				+ "      		AND usr.STATUS in ( '" + UserConstant.STATUS_ACTIVE + "' )";
		if ((exclSuperUsers != null) && !"".equals(exclSuperUsers)) {
			userSQL += "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) ) ";
		}
		else {
			userSQL += "      )";
		}

		try {
			dbUtil = new DBUtil();
			try {
				dbUtil.setSQL(userSQL);
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query", e);
			}
			dbUtil.executeUpdate();
			dbUtil.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Exception at dormantUserAccounts() method", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new UserException("Exception at dormantUserAccounts() method", e);
			}
		}
	}

}