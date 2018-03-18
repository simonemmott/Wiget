package com.k2.Wiget.templateFactory;

import com.k2.Util.StringUtil;

/**
 * This Error class is the root of all unchecked errors defined by the k2 utilities
 * 
 * @author simon
 *
 */
public class TemplateWriterException extends Exception {

	private static final long serialVersionUID = -1782268655017879994L;


	/**
	 * Create a template writer exception with the given message
	 * @param message	The error message
	 * @param replacements The objects to convert to strings to replace instances of '{}' in the message
	 */
	public TemplateWriterException(String message, Object ... replacements) {
		super(StringUtil.replaceAll(message, "{}", replacements));
	}
	/**
	 * Create a template writer exception for the given throwable cause
	 * @param cause	The throwable that gave rise to this error
	 */
	public TemplateWriterException(Throwable cause) {
		super(cause);
	}
	/**
	 * Create a template writer exception for the given message and throwable cause
	 * @param message	The error message
	 * @param cause	The throwable cause
	 * @param replacements The objects to convert to strings to replace instances of '{}' in the message
	 */
	public TemplateWriterException(String message, Throwable cause, Object ... replacements) {
		super(StringUtil.replaceAll(message, "{}", replacements), cause);
	}
	/**
	 * Create a template writer exception for the given message and throwable cause defining whether the error can be suppressed and
	 * Whether the stack trace should be writable. 
	 * @param message	The error message
	 * @param cause	The throwable cause
	 * @param enableSuppression	True if the error can be suppressed
	 * @param writableStackTrace	True if the stack trace should be writable
	 * @param replacements The objects to convert to strings to replace instances of '{}' in the message
	 */
	public TemplateWriterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object ... replacements) {
		super(StringUtil.replaceAll(message, "{}", replacements), cause, enableSuppression, writableStackTrace);
	}

}
