let gridX = 160;
let maxX = 1000;
let gridY = 160;
let courseNodes = [];
let courseNames = [];
let currentUpid = -1;
let currentName = "";

function addCourse() {
    let numDivs = document.getElementById('courses').getElementsByTagName('div').length;
    let course = document.createElement("course");
    course.innerHTML =
        "<div id=course" + numDivs + " class='mydiv'>" +
        "   <div class='mydivheader'>" +
        "   <input id=courseInput" + numDivs + " class=courseInput width='10px' type='text' placeholder='Course Num' name='courseName' " +
        "       onKeyDown=\"if(event.keyCode===13) getCourse(" + numDivs + ")\"><button class=\"btn\"><i class=\"fa fa-close\" onClick=remove(" + numDivs + ")></i></button></div>\n" +
        "   <label id=credits" + numDivs + ">Credits: </label><br>\n" +
        "   <label id=preReqs" + numDivs + ">PreReqs: </label>\n" +
        "</div>";
    let x = 0;
    let y = -50;
    let localMaxX = -gridX;
    let done = false;
    while (!done) {
        for (let num in courseNodes) {
            let course0 = courseNodes[num];
            if (course0.hasOwnProperty('x') && course0.y === y && course0.x > localMaxX) {
                localMaxX = course0.x;
            }
        }
        if (localMaxX > maxX) {
            localMaxX = -gridX;
            y += gridY;
        } else {
            done = true;
            break;
        }
    }
    x = localMaxX + gridX;
    document.getElementById('courses').appendChild(course);
    document.getElementById('course' + numDivs).style.left = x + 'px';
    document.getElementById('course' + numDivs).style.top = y + 'px';
    courseNodes[numDivs] = {};
    courseNodes[numDivs].x = x;
    courseNodes[numDivs].y = y;
    let start = true;
    $(course).draggable({
        grid: [gridX, gridY],
        obstacle: ".notHere",
        preventCollision: true,
        containment: "#moveInHere",
        stop: function () {
            let offset = $(this).offset();
            let xPos = offset.left;
            let yPos = offset.top;
            courseNodes[numDivs]["x"] = xPos;
            courseNodes[numDivs]["y"] = yPos;
        }
    });
    return numDivs;
}

function getCourse(nodeNum, courseInput = null) {
    let i = 0;
    let name = document.getElementById('courseInput' + nodeNum).value;
    if (courseInput != null) {
        document.getElementById('courseInput' + nodeNum).value = courseInput;
        name = courseInput;
    }
    let a = Get('http://coms-319-078.cs.iastate.edu:8080/course/' + name);
    let b = JSON.parse(a);
    if (b.hasOwnProperty("courseInput")) {
        courseNodes[nodeNum] = b;
    } else {
        alert("Course not found! Sorry")
    }
    updateCredits();
    courseNames[i] = b;
    i++;
    document.getElementById('credits' + nodeNum).innerText = "Credits: " + b.credits;
    document.getElementById('preReqs' + nodeNum).innerText = "PreReqs: " + b.prereqs;
    document.getElementById('preReqs' + nodeNum).style.fontSize = '15px';
    courseNodes[nodeNum].x = parseInt(document.getElementById('course' + nodeNum).style.left);
    courseNodes[nodeNum].y = parseInt(document.getElementById('course' + nodeNum).style.top);
    return b;
}

function remove(nodeNum) {
    document.getElementById('course' + nodeNum).remove();
    courseNodes.splice(nodeNum, 1);
    updateCredits();
}

function updateCredits() {
    let sum = 0;
    for (const key in courseNodes) {
        if (courseNodes[key].hasOwnProperty("credits")) {
            sum += parseInt(courseNodes[key].credits);
        }
    }
    document.getElementById('creditsTotal').innerText = "Total Credits: " + sum;
}

function Get(yourUrl) {
    var httpSite = new XMLHttpRequest(); // a new request
    httpSite.open("GET", yourUrl, false);
    httpSite.send(null);
    return httpSite.responseText;
}

/**
 * @return {string}
 */
function Post() {
    let out = [];
    for (let course0 in courseNodes) {
        let course1 = courseNodes[course0];
        if (course1.hasOwnProperty("courseInput"))
            out.push([course1.courseInput.toString(), (course1.x).toString(), (course1.y).toString()]);
    }
    let send = {};
    send["name"] = document.getElementById('courseName').value.toString();
    if (send["name"] === currentName && currentUpid !== -1) {
        send["upid"] = currentUpid;
    }
    send["semestersCourses"] = out;

    const xhr = new XMLHttpRequest();
    const url = "http://coms-319-078.cs.iastate.edu:8080/plan"; //HERE
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    let strSend = JSON.stringify(send);
    xhr.send(strSend);
    let response = xhr.responseText;
    currentUpid = parseInt(response.substring(response.indexOf("#")+1, response.indexOf(":")));
    currentName = send["name"];
    return send["name"];
}

function saveSchedule() {
    let name = Post();
    alert("Schedule " + name + " Saved!")
    //Function to save to the User object once implemented
}

function loadSchedule() {
    let coursesList = document.getElementById('courses');
    while (coursesList.hasChildNodes()) {
        coursesList.removeChild(coursesList.lastChild);
    }
    courseNodes = [];
    const name = document.getElementById("courseName");
    let url = 'http://coms-319-078.cs.iastate.edu:8080/plan/name/' + name.value
    let a = Get(url);
    let b = JSON.parse(a);
    currentUpid = parseInt(b["upid"]);
    let courses = b["semestersCourses"];
    currentName = name.value;//b["name"];
    for (let i in courses) {
        let course = courses[i];
        let nodeNum = addCourse(course[0]);
        getCourse(nodeNum, course[0]);
        document.getElementById('course' + nodeNum).style.left = course[1] + 'px';
        document.getElementById('course' + nodeNum).style.top = course[2] + 'px';
    }
}