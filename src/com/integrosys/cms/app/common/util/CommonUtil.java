/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/common/util/CommonUtil.java,v 1.28 2006/10/27 02:51:53 hmbao Exp $
 */
package com.integrosys.cms.app.common.util;

//java

import java.io.ByteArrayInputStream;
import java.util.TimeZone;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.contact.SBCommonManager;
import com.integrosys.base.businfra.contact.SBCommonManagerHome;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.ICurrency;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.AbstractDBUtil;
import com.integrosys.base.techinfra.dbsupport.InvalidStatementTypeException;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This utility class that contains utility methods that are resuable by others
 *
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.28 $
 * @since $Date: 2006/10/27 02:51:53 $ Tag: $Name: $
 */
public class CommonUtil {
    private static final int NUM_WEEKS_PER_YEAR = 52;

    private static final String COMMA_DELIMITER = ",";

    private static final String BIND_SYMBOL = "?";

    public static final int LESS = -1;

    public static final int EQUAL = 0;

    public static final int GREATER = 1;

    /**
     * SQL construct for IN variable list, provides binding for string literals
     * i.e. Countries, Organisations and State Values translates to a string
     * result set in Oracle Same behaviour as IN ('SG','HK','MY') Note the
     * maximum length of the binded parameter "?" is 2000 chars
     */
    public static final String SQL_STRING_SET = " IN (select * from table(cast(in_strlist(?) as strTableType)))";

    /**
     * SQL construct for IN variable list, provides binding for numeric values
     * i.e. security ids translates to a number result set in Oracle same
     * behaviour as IN (123,456,789) Note the maximum length of the string "?"
     * is 2000 chars
     */
    public static final String SQL_NUMBER_SET = " IN (select * from table(cast(in_numlist(?) as numTableType)))";

    /**
     * To calculate the percentage value of aValue.
     *
     * @param aValue a number value
     * @param pctVal percentage
     * @return value after percentage
     */
    public static BigDecimal calcAfterPercent(BigDecimal aValue, double pctVal) {
        if (aValue == null) {
            return null;
        }
        if (pctVal == ICMSConstant.DOUBLE_INVALID_VALUE) {
            return new BigDecimal(0d);
        }
        BigDecimal pctValBD = new BigDecimal(String.valueOf(pctVal / 100));
        return aValue.multiply(pctValBD);
    }

    /**
     * Calculated percentage val2 over val1.
     *
     * @param val1 of type BigDecimal
     * @param val2 of type BigDecimal
     * @return val2 over val1 percentage
     */
    public static BigDecimal calcPercentage(BigDecimal val1, BigDecimal val2) {
        if (val1 == null) {
            return null; // nan.
        }
        if (val2 == null) {
            return null;
        }
        BigDecimal bd = new BigDecimal(100);
        return val2.multiply(bd).divide(val1, ICMSConstant.PERCENTAGE_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Helper method to round the BigDecimal value using
     * BigDecimal.ROUND_HALF_UP.
     *
     * @param aNum  of type BigDecimal
     * @param scale of type int
     * @return rounded number
     */
    public static BigDecimal roundAmount(BigDecimal aNum, int scale) {
        // add 0.0000000000001 because we want 0.4999999999999 to be rounded as
        // 1.
        BigDecimal bd = aNum;
        if (scale == 0) {
            bd = aNum.add(new BigDecimal("0.0000000000001"));
        }
        bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    /**
     * This method performs a deep clone of an object
     *
     * @param input of Object type
     * @return Object - the cloned object
     * @throws IOException throws ClassNotFoundException
     */
    public static Object deepClone(Object input) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(input);
        byte[] byteArry = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(byteArry);
        ObjectInputStream iis = new ObjectInputStream(in);
        return iis.readObject();
    }

    /**
     * To roll up the date by the number of months specified
     *
     * @param aDate        of Date type
     * @param aNumOfMonths of int type
     * @return Date - the date being rolled up
     */
    public static Date rollUpDateByMonths(Date aDate, int aNumOfMonths) {
        Calendar cal = DateUtil.getCalendar();
        cal.setTime(aDate);

        int total_month = cal.get(Calendar.MONTH) + aNumOfMonths;
        int inc_year = total_month / 12;
        int tyear = cal.get(Calendar.YEAR) + inc_year;
        int tmonth = total_month - (12 * inc_year);
        int inc_day = cal.get(Calendar.DAY_OF_MONTH);

        int orgMaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, tmonth);
        cal.set(Calendar.YEAR, tyear);

        if (inc_day == orgMaxDay) {
            inc_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        cal.set(Calendar.DAY_OF_MONTH, inc_day);
        cal.set(Calendar.MONTH, tmonth);
        cal.set(Calendar.YEAR, tyear);

        return cal.getTime();

        /*
           * int numOfYears = aNumOfMonths/NUM_MONTHS_PER_YEAR;
           * DefaultLogger.debug(new CommonUtil(), "Number Of years: " +
           * numOfYears); int numOfMonths = aNumOfMonths - (numOfYears
           * NUM_MONTHS_PER_YEAR); DefaultLogger.debug(new CommonUtil(),
           * "Number of Months: " + numOfMonths);
           *
           * Date temp = aDate; if (numOfYears > 0) { temp =
           * rollUpDateByYears(aDate, numOfYears); }
           *
           * if (numOfMonths != 0) { Calendar cal = DateUtil.getCalendar();
           * cal.setTime(temp); cal.roll(Calendar.MONTH, aNumOfMonths); Date
           * afterDate = cal.getTime(); DefaultLogger.debug(new CommonUtil(),
           * "Rolled Date before year check: " + afterDate); if
           * (temp.after(afterDate)) cal.roll(Calendar.YEAR, 1); temp =
           * cal.getTime(); } DefaultLogger.debug(new CommonUtil(),
           * "Rolled Date afer year check: " + temp); return temp;
           */
    }

    /**
     * To rollup a date by the number of years specified
     *
     * @param aDate       of Date type
     * @param aNumOfYears of int type
     * @return Date - the date being rolled up
     */
    public static Date rollUpDateByYears(Date aDate, int aNumOfYears) {
        // Calendar cal = DateUtil.getCalendar();
        // cal.setTime(aDate);
        // cal.roll(Calendar.YEAR, aNumOfYears);
        // return cal.getTime();
        return rollUpDateByMonths(aDate, aNumOfYears * 12);
    }

    /**
     * To rollup a date by the number of weeks specified
     *
     * @param aDate       of Date type
     * @param aNumOfWeeks of int type
     * @return Date - the date being rolled up
     */
    public static Date rollUpDateByWeeks(Date aDate, int aNumOfWeeks) {
        int numOfYears = aNumOfWeeks / NUM_WEEKS_PER_YEAR;
        DefaultLogger.debug(new CommonUtil(), "Number Of years: " + numOfYears);
        int numOfWeeks = aNumOfWeeks - (numOfYears * NUM_WEEKS_PER_YEAR);
        DefaultLogger.debug(new CommonUtil(), "Number of Weeks: " + numOfWeeks);

        Date temp = aDate;
        if (numOfYears > 0) {
            temp = rollUpDateByYears(aDate, numOfYears);
        }

        if (numOfWeeks != 0) {
            Calendar cal = DateUtil.getCalendar();
            cal.setTime(temp);
            cal.roll(Calendar.WEEK_OF_YEAR, numOfWeeks);
            Date afterDate = cal.getTime();
            DefaultLogger.debug(new CommonUtil(), "Rolled Date before year check: " + afterDate);
            if (temp.after(afterDate)) {
                cal.roll(Calendar.YEAR, 1);
            }
            temp = cal.getTime();
        }
        DefaultLogger.debug(new CommonUtil(), "Rolled Date afer year check: " + temp);
        return temp;
    }

    /**
     * To roll up a date by the number of days
     *
     * @param aDate      of Date type
     * @param aNumOfDays of int type
     * @return Date - the date being rolled up
     */
    public static Date rollUpDateByDays(Date aDate, int aNumOfDays) {
        Date temp = aDate;
        for (int ii = 0; ii < aNumOfDays; ii++) {
            temp = rollUpDate(temp);
        }
        return temp;
    }

    /**
     * Roll up a particular date by a day
     *
     * @param aDate - Date
     * @return Date - the rolled up date
     */
    public static Date rollUpDate(Date aDate) {
        Calendar cal = DateUtil.getCalendar();
        cal.setTime(aDate);
        cal.roll(Calendar.DAY_OF_YEAR, 1);
        Date afterDate = cal.getTime();
        if (aDate.after(afterDate)) {
            cal.roll(Calendar.YEAR, 1);
        }
        return cal.getTime();
    }

    /**
     * Roll up a particular date based on the specified frequency.
     *
     * @param aDate     - Date to rollup
     * @param aFreq     - int
     * @param aFreqUnit - String. Valid values are ICMSConstant.FREQ_UNIT_DAYS,
     *                  ICMSConstant.FREQ_UNIT_WEEKS, ICMSConstant.FREQ_UNIT_MONTHS or
     *                  ICMSConstant.FREQ_UNIT_YEARS
     * @return
     */
    public static Date rollUpDate(Date aDate, int aFreq, String aFreqUnit) {
        if (ICMSConstant.FREQ_UNIT_DAYS.equals(aFreqUnit) || ICMSConstant.TIME_FREQ_DAY.equals(aFreqUnit)) {
            return CommonUtil.rollUpDateByDays(aDate, aFreq);
        }
        if (ICMSConstant.FREQ_UNIT_WEEKS.equals(aFreqUnit) || ICMSConstant.TIME_FREQ_WEEK.equals(aFreqUnit)) {
            return CommonUtil.rollUpDateByWeeks(aDate, aFreq);
        }
        if (ICMSConstant.FREQ_UNIT_MONTHS.equals(aFreqUnit) || ICMSConstant.TIME_FREQ_MONTH.equals(aFreqUnit)) {
            return CommonUtil.rollUpDateByMonths(aDate, aFreq);
        }
        if (ICMSConstant.FREQ_UNIT_YEARS.equals(aFreqUnit) || ICMSConstant.TIME_FREQ_YEAR.equals(aFreqUnit)) {
            return CommonUtil.rollUpDateByYears(aDate, aFreq);
        }
        return null;
    }

    /**
     * Roll down a particular date by a day
     *
     * @param aDate - Date
     * @return Date - the rolled down date
     */
    public Date rollDownDate(Date aDate) {
        Calendar cal = DateUtil.getCalendar();
        cal.setTime(aDate);
        cal.roll(Calendar.DAY_OF_YEAR, -1);
        Date afterDate = cal.getTime();
        if (aDate.before(afterDate)) {
            cal.roll(Calendar.YEAR, -1);
        }
        return cal.getTime();
    }

    /**
     * Computes the due date of a date given its day period and country code.
     *
     * @param startDate   start date
     * @param numOfDays   the number of days before the startDate is due
     * @param countryCode country code
     * @return due date
     */
    public static Date getDueDate(Date startDate, int numOfDays, String countryCode) {
        ILimitProxy proxy = LimitProxyFactory.getProxy();
        return proxy.getDueDate(startDate, numOfDays, countryCode);
    }

    /**
     * If the two dates are equals, Calendar.MONTH and YEAR will not return the
     * correct figures.
     * <p/>
     * Returns the absolute difference between 2 Dates. The types supported are
     * Calendar.MILLISECOND, Calendar.SECOND, Calendar.MINUTE,
     * Calendar.HOUR_OF_DAY, Calendar.DATE, Calendar.MONTH and Calendar.YEAR.
     *
     * @param date1 the first date
     * @param date2 the second date
     * @param type  the type of difference
     * @return long the difference based on the type specified.
     */
    public static long dateDiff(Date date1, Date date2, int type) {
        long time1, time2;
        long result = -1;

        if ((date1 == null) || (date2 == null)) {
            return -1;
        }

        if (date1.getTime() > date2.getTime()) {
            time1 = date1.getTime();
            time2 = date2.getTime();
        } else if (date1.getTime() < date2.getTime()) {
            time2 = date1.getTime();
            time1 = date2.getTime();
        } else {
            return 0;
        }

        switch (type) {
            case Calendar.MILLISECOND: {
                result = time1 - time2;
                break;
            }
            case Calendar.SECOND: {
                result = (time1 - time2) / 1000;
                break;
            }
            case Calendar.MINUTE: {
                result = (time1 - time2) / 1000 / 60;
                break;
            }
            case Calendar.HOUR_OF_DAY: {
                result = (time1 - time2) / 1000 / 60 / 60;
                break;
            }
            case Calendar.DATE: {
                result = (time1 - time2) / 1000 / 60 / 60 / 24;
                break;
            }

            case Calendar.MONTH:
            case Calendar.YEAR: {
                GregorianCalendar gcLarger = new GregorianCalendar();
                gcLarger.setGregorianChange(new Date(Long.MIN_VALUE));
                GregorianCalendar gcSmaller = new GregorianCalendar();
                gcSmaller.setGregorianChange(new Date(Long.MIN_VALUE));

                if (date1.getTime() > date2.getTime()) {
                    gcLarger.setTime(date1);
                    gcSmaller.setTime(date2);
                } else {
                    gcLarger.setTime(date2);
                    gcSmaller.setTime(date1);
                }

                // this line must come before the destructive add methods below
                boolean sameYear = (gcLarger.get(Calendar.YEAR) == gcSmaller.get(Calendar.YEAR));

                gcLarger.add(Calendar.MILLISECOND, -gcSmaller.get(Calendar.MILLISECOND));
                gcLarger.add(Calendar.SECOND, -gcSmaller.get(Calendar.SECOND));
                gcLarger.add(Calendar.MINUTE, -gcSmaller.get(Calendar.MINUTE));
                gcLarger.add(Calendar.HOUR_OF_DAY, -gcSmaller.get(Calendar.HOUR_OF_DAY));
                gcLarger.add(Calendar.DATE, -gcSmaller.get(Calendar.DATE));
                gcLarger.add(Calendar.MONTH, -gcSmaller.get(Calendar.MONTH));

                int yearCount = 0;

                // this is to account for the fact that the year 0000 cannot
                // exist in Calendar, if this operation is allowed to continue,
                // we will get a year 0001 in cases when the result is actually 0000
                if (gcLarger.get(Calendar.YEAR) == gcSmaller.get(Calendar.YEAR)) {
                    yearCount = 0;
                } else {
                    gcLarger.add(Calendar.YEAR, -gcSmaller.get(Calendar.YEAR));
                    yearCount = gcLarger.get(Calendar.YEAR);
                }

                if (type == Calendar.MONTH) {
                    result = gcLarger.get(Calendar.MONTH);
                    if (!sameYear) {
                        result += (yearCount * 12);
                    }
                } else if (type == Calendar.YEAR) {
                    result = yearCount;
                }
                break;
            }
            default:
                result = -1;
        }
        return result;
    }

    /**
     * converts a string array to a delimited string of choice
     * <p/>
     * countries[0]='SG'; countries[1]='HK'; countries[2]='MY'; delim = ',"
     * <p/>
     * returns a string 'SG,HK,MY'
     *
     * @param array
     * @param delim
     * @return String
     */
    private static String arrayToDelimStr(String[] array, String delim) {

        if ((array == null) || (array.length == 0) || (delim == null)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * converts a string array to a comma delimited string
     *
     * @param array
     * @return String
     */
    public static String arrayToDelimStr(String[] array) {

        if ((array == null) || (array.length == 0)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(COMMA_DELIMITER);
            }
        }
        return sb.toString();
    }

    /**
     * converts a long array to a comma delimited string
     *
     * @param array
     * @return String
     */
    public static String arrayToDelimStr(long[] array) {

        if ((array == null) || (array.length == 0)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append(Long.toString(array[i]));
            if (i < array.length - 1) {
                sb.append(COMMA_DELIMITER);
            }
        }
        return sb.toString();
    }

    /**
     * sets the SQL parameters used in JDBC calls ArrayList is populated during
     * the construction of the dynamic SQL String Precondition: the number of
     * variables to be binded must match supports only the common JDBC types
     *
     * @param params
     * @throws SQLException
     * @throws NoSQLStatementException
     * @throws InvalidStatementTypeException
     */
    public static void setSQLParams(SQLParameter params, AbstractDBUtil util) throws SQLException,
            NoSQLStatementException, InvalidStatementTypeException {

        if ((params == null) || (params.size() == 0)) {
            return;
        }

        int i = 1;
        for (Iterator iter = params.iterator(); iter.hasNext(); i++) {
            Object each = iter.next();
            if (each != null) {
                if (each instanceof String) {
                    util.setString(i, (String) each);

                } else if (each instanceof Long) {
                    util.setLong(i, ((Long) each).longValue());

                } else if (each instanceof java.sql.Date) {
                    util.setDate(i, (java.sql.Date) each);

                } else if (each instanceof java.util.Date) {
                    util.setDate(i, new java.sql.Date(((java.util.Date) each).getTime()));

                } else if (each instanceof Integer) {
                    util.setInt(i, ((Integer) each).intValue());

                } else {
                    throw new InvalidStatementTypeException("SQL Type not supported!");
                }
            }
        }
    }

    /**
     * sets the SQL parameters used in JDBC calls ArrayList is populated during
     * the construction of the dynamic SQL String Precondition: the number of
     * variables to be binded must matched
     *
     * @param params
     * @throws SQLException
     * @throws NoSQLStatementException
     * @throws InvalidStatementTypeException
     */
    public static void setSQLParams(List params, AbstractDBUtil util) throws SQLException, NoSQLStatementException,
            InvalidStatementTypeException {

        if (params == null) {
            return;
        }

        for (int i = 0, j = i + 1; i < params.size(); i++, j++) {

            Object each = params.get(i);

            if (each != null) {
                if (each instanceof String) {
                    util.setString(j, (String) each);

                } else if (each instanceof Long) {
                    util.setLong(j, ((Long) each).longValue());

                } else if (each instanceof Date) {
                    util.setDate(j, (java.sql.Date) each);

                } else if (each instanceof Integer) {
                    util.setInt(j, ((Integer) each).intValue());
                } else {
                    throw new InvalidStatementTypeException("SQL Type not supported!");
                }

            }
        }
    }

    /**
     * generates the SQL JDBC binding construct <code>
     * IN (?,?,?)
     * </code> this only work for
     * variable string lists i.e. ('HK','SG');
     *
     * @param array
     * @param sb
     * @param params
     */
    public static void buildSQLInList(String[] array, StringBuffer sb, List params) {

        if (isEmptyArray(array)) {
            return;
        }

        sb.append(" IN (");

        for (int i = 0; i < array.length; i++) {
            sb.append(BIND_SYMBOL);
            params.add(array[i]);
            if (i < array.length - 1) {
                sb.append(COMMA_DELIMITER);
            }
        }
        sb.append(")");
    }

    /**
     * generates the SQL JDBC binding construct <code>
     * IN (?,?,?)
     * </code> this only work for
     * variable Long lists
     *
     * @param array
     * @param sb
     * @param params
     */
    public static void buildSQLInList(Long[] array, StringBuffer sb, List params) {

        if (isEmptyArray(array)) {
            return;
        }

        sb.append(" IN (");

        for (int i = 0; i < array.length; i++) {
            sb.append(BIND_SYMBOL);
            params.add(array[i]);
            if (i < array.length - 1) {
                sb.append(COMMA_DELIMITER);
            }
        }
        sb.append(")");
    }

    /**
     * generates the SQL JDBC binding construct <code>
     * IN (?,?,?)
     * </code>
     *
     * @param list
     * @param sb
     * @param params
     */
    public static void buildSQLInList(List list, StringBuffer sb, List params) {

        if ((list == null) || (list.size() == 0)) {
            return;
        }

        sb.append(" IN (");

        for (int i = 0; i < list.size(); i++) {
            sb.append(BIND_SYMBOL);
            params.add(list.get(i));
            if (i < list.size() - 1) {
                sb.append(COMMA_DELIMITER);
            }
        }
        sb.append(")");
    }

    /**
     * generates the SQL JDBC binding construct <code>
     * IN ((?,?),(?,?))
     * </code>
     *
     * @param list1
     * @param list2
     * @param sb
     * @param params
     */
    public static void buildSQLInList(List list1, List list2, StringBuffer sb, List params) {

        if ((list1 == null) || (list1.size() == 0) || (list2 == null) || (list2.size() == 0)) {
            return;
        }
        if (list1.size() != list2.size()) {
            return;
        }

        sb.append(" IN (");

        for (int i = 0; i < list1.size(); i++) {
            sb.append("(");
            sb.append(BIND_SYMBOL);
            params.add(list1.get(i));
            sb.append(COMMA_DELIMITER);
            sb.append(BIND_SYMBOL);
            params.add(list2.get(i));
            sb.append(")");

            if (i < list1.size() - 1) {
                sb.append(COMMA_DELIMITER);
            }
        }
        sb.append(")");
    }

    /**
     * helper method to test empty array
     *
     * @param array
     * @return boolean
     */
    public static boolean isEmptyArray(Object[] array) {
        return ((array == null) || (array.length == 0));
    }

    /**
     * Helper method to check if a list is empty.
     *
     * @param list - List
     * @return boolean
     */
    public static boolean isEmptyList(List list) {
        return ((list == null) || (list.size() == 0));
    }

    /**
     * Helper to determine if a String is empty. Empty is defined as either null
     * or "" (after trimming)
     *
     * @param str String
     * @return true if empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        if ((str == null) || (str.trim()).equals("")) {
            return true;
        }
        return false;
    }

    /**
     * Helper method to get the minimum of 2 BigDecimal
     *
     * @param a - BigDecimal
     * @param b - BigDecimal
     */
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        if ((a == null) || (b == null)) {
            return null;
        }
        int compareResult = a.compareTo(b);
        return (compareResult <= 0) ? a : b;
    }

    private static String baseCurrency = PropertyManager.getValue(ICMSConstant.BASE_EXCHANGE_CURRENCY, "INR");

    public static String getBaseCurrency(ICommonUser user) {
        DefaultLogger.debug(new CommonUtil(), "base Currency: " + baseCurrency);
        // if(baseCurrency == null){
        baseCurrency = PropertyManager.getValue(ICMSConstant.BASE_CURRENCY_CODE, "USD");
        if (ICMSConstant.BASE_CURRENCY_USER.equals(baseCurrency)) {
            DefaultLogger.debug(new CommonUtil(), "user country: " + user.getCountry());
            baseCurrency = getCurrencyCodeByCountry(user.getCountry());
        }
        // }
        return baseCurrency;
    }

    public static String getBaseCurrency() {
    	/*Hardcoded BaseCurrency to INR -  to fix issue at HDFC*/
//        return baseCurrency;    
    	return "INR";
    }

    private static String baseExchangeCurrency = null;

    public static String getBaseExchangeCurrency() {
        if (baseExchangeCurrency == null) {
            baseExchangeCurrency = PropertyManager.getValue(ICMSConstant.BASE_EXCHANGE_CURRENCY);
        }
        return baseExchangeCurrency;
    }

    private static HashMap countryCurrencyMap = new HashMap();

    public static String getCurrencyCodeByCountry(String countryCode) {
        if ((countryCode == null) || (countryCode.length() == 0)) {
            return null;
        }
        try {
            if (countryCurrencyMap.size() == 0) {
                SBCommonManager mgr = getCommonManager();
                Collection list = mgr.getAllCurrencies();
                Iterator itr = list.iterator();
                while (itr.hasNext()) {
                    ICurrency currency = (ICurrency) itr.next();
                    countryCurrencyMap.put(currency.getCountryCode(), currency.getCurrencyCode());
                }
            }
            return (String) countryCurrencyMap.get(countryCode);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Helper method to get ejb object of data protection proxy session bean.
     *
     * @return SBDataProtection proxy ejb object
     */
    private static SBCommonManager getCommonManager() throws Exception {
        DefaultLogger.debug(new CommonUtil(), "Getting SBCommonManager");
        SBCommonManager mgr = (SBCommonManager) BeanController.getEJB(ICMSJNDIConstant.SB_COMMON_MANAGER_JNDI,
                SBCommonManagerHome.class.getName());

        if (mgr == null) {
            throw new Exception("SBCommonManager is null!");
        }
        return mgr;
    }

    // ====================================
    // Method from GeneralChargeUtil
    // ====================================
    /**
     * Check if the amount is a result of a forex error.
     *
     * @param amt - Amount
     * @return - boolean
     */
    public static boolean isForexErrorAmount(Amount amt) {
        boolean isForexErrorAmt = ((amt != null) && (amt.getAmount() == 0) && amt.getCurrencyCode().equals(
                ICMSConstant.CURRENCYCODE_INVALID_VALUE));
        return isForexErrorAmt;
    }

    /**
     * Get amount representing forex error has occurred.
     *
     * @return - boolean
     */
    public static Amount getForexErrorAmount() {
        return new Amount(new BigDecimal(0), new CurrencyCode(ICMSConstant.CURRENCYCODE_INVALID_VALUE));
    }
    
    /**
     * Convert an amount to the specific currency code
     *
     * @param amt - the amount to convert
     * @param toCurrency - the currency code convert to
     * @return - Amount
     * @throws AmountConversionException - thrown when there is no exchange rate is available to does the currency conversion
     */
	public static Amount convertAmount(Amount amt, String toCurrency) throws AmountConversionException {
        try {
			if (amt == null) {
                return amt;
            }
			if( amt.getCurrencyCode().equals( toCurrency ) ) {
				return amt;
			}
            if ( isForexErrorAmount(amt) ) {
                return getForexErrorAmount();
            }
            
            Amount convertedAmt = AmountConversion.getConversionAmount (amt, toCurrency);
            return convertedAmt;

        } catch (Exception e) {
        	DefaultLogger.error(CommonUtil.class.getName(), "Exception caught in convertAmount : "+e.getMessage(), e);
            throw new AmountConversionException(e.getMessage());
        }
    }

    /**
     * Add 2 amount object. If the amount is in different currency, convert the
     * 2nd amount (amtToAdd) to the currency of the 1st amount (total) and
     * perform the addition.
     *
     * @param total    - total amount (When total is null, amtToAdd is returned)
     * @param amtToAdd - amount to add to the total amount
     * @return - Amount
     * @throws AmountConversionException - thrown when there is no exchange rate
     *                                   is available to does the currency conversion
     */
    public static Amount addAmount(Amount total, Amount amtToAdd) throws AmountConversionException {
        try {
            if (isForexErrorAmount(total) || isForexErrorAmount(amtToAdd)) {
                return getForexErrorAmount();
            }
            if (total == null) {
                return amtToAdd;
            }

            Amount convertedAmt = AmountConversion.getConversionAmount(amtToAdd, total.getCurrencyCode());
            return (amtToAdd == null) ? total : total.add(convertedAmt);

        }
        catch (ChainedException e) {
            throw new AmountConversionException(e.getMessage());
        }
    }

    /**
     * Subtract 2 amount object. If the amount is in different currency, convert
     * the 2nd amount (amtToSubtract) to the currency of the 1st amount (total)
     * and perform the addition. If total is null, it is treated as [0 -
     * amtToSubtract]
     *
     * @param total         - total amount (When total is null, negative of
     *                      amtToSubtract is returned)
     * @param amtToSubtract - amount to subtract from the total amount
     * @return - Amount
     * @throws AmountConversionException - thrown when there is no exchange rate
     *                                   is available to does the currency conversion
     */
    public static Amount subtractAmount(Amount total, Amount amtToSubtract) throws AmountConversionException {
        try {
            if (isForexErrorAmount(total) || isForexErrorAmount(amtToSubtract)) {
                return getForexErrorAmount();
            }

            if (total == null) {
                return new Amount(-amtToSubtract.getAmount(), amtToSubtract.getCurrencyCode());
            }

            Amount convertedAmt = AmountConversion.getConversionAmount(amtToSubtract, total.getCurrencyCode());
            return (amtToSubtract == null) ? total : total.subtract(convertedAmt);

        }
        catch (ChainedException e) {
            throw new AmountConversionException(e.getMessage());
        }

    }

    /**
     * Compares 2 amount object to determine if they are equal in value or which
     * is smaller or greater. If the amounts are in different currencies,
     * conversion of the 2nd amount object (amt2) will be converted to the
     * currency of the first object before carrying out the comparison. Both
     * objects are considered equal when 1. they are both null, 2. the "amount"
     * value is equal (in the same currency)
     *
     * @param amt1 - Amount 1 to be compared against
     * @param amt2 - Amount 2 to be compared to Amount 1
     * @return CommonUtil.EQUAL if amt1 = amt2, CommonUtil.LESS if amt1 < amt2,
     *         CommonUtil.GREATER if amt1 > amt2
     * @throws AmountConversionException - thrown when there is no exchange rate
     *                                   is available to does the currency conversion
     */
    public static int compareAmount(Amount amt1, Amount amt2) throws AmountConversionException {
        if ((amt1 == null) && (amt2 == null)) {
            return EQUAL;
        }

        if ((amt1 == null) && (amt2 != null)) {
            return LESS;
        }

        if ((amt1 != null) && (amt2 == null)) {
            return GREATER;
        }

        if (!(amt1.getCurrencyCode().equals(amt2.getCurrencyCode()))) {
            amt2 = AmountConversion.getConversionAmount(amt2, amt1.getCurrencyCode());
        }

        if ((amt1.getAmount() == amt2.getAmount())) {
            return EQUAL;
        }

        return (amt1.getAmount() < amt2.getAmount()) ? LESS : GREATER;
    }

    /**
     * Helper method to get the current date
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        Date returnDate = DateUtil.getDate();
        returnDate = DateUtil.clearTime(returnDate);
        return returnDate;
    }

    /**
     * Helper method to get the quarter code of the pass in date
     *
     * @param currentDate - the date
     * @return String
     */
    public static String getQuarterCode(Date currentDate) {

        if (currentDate != null) {
            Calendar mcal = new GregorianCalendar();
            mcal.setTime(currentDate);
            int curMth = mcal.get(Calendar.MONTH);
            if (curMth <= 2) {
                return ICMSConstant.PROP_IDX_TYPE_Q1;
            } else if (curMth <= 5) {
                return ICMSConstant.PROP_IDX_TYPE_Q2;
            } else if (curMth <= 8) {
                return ICMSConstant.PROP_IDX_TYPE_Q3;
            } else if (curMth <= 11) {
                return ICMSConstant.PROP_IDX_TYPE_Q4;
            }
        }
        return null;
    }

    /**
     * Helper method to get the quarter value based on quarter code for the usage to sort the quarter code according to the priority
     *
     * @param quarterCode - the quarter code
     * @return int
     */
    public static int getQuarterCodeValue(String quarterCode) {
        if (quarterCode.equals( ICMSConstant.PROP_IDX_TYPE_Q4 ) ) {
			return 1;
		}
		else if( quarterCode.equals( ICMSConstant.PROP_IDX_TYPE_Q3 ) ) {
			return 2;
		}
		else if( quarterCode.equals( ICMSConstant.PROP_IDX_TYPE_Q2 ) ) {
			return 3;
		}
		else if( quarterCode.equals( ICMSConstant.PROP_IDX_TYPE_Q1 ) ) {
			return 4;
		}
		else if( quarterCode.equals( ICMSConstant.PROP_IDX_TYPE_A ) ) {
			return 5;
		}
		return 0;
	}

	/**
     * Helper method to get the year of the pass in Date.
     *
     * @param currentDate - the date
     * @return int
     */
	public static int getYear(Date currentDate) {
		if( currentDate != null ) {
			Calendar mcal = new GregorianCalendar();
            mcal.setTime(currentDate);
			return mcal.get( Calendar.YEAR );
		}
        return 0;
    }
	
	/**
	 * This method returns the current date and time stamp in dd-MMM-yyyy_timestamp format.
	 * @return 
	 */
	public static String getCurrentDateTimeStamp() {
		String dateTimeStamp = new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime())+"_"+Calendar.getInstance().getTimeInMillis();
		return dateTimeStamp;
	}

	public static String getCurrentDateForPosidex(Calendar currentDate) {
		
		if(currentDate == null){
			currentDate = Calendar.getInstance();
			DefaultLogger.debug(new CommonUtil(), "currentDate.getTime():::"+currentDate.getTime());
			currentDate.setTime(currentDate.getTime());
		}
		DefaultLogger.debug(new CommonUtil(), "currentDate:::"+currentDate.getTime());
		String date = new SimpleDateFormat("ddMMyyyy").format(currentDate.getTime());
		return date;
	}
	
	public static BigDecimal convertToBaseCcy(Amount amt) {
		if (amt != null) {
			BigDecimal localAmt = null;
			
			DefaultLogger.debug(CommonUtil.class.getName() , "Inside convertToBaseCcy with amt :"+amt );
			
			try {
				if(StringUtils.isNotBlank(amt.getCurrencyCode()))
					amt.setCurrencyCode(amt.getCurrencyCode().trim());
				else
					amt.setCurrencyCode(baseCurrency);
				
				String currencyCode = amt.getCurrencyCode() ;
				BigDecimal amtToConvert = amt.getAmountAsBigDecimal();
				
				localAmt = convertToBaseCcy(currencyCode, amtToConvert);
				return localAmt;
			}
			catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.error(CommonUtil.class.getName() , "Exception caught in convertToBaseCcy : "+e.getMessage(), e);
			}
		}
		
		return null;
	}
	

	public static BigDecimal convertToBaseCcy(String currencyCode, String amountStr) {
		
		try {
			if(StringUtils.isBlank(currencyCode) || StringUtils.isBlank(amountStr))
				return null;
			
			BigDecimal amount = new BigDecimal(UIUtil.removeComma(amountStr));
			
			BigDecimal convertedAmount = convertToBaseCcy(currencyCode,amount);
			
			return convertedAmount;
			
		}
		catch (Exception e) {
			DefaultLogger.error(CommonUtil.class.getName() , "Exception caught in convertToBaseCurrency"+e.getMessage(), e);
			return null;
		}
	}
	
	public static BigDecimal convertToBaseCcy(String currencyCode, BigDecimal amount) {
		BigDecimal convertedAmt = null;
		if(currencyCode.equals(baseCurrency)) {
			return amount;
		}
		
		if(amount != null) {
			IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
			BigDecimal exchangeRate = frxPxy.getExchangeRateWithINR(currencyCode.trim());
			convertedAmt = exchangeRate.multiply(amount);
			convertedAmt = convertedAmt.setScale(2, RoundingMode.HALF_UP);
		}
		
		return convertedAmt;
	}
	
	public static String getCurrentDateTime(String format) {
		format = StringUtils.isNotEmpty(format) ? format : "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(TimeZone.getDefault());
		return formatter.format(new Date());
	}

}