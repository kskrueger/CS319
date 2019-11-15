let gridX = 120;
let gridY = 100;
function dragElement(elmnt) {
    var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    if (document.getElementById(elmnt.id)) {
        /* if present, the header is where you move the DIV from:*/
        document.getElementById(elmnt.id).onmousedown = dragMouseDown;
    } else {
        /* otherwise, move the DIV from anywhere inside the DIV:*/
        elmnt.onmousedown = dragMouseDown;
    }

    function dragMouseDown(e) {
        e = e || window.event;
        e.preventDefault();
        // get the mouse cursor position at startup:
        pos3 = e.clientX;
        pos4 = e.clientY;
        document.onmouseup = closeDragElement;
        // call a function whenever the cursor moves:
        document.onmousemove = elementDrag;
    }

    function elementDrag(e) {
        e = e || window.event;
        e.preventDefault();
        // calculate the new cursor position:
        pos1 = pos3 - e.clientX; // pos3 = initialTranslateX, pos1 = amount of change
        pos2 = pos4 - e.clientY;

        pos4 = e.clientY;
        pos3 = e.clientX;

        // set the element's new position:
        //dragContext.initialTranslateX + ((int) (mouseEvent.getX()) / gridX) * gridX - dragContext.mouseAnchorX
        //elmnt.style.left = (elmnt.offsetLeft - Math.floor(pos3/gridX) * gridX) + "px";
        //elmnt.style.top = (elmnt.offsetTop - Math.floor(pos4/gridY) * gridY) + "px"; // add current position + amount of change
        elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
        elmnt.style.top = (elmnt.offsetTop - pos2) + "px"; // add current position + amount of change
    }

    function closeDragElement() {
        /* stop moving when mouse button is released:*/
        document.onmouseup = null;
        document.onmousemove = null;
    }
}

function addCourse() {
    let numDivs = document.getElementById('courses').getElementsByTagName('div').length;
    var course = document.createElement("divvvv");
    course.innerHTML =
        "<div id=div-"+numDivs+" class='mydiv'>\n" +
        "        <div id='aaaa' class='mydivheader'>Course</div>\n" +
        "        <label>Credits: 3</label><br>\n" +
        "        <label>Prereqs: </label>\n" +
        "    </div>";                // Insert text
    //courses.push(course);
    //dragElement(course);
    document.getElementById('courses').appendChild(course);
    let childDivs = document.getElementById('courses').getElementsByTagName('div');
    for(let i = 0; i < childDivs.length; i++) {
        dragElement(childDivs[i]);
    }
}

function intDiv(a,b) {
    let result = a/b;
    if(result>=0)
        return Math.floor(result);
    else
        return Math.ceil(result);
}