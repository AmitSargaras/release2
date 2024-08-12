/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBPreDealBusManagerBean
 *
 * Created on 2:47:17 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import java.rmi.RemoteException;
import java.util.Calendar;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.IPolicyCapProxyManager;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.PolicyCapProxyManagerFactory;
import com.integrosys.cms.app.predeal.PreDealConstants;
import com.integrosys.cms.app.predeal.PreDealException;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 2:47:17 PM
 */
public class SBPreDealBusManagerBean implements SessionBean {

	private SessionContext context;

	public SBPreDealBusManagerBean() {

	}

	public void ejbActivate() throws EJBException, RemoteException {

	}

	public void ejbPassivate() throws EJBException, RemoteException {

	}

	public void ejbRemove() throws EJBException, RemoteException {

	}

	public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
		this.context = sessionContext;
	}

	public void ejbCreate() throws CreateException {

	}

	public void ejbPostCreate() {

	}

	public IPreDeal createNewEarMark(IPreDeal data) throws PreDealException {
		EBEarMarkHome home = getEBEarMarkHome();
		EBEarMarkGroupHome groupHome = getEBEarMarkGroupHome();

		OBEarMark ob = new OBEarMark();
		OBPreDeal predeal = new OBPreDeal();
		OBEarMarkGroup groupOb = null;
		boolean earMarkGroupRecordExist = false;

		String sourceUpdate;

		data.setHoldingInd(false); // for sure and must be false upon creation
		data.setEarMarkStatus(PreDealConstants.EARMARK_STATUS_EARMARKED);

		AccessorUtil.copyValue(data, ob);

		try {

			if (data.getSourceSystem() == null) {
				throw new PreDealException("SOURCE SYSTEM IS NULL");
			}

			sourceUpdate = data.getSourceSystem();

			// MayBank code
			// else if ( PreDealConstants.SOURCE_SYSTEM_WAA.equalsIgnoreCase (
			// data.getSourceSystem () ) ||
			// PreDealConstants.SOURCE_SYSTEM_MARSHA.equalsIgnoreCase (
			// data.getSourceSystem () ) )
			// {
			// sourceUpdate = PreDealConstants.SOURCE_SYSTEM_MARSHA ;
			// }
			// else if ( PreDealConstants.SOURCE_SYSTEM_MEAA.equalsIgnoreCase (
			// data.getSourceSystem () ) ||
			// PreDealConstants.SOURCE_SYSTEM_WOLOC.equalsIgnoreCase (
			// data.getSourceSystem () ) ||
			// PreDealConstants.SOURCE_SYSTEM_NOMINEES.equalsIgnoreCase (
			// data.getSourceSystem () ) )
			// {
			// sourceUpdate = PreDealConstants.SOURCE_SYSTEM_NOMINEES ;
			// }
			// else
			// {
			// throw new PreDealException ( "UNKNOWN SOURCE SYSTEM : " +
			// data.getSourceSystem () ) ;
			// }

			DefaultLogger.debug(this, "sourceUpdate : " + sourceUpdate);
			DefaultLogger.debug(this, "data.getFeedId () : " + data.getFeedId());

			try {
				EBEarMarkGroup groupMark = groupHome.findBySourceAndFeedId(sourceUpdate, new Long(data.getFeedId()));

				groupOb = groupMark.getOBEarMarkGroup();
				earMarkGroupRecordExist = true;
			}
			catch (FinderException fex) {
				DefaultLogger.debug(this, "Earmark group record may not exist , creating new obearmark group ");

				groupOb = new OBEarMarkGroup();
				earMarkGroupRecordExist = false;

				groupOb.setEarMarkGroupId(new Long(ICMSConstant.LONG_INVALID_VALUE));
			}

			PreDealSearchRecord record = new PreDealDao().searchByFeedId(String.valueOf(data.getFeedId()));

			long temp = groupOb.getEarMarkCurrent() + data.getEarMarkUnits();

			// long totalUnits = record.getTotalUnits () ;
			// long cmsActual = record.getCmsActualHolding () ;
			// long earMarkolding = record.getEarmarkHolding () ;
			// long earMarkCurrent = record.getEarmarkCurrent () ;
			// long earMarkNew = ob.getEarMarkUnits () ;
			// long listedShare = record.getListedShareQuantity () ;

			long totalUnits = groupOb.getTotalOfUnits();
			long cmsActual = groupOb.getCmsActualHolding();
			long earMarkolding = groupOb.getEarMarkHolding();
			long earMarkCurrent = groupOb.getEarMarkCurrent();
			long earMarkNew = ob.getEarMarkUnits();
			long listedShare = record.getListedShareQuantity();

			double newConcentration = (totalUnits + cmsActual + earMarkolding + earMarkCurrent + earMarkNew) * 100
					/ (double) listedShare;

			double firstLimit = 0;
			double secondLimit = 0;

			IPolicyCapProxyManager proxy2 = PolicyCapProxyManagerFactory.getPolicyCapProxyManager();
			IPolicyCapGroup policyCapGroup = proxy2.getPolicyCapGroupByExchangeBank(record.getStockExchangeCode(),
					sourceUpdate);

			IPolicyCap[] policyCapList = policyCapGroup.getPolicyCapArray();
			String broadType = record.getBoardType();

			// Getting the first and second Limit
			for (int loop = 0; loop < policyCapList.length; loop++) {

				if ((broadType != null) && (policyCapList[loop].getBoard() != null)
						&& broadType.trim().equalsIgnoreCase(policyCapList[loop].getBoard().trim())) {

					if (record.getIsFi()) {
						firstLimit = policyCapList[loop].getQuotaCollateralCapFI();
						secondLimit = policyCapList[loop].getMaxCollateralCapFI();
						break;
					}
					else {
						firstLimit = policyCapList[loop].getQuotaCollateralCapNonFI();
						secondLimit = policyCapList[loop].getMaxCollateralCapNonFI();
						break;
					}
				}
			}

			// Indentify if the quota is breached and specify the breach date
			if ((newConcentration > firstLimit) && (newConcentration <= secondLimit)) {

				// more than quota but lesser than maximum collateral cap
				groupOb.setBreachInd(true);
				groupOb.setDateQuotaBreach(Calendar.getInstance().getTime());

				if (groupOb.getLastDateQuotaBreach() == null) {
					groupOb.setLastDateQuotaBreach(groupOb.getDateQuotaBreach());
				}
			}
			else if (newConcentration > secondLimit) {

				// more than quota
				groupOb.setBreachInd(true);
				groupOb.setDateMaxCapBreach(Calendar.getInstance().getTime());
				ob.setDateMaxCapBreach(groupOb.getDateMaxCapBreach());

				if (groupOb.getLastDateMaxCapBreach() == null) {
					groupOb.setLastDateMaxCapBreach(groupOb.getDateMaxCapBreach());
				}

				// which means the user breaches both date togather !
				if (groupOb.getDateQuotaBreach() == null) {
					groupOb.setDateQuotaBreach(Calendar.getInstance().getTime());

					if (groupOb.getLastDateQuotaBreach() == null) {
						groupOb.setLastDateQuotaBreach(groupOb.getDateQuotaBreach());
					}
				}
			}
			else { // no longer breaches any limit
				groupOb.setDateQuotaBreach(null);
				groupOb.setDateMaxCapBreach(null);
			}

			groupOb.setEarMarkCurrent(temp);
			groupOb.setStatus(ICMSConstant.STATE_ACTIVE);
			ob.setStatus(true);

			if (!isStagingOp()) {// if this bean is use to create the staging
									// data , skip it
				if (earMarkGroupRecordExist) {
					EBEarMarkGroup groupMark = groupHome
							.findBySourceAndFeedId(sourceUpdate, new Long(data.getFeedId()));
					groupMark.updateEBEarMarkGroup(groupOb);
					groupOb = groupMark.getOBEarMarkGroup();
				}
				else {
					groupOb.setSourceSystemId(sourceUpdate);
					groupOb.setFeedId(record.getFeedId());
					EBEarMarkGroup groupMark = groupHome.create(groupOb);
					groupOb = groupMark.getOBEarMarkGroup();
				}
			}

			ob.setEarMarkGroupId(groupOb.getEarMarkGroupId().longValue());
			DefaultLogger.debug(this, "Earmark data before create : " + AccessorUtil.printMethodValue(ob));
			ob = home.create(ob).getOBEarMark();
			AccessorUtil.copyValue(ob, predeal);

			return predeal;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			rollback();

			throw new PreDealException(e);
		}
	}

	public IPreDeal updateEarMark(IPreDeal data) throws PreDealException {
		EBEarMarkHome home = getEBEarMarkHome();
		EBEarMarkGroupHome groupHome = getEBEarMarkGroupHome();

		String sourceUpdate;

		OBEarMark ob = new OBEarMark();
		OBPreDeal predeal = new OBPreDeal();

		AccessorUtil.copyValue(data, ob);

		try {

			if (data.getSourceSystem() == null) {
				throw new PreDealException("SOURCE SYSTEM IS NULL");
			}
			// maybank code
			// else if ( PreDealConstants.SOURCE_SYSTEM_WAA.equalsIgnoreCase (
			// data.getSourceSystem () ) ||
			// PreDealConstants.SOURCE_SYSTEM_MARSHA.equalsIgnoreCase (
			// data.getSourceSystem () ) )
			// {
			// sourceUpdate = PreDealConstants.SOURCE_SYSTEM_MARSHA ;
			// }
			// else if ( PreDealConstants.SOURCE_SYSTEM_MEAA.equalsIgnoreCase (
			// data.getSourceSystem () ) ||
			// PreDealConstants.SOURCE_SYSTEM_WOLOC.equalsIgnoreCase (
			// data.getSourceSystem () ) ||
			// PreDealConstants.SOURCE_SYSTEM_NOMINEES.equalsIgnoreCase (
			// data.getSourceSystem () ) )
			// {
			// sourceUpdate = PreDealConstants.SOURCE_SYSTEM_NOMINEES ;
			// }
			// else
			// {
			// throw new PreDealException ( "UNKNOWN SOURCE SYSTEM : " +
			// data.getSourceSystem () ) ;
			// }

			sourceUpdate = data.getSourceSystem();

			EBEarMarkGroup groupMark = groupHome.findBySourceAndFeedId(sourceUpdate, new Long(data.getFeedId()));
			EBEarMark mark = home.findByPrimaryKey(data.getEarMarkId());
			OBEarMarkGroup groupOb = groupMark.getOBEarMarkGroup();

			if (PreDealConstants.EARMARK_STATUS_HOLDING.equals(data.getEarMarkStatus())) {
				ob.setHoldingInd(true);

				// transfer from current to holding
				groupOb.setEarMarkHolding(groupOb.getEarMarkHolding() + ob.getEarMarkUnits());
				groupOb.setEarMarkCurrent(groupOb.getEarMarkCurrent() - ob.getEarMarkUnits());

			}
			else if (PreDealConstants.EARMARK_STATUS_RELEASED.equals(data.getEarMarkStatus())) {
				ob.setHoldingInd(false);

				if ("cancel".equalsIgnoreCase(data.getReleaseStatus())) // actually
																		// a
																		// delete
																		// operation
																		// on
																		// earmark
																		// in
																		// the
																		// holding
																		// area
																		// by
																		// the
																		// SAC
																		// team
				{
					if (!isStagingOp()) // don't set the delete status yet until
										// the actual update , since it's under
										// release operation
					{
						ob.setEarMarkStatus(PreDealConstants.EARMARK_STATUS_DELETED);
						ob.setStatus(false);
					}

					groupOb.setEarMarkHolding(groupOb.getEarMarkHolding() - ob.getEarMarkUnits());
				}
				else {
					// transfer from holding to actual holding
					groupOb.setCmsActualHolding(groupOb.getCmsActualHolding() + ob.getEarMarkUnits());
					groupOb.setEarMarkHolding(groupOb.getEarMarkHolding() - ob.getEarMarkUnits());
				}

			}
			else if (PreDealConstants.EARMARK_STATUS_DELETED.equals(data.getEarMarkStatus())) {
				ob.setHoldingInd(false);

				// minus out what ear mark units that have been deleted
				groupOb.setEarMarkCurrent(groupOb.getEarMarkCurrent() - ob.getEarMarkUnits());
			}
			else if (PreDealConstants.EARMARK_STATUS_EARMARKED.equals(data.getEarMarkStatus()) && isStagingOp()) {
				// do nothing and it's impossible to get here
			}
			else {
				throw new PreDealException("Update operation cannot be processed with ear status : "
						+ data.getEarMarkStatus());
			}

			// DefaultLogger.debug ( this , "data before update : " +
			// AccessorUtil.printMethodValue ( ob ) ) ;
			//
			// DefaultLogger.debug ( this , "data before update : " +
			// AccessorUtil.printMethodValue ( groupOb ) ) ;
			//
			// DefaultLogger.debug ( this , "\"" + ob.getReleaseStatus () + "\""
			// ) ;

			mark.updateEBEarMark(ob);

			if (!isStagingOp()) // if this bean is use to create the staging
								// data , skip it
			{
				groupMark.updateEBEarMarkGroup(groupOb);
			}

			// DefaultLogger.debug ( this , "data after update : " +
			// AccessorUtil.printMethodValue ( ob ) ) ;
			//
			// DefaultLogger.debug ( this , "data after update : " +
			// AccessorUtil.printMethodValue ( groupOb ) ) ;

			ob = mark.getOBEarMark();

			AccessorUtil.copyValue(ob, predeal);

			return predeal;
		}
		catch (Exception e) {
			rollback();

			throw new PreDealException(e);
		}
	}

	public IPreDeal getByEarMarkId(String earMarkId) throws PreDealException {
		EBEarMarkHome home = getEBEarMarkHome();
		OBPreDeal predeal = new OBPreDeal();

		try {
			EBEarMark mark = home.findByPrimaryKey(new Long(earMarkId));
			AccessorUtil.copyValue(mark.getOBEarMark(), predeal);

			return predeal;
		}
		catch (FinderException fex) {
			throw new PreDealException(fex);
		}
		catch (Exception e) {
			rollback();

			throw new PreDealException(e);
		}
	}

	public IEarMarkGroup getEarMarkGroupBySourceAndFeedId(String sourceSystemId, long feedId) throws PreDealException {
		try {
			EBEarMarkGroup eag = this.getEBEarMarkGroupHome().findBySourceAndFeedId(sourceSystemId, new Long(feedId));
			return eag.getOBEarMarkGroup();

		}
		catch (FinderException fex) {

			throw new PreDealException(fex);
		}
		catch (Exception e) {

			throw new PreDealException(e);
		}
	}

	protected EBEarMarkHome getEBEarMarkHome() {
		EBEarMarkHome ejbHome = (EBEarMarkHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_EAR_MARK_JNDI,
				EBEarMarkHome.class.getName());

		if (ejbHome == null) {
			DefaultLogger.debug(this, "EJB home is null");
		}

		return ejbHome;
	}

	protected EBEarMarkGroupHome getEBEarMarkGroupHome() {
		EBEarMarkGroupHome ejbHome = (EBEarMarkGroupHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_EAR_MARK_GROUP_JNDI, EBEarMarkGroupHome.class.getName());

		if (ejbHome == null) {
			DefaultLogger.debug(this, "EJB home is null");
		}

		return ejbHome;
	}

	protected boolean isStagingOp() {
		return false;
	}

	private void rollback() {
		if (this.context != null) {
			try {
				this.context.setRollbackOnly();
			}
			catch (Throwable t) {
				// do nothing
			}
		}
	}

}
