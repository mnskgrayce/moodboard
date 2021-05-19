function insert_options () {
    for (let index = 0; index < 5; index++) {
        var checkbox_html = 
        `<div class="form-check">
        <label class="form-check-label">
            <input type="checkbox" class="form-check-input" name="" id="" value="checkedValue" checked>
            My option ${index}
        </label>
        </div>`
        document.querySelector(".custom-scrollbox").innerHTML += checkbox_html;
    }       
}
    
    