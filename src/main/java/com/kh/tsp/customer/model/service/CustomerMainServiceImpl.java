package com.kh.tsp.customer.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.tsp.customer.model.dao.CustomerMainDao;
import com.kh.tsp.customer.model.vo.Member;
import com.kh.tsp.customer.model.vo.Parking;

@Service
public class CustomerMainServiceImpl implements CustomerMainService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	@Autowired
	private CustomerMainDao cmd;
	
	
	
	public CustomerMainServiceImpl() {
		// 기본 생성자
	}

	@Override
	public Member selectCheckMember(Member m) {
		
		return cmd.selectCheckMember(sqlSession,m);
	}

	@Override
	public ArrayList<Parking> getnearParkings() {
		return cmd.selectnearParkings(sqlSession);
	}

	@Override
	public HashMap<Integer, Parking> searchVoiceParking(String keyword, String type) {
		
		HashMap<Integer, Parking> hmap;
		
		if (type.equals("지역")) {
			hmap = cmd.searchVoiceLocalParking(sqlSession, keyword);
		} else if (type.equals("근처 주차장")) {
			//37.4996559/127.0330656
			String[] temp = keyword.split("/");
			double lat = Double.parseDouble(temp[0]);
			double lon = Double.parseDouble(temp[1]);
			
			hmap = cmd.searchVoiceNearParking(sqlSession, lat, lon);
		} else {
			hmap = null;
		}
		
		return hmap;
	}
	
	
	

}
