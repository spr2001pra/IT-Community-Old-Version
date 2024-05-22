$(function(){
    $("#dataUV").click(send_data_uv);
    $("#dataDau").click(send_data_dau);
    $("#uvResult").click(send_data_uv_list);
    $("#dauResult").click(send_data_dau_list);
});

function send_data_uv() {
    var start = $("#dataUV_start").val();
    var end = $("#dataUV_end").val();

    $.post(
        CONTEXT_PATH + "/data/uv",
        {"start": start, "end": end},
        function (data) {
            data = $.parseJSON(data);
            if(data.code == 0){
                $("#hintBody").text("统计成功！");
                $("#dataUV_start").text(data.uvStartDate);
                $("#dataUV_end").text(data.uvEndDate);
                $("#uvResult").text(data.uvResult);
            } else {
                $("#hintBody").text(data.msg);
            }
            $("#hintModal").modal("show");
        }
    );
}

function send_data_dau(){
    var start = $("#dataDau_start").val();
    var end = $("#dataDau_end").val();

    $.post(
        CONTEXT_PATH + "/data/dau",
        {"start": start, "end": end},
        function (data){
            data = $.parseJSON(data);
            if(data.code == 0){
                $("#hintBody").text("统计成功！");
                $("#dataDau_start").text(data.dauStartDate);
                $("#dataDau_end").text(data.dauEndDate);
                $("#dauResult").text(data.dauResult);
            }else {
                $("#hintBody").text(data.msg);
            }
            $("#hintModal").modal("show");
        }
    );
}

function send_data_uv_list(){
    var start = $("#dataUV_start").val();
    var end = $("#dataUV_end").val();

    $.post(
        CONTEXT_PATH + "/data/uv/list",
        {"start":start, "end":end},
        function (data){
            data = $.parseJSON(data);
            if(data.code==0){
                // 清空表格内容
                $('#visitorTableBody').empty();
                // 遍历服务器返回的数据并生成表格行
                data.unionVisitor.forEach(function(visitor) {
                    var row = '<tr><td>' + visitor.ip + '</td><td>' + visitor.date + '</td></tr>';
                    $('#visitorTableBody').append(row);
                });
                // 显示模态框
                $('#dataUV_list').modal('show');
            }else {
                $("#hintBody").text(data.msg);
                $("#hintModal").modal("show");
            }
        }
    );
}

function send_data_dau_list(){
    var start = $("#dataDau_start").val();
    var end = $("#dataDau_end").val();

    $.post(
        CONTEXT_PATH + "/data/dau/list",
        {"start":start, "end":end},
        function (data){
            data = $.parseJSON(data);
            if(data.code == 0){
                $("#DailyActiveUserTableBody").empty();
                data.dailyActiveUser.forEach(function (user){
                    var row = "<tr><td>" + user.username + "</td><td>" + user.date + "</td></tr>";
                    $("#DailyActiveUserTableBody").append(row);
                });
                $("#dataDAU_list").modal("show");
            }else {
                $("#hintBody").text(data.msg);
                $("#hintModal").modal("show");
            }
        }
    );
}