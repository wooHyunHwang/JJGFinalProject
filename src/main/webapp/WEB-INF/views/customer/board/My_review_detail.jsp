<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<jsp:include page="/WEB-INF/views/common/bootInfo.jsp"></jsp:include>



</head>

<body class="">
  <div class="wrapper">
    <jsp:include page="/WEB-INF/views/customer/common/sidebar_customer.jsp"></jsp:include>

        <div class="main-panel">
      <!-- Navbar -->
      <nav class="navbar navbar-expand-lg navbar-absolute navbar-transparent">
        <div class="container-fluid">
          <div class="navbar-wrapper">
            <div class="navbar-toggle d-inline">
              <button type="button" class="navbar-toggler">
                <span class="navbar-toggler-bar bar1"></span>
                <span class="navbar-toggler-bar bar2"></span>
                <span class="navbar-toggler-bar bar3"></span>
              </button>
            </div>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <div>
               <button class="btn btn-link" id="search-button" data-toggle="modal" data-target="#searchModal"><i class="tim-icons icon-zoom-split" style="color: white;"></i>
               </button>
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <button class="btn btn-link" data-toggle="modal" data-target=".bd-example-modal-lg-6"><i class="fas fa-microphone-alt" style="color: white;"></i>
               </button>
            </div>
            <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 음성 인식 모달 -->
            <div class="modal fade bd-example-modal-lg-6" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
           <div class="modal-dialog modal-lg">
             <div class="modal-content" style="background: rgb(39,41,61);">
               <div class="modal-body">
                 <div class="table-responsive"  style="overflow: hidden;">
                   <table class="table tablesorter " id="">
                     <tbody>
                           <tr><td align="center"><b><i class="fas fa-microphone-alt" style="color: white;"></i> &nbsp;&nbsp; : &nbsp;&nbsp; 안녕하세요??</b></td></tr>
                           <tr>
                              <td align="center">
                                 검색 결과
                              </td>
                           </tr>
                           <tr><td align="center"><button type="button" class="btn btn-default" data-dismiss="modal" onclick="window.location.reload();">닫기</button></td></tr>      
                     </tbody>
                   </table>
                 </div>
               </div>
             </div>
           </div>
         </div>
        <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->          </div>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navigation" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-bar navbar-kebab"></span>
            <span class="navbar-toggler-bar navbar-kebab"></span>
            <span class="navbar-toggler-bar navbar-kebab"></span>
          </button>
          <div class="collapse navbar-collapse" id="navigation">
            <ul class="navbar-nav ml-auto">
              <li class="search-bar input-group">
                <button class="btn btn-link" id="search-button" onclick="location.href='customerInfo.cu'" style="color: gray;"><i class="tim-icons icon-badge"></i>&nbsp;&nbsp;Info Edit
                  <span class="d-lg-none d-md-block"></span>
                </button>
              </li>
              <li class="search-bar input-group">
                <button class="btn btn-link" id="search-button" style="color: gray;"><i class="tim-icons icon-button-power"></i>&nbsp;&nbsp;LogOut
                  <span class="d-lg-none d-md-block"></span>
                </button>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      <div class="modal modal-search fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="searchModal" aria-hidden="true">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <input type="text" class="form-control" id="inlineFormInputGroup" placeholder="SEARCH">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <i class="tim-icons icon-simple-remove"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
      <!-- End Navbar -->


<div class="content">
        <div class="row">
		  <div class="col-md-12" height="500px">
            <div class="card ">
              <div class="card-header">
                <h3 class="card-title" align="center">내 후기</h3>
                <hr style="border: solid 1px white;">
              		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!-- BOARD TITLE -->
			
			
			<tr height="20"><!-- BOARD CONTENTS -->
				
				<td>제목</td>
			</tr>
			
			<tr height="200">
				
				<td valign="top" style="border: 1px solid white">내용</td>
			</tr>
			
			<tr height="40">
				<th>첨부파일</th>
				<td>
				</td>
			</tr>
			<tr>
			<td><td>
			</tr>
			
			<tr><!-- BOARD BUTTONS -->
				<td colspan="5" align="center">
					
					<button class="btn btn-info btn-sm">수정</button>&nbsp;
					<button class="btn btn-info btn-sm">삭제</button>&nbsp;					
					<button class="btn btn-info btn-sm">목록</button>&nbsp;					
				
				</td>
			</tr>
		</table>
		<br>
		<br>
		<br>
        
              </div>
            </div>
          </div>
        </div>
      </div>
	
</body>
</html>