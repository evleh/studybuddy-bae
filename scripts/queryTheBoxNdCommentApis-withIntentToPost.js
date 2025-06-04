
var mychaindebugger = function(input) {
    console.log(input);
    return(input);
}
var debug_the_everything = false;
if (!debug_the_everything) { mychaindebugger = (data) => {return data;} }

fetch("http://localhost:8080/boxes")
    .then((response) => response.json())
    .then((data) => mychaindebugger(data))
    .then((data) => {
        console.log(`got ${data.length} entries when asking the api for boxes`);
    }); // this is not await


fetch("http://localhost:8080/boxcomments")
    .then((response) => response.json())
    .then((data) => mychaindebugger(data))
    .then((data) => {
        console.log(`got ${data.length} entries when asking the api for comments`);
    });

// src for this: https://developer.mozilla.org/en-US/docs/Web/API/RequestInit#examples
var makeABoxApiFetchRequestObject = function(requestBody) {
    return new Request("http://localhost:8080/box", {
        method: "POST",
        body: JSON.stringify(requestBody),
        headers: {
            "Content-Type": "application/json"
        }
    })
}

var makeRandomBoxForPost = function() {
    return {
        title: `A Title with a Random Number at the End. (${Math.floor(Math.random() * 1000)})`,
        description: "And a description, too. The number might or might not be 4. See https://xkcd.com/221/"
    }
}


fetch(makeABoxApiFetchRequestObject(makeRandomBoxForPost()))
    .then((response) => response.json())
    .then((data) => mychaindebugger(data))
    .then((data) => {
        console.log(`something happened. the promise resolved to success. here is some data: `);
        console.log(data);
        return data;
    }, (data) => {
        console.log(`something happened. the promise resolved to NO sucess. here is some data: `);
        console.log(data);
        return data;
    });