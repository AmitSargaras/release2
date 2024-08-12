/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralStagingBean.java,v 1.12 2006/07/27 04:34:08 jzhan Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging Collateral.
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/07/27 04:34:08 $ Tag: $Name: $
 */
public abstract class EBCollateralStagingBean extends EBCollateralBean {

	private static final long serialVersionUID = -4302963592776187093L;

	/**
	 * Get valuation local home object.
	 * 
	 * @return EBValuationLocalHome
	 */
	protected EBValuationLocalHome getEBValuationLocalHome() {
		EBValuationLocalHome ejbHome = (EBValuationLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_VALUATION_STAGING_LOCAL_JNDI, EBValuationLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBValuationLocalHome is Null!");
		}
		return ejbHome;
	}

	/**
	 * Get insurance policy local home object.
	 * 
	 * @return EBInsurancePolicyLocalHome
	 */
	protected EBInsurancePolicyLocalHome getEBInsurancePolicyLocalHome() {
		EBInsurancePolicyLocalHome ejbHome = (EBInsurancePolicyLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_INSURANCE_POLICY_STAGING_LOCAL_JNDI, EBInsurancePolicyLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("Staging EBInsurancePolicyLocalHome is Null!");
		}
		return ejbHome;
	}
	
	protected EBSecurityCoverageLocalHome getEBSecurityCoverageLocalHome() {
		EBSecurityCoverageLocalHome ejbHome = (EBSecurityCoverageLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SECURITY_COVERAGE_STAGING_LOCAL_JNDI, EBSecurityCoverageLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBSecurityCoverageStagingLocalHome is Null!");
		}
		return ejbHome;
	}
	
	protected EBAddtionalDocumentFacilityDetailsLocalHome getEBAddtionalDocumentFacilityDetailsLocalHome() {
		EBAddtionalDocumentFacilityDetailsLocalHome ejbHome = (EBAddtionalDocumentFacilityDetailsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_ADDTIONAL_DOCUMENT_FACILITY_DETAILS_STAGING_LOCAL_JNDI, EBAddtionalDocumentFacilityDetailsLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("Staging EBAddtionalDocumentFacilityDetailsLocalHome is Null!");
		}
		return ejbHome;
	}

	/**
	 * Get collateral pledgor local home.
	 * 
	 * @return EBCollateralPledgorLocalHome
	 */
	protected EBCollateralPledgorLocalHome getEBCollateralPledgorLocalHome() {
		EBCollateralPledgorLocalHome ejbHome = (EBCollateralPledgorLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_PLEDGOR_STAGING_LOCAL_JNDI, EBCollateralPledgorLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCollateralPledgorLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get collateral limit charge local home.
	 * 
	 * @return EBLimitChargeLocalHome
	 */
	protected EBLimitChargeLocalHome getEBLimitChargeLocalHome() {
		EBLimitChargeLocalHome ejbHome = (EBLimitChargeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_LIMITCHARGE_STAGING_LOCAL_JNDI, EBLimitChargeLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBLimitChargeLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get collateral limit map local home.
	 * 
	 * @return EBCollateralLimitMapLocalHome
	 */
	protected EBCollateralLimitMapLocalHome getEBCollateralLimitMapLocalHome() {
		EBCollateralLimitMapLocalHome ejbHome = (EBCollateralLimitMapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_LIMIT_MAP_STAGING_LOCAL_JNDI, EBCollateralLimitMapLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCollateralLimitMapLocalHome for staging is Null!");
		}

		return ejbHome;
	}

	protected EBSecApportionmentLocalHome getEBSecApportionmentLocalHome() {
		EBSecApportionmentLocalHome ejbHome = (EBSecApportionmentLocalHome) (BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_APPORTIONMENT_STAGING_LOCAL_JNDI, EBSecApportionmentLocalHome.class.getName()));
		if (ejbHome == null) {
			throw new EJBException("EBSecApportionmentLocalHome is Null!");
		}
		return ejbHome;
	}

	/**
	 * Set the references to this collateral.
	 * 
	 * @param collateral of type ICollateral
	 * @param isAdd true is to create new references, otherwise false
	 */
	protected void setReferences(ICollateral collateral, boolean isAdd, boolean createPledgor) {
		if (createPledgor) {
			try {
				setPledgorsRef(collateral.getPledgors(), isAdd);
			}
			catch (CreateException e) {
				throw new EJBException(
						"failed to create/update collateral pledgor map or pledgor, staging collateral id ["
								+ collateral.getCollateralID() + "]", e);
			}
		}
		// do not insert record in limit sec mapping staging table because
		// it will affect Manual input security
		// From manual input, col ID in limit sec mapping staging table is
		// actual col ID
		// revert back.
		try {
			setCollateralLimitsRef(collateral.getCollateralLimits(), isAdd);
		}
		catch (CreateException e) {
			throw new EJBException("failed to create/update collateral limit map, staging collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}

		// [28th Nov 2008]cz: getValuation() = store the latest valuation
		// record only;
		// for info purpose only - do NOT persists
		// OBValuation obVal = (OBValuation) collateral.getValuation();
		// if ((obVal != null) && !obVal.isEmpty()) {
		// setValuationRef(collateral.getValuation());
		// }

		// valuation in CMS
		try {
			setValuationRef(collateral.getValuationIntoCMS());
		}
		catch (CreateException e) {
			throw new EJBException("failed to create/update valuation into CMS, staging collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}

		if ((collateral.getSourceValuation() != null) && (collateral.getSourceValuation().getCMV() != null)
				&& ((collateral.getSourceValuation().getValuationID() < 0) // new
				// valuation
				// object
				// new staging collateral created, therefore a corresponding
				// valuation object needs to be created (even if there is no
				// change in valuation)
				|| (collateral.getSourceValuation().getCollateralID() != collateral.getCollateralID()))) {
			try {
				setValuationRef(collateral.getSourceValuation());
			}
			catch (CreateException e) {
				throw new EJBException("failed to create/update source valuation, staging collateral id ["
						+ collateral.getCollateralID() + "]", e);
			}
		}

		populateChargeIdFromLimitMapToChargeMap(collateral);

		// limit charge map and charge detail
		try {
			setLimitChargesRef(collateral.getLimitCharges(), isAdd);
		}
		catch (CreateException e) {
			throw new EJBException("failed to create/update limit charge map or charge detail, staging collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}
		catch (RemoveException e) {
			throw new EJBException("failed to remove limit charge map or charge detail, staging collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}

		// insurance policy
		try {
			setInsurancePolicyRef(collateral.getInsurancePolicies(), isAdd);
		}
		catch (CreateException e) {
			throw new EJBException("failed to create/update insurance policys, staging collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}
		
		try {
			updateSecurityCoverage(collateral.getSecurityCoverage(), collateral.getCollateralID(), isAdd);
		} catch (CollateralException e) {
			DefaultLogger.error(this, e.getMessage(), e);
			throw new EJBException("failed to create/update staging security coverage, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		} 
		
		// additional document facility details
				try {
					setAddtionalDocumentFacilityDetailsRef(collateral.getAdditonalDocFacDetails(), isAdd);
				}
				catch (CreateException e) {
					throw new EJBException("failed to create/update additional document facility details, staging collateral id ["
							+ collateral.getCollateralID() + "]", e);
				}

		// security apportionment
		/* 
		try {
			setSecApportionmentRef(collateral.getSecApportionment(), isAdd);
		}
		catch (CreateException e) {
			throw new EJBException("failed to create/update security apportionment, staging collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}
		catch (RemoveException e) {
			throw new EJBException("failed to remove security apportionment, staging collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}
		*/

	}

	/**
	 * Set valuation of the collateral.
	 * 
	 * @param valuation of type IValuation
	 * @throws javax.ejb.CreateException on error creating reference to
	 *         valuation entity
	 */
	protected void setValuationRef(IValuation valuation) throws CreateException {
		if (valuation == null) {
			return;
		}
		EBValuationLocalHome ejbHome = getEBValuationLocalHome();
		getValuationCMR().add(ejbHome.create(valuation));
	}

	public List getSecApportionment() {
		List lst = new ArrayList();
		try {
			lst = new SecApportionmentDAO().getStgApportionmentsForSecurity(getCollateralID());
		}
		catch (Exception ex) {
		}
		return lst;
	}

	public List getSourceValuationDetails() {
		return null; // Todo - implement method
	}

	// FOR SourceType == "A" . ie automatic for System Valuation Details
	public IValuation getSourceValuation() {
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> in getSourceValuation()");
		IValuation iValuation = null;
		String sourceType = ICMSConstant.VALUATION_SOURCE_TYPE_A;
		ICollateralDAO dao = CollateralDAOFactory.getStagingDAO();
		try {
			iValuation = dao.getSourceValuation(getCollateralID(), sourceType);
		}
		catch (SearchDAOException ex) {
		}
		return iValuation;

	}

	public void setSourceValuation(IValuation shareSecArray) {

	}

	// FOR SourceType == "S" . ie for from LOS/Source System
	public IValuation[] getValuationFromLOS() {
		return null;
	}

	public void setValuationFromLOS(IValuation[] shareSecArray) {

	}

	// FOR SourceType == "M" . ie manual for Input into GCMS
	public IValuation getValuationIntoCMS() {
		IValuation iValuation = null;
		String sourceType = ICMSConstant.VALUATION_SOURCE_TYPE_M;
		ICollateralDAO dao = CollateralDAOFactory.getStagingDAO();
		try {
			iValuation = dao.getSourceValuation(getCollateralID(), sourceType);
		}
		catch (SearchDAOException ex) {
			ex.printStackTrace();
		}
		return iValuation;
	}

	public void setValuationIntoCMS(IValuation shareSecArray) {

	}

	protected EBInstrumentLocalHome getEBInstrumentLocalHome() {
		EBInstrumentLocalHome ejbHome = (EBInstrumentLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_INSTRUMENT_STAGE_JNDI, EBInstrumentLocalHome.class.getName());
		if (ejbHome == null) {
			throw new EJBException("EBInstrumentStageLocalHome is Null!");
		}
		return ejbHome;
	}
}