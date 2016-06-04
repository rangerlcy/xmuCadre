	var nameFormat = /^[ a-zA-Z\u4E00-\u9FA5]{2,40}$/;
	var emailFormat = /^[-._A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
	var phoneFormat = /^([0-9]{4}( |-)?)?[0-9]{7}$/;
	var passwordFormat = /^_\-[0-9a-zA-Z]{6,12}$/;
	var mobileFormat = /^(13|14|15|18)[0-9]{9}$/;
	var personalIDFormat = /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|x|X)$/;
	var digitalWordFormat = /^[0-9A-Za-z]+$/;
	
	var tipInfo;
	//提示信息
	var tipmsg = {
		ERROR : {
			"PHONE_LENGTH" : "长度7-12位",
			"NAME_LENGTH":"长度2-40位",
			"MOBIEL_LENGTH" : "长度11位",
			"PERSONALID_LENGTH" : "长度18位",
			"FORMAT" : "数据无效",
			"DIGITALWORD_LENGTH" : "长度不足",
		},
		NOTNULL : "不能为空",
		PASS : "<font color=green>通过</font>"
	}

var countFail = 0;
$(function(){
	bindPhone();
	bindName();
	bindPersonalId();
	bindMobile();
	bindClearMessage();
	bindEmail();
	bindSelect();
	bindDigitalWord();
	$("input[datatype=submit]").click(function(){
		$(this).focus();
		if(countFail != 0){ return false; }
		bindRequried();
		if(countFail == 0){
			return true;
		}
		else
			return false;
	});
});
function bindDigitalWord(){
	$("input[datatype=digitalWord]").blur(function(){
		if( $(this).val().length != 0 || $(this).hasClass("required") ){			
			countFail ++;
			if($(this).val().length < 6){
				tipInfo = tipmsg.ERROR["DIGITALWORD_LENGTH"]	;
			}
			else if( !$(this).val().trim().match(digitalWordFormat) ){
				tipInfo = tipmsg.ERROR["FORMAT"];
			}
			else {
				tipInfo = tipmsg.PASS;
				countFail --;
				if(countFail < 0 ) countFail = 0;
			}
			var span = "<span>"+tipInfo+"</span>";
			$(this).after(span);
		}
	});
}
function bindDemo(){
/*
	$("input[datatype=_attr]").blur(function(){
		if( $(this).val().length != 0 || $(this).hasClass("required") ){			
			countFail ++;
			if($(this).val().length != _length){
				tipInfo = tipmsg.ERROR["_LENGTH"]	;
			}
			else if( !$(this).val().trim().match(_Format) ){
				tipInfo = tipmsg.ERROR["FORMAT"];
			}
			else {
				tipInfo = tipmsg.PASS;
				countFail --;
				if(countFail < 0 ) countFail = 0;
			}
			var span = "<span>"+tipInfo+"</span>";
			$(this).after(span);
		}
	});
*/
}
function bindSelect(){
	$("select").blur(function(){
		if($(this).hasClass("required")){				
			if($(this).find("option:selected").text() == '请选择'){
				tipInfo = tipmsg.NOTNULL;		
				countFail ++;
			}
			else{
				tipInfo = tipmsg.PASS;
				countFail --;
			}
			var span = "<span>"+tipInfo+"</span>";
			$(this).after(span);
		}
	});
}
function bindRequried(){
	$(".required").each(function(){
		if($(this).is("input")){
			if($(this).val().length == 0){
				countFail ++;
				$(this).next().remove();
				var span = "<span>不能为空</span>";
				$(this).after(span);
			}	
		}
		else if($(this).is("select")){
			if($(this).find("option:selected").text() == '请选择'){
				countFail ++;
				$(this).parent().find("span").remove();
				var span = "<span>不能为空</span>";
				$(this).after(span);
			}
		}	
	});
}
function bindEmail(){
	$("input[datatype=email]").blur(function(){
		if( $(this).val().length != 0 || $(this).hasClass("required") ){			
			countFail ++;
			if( $(this).val().length < 7 || !$(this).val().trim().match(emailFormat) ){
				tipInfo = tipmsg.ERROR["FORMAT"];
			}
			else {
				tipInfo = tipmsg.PASS;
				countFail --;
				if(countFail < 0 ) countFail = 0;
			}
			var span = "<span>"+tipInfo+"</span>";
			$(this).after(span);
		}
	});	
}
function bindMobile(){
	$("input[datatype=mobile]").blur(function(){
		if( $(this).val().length != 0  || $(this).hasClass("required")){			
			countFail ++;
			if($(this).val().length != 11){
				tipInfo = tipmsg.ERROR["MOBIEL_LENGTH"]	;
			}
			else if( !$(this).val().trim().match(mobileFormat) ){
				tipInfo = tipmsg.ERROR["FORMAT"];
			}
			else {
				tipInfo = tipmsg.PASS;
				countFail --;
				if(countFail < 0 ) countFail = 0;
			}
			var span = "<span>"+tipInfo+"</span>";
			$(this).after(span);
		}
	});
}
function bindClearMessage(){
	$("input[datatype!=submit]").focus(function(){
		if($(this).next().text() != "通过") {
			countFail --;
			if(countFail < 0 ) countFail = 0;
		}
		if($(this).nextAll().length == 1){
			$(this).next().remove();
		}
	});
	$("select").focus(function(){
		if($(this).hasClass("required")){
			$(this).parent().find("span").remove();
		}
	});
}
function bindPersonalId(){
	$("input[datatype=personalID]").blur(function(){
		if( $(this).val().length != 0 || $(this).hasClass("required")){
			countFail ++;
			if($(this).val().length != 18){
				tipInfo = tipmsg.ERROR["PERSONALID_LENGTH"]	;
			}
			else if( !$(this).val().trim().match(personalIDFormat) ){
				tipInfo = tipmsg.ERROR["FORMAT"];
			}
			else {
				var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "}; 
				var code = $(this).val().split('');
				var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
				//校验位
				var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
				var sum = 0;
				for (var i = 0; i < 17; i++)
				{
					sum += code[i] * factor[i];
				}
				if(code[17]=='x') code[17]='X';
				if(parity[sum % 11] != code[17] || !city[$(this).val().substr(0,2)]) tipInfo = tipmsg.ERROR["FORMAT"];
				else{
					tipInfo = tipmsg.PASS;
					countFail --;
					if(countFail < 0 ) countFail = 0;
				}
			}
			var span = "<span>"+tipInfo+"</span>";
			$(this).after(span);
		}
	});
}
function bindName(){
	$("input[datatype=name]").blur(function(){
		if( $(this).val().length != 0  || $(this).hasClass("required")){
			countFail ++;
			if($(this).val().length < 2 || $(this).val().length > 40){
				tipInfo = tipmsg.ERROR["NAME_LENGTH"]	;
			}
			else if( !$(this).val().trim().match(nameFormat) ){
				tipInfo = tipmsg.ERROR["FORMAT"];
			}
			else {
				tipInfo = tipmsg.PASS;
				countFail --;
				if(countFail < 0 ) countFail = 0;
			}
			var span = "<span>"+tipInfo+"</span>";
			$(this).after(span);
		}
	});
}
function bindPhone(){
	$("input[datatype=phone]").blur(function(){
		if( $(this).val().length != 0  || $(this).hasClass("required")){
			countFail ++;
			if($(this).val().length < 7 || $(this).val().length > 12){
				tipInfo = tipmsg.ERROR["PHONE_LENGTH"]	;
			}
			else if( !$(this).val().trim().match(phoneFormat) ){
				tipInfo = tipmsg.ERROR["FORMAT"];
			}
			else {
				tipInfo = tipmsg.PASS;
				countFail --;
				if(countFail < 0 ) countFail = 0;
			}
			var span = "<span>"+tipInfo+"</span>";
			$(this).after(span);
		}
	});
}