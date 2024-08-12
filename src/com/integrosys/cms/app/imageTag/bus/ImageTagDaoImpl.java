package com.integrosys.cms.app.imageTag.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.image.bus.IImageUploadDao;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.ui.checklist.ITagUntagImageConstant;
import com.integrosys.cms.ui.image.IImageUploadAdd;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;
import com.integrosys.cms.ui.imageTag.ImageTagException;

public class ImageTagDaoImpl extends HibernateDaoSupport implements IImageTagDao {
	Session session = null;
	
	
	Long imgId;

	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * This method creates Image Tag Detail
	 */
	public Long createImageTagDetails(String entityName,
			IImageTagDetails imageData) {
		if (!(entityName == null || imageData == null)) {
		Long key = (Long) (getHibernateTemplate().save(entityName, imageData));
		
		return key;
		}else {
			throw new ImageTagException("ERROR- Entity name or key is null ");
		}
	}

	/**
	 * This method creates Image Tag Map
	 */

	public Long createImageTagMap(String entityName, IImageTagMap imageData) {
		Long key=new Long("1");
		if (!(entityName == null || imageData == null)) {
			if(IImageTagDao.ACTUAL_IMAGE_MAP.equals(entityName)){
				IImageTagMap mapDetail=(OBImageTagMap)(getHibernateTemplate().save(entityName, imageData));
				key=new Long(mapDetail.getTagId());
			}else if(IImageTagDao.STAGE_IMAGE_MAP.equals(entityName)){
				key = (Long) (getHibernateTemplate().save(entityName, imageData));
			}
		return key;
		}else {
			throw new ImageTagException("ERROR- Entity name or key is null ");
		}
	}
	/**
	 * This method creates Image Tag Map
	 */
	
	public int updateImageTagMap(String entityName, IImageTagMap imageData) {
		List list=null;
		if (!(entityName == null || imageData == null)) {
//			String sql = "SELECT FROM "+entityName+" ob where ob.tagId = :tagId and ob.imageId=:imgId";
			
			String sql = "SELECT FROM "+entityName+" WHERE TAG_ID=" +imageData.getTagId()+" and IMAGE_ID="+imageData.getImageId();
	        Query query = currentSession().createQuery(sql);
//	        query.setString("status",imageData.getUntaggedStatus());
//	        query.setLong("tagId",imageData.getTagId());
//	        query.setLong("imgId",imageData.getImageId());
	        list = query.list();
	        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				IImageTagMap tagMapDetail = (IImageTagMap) iterator.next();
				tagMapDetail.setUntaggedStatus(imageData.getUntaggedStatus());
				currentSession().update(entityName, tagMapDetail);
			}
		/*    String sql = "UPDATE "+entityName+" ob set ob.untaggedStatus = :status where ob.tagId = :tagId and ob.imageId=:imgId";
	        Query query = getSession().createSQLQuery(sql);
	        query.setString("status",imageData.getUntaggedStatus());
	        query.setLong("tagId",imageData.getTagId());
	        query.setLong("imgId",imageData.getImageId());
	        int affectedRowCount = query.executeUpdate();
	        /*int affectedRowCount = getSession().createQuery(hql)
										        .setString("status",imageData.getUntaggedStatus())
										        .setLong("tagId",imageData.getTagId())
										        .setLong("imgId",imageData.getImageId()).executeUpdate();*/
	        
			return 1;
		}else {
			throw new ImageTagException("ERROR- Entity name or key is null ");
		}
	}

	/**
	 * @return list of Image to be tagged
	 */
	public List getCustImageList(String entityName, String custId,String category) {
		List list = null;
		List list1 = null;
		List returnList = new ArrayList();
		try {
			
			String strCustId = custId;
			Query query = currentSession().createQuery("SELECT FROM "+IImageUploadDao.TEMP_IMAGEUPLOAD+" WHERE CUST_ID = '"+ strCustId + "' AND CATEGORY='"+category+"' AND IMG_DEPRICATED='N'  ");
			//DefaultLogger.debug(this, "getCustImageList()-------->" + query.getQueryString());
			list = query.list();
			if(list!=null && list.size()>0)
				returnList.addAll(list);
			
			Query query1 = currentSession().createQuery("SELECT FROM "+IImageUploadDao.ACTUAL_IMAGEUPLOAD+" WHERE CUST_ID = '"+ strCustId + "' AND CATEGORY='"+category+"' AND IMG_DEPRICATED='N'  ");
			list1 = query1.list();
			
			if(list1!=null && list1.size()>0)
				returnList.addAll(list1);
			
		} catch (NumberFormatException e) {
			throw new ImageTagException("ERROR-- Error while getting  Image List");
		} catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting  Image List");
		}
		return returnList;

	}

	public IImageTagDetails getCustImageListForView(String entityName, String custId,String category,String id) {
		//List list = null;
		String docType = category;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT FROM actualOBImageTagDetails ob WHERE ");
		if(docType!=null)
			sql.append(" ob.docDesc='"+id+"'");
		
		/*if(IImageTagConstants.SECURITY_DOC.equals(docType)){
			sql.append("AND ob.securityId = "+imageTagDetails.getSecurityId());
		}else if(IImageTagConstants.FACILITY_DOC.equals(docType)){
			sql.append("AND ob.facilityId = "+imageTagDetails.getFacilityId());
		}*/
		try{
		Query query= currentSession().createQuery(sql.toString());
		List list = query.list();
		if(list!=null&&list.size()>0){
			return (OBImageTagDetails)list.get(0);
		}else{
			return null;
		}
		}
	/*	try {
			String sql = "";
			String strCustId = custId;
			if(IImageTagConstants.SECURITY_DOC.equals(category))
				sql="FROM "+entityName +" where CUST_ID='"+custId+"' AND doc_type='"+category+"' AND security_id='"+id+"' ";
			
			if(IImageTagConstants.FACILITY_DOC.equals(category))
				sql="FROM "+entityName +" where CUST_ID='"+custId+"' AND doc_type='"+category+"' AND facility_id='"+id+"' ";
			
//			if(IImageTagConstants.CAM_DOC.equals(category))
				sql="FROM "+entityName +" where CUST_ID='"+custId+"' AND doc_type='"+category+"' AND doc_desc='"+id+"' ";
			
			
			Query query = getSession().createQuery(sql);
			
			
			DefaultLogger.debug(this, "getCustImageList()-------->" + query.getQueryString());
			list = query.list();
		} */catch (NumberFormatException e) {
			throw new ImageTagException("ERROR-- Error while getting  Image List");
		} catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting  Image List");
		}
		//return list;

	}
	/**
	 * @return list of Image to be displayed after tag
	 */
	public List getTagImageList(String entityName, String tagId,String untaggedFilter) {
		List list = null;
		List list1 = null;
		HashMap map= new HashMap();
		try {
			List listImageDetails =new ArrayList();
//			long strTagId = Long.parseLong(tagId);
			String sql="select obImg.imageId from "+entityName+" as obImg , tempOBImageUpload as temp where obImg.tagId = '"+ tagId + "' and  obImg.imageId=temp.imgId and temp.imgDepricated='N' " ;
			if(IImageTagConstants.STATUS_YES.equalsIgnoreCase(untaggedFilter)){
				sql=sql+" and obImg.untaggedStatus='Y' ";
			}else if(IImageTagConstants.STATUS_NO.equalsIgnoreCase(untaggedFilter)){
				sql=sql+" and obImg.untaggedStatus='N' ";
			}else{
				//skipping the filter to get all 
			}
			sql=sql+" ORDER BY   temp.subfolderName , temp.documentName ,temp.imgFileName";
			DefaultLogger.debug(this,"Retriving Image list "+sql);
			Query query = currentSession().createQuery(sql);
			list = query.list();
			
			if(list!=null){
				DefaultLogger.debug(this, "=========================181======================list size:"+list.size());
			}
			
			String sql1="select obImg.imageId from "+entityName+" as obImg , actualUploadedImages as temp where obImg.tagId = '"+ tagId + "' and  obImg.imageId=temp.imgId and temp.imgDepricated='N' " ;
			if(IImageTagConstants.STATUS_YES.equalsIgnoreCase(untaggedFilter)){
				sql1=sql1+" and obImg.untaggedStatus='Y' ";
			}else if(IImageTagConstants.STATUS_NO.equalsIgnoreCase(untaggedFilter)){
				sql1=sql1+" and obImg.untaggedStatus='N' ";
			}else{
				//skipping the filter to get all 
			}
			sql1=sql1+" ORDER BY   temp.subfolderName , temp.documentName ,temp.imgFileName";
			DefaultLogger.debug(this,"Retriving Image list "+sql1);
			Query query1 = currentSession().createQuery(sql1);
			list1 = query1.list();
			if(list1!=null){
				DefaultLogger.debug(this, "=========================197======================list1 size:"+list1.size());
			}
			
			if(list!=null && list.size()>0){
				for (Iterator ita = list.iterator(); ita.hasNext();) {
					Long lvalue=(Long)ita.next();
					String value=String.valueOf(lvalue); 
					map.put(value, value) ;
				}
			}
			if(list1!=null && list1.size()>0){
				for (Iterator itb = list1.iterator(); itb.hasNext();) {
					Long lvalue=(Long)itb.next();
					String value=String.valueOf(lvalue); 
					map.put(value, value) ;
				}
			}
			if (map != null) {
				for (Iterator it = map.values().iterator(); it.hasNext();) {
					
					String value=(String)it.next();
					imgId = new Long(value);
					listImageDetails.add(getImageDetails(imgId.longValue()));
				}

			}
			DefaultLogger.debug(this, "=========================218======================Imagelist size:"+listImageDetails.size());
			TreeMap sortedMap = new TreeMap();
			if(listImageDetails!=null && listImageDetails.size()>0){
				for (Iterator itc = listImageDetails.iterator(); itc.hasNext();) {
					IImageUploadAdd imageUploadAdd=(IImageUploadAdd)itc.next();
					String value="";
					
					if(imageUploadAdd.getCategory()!=null){
						value=value+imageUploadAdd.getCategory();
					}
					if(imageUploadAdd.getSubfolderName()!=null){
						value=value+imageUploadAdd.getSubfolderName();
					}
					if(imageUploadAdd.getDocumentName()!=null){
						value=value+imageUploadAdd.getDocumentName();
					}
					if(imageUploadAdd.getImgFileName()!=null){
						value=value+imageUploadAdd.getImgFileName();
					}
					if(imageUploadAdd.getImgId()!= 0){
					value=value+imageUploadAdd.getImgId();
					}
					sortedMap.put(value, imageUploadAdd) ;
				}
			}
			
			
			DefaultLogger.debug(this, "=========================228======================Imagelist size:"+sortedMap.size());
			//session.close();
			return new ArrayList(sortedMap.values());
		}/* catch (NumberFormatException e) {
			throw new ImageTagException("ERROR-- Error while getting Tag Image List");
		}*/ catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting Tag Image List");
		}
		

	}
	
	
	
	public List getTagUntagImageList(String entityName, String tagId,String untaggedFilter,String category) {
		List list = null;
		List list1 = null;
		HashMap map= new HashMap();
		try {
			List listImageDetails =new ArrayList();
//			long strTagId = Long.parseLong(tagId);
			String sql="select obImg.imageId from "+entityName+" as obImg , tempOBImageUpload as temp where obImg.tagId = '"+ tagId + "' and  obImg.imageId=temp.imgId and temp.imgDepricated='N' and temp.category IS NOT NULL  " ;
			if(IImageTagConstants.STATUS_YES.equalsIgnoreCase(untaggedFilter)){
				sql=sql+" and obImg.untaggedStatus='Y' ";
			}else if(IImageTagConstants.STATUS_NO.equalsIgnoreCase(untaggedFilter)){
				sql=sql+" and obImg.untaggedStatus='N' ";
			}else{
				//skipping the filter to get all 
			}
			sql=sql+" ORDER BY  temp.documentName ,temp.imgFileName";
			DefaultLogger.debug(this,"Retriving Image list "+sql);
			Query query = currentSession().createQuery(sql);
			list = query.list();
			
			if(list!=null){
				DefaultLogger.debug(this, "=========================181======================list size:"+list.size());
			}
			
			String sql1="select obImg.imageId from "+entityName+" as obImg , actualUploadedImages as temp where obImg.tagId = '"+ tagId + "' and  obImg.imageId=temp.imgId and temp.imgDepricated='N' and  temp.category = '"+category+"' and temp.category IS NOT NULL " ;
//			if(IImageTagConstants.STATUS_YES.equalsIgnoreCase(untaggedFilter)){
//				sql1=sql1+" and obImg.untaggedStatus='Y' ";
//			}else if(IImageTagConstants.STATUS_NO.equalsIgnoreCase(untaggedFilter)){
//				sql1=sql1+" and obImg.untaggedStatus='N' ";
//			}else{
//				//skipping the filter to get all 
//			}
			sql1=sql1+" ORDER BY  temp.documentName ,temp.imgFileName";
			DefaultLogger.debug(this,"Retriving Image list "+sql1);
			Query query1 = currentSession().createQuery(sql1);
			list1 = query1.list();
			if(list1!=null){
				DefaultLogger.debug(this, "=========================197======================list1 size:"+list1.size());
			}
			
			if(list!=null && list.size()>0){
				for (Iterator ita = list.iterator(); ita.hasNext();) {
					Long lvalue=(Long)ita.next();
					String value=String.valueOf(lvalue); 
					map.put(value, value) ;
				}
			}
			if(list1!=null && list1.size()>0){
				for (Iterator itb = list1.iterator(); itb.hasNext();) {
					Long lvalue=(Long)itb.next();
					String value=String.valueOf(lvalue); 
					map.put(value, value) ;
				}
			}
			if (map != null) {
				for (Iterator it = map.values().iterator(); it.hasNext();) {
					
					String value=(String)it.next();
					imgId = new Long(value);
					listImageDetails.add(getImageDetails(imgId.longValue()));
				}

			}
			DefaultLogger.debug(this, "=========================218======================Imagelist size:"+listImageDetails.size());
			TreeMap sortedMap = new TreeMap();
			if(listImageDetails!=null && listImageDetails.size()>0){
				for (Iterator itc = listImageDetails.iterator(); itc.hasNext();) {
					IImageUploadAdd imageUploadAdd=(IImageUploadAdd)itc.next();
					String value="";
					
					if(imageUploadAdd.getCategory()!=null){
						value=value+imageUploadAdd.getCategory();
					}
					if(imageUploadAdd.getSubfolderName()!=null){
						value=value+imageUploadAdd.getSubfolderName();
					}
					if(imageUploadAdd.getDocumentName()!=null){
						value=value+imageUploadAdd.getDocumentName();
					}
					if(imageUploadAdd.getImgFileName()!=null){
						value=value+imageUploadAdd.getImgFileName();
					}
					if(imageUploadAdd.getImgId()!= 0){
					value=value+imageUploadAdd.getImgId();
					}
					sortedMap.put(value, imageUploadAdd) ;
				}
			}
			
			
			DefaultLogger.debug(this, "=========================228======================Imagelist size:"+sortedMap.size());
			//session.close();
			return new ArrayList(sortedMap.values());
		}/* catch (NumberFormatException e) {
			throw new ImageTagException("ERROR-- Error while getting Tag Image List");
		}*/ catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting Tag Image List");
		}
		

	}
	

	public List getImageTagMapList(String entityName, String tagId) {
		List list = null;
		try {
			String sql="SELECT FROM "+entityName+" as obImg where obImg.tagId = '"+ tagId + "'";
			DefaultLogger.debug(this,"Retriving Image list "+sql);
			Query query = currentSession().createQuery(sql);
			list = query.list();
			return list;
		} catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting Tag Untag Image map");
		}
		
		
	}
	
	/**
	 * @return Image tag details to be tagged
	 */
	public IImageUploadAdd getImageDetails(long key) {
		IImageUploadAdd obj = new OBImageUploadAdd();
		try {
			long strImgId = key;

			Query query = currentSession().createQuery("from actualUploadedImages as obImg where obImg.imgId = '"+ strImgId + "'");
			obj = (OBImageUploadAdd) query.uniqueResult();

			if(obj==null){
				Query query1 = currentSession().createQuery("from tempOBImageUpload as obImg where obImg.imgId = '"+ strImgId + "'");
				obj = (OBImageUploadAdd) query1.uniqueResult();
			}
			
		} catch (NumberFormatException e) {
			throw new ImageTagException("ERROR-- Error while getting Image Details");
		} catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting Image Details");
		}
		return obj;

	}

	/**
	 * @return list of Collateral to be tagged
	 */

	public List getCollateralId(String entityName, String key) {
		List list = null;
		try {
			String strCustId = key;
			SessionFactory sessionFactory = getHibernateTemplate()
			.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session
			.createQuery("from com.integrosys.cms.app.image.bus.OBImageUploadAdd as obImg where obImg.custId = '"
					+ strCustId + "'");
			DefaultLogger.debug(this, "-------->" + query);
			list = query.list();
			session.close();
		} catch (NumberFormatException e) {
			throw new ImageTagException("ERROR-- Error while getting Collateral id");
		} catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting Collateral id");
		}
		return list;

	}

	/**
	 * This method creates Image Tag Detail
	 */

	public IImageTagDetails createImageTagDetail(String entityName,
			IImageTagDetails imageTagDetails) throws ImageTagException {
		if (!(entityName == null || imageTagDetails == null)) {

			Long key = (Long) getHibernateTemplate().save(entityName,
					imageTagDetails);
			imageTagDetails.setId(key.longValue());
			return imageTagDetails;
		} else {
			throw new ImageTagException("ERROR- Entity name or key is null ");
		}
	}

	/**
	 * @return Image Tag Detail
	 */

	public IImageTagDetails getImageTag(String entityName, Serializable key)
	throws ImageTagException {

		if (!(entityName == null || key == null)) {

			return (IImageTagDetails) getHibernateTemplate().get(entityName,
					key);
		} else {
			throw new ImageTagException("ERROR-- Entity Name Or Key is null");
		}
	}

	/**
	 * This method updates Image Tag
	 */

	public IImageTagDetails updateImageTag(String entityName,
			IImageTagDetails item) throws ImageTagException {
		if (!(entityName == null || item == null)) {
			getHibernateTemplate().update(entityName, item);
			return (IImageTagDetails) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new ImageTagException("ERROR-- Entity Name Or Key is null");
		}
	}

	public IImageTagDetails getExistingImageTag(IImageTagDetails imageTagDetails)
			throws ImageTagException {
		String docType = imageTagDetails.getDocType();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT FROM actualOBImageTagDetails ob WHERE ");
		if(docType!=null)
			sql.append(" ob.docDesc='"+imageTagDetails.getDocDesc()+"' ");
		
		if(IImageTagConstants.SECURITY_DOC.equals(docType)){
			sql.append(" AND ob.securityId = '"+imageTagDetails.getSecurityId()+"' ");
		}else if(IImageTagConstants.FACILITY_DOC.equals(docType)){
			sql.append(" AND ob.facilityId = '"+imageTagDetails.getFacilityId()+"' ");
		}
		
		// Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II 
		else if(IImageTagConstants.EXCHANGE_OF_INFO.equals(docType)){
			sql.append("AND ob.bank = '"+imageTagDetails.getBank()+"'");
		} // Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II 
		try{
//			SessionFactory sessionFactory = getHibernateTemplate()
//			.getSessionFactory();
//	session = sessionFactory.openSession();
//	if(getSession() == null) {
//		Query query= session.createQuery(sql.toString());
//		List list = query.list();
//		session.close();
//		if(list!=null&&list.size()>0){
//			return (OBImageTagDetails)list.get(0);
//		}else{
//			return null;
//		}
//	}else {
		Query query= currentSession().createQuery(sql.toString());
		List list = query.list();
//		session.close();
		if(list!=null&&list.size()>0){
			return (OBImageTagDetails)list.get(0);
		}else{
			return null;
		}
//	}
		}catch (Exception e) {
			e.printStackTrace();
			throw new ImageTagException("Error reterving existing image tag details", e);
		}
	}

	/**
	 * @return list of Image to be tagged
	 */
	public List getCustImageListByCriteria(String entityName, IImageTagDetails tagDetails){
		List list = null;
		List tempList = null;
		try {
			//For Actual
			// comment below code by sachin on 14th Oct 2014. for image retriving issue
			/*StringBuffer sql=new StringBuffer("SELECT FROM "+IImageUploadDao.ACTUAL_UPLOADED_IMAGES+" temp ");
					sql.append(" WHERE CUST_ID = '"+ tagDetails.getCustId() + "' " );
					sql.append(" AND CATEGORY = '"+tagDetails.getCategory()+"' "); 
					sql.append(" AND IMG_DEPRICATED='N'");
					
					if(tagDetails.getSubfolderName()!=null  && ! "".equals(tagDetails.getSubfolderName().trim())){
						sql.append("AND SUBFOLDER_NAME = '"+tagDetails.getSubfolderName()+"' ");
					}
					if(tagDetails.getDocumentName()!=null  && ! "".equals(tagDetails.getDocumentName().trim())){
						sql.append("AND DOCUMENT_NAME = '"+tagDetails.getDocumentName()+"' ");
					}
						
					sql=sql.append(" ORDER BY   temp.subfolderName , temp.documentName ,temp.imgFileName");
			Query query = getSession().createQuery(sql.toString());
			DefaultLogger.debug(this, "getCustImageList()-------->" + query.getQueryString());
			list = query.list();*/
			
			//added by sachin
			StringBuffer sql=new StringBuffer("SELECT FROM "+IImageUploadDao.ACTUAL_UPLOADED_IMAGES+" temp ");
			sql.append(" WHERE CUST_ID = '"+ tagDetails.getCustId() + "' " );
			sql.append(" AND IMG_DEPRICATED='N'");
			
			if(tagDetails.getCategory()!=null  && ! "".equals(tagDetails.getCategory().trim())){
				sql.append(" AND CATEGORY = '"+tagDetails.getCategory()+"' "); 
				//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
				if(tagDetails.getCategory().equals(IImageTagConstants.IMG_CATEGORY_EXCH_INFO)){
					if(null !=tagDetails.getBank() && ! "".equals(tagDetails.getBank().trim()))
					sql.append(" AND BANK_NAME = '"+tagDetails.getBank()+"' "); 
				}
				//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
			}
			if(tagDetails.getSubfolderName()!=null  && ! "".equals(tagDetails.getSubfolderName().trim())){
				sql.append("AND SUBFOLDER_NAME = '"+tagDetails.getSubfolderName()+"' ");
			}
			if(tagDetails.getDocumentName()!=null  && ! "".equals(tagDetails.getDocumentName().trim())){
				sql.append("AND DOCUMENT_NAME = '"+tagDetails.getDocumentName()+"' ");
			}
			
			if(tagDetails.getFacilityDocName()!=null  && ! "".equals(tagDetails.getFacilityDocName().trim())){
				sql.append("AND FACILITY_DOC_NAME = '"+tagDetails.getFacilityDocName()+"' ");
			}
			
			if(tagDetails.getSecurityDocName()!=null  && ! "".equals(tagDetails.getSecurityDocName().trim())){
				sql.append("AND SECURITY_DOC_NAME = '"+tagDetails.getSecurityDocName()+"' ");
			}
			
			if(tagDetails.getOthersDocsName()!=null  && ! "".equals(tagDetails.getOthersDocsName().trim())){
				sql.append("AND OTHER_DOC_NAME = '"+tagDetails.getOthersDocsName()+"' ");
			}
			
			if(tagDetails.getCamDocName()!=null  && ! "".equals(tagDetails.getCamDocName().trim())){
				sql.append("AND CAM_DOC_NAME = '"+tagDetails.getCamDocName()+"' ");
			}
			
			if(tagDetails.getStatementDocName()!=null  && ! "".equals(tagDetails.getStatementDocName().trim())){
				sql.append("AND STATEMENT_DOC_NAME = '"+tagDetails.getStatementDocName()+"' ");
			}
			
			//STATEMENT_DOC_NAME CAM_DOC_NAME  OTHER_DOC_NAME  SECURITY_DOC_NAME  FACILITY_DOC_NAME
				
//			sql=sql.append(" ORDER BY   temp.subfolderName , temp.documentName ,temp.imgFileName");
			sql=sql.append(" ORDER BY   temp.imgId desc");
	//Query query = getSession().createQuery(sql.toString());
//	DefaultLogger.debug(this, "getCustImageList()-------->" + sql.toString());
	list = getHibernateTemplate().find(sql.toString());
			
			//sachin
			
			//For temp
	// comment below code by sachin on 14th Oct 2014. for image retriving issue
	/*
			StringBuffer sql1=new StringBuffer("SELECT FROM "+IImageUploadDao.TEMP_IMAGEUPLOAD+" temp ");
					sql1.append(" WHERE CUST_ID = '"+ tagDetails.getCustId() + "' " );
					sql1.append(" AND CATEGORY = '"+tagDetails.getCategory()+"' "); 
					sql1.append(" AND IMG_DEPRICATED='N'");
					
					if(tagDetails.getSubfolderName()!=null  && ! "".equals(tagDetails.getSubfolderName().trim())){
						sql1.append("AND SUBFOLDER_NAME = '"+tagDetails.getSubfolderName()+"' ");
					}
					if(tagDetails.getDocumentName()!=null  && ! "".equals(tagDetails.getDocumentName().trim())){
						sql1.append("AND DOCUMENT_NAME = '"+tagDetails.getDocumentName()+"' ");
					}
						
					sql1=sql1.append(" ORDER BY   temp.subfolderName , temp.documentName ,temp.imgFileName");
			Query query1 = getSession().createQuery(sql1.toString());
			DefaultLogger.debug(this, "getCustImageList()-------->" + query1.getQueryString());
			tempList = query1.list();*/
			
			//added by sachin
			StringBuffer sql1=new StringBuffer("SELECT FROM "+IImageUploadDao.TEMP_IMAGEUPLOAD+" temp ");
			sql1.append(" WHERE CUST_ID = '"+ tagDetails.getCustId() + "' " );
			sql1.append(" AND IMG_DEPRICATED='N'");
			
			if(tagDetails.getCategory()!=null  && ! "".equals(tagDetails.getCategory().trim())){
				sql1.append(" AND CATEGORY = '"+tagDetails.getCategory()+"' "); 
				//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
				if(tagDetails.getCategory().equals(IImageTagConstants.IMG_CATEGORY_EXCH_INFO)){
					if(null !=tagDetails.getBank() && ! "".equals(tagDetails.getBank().trim()))
					sql1.append(" AND BANK_NAME = '"+tagDetails.getBank()+"' "); 
				}
				//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
			}
			if(tagDetails.getSubfolderName()!=null  && ! "".equals(tagDetails.getSubfolderName().trim())){
				sql1.append("AND SUBFOLDER_NAME = '"+tagDetails.getSubfolderName()+"' ");
			}
			if(tagDetails.getDocumentName()!=null  && ! "".equals(tagDetails.getDocumentName().trim())){
				sql1.append("AND DOCUMENT_NAME = '"+tagDetails.getDocumentName()+"' ");
			}
			
			if(tagDetails.getFacilityDocName()!=null  && ! "".equals(tagDetails.getFacilityDocName().trim())){
				sql1.append("AND FACILITY_DOC_NAME = '"+tagDetails.getFacilityDocName()+"' ");
			}
			
			if(tagDetails.getSecurityDocName()!=null  && ! "".equals(tagDetails.getSecurityDocName().trim())){
				sql1.append("AND SECURITY_DOC_NAME = '"+tagDetails.getSecurityDocName()+"' ");
			}
			
			if(tagDetails.getOthersDocsName()!=null  && ! "".equals(tagDetails.getOthersDocsName().trim())){
				sql1.append("AND OTHER_DOC_NAME = '"+tagDetails.getOthersDocsName()+"' ");
			}
			
			if(tagDetails.getCamDocName()!=null  && ! "".equals(tagDetails.getCamDocName().trim())){
				sql1.append("AND CAM_DOC_NAME = '"+tagDetails.getCamDocName()+"' ");
			}
			
			if(tagDetails.getStatementDocName()!=null  && ! "".equals(tagDetails.getStatementDocName().trim())){
				sql1.append("AND STATEMENT_DOC_NAME = '"+tagDetails.getStatementDocName()+"' ");
			}
				
//			sql1=sql1.append(" ORDER BY   temp.subfolderName , temp.documentName ,temp.imgFileName");
			sql1=sql1.append(" ORDER BY   temp.imgId desc");
	//Query query1 = getSession().createQuery(sql1.toString());
//	DefaultLogger.debug(this, "getCustImageList()-------->" + sql1.toString());
	tempList = getHibernateTemplate().find(sql1.toString());;
			//sachin
			
		} catch (NumberFormatException e) {
			throw new ImageTagException("ERROR-- Error while getting  Image List");
		} catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting  Image List");
		}
		return mergeList(list, tempList);

	}

	private List mergeList(List actual, List temp) {
		List returnList = new ArrayList();
		if (actual != null && actual.size() > 0)
			returnList.addAll(actual);
		if (temp != null && temp.size() > 0)
			returnList.addAll(temp);
		return returnList;

	}

	// code start:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	
	public List<String> getTagId(String custId){
		List<String> tagIdList=new ArrayList<String>();
		
		if(null!=custId){
		String sql="select id from cms_image_tag_details where cust_id='"+custId+"'";
		try {
			DBUtil util=new DBUtil();
			util.setSQL(sql);
			ResultSet rs = util.executeQuery();
			if(null!=rs){
				while(rs.next()){
					tagIdList.add(rs.getString(1));
				}
			}
			
		} catch (DBConnectionException e) {
			// TODO Auto-generated catch block
			DefaultLogger.debug(this, "Error in getTagId:"+e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DefaultLogger.debug(this, "Error in getTagId:"+e.getMessage());
			e.printStackTrace();
		}
		}
		return tagIdList;
		
	}
	
	public List<String> getTaggedImageId(List<String> imageIdList,List<String> tagIdList){
		List<String> taggedImageIdList=new ArrayList<String>();
		String sql="select image_id from cms_image_tag_map where image_id in (:imageIdList) and tag_id in (:tagIdList) and untagged_status='N'";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(getDataSource());
		Map<String, List<String>> parameter=new HashMap<String, List<String>>();
		parameter.put("imageIdList", imageIdList);
		parameter.put("tagIdList", tagIdList);
		taggedImageIdList = namedParameterJdbcTemplate.queryForList(sql, parameter,String.class);
		
		return taggedImageIdList;
		
	}
	// code end:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	public String getSystemBankId(String customerID){
		DefaultLogger.debug(this, "Inside getSystemBankId Method");
	//	List<String> systemBankIdList=new ArrayList<String>();
		String systemBankIdList="";
		DBUtil dbUtil=null;
		// TODO Auto-generated method stub
		String sql="select cms_le_bank_id from sci_le_banking_method where cms_le_main_profile_id in (select cms_le_main_profile_id from sci_le_main_profile where LMP_LE_ID='"+
		customerID+"') and cms_le_bank_type= 'S'";
		
	try {
		 dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				systemBankIdList=systemBankIdList+rs.getString("cms_le_bank_id")+",";
			}
		}
	} catch (DBConnectionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		try {
			dbUtil.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	return systemBankIdList;
	}
	public String getOtherBankId(String customerID){
		DefaultLogger.debug(this, "Inside getOtherBankId Method");
		//List<String> otherBankIdList=new ArrayList<String>();
		String otherBankIdList="";
		// TODO Auto-generated method stub
		String sql="select cms_le_bank_id from sci_le_banking_method where cms_le_main_profile_id in (select cms_le_main_profile_id from sci_le_main_profile where LMP_LE_ID='"+
		customerID+"') and cms_le_bank_type ='O'";
		DBUtil dbUtil=null;
	try {
		 dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				otherBankIdList=otherBankIdList+rs.getString("cms_le_bank_id")+",";
			}
		}
	} catch (DBConnectionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		try {
			dbUtil.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return otherBankIdList;
	}
	
	public List<String> getSystemBankBranchName(String systemBankId){
		
		DefaultLogger.debug(this, "Inside getSystemBankBranchName Method");
		//List<String> otherBankIdList=new ArrayList<String>();
		List<String> systemBankIdList=new ArrayList<String>();
		// TODO Auto-generated method stub
		String sql="select system_bank_name from cms_system_bank where id in (select system_bank_code from cms_system_bank_branch where id in ("+systemBankId +"))";
		DBUtil dbUtil=null;
	try {
		 dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				systemBankIdList.add(rs.getString("system_bank_name"));
			}
		}
	} catch (DBConnectionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		try {
			dbUtil.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return systemBankIdList;
	}
	
	public List<String> getOtherBankBranchName(String otherBankId){
		
		DefaultLogger.debug(this, "Inside getOtherBankBranchName Method");
		//List<String> otherBankIdList=new ArrayList<String>();
		List<String> otherBankIdList=new ArrayList<String>();
		// TODO Auto-generated method stub
		String sql="select bank_name from cms_other_bank where id in (select other_bank_code from cms_other_bank_branch where id in ("+otherBankId +"))";
		DBUtil dbUtil=null;
	try {
		 dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				otherBankIdList.add(rs.getString("bank_name"));
			}
		}
	} catch (DBConnectionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		try {
			dbUtil.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return otherBankIdList;
	}
	
	public List populateBankList(List<String> bankBranchName){
		List bankList=new ArrayList();
		if(null!=bankBranchName && bankBranchName.size()!=0){
			for(String bankBranchNameStr:bankBranchName){
		  bankList.add(new LabelValueBean(bankBranchNameStr,bankBranchNameStr));
			}
		}
		return bankList;
		
	}
	//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
	
	public List<String> getIFSCBankBranchName(String customerID) {

		DefaultLogger.debug(this, "Inside getIFSCBankBranchName Method");
		List<String> ifscBankIdList = new ArrayList<String>();
		String sql = "SELECT DISTINCT BANK_NAME FROM CMS_IFSC_CODE WHERE CMS_LE_MAIN_PROFILE_ID IN(SELECT CMS_LE_SUB_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID='"
				+ customerID + "') AND BANK_TYPE ='O'";
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if (null != rs) {
				while (rs.next()) {
					ifscBankIdList.add(rs.getString("BANK_NAME"));
				}
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ifscBankIdList;
	}
	
	
public List<String> getFacilityNames(String custId){
		
		System.out.println( "Inside getFacilityNames Method");
		//List<String> otherBankIdList=new ArrayList<String>();
		List<String> custIdList=new ArrayList<String>();
		Set<String> custIdSet = null;
		// TODO Auto-generated method stub
		String sql="SELECT DISTINCT FACILITY_NAME,LMT_FAC_CODE FROM SCI_LSP_APPR_LMTS WHERE CMS_LIMIT_PROFILE_ID = " + 
				"(SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE WHERE LLP_LE_ID = '"+custId+"') AND FACILITY_NAME IS NOT NULL ORDER BY FACILITY_NAME";
		DBUtil dbUtil=null;
	try {
		 dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				custIdList.add(rs.getString("FACILITY_NAME"));
			}
		}
//		if(!custIdList.isEmpty()) {
//			custIdSet = new LinkedHashSet<String>(custIdList);
//			if (custIdSet != null && !custIdSet.isEmpty()) {
//				custIdList.clear();
//				for (String custI : custIdSet) {
//					custIdList.add(custI);
//				}
//			}
//		}
	} catch (DBConnectionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		try {
			dbUtil.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return custIdList;
	}


public List<String> getFacilityCodes(String custId){
	
	System.out.println( "Inside getFacilityCodes Method");
	//List<String> otherBankIdList=new ArrayList<String>();
	List<String> facilitycodeList=new ArrayList<String>();
	Set<String> facilitycodeSet = null;
	// TODO Auto-generated method stub
	String sql="SELECT DISTINCT FACILITY_NAME,LMT_FAC_CODE FROM SCI_LSP_APPR_LMTS WHERE CMS_LIMIT_PROFILE_ID = " + 
			"(SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE WHERE LLP_LE_ID = '"+custId+"') AND LMT_FAC_CODE IS NOT NULL ORDER BY FACILITY_NAME";
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			facilitycodeList.add(rs.getString("LMT_FAC_CODE"));
		}
	}
	
//	if(!facilitycodeList.isEmpty()) {
//		facilitycodeSet = new LinkedHashSet<String>(facilitycodeList);
//		if (facilitycodeSet != null && !facilitycodeSet.isEmpty()) {
//			facilitycodeList.clear();
//			for (String fac : facilitycodeSet) {
//				facilitycodeList.add(fac);
//			}
//		}
//	}
} catch (DBConnectionException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
return facilitycodeList;
}

public List<String> getOtherDocumentList(){
	//This is for Other Facility Document Names List.
	System.out.println( "Inside getOtherDocumentList Method");
	//List<String> otherBankIdList=new ArrayList<String>();
	List<String> otherDocList=new ArrayList<String>();
	Set<String> otherDocSet = null;
	// TODO Auto-generated method stub
	String sql="SELECT DOCUMENT_DESCRIPTION FROM CMS_DOCUMENT_GLOBALLIST WHERE CATEGORY= 'F' AND STATUS='ENABLE' AND DEPRECATED='N'";
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			otherDocList.add(rs.getString("DOCUMENT_DESCRIPTION"));
		}
	}
	if(!otherDocList.isEmpty()) {
		otherDocSet = new LinkedHashSet<String>(otherDocList);
		if (otherDocSet != null && !otherDocSet.isEmpty()) {
			otherDocList.clear();
			for (String otherDoc : otherDocSet) {
				otherDocList.add(otherDoc);
			}
		}
	}
} catch (DBConnectionException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
return otherDocList;
}


public List<String> getSecurityOtherDocumentList(){
	
	System.out.println( "Inside getSecurityOtherDocumentList Method");
	List<String> otherSecDocList=new ArrayList<String>();
	Set<String> otherSecDocSet = null;
	String sql="SELECT DOCUMENT_DESCRIPTION FROM CMS_DOCUMENT_GLOBALLIST WHERE CATEGORY= 'S' AND STATUS='ENABLE' AND DEPRECATED='N'";
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			otherSecDocList.add(rs.getString("DOCUMENT_DESCRIPTION"));
		}
	}
	if(!otherSecDocList.isEmpty()) {
		otherSecDocSet = new LinkedHashSet<String>(otherSecDocList);
		if (otherSecDocSet != null && !otherSecDocSet.isEmpty()) {
			otherSecDocList.clear();
			for (String otherDoc : otherSecDocSet) {
				otherSecDocList.add(otherDoc);
			}
		}
	}
} catch (DBConnectionException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
return otherSecDocList;
}



public List<String> getTypeOfDocumentList(){
	
	System.out.println( "Inside getTypeOfDocumentList Method");
	List<String> typeOfDocList=new ArrayList<String>();
	String sql="SELECT ENTRY_NAME,ENTRY_CODE FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE LIKE 'IMG_UPLOAD_CATEGORY%' AND ACTIVE_STATUS = 1 ";
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			typeOfDocList.add(rs.getString("ENTRY_NAME"));
		}
	}
	
} catch (DBConnectionException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
return typeOfDocList;
}



public List<String> getSecurityNameIds(String custId){
	
	System.out.println( "Inside getSecurityNameIds Method");
	String securityId = "";
	String subTypeName = "";
	String secIdSubTypeName = "";
	//List<String> otherBankIdList=new ArrayList<String>();
	List<String> securityNameIdList=new ArrayList<String>();
	Set<String> securityNameIdSet = null;
	// TODO Auto-generated method stub
	String sql="SELECT DISTINCT CMS.CMS_COLLATERAL_ID AS SECURITY_ID, " + 
			"CMS.SUBTYPE_NAME AS SUBTYPE_NAME " + 
			"FROM " + 
			"CMS_SECURITY CMS, " + 
			"SCI_LSP_LMT_PROFILE LMT, " + 
			"SCI_LSP_APPR_LMTS APPR, " + 
			"CMS_LIMIT_SECURITY_MAP MAP " + 
			"WHERE " + 
			"CMS.CMS_COLLATERAL_ID= MAP.CMS_COLLATERAL_ID and " + 
			"APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID and " + 
			"LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID AND " + 
			"LMT.LLP_LE_ID = '"+custId+"' AND MAP.UPDATE_STATUS_IND = 'I' AND CMS.STATUS = 'ACTIVE' ";
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			securityId = rs.getString("SECURITY_ID");
			subTypeName = rs.getString("SUBTYPE_NAME");
			secIdSubTypeName = securityId +"-"+subTypeName;
//			securityNameIdList.add(rs.getString("SECURITY_ID"));
			securityNameIdList.add(secIdSubTypeName);
		}
	}
	if(!securityNameIdList.isEmpty()) {
		securityNameIdSet = new LinkedHashSet<String>(securityNameIdList);
		if (securityNameIdSet != null && !securityNameIdSet.isEmpty()) {
			securityNameIdList.clear();
			for (String securityNameI : securityNameIdSet) {
				securityNameIdList.add(securityNameI);
			}
		}
	}
} catch (DBConnectionException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
return securityNameIdList;
}


public String getFacilityNameFromFacilityCode(String facilityCode,String custId){
	System.out.println( "Inside getFacilityNameFromFacilityCode Method");
	//List<String> otherBankIdList=new ArrayList<String>();
	String facilityName="";
	// TODO Auto-generated method stub
//	String sql="SELECT DISTINCT FACILITY_NAME FROM SCI_LSP_APPR_LMTS WHERE LMT_FAC_CODE='"+facilityCode+"'";
	
	String sql = "SELECT DISTINCT FACILITY_NAME FROM SCI_LSP_APPR_LMTS WHERE CMS_LIMIT_PROFILE_ID =  " + 
			"				(SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE WHERE LLP_LE_ID = '"+custId+"') AND LMT_FAC_CODE ='"+facilityCode+"'  AND FACILITY_NAME IS NOT NULL ORDER BY FACILITY_NAME ";
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			facilityName=rs.getString("FACILITY_NAME");
		}
	}
} catch (DBConnectionException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
return facilityName;
}


public String getCamNumber(String custId){
	System.out.println( "Inside getCamNumber Method");
	//List<String> otherBankIdList=new ArrayList<String>();
	String camNumber="";
	// TODO Auto-generated method stub
	String sql="SELECT LLP_BCA_REF_NUM FROM SCI_LSP_LMT_PROFILE WHERE LLP_LE_ID = '"+custId+"'";
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			camNumber=rs.getString("LLP_BCA_REF_NUM");
		}
	}
} catch (DBConnectionException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
return camNumber;
}

	public Map<Long, String> getImageIdTaggedStatusMap(String checklistDocItemId) {
		if(StringUtils.isNotBlank(checklistDocItemId)) {
			NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("checklistDocItemId", checklistDocItemId);
			
			String sql = "select tag_map.IMAGE_ID, tag_map.UNTAGGED_STATUS from cms_image_tag_map tag_map " + 
					"inner join cms_image_tag_details tag_dtl on tag_dtl.ID = tag_map.TAG_ID where tag_dtl.DOC_DESC = :checklistDocItemId";
			
			return (Map<Long, String>) template.query(sql, params,  new ResultSetExtractor() {
				public Map<Long, String> extractData(ResultSet rs) throws SQLException {
					 Map<Long, String> map = new HashMap<Long, String>();
					 while(rs.next()) {
						 String unTaggedStatus = ICMSConstant.YES.equals(rs.getString("UNTAGGED_STATUS")) ? 
								 ITagUntagImageConstant.UN_TAGGED  : ITagUntagImageConstant.TAGGED;
						 map.put(rs.getLong("IMAGE_ID"), unTaggedStatus);
					 }
					return map;
				}
			});
		}
		
		return Collections.emptyMap();
	}
	
	public List getImageIdDocDescMap(String checklistDocItemId,String checkListType) {
		final List imgIdDocDescList = new ArrayList();
		if(StringUtils.isNotBlank(checklistDocItemId)) {
			NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("checklistDocItemId", checklistDocItemId);
			
			String sql = "select tag_map.IMAGE_ID, tag_map.UNTAGGED_STATUS, cci.DOC_DESCRIPTION from cms_checklist_item cci , cms_image_tag_map tag_map " + 
					"inner join cms_image_tag_details tag_dtl on tag_dtl.ID = tag_map.TAG_ID where tag_dtl.DOC_DESC = :checklistDocItemId AND cci.doc_item_id = :checklistDocItemId  order by tag_map.IMAGE_ID";
			
			return (List) template.query(sql, params,  new ResultSetExtractor() {
				public List extractData(ResultSet rs) throws SQLException {
					 List map = new ArrayList();
					 
					 while(rs.next()) {
						 String docDesc = rs.getString("DOC_DESCRIPTION");
						 map.add(rs.getLong("IMAGE_ID"));
						 map.add(docDesc);
						 imgIdDocDescList.add(map);
					 }
					return imgIdDocDescList;
				}
			});
		}
		
		return Collections.emptyList();
	}
	
	

public String getCategorycode(String categoryName){
	System.out.println( "Inside getCategorycode Method");
	//List<String> otherBankIdList=new ArrayList<String>();
	String categoryEntryCode="";
	// TODO Auto-generated method stub
	String sql="SELECT ENTRY_CODE FROM COMMON_CODE_CATEGORY_ENTRY WHERE ENTRY_NAME = '"+categoryName+"' AND CATEGORY_CODE = 'IMG_UPLOAD_CATEGORY' AND ACTIVE_STATUS = 1";
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			categoryEntryCode=rs.getString("ENTRY_CODE");
		}
	}
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
return categoryEntryCode;
}

public String getEntryNameFromCode(String categoryName){
//	System.out.println( "Inside getEntryNameFromCode Method");
	//List<String> otherBankIdList=new ArrayList<String>();
	String categoryEntryCode="";
	// TODO Auto-generated method stub
	String sql="SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE  " + 
			"ENTRY_CODE = '"+categoryName+"' AND CATEGORY_CODE = 'IMG_UPLOAD_CATEGORY' AND ACTIVE_STATUS = 1";
//	System.out.println("getEntryNameFromCode() => sql = "+sql);
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			categoryEntryCode=rs.getString("ENTRY_NAME");
		}
	}
	rs.close();
	
	if("".equals(categoryEntryCode)) {
		String sql1="SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE  " + 
				"ENTRY_CODE = '"+categoryName+"' AND CATEGORY_CODE = 'IMG_UPLOAD_CATEGORY' AND ACTIVE_STATUS = 0";
		System.out.println("getEntryNameFromCode() => categoryEntryCode is empty=>  sql1 = "+sql1);
		
		DBUtil dbUtil1=null;
	
		 dbUtil1=new DBUtil();
		dbUtil1.setSQL(sql1);
		ResultSet rs1 = dbUtil1.executeQuery();
		if(null!=rs1){
			while(rs1.next()){
				categoryEntryCode=rs1.getString("ENTRY_NAME");
			}
		}
		rs1.close();
		dbUtil1.close();
	}
	
	
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
return categoryEntryCode;
}


public List<String> getStatementDocumentList(){
	
	System.out.println("Inside getStatementDocumentList Method");
	List<String> statementDocList=new ArrayList<String>();
	String sql="SELECT  " + 
			"DOCUMENT_DESCRIPTION " + 
			"FROM CMS_DOCUMENT_GLOBALLIST " + 
			"WHERE CATEGORY= 'REC' " + 
			"AND STATUS    ='ENABLE' " + 
			"AND DEPRECATED='N' " + 
			"AND STATEMENT_TYPE IS NOT NULL ";
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			statementDocList.add(rs.getString("DOCUMENT_DESCRIPTION"));
		}
	}
	
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
return statementDocList;
}


public List<String> getCamDocumentList(){
	
	System.out.println("Inside getCamDocumentList Method");
	List<String> camDocList=new ArrayList<String>();
	String sql="SELECT DOCUMENT_DESCRIPTION " + 
			"FROM CMS_DOCUMENT_GLOBALLIST " + 
			"WHERE CATEGORY= 'CAM' " + 
			"AND STATUS    ='ENABLE' " + 
			"AND DEPRECATED='N'";
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			camDocList.add(rs.getString("DOCUMENT_DESCRIPTION"));
		}
	}
	
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
return camDocList;
}


public List<String> getOtherMasterDocumentList(){
	
	System.out.println("Inside getOtherMasterDocumentList Method");
	List<String> otherMasterDocList=new ArrayList<String>();
	String sql="SELECT DOCUMENT_DESCRIPTION " + 
			"FROM CMS_DOCUMENT_GLOBALLIST " + 
			"WHERE CATEGORY= 'O' " + 
			"AND STATUS    ='ENABLE' " + 
			"AND DEPRECATED='N'";
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			otherMasterDocList.add(rs.getString("DOCUMENT_DESCRIPTION"));
		}
	}
	
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
return otherMasterDocList;
}


public List<String> getcamNumberList(String custId){
	
	System.out.println("Inside getcamNumberList Method");
	List<String> camNumberList=new ArrayList<String>();
	String sql="SELECT CMS_CHECKLIST.CAMNUMBER AS  CAM_NUMBER " + 
			"FROM  " + 
			"CMS_CHECKLIST, TRANSACTION ,SCI_LSP_LMT_PROFILE  " + 
			"WHERE TRANSACTION.REFERENCE_ID = CMS_CHECKLIST.CHECKLIST_ID  AND  " + 
			"TRANSACTION.TRANSACTION_TYPE = 'CHECKLIST' AND TRANSACTION.STATUS <> 'OBSOLETE' AND  " + 
			"CMS_CHECKLIST.CATEGORY = 'CAM'  " + 
			"AND SCI_LSP_LMT_PROFILE.LLP_LE_ID = '"+custId+"'  " + 
			"AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = CMS_CHECKLIST.CMS_LSP_LMT_PROFILE_ID  ";
	System.out.println("getcamNumberList sql => "+sql);
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			camNumberList.add(rs.getString("CAM_NUMBER"));
		}
	}
	
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
return camNumberList;
}



public List<String> getFacilityDocTagModuleList(String custId){
	
	System.out.println("Inside getFacilityDocTagModuleList Method");
	List<String> facilityDocTagModuleList=new ArrayList<String>();
	String sql="SELECT DOC_DESCRIPTION " + 
			"FROM CMS_DOCUMENT_ITEM " + 
			"WHERE MASTERLIST_ID IN " + 
			"  (SELECT MASTERLIST_ID " + 
			"  FROM CMS_DOCUMENT_MASTERLIST " + 
			"  WHERE SECURITY_SUB_TYPE_ID IN ( " + 
			"SELECT distinct LMT_FAC_CODE FROM SCI_LSP_APPR_LMTS  " + 
			"WHERE CMS_LIMIT_PROFILE_ID =  " + 
			"(SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE  " + 
			"WHERE LLP_LE_ID = '"+custId+"' ) AND FACILITY_NAME  " + 
			"IS NOT NULL ))  ";
	System.out.println("getFacilityDocTagModuleList sql => "+sql);
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			facilityDocTagModuleList.add(rs.getString("DOC_DESCRIPTION"));
		}
	}
	
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
return facilityDocTagModuleList;
}

public List<String> getSecurityDocTagModuleList(String custId){
	
	System.out.println("Inside getSecurityDocTagModuleList Method");
	List<String> securityDocTagModuleList=new ArrayList<String>();
	String sql="SELECT DOC_DESCRIPTION FROM CMS_DOCUMENT_ITEM    " + 
			"WHERE MASTERLIST_ID IN(   SELECT MASTERLIST_ID FROM CMS_DOCUMENT_MASTERLIST    " + 
			"WHERE SECURITY_SUB_TYPE_ID IN(   SELECT LMT_FAC_CODE FROM SCI_LSP_APPR_LMTS    " + 
			"WHERE CMS_LSP_APPR_LMTS_ID IN (SELECT CMS_LSP_APPR_LMTS_ID FROM CMS_LIMIT_SECURITY_MAP    " + 
			"WHERE CMS_COLLATERAL_ID IN (SELECT DISTINCT CMS.CMS_COLLATERAL_ID AS SECURITY_ID " + 
			"FROM CMS_SECURITY CMS, SCI_LSP_LMT_PROFILE LMT, SCI_LSP_APPR_LMTS APPR,  " + 
			"CMS_LIMIT_SECURITY_MAP MAP WHERE CMS.CMS_COLLATERAL_ID= MAP.CMS_COLLATERAL_ID  " + 
			"and APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID  " + 
			"and LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID  " + 
			"AND LMT.LLP_LE_ID = '"+custId+"'))))  ";
	System.out.println("getSecurityDocTagModuleList sql => "+sql);
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			securityDocTagModuleList.add(rs.getString("DOC_DESCRIPTION"));
		}
	}
	
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
return securityDocTagModuleList;
}

public List<String> getFacilityDocFromMasterList(){
	
	System.out.println("Inside getFacilityDocFromMasterList Method");
	List<String> facilityDocNameMasterList=new ArrayList<String>();
	String sql="SELECT DISTINCT DOCUMENT_DESCRIPTION " + 
			"FROM CMS_DOCUMENT_GLOBALLIST " + 
			"WHERE CATEGORY= 'F' " + 
			"AND STATUS    ='ENABLE' " + 
			"AND DEPRECATED='N' ";
	System.out.println("getFacilityDocFromMasterList sql => "+sql);
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			facilityDocNameMasterList.add(rs.getString("DOCUMENT_DESCRIPTION"));
		}
	}
	
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
return facilityDocNameMasterList;
}

public List<String> getSecurityDocFromMasterList(){
	
	System.out.println("Inside getSecurityDocFromMasterList Method");
	List<String> securityDocNameMasterList=new ArrayList<String>();
	String sql="SELECT DISTINCT DOCUMENT_DESCRIPTION " + 
			"FROM CMS_DOCUMENT_GLOBALLIST " + 
			"WHERE CATEGORY= 'S' " + 
			"AND STATUS    ='ENABLE' " + 
			"AND DEPRECATED='N' ";
	System.out.println("getSecurityDocFromMasterList sql => "+sql);
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			securityDocNameMasterList.add(rs.getString("DOCUMENT_DESCRIPTION"));
		}
	}
	
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
return securityDocNameMasterList;
}


public String getPartyName(String partyId){
	System.out.println( "Inside getPartyName Method for XXX party Name");
	//List<String> otherBankIdList=new ArrayList<String>();
	String categoryEntryCode="";
	// TODO Auto-generated method stub
	String sql="SELECT LSP_SHORT_NAME FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID = '"+partyId+"'";
	System.out.println("getPartyName() => sql = "+sql);
	DBUtil dbUtil=null;
try {
	 dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	ResultSet rs = dbUtil.executeQuery();
	if(null!=rs){
		while(rs.next()){
			categoryEntryCode=rs.getString("LSP_SHORT_NAME");
		}
	}
	rs.close();
	
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finally{
	try {
		dbUtil.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
return categoryEntryCode;
}

	public Map<String, String> getFacilityMap(String custId){
		Map<String, String> facilityMap = new HashMap<String, String>();
		String sql="SELECT DISTINCT FACILITY_NAME,LMT_FAC_CODE FROM SCI_LSP_APPR_LMTS WHERE CMS_LIMIT_PROFILE_ID = " + 
				"(SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE WHERE LLP_LE_ID = '"+custId+"') AND LMT_FAC_CODE IS NOT NULL ORDER BY FACILITY_NAME";
		DBUtil dbUtil=null;
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					facilityMap.put(rs.getString("LMT_FAC_CODE"), rs.getString("FACILITY_NAME"));
				}
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return facilityMap;
	}

	@Override
	public List<Long> getImageTagIdForStockDetails(String partyId) {
		if(StringUtils.isNotBlank(partyId)) {
			StringBuilder sb = new StringBuilder("select obImg.TAG_ID from cms_image_tag_map obImg ")
					.append("INNER JOIN CMS_TEMP_IMAGE_UPLOAD temp ON obImg.IMAGE_ID =temp.IMG_ID ")
					.append("WHERE temp.CUST_ID = :partyId AND temp.CATEGORY = :category ");
			
			NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(getDataSource());
			Map<String, String> parameter=new HashMap<String, String>();
			parameter.put("partyId", partyId);
			parameter.put("category", "IMG_CATEGORY_STOCK_STMT");
			try {
				return namedParameterJdbcTemplate.queryForList(String.valueOf(sb), parameter,Long.class);
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Exception caught in getImageTagIdForStockDetails :"+e.getMessage(), e);
			}
		}
		
		return Collections.emptyList();
	}

	@Override
	public Long getChecklistItemIdForViewStockStatement(String partyId, String docCode) {
		if(StringUtils.isNotBlank(partyId) && StringUtils.isNotBlank(docCode)) {
			StringBuilder sb = new StringBuilder("select chk_item.DOC_ITEM_ID from CMS_CHECKLIST_ITEM chk_item ")
					.append("inner join CMS_CHECKLIST chk on chk.CHECKLIST_ID = chk_item.CHECKLIST_ID ")
					.append("inner join SCI_LSP_LMT_PROFILE prof on prof.CMS_LSP_LMT_PROFILE_ID = chk.CMS_LSP_LMT_PROFILE_ID ")
					.append("WHERE chk_item.DOCUMENT_CODE= :docCode ")
					.append("and prof.LLP_LE_ID = :partyId ");
			
			NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(getDataSource());
			Map<String, String> parameter=new HashMap<String, String>();
			parameter.put("docCode", docCode);
			parameter.put("partyId", partyId);
			
			try {
				return (Long) namedParameterJdbcTemplate.queryForObject(String.valueOf(sb), parameter,Long.class);
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Exception caught in getImageTagIdForStockDetails :"+e.getMessage(), e);
			}
			
		}
		
		return null;
	}
	
	

}
