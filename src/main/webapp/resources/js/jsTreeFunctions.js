var pasteBool = false;
$.varBool = { disabled : true };

function customMenu(node) {
    // The default set of all items
    var items = {
        cutItem: {
            label: "Cut",
            action: function (data) {
                var inst = $.jstree.reference(data.reference);
                var obj = inst.get_json(data.reference).id;
                addNodesToMemory(obj,"cut");
            }
        },
        copyItem: {
            label: "Copy",
            action: function (data) {
                var inst = $.jstree.reference(data.reference);
                var obj = inst.get_json(data.reference).id;
                addNodesToMemory(obj,"copy")
            }
        },
        renameItem: { // The "rename" menu item
            label: "Rename",
            action: function (data) {
                var inst = $.jstree.reference(data.reference);
                var obj = inst.get_json(data.reference).id;
                renameNode(obj);
            }
        },
        deleteItem: { // The "delete" menu item
            label: "Delete",
            action: function (data) {
                var inst = $.jstree.reference(data.reference);
                var obj = inst.get_json(data.reference).id;
                deleteNodes(obj);
            }
        },
        createItem: { // The "create" menu item
            label: "New Node",
            action: function (data) {
                var inst = $.jstree.reference(data.reference);
                var obj = inst.get_json(data.reference).id;
                newNode(obj);
            }
        },
        paste: { // The "create" menu item
            label: "Paste",
            _disabled :  function () {
                if ($.varBool.disabled) {
                    return true;
                }
                else {
                    return false;
                }
            },
            action: function (data) {
                var inst = $.jstree.reference(data.reference);
                var obj = inst.get_json(data.reference).id;
                paste(obj);
            }
        }
    };
    return items;
}