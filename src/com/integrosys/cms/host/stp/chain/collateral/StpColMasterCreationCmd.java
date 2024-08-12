package com.integrosys.cms.host.stp.chain.collateral;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateral.bus.*;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.OBAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.OBPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.OBSpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold.OBSpecificChargeGold;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.ISpecificChargePlant;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle.ISpecificChargeVehicle;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.vessel.OBVessel;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashcash.OBCashCash;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.OBCashFd;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.repo.OBRepo;
import com.integrosys.cms.app.collateral.bus.type.document.OBDocumentCollateral;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.leaseagreement.OBLeaseAgreement;
import com.integrosys.cms.app.collateral.bus.type.guarantee.OBGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondsforeign.OBBondsForeign;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondslocal.OBBondsLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.govtforeigndiffccy.OBGovtForeignDiffCurrency;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.govtforeignsameccy.OBGovtForeignSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.govtlocal.OBGovtLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexforeign.OBMainIndexForeign;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexlocal.OBMainIndexLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.nonlistedlocal.OBNonListedLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedforeign.OBOtherListedForeign;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.OBOtherListedLocal;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.IForexDao;
import com.integrosys.cms.app.feed.bus.forex.OBForexFeedEntry;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CountryList;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 5, 2008
 * Time: 12:01:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class StpColMasterCreationCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private IForexDao forexDao;

    public IForexDao getForexDao() {
        return forexDao;
    }

    public void setForexDao(IForexDao forexDao) {
        this.forexDao = forexDao;
    }

    public boolean execute(Map ctx) throws Exception {
        HashMap addHdrField = new HashMap();

        OBCollateralTrxValue obCollateralTrxValue = (OBCollateralTrxValue) ctx.get(COL_TRX_VALUE);
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);

        ctx.put(FIELD_VAL_MAP, addHdrField);
        ctx.put(REF_NUM, Long.toString(obCollateralTrxValue.getCollateral().getCollateralID()));
        ctx.put(STP_TRANS_MAP, StpCommandUtil.getTrxMap(obStpMasterTrans));
        OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

        if (obStpTrans != null) {
            String securityType = obCollateralTrxValue.getCollateral().getCollateralType().getTypeCode();
            String securitySubType = obCollateralTrxValue.getCollateral().getCollateralSubType().getSubTypeCode();
            //put the OB into an array of object
            ArrayList aObject = new ArrayList();

            //Andy Wong: code cleanup, remove obsolete codes
            aObject.add(obCollateralTrxValue.getCollateral());
            if (securityType.equals(ICMSConstant.SECURITY_TYPE_ASSET)) {
                aObject = mapABtoArrayList(obCollateralTrxValue, securitySubType, aObject);
            } else if (securityType.equals(ICMSConstant.SECURITY_TYPE_CASH)) {
                aObject = mapCStoArrayList(obCollateralTrxValue, securitySubType, aObject);
            } else if (securityType.equals(ICMSConstant.SECURITY_TYPE_MARKETABLE)) {
                aObject = mapMStoArrayList(obCollateralTrxValue, securitySubType, aObject);
            }

            OBCollateralPledgor[] obCollateralPledgor = (OBCollateralPledgor[]) obCollateralTrxValue.getCollateral().getPledgors();
            // Andy Wong: validate if pledgor available
            if (obCollateralPledgor.length > 0) {
                aObject.add(obCollateralPledgor[0]);
            }

            String cty = obCollateralTrxValue.getCollateral().getCollateralLocation();
            ICollateralProxy proxy = CollateralProxyFactory.getProxy();
            OBCollateralParameter obCollateralParameter = (OBCollateralParameter) proxy.getCollateralParameter(cty, securitySubType);
            if (obCollateralParameter != null) {
                aObject.add(obCollateralParameter);
            }

            addHdrField = preProcess(addHdrField, obCollateralTrxValue);

            OBCollateralSubType obCollateralSubType = (OBCollateralSubType) obCollateralTrxValue.getCollateral().getCollateralSubType();
            //Andy Wong, 26 Feb 2009: map collateral desc to make and model for VEH collateral
            if (getVehColCodes().containsKey(obCollateralTrxValue.getCollateral().getSourceSecuritySubType())) {
                if (StringUtils.equals(securitySubType, ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
                    ISpecificChargeVehicle obSpecificChargeVehicle = (ISpecificChargeVehicle) obCollateralTrxValue.getCollateral();
                    addHdrField.put(MSG_COL_DESC, getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.VEHICLE_BRAND, obSpecificChargeVehicle.getBrand())
                            + " " + getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.ASSET_MODEL_TYPE, obSpecificChargeVehicle.getModelNo()));

                } else if (StringUtils.equals(securitySubType, ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
                    ISpecificChargePlant obSpecificChargePlant = (ISpecificChargePlant) obCollateralTrxValue.getCollateral();
                    addHdrField.put(MSG_COL_DESC, getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.VEHICLE_BRAND, obSpecificChargePlant.getBrand())
                            + " " + getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.ASSET_MODEL_TYPE, obSpecificChargePlant.getModelNo()));
                }
            } else if (obCollateralSubType != null) {
                aObject.add(obCollateralSubType);
            }

            aObject.add(addHdrField);

            //map to field OB
            List stpList = prepareRequest(ctx, aObject.toArray(), obStpTrans.getTrxType(), ISTPMapper.COLLATERAL_PATH);

            // construct msg, send msg and decipher msg
            stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());

            //put required OB in context to be used in subsequent command
            ctx.put(STP_TRX_VALUE, obStpMasterTrans);
            ctx.put(COL_TRX_VALUE, obCollateralTrxValue);

            //map object to biz OB and update STP transaction
            boolean successUpd = processResponse(ctx, obStpTrans, stpList);
            if (!successUpd) {
                return true;
            } else {
                updateColWithoutTrx(ctx, obCollateralTrxValue);
                return false;
            }
        } else {
            return false;
        }
    }

    public HashMap preProcess(HashMap xmlHashMap, OBCollateralTrxValue obCollateralTrxValue) throws Exception {
        String secType = obCollateralTrxValue.getCollateral().getCollateralType().getTypeCode();
        String secSubType = obCollateralTrxValue.getCollateral().getCollateralSubType().getSubTypeCode();
        boolean hvIns = false;
        double interestRate;
        DateFormat dateFormat = new SimpleDateFormat(PropertyManager.getValue(STP_DATE_PATTERN));
        String strValue = null;

        if (secType.equals(ICMSConstant.SECURITY_TYPE_OTHERS)) {
            xmlHashMap.put(MSG_NO_UNIT_FIELD, "1");
        }

        IInsurancePolicy[] obInsurancePolicy = obCollateralTrxValue.getCollateral().getInsurancePolicies();
        for (int i = 0; i < obInsurancePolicy.length; i++) {
            if (!StringUtils.equalsIgnoreCase(ICMSConstant.STATE_DELETED, obInsurancePolicy[i].getStatus())) {
                hvIns = true;
                break;
            }
        }
        if (hvIns) {
            xmlHashMap.put(MSG_INS_FLAG_FIELD, "Y");
        } else {
            xmlHashMap.put(MSG_INS_FLAG_FIELD, "N");
        }

        if (secType.equals(ICMSConstant.SECURITY_TYPE_PROPERTY)) {
            OBPropertyCollateral obPropertyCollateral = (OBPropertyCollateral) obCollateralTrxValue.getCollateral();
            if (obPropertyCollateral.getCollateralMaturityDate() != null) {
                xmlHashMap.put(MSG_EXPIRY_DATE_FIELD, dateFormat.format(obPropertyCollateral.getCollateralMaturityDate()));
            }
            if (StringUtils.isNotBlank(obPropertyCollateral.getPropertyUsage()) && (obPropertyCollateral.getPropertyUsage().equals("O01") || obPropertyCollateral.getPropertyUsage().equals("O02"))) {
                xmlHashMap.put(MSG_PROPERTY_USAGE_FIELD, "Y");
            } else {
                xmlHashMap.put(MSG_PROPERTY_USAGE_FIELD, "N");
            }
            if (obPropertyCollateral.getSalePurchaseValue() != null) {
                if (obPropertyCollateral.getSalePurchaseValue().getAmount() > 0) {
                    BigDecimal bigDecimal = new BigDecimal(obPropertyCollateral.getSalePurchaseValue().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    strValue = bigDecimal.toString();
                    xmlHashMap.put(MSG_PUR_PRICE_FIELD, strValue);
                }
            }

            if (StringUtils.equals(obPropertyCollateral.getTitleType(), "M")) {
                xmlHashMap.put(MSG_TITLE_TYPE_FIELD, obPropertyCollateral.getTitleType());
            } else {
                xmlHashMap.put(MSG_TITLE_TYPE_FIELD, "I");
            }

            if (StringUtils.isNotBlank(obPropertyCollateral.getTitleNumberPrefix()) && StringUtils.isNotBlank(obPropertyCollateral.getTitleNumber())) {
                xmlHashMap.put("CCDTTL", obPropertyCollateral.getTitleNumberPrefix() + "" + obPropertyCollateral.getTitleNumber());
            }
            if (obPropertyCollateral.getPriCaveatGuaranteeDate() != null) {
                xmlHashMap.put("D8CCPCD6", dateFormat.format(obPropertyCollateral.getPriCaveatGuaranteeDate()));
            }
            if (obPropertyCollateral.getChattelSoldDate() != null) {
                xmlHashMap.put("D8CCCSD6", dateFormat.format(obPropertyCollateral.getChattelSoldDate()));
            }
            if (obPropertyCollateral.getAuctionPrice() != null) {
                if (obPropertyCollateral.getAuctionPrice().getAmount() > 0) {
                    BigDecimal bigDecimal = new BigDecimal(obPropertyCollateral.getAuctionPrice().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    strValue = bigDecimal.toString();
                    xmlHashMap.put("CCAUTP", strValue);
                }
            }
            if (obPropertyCollateral.getAuctionDate() != null) {
                xmlHashMap.put("D8CCAUT6", dateFormat.format(obPropertyCollateral.getAuctionDate()));
            }

            //Andy Wong, 16 Jan 2009: manual map auctioneer field
            if (StringUtils.isNotBlank(obPropertyCollateral.getAuctioneer())) {
                xmlHashMap.put("CCAUTN", obPropertyCollateral.getAuctioneer());
            }

            //Andy Wong, 10 Feb 2009: append lotNumberPrefix and lotNo as lotNo field in Sibs
            if (StringUtils.isNotBlank(obPropertyCollateral.getLotNumberPrefix())
                    && StringUtils.isNotBlank(obPropertyCollateral.getLotNo())) {
                xmlHashMap.put(MSG_PROP_LOT_NO, obPropertyCollateral.getLotNumberPrefix() + "" + obPropertyCollateral.getLotNo());
            }

            //Andy Wong, 10 March 2009: append UOM with land area value as CCLAND field
            if (StringUtils.isNotBlank(obPropertyCollateral.getLandAreaUOM())
                    && obPropertyCollateral.getLandArea() > 0) {
                xmlHashMap.put(MSG_LAND_AREA, obPropertyCollateral.getLandArea() + "" + getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.AREA_UOM, obPropertyCollateral.getLandAreaUOM()));
            }

            //Andy Wong, 1 July 2009: append UOM with built up area value as CCBUAR field
            if (StringUtils.isNotBlank(obPropertyCollateral.getBuiltupAreaUOM())
                    && obPropertyCollateral.getBuiltupArea() > 0) {
                xmlHashMap.put(MSG_BUILT_UP_AREA, obPropertyCollateral.getBuiltupArea() + "" + getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.AREA_UOM, obPropertyCollateral.getBuiltupAreaUOM()));
            }

            //Andy Wong, 10 March 2009: map property tenure
            if (obPropertyCollateral.getTenure() > 0) {
                xmlHashMap.put(MSG_TENURE_PERIOD, obPropertyCollateral.getTenure() + "");
            }
        } else {
            xmlHashMap.put(MSG_PROPERTY_USAGE_FIELD, "N");
        }

        if (secType.equals(ICMSConstant.SECURITY_TYPE_MARKETABLE)) {
            OBMarketableCollateral obMarketableCollateral = (OBMarketableCollateral) obCollateralTrxValue.getCollateral();
            if (obMarketableCollateral.getInterestRate() > 0) {
//                interestRate = obMarketableCollateral.getInterestRate() / 100;
                BigDecimal bigDecimal = new BigDecimal(obMarketableCollateral.getInterestRate()).
                        setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
                xmlHashMap.put(MSG_INT_RATE_FIELD, bigDecimal.toString());
            }
        }
        if (secType.equals(ICMSConstant.SECURITY_TYPE_MARKETABLE) && secSubType.equals(ICMSConstant.COLTYPE_MAR_NONLISTED_LOCAL)) {
            xmlHashMap.put("CCDSID", "UNQUOTED");
        } else if (secType.equals(ICMSConstant.SECURITY_TYPE_MARKETABLE)) {
            OBMarketableCollateral obMarketableCollateral = (OBMarketableCollateral) obCollateralTrxValue.getCollateral();
            OBMarketableEquity[] obMarketableEquity = (OBMarketableEquity[]) obMarketableCollateral.getEquityList();
            if (obMarketableEquity != null && obMarketableEquity.length > 0) {
                if (StringUtils.isNotBlank(obMarketableEquity[0].getStockCode())) {
                    xmlHashMap.put("CCDSID", obMarketableEquity[0].getStockCode());
                }
            }
        }
        if (secType.equals(ICMSConstant.SECURITY_TYPE_MARKETABLE) && (secSubType.equals(ICMSConstant.COLTYPE_MAR_BONDS_LOCAL) || secSubType.equals(ICMSConstant.COLTYPE_MAR_BONDS_FOREIGN))) {
            OBMarketableCollateral obMarketableCollateral = (OBMarketableCollateral) obCollateralTrxValue.getCollateral();
            OBMarketableEquity[] obMarketableEquity = (OBMarketableEquity[]) obMarketableCollateral.getEquityList();
            if (obMarketableEquity != null && obMarketableEquity.length > 0) {
                if (StringUtils.isNotBlank(obMarketableEquity[0].getCertificateNo())) {
                    xmlHashMap.put(MSG_REG_NUM_FIELD, obMarketableEquity[0].getCertificateNo());
                    xmlHashMap.put(MSG_BOND_NO, obMarketableEquity[0].getCertificateNo());
                }
                if (StringUtils.isNotBlank(obMarketableEquity[0].getIssuerName())) {
                    xmlHashMap.put(MSG_BENEFIT_NAME, obMarketableEquity[0].getIssuerName());
                }
                if (obMarketableEquity[0].getBondMaturityDate() != null) {
                    xmlHashMap.put(MSG_BOND_MATURE_DATE, dateFormat.format(obMarketableEquity[0].getBondMaturityDate()));
                }
            }
        }

        if (secType.equals(ICMSConstant.SECURITY_TYPE_ASSET)) {
            xmlHashMap.put(MSG_PN_CHEQUIE_FIELD, "P");

            if (secSubType.equals(ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE)) {
                OBAssetPostDatedCheque obAssetPostDatedCheque = (OBAssetPostDatedCheque) obCollateralTrxValue.getCollateral();
                if (obAssetPostDatedCheque.getInterestRate() > 0) {
                    BigDecimal bigDecimal = new BigDecimal(obAssetPostDatedCheque.getInterestRate()).
                            setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
                    xmlHashMap.put(MSG_INT_RATE_FIELD, bigDecimal.toString());
                }
                OBPostDatedCheque[] obPostDatedCheque = (OBPostDatedCheque[]) obAssetPostDatedCheque.getPostDatedCheques();
                if (obPostDatedCheque != null && obPostDatedCheque.length > 0) {
                    xmlHashMap.put(MSG_SEC_ISSUER_FIELD, obPostDatedCheque[0].getIssuerName());
                }
                if (obAssetPostDatedCheque.getPriCaveatGuaranteeDate() != null) {
                    xmlHashMap.put("D8CCPCD6", dateFormat.format(obAssetPostDatedCheque.getPriCaveatGuaranteeDate()));
                }
                xmlHashMap.put(MSG_PN_CHEQUIE_FIELD, "B");

            } else if (secSubType.equals(ICMSConstant.COLTYPE_ASSET_VESSEL)) {
                OBVessel obVessel = (OBVessel) obCollateralTrxValue.getCollateral();
                if (StringUtils.isNotBlank(obVessel.getRegCountry())) {
                    xmlHashMap.put(MSG_PORT_REG_FIELD, CountryList.getInstance().getCountryName(obVessel.getRegCountry()));
                }
                if (StringUtils.isNotBlank(obVessel.getModelNo())) {
                    if (StringUtils.isNotBlank(getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.ASSET_MODEL_TYPE, obVessel.getModelNo()))) {
                        xmlHashMap.put(MSG_MODEL_NUMBER_FIELD, getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.ASSET_MODEL_TYPE, obVessel.getModelNo()));
                    }
                }
                if (StringUtils.isNotBlank(obVessel.getBrand())) {
                    if (StringUtils.isNotBlank(getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.VEHICLE_BRAND, obVessel.getBrand()))) {
                        xmlHashMap.put(MSG_MAKE_GOOD_FIELD, getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.VEHICLE_BRAND, obVessel.getBrand()));
                    }
                }
                if (obVessel.getChattelSoldDate() != null) {
                    xmlHashMap.put("D8CCCSD6", dateFormat.format(obVessel.getChattelSoldDate()));
                }

            } else if (secSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
                ISpecificChargeVehicle obSpecificChargeVehicle = (ISpecificChargeVehicle) obCollateralTrxValue.getCollateral();
                if (StringUtils.isNotBlank(obSpecificChargeVehicle.getChassisNumber())) {
                    xmlHashMap.put(MSG_CHA_NUM_FIELD, obSpecificChargeVehicle.getChassisNumber());
                }
                if (StringUtils.isNotBlank(obSpecificChargeVehicle.getRegistrationNo())) {
                    xmlHashMap.put(MSG_REG_NUM_FIELD, obSpecificChargeVehicle.getRegistrationNo());
                }
                if (obSpecificChargeVehicle.getDptradein() != null) {
                    if (obSpecificChargeVehicle.getDptradein().getAmount() > 0) {
                        BigDecimal bigDecimal = new BigDecimal(obSpecificChargeVehicle.getDptradein().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        strValue = bigDecimal.toString();
                        xmlHashMap.put("CCDPTE", strValue);
                    }
                }
                if (obSpecificChargeVehicle.getDpcash() != null) {
                    if (obSpecificChargeVehicle.getDpcash().getAmount() > 0) {
                        BigDecimal bigDecimal = new BigDecimal(obSpecificChargeVehicle.getDpcash().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        strValue = bigDecimal.toString();
                        xmlHashMap.put("CCDPCS", strValue);
                    }
                }
                if (StringUtils.isNotBlank(obSpecificChargeVehicle.getModelNo())) {
                    if (StringUtils.isNotBlank(getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.ASSET_MODEL_TYPE, obSpecificChargeVehicle.getModelNo()))) {
                        xmlHashMap.put(MSG_MODEL_NUMBER_FIELD, getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.ASSET_MODEL_TYPE, obSpecificChargeVehicle.getModelNo()));
                    }
                }
                if (StringUtils.isNotBlank(obSpecificChargeVehicle.getBrand())) {
                    if (StringUtils.isNotBlank(getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.VEHICLE_BRAND, obSpecificChargeVehicle.getBrand()))) {
                        xmlHashMap.put(MSG_MAKE_GOOD_FIELD, getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.VEHICLE_BRAND, obSpecificChargeVehicle.getBrand()));
                    }
                }
                if (obSpecificChargeVehicle.getPurchasePrice() != null) {
                    if (obSpecificChargeVehicle.getPurchasePrice().getAmount() > 0) {
                        BigDecimal bigDecimal = new BigDecimal(obSpecificChargeVehicle.getPurchasePrice().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        strValue = bigDecimal.toString();
                        xmlHashMap.put(MSG_PUR_PRICE_FIELD, strValue);
                    }
                }
                if (obSpecificChargeVehicle.getChattelSoldDate() != null) {
                    xmlHashMap.put("D8CCCSD6", dateFormat.format(obSpecificChargeVehicle.getChattelSoldDate()));
                }
                if (getVehColCodes().containsKey(obSpecificChargeVehicle.getSourceSecuritySubType())) {
                    if (obSpecificChargeVehicle.getInvoiceDate() != null) {
                        xmlHashMap.put(MSG_CONTRACT_DATE, dateFormat.format(obSpecificChargeVehicle.getInvoiceDate()));
                    }

                    //Andy Wong, 22 Feb 2009: map vehicle price list amount
                    if (obSpecificChargeVehicle.getPlist() != null && obSpecificChargeVehicle.getPlist().getAmount() > 0) {
                        BigDecimal bigDecimal = new BigDecimal(obSpecificChargeVehicle.getPlist().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        strValue = bigDecimal.toString();
                        xmlHashMap.put("CCDORG", strValue);
                    }
                }

            } else if (secSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
                ISpecificChargePlant obSpecificChargePlant = (ISpecificChargePlant) obCollateralTrxValue.getCollateral();
                if (StringUtils.isNotBlank(obSpecificChargePlant.getSerialNumber())) {
                    xmlHashMap.put(MSG_CHA_NUM_FIELD, obSpecificChargePlant.getSerialNumber());
                }
                if (StringUtils.isNotBlank(obSpecificChargePlant.getRegistrationNo())) {
                    xmlHashMap.put(MSG_REG_NUM_FIELD, obSpecificChargePlant.getRegistrationNo());
                }
                if (obSpecificChargePlant.getDptradein() != null) {
                    if (obSpecificChargePlant.getDptradein().getAmount() > 0) {
                        BigDecimal bigDecimal = new BigDecimal(obSpecificChargePlant.getDptradein().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        strValue = bigDecimal.toString();
                        xmlHashMap.put("CCDPTE", strValue);
                    }
                }
                if (obSpecificChargePlant.getDpcash() != null) {
                    if (obSpecificChargePlant.getDpcash().getAmount() > 0) {
                        BigDecimal bigDecimal = new BigDecimal(obSpecificChargePlant.getDpcash().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        strValue = bigDecimal.toString();
                        xmlHashMap.put("CCDPCS", strValue);
                    }
                }
                if (StringUtils.isNotBlank(obSpecificChargePlant.getModelNo())) {
                    if (StringUtils.isNotBlank(getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.ASSET_MODEL_TYPE, obSpecificChargePlant.getModelNo()))) {
                        xmlHashMap.put(MSG_MODEL_NUMBER_FIELD, getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.ASSET_MODEL_TYPE, obSpecificChargePlant.getModelNo()));
                    }
                }
                if (StringUtils.isNotBlank(obSpecificChargePlant.getBrand())) {
                    if (StringUtils.isNotBlank(getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.VEHICLE_BRAND, obSpecificChargePlant.getBrand()))) {
                        xmlHashMap.put(MSG_MAKE_GOOD_FIELD, getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.VEHICLE_BRAND, obSpecificChargePlant.getBrand()));
                    }
                }
                if (obSpecificChargePlant.getPurchasePrice() != null) {
                    if (obSpecificChargePlant.getPurchasePrice().getAmount() > 0) {
                        BigDecimal bigDecimal = new BigDecimal(obSpecificChargePlant.getPurchasePrice().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        strValue = bigDecimal.toString();
                        xmlHashMap.put(MSG_PUR_PRICE_FIELD, strValue);
                    }
                }
                if (obSpecificChargePlant.getChattelSoldDate() != null) {
                    xmlHashMap.put("D8CCCSD6", dateFormat.format(obSpecificChargePlant.getChattelSoldDate()));
                }

                if (getVehColCodes().containsKey(obSpecificChargePlant.getSourceSecuritySubType())) {
                    //Andy Wong, 9 Feb 2009: dont send contract/invoice date for plant equiptment subtype, CMS not sending expiry date
//                xmlHashMap.put("D8CCCDT6", dateFormat.format(obSpecificChargePlant.getInvoiceDate()));
                    //Andy Wong, 22 Feb 2009: map plants equipment price list amount
                    if (obSpecificChargePlant.getPlist() != null && obSpecificChargePlant.getPlist().getAmount() > 0) {
                        BigDecimal bigDecimal = new BigDecimal(obSpecificChargePlant.getPlist().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        strValue = bigDecimal.toString();
                        xmlHashMap.put("CCDORG", strValue);
                    }
                }

            } else if (secSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_GOLD)) {
                OBSpecificChargeGold obSpecificChargeGold = (OBSpecificChargeGold) obCollateralTrxValue.getCollateral();
                if (obSpecificChargeGold.getAuctionPrice() != null) {
                    if (obSpecificChargeGold.getAuctionPrice().getAmount() > 0) {
                        BigDecimal bigDecimal = new BigDecimal(obSpecificChargeGold.getAuctionPrice().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        strValue = bigDecimal.toString();
                        xmlHashMap.put("CCAUTP", strValue);
                        if (obSpecificChargeGold.getAuctionDate() != null) {
                            xmlHashMap.put("D8CCAUT6", dateFormat.format(obSpecificChargeGold.getAuctionDate()));
                        }
                    }
                }

                //Andy Wong, 16 Jan 2009: manual map auctioneer field
                if (StringUtils.isNotBlank(obSpecificChargeGold.getAuctioneer())) {
                    xmlHashMap.put("CCAUTN", obSpecificChargeGold.getAuctioneer());
                }

                if (obSpecificChargeGold.getPurchasePrice() != null) {
                    if (obSpecificChargeGold.getPurchasePrice().getAmount() > 0) {
                        BigDecimal bigDecimal = new BigDecimal(obSpecificChargeGold.getPurchasePrice().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        strValue = bigDecimal.toString();
                        xmlHashMap.put(MSG_PUR_PRICE_FIELD, strValue);
                    }
                }

            } else if (secSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT)) {
                OBSpecificChargeAircraft obSpecificChargeAircraft = (OBSpecificChargeAircraft) obCollateralTrxValue.getCollateral();
                if (StringUtils.isNotBlank(obSpecificChargeAircraft.getModelNo())) {
                    if (StringUtils.isNotBlank(getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.ASSET_MODEL_TYPE, obSpecificChargeAircraft.getModelNo()))) {
                        xmlHashMap.put(MSG_MODEL_NUMBER_FIELD, getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.ASSET_MODEL_TYPE, obSpecificChargeAircraft.getModelNo()));
                    }
                }
                if (StringUtils.isNotBlank(obSpecificChargeAircraft.getBrand())) {
                    if (StringUtils.isNotBlank(getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.VEHICLE_BRAND, obSpecificChargeAircraft.getBrand()))) {
                        xmlHashMap.put(MSG_MAKE_GOOD_FIELD, getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.VEHICLE_BRAND, obSpecificChargeAircraft.getBrand()));
                    }
                }
                if (obSpecificChargeAircraft.getChattelSoldDate() != null) {
                    xmlHashMap.put("D8CCCSD6", dateFormat.format(obSpecificChargeAircraft.getChattelSoldDate()));
                }
            }
        }

        if (secType.equals(ICMSConstant.SECURITY_TYPE_GUARANTEE)) {
            OBGuaranteeCollateral obGuaranteeCollateral = (OBGuaranteeCollateral) obCollateralTrxValue.getCollateral();

            if (secSubType.equals(ICMSConstant.COLTYPE_GUARANTEE_SBLC_SAMECCY)) {
                if (StringUtils.isNotBlank(obGuaranteeCollateral.getIssuingBank())) {
                    xmlHashMap.put(MSG_SEC_ISSUER_FIELD, obGuaranteeCollateral.getIssuingBank());
                }
                if (obGuaranteeCollateral.getIssuingDate() != null) {
                    xmlHashMap.put(MSG_ISSUE_DATE, dateFormat.format(obGuaranteeCollateral.getIssuingDate()));
                }
                if (StringUtils.isNotBlank(obGuaranteeCollateral.getReferenceNo())) {
                    xmlHashMap.put(MSG_BOND_NO, obGuaranteeCollateral.getReferenceNo());
                }
                //Andy Wong, 10 Feb 2009: map beneficiary name to as issuer name
                if (StringUtils.isNotBlank(obGuaranteeCollateral.getBeneficiaryName())) {
                    xmlHashMap.put(MSG_BENEFIT_NAME, obGuaranteeCollateral.getBeneficiaryName());
                }
                if (obGuaranteeCollateral.getCollateralMaturityDate() != null) {
                    xmlHashMap.put(MSG_BOND_MATURE_DATE, dateFormat.format(obGuaranteeCollateral.getCollateralMaturityDate()));
                }
                if (obGuaranteeCollateral.getGuaranteeDate() != null) {
                    xmlHashMap.put(MSG_GUARANTEE_DATE, dateFormat.format(obGuaranteeCollateral.getGuaranteeDate()));
                }
                if (obGuaranteeCollateral.getReimbursementBankEntryCode() != null) {
                    xmlHashMap.put(MSG_REIMBURSE_BANK, obGuaranteeCollateral.getReimbursementBankEntryCode());
                }
            }
            xmlHashMap.put(MSG_ISSUE_INSTITUTE, obGuaranteeCollateral.getIssuingBank());

            if (obGuaranteeCollateral.getGuaranteeAmount() != null) {
                if (obGuaranteeCollateral.getGuaranteeAmount().getAmount() > 0) {
                    BigDecimal bigDecimal = new BigDecimal(obGuaranteeCollateral.getGuaranteeAmount().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    strValue = bigDecimal.toString();
                    xmlHashMap.put("CCGAMT", strValue);
                }
            }
            if (obGuaranteeCollateral.getCollateralMaturityDate() != null) {
                xmlHashMap.put(MSG_DEP_EXPIRY_DATE, dateFormat.format(obGuaranteeCollateral.getCollateralMaturityDate()));
            }
            if (obGuaranteeCollateral.getGuaranteeDate() != null) {
                xmlHashMap.put(MSG_CONTRACT_DATE, dateFormat.format(obGuaranteeCollateral.getGuaranteeDate()));
            }
        }

        if (secSubType.equals(ICMSConstant.COLTYPE_CASH_FD)) {
            OBCashFd obCashFd = (OBCashFd) obCollateralTrxValue.getCollateral();
            OBCashDeposit[] obCashDeposit = (OBCashDeposit[]) obCashFd.getDepositInfo();
            if (obCashDeposit != null && obCashDeposit.length > 0) {
                if (obCashDeposit[0].getOwnBank()) {
                    xmlHashMap.put(MSG_OWN_BANK_FIELD, "B");
                } else {
                    xmlHashMap.put(MSG_OWN_BANK_FIELD, "O");
                }
                if (obCashDeposit[0].getDepositMaturityDate() != null) {
                    xmlHashMap.put(MSG_DEP_EXPIRY_DATE, dateFormat.format(obCashDeposit[0].getDepositMaturityDate()));
                }
                //Andy Wong, 11 March 2009: map to issue date if available
                if (obCashDeposit[0].getIssueDate() != null) {
                    xmlHashMap.put(MSG_ISSUE_DATE, dateFormat.format(obCashDeposit[0].getIssueDate()));
                }
                //Andy Wong, 10 March 2009: map property tenure
                if (obCashDeposit[0].getTenure() > 0) {
                    xmlHashMap.put(MSG_TENURE_PERIOD, obCashDeposit[0].getTenure() + "");
                }
            }
            if (StringUtils.isNotBlank(obCashFd.getIssuer())) {
                xmlHashMap.put(MSG_ISSUE_INSTITUTE, obCashFd.getIssuer());
            }
        }

        if (secSubType.equals(ICMSConstant.COLTYPE_DOC_LEASE_AGREEMENT)) {
            OBLeaseAgreement obLeaseAgreement = (OBLeaseAgreement) obCollateralTrxValue.getCollateral();
            if (obLeaseAgreement.getGuranteeAmount() != null) {
                if (obLeaseAgreement.getGuranteeAmount().getAmount() > 0) {
                    BigDecimal bigDecimal = new BigDecimal(obLeaseAgreement.getGuranteeAmount().getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    strValue = bigDecimal.toString();
                    xmlHashMap.put("CCGAMT", strValue);
                }
            }
            if (obLeaseAgreement.getCollateralMaturityDate() != null) {
                xmlHashMap.put(MSG_DEP_EXPIRY_DATE, dateFormat.format(obLeaseAgreement.getCollateralMaturityDate()));
            }
        }

        if (secType.equals(ICMSConstant.SECURITY_TYPE_DOCUMENT)) {
            OBDocumentCollateral obDocumentCollateral = (OBDocumentCollateral) obCollateralTrxValue.getCollateral();
            if (obDocumentCollateral.getContractDate() != null) {
                xmlHashMap.put(MSG_CONTRACT_DATE, dateFormat.format(obDocumentCollateral.getContractDate()));
            }
        }

        String valuerCode = obCollateralTrxValue.getCollateral().getValuer();
        if (StringUtils.isBlank(valuerCode)
                && obCollateralTrxValue.getCollateral().getLastRemarginDate() != null) {
            //Andy Wong, 6 Feb 2009: default valuer code when valuation date available, complying SIBS validation
            valuerCode = PropertyManager.getValue(DEFAULT_VALUER);
        }
        if (StringUtils.isNotBlank(valuerCode)) {
            xmlHashMap.put("CCDAPP", getStpTransBusManager().getCodeCategoryEntry(CategoryCodeConstant.VALUER, valuerCode));
        }

        if (StringUtils.isNotBlank(obCollateralTrxValue.getCollateral().getCurrencyCode())) {
            OBForexFeedEntry obForexFeedEntry = (OBForexFeedEntry) getForexDao().getForexFeedByCurrencyCode(IForexDao.ACTUAL_FOREX_FEED_ENTRY_ENTITY_NAME, obCollateralTrxValue.getCollateral().getCurrencyCode());
            //Andy Wong: feed entry might not available causing null pointer
            BigDecimal bigDecimal = obForexFeedEntry != null ? obForexFeedEntry.getBuyRate().setScale(9, BigDecimal.ROUND_HALF_UP) : new BigDecimal(0);
            strValue = bigDecimal.toString();

            xmlHashMap.put(MSG_EX_RATE_FIELD, strValue);
            xmlHashMap.put(MSG_CON_RATE_FIELD, strValue);
            xmlHashMap.put(MSG_EXC_RATE_FIELD, strValue);
        }

        List iList = getStpTransBusManager().getTotalAmountSold(new Long(obCollateralTrxValue.getCollateral().getCollateralID()));
        if (iList != null) {
            xmlHashMap.put(MSG_AMT_SOLD_FIELD, iList.get(0));
        }

        //Andy Wong, 11 March 2009: if system valuation, omv=0, default to 1
        if (obCollateralTrxValue.getCollateral().getCMV() != null
                && obCollateralTrxValue.getCollateral().getCMV().getAmount() <= 0
                && StringUtils.equals(obCollateralTrxValue.getCollateral().getValuationType(), "A")) {
            xmlHashMap.put(MSG_APPRAISED_VALUE, "1");
            xmlHashMap.put(MSG_CUR_MARKET_VALUE, "1");
        }
        return xmlHashMap;
    }

    public boolean processResponse(Map ctx, OBStpTrans obStpTrans, List obStpFieldL) throws Exception {
        boolean successStp = super.processResponse(ctx, obStpTrans, obStpFieldL);
        if (successStp) {
            OBCollateralTrxValue obCollateralTrxValue = (OBCollateralTrxValue) ctx.get(COL_TRX_VALUE);
            OBCollateral obActCollateral = (OBCollateral) obCollateralTrxValue.getCollateral();
            obActCollateral = (OBCollateral) getStpMapper().mapToBizOB(obActCollateral, obStpFieldL);

            OBCollateral obStgCollateral = (OBCollateral) obCollateralTrxValue.getStagingCollateral();
            obStgCollateral = (OBCollateral) getStpMapper().mapToBizOB(obStgCollateral, obStpFieldL);

            //Andy Wong, 10 March 2009: map deposit issue date for FD subtype
            if (StringUtils.equals(ICMSConstant.COLTYPE_CASH_FD, obActCollateral.getCollateralSubType().getSubTypeCode())) {
                OBCashFd obCashFd = (OBCashFd) obActCollateral;
                OBCashFd obStgCashFd = (OBCashFd) obStgCollateral;
                OBCashDeposit[] obCashDeposit = (OBCashDeposit[]) obCashFd.getDepositInfo();
                OBCashDeposit[] obStgCashDeposit = (OBCashDeposit[]) obStgCashFd.getDepositInfo();
                Map responseMsgMap = StpCommandUtil.getResponseMsgMap(obStpFieldL);

                int checkDate = Integer.parseInt((String) responseMsgMap.get(MSG_ISSUE_DATE));
                if (checkDate > 0 && !ArrayUtils.isEmpty(obCashDeposit)) {
                    String strDate = ((String) responseMsgMap.get(MSG_ISSUE_DATE)).substring(1, 9);
                    DateFormat dateFormat = new SimpleDateFormat(PropertyManager.getValue(STP_DATE_PATTERN));
                    Date date = dateFormat.parse(strDate);
                    obCashDeposit[0].setIssueDate(date);
                    obStgCashDeposit[0].setIssueDate(date);
                }
            }

            obCollateralTrxValue.setCollateral(obActCollateral);
            obCollateralTrxValue.setStagingCollateral(obStgCollateral);
            ctx.put(COL_TRX_VALUE, obCollateralTrxValue);
        }
        return successStp;
    }

    public ArrayList mapABtoArrayList(OBCollateralTrxValue obCollateralTrxValue, String subType, ArrayList aObject) {
        //Andy Wong: clean up obsolete code
        if (subType.equals(ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE)) {
            OBAssetPostDatedCheque obAssetPostDatedCheque = (OBAssetPostDatedCheque) obCollateralTrxValue.getCollateral();
            OBPostDatedCheque[] obPostDatedCheque = (OBPostDatedCheque[]) obAssetPostDatedCheque.getPostDatedCheques();
            if (obPostDatedCheque != null && obPostDatedCheque.length > 0) {
                aObject.add(obPostDatedCheque[0]);
            }
        }
        return aObject;
    }

    public ArrayList mapCStoArrayList(OBCollateralTrxValue obCollateralTrxValue, String subType, ArrayList aObject) {
        OBCashDeposit[] obCashDeposit = null;

        if (subType.equals(ICMSConstant.COLTYPE_CASH_CASH)) {
            OBCashCash obCashCash = (OBCashCash) obCollateralTrxValue.getCollateral();
            obCashDeposit = (OBCashDeposit[]) obCashCash.getDepositInfo();
            aObject.add(obCashCash);
        } else if (subType.equals(ICMSConstant.COLTYPE_CASH_FD)) {
            OBCashFd obCashFd = (OBCashFd) obCollateralTrxValue.getCollateral();
            obCashDeposit = (OBCashDeposit[]) obCashFd.getDepositInfo();
            aObject.add(obCashFd);
        } else if (subType.equals(ICMSConstant.COLTYPE_CASH_REPO)) {
            OBRepo obRepo = (OBRepo) obCollateralTrxValue.getCollateral();
            obCashDeposit = (OBCashDeposit[]) obRepo.getDepositInfo();
            aObject.add(obRepo);
        }
        if (obCashDeposit != null && obCashDeposit.length > 0) {
            aObject.add(obCashDeposit[0]);
        }
        return aObject;
    }

    public ArrayList mapMStoArrayList(OBCollateralTrxValue obCollateralTrxValue, String subType, ArrayList aObject) {
        OBMarketableEquity[] obMarketableEquity = null;

        if (subType.equals(ICMSConstant.COLTYPE_MAR_MAIN_IDX_LOCAL)) {
            OBMainIndexLocal obMainIndexLocal = (OBMainIndexLocal) obCollateralTrxValue.getCollateral();
            obMarketableEquity = (OBMarketableEquity[]) obMainIndexLocal.getEquityList();
        } else if (subType.equals(ICMSConstant.COLTYPE_MAR_MAIN_IDX_FOREIGN)) {
            OBMainIndexForeign obMainIndexForeign = (OBMainIndexForeign) obCollateralTrxValue.getCollateral();
            obMarketableEquity = (OBMarketableEquity[]) obMainIndexForeign.getEquityList();
        } else if (subType.equals(ICMSConstant.COLTYPE_MAR_GOVT_LOCAL)) {
            OBGovtLocal obGovtLocal = (OBGovtLocal) obCollateralTrxValue.getCollateral();
            obMarketableEquity = (OBMarketableEquity[]) obGovtLocal.getEquityList();
        } else if (subType.equals(ICMSConstant.COLTYPE_MAR_GOVT_FOREIGN_SAMECCY)) {
            OBGovtForeignSameCurrency obGovtForeignSameCurrency = (OBGovtForeignSameCurrency) obCollateralTrxValue.getCollateral();
            obMarketableEquity = (OBMarketableEquity[]) obGovtForeignSameCurrency.getEquityList();
        } else if (subType.equals(ICMSConstant.COLTYPE_MAR_GOVT_FOREIGN_DIFFCCY)) {
            OBGovtForeignDiffCurrency obGovtForeignDiffCurrency = (OBGovtForeignDiffCurrency) obCollateralTrxValue.getCollateral();
            obMarketableEquity = (OBMarketableEquity[]) obGovtForeignDiffCurrency.getEquityList();
        } else if (subType.equals(ICMSConstant.COLTYPE_MAR_OTHERLISTED_LOCAL)) {
            OBOtherListedLocal obOtherListedLocal = (OBOtherListedLocal) obCollateralTrxValue.getCollateral();
            obMarketableEquity = (OBMarketableEquity[]) obOtherListedLocal.getEquityList();
        } else if (subType.equals(ICMSConstant.COLTYPE_MAR_OTHERLISTED_FOREIGN)) {
            OBOtherListedForeign obOtherListedForeign = (OBOtherListedForeign) obCollateralTrxValue.getCollateral();
            obMarketableEquity = (OBMarketableEquity[]) obOtherListedForeign.getEquityList();
        } else if (subType.equals(ICMSConstant.COLTYPE_MAR_NONLISTED_LOCAL)) {
            OBNonListedLocal obNonListedLocal = (OBNonListedLocal) obCollateralTrxValue.getCollateral();
            obMarketableEquity = (OBMarketableEquity[]) obNonListedLocal.getEquityList();
        } else if (subType.equals(ICMSConstant.COLTYPE_MAR_BONDS_LOCAL)) {
            OBBondsLocal obBondsLocal = (OBBondsLocal) obCollateralTrxValue.getCollateral();
            obMarketableEquity = (OBMarketableEquity[]) obBondsLocal.getEquityList();
        } else if (subType.equals(ICMSConstant.COLTYPE_MAR_BONDS_FOREIGN)) {
            OBBondsForeign obBondsForeign = (OBBondsForeign) obCollateralTrxValue.getCollateral();
            obMarketableEquity = (OBMarketableEquity[]) obBondsForeign.getEquityList();
        }
        if (obMarketableEquity != null && obMarketableEquity.length > 0) {
            aObject.add(obMarketableEquity[0]);
        }
        return aObject;
    }
}
