function JSValidate(){
	var username = document.getElementById("username");
	var password = document.getElementById("password");
	var email = document.getElementById("email");
	var major = document.getElementById("major");
    var userList = [{}];
    var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
    var xhr = new XMLHttpRequest();
    var url = "http://coms-319-078.cs.iastate.edu:8080/user"; //HERE
    xhr.open("POST", url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify({"username":"abc"}));
    alert("HELLO");
    alert(xhr.status);
    alert(xhr.responseText);
    //xhr.setRequestHeader("Content-Type", "application/json");
    var pass = true;
	if(!alphaNumCheck(username.value)){
        alert("Username Must contain only alphabetic or numeric characters.");
        pass = false;//
	}
    else if(username.value.length === 0 || username.value.length > 15){
        alert("Username must contain atleast 1 and at most 14 characters");
        pass = false;
    }
    if(!checkEmail(email.value)){
        alert("Please Enter a valid email address");
        pass = false;
	}
	if(!alphaNumCheck(password.value)){
        alert("Password must contain only alphabetic or numericcharacters.");
        pass = false;
	}
    else if(password.value.length < 8 || password.value.length > 15){
        alert("Password must be atleast 8 characters and at most 14 characters");
        pass = false;
    }
    if(major.value === ""){
        alert("Please Fill out the Major field")
        pass = false;
    }
    if(pass === true){
        var Json = JSON.stringify({"username":username.value, "password":password.value, "major":major.value, "email":email.value});
        //xhr.send(Json);
    }
    //xhr.send(JSON.stringify({"username":"abcddddd"}));
}
	
function alphaNumCheck(entry) {
    var regex = /^[a-z0-9]+$/i;
    if (entry != null && entry.match(regex)) {
        return true;
    } else {
        return false;
    }
}

function checkEmail(entry){
    var regex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}/i;
    if(entry != null && entry.match(regex)){
        return true
    }
    return false;
}
function showPass(entry) {
  if (entry.type === "password") {
    entry.type = "text";
  } else {
    entry.type = "password";
  }
}

