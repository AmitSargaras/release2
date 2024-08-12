package com.integrosys.base.uiinfra.common;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>
 * Abstract implementation of {@link org.apache.struts.action.Action} to
 * generate the option list html output.
 * <p>
 * Output will be in XML format, and all generated <b>&lt;option&gt;</b> tags
 * will be embedded inside <b>&lt;optionlist&gt;</b>(default) tag, or can be set
 * via {@link #setOptionListTagName(String)}.
 * <p>
 * Implementation note: Consider to use an AJAX way to use this action to
 * retrieve the options without start a new request to the server. Then the
 * javascript must able to process the options tag in &lt;optionlist&gt;.
 * @author Chong Jun Yong
 * @see #beforeProcessOptions(StringBuffer)
 * @see #afterProcessOptions(StringBuffer)
 * 
 */
public abstract class AbstractOptionsListAction extends Action {

	private String optionListTagName = "optionlist";

	/**
	 * To set the tag name to enclose all the option tags generated. default
	 * value is "optionList".
	 * @param optionListTagName tag name to enclose all the option tags
	 */
	public void setOptionListTagName(String optionListTagName) {
		this.optionListTagName = optionListTagName;
	}

	public final ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!preCheck(form, request)) {
			return null;
		}

		List optionsList = generateOptionsList(form, request);

		StringBuffer optionsBuf = new StringBuffer();
		optionsBuf.append("<?xml version=\"1.0\"?>").append("<").append(this.optionListTagName).append(">");

		beforeProcessOptions(optionsBuf);

		for (Iterator itr = optionsList.iterator(); itr.hasNext();) {
			Object option = itr.next();
			String label = extractLabel(option);
			String value = extractValue(option);
			optionsBuf.append("<option label=\"" + StringEscapeUtils.escapeHtml(label) + "\" value=\""
					+ StringEscapeUtils.escapeHtml(value) + "\" />");
		}

		afterProcessOptions(optionsBuf);

		optionsBuf.append("</").append(this.optionListTagName).append(">");

		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().print(optionsBuf.toString());
		response.flushBuffer();

		return null;
	}

	/**
	 * Subclass to generate a list of objects that required to generate the
	 * option tags
	 * @param form the struts action form
	 * @param request the http servlet request.
	 * @return list of object required for the generation of the option tags,
	 *         <b>must not</b> return a null object, at least an empty list.
	 */
	protected abstract List generateOptionsList(ActionForm form, HttpServletRequest request);

	/**
	 * Extract the label for each object return from
	 * {@link #generatedOptionsList(ActionForm, HttpServletRequest)} to be used
	 * for a single option tag
	 * @param option the option object to be extracted for it's label
	 * @return label of the option object
	 */
	protected abstract String extractLabel(Object option);

	/**
	 * Extract the value for each object return from
	 * {@link #generatedOptionsList(ActionForm, HttpServletRequest)} to be used
	 * for a single option tag
	 * @param option the option object to be extracted for it's value
	 * @return value of the option object
	 */
	protected abstract String extractValue(Object option);

	/**
	 * Subclass to run any checking before really go into the logic to construct
	 * option list. Default return true.
	 * @param form the action form
	 * @param request the http servlet request
	 * @return whether pass any validation specified here
	 */
	protected boolean preCheck(ActionForm form, HttpServletRequest request) {
		return true;
	}

	/**
	 * Do something <b>before</b> append the options tag into optionList tag,
	 * could mean add the extra option.
	 * @param optionsBuf the string buffer to construct the html output for the
	 *        option list
	 */
	protected void beforeProcessOptions(StringBuffer optionsBuf) {
	}

	/**
	 * Do something <b>after</b> append the options tag into optionList tag,
	 * could mean add the extra option.
	 * @param optionsBuf the string buffer to construct the html output for the
	 *        option list
	 */
	protected void afterProcessOptions(StringBuffer optionsBuf) {
	}

}
