<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Change role</title>
</head>
<body>
<p>User : ${user.userName}</p>

<c:choose>
    <c:when test="${isModerator}">
        <p>Role: MODERATOR </p>
    </c:when>
    <c:otherwise>
        <p>Role: USER </p>
    </c:otherwise>
</c:choose>

<button id="cancel" class="butons" type="button">Cancel</button>


<c:choose>
    <c:when test="${isModerator}">
        <a href="/addOrRemoveRole?moderator=false">
            <button class="butons" type="button">Remove Moderator Role</button>
        </a>
    </c:when>
    <c:when test="${!isModerator}">
        <a href="/addOrRemoveRole?moderator=true">
            <button class="butons" type="button">Add Moderator Role</button>
        </a>
    </c:when>
</c:choose>

<script>
    document.getElementById("cancel").onclick = function () {
        window.history.back();
    };

    document.getElementById("add").onclick = function () {
        location.href = "/addOrRemoveRole?moderator=true";
    };

    document.getElementById("remove").onclick = function () {
        location.href = "/addOrRemoveRole?moderator=false";
    };
</script>
</body>
</html>
