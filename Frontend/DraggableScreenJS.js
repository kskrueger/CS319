let gridX = 160;
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
    document.getElementById('courses').appendChild(course);
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
    if (b.hasOwnProperty("courseNumber")) {
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

Function Post(yourURL, courseNames){
    var String = "";
    for(var i = 0; i < courseNames.length - 1; i++){
        String += courseNames[i] + ' , ';
    }
    String += courseNames[courseNames.length];
    var httpSite = new XMLHttpRequest(); // a new request
    httpSite.open("POST",yourUrl,false);
    httpSite.send(JSON.stringify(String));
    return httpSite.responseText;
}
function saveSchedule(courseNames){
    //Post()
    
    
    alert("Schedule Saved!")
    //Function to save to the User object once implemented
}