<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<head>
<script src="js/jquery.min.js"></script>
<script>
$(document).ready(function(){
    $("button").click(function(){
        $.getJSON("water-points.json", function(result){
            $.each(result, function(i, field){
                $("div").append(field + " ");
            });
        });
    });
});

function getData()
{
	 var data = '{"name": "mkyong","age": 30,"address": {"streetAddress": "88 8nd Street","city": "New York"},"phoneNumber": [{"type": "home","number": "111 111-1111"},{"type": "fax","number": "222 222-2222"}]}';
	$.ajax({
		type:"GET",
		  dataType: "json",
		  url: "/mydata",
		  data: data,
		  success: success
		});
}
</script>
</head>
<body>

<button>Get JSON data</button>

<div></div>

</body>
</html>