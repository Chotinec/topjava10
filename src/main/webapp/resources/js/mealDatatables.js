var ajaxUrl = "ajax/profile/meals/";
var datatableApi;


function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: updateTableByData
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {

    $('#startDate').datetimepicker({
        format:'Y-m-d',
        lang: 'ru',
        formatDate: 'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                maxDate:jQuery('#startDate').val()?jQuery('#date_timepicker_end').val():false
            })
        },
        timepicker:false
    });
    $('#endDate').datetimepicker({
        format:'Y-m-d',
        lang: 'ru',
        formatDate: 'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                minDate:jQuery('#startDate').val()?jQuery('#endDate').val():false
            })
        },
        timepicker:false
    });

    $('#startDate').datetimepicker({
        format:'Y-m-d',
        lang: 'ru',
        formatDate: 'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                maxDate:jQuery('#startDate').val()?jQuery('#endDate').val():false
            })
        },
        timepicker:false
    });
    $('#endDate').datetimepicker({
        format:'Y-m-d',
        lang: 'ru',
        formatDate: 'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                minDate:jQuery('#startDate').val()?jQuery('#endDate').val():false
            })
        },
        timepicker:false
    });


    $('#startTime').datetimepicker({
        format:'H:i',
        lang: 'ru',
        onShow:function( ct ){
            this.setOptions({
                maxDate:jQuery('#startTime').val()?jQuery('#endTime').val():false
            })
        },
        datepicker:false
    });
    $('#endTime').datetimepicker({
        format:'H:i',
        lang: 'ru',
        onShow:function( ct ){
            this.setOptions({
                minDate:jQuery('#startTime').val()?jQuery('#endTime').val():false
            })
        },
        datepicker:false
    });

    jQuery('#dateTime').datetimepicker({
        format:'Y-m-d\\TH:i',
        lang:'ru'
    });

    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (data, type, row) {
                    if (type === 'display') {
                        return '<span>' + data.replace('T', ' ') + '</span>';
                        //return "ura1";
                    }
                    return data;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (!data.exceed) {
                $(row).addClass("exceeded");
            } else {
                $(row).addClass("normal");
            }
        },
        "initComplete": makeEditable
    });
    //makeEditable();
});
