package com.integrosys.cms.app.liquidation.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This object represents Liquidation including income, expenses
 * 
 * @author $Author: lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBLiquidation implements ILiquidation {

	private static final long serialVersionUID = 6961665298119991479L;

	private Long liquidationID;

	private long collateralID;

	private String securityID;

	private String securityType;

	private Collection recoveryExpense = null;

	private Collection recovery = null;

	// private Collection nPLInfo = null;
	private long versionTime;

	/**
	 * Default Constructor.
	 */
	public OBLiquidation() {
		super();
		recoveryExpense = new ArrayList();
		recovery = new ArrayList();
		// nPLInfo = new ArrayList();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ILiquidation
	 */
	public OBLiquidation(ILiquidation obj) {
		this();
		AccessorUtil.copyValue(obj, this);

	}

	public Long getLiquidationID() {
		return liquidationID;
	}

	public void setLiquidationID(Long liquidationID) {
		this.liquidationID = liquidationID;
	}

	public String getSecurityID() {
		return securityID;
	}

	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	public long getCollateralID() {
		return collateralID;
	}

	public Collection getRecoveryExpense() {
		return recoveryExpense;
	}

	public void setRecoveryExpense(Collection recoveryExpense) {
		this.recoveryExpense = recoveryExpense;
	}

	public Collection getRecovery() {
		return recovery;
	}

	public void setRecovery(Collection recovery) {
		this.recovery = recovery;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public void removeExpenseItems(int[] anItemIndexList) {
		Collection expense = getRecoveryExpense();
		IRecoveryExpense[] itemList = null;
		if (expense != null) {
			itemList = (IRecoveryExpense[]) expense.toArray(new IRecoveryExpense[0]);
		}
		IRecoveryExpense[] newList = new OBRecoveryExpense[itemList.length - anItemIndexList.length];
		int ctr = 0;
		boolean removeFlag = false;
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				for (int jj = 0; jj < anItemIndexList.length; jj++) {
					if (ii == anItemIndexList[jj]) {
						removeFlag = true;
						break;
					}
				}
				if (!removeFlag) {
					newList[ctr] = itemList[ii];
					ctr++;
				}
				removeFlag = false;
			}
		}
		setRecoveryExpense(new ArrayList(Arrays.asList(newList)));
	}

	public void removeIncomeItems(int[] anItemIndexList, String recoveryType) {
		// Collection income = getRecoveryIncome();
		Collection income = null;
		Collection recovery = getRecovery();
		for (Iterator itrRecovery = recovery.iterator(); itrRecovery.hasNext();) {
			OBRecovery obRecovery = (OBRecovery) itrRecovery.next();
			if (obRecovery.getRecoveryType().equalsIgnoreCase(recoveryType)) {
				income = obRecovery.getRecoveryIncome();
			}
		}
		IRecoveryIncome[] itemList = null;
		if (income != null) {
			itemList = (IRecoveryIncome[]) income.toArray(new IRecoveryIncome[0]);
		}
		IRecoveryIncome[] newList = new OBRecoveryIncome[itemList.length - anItemIndexList.length];
		int ctr = 0;
		boolean removeFlag = false;
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				for (int jj = 0; jj < anItemIndexList.length; jj++) {
					if (ii == anItemIndexList[jj]) {
						removeFlag = true;
						break;
					}
				}
				if (!removeFlag) {
					newList[ctr] = itemList[ii];
					ctr++;
				}
				removeFlag = false;
			}
		}
		recovery = getRecovery();
		for (Iterator itrRecovery = recovery.iterator(); itrRecovery.hasNext();) {
			OBRecovery obRecovery = (OBRecovery) itrRecovery.next();
			if (obRecovery.getRecoveryType().equalsIgnoreCase(recoveryType)) {
				obRecovery.setRecoveryIncome(new ArrayList(Arrays.asList(newList)));
			}
		}
	}

	public void removeRecoveryItems(int[] anItemIndexList) {
		Collection recovery = getRecovery();
		IRecovery[] itemList = null;
		if (recovery != null) {
			itemList = (IRecovery[]) recovery.toArray(new IRecovery[0]);
		}
		IRecovery[] newList = new OBRecovery[itemList.length - anItemIndexList.length];
		int ctr = 0;
		boolean removeFlag = false;
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				for (int jj = 0; jj < anItemIndexList.length; jj++) {
					if (ii == anItemIndexList[jj]) {
						removeFlag = true;
						break;
					}
				}
				if (!removeFlag) {
					newList[ctr] = itemList[ii];
					ctr++;
				}
				removeFlag = false;
			}
		}
		setRecovery(new ArrayList(Arrays.asList(newList)));
	}

	public void addExpenseItem(IRecoveryExpense anItem) {
		int numOfItems = 0;
		IRecoveryExpense[] itemList = null;
		Collection expense = getRecoveryExpense();
		if (expense != null) {
			itemList = (IRecoveryExpense[]) expense.toArray(new IRecoveryExpense[0]);
		}

		if (itemList != null) {
			numOfItems = itemList.length;
		}
		IRecoveryExpense[] newList = new OBRecoveryExpense[numOfItems + 1];
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				newList[ii] = itemList[ii];
			}
		}
		newList[numOfItems] = anItem;
		setRecoveryExpense(new ArrayList(Arrays.asList(newList)));
	}

	public void addIncomeItem(IRecoveryIncome anItem, String recoveryType) {
		int numOfItems = 0;
		IRecoveryIncome[] itemList = null;
		// Collection income = getRecoveryIncome();
		Collection income = null;
		Collection recovery = getRecovery();
		for (Iterator itrRecovery = recovery.iterator(); itrRecovery.hasNext();) {
			OBRecovery obRecovery = (OBRecovery) itrRecovery.next();
			if (obRecovery.getRecoveryType().equalsIgnoreCase(recoveryType)) {
				income = obRecovery.getRecoveryIncome();
			}
		}
		if (income != null) {
			itemList = (IRecoveryIncome[]) income.toArray(new IRecoveryIncome[0]);
		}

		if (itemList != null) {
			numOfItems = itemList.length;
		}
		IRecoveryIncome[] newList = new OBRecoveryIncome[numOfItems + 1];
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				newList[ii] = itemList[ii];
			}
		}
		newList[numOfItems] = anItem;

		for (Iterator itrRecovery = recovery.iterator(); itrRecovery.hasNext();) {
			OBRecovery obRecovery = (OBRecovery) itrRecovery.next();
			if (obRecovery.getRecoveryType().equalsIgnoreCase(recoveryType)) {
				obRecovery.setRecoveryIncome(new ArrayList(Arrays.asList(newList)));
			}
		}
	}

	public void addRecoveryItem(IRecovery anItem) {
		int numOfItems = 0;
		IRecovery[] itemList = null;
		Collection recovery = getRecovery();
		if (recovery != null) {
			itemList = (IRecovery[]) recovery.toArray(new IRecovery[0]);
		}

		if (itemList != null) {
			numOfItems = itemList.length;
		}
		IRecovery[] newList = new OBRecovery[numOfItems + 1];
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				newList[ii] = itemList[ii];
			}
		}
		newList[numOfItems] = anItem;
		setRecovery(new ArrayList(Arrays.asList(newList)));
	}

	public void updateExpense(int anIndex, IRecoveryExpense anIExpense) {
		ArrayList expList = (ArrayList) getRecoveryExpense();

		if ((expList != null) && (anIndex < expList.size())) {
			OBRecoveryExpense obRecoveryExpense = (OBRecoveryExpense) expList.get(anIndex);
			obRecoveryExpense.setDateOfExpense(anIExpense.getDateOfExpense());
			obRecoveryExpense.setExpenseAmount(anIExpense.getExpenseAmount());
			obRecoveryExpense.setExpenseAmtCurrency(anIExpense.getExpenseAmtCurrency());
			obRecoveryExpense.setExpenseType(anIExpense.getExpenseType());
			obRecoveryExpense.setRemarks(anIExpense.getRemarks());
			obRecoveryExpense.setSettled(anIExpense.getSettled());

			setRecoveryExpense(new ArrayList(expList));
		}
	}

	public void updateRecovery(int anIndex, IRecovery anIRecovery) {
		ArrayList recoveryList = (ArrayList) getRecovery();

		if ((recoveryList != null) && (anIndex < recoveryList.size())) {
			OBRecovery obRecovery = (OBRecovery) recoveryList.get(anIndex);
			obRecovery.setRecoveryType(anIRecovery.getRecoveryType());
			obRecovery.setRemarks(anIRecovery.getRemarks());
		}
	}

	public void updateIncome(int anIndex, IRecoveryIncome anIncome, String recoveryType) {
		// ArrayList incList = (ArrayList)getRecoveryIncome();
		ArrayList incList = null;
		Collection recovery = getRecovery();
		for (Iterator itrRecovery = recovery.iterator(); itrRecovery.hasNext();) {
			OBRecovery obRecovery = (OBRecovery) itrRecovery.next();
			if (obRecovery.getRecoveryType().equalsIgnoreCase(recoveryType)) {
				incList = (ArrayList) obRecovery.getRecoveryIncome();
			}
		}

		if ((incList != null) && (anIndex < incList.size())) {
			IRecoveryIncome income = (IRecoveryIncome) incList.get(anIndex);
			income.setRecoveryDate(anIncome.getRecoveryDate());
			income.setRemarks(anIncome.getRemarks());
			income.setTotalAmountRecovered(anIncome.getTotalAmountRecovered());
			income.setTotalAmtRecoveredCurrency(anIncome.getTotalAmtRecoveredCurrency());
		}
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
