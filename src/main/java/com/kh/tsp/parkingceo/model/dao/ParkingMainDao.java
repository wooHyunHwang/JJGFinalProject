package com.kh.tsp.parkingceo.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;

import com.kh.tsp.common.ParkingCeoPageInfo;

public interface ParkingMainDao {

	int selectListCountInsertParkingSystemList(SqlSessionTemplate sqlSession, HashMap<String, Object> selectHmap);

	ArrayList<HashMap<String, Object>> selectSearchInsertParkingSystemList(SqlSessionTemplate sqlSession,
			HashMap<String, Object> selectHmap, ParkingCeoPageInfo pi);

	int selectListCountResParkingList(SqlSessionTemplate sqlSession, HashMap<String, Object> selectParkingBox);

	ArrayList<HashMap<String, Object>> selectSearchResParkingList(SqlSessionTemplate sqlSession,
			HashMap<String, Object> selectParkingBox, ParkingCeoPageInfo pi);

	void updateResComplete(SqlSessionTemplate sqlSession, int completeResNo);

	void updateResCancel(SqlSessionTemplate sqlSession, int completeResNo);

	void updateResCancel(SqlSessionTemplate sqlSession, int completeResNo, String resCancelText);

	HashMap<String, Object> searchPakringSize(SqlSessionTemplate sqlSession,
			HashMap<String, Object> searchHmap);

	void insertNonMemberEntryList(SqlSessionTemplate sqlSession, HashMap<String, Object> searchHashmap);

	void insertNomalMemberEntryList(SqlSessionTemplate sqlSession, HashMap<String, Object> searchHashmap);

	int searchMemberNo(HashMap<String, Object> searchHashmap);

	void insertResMemberEntryList(SqlSessionTemplate sqlSession, HashMap<String, Object> searchHashmap);

	void updateLeftSize(SqlSessionTemplate sqlSession, HashMap<String, Object> searchHashmap);






}
