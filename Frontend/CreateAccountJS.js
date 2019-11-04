function JSValidate(){
	var username = document.getElementById("username");
	var password = document.getElementById("password");
	var email = document.getElementById("email");
	var major = document.getElementById("major");
	if(!alphaNumCheck(username.value)){
        alert("Username Must contain only alphabetic or numeric characters.");
	}
    else if(username.value.length == 0 || username.value.length > 15){
        alert("Username must contain atleast 1 and at most 14 characters");
    }
    if(!checkEmail(email.value)){
        alert("Please Enter a valid email address");
	}
	if(!alphaNumCheck(password.value)){
        alert("Password must contain only alphabetic or numericcharacters.");
	}
    else if(password.value.length < 8 || password.value.length > 15){
        alert("Password must be atleast 8 characters and at most 14 characters");
    }
    if(major.value == ""){
        alert("Please Fill out the Major filed")
    }
    
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


