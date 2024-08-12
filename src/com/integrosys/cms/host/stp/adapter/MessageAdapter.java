package com.integrosys.cms.host.stp.adapter;

import com.integrosys.cms.host.stp.bus.OBStpField;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.StpUtil;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Aug 26, 2008
 * Time: 10:34:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class MessageAdapter implements IMessageAdapter {

    private int startPos = 0;
    private int p_MsgLen = 0;                //Buffer Length
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageAdapter() {
    }

    /**
     * Construct Message from List to byte[]
     *
     * @param aList List
     */
    public byte[] constructMessageToByte(List aList) throws UnsupportedEncodingException {

        String listFieldID = null;
        String listClassField = null;
        String listPosition = null;
        String listLength = null;
        String listValue = "";
        String listFormat = null;
        String listDecimalPoint = null;
        byte[] byteTemp = null;
        byte[] byteEncoded = new byte[p_MsgLen];
        int lengthForDecimal = 0;
        int lengthNeeded = 0;
        startPos = 0;

        for (int i = 0; i < aList.size(); i++) {
            OBStpField obStpField = new OBStpField();
            obStpField = (OBStpField) aList.get(i);
            listFieldID = obStpField.getFieldID();
            listClassField = obStpField.getClassField();
            listPosition = obStpField.getPosition();
            listLength = obStpField.getLength();
            listValue = obStpField.getValue();
            listFormat = obStpField.getFormat();
            listDecimalPoint = obStpField.getDecimalPoint();
            if (StringUtils.isNotBlank(listDecimalPoint)) {
                lengthForDecimal = Integer.parseInt(listDecimalPoint);
            }
            if (StringUtils.isNotBlank(listLength) && (listFormat.equals("P") || listFormat.equals("B"))) {
                lengthNeeded = Integer.parseInt(listLength);
                lengthNeeded = lengthNeeded * 2;
            } else {
                lengthNeeded = Integer.parseInt(listLength);
            }
            if (listFieldID.equals(IStpConstants.HLEN_FIELD_ID)) {
                int headerLength = Integer.parseInt(listValue);
                byteEncoded = new byte[headerLength + 4];
            }
            byteTemp = StpUtil.Encode(StringUtils.trim(listValue), listFormat.charAt(0), lengthNeeded, true, lengthForDecimal);
            for (int m = 0; m < byteTemp.length; m++) {
                Arrays.fill(byteEncoded, startPos, startPos + 1, byteTemp[m]);
                startPos++;
            }
        }
        return byteEncoded;
    }

    /**
     * Construct Message from List to String
     *
     * @param aList List
     */
    public String constructMessageToString(List aList) {

        StringBuffer sb = new StringBuffer();

        String message = null;
        String listFieldID = null;
        String listClassField = null;
        String listPosition = null;
        String listLength = null;
        String listValue = null;
        String listFormat = null;
        String listDecimalPoint = null;
        byte[] byteTemp = new byte[p_MsgLen];
        byte[] byteEncoded = new byte[p_MsgLen];
        int lengthForDecimal = 0;
        int lengthNeeded = 0;
        startPos = 0;

        for (int i = 0; i < aList.size(); i++) {
            OBStpField obStpField = new OBStpField();
            obStpField = (OBStpField) aList.get(i);
            listFieldID = obStpField.getFieldID();
            listClassField = obStpField.getClassField();
            listPosition = obStpField.getPosition();
            listLength = obStpField.getLength();
            listValue = obStpField.getValue();
            listFormat = obStpField.getFormat();
            listDecimalPoint = obStpField.getDecimalPoint();
            if (StringUtils.isNotBlank(listDecimalPoint)) {
                lengthForDecimal = Integer.parseInt(listDecimalPoint);
            }
            if (StringUtils.isNotBlank(listLength) && (listFormat.equals("P") || listFormat.equals("S"))) {
                lengthNeeded = Integer.parseInt(listLength);
                lengthNeeded = lengthNeeded * 2;
                message = unformatDecimal(listValue, lengthForDecimal, lengthNeeded);
            } else if (listFormat.equals("B")) {
                message = listValue;
            } else {
                lengthNeeded = Integer.parseInt(listLength);
                message = appendString(listValue, lengthNeeded);
            }
            sb.append(message);
        }
        return sb.toString();
    }

    /**
     * Append String at the back of the value to fit the length
     *
     * @param stringValue String
     * @param length      int
     */
    public static String appendString(String stringValue, int length) {
        StringBuffer sb = new StringBuffer();
        if (stringValue != null) {
            sb.append(stringValue);
            for (int i = stringValue.length(); i < length; i++) {
                sb.append(" ");
            }
        } else {
            for (int i = 0; i < length; i++) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * format the decimal value to become no decimal point
     *
     * @param strIn    String
     * @param DecIn    int
     * @param FieldLen int
     */
    public static String unformatDecimal(String strIn, int DecIn, int FieldLen) {

        String strTemp = "";
        boolean bNegative = false;
        int intPos = 0;
        int intDecimal = 0;
        int i = 0;
        int NumofZero = 0;

        if (DecIn == 0) {
            NumofZero = FieldLen - strIn.length();
            for (i = 0; i < NumofZero; i++) {
                strIn = '0' + strIn;
            }
            return strIn;    // no decimal define
        }

        if (DecIn != 0) {
            intPos = strIn.lastIndexOf('.');
            if (intPos == -1) {// No decimal Point
                if (DecIn != 0) {
                    for (i = 0; i < DecIn; i++) {
                        strIn = strIn + '0';
                    }
                }

                strTemp = strIn;
            } else {// There is decimal point '.'
                intDecimal = strIn.length() - intPos - 1;
                if (intDecimal != DecIn) {
                    if (intDecimal > DecIn) { // .xx (front end) vs .x (host)
                        //strIn = strIn.substring(0, strIn.length() - 1);
                        strIn = strIn.substring(0, strIn.length() - (intDecimal - DecIn));
                    } else { // .xx (front end) vs .xxx (host)
                        for (i = 0; i < (DecIn - intDecimal); i++) {
                            strIn = strIn + '0';
                        }
                    }
                }
                strTemp = strIn.substring(0, intPos) + strIn.substring(intPos + 1, strIn.length());
            }
        }

        if (strTemp.indexOf("-", 0) == 0) { // -ve number
            strTemp = strTemp.substring(1, strTemp.length());
            bNegative = true;
        } else {
            bNegative = false;
        }

        NumofZero = FieldLen - 1 - strTemp.length();
        for (i = 0; i < NumofZero; i++) {
            strTemp = '0' + strTemp;
        }
        if (bNegative) {
            strTemp = '-' + strTemp;
        }
        return strTemp;
    }

    /**
     * Decipher Message from byte[] to List
     *
     * @param aBytes byte[]
     * @param aList  List
     */
    public List decipherMessageToList(byte[] aBytes, List aList) throws UnsupportedEncodingException {
        List obStpList = new ArrayList();
        String listFieldID = null;
        String listSequence = null;
        String listClassName = null;
        String listClassField = null;
        String listClassFieldType = null;
        String listPosition = null;
        String listLength = null;
        String listValue = null;
        String listFormat = null;
        String listMandatory = null;
        String listDecimalPoint = null;
        byte[] byteTemp = new byte[p_MsgLen];
        int lengthGetByte = 0;
        int lengthForDecimal = 0;
        int lengthValue = 0;
        startPos = 0;
        boolean bNegative = false;

        for (int i = 0; i < aList.size(); i++) {
            if (startPos > aBytes.length) {
                break;
            } else {
                OBStpField obStpField = new OBStpField();
                obStpField = (OBStpField) aList.get(i);
                listFieldID = obStpField.getFieldID();
                listSequence = obStpField.getSequence();
                listClassName = obStpField.getClassName();
                listClassField = obStpField.getClassField();
                listClassFieldType = obStpField.getClassFieldType();
                listPosition = obStpField.getPosition();
                listLength = obStpField.getLength();
                listFormat = obStpField.getFormat();
                listMandatory = obStpField.getMandatory();
                listDecimalPoint = obStpField.getDecimalPoint();
                if ((listFormat.equals("P") || listFormat.equals("B")) && StringUtils.isNotBlank(listLength)) {
                    lengthGetByte = Integer.parseInt(listLength);
                    lengthValue = Integer.parseInt(listLength) * 2;
                } else {
                    lengthGetByte = Integer.parseInt(listLength);
                    lengthValue = Integer.parseInt(listLength);
                }
                if (StringUtils.isNotBlank(listDecimalPoint)) {
                    lengthForDecimal = Integer.parseInt(listDecimalPoint);
                }
                byteTemp = new byte[lengthGetByte];
                for (int k = 0; k < lengthGetByte; k++) {
                    byteTemp[k] = aBytes[startPos];
                    startPos++;
                    if (startPos >= aBytes.length) break;
                }

                listValue = StpUtil.Decode(byteTemp, lengthValue, listFormat.charAt(0), true, lengthForDecimal);
                listValue = listValue.trim();
                OBStpField obTempStpField = new OBStpField();
                obTempStpField.setFieldID(listFieldID);
                obTempStpField.setSequence(listSequence);
                obTempStpField.setClassName(listClassName);
                obTempStpField.setClassField(listClassField);
                obTempStpField.setClassFieldType(listClassFieldType);
                obTempStpField.setPosition(listPosition);
                obTempStpField.setLength(listLength);
                obTempStpField.setValue(listValue);
                obTempStpField.setFormat(listFormat);
                obTempStpField.setMandatory(listMandatory);
                obTempStpField.setDecimalPoint(listDecimalPoint);
                obStpList.add(obTempStpField);
            }
        }
        return obStpList;
    }

    /**
     * Decipher Message from byte[] to String
     *
     * @param aBytes byte[]
     * @param aList  List
     */

    public String decipherMessageToString(byte[] aBytes, List aList) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();

        String listFieldID = null;
        String listClassName = null;
        String listClassField = null;
        String listClassFieldType = null;
        String listPosition = null;
        String listLength = null;
        String listValue = null;
        String listFormat = null;
        String listMandatory = null;
        String listDecimalPoint = null;
        byte[] byteTemp = new byte[p_MsgLen];
        int lengthGetByte = 0;
        int lengthForDecimal = 0;
        int lengthValue = 0;
        startPos = 0;
        boolean bNegative = false;

        for (int i = 0; i < aList.size(); i++) {
            if (startPos >= aBytes.length) {
                break;
            } else {
                OBStpField obStpField = new OBStpField();
                obStpField = (OBStpField) aList.get(i);
                listFieldID = obStpField.getFieldID();
                listClassName = obStpField.getClassName();
                listClassField = obStpField.getClassField();
                listClassFieldType = obStpField.getClassFieldType();
                listPosition = obStpField.getPosition();
                listLength = obStpField.getLength();
                listFormat = obStpField.getFormat();
                listMandatory = obStpField.getMandatory();
                listDecimalPoint = obStpField.getDecimalPoint();
                if ((listFormat.equals("P") || listFormat.equals("B")) && StringUtils.isNotBlank(listLength)) {
                    lengthGetByte = Integer.parseInt(listLength);
                    lengthValue = Integer.parseInt(listLength) * 2;
                } else {
                    lengthGetByte = Integer.parseInt(listLength);
                    lengthValue = Integer.parseInt(listLength);
                }
                if (StringUtils.isNotBlank(listDecimalPoint)) {
                    lengthForDecimal = Integer.parseInt(listDecimalPoint);
                }

                byteTemp = new byte[lengthGetByte];
                for (int k = 0; k < lengthGetByte; k++) {
                    byteTemp[k] = aBytes[startPos];
                    startPos++;
                    if (startPos >= aBytes.length) break;
                }

                listValue = StpUtil.Decode(byteTemp, lengthValue, listFormat.charAt(0), true, lengthForDecimal);
                if (listFormat.equals("P")) {
                    listValue = StpUtil.unformatDecimal(listValue, lengthForDecimal, lengthValue);
                }
                sb.append(listValue);
            }
        }
        return sb.toString();
    }

}
