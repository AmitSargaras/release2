package com.integrosys.cms.host.stp.castor;

import com.integrosys.cms.host.stp.FileSystemAccessException;
import com.integrosys.cms.host.stp.STPMessage;
import com.integrosys.cms.host.stp.XmlMappingException;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.core.MessageMarshallerFactory;
import com.integrosys.cms.host.stp.support.ISTPHeaderConstant;
import com.integrosys.cms.host.stp.support.STPHeaderHelper;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * Implementation of {@link com.integrosys.cms.host.stp.core.MessageMarshallerFactory} using castor routine, the
 * XML mapping resource to process unmarshalling/marshalling is based on the
 * <b>message id</b> of the message, either in raw xml form or STPMessage objet
 * form.
 *
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 *
 */
public class CastorMessageIdBaseMessageMarshallerFactory implements MessageMarshallerFactory {

    public CastorMessageIdBaseMessageMarshallerFactory(){}

    public CastorMessageIdBaseMessageMarshallerFactory(Map map){
        this.messageIdMarshallerMap=  map;
        this.messageIdUnmarshallerMap = map;
    }


	private Map messageIdMarshallerMap;

	private Map messageIdUnmarshallerMap;

	/**
	 * <p>
	 * The preconfigured marshaller to be used to marshall the Object to XML
	 * String.
	 * <p>
	 * Key is the message id of the Message object, value is the marshaller
	 * instance.
	 *
	 * @param messageIdMarshallerMap message id - marshaller map
	 */
	public void setMessageIdMarshallerMap(Map messageIdMarshallerMap) {
		this.messageIdMarshallerMap = messageIdMarshallerMap;
	}

	/**
	 * <p>
	 * The preconfigured unmarshaller to be used to unmarshall the XML string to
	 * Message Object that going to be processed.s
	 * <p>
	 * Key is the message id of the Message object, value is the unmarshaller
	 * instance.
	 *
	 * @param //messageIdMarshallerMap message id - unmarshaller map
	 */
	public void setMessageIdUnmarshallerMap(Map messageIdUnmarshallerMap) {
		this.messageIdUnmarshallerMap = messageIdUnmarshallerMap;
	}

	public String marshall(STPMessage stpMessage) throws XmlMappingException, FileSystemAccessException {
		String messageId = stpMessage.getMsgHeader().getMessageId();

		synchronized (this.messageIdMarshallerMap) {
			Marshaller marshaller = (Marshaller) this.messageIdMarshallerMap.get(messageId);

			if (marshaller == null) {
				throw new FileSystemAccessException("failed to find marshaller for message id [" + messageId + "]");
			}

			try {
				StringWriter sw = new StringWriter();
				marshaller.setWriter(sw);
				marshaller.marshal(stpMessage);

				return sw.toString();
			}
			catch (IOException ex) {
				throw new FileSystemAccessException("failed to set XML serializer as document handler", ex);
			}
			catch (MarshalException ex) {
				throw new MarshallingFailureException("failed to marshall message object [" + stpMessage + "] to XML",
						ex);
			}
			catch (ValidationException ex) {
				throw new MarshallingFailureException("the message object supplied [" + stpMessage
						+ "] doesn't tally with the one in marshaller [" + marshaller + "]", ex);
			}
		}
	}

	public STPMessage unmarshall(String rawMessage) throws XmlMappingException, FileSystemAccessException {
		String messageId = STPHeaderHelper.getHeaderValue(rawMessage, ISTPHeaderConstant.MESSAGE_ID);
		synchronized (this.messageIdUnmarshallerMap) {
			Unmarshaller unmarshaller = (Unmarshaller) this.messageIdUnmarshallerMap.get(messageId);

			if (unmarshaller == null) {
				throw new FileSystemAccessException("failed to find unmarshaller for message id [" + messageId + "]");
			}

			try {
				return (STPMessage) unmarshaller.unmarshal(new StringReader(rawMessage));
			}
			catch (MarshalException ex) {
				throw new UnmarshallingFailureException("failed to unmarshall message stream to message object", ex);
			}
			catch (ValidationException ex) {
				throw new UnmarshallingFailureException("the message stream supplied [" + rawMessage
						+ "] doesn't tally with the one in unmarshaller [" + unmarshaller + "]", ex);
			}
		}
	}
}