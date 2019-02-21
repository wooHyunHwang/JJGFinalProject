package com.kh.tsp.parkingceo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.tsp.common.ParkingCeoPageInfo;
import com.kh.tsp.common.ParkingCeoPagination;
import com.kh.tsp.customer.model.vo.Member;
import com.kh.tsp.parkingceo.model.service.ParkingMainService;
import com.kh.tsp.parkingceo.model.service.ParkingService;
import com.kh.tsp.parkingceo.model.service.PromotionService;

@Controller
public class ParkingCeoMain {
	
	@Autowired
	private PromotionService promotion;
	@Autowired
	private ParkingService ps;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ParkingMainService pms;

	
	public ParkingCeoMain() {
		
	}
	
	
	//사업자 메인 페이지 메소드
	@RequestMapping(value="/parkingceoMain.pc", method=RequestMethod.GET)
	public String ParkingMainPage(HttpSession session,Model model) {
		Member m = (Member)session.getAttribute("loginUser");		
		try {
			//현재 보유중인 주차장 검색
			ArrayList<HashMap<String, Object>> list = promotion.selectCurrentParkingList(m);
			model.addAttribute("CurrentParkinglist", list);
		}catch(Exception e) {
			model.addAttribute("message", "주차장 조회 실패!");
			return "common/errorPage";
		}
		return "parkingceo/main/Parkingceo_main";
	}
	
	//사업자 로그인 페이지 메소드
	@RequestMapping(value="/parkingceoLogin.pc", method=RequestMethod.GET)
	public String ParkingceoLoginPage() {
		
		
		return "parkingceo/main/Parkingceo_login";
	}
	
	
	//사업자 이용약관 동의 페이지
	@RequestMapping(value="/ceoServiceTerm.pc", method=RequestMethod.GET)
	public String ceoServiceTermPage() {
		
		
		return "parkingceo/main/ceoServiceTerm";
	}
	
	//사업자 회원가입 페이지 
	@RequestMapping(value="/ceoJoin.pc", method=RequestMethod.GET)
	public String ceoJoinPage() {
		
		
		return "parkingceo/main/ceoJoin";
	}
	
	//사업자 회원가입 기능
	@RequestMapping(value="/insertParkinCeo.pc")
	public String inserParkingCeo(@ModelAttribute Member m, Model model ) {
		
		
	try{
			String encPassword = passwordEncoder.encode(m.getMember_pwd());
			m.setMember_pwd(encPassword);
			ps.insertParkingCeo(m);
		}catch(Exception e) {
			model.addAttribute("message", e.getMessage());
			return "common/errorPage";
		}
		return "redirect:parkingceoLogin.pc";
	}
	
	
	//입차현황 조회 메소드
	@RequestMapping(value="/searchInsertParkingSystemList.pc",method=RequestMethod.POST)
	public ModelAndView searchInsertParkingSystemList(@RequestParam String selectParkingBox
			,ModelAndView mv,@RequestParam String currentPage) {		
		
		int resultCurrentPage = 1;
		if(currentPage != null) {
			resultCurrentPage = Integer.parseInt(currentPage);
		}
		HashMap<String, Object> selectHmap = new HashMap<String,Object>();
		selectHmap.put("parking_no", selectParkingBox);
		
		try {
			int listCount = pms.selectListCountInsertParkingSystemList(selectHmap);
			ParkingCeoPageInfo pi = ParkingCeoPagination.getPageInfo(resultCurrentPage, listCount);
			ArrayList<HashMap<String, Object>> list = pms.selectSearchInsertParkingSystemList(selectHmap,pi);
			mv.addObject("pi", pi);
			mv.addObject("list", list);
			
		}catch(Exception e) {
			mv.addObject("message", "입차 조회 실패!");
			mv.setViewName("jsonView");
			return mv;
		}
		
		
		mv.setViewName("jsonView");
		
		return mv;
	}
	
	//예약 현황 메소드
	@RequestMapping(value="/searchResParkingSystem.pc")
	public @ResponseBody HashMap<String, Object> searchResParkingSystem(@RequestParam String selectParkingBox
			,ModelAndView mv,@RequestParam String currentPage){
		
		HashMap<String, Object> ajaxHmap = new HashMap<String,Object>();
		HashMap<String, Object> mybatisHmap = new HashMap<String,Object>();
		mybatisHmap.put("parking_no", selectParkingBox);
		
		int resultCurrentPage = 1;
		if(currentPage != null) {
			resultCurrentPage = Integer.parseInt(currentPage);
		}
		try {
			int listCount = pms.selectListCountResParkingList(mybatisHmap);
			ParkingCeoPageInfo pi = ParkingCeoPagination.getPageInfo(resultCurrentPage, listCount);
			ArrayList<HashMap<String, Object>> list = pms.selectSearchResParkingList(mybatisHmap,pi);
			ajaxHmap.put("list", list);
			ajaxHmap.put("pi", pi);
		}catch(Exception e) {
			ajaxHmap.put("message", "예약현황 조회 실패!");
			return ajaxHmap;
		}
		return ajaxHmap;
	}

}
