package com.example.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Controller
public class UpbitController {
	
	
	@RequestMapping(value="/mm")
	public Map<String, BigDecimal> getData() throws IOException, ParseException {
		
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("https://api.upbit.com/v1/orderbook?markets=KRW-BTC")
		  .get()
		  .addHeader("accept", "application/json")
		  .build();

		Response response = client.newCall(request).execute();
		
		String message = response.body().string();
		
		
        JSONArray list = (JSONArray) new JSONParser().parse(message);
        
	        System.out.println("==========list============================");
	        System.out.println(list.toJSONString());
	        System.out.println("=========================================");
        
        
        JSONObject obj = (JSONObject) list.get(0);
        
        
        JSONArray arr=(JSONArray)obj.get("orderbook_units");
        System.out.println("size"+arr.size());
        
//        JSONObject obj1=(JSONObject)arr.get(12);
//        JSONObject obj2=(JSONObject)arr.get(13);
//        JSONObject obj3=(JSONObject)arr.get(14);
        JSONObject obj4=(JSONObject)arr.get(0);
        JSONObject obj5=(JSONObject)arr.get(1);
        JSONObject obj6=(JSONObject)arr.get(2);
        
        
        
        Double bid_price1 = (Double) obj4.get("bid_price");
      	BigDecimal val1=new BigDecimal(bid_price1);
      	
        Double bid_price2 = (Double) obj5.get("bid_price");
        BigDecimal val2=new BigDecimal(bid_price2);
        
        Double bid_price3 = (Double) obj6.get("bid_price");
        BigDecimal val3=new BigDecimal(bid_price3);
        
        Double ask_price3 = (Double) obj6.get("ask_price");
        BigDecimal val4=new BigDecimal(ask_price3);
        
        Double ask_price2 = (Double) obj5.get("ask_price");
        BigDecimal val5=new BigDecimal(ask_price2);
        
        Double ask_price1 = (Double) obj4.get("ask_price");
        BigDecimal val6=new BigDecimal(ask_price1);
        
        System.out.println(bid_price1+"//"+bid_price2+"//"+bid_price3+"//"+ask_price3+"//"+ask_price2+"//"+ask_price1);
        System.out.println(val1+"//"+val2+"//"+val3+"//"+val4+"//"+val5+"//"+val6);
        
        Map<String, BigDecimal> map=new HashMap<>();
        map.put("val", val1);
        map.put("va2", val2);
        map.put("va3", val3);
        map.put("va4", val4);
        map.put("va5", val5);
        map.put("va6", val6);
        
        
		return map;
        
        
        
		
		
		
		
	}

}
