package com.qingclass.squirrel.cms.controller.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public class MIMEType {

	protected static final Map<String, String> types = new HashMap<String, String>(){{
		put( "image/gif", ".gif" );
		put( "image/jpeg", ".jpg" );
		put( "image/jpg", ".jpg" );
		put( "image/png", ".png" );
		put( "image/bmp", ".bmp" );
	}};
	
	public static String getSuffix ( String mime ) {
		return MIMEType.types.get( mime );
	}
	
}
