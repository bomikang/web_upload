<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="upload.do" method="post" enctype="multipart/form-data" >
		<fieldset>
			<legend>Upload</legend>
			<p>
				<label for="">파일명1</label>
				<input type="file" name="file1"/>
			</p>
			<p>
				<label for="">파일명2</label>
				<input type="file" name="file2"/>
			</p>
			<p>
				<input type="submit" value="전송"/>
			</p>
		</fieldset>
	</form>
</body>
</html>