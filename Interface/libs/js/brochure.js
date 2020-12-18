function schoolsearch(){
    let data = {
        schoolname: $("#schoolname").val()
    };
    var brochuredata;
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/URsystem/brochure',
        // url: 'http://localhost:8080/Admissions_war_exploded/brochure',
        async: false,
        data: data,
        success: function(result) {

            brochuredata = result;
            if(result === "None"){
                alert("没有找到这所大学的数据哦~");
            }
        }
    })
    if(brochuredata !== "None"){
        document.getElementById('brochurediv').innerHTML = brochuredata;
    }
}


