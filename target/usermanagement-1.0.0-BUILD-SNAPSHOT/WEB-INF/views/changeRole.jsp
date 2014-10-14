<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Change role</title>
    <link rel="stylesheet" type="text/css"
          href="resources/changeRole.css">
</head>
<body>

<div class="div">
    <h1 class="title">User Group&Role</h1>

    <p class="p">User : ${user.userName}</p>

    <c:choose>
        <c:when test="${isModerator}">
            <p class="p">Role: MODERATOR </p>
        </c:when>
        <c:otherwise>
            <p class="p">Role: USER </p>
        </c:otherwise>
    </c:choose>

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

    <a href="/removeUserFromGroup">
        <button class="butons" type="button">Remove User From Group</button>
    </a>

    <button id="cancel" class="butons" type="button">Cancel</button>

</div>
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
