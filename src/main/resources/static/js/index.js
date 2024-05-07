$(function(){
	$("#publishBtn").click(publish); // 页面加载完后，就获取这个单击按钮，给它定义了相应的单击事件
});

function publish() {
	$("#publishModal").modal("hide"); // 隐藏发布框

    // 发送AJAX请求之前,将CSRF令牌设置到请求的消息头中.
//    var token = $("meta[name='_csrf']").attr("content");
//    var header = $("meta[name='_csrf_header']").attr("content");
//    $(document).ajaxSend(function(e, xhr, options){
//        xhr.setRequestHeader(header, token);
//    });

	// 获取标题和内容
	var title = $("#recipient-name").val(); //.val方法取值
	var content = $("#message-text").val();
	// 发送异步请求(POST)
	$.post(
	    CONTEXT_PATH + "/discuss/add",
	    {"title":title,"content":content},
	    function(data) {
	        data = $.parseJSON(data);
	        // 在提示框中显示返回消息
	        $("#hintBody").text(data.msg); //text方法修改内容，改它里面的文本
	        // 显示提示框
            $("#hintModal").modal("show");
            // 2秒后,自动隐藏提示框
            setTimeout(function(){
                $("#hintModal").modal("hide");
				// 刷新页面
				// 如果失败就不刷新了，但是不管成功还是失败都会有提示信息
                if(data.code == 0) {
                    window.location.reload(); //js刷新页面很简单
                }
            }, 2000);
	    }
	);

}