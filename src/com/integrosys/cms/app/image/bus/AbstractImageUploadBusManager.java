package com.integrosys.cms.app.image.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.image.IImageUploadAdd;


/**
 *@author $Govind: Sahu  $
 * Abstract Image Upload Bus manager 
 */
public abstract class AbstractImageUploadBusManager implements IImageUploadBusManager {

	private IImageUploadDao imageUploadDao;

	/**
	 * @return the imageUploadDao
	 */
	public IImageUploadDao getImageUploadDao() {
		return imageUploadDao;
	}

	/**
	 * @param imageUploadDao the imageUploadDao to set
	 */
	public void setImageUploadDao(IImageUploadDao imageUploadDao) {
		this.imageUploadDao = imageUploadDao;
	}
/*
 * this method return actual entity name
 */
	public abstract String getImageUploadName();
	
	public abstract String getImageUploadDetailsName();
	
	/**
	  * @return Particular System Bank  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	
    public void createImageUploadAdd(IImageUploadAdd imageData) throws ImageUploadException
    {
    	if(imageData!=null){
    		  getImageUploadDao().createImageUpload(imageData);
    		}else{
    			throw new ImageUploadException("ERROR-- Key for Object Retrival is null.");
    		}
    }
    
    
    public void createImageUploadAdd(IImageUploadAdd imageData, boolean isActual) throws ImageUploadException
    {
    	if(imageData != null){
  		  getImageUploadDao().createImageUpload(imageData, isActual);
  		}else{
  			throw new ImageUploadException("ERROR-- Key for Object Retrival is null.");
  		}
    }
	
	/**
	 * This method get CustImage List from table
	 *  method in parameter Object
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return List
	 */
	public List getCustImageList(IImageUploadAdd imageData) throws ImageUploadException
	{
		if(imageData!=null){
			return getImageUploadDao().getCustImageList(imageData);
  		}else{
  			throw new ImageUploadException("ERROR-- Key for Object Retrival is null.");
  		}
		
	}
	
	public List getStoredCustImageList(String custId) throws ImageUploadException {
		if(custId!=null){
			return getImageUploadDao().getStoredCustImageList(custId);
  		}else{
  			throw new ImageUploadException("ERROR-- Key for Object Retrival is null.");
  		}
	}
	
	/**
	 * This method Insert data in table
	 * method in parameter Object
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return IImageUploadAdd
	 */
	public IImageUploadAdd createStageImageUploadAdd(IImageUploadAdd imageData) throws ImageUploadException,TrxParameterException,TransactionException
	{
		try {
			return getImageUploadDao().createStageImageUpload(imageData);
		}
		catch (ImageUploadException ex) {
			throw new ImageUploadException("Current ImageUpload [" + imageData.getImgId() + "]not created in staging table");
				
		}
	}	
	
	/**
	 * This method get records from cms_temp_image_upload table
	 * get method two parameter Entity Name and DAO
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return void
	 */
	public IImageUploadAdd getImageUploadById(long stagingImgId)throws ImageUploadException
	{
		
		try {
			return getImageUploadDao().getImageUploadById(stagingImgId);
		}
		catch (ImageUploadException ex) {
			throw new ImageUploadException("Current ImageUpload [" + stagingImgId + "]not in staging table");
				
		}
	}
	
	/**
	 * This method get records from cms_temp_image_upload table
	 * get method two parameter Entity Name and DAO
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return void
	 */
	public IImageUploadAdd getActualImageUploadById(long actualImgId)throws ImageUploadException
	{
		
		try {
			return getImageUploadDao().getActualImageUploadById(actualImgId);
		}
		catch (ImageUploadException ex) {
			throw new ImageUploadException("Current ImageUpload [" + actualImgId + "] id not in actual table");
				
		}
	}
	
	
	
	/**
	 * This method get records from cms_temp_image_upload table
	 * get method two parameter Entity Name and DAO
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return void
	 */
	public IImageUploadAdd getImageDetailByTrxIDDao(String trxId)
	throws ImageUploadException, TrxParameterException,
	TransactionException {

		return getImageUploadDao().getImageDetailByTrxIDDao(trxId);
	}
	
	/**
	 @return ImageUploadAdd Object after update
	 * 
	 */
	public IImageUploadAdd updateImageUpload(IImageUploadAdd item) throws ImageUploadException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		try {
			return getImageUploadDao().updateImageUpload(getImageUploadName(), item);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new ImageUploadException("Current ImageUpload [" + item + "] was updated before by ["
					+ item.getCustId() + "] at [" + item.getCustName() + "]");
		}
		
	}
	
	/**
	 * This method Insert data in table
	 * method in parameter Object
	 * 
	 * @param imageData is of type IImageUploadAdd
	 * @throws Exception on errors
	 * @throws ImageUploadException on errors
	 * @return IImageUploadAdd
	 */
	public IImageUploadAdd createActualImageUploadAdd(IImageUploadAdd imageData) throws ImageUploadException{
		try {
			return getImageUploadDao().createActualImageUploadAdd(imageData);
		}
		catch (ImageUploadException ex) {
			throw new ImageUploadException("Current ImageUpload [" + imageData.getImgId() + "]not created in actual table");
				
		}
	}	
	
	/**
	 * @return List of all authorized Image Tag according to Search Criteria provided.
	 * 
	 */
	public IImageUploadDetails getImageUploadDetailsById(long id)
			throws ImageUploadException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getImageUploadDao().getImageUpload(
					getImageUploadDetailsName(), new Long(id));
		} else {
			throw new ImageUploadException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 @return ImageUpload Object after update
	 * 
	 */
	public IImageUploadDetails updateImageUploadDetails(IImageUploadDetails item)
	throws ImageUploadException, TrxParameterException,
	TransactionException {
		try {
			return getImageUploadDao().updateImageUploadDetails(getImageUploadDetailsName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ImageUploadException("current ImageUpload ["
					+ item + "] was updated before by ["
					+ item.getCustId() + "] at ["
					+ item.getCustId() + "]");
		}
	}
	

	/**
	 * This method creates Image Upload Detail for checker
	 */
	public Long checkerCreateImageUploadDetailsMap(IImageUploadDetailsMap imageData)
	throws ImageUploadException {

		return getImageUploadDao().createImageUploadDetailsMap(
				IImageUploadDao.ACTUAL_IMAGE_MAP, imageData);
	}

	/** This method retrieves the SubFolderName List by category from Actual Table 
	 * @see com.integrosys.cms.app.image.bus.IImageUploadBusManager#getSubFolderNameList(java.lang.String, java.lang.String)
	 */
	public List getSubFolderNameList(String custID, String catgory)
			throws ImageUploadException {
		return getImageUploadDao().getSubFolderNameList(custID, catgory);
	}

	/** This method retrieves the documentName List by category and subfolderName from Actual Table
	 * @see com.integrosys.cms.app.image.bus.IImageUploadBusManager#getDocumentNameList(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getDocumentNameList(String custID, String catgory,
			String subfolderName) throws ImageUploadException {
		return getImageUploadDao().getDocumentNameList(custID, catgory,subfolderName);	}
	
	public ArrayList getCustRemoveImageList(IImageUploadAdd imageData) throws ImageUploadException
	{
		if(imageData!=null){
			return getImageUploadDao().getCustRemoveImageList(imageData);
  		}else{
  			throw new ImageUploadException("ERROR-- Key for Object Retrival is null.");
  		}
		
	}	
	
	public ArrayList getImageIdWithTagList(ArrayList custImageList) throws ImageUploadException
	{
		if(custImageList!=null){
			return getImageUploadDao().getImageIdWithTagList(custImageList);
  		}else{
  			throw new ImageUploadException("ERROR-- Key for Object Retrival is null.");
  		}
		
	}	
	
	public List getFacilityDocumentNameList(String facCode) throws ImageUploadException {
		return getImageUploadDao().getFacilityDocumentNameList(facCode);	}
	
	public List getSecurityDocumentNameList(String secCode) throws ImageUploadException {
		return getImageUploadDao().getSecurityDocumentNameList(secCode);	}

}