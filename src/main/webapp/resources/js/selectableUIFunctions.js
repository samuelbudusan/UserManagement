var selectedArray = new Array();
var menu = [
    {
        name: 'Cut',
        title: 'cut button',
        fun: function () {
            addNodesToMemory(selectedArray, "cut");
        }
    },
    {
        name: 'Copy',
        title: 'copy button',
        fun: function () {
            addNodesToMemory(selectedArray, "copy");
        }
    },
    {
        name: 'Rename',
        title: 'rename button',
        fun: function () {
            renameNode(selectedArray[selectedArray.length - 1]);
        }
    },
    {
        name: 'Delete',
        title: 'delete button',
        fun: function () {
            deleteNodes(selectedArray);
        }
    },
    {
        name: 'New Node',
        title: 'newNode button',
        fun: function () {
            newNode(selectedArray[selectedArray.length - 1]);
        }
    },
    {
        name: 'Paste',
        title: 'paste button',
        disable: 'true',
        fun: function () {
            newNode(selectedArray[selectedArray.length - 1]);
        }
    }
];

function myMenu(x) {
    if (selectedArray.length != 0) {
        $(x).contextMenu(menu, {
            triggerOn: 'click',
            displayAround: 'cursor'
        })
    }
}
