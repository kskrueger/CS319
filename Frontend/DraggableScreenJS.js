let gridX = 160;
let maxX = 1000;
let gridY = 160;
let courseNodes = [];
let courseNames = [];

function addCourse() {
    let numDivs = document.getElementById('courses').getElementsByTagName('div').length;
    let course = document.createElement("course");
    course.innerHTML =
        "<div id=course"+numDivs+" class='mydiv'>" +
        "   <div class='mydivheader'>" +
        "   <input id=courseInput"+numDivs+" class=courseInput width='10px' type='text' placeholder='Course Num' name='courseName' " +
        "       onKeyDown=\"if(event.keyCode===13) getCourse("+numDivs+")\"></div>\n" +
        "   <label id=credits"+numDivs+">Credits: </label><br>\n" +
        "   <label id=preReqs"+numDivs+">PreReqs: </label>\n" +
        "</div>";
    courseNodes[numDivs] = {};
    let x = 0;
    let y = -50;
    let localMaxX = -gridX;
    for (let num in courseNodes) {
        let course = courseNodes[num];
        if (course.hasOwnProperty('x') && course.y === y && course.x > localMaxX) {
            localMaxX = course.x;
        }
    }
    x = x + localMaxX + gridX;
    document.getElementById('courses').appendChild(course);
    document.getElementById('course'+numDivs).style.left = x+'px';
    document.getElementById('course'+numDivs).style.top = y+'px';
    let start = true;
    $(course).draggable({
        grid: [gridX, gridY],
        obstacle: ".notHere",
        preventCollision: true,
        containment: "#moveInHere",
        stop: function() {
            let offset = $(this).offset();
            let xPos = offset.left;
            let yPos = offset.top;
            courseNodes[numDivs]["x"] = xPos;
            courseNodes[numDivs]["y"] = yPos;
        }
    });
}

function getCourse(nodeNum) {
    let i = 0;
    let name = document.getElementById('courseInput'+nodeNum).value;
    let a = Get('http://coms-319-078.cs.iastate.edu:8080/course/'+name);
    let b = JSON.parse(a);
    if (b.hasOwnProperty("courseInput")) {
        courseNodes[nodeNum] = b;
    } else {
        alert("Course not found! Sorry")
    }
    updateCredits();
    courseNames[i] = b;
    i++;
    document.getElementById('credits'+nodeNum).innerText = "Credits: "+b.credits;
    document.getElementById('preReqs'+nodeNum).innerText = "PreReqs: "+b.prereqs;
    document.getElementById('preReqs'+nodeNum).style.fontSize = '15px';
    let x = 0;
    let y = -50;
    let localMaxX = -gridX;
    for (let num in courseNodes) {
        let course = courseNodes[num];
        if (course.hasOwnProperty('x') && course.y === y && course.x > localMaxX) {
            localMaxX = course.x;
        }
    }
    x = x + localMaxX + gridX;
    document.getElementById('course'+nodeNum).style.left = x+'px';
    courseNodes[nodeNum].x = x;
    document.getElementById('course'+nodeNum).style.top = y+'px';
    courseNodes[nodeNum].y = y;
    return b;
}

function updateCredits() {
    let sum = 0;
    for (const key in courseNodes) {
        if (courseNodes[key].hasOwnProperty("credits")) {
            sum += parseInt(courseNodes[key].credits);
        }
    }
    document.getElementById('creditsTotal').innerText = "Total Credits: "+sum;
}

function Get(yourUrl){
    var httpSite = new XMLHttpRequest(); // a new request
    httpSite.open("GET",yourUrl,false);
    httpSite.send(null);
    return httpSite.responseText;
}

/**
 * @return {string}
 */
function Post(){
    let out = [];
    for (let course0 in courseNodes) {
        let course1 = courseNodes[course0];
        if (course1.hasOwnProperty("courseInput"))
        out.push([course1.courseInput.toString(), (course1.x).toString(), (course1.y).toString()]);
    }
    let send = {};
    send["name"] = document.getElementById('courseName').value.toString();
    //send["semesterCourses"] = out;
    send["semestersCourses"] = out;

    const xhr = new XMLHttpRequest();
    const url = "http://coms-319-078.cs.iastate.edu:8080/plan"; //HERE
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    let strSend = JSON.stringify(send);
    xhr.send(strSend);
    return send["name"];
}
function saveSchedule(){
    let name = Post();

    alert("Schedule "+name+" Saved!")
    //Function to save to the User object once implemented
}