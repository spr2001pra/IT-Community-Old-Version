$(function(){
    $("#dataUV").click(send_data_uv);
    $("#dataDau").click(send_data_dau);
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