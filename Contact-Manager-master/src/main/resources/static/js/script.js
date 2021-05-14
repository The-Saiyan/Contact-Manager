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