function JSValidate(){
	var username = document.getElementById("username");
	var password = document.getElementById("password");
	var email = document.getElementById("email");
	var major = document.getElementById("major");
	if(!alphaNumCheck(p1.value)){
        alert("Username Name Must contain only alphabetic or numeric characters.");
	}
	if(!alphaNumCheck(p2.value)){
        alert("Last Name Must contain only alphabetic or numericcharacters.");
	}
	if(p3.value == ""){
        alert("Please Select a Field");
	}
	if(p4.value == ""){
        alert("Please Select a Field");
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

function passwordCheck(entry){
    
}

