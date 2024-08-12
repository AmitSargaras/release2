package com.integrosys.cms.host.eai.castor;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.FileSystemAccessException;
import com.integrosys.cms.host.eai.XmlMappingException;
import com.integrosys.cms.host.eai.core.MessageMarshallerFactory;
import com.integrosys.cms.host.eai.support.EAIHeaderHelper;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;

/**
 * Implementation of {@link MessageMarshallerFactory} using castor routine, the
 * XML mapping resource to process unmarshalling/marshalling is based on the
 * <b>message id</b> of the message, either in raw xml form or EAIMessage objet
 * form.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CastorMessageIdBaseMessageMarshallerFactory implements MessageMarshallerFactory {

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
	 * @param messageIdMarshallerMap message id - unmarshaller map
	 */
	public void setMessageIdUnmarshallerMap(Map messageIdUnmarshallerMap) {
		this.messageIdUnmarshallerMap = messageIdUnmarshallerMap;
	}

	public String marshall(EAIMessage eaiMessage) throws XmlMappingException, FileSystemAccessException {
		String messageId = eaiMessage.getMsgHeader().getMessageId();

		synchronized (this.messageIdMarshallerMap) {
			Marshaller marshaller = (Marshaller) this.messageIdMarshallerMap.get(messageId);

			if (marshaller == null) {
				throw new FileSystemAccessException("failed to find marshaller for message id [" + messageId + "]");
			}

			try {
				StringWriter sw = new StringWriter();
				marshaller.setWriter(sw);
				marshaller.marshal(eaiMessage);

				return sw.toString();
			}
			catch (IOException ex) {
				throw new FileSystemAccessException("failed to set XML serializer as document handler", ex);
			}
			catch (MarshalException ex) {
				throw new MarshallingFailureException("failed to marshall message object [" + eaiMessage + "] to XML",
						ex);
			}
			catch (ValidationException ex) {
				throw new MarshallingFailureException("the message object supplied [" + eaiMessage
						+ "] doesn't tally with the one in marshaller [" + marshaller + "]", ex);
			}
		}
	}

	public EAIMessage unmarshall(String rawMessage) throws XmlMappingException, FileSystemAccessException {
		String messageId = EAIHeaderHelper.getHeaderValue(rawMessage, IEAIHeaderConstant.MESSAGE_ID);

		synchronized (this.messageIdUnmarshallerMap) {
			Unmarshaller unmarshaller = (Unmarshaller) this.messageIdUnmarshallerMap.get(messageId);

			if (unmarshaller == null) {
				throw new FileSystemAccessException("failed to find unmarshaller for message id [" + messageId + "]");
			}

			try {
				return (EAIMessage) unmarshaller.unmarshal(new StringReader(rawMessage));
			}
			catch (MarshalException ex) {
				throw new UnmarshallingFailureException("failed to unmarshall message stream to message object", ex);
			}
			catch (ValidationException ex) {
				throw new MarshallingFailureException("the message stream supplied [" + rawMessage
						+ "] doesn't tally with the one in unmarshaller [" + unmarshaller + "]", ex);
			}
		}
	}
}
