package com.kh.tsp.customer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.tsp.customer.model.service.BoardService;
import com.kh.tsp.customer.model.vo.Board;
import com.kh.tsp.customer.model.vo.Member;

@Controller
public class CustomerBoardDetail {
	@Autowired
	private BoardService bs;
	
	public CustomerBoardDetail() {}
	//공지사항 상세보기
	@RequestMapping(value="customerNoticeDetail.cu", method=RequestMethod.GET)
	public String CustomerNoticeDetail(HttpServletRequest request, HttpServletResponse response) {
		int bno = Integer.parseInt(request.getParameter("num"));
		
		System.out.println("bno: "+bno);
		
		Board b = bs.selectOneNotice(bno); 
		
		request.setAttribute("b", b);
		return "customer/board/Customer_notice_detail";
	}
	//후기 상세보기
	@RequestMapping(value="reviewDetail.cu", method=RequestMethod.GET)
	public String MyReviewDetail(String num, Model model) {
		
		int bno = Integer.parseInt(num);
		
		System.out.println("후기 상세보기 bno: "+bno);
		
		Board b = bs.selectOneReview(bno);
		
		model.addAttribute("b", b);
		
		return "customer/board/My_review_detail";
	}
	
	//문의 상세보기
	@RequestMapping(value="qnaDetail.cu", method=RequestMethod.GET)
	public String MyQnaDetail(HttpServletRequest request, HttpServletResponse response) {
		int bno = Integer.parseInt(request.getParameter("num"));
		System.out.println("문의상세보기 서블릿 bno: "+bno);
		
		
	
		Board b = bs.selectOneQna(bno); 
		
		request.setAttribute("b", b);
	
		return "customer/board/My_qna_detail";
	}
	
	//주차장 문의 상세보기
	@RequestMapping(value="parkingQnaDetail.cu", method=RequestMethod.GET)
	public String ParkingQnaDetail(HttpServletRequest request, HttpServletResponse response) {
		int bno = Integer.parseInt(request.getParameter("num"));
		System.out.println("문의상세보기 서블릿 bno: "+bno);
		
		String pno = request.getParameter("pno");
		
		System.out.println("주차장문의상세보기 pno : "+pno);
		Board b = bs.selectOneParkingQna(bno); 
		
		request.setAttribute("b", b);
	
		return "customer/board/Parking_qna_detail";
	}
	
	//주차장 후기 상세보기	
	@RequestMapping(value="parkingReviewDetail.cu", method=RequestMethod.GET)
	public String ParkingReviewDetail(HttpServletRequest request, HttpServletResponse response) {
		int bno = Integer.parseInt(request.getParameter("num"));
		System.out.println("주차장 후기상세보기 서블릿 bno: "+bno);
		
		String pno = request.getParameter("pno");		
		System.out.println("주차장문의상세보기 pno : "+pno);
	
		Board b = bs.selectOneParkingReview(bno); 
		
		request.setAttribute("b", b);
		request.setAttribute("pno", pno);
		return "customer/board/Parking_review_detail";
	}
}
