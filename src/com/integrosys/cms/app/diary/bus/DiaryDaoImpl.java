package com.integrosys.cms.app.diary.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;

public class DiaryDaoImpl extends HibernateDaoSupport implements IDiaryDao {

	public IDiaryItemUI getDiaryItem(String entityName, Serializable key) {

		return (IDiaryItemUI) getHibernateTemplate().get(entityName, key);

	}

	public IDiaryItemUI createDiaryItem(String entityName, IDiaryItemUI item) {
		Long key = (Long) getHibernateTemplate().save(entityName, item);

		item.setItemID(key.longValue());
		return item;
	}

	public IDiaryItemUI updateDiaryItem(String entityName, IDiaryItemUI item) {
		getHibernateTemplate().update(entityName, item);

		return (IDiaryItemUI) getHibernateTemplate().load(entityName, new Long(item.getItemID()));

	}

	public SearchResult getAllDiaryItemList(String entityName) throws DiaryItemException {
		try {
			String query = "";
			ArrayList diaryItemList = new ArrayList();

			query = "FROM " + entityName + " where STATUS <> 'Closed' and IS_DELETE='N'";

			diaryItemList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, diaryItemList.size(), diaryItemList.size(), diaryItemList);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getAllDiaryItemList", obe);
			obe.printStackTrace();
			throw new DiaryItemException("Unable to get DiaryItem");
		}
	}

	public int getSegmentGenericCount(String segment,Long teamId) throws Exception {

		int count = 0;
		DBUtil dbUtil=null;
		try {
			
			segment = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segment+"')";
			String sql = "select count(1) from CMS_DIARY_ITEM where STATUS <> 'Closed' and IS_DELETE='N' and CUSTOMER_SEGMENT=" + segment
					+ " and DROP_LINE_OD='N' and TEAM_ID = '"+ teamId +"'";
			

			
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				// DefaultLogger.debug(this, sql);
			
				ResultSet rs = dbUtil.executeQuery();
				while (rs.next()) {
					count= rs.getInt(1);
				}

		} catch (Exception e) {
			System.out.println(e);
		}finally {
			if(null!=dbUtil )
				dbUtil.close();
		}

		return count;
	}

	public int getSegmentDroplineODCount(String segment,Long teamId) throws Exception {

		int count = 0;
		DBUtil dbUtil=null;
		try {
			segment = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segment+"')";
			String sql = "select count(1) from CMS_DIARY_ITEM where STATUS <> 'Closed' and IS_DELETE='N' and CUSTOMER_SEGMENT=" + segment
					+ " and DROP_LINE_OD='Y' and TEAM_ID = '"+ teamId +"'";
			

			
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				// DefaultLogger.debug(this, sql);
			
				ResultSet rs = dbUtil.executeQuery();
				while (rs.next()) {
					count= rs.getInt(1);
				}

		} catch (Exception e) {
			System.out.println(e);
		}finally {
			if(null!=dbUtil )
				dbUtil.close();
		}

		return count;
		
	}
	
	public SearchResult getGenericListWithSegment(String entityName,String segmentName) throws DiaryItemException {
		try {
			String query = "";
			ArrayList diaryItemList = new ArrayList();
			segmentName = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segmentName+"')";
			query = "FROM " + entityName + " where STATUS <> 'Closed' and IS_DELETE='N' and DROP_LINE_OD='N' and CUSTOMER_SEGMENT="+ segmentName +"";

			diaryItemList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, diaryItemList.size(), diaryItemList.size(), diaryItemList);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getGenericListWithSegment", obe);
			obe.printStackTrace();
			throw new DiaryItemException("Unable to get GenericListWithSegment");
		}
	}
	
	public SearchResult getGenericListWithoutSegment(String entityName) throws DiaryItemException {
		try {
			String query = "";
			ArrayList diaryItemList = new ArrayList();

			query = "FROM " + entityName + " where STATUS <> 'Closed' and IS_DELETE='N' and DROP_LINE_OD='N'";

			diaryItemList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, diaryItemList.size(), diaryItemList.size(), diaryItemList);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getGenericListWithoutSegment", obe);
			obe.printStackTrace();
			throw new DiaryItemException("Unable to get GenericListWithoutSegment");
		}
	}
	
	public SearchResult getDropLineListWithSegment(String entityName,String segmentName) throws DiaryItemException {
		try {
			String query = "";
			ArrayList diaryItemList = new ArrayList();
			segmentName = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segmentName+"')";
			query = "FROM " + entityName + " where STATUS <> 'Closed' and IS_DELETE='N' and DROP_LINE_OD='Y' and CUSTOMER_SEGMENT="+ segmentName +"";

			diaryItemList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, diaryItemList.size(), diaryItemList.size(), diaryItemList);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getGenericListWithSegment", obe);
			obe.printStackTrace();
			throw new DiaryItemException("Unable to get GenericListWithSegment");
		}
	}
	
	
	public SearchResult getDropLineListWithoutSegment(String entityName) throws DiaryItemException {
		try {
			String query = "";
			ArrayList diaryItemList = new ArrayList();

			query = "FROM " + entityName + " where STATUS <> 'Closed' and IS_DELETE='N' and DROP_LINE_OD='Y'";

			diaryItemList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, diaryItemList.size(), diaryItemList.size(), diaryItemList);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getGenericListWithoutSegment", obe);
			obe.printStackTrace();
			throw new DiaryItemException("Unable to get GenericListWithoutSegment");
		}
	}
	
	public SearchResult getTotalListWithSegment(String entityName,String segmentName) throws DiaryItemException {
		try {
			String query = "";
			ArrayList diaryItemList = new ArrayList();
			segmentName = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segmentName+"')";
			query = "FROM " + entityName + " where STATUS <> 'Closed' and IS_DELETE='N' and CUSTOMER_SEGMENT="+ segmentName +"";

			diaryItemList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, diaryItemList.size(), diaryItemList.size(), diaryItemList);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getGenericListWithSegment", obe);
			obe.printStackTrace();
			throw new DiaryItemException("Unable to get GenericListWithSegment");
		}
	}

	public int getGenericTodayCount(String applicationDate,Long teamId) throws Exception {

		int count = 0;
		DBUtil dbUtil=null;
		try {
			String sql = "select count(*) from cms_diary_item where team_id = '"+ teamId +"'" 
					+ " and DROP_LINE_OD='N' and STATUS <> 'Closed'" 
			        + " and IS_DELETE='N' and CAST(ITEM_DUE_DATE AS DATE) = to_date('"+ applicationDate +"','dd-mm-yyyy')";
			
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				// DefaultLogger.debug(this, sql);
			
				ResultSet rs = dbUtil.executeQuery();
				while (rs.next()) {
					count= rs.getInt(1);
				}

		} catch (Exception e) {
			System.out.println(e);
		}finally {
			if(null!=dbUtil )
				dbUtil.close();
		}

		return count;
		
	}
	
	public int getDroplineTodayCount(String applicationDate,Long teamId) throws Exception {

		int count = 0;
		DBUtil dbUtil=null;
		try {
			String sql = "select count(*) from CMS_DIARY_SCHEDULE_DATA where team_id = '"+ teamId +"'"
					+ " and IS_CLOSED='N' and CAST(ITEM_DUE_DATE AS DATE) = to_date('"+ applicationDate +"','dd-mm-yyyy')";
			
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				// DefaultLogger.debug(this, sql);
			
				ResultSet rs = dbUtil.executeQuery();
				while (rs.next()) {
					count= rs.getInt(1);
				}

		} catch (Exception e) {
			System.out.println(e);
		}finally {
			if(null!=dbUtil )
				dbUtil.close();
		}

		return count;
		
	}
	
	public int getOverdueGenericTodayCount(String applicationDate,Long teamId) throws Exception {

		int count = 0;
		DBUtil dbUtil=null;
		try {
			String sql = "select count(*) from cms_diary_item where team_id = '"+ teamId +"'"
					+ " and DROP_LINE_OD='N' and STATUS <> 'Closed'" 
			        + " and IS_DELETE='N' and CAST(ITEM_DUE_DATE AS DATE) < to_date('"+ applicationDate +"','dd-mm-yyyy')";
			
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				// DefaultLogger.debug(this, sql);
			
				ResultSet rs = dbUtil.executeQuery();
				while (rs.next()) {
					count= rs.getInt(1);
				}

		} catch (Exception e) {
			System.out.println(e);
		}finally {
			if(null!=dbUtil )
				dbUtil.close();
		}

		return count;
		
	}
	
	public int getOverdueDroplineTodayCount(String applicationDate,Long teamId) throws Exception {

		int count = 0;
		DBUtil dbUtil=null;
		try {
			String sql = "select count(*) from CMS_DIARY_SCHEDULE_DATA where team_id = '"+ teamId +"'"
					+ " and IS_CLOSED='N' and CAST(ITEM_DUE_DATE AS DATE) < to_date('"+ applicationDate +"','dd-mm-yyyy')";
			
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				// DefaultLogger.debug(this, sql);
			
				ResultSet rs = dbUtil.executeQuery();
				while (rs.next()) {
					count= rs.getInt(1);
				}

		} catch (Exception e) {
			System.out.println(e);
		}finally {
			if(null!=dbUtil )
				dbUtil.close();
		}

		return count;
		
	}
	
	public int getSegmentGenericTodayCount(String segment,String applicationDate,Long teamId) throws Exception {

		int count = 0;
		DBUtil dbUtil=null;
		try {
			segment = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segment+"')";
			String sql = "select count(*) from CMS_DIARY_ITEM where team_id = '"+ teamId +"'"
					+ " and DROP_LINE_OD = 'N' and STATUS <> 'Closed'" 
			        + " and IS_DELETE='N' and CUSTOMER_SEGMENT = " + segment + ""
			        + " and CAST(ITEM_DUE_DATE AS DATE) = to_date('"+ applicationDate +"','dd-mm-yyyy')";
			
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				// DefaultLogger.debug(this, sql);
			
				ResultSet rs = dbUtil.executeQuery();
				while (rs.next()) {
					count= rs.getInt(1);
				}

		} catch (Exception e) {
			System.out.println(e);
		}finally {
			if(null!=dbUtil )
				dbUtil.close();
		}

		return count;
	}
	
	public int getSegmentDroplineTodayCount(String segment,String applicationDate,Long teamId) throws Exception {

		int count = 0;
		DBUtil dbUtil=null;
		try {
			segment = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segment+"')";
			String sql = "select count(*) from CMS_DIARY_SCHEDULE_DATA dsd where team_id = '"+ teamId +"'" 
					+ " and IS_CLOSED = 'N' and " + segment + " = (select CUSTOMER_SEGMENT from CMS_DIARY_ITEM "
					+ " where dsd.DIARY_NUMBER = CMS_DIARY_ITEM.DIARY_NUMBER)"
					+ " and CAST(dsd.ITEM_DUE_DATE AS DATE) = to_date('"+ applicationDate +"','dd-mm-yyyy')";
			
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				// DefaultLogger.debug(this, sql);
			
				ResultSet rs = dbUtil.executeQuery();
				while (rs.next()) {
					count= rs.getInt(1);
				}

		} catch (Exception e) {
			System.out.println(e);
		}finally {
			if(null!=dbUtil )
				dbUtil.close();
		}

		return count;
	}
	
	public int getOverdueSegmentGenericTodayCount(String segment,String applicationDate,Long teamId) throws Exception {

		int count = 0;
		DBUtil dbUtil=null;
		try {
			segment = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segment+"')";
			String sql = "select count(*) from CMS_DIARY_ITEM where team_id = '"+ teamId +"'"
					+ " and DROP_LINE_OD = 'N' and STATUS <> 'Closed'" 
			        + " and IS_DELETE='N' and CUSTOMER_SEGMENT = " + segment + ""
			        + " and CAST(ITEM_DUE_DATE AS DATE) < to_date('"+ applicationDate +"','dd-mm-yyyy')";
			
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				// DefaultLogger.debug(this, sql);
			
				ResultSet rs = dbUtil.executeQuery();
				while (rs.next()) {
					count= rs.getInt(1);
				}

		} catch (Exception e) {
			System.out.println(e);
		}finally {
			if(null!=dbUtil )
				dbUtil.close();
		}

		return count;
	}
	
	public int getOverdueSegmentDroplineTodayCount(String segment,String applicationDate,Long teamId) throws Exception {

		int count = 0;
		DBUtil dbUtil=null;
		try {
			segment = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segment+"')";
			String sql = "select count(*) from CMS_DIARY_SCHEDULE_DATA dsd where team_id = '"+ teamId +"'"
					+ " and IS_CLOSED = 'N' and " + segment + " = (select CUSTOMER_SEGMENT from CMS_DIARY_ITEM "
					+ " where dsd.DIARY_NUMBER = CMS_DIARY_ITEM.DIARY_NUMBER)"
					+ " and CAST(dsd.ITEM_DUE_DATE AS DATE) < to_date('"+ applicationDate +"','dd-mm-yyyy')";
			
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				// DefaultLogger.debug(this, sql);
			
				ResultSet rs = dbUtil.executeQuery();
				while (rs.next()) {
					count= rs.getInt(1);
				}

		} catch (Exception e) {
			System.out.println(e);
		}finally {
			if(null!=dbUtil )
				dbUtil.close();
		}

		return count;
	}
	
	/*
	 * public ArrayList getRegionAndSegment(String legalReference) { // TODO
	 * Auto-generated method stub
	 * 
	 * List resultList = null; try { Query q = getSession().
	 * createQuery("select lmt.LMP_SGMNT_CODE_VALUE,lmt.region from SCI_LE_MAIN_PROFILE lmt where lmt.LMP_LE_ID = '"
	 * +legalReference+"'"); StringBuffer hqlBuf = new StringBuffer(); //
	 * hqlBuf.
	 * append("select lmt.LMP_SGMNT_CODE_VALUE,lmt.region from SCI_LE_MAIN_PROFILE lmt where lmt.LMP_LE_ID = '"
	 * +legalReference+"'");
	 * 
	 * resultList = getHibernateTemplate().find(q.getQueryString());
	 * 
	 * 
	 * 
	 * }catch(Exception e) { System.out.println(e); } return (ArrayList)
	 * resultList; }
	 */

}
