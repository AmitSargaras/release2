/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/proxy/CollateralProxyUtil.java,v 1.17 2006/09/15 12:44:28 hshii Exp $
 */
package com.integrosys.cms.app.collateral.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

/**
 * Proxy utility class.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2006/09/15 12:44:28 $ Tag: $Name: $
 */
public abstract class CollateralProxyUtil {
	/**
	 * Helper method to get parent transaction id given a list of transactions.
	 * 
	 * @param trxValues of type ICollateralTrxValue[]
	 * @return transaction that belongs to the parent transaction id
	 */
	public static ICollateralTrxValue getParentTrxValue(ICollateralTrxValue[] trxValues) {
		int size = 0;
		if ((trxValues == null) || ((size = trxValues.length) == 0)) {
			return null;
		}

		String parentTrxID = trxValues[0].getTrxReferenceID();
		if (parentTrxID == null) {
			return null;
		}

		for (int i = 0; i < size; i++) {
			if (trxValues[i].getTransactionID().equals(parentTrxID)) {
				return trxValues[i];
			}
		}
		return null;
	}

	/**
	 * Get commmodity collateral limit maps given the limit profile.
	 * 
	 * @param lp of type ILimitProfile
	 * @return HashMap contains key: ICollateral, value: Arraylist of ILimit or
	 *         ICoBorrowerLimit
	 * @throws CollateralException on errors
	 */
	public static final HashMap getCommodityLimitMaps(ILimitProfile lp) throws CollateralException {
		ILimit[] limits = lp.getLimits();
		HashMap hmap = new HashMap();

		if ((limits == null) || (limits.length == 0)) {
			return hmap;
		}

		for (int i = 0; i < limits.length; i++) {
			ILimit limit = limits[i];
			ICollateralAllocation[] allocList = limit.getCollateralAllocations();

			if (allocList != null) {
				for (int j = 0; j < allocList.length; j++) {
					ICollateral col = allocList[j].getCollateral();
					if (col == null) {
						throw new CollateralException("ICollateral in ICollateralAllocation is null for limitID: "
								+ limit.getLimitID());
					}

					// only to get commodity collaterals
					if (!(col instanceof ICommodityCollateral)) {
						continue;
					}

					ArrayList alist = (ArrayList) hmap.get(col);

					if (alist == null) {
						alist = new ArrayList();
					}
					alist.add(limit);
					hmap.put(col, alist);
				}
			}

			ICoBorrowerLimit[] colmts = limit.getCoBorrowerLimits();
			if (colmts != null) {
				for (int k = 0; k < colmts.length; k++) {
					ICoBorrowerLimit colmt = colmts[k];
					allocList = colmt.getCollateralAllocations();
					if (allocList == null) {
						continue;
					}
					for (int l = 0; l < allocList.length; l++) {
						ICollateral col = allocList[l].getCollateral();
						if (col == null) {
							throw new CollateralException("ICollateral in ICollateralAllocation is null for LimitID: "
									+ colmt.getLimitID());
						}

						// only to get commodity collaterals
						if (!(col instanceof ICommodityCollateral)) {
							continue;
						}

						ArrayList alist = (ArrayList) hmap.get(col);

						if (alist == null) {
							alist = new ArrayList();
						}
						alist.add(colmt);
						hmap.put(col, alist);
					}
				}
			}
		}
		return hmap;
	}

	/**
	 * Get A list of commodity collateral ids given the limit profile.
	 * 
	 * @param lp of type ILimitProfile
	 * @return a list of collateral ids
	 * @throws CollateralException on errors
	 */
	public static Long[] getCommodityIDs(ILimitProfile lp) throws CollateralException {
		ILimit[] limits = lp.getLimits();
		ICoBorrowerLimit[] coLimit = null;
		if ((limits == null) || (limits.length == 0)) {
			return null;
		}

		ArrayList idList = new ArrayList();
		for (int i = 0; i < limits.length; i++) {
			ILimit limit = limits[i];
			ICollateralAllocation[] allocList = limit.getCollateralAllocations();
			if (allocList != null) {

				for (int j = 0; j < allocList.length; j++) {
					ICollateral col = allocList[j].getCollateral();
					if (col == null) {
						throw new CollateralException("ICollateral in ICollateralAllocation is null for limitID: "
								+ limit.getLimitID());
					}

					// only to get commodity collaterals
					if (!(col instanceof ICommodityCollateral)) {
						continue;
					}

					Long colID = new Long(col.getCollateralID());

					if (!idList.contains(colID)) {
						idList.add(new Long(col.getCollateralID()));
					}
				}
			}
			// CR -035
			if (limit.getCoBorrowerLimits() != null) {
				coLimit = limit.getCoBorrowerLimits();
				if (coLimit != null) {
					for (int k = 0; k < coLimit.length; k++) {
						ICollateralAllocation[] allocList1 = coLimit[k].getCollateralAllocations();
						if (allocList1 != null) {
							for (int j = 0; j < allocList1.length; j++) {
								ICollateral col = allocList1[j].getCollateral();
								if (col == null) {
									throw new CollateralException(
											"ICollateral in ICollateralAllocation is null for limitID: "
													+ coLimit[k].getLimitID());
								}

								// only to get commodity collaterals
								if (!(col instanceof ICommodityCollateral)) {
									continue;
								}

								Long colID = new Long(col.getCollateralID());

								if (!idList.contains(colID)) {
									idList.add(new Long(col.getCollateralID()));
								}
							}
						}
					}
				}
			}
		}
		return (Long[]) idList.toArray(new Long[0]);
	}

	/**
	 * To check if the collateral limit belongs to the customer limit profile.
	 * 
	 * @param lp customer limit profile
	 * @param colLmt collateral limit
	 * @return boolean
	 */
	public static boolean isTheCustomerLimit(ILimitProfile lp, ICollateralLimitMap colLmt) {
		if (lp.getLimitProfileRef() == null) {
			ILimit[] lmts = lp.getLimits();
			for (int i = 0; i < lmts.length; i++) {
				if (lmts[i].getLimitID() == colLmt.getLimitID()) {
					return true;
				}
			}
		}
		else {
			long sciLp = Long.parseLong(lp.getLimitProfileRef());
			if (sciLp == colLmt.getSCILimitProfileID()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * To check if the collateral limit belongs to the customer limit profile.
	 * 
	 * @param lp customer limit profile
	 * @param colLmt collateral limit
	 * @return boolean
	 */
	public static Object getCustomerLimit(ILimitProfile lp, ICollateralLimitMap colLmt) {
		ILimit[] lmts = lp.getLimits();

		for (int i = 0; i < lmts.length; i++) {
			if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(colLmt.getCustomerCategory())) {
				if (lmts[i].getLimitID() == colLmt.getLimitID()) {
					return lmts[i];
				}
			}
			else if (ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER.equals(colLmt.getCustomerCategory())) {
				ICoBorrowerLimit[] colmts = lmts[i].getCoBorrowerLimits();
				if (colmts != null) {
					for (int j = 0; j < colmts.length; j++) {
						if (colmts[j].getLimitID() == colLmt.getCoBorrowerLimitID()) {
							return colmts[j];
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * Helper method to remove charges from the given charges list and limit id.
	 * 
	 * @param charges a list of ILimitCharge objects
	 * @param limitID limit id
	 * @return a new list of ILimitCharge objects
	 */
	public static ILimitCharge[] removeCollateralCharges(ILimitCharge[] charges, long limitID) {
		List deletedCharges = new ArrayList();
		List newCharges = new ArrayList();

		if (charges != null) {
			for (int i = 0; i < charges.length; i++) {
				ICollateralLimitMap[] maps = charges[i].getLimitMaps();

				boolean mapFound = false;
				ArrayList newMaps = new ArrayList();

				for (int j = 0; j < maps.length; j++) {
					if (maps[j].getLimitID() == limitID) {
						mapFound = true;
					}
					else {
						newMaps.add(maps[j]);
					}
				}
				charges[i].setLimitMaps((ICollateralLimitMap[]) newMaps.toArray(new ICollateralLimitMap[0]));

				if (mapFound && (maps.length <= 1)) {
					deletedCharges.add(charges[i]);
				}
				else {
					newCharges.add(charges[i]);
				}
			}
		}
		return (ILimitCharge[]) newCharges.toArray(new ILimitCharge[0]);
	}

	/**
	 * Helper method to remove a limit from a collateral limit map list.
	 * 
	 * @param limitMap a list of ILimitCharge objects
	 * @param limitID limit id
	 * @return a new list of ICollateralLimitMap objects
	 */
	public static ICollateralLimitMap[] removeCollateralLimit(ICollateralLimitMap[] limitMap, long limitID) {
		List newLimits = new ArrayList();

		if (limitMap != null) {
			for (int i = 0; i < limitMap.length; i++) {
				ICollateralLimitMap map = limitMap[i];

				if (map.getLimitID() != limitID) {
					newLimits.add(map);
				}
			}
		}
		return (ICollateralLimitMap[]) newLimits.toArray(new ICollateralLimitMap[0]);
	}

}
