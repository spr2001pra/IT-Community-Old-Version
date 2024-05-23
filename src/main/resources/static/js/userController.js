function udType(userid, type){
    $.post(
        CONTEXT_PATH + "/admin/usermanage/type",
        {"userid":userid, "type":type},
        function (data){
            data = $.parseJSON(data);
            if(data.code == 0){
                $("#hintBody").text(data.msg);
                // if(type === 1){
                //     $("#buttonRight").text("授权");
                //     $("#buttonRight").removeClass("btn-secondary").addClass("btn-info");
                // }else {
                //     $("#buttonRight").text("已授权");
                //     $("#buttonRight").removeClass("btn-info").addClass("btn-secondary");
                // }
            }else {
                $("#hintBody").text("服务器异常，请重试！");
            }
            $("#hintModal").modal("show");
            setTimeout(function (){
               if(data.code==0){
                   window.location.reload();
               }
            }, 500);
        }
    );
}

function udStatus(userid, status){
    $.post(
        CONTEXT_PATH + "/admin/usermanage/status",
        {"userid":userid, "status":status},
        function (data){
            data = $.parseJSON(data);
            if(data.code == 0){
                $("#hintBody").text(data.msg);
            }else {
                $("#hintBody").text("服务器异常，请重试！");
            }
            $("#hintModal").modal("show");
            setTimeout(function (){
                if(data.code==0){
                    window.location.reload();
                }
            }, 500);
        }
    );
}