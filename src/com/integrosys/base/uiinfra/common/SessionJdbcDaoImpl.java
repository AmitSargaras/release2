package com.integrosys.base.uiinfra.common;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.component.bizstructure.app.constants.BizstructureConstant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
/**
 * Implementation of SessionJdbcDao using Spring framework Jdbc routine.
 * 
 * @author Chong Jun Yong
 * 
 */
public class SessionJdbcDaoImpl extends JdbcDaoSupport implements SessionJdbcDao {

	public int clearSession() {
		return getJdbcTemplate().update("delete from cms_user_session");
	}

	public void clearSessionByLoginId(String loginId) {
		String sql = "delete from cms_user_session s where s.user_id = "
				+ " (select user_id from cms_user usr where login_id = ? and status = ? )";

		getJdbcTemplate().update(sql, new Object[] { loginId, BizstructureConstant.STATUS_ACTIVE });
	}

	public void clearSessionBySessionId(String sessionId) {
		String sql = "delete from cms_user_session s where s.session_id = ?";
		ILimitDAO limitDAO = LimitDAOFactory.getDAO();
		limitDAO.deleteLimitProfileID();
		getJdbcTemplate().update(sql, new Object[] { sessionId });
	
	}

}