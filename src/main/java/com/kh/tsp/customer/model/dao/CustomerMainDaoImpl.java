package com.kh.tsp.customer.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.tsp.common.PageInfo;
import com.kh.tsp.customer.model.vo.CCoupon;
import com.kh.tsp.customer.model.vo.ChargeOil;
import com.kh.tsp.customer.model.vo.Member;
import com.kh.tsp.customer.model.vo.OilList;
import com.kh.tsp.customer.model.vo.Parking;
import com.kh.tsp.customer.model.vo.Points;
import com.kh.tsp.customer.model.vo.Reservation;;

@Repository
public class CustomerMainDaoImpl implements CustomerMainDao {

	public CustomerMainDaoImpl() {
		// 기본 생성자
	}

	@Override
	public Member selectCheckMember(SqlSessionTemplate sqlSession, Member m) {
		
		//System.out.println("로그인 체크하는 메소드 dao: "+sqlSession.selectOne("Member.selectCheckMember",m));
		//System.out.println("dao 비밀번호 : "+m.getMember_pwd());
		
		return sqlSession.selectOne("Member.selectCheckMember",m);
	}

	@Override
	public ArrayList<Parking> selectnearParkings(Points p,SqlSessionTemplate sqlSession) {
		
		
		return (ArrayList)sqlSession.selectList("Member.selectnearParkings",p);
	}

	@Override
	public HashMap<String, Parking> searchVoiceLocalParking(SqlSessionTemplate sqlSession, String keyword) {
		HashMap<String, Parking> hmap = new HashMap<String, Parking>();
		System.out.println(keyword);
		ArrayList<Parking> list = (ArrayList)sqlSession.selectList("Member.selectSearchTitleParking", keyword);// 1. 이름 기준 검색
		
		for (int i = 0; i < list.size(); i++) {
			hmap.put("" + (i + 100001), list.get(i));
		}
		
		list = (ArrayList)sqlSession.selectList("Member.searchAddrParking", keyword); // 2. 주소 기준 검색
		
		for (int i = 0; i < list.size(); i++) {
			hmap.put("" + (i + 200001), list.get(i));
		}
		
		System.out.println(hmap);
		
		return hmap;
	}

	@Override
	public HashMap<String, Parking> searchVoiceNearParking(SqlSessionTemplate sqlSession, double lat, double lon) {
		HashMap<String, Parking> hmap = new HashMap<String, Parking>();
		
		ArrayList<Parking> list = (ArrayList)sqlSession.selectList("Member.searchLocalParkings", lat);
		
		for (int i = 0; i < list.size(); i++) {
			hmap.put("" + (i + 1), list.get(i));
		}
		
		return hmap;
	}

	@Override
	public HashMap<String, Parking> selectSearchTextParking(SqlSessionTemplate sqlSession, String keyword) {
		HashMap<String, Parking> hmap = new HashMap<String, Parking>();
		System.out.println(keyword);
		ArrayList<Parking> list = (ArrayList)sqlSession.selectList("Member.selectSearchTitleParking", keyword);// 1. 이름 기준 검색
		
		for (int i = 0; i < list.size(); i++) {
			hmap.put("" + (i + 100001), list.get(i));
		}
		
		list = (ArrayList)sqlSession.selectList("Member.searchAddrParking", keyword); // 2. 주소 기준 검색
		
		for (int i = 0; i < list.size(); i++) {
			hmap.put("" + (i + 200001), list.get(i));
		}
		
		System.out.println(hmap);
		
		return hmap;
	}

	@Override
	public ArrayList<Reservation> selectShowReserv(SqlSessionTemplate sqlSession, Member member) {
		return (ArrayList)sqlSession.selectList("Member.selectReserve", member);
	}

    //카카오톡 로그인
	public Member selectCheckKakao(SqlSessionTemplate sqlSession, String kakao_id) {

		return sqlSession.selectOne("Member.selectCheckKakao", kakao_id);
	}
	//카카오톡 회원가입
	@Override
	public int insertKakao(SqlSessionTemplate sqlSession, Member m) {

		return sqlSession.insert("Member.insertKakao",m);

	}
	//아이디 중복확인
	@Override
	public Member idCheck(SqlSessionTemplate sqlSession, String member_id) {

		return sqlSession.selectOne("Member.idCheck", member_id);
	}
	//일반 회원가입
	@Override
	public int insertMember(SqlSessionTemplate sqlSession, Member m) {

		return sqlSession.insert("Member.insertMember", m);
	}

	@Override
	public int updateCancelReserve(SqlSessionTemplate sqlSession, Reservation reserv) {
		System.out.println(reserv);
		return sqlSession.update("Member.updateCancelReserve", reserv);
	}

	@Override
	public String selectOneCancelAndReserveTime(SqlSessionTemplate sqlSession, int resNo) {
		return sqlSession.selectOne("Member.selectOneCancelAndReserveTime", resNo);
	}

	@Override
	public int updateCancelReserveOil(SqlSessionTemplate sqlSession, Reservation reserv) {
		return sqlSession.update("Member.updateCancelReserveOil", reserv);
	}

	@Override
	public String selectCancelReason(SqlSessionTemplate sqlSession, Reservation reserv) {
		return sqlSession.selectOne("Member.selectCancelReason", reserv);
	}

	@Override
	public ArrayList<CCoupon> selectUserCoupon(SqlSessionTemplate sqlSession, Member member) {
		return (ArrayList)sqlSession.selectList("Member.selectUserCoupon", member);
	}
	//아이디찾기
	@Override
	public Member findId(SqlSessionTemplate sqlSession, String email) {

		return sqlSession.selectOne("Member.findId", email);
	}
	//이메일 중복 확인
	@Override
	public Member emailCheck(SqlSessionTemplate sqlSession, String email) {

		return sqlSession.selectOne("Member.emailChk", email);
	}
	//암호화된 비밀번호 조회용 메소드
	@Override
	public String selectEncPassword(SqlSessionTemplate sqlSession, Member m) {
		System.out.println("비밀번호 조회 메소드 dao"+m);
		return sqlSession.selectOne("Member.selectPwd", m.getMember_id());
	}

	@Override
	public int insertCustomerOilCharge(SqlSessionTemplate sqlSession, ChargeOil chargeOil) {
		return sqlSession.insert("Member.insertCustomerOilCharge", chargeOil);
	}

	@Override
	public void updateCustomerOilCharge(SqlSessionTemplate sqlSession, ChargeOil chargeOil) {
		System.out.println(sqlSession);
		sqlSession.update("Member.updateCustomerOilCharge", chargeOil);
	}
	//비밀번호 찾기
	@Override
	public Member chkForPwd(SqlSessionTemplate sqlSession, Member m) {

		return sqlSession.selectOne("Member.chkForPwd", m);
	}
	
	//임시비밀번호 등록
	@Override
	public int insertTempPwd(SqlSessionTemplate sqlSession, Member m) {

		return sqlSession.update("Member.insertTempPwd", m);
	}
	
	
	

	@Override
	public ArrayList<OilList> searchOilList(SqlSessionTemplate sqlSession, OilList searchInfo, PageInfo pi) {

		int offset = (pi.getCurrentPage() - 1) * pi.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pi.getLimit());
		
		ArrayList<OilList> list = (ArrayList)sqlSession.selectList("Member.searchOilList", searchInfo, rowBounds);
		
		return list;
	}

	@Override
	public int getOilListCount(SqlSessionTemplate sqlSession, OilList searchInfo) {
		return sqlSession.selectOne("Member.getOilListCount", searchInfo);
	}


	@Override
	public int intsertRequestRefund(SqlSessionTemplate sqlSession, HashMap<String, String> requesthmap) {
		return sqlSession.insert("Member.intsertRequestRefund", requesthmap);
	}
  
	//네이버 아이디 유무 확인
	@Override
	public Member chkNaver(SqlSessionTemplate sqlSession, String member_id) {

		return sqlSession.selectOne("Member.chkNaver", member_id);
	}

	@Override
	public int insertNaver(SqlSessionTemplate sqlSession, Member m) {

		return sqlSession.insert("Member.insertNaver", m);

	}
	

}
