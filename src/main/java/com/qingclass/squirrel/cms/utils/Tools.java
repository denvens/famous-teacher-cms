package com.qingclass.squirrel.cms.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Tools {

	private Tools() {
	}

	public static String mapToJson(@SuppressWarnings("rawtypes") Map map) {
		try {
			return new ObjectMapper().writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{}";
	}

	public static String stringEncoding (String s,String encoding) {

		try {
			return new String( s.getBytes(encoding) , encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String randomString(int digit){
		char[] arr = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','0','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		StringBuffer str = new StringBuffer();

		Random random = new Random();
		for(int i = 0 ; i < digit ; i++){
			str.append(arr[random.nextInt(arr.length)]);
		}

		return str.toString();
	}

	public static Map<String, Object> jsonToMap(String json) {
		if (StringUtils.isEmpty(json)) {
			json = "{}";
		}
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		HashMap<String, Object> map = null;
		try {
			map = mapper.readValue(json, typeRef);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static String mapToSimpleXml(Map<String, String> params) {

		StringBuilder builder = new StringBuilder();
		builder.append("<xml>\n");
		for (String key : params.keySet()) {
			builder.append("<").append(key).append("><![CDATA[").append(params.get(key)).append("]]></").append(key)
					.append(">\n");
		}
		builder.append("</xml>");

		return builder.toString();
	}

	public static Map<String, String> simpleXmlToMap(String xml) {

		Map<String, String> result = new HashMap<String, String>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Document document = null;
		try {
			document = builder.parse(new ByteArrayInputStream(xml.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (null == document) {
			return result;
		}

		Element root = document.getDocumentElement();

		NodeList nodes = root.getChildNodes();

		int len = nodes.getLength();

		for (int i = 0; i < len; i++) {
			Node node = nodes.item(i);
			if (1 != node.getNodeType()) {
				continue;
			}
			result.put(node.getNodeName(), node.getTextContent());
		}

		return result;
	}

	public static Map<String, String> arrayToMap(String[] pairs) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < pairs.length; i += 2) {
			map.put(pairs[i], pairs[i + 1]);
		}
		return map;
	}

	public static String md5(String input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] messageDigest = md.digest(input.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		// manually padding leading zeros
		String hashed = String.format("%32s", number.toString(16)).replace(' ', '0');
		return hashed;
	}

	public static String randomString32Chars() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	public static Map<String, Object> wrapReturn(boolean denied, boolean success, Object data, int code,
			String message) {

		Map<String, Object> template = new HashMap<String, Object>();
		template.put("denied", denied);
		template.put("success", success);
		template.put("data", data);
		template.put("code", code);
		template.put("message", message);

		return template;
	}

	/**
	 * 2018.11.08 xiaodong.zhu 如果经过多层代理，x-forwarded-for可能含有多个逗号分隔的ip地址 这里取用第一个
	 * 
	 * @param req
	 * @return
	 */
	public static String getClientIp(HttpServletRequest req) {
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		if (ip == null) {
			return null;
		}
		String[] ips = ip.split(",");
		return ips[0];
	}

	public static void setHttpPostParameters(HttpPost httpPost, Map<String, String> params) {
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		for (String key : params.keySet()) {
			String value = params.get(key);
			postParameters.add(new BasicNameValuePair(key, value));
		}
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getRequestedUrl(HttpServletRequest req) {

		String scheme = req.getScheme(); // http
		String serverName = req.getServerName(); // hostname.com
		int serverPort = req.getServerPort(); // 80
		String contextPath = req.getContextPath(); // /mywebapp
		String servletPath = req.getServletPath(); // /servlet/MyServlet
		String pathInfo = req.getPathInfo(); // /a/b;c=123
		String queryString = req.getQueryString(); // d=789

		// Reconstruct original requesting URL
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if (serverPort != 80 && serverPort != 443) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath).append(servletPath);

		if (pathInfo != null) {
			url.append(pathInfo);
		}
		if (queryString != null) {
			url.append("?").append(queryString);
		}
		return url.toString();
	}
	
	public static Map<String, Object> d() {
		return wrapReturn(true, false, "", 0, "Access denied. You are not signed-in.");
	}

	public static Map<String, Object> s() {
		return wrapReturn(false, true, "", 0, "");
	}
	public static Map<String, Object> s(Object data) {
		return wrapReturn(false, true, data, 0, "");
	}

	public static Map<String, Object> f() {
		return wrapReturn(false, false, "", -1, "");
	}
	
	public static Map<String, Object> f(String message) {
		return wrapReturn(false, false, "", -1, message);
	}

	public static Map<String, Object> f(Object data) {
		return wrapReturn(false, false, data, -1, "");
	}

	public static Map<String, Object> f(Object data, int code) {
		return wrapReturn(false, false, data, code, "");
	}

	public static Map<String, Object> f(Object data, int code, String message) {
		return wrapReturn(false, false, data, code, message);
	}

}
