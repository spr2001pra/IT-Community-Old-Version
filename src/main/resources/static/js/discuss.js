$(function(){
    $("#topBtn").click(setTop);
    $("#wonderfulBtn").click(setWonderful);
    $("#deleteBtn").click(setDelete);
});

function like(btn, entityType, entityId, entityUserId, postId) {
    $.post(
        CONTEXT_PATH + "/like",
        {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId,"postId":postId},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                $(btn).children("i").text(data.likeCount);
                $(btn).children("b").text(data.likeStatus==1?'已赞':"赞");
            } else {
                alert(data.msg);
            }
        }
    );
}

// 置顶
function setTop() {
    $.post(
        CONTEXT_PATH + "/discuss/top",
        {"id":$("#postId").val(),"type":$("#postType").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                // $("#topBtn").removeClass("btn-danger").addClass("btn-secondary");
                $("#hintBody").text(data.msg);
            } else {
                alert(data.msg);
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

// 加精
function setWonderful() {
    $.post(
        CONTEXT_PATH + "/discuss/wonderful",
        {"id":$("#postId").val(), "status":$("#postStatus").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                // $("#wonderfulBtn").attr("disabled", "disabled");
                $("#hintBody").text(data.msg);
            } else {
                alert(data.msg);
            }
            $("#hintModal").modal("show");
            setTimeout(function (){
                if(data.code == 0){
                    window.location.reload();
                }
            }, 500);

        }
    );
}

// 删除
function setDelete() {
    $.post(
        CONTEXT_PATH + "/discuss/delete",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                location.href = CONTEXT_PATH + "/index";
            } else {
                alert(data.msg);
            }
        }
    );
}