/*
 * Created on Apr 9, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.aa.bus;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class TradeAgreementDAO {
	public String getAgreementByAA(String aaId) throws SearchDAOException {
		String query = "SELECT AGREEMENT_TYPE FROM CMS_TRADING_AGREEMENT WHERE CMS_LSP_LMT_PROFILE_ID = ? AND STATUS = 'ACTIVE'";
		DBUtil curUtil = null;
		ResultSet rs = null;
		try {
			String agreementType = "";
			curUtil = new DBUtil();
			curUtil.setSQL(query);
			curUtil.setString(1, aaId);
			rs = curUtil.executeQuery();
			if (rs.next()) {
				agreementType = rs.getString("AGREEMENT_TYPE");
			}
			return agreementType;
		}
		catch (Exception ex) {
			finalize(curUtil,rs);
			ex.printStackTrace();
			throw new SearchDAOException();
		}
	}

	public Map getAgreementsByAA(List aaList) throws SearchDAOException {
		String query = "SELECT AGREEMENT_TYPE, CMS_LSP_LMT_PROFILE_ID FROM CMS_TRADING_AGREEMENT WHERE CMS_LSP_LMT_PROFILE_ID IN (#aalist#) AND STATUS = 'ACTIVE'";
		DBUtil curUtil = null;
		ResultSet rs = null;
		Map resMap = new HashMap();
		try {
			StringBuffer sb = new StringBuffer(query);
			int startInd = query.indexOf("#aalist#");
			int endInd = startInd + "#aalist#".length();
			sb.replace(startInd, endInd, getParamListStr(aaList));
			curUtil = new DBUtil();
			curUtil.setSQL(sb.toString());
			rs = curUtil.executeQuery();
			while (rs.next()) {
				String agreementType = rs.getString("AGREEMENT_TYPE");
				String aaId = rs.getString("CMS_LSP_LMT_PROFILE_ID");
				resMap.put(aaId, agreementType);
			}
			return resMap;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			finalize(curUtil, rs);
			throw new SearchDAOException();
		}
	}

	private String getParamListStr(List paramList) {
		String res = "";
		if (paramList != null) {
			for (int i = 0; i < paramList.size(); i++) {
				res = res + (String) (paramList.get(i)) + ", ";
			}
		}
		if (res.endsWith(", ")) {
			res = res.substring(0, res.length() - 2);
		}
		return res;
	}

	public String getFacilityGroupByAA(String aaId) throws SearchDAOException {
		String query = "SELECT LMT_PROFILE_TYPE FROM SCI_LSP_LMT_PROFILE WHERE CMS_LSP_LMT_PROFILE_ID = ?";
		DBUtil curUtil = null;
		ResultSet rs = null;
		try {
			String facGroup = "";
			curUtil = new DBUtil();
			curUtil.setSQL(query);
			curUtil.setString(1, aaId);
			rs = curUtil.executeQuery();
			if (rs.next()) {
				facGroup = rs.getString("LMT_PROFILE_TYPE");
			}
			return facGroup;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			finalize(curUtil, rs);
			throw new SearchDAOException();
		}
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
}
