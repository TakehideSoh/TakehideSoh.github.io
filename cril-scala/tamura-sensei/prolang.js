MathJax.Hub.Config({
        TeX: {
            Macros: {
                    F: "\\bot",
                    T: '\\top',
                    FV: '\\mbox{F}',
                    TV: '\\mbox{T}',
                    imp: '\\mathop{\\Rightarrow}',
                    lra: '\\longrightarrow',
                    llra: '\\longleftrightarrow',
                    Llra: '\\Longleftrightarrow',
                    Lra: '\\Longrightarrow',
                    Lla: '\\Longleftarrow',
                    nts: ['\\mbox{"}#1\\mbox{"}', 1],
                    equ: '\\Leftrightarrow',
                    Equ: '\\mathrel{\\,\\sim\\,}',
                    bold: ['{\\bf #1}', 1],
                    Def: '\\stackrel{\\textrm{def}}{\\Longleftrightarrow}',
                    EquSat: '\\stackrel{\\textrm{equi-sat}}{\\Longleftrightarrow}',
                    D: '\\Delta',
                    G: '\\Gamma',
                    Infer: ['\\lower-0.8em\\cfrac{#3}{#2}\\;\\lower-0.8em\\rlap{\\small\\rm #1}{}', 3],
                    // Infer: ['\\begin{array}{c}#3\\\\\\hline #2\\end{array}\\;\\vphantom{\\small\\rm #1}', 3],
                    AND: '\\qquad\\qquad',
            }
        }
    });

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
