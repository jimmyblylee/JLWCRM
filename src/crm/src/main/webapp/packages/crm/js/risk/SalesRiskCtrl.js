/**
 * Created by Jimmybly Lee on 2017/6/8.
 */
function SalesRiskCtrl($scope, $http, $q) {
    $scope.year = 2016;
    $scope.initSalesChart = function(risk) {
        var i, data = [];
        for (i = 0; i < 12; i++) {
            data.push({
                "date" : $scope.year + (i < 9 ? "0" : "") + (i + 1) + "-01",
                "month" : (i + 1) + "月",
                "cost": risk["costSum"][i],
                "recPay": risk["recPaySum"][i],
                "pact" : risk["pactSum"][i],
                "clew" : risk["clewSum"][i]
            });
        }
        var chart = AmCharts.makeChart("salesChart", {
            "type": "serial",
            "theme": "light",

            "fontFamily": 'Open Sans',
            "color":    '#888888',

            "legend": {
                "equalWidths": false,
                "useGraphSettings": true,
                "valueAlign": "left",
                "valueWidth": 120
            },
            "dataProvider": data,
            "valueAxes": [{
                "id": "costAxis",
                "axisAlpha": 0,
                "gridAlpha": 0,
                "position": "left",
                "title": "销售支出"
            }, {
                "id": "pactAxis",
                "axisAlpha": 0,
                "gridAlpha": 0,
                "labelsEnabled": false,
                "position": "right"
            }, {
                "id": "clewAxis",
                "axisAlpha": 0,
                "gridAlpha": 0,
                "labelsEnabled": false,
                "position": "right"
            },{
                "id": "recPayAxis",
                "axisAlpha": 0,
                "gridAlpha": 0,
                "inside": true,
                "position": "right",
                "title": "回款"
            }],
            "graphs": [{
                "alphaField": "alpha",
                "balloonText": "销售支出: [[value]] 元",
                "dashLengthField": "dashLength",
                "fillAlphas": 0.7,
                "legendPeriodValueText": "总共: [[value.sum]] 元",
                "legendValueText": "[[value]] 元",
                "title": "销售支出",
                "type": "column",
                "valueField": "cost",
                "valueAxis": "costAxis"
            }, {
                "balloonText": "合同额:[[value]] 元",
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "useLineColorForBulletBorder": true,
                "bulletColor": "#FFFFFF",
                "bulletSizeField": "pact",
                "dashLengthField": "dashLength",
                "labelPosition": "right",
                "legendValueText": "[[value]] 元",
                "title": "合同额",
                "fillAlphas": 0,
                "valueField": "pact",
                "valueAxis": "pactAxis"
            }, {
                "balloonText": "商机预算:[[value]] 元",
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "useLineColorForBulletBorder": true,
                "bulletColor": "#FFFFFF",
                "bulletSizeField": "clew",
                "dashLengthField": "dashLength",
                "labelPosition": "right",
                "legendValueText": "[[value]] 元",
                "title": "商机",
                "fillAlphas": 0,
                "valueField": "clew",
                "valueAxis": "clewAxis"
            }, {
                "bullet": "square",
                "bulletBorderAlpha": 1,
                "bulletBorderThickness": 1,
                "dashLengthField": "dashLength",
                "legendValueText": "[[value]] 元",
                "title": "回款",
                "fillAlphas": 0,
                "valueField": "recPay",
                "valueAxis": "recPayAxis"
            }],
            "chartCursor": {
                "cursorAlpha": 0.1,
                "cursorColor": "#000000",
                "fullWidth": true,
                "valueBalloonsEnabled": false,
                "zoomable": false
            },
            "categoryField": "month"
        });

        $('#salesChart').closest('.portlet').find('.fullscreen').click(function() {
            chart.invalidateSize();
        });
    };

    $scope.initSalesChart2 = function(risk) {
        var i, data = [];
        console.log(risk);
        for (i = 0; i < 12; i++) {
            data.push({
                "date" : $scope.year + "-" + (i < 9 ? "0" : "") + (i + 1),
                "cost": risk["costSum"][i],
                "recPay": risk["recPaySum"][i],

                "pactSize": (i+1)%4 == 1 ? 20 : 10,
                "pact": risk["pactSum"][i],

                "clew" : risk["clewSum"][i]
            });
        }

        var chart = AmCharts.makeChart("salesChart", {
            "type": "serial",
            "theme": "light",

            "fontFamily": 'Open Sans',
            "color":    '#888888',

            "legend": {
                "equalWidths": false,
                "useGraphSettings": true,
                "valueAlign": "left",
                "valueWidth": 120
            },
            "dataProvider": data,
            "valueAxes": [{
                "id": "costAxis",
                "axisAlpha": 0,
                "gridAlpha": 0,
                "position": "left",
                "title": "销售支出"
            }, {
                "id": "pactAxis",
                "axisAlpha": 0,
                "gridAlpha": 0,
                "labelsEnabled": false,
                "position": "right"
            }, {
                "id": "clewAxis",
                "axisAlpha": 0,
                "gridAlpha": 0,
                "labelsEnabled": false,
                "position": "right"
            }, {
                "id": "recPayAxis",
                "axisAlpha": 0,
                "gridAlpha": 0,
                "inside": true,
                "position": "right",
                "title": "回款"
            }],
            "graphs": [{
                "alphaField": "alpha",
                "balloonText": "[[value]] 元",
                "dashLengthField": "dashLength",
                "fillAlphas": 0.7,
                "legendPeriodValueText": "总共销售支出: [[value.sum]] 元",
                "legendValueText": "[[value]] 元",
                "title": "销售支出",
                "type": "column",
                "valueField": "cost",
                "valueAxis": "costAxis"
            }, {
                "balloonText": "签订合同:[[value]]",
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "useLineColorForBulletBorder": true,
                "bulletColor": "#FFFFFF",
                "bulletSizeField": "pactSize",
                "dashLengthField": "dashLength",
                "labelPosition": "right",
                "legendValueText": "[[value]] 元",
                "title": "签订合同",
                "fillAlphas": 0,
                "valueField": "pact",
                "valueAxis": "pactAxis"
            }, {
                "balloonText": "商机:[[value]]",
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "useLineColorForBulletBorder": true,
                "bulletColor": "#FFFFFF",
                "bulletSizeField": "pactSize",
                "dashLengthField": "dashLength",
                "labelPosition": "right",
                "legendValueText": "[[value]] 元",
                "title": "商机",
                "fillAlphas": 0,
                "valueField": "clew",
                "valueAxis": "clewAxis"
            }, {
                "bullet": "square",
                "bulletBorderAlpha": 1,
                "bulletBorderThickness": 1,
                "dashLengthField": "dashLength",
                "legendValueText": "[[value]] 元",
                "legendPeriodValueText": "总共回款: [[value.sum]] 元",
                "title": "回款",
                "fillAlphas": 0,
                "valueField": "recPay",
                "valueAxis": "recPayAxis"
            }],
            "chartCursor": {
                "categoryBalloonDateFormat": "MM",
                "cursorAlpha": 0.1,
                "cursorColor": "#000000",
                "fullWidth": true,
                "valueBalloonsEnabled": false,
                "zoomable": false
            },
            "dataDateFormat": "YYYY-MM",
            "categoryField": "date",
            "categoryAxis": {
                "dateFormats": [{
                    "period": "MM",
                    "format": "MM"
                }, {
                    "period": "YYYY",
                    "format": "YYYY"
                }],
                "parseDates": false,
                "autoGridCount": false,
                "axisColor": "#555555",
                "gridAlpha": 0.1,
                "gridColor": "#FFFFFF",
                "gridCount": 100
            }
        });

        $('#salesChart').closest('.portlet').find('.fullscreen').click(function() {
            chart.invalidateSize();
        });
    };

    $scope.initChart = function() {
        sendPost($http, {
            "controller" : "RiskController",
            "method" : "querySalesRisk",
            "year" : $scope.year
        }, $q).then(function(success){
            var risk = StrParesJSON(success).result;
            $scope.initSalesChart2(risk);
        });
    }
}
