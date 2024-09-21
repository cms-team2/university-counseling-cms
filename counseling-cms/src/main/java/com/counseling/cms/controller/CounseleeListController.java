package com.counseling.cms.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.counseling.cms.dto.CounselingRecordDto;
import com.counseling.cms.entity.CounseleeListEntity;
import com.counseling.cms.service.CounseleeListService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CounseleeListController {
	
	@Autowired
	private CounseleeListService counseleeListService;
	
	@GetMapping("/counselor/getCounseleeList")
	public String getCounseleeList(Model model, HttpServletRequest req, 
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="searchValue", defaultValue="") String searchValue,
			@RequestParam(value="category", defaultValue="A") String category) {
		Map<String, Object> result=counseleeListService.getCounseleeList(req, page, searchValue, category);
		
		model.addAttribute("category",category);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("totalPage",result.get("totalPage"));
		model.addAttribute("counseleeList",result.get("counseleeList"));
		model.addAttribute("totalList",result.get("totalList"));
		return "/counselor/counseleeList";
	}
	
	@GetMapping("/counselor/applyContent")
	public String getApplyContent(@RequestParam(value="applyNo", required = true) int applyNo, HttpServletRequest req, Model model) {
		Map<String, Object> applyResult=counseleeListService.getApplyView(req, applyNo);

		model.addAttribute("applyList", applyResult.get("applyList"));
		model.addAttribute("fileList", applyResult.get("fileList"));
		return "/counselor/counseleeVIew";
	}
	
	@GetMapping("/counselor/writeCounselingRecord")
	public String counselingRecord(@RequestParam(value="applyNo", required = true) int applyNo, HttpServletRequest req, Model model) {
		Map<String, Object> applyResult=new HashMap<>();
		
		applyResult=counseleeListService.getApplyView(req, applyNo);
		model.addAttribute("applyList", applyResult.get("applyList"));
		model.addAttribute("fileList", applyResult.get("fileList"));
		
		Map<String, Object> result=counseleeListService.getCounselingRecord(applyNo);
		
		model.addAttribute("today", result.get("today"));
		model.addAttribute("recordCount", result.get("recordCount"));
		model.addAttribute("recordList", result.get("recordList"));
		model.addAttribute("counselorName", result.get("counselorName"));
		return "/counselor/counselingRecord";
	}
	
	@GetMapping("/counselor/counselingRecordList")
	public String getCounselingRecordList(Model model, HttpServletRequest req, 
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="searchValue", defaultValue="") String searchValue,
			@RequestParam(value="category", defaultValue="A") String category) {
		Map<String, Object> result=counseleeListService.getCounselingRecordList(req, page, searchValue, category);

		model.addAttribute("category",category);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("totalPage",result.get("totalPage"));
		model.addAttribute("recordList",result.get("recordList"));
		model.addAttribute("totalList",result.get("totalList"));
		
		return "/counselor/counselingRecordList";
	}
	
	@PostMapping("/counselor/counselingRecordSave")
	@ResponseBody
	public ResponseEntity<String> counselingRecordSave(@RequestBody CounselingRecordDto counselingRecordDto, HttpServletRequest req){

		return counseleeListService.saveCounselingRecord(counselingRecordDto, req);
	}
	
	@PostMapping("/counselor/counselingRecordModify")
	@ResponseBody
	public ResponseEntity<String> counselingRecordModify(@RequestBody CounselingRecordDto counselingRecordDto){

		return counseleeListService.modifyCounselingRecord(counselingRecordDto);
	}
	
	 @GetMapping("/counselor/downloadFile")
	 @ResponseBody
	 @CrossOrigin(origins = "*", allowedHeaders = "*")
	    public ResponseEntity<String> downloadFile(@RequestParam String filePath, @RequestParam String fileName) {

		 	System.out.println(fileName);
		 
		 	// 파일 경로 설정
	        String fileUrl = "http://172.30.1.16:20080/"+filePath.split("CDN")[1];
	        File file = new File(fileUrl);
	        
	        // 파일 존재 여부 및 읽기 권한 확인
	        if (!file.exists() || !file.canRead()) {
	            return ResponseEntity.status(704).build();
	        }
	        FileOutputStream fos = null;
			InputStream is = null;
			
			try {

				fos = new FileOutputStream("\"C:\\Users\\admin\\Downloads\""+fileName);
				
				URL url = new URL(fileUrl);
				URLConnection urlConnection = url.openConnection();
				//https
				//HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
				is = urlConnection.getInputStream();
				
				byte[] buffer = new byte[1024];
				int readBytes;
				while ((readBytes = is.read(buffer)) != -1) {
					fos.write(buffer, 0, readBytes);
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return ResponseEntity.ok("ok");	
	    }
	
}
