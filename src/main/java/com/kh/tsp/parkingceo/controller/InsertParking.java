package com.kh.tsp.parkingceo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.tsp.common.CommonUtils;
import com.kh.tsp.customer.model.vo.Member;
import com.kh.tsp.parkingceo.model.service.ParkingService;
import com.kh.tsp.parkingceo.model.vo.ParkingCeoAttachmentVo;
import com.kh.tsp.parkingceo.model.vo.ParkingCeoVo;

@Controller
public class InsertParking {
	
	@Autowired
	private ParkingService ps;
	
	

	public InsertParking() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/insertParkingPage.pc", method=RequestMethod.GET)
	public String insertParkingPage(){
		
		return "parkingceo/parking/InsertParking";
	}
	
	@RequestMapping(value="/insertParking.pc",method=RequestMethod.POST)
	public String insertPakringOne(HttpSession session,Model model,HttpServletRequest request,@ModelAttribute ParkingCeoVo parking,@RequestParam(value="parkingImage1",required=false) MultipartFile parkingImage1
			,@RequestParam(value="parkingImage2",required=false) MultipartFile parkingImage2,@RequestParam(value="parkingImage3",required=false) MultipartFile parkingImage3
			,@RequestParam String address,@RequestParam String detailAddress) {
		
		
		
		Member m = (Member)session.getAttribute("loginUser");
		String resultAddress = address+" "+detailAddress;
		String weekStartTime = parking.getWeekdayStime();
		String weekEndTime = parking.getWeekdayEtime();
		String satStartTime = parking.getSatStime();
		String satEndTime = parking.getSatEtime();
		String holStartTime = parking.getHollydayStime();
		String holEndTime = parking.getHollydayEtime();
		
		
		//?????? ?????? ??????
		parking.setMemberNo(m.getMember_no());
		//?????? ??? ??????
		parking.setAddress(resultAddress);
		//?????? ?????? ??? ??????
		String openDaysResult = String.join(",", parking.getOpenDaysArry());
		parking.setOpenDays(openDaysResult);
		//?????? ?????? ??? ??????
		parking.setWeekdayStime("00:00");
		parking.setWeekdayEtime("00:00");
		parking.setSatStime("00:00");
		parking.setSatEtime("00:00");
		parking.setHollydayStime("00:00");
		parking.setHollydayEtime("00:00");
		
		for(int i = 0; i < parking.getOpenDaysArry().length; i++) {
			if(parking.getOpenDaysArry()[i].equals("??????")) {
				parking.setWeekdayStime(weekStartTime);
				parking.setWeekdayEtime(weekEndTime);
			}else if(parking.getOpenDaysArry()[i].equals("?????????")) {
				parking.setSatStime(satStartTime);
				parking.setSatEtime(satEndTime);
			}else if(parking.getOpenDaysArry()[i].equals("?????????")) {
				parking.setHollydayStime(holStartTime);
				parking.setHollydayEtime(holEndTime);
			}
		}
		
		//?????? ?????? ??? ??????
		String payTypeResult = String.join(",", parking.getPay_typeArry());
		parking.setPayType(payTypeResult);
		
		//?????? ?????? ?????? ??????
		parking.setLeftSize(parking.getParkingSize());
		
		//?????? ??????
		if(parking.getRemarks() == "") {
			parking.setRemarks("??????");
		}
		
		//?????? ??????
		String root = request.getSession().getServletContext().getRealPath("resources");
		String filePath = root + "\\common\\parkingImage";
		ArrayList<MultipartFile> multipartList = new ArrayList<MultipartFile>();
		ArrayList<String> changeNameList = new ArrayList<String>();
		ArrayList<ParkingCeoAttachmentVo> attachList = new ArrayList<ParkingCeoAttachmentVo>();
		ArrayList<String> extNameList = new ArrayList<String>();
		
		if(parkingImage1.getOriginalFilename() != "") {
			multipartList.add(parkingImage1);			
		}
		if(parkingImage2.getOriginalFilename() != "") {
			multipartList.add(parkingImage2);			
		}
		if(parkingImage3.getOriginalFilename() != "") {
			multipartList.add(parkingImage3);			
		}
		String originFileName = "";
		String changeName = "";
		String ext = "";
		
		int count = 1;

		
		try {
			for(int i = 0 ; i < multipartList.size(); i++) {
					ParkingCeoAttachmentVo attach = new ParkingCeoAttachmentVo();
					originFileName = multipartList.get(i).getOriginalFilename();
					ext = originFileName.substring(originFileName.lastIndexOf("."));
					changeName = CommonUtils.getRandomString();
					changeNameList.add(changeName);
					extNameList.add(ext);
					
					attach.setFile_path(filePath);
					attach.setChange_name(changeName);
					attach.setOrigin_name(originFileName);
					attach.setFile_level(count);
					count ++;
					attachList.add(attach);
					multipartList.get(i).transferTo(new File(filePath+"\\"+changeName+ext));
				}
				//????????? ???????????? ?????? ?????? ?????????
				System.out.println(attachList);
				ps.insertParkingOne(parking,attachList);
			
			}catch (Exception e) {
				e.printStackTrace();
				for(int i = 0 ; i < changeNameList.size(); i ++) {
					new File(filePath + "\\" + changeNameList.get(i) + extNameList.get(i)).delete();					
				}
				return "redirect:insertParkingFailed.pc";
			}
			
		return "redirect:insertParkingSuccess.pc";
	}
	
	@RequestMapping(value="/insertParkingFailed.pc",method=RequestMethod.GET)
	public String insertParkingFalied(Model model) {
		model.addAttribute("message", "????????? ?????? ??????!");
		return "parkingceo/common/success";
	}
	
	
	@RequestMapping(value="/insertParkingSuccess.pc",method=RequestMethod.GET)
	public String insertParkingSuccess(Model model) {
		model.addAttribute("message", "????????? ?????? ??????!");
		return "parkingceo/common/success";
	}
	

}
