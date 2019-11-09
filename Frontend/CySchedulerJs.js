function login(){
    var username = document.getElementById("username");
	var password = document.getElementById("password");
    var login = false;
    var userList = [{}];
    var url = "http://coms-319-078.cs.iastate:8080/users"; //HERE
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", url, true);
    xmlhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        userList = JSON.parse(this.responseText);
        myFunction(myArr);
        }
    };
    xmlhttp.send();

}
function myFunction(arr){
    for(var i = 0; i < myArr.length; i++){
        if(username.value == myArr[i].username && password.value == myArr[i].password.value){
            login = true;
            break;
        }
        else{
            login = false;
        }
    }
    if(login == true){
        window.open("SchedulingPage.html");
    }
    else{
        alert("Invalid Login Credentials");
    }
}