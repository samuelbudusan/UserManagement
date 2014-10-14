<%--
  Created by IntelliJ IDEA.
  User: samuelbudusan
  Date: 10/10/2014
  Time: 12:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Access Denied</title>
</head>
<body>

<h1> ${httpStatusMessage}</h1>

<h1> ${msg}</h1>

<button id="cancel" type="button">Back</button>

<script>
    document.getElementById("cancel").onclick = function () {
        window.history.back();
    };
</script>
</body>
</html>
