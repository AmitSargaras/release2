package com.integrosys.cms.app.rbicategory.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


/**
 * @author  Govind.Sahu 
 * Dao implication of interface Rbi Category
 */

public class RbiCategoryDaoImpl extends HibernateDaoSupport implements IRbiCategoryDao{
	
	


	/*
	 *  This method get Rbi Code Category result list
	 */
	public List getAllRbiCategoryList(String entityName) {
		List resultList = null;
		//CMS_RBI_CATEGORY
		try {
			resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName+" where DEPRECATED = 'N' AND STATUS = 'ACTIVE' ORDER BY INDUSTRY_NAME_ID");

		} catch (Exception e) {
			e.printStackTrace();
			throw new RbiCategoryException("ERROR-- While retriving Rbi Code Category");
		}
		return resultList;
	}
	
	public List getRbiIndCodeByNameList(String industry,String entityName) {
		List resultList = null;
		try {
			resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName+" where DEPRECATED = 'N' AND STATUS = 'ACTIVE' and INDUSTRY_NAME_ID = '"+industry+"' ");
           /* String id = (String)resultList.get(0);
            resultList = (ArrayList)getHibernateTemplate().find("select from "+entityName+" where rbi_category_id ='"+id+"' ");*/
		} catch (Exception e) {
			e.printStackTrace();
			throw new RbiCategoryException("ERROR-- While retriving Rbi Code Category");
		}
		return resultList;
	}
	
	/*
	 *  This method get Rbi Code Category result list
	 */
	public List searchRbiCategory(String srAlph,String entityName) {
		List resultList = null;
		try {
			resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName+" where DEPRECATED = 'N' AND STATUS = 'ACTIVE'");

		} catch (Exception e) {
			e.printStackTrace();
			throw new RbiCategoryException("ERROR-- While retriving Rbi Code Category");
		}
		return resultList;
	}
	
	
	/**
	  * @return Particular Rbi Category according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */
	public IRbiCategory getRbiCategory(String entityName, Serializable key)throws RbiCategoryException {
		
		if(!(entityName==null|| key==null)){
		
		return (IRbiCategory) getHibernateTemplate().get(entityName, key);
		}else{
			throw new RbiCategoryException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return RbiCategory Object
	 * @param Entity Name
	 * @param RbiCategory Object  
	 * This method Updates Rbi Category Object
	 */
	
	public IRbiCategory updateRbiCategory(String entityName, IRbiCategory rbiCategory)throws RbiCategoryException{
		Long key = null ;
		if(!(entityName==null|| rbiCategory==null)){
			try{
				String strInindustryName = "";
				if(entityName.equals("actualRbiCategory"))
				{
					strInindustryName = "codeCategoryMappingActual";
					
				}
				else if(entityName.equals("stageRbiCategory"))
				{
					strInindustryName = "codeCategoryMappingStage";
				}
			Collection delItem = (List)getHibernateTemplate().find("FROM "+strInindustryName+" WHERE RBI_CATEGORY_ID = "+rbiCategory.getId());
			if(!delItem.isEmpty())
			{
			rbiCategory.getStageIndustryNameSet().removeAll(delItem);
			getHibernateTemplate().deleteAll(delItem);
			}
			getHibernateTemplate().saveOrUpdate(entityName, rbiCategory);
			
			return  (IRbiCategory) getHibernateTemplate().load(entityName, new Long(rbiCategory.getId()));
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new RbiCategoryException("ERROR-- Exception found in Updating rbi category");
			}
		}else{
			throw new RbiCategoryException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return RbiCategory Object
	 * @param Entity Name
	 * @param RbiCategory Object  
	 * This method delete Rbi Category Object
	 */
	
	public IRbiCategory deleteRbiCategory(String entityName, IRbiCategory item)throws RbiCategoryException{

		if(!(entityName==null|| item==null)){
			item.setDeprecated("Y");
			item.setStatus("INACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (IRbiCategory) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new RbiCategoryException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return RbiCategory Object
	 * @param Entity Name
	 * @param RbiCategory Object  
	 * This method Creates Rbi Category Object
	 */
	public IRbiCategory createRbiCategory(String entityName,IRbiCategory rbiCategory)throws RbiCategoryException {
		Long key = null ;
//		System.out.println("Selected Rbi Code And Category:"+rbiCategory.getStageIndustryNameSet().size());
		if(!(entityName==null|| rbiCategory==null)){
	    	  key = (Long) getHibernateTemplate().save(entityName, rbiCategory);
	    	  //getHibernateTemplate().update(entityName, iRbiCategory);
	    	  rbiCategory.setId(key.longValue());
			return rbiCategory;
			}else{
				throw new RbiCategoryException("ERROR- Entity name or key is null ");
			}
	}


	
	
	/**
	  * @return Particular Rbi Category according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IRbiCategory load(String entityName, long id)throws RbiCategoryException
	{
		if(!(entityName==null|| id==0)){
		return (IRbiCategory)getHibernateTemplate().get(entityName , new Long(id));	
		}else{
			throw new RbiCategoryException("ERROR- Entity name or key is null ");
		}
	}
	
	/*
	 *  This method get Rbi Code Category result object
	 */
	public boolean getActualRbiCategory(String entityName,IRbiCategory rbiCategory)throws RbiCategoryException {
		List resultList = null;
		boolean result;
		try {
			resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName+" where DEPRECATED = 'N' AND STATUS = 'ACTIVE' AND INDUSTRY_NAME_ID ='"+rbiCategory.getIndustryNameId()+"'");
			if(!resultList.isEmpty())
			{
				result = true;
			}
			else
			{
				result = false;	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RbiCategoryException("ERROR-- While retriving Rbi Code Category");
		}
		return result;
	}
	

	/*
	 *  This method return true if Industry Name already approve else return false.
	 */
	public boolean isIndustryNameApprove(String entityName,String industryNameId) {
		List resultList = null;
		try {
			resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName+" where DEPRECATED = 'N' AND STATUS = 'ACTIVE' AND INDUSTRY_NAME_ID = '"+industryNameId+"'");
			if(resultList.size()>0)
				return true;
			else 
				return false;
			}
		catch (Exception e) {
			e.printStackTrace();
			throw new RbiCategoryException("ERROR-- While retriving Rbi Code Category");
		}
	}
	
	/*
	 *  This method return true if Rbi Code Category already assign to Industry else return false.
	 */
	public List isRbiCodeCategoryApprove(String entityName,OBRbiCategory stgObRbiCategory,boolean isEdit,OBRbiCategory actObRbiCategory) {
		List resultList = null;
		List rbiCodeCatApprovedList = new ArrayList();
		OBRbiCategory obRbiCategory = new OBRbiCategory();
		String strRbiCodCat;
		String sql;
		
		ArrayList rbiCodeCategoryIdList = new ArrayList();
		Set rbiCode = stgObRbiCategory.getStageIndustryNameSet();
		
		
		Iterator itCodeSet = rbiCode.iterator();
				while(itCodeSet.hasNext())
				{
				OBIndustryCodeCategory oBIndustryNameStg = new OBIndustryCodeCategory();
			 	oBIndustryNameStg = (OBIndustryCodeCategory)itCodeSet.next();
						
			 	rbiCodeCategoryIdList.add(oBIndustryNameStg.getRbiCodeCategoryId());
				}
		
		try {
			Iterator itRbiCodeCat = rbiCodeCategoryIdList.iterator();
			
			while(itRbiCodeCat.hasNext())
			{
				strRbiCodCat =itRbiCodeCat.next().toString();
				if(isEdit)
				{
					sql = "SELECT FROM  actualRbiCategory rbi,codeCategoryMappingActual ind WHERE rbi.id = ind.rbiCategoryId AND rbi.id != "+actObRbiCategory.getId()+" AND rbi.deprecated = 'N' AND rbi.status = 'ACTIVE' AND ind.rbiCodeCategoryId = '"+strRbiCodCat+"'";
				}
				else
				{
					sql = "SELECT FROM  actualRbiCategory rbi,codeCategoryMappingActual ind WHERE rbi.id = ind.rbiCategoryId AND rbi.deprecated = 'N' AND rbi.status = 'ACTIVE' AND ind.rbiCodeCategoryId = '"+strRbiCodCat+"'";	
				}
				
				resultList = (ArrayList)getHibernateTemplate().find(sql);
				
				if(resultList.size()>0)
				{
					rbiCodeCatApprovedList.add(strRbiCodCat);
				}
			}
			return rbiCodeCatApprovedList;
			}
		catch (Exception e) {
			e.printStackTrace();
			throw new RbiCategoryException("ERROR-- While retriving Rbi Code Category");
		}
	}
	

}
