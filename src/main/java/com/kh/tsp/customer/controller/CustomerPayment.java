package com.kh.tsp.customer.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kh.tsp.common.PageInfo;
import com.kh.tsp.common.ReservPagination;
import com.kh.tsp.customer.model.service.CustomerMainService;
import com.kh.tsp.customer.model.vo.ChargeOil;
import com.kh.tsp.customer.model.vo.Member;
import com.kh.tsp.customer.model.vo.Reservation;

@SessionAttributes("loginUser")
@Controller
public class CustomerPayment {

	@Autowired
	private CustomerMainService cms;
	
	public CustomerPayment() {
		
	}
	
	@RequestMapping(value="/oil.cu", method=RequestMethod.GET)
	public String CustomerOil(HttpServletRequest request, HttpSession session) {
		
		if ((Member)session.getAttribute("loginUser") != null) {
			int UserOilInfo = Integer.parseInt(cms.getRefreshMember((Member)session.getAttribute("loginUser")).getOil());
			
			request.setAttribute("UserOilInfo", UserOilInfo);
		}
		
		return "customer/member/Customer_oil";
	}
	
	@RequestMapping(value="/oilIn.cu", method=RequestMethod.GET)
	public String CustomerOilInsert(HttpServletRequest request, HttpSession session) {
		String imp_uid;
		String merchant_uid;
		boolean imp_success;
		String [] breakString;
		if (request.getParameter("imp_uid") != null && request.getParameter("merchant_uid") != null && request.getParameter("imp_success") != null) {
			imp_uid = request.getParameter("imp_uid");
			merchant_uid = request.getParameter("merchant_uid");
			imp_success = Boolean.valueOf(request.getParameter("imp_success"));
			
			breakString = request.getParameter("merchant_uid").split("_");
			
			System.out.println("????????? ??????????????????!");
			System.out.println(imp_uid + "\r\n" + merchant_uid + "\r\n" + imp_success);
			
			if (imp_success) { // ?????? ????????? ??????
				ChargeOil chargeOil = new ChargeOil();
				
				chargeOil.setImp_uid(imp_uid);
				chargeOil.setMerchant_uid(merchant_uid);
				chargeOil.setImp_success(imp_success);
				chargeOil.setMemberNo(Integer.parseInt(breakString[0]));
				chargeOil.setOil(Integer.parseInt(breakString[1]));
				
				int result = 0;
				
				result = cms.insertCustomerOilCharge(chargeOil);
				
				if (result > 0) { // ?????? ????????? ???????????????!
					cms.updateCustomerOilCharge(chargeOil);
					System.out.println("????????? ????????? ????????? DB??? ????????? ?????????!");
				}
			} else {
				System.out.println("????????? ??????????????? ?????? ???????????? ???????????????...?????????");
			}
			
		}
		
		return "redirect:oil.cu";
	}
	
	@RequestMapping(value="/reserv.cu", method=RequestMethod.GET)
	public String showCustomerReserve(HttpServletRequest request, HttpSession session) {
		
		int currentPage = 1;
		int listCount = 0;
		PageInfo pi;
		
		if(request.getParameter("pageNo") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNo"));
		}
		
		Member member = (Member) session.getAttribute("loginUser");
		
		if (member != null) {
			listCount = cms.selectReservCount(member);
			
			pi = ReservPagination.getPageInfo(currentPage, listCount);
			
			ArrayList<Reservation> reservList = cms.selectShowReserv(member, pi);
			
			Date today = new Date(new java.util.Date().getTime());
			
			
			for (int i = 0; i < reservList.size(); i++) { // ????????????
				if (today.getTime() <= reservList.get(i).getResDate().getTime()) {
					reservList.get(i).setChkDate("true");
				} else {
					reservList.get(i).setChkDate("false");
				}
				
			}
			
			request.setAttribute("pi", pi);
			request.setAttribute("reservList", reservList);
			
			System.out.println(reservList);
		} else {
			System.out.println("????????? ????????? ????????????!");
		}
		
		// ?????? ??????
		if ((Member)session.getAttribute("loginUser") != null) {
			int UserOilInfo = Integer.parseInt(cms.getRefreshMember((Member)session.getAttribute("loginUser")).getOil());
			
			request.setAttribute("UserOilInfo", UserOilInfo);
		}
		
		return "customer/promotion/Customer_reserv";
	}	
	
	@RequestMapping("updateCancelReserve.cu")
	public @ResponseBody String updateCancelReserve(@RequestParam String resNo, HttpSession session) {
		
		Member member = (Member) session.getAttribute("loginUser");
		
		Reservation reserv = new Reservation();
		
		reserv.setResNo(Integer.parseInt(resNo));
		reserv.setMemberNo(member.getMember_no());
		
		String status = cms.selectReservDay(Integer.parseInt(resNo));
		
		int result = cms.updateCancelReserve(reserv);
		
		if (result > 0) { // ?????? ?????? ??? ????????? ???????????? ????????? ??????
			result = 0;
			
			result = Integer.parseInt(cms.selectOneCancelAndReserveTime(Integer.parseInt(resNo)));
			
			System.out.println("?????? ????????? ???????????? " + result + " ?????????.");
			System.out.println("?????? ???????????? ????????? " + status);
			
			if (!(result > -60  && status.equals("??????"))) { // ????????? ????????? ???????????? ????????? ??????????????? ????????? ???????????? ?????? ?????? - ???????????? nprice??? 3???
				result = 0;
				
				// OilList??? ?????? ?????? ?????? ??????
				cms.insertCancelReserveOilList(reserv);
				
				result = cms.updateCancelReserveOil(reserv);
				
				if (result > 0) {
					return "?????? ??? ?????? ??????";
				} else {
					return "?????? ??????";
				}
			} else {
				
				return "?????? ??????";
			}
			
		}else {
			return "??????";
		}
		
    }
	
	@RequestMapping("selectCancelReason.cu")
	public @ResponseBody String selectCancelReason(@RequestParam String resNo, HttpSession session) {
		
		Member member = (Member) session.getAttribute("loginUser");
		
		Reservation reserv = new Reservation();
		
		reserv.setResNo(Integer.parseInt(resNo));
		reserv.setMemberNo(member.getMember_no());
		
		String result = cms.selectCancelReason(reserv);
		
		return result;
    }
	
	@RequestMapping("requestRefund.cu")
	public @ResponseBody int intsertRequestRefund(@RequestParam int memberNo, @RequestParam int refundOil, @RequestParam String accountHolder, @RequestParam String Application_bank, @RequestParam String application_account_number, HttpSession session) {
		HashMap<String, String> requesthmap = new HashMap<String, String>();
		requesthmap.put("memberNo", "" + memberNo);
		requesthmap.put("refundOil", "" + refundOil);
		requesthmap.put("accountHolder", accountHolder);
		requesthmap.put("Application_bank", Application_bank);
		requesthmap.put("application_account_number", application_account_number);
		
		System.out.println(requesthmap.toString());
		
		int result = cms.intsertRequestRefund(requesthmap);
		
		if (result > 0) {
			result = 0;
			
			result = cms.updateRequestRefund(requesthmap);
			
			if (result > 0) {
				result = 0;
				
				result = cms.insertOilListRefund(requesthmap);
			}
		}
		
		return result;
    }
	
	@RequestMapping(value="/requestReserve.cu", method=RequestMethod.POST)
	public String RequestReserve(HttpServletRequest request, HttpSession session, @RequestParam String reservParkingNo, @RequestParam String selectedTime, @RequestParam String selectTime2, @RequestParam String carNo) {
		
		Member user = (Member)session.getAttribute("loginUser");
		
		int year = 2000 + Integer.parseInt(selectedTime.split(" ")[0].split("???")[0]);
		int month = Integer.parseInt(selectedTime.split(" ")[1].split("???")[0]);
		int day = Integer.parseInt(selectedTime.split(" ")[2].split("???")[0]);
		
		int hour = Integer.parseInt(selectTime2.split(":")[0]);
		
		
		
		Reservation reservInfo = new Reservation();
		
		reservInfo.setMemberNo(user.getMember_no());
		reservInfo.setCarNo(carNo);
		reservInfo.setParkingNo(reservParkingNo);
		reservInfo.setStringResDate(month + "-" + day + "-" + year + " " + hour + ":00");
		
		System.out.println(reservInfo.toString());
		
		
		
		int result = cms.insertRequestReserve(reservInfo);
		
		if (result > 0) {
			System.out.println("?????? ????????? ??????");
			cms.updateRequestReserveOil(reservInfo);
			result = 0;
			result = cms.insertRequestReserveOilList(reservInfo);
			if (result > 0) {
				System.out.println("?????? ???????????? ?????? ??????");
			}
		}
		
		return "redirect:reserv.cu";
	}
	
	
	
	
	
	
	public boolean compareTime() {
		return false;
	}
	
	
}
