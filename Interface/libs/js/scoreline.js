$('#table').bootstrapTable({
    pagination:true,
    pageSize:5,
    pageList:[5,10,20,30],
    columns: [{
        field: 'id',
        title: '位次'
    }, {
        field: 'name',
        title: '专业名称'
    }, {
        field: 'avgscore',
        title: '平均分'
    },{
    	field: 'maxscore',
    	title: '最高分'
    }]
});


var myChart = echarts.init(document.getElementById('main'));

// 初始化
var scoreline_score; // 折线图数据(分数)
var scoreline_year; // 折线图数据(年份)
var major; // 表格数据(专业)

// 配置表格的初始数据
var option = {
    xAxis: {
        type: 'category',
        data: ['2010', '2011', '2012', '2013', '2014', '2015', '2016', '2017', '2018']
    },
    yAxis: {
        type: 'value'
    },
    series: [{
        data: ['0', '0', '0', '0', '0', '0', '0', '0', '0'],
        type: 'line'
    }],
    tooltip:{
        trigger: 'axis',
        axisPointer: {
            type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
        }
    }
};

// 显示图表
myChart.setOption(option);

function schoolsearch(){
	// schoolsearch() 函数，在点击查询按钮之后，会调用该函数
	let data = {
            schoolname: $("#schoolname").val()
        };

    var scoredata;
    var majordata;
    var minscore;

    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/URsystem/scoreline',
        // url: 'http://localhost:8080/Admissions_war_exploded/scoreline',
        async: false,
        data: data,
        success: function(result) {
        	// 初始化scoreline_score和scoreline_year
        	scoreline_score = new Array(34);
        	scoreline_year = new Array(34);
        	for(var i = 1; i <= 33; i ++){
        		scoreline_score[i] = new Array(3);
        		scoreline_year[i] = new Array(3);
        		for (var j = 1; j <= 2; j ++){
        			scoreline_score[i][j] = new Array();
        			scoreline_year[i][j] = new Array();
        		}
        	}
        	// 解析result，存入scoredata
        	scoredata = JSON.parse(result);
        	if(scoredata.length == 0){
        		alert("没有找到这所大学的投档线数据哦~");
        	}
        }
    })

    for(var i = 0; i < scoredata.length; i++){
    	// 获取数据
    	var province = scoredata[i].province;
    	var score = scoredata[i].score;
    	var year = scoredata[i].year;
    	var type = scoredata[i].type;
    	// 按照省份和文理科存入相应的数组
    	scoreline_score[province][type].push(score);
    	scoreline_year[province][type].push(year);
    }

    // 冒泡排序，将年份从小到大排序，并且将分数也进行对应的转换
    for(var province = 1; province <= 33; province ++){
    	for (var type = 1; type <= 2; type ++){
    		var len = scoreline_year[province][type].length;
    		for(var i = 0; i < len - 1; i++){
    			for(var j = 0; j < len - 1- i; j++){
    				if(scoreline_year[province][type][j] > scoreline_year[province][type][j + 1]){
    					var tmp_year = scoreline_year[province][type][j];
    					var tmp_score = scoreline_score[province][type][j];
    					scoreline_year[province][type][j] = scoreline_year[province][type][j + 1];
    					scoreline_score[province][type][j] = scoreline_score[province][type][j + 1];
    					scoreline_year[province][type][j + 1] = tmp_year;
    					scoreline_score[province][type][j + 1] = tmp_score;
    				}
    			}
    		}
    	}
    }

    // 初始化展示，展示 北京 理科 数据
    minscore = 1000;
    for(var i = 0; i < scoreline_score[1][1].length; i++){
    	// 获取分数最小值（用于折线图动态范围变化）
    	if(scoreline_score[1][1][i] < minscore){
    		minscore = scoreline_score[1][1][i];
    	}
    }

    var option = {
		    xAxis: {
		        type: 'category',
		        data: scoreline_year[1][1]
		    },
		    yAxis: {
		        type: 'value',
		        scale: true,
		        min: minscore - 50
		    },
		    series: [{
		        data: scoreline_score[1][1],
		        type: 'line'
		    }],
		    tooltip:{
		        trigger: 'axis',
		        axisPointer: {
		            type: 'cross',
		            label: {
		                backgroundColor: '#6a7985'
		            }
		        }
		    }
		};
	myChart.setOption(option);

	$.ajax({
        type: "POST",
        url: 'http://localhost:8080/URsystem/schoolToMajor',
        // url: 'http://localhost:8080/Admissions_war_exploded/schoolToMajor',
        async: false,
        data: data,
        success: function(result) {
        	majordata = result;
        	major = new Array(34);

        	if(majordata.length == 0){
        		alert("没有找到这所大学的专业分数线数据哦~");
        	}

        	// 初始化表格数据major
        	for(var i = 1; i <= 33; i++){
        		major[i] = new Array(3);
        		for(var j = 1; j <= 2; j++){
        			major[i][j] = new Array(4);
        			for(var k = 1; k <= 3; k++){
        				major[i][j][k] = new Array();
        			}
        		}
        	}


        	// 添加数据
        	for(var i = 0; i < majordata.length; i++){
        		province = majordata[i].Province;
        		type = majordata[i].type;
        		year = majordata[i].year - 2014;
        		majorlist = majordata[i].majorList;
        		for(var j = 0; j < majorlist.length; j++){
        			elem = {};
        			elem.id = majorlist[j].ranking;
        			elem.name = majorlist[j].majorName;
        			elem.avgscore = majorlist[j].averageScore;
        			elem.maxscore = majorlist[j].maxScore;
        			major[province][type][year].push(elem);
        		}
        	}

        	// 展示数据
        	$('#table').bootstrapTable('load', major[1][1][1]);
        }
    })
}

$("#scoreline_province").change(function(){
	var province = $("#scoreline_province").val();
	var type = $("#scoreline_type").val();

	// 获取最小值
	minscore = 1000;
    for(var i = 0; i < scoreline_score[province][type].length; i++){
    	if(scoreline_score[province][type][i] < minscore){
    		minscore = scoreline_score[province][type][i];
    	}
    }

	if(scoreline_year[province][type].length == 0){
		option = {
		    xAxis: {
		        type: 'category',
		        data: ['0']
		    },
		    yAxis: {
		        type: 'value'
		    },
		    series: [{
		        data: ['0'],
		        type: 'line'
		    }],
		    tooltip:{
		        trigger: 'axis',
		        axisPointer: {
		            type: 'cross',
		            label: {
		                backgroundColor: '#6a7985'
		            }
		        }
		    }
		};
		alert("没有相关数据哦~");
	} else{
		option = {
		    xAxis: {
		        type: 'category',
		        data: scoreline_year[province][type]
		    },
		    yAxis: {
		        type: 'value',
		        scale: true,
		        min: minscore - 50
		    },
		    series: [{
		        data: scoreline_score[province][type],
		        type: 'line'
		    }],
		    tooltip:{
		        trigger: 'axis',
		        axisPointer: {
		            type: 'cross',
		            label: {
		                backgroundColor: '#6a7985'
		            }
		        }
		    }
		};
	}
	myChart.setOption(option);
});

$("#scoreline_type").change(function(){
	var province = $("#scoreline_province").val();
	var type = $("#scoreline_type").val();

	// 获取最小值
	minscore = 1000;
    for(var i = 0; i < scoreline_score[province][type].length; i++){
    	if(scoreline_score[province][type][i] < minscore){
    		minscore = scoreline_score[province][type][i];
    	}
    }

	if(scoreline_year[province][type].length == 0){
		option = {
		    xAxis: {
		        type: 'category',
		        data: ['0']
		    },
		    yAxis: {
		        type: 'value'
		    },
		    series: [{
		        data: ['0'],
		        type: 'line'
		    }],
		    tooltip:{
		        trigger: 'axis',
		        axisPointer: {
		            type: 'cross',
		            label: {
		                backgroundColor: '#6a7985'
		            }
		        }
		    }
		};
		alert("没有相关数据哦~");
	} else{
		option = {
		    xAxis: {
		        type: 'category',
		        data: scoreline_year[province][type]
		    },
		    yAxis: {
		        type: 'value',
		        scale: true,
		        min: minscore - 50
		    },
		    series: [{
		        data: scoreline_score[province][type],
		        type: 'line'
		    }],
		    tooltip:{
		        trigger: 'axis',
		        axisPointer: {
		            type: 'cross',
		            label: {
		                backgroundColor: '#6a7985'
		            }
		        }
		    }
		};
	}
	myChart.setOption(option);
});

$('#major_province').change(function(){
	var province = $("#major_province").val();
	var type = $('#major_type').val();
	var year = $('#major_year').val() - 2014;
	$('#table').bootstrapTable('load', major[province][type][year]);
});

$('#major_type').change(function(){
	var province = $("#major_province").val();
	var type = $('#major_type').val();
	var year = $('#major_year').val() - 2014;
	$('#table').bootstrapTable('load', major[province][type][year]);
});

$('#major_year').change(function(){
	var province = $("#major_province").val();
	var type = $('#major_type').val();
	var year = $('#major_year').val() - 2014;
	$('#table').bootstrapTable('load', major[province][type][year]);
});

window.addEventListener("resize",function(){
	myChart.resize();
});
