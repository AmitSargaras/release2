package com.integrosys.cms.app.image.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.ui.image.IImageUploadAdd;
import com.integrosys.cms.ui.imageTag.ImageTagException;

/**
 * This class defines the operations that are provided by a image
 * 
 * @author $Govind: Sahu $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/02 11:32:23 $ Tag: $Name: $
 */

public class ImageUploadDaoImpl extends HibernateDaoSupport implements
		IImageUploadDao {
	/**
	 * This method insert data in the database using hibernate save method, in
	 * save method two parameter Entity Name and DAO
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return void
	 */
	public void createImageUpload(IImageUploadAdd imageData) throws ImageUploadException {
		try {
			if(imageData.getDocumentName()==null){
				
			}else{
				if(imageData.getDocumentName().trim().equals("null")){
					imageData.setDocumentName(null);
				}
			}
			String key = (getHibernateTemplate().save(IImageUploadDao.TEMP_IMAGEUPLOAD, imageData)).toString();
			//imageData.setImgId(Long.parseLong(key));
			DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Date Has Been Save");
		}catch (Exception e) {
			DefaultLogger.debug(this, "########### Error in createImageUpload() due to inserting data in database");
			e.printStackTrace();
			throw new ImageUploadException("Unable to Insert customer Image Details");
		}
	}
	
	public void createImageUpload(IImageUploadAdd imageData, boolean isActual) throws ImageUploadException {
		try {
			if(imageData.getDocumentName()==null){
				
			}else{
				if(imageData.getDocumentName().trim().equals("null")){
					imageData.setDocumentName(null);
				}
			}
			String key = (getHibernateTemplate().save(isActual ? IImageUploadDao.TEMP_IMAGEUPLOAD : IImageUploadDao.STGE_TEMP_IMAGEUPLOAD, imageData)).toString();
			//imageData.setImgId(Long.parseLong(key));
			DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Date Has Been Save");
		}catch (Exception e) {
			DefaultLogger.debug(this, "########### Error in createImageUpload() due to inserting data in database");
			e.printStackTrace();
			throw new ImageUploadException("Unable to Insert customer Image Details");
		}
	}

	private static Comparator<OBImageUploadAdd> imgUploadByCreationDesc = new Comparator<OBImageUploadAdd>() {
		public int compare(OBImageUploadAdd o1, OBImageUploadAdd o2) {
			return o2.getCreationDate().compareTo(o1.getCreationDate());
		};
	};
	
	/**
	 * Get all the image detail for given custId of customer.
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return a List of image detail records
	 */

	public List getCustImageList(IImageUploadAdd imageData)
			throws ImageUploadException {
		/*ArrayList returnList = new ArrayList();
		ArrayList oBImageUploadAddList = new ArrayList();
		ArrayList oBTempImageUploadList = new ArrayList();
		String strCustId = imageData.getCustId();
		try {
			//from Actual table
			oBImageUploadAddList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+IImageUploadDao.ACTUAL_UPLOADED_IMAGES+" WHERE CUST_ID = '"+ strCustId + "' AND IMG_DEPRICATED='N' order by category,subfolder_name,document_name,img_filename ");
			if(oBImageUploadAddList!=null && oBImageUploadAddList.size()>0)
				returnList.addAll(oBImageUploadAddList);
			
			//from Temp table
			oBTempImageUploadList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+IImageUploadDao.TEMP_IMAGEUPLOAD+" WHERE CUST_ID = '"+ strCustId + "'  AND IMG_DEPRICATED='N' order by category,subfolder_name,document_name,img_filename ");
			if(oBTempImageUploadList!=null && oBTempImageUploadList.size()>0)
				returnList.addAll(oBTempImageUploadList);
			
		} 
		catch (Exception e) {
			DefaultLogger.debug(this, "########### Error in getCustImageList() due to HQL");
			e.printStackTrace();
			throw new ImageUploadException("Unable to get customer Image List");
		}
		return returnList;*/
		
		
		List list = null;
		List tempList = null;
		try {
			//For Actual
			StringBuffer sql=new StringBuffer("SELECT FROM "+IImageUploadDao.ACTUAL_UPLOADED_IMAGES+" temp ");
					sql.append(" WHERE CUST_ID = '"+ imageData.getCustId() + "' " );
					sql.append(" AND IMG_DEPRICATED='N'");
					
//					if(imageData.getCategory()!=null  && ! "".equals(imageData.getCategory().trim())){
//						sql.append(" AND CATEGORY = '"+imageData.getCategory()+"' "); 
//					}
					if(imageData.getSubfolderName()!=null  && ! "".equals(imageData.getSubfolderName().trim())){
						sql.append(" AND SUBFOLDER_NAME = '"+imageData.getSubfolderName()+"' ");
					}
//					if(imageData.getDocumentName()!=null  && ! "".equals(imageData.getDocumentName().trim())){
//						sql.append("AND DOCUMENT_NAME = '"+imageData.getDocumentName()+"' ");
//					}
//					
//					//Uma :Prod issue: image upload - all the bank details are getting displayed.
					if(null!=imageData.getBank()  && ! "".equals(imageData.getBank().trim())){
						sql.append("AND BANK_NAME = '"+imageData.getBank()+"' ");
					}
					
					//Added By Prachit: Start
					
//					if(imageData.getTypeOfDocument() !=null  && ! "".equals(imageData.getTypeOfDocument().trim())){
//						sql.append(" AND TYPE_OF_DOC = '"+imageData.getTypeOfDocument()+"' "); 
//					}
					
					if(imageData.getCategory()!=null  && ! "".equals(imageData.getCategory().trim())){
						sql.append(" AND CATEGORY = '"+imageData.getCategory()+"' "); 
					}
					
					if(imageData.getFacilityName() !=null  && ! "".equals(imageData.getFacilityName().trim())){
						sql.append(" AND FACILITY_NAME = '"+imageData.getFacilityName()+"' "); 
					}
					
					if(imageData.getFacilityDocName() !=null  && ! "".equals(imageData.getFacilityDocName().trim())){
						sql.append(" AND FACILITY_DOC_NAME = '"+imageData.getFacilityDocName()+"' "); 
					}
					
					if(imageData.getOtherDocName() !=null  && ! "".equals(imageData.getOtherDocName().trim())){
						sql.append(" AND OTHER_FACILITY_DOC_NAME = '"+imageData.getOtherDocName()+"' "); 
					}
					
					/*if(imageData.getSecurityNameId() !=null  && ! "".equals(imageData.getSecurityNameId().trim())){
						sql.append(" AND SECURITY_NAME_ID = '"+imageData.getSecurityNameId()+"' "); 
					}*/
					
					if(imageData.getSecurityIdi() !=null  && ! "".equals(imageData.getSecurityIdi().trim())){
						sql.append(" AND SECURITY_NAME_ID = '"+imageData.getSecurityIdi()+"' "); 
					}
					
					if(imageData.getSecurityDocName() !=null  && ! "".equals(imageData.getSecurityDocName().trim())){
						sql.append(" AND SECURITY_DOC_NAME = '"+imageData.getSecurityDocName()+"' "); 
					}
					
					if(imageData.getOtherSecDocName() !=null  && ! "".equals(imageData.getOtherSecDocName().trim())){
						sql.append(" AND OTHER_SECURITY_DOC_NAME = '"+imageData.getOtherSecDocName()+"' "); 
					}
					
					if(imageData.getHasCam() !=null  && ! "".equals(imageData.getHasCam().trim())){
						sql.append(" AND CAM_NUMBER = '"+imageData.getHasCam()+"' "); 
					}
					
					if(imageData.getCamDocName() !=null  && ! "".equals(imageData.getCamDocName().trim())){
						sql.append(" AND CAM_DOC_NAME = '"+imageData.getCamDocName()+"' "); 
					}
					
					if(imageData.getStatementTyped() !=null  && ! "".equals(imageData.getStatementTyped().trim())){
						sql.append(" AND STATEMENT_TYPE = '"+imageData.getStatementTyped()+"' "); 
					}
					
					if(imageData.getStatementDocName() !=null  && ! "".equals(imageData.getStatementDocName().trim())){
						sql.append(" AND STATEMENT_DOC_NAME = '"+imageData.getStatementDocName()+"' "); 
					}
					
					if(imageData.getOthersDocsName() !=null  && ! "".equals(imageData.getOthersDocsName().trim())){
						sql.append(" AND OTHER_DOC_NAME = '"+imageData.getOthersDocsName()+"' "); 
					}

					
//					sql=sql.append(" ORDER BY   temp.subfolderName , temp.documentName ,temp.imgFileName");
					
					sql=sql.append(" ORDER BY temp.creationDate desc");
					System.out.println("Query Of SQL ===== "+sql);
					// Added By Prachit: End
					
			//Query query = getSession().createQuery(sql.toString());
		//	DefaultLogger.debug(this, "getCustImageList()-------->" + sql.toString());
			list = getHibernateTemplate().find(sql.toString());
			
			//For temp
			StringBuffer sql1=new StringBuffer("SELECT FROM "+IImageUploadDao.TEMP_IMAGEUPLOAD+" temp ");
					sql1.append(" WHERE CUST_ID = '"+ imageData.getCustId() + "' " );
					sql1.append(" AND IMG_DEPRICATED='N'");
					
//					if(imageData.getCategory()!=null  && ! "".equals(imageData.getCategory().trim())){
//						sql1.append(" AND CATEGORY = '"+imageData.getCategory()+"' "); 
//					}
					if(imageData.getSubfolderName()!=null  && ! "".equals(imageData.getSubfolderName().trim())){
						sql1.append(" AND SUBFOLDER_NAME = '"+imageData.getSubfolderName()+"' ");
					}
//					if(imageData.getDocumentName()!=null  && ! "".equals(imageData.getDocumentName().trim())){
//						sql1.append("AND DOCUMENT_NAME = '"+imageData.getDocumentName()+"' ");
//					}
//					
//					//Uma :Prod issue: image upload - all the bank details are getting displayed.
					if(null!=imageData.getBank()  && ! "".equals(imageData.getBank().trim())){
						sql1.append("AND BANK_NAME = '"+imageData.getBank()+"' ");
					}
					
					//Added By Prachit: Start
//					if(imageData.getTypeOfDocument() !=null  && ! "".equals(imageData.getTypeOfDocument().trim())){
//						sql1.append(" AND TYPE_OF_DOC = '"+imageData.getTypeOfDocument()+"' "); 
//					}
					
					if(imageData.getCategory()!=null  && ! "".equals(imageData.getCategory().trim())){
					sql1.append(" AND CATEGORY = '"+imageData.getCategory()+"' "); 
				}
					
					if(imageData.getFacilityName() !=null  && ! "".equals(imageData.getFacilityName().trim())){
						sql1.append(" AND FACILITY_NAME = '"+imageData.getFacilityName()+"' "); 
					}
					
					if(imageData.getFacilityDocName() !=null  && ! "".equals(imageData.getFacilityDocName().trim())){
						sql1.append(" AND FACILITY_DOC_NAME = '"+imageData.getFacilityDocName()+"' "); 
					}
					
					if(imageData.getOtherDocName() !=null  && ! "".equals(imageData.getOtherDocName().trim())){
						sql1.append(" AND OTHER_FACILITY_DOC_NAME = '"+imageData.getOtherDocName()+"' "); 
					}
					
					/*if(imageData.getSecurityNameId() !=null  && ! "".equals(imageData.getSecurityNameId().trim())){
						sql1.append(" AND SECURITY_NAME_ID = '"+imageData.getSecurityNameId()+"' "); 
					}*/
					
					if(imageData.getSecurityIdi() !=null  && ! "".equals(imageData.getSecurityIdi().trim())){
						sql1.append(" AND SECURITY_NAME_ID = '"+imageData.getSecurityIdi()+"' "); 
					}
					
					if(imageData.getSecurityDocName() !=null  && ! "".equals(imageData.getSecurityDocName().trim())){
						sql1.append(" AND SECURITY_DOC_NAME = '"+imageData.getSecurityDocName()+"' "); 
					}
					
					if(imageData.getOtherSecDocName() !=null  && ! "".equals(imageData.getOtherSecDocName().trim())){
						sql1.append(" AND OTHER_SECURITY_DOC_NAME = '"+imageData.getOtherSecDocName()+"' "); 
					}
					if(imageData.getHasCam() !=null  && ! "".equals(imageData.getHasCam().trim())){
						sql1.append(" AND CAM_NUMBER = '"+imageData.getHasCam()+"' "); 
					}
					
					if(imageData.getCamDocName() !=null  && ! "".equals(imageData.getCamDocName().trim())){
						sql1.append(" AND CAM_DOC_NAME = '"+imageData.getCamDocName()+"' "); 
					}
					
					if(imageData.getStatementTyped() !=null  && ! "".equals(imageData.getStatementTyped().trim())){
						sql1.append(" AND STATEMENT_TYPE = '"+imageData.getStatementTyped()+"' "); 
					}
					
					if(imageData.getStatementDocName() !=null  && ! "".equals(imageData.getStatementDocName().trim())){
						sql1.append(" AND STATEMENT_DOC_NAME = '"+imageData.getStatementDocName()+"' "); 
					}
					
					if(imageData.getOthersDocsName() !=null  && ! "".equals(imageData.getOthersDocsName().trim())){
						sql1.append(" AND OTHER_DOC_NAME = '"+imageData.getOthersDocsName()+"' "); 
					}
					
//					sql1=sql1.append(" ORDER BY   temp.subfolderName , temp.documentName ,temp.imgFileName");
					
					sql1=sql1.append(" ORDER BY temp.creationDate desc");
					System.out.println("Query Of SQL1 === "+sql1);
					// Added By Prachit: End
					
			//Query query1 = getSession().createQuery(sql1.toString());
		//	DefaultLogger.debug(this, "getCustImageList()-------->" + sql1.toString());
			tempList = getHibernateTemplate().find(sql1.toString());
			
			
		} catch (NumberFormatException e) {
			throw new ImageTagException("ERROR-- Error while getting  Image List");
		} catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting  Image List");
		}
		
		List mainList = mergeList(list, tempList);
		
		Collections.sort(mainList, imgUploadByCreationDesc);
		
		return mainList;
		
	}
	
	public List getStoredCustImageList(String custId) throws ImageUploadException {
		List list = null;
		try {
			StringBuffer sql=new StringBuffer("SELECT FROM "+IImageUploadDao.STGE_TEMP_IMAGEUPLOAD+" temp ")
					.append(" WHERE temp.custId = ? and temp.creationDate between ? and ? " )
					.append(" ORDER BY temp.creationDate desc");
			String day = PropertyManager.getValue("local.file.cleanup.days");
			day = (day == null || day.length() == 0) ? "7" : day;
			Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -1 * Integer.parseInt(day));
			list = getHibernateTemplate().find(sql.toString(), new Object[] {custId, cal.getTime(), new Date()});
		} catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting  Image List");
		}
		return list;
	}
	
	/**
	 * This method insert data in the database using hibernate save method, in
	 * save method two parameter Entity Name and DAO
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return void
	 */
	public IImageUploadAdd createStageImageUpload(IImageUploadAdd imageData) throws ImageUploadException {		
		try {
			
			//IImageUploadAdd stageImageData;
			OBImageUploadAdd obImageUploadAdd = new OBImageUploadAdd();
			obImageUploadAdd.setImgFileName(imageData.getImgFileName());
			obImageUploadAdd.setImgSize((imageData.getImgSize()));
			obImageUploadAdd.setCustId(imageData.getCustId());
			obImageUploadAdd.setCustName(imageData.getCustName());
			obImageUploadAdd.setImgDepricated(imageData.getImgDepricated());
			obImageUploadAdd.setImageFilePath(imageData.getImageFilePath());
			

			DefaultLogger.debug(this, "IMAGE ID###############"+imageData.getImgId());
			//imageData = (IImageUploadAdd)(getHibernateTemplate().load(IImageUploadDao.TEMP_IMAGEUPLOAD, new Long(imageData.getImgId())));
			String key = (getHibernateTemplate().save(IImageUploadDao.STAGE_IMAGEUPLOAD, obImageUploadAdd)).toString();
			updateTempImageUploadSendForApp(imageData);
			imageData = (IImageUploadAdd) getHibernateTemplate().load(IImageUploadDao.STAGE_IMAGEUPLOAD, new Long(key));
			//imageData.setImgId(Long.parseLong(key));
		}catch (Exception e) {
			DefaultLogger.debug(this, "########### Error in createStageImageUpload() due to inserting data in database");
			e.printStackTrace();
			throw new ImageUploadException("Unable to Insert customer Image Details");
		}
		return imageData;
	}
	
	
	public IImageUploadAdd getImageDetailByTrxIDDao(String trxId) throws ImageUploadException,TrxParameterException,TransactionException
	{
		
		OBCMSTrxValue oBCMSTrxValue = (OBCMSTrxValue)getHibernateTemplate().find("SELECT FROM TRANSACTION WHERE TRANSACTION_ID = '"+ trxId +"'" );
		
		DefaultLogger.debug(this, "getReferenceID===============>"+oBCMSTrxValue.getReferenceID());
		long stagingImgId = Long.parseLong(oBCMSTrxValue.getReferenceID());
		
		return getImageUploadById(stagingImgId);
		
	}
	
	
	/**
	 * This method Update cms_temp_image_upload table for send for approval
	 * save method two parameter Entity Name and DAO
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return void
	 */
	public void updateTempImageUploadSendForApp(IImageUploadAdd imageData)throws ImageUploadException
	{
		try{
		imageData.setSendForAppFlag("Y");
		getHibernateTemplate().update(IImageUploadDao.TEMP_IMAGEUPLOAD, imageData);
		}catch (Exception e) {
			DefaultLogger.debug(this, "########### Error in updateTempImageUploadSendForApp() due to updateing data in database");
			e.printStackTrace();
			throw new ImageUploadException("Unable to Update send for appr status");
		}
	}
	
	/**
	 * This method Update cms_temp_image_upload table for send for approval
	 * save method two parameter Entity Name and DAO
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return void
	 */
	public IImageUploadAdd getImageUploadById(long stagingImgId)throws ImageUploadException
	{
		if(stagingImgId!=0){
		IImageUploadAdd imageUploadAdd = null;
		
		try{
		imageUploadAdd = (IImageUploadAdd) getHibernateTemplate().load(IImageUploadDao.STAGE_IMAGEUPLOAD, new Long(stagingImgId));
		}catch (Exception e) {
			DefaultLogger.debug(this, "########### Error in getImageUploadById()");
			e.printStackTrace();
			throw new ImageUploadException("Unable to get data for ImgstagingId");
		}
		return imageUploadAdd;
		}else
		{
			throw new ImageUploadException("Unable to get data for ImgstagingId due to ImgId is null");
		}
	}
	
	/**
	 * This method Update cms_temp_image_upload table for send for approval
	 * save method two parameter Entity Name and DAO
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return void
	 */
	public IImageUploadAdd getActualImageUploadById(long actualImgId)throws ImageUploadException
	{
		if(actualImgId!=0){
		IImageUploadAdd imageUploadAdd = null;
		
		try{
		imageUploadAdd = (IImageUploadAdd) getHibernateTemplate().load(IImageUploadDao.ACTUAL_IMAGEUPLOAD, new Long(actualImgId));
		}catch (Exception e) {
			DefaultLogger.debug(this, "########### Error in getImageUploadById()");
			e.printStackTrace();
			throw new ImageUploadException("Unable to get data for ImgActualId");
		}
		return imageUploadAdd;
		}else
		{
			throw new ImageUploadException("Unable to get data for ImgActualId due to ImgId is null");
		}
	}
	
	/**
	 * @return IImageUploadAdd Object
	 * @param Entity Name
	 * @param IImageUploadAdd Object  
	 * This method Updates IImageUploadAdd  Object
	 */
	public IImageUploadAdd updateImageUpload(String entityName, IImageUploadAdd item)throws ImageUploadException {
		if(!(entityName==null|| item==null)){
		getHibernateTemplate().update(entityName, item);

		return (IImageUploadAdd) getHibernateTemplate().load(entityName,
				new Long(item.getImgId()));
		}else{
			throw new ImageUploadException("ERROR- Entity name or key is null ");
		}

	}
	
	/**
	 * This method insert data in the database using hibernate save method, in
	 * save method two parameter Entity Name and DAO
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return void
	 */
	public IImageUploadAdd createActualImageUploadAdd(IImageUploadAdd imageData) throws ImageUploadException {	
		IImageUploadAdd actimageDate = null;
		try {
			DefaultLogger.debug(this, "IMAGE ID###############"+imageData.getImgId());
			//imageData = (IImageUploadAdd)(getHibernateTemplate().load(IImageUploadDao.TEMP_IMAGEUPLOAD, new Long(imageData.getImgId())));
			String key = (getHibernateTemplate().save(IImageUploadDao.ACTUAL_IMAGEUPLOAD, imageData)).toString();
			actimageDate = (IImageUploadAdd) getHibernateTemplate().load(IImageUploadDao.ACTUAL_IMAGEUPLOAD, new Long(key));
		}catch (Exception e) {
			DefaultLogger.debug(this, "########### Error in createActualImageUpload() due to inserting data in database");
			e.printStackTrace();
			throw new ImageUploadException("Unable to Insert customer Image Details");
		}
		return actimageDate;
	}
	
	//this method map data in ob class
	public IImageUploadAdd mapToOb(IImageUploadAdd imageData) {
		OBImageUploadAdd obImageUploadAdd = new OBImageUploadAdd();
		obImageUploadAdd.setImgFileName(imageData.getImgFileName());
		obImageUploadAdd.setImgSize((imageData.getImgSize()));
		obImageUploadAdd.setCustId(imageData.getCustId());
		obImageUploadAdd.setCustName(imageData.getCustName());
		obImageUploadAdd.setImgDepricated(imageData.getImgDepricated());
		obImageUploadAdd.setImageFilePath(imageData.getImageFilePath());
		
        return obImageUploadAdd;
	}
	
	/**
	 * This method creates Image Upload Detail
	 */

	public IImageUploadDetails createImageUploadDetail(String entityName,
			IImageUploadDetails imageUploadDetails) throws ImageUploadException {
		if (!(entityName == null || imageUploadDetails == null)) {

			Long key = (Long) getHibernateTemplate().save(entityName,
					imageUploadDetails);
			imageUploadDetails.setId(key.longValue());
			return imageUploadDetails;
		} else {
			throw new ImageUploadException("ERROR- Entity name or key is null ");
		}
	}
	
	

	/**
	 * This method creates Image Upload Detail Map
	 */

	public void createImageUploadMap(String entityName, IImageUploadDetailsMap imageData) {
		if (!(entityName == null || imageData == null)) {
		Long key = (Long) (getHibernateTemplate().save(entityName, imageData));
		
		}else {
			throw new ImageTagException("ERROR- Entity name or key is null ");
		}
	}

	
	/**
	 * @return list of Image to be displayed after upload
	 */
	public List getUploadImageList(String entityName, String uploadId) {
		List list = null;
		try {
			Long imgId;
			List listImageDetails =new ArrayList();
			long strUploadId = Long.parseLong(uploadId);

			DefaultLogger.debug(this,("select obImg.imageId from com.integrosys.cms.app.image.bus.OBImageUploadDetailsMap as obImg where obImg.uploadId = '"
							+ strUploadId + "'"));
			Query query = currentSession()
			.createQuery("select obImg.imageId from com.integrosys.cms.app.image.bus.OBImageUploadDetailsMap as obImg where obImg.uploadId = '"
					+ strUploadId + "'");
			DefaultLogger.debug(this, "getUploadImageList()-------->" + query);
			list = query.list();
			if (list != null) {
				for (Iterator it = list.iterator(); it.hasNext();) {

					imgId = (Long) it.next();

					listImageDetails.add(getImageDetails(imgId.longValue()));
					
				}

			}
			return listImageDetails;
		} catch (NumberFormatException e) {
			throw new ImageTagException("ERROR-- Error while getting Tag Image List");
		} catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting Tag Image List");
		}
		

	}
	
	/**
	 * @return Image tag details to be tagged
	 */
	public IImageUploadAdd getImageDetails(long key) {
		IImageUploadAdd obj = new OBImageUploadAdd();
		try {
			long strImgId = key;
			Query query = currentSession()
			.createQuery("from com.integrosys.cms.app.image.bus.OBImageUploadAdd as obImg where obImg.imgId = '"
					+ strImgId + "'");
			DefaultLogger.debug(this, "-------->" + query);
			obj = (OBImageUploadAdd) query.uniqueResult();
		} catch (NumberFormatException e) {
			throw new ImageTagException("ERROR-- Error while getting Image Details");
		} catch (Exception e) {
			throw new ImageTagException("ERROR-- Error while getting Image Details");
		}
		return obj;

	}
	
	/**
	 * @return Image Tag Detail
	 */

	public IImageUploadDetails getImageUpload(String entityName, Serializable key)
	throws ImageUploadException {

		if (!(entityName == null || key == null)) {

			return (IImageUploadDetails) getHibernateTemplate().get(entityName,
					key);
		} else {
			throw new ImageUploadException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * This method updates Image Upload
	 */

	public IImageUploadDetails updateImageUploadDetails(String entityName,
			IImageUploadDetails item) throws ImageUploadException {
		if (!(entityName == null || item == null)) {
			getHibernateTemplate().update(entityName, item);
			return (IImageUploadDetails) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new ImageUploadException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	
	/**
	 * This method creates Image Upload Map
	 */

	public Long createImageUploadDetailsMap(String entityName, IImageUploadDetailsMap imageData) {
		if (!(entityName == null || imageData == null)) {
		Long key = (Long) (getHibernateTemplate().save(entityName, imageData));
		IImageUploadAdd imageUploadAdd=getTempImageUploadById(imageData.getImageId());
		createActualImageUploadAdd(imageUploadAdd);
		updateTempImageUploadSendForApp(getTempImageUploadById(imageData.getImageId()));
		
		
		return key;
		}else {
			throw new ImageUploadException("ERROR- Entity name or key is null ");
		}
	}
	
	
	/**
	 * This method Update cms_temp_image_upload table for send for approval
	 * save method two parameter Entity Name and DAO
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return void
	 */
	public IImageUploadAdd getTempImageUploadById(long ImgId)throws ImageUploadException
	{
		if(ImgId!=0){
		IImageUploadAdd imageUploadAdd = null;
		
		try{
		imageUploadAdd = (IImageUploadAdd) getHibernateTemplate().load(IImageUploadDao.TEMP_IMAGEUPLOAD, new Long(ImgId));
		}catch (Exception e) {
			DefaultLogger.debug(this, "########### Error in getImageUploadById()");
			e.printStackTrace();
			throw new ImageUploadException("Unable to get data for ImgActualId");
		}
		return imageUploadAdd;
		}else
		{
			throw new ImageUploadException("Unable to get data for ImgActualId due to ImgId is null");
		}
	}

	public void removeImagesUpload(String entityName, IImageUploadAdd obImage)
			throws ImageUploadException {
		if(obImage!=null){
			try {
				getHibernateTemplate().update(entityName,obImage);
			} catch (Exception e) {
				throw new ImageUploadException("Unable to delete image", e);
			}			
		}
		
	}

	public List listTempImagesUpload() throws ImageUploadException {
		List oBImageUploadAddList = null;
		try {
			String fromServer=PropertyManager.getValue("integrosys.server.identification","APP1");
			oBImageUploadAddList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+IImageUploadDao.TEMP_IMAGEUPLOAD+" WHERE STATUS = '0' AND FROM_SERVER='"+fromServer+"'");
		} 
		catch (Exception e) {
			DefaultLogger.debug(this, "########### Error in getCustImageList() due to HQL");
			e.printStackTrace();
			throw new ImageUploadException("Unable to get customer Image List");
		} 
		return oBImageUploadAddList;
	}

	public void updateTempImageUpload(IImageUploadAdd obImage) throws ImageUploadException {
		DefaultLogger.debug(this,"updateTempImageUpload()......");
		if(obImage!=null){
			try {
				DefaultLogger.debug(this,"Updating Image......");
				getHibernateTemplate().update(IImageUploadDao.TEMP_IMAGEUPLOAD,obImage);
				DefaultLogger.debug(this,"Image Updated......");
			} catch (Exception e) {
				throw new ImageUploadException("Unable to delete image", e);
			}			
		}
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.image.bus.IImageUploadDao#getSubFolderNameList(java.lang.String, java.lang.String)
	 */
	public List getSubFolderNameList(String custID, String catgory)
			throws ImageUploadException {
		List subFolderNameList =new ArrayList();
			DefaultLogger.debug(this, "getUploadImageList()1-------->" +DateUtil.getDate().getTime());
			String sql="select distinct(img.subfolder_name)as subfolder_name from CMS_UPLOADED_IMAGES img" +
					" where img.cust_id= '"+ custID + "'"+
					" AND img.category = '"+ catgory + "'" +
					" AND img.img_depricated = 'N'" +
					" ORDER BY img.subfolder_name";
			
			String sql1="select distinct(img.subfolder_name)as subfolder_name from CMS_TEMP_IMAGE_UPLOAD img" +
					" where img.cust_id= '"+ custID + "'"+
					" AND img.category = '"+ catgory + "'" +
					" AND img.img_depricated = 'N'" +
					" ORDER BY img.subfolder_name";
			
			DBUtil dbUtil=null;
		//	DefaultLogger.debug(this, "sql is " + sql);
			TreeMap map= new TreeMap();
			try {
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				ResultSet rs = dbUtil.executeQuery();
				String temp;
				while(rs.next()){
					temp=rs.getString("subfolder_name");
					if(temp!=null){
					map.put(temp, temp);
					}
				}
				rs.close();
				
				dbUtil.setSQL(sql1);
				ResultSet rs1 = dbUtil.executeQuery();
				while(rs1.next()){
					temp=rs1.getString("subfolder_name");
					if(temp!=null){
						map.put(temp, temp);
						}
				}
				rs1.close();
				subFolderNameList.addAll(map.values());
				return subFolderNameList;
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getSubFolderNameList", ex);
			}
			catch (Exception ex) {
				throw new SearchDAOException("Exception in getSubFolderNameList", ex);
			}
			finally {
				try {
					dbUtil.close();
				}
				catch (SQLException ex) {
					throw new SearchDAOException("SQLException in getSubFolderNameList", ex);
				}
			}
			
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.image.bus.IImageUploadDao#getDocumentNameList(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getDocumentNameList(String custID, String catgory,
			String subfolderName) throws ImageUploadException {
		List documentNameList =new ArrayList();
			/*Query query = getSession().createQuery("select DISTINCT obImg.subfolderName  from com.integrosys.cms.app.image.bus.OBImageUploadAdd as obImg where " +
					" obImg.custId = = '"+ custID + "'"+
					" AND obImg.category = '"+ catgory + "'"+
					" AND obImg.subfolderName = '"+ subfolderName + "'");
			DefaultLogger.debug(this, "getUploadImageList()-------->" + query);
			documentNameList = query.list();*/
			
			String sql="select img.document_name as document_name from CMS_UPLOADED_IMAGES img" +
					" where img.cust_id= '"+ custID + "'"+
					" AND img.category = '"+ catgory + "'"+
					" AND img.img_depricated = 'N'" +
					" AND img.subfolder_name = '"+ subfolderName + "'"+
					" ORDER BY img.document_name";
			
			String sql1="select distinct(img.document_name)as document_name from CMS_TEMP_IMAGE_UPLOAD img" +
					" where img.cust_id= '"+ custID + "'"+
					" AND img.category = '"+ catgory + "'"+
					" AND img.img_depricated = 'N'" +
					" AND img.subfolder_name = '"+ subfolderName + "'"+
					" ORDER BY img.document_name";
			DBUtil dbUtil=null;
		//	DefaultLogger.debug(this, "sql is " + sql);
			TreeMap map= new TreeMap();
			try {
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				ResultSet rs = dbUtil.executeQuery();
				String temp;
				while(rs.next()){
					temp=rs.getString("document_name");
					if(temp!=null){
						map.put(temp, temp);
						}
				}
				rs.close();

				dbUtil.setSQL(sql1);
				ResultSet rs1 = dbUtil.executeQuery();
				while(rs1.next()){
					temp=rs1.getString("document_name");
					if(temp!=null){
						map.put(temp, temp);
						}
				}
				rs1.close();
				documentNameList.addAll(map.values());
				return documentNameList;
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getDocumentNameList", ex);
			}
			catch (Exception ex) {
				throw new SearchDAOException("Exception in getDocumentNameList", ex);
			}
			finally {
				try {
					dbUtil.close();
				}
				catch (SQLException ex) {
					throw new SearchDAOException("SQLException in getDocumentNameList", ex);
				}
			}
			
	}
	
	
	public List getFacilityDocumentNameList(String facCode) throws ImageUploadException {
		List facilityDocumentNameList =new ArrayList();
			String sql="SELECT DOC_DESCRIPTION FROM CMS_DOCUMENT_ITEM " + 
					" WHERE MASTERLIST_ID IN( " + 
					"  SELECT MASTERLIST_ID FROM CMS_DOCUMENT_MASTERLIST " + 
					"  WHERE SECURITY_SUB_TYPE_ID='"+facCode+"')";
			
			DBUtil dbUtil=null;
			TreeMap map= new TreeMap();
			try {
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				ResultSet rs = dbUtil.executeQuery();
				String temp;
				while(rs.next()){
					temp=rs.getString("DOC_DESCRIPTION");
					if(temp!=null){
						map.put(temp, temp);
						}
				}
				map.put("Other Additional Document","Other Additional Document");
				rs.close();
				facilityDocumentNameList.addAll(map.values());
				return facilityDocumentNameList;
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getFacilityDocumentNameList", ex);
			}
			catch (Exception ex) {
				throw new SearchDAOException("Exception in getFacilityDocumentNameList", ex);
			}
			finally {
				try {
					dbUtil.close();
				}
				catch (SQLException ex) {
					throw new SearchDAOException("SQLException in getFacilityDocumentNameList", ex);
				}
			}
	}
	
	
	
	public List getSecurityDocumentNameList(String secCode) throws ImageUploadException {
		List securityDocumentNameList =new ArrayList();
//			String sql="SELECT DOC_DESCRIPTION FROM CMS_DOCUMENT_ITEM " + 
//					"  WHERE MASTERLIST_ID IN( " + 
//					"  SELECT MASTERLIST_ID FROM CMS_DOCUMENT_MASTERLIST " + 
//					"  WHERE SECURITY_SUB_TYPE_ID IN( " + 
//					"  SELECT LMT_FAC_CODE FROM SCI_LSP_APPR_LMTS " + 
//					"  WHERE CMS_LSP_APPR_LMTS_ID IN (SELECT CMS_LSP_APPR_LMTS_ID FROM CMS_LIMIT_SECURITY_MAP " + 
//					"  WHERE CMS_COLLATERAL_ID ='"+secCode+"')))";
		
//		String sql="SELECT DOC_DESCRIPTION " + 
//				"FROM CMS_DOCUMENT_ITEM   " + 
//				"WHERE MASTERLIST_ID IN(   SELECT MASTERLIST_ID FROM CMS_DOCUMENT_MASTERLIST   " + 
//				"WHERE CATEGORY='S' AND SECURITY_SUB_TYPE_ID IN(   SELECT SECURITY_SUB_TYPE_ID FROM CMS_SECURITY     " + 
//				"WHERE CMS_COLLATERAL_ID ='"+secCode+"')) AND IS_DELETED='N' AND (IS_MANDATORY_DISPLAY = 'Y' OR IS_MANDATORY='Y') ";
		
		String sql1 = "SELECT TYPE_NAME,SUBTYPE_NAME FROM CMS_SECURITY WHERE CMS_COLLATERAL_ID = '"+secCode+"' ";
		System.out.println("getSecurityDocumentNameList=> sql1=>"+sql1);
		DBUtil dbUtil1=null;
		DBUtil dbUtil=null;
		String typeName = "";
		String subTypeName = "";
		try {
			dbUtil1 = new DBUtil();
			dbUtil1.setSQL(sql1);
			ResultSet rs1 = dbUtil1.executeQuery();
			while(rs1.next()){
				typeName = rs1.getString("TYPE_NAME");
				subTypeName = rs1.getString("SUBTYPE_NAME");
			}
			rs1.close();
			dbUtil1.close();
		
		
		String sql="SELECT DOCUMENT_DESCRIPTION FROM CMS_DOCUMENT_GLOBALLIST WHERE DOCUMENT_CODE IN ( " + 
				"SELECT DISTINCT DOCUMENT_CODE " + 
				"FROM CMS_DOCUMENT_ITEM    " + 
				"WHERE MASTERLIST_ID IN(   SELECT MASTERLIST_ID FROM CMS_DOCUMENT_MASTERLIST    " + 
				"WHERE CATEGORY='S' AND SECURITY_SUB_TYPE_ID IN(   SELECT DISTINCT SECURITY_SUB_TYPE_ID  " + 
				"FROM CMS_SECURITY WHERE SUBTYPE_NAME = '"+subTypeName+"' AND TYPE_NAME = '"+typeName+"')) " + 
				"AND IS_DELETED='N') ";
		System.out.println("getSecurityDocumentNameList=> sql=>"+sql);
			
			TreeMap map= new TreeMap();
			
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				ResultSet rs = dbUtil.executeQuery();
				String temp;
				while(rs.next()){
					temp=rs.getString("DOCUMENT_DESCRIPTION");
					if(temp!=null){
						map.put(temp, temp);
						}
				}
				map.put("Other Additional Document","Other Additional Document");
				rs.close();
				securityDocumentNameList.addAll(map.values());
				return securityDocumentNameList;
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getSecurityDocumentNameList", ex);
			}
			catch (Exception ex) {
				throw new SearchDAOException("Exception in getSecurityDocumentNameList", ex);
			}
			finally {
				try {
					dbUtil.close();
				}
				catch (SQLException ex) {
					throw new SearchDAOException("SQLException in getSecurityDocumentNameList", ex);
				}
			}
	}

	/**
	 * Get all the image detail for given custId of customer.
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return a List of image detail records
	 */
	
	public ArrayList getCustRemoveImageList(IImageUploadAdd imageData)
			throws ImageUploadException {
		ArrayList returnList = new ArrayList();
		ArrayList oBActualImageUploadList = new ArrayList();
		ArrayList oBTempImageUploadList = new ArrayList();
		String strCustId = imageData.getCustId();
		try {
			DefaultLogger.debug(this, "Inside getCustRemoveImageList ================= 1");
			//from Actual table
			oBActualImageUploadList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+IImageUploadDao.ACTUAL_UPLOADED_IMAGES+" WHERE CUST_ID = '"+ strCustId + "' AND IMG_DEPRICATED='N' order by img_filename");
			if(oBActualImageUploadList!=null && oBActualImageUploadList.size()>0)
				returnList.addAll(oBActualImageUploadList);
			
			DefaultLogger.debug(this, "Inside getCustRemoveImageList ================= 2");
			
			//from Actual table only images with status =4 i.e. processed by DB2CM
			oBTempImageUploadList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+IImageUploadDao.TEMP_IMAGEUPLOAD+" WHERE CUST_ID = '"+ strCustId + "' AND IMG_DEPRICATED='N' AND STATUS = 4 order by img_filename");
//			oBTempImageUploadList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+IImageUploadDao.TEMP_IMAGEUPLOAD+" WHERE CUST_ID = '"+ strCustId + "' AND IMG_DEPRICATED='N' AND STATUS IN (0,2,4) order by img_filename");
			if(oBTempImageUploadList!=null && oBTempImageUploadList.size()>0)
				returnList.addAll(oBTempImageUploadList);
			
			DefaultLogger.debug(this, "Inside getCustRemoveImageList ================= 3");
		} 
		catch (Exception e) {
			DefaultLogger.debug(this, "########### Error in getCustImageList() due to HQL");
			e.printStackTrace();
			throw new ImageUploadException("Unable to get customer Image List");
		}
		
		Collections.sort(returnList, imgUploadByCreationDesc);
		
		return returnList;
	}
	
	
	public ArrayList getImageIdWithTagList(ArrayList custImageList)
			throws ImageUploadException {
		
		DBUtil dbUtil=null;
		ArrayList returnList = new ArrayList();
		
		if (custImageList != null && custImageList.size() > 0){
			
			DefaultLogger.debug(this, "Inside getImageIdWithTagList ================ start");
			for (int i = 0; i < custImageList.size(); i++)  {
				
				IImageUploadAdd image = (IImageUploadAdd) custImageList.get(i);
				
				String sql = "select count(*) as count from cms_image_tag_map where IMAGE_ID='"+image.getImgId()+"' and UNTAGGED_STATUS = 'N'";
				
				try {
					dbUtil = new DBUtil();
					dbUtil.setSQL(sql);
					ResultSet rs = dbUtil.executeQuery();
					String temp;
					while(rs.next()){
						temp=rs.getString("count");
						if(Integer.parseInt(temp) > 0){
							returnList.add(image);
							}
					}
					rs.close();
					
				}
				catch (SQLException ex) {
					throw new SearchDAOException("SQLException in getImageIdWithTagList", ex);
				}
				catch (Exception ex) {
					throw new SearchDAOException("Exception in getImageIdWithTagList", ex);
				}
				finally {
					try {
						dbUtil.close();
					}
					catch (SQLException ex) {
						throw new SearchDAOException("SQLException in getImageIdWithTagList", ex);
					}
				}
				
			}
		}
		
		DefaultLogger.debug(this, "Inside getImageIdWithTagList ============= end");
		
		return returnList;
	}
	

	private List mergeList(List actual, List temp) {
		List returnList = new ArrayList();
		if (actual != null && actual.size() > 0)
			returnList.addAll(actual);
		if (temp != null && temp.size() > 0)
			returnList.addAll(temp);
		return returnList;

	}	
	
	
/*	
	public String getSequenceNo() {
		int seqNo=0;
		List seqNoList = new ArrayList();
		SessionFactory sessionFactory = getHibernateTemplate().getSessionFactory();
		
		//Current Session - no need to close
		Session currentSession = sessionFactory.getCurrentSession();
		
		//open new session
		//Session newSession = sessionFactory.openSession();
		String sql = "SELECT HCP_IMAGE_SEQ.NEXTVAL FROM dual";
		Query query = currentSession.createSQLQuery(sql);
		seqNoList = query.list();
		
		return String.valueOf(seqNoList.indexOf(0)).toString();
	}*/
	
	
	public String getSequenceNo() {
		List seqNoList = new ArrayList();
		Query query1 = currentSession().createSQLQuery("SELECT HCP_IMAGE_SEQ.NEXTVAL FROM dual");
		seqNoList = (List) query1.list();
		System.out.println("seqNoList.............."+seqNoList);
		return String.valueOf(seqNoList.get(0)).toString();
		
	}

	
}
