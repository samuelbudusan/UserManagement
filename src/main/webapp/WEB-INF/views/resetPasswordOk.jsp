<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
    <link rel="stylesheet" type="text/css" href="resources/resetPasswordOkStyle.css">
    <title>ResetPasswordOk</title>
</head>

<body>
<p>Your new password is: ${newPassword}</p>
<a href="/login">
    <button
            class="butons" type="button">Back to login
    </button>
</a>
</body>

</html>