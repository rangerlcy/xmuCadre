/**
 * 一些通用的js函数
 * 
 * 以下是本文件中所找拥有的js方法
 * trim(str);
 * ltrim(str);
 * rtrim(str);
 * isEmpty(str);
 * isBlank(str);
 * isNotEmpty(str);
 * isNotBlank(str);
 * tips(str,time);
 * isExcelFile(fileName) 是否是Excel文件
 * String.prototype.startWith(str)
 * String.prototype.endWith(str)
 * String.prototype.deleteWhiteSpaces()//删除空白符
 * getTopWinow() 得到最顶层窗口
 */

function trim(str) { //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
function ltrim(str) { //删除左边的空格
	return str.replace(/(^\s*)/g, "");
}
function rtrim(str) { //删除右边的空格
	return str.replace(/(\s*$)/g, "");
}

function isEmpty(str) {
	return str == null || str == "";
}

function isBlank(str) {
	return str == null || trim(str) == "";
}

function isNotEmpty(str) {
	return !isEmpty(str);
}

function isNotBlank(str){
	return !isBlank(str);
}

function tips(str,time) {
	if(time) {
		art.dialog.tips('<span style="color:green;">'+str+'</span>',time);
	} else {
		art.dialog.tips('<span style="color:green;">'+str+'</span>');
	}
}

//
//给字符串对象添加一个startsWith()方法
//
String.prototype.startWith = function (substring) {
	var reg = new RegExp("^" + substring);
	return reg.test(this);
};

//
//给字符串对象添加一个endsWith()方法
//
String.prototype.endWith = function (substring) {
	var reg = new RegExp(substring + "$");
	return reg.test(this);
};

//
//删除所有空白字符
//
String.prototype.deleteWhiteSpaces = function () {
	var extraSpace = /[\s\n\r]+/g;
	return this.replace(extraSpace, "");
};

function isExcelFile(fileName) {
    if(fileName == null 
            || fileName == "" 
            || !(fileName.endWith("xls") || fileName.endWith("xlsx"))){
       return false;
    }
    return true;
}

function isImgFile(fileName) {
	if(fileName == null || fileName == ""){
		return false;
	}
	if(fileName.endWith("bmp") || fileName.endWith("BMP")
		|| fileName.endWith("gif") || fileName.endWith("GIF")
		|| fileName.endWith("jpeg") || fileName.endWith("JPEG")
		|| fileName.endWith("jpg") || fileName.endWith("JPG")
		|| fileName.endWith("tiff") || fileName.endWith("TIFF")
		|| fileName.endWith("png") || fileName.endWith("PNG")){
		return true;
	}
	return false;
}
//校验手机
function isMobile(dat){
	return /^[1][3,5,8][0-9]{9}$/.test(dat);
}
//校验邮箱
function isEmail(dat){
	return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(dat);
}

/** 
 * 在页面中任何嵌套层次的窗口中获取顶层窗口 
 * @return 当前页面的顶层窗口对象 
 */  
function getTopWinow(){  
    var p = window;  
    while(p != p.parent){  
        p = p.parent;  
    }  
    return p;  
}  


