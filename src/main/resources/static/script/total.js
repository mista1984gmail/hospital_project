var tds = document.getElementsByName("price");

let total = 0;

for(var i=0; i<tds.length; i++)
    total += parseInt(tds[i].innerHTML);

document.getElementById('granted').innerHTML=total;