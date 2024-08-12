package com.integrosys.cms.app.imageTag.bus;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.ui.imageTag.ImageTagException;

/**
 * @author Abhijit R.
 * Abstract Bus Manager of Image Tag
 */
public abstract class AbstractImageTagBusManager implements
		IImageTagBusManager {

	private IImageTagDao imageTagDao;

	
	
	
	public IImageTagDao getImageTagDao() {
		return imageTagDao;
	}

	public void setImageTagDao(IImageTagDao imageTagDao) {
		this.imageTagDao = imageTagDao;
	}

	public abstract String getImageTagName();
	
	/**
	  * @return Particular Image Tag according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */
//
//	public IImageTag getImageTagById(long id)
//			throws ImageTagException, TrxParameterException,
//			TransactionException {
//		if (id != 0) {
//			return getImageTagDao().getImageTag(
//					getImageTagName(), new Long(id));
//		} else {
//			throw new SystemBankException(
//					"ERROR-- Key for Object Retrival is null.");
//		}
//	}
	/**
	 * @return List of all authorized Image Tag according to Search Criteria provided.
	 * 
	 */

//	public List getAllImageTag(String searchBy, String searchText)throws ImageTagException,TrxParameterException,TransactionException {
//
//		return getImageTagJdbc().getAllImageTag(searchBy,
//				searchText);
//	}
	/**
	 * @return List of all authorized Image Tag
	 */

//	public List getAllImageTag()throws ImageTagException,TrxParameterException,TransactionException,ConcurrentUpdateException {
//
//		return getImageTagJdbc().getAllImageTag();
//	}
	/**
	 * @return List of all authorized Image Tag according to Search Criteria provided.
	 * 
	 */
//	public List searchBranch(String login)throws ImageTagException,TrxParameterException,TransactionException {
//
//		return getImageTagJdbc().getAllImageTagSearch(login);
//	}
//	
	/**
	 @return ImageTag Object after update
	 * 
	 */

//	public IImageTag updateImageTag(IImageTag item)
//			throws ImageTagException, TrxParameterException,
//			TransactionException {
//		try {
//			return getImageTagDao().updateImageTag(
//					getImageTagName(), item);
//		} catch (HibernateOptimisticLockingFailureException ex) {
//			throw new ImageTagException("current ImageTag ["
//					+ item + "] was updated before by ["
//					+ item.getImageTagCode() + "] at ["
//					+ item.getImageTagName() + "]");
//		}
//	}
	/**
	 @return ImageTag Object after delete
	 * 
	 */
//	public IImageTag deleteImageTag(IImageTag item)
//			throws ImageTagException, TrxParameterException,
//			TransactionException {
//		try {
//			return getImageTagDao().deleteImageTag(
//					getImageTagName(), item);
//		} catch (HibernateOptimisticLockingFailureException ex) {
//			throw new ImageTagException("current ImageTag ["
//					+ item + "] was updated before by ["
//					+ item.getImageTagCode() + "] at ["
//					+ item.getImageTagName() + "]");
//		}
//	}
	/**
	 @return ImageTag Object after create
	 * 
	 */

	public IImageTagDetails createImageTagDetail(
			IImageTagDetails imageTag)
			throws ImageTagException {
		if (!(imageTag == null)) {
			return getImageTagDao().createImageTagDetail(getImageTagName(), imageTag);
		} else {
			throw new ImageTagException(
					"ERROR- Image Tag object   is null. ");
		}
	}

	/**
	 * @return List of all authorized Image Tag according to Search Criteria provided.
	 * 
	 */
	public IImageTagDetails getImageTagById(long id)
			throws ImageTagException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getImageTagDao().getImageTag(
					getImageTagName(), new Long(id));
		} else {
			throw new SystemBankException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 * @return List of  Image Tag according to Search Criteria provided.
	 * 
	 */
/*	public List getTagImageList(String tagId) throws ImageTagException {

		return (List) getImageTagDao().getTagImageList(
				IImageTagDao.ACTUAL_IMAGE_MAP, tagId);
	}*/
	
	/**
	 @return ImageTag Object after update
	 * 
	 */
	public IImageTagDetails updateImageTag(IImageTagDetails item)
	throws ImageTagException, TrxParameterException,
	TransactionException {
		try {
			return getImageTagDao().updateImageTag(getImageTagName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ImageTagException("current ImageTag ["
					+ item + "] was updated before by ["
					+ item.getCustId() + "] at ["
					+ item.getCustId() + "]");
		}
	}
	

}