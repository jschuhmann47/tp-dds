// agregar registro
$("#addRow").click(function () {
    var html = '';
    html += '<div id="inputFormRow">';
    html += '<div class="input-group mb-3">';
    html += '<input type="text" name="title1[]" class="form-control m-input" placeholder="Ingrese Direccion Origen" autocomplete="off">';
    html += '<input type="text" name="title12[]" class="form-control m-input" placeholder="Ingrese Direccion Fin" autocomplete="off">';
    html += '<div class="input-group-append">';
    html += '<button id="removeRow" type="button" class="btn btn-danger ">Borrar Tramo</button>';
    html += '</div>';
    html += '</div>';
    
    $('#newRow').append(html);
    });
    
    // borrar registro
    $(document).on('click', '#removeRow', function () {
    $(this).closest('#inputFormRow').remove();
    });