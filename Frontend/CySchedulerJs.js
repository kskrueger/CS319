function login(){
    var username = document.getElementById("username");
	var password = document.getElementById("password");
    var xhr = new XMLHttpRequest();
    var url = "http://coms-319-078.cs.iastate:8080/users"; //HERE
    xhr.onreadystatechange = function () { 
        if (xhr.readyState === 4 && xhr.status === 200) { 
  
            var userList = JSON.parse(this.responseText);
  
        } 
     };
    xhr.open("GET", url, true);
    xhr.send();
    
}