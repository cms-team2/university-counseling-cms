package com.counseling.cms.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.counseling.cms.dto.Dscsn_Aply_Info_dto;
@Mapper
public interface Dscsn_Aply_Info_Repo {
	List<Dscsn_Aply_Info_dto> dscsn_Aply_List(String keyword,String type);
}
