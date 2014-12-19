/* 
 * Copyright 2012 Lokesh Jain.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jain.addon;


public final class StringHelper {
	private StringHelper() {}

	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	public static boolean isEmptyWithTrim(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static boolean isNotEmptyWithTrim(String s) {
		return !isEmptyWithTrim(s);
	}

	public static String camelCaseToDisplayName(String camelCase) {
		if(isNotEmptyWithTrim(camelCase)) {
			StringBuilder builder = new StringBuilder("");
			for (int i = 0; i < camelCase.length(); i++) {
				if(i == 0){
					builder.append((camelCase.charAt(i) + "").toUpperCase());
				}else if(Character.isUpperCase(camelCase.charAt(i))) {
					builder.append(" ");
					builder.append(camelCase.charAt(i));
				} else {
					builder.append(camelCase.charAt(i));
				}
			}
			return builder.toString();
		}
		return "";
	}

	public static String displayNameToCamelCase(String selectedChildTag) {
		if(isNotEmptyWithTrim(selectedChildTag)) {
			StringBuilder builder = new StringBuilder("");
			for (int i = 0; i < selectedChildTag.length(); i++) {
				if(i == 0){
					builder.append((selectedChildTag.charAt(i) + "").toLowerCase());
				}else if(!Character.isSpaceChar(selectedChildTag.charAt(i))) {
					builder.append(selectedChildTag.charAt(i));
				} 
			}
			return builder.toString();
		}
		return null;
	}

	public static String removeMethodAccessor(String s) {
		if(isNotEmptyWithTrim(s)) {
			if(s.startsWith("get")) {
				return s.length() > 3 ? s.substring(3) : s;
			}else if(s.startsWith("is")) {
				return s.length() > 2 ? s.substring(2) : s;
			}else if(s.startsWith("set")) {
				return s.length() > 3 ? s.substring(3) : s;
			}
		}
		return s;
	}

	public static String firstCharUPCase(String s) {
		if(isNotEmptyWithTrim(s)) {
			return Character.toUpperCase(s.charAt(0)) + (s.length() > 1 ? s.substring(1) : "");
		}
		return s;
	}

	public static String firstCharLowerCase(String s) {
		if(isNotEmptyWithTrim(s)) {
			return Character.toLowerCase(s.charAt(0)) + (s.length() > 1 ? s.substring(1) : "");
		}
		return s;
	}

	public static String methodToPropertyName(String s) {
		s = removeMethodAccessor(s);
		return firstCharLowerCase(s);
	}
}
