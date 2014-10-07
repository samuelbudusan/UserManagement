<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="resources/groups.css">
    <title>Groups Management</title>
</head>
<body>
<p>Groups:</p>
<div class="div">
    <h1 class="title">Groups Management</h1>
    <div id="table_wrapper">
        <table class="table1">
            <tr>
                <td class="t1">Group Name</td>
                <td class="t2">Nr. of members</td>
            </tr>
        </table>
        <div id="tbody">
            <table class="table2">
                <c:forEach var="i" begin="1" end="5">
                    <c:forEach items="${groupList}" var="group">
                        <tr onclick="myFunction(this)">
                            <td class="td1">${group.groupName}</td>
                            <td class="td2">${group.getUsers().size()}</td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </table>
        </div>
    </div>
    <a href="/success">
        <button class="butons" type="button">Back</button>
    </a>
    <a href="/newGroup">
        <button class="butons" type="button">New Group</button>
    </a>
</div>


<script>
    function myFunction(x) {
        var str = "/editGroup?index="+x.rowIndex;
        window.location = str;
    }
</script>

</body>
</html>
