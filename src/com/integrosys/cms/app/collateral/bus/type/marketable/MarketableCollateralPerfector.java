package com.integrosys.cms.app.collateral.bus.type.marketable;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.collateral.bus.AbstractCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.IBondsCommon;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondsforeign.IBondsForeign;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondslocal.IBondsLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.govtforeigndiffccy.IGovtForeignDiffCurrency;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.govtforeignsameccy.IGovtForeignSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexforeign.IMainIndexForeign;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexlocal.IMainIndexLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.nonlistedlocal.INonListedLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedforeign.IOtherListedForeign;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.IOtherListedLocal;

public class MarketableCollateralPerfector extends AbstractCollateralPerfector {

	private static final Class[] STOCK_CODE_REQUIRED_SUB_CLASSES = new Class[] { IBondsForeign.class,
			IBondsLocal.class, IGovtForeignSameCurrency.class, IGovtForeignDiffCurrency.class, IMainIndexLocal.class,
			IOtherListedLocal.class, IOtherListedForeign.class };

	private static final Class[] MATURITY_DATE_REQUIRED_SUB_CLASSES = new Class[] { IBondsCommon.class,
			INonListedLocal.class, IMainIndexForeign.class };

	public boolean isCollateralPerfected(ICollateral collateral) {

		IMarketableCollateral col = (IMarketableCollateral) collateral;

		if (!isCollateralCommonFieldsPerfected(col)) {
			return false;
		}

		if (!isChargeDetailsPerfected(col.getLimitCharges())) {
			return false;
		}

		IMarketableEquity[] eqList = col.getEquityList();
		if ((null == eqList) || (eqList.length == 0)) {
			return false;
		}
		else {
			for (int i = 0; i < eqList.length; i++) {
				if (eqList[i].getNoOfUnits() == 0) {
					return false;
				}

				if (isClassAssignableWithAnyOfClasses(col.getClass(), STOCK_CODE_REQUIRED_SUB_CLASSES)) {
					if (StringUtils.isBlank(eqList[i].getStockCode())) {
						return false;
					}
				}

				if (isClassAssignableWithAnyOfClasses(col.getClass(), MATURITY_DATE_REQUIRED_SUB_CLASSES)) {
					if (eqList[i].getCollateralMaturityDate() == null) {
						return false;
					}
				}
			}
		}

		return true;
	}
}