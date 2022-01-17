package org.gsonformat.intellij.common;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: LEWIS
 * @Date: 2022/1/15 17:43
 * @packageName:org.gsonformat.intellij.common
 * @projectName:GsonFormat-Plus
 */
public class GenerateUtil {



	public static String  translate(String excelTmple){
		if(StringUtils.isBlank(excelTmple)){
			return excelTmple;
		}

		String errorString="";
		String retString="";
		JSONObject jsonObject = new JSONObject();
		String[] demoString = excelTmple.split(" ");
		if(demoString.length<2){
			demoString = excelTmple.split("\\n");
			if(demoString.length<2){
				return excelTmple;
			}
		}

		List<String> lineItems = new LinkedList<>(Arrays.asList(demoString));
		while (lineItems.size() > 0) {
			String lineItem = lineItems.get(0);
			String[] cols = lineItem.split("\\t");
			if(cols.length != 3 || StringUtils.isBlank(cols[0])|| StringUtils.isBlank(cols[1])|| StringUtils.isBlank(cols[2])){
				errorString+="行错误："+ lineItem+"\n";
				lineItems.remove(0);
				continue;
			}
			Object object = mapJson(jsonObject, cols[1]);
			if (object != null) {
				if (object instanceof JSONObject) {
					JSONObject sonObject = (JSONObject) object;
					turnObj(sonObject, cols[0], cols[1], cols[2]);
				} else if (object instanceof JSONArray) {
					JSONObject sonObject = ((JSONArray) object).getJSONObject(0);
					turnObj(sonObject, cols[0], cols[1], cols[2]);
				} else {
					errorString+="属性错误：" + cols[1] +"："+ lineItem+"\n";
				}
			} else {
				turnObj(jsonObject, cols[0], cols[1], cols[2]);
			}
			lineItems.remove(0);
		}
        retString = formatJson(jsonObject.toString());
		if(StringUtils.isNotBlank(errorString)){
			retString = errorString+"----------------------------------------------------\n"+retString;
		}

		return retString;
	}

	public static String formatJson(String json){
		if(StringUtils.isBlank(json)){
			return json;
		}
		String fromatString="";
		json = json.trim();
		if (json.startsWith("{")) {
			JSONObject jsonObject = new JSONObject(json);
			String formatJson = jsonObject.toString(4);
			fromatString+=formatJson;

		} else if (json.startsWith("[")) {
			JSONArray jsonArray = new JSONArray(json);
			String formatJson = jsonArray.toString(4);
			fromatString+=formatJson;
		}
		return fromatString;
	}

	public static void turnObj(JSONObject object, String key1, String key2, String key3){
	    if (key3.toLowerCase().startsWith("object")) {
			JSONObject sonJson = new JSONObject();
			object.put(key1, sonJson);
		} else if (key3.toLowerCase().startsWith("array")) {
			JSONArray sonArray = new JSONArray();
			JSONObject sonObject = new JSONObject();
			sonArray.put(sonObject);
			object.put(key1, sonArray);
		}else{
			object.put(key1, "");
		}
	}

	public static Object mapJson(JSONObject jsonObject, String key){
		Set<String> set = jsonObject.keySet();
		for (String key1 : set) {
			if (key.equals(key1)) {
				Object object = jsonObject.get(key1);
				if (object != null) {
					return object;
				}
			}
			if (jsonObject.get(key1) instanceof JSONObject) {
				Object object = mapJson(jsonObject.getJSONObject(key1), key);
				if (object != null) {
					return object;
				}
			} else if (jsonObject.get(key1) instanceof JSONArray) {
				Object object = mapJson(jsonObject.getJSONArray(key1).getJSONObject(0), key);
				if (object != null) {
					return object;
				}
			}

		}
		return null;
	}

	public static void main(String[] args){
		String aa = "PROFIT_SHARING\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "\t\t\n" + "APP_ID\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "MCH_ID\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "DEVICE_INFO\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "NONCE_STR\tUNIFIED_ORDER_REQ\tString\n" + "\t\t" +
				"\n" + "SIGN\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "SIGN_TYPE\tUNIFIED_ORDER_REQ\tString\n" + "BODY\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "SUB_APPID\tUNIFIED_ORDER_REQ\tString\n" + "SUB_MCH_ID\tUNIFIED_ORDER_REQ\tString\n" + "SUB_OPENID\tUNIFIED_ORDER_REQ\tString\n" + "DETAIL\tUNIFIED_ORDER_REQ\tarray\n" + "\t\t\n" + "GOODS_DETAIL\tDETAIL\tobject\n" + "GOODS_ID\tGOODS_DETAIL\tString\n" + "WXPAY_GOODS_ID\tGOODS_DETAIL\tString\n" + "GOODS_NAME\tGOODS_DETAIL\tString\n" + "QUANTITY\tGOODS_DETAIL\tString\n" + "PRICE\tGOODS_DETAIL\tString\n" + "GOODS_CATEGORY\tGOODS_DETAIL\tString\n" + "BODY\tGOODS_DETAIL\tString\n" + "ATTACH\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "OUT_TRADE_NO\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "FEE_TYPE\tUNIFIED_ORDER_REQ\tSring\n" + "\t\t\n" + "TOTAL_FEE\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "SPBILL_CREATE_IP\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "TIME_START\tUNIFIED_ORDER_REQ\tSring\n" + "TIME_EXPIRE\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "GOODS_TAG\tUNIFIED_ORDER_REQ\tString\n" + "NOTIFY_URL\tUNIFIED_ORDER_REQ\tSring\n" + "\t\t\n" + "\t\t\n" + "TRADE_TYPE\tUNIFIED_ORDER_REQ\tString\n" + "\t\t\n" + "\t\t\n" + "\t\t\n" + "PRODUCT_ID\tUNIFIED_ORDER_REQ\tString\n" + "LIMIT_PAY\tUNIFIED_ORDER_REQ\tSring\n" + "OPEN_ID\tUNIFIED_ORDER_REQ\tString\n" + "SCENE_INFO\tUNIFIED_ORDER_REQ\tobject\n" + "ID\tSCENE_INFO\tString\n" + "\t\t\n" + "NAME\tSCENE_INFO\tString\n" + "\t\t\n" + "AREA_CODE\tSCENE_INFO\tSring\n" + "\t\t\n" + "ADDRESS\tSCENE_INFO\tString\n" + "\t\t\n";
		System.out.println(translate(aa));
	}

}
