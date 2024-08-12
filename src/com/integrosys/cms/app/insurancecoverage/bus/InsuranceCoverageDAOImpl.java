package com.integrosys.cms.app.insurancecoverage.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue;
import com.integrosys.cms.app.insurancecoveragedtls.bus.IInsuranceCoverageDtlsDAO;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * Purpose : This InsuranceCoverageDAOImpl implements the methods that will be available to the
 * operating on a Insurance Coverage 
 *  
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 */
public class InsuranceCoverageDAOImpl extends HibernateDaoSupport implements IInsuranceCoverageDAO {
	/**
	 * @return String entity name
	 */
	public String getEntityName(){
		return IInsuranceCoverageDAO.ACTUAL_ENTITY_NAME; 
	}
	
	private DBUtil dbUtil = null;
	/**
	 * returns SearchResult List of Insurance Coverage
	 */
	public SearchResult getInsuranceCoverage() throws InsuranceCoverageException{

		try{
			ArrayList resultList = (ArrayList) getHibernateTemplate().loadAll(OBInsuranceCoverage.class);
			return new SearchResult(0, resultList.size(), resultList.size(), resultList);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getInsuranceCoverage",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageException("Unable to get Insurance Coverage");
		}
	}

	/**
	 * Returns Insurance Coverage present for the  input bank id
	 */
	public IInsuranceCoverage getInsuranceCoverageById(long id) throws InsuranceCoverageException {
		IInsuranceCoverage InsuranceCoverage = new OBInsuranceCoverage();
		try{
			InsuranceCoverage = (IInsuranceCoverage)getHibernateTemplate().load(getEntityName(), new Long(id));
			return InsuranceCoverage;
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getInsuranceCoverageById ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageException("Unable to find Insurance Coverage with id ["+id+"]");
		}
	
	}

	/**
	 * Return List of Insurance Coverage.
	 */
	public SearchResult getInsuranceCoverageList(String icCode,String companyName) throws InsuranceCoverageException{

		try{
			String query = "SELECT FROM "+getEntityName()+" WHERE DEPRECATED!='Y'";
			if(icCode != null && !icCode.trim().equals("")){
				query = query + " AND UPPER(INSURANCE_COVERAGE_CODE) like'"+icCode.toUpperCase()+"%' ";
			}
			if(companyName!=null && !companyName.trim().equals("")){
				query = query + " AND UPPER(COMPANY_NAME) like'"+companyName.toUpperCase()+"%' ";
			}
			query = query + " ORDER BY INSURANCE_COVERAGE_CODE";
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, resultList.size(), resultList.size(), resultList);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getInsuranceCoverageList ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageException("Unable to get Insurance Coverage List");
		}
		
	}
	
	/*public ArrayList getInsuranceDetailList(Long colId,String compnent) throws InsuranceCoverageException{

		try{
			String query = "select * from cms_asset_gc_STOCK_det det "+
			" where det.gc_det_id IN	("+
			"select gc.gc_det_id from cms_asset_gc_det gc where gc.doc_code=latest_gc_doccode("+colId+")"+
			")"+
			"and det.stock_type='CurrentAsset'"+
			"AND DET.HAS_INSURANCE='Y'"+
			"and component='"+compnent+"'";			
			
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return resultList;
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getInsuranceCoverageList ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageException("Unable to get Insurance Coverage List");
		}
		
	}
	*/
	
	public ArrayList getInsuranceDetailList(Long colId,String compnent) throws InsuranceCoverageException{
	 {
		
		 String sql = "select INSURANCE_COMPANY_NAME, HAS_INSURANCE,INSURANCE_COMPANY_CATEGORY," +
		 		"INSURANCE_DESCRIPTION," +
		 		"INSURED_AMOUNT," +
		 		"INSURANCE_EFFECTIVE_DATE," +
		 		"INSURANCE_EXPIRY_DATE," +
		 		"TOTAL_POLICY_AMOUNT," +
		 		"INSURANCE_POLICY_NO," +
		 		"INSURANCE_COVER_NOTE," +
		 		"INSURANCE_RECIVED_DATE," +
		 		"INSURANCE_DEFAULTED," +
		 		"INSURANCE_PREMIUM from cms_asset_gc_STOCK_det det "+
			" where det.gc_det_id IN	("+
			" select gc.gc_det_id from cms_asset_gc_det gc where gc.doc_code=latest_gc_doccode("+colId+")"+
			" and gc.cms_collateral_id = "+colId+
			" )"+
			" and det.stock_type='CurrentAsset'"+
			" AND DET.HAS_INSURANCE='Y'"+
			" and component='"+compnent+"'";		
	
	     
	     String value = null;
	     StringBuffer strBuffer = new StringBuffer(sql.trim());
	     try
	     {
	    	 ArrayList list = new ArrayList();
	     
	    	 dbUtil = new DBUtil();
				//println(strBuffer.toString());
				try {
					dbUtil.setSQL(strBuffer.toString());
					
				}
				catch (SQLException e) {
					throw new SearchDAOException("Could not set SQL query statement", e);
				}
				ResultSet rs = dbUtil.executeQuery();
				
				if (rs.next()) {						
					list.add(0, rs.getString("INSURANCE_COMPANY_NAME"));	
					list.add(1, rs.getString("HAS_INSURANCE"));
					list.add(2, rs.getString("INSURANCE_COMPANY_CATEGORY"));
					list.add(3, rs.getString("INSURANCE_DESCRIPTION"));
					list.add(4, rs.getString("INSURED_AMOUNT"));
					list.add(5, rs.getDate("INSURANCE_EFFECTIVE_DATE"));
					list.add(6, rs.getDate("INSURANCE_EXPIRY_DATE"));
					list.add(7, rs.getString("TOTAL_POLICY_AMOUNT"));
					list.add(8, rs.getString("INSURANCE_POLICY_NO"));
					list.add(9, rs.getString("INSURANCE_COVER_NOTE"));
					list.add(10, rs.getDate("INSURANCE_RECIVED_DATE"));
					list.add(11, rs.getString("INSURANCE_DEFAULTED"));
					list.add(12, rs.getString("INSURANCE_PREMIUM"));
					
				}
				
	         
	         return list;
	        
	     }
	     catch(Exception e)
	     {
	         throw new InsuranceCoverageException("failed to retrieve MIGRATED PARTY ", e);
	     }
	     finally{
	    	 try {
				dbUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	 }
	}
	/*public SearchResult getInsuranceDetailList(Long colId,String compnent) throws InsuranceCoverageException{
		

		String sql = "select * from cms_asset_gc_STOCK_det det"+
		"where det.gc_det_id IN	("+
		"select gc.gc_det_id from cms_asset_gc_det gc where gc.doc_code=latest_gc_doccode(?)"+
		")"+
		"and det.stock_type='CurrentAsset'"+
		"AND DET.HAS_INSURANCE='Y'"+
		"and component=?";	

		ICollateralSubType resultSubType = (ICollateralSubType) getHibernateTemplate().query(sql, new Object[] { colId,compnent },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							ICollateralSubType subType = new OBCollateralSubType();
							String subTypeName = rs.getString("SUBTYPE_NAME");
							String typeName = rs.getString("TYPE_NAME");
							subType.setSubTypeName(subTypeName);
							subType.setTypeName(typeName);
							return subType;
						}
						return null;
					}
				});

		return resultSubType;

	}*/
	
	/**
	 * Return List of Insurance Coverage.
	 */
	public SearchResult getInsuranceCoverageDtlsList(long id) throws InsuranceCoverageException{

		try{
			String query = "SELECT FROM "+IInsuranceCoverageDtlsDAO.ACTUAL_ENTITY_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND INSURANCE_COVERAGE_CODE="+id;
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, resultList.size(), resultList.size(), resultList);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getInsuranceCoverageList ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageException("Unable to get Insurance Coverage List");
		}
		
	}

	/**
	 * Updates the Insurance Coverage
	 */
	public IInsuranceCoverage updateInsuranceCoverage(IInsuranceCoverage InsuranceCoverage) throws InsuranceCoverageException {

		try{
			getHibernateTemplate().saveOrUpdate(getEntityName(), InsuranceCoverage);
			IInsuranceCoverage returnObj = (IInsuranceCoverage) getHibernateTemplate().load(getEntityName(),new Long(InsuranceCoverage.getId()));
			return returnObj;
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in updateInsuranceCoverage ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageException("Unable to update Insurance Coverage with id ["+InsuranceCoverage.getId()+"]");
		}
		
	}
	
	/**
	 * soft delete the Insurance Coverage
	 */
	
	public IInsuranceCoverage deleteInsuranceCoverage(IInsuranceCoverage InsuranceCoverage) throws InsuranceCoverageException {
		IInsuranceCoverage returnObj = new OBInsuranceCoverage();
		try{
			InsuranceCoverage.setStatus("INACTIVE");
			InsuranceCoverage.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(getEntityName(), InsuranceCoverage);
			returnObj = (IInsuranceCoverage) getHibernateTemplate().load(getEntityName(),new Long(InsuranceCoverage.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in deleteInsuranceCoverage ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageException("Unable to delete Insurance Coverage with id ["+InsuranceCoverage.getId()+"]");
		}
		return returnObj;
	}
	
	/**
	 * Create the Insurance Coverage
	 */
	public IInsuranceCoverage createInsuranceCoverage(IInsuranceCoverage InsuranceCoverage) throws InsuranceCoverageException {
		IInsuranceCoverage returnObj = new OBInsuranceCoverage();
		try{
			getHibernateTemplate().save(getEntityName(), InsuranceCoverage);
			returnObj = (IInsuranceCoverage) getHibernateTemplate().load(getEntityName(),new Long(InsuranceCoverage.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in createInsuranceCoverage ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageException("Unable to create Insurance Coverage ");
		}
		return returnObj;
	}

	/**
	 * @return InsuranceCoverage Object
	 * @param Entity Name
	 * @param Key  
	 * This method returns Insurance Coverage Object
	 */
	public IInsuranceCoverage getInsuranceCoverage(String entityName, Serializable key)throws InsuranceCoverageException {
		if(!(entityName==null|| key==null)){
		return (IInsuranceCoverage) getHibernateTemplate().get(entityName, key);
		}else{
			throw new InsuranceCoverageException("ERROR- Entity name or key is null ");
		}
	}

	/**
	 * @return InsuranceCoverage Object
	 * @param Entity Name
	 * @param InsuranceCoverage Object  
	 * This method Updates Insurance Coverage Object
	 */
	public IInsuranceCoverage updateInsuranceCoverage(String entityName, IInsuranceCoverage item)throws InsuranceCoverageException {
		if(!(entityName==null|| item==null)){
		getHibernateTemplate().update(entityName, item);

		return (IInsuranceCoverage) getHibernateTemplate().load(entityName,
				new Long(item.getId()));
		}else{
			throw new InsuranceCoverageException("ERROR- Entity name or key is null ");
		}

	}
	/**
	 * @return InsuranceCoverage Object
	 * @param Entity Name
	 * @param InsuranceCoverage Object  
	 * This method Creates Insurance Coverage Object
	 */

	public IInsuranceCoverage createInsuranceCoverage(String entityName,
			IInsuranceCoverage insuranceCoverage)throws InsuranceCoverageException {
		if(!(entityName==null|| insuranceCoverage==null)){	
			if( insuranceCoverage.getInsuranceCoverageCode() == null || insuranceCoverage.getInsuranceCoverageCode().equals("")){
				String insuranceCode=getInsuranceCoverageCode();
				insuranceCoverage.setInsuranceCoverageCode(insuranceCode);
			}
			Long key = (Long) getHibernateTemplate().save(entityName, insuranceCoverage);
			insuranceCoverage.setId(key.longValue());
			return insuranceCoverage;
		}else{
			throw new InsuranceCoverageException("ERROR- Entity name or key is null ");
		}
	}
	
	private String getInsuranceCoverageCode() {
		Query query = currentSession().createSQLQuery("SELECT INSURANCE_COVERAGE_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String insuranceCode = numberFormat.format(Long.parseLong(sequenceNumber));
		insuranceCode = "INS" + insuranceCode;		
		return insuranceCode;
	}

	public boolean isICCodeUnique(String rmCode){
		String query = "SELECT FROM "+IInsuranceCoverageDAO.STAGING_ENTITY_NAME+" WHERE INSURANCE_COVERAGE_CODE='"+rmCode+"'";
		ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
		if(resultList.size()>0){
			String query1 = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND INSURANCE_COVERAGE_CODE='"+rmCode+"'";
			ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
			if(resultList1.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;
		 
	}
	
	
//################### File Upload ###########################
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId insertInsuranceCoverage(String entityName,
			IFileMapperId fileId, IInsuranceCoverageTrxValue trxValue)
			throws InsuranceCoverageException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new InsuranceCoverageException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertInsuranceCoverage(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
		// TODO Auto-generated method stub
		int noOfRecInserted = 0;
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		Long key=null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException(
					"Data to be instered to DB is null or empty");
		}
		try {
			for (int index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				OBInsuranceCoverage obInsuranceCoverage = new OBInsuranceCoverage();
				String insuranceCode = (String) eachDataMap.get("INSURANCE_COVERAGE_CODE");
				String contactNumber = (String) eachDataMap.get("CONTACT_NUMBER");
				
				
				if( insuranceCode == null || insuranceCode.equals("")  )
					obInsuranceCoverage.setInsuranceCoverageCode(getInsuranceCoverageCode());
				else
					obInsuranceCoverage.setInsuranceCoverageCode(insuranceCode );
				
				obInsuranceCoverage.setCompanyName((String) eachDataMap.get("COMPANY_NAME"));
				obInsuranceCoverage.setAddress((String)eachDataMap.get("ADDRESS"));
				if( contactNumber != null && !contactNumber.trim().equals("")  ){
				obInsuranceCoverage.setContactNumber(Long.parseLong(eachDataMap.get("CONTACT_NUMBER").toString()));
				}
				obInsuranceCoverage.setLastUpdateDate(new Date());
				obInsuranceCoverage.setCreatedBy(userName);
				obInsuranceCoverage.setCreationDate(new Date());
				obInsuranceCoverage.setStatus("ACTIVE");
				obInsuranceCoverage.setDeprecated("N");
				obInsuranceCoverage.setLastUpdateBy(userName);
				key = (Long) getHibernateTemplate().save("stagingInsuranceCoverage", obInsuranceCoverage);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
					getHibernateTemplate().get("fileMapper", key);
					}else{
						throw new InsuranceCoverageException("ERROR- Entity name or key is null ");
					}
				noOfRecInserted++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
					"Unable to update/insert dad retrived form CSV file");

		}
		return noOfRecInserted;
	}

	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId createFileId(String entityName, IFileMapperId fileId)
	throws InsuranceCoverageException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new InsuranceCoverageException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws InsuranceCoverageException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new InsuranceCoverageException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	/**
	 * @return InsuranceCoverage Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates InsuranceCoverage Object
	 */
	public IInsuranceCoverage insertActualInsuranceCoverage(String sysId)
	throws InsuranceCoverageException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("stagingInsuranceCoverage").add(Restrictions.allEq(parametersMap));
		List InsuranceCoverageList = getHibernateTemplate().findByCriteria(criteria);
			return (IInsuranceCoverage)InsuranceCoverageList.get(0);
		}
	/**
	 * @return InsuranceCoverage Object
	 * @param Entity Name
	 * @param InsuranceCoverage Object  
	 * This method Creates InsuranceCoverage Object
	 */
	public IInsuranceCoverage insertInsuranceCoverage(String entityName,
			IInsuranceCoverage InsuranceCoverage)
			throws InsuranceCoverageException {
		if(!(entityName==null|| InsuranceCoverage==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, InsuranceCoverage);
			InsuranceCoverage.setId(key.longValue());
			return InsuranceCoverage;
			}else{
				throw new InsuranceCoverageException("ERROR- Entity name or key is null ");
			}
	}
	
	//************************************************************************
	//********************* Methods for File Upload **************************
	//************************************************************************
	
	public List getAllStageInsuranceCoverage(String searchBy, String login) {
		List resultList = null;
		String searchByValue = searchBy;
		String strId ="";
		List listId = getFileMasterList(searchBy);
		for (int i = 0; i < listId.size(); i++) {
			OBFileMapperMaster map = (OBFileMapperMaster) listId.get(i);
//				System.out.println("val = " + map.getSysId());
				if(!strId.equals("")){
					strId +=",";
				}
				strId += map.getSysId();
		}
		
		if(!strId.equals("")){
			Map parameters = new HashMap();
			parameters.put("deprecated","N");
			parameters.put("id", strId);
			String query = "SELECT FROM "+IInsuranceCoverageDAO.STAGING_ENTITY_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new InsuranceCoverageException("ERROR-- While retriving InsuranceCoverage");
			}
		}
		return resultList;
	}
	
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_INSU_CVRG'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_INSU_CVRG' AND status != 'ACTIVE'";
			List holidayList = getHibernateTemplate().find(sqlQuery);
			if(holidayList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return list of files uploaded in staging table of InsuranceCoverage.
	 */
	public List getFileMasterList(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}

	public boolean isCompanyNameUnique(String companyName) {
		String stagingQuery = "SELECT FROM "+IInsuranceCoverageDAO.STAGING_ENTITY_NAME+" WHERE UPPER(company_name) like '"+companyName.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+IInsuranceCoverageDAO.ACTUAL_ENTITY_NAME+" WHERE UPPER(company_name) like '"+companyName.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 )
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_INSU_CVRG' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);			
	}

	
	public List getInsuranceCoverageList()throws InsuranceCoverageException {
		List resultList=new ArrayList();
		String query="SELECT FROM "+IInsuranceCoverageDAO.ACTUAL_ENTITY_NAME+" WHERE STATUS= 'ACTIVE'";
		try{
			resultList = getHibernateTemplate().find(query);
		}
		catch(Exception ex){
			throw new RelationshipMgrException("ERROR-- While retriving CompanyList");
			
		}
		return resultList;
	}
	
	public String getInsuranceCompanyName(String icCode) throws InsuranceCoverageException{

		String companyName = "";
		try{
			String query = "SELECT FROM "+getEntityName()+" WHERE DEPRECATED!='Y'";
			if(icCode != null && !icCode.trim().isEmpty()){
				query = query + " AND UPPER(INSURANCE_COVERAGE_CODE) = '"+icCode.toUpperCase()+"' ";
			}
			List<Object> resultList = (ArrayList) getHibernateTemplate().find(query);
			
			if(resultList!=null && resultList.size()>0){
				companyName =  ((OBInsuranceCoverage)resultList.get(0)).getCompanyName();
			}
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getInsuranceCoverageList ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageException("Unable to get Insurance Company Name");
		}
		return companyName;
		
	}
}
