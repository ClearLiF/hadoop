<%--
  Created by IntelliJ IDEA.
  User: 李琪
  Date: 2019/12/30
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<s:actionerror></s:actionerror>
<s:form namespace="/up" action="uploadFile" enctype="multipart/form-data" method="POST">
    <s:label>upload.Upload your file</s:label>
    <s:file id="photo" name="photo"></s:file>

    <s:submit name="submit" label="上传"></s:submit>
</s:form>
</body>
</html>
