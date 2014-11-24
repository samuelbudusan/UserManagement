function renameNode(node) {
    var newName = prompt("New name:", "");
    if (newName != null) {
        var str = "/renameNode?nodeID=" + node + "&newName=" + newName;
        window.location = str;
    }
}

function newNode(currentNode) {
    var newNode = prompt("Node name", "");
    if (newNode != null) {
        var str = "/newNode?node=" + currentNode + "&newNode=" + newNode;
        window.location = str;
    }
}

function deleteNodes(node) {
    if (confirm("Are you sure that you want to permanently delete the selected files?") == true) {
        var str = "/deleteNode?nodeList=" + node;
        window.location = str;
    }
}

function addNodesToMemory(nodeList, operation) {
    $.ajax({
        type: "get",
        url: "/addNodesToMemory",
        data: "operation=" + operation + "&nodeList=" + nodeList,
        success: function(response){
            $.varBool.disabled = false;
        },
        error: function(response){
            alert('Error while request..');
        }
    });
}

function paste(currentNode) {
    $.varBool.disabled = true;
    var str = "/paste?node=" + currentNode;
    window.location = str;
}

