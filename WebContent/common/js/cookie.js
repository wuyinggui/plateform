function getCookie(name){ 
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
		return unescape(arr[2]); 
	else 
		return null; 
} 

function setCookie(name,value) 
{ 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + 30*60); 
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
} 

function cookieLogin(uname,uauth){
	 var result = '';
	 var pathName=window.document.location.pathname;
	 var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	 $.ajax({
		type : 'POST',
		url : '../../..'+projectName+'/LBS/loginAU',
		async : false,
		data : {
			'entity.username' : uname,
			'entity.password' : uauth,
			'entity.isAuthen' : "1"
		},
		success : function(data){
			var json = eval("("+data+")");
			if(json.success){
				result =  data.username;
			}
		}
	});
	 return result;
}