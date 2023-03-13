package com.example.demo.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class OuterTasklet implements Tasklet {
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
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
        
       // System.out.println(bid_price1+"//"+bid_price2+"//"+bid_price3+"//"+ask_price3+"//"+ask_price2+"//"+ask_price1);
       // System.out.println(val1+"//"+val2+"//"+val3+"//"+val4+"//"+val5+"//"+val6);
        
        List<String> datalist=new ArrayList<>();
        datalist.add(val1.toString());
        datalist.add(val2.toString());
        datalist.add(val3.toString());
        datalist.add(val4.toString());
        datalist.add(val5.toString());
        datalist.add(val6.toString());
        
       
        System.out.println("datalist===>"+datalist);
        
        Date today = new Date();
		SimpleDateFormat time=new SimpleDateFormat("hh:mm:ss a");
		String now =time.format(today);
		System.out.println(now);
                                                         
  
		String file_path = "C://excel/upbit.xlsx";
				
		
									 //액셀 관련 poi 라이브러리 추가해야함
		Workbook xworkbook = null;   //엑셀파일 객체 생성
		Sheet xSheet = null;         //시트 객체 생성
		Row xRow = null;             //행 객체 생성
		Cell xCell = null;           //열 객체 생성
		
		
		HSSFWorkbook wb = null;
			
		try {				
			
			//파일 생성
			File file = new File(file_path);            // 파일 확장자 .xlsx로 고정
			if(file.exists()) {
				System.out.println("Write Start next " + now);
				
				FileInputStream fis = new FileInputStream(file);
				
				xworkbook = new XSSFWorkbook(fis);          //엑셀파일 생성
				xSheet = xworkbook.getSheetAt(0);				
				
				int row = xSheet.getPhysicalNumberOfRows();
				System.out.println("row: " + row);
				xRow = xSheet.createRow(row);
					
				for(int i=0; i<datalist.size(); i++) {
					
				
					// 데이터 입력
					xCell = xRow.createCell(i);
					xCell.setCellValue(datalist.get(i));
					
					
							
				}
			
				FileOutputStream fos = new FileOutputStream(file);
				
				// 파일 쓰기
				xworkbook.write(fos);
				xRow = xSheet.createRow(row++);
				if(fos != null) {
					fos.close();
				}
				if (fis != null) fis.close();
				
				System.out.println("Write complete " + now);
			} else {
				System.out.println("Write Start first " + now);
				
				xworkbook = new XSSFWorkbook();          //엑셀파일 생성
				xSheet = xworkbook.createSheet("sheet1"); //시트 생성
				
				int row = 0; //row번째 행
				
				//첫 행 입력
				
				xRow = xSheet.createRow(row++); //행
				
				
				xCell = xRow.createCell(0); //셀
				xCell.setCellValue("bit-1");
				xCell = xRow.createCell(1);				
				xCell.setCellValue("bit-2");
				xCell = xRow.createCell(2);				
				xCell.setCellValue("bit-3");
				xCell = xRow.createCell(3);				
				xCell.setCellValue("bit-4");
				xCell = xRow.createCell(4);				
				xCell.setCellValue("bit-5");
				xCell = xRow.createCell(5);				
				xCell.setCellValue("bit-6");
				
				
				xRow = xSheet.createRow(row++);
				
				for(int i=0; i<datalist.size(); i++) {
					
					
					// 데이터 입력
					xCell = xRow.createCell(i);
					xCell.setCellValue(datalist.get(i));
					
					
					
				}
				
				FileOutputStream fos = new FileOutputStream(file);
				
				// 파일 쓰기
				xworkbook.write(fos);
				xRow = xSheet.createRow(row++);
				if(fos != null) {
					fos.close();
				}
				System.out.println("Write complete " + now);
				
			}
									
				
		}  catch (Exception e) {
				
			System.out.print(e);
			
		} 
		
		return RepeatStatus.FINISHED;
	}
	
	

}
