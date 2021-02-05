// Holds JSON data for shop tool locations
var toolData;

// Initializes the application
window.onload = function () {
    $.getJSON('tool-data.json', function(data) {
        toolData = data;

        // Large Items
        for (i = 0; i < toolData.largeItems.length; i++) {
            var ul = document.getElementById("toolList");
            var li = document.createElement("li");
            var button = document.createElement("button");

            var finalItemText = toolData.largeItems[i].name;
            button.innerHTML = finalItemText;

            var buttonAtt = document.createAttribute("onclick");
            buttonAtt.value = "clicked(this)";
            button.setAttributeNode(buttonAtt);
            li.appendChild(button);
            ul.appendChild(li);
        }

        // Small Items
        for (i = 0; i < toolData.smallItems.length; i++) {
            var ul = document.getElementById("toolList");
            var li = document.createElement("li");
            var button = document.createElement("button");

            var finalItemText = toolData.smallItems[i].name;
            button.innerHTML = finalItemText;

            var buttonAtt = document.createAttribute("onclick");
            buttonAtt.value = "clicked(this)";
            button.setAttributeNode(buttonAtt);
            li.appendChild(button);
            ul.appendChild(li);
        }
    });


    var c = document.getElementById("shopCanvas");
    var ctx = c.getContext("2d");
    var img = document.getElementById("shopEmpty");
    ctx.drawImage(img, 0, 0);
}

// Filters the tool results based on what is input into the search bar
function filter() {
    // Declare variables
    var input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('searchInput');
    filter = input.value.toUpperCase();
    ul = document.getElementById('toolList');
    li = ul.getElementsByTagName('li');

    // Loop through all list items, and hide those who don't match the search query
    for (i = 0; i < li.length; i++) {
        button = li[i].getElementsByTagName("button")[0];
        txtValue = button.textContent;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}

// Creates a circle at the location of a tool that was clicked on
function clicked(button) {
    var textValue = button.textContent;
    var xPos, yPos;
    var c = document.getElementById("shopCanvas");
    var ctx = c.getContext("2d");
    var img = document.getElementById("shopEmpty");
    ctx.drawImage(img, 0, 0);
    ctx.beginPath();

    // Input Tool Locations (change xPos and yPos to coordinates of the tool on the diagram)
    for (var i = 0; i < toolData.largeItems.length; i++) {
        if (textValue == toolData.largeItems[i].name) {
            xPos = toolData.largeItems[i].xPos;
            yPos = toolData.largeItems[i].yPos;
        }
    }

    for (var i = 0; i < toolData.smallItems.length; i++) {
        if (textValue == toolData.smallItems[i].name) {
            for (var j = 0; j < toolData.locations.length; j++) {
                //alert(textValue + "-" + locations[j].locText + "-" + smallItems[i].location + "-");
                if (toolData.smallItems[i].location == toolData.locations[j].name) {
                    xPos = toolData.locations[j].xPos;
                    yPos = toolData.locations[j].yPos;
                    break;
                }
            }
        }
    }

    // Create circle above tool location
    ctx.arc(xPos, yPos, 15, 0, 2 * Math.PI, false);
    ctx.fillStyle = 'green';
    ctx.fill();
    ctx.lineWidth = 5;
    ctx.strokeStyle = '#003300';
    ctx.stroke();
}