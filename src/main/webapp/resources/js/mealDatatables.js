var ajaxUrl = 'ajax/meals/';
var datatableApi;

$(function () {
    datatableApi = $('#mealdatatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
    init();
});

function clearFilter() {
    $("#filter").trigger( 'reset' );
}

function updateTable() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: {
            startDate: $("#startDate").val(),
            startTime: $("#startTime").val(),
            endDate: $("#endDate").val(),
            endTime: $("#endTime").val()
        },
        success: function (data) {
            datatableApi.clear();
            $.each(data, function (key, item) {
                datatableApi.row.add(item);
            });
            datatableApi.draw();
        }
    });
    init();
}

function init() {

}