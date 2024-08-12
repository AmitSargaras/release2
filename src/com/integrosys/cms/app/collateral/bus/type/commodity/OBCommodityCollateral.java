/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBCommodityCollateral.java,v 1.11 2005/07/18 09:53:05 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a Collateral of type Commodity entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/07/18 09:53:05 $ Tag: $Name: $
 */
public class OBCommodityCollateral extends OBCollateral implements ICommodityCollateral {
	private IContract[] contracts;

	private IApprovedCommodityType[] approvedCommodityTypes;

	private IHedgingContractInfo[] hedgingContractInfos;

	private ILoanAgency[] loans;

	private String degreeOfEnvironmentalRisky;

	private Date dateOfDegreeOfEnvironmentalRiskyConfirmed;

	private String remarks;

	private String approvedCustomerDifferentialSign;

	private BigDecimal approvedCustomerDifferential;

	private IPreCondition[] preConditions;

	private IPreCondition preCondition;

	private boolean updatePreConditionOnly;

	/**
	 * Default Constructor.
	 */
	public OBCommodityCollateral() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICommodityCollateral
	 */
	public OBCommodityCollateral(ICommodityCollateral obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get a list of commodity contracts.
	 * 
	 * @return IContract[]
	 */
	public IContract[] getContracts() {
		if ((contracts != null) && (contracts.length > 0)) {
			ArrayList activeContracts = new ArrayList();
			for (int i = 0; i < contracts.length; i++) {
				if ((contracts[i].getStatus() != null) && contracts[i].getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}
				activeContracts.add(contracts[i]);
			}
			return (activeContracts.size() == 0) ? null : (IContract[]) activeContracts
					.toArray(new IContract[activeContracts.size()]);
		}
		return null;
	}

	/**
	 * Set a list of commodity contracts.
	 * 
	 * @param contracts of type IContract[]
	 */
	public void setContracts(IContract[] contracts) {
		this.contracts = contracts;
	}

	public IApprovedCommodityType[] getApprovedCommodityTypes() {
		return approvedCommodityTypes;
	}

	public void setApprovedCommodityTypes(IApprovedCommodityType[] approvedCommodityTypes) {
		this.approvedCommodityTypes = approvedCommodityTypes;
	}

	public IHedgingContractInfo[] getHedgingContractInfos() {
		if ((hedgingContractInfos != null) && (hedgingContractInfos.length > 0)) {
			ArrayList deletedContracts = new ArrayList();
			for (int i = 0; i < hedgingContractInfos.length; i++) {
				if ((hedgingContractInfos[i].getStatus() != null)
						&& hedgingContractInfos[i].getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}
				deletedContracts.add(hedgingContractInfos[i]);
			}
			return (deletedContracts.size() == 0) ? null : (IHedgingContractInfo[]) deletedContracts
					.toArray(new IHedgingContractInfo[deletedContracts.size()]);
		}
		return null;
	}

	public void setHedgingContractInfos(IHedgingContractInfo[] hedgingContractInfos) {
		this.hedgingContractInfos = hedgingContractInfos;
	}

	/**
	 * Gets the list of DELETED contracts associated with this commodity
	 * collateral. Note that this list contains non-active contracts as
	 * retrieved from the underlying persistence.
	 * 
	 * @return IContract[] - list of DELETED contracts
	 */
	public IContract[] getDeletedContracts() {
		ArrayList deletedContracts = new ArrayList();
		for (int i = 0; i < contracts.length; i++) {
			if ((contracts[i].getStatus() != null) && contracts[i].getStatus().equals(ICMSConstant.STATE_DELETED)) {
				deletedContracts.add(contracts[i]);
			}
		}
		return (deletedContracts.size() == 0) ? null : (IContract[]) deletedContracts
				.toArray(new IContract[deletedContracts.size()]);
	}

	/**
	 * Gets the list of DELETED hedging contracts associated with this commodity
	 * collateral. Note that this list contains non-active hedging contracts as
	 * retrieved from the underlying persistence.
	 * 
	 * @return IHedgingContractInfo[] - list of DELETED hedging contracts
	 */
	public IHedgingContractInfo[] getDeletedHedgeContractInfos() {
		ArrayList deletedContracts = new ArrayList();
		for (int i = 0; i < hedgingContractInfos.length; i++) {
			if ((hedgingContractInfos[i].getStatus() != null)
					&& hedgingContractInfos[i].getStatus().equals(ICMSConstant.STATE_DELETED)) {
				deletedContracts.add(hedgingContractInfos[i]);
			}
		}
		return (deletedContracts.size() == 0) ? null : (IHedgingContractInfo[]) deletedContracts
				.toArray(new IHedgingContractInfo[deletedContracts.size()]);
	}

	public ILoanAgency[] getLoans() {
		return loans;
	}

	public void setLoans(ILoanAgency[] loans) {
		this.loans = loans;
	}

	public BigDecimal getApprovedCustomerDifferential() {
		return approvedCustomerDifferential;
	}

	public void setApprovedCustomerDifferential(BigDecimal approvedCustomerDifferential) {
		this.approvedCustomerDifferential = approvedCustomerDifferential;
	}

	public String getApprovedCustomerDifferentialSign() {
		return approvedCustomerDifferentialSign;
	}

	public void setApprovedCustomerDifferentialSign(String approvedCustomerDifferentialSign) {
		this.approvedCustomerDifferentialSign = approvedCustomerDifferentialSign;
	}

	public Date getDateOfDegreeOfEnvironmentalRiskyConfirmed() {
		return dateOfDegreeOfEnvironmentalRiskyConfirmed;
	}

	public void setDateOfDegreeOfEnvironmentalRiskyConfirmed(Date dateOfDegreeOfEnvironmentalRiskyConfirmed) {
		this.dateOfDegreeOfEnvironmentalRiskyConfirmed = dateOfDegreeOfEnvironmentalRiskyConfirmed;
	}

	public String getDegreeOfEnvironmentalRisky() {
		return degreeOfEnvironmentalRisky;
	}

	public void setDegreeOfEnvironmentalRisky(String degreeOfEnvironmentalRisky) {
		this.degreeOfEnvironmentalRisky = degreeOfEnvironmentalRisky;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Retrieve pre-Condition for the specific BCA.
	 * 
	 * @param limitProfileID of type long
	 * @return a pre-condition
	 */
	public IPreCondition retrievePreCondition(long limitProfileID) {
		if ((preCondition != null) && (preCondition.getLimitProfileID() == limitProfileID)) {
			return preCondition;
		}

		if (preConditions != null) {
			for (int i = 0; i < preConditions.length; i++) {
				if (preConditions[i].getLimitProfileID() == limitProfileID) {
					this.preCondition = preConditions[i];
					return preConditions[i]; // return the same reference.
				}
			}
		}
		return null;
	}

	/**
	 * Set pre-condition.
	 * 
	 * @param preCondition of type IPreCondition
	 */
	public void setPreCondition(IPreCondition preCondition) {
		this.preCondition = preCondition;
	}

	/**
	 * Set preconditions.
	 * 
	 * @param preConditions of type IPreCondition[]
	 */
	protected void setPreConditions(IPreCondition[] preConditions) {
		this.preConditions = preConditions;
	}

	/**
	 * Get precondition.
	 * 
	 * @return IPreCondition
	 */
	protected IPreCondition getPreCondition() {
		return this.preCondition;
	}

	/**
	 * Indicator to update pre-condition only.
	 * 
	 * @return boolean
	 */
	protected boolean isUpdatePreConditionOnly() {
		return updatePreConditionOnly;
	}

	/**
	 * Indicator to update pre-condition only.
	 * 
	 * @param updatePreConditionOnly of type boolean
	 */
	public void setUpdatePreConditionOnly(boolean updatePreConditionOnly) {
		this.updatePreConditionOnly = updatePreConditionOnly;
	}

	public boolean equals(Object o) {
		return ((o != null) && this.toString().equals(o.toString())) && (o.getClass().equals(this.getClass()));
	}

}