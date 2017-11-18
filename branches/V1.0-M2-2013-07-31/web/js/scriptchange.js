//<script type="text/javascript" src="./jquery/jquery-1.7.min.js"></script>

function chan(bgimage ,no, isPortion)
{
    var nom = eval(no.substr(3));

    if (nom%4 == 0) {
        document.getElementById(nom).style.visibility = 'hidden';
        document.getElementById(nom).style.display = 'none';

        document.getElementById(nom-3).style.visibility = 'visible';
        document.getElementById(nom-3).style.display = 'block';
        document.getElementById(nom-3).setAttribute("class", "btn_"+bgimage);
    }else {
        document.getElementById(nom).style.visibility = 'hidden';
        document.getElementById(nom).style.display = 'none';

        if ((!isPortion) & (bgimage == "left" || bgimage == "right")){
            var el = document.getElementById(nom);
            var p = null;
            if (el != null){
                p = el.parentNode;
                if (p != null){
                    var clearId = eval($(p).children('input[class=btn_clear]')[0].id);
                    var fullId  = eval($(p).children('input[class=btn_full]')[0].id);
                    var isDefault = fullId < clearId;
                    nom = nom - 2;
                    if (isDefault){
                        bgimage = "full";
                    }
                    else{
                        bgimage = "clear";
                    }
                }
            }
        }

        document.getElementById(nom+1).style.visibility = 'visible';
        document.getElementById(nom+1).style.display = 'block';
        document.getElementById(nom+1).setAttribute("class", "btn_"+bgimage);
    }
    return bgimage;

}
