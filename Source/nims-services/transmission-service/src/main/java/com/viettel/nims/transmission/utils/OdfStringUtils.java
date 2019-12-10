package com.viettel.nims.transmission.utils;

import java.util.regex.Pattern;

/**
 * OdfStringUtils	Version 1.0		Date: 22-10-2019	Copyright	 Modification	Logs:
 *
 * DATE		 				AUTHOR		 		DESCRIPTION
 * -----------------------------------------------------------------------
 * 22-10-2019 				HungNV 				Create
 */
public class OdfStringUtils {

	private OdfStringUtils() {
	}

	// =========================================================================
	// Logger
	// ==============

	// =========================================================================
	// Properties
	// ==========

	/** ODF name */
	public static final String ALLOW_UPDATE = "Y";

	/** ODF name */
	public static final String ODF = "ODF";

	/** ODF name */
	public static final String PATTERN_DOT = "\\.";

	/** Vendor */
	public static final String VENDOR = "VENDOR";

	/** Cat owner */
	public static final String CAT_OWNER = "CAT_OWNER";

	/** Pattern number two digit */
	public static Pattern ONE_DIGIT_NUMBER  = Pattern.compile("\\d{1}");

	/** Pattern number two digit */
	public static Pattern TWO_DIGIT_NUMBER  = Pattern.compile("\\d{2}");

	/** Pattern attenuation */
	public static Pattern PATTERN_ATTENUATION  = Pattern.compile("[^-]\\d+\\.\\d+");

	/** Pattern number to 1 - 5 digit  */
	public static Pattern ONE_TO_FIVE_DIGIT_NUMBER  = Pattern.compile("\\d{1,5}");

	/** Pattern number two digit  */
	public static Pattern PATTERN_DATETIME_DDMMYYYY  = Pattern.compile("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$");
}
