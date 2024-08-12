package com.integrosys.cms.app.directorMaster.bus;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Dao implication of interface DirectorMaster
 */

public class DirectorMasterDaoImpl extends HibernateDaoSupport implements IDirectorMasterDao{
	
	/**
	  * @return Particular Director Master according 
	  * to the id passed as parameter.  
	  * @param Din No
	  */

	public IDirectorMaster getDirectorMaster(String entityName, Serializable key)throws DirectorMasterException {
		
		if(!(entityName==null|| key==null)){
		
		return (IDirectorMaster) getHibernateTemplate().get(entityName, key);
		}else{
			throw new DirectorMasterException("ERROR-- Entity Name Or Key is null in get");
		}
	}
	
	/**
	 * @return DirectorMaster Object
	 * @param Entity Name
	 * @param DirectorMaster Object  
	 * This method Updates Director Master h Object
	 */
	
	public IDirectorMaster updateDirectorMaster(String entityName, IDirectorMaster item)throws DirectorMasterException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IDirectorMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new DirectorMasterException("ERROR-- Entity Name Or Key is null in update");
		}
	}
	/**
	 * @return DirectorMaster Object
	 * @param Entity Name
	 * @param DirectorMaster Object  
	 * This method  Director Master Object
	 */
	
	public IDirectorMaster disableDirectorMaster(String entityName, IDirectorMaster item)throws DirectorMasterException{

		if(!(entityName==null|| item==null)){
				item.setStatus("INACTIVE");
				Long key = (Long) getHibernateTemplate().save(entityName, item);
				item.setId(key.longValue());
				return item;
			}else{
				throw new DirectorMasterException("ERROR-- Entity Name Or Key is null in disable");
			}
	}
	/**
	 * @return DirectorMaster Object
	 * @param Entity Name
	 * @param DirectorMaster Object  
	 * This method enable Director Master Object
	 */
	
	public IDirectorMaster enableDirectorMaster(String entityName, IDirectorMaster item)throws DirectorMasterException{

		if(!(entityName==null|| item==null)){
		
			item.setStatus("ACTIVE");
			Long key = (Long) getHibernateTemplate().save(entityName, item);
			item.setId(key.longValue());
				
		return item;
		}else{
			throw new DirectorMasterException("ERROR-- Entity Name Or Key is null in enable");
		}
	}
		
	/**
	 * @return DirectorMaster Object
	 * @param Entity Name
	 * @param DirectorMaster Object  
	 * This method Creates Director Master Object
	 */
	public IDirectorMaster createDirectorMaster(String entityName,
			IDirectorMaster directorMaster)
			throws DirectorMasterException {
		if(!(entityName==null|| directorMaster==null)){
			if( directorMaster.getDirectorCode() == null || directorMaster.getDirectorCode().equals("")){
				String directorCode=getDirectorMasterCode();
				directorMaster.setDirectorCode(directorCode);
			}			
			Long key = (Long) getHibernateTemplate().save(entityName, directorMaster);
			directorMaster.setId(key.longValue());
			return directorMaster;
			}else{				
				throw new DirectorMasterException("ERROR- Entity name or key is null in create ");
			}
	}

	private String getDirectorMasterCode() {
		/* Changed the hibernate version from 3 to 4
		 * Query query = getSession().createSQLQuery("SELECT DIRECTOR_SEQ.NEXTVAL FROM dual");*/
		Query query = currentSession().createSQLQuery("SELECT DIRECTOR_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String directorCode = numberFormat.format(Long.parseLong(sequenceNumber));
		directorCode = "DIR" + directorCode;		
		return directorCode;
	}
	/**
	  * @return Particular Director Master according 
	  * to the id passed as parameter.  
	  * @param Entity Name 
	  */

	public IDirectorMaster load(String entityName, long id)throws DirectorMasterException
	{
		if(!(entityName==null|| id==0)){
		return (IDirectorMaster)getHibernateTemplate().get(entityName , new Long(id));	
		}else{
			throw new DirectorMasterException("ERROR- Entity name or key is null in load");
		}
	}
	/**
	  * @return Particular Director Master according 
	  * to the id passed as parameter.  
	  * @param Entity Name 
	  */
	public IDirectorMaster saveDirectorMaster(String entityName, IDirectorMaster item)throws DirectorMasterException{

		
		Long key;
		if(!(entityName==null|| item==null)){
		 key = (Long) getHibernateTemplate().save(entityName, item);
		}else{
		throw new DirectorMasterException("ERROR-- Entity Name Or DirectorMaster Ob is null");
		}
		
		return (IDirectorMaster) getHibernateTemplate().get(entityName, key);
		}

	public boolean isDinNumberUnique(String dinNumber) {
		String stagingQuery = "SELECT FROM "+IDirectorMasterDao.STAGE_DIRECTOR_MASTER_NAME+" WHERE UPPER(din_no) like '"+dinNumber.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if(stagingResultList.size()>0){
			String actualQuery = "SELECT FROM "+IDirectorMasterDao.ACTUAL_DIRECTOR_MASTER_NAME+" WHERE STATUS != 'INACTIVE' AND UPPER(din_no) like '"+dinNumber.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}

	public SearchResult getAllDirectorMaster() throws DirectorMasterException {
		try {
			String query = "FROM " + IDirectorMasterDao.ACTUAL_DIRECTOR_MASTER_NAME  + " ORDER BY DIRECTOR_CODE";
			ArrayList direcotrList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, direcotrList.size(), direcotrList.size(),direcotrList);
		} catch (Exception obe) {
			DefaultLogger
					.error(this, "############# error in listDirector", obe);
			obe.printStackTrace();
			throw new DirectorMasterException("Unable to get Director");
		}

	}

	public SearchResult getAllDirectorMaster(String searchBy, String searchText)throws DirectorMasterException {
		String query = "";
		if( ( searchBy == null || searchBy.equals("") ) || ( searchText == null || searchText.equals("") ) ){
			query = "FROM " + IDirectorMasterDao.ACTUAL_DIRECTOR_MASTER_NAME;		
		}
		else if (searchBy.equalsIgnoreCase("director_code")) {
			if (searchText != null || !(searchText.trim().equals(""))) {
				query = "FROM " + IDirectorMasterDao.ACTUAL_DIRECTOR_MASTER_NAME + " dir WHERE UPPER(dir.directorCode) LIKE '"
						+ searchText.toUpperCase() + "%' order by director_code";
			} else
				query = "FROM " + IDirectorMasterDao.ACTUAL_DIRECTOR_MASTER_NAME + " dir ORDER BY UPPER(DIRECTOR_CODE)";
		}
		else if (searchBy.equalsIgnoreCase("name")) {
			if (searchText != null || !(searchText.trim().equals(""))) {
				query = "FROM " + IDirectorMasterDao.ACTUAL_DIRECTOR_MASTER_NAME + " dir WHERE UPPER(dir.name) LIKE '"
						+ searchText.toUpperCase() + "%' order by director_code";
			} else
				query = "FROM " + IDirectorMasterDao.ACTUAL_DIRECTOR_MASTER_NAME + " dir ORDER BY UPPER(NAME)";
		}
		
		ArrayList countryList = (ArrayList) getHibernateTemplate().find(query);
		return new SearchResult(0, countryList.size(), countryList.size(),countryList);
	}

	public boolean isDirectorNameUnique(String directorName) {
		String newDirectorName = "";
		int len =directorName.length();
		for(int i=0;i<len;i++){
			if( String.valueOf(directorName.charAt(i)).equals("'"))
				newDirectorName = newDirectorName.concat("'");
			
			newDirectorName = newDirectorName.concat(String.valueOf(directorName.charAt(i)));
			}
		String stagingQuery = "SELECT FROM "+IDirectorMasterDao.STAGE_DIRECTOR_MASTER_NAME+" WHERE UPPER(NAME) like '"+newDirectorName.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+IDirectorMasterDao.ACTUAL_DIRECTOR_MASTER_NAME+" WHERE STATUS != 'INACTIVE' AND UPPER(NAME) like '"+newDirectorName.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 )
				return true;
			else
				return false;
		}	
		else 
			return false;
	}
}
