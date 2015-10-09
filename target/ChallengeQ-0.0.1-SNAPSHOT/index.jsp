<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
function mydata()
{
	var data = data.json;
	var mydata = JSON.parse(data);
	alert (mydata.name);
	alert (mydata.sex);
}
function getdd()
{
	$.getJSON('data.json', function(data) {
		  console.log(data);
		});
	}
function getData()
{
	 // var url = data.json
	
	 $.ajax({
		 type: 'POST',
		 dataType: 'json',
		 contentType:'application/json',
		 url: "water-points.json",
		 data:JSON.stringify(data),
		 success: function(data, textStatus ){
		 console.log(data);
		 alert("success");
		
		 },
		 error: function(xhr, textStatus, errorThrown){
		 alert('request failed'+errorThrown);
		 }
		 });
}
</script>	
<button onclick="getdd()"> Click me</button>

</body>
</html>