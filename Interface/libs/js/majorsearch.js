$('#table').bootstrapTable({
    pagination:true,
    pageSize:10,
    pageList:[10,20],
    columns: [{
        field: 'id',
        title: '位次'
    }, {
        field: 'schoolname',
        title: '院校名称'
    }, {
        field: 'avgscore',
        title: '平均分'
    }, {
        field: 'maxscore',
        title: '最高分'
    }, {
        field: 'batch',
        title: '批次'
    }]
});

function majorsearch(){
	let data = {
	    majorname: $("#majorname").val(),
        province: $('#province').val(),
        year: $('#year').val(),
        type: $('#type').val()
    };
	var resultdata;
	var tabledata;
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/URsystem/majorToSchool',
        async: false,
        data: data,
        success: function(result) {
        	if(result.length == 0){
        		alert("没有找到相关的数据哦~");
        	}
        	resultdata = result;
        }
    })
    tabledata = new Array();
	for(var i = 0; i < resultdata.length; i++){
		elem = {};
		elem.id = resultdata[i].ranking;
		elem.schoolname = resultdata[i].school;
		elem.avgscore = resultdata[i].averageScore;
		elem.maxscore = resultdata[i].maxScore;
		elem.batch = resultdata[i].batch;
		tabledata.push(elem);
	}
	$('#table').bootstrapTable('load', tabledata);
}
