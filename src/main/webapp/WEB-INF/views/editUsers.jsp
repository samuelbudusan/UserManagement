<%@ page import="com.evozon.usermanagement.model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="resources/editUsersStyle.css">
    <title>Edit Users</title>
</head>
<body>

<div class="div">
    <h1 class="title"> Edit user accounts</h1>

    <div id="table_wrapper">
        <table class="table1">
            <tr>
                <td class="t1">Username</td>
                <td class="t2">Email</td>
                <td class="t3">Birthdate</td>
                <td class="t4">Phone</td>
                <td class="t5">Firstname</td>
                <td class="t6">Lastname</td>
                <td class="t7">Account status</td>
            </tr>
        </table>
        <div id="tbody">
            <table class="table2">
                <c:forEach var="i" begin="1" end="5">
                    <c:forEach items="${userList}" var="user">
                        <tr onclick="myFunction(this)">
                            <td class="td1">${user.userName}</td>
                            <td class="td2">${user.email}</td>
                            <td class="td3">${user.birthdate}</td>
                            <td class="td4">${user.phone}</td>
                            <td class="td5">${user.firstName}</td>
                            <td class="td6">${user.lastName}</td>
                            <td class="td7">
                                <c:choose>
                                    <c:when test="${user.enabled == 1}">
                                        <p> Enabled </p>
                                    </c:when>
                                    <c:otherwise>
                                        <p> Disabled </p>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </table>
        </div>
    </div>
    <a href="/success">
        <button class="butons" type="button">Back</button>
    </a>
</div>


<script>
    function myFunction(x) {
        var str = "/editUserByAdmin?index=" + x.rowIndex;
        window.location = str;
    }
</script>
</body>
</html>
