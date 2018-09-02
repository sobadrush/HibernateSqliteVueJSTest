<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@taglib prefix="sp" uri="http://www.springframework.org/tags" %>
 
<!DOCTYPE html>
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta charset="UTF-8">
    <title>Index.jsp</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <style type="text/css">
    	.margin-around {
    		margin: 5px;
    	}
    </style>
  </head>
  <body>
      
      <div class="container">
      	<div class="row">
      		<div class="col-2" style="background:pink;height:0.5cm;"></div>
      		<div class="col" style="background:lightgreen;height:0.5cm;"></div>
      		<div class="col-2" style="background:lightblue;height:0.5cm;"></div>
      	</div>
      </div>
      
      <div class="container">
      	<div class="row">
      		<div class="col-2"></div>
      		<div class="col">
		      <h1 style="color:brown;">index.jsp</h1>
		      
		      <button type="button" id="btn1" class="btn btn-danger margin-around">Test Spring-MVC (Form POST)</button> <br>
      		
      		  <button type="button" id="btn2" class="btn btn-success margin-around">Test Spring-MVC (ajax POST 回Json-String)</button> <br>
      		  
      		  <button type="button" id="btn3" class="btn btn-warning margin-around">Test Spring-MVC (ajax POST 回PlayerVO)</button> <br>
      		  
      		  <button type="button" id="btn4" class="btn btn-info margin-around">Test Spring-MVC (ajax POST 接收VO)</button> <br>
      		  
      		  <button type="button" id="btn5" class="btn btn-dark margin-around">Test Spring-MVC (查Emps)</button> <br>
      		
      		</div>
      		<div class="col-2"></div>
      	</div>
      </div>
      
      <script> 
		  $(function(){
			  
			  // >>> 普通Form POST <<<
			  $('#btn1').click(function(e){
				  var toUrl = '<sp:url value="/TestController/testPost"/>';
			      var postData = { 'myName' : 'Roger' };								  
				  dynamicFormPost( toUrl , postData ); 
			  });
			  
			  // >>> ajax POST , 後端回自己組的 JSON-String <<<
			  $('#btn2').click(function(e){
				  var toUrl = '<sp:url value="/TestController/testAjaxPost"/>';
			      var postData = { 'empName' : 'Roger' , 'empLevel' : 'Manager' , 'empAge' : '26' };								  
			      $.ajax({ 
			    	    url: toUrl ,
			    	    type:"POST", 
			    	    contentType: "application/json; charset=utf-8",
			    	    data: toJson( postData ), // Stringified Json Object
			    	    async: true,     // Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
			    	    cache: false,     // This will force requested pages not to be cached by the browser          
			    	    processData:false, // To avoid making query String instead of JSON
			    	    success: function( cbData ){
			    	    	console.log( ' cbData >>> ' , cbData );
			    	        alert('SUCCESS!!!');
			    	    }
			       });
			  });
			  
			  // >>> ajax POST , 後端回在TestController中宣告的DeptVO <<<
			  $('#btn3').click(function(e){
				  var toUrl = '<sp:url value="/TestController/testAjaxPostReturnPlayerVO"/>';
			      var postData = { 'empName' : 'Roger333' , 'empLevel333' : 'Manager333' , 'empAge333' : '26333' };								  
			      $.ajax({ 
			    	    url: toUrl ,
			    	    type:"POST", 
			    	    contentType: "application/json; charset=utf-8",
			    	    data: toJson( postData ), // Stringified Json Object
			    	    async: true,     // Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
			    	    cache: false,     // This will force requested pages not to be cached by the browser          
			    	    processData:false, // To avoid making query String instead of JSON
			    	    success: function( cbData ){
			    	    	console.log( ' cbData >>> ' , cbData );
			    	        alert('SUCCESS!!!');
			    	    }
			       });
			  });
			  
			  // >>> ajax POST , 後端透過@RequestBody直接將前端送過去的JSON字串，轉換成VO <<<
			  $('#btn4').click(function(e){
				  var toUrl = '<sp:url value="/TestController/testAjaxPostReceivePlayerVO"/>';
			      var postData = { 'playerName' : 'Roger' , 'playerRole' : 'Magician' , 'playerAge' : '18' };								  
			      $.ajax({ 
			    	    url: toUrl ,
			    	    type:"POST", 
			    	    contentType: "application/json; charset=utf-8",
			    	    data: toJson( postData ), // Stringified Json Object
			    	    async: true,     // Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
			    	    cache: false,     // This will force requested pages not to be cached by the browser          
			    	    processData:false, // To avoid making query String instead of JSON
			    	    success: function( cbData ){
			    	    	console.log( ' cbData >>> ' , cbData );
			    	        alert('SUCCESS!!!');
			    	    }
			       });
			  });
			  
			  // >>> ajax POST , 後端透過@RequestBody直接將前端送過去的JSON字串，轉換成VO <<<
			  $('#btn5').click(function(e){
				  var toUrl = '<sp:url value="/EmpController/getAllEmps"/>';
			      var postData = { 'playerName' : 'Roger' , 'playerRole' : 'Magician' , 'playerAge' : '18' };								  
			      $.ajax({ 
			    	    url: toUrl ,
			    	    type:"POST", 
			    	    contentType: "application/json; charset=utf-8",
			    	    data: toJson( postData ), // Stringified Json Object
			    	    async: true,     // Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
			    	    cache: false,     // This will force requested pages not to be cached by the browser          
			    	    processData:false, // To avoid making query String instead of JSON
			    	    success: function( cbData ){
			    	    	console.log( ' cbData >>> ' , cbData );
			    	        alert('SUCCESS!!!');
			    	    }
			       });
			  });
			  
		  })  
		  
		  function dynamicFormPost( _url , _params ){
			 // console.log( ' _url >>> ' , _url );
			 var bodyTag = $('body');
			 var tempForm = $('<form></form>').css('display','none').prop('method' , 'POST').prop('action' , _url);
			 
			 var documentFragment = $(document.createDocumentFragment());
			 for( key in _params ){
				 // console.log( ' key >>> ' , key , ' value >>> ' , _params[key] );
				 var inputTag = $('<input type="text"></input>').prop( 'name' , key ).prop( 'value' , _params[key] );
				 documentFragment.append( inputTag );
			 }
			 tempForm.append( documentFragment );
			 tempForm.appendTo( bodyTag );
			 tempForm.submit();
			 return;
		  }
		  
		  function toJson( _jsonObj ){
			  return JSON.stringify( _jsonObj );
		  }
		  
      </script>
  </body>
</html>
