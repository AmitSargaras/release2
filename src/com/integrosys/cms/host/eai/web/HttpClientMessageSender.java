package com.integrosys.cms.host.eai.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.Validate;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAIProcessFailedException;
import com.integrosys.cms.host.eai.FileSystemAccessException;
import com.integrosys.cms.host.eai.core.MessageMarshallerFactory;
import com.integrosys.cms.host.eai.service.MessageSender;

/**
 * Implementation of {@link MessageSender} using Apache Commons Http Client
 * routine.
 * 
 * @author Chong Jun Yong
 * 
 */
public class HttpClientMessageSender implements MessageSender {

	private String url;

	private String messageAttributeName;

	private MessageMarshallerFactory messageMarshallerFactory;

	public String getUrl() {
		return url;
	}

	public String getMessageAttributeName() {
		return messageAttributeName;
	}

	public MessageMarshallerFactory getMessageMarshallerFactory() {
		return messageMarshallerFactory;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setMessageAttributeName(String messageAttributeName) {
		this.messageAttributeName = messageAttributeName;
	}

	public void setMessageMarshallerFactory(MessageMarshallerFactory messageMarshallerFactory) {
		this.messageMarshallerFactory = messageMarshallerFactory;
	}

	public void send(EAIMessage message) throws EAIMessageException {
		sendAndReceive(message);
	}

	public Object sendAndReceive(EAIMessage message) throws EAIMessageException {
		Validate.notNull(message, "'message' to be sent over must not be null.");

		String rawMessage = getMessageMarshallerFactory().marshall(message);

		NameValuePair msgNameValue = new NameValuePair(getMessageAttributeName(), rawMessage);

		PostMethod post = new PostMethod(getUrl());
		post.addParameter(msgNameValue);

		try {
			HttpClient httpClient = new HttpClient();
			int responseCode = 0;
			try {
				responseCode = httpClient.executeMethod(post);
			}
			catch (HttpException e) {
				throw new EAIProcessFailedException("failed to send message, header info [" + message.getMsgHeader()
						+ "]", e);
			}
			catch (IOException e) {
				throw new FileSystemAccessException("failed to send message, header info [" + message.getMsgHeader()
						+ "]", e);
			}

			String responseBody = null;
			if (responseCode == HttpServletResponse.SC_OK) {
				try {
					responseBody = post.getResponseBodyAsString();
				}
				catch (IOException e) {
					throw new FileSystemAccessException("failed to retrieve reponse from the post, header info ["
							+ message.getMsgHeader() + "]", e);
				}

				if (responseBody != null && responseBody.trim().length() > 0) {
					return responseBody;
				}
			}
			return null;
		}
		finally {
			post.releaseConnection();
		}
	}
}
