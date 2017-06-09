/**
 * Created by Jimmybly Lee on 2017/6/8.
 */
function MgmtRiskCtrl($scope, $http, $q) {
    $scope.year = 2017;
    $scope.initRecPayAndCostChart = function(risk) {

        var i, categories = [];
        for (i = 0; i < 12; i++) {
            categories.push((i + 1) + "月");
        }
        // BAR CHART
        $('#costAndRecPayChart').highcharts({
            chart: {
                type: 'bar',
                style: {
                    fontFamily: 'Open Sans'
                }
            },
            title: {
                text: '收入支出对比表'
            },
            subtitle: {
                text: '对比每个月份的收入与支出'
            },
            xAxis: [{
                categories: categories,
                reversed: false,
                labels: {
                    step: 1
                }
            }, { // mirror axis on right side
                opposite: true,
                reversed: false,
                categories: categories,
                linkedTo: 0,
                labels: {
                    step: 1
                }
            }],
            yAxis: {
                title: {
                    text: null
                },
                labels: {
                    formatter: function () {
                        return Math.abs(this.value);
                    }
                }
            },

            plotOptions: {
                series: {
                    stacking: 'normal'
                }
            },

            tooltip: {
                formatter: function () {
                    return '<b>' + this.point.category + ', ' + this.series.name + '</b><br/>' +
                        '金额: ' + Highcharts.numberFormat(Math.abs(this.point.y), 0);
                }
            },

            series: [{
                name: '收入',
                data: risk["recPaySum"]
            }, {
                name: '支出',
                data: risk["costSum"]
            }]
        });
    };
    $scope.initCostPossessionChart = function(risk) {
        var i, baseLine = [], costPossessionSum = [];
        for (i = 0; i < 12; i++) {
            baseLine.push([i + 1, 0]);
            costPossessionSum.push([i + 1, risk["costPossessionSum"][i]])
        }

        var options = {
            axisLabels: {
                show: true
            },
            xaxes: [{
                axisLabel: '月份',
                tickColor: "#eee"
            }],
            yaxes: [{
                position: 'left',
                axisLabel: '占用资金',
                tickColor: "#eee"
            }, {
                position: 'right',
                axisLabel: 'bleem'
            }],

            grid: {
                borderColor: "#eee",
                borderWidth: 1
            }
        };

        $.plot($("#costPossessionChart"),
            [{
                label: "基准线",
                data: baseLine,
                lines: {
                    lineWidth: 1
                },
                shadowSize: 0
            }, {
                label: "占用资金",
                data: costPossessionSum,
                lines: {
                    lineWidth: 1
                },
                shadowSize: 0
            }],
            options
        );
    };
    $scope.initChart = function() {
        sendPost($http, {
            "controller" : "RiskController",
            "method" : "queryMgmtRisk",
            "year" : $scope.year
        }, $q).then(function(success){
            var risk = StrParesJSON(success).result;
            var costSumWithMinus = [];
            $.each(risk.costSum, function(idx, data){
                costSumWithMinus.push(0 - data);
            });
            risk.costSum = costSumWithMinus;
            $scope.initRecPayAndCostChart(risk);
            $scope.initCostPossessionChart(risk)
        });
    }
}
