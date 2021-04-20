const mealAjaxUrl = "profile/meals/";
const filterUrl = "filter"

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    filterMealsUrl: filterUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
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
                    "desc"
                ]
            ]
        })
    );
});

function filter() {
    const form = $("#filteredForm");
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + ctx.filterMealsUrl,
        data: form.serialize()
    }).done(function () {
        $.get(ctx.ajaxUrl + ctx.filterMealsUrl, function (data) {
            ctx.datatableApi.clear().rows.add(data).draw();
        });
        successNoty("Filtered");
    });
}