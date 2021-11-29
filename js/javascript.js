//专辑特效
$(function(){

	$(".enlarge").hover(function(){
		$(this).children(".zhuanji_pic").css({
			transform: 'scale(1.1)',
			filter: 'brightness(0.7)'
		}).next().css({
			transform: 'scale(1)',
			opacity: '1'
		});
	},function(){
		$(this).children(".zhuanji_pic").css({
			transform: 'scale(1)',
			filter: 'brightness(1)'
		}).next().css({
			transform: 'scale(0)',
			opacity: '0'
		});
	})
})
//栏目切换
$(function(){
	$("#recommond").click(function(){
		$(this).css("color","#31c27c").siblings().css("color","black")
		$("#myCarousel1").show()
		$("#myCarousel2").hide()
	})
	$("#gufeng").click(function(){
		$(this).css("color","#31c27c").siblings().css("color","black")
		$("#myCarousel1").hide()
		$("#myCarousel2").show()
	})
})
// var lanmu = 1;//代表当前栏目
// function zhuanji_qiehuan(n){
// 	if (n==1) {
// 		lanmu = 1;
// 		document.getElementById("recommond").style.color = "#31c27c";
// 		document.getElementById("gufeng").style.color = "black";
// 		document.getElementById("album_1").style.display = "block";
// 		document.getElementById("album_2").style.display = "none";
// 		document.getElementById("album_3").style.display = "none";
// 		document.getElementById("album_4").style.display = "none";
// 	}
// 	else if (n==2) {
// 		lanmu = 2;
// 		document.getElementById("gufeng").style.color = "#31c27c";
// 		document.getElementById("recommond").style.color = "black";
// 		document.getElementById("album_1").style.display = "none";
// 		document.getElementById("album_2").style.display = "none";
// 		document.getElementById("album_3").style.display = "block";
// 		document.getElementById("album_4").style.display = "none";
// 	}	
// }

// //点击切换lanmu
// var block1 = 1;
// var block2 = 1;
// function qiehuan(){
// 	if(lanmu==1){
// 		if (block1%2==0) {
// 				document.getElementById("album_1").style.display = "block";
// 				document.getElementById("album_2").style.display = "none";
// 				document.getElementById("c1").style.opacity = "1";
// 				document.getElementById("c2").style.opacity = "0.5";
// 			}
// 			else{
// 				document.getElementById("album_1").style.display = "none";
// 				document.getElementById("album_2").style.display = "block";
// 				document.getElementById("c1").style.opacity = "0.5";
// 				document.getElementById("c2").style.opacity = "1";
// 			}
// 			block1++;
// 	}
// 	else if(lanmu ==2){
// 		if (block2%2==0) {
// 			document.getElementById("album_3").style.display = "block";
// 			document.getElementById("album_4").style.display = "none";
// 			document.getElementById("c1").style.opacity = "1";
// 			document.getElementById("c2").style.opacity = "0.5";
// 		}
// 		else{
// 			document.getElementById("album_3").style.display = "none";
// 			document.getElementById("album_4").style.display = "block";
// 			document.getElementById("c1").style.opacity = "0.5";
// 			document.getElementById("c2").style.opacity = "1";
// 		}
// 		block2++;
// 	}
// }	

//左右切换键的特效
// function L_and_R_in(){
// 		document.getElementById("L_s").style.transform = "translateX(0px)";
// 		document.getElementById("L_s").style.opacity = "0.6";
// 		document.getElementById("R_s").style.transform = "translateX(0px)";
// 		document.getElementById("R_s").style.opacity = "0.6";
// }

// function L_and_R_out(){
// 		document.getElementById("L_s").style.transform = "translateX(-95px)";
// 		document.getElementById("L_s").style.opacity = "0";
// 		document.getElementById("R_s").style.transform = "translateX(95px)";
// 		document.getElementById("R_s").style.opacity = "0";
// }
$(function(){
	$("#body1").hover(function(){
		$(".L_s,.R_s").css({
			transform: 'translateX(0px)',
			opacity: '0.6'
		});
	},function(){
		$(".L_s").css({
			transform: 'translateX(-95px)',
			opacity: '0'
		});
		$(".R_s").css({
			transform: 'translateX(95px)',
			opacity: '0'
		})
	})
})
//关闭登陆页面
function guanbi(){
	document.getElementById("log").style.display = "none";
	document.getElementById("body").style.overflow = 'visible';
}
//打开登陆页面
function denglu(){
	var obj = document.getElementById("right1").text;
	if (obj == "登陆") {
		document.getElementById("log").style.display = "block";
		document.getElementById("body").style.overflow = 'hidden';
	}
	else{
		document.getElementById("right1").text = "登陆";
		document.getElementById('right1').style = "text-decoration:none";
	}
}

//登陆界面表单验证
var shuju = document.getElementsByClassName("text_in");
function log_yanzheng(flag){
		if(flag==1){
			if (shuju[0].value.length >11) {
			alert("位数错误！");
			shuju[0].value = "";
			}	
		}
		else if(flag==2)
		{
			if (shuju[1].value.length < 6) {
			alert("密码至少六位！");
			shuju[1].value = "";
			}	
		}
}
function denglu_btn(){
	var num1 = shuju[0].value.length;
	var num2 = shuju[1].value.length;
	if(num1!=0&&num2!=0)
	{
		document.getElementById("log").style.display = "none";
		document.getElementById("mima").value = "";
		document.getElementById("zhanghao").value = "";
		document.getElementById('right1').text="注销";
		document.getElementById('right1').style = "text-decoration:underline"
	}
	else{
		alert("请输入正确信息！");
	}
}


