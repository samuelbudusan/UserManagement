<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
    <link rel="stylesheet" type="text/css" href="resources/success.css">

    <title>LOGIN</title>

</head>


<body>
<c:url value="/j_spring_security_logout" var="logoutUrl"/>
<ul class="meniu">
    <li><a href="https://www.google.ro/?gws_rd=cr&ei=aCnFU4zzA4e0ywPAzoGQCw">Home</a></li>
    <li><a href="/changePassword">Change Password</a></li>
    <li><a href="/edit">Edit User Information</a></li>
    <c:if test="${isAdmin == 1}">
        <li><a href="/editUsers">Edit Users</a></li>
        <style> li {
            display: inline;
            margin-left: 170px;
        } </style>
    </c:if>
    <li class="logout"><a href="${logoutUrl}">Logout</a></li>
</ul>
<h1 class="username">${userName}</h1>
<c:choose>
    <c:when test="${isOk == 0}">
        <p class="username">Your password has been successfully changed!</p>
    </c:when>
    <c:when test="${isOk == 1}">
        <p class="username">Your account has been successfully edited!</p>
    </c:when>

</c:choose>

<div class="div">
    <h1 class="title">Groups</h1>

    <div id="table_wrapper">
        <table class="table1">
            <tr>
                <td class="t1">Group Name</td>
            </tr>
        </table>
        <div id="tbody">
            <table class="table2">
                <c:forEach var="i" begin="1" end="5">
                    <c:forEach items="${groupList}" var="group">
                        <tr onclick="myFunction(this)">
                            <td class="td1">${group.groupName}</td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<c:if test="${isAdmin == 1}">
    <h1 id="groups"><a href="/groupsManagement" style="color:black">Groups Management</a></h1>
</c:if>

<script>
    function myFunction(x) {
        var str = "/getGroup?index=" + x.rowIndex;
        window.location = str;
    }
</script>

</body>
</html>

