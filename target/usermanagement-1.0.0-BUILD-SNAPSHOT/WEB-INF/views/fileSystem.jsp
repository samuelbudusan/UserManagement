<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="resources/fileSystem.css">
    <title>File System</title>
</head>

<body>

<div class="div">

    <h1 class="title">File System</h1>
    <c:if test="${selectedNodes.size() > 0}">
        <button class="buttons" onclick="">Paste</button>
        <button class="buttons" onclick="cancelOperation('${currentNode.getName()}')">Cancel</button>
        <style>
            .div { height: 570px;}
        </style>
    </c:if>

    <%--  <form:form>
      <form:input path="file path"/>
      </form:form>--%>

    <input id="path" type="text" name="path" value="${nodePath}">

    <div id="tbody">
        <table id="table">
            <c:forEach items="${nodeList}" var="node">
                <tr ondblclick="enterFolder('${node.name}' )"
                    oncontextmenu="select('${node.name}',this);return false">
                    <td><img src="resources/images/img.jpg" height=15px; style="margin-right:5px">${node.name}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <button class="buttons" style="width:50px;" type="button" onclick="back('${currentNode.getParent().name}')">Back</button>
    <button class="buttons" onclick="newNode('${currentNode.getName()}')">New Node</button>
    <select id="list">
        <option value="open">Open</option>
        <option value="rename">Rename</option>
        <option value="delete">Delete</option>
        <option value="copy">Copy</option>
        <option value="cut">Cut</option>
        <option value="properties">Properties</option>
    </select>
    <button class="buttons" onclick="option('${currentNode.getName()}')">Execute</button>
</div>


<%--<button class="buttons" onclick="renameNode('${currentNode.getName()}')">Rename</button>
<button class="buttons" onclick="deleteNodes('${currentNode.getName()}')">Delete</button>
<button class="buttons" onclick="renameNode('${currentNode.getName()}')">Copy</button>--%>

<p id="errors">${errors}</p>

<p id="demo"></p>

<c:forEach items="${selectedNodes}" var="node">
    <p>${node}</p>
</c:forEach>

<script>

    function option(currentNode) {
        var option = document.getElementById("list").value;
        var str="!!:";
        switch (option) {
            case 'open' : openFolder(currentNode); break;
            case 'rename' : renameNode(currentNode); break;
            case 'delete' : deleteNodes(currentNode); break;
            case 'copy' :  str ="copyyy"; break;
            case 'cut' : cutNode(currentNode); break;
            case 'properties' : str ="renameeeeeeee"; break;
        }
       document.getElementById("demo").innerHTML = str;
    }

    function openFolder(currentNode) {
        var nodeList = selectedRows(currentNode);
        if (nodeList.length == 1) {
            var str = "/fileSystem?node=" + nodeList[0];
            window.location = str;
        }
    }

    function selectedRows(currentNode) {
        var count = 0;
        var table = document.getElementById("table").rows;
        var selectedRows = new Array();

        for (var i = 0; i < table.length; i++) {
            if (table[i].style.backgroundColor == "yellow") {
                count += 1;
                selectedRows.push(table[i].innerText);
            }
        }
        return selectedRows;
    }

    function select(node, x) {
        var current = x.style.backgroundColor;
        if (current == "yellow") {
            x.style.backgroundColor = '';
        } else {
            x.style.backgroundColor = 'yellow';
        }
        return false;
    }

    function newNode(currentNode) {
        var newNode = prompt("Node name", "");
        if (newNode != null) {
            var str = "/newNode?node=" + currentNode + "&newNode=" + newNode;
            window.location = str;
        }
    }

    function enterFolder(node) {
        var str = "/fileSystem?node=" + node;
        window.location = str;
    }

    function back(parent) {
        var str;
        if (parent) {
            str = "/fileSystem?node=" + parent;
        } else {
            str = "/success"
        }
        window.location = str;
    }

    function deleteNodes(currentNode) {
        var nodeList = selectedRows(currentNode);
        if (nodeList.length > 0) {
            if (confirm("Are you sure that you want to permanently delete the selected files?") == true) {
                str = "/deleteNode?node=" + currentNode + "&nodeList=" + nodeList;
                window.location = str;
            }
        }
    }

    function renameNode(currentNode) {
        var nodeList = selectedRows(currentNode);
        if (nodeList.length > 0) {
            if (nodeList.length > 1) {
                alert("You can rename just one file!");
            } else {
                var newName = prompt("Current name: " + nodeList[0] + "\nNew name:", "");
                if (newName != null) {
                    var str = "/renameNode?node=" + currentNode + "&nodeName=" + nodeList[0] + "&newName=" + newName;
                    window.location = str;
                }
            }
        }
    }

    function cutNode(currentNode) {
        var nodeList = selectedRows(currentNode);
        if (nodeList.length > 0) {
                str = "/cutNode?node=" + currentNode + "&nodeList=" + nodeList;
                window.location = str;
            }
    }

    function cancelOperation(currentNode) {
        var str = "/cancelOperation?node=" + currentNode;
        window.location = str;
    }

</script>
</body>

</html>
