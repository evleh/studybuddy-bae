
var mychaindebugger = function(input) {
    console.log(input);
    return(input);
}
var debug_the_everything = true;
if (!debug_the_everything) { mychaindebugger = (data) => {return data;} }


fetch("http://localhost:8080/boxes")
    .then((response) => response.json())
    .then((data) => mychaindebugger(data)) // die zeile und die funktion oben kann man weglassen, aber gut fuer debuging methinks.
    .then((data) => {
        console.log(`got ${data.length} entries when asking the api for boxes`);
    }); // this is not await


fetch("http://localhost:8080/boxcomments")
    .then((response) => response.json())
    .then((data) => mychaindebugger(data)) // die zeile und die funktion oben kann man weglassen, aber gut fuer debuging methinks.
    .then((data) => {
        console.log(`got ${data.length} entries when asking the api for comments`);
    });
