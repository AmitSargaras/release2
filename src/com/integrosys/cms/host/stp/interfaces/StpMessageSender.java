package com.integrosys.cms.host.stp.interfaces;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.host.mq.IMessageSenderProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Aug 25, 2008
 * Time: 1:21:31 PM
 * <p/>
 * MBase socket messaging for send and receive operation
 *
 * @author Andy Wong
 * @since 24 Sept 2008
 */
public class StpMessageSender implements IMessageSenderProxy {
    private int p_MsgLen = 4096;                //Buffer Length
    private int p_TimeOut = 0;                   //Timeout
    private int p_Port = 0;                   //Port
    private String p_IPAddress = null;                //IP Address
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public StpMessageSender() {
        init();
    }

    protected void init() {
        String timeOut = PropertyManager.getValue("stp.messageSender.timeout");
        logger.info("init timeOut : " + timeOut);
        if (timeOut != null) {
            p_TimeOut = Integer.parseInt(timeOut);
        }
        String port = PropertyManager.getValue("stp.messageSender.portNumber");
        logger.info("init port : " + port);
        if (port != null) {
            p_Port = Integer.parseInt(port);
        }
        p_IPAddress = PropertyManager.getValue("stp.messageSender.ipAddress");
        logger.info("init p_IPAddress : " + p_IPAddress);
    }

    /**
     * Socket Communication with host
     *
     * @param request message in byte[] and trx unique id
     * @return response message in byte[]
     */
    public Object sendAndReceive(Object in, String uid) {
        boolean bEOM = false;
        boolean bHDR = false;
        int byteLen = 0;
        int loop = 1;
        int TmpIndex = 0;
        long TmpMsgLen = 0;
        long intByteRec = 0;
        byte[] byteTemp = new byte[p_MsgLen];
        byte[] p_byteReceiveHostComm = null;

        Socket echoSocket = null;
        DataOutputStream dout = null;
        DataInputStream din = null;

        try {
            byte[] msgReceive = (byte[]) in;
            byteLen = msgReceive.length;
            // Initialise Socket and Send
            echoSocket = new Socket(p_IPAddress, p_Port);
            echoSocket.setSoTimeout(p_TimeOut * 1000);
            dout = new DataOutputStream(echoSocket.getOutputStream());

            dout.write(msgReceive, 0, byteLen);
            dout.flush();

            din = new DataInputStream(echoSocket.getInputStream());
            //Andy Wong: initialize response byte before looping
            p_byteReceiveHostComm = new byte[p_MsgLen];

            /*get response from server*/
            while (!bEOM) {
                logger.debug("##### No of loop retrieving response = " + loop++);
                intByteRec = din.read(byteTemp, 0, p_MsgLen);

                if ((intByteRec == 0) || (intByteRec == -1)) break;
                for (int i = 0; i < intByteRec; i++) {
                    Arrays.fill(p_byteReceiveHostComm, TmpIndex, TmpIndex + 1, byteTemp[i]);
                    TmpIndex++;
                }

                if (!bHDR) {
                    if (TmpIndex > 4) {
                        TmpMsgLen = toDecimal(p_byteReceiveHostComm, 4 * 2, 16);
                        bHDR = true;
                    }
                }

                if (((TmpIndex >= (TmpMsgLen + 4)) && (bHDR)) || (TmpIndex == p_MsgLen)) {
                    logger.debug("##### End of message: Socket header length = " + TmpMsgLen + "; Message length = " + TmpIndex);
                    bEOM = true;
                    break;
                }
            }

            dout.close();
            din.close();
            echoSocket.close();
        } catch (IOException e) {
            logger.error("IOException caught while processing request with trxUID: " + uid, e);
        } finally {
            try {
                dout.close();
                din.close();
                echoSocket.close();
            } catch (Exception e) {
            }
        }
        return p_byteReceiveHostComm;
    }

    /* Convert from Hex or Binary or Octal to Decimal
    *  radix 16 indicate Hex
    *  radix 2 indicate  Binary
    *  radix 8 indicate  Octal
    */

    private long toDecimal(byte[] InBuf, int LenBuf, int radix) {
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
}
