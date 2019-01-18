function toggleAnswer(ans) {
    var nodes = ans.getElementsByTagName("dd");
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].style) {
            if (nodes[i].style.display == "block") {
                nodes[i].style.display = "none";
            } else {
                nodes[i].style.display = "block";
            }
        }
    }
}
