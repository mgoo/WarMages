/**
 * Called from java
 * @param filename the raw file name of the save file
 */
function addSaveFiles(filename) {
    var filenamePretty =  prettifyFilename(filename);
    var fileRadio = $('<div class="radio"><label><input type="radio" value="'+filename+'" name="file"><span>'+filenamePretty+'</span></label></div>');
    fileRadio.appendTo($('#files'));
}

$('#load-button').on('click', function(event) {
    var filename = $('input[name="file"]:checked').val();
    controller.loadBtn(filename);
});

function prettifyFilename(filename) {
    filename = filename.replace(new RegExp('_', 'g'), ' ');
    return filename.split('.')[0];
}

function uglifyFilename(filename) {
    filename = filename.replace(new RegExp(' ', 'g'), '_');
    return filename + '.sav';
}

$('#save-button').on('click', function(event) {
    var filename = $('#save-file').val();
    filename = uglifyFilename(filename);
    controller.save(filename);
});


