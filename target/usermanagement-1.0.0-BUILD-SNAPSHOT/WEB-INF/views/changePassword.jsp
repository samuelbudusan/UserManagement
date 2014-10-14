<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <link rel="stylesheet" type="text/css"
          href="resources/changePassStyle.css">
    <title>Change Password</title>
</head>
<body>
<div class="div">
    <h1 class="title">Change Password</h1>
    <form:form action="${myUrl}" commandName="user" method="POST"
               cssClass="form">
        <% String user = request.getParameter("userName"); %>
        <table class="myTable">
            <tr>
                <td>Current Password:</td>
                <td><input type="password" name="currentPassword"/></td>
            </tr>
            <tr>
                <td>New Password:</td>
                <td><input type="password" name="newPassword"/></td>
            </tr>
            <tr>
                <td>Confirm Password:</td>
                <td><input type="password" name="confirmPassword"/></td>
            </tr>
        </table>
        <a href="/success">
            <button class="butons" type="button">Cancel</button>
        </a>
        <button class="butons" type="submit">Save Changes</button>
        <c:if test="${fail ==0}">
            <p class="wrong ">${errors}<br>Please try again.
            </p>
        </c:if>
    </form:form>


</div>
</body>
</html>

