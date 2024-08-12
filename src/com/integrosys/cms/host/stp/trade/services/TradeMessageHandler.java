package com.integrosys.cms.host.stp.trade.services;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.host.eai.support.MessageDate;
import com.integrosys.cms.host.stp.STPHeader;
import com.integrosys.cms.host.stp.STPMessage;
import com.integrosys.cms.host.stp.STPMessageException;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.core.AbstractPostProcessMessageHandler;
import com.integrosys.cms.host.stp.support.ISTPHeaderConstant;
import com.integrosys.cms.host.stp.support.STPHeaderHelper;
import com.integrosys.cms.host.stp.trade.TradeBody;
import com.integrosys.cms.host.stp.trade.bus.OBTradeMasterTrans;
import com.integrosys.cms.host.stp.trade.support.OBTradeBody;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Message Handler to prepare the xml mapping resource and passed into the
 * message manager to process, in return a message object will be retrieved, and
 * possible to be marshall if it's a inquiry message.
 *
 * @author marvin
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 * @since 1.0
 */

public class TradeMessageHandler extends AbstractPostProcessMessageHandler {
    private final String TRADE_MESSAGE_TYPE = "TRADE";
    private final String TRADE_MESSAGE_ID = "T001";
    //private ISecurityDao securityDao;

    private Map messageIdMap;

    /*public ISecurityDao getSecurityDao() {
        return securityDao;
    }*/

    /*public void setSecurityDao(ISecurityDao securityDao) {
        this.securityDao = securityDao;
    }*/

    /**
     * <p>
     * Mapping between the security type and response message id.
     * <p>
     * Key is the security type, such AB, PT, etc. Value is the response message
     * id, such CO001R, CO002R
     * @param messageIdMap the map between security type code and
     *        response message id
     */
    public void setMessageIdMap(Map messageIdMap) {
        this.messageIdMap = messageIdMap;
    }

    public Map getMessageIdMap() {
        return messageIdMap;
    }

    protected void doPostProcessMessage(STPMessage message) {
    }

    private STPHeader preprareHeader(Map setting)throws Exception {
        return STPHeaderHelper.prepareHeader((String) setting.get(ISTPHeaderConstant.MESSAGE_TYPE),(String) setting.get(ISTPHeaderConstant.STPHDR_MESSAGE_NUMBER), setting);
    }

    private String formatDecimal(BigDecimal value, Locale locale, int decimalPlaces)throws StpCommonException{
        return formatDecimal(value, locale, decimalPlaces, 0);
    }

    private String formatDecimal(BigDecimal value, Locale locale, int decimalPlaces, int leadingDigit)throws StpCommonException{
        int actualLeadingDigit = leadingDigit - decimalPlaces;
        //if(leadingDigit > 0 && actualLeadingDigit <= 0){
        //    throw new StpCommonException();
        //}
        StringBuffer pattern = new StringBuffer();
        //if(leadingDigit >0){
           // for(int i =0; i < actualLeadingDigit; i++){
                pattern.append("#");
           // }
            pattern.append(".");
            for(int i =0; i < decimalPlaces; i++){
                pattern.append("0");
            }
       // }

        NumberFormat decimalFormat = NumberFormat.getCurrencyInstance();
       ((DecimalFormat) decimalFormat).applyPattern(pattern.toString());



        String result = decimalFormat.format(value.setScale(decimalPlaces, BigDecimal.ROUND_FLOOR));

        //String result = UIUtil.mapNumberObjectToString(value, locale, decimalPlaces);

        if(leadingDigit != 0){
            int totalLeadingDigit = result.indexOf(".");
            if(totalLeadingDigit > leadingDigit){
                throw new IllegalArgumentException("exceed leading digit");
            }
        }
        return result;
    }

    protected STPMessage doPostProcessMessage(Object obj)throws STPMessageException
    {
        Properties properties = PropertyManager.getProperty();

        Locale locale = (Locale) properties.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        STPMessage message = new STPMessage();
        if(obj instanceof OBTradeBody){
            System.out.println("object is OBTradeBody");
            OBTradeBody ob = (OBTradeBody) obj;

            TradeBody body = new TradeBody();
            body.setAccountNo(ob.getAccountNo());
            body.setApprovedDt(ob.getApprovedDt());

            body.setApprovedLimit(formatDecimal(ob.getApprovedLimit(), locale, 4, 30));
            body.setApprovedTenure(formatDecimal(ob.getApprovedTenure(), locale, 4, 30));
            body.setAutoPurgeFlag(ob.getAutoPurgeFlag());
            body.setCdtFileId(ob.getCdtFileId());
            body.setCgcFlag(ob.getCgcFlag());
            body.setCgcRefNo(ob.getCgcRefNo());
            body.setCgcSchema(ob.getCgcSchema());
            body.setCommissionRate(formatDecimal(ob.getCommissionRate(), locale, 9, 18));
            body.setCreateBy(ob.getCreateBy());
            body.setCreatedDt(ob.getCreatedDt());
            body.setDelFlag(ob.getDelFlag());
            body.setExpiryDt(ob.getExpiryDt());
            body.setFirstLineFacilityId(ob.getFirstLineFacilityId());
            body.setId(ob.getId());
            body.setIntRate(formatDecimal(ob.getIntRate(),locale, 9 ,18));
            body.setLimitDesc(ob.getLimitDesc());
            body.setLimitNo(ob.getLimitNo());
            body.setLsmCode(ob.getLsmCode());
            body.setMainLineFacilityId(ob.getMainLineFacilityId());
            body.setMbgFlag(ob.getMbgFlag());
            body.setMtBrCode(ob.getMtBrCode());
            body.setMtCurrencyCode(ob.getMtCurrencyCode());
            body.setMtFacilityCode(ob.getMtFacilityCode());
            body.setMtFacilityId(ob.getMtFacilityId());
            body.setMtIntRateCode(ob.getMtIntRateCode());
            body.setMtMaintStsCode(ob.getMtMaintStsCode());
            body.setMtOdRateCode(ob.getMtOdRateCode());
            body.setMtPenaltyIntRateCode(ob.getMtPenaltyIntRateCode());
            body.setMtrFlag(ob.getMtrFlag());
            body.setMtTenureTimeCode(ob.getMtTenureTimeCode());
            body.setOdRate(formatDecimal(ob.getOdRate(), locale, 9, 18));
            body.setPenaltyIntRate(formatDecimal(ob.getPenaltyIntRate(), locale, 9, 18));
            body.setRbsPurposeCode(ob.getRbsPurposeCode());
            body.setReviewDt(ob.getReviewDt());
            body.setRevolvingFlag(ob.getRevolvingFlag());
            body.setSectorFlag(ob.getSectorFlag());
            body.setSpecialRemarkFromString(ob.getSpecialRemark());
            body.setTempFlag(ob.getTempFlag());
            body.setUpdateBy(ob.getUpdateBy());
            body.setUpdatedDt(ob.getUpdatedDt());
            body.setVersion(ob.getVersion());

            message.setMsgBody(body);

        }else if(obj instanceof OBTradeMasterTrans){

            TradeBody body = new TradeBody();
            body.setAccountNo("1");
            body.setApprovedDt(new Date());
            body.setApprovedLimit(formatDecimal(new BigDecimal("123456789.123456789"), locale, 4, 30));
            body.setApprovedTenure(formatDecimal(new BigDecimal("123456789.123456789"), locale, 4, 30));
            body.setAutoPurgeFlag("Y");
            body.setCdtFileId("Y");
            body.setCgcFlag("Y");
            body.setCgcRefNo("123");
            body.setCgcSchema("123");
            body.setCommissionRate(formatDecimal(new BigDecimal("123456789.123456789"), locale, 9, 18));
            body.setCreateBy("123");
            body.setCreatedDt(new Date());
            body.setDelFlag("Y");
            body.setExpiryDt(new Date());
            body.setFirstLineFacilityId("Y");
            body.setId("123");
            body.setIntRate(formatDecimal(new BigDecimal("123456789.123456789"), locale, 9, 18));
            body.setLimitDesc("123");
            body.setLimitNo("123");
            body.setLsmCode("123");
            body.setMainLineFacilityId("123");
            body.setMbgFlag("Y");
            body.setMtBrCode("123");
            body.setMtCurrencyCode("MYR");
            body.setMtFacilityCode("123");
            body.setMtFacilityId("123");
            body.setMtIntRateCode("123");
            body.setMtMaintStsCode("123");
            body.setMtOdRateCode("123");
            body.setMtPenaltyIntRateCode("123");
            body.setMtrFlag("123");
            body.setMtTenureTimeCode("123");
            body.setOdRate(formatDecimal(new BigDecimal("123456789.123456789"), locale, 9, 18));
            body.setPenaltyIntRate(formatDecimal(new BigDecimal("123456789.123456789"), locale, 9, 18));
            body.setRbsPurposeCode("123");
            body.setReviewDt(new Date());
            body.setRevolvingFlag("Y");
            body.setSectorFlag("Y");
            body.setSpecialRemarkFromString("this is remark");
            body.setTempFlag("Y");
            body.setUpdateBy("123");
            body.setUpdatedDt(new Date());
            body.setVersion(new Long(1));

            message.setMsgBody(body);
         //   message.setCorrelationId(body.getId());
        }else{
            System.out.println("object is unknow : " + obj.toString());;

            TradeBody body = new TradeBody();
            body.setAccountNo("1");
            body.setApprovedDt(new Date());
            body.setApprovedLimit(formatDecimal(new BigDecimal("123456789.123456789"), locale, 4, 30));
            body.setApprovedTenure(formatDecimal(new BigDecimal("123456789.123456789"), locale, 4, 30));
            body.setAutoPurgeFlag("Y");
            body.setCdtFileId("Y");
            body.setCgcFlag("Y");
            body.setCgcRefNo("123");
            body.setCgcSchema("123");
            body.setCommissionRate(formatDecimal(new BigDecimal("123456789.123456789"), locale, 9, 18));
            body.setCreateBy("123");
            body.setCreatedDt(new Date());
            body.setDelFlag("Y");
            body.setExpiryDt(new Date());
            body.setFirstLineFacilityId("Y");
            body.setId("123");
            body.setIntRate(formatDecimal(new BigDecimal("123456789.123456789"), locale, 9, 18));
            body.setLimitDesc("123");
            body.setLimitNo("123");
            body.setLsmCode("123");
            body.setMainLineFacilityId("123");
            body.setMbgFlag("Y");
            body.setMtBrCode("123");
            body.setMtCurrencyCode("MYR");
            body.setMtFacilityCode("123");
            body.setMtFacilityId("123");
            body.setMtIntRateCode("123");
            body.setMtMaintStsCode("123");
            body.setMtOdRateCode("123");
            body.setMtPenaltyIntRateCode("123");
            body.setMtrFlag("123");
            body.setMtTenureTimeCode("123");
            body.setOdRate(formatDecimal(new BigDecimal("123456789.123456789"), locale, 9, 18));
            body.setPenaltyIntRate(formatDecimal(new BigDecimal("123456789.123456789"), locale, 9, 18));
            body.setRbsPurposeCode("123");
            body.setReviewDt(new Date());
            body.setRevolvingFlag("Y");
            body.setSectorFlag("Y");
            body.setSpecialRemarkFromString("this is remark");
            body.setTempFlag("Y");
            body.setUpdateBy("123");
            body.setUpdatedDt(new Date());
            body.setVersion(new Long(1));

            message.setMsgBody(body);
        }

            Map setting = new HashMap();
            setting.put(ISTPHeaderConstant.STPHDR_MESSAGE_NUMBER, "T001");
            setting.put(ISTPHeaderConstant.STPHDR_SOURCE, ISTPHeaderConstant.MESSAGE_SOURCE_TRADE);
            //setting.put(ISTPHeaderConstant.STPHDR_MESSAGE_REF_NO, "");
            setting.put(ISTPHeaderConstant.MESSAGE_TYPE, ISTPHeaderConstant.MESSAGE_TYPE_CUSTOMER);
            setting.put(ISTPHeaderConstant.PUBLISH_DATE, MessageDate.getInstance().getString(new Date()));
            setting.put(ISTPHeaderConstant.PUBLISH_TYPE, ISTPHeaderConstant.COVENANT_TYPE);

            try{
                message.setMsgHeader(preprareHeader(setting));
            }catch(Exception e){
                throw new StpCommonException("Fail to create header", e);
            }

        return message;
    }

}
