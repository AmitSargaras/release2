package com.integrosys.cms.app.fileUpload.bus;

import java.io.Serializable;
import java.util.ArrayList;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.cms.app.FileUploadLog.OBFileUploadLog;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public class FileUploadDaoImpl extends HibernateDaoSupport implements IFileUploadDao{
	
	private IFileUploadDao fileUploadDao;
	public IFileUploadDao getFileUploadDao() {
		return fileUploadDao;
	}
	public void setFileUploadDao(IFileUploadDao fileUploadDao) {
		this.fileUploadDao = fileUploadDao;
	}
	public IFileUpload makerCreateFile(String entityName,
			IFileUpload fileUpload)
			throws FileUploadException {
		if(!(entityName==null|| fileUpload==null)){
			Long key = (Long) getHibernateTemplate().save(entityName, fileUpload);
			fileUpload.setId(key.longValue());
			return fileUpload;
			}else{
				throw new FileUploadException("ERROR- Entity name or key is null  in FileUploadDaoImpl");
			}
	}
	
	public void createUbsFile(String entityName,OBUbsFile fileUpload)throws FileUploadException{ 
		if(!(entityName==null|| fileUpload==null)){
			Long key = (Long) getHibernateTemplate().save(entityName, fileUpload);
			fileUpload.setId(key.longValue());
			}else{
				throw new FileUploadException("ERROR- Entity name or key is null  in FileUploadDaoImpl With UBS File");
			}
	}

	
	public void createFinwareFile(String entityName,OBFinwareFile fileUpload)throws FileUploadException{ 
		if(!(entityName==null|| fileUpload==null)){
			Long key = (Long) getHibernateTemplate().save(entityName, fileUpload);
			fileUpload.setId(key.longValue());
			}else{
				throw new FileUploadException("ERROR- Entity name or key is null  in FileUploadDaoImpl With Finware File");
			}
	}
	
	public void createBahrainFile(String entityName,OBBahrainFile fileUpload)throws FileUploadException{ 
		if(!(entityName==null|| fileUpload==null)){
			Long key = (Long) getHibernateTemplate().save(entityName, fileUpload);
			fileUpload.setId(key.longValue());
			}else{
				throw new FileUploadException("ERROR- Entity name or key is null  in FileUploadDaoImpl With Finware File");
			}
	}
	
	public void createHongKongFile(String entityName,OBHongKongFile fileUpload)throws FileUploadException{ 
		if(!(entityName==null|| fileUpload==null)){
			Long key = (Long) getHibernateTemplate().save(entityName, fileUpload);
			fileUpload.setId(key.longValue());
			}else{
				throw new FileUploadException("ERROR- Entity name or key is null  in FileUploadDaoImpl With HongKong File");
			}
	}
	
	
	
public IFileUpload getFileUpload(String entityName, Serializable key)throws FileUploadException {
		if(!(entityName==null|| key==null)){
		return (IFileUpload) getHibernateTemplate().get(entityName, key);
		}else{
			throw new FileUploadException("ERROR-- Entity Name Or Key is null");
		}
	}

	public IFileUpload updateFileUpload(String entityName, IFileUpload item)throws FileUploadException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IFileUpload) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new FileUploadException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public IFileUpload createFileUpload(String entityName,IFileUpload anFile)throws FileUploadException {
		if(!(entityName==null|| anFile==null)){	
			Long key = (Long) getHibernateTemplate().save(entityName, anFile);
			anFile.setId(key.longValue());
			return anFile;
			}else{
				throw new FileUploadException("ERROR- Entity name or key is null ");
			}
	}
	
	public void insertFileUploadMessage(OBFileUploadLog obfileuploadlog) {
		try {
				System.out.println("Going for insertFileUploadMessage => save data in table CMS_FILE_UPLOAD_LOG");
				Long key = (Long) getHibernateTemplate().save("fileUploadlogStatus", obfileuploadlog);
			
		} catch (Exception e) {
			System.out.println("Exception in insertFileUploadMessage.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	
	}
	
	@Override
	public String getfileuploadidFromSeq() {
		String fileuploadid = null;
		try {
			System.out.println("Inside getfileuploadidFromSeq=>");
		fileuploadid= (new SequenceManager()).getSeqNum("CMS_FILE_UPLOAD_SEQ", true);
			System.out.println("------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.fileuploadid from DB SEQ"+fileuploadid);
		} catch (Exception e1) {
			System.out.println("Exception while fetching Seq getfileuploadidFromSeq=>e=>"+e1);
			e1.printStackTrace();
		}
	return fileuploadid;
	}
	
	/*@Override
	public String getfileuploadidFromSeq() {
		try {
			String query = " select CMS_FILE_UPLOAD_SEQ.nextval from DUAL ";
			System.out.println("Inside getfileuploadidFromSeq=>query=>"+query);
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query);
				if(resultList!=null && resultList.size()>0){
					return (String) resultList.get(0); 
				}
			}else{
				System.out.println("Exception while fetching Seq getfileuploadidFromSeq...query empty.");
			}
		} catch (Exception e) {
			System.out.println("Exception while fetching Seq getfileuploadidFromSeq=>e=>"+e);
			e.printStackTrace();
			
		}
		return null;
	}
	
	@Override
	public String getDocumentCount() {
		try {
			String query = " select COUNT(1) AS CNT from CMS_DOCUMENT_GLOBALLIST ";
			System.out.println("Inside getDocumentCount=>query=>"+query);
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query);
				if(resultList!=null && resultList.size()>0){
					return (String) resultList.get(0); 
				}
			}else{
				System.out.println("Exception while fetching Seq getDocumentCount...query empty.");
			}
		} catch (Exception e) {
			System.out.println("Exception while fetching Seq getDocumentCount=>e=>"+e);
			e.printStackTrace();
			
		}
		return null;
	}*/
	
	

}
