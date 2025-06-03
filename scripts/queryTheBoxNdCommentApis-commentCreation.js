
var mychaindebugger = function(input) {
    console.log(input);
    return(input);
}
var debug_the_everything = true;
if (!debug_the_everything) { mychaindebugger = (data) => {return data;} }


let boxresponse = await fetch("http://localhost:8080/box");
let boxjson = await boxresponse.json();
console.log(`got ${boxjson.length} entries when asking the api for boxes`)


let commentresponse = await fetch("http://localhost:8080/boxcomment");
let commentjson = await commentresponse.json();
console.log(`got ${commentjson.length} entries when asking the api for comments`);

let randomBoxId = () => boxjson[Math.floor(Math.random() * boxjson.length)].id;

// checking the randomBoxId function
console.log(boxjson.map((d) => d.id));
console.log(randomBoxId());

let randomBoxCommentForRandomBoxPostBody = function() { return {
    boxId: randomBoxId(),
    text: `The Text of a generated comment. Here is a random number: ${Math.floor(Math.random() * 1000)} (yay)`
}};

let postARandomCommentForRandomBox = function() {
    let reqBody = randomBoxCommentForRandomBoxPostBody()
    let request = new Request("http://localhost:8080/boxcomment", {
        method: "POST",
        body: JSON.stringify(reqBody),
        headers: {
            "Content-Type": "application/json"
        }
    });
    mychaindebugger(reqBody);
    mychaindebugger(request);
    fetch(request)
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
}

postARandomCommentForRandomBox()