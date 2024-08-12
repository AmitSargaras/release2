package com.integrosys.cms.host.stp.common;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 4, 2008
 * Time: 11:55:09 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IStpTransType {

    /* list of STP transaction catered in CMS */
    // inquiry/listing
    //Phase 1
    public final static String TRX_TYPE_INQUIRE_CIF = "001";
    public final static String TRX_TYPE_ACC_VERIFY = "906";
    public final static String TRX_TYPE_SEARCH_FD_ACCT_LIST = "908";
    //Phase 2
    public final static String TRX_TYPE_COL_LINK_FAC_SUBFILE = "701";
    public final static String TRX_TYPE_COL_LINK_FAC_INQUIRY = "711";
    public final static String TRX_TYPE_FAC_COL_CHARGE_LIST = "702";
    public final static String TRX_TYPE_FAC_COL_CHARGE_INQUIRY = "712";
    public final static String TRX_TYPE_LS_FAC_REL_SUBFILE_INQUIRY = "302";
    public final static String TRX_TYPE_LS_FAC_REL_DETAIL_INQUIRY = "312";
    public final static String TRX_TYPE_LS_FAC_BNM_DETAIL_INQUIRY = "317";
    public final static String TRX_TYPE_LS_FAC_OFFICER_SUBFILE_INQUIRY = "308";
    public final static String TRX_TYPE_LS_FAC_INSURE_DETAIL_INQUIRY = "319";
    public final static String TRX_TYPE_LS_FAC_MASTER_SUBFILE_INQUIRY = "301";
    public final static String TRX_TYPE_LS_FAC_MASTER_DETAIL_INQUIRY = "311";
    //Phase 2 - Islamic
    public final static String TRX_TYPE_FAC_ISLAMIC_RENTAL_RENEWAL_INQUIRY = "363";
    public final static String TRX_TYPE_FAC_ISLAMIC_SEC_DEPOSIT_INQUIRY = "364";
    public final static String TRX_TYPE_FAC_ISLAMIC_MASTER_INQUIRY = "366";
    public final static String TRX_TYPE_FAC_MULTI_TIER_FINANCE_LIST = "304";
    //Rlos
    public final static String TRX_TYPE_CIF_BLACKLIST_INQUIRY = "015";
    
    // facility creation
    //phase 1
    public final static String TRX_TYPE_FAC_MASTER_CREATE = "321";
    public final static String TRX_TYPE_FAC_BMN_CREATE = "327";
    public final static String TRX_TYPE_FAC_OFFICER_CREATE = "328";
    public final static String TRX_TYPE_FAC_RELATION_CREATE = "322";
    public final static String TRX_TYPE_FAC_INSURE_CREATE = "329";
    //phase 2
    public final static String TRX_TYPE_FAC_ALT_SCHEDULE_CREATE = "325";
    public final static String TRX_TYPE_FAC_TIER_CREATE = "326";
    public final static String TRX_TYPE_FAC_SCHEDULE_DRAWDOWN_CREATE = "372";
    public final static String TRX_TYPE_FAC_CGC_CREATE = "375";
    public final static String TRX_TYPE_FAC_ISLAMIC_MASTER_CREATE = "376";
    public final static String TRX_TYPE_FAC_MULTI_TIER_FINANCE_CREATE = "324";
    public final static String TRX_TYPE_FAC_BBA_VARI_PACK_CREATE = "371";
    public final static String TRX_TYPE_FAC_INCREMENT_REDUCT_CREATE = "377";
    public final static String TRX_TYPE_FAC_ISLAMIC_SEC_DEPOSIT_CREATE = "374";
    public final static String TRX_TYPE_FAC_ISLAMIC_RENTAL_RENEW_CREATE = "373";
    public final static String TRX_TYPE_FAC_BASEL_2_CREATE = "369";

    // collateral creation
    public final static String TRX_TYPE_COL_MASTER_CREATE = "122";
    public final static String TRX_TYPE_COL_INSURE_CREATE = "124";
    public final static String TRX_TYPE_COL_TRADE_IN_CREATE = "125";
    public final static String TRX_TYPE_COL_CHARGOR_CREATE = "126";
    public final static String TRX_TYPE_COL_LINK_FAC_CREATE = "721";
    public final static String TRX_TYPE_COL_FAC_CHARGE_CREATE = "722";

    // facility update
    //phase 1
    public final static String TRX_TYPE_FAC_MASTER_UPDATE = "331";
    public final static String TRX_TYPE_FAC_BMN_UPDATE = "337";
    public final static String TRX_TYPE_FAC_OFFICER_UPDATE = "338";
    public final static String TRX_TYPE_FAC_RELATION_UPDATE = "332";
    public final static String TRX_TYPE_FAC_INSURE_UPDATE = "339";
    //phase 2
    public final static String TRX_TYPE_FAC_ALT_SCHEDULE_UPDATE = "335";
    public final static String TRX_TYPE_FAC_TIER_UPDATE = "336";
    public final static String TRX_TYPE_FAC_SCHEDULE_DRAWDOWN_UPDATE = "382";
    public final static String TRX_TYPE_FAC_CGC_UPDATE = "385";
    public final static String TRX_TYPE_FAC_ISLAMIC_MASTER_UPDATE = "386";
    public final static String TRX_TYPE_FAC_MULTI_TIER_FINANCE_UPDATE = "334";
    public final static String TRX_TYPE_FAC_BBA_VARI_PACK_UPDATE = "381";
    public final static String TRX_TYPE_FAC_INCREMENT_REDUCT_UPDATE = "387";
    public final static String TRX_TYPE_FAC_ISLAMIC_SEC_DEPOSIT_UPDATE = "384";
    public final static String TRX_TYPE_FAC_ISLAMIC_RENTAL_RENEW_UPDATE = "383";
    public final static String TRX_TYPE_FAC_BASEL_2_UPDATE = "389";

    // collateral update
    public final static String TRX_TYPE_COL_MASTER_UPDATE = "132";
    public final static String TRX_TYPE_COL_INSURE_UPDATE = "134";
    public final static String TRX_TYPE_COL_TRADE_IN_UPDATE = "135";
    public final static String TRX_TYPE_COL_CHARGOR_UPDATE = "136";
    public final static String TRX_TYPE_COL_LINK_FAC_UPDATE = "731";
    public final static String TRX_TYPE_COL_FAC_CHARGE_UPDATE = "732";

    // facility deletion
    //phase 1
    public final static String TRX_TYPE_FAC_MASTER_DELETE = "341";
    public final static String TRX_TYPE_FAC_BMN_DELETE = "347";
    public final static String TRX_TYPE_FAC_OFFICER_DELETE = "348";
    public final static String TRX_TYPE_FAC_RELATION_DELETE = "342";
    public final static String TRX_TYPE_FAC_INSURE_DELETE = "349";
    //phase 2
    public final static String TRX_TYPE_FAC_ALT_SCHEDULE_DELETE = "345";
    public final static String TRX_TYPE_FAC_TIER_DELETE = "346";
    public final static String TRX_TYPE_FAC_SCHEDULE_DRAWDOWN_DELETE = "392";
    public final static String TRX_TYPE_FAC_CGC_DELETE = "395";
    public final static String TRX_TYPE_FAC_ISLAMIC_MASTER_DELETE = "396";
    public final static String TRX_TYPE_FAC_MULTI_TIER_FINANCE_DELETE = "344";
    public final static String TRX_TYPE_FAC_BBA_VARI_PACK_DELETE = "391";
    public final static String TRX_TYPE_FAC_INCREMENT_REDUCT_DELETE = "397";
    public final static String TRX_TYPE_FAC_ISLAMIC_SEC_DEPOSIT_DELETE = "394";
    public final static String TRX_TYPE_FAC_ISLAMIC_RENTAL_RENEW_DELETE = "393";

    // collateral deletion
    public final static String TRX_TYPE_COL_MASTER_DELETE = "142";
    public final static String TRX_TYPE_COL_INSURE_DELETE = "144";
    public final static String TRX_TYPE_COL_TRADE_IN_DELETE = "145";
    public final static String TRX_TYPE_COL_CHARGOR_DELETE = "146";
    public final static String TRX_TYPE_COL_LINK_FAC_DELETE = "741";
    public final static String TRX_TYPE_COL_FAC_CHARGE_DELETE = "742";


}