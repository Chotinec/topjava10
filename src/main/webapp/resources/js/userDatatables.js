var ajaxUrl = 'ajax/admin/users/';
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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

function enableUser(id) {
    var enabled = $('#enabled_'+id).is(":checked");
    if (enabled)
        $('#' + id).removeClass('disabled').addClass('enabled');
    else
        $('#' + id).removeClass('enabled').addClass('disabled');

    $.ajax({
        type: "POST",
        url: ajaxUrl + id,
        data: "enabled="+enabled,
        success: function () {
            successNoty(enabled? 'enabled' : 'disabled');
        }
    });
}

function init() {
    $(":checkbox").each(function () {
       if ($(this).is(":checked")) {
           $(this).parent().parent().removeClass('disabled').addClass('enabled');
       } else {
           $(this).parent().parent().removeClass('enabled').addClass('disabled');
       }
    });
}