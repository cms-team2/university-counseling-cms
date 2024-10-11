package com.counseling.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.counseling.cms.dto.FaqBbsDto;
import com.counseling.cms.dto.PostDto;
import com.counseling.cms.entity.FileEntity;
import com.counseling.cms.entity.PostEntity;
import com.counseling.cms.entity.UserBoardEntity;
import com.counseling.cms.entity.userReviewEntity;
import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.mapper.FileMapper;
import com.counseling.cms.mapper.UserBoardMapper;
import com.counseling.cms.utility.CookieUtility;
import com.counseling.cms.utility.FileUtility;

import jakarta.servlet.http.HttpServletRequest;

@Service("UserBoardService")
public class UserBoardService {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private FileUtility Utility; 
    
    @Autowired
    private UserBoardMapper userBoardMapper;

   public List<userReviewEntity> getCSclsfNm(String aplyNo) {
	   return userBoardMapper.getCSclsfNm(aplyNo);
   }
    
    public String getAuthrt(HttpServletRequest req) {
    	  String accessToken = CookieUtility.getCookie(req, "accessToken");
          String userId = jwtUtil.extractUserId(accessToken);
    	return userBoardMapper.getAuthrt(userId);
    }
    
    public UserBoardEntity getBoardView(String boardId, String pstNo) {
    	int boardNo = getBoardNo(boardId);
        UserBoardEntity dto = userBoardMapper.getBoardView(boardNo, pstNo);
        int inquiryCount = Integer.parseInt(dto.getPstInqCnt()) + 1;
        userBoardMapper.upInqCnt(pstNo, inquiryCount);
        return dto;
    }
    
    public List<UserBoardEntity> getBoardList(String boardId, int limit, int offset, HttpServletRequest req, String keyword, String category) {
        int boardNo = getBoardNo(boardId);
        if (boardNo != -1 && boardNo != 5) {
            return userBoardMapper.getBoardList(boardNo, limit, offset, keyword, category);
        } else if (boardNo == 5) {
            String accessToken = CookieUtility.getCookie(req, "accessToken");
            if(accessToken!=null) {
            String userId = jwtUtil.extractUserId(accessToken);
            return userBoardMapper.getInquiryList(boardNo, limit, offset, userId);
            }
        }else if(boardId=="1") {
        	return userBoardMapper.getBoardList(Integer.valueOf(boardId), limit, offset, keyword, category);
        }
        return List.of();
    }

    public int getBoardCount(String boardId, HttpServletRequest req, String keyword, String category) {
        int boardNo = getBoardNo(boardId);
        if (boardNo != -1 && boardNo != 5) {
            return userBoardMapper.getBoardCount(boardNo, keyword, category);
        } else if (boardNo == 5) {
            String accessToken = CookieUtility.getCookie(req, "accessToken");
            if(accessToken ==null || accessToken.isEmpty() ) {
            	return -1;
            }else {
            String userId = jwtUtil.extractUserId(accessToken);
            return userBoardMapper.getInquiryCount(boardNo, userId);
            }
        }
        return 0;
    }
    
    public String getUserName(HttpServletRequest req) {
    	  String accessToken = CookieUtility.getCookie(req, "accessToken");
    	  String userId = jwtUtil.extractUserId(accessToken);
    	return userBoardMapper.getUserName(userId);
    }

    public String getComment(String cmntNo) {
    	return userBoardMapper.getComment(cmntNo);
    }
    
    
    public List<FaqBbsDto> getFaqList(int limit, int offset) {
        return userBoardMapper.getFaqList(limit, offset);
    }

    public int getFaqCount() {
        return userBoardMapper.getFaqCount();
    }

    private int getBoardNo(String boardId) {
        switch (boardId) {
        	case "counselorBoard": return 1;
            case "notice": return 2;
            case "review": return 4;
            case "inquiry": return 5;
            case "dataroom": return 6;
            case "program": return 7;
            default: return -1; 
        }
    }
    
    public String filesave(MultipartFile file[], HttpServletRequest req,String title,String content,String category,String author,String boardId) {
    	 String accessToken = CookieUtility.getCookie(req, "accessToken");
   	  	 String userId = jwtUtil.extractUserId(accessToken);
   	  int BBS_NO=0;
   	  	 if(boardId.equals("review")) {
   	  		 BBS_NO=4;
   	  	 }else if(boardId.equals("inquiry")) {
   	  	BBS_NO=5;
   	  	 }
    	try {
    	int callback=0;
    	Integer fileCode=null;
    	for(int i=0;i<file.length;i++) {
    	if(file[i].getSize()>0) {
    	  String fileUuid= Utility.ftpImageUpload(file[i]);
    	  String filePath = Utility.createFilePath(file[i], fileUuid);
    	  fileCode = Utility.createFileCode();
    	  callback = userBoardMapper.boardfileWrite(fileCode,fileUuid,file[i].getOriginalFilename(),filePath,file[i].getSize());
    	  if(callback<=0) {
    		  return "error";
    	  }
    	}
    	  callback=userBoardMapper.boardPstWrite(userId,title,content,fileCode,category,author,BBS_NO);
    	  if(callback<=0) {
    		  return "error";
    	  }
    	}
    	}catch(Exception e) {
    		return "error";
    	}
    	return "ok";
    }
    
    public int deleteboard(String pntNo) {
    	int result=0;
    	try {
    		String filePath = userBoardMapper.getPstWithFilePath(pntNo);
    		int reuslt1 = Utility.ftpDeleteImage(filePath);
    		int reuslt2 = userBoardMapper.deleteComFile(filePath);
    		int reuslt3 = userBoardMapper.deleteBoard(pntNo);
    		if(reuslt1 >0 && reuslt2>0 && reuslt3>0) {
    			result=1;
    		}
    	}catch(Exception e) {
    		return 0;
    	}
    	return result;
    };
    
    public String modifyBoard(String pstNo, MultipartFile file[], String title, String content, String category, String author) {
        try {
            String filePath = userBoardMapper.getPstWithFilePath(pstNo);
            // 원래 파일이 있고 받은 파일이 있을 때
            if (filePath != null) {
            	if (file[0].getSize() > 0) {
            	    // 원래 파일이 있고 받은 파일도 있을 때
            	    int result1 = Utility.ftpDeleteImage(filePath);
            	    String fileUuid = Utility.ftpImageUpload(file[0]); 
            	    String filePath2 = Utility.createFilePath(file[0], fileUuid);
            	    int updateComFile = userBoardMapper.modifyComFile(fileUuid, file[0].getOriginalFilename(), filePath2, file[0].getSize(), filePath);
            	    if (updateComFile > 0) {
            	        int updatePst = userBoardMapper.updateBoard(pstNo, title, content, category, author);

            	        if (updatePst > 0) {
            	            return "정상적으로 수정 되었습니다.";
            	        } else {
            	            return "시스템 오류로 인해 잠시 후 다시 시도해주세요!";
            	        }
            	    } else {
            	        return "시스템 오류로 인해 잠시 후 다시 시도해주세요!";
            	    }
            	}
                // 원래 있고 받은 건 없음
                else {
                    int reuslt1 = Utility.ftpDeleteImage(filePath);
                    int deleteComfile = userBoardMapper.deleteComFile(filePath);
                    Integer fileCode = null; 
                    int updatePst = userBoardMapper.updateBoard2(pstNo, title, content, fileCode, category, author);
                    if (updatePst > 0 && deleteComfile > 0 && reuslt1 > 0) {
                        return "정상적으로 수정 되었습니다.";
                    } else {
                        return "시스템 오류로 인해 잠시 후 다시 시도해주세요!";
                    }
                }
            } 
            else {
                if (file[0].getSize() > 0) {
                    // 받은 것만 있음
                    for (int i = 0; i < file.length; i++) {
                        String fileUuid2 = Utility.ftpImageUpload(file[i]);
                        String filePath3 = Utility.createFilePath(file[i], fileUuid2);
                        int fileCode = Utility.createFileCode();
                        int boardfileWrite = userBoardMapper.boardfileWrite(fileCode, fileUuid2, file[i].getOriginalFilename(), filePath3, file[i].getSize());
                        int updatePst = userBoardMapper.updateBoard2(pstNo, title, content, fileCode, category, author);
                        if (boardfileWrite > 0 && updatePst > 0) {
                            return "정상적으로 수정 되었습니다.";
                        } else {
                            return "시스템 오류로 인해 잠시 후 다시 시도해주세요!";
                        }
                    }
                } 
                else {
                    // 원래와 받은 파일 둘 다 없을 때 
                    int updatePst = userBoardMapper.updateBoard(pstNo, title, content, category, author);
                    if (updatePst > 0) {
                        return "정상적으로 수정 되었습니다.";
                    } else {
                        return "시스템 오류로 인해 잠시 후 다시 시도해주세요!";
                    }
                }
            }
        } catch (Exception e) {
            return "시스템 오류로 인해 잠시 후 다시 시도해주세요!";
        }
        return null;
    }

    public List<UserBoardEntity> getCounSlerList(){
    	return userBoardMapper.getCounSlerList();
    }
    
    public int getCounslerCount(String keyword,String category) {
    	return userBoardMapper.getBoardCount(1, keyword, category);
    }
}
