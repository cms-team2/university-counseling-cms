package com.counseling.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.counseling.cms.dto.ApplyDto;
import com.counseling.cms.dto.CounselingMenuDto;
import com.counseling.cms.entity.ApplyEntity;
import com.counseling.cms.entity.ApplyStudentInfoEntity;
import com.counseling.cms.entity.FileEntity;
import com.counseling.cms.mapper.CounselingApplyMapper;
import com.counseling.cms.mapper.FileMapper;
import com.counseling.cms.utility.FileUtility;
import com.counseling.cms.utility.GetUserInfoUtility;

@Service
public class CounselingApplyService {
	
	@Autowired
	private CounselingApplyMapper counselingApplyMapper;
	@Autowired
    private FileMapper fileMapper;
	@Autowired
	private FileUtility fileUtility;
	
	public List<CounselingMenuDto> getCounselingMenu() {
		return counselingApplyMapper.getCounselingMenuMapper();
	}
	
	public ApplyStudentInfoEntity getStudentInfo() {
		return counselingApplyMapper.getStudentInfo(GetUserInfoUtility.getUserId());
	}
	
	public ResponseEntity<String> createApplicationService(ApplyDto applyDto){
		MultipartFile file[] = applyDto.getApplyFile();
		Integer fileNumber = fileUtility.createFileCode();
		
		if(file[0].getSize()>0) {
			for(int i = 0 ; i < file.length ; i++) {
				try {
					FileEntity fileEntity = new FileEntity();
					fileEntity.setFileEntity(fileUtility, file[i], fileNumber);
					fileMapper.createFile(fileEntity);	
				}catch(Exception e) {
					return ResponseEntity.status(701).body("파일 저장 오류");
				}
				
			}
		}	
		
		ApplyEntity applyEntity = new ApplyEntity(applyDto, fileNumber);
		try{
			counselingApplyMapper.createApplicationMapper(applyEntity);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			return ResponseEntity.status(711).body("신청정보 저장 실패");
		}
	}
	
}
