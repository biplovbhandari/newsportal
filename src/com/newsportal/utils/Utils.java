package com.newsportal.utils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.newsportal.model.auth.AuthUser;
import com.vividsolutions.jts.geom.Geometry;

public class Utils {
	
	private static final Logger LOG = Logger.getLogger(Utils.class.getName());
	
	public static String MD5(String content) throws NoSuchAlgorithmException {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] stringArray = md5.digest(content.getBytes("UTF-8"));
			StringBuffer stringBuffer = new StringBuffer();
			for (int i=0; i<stringArray.length; ++i) {
				stringBuffer.append(Integer.toHexString((stringArray[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return stringBuffer.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			LOG.severe(Utils.class.getSimpleName() + " Error: " + e.getMessage());
		}
		return null;
	}
	
	public static String getFullName(AuthUser authUser) {
		
		return authUser.getFirstName() + " " + authUser.getMiddleName() + " " + authUser.getLastName();
	}
	
	public static String getFieldType (Field field) {
		
		String fieldType = "none";
		if (field != null) {
			
			Class <?> classType = field.getType();
			
			// empty
			//Class empty[] = {};
			
			// non-empty
			//Class[] nonEmpty = new Class[1];
			
			if(classType.equals(String.class)){
				fieldType = "String";
			}
			else if(classType.equals(Character.class)){
				fieldType = "Character";
			}	
			else if(classType.equals(Integer.class)){
				fieldType = "Integer";
			}
			else if(classType.equals(Long.class)){
				fieldType = "Long";
			}
			else if(classType.equals(Boolean.class)){
				fieldType = "Boolean";
			}
			else if(classType.equals(Byte.class)){
				fieldType = "Byte";		
			}
			else if(classType.equals(Short.class)){
				fieldType = "Short";
			}
			else if(classType.equals(Float.class)){
				fieldType = "Float";
			}
			else if(classType.equals(List.class)){
				fieldType = "List";
			}
			else if(classType.equals(Date.class)){
				fieldType = "Date";
			}
			else if(classType.equals(Set.class)){
				fieldType = "Set";
			}
			else if(classType.equals(Geometry.class)){
				fieldType = "Geometry";
			}
		}
		
		return fieldType;
	}

	public static String getSetterMethodName(String attributeName) {
		String methodName = null;
		if (attributeName != null && attributeName.length() > 0) {
			methodName = "set" + Character.toUpperCase(attributeName.charAt(0));
			if (attributeName.length() > 1) {
				methodName = methodName + attributeName.substring(1);
			}
		}
		return methodName;
	}
}
