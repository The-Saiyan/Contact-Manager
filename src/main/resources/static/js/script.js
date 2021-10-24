const toggleSidebar = () => {
    if ($('.sidebar').is(":visible")) {
        // Here I will close the toggle 
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left","0%")
    }
    else {
        // here i will enable the toggle
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left","20%")
    }
}
function copyToClipBoard(elem) {
    var range = document.createRange();
    range.selectNode(elem); //changed here
    window.getSelection().removeAllRanges();
    window.getSelection().addRange(range);
    document.execCommand("copy");
    window.getSelection().removeAllRanges();

    var span = event.target.previousElementSibling;
    span.innerText = "copied to clipboard!";
    span.style.color = "green";
    setTimeout(()=> {span.innerText = ""}, 2000);
    console.log("copeid to ");
}

const search = () => {
    let query = $("#search-input").val();
    let url = `http://localhost:8085/search/${query}`;
    fetch(url).then(response => {
        return response.json();
    }).then(data => {
        let text = `<div class = 'list-group'>`;
        data.forEach(contact => {
            text += `<a href = '/user/${contact.cid}/contact' class = 'list-group-item list-group-action'> ${contact.name} </a>`;
        });
        text += `</div>`;
        $(".search-result").html(text);
        $(".search-result").show();
    })
    if (query == '') {
        $(".search-result").hide();
    }
}