package com.integrosys.cms.host.stp.mapper;

import java.util.List;
import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 24, 2008
 * Time: 1:04:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISTPMapper {

    //Full package of the data type
    public static final String STR_AMOUNT_FULL = "com.integrosys.base.businfra.currency.Amount";
    public static final String STR_BOOKINGLOC_FULL = "com.integrosys.cms.app.common.bus.IBookingLocation";

    //Others static values
    public static final String COND_TRUE = "true";
    public static final String COND_FALSE = "false";
    
    //Xml definition path
    public static final String COLLATERAL_PATH = "stp.collateral.filepath";
    public static final String FACILITY_PATH = "stp.facility.filepath";
    public static final String OTHER_PATH = "stp.other.filepath";

    List getList(String id, String filePath) throws Exception;

    List mapToFieldOB(Object object, List list) throws Exception;

    Object mapToBizOB(Object object, List list) throws Exception;;

    List mapToBizOBList(List list) throws Exception;
}
