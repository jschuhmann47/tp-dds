function getSectores(recurso) {
    $.ajax({
        type: "GET",
        url: "/trabajador/vinculacion/nuevo",
        data: { id: $(recurso)},
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function () {

        },
        failure: function () {
            alert("Failed!");
        }
    });
}