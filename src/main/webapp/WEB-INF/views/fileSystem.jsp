<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="${pageContext.servletContext.contextPath}/resources/dist/libs/jquery.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/dist/libs/contextMenu/contextMenu.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/dist/jstree.min.js"></script>
    <link rel="stylesheet"href="${pageContext.servletContext.contextPath}/resources/dist/themes/default/style.min.css"/>
    <script src="${pageContext.servletContext.contextPath}/resources/js/fileSystemFunctions.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/js/jsTreeFunctions.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/js/selectableUIFunctions.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/fileSystem.css">
    <link rel="stylesheet" type="text/css" href="resources/dist/libs/contextMenu/contextMenu.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
    <title>File System</title>
</head>
<body>

<div class="div">
    <input type="image" src="resources/images/goBack.png" id="goBack" onclick="makeAjaxCall(currentNode,'backward')" onmouseover="mOver(this)">
    <input type="image" src="resources/images/newFolder.png" id="newFolder" onclick="newNode(currentNode)">
    <input id="path" type="text">
    <input type="image" src="resources/images/x.png" id="exit" onclick="location.href='/success'">

    <div id="fileExplorer">
        <div id="jstree"></div>
        <div id="tbody">
            <table id="selectable" >
                <c:forEach items="${nodeList}" var="node">
                    <tr ondblclick="makeAjaxCall('${node.nodeID}','forward' )"
                        oncontextmenu="myMenu(this);return false">
                        <td><img src="resources/images/img.jpg" height=15px; style="margin-right:5px">${node.name}</td>
                        <td style="display:none;">${node.nodeID}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<script>
    var json = [${tree}];
    var currentNode = 1;

    $('#jstree').jstree({
        'core': { data: json },
        'plugins': ["contextmenu", "wholerow"],
        "contextmenu": { items: customMenu }
    });

    $("#jstree").bind("select_node.jstree", function (e, data) {
        makeAjaxCall(data.node.id, 'forward')
    });

    function makeAjaxCall(ID, direction) {
        var table = document.getElementById("selectable");
        $.ajax({
            async: true,
            type: "get",
            url: "/getChildren",
            data: 'node=' + ID + '&direction=' + direction,
            dataType: 'json',
            success: function (response) {
                var json = response.data;
                var rows = "";
                $.each(json, function (idx, obj) {
                    rows += " <tr ondblclick=\"makeAjaxCall('" + obj.id + "' , 'forward' )\" oncontextmenu=\"myMenu(this);return false\"> ";
                    rows += "<td><img src=\"resources/images/img.jpg\" height=15px; style=\"margin-right:5px\">" + obj.name + "</td>";
                    rows += "<td style=\"display:none;\">" + obj.id + "</td> </tr>"
                });
                table.innerHTML = rows;
                $('#path').val(response.path);
                currentNode = response.currentNode;
            },
            error: function (response) {
                alert('Error while request..');
            }
        });
    }

    // selectable UI
    $("#tbody").selectable({
        filter: 'tbody tr',
        cancel: '.ui-selected',
        stop: function () {
            selectedArray.length = 0;
            $(".ui-selected", this).each(function () {
                var current = this.children[1].innerText;
                selectedArray.push(current);
            });
        }
    });

    function mOver(obj) {

    }

</script>
</body>
</html>
