/*
 * Created on May 25, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.support;

import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author Administrator
 * @deprecated 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class StockPriceCappingRule {
    private final static Logger logger = LoggerFactory.getLogger(StockPriceCappingRule.class);
//	public static final String BOARDTP_KLCICOMP = "001";
//	public static final String BOARDTP_MBEXCLKLCI = "002";
//	public static final String BOARDTP_SECONDBD = "003";
//	public static final String BOARDTP_MESDAQ = "004";
//	public static final String BOARDTP_MBEXCLSTI = "005";
//	public static final String BOARDTP_SESDAQ = "006";
//	public static final String BOARDTP_STICOMP = "007";
//	public static final String RELISTED = "relisted";

    public static final String BOARDTP_KLCICOMP = "1";
    public static final String BOARDTP_MBEXCLKLCI = "2";
    public static final String BOARDTP_SECONDBD = "3";
    public static final String BOARDTP_MESDAQ = "4";
    public static final String BOARDTP_MBEXCLSTI = "5";
    public static final String BOARDTP_SESDAQ = "6";
    public static final String BOARDTP_STICOMP = "7";
    public static final String RELISTED = "relisted";


	private static HashMap cappingPercentage = new HashMap();

	static {
		cappingPercentage.put(BOARDTP_KLCICOMP, new double[] { 1.0, 1.5 });
		cappingPercentage.put(BOARDTP_MBEXCLKLCI, new double[] { 1.0, 1.5 });
		cappingPercentage.put(BOARDTP_SECONDBD, new double[] { 1.0, 1.5 });
		cappingPercentage.put(BOARDTP_MESDAQ, new double[] { 0.5, 1.5 });
		cappingPercentage.put(BOARDTP_MBEXCLSTI, new double[] { 0.5, 1.5 });
		cappingPercentage.put(BOARDTP_SESDAQ, new double[] { 0.5, 1.5 });
		cappingPercentage.put(BOARDTP_STICOMP, new double[] { 0.5, 1.5 });
		cappingPercentage.put(RELISTED, new double[] { 0.5, 1.5 });
	}

	public static Amount getPriceCap(String boardType, Amount curPrice, Amount lastQuarterPrice, Double cappingPrice) {
		if (curPrice != null) {

            if(boardType == null) {
                logger.info("Board Type is null, no capping retrieved.");
                return curPrice;
            }

            double[] percentConfig = (double[]) (cappingPercentage.get(boardType));
			double curPriceVal = curPrice.getAmount() * percentConfig[0];

            //DefaultLogger.debug("StockPriceCappingRule", ">>>>>>>>>>>>>>> percentConfig = " + percentConfig[0]);
            //DefaultLogger.debug("StockPriceCappingRule", ">>>>>>>>>>>>>>> currPriceVal = " + curPriceVal);

            if ((lastQuarterPrice != null) && (cappingPrice != null)) {
				double minVal = Math.min(Math.min(curPriceVal, lastQuarterPrice.getAmount() * percentConfig[1]),
						cappingPrice.doubleValue());
				return new Amount(minVal, curPrice.getCurrencyCode());
			}
			else if ((lastQuarterPrice == null) && (cappingPrice != null)) {
				double minVal = Math.min(curPriceVal, cappingPrice.doubleValue());
                //logger.debug(">>>>>>>>>>>>>>> curPriceVal = " + curPriceVal);
                //logger.debug(">>>>>>>>>>>>>>> cappingPrice.doubleValue() = " + cappingPrice.doubleValue());
                //logger.debug(">>>>>>>>>>>>>>> minVal = " + minVal);
                return new Amount(minVal, curPrice.getCurrencyCode());
			}
			else if ((lastQuarterPrice != null) && (cappingPrice == null)) {
				double minVal = Math.min(curPriceVal, lastQuarterPrice.getAmount() * percentConfig[1]);
				return new Amount(minVal, curPrice.getCurrencyCode());
			}
			else {
				return curPrice;
			}
		}
		else {
			return null;
		}
	}

	public static Amount getRelistPriceCap(Amount curPrice, Amount lastQuarterPrice) {
		if (curPrice != null) {
			double cappingPrice = 0.5;
			double[] percentConfig = (double[]) (cappingPercentage.get(RELISTED));
			double curPriceVal = curPrice.getAmount() * percentConfig[0];
			if (lastQuarterPrice != null) {
				double minVal = Math.min(Math.min(curPriceVal, lastQuarterPrice.getAmount() * percentConfig[1]),
						cappingPrice);
				return new Amount(minVal, curPrice.getCurrencyCode());
			}
			else {
				double minVal = Math.min(curPriceVal, cappingPrice);
				return new Amount(minVal, curPrice.getCurrencyCode());
			}
		}
		else {
			return null;
		}
	}
}
