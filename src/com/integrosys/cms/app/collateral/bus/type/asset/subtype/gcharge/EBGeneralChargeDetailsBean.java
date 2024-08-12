/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBDebtorBean.java,v 1.11 2005/08/12 03:32:36 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import org.springframework.util.CollectionUtils;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.batch.common.BatchResourceFactory;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.ILeadBankStock;

/*
 * This is EBDebtorBean.java class
 * @author $Author: wltan $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/08/12 03:32:36 $
 * Tag: $Name:  $
 */

public abstract class EBGeneralChargeDetailsBean implements IGeneralChargeDetails, EntityBean {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_GENERAL_CHARGE_DETAILS;

	private static final String[] EXCLUDE_METHOD = new String[] { "getGeneralChargeDetailsID", "getCmsCollatralId" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBGeneralChargeDetailsBean() {
	}

	// ************* Non-persistent methods ***********
	/**
	 * Get the contact ID
	 * 
	 * @return long
	 */
	public long getGeneralChargeDetailsID() {
		if (null != getGeneralChargeDetailsPK()) {
			return getGeneralChargeDetailsPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	
	public long getCmsCollatralId() {
		if (null != getCmsCollatralIdFK()) {
			return getCmsCollatralIdFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setCmsCollatralId(long value) {
		setCmsCollatralIdFK(new Long(value));
	}
	
	public abstract Long getCmsCollatralIdFK();
	
	public abstract void setCmsCollatralIdFK(Long value);
	/**
	 * Get Legal ID
	 * 
	 * @return long
	 */
	

	/**
	 * Set the contact ID
	 * 
	 * @param value is of type long
	 */
	public void setGeneralChargeDetailsID(long value) {
		setGeneralChargeDetailsPK(new Long(value));
	}

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type long
	 */
	
	// ************** Abstract methods ************
	/**
	 * Get the contact PK
	 * 
	 * @return Long
	 */
	public abstract Long getGeneralChargeDetailsPK();

	/**
	 * Get Legal FK
	 * 
	 * @return Long
	 */
	

	/**
	 * Set the contact PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setGeneralChargeDetailsPK(Long value);

	/**
	 * Set Legal FK
	 * 
	 * @param value is of type Long
	 */


	// *****************************************************
	/**
	 * Create a Contact Information
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IContact object
	 * @return Long the contact ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(IGeneralChargeDetails value) throws CreateException {
		if (null == value) {
			throw new CreateException("IGeneralChargeDetails is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
			/*
			 * long pk =com.integrosys.cms.app.common.constant.ICMSConstant.
			 * LONG_INVALID_VALUE; if(value.getContactID() ==
			 * com.integrosys.cms.
			 * app.common.constant.ICMSConstant.LONG_INVALID_VALUE) { pk =
			 * Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME,
			 * true)); } else { pk = value.getContactID(); }
			 */
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			// setLEID(legalID); //to be set by cmr
			setGeneralChargeDetailsID(pk);

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			_context.setRollbackOnly();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Post-Create a record
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IContact object
	 */
	public void ejbPostCreate(IGeneralChargeDetails generalChargeDetails) {
			setReferences(generalChargeDetails, true);
	}
	
	
	/**
	 * Set the references to this general charge asset.
	 * 
	 * @param generalChargeDetails of type ICollateral
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(IGeneralChargeDetails generalChargeDetails, boolean isAdd) {
		IGeneralChargeDetails genChargeDetails = (IGeneralChargeDetails) generalChargeDetails;
		try {
				updateGeneralChargeStockDetails(genChargeDetails.getGeneralChargeStockDetails(), genChargeDetails.getGeneralChargeDetailsID(),isAdd);
				updateLeadBankStock(genChargeDetails.getLeadBankStock(), genChargeDetails.getGeneralChargeDetailsID(),isAdd);
			}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}


	/**
	 * Return the OB representation of this object
	 * 
	 * @return IContact
	 */
	public IGeneralChargeDetails getValue() {
		IGeneralChargeDetails value = new OBGeneralChargeDetails();
		AccessorUtil.copyValue(this, value);
		value.setLeadBankStock(getLeadBankStock());
		return value;
	}

	/**
	 * Persist a contact information
	 * 
	 * @param generalChargeDetails is of type IGeneralChargeDetails
	 */
	public void setValue(IGeneralChargeDetails generalChargeDetails) {
		AccessorUtil.copyValue(generalChargeDetails, this, EXCLUDE_METHOD);
		setReferences(generalChargeDetails, false);
	}
	
	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() {
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}


	public abstract Date getDueDate() ;
	public abstract void setDueDate(Date dueDate);

	public abstract String getDocCode() ;
	public abstract void setDocCode(String docCode);

	public abstract String getStatus();
	public abstract void setStatus(String status);
	
	public abstract  String getFundedShare() ;
	public abstract  void setFundedShare(String fundedShare) ;
	
	public abstract  String getCalculatedDP() ;
	public abstract  void setCalculatedDP(String calculatedDP) ;
	
	public abstract   String getLastUpdatedBy() ;
	public abstract   void setLastUpdatedBy(String lastUpdatedBy) ;
	
	public abstract   Date getLastUpdatedOn();
	public abstract   void setLastUpdatedOn(Date lastUpdatedOn) ;
	
	public abstract   String getLastApprovedBy() ;
	public abstract   void setLastApprovedBy(String lastApprovedBy);
	
	public abstract   Date getLastApprovedOn() ;
	public abstract   void setLastApprovedOn(Date lastApprovedOn) ;
	
	//Uma Khot:Cam upload and Dp field calculation CR
	public abstract  String getDpShare() ;
	public abstract  void setDpShare(String dpShare) ;

	//Start Santosh
	public abstract String getDpCalculateManually();
	public abstract void setDpCalculateManually(String dpCalculateManually);
	//End Santosh
	
	public abstract String getStockdocMonth();
	public abstract void setStockdocMonth(String stockdocMonth);
	
	public abstract String getStockdocYear();
	public abstract void setStockdocYear(String stockdocYear);
	
	public abstract BigDecimal getDpForCashFlowOrBudget();
	public abstract void setDpForCashFlowOrBudget(BigDecimal dpForCashFlowOrBudget);
	
	public abstract BigDecimal getActualDp();
	public abstract void setActualDp(BigDecimal actualDp);
	
	public abstract BigDecimal getCoverageAmount();
	public abstract void setCoverageAmount(BigDecimal coverageAmount);
	
	public abstract BigDecimal getAdHocCoverageAmount();
	public abstract void setAdHocCoverageAmount(BigDecimal adHocCoverageAmount);
	
	public abstract Double getCoveragePercentage();
	public abstract void setCoveragePercentage(Double coveragePercentage);
	
	public abstract  String getRemarkByMaker() ;
	public abstract  void setRemarkByMaker(String remarkByMaker) ;

	public abstract  String getTotalLoanable() ;
	public abstract  void setTotalLoanable(String totalLoanable) ;
	
	public abstract  String getMigrationFlag_DP_CR() ;
	public abstract  void setMigrationFlag_DP_CR(String migrationFlag_DP_CR) ;
	
	
	public BigDecimal getTotalLoanableAmount() {
		return null;
	}
	public void setTotalLoanableAmount(BigDecimal totalLoanableAmount) {
		
	}
	
	public String getLocation() {
		return null;
	}

	public void setLocation(String location) {
	}

	public String getLocationDetail() {
		return null;
	}

	public void setLocationDetail(String locationDetail) {
	}
	
	//Added by Anil will be for one to many relationship of GeneralChargeDetails 
		public IGeneralChargeStockDetails[] getGeneralChargeStockDetails() {
			return retrieveGeneralChargeStockDetails();
		}
		public void setGeneralChargeStockDetails(IGeneralChargeStockDetails[] generalChargeStockDetails) {
			//do noting
		}
		
		public abstract Collection getCMRGeneralChargeStockDetails();
		public abstract void setCMRGeneralChargeStockDetails(Collection value);

		private IGeneralChargeStockDetails[] retrieveGeneralChargeStockDetails() throws GeneralChargeException {
			try {
				Collection c = getCMRGeneralChargeStockDetails();
				if ((null == c) || (c.size() == 0)) {
					return null;
				}
				else {
					ArrayList aList = new ArrayList();
					Iterator i = c.iterator();
					while (i.hasNext()) {
						EBGeneralChargeStockDetailsLocal local = (EBGeneralChargeStockDetailsLocal) i.next();
						IGeneralChargeStockDetails ob = local.getValue();
						aList.add(ob);
					}

					return (IGeneralChargeStockDetails[]) aList.toArray(new IGeneralChargeStockDetails[0]);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				if (e instanceof GeneralChargeException) {
					throw (GeneralChargeException) e;
				}
				else {
					throw new GeneralChargeException("Caught Exception: " + e.toString());
				}
			}
		}
		
		private void updateGeneralChargeStockDetails(IGeneralChargeStockDetails[] chargeDetails,long generalChargeDetailsID, boolean isAdd) throws GeneralChargeException {
			try {
				Collection c = getCMRGeneralChargeStockDetails();

				if (null == chargeDetails) {
					if ((null == c) || (c.size() == 0)) {
						return; // nothing to do
					}
					else {
						// delete all records
						deleteGeneralChargeStockDetails(new ArrayList(c));
					}
				}
				else if (isAdd ||(null == c) || (c.size() == 0)) {
					// create new records
					createGeneralChargeStockDetails(Arrays.asList(chargeDetails),generalChargeDetailsID);
				}
				else {
					Iterator i = c.iterator();
					ArrayList createList = new ArrayList(); // contains list of OBs
					ArrayList deleteList = new ArrayList(); // contains list of
															// local interfaces

					// identify identify records for delete or udpate first
					while (i.hasNext()) {
						EBGeneralChargeStockDetailsLocal local = (EBGeneralChargeStockDetailsLocal) i.next();

						long generalChargeStockDetailsID = local.getGeneralChargeStockDetailsID();
						boolean update = false;

						for (int j = 0; j < chargeDetails.length; j++) {
							IGeneralChargeStockDetails newOB = chargeDetails[j];

							if (newOB.getGeneralChargeStockDetailsID() == generalChargeStockDetailsID) {
								// perform update
								local.setValue(newOB);
								update = true;
								break;
							}
						}
						if (!update) {
							// add for delete
							deleteList.add(local);
						}
					}
					// next identify records for add
					for (int j = 0; j < chargeDetails.length; j++) {
						i = c.iterator();
						IGeneralChargeStockDetails newOB = chargeDetails[j];
						boolean found = false;

						while (i.hasNext()) {
							EBGeneralChargeStockDetailsLocal local = (EBGeneralChargeStockDetailsLocal) i.next();
							long id = local.getGeneralChargeStockDetailsID();

							if (newOB.getGeneralChargeStockDetailsID() == id) {
								found = true;
								break;
							}
						}
						if (!found) {
							// add for adding
							createList.add(newOB);
						}
					}
					deleteGeneralChargeStockDetails(deleteList);
					createGeneralChargeStockDetails(createList,generalChargeDetailsID);
				}
			}
			catch (GeneralChargeException e) {
				throw e;
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new GeneralChargeException("Caught Exception: " + e.toString());
			}
		}
		private void deleteGeneralChargeStockDetails(List deleteList) throws GeneralChargeException {
			if ((null == deleteList) || (deleteList.size() == 0)) {
				return; // do nothing
			}
			try {
				Collection c = getCMRGeneralChargeStockDetails();
				Iterator i = deleteList.iterator();
				while (i.hasNext()) {
					EBGeneralChargeStockDetailsLocal local = (EBGeneralChargeStockDetailsLocal) i.next();
					c.remove(local);
					local.remove();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				if (e instanceof GeneralChargeException) {
					throw (GeneralChargeException) e;
				}
				else {
					throw new GeneralChargeException("Caught Exception: " + e.toString());
				}
			}
		}
		
		private void createGeneralChargeStockDetails(List createList,long generalChargeDetailsID) throws GeneralChargeException {
			if ((null == createList) || (createList.size() == 0)) {
				return; // do nothing
			}
			Collection c = getCMRGeneralChargeStockDetails();
			Iterator i = createList.iterator();
			try {
				EBGeneralChargeStockDetailsLocalHome home = getEBLocalHomeGeneralChargeStockDetails();
				while (i.hasNext()) {
					IGeneralChargeStockDetails ob = (IGeneralChargeStockDetails) i.next();
					if(ob!=null){
					DefaultLogger.debug(this, "Creating GeneralChargeStockDetails ID: " + ob.getGeneralChargeStockDetailsID());
					String serverType = (new BatchResourceFactory()).getAppServerType();
					DefaultLogger.debug(this, "=======Application server Type is(banking method) ======= : " + serverType);
					if(serverType.equals(ICMSConstant.WEBSPHERE))
					{
						ob.setGeneralChargeDetailsID(generalChargeDetailsID);
					}
					EBGeneralChargeStockDetailsLocal local = home.create(ob);
					c.add(local);
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				if (e instanceof GeneralChargeException) {
					throw (GeneralChargeException) e;
				}
				else {
					throw new GeneralChargeException("Caught Exception: " + e.toString());
				}
			}
		}
		
		//TODO: need to change EB_GENERALCHARGESTOCKDETAILS_LOCAL_STAGING_JNDI to EB_GENERALCHARGESTOCKDETAILS_LOCAL_JNDI
		protected EBGeneralChargeStockDetailsLocalHome getEBLocalHomeGeneralChargeStockDetails() throws GeneralChargeException {
			EBGeneralChargeStockDetailsLocalHome home = (EBGeneralChargeStockDetailsLocalHome) BeanController.getEJBLocalHome(
					ICMSJNDIConstant.EB_GENERALCHARGESTOCKDETAILS_LOCAL_JNDI, EBGeneralChargeStockDetailsLocalHome.class.getName());

			if (null != home) {
				return home;
			}
			else {
				throw new GeneralChargeException("EBGeneralChargeStockDetailsLocalHome is null!");
			}
		}
	
	public abstract Collection getLeadBankStockCMR();
	
	public abstract void setLeadBankStockCMR(Collection stock);
	
	public List<ILeadBankStock> getLeadBankStock() {
		Collection<?> cmr = getLeadBankStockCMR();
		if (CollectionUtils.isEmpty(cmr))
			return null;
		List<ILeadBankStock> list = new ArrayList<ILeadBankStock>();
		
		try {
			Iterator<?> iter = cmr.iterator();
			while (iter.hasNext()) {
				EBLeadBankStockLocal local = (EBLeadBankStockLocal) iter.next();
				ILeadBankStock stockAndDate = local.getValue();
				list.add(stockAndDate);
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception retrieving Stock & Due Date data", e);
			//TODO throw Exception
		}
		return list;
	}
	
	public void setLeadBankStock(List<ILeadBankStock> leadNodaBankStock) {
		
	}
	
	protected EBLeadBankStockLocalHome getEBLeadBankStockLocalHome() throws GeneralChargeException {
		EBLeadBankStockLocalHome home = (EBLeadBankStockLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LEAD_BANK_STOCK_JNDI, EBLeadBankStockLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new GeneralChargeException("EBLeadBankStockLocalHome is null!");
		}
	}
	
	private void updateLeadBankStock(List<ILeadBankStock> leadBankStockList, long generalChargeDetailsID, boolean isAdd) throws GeneralChargeException {
		try {
			Collection cmr = getLeadBankStockCMR();

			if (leadBankStockList == null) {
				if (CollectionUtils.isEmpty(cmr)) {
					return;
				} else {
					deleteLeadBankStock(new ArrayList(cmr));
				}
			} else if (isAdd || CollectionUtils.isEmpty(cmr)) {
				createLeadBankStock(leadBankStockList, generalChargeDetailsID);
			} else {
				Iterator iterator = cmr.iterator();
				List<ILeadBankStock> createList = new ArrayList<ILeadBankStock>();
				List<EBLeadBankStockLocal> deleteList = new ArrayList<EBLeadBankStockLocal>();

				while (iterator.hasNext()) {
					EBLeadBankStockLocal local = (EBLeadBankStockLocal) iterator.next();
					boolean update = false;

					for (ILeadBankStock leadBankStock : leadBankStockList) {
						if (leadBankStock.getId() == local.getLeadBankStockId()) {
							local.setValue(leadBankStock);
							update = true;
							break;
						}
					}
					if (!update) {
						deleteList.add(local);
					}
				}

				for (ILeadBankStock leadBankStock : leadBankStockList) {
					iterator = cmr.iterator();
					boolean found = false;

					while (iterator.hasNext()) {
						EBLeadBankStockLocal local = (EBLeadBankStockLocal) iterator.next();
						if (leadBankStock.getId() == local.getLeadBankStockId()) {
							found = true;
							break;
						}
					}
					if (!found) {
						createList.add(leadBankStock);
					}
				}
				deleteLeadBankStock(deleteList);
				createLeadBankStock(createList, generalChargeDetailsID);
			}
		} catch (GeneralChargeException e) {
			DefaultLogger.error(this, "Exception While updating Lead Bank Stock Details", e);
			throw e;
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception While updating Lead Bank Stock Details", e);
			throw new GeneralChargeException("Caught Exception: " + e.toString());
		}
	}
	
	private void deleteLeadBankStock(List<EBLeadBankStockLocal> leadBankStockList) {
		if (CollectionUtils.isEmpty(leadBankStockList)) {
			return;
		}

		Collection cmr = getLeadBankStockCMR();
		Iterator<EBLeadBankStockLocal> iterator = leadBankStockList.iterator();
		while (iterator.hasNext()) {
			EBLeadBankStockLocal local = iterator.next();
			cmr.remove(local);
			try {
				local.remove();
			} catch (EJBException e) {
				DefaultLogger.error(this, "Exception while deleting Lead Bank Stock item(s)", e);
				throw new GeneralChargeException(e.getMessage(), e);
			} catch (RemoveException e) {
				DefaultLogger.error(this, "Exception while deleting Lead Bank Stock item(s)", e);
				throw new GeneralChargeException(e.getMessage(), e);
			}
		}

	}
	
	private void createLeadBankStock(List<ILeadBankStock> leadBankStockList, long generalChargeDetailsID) {
		if (CollectionUtils.isEmpty(leadBankStockList)) {
			return;
		}
		Collection cmr = getLeadBankStockCMR();
		Iterator<ILeadBankStock> iterator = leadBankStockList.iterator();
		EBLeadBankStockLocalHome home = getEBLeadBankStockLocalHome();
		while (iterator.hasNext()) {
			ILeadBankStock leadBankStock = iterator.next();
			if (leadBankStock != null) {
				String serverType = (new BatchResourceFactory()).getAppServerType();
				DefaultLogger.debug(this, "Creating LeadBankStock ID: " + leadBankStock.getId()
						+ ", Application Server Type : " + serverType);
				
				if (serverType.equals(ICMSConstant.WEBSPHERE)) {
					leadBankStock.setGeneralChargeDetailId(generalChargeDetailsID);
				}
				
				EBLeadBankStockLocal local;
				try {
					local = home.create(leadBankStock);
				} catch (CreateException e) {
					DefaultLogger.error(this, "Exception while creating Lead Bank Stock item(s)", e);
					throw new GeneralChargeException(e.getMessage(), e);
				}
				cmr.add(local);
			}
		}

	}
	
}
	