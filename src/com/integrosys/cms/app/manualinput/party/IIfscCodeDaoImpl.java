package com.integrosys.cms.app.manualinput.party;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.customer.bus.IIfscMethod;
import com.integrosys.cms.app.customer.bus.OBIfscMethod;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.notification.bus.CMSNotificationException;
import com.integrosys.cms.app.notification.bus.OBCMSNotification;
import com.integrosys.component.notification.bus.INotification;

public class IIfscCodeDaoImpl extends HibernateDaoSupport implements IIfscCodeDao {
	private DBUtil dbUtil;
	private final static Logger logger = LoggerFactory.getLogger(IIfscCodeDaoImpl.class);
	public String getStageEntityName() {
		return IIfscCodeDao.STAGE_IFSC_CODE_NAME;
	}
	
	public String getActualEntityName() {
		return IIfscCodeDao.ACTUAL_IFSC_CODE_NAME;
	}

	public IIfscMethod createStageIfscCode(IIfscMethod ifscList) {
		DefaultLogger.debug(this, "In createStageIfscCode()...");
		IIfscMethod returnObj = new OBIfscMethod();
		DBUtil dbUtil = null;
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String strDate = dateFormat.format(date);
		System.out.println("strDate.........." + strDate);
		String seq = strDate + String.valueOf(getSequenceID());
		System.out.println("seq.........." + seq + "*");
		long strDatenum = Long.parseLong(seq);
		try {
			String query = "INSERT INTO CMS_STAGE_IFSC_CODE VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			dbUtil = new DBUtil();

			dbUtil.setSQL(query);
			dbUtil.setLong(1, strDatenum);
			dbUtil.setString(2, String.valueOf(ifscList.getCustomerId()));
			dbUtil.setString(3, ifscList.getNodal());
			dbUtil.setString(4, ifscList.getLead());
			dbUtil.setString(5, ifscList.getIfscCode());
			dbUtil.setString(6, ifscList.getBankName());
			dbUtil.setString(7, ifscList.getBranchName());
			dbUtil.setString(8, ifscList.getBranchNameAddress());
			dbUtil.setString(9, ifscList.getBankType());
			dbUtil.setString(10, ifscList.getEmailID());
			dbUtil.setString(11, ifscList.getStatus());
			dbUtil.setString(12, ifscList.getRevisedEmailID());
			dbUtil.executeUpdate();
			dbUtil.commit();
			
		} catch (Exception obe) {
			DefaultLogger.debug(this, obe.getMessage());
			obe.printStackTrace();
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnObj;
	}
	
	public void createActualIfscCode(String[] ifscList, String referenceId) {
		DefaultLogger.debug(this, "In createActualIfscCode()...");
		DBUtil dbUtil = null;
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String strDate = dateFormat.format(date);
		System.out.println("strDate.........." + strDate);
		
		try {
			for (int i = 0; i < ifscList.length; i++) {
				String seq = strDate + getSequenceID();
				System.out.println("seq.........." + seq + "*");
				long strDatenum = Long.parseLong(seq);
				IIfscMethod returnObj = new OBIfscMethod();
				returnObj = getIfscStageObj(new Long(ifscList[i]));
				returnObj.setCustomerId(Long.parseLong(referenceId));

				try {
					String query = "INSERT INTO CMS_IFSC_CODE VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
					dbUtil = new DBUtil();

					dbUtil.setSQL(query);
					dbUtil.setLong(1, strDatenum);
					dbUtil.setString(2, String.valueOf(returnObj.getCustomerId()));
					dbUtil.setString(3, returnObj.getNodal());
					dbUtil.setString(4, returnObj.getLead());
					dbUtil.setString(5, returnObj.getIfscCode());
					dbUtil.setString(6, returnObj.getBankName());
					dbUtil.setString(7, returnObj.getBranchName());
					dbUtil.setString(8, returnObj.getBranchNameAddress());
					dbUtil.setString(9, returnObj.getBankType());
					dbUtil.setString(10, returnObj.getEmailID());
					dbUtil.setString(11, returnObj.getStatus());
					dbUtil.setString(12, returnObj.getRevisedEmailID());
					dbUtil.executeUpdate();
					dbUtil.commit();
					
				} catch (Exception obe) {
					DefaultLogger.debug(this, obe.getMessage());
					obe.printStackTrace();
				}
				DefaultLogger.debug(this, "In createActualIfscCode()..");
			}
		} catch (Exception obe) {
			DefaultLogger.debug(this, obe.getMessage());
			obe.printStackTrace();
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public SearchResult getIfscCodeList(String custID){
		ArrayList resultList = new ArrayList();
		try{
			String query = "SELECT FROM "+getActualEntityName() + " WHERE STATUS!='INACTIVE' AND CMS_LE_MAIN_PROFILE_ID='"+custID+"' ORDER BY id";
			resultList = (ArrayList) getHibernateTemplate().find(query);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getIfscCodeList ",obe);
			obe.printStackTrace();
		}
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}
	
	public SearchResult getStageIfscCodeList(String custID){
		ArrayList resultList = new ArrayList();
		try{
			String query = "SELECT FROM "+getStageEntityName() + " WHERE STATUS!='INACTIVE' AND CMS_LE_MAIN_PROFILE_ID='"+custID+"' ORDER BY id";
			resultList = (ArrayList) getHibernateTemplate().find(query);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getIfscCodeList ",obe);
			obe.printStackTrace();
		}
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}
	
	public void disableActualIfscCode(String referenceId) {
		ArrayList resultList = new ArrayList();
		try{
			String query = "SELECT FROM "+getActualEntityName() + " WHERE STATUS='ACTIVE' AND CMS_LE_MAIN_PROFILE_ID='"+referenceId+"'";
			resultList = (ArrayList) getHibernateTemplate().find(query);
			
			if(null!=resultList) {
				for(int i=0;i<resultList.size();i++) {
					IIfscMethod obj=(IIfscMethod)resultList.get(i);
					obj.setStatus("INACTIVE");
					getHibernateTemplate().saveOrUpdate("actualIfsccode", obj);
				}
			}
			
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in disableActualIfscCode ",obe);
			obe.printStackTrace();
		}
	}

	public String getSequenceID() {
		int seq = 0;
		DBUtil dbUtil = null;
		String query = "select CMS_IFSC_CODE_SEQ.NEXTVAL from dual";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			ResultSet rs = dbUtil.executeQuery();
			if (rs.next()) {
				seq = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		String sequence= String.valueOf(seq);
		if(null!=sequence) {
			int size=9-sequence.length();
			for(int i=0;i<size;i++) {
				sequence="0"+sequence;
			}
		}
		
		return sequence;
	}
	
	
	public IIfscMethod getIfscStageObj(long id) {
		int seq = 0;
		DBUtil dbUtil = null;
		IIfscMethod ifscObj = new OBIfscMethod();
		String query = "select * from cms_stage_ifsc_code where id=" + id;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			ResultSet rs = dbUtil.executeQuery();
			if (rs.next()) {
				ifscObj.setCustomerId(rs.getLong(2));
				ifscObj.setNodal(rs.getString(3));
				ifscObj.setLead(rs.getString(4));
				ifscObj.setIfscCode(rs.getString(5));
				ifscObj.setBankName(rs.getString(6));
				ifscObj.setBranchName(rs.getString(7));
				ifscObj.setBranchNameAddress(rs.getString(8));
				ifscObj.setBankType(rs.getString(9));
				ifscObj.setEmailID(rs.getString(10));
				ifscObj.setStatus(rs.getString(11));
				ifscObj.setRevisedEmailID(rs.getString(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ifscObj;
	}
}
