
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

//mychaindebugger(commentjson);
//mychaindebugger(boxjson);

let boxes_by_id = {};
boxjson.forEach(function(b) { boxes_by_id[b.id] = b; }) ;
let boxcomments_by_id = {};
commentjson.forEach(function(c) { boxcomments_by_id[c.id] = c; });


let sort_comments_by = [null,...boxjson.map((b) => b.id)];
//mychaindebugger(sort_comments_by);

let line_for_box = function(id) {
    if (id == null) {
        return "Comments with no Box:";
    } else {
        var box = boxes_by_id[id];
        return `Box with ID ${box.id} and Name '${box.title}' has the following comments assigned:`;
    }
}

let line_for_box_comment = function(id) {
    if (id == null) { console.log("should not happen?"); return "eeeek"; } else {
        var comment = boxcomments_by_id[id];
        return `    Text: '${comment.text}'`;
    }
}

let comments_with_boxid = function(boxid) {
    return commentjson.filter(function(comment) { return comment.boxId == boxid; });
}


sort_comments_by.forEach(function(box_id_or_null) {
    //mychaindebugger(box_id_or_null);
    console.log(line_for_box(box_id_or_null));
    comments_with_boxid(box_id_or_null).forEach(function(comment) {
        console.log(line_for_box_comment(comment.id));
    })
});