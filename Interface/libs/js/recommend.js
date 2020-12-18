$('#table').bootstrapTable({
	pagination:true,
    pageSize:5,
    pageList:[5,10,20,30],
    columns: [{
        field: 'id',
        title: '推荐位次'
    }, {
        field: 'major',
        title: '推荐专业'
    }, {
        field: 'schoolname',
        title: '大学名字'
    }, {
        field: 'score',
        title: '录取分数'
    }, {
        field: 'batch',
        title: '批次'
    }]
});

var barChart;
var pinChart;
var treeChart;

function recommend(){
	let data = {
			score: $('#score').val(),
            province: $('#province').val(),
            type: $('#type').val()
    };

	var recommend_data;

    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/URsystem/recommend',
        async: false,
        data: data,
        success: function(result) {
        	recommend_data = result;
        	if(recommend_data.status == 101){
        		alert("非法输入！请检查输入是否包含数字以外的内容，或者以0开头");
        	}
        	if(recommend_data.status == 202){
        		alert("这边发现分数超过总分了呢！");
        	}
        	if(recommend_data.status == 200){
        		// status正常

        		// bar data 变量
        		var bar_xAxis_data = new Array();
        		var bar_data = new Array();

        		// pin data 变量
        		var school_set = new Array();
        		var school_cnt = new Array();

        		// tree data 变量
        		var tree_data = new Array();

        		// table data 变量
        		var tabledata = new Array();

        		// 提取scoreList
        		score_list = recommend_data.scoreList;


        		for(var i = 0; i < score_list.length; i++){
        			// 遍历scoreList，将相关的表格数据push进入tabledata中
        			var inf_list = score_list[i].infList;
        			var score = score_list[i].score
        			var rank = score_list[i].ranking
        			for(var j = 0; j < inf_list.length; j++){
        				elem = {};
        				elem.id = rank;
        				elem.score = score;
        				elem.major = inf_list[j].majorName;
        				elem.schoolname = inf_list[j].schoolName;
        				elem.batch = inf_list[j].batch;
        				tabledata.push(elem);

        				// pin
        				// 构建school_set和school_cnt
        				var find = -1;
        				for(var index = 0; index < school_set.length; index++){
        					// 找elem.schoolname在school_set中对应的下标(同时也会是school_cnt中对应的下标)
        					if(school_set[index] == elem.schoolname){
        						find = index;
        						break;

        					}
        				}
        				if(find != -1){
        					// 找到的情况下，schoo_cnt[find] + 1
        					school_cnt[find] = school_cnt[find] + 1;
        				}else{
        					// 找不到的情况下，在school_set中新增一个school，并且设置cnt量为1
        					school_cnt.push(1);
        					school_set.push(elem.schoolname);
        				}

        				// tree
        				// 构建tree_data
        				var find = -1;
        				for(var index = 0; index < tree_data.length; index++){
        					// 找elem.schoolname在tree_data中对应的下标
        					if(tree_data[index].name == elem.schoolname){
        						find = index;
        						break;
        					}
        				}
        				if(find != -1){
        					// 找到的情况下，在tree_data[find].children中新建一个child
        					new_elem = {};
        					new_elem.name = elem.major;
        					new_elem.value = elem.score;
        					tree_data[index].children.push(new_elem)
        				}else{
        					// 找不到的情况下，在tree_data中新增一个学校(name, children)，并在其children中新建一个child
        					new_school_elem = {};
        					new_school_elem.name = elem.schoolname;
        					new_school_elem.children = new Array();

        					new_elem = {};
        					new_elem.name = elem.major;
        					new_elem.value = elem.score;
        					new_school_elem.children.push(new_elem);

        					tree_data.push(new_school_elem);
        				}
        			}
        		}
        		$('#table').bootstrapTable('load', tabledata);


        		// bar
        		for(var i = score_list.length - 1; i >= 0; i--){
        			// 获取柱状图的数据，包括x轴(分数)和y轴(专业数)
        			bar_xAxis_data.push(score_list[i].score);
        			bar_data.push(score_list[i].infList.length)
        		}
        		barChart = echarts.init(document.getElementById('bar'));
        		// 构建option
        		option = {
        		    legend: {
        		        data: ['录取专业数']
        		    },
        		    tooltip: {},
        		    xAxis: {
        		        data: bar_xAxis_data,
        		        splitLine: {
        		            show: false
        		        }
        		    },
        		    yAxis: {
        		    },
        		    series: [{
        		        name: '专业数',
        		        type: 'bar',
        		        data: bar_data,
        		        animationDelay: function (idx) {
        		            return idx * 10;
        		        }
        		    }],
        		    animationEasing: 'elasticOut',
        		    animationDelayUpdate: function (idx) {
        		        return idx * 5;
        		    }
        		};

        		barChart.setOption(option);

        		// pin
        		var pin_data = new Array();
        		for(var i = 0; i < school_set.length; i++){
        			// 获取饼图数据，包括学校名和专业数
        			elem = {};
        			elem.name = school_set[i];
        			elem.value = school_cnt[i];
        			pin_data.push(elem);
        		}
        		// 构建option
        		pinChart = echarts.init(document.getElementById('pin'));
        		option = {
        			    backgroundColor: '#ffffff',
        			    tooltip: {
        			        trigger: 'item',
        			        formatter: '{a} <br/>{b} : {c} ({d}%)'
        			    },

        			    visualMap: {
        			        show: false,
        			        min: 80,
        			        max: 600,
        			        inRange: {
        			            colorLightness: [0.8, 1]
        			        }
        			    },
        			    series: [
        			        {
        			            name: '专业数',
        			            type: 'pie',
        			            radius: '55%',
        			            center: ['50%', '50%'],
        			            data: pin_data.sort(function (a, b) { return a.value - b.value; }),
        			            roseType: 'radius',
        			            label: {
        			                color: 'rgba(0, 0, 0, 0.3)'
        			            },
        			            labelLine: {
        			                lineStyle: {
        			                    color: 'rgba(0, 0, 0, 0.3)'
        			                },
        			                smooth: 0.2,
        			                length: 10,
        			                length2: 20
        			            },
        			            itemStyle: {
        			                color: '#ff0000' ,
        			                shadowBlur: 10,
        			                shadowColor: 'rgba(0, 0, 0, 0.5)'
        			            },

        			            animationType: 'scale',
        			            animationEasing: 'elasticOut',
        			            animationDelay: function (idx) {
        			                return Math.random() * 200;
        			            }
        			        }
        			    ]
        			};
        		pinChart.setOption(option);

        		// tree
        		treeChart = echarts.init(document.getElementById('tree'));
        		// 构建option
        		option = {
        		        tooltip: {
        		            trigger: 'item',
        		            triggerOn: 'mousemove'
        		        },
        		        series: [
        		            {
        		                type: 'tree',
        		                // 根为"录取专业", children为tree_data
        		                data: [{"name":"录取专业", "children": tree_data}],

        		                top: '1%',
        		                left: '7%',
        		                bottom: '1%',
        		                right: '20%',

        		                symbolSize: 7,

        		                label: {
        		                    position: 'left',
        		                    verticalAlign: 'middle',
        		                    align: 'right',
        		                    fontSize: 9
        		                },

        		                leaves: {
        		                    label: {
        		                        position: 'right',
        		                        verticalAlign: 'middle',
        		                        align: 'left'
        		                    }
        		                },

        		                expandAndCollapse: true,
        		                animationDuration: 550,
        		                animationDurationUpdate: 750
        		            }
        		        ]
        		};

        		treeChart.setOption(option);

        	}
        }
    })

}

window.addEventListener("resize",function(){
	barChart.resize();
	pinChart.resize();
	treeChart.resize();
});
