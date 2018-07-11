window.onload = () => {
    // AJAX test, remove when done
    let ajaxbtn = document.getElementById("testajax");
    ajaxbtn.onclick = () => {
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                let jsonresponse = JSON.parse(this.response);
                ajaxbtn.innerHTML = jsonresponse.name;
            }
        };
        xhttp.open("GET", "testAjax", true);
        xhttp.send();
    }
}