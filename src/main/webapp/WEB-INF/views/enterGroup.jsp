<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>${group.groupName}</title>
    <link rel="stylesheet" type="text/css"
          href="resources/enterGroup.css">
</head>
<body>

<div class="div">
    <h1 class="title">${group.groupName}</h1>

    <div id="table_wrapper">
        <table class="table1">
            <tr>
                <td class="t1">Users</td>
            </tr>
        </table>
        <div id="tbody">
            <table class="table2" id="groupTable">
                <c:forEach items="${group.users}" var="user">
                    <tr>
                        <td class="td1">${user.userName}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <textarea rows="40" cols="100">
razvan: Hello claudiu, how are you ?
claudiu: I'm very well today, how about you sir ?
    </textarea>

    <a href="/success">
        <button class="butons" type="button">Back</button>
    </a>

    <input type="text" name="text" class="typingArea">
    <button class="butons" type="submit">Send</button>
</div>
</body>
</html>
