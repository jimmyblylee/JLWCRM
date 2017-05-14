//多语言支持

//设置要传递的语言
//name:变量名
//value:传递的值
function setCookie(name,value){
    var Days=7;
    var exp=new Date();
    exp.setTime(exp.getTime()+Days*24*60*60*1000);
    document.cookie=name+"="+escape(value)+";expires="+exp.toGMTString();
}

//获取传递的语言
//name：变量名
function getCookie(name){
    var arr=document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if(arr!=null){
        return unescape(arr[2]);
    }
    return null;
};

//设置页面使用的语言
//$translate调用名,controller传递的
//languageJson：json文件名字（如loginCN,loginEN传login）
//lang：使用的语言
function setLanguage($translate,lang,languageJson){
    switch(lang){
        case "English":
            $translate.use("en\/"+languageJson+"EN");
            break;
        case "simplifiedChinese":
            $translate.use("cn\/"+languageJson+"CN");
            break;
        default:
            $translate.use("cn\/"+languageJson+"CN");
    }
}
