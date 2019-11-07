var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var xhr = new XMLHttpRequest();
var url = "http://coms-319-078.cs.iastate.edu:8080/user"; //HERE
xhr.open("POST", url, true);
xhr.setRequestHeader('Content-Type', 'application/json');
xhr.send(JSON.stringify({"username":"abcdef"}));
// THIS IS WORKING
// NOT SURE WHY THE REAL PROGRAM ISN'T