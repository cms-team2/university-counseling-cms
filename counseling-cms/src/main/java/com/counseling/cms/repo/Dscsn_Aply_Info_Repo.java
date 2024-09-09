package com.counseling.cms.repo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.counseling.cms.dto.Stdnt_Dscsn_join_dto;
@Mapper
public interface Dscsn_Aply_Info_Repo {
	List<Stdnt_Dscsn_join_dto> dscsn_Aply_List(String keyword,String type);
	
	

}
