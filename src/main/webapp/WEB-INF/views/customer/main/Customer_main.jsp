 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<html>




<head>
<jsp:include page="/WEB-INF/views/common/bootInfo.jsp"></jsp:include>
</head>

     
<body class="">
  <div class="wrapper">
   <jsp:include page="/WEB-INF/views/customer/common/nav_customer.jsp"></jsp:include>
 
   
   
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=85185db0fc452125ec8070a4279f67bb&libraries=services,clusterer,drawing"></script>
    <script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
    
     <!--  <div class="row"> -->
            <div class="map" style="position: relative;height: 90%; width: 90%;">
	          <div id="daumMap" style=" height: 100%; width: 100%; position: fixed; top: 54px; left: 0px; background-color: rgb(229, 227, 223);" >
	          </div>
	          </div>
	          
	          <div style="position:absolute; width: 100px;height: 200px;z-index:100000000;top: 7%;left: 72%;">
	          <br>
	          <div id="" style="margin:1px; background-color:white; border-radius: 10px;">
	          <span align="center" style="margin:2px; color:black; font-size:0.5em;"><img src='resources/common/img/greenMarker.png' width="25px" height="25px"> &nbsp;<b>2000원 이하</b></span>
	          <span align="center" style="margin:2px; color:black; font-size:0.5em;"><img src='resources/common/img/blueMarker.png' width="25px" height="25px"> &nbsp;<b>2000~4000</b></span>
	          <span align="center" style="margin:2px; color:black; font-size:0.5em;"><img src='resources/common/img/yellowMarker.png' width="25px" height="25px"> &nbsp;<b>4000~8000</b></span>
	          <span align="center" style="margin:2px; color:black; font-size:0.5em;"><img src='resources/common/img/pinkMarker.png' width="25px" height="25px"> &nbsp;<b>8000원 이상</b></span>
	          <span align="center" style="margin:2px; color:black; font-size:0.5em;"><img src='resources/common/img/muni.png' width="25px" height="25px"> &nbsp;<b>관리자에게 문의</b></span>
	           <span align="center" style="margin:2px; color:black; font-size:0.5em;"><img src='resources/common/img/freeparking.png' width="25px" height="25px"> &nbsp;<b>무료</b></span>
	          </div>
	          </div>
	         
	      
	          
	          <!--@@@@@@@@@@@@@@@@@@@@@2모달과 버튼  -->
	           <button id="detailparking" class="btn btn-link" data-toggle="modal" data-target=".bd-detail-modal-lg-6"></button>
	              <div class="modal fade bd-detail-modal-lg-6" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			 	  <div class="modal-dialog modal-lg" style="font-size: 0.7em; margin-top:130px;">
			    <div class="modal-content" style="background: white; color:black;">
			      <div class="modal-body1" style="padding-bottom: 0px;">
			        <div class="table-responsive" id="voiceSize" style="overflow: auto; padding-bottom: 0px;  ">
			          <table class="table tablesorter " style="margin-bottom: 0px;">
			            <tbody id="STTResult">
			                  <tr>
			                  <td align="left" colspan="5">-주차장명 <br> <b id="pname"></b></td>
			                  <td align="right" colspan="6" style="padding:0px">총자리:<span id="allseat" style="display:inline">150</span><br><span style="color:red">남은자리:<span id="extraseat" style="display:inline">100</span></span></td>
			                  </tr>
			                  <tr ><td colspan="12" style="padding:0px"><hr></td></tr>
			                  <tr>
			                  <td colspan="3"><img src="resources/common/img/pinkMarker.png" width="100px" height="100px" style="border:1px solid black;"/></td>
			                  <td colspan="7">
			                   				  <span id="paddr"></span><br>
			                  				  <b>주차요금 </b>: 30분 ,<span id="price"></span>원<br>
			                  				  <b>운영시간</b> : <span id="time"></span>
			                  </td>
			                  </tr>
			                  <tr>
			                  <td align="center" colspan="3"><button type="button" class="btn btn-sm  " data-dismiss="modal" onclick="navi()">길찾기</button></td>
			                  <td align="center" colspan="3"><button type="button" class="btn btn-sm " data-dismiss="modal">예약하기</button></td>
			                  <td align="center" colspan="3"><button type="button" class="btn btn-sm  " data-dismiss="modal">즐겨찾기</button></td>
			                  </tr>
			                  <tr>
			                  <td align="center" colspan="3"><button type="button" class="btn btn-sm  " data-dismiss="modal">문의</button></td>
			                  <td align="center" colspan="3"><button type="button" class="btn btn-sm  " data-dismiss="modal">후기</button></td>
			                  <td align="center" colspan="3"><button type="button" class="btn btn-sm  " data-dismiss="modal">닫기</button></td>
			                  <td><input type="hidden" id="member_id" name="member_id" value="">
                   				<input type="hidden" id="member_name" name="member_name" value="">
                   	                   	<input type="hidden" id="email" name="email" value=""></td>
			                  <td></td>
			                  <td></td>
			                  </tr>
			            </tbody>
			          </table>
			        </div>
			      </div>
			    </div>
			  </div>
			</div>
	           
	           
	           
	           
           <!--@@@@@@@@@@@@@@@@@@@@@2모달과 버튼  -->
           <script type='text/javascript'>
       
		 	
            
            
             /*@@@@@@@@@@@@@@@@@지도 초기 셋팅 @@@@@@@@@@@@@@@@@@@@@@@@@  */
				var mapContainer = document.getElementById('daumMap'), // 지도를 표시할 div 
				mapOption = { 
				    center: new daum.maps.LatLng(37.4989756, 127.03291420000001),
				    draggable:true,
				    scrollwheel:true,// 지도의 중심좌표
				    level: 3 // 지도의 확대 레벨
				};
				
				var map = new daum.maps.Map(mapContainer, mapOption); 
			
				
				var mypositionMarker;
			        
				 var imageSrc = 'resources/common/img/soro.gif', // 마커이미지의 주소입니다    
				    imageSize = new daum.maps.Size(70, 70), // 마커이미지의 크기입니다
				    imageOption = {offset: new daum.maps.Point(50, 50)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
				    var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize, imageOption);
				 
				 
			        // 마커와 인포윈도우를 표시합니다
			        // 마커를 생성합니다
			    mypositionMarker = new daum.maps.Marker({  
			        map: map, 
			        position:new daum.maps.LatLng(37.4989756, 127.03291420000001),
			   		image: markerImage
			    }); 
			    
			        
			    
				
				var zoomlevel=3;
				 daum.maps.event.addListener(map, 'zoom_changed', function() {
					   	zoomlevel=map.getLevel();
					}); 
		 
		 
		 
		  /*@@@@@@@@@@@@@@@@@움직 일때 마다 내위치 마커 이동 @@@@@@@@@@@@@@@@@@@@@@@@@  */
				// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
				var count=1;
				if (navigator.geolocation) {
				    console.log("지오로케이션사용");
				    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
				    navigator.geolocation.watchPosition(function(position) {
				        console.log("움직였당"+count);
				        var lat = position.coords.latitude, // 위도
				            lon = position.coords.longitude; // 경도
				        console.log(lat+","+lon);
				        var locPosition = new daum.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
				          
				        mypositionMarker.setPosition(new daum.maps.LatLng(lat, lon));
				       
				        if(count==1){
				        	 map.setLevel(3, {anchor: new daum.maps.LatLng(lat, lon)});
				        	map.setCenter(locPosition); 
				        }
				        count++;
					/* displayMarker(locPosition, message); */
				            
				      });
				}
			    function span_myP(){
		        	   var moveLatLon = mypositionMarker.getPosition();
		        	    // 지도 중심을 부드럽게 이동시킵니다
		        	    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
		        	    map.panTo(moveLatLon);            
		           }
		          
				
				
				 Kakao.init('df9b4b2c505f7b6860e9e73d0c22e278');
		           var ppname=null;
		           var latt=null;
		           var loo=null;
		           var addr =null;
		           
				 	 function navi(){
				 		    console.log(ppname);
				 		    console.log(addr);
				 		 	console.log(Number(latt));
				 		 	console.log(Number(loo));
				 		 	
					         Kakao.Navi.start({
					        	 name: ppname,
					             x: Number(loo),
					             y: Number(latt),
					             coordType: 'wgs84'
					        }); 
					    }
				
		/*@@@@@@@@@@@@@@@@@움직 일때 마다 내위치 마커 이동 @@@@@@@@@@@@@@@@@@@@@@@@@  */
		 /* else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
				    
				    var locPosition = new daum.maps.LatLng(33.450701, 126.570667),    
				        message = 'geolocation을 사용할수 없어요..'
				        
				    displayMarker(locPosition, message);
				} */

			/* 	// 지도에 마커와 인포윈도우를 표시하는 함수입니다
				function displayMarker(locPosition, message) {

				    // 마커를 생성합니다
				    var marker = new daum.maps.Marker({  
				        map: map, 
				        position: locPosition
				    }); 
				    
				    var iwContent = message, // 인포윈도우에 표시할 내용
				        iwRemoveable = true;

				    // 인포윈도우를 생성합니다
				    var infowindow = new daum.maps.InfoWindow({
				        content : iwContent,
				        removable : iwRemoveable
				    });
				    
				    // 인포윈도우를 마커위에 표시합니다 
				    infowindow.open(map, marker);
				    
				    // 지도 중심좌표를 접속위치로 변경합니다
				    map.setCenter(locPosition);      
				}     */
				
			
				
				
				var geocoder = new daum.maps.services.Geocoder();
				//클러스터 생성
				 var clusterer = new daum.maps.MarkerClusterer({
				        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
				        gridSize:700,
				        averageCenter: false, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
				        minLevel: 8 // 클러스터 할 최소 지도 레벨 
				    });
				
			 
				
				// 지도가 이동, 확대, 축소로 인해 지도영역이 변경되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
				 daum.maps.event.addListener(map, 'tilesloaded',selectnearParking);
					  
				
			
					 function selectnearParking() {
						
						 /* 	현재 지도 영역 구하기. */
						 	var bounds = map.getBounds();
						 	  // 영역정보의 남서쪽 정보를 얻어옵니다 
						    var swLatlng = bounds.getSouthWest();//x0,y1
						    // 영역정보의 북동쪽 정보를 얻어옵니다 
						    var neLatlng = bounds.getNorthEast();//x1,y0
						    
						    var x0=Number(swLatlng.getLat());
						    var x1=Number(neLatlng.getLat());
						    var y0=Number(swLatlng.getLng());
						    var y1=Number(neLatlng.getLng());
						    
						    
						   if(zoomlevel<7){
							   var markers=[];
								 clusterer.clear();
							$.ajax({
								url:"getnearParkings.cu",
								type:"get",
								data:{x0:x0,x1:x1,y0:y0,y1:y1}, 
								success:function(data){
								 	for(t in data.parkings){
										var lat =data.parkings[t].latitude;
										var lon =data.parkings[t].longitude;
										var type =data.parkings[t].operate_TYPE;
										var name =data.parkings[t].parking_NAME;
										var ntime = Number(data.parkings[t].parking_NTIME);//기본시간
										var nprice =Number(data.parkings[t].parking_NPRICE);//기본시간요금
										var atime=Number(data.parkings[t].parking_ATIME);//추가시간
										var aprice=Number(data.parkings[t].parking_APRICE);//추가시간요금
										var pinfo=data.parkings[t].price_INFO;//유료or무료
										var img=null;
										
										var allfair=0;
										
										var Platlng =new daum.maps.LatLng(lat, lon);
										
										if(pinfo=="유료"&&ntime>0){
											
										if(atime>0){	
										 if(ntime>30){
											 var em=Number(ntime/30);
											 allfair=Number(nprice/em);
										 }else{
											 var emptytime=Number(30-ntime);
											 var afairtime=Number(emptytime/atime);
											 var afair=Number(afairtime*aprice);
											 allfair=Number(nprice+afair);
										 }
										}
										else{
											if(ntime>30){
												 var em=Number(ntime/30);
												 allfair=Number(nprice/em);
												 
											 }else{
												 var em2 =Number(30/ntime);
												 allfair=Number(em2*nprice);
												 
											 }
											
										}
										}else{
											if(pinfo=="무료"){
												img='resources/common/img/freeparking.png';
											}
											 else if(atime>0){
												 if(atime>30){
													 var em=Number(atime/30);
													 allfair=Number(aprice/em);
													 
												 }else{
												// var emptytime=Number(30-ntime);
												//	 var afairtime=Number(emptytime/ntime);
												//	 var afair=Number(afairtime*nprice);
												//	 allfair=Number(nprice+afair); 
													 var em2 =Number(30/atime);
													 allfair=Number(em2*aprice);
												 }
												 
											 } 
											else{
												img='resources/common/img/muni.png';
											}
										}
										
										if((0<allfair) && (2000>=allfair)){
											 img='resources/common/img/greenMarker.png';
											}else if((2000<=allfair) && (4000>=allfair)){
											 img='resources/common/img/blueMarker.png';
											}else if((4000<=allfair) && (8000>=allfair)){
												img='resources/common/img/yellowMarker.png';
											}else if(allfair>=8000){
												img='resources/common/img/pinkMarker.png';
											}
											else{
												/*  img='resources/common/img/muni.png';  */
											}
										
										if(img==null){
											img='resources/common/img/muni.png';
										}
										
										
										
										
										var parkingImgPath =img, // 마커이미지의 주소입니다    
									    imageSize = new daum.maps.Size(30, 30), // 마커이미지의 크기입니다
									    imageOption = {offset: new daum.maps.Point(30, 30)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
									    var parkingImg = new daum.maps.MarkerImage(parkingImgPath, imageSize, imageOption);
										//마커객체 생성
										var marker2= new daum.maps.Marker({
								            position:new daum.maps.LatLng(lat, lon),
									 		image:parkingImg
								        }); 
										
										console.log(allfair);
										daum.maps.event.addListener(marker2, 'click',makeclickListener(map,marker2,data.parkings[t],allfair));
									
								
								 		markers.push(marker2);
								 		
								 		 
								 	}
								 	
								 	
								 	//마커 클릭했을떄 이벤트 등록
								 	function makeclickListener(map, marker, parking,price) {
								 	    return function() {
								 	    	/*  pname allseat extraseat */
								 	    	$("#detailparking").click();
								 	    	console.log(parking);
								 	    	ppname=parking.parking_NAME;
								 	    	addr =parking.address;
								 	    	 latt=parking.latitude;
								 	    	loo=parking.longitude;
								 	    	$("#pname").text(parking.parking_NAME);
								 	    	$("#allseat").text(parking.parking_SIZE);
								 	    	$("#extraseat").text(parking.left_SIZE);
								 	    	console.log(price);
								 	    	$("#price").text(Number(price));
								 	    	$("#time").text(parking.weekday_STIME+"~ 익일:"+parking.weekday_ETIME);
								 	    	$("#paddr").text(addr);
								 	   /* 		$.ajax({
											url:"getnearParkings.cu",
											type:"get",
											data:{x0:x0,x1:x1,y0:y0,y1:y1}, 
											success:function(data){
											 
									
										
												},error:function(status){
												console.log(status);
											}
										 }); */
								 	    	
								 	    	
								 	    	
								 	    };
								 	}
							 clusterer.addMarkers(markers);
							
									},error:function(status){
									console.log(status);
								}
							 });
						 }
					 }
				
						
				
				
				
				
				
			</script>
		
			  
			     
          </div>
  
 
 
 <!--@@@@@@@@@@@@@@@@@@@@@@@@@@@사이드바 자리입니다.  -->
 
 <jsp:include page="/WEB-INF/views/customer/common/sidebar_customer.jsp"></jsp:include>
</body>
 <script type="text/javascript">
	//네이버 로그인
  var naver_id_login = new naver_id_login("hEi80LKwFmVECmA2A2z0", "http://127.0.0.1:8765/jjg/customer.cu");
  // 접근 토큰 값 출력
 // alert(naver_id_login.oauthParams.access_token);
  // 네이버 사용자 프로필 조회
  naver_id_login.get_naver_userprofile("naverSignInCallback()");
  // 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
  function naverSignInCallback() {
    console.log(naver_id_login.getProfileData('email'));
    console.log(naver_id_login.getProfileData('name'));
  	console.log(naver_id_login.getProfileData('id'));
  	/* $("#member_id").val(naver_id_login.getProfileData('id'));
    $("#member_name").val(naver_id_login.getProfileData('name'));
     */
     var member_id=naver_id_login.getProfileData('id');
    var member_name=naver_id_login.getProfileData('name');
    var email = naver_id_login.getProfileData('email');
     console.log(member_id);
     console.log(member_name);
     console.log(email);
      location.href="./naverLogin.cu?member_id="+member_id+"&member_name="+member_name+"&email="+email;
   // alert(naver_id_login.getProfileData('age'));
  }
</script>
</html>