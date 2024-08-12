/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/QuantityDifferential.java,v 1.8 2004/08/30 12:27:52 hshii Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.io.Serializable;
import java.math.BigDecimal;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.8 $
 * @since $Date: 2004/08/30 12:27:52 $ Tag: $Name: $
 */
public class QuantityDifferential implements Serializable {
	private Quantity myQty;

	private DifferentialSign mySign;

	public QuantityDifferential() {
	}

	public QuantityDifferential(Quantity aQty, String aSignCode) {
		myQty = aQty;
		mySign = DifferentialSign.valueOf(aSignCode);
	}

	public QuantityDifferential(Quantity aQty, DifferentialSign aSign) {
		myQty = aQty;
		mySign = aSign;
	}

	public Quantity getQuantity() {
		return myQty;
	}

	public void setQuantity(Quantity aQty) {
		myQty = aQty;
	}

	public DifferentialSign getSign() {
		return mySign;
	}

	public void setSign(DifferentialSign aSign) {
		mySign = aSign;
	}

	/**
	 * Apply differential to the quantity.
	 * 
	 * @param qtyToApplyDifferential
	 * @return
	 * @throws PriceDifferentialException
	 */
	public Quantity calculate(Quantity qtyToApplyDifferential) throws PriceDifferentialException {

		if (qtyToApplyDifferential == null) {
			return null;
		}
		if (qtyToApplyDifferential.getQuantity() == null) {
			return qtyToApplyDifferential;
		}

		BigDecimal qty = qtyToApplyDifferential.getQuantity();
		BigDecimal qtyDiff = myQty.getQuantity();

		if (mySign == DifferentialSign.POSITIVE_DIFFERENTIAL) {
			qty = qty.add(qtyDiff);
		}
		else if (mySign == DifferentialSign.NEGATIVE_DIFFERENTIAL) {
			qty = qty.subtract(qtyDiff);
		}
		else {
			// no change to qty
		}
		return new Quantity(qty, qtyToApplyDifferential.getUnitofMeasure());
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof QuantityDifferential)) {
			return false;
		}

		final QuantityDifferential quantityDifferential = (QuantityDifferential) o;

		if (myQty != null ? !myQty.equals(quantityDifferential.myQty) : quantityDifferential.myQty != null) {
			return false;
		}
		if (mySign != null ? !mySign.equals(quantityDifferential.mySign) : quantityDifferential.mySign != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (myQty != null ? myQty.hashCode() : 0);
		result = 29 * result + (mySign != null ? mySign.hashCode() : 0);
		return result;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
