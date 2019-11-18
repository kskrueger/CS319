function login(){
    let userList = JSON.parse(Get("http://coms-319-078.cs.iastate.edu:8080/users"));
    checkCredentials(userList);

}
function checkCredentials(myArr){
    const username = document.getElementById("username");
    const password = document.getElementById("password");
    let login = false;
    for(let i = 0; i < myArr.length; i++){
        if(username.value === myArr[i].username && password.value === myArr[i].password){
            login = true;
            break;
        }
        else{
            login = false;
        }
    }
    if(login === true){
        window.open("SchedulingPage.html");
    }
    else{
        alert("Invalid Login Credentials");
    }
}

/**
 * @return {string}
 */
function Get(yourUrl){
    var httpSite = new XMLHttpRequest(); // a new request
    httpSite.open("GET",yourUrl,false);
    httpSite.send(null);
    return httpSite.responseText;
}