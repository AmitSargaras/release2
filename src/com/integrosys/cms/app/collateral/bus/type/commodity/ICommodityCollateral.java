/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/ICommodityCollateral.java,v 1.7 2005/07/15 08:22:24 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.cms.app.collateral.bus.ICollateral;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: May 3, 2004 Time:
 * 10:28:33 AM To change this template use File | Settings | File Templates.
 */
public interface ICommodityCollateral extends ICollateral, java.io.Serializable {

	public static final String DEGREE_OF_RISK_HIGH = "HIGH";

	public static final String DEGREE_OF_RISK_MEDIUM = "MEDIUM";

	public static final String DEGREE_OF_RISK_LOW = "LOW";

	public static final String DEGREE_OF_RISK_NA = "NA";

	public IContract[] getContracts();

	public IApprovedCommodityType[] getApprovedCommodityTypes();

	public IHedgingContractInfo[] getHedgingContractInfos();

	public ILoanAgency[] getLoans();

	/**
	 * Gets the list of DELETED contracts associated with this commodity
	 * collateral. Note that this list contains non-active contracts as
	 * retrieved from the underlying persistence.
	 * 
	 * @return IContract[] - list of DELETED contracts
	 */
	public IContract[] getDeletedContracts();

	/**
	 * Gets the list of DELETED hedging contracts associated with this commodity
	 * collateral. Note that this list contains non-active hedging contracts as
	 * retrieved from the underlying persistence.
	 * 
	 * @return IHedgingContractInfo[] - list of DELETED hedging contracts
	 */
	public IHedgingContractInfo[] getDeletedHedgeContractInfos();

	/**
	 * 
	 * @return ICommodityCollateral.DEGREE_OF_RISK_XXX ( hight, medium,low, or
	 *         NA)
	 */
	public String getDegreeOfEnvironmentalRisky();

	public Date getDateOfDegreeOfEnvironmentalRiskyConfirmed();

	public String getRemarks();

	/**
	 * @return ICMSConstant.SIMPLE_SIGN_PLUS/MINUS/PLUS_OR_MINUS
	 */
	public String getApprovedCustomerDifferentialSign();

	public BigDecimal getApprovedCustomerDifferential();

	/**
	 * Retrieve pre-Condition for the specific BCA.
	 * 
	 * @param limitProfileID of type long
	 * @return a pre-condition
	 */
	public IPreCondition retrievePreCondition(long limitProfileID);

	/**
	 * Set pre-condition.
	 * 
	 * @param preCondition of type IPreCondition
	 */
	public void setPreCondition(IPreCondition preCondition);
}
