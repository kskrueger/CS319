function login(){
    var username = document.getElementById("username");
	var password = document.getElementById("password");
    var login = false;
    //TEST CODE
//    var userList = [{"uuid":1,"username":"asdfasdf","password":"adsfasdf","major":"add","email":"adsf@asdf.com"},{"uuid":2,"username":"abcdef"},{"uuid":3,"username":"WORKING1DAB","password":"asdfadsf","major":"dabbb","email":"it@WORKS.dab"},{"uuid":4,"username":"Karterrrrr","password":"asdfghjkl","major":"As","email":"A@k.com"},{"uuid":5,"username":"djleach","password":"ddddddddddddd","major":"ddd","email":"Daultonleach@yahoo.com"},{"uuid":6,"username":"djleach","password":"password1","major":"SE","email":"Daultonleach@yahoo.com"}];
     var userList = [{}];
    var url = "http://coms-319-078.cs.iastate:8080/users"; //HERE
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", url, true);
    xmlhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        userList = JSON.parse(this.responseText);
        myFunction(userList);
        }
    };
    myFunction(userList);
    xmlhttp.send();

}
function myFunction(myArr){
    for(var i = 0; i < myArr.length; i++){
        if(username.value == myArr[i].username && password.value == myArr[i].password){
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