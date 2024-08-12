package com.integrosys.cms.host.stp.common;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * @author Andy Wong
 * @since 25 Sept 2008
 */
public class StpUtil implements Serializable {
    // CONSTANT
    private static final int CONS_HEX = 16;
    private static final int CONS_UNSIGNCHAR = 256;

    /* Thai EBCDIC to Ascii */
    private static final short ETOA[] = {
            0x00, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f,
            0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f,
            0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f,
            0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f,
            0x20, 0x7f, 0xa1, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0x5b, 0xfc, 0x2e, 0x3c, 0x28, 0x2b, 0x20,
            0x26, 0x7f, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0x5d, 0x21, 0x24, 0x2a, 0x29, 0x3b, 0xfd,
            0x2d, 0x2f, 0xaf, 0xb0, 0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0x5e, 0xfe, 0x2c, 0x25, 0x5f, 0x3e, 0x3f,
            0xdf, 0x7f, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0x60, 0x3A, 0x23, 0x40, 0x27, 0x3d, 0x22,
            0x7f, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2,
            0x7f, 0x6a, 0x6b, 0x6c, 0x6d, 0x6e, 0x6f, 0x70, 0x71, 0x72, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8,
            0x7f, 0x7e, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7a, 0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce,
            0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8, 0xf9, 0xcf, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4,
            0x7b, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x7f, 0xd5, 0xd6, 0xd7, 0xd8, 0xd9,
            0x7d, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x50, 0x51, 0x52, 0xda, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4,
            0x5c, 0x7f, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5a, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0xea,
            0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0xeb, 0xec, 0xed, 0x7f, 0x7f, 0x7f
    };

    /* Ascii to Thai EBCDIC */
    private static final short ATOE[] = {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f,
            0x40, 0x5a, 0x7f, 0x7b, 0x5b, 0x6c, 0x50, 0x7d, 0x4d, 0x5d, 0x5c, 0x4e, 0x6b, 0x60, 0x4b, 0x61,
            0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8, 0xf9, 0x7a, 0x5e, 0x4c, 0x7e, 0x6e, 0x6f,
            0x7c, 0xc1, 0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xd1, 0xd2, 0xd3, 0xd4, 0xd5, 0xd6,
            0xd7, 0xd8, 0xd9, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0xad, 0xe0, 0xbd, 0x5f, 0x6d,
            0x79, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96,
            0x97, 0x98, 0x99, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xc0, 0x6a, 0xd0, 0xa1, 0xff,
            0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff,
            0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff,
            0xff, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x52, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x62,
            0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x8a, 0x8b, 0x8c,
            0x8d, 0x8e, 0x8f, 0x9a, 0x9b, 0x9C, 0x9d, 0x9e, 0x9f, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xba,
            0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xda, 0xff, 0xff, 0xff, 0xff, 0xff,
            0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef, 0xfa, 0xfb, 0xfc, 0xff, 0xff,
            0xb0, 0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff
    };

    /**
     * Creates new Util
     */
    public StpUtil() {
    }


    /*  Note:
     *  Because of byte range form -127 to 128 therfore the negative value
     *  cause problem on ATOE and ETOA array
     */

    /**
     * Convert from byte[] to char[]*
     */
    public static char[] ConvertToChar(byte[] inBuf, int lenBuf) {
        char[] outBuf = new char[lenBuf];
        int Temp = 0;

        for (int i = 0; i < lenBuf; i++) {
            if (inBuf[i] < 0) {
                Temp = CONS_UNSIGNCHAR + inBuf[i];
            } else {
                Temp = inBuf[i];
            }
            outBuf[i] = (char) Temp;
        }
        return outBuf;
    }

    /**
     * Convert from Ascii byte[] to EBCDIC byte[] *
     */
    public final static byte[] ConvertATOE(byte[] inBuf, int lenBuf) {
        byte[] outBuf = new byte[lenBuf];
        int intPos = 0;

        for (int i = 0; i < lenBuf; i++) {
            if (inBuf[i] < 0) intPos = inBuf[i] + CONS_UNSIGNCHAR;
            else intPos = inBuf[i];

            outBuf[i] = (byte) ATOE[intPos];
        }
        return outBuf;
    }

    /**
     * Convert EBCDIC byte[] to ASCII
     * Because the byte will have -ve value, this will cause problem when fill
     * into the array. Therefore we ensure the value is +ve
     */
    public static char[] ConvertETOA(byte[] inBuf, int lenBuf) {
        char[] outBuf = new char[lenBuf];
        int intPos;

        for (int i = 0; i < lenBuf; i++) {
            if (inBuf[i] < 0) intPos = inBuf[i] + CONS_UNSIGNCHAR;
            else intPos = inBuf[i];
            outBuf[i] = (char) ETOA[intPos];
        }
        return outBuf;
    }

    /**
     * Decode Host Message byte[] to String for front End
     *
     * @ typeBuf  : 'A' - Alpha Numeric, 'S' - Numeric, 'P' - Packed, 'B' - Binary, 'H'- Hex Stream, 'X'- no conversion
     * @ lenBuf   : length according to front End Data structure
     */
    public static String Decode(byte[] inBuf, int lenBuf, char typeBuf, boolean IsEBCDIC, int DecimalPoint) throws UnsupportedEncodingException {

        String rtnStr = "";
        String strTemp = "";
        int intTemp = 0;
        boolean bNegative = false;
        switch (Character.toUpperCase(typeBuf)) {
            case 'P':
                strTemp = Unpack(inBuf, lenBuf);
                rtnStr = formatDecimal(strTemp, DecimalPoint);
                break;
            case 'H':
                rtnStr = UnHex(inBuf, lenBuf);
                break;
            case 'S':  // Convert to Ascii Numeric
                if (((byte) inBuf[lenBuf - 1] & (byte) 0xF0) == (byte) 0xD0) {
                    //-ve
                    inBuf[lenBuf - 1] = (byte) (inBuf[lenBuf - 1] | 0xF0);
                    bNegative = true;
                }

                if (IsEBCDIC) {
                    strTemp = new String(inBuf, "Cp838");
                    //strTemp = String.valueOf(ConvertETOA(inBuf, lenBuf));
                } else strTemp = String.valueOf(ConvertToChar(inBuf, lenBuf));

                if (bNegative) {
                    rtnStr = '-' + formatDecimal(strTemp, DecimalPoint);
                } else rtnStr = formatDecimal(strTemp, DecimalPoint);

                break;
            case 'A':  // Alpha Numeric
                if (IsEBCDIC) {
                    rtnStr = new String(inBuf, "Cp838");
                    //rtnStr = String.valueOf(ConvertETOA(inBuf, lenBuf));
                } else rtnStr = String.valueOf(ConvertToChar(inBuf, lenBuf));
                break;
            case 'B':  // Binary to Decimal String
                long valDecimal = toDecimal(inBuf, lenBuf, CONS_HEX);
                rtnStr = Long.toString(valDecimal);
                break;
            case 'X':
                rtnStr = String.valueOf(ConvertToChar(inBuf, lenBuf));
                break;
        }
        return rtnStr;
    }

    /**
     * Encode to Host Message Format
     * Type 'A' - alphanumeric. Initialize to all blank. Ascii = 0x20
     * Type 'B' - Binary. For positive number only.
     * Type 'P' - Pack data. The lenght in is the actual length in Front End Screen
     * - for +ve number, len = odd value; -ve number, len = even value (one byte for '-' sign
     * Type 'S' - numeric. Initilize to '0'. Ascii = 0x30
     * e.g: Numeric 0x49 -> 0xF1
     * Type 'H' - Hex String (2 byte) to value(1 byte) e.g String FF->0xFF
     * Type 'X' - String with no conversion
     */
    public static byte[] Encode(String strIn, char Type, int Len, boolean ToEBCDIC, int DecimalPoint) throws UnsupportedEncodingException {
        byte[] byteInData = new byte[Len];
        byte[] byteTmpData = new byte[Len];
        byte[] byteOutData = null;
        int i;
        boolean bNegative = false;
        int NumofZero = 0;
        String strTemp = null;
        switch (Character.toUpperCase(Type)) {
            case 'P':
                if (StringUtils.isNotEmpty(strIn)) {
                    strTemp = unformatDecimal(strIn, DecimalPoint, Len);
                }
                byteOutData = Pack(strTemp, Len);
                break;
            case 'H':
                strIn = CheckStringLength(strIn, Len * 2);
                byteOutData = Hex(strIn, Len);
                break;

            case 'S':
                byteOutData = new byte[Len];

                if (StringUtils.isNotEmpty(strIn)) {

                    if (strIn.indexOf("-", 0) == 0) { // -ve number
                        strIn = strIn.substring(1, strIn.length());
                        bNegative = true;
                    }

                    strIn = CheckStringLength(strIn, Len);

                    strTemp = unformatDecimal(strIn, DecimalPoint, Len);
                    strIn = strTemp;
                    NumofZero = Len - strIn.length();
                } else {
                    strIn = "";
                    NumofZero = Len;
                }

                for (i = 0; i < NumofZero; i++) strIn = '0' + strIn;

                if (ToEBCDIC) {
                    byteOutData = strIn.getBytes("Cp838");
                    //byteOutData = ConvertATOE(strIn.getBytes(), strIn.length());
                } else byteOutData = strIn.getBytes();

                if (bNegative) {
                    byteOutData[Len - 1] = (byte) (byteOutData[Len - 1] & (byte) 0xDF);
                }

                break;

            case 'A':  // Alpha Numeric
                byteOutData = new byte[Len];
                strIn = CheckStringLength(strIn, Len);
                if (StringUtils.isNotEmpty(strIn)) {
                    NumofZero = Len - strIn.length();
                } else {
                    strIn = "";
                    NumofZero = Len;
                }

                for (i = 0; i < NumofZero; i++) strIn = strIn + ' ';
                if (ToEBCDIC) {
                    byteOutData = strIn.getBytes("Cp838");
                    //byteOutData = ConvertATOE(strIn.getBytes(), strIn.length());
                } else byteOutData = strIn.getBytes();
                break;

            case 'B':  // Binary
                byteOutData = new byte[Len / 2];
                for (i = 0; i < Len / 2; i++) byteOutData[i] = 0x00;

                if (StringUtils.isNotEmpty(strIn)) {
                    long intTemp = Long.parseLong(strIn);
                    strTemp = Long.toHexString(intTemp);

                    NumofZero = Len - strTemp.length();
                    for (i = 0; i < NumofZero; i++) strTemp = '0' + strTemp;
                    byteOutData = toBinary(strTemp.toUpperCase());
                }
                break;

            case 'X':  // NO CONVERSION
                byteOutData = new byte[Len];
                strIn = CheckStringLength(strIn, Len);
                byteOutData = strIn.getBytes();
                break;
        }

        return byteOutData;
    }

    /**
     * Put in the decimal point with corresponding host decimal point
     * Example:  	11 2 means 123456789.12 (Front End) => 12345678912 in (Host)
     * Receive from host 	12345678912
     * Send to front end 	123456789.12
     */
    public static String formatDecimal(String strIn, int DecIn) {
        String strTemp = "";
        if (strIn == null) return strIn;
        if (strIn == "") return strIn;

        if (DecIn == 0) return strIn;
        strTemp = strIn.substring(0, strIn.length() - DecIn) + "." + strIn.substring(strIn.length() - DecIn, strIn.length());
        return strTemp;
    }

    /**
     * Take out the decimal point with corresponding host decimal point
     * Example:  	11 2 means 123456789.12 (Front End) => 12345678912 in (Host)
     * Receive from front end	123456789.12
     * Send to host 		 	12345678912
     */
    public static String unformatDecimal(String strIn, int DecIn, int FieldLen) {
        String strTemp = "";
        int intPos = 0;
        int intDecimal = 0;
        int i = 0;

        if (DecIn == 0) return strIn;    // no decimal define

        intPos = strIn.lastIndexOf('.');
        if (intPos == -1) {// No decimal Point
            if (DecIn != 0) {
                for (i = 0; i < DecIn; i++) {
                    strIn = strIn + '0';
                }
            }
            return strIn;
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
            return strTemp;
        }
    }

    /**
     * Convert from String to binary byte[]
     * The String that passed in is in Hex String. Is already formatted according to
     * the binary length (Even Number)
     */
    public static byte[] toBinary(String strIn) {

        byte[] byteTmp = new byte[strIn.length() / 2];
        long intTemp = 0;

        for (int i = 0; i < strIn.length() / 2; i++) {
            intTemp = Long.parseLong(strIn.substring(i * 2, (i * 2) + 2), CONS_HEX);
            byteTmp[i] = (byte) intTemp;
        }
        return byteTmp;
    }


    /**
     * Pack the String(numeric) to byte[] . The lenBuf that pass in is "unpack length" + sign
     * store in DB. For +ve number = Input lenght = 7, -ve number Input lenght = 8
     */
    public static byte[] Pack(String inBuf, int lenBuf) {
        byte[] bytePack = new byte[lenBuf / 2];
        boolean bNegative = false;
        int j = 0;
        int i = 0;

        for (i = 0; i < (lenBuf / 2); i++) bytePack[i] = 0x00;

        if (inBuf != null) {
            if (inBuf.indexOf("-", 0) == 0) { // -ve number
                inBuf = inBuf.substring(1, inBuf.length());
                bNegative = true;
            }

            int tempInt = 0;

            int NumofZero = lenBuf - 1 - inBuf.length();
            for (i = 0; i < NumofZero; i++) inBuf = '0' + inBuf;

            tempInt = 0;
            for (i = 0; i < inBuf.length(); i += 2) {
                if ((i + 2) > inBuf.length()) {
                    tempInt = Integer.parseInt(inBuf.substring(i, i + 1) + "0", CONS_HEX);
                } else {
                    tempInt = Integer.parseInt(inBuf.substring(i, i + 2), CONS_HEX);
                }
                bytePack[j] = (byte) tempInt;
                j++;
            }
        }

        if (bNegative) // Negative Value
        {
            bytePack[(lenBuf / 2) - 1] = (byte) (bytePack[(lenBuf / 2) - 1] | (byte) 0x0D);
        } else            // Positive Value
        {
            bytePack[(lenBuf / 2) - 1] = (byte) (bytePack[(lenBuf / 2) - 1] | (byte) 0x0F);
        }
        return bytePack;
    }

    /**
     * Unpack the byte[] (pack Data), convert it to String with the
     * last character being chop off.
     * The lenBuf that passed in is the unpack length read from Database
     */
    public static String Unpack(byte[] inBuf, int lenBuf) {
        String outStr = "";
        int high = 0;
        int low = 0;
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        for (int i = 0; i < lenBuf / 2; i++) {
            high = ((inBuf[i] & 0xf0) >> 4);
            low = (inBuf[i] & 0x0f);
            outStr = outStr + hexChars[high] + hexChars[low];
        }

        outStr = outStr.substring(0, outStr.length() - 1);
        if (low == 0x0D) { // For negative number
            outStr = "-" + outStr;
        }
        return outStr;
    }

    /**
     * Convert from Hex or Binary or Octal to Decimal
     * radix 16 indicate Hex
     * radix 2  indicate  Binary
     * radix 8  indicate  Octal
     */
    public static long toDecimal(byte[] InBuf, int LenBuf, int radix) {

        long valDecimal = 0;
        long high = 0;
        long low = 0;

        for (int i = 0; i < LenBuf / 2; i++) {

            high = (long) (((InBuf[i] & 0xf0) >> 4) * Math.pow(radix, (LenBuf - 1) - (i * 2)));
            low = (long) (((InBuf[i] & 0x0f)) * Math.pow(radix, (LenBuf - 2) - (i * 2)));
            valDecimal = valDecimal + high + low;
        }
        return valDecimal;
    }


    /**
     * Format from byte[] as hex string for display purposes*
     */
    public static String toStringHex(byte[] InBuf, int LenBuf) {
        int intTemp = 0;
        String strTemp = "";

        for (int i = 0; i < LenBuf; i++) {

            if (InBuf[i] < 0) intTemp = InBuf[i] + CONS_UNSIGNCHAR;
            else intTemp = InBuf[i];
            strTemp = strTemp + Integer.toHexString(intTemp) + ":";
        }
        return strTemp;
    }

    /**
     * Convert the String(Hex String to byte stream.
     * Example: FFCC12->3 byte with byte[0]= 0xFF,byte[1]= 0xCC,byte[2]= 0x12
     * inBuf  = String receive
     * lenBuf = actual length in backend. Therefore inBuf length must be 2x lenBuf
     */
    public static byte[] Hex(String inBuf, int lenBuf) {

        byte[] bytePack = new byte[lenBuf];
        int j = 0;
        int i = 0;

        for (i = 0; i < (lenBuf); i++) bytePack[i] = 0x00;

        if (inBuf != null) {
            int tempInt = 0;

            int NumofZero = (lenBuf * 2) - inBuf.length();
            for (i = 0; i < NumofZero; i++) inBuf = '0' + inBuf;

            tempInt = 0;
            for (i = 0; i < inBuf.length(); i += 2) {
                if ((i + 2) > inBuf.length()) {
                    tempInt = Integer.parseInt(inBuf.substring(i, i + 1) + "0", CONS_HEX);
                } else {
                    tempInt = Integer.parseInt(inBuf.substring(i, i + 2), CONS_HEX);
                }
                bytePack[j] = (byte) tempInt;
                j++;
            }
        }

        return bytePack;
    }

    /**
     * Convert host Hex to String
     * The lenBuf that passed in is String length / 2
     */
    public static String UnHex(byte[] inBuf, int lenBuf) {

        String outStr = "";
        int high = 0;
        int low = 0;
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        for (int i = 0; i < lenBuf; i++) {
            high = ((inBuf[i] & 0xf0) >> 4);
            low = (inBuf[i] & 0x0f);
            outStr = outStr + hexChars[high] + hexChars[low];
        }
        return outStr;
    }

    /**
     * This is to check the String length. If the String is longer then the actual length
     * The string will be cut off
     */
    public static String CheckStringLength(String inStr, int Len) {
        int intExtra = 0;
        if (inStr != null) {
            if (inStr.length() > Len) {
                inStr = inStr.substring(0, Len);
            }
        }
        return inStr;
    }
}
