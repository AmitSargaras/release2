/*
 * Created on Jun 19, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.rv;

import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.commodity.common.AmountConversion;

public class RVCalculator {

	// cash ---> cmv
	// guarantee ---> cmv
	// insurance ---> cmv
	// others ---> cmv
	// ab --> cmv
	// documentation ---> 0
	// MS --> cmv
	
	public static Amount calculateSecurityRV(ICollateral col) {
		Amount returnAmt = null;
		try {
            String typeCode = col.getCollateralSubType().getTypeCode();

//			if (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(typeCode)) {
//				returnAmt = calculateSecurityPropertyRV(col);
//			} else
//            if (ICMSConstant.SECURITY_TYPE_DOCUMENT.equals(typeCode)) {
//                returnAmt = new Amount((double) 0, col.getSCICurrencyCode());
//            } else {
//                returnAmt = col.getCMV();
                  returnAmt = calculateCommonSecurityRV(col);
//            }
        } catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnAmt;
	}

	private static Amount calculateSecurityPropertyRV(ICollateral col) {
		try {
			IValuation manualVal = col.getValuationIntoCMS();
			Amount cmvAmt = col.getCMV();
			Amount fsvAmt = col.getFSV();
			Amount reserveAmt = null;
			Amount returnAmt = cmvAmt;
			if ((manualVal != null) && (manualVal.getReservePrice() != null)
					&& (manualVal.getReservePrice().getAmount() >= 0)
					&& (cmvAmt != null)) {
				reserveAmt = AmountConversion.getConversionAmount(manualVal
						.getReservePrice(), cmvAmt.getCurrencyCode());
			}
			if ((fsvAmt != null) && (cmvAmt != null)
					&& (fsvAmt.getAmount() < cmvAmt.getAmount())) {
				returnAmt = fsvAmt;
			}
			if ((reserveAmt != null)&& (returnAmt != null)
					&& (reserveAmt.getAmount() < returnAmt.getAmount())) {
				returnAmt = reserveAmt;
			}
			Amount realisableAmt = col.getReservePrice();
			if(returnAmt!=null&&realisableAmt!=null&&realisableAmt.getAmount() < returnAmt.getAmount()){
				returnAmt = realisableAmt;
			}
			return returnAmt;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

    /**
     * Take the lowest of either FSV, Reserved Price or Realisable Price as new RV value
     * @param col
     * @return new RV value
     */
    private static Amount calculateCommonSecurityRV(ICollateral col) {
		try {
			IValuation manualVal = col.getValuationIntoCMS();
			Amount cmvAmt = col.getCMV();
            Amount curRVAmount = col.getNetRealisableAmount();
            Amount fsvAmt = col.getFSV();
			Amount reserveAmt = null;
			Amount returnAmt = curRVAmount;   // set current RV Amount
			if ((manualVal != null) && (manualVal.getReservePrice() != null)
					&& (manualVal.getReservePrice().getAmount() >= 0)
					&& (cmvAmt != null)) {
				reserveAmt = AmountConversion.getConversionAmount(manualVal
						.getReservePrice(), cmvAmt.getCurrencyCode());
			}

            // compare FSV
            if (fsvAmt != null) {
                if ((returnAmt == null) || ( (returnAmt != null)
                        && (fsvAmt.getAmount() < returnAmt.getAmount()) )) {
                    returnAmt = fsvAmt;
                }
            }

            // compare Reserver Amount (manual valuation)
            if (reserveAmt != null) {
                if ((returnAmt == null) || ( (returnAmt != null)
                        && (reserveAmt.getAmount() < returnAmt.getAmount()) )) {
                    returnAmt = reserveAmt;
                }
            }

            // compare Realisable Amount
            Amount realisableAmt = col.getReservePrice();
            if (realisableAmt != null) {
                if ((returnAmt == null) || ( (returnAmt != null)
                        && (realisableAmt.getAmount() < returnAmt.getAmount()) )) {
                    returnAmt = realisableAmt;
                }
            }

			return returnAmt;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

    public static List calculateAccountRV(Amount securityRV, List accountList) {
		try {
			if ((securityRV != null) && (accountList != null)) {
				double initVal = securityRV.getAmount();
				System.out.println("INIT RV *******: " + initVal);
				for (int i = 0; i < accountList.size(); i++) {
					AccountRVDetail nextRv = (AccountRVDetail) (accountList
							.get(i));
					// if (nextRv.isAccountNPL())
					// {
					double curRv = 0;
					Amount chargeAmt = nextRv.getChargeAmt();
					Amount outstdAmt = nextRv.getOutstandingAmt();
					Amount curOutstdRV = nextRv.getCurRvValue();
					System.out.println("chargeAmt ******** : " + chargeAmt
							+ "   outstdAmt ********* : " + outstdAmt
							+ "   curRV ******** : " + curOutstdRV);
					if ((chargeAmt != null) && (outstdAmt != null)) {
						if (i == (accountList.size() - 1)) {
							curRv = initVal;
						} else {
							double minAmt = Math.min(chargeAmt.getAmount(),
									Math.max(outstdAmt.getAmount()
											- curOutstdRV.getAmount(), 0));
							if (initVal >= minAmt) {
								curRv = minAmt;
							} else {
								curRv = initVal;
							}
						}

					}
//					System.out.println("Cur RV for account : "+ nextRv.getAccountId() + " is: " + curRv);
					nextRv.setRealizableValue(new Amount(curRv, securityRV
							.getCurrencyCode()));
					nextRv.setCurRvValue(new Amount(curRv
							+ curOutstdRV.getAmount(), securityRV
							.getCurrencyCode()));
					initVal = initVal - curRv;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return accountList;
	}
}
