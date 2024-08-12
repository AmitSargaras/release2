/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.liquidation.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * The SBLiquidationBusManagerStagingBean acts as the facade to the Entity Beans
 * for Liquidation stage data.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class SBLiquidationBusManagerStagingBean extends SBLiquidationBusManagerBean {
	/**
	 * Default Constructor
	 */
	public SBLiquidationBusManagerStagingBean() {
	}

	/**
	 * @see com.integrosys.cms.app.liquidation.bus.ILiquidationBusManager#getLiquidation
	 */
	/*
	 * public ILiquidation getLiquidation (long liquidationID) throws
	 * LiquidationException {
	 * 
	 * try { System.out.println("in SBLiquidationBusManagerStagingBean ");
	 * EBRecoveryExpenseHome recoveryExpenseHome = getEBRecoveryExpenseHome();
	 * // Collection expense =
	 * recoveryExpenseHome.findByLiquidationID(liquidationID); EBRecoveryHome
	 * recoveryHome = getEBRecoveryHome(); // Collection recovery =
	 * recoveryHome.findByLiquidationID(liquidationID); EBRecoveryIncomeHome
	 * recoveryIncomeHome = getEBRecoveryIncomeHome(); // Collection income =
	 * recoveryIncomeHome.findByRecoveryID(liquidationID); Collection recovery =
	 * null; Collection expense = null;
	 * 
	 * ArrayList exp = new ArrayList(); Iterator itrExp = null; if (expense !=
	 * null) itrExp = expense.iterator(); if (itrExp != null) while
	 * (itrExp.hasNext()) { EBRecoveryExpense theEjb = (EBRecoveryExpense)
	 * itrExp.next(); exp.add (theEjb.getValue());
	 * DefaultLogger.debug(this,theEjb.getValue()); } ArrayList rec = new
	 * ArrayList(); Iterator itrRec = null; if (recovery != null) itrRec =
	 * recovery.iterator(); if (itrRec != null) while (itrRec.hasNext()) {
	 * EBRecovery theEjb = (EBRecovery) itrRec.next(); rec.add
	 * (theEjb.getValue()); DefaultLogger.debug(this,theEjb.getValue()); }
	 * 
	 * /ArrayList inc = new ArrayList(); Iterator itrInc = null; if (income !=
	 * null) itrInc = income.iterator(); if (itrInc != null) while
	 * (itrInc.hasNext()) { EBRecoveryIncome theEjb = (EBRecoveryIncome)
	 * itrInc.next(); inc.add (theEjb.getValue());
	 * DefaultLogger.debug(this,theEjb.getValue()); }
	 * 
	 * ILiquidation ob = new OBLiquidation(); ob.setRecoveryExpense(exp);
	 * ob.setRecovery(rec); // ob.setRecoveryIncome(inc);
	 * ob.setCollateralID(liquidationID);
	 * System.out.println("return OBstageLiquidation" + ob); return ob; //
	 * }catch (FinderException e) { // throw new LiquidationException
	 * ("Exception caught at getLiquidation: " + e.toString()); }catch
	 * (Exception e) { throw new LiquidationException
	 * ("Exception caught at getLiquidation: " + e.toString()); }
	 * 
	 * }
	 */

	protected EBLiquidationHome getLiquidationHome() throws LiquidationException {

		EBLiquidationHome ejbHome = (EBLiquidationHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_LIQUIDATION_STAGING_JNDI, EBLiquidationHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBLiquidationHome is null!");
		}

		return ejbHome;
	}

	protected EBRecoveryHome getEBRecoveryHome() throws LiquidationException {
		EBRecoveryHome ejbHome = (EBRecoveryHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_RECOVERY_STAGING_JNDI,
				EBRecoveryHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryHome is null!");
		}

		return ejbHome;
	}

	protected EBRecoveryIncomeHome getEBRecoveryIncomeHome() throws LiquidationException {
		EBRecoveryIncomeHome ejbHome = (EBRecoveryIncomeHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_RECOVERY_INCOME_STAGING_JNDI, EBRecoveryIncomeHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryIncomeHome is null!");
		}

		return ejbHome;
	}

	protected EBRecoveryExpenseHome getEBRecoveryExpenseHome() throws LiquidationException {
		EBRecoveryExpenseHome ejbHome = (EBRecoveryExpenseHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_RECOVERY_EXPENSE_STAGING_JNDI, EBRecoveryExpenseHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryExpenseHome is null!");
		}

		return ejbHome;
	}

	/**
	 * helper method to get staging Liquidation home interface.
	 * 
	 * @return Liquidation home interface
	 * @throws LiquidationException on errors encountered
	 */
	protected EBNPLInfoHome getEBNPLInfoHome() throws LiquidationException {
		EBNPLInfoHome ejbHome = (EBNPLInfoHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_NPL_INFO_STAGING_JNDI,
				EBNPLInfoHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBNPLInfoHome for staging is null!");
		}

		return ejbHome;
	}

}