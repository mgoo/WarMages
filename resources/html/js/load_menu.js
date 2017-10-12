function addSaveFiles() {

}

function prettifyFilename(filename) {
    filename = filename.replace(new RegExp('_', 'g'), ' ');
    return filename.split('.')[0];
}

function uglifyFilename(filename) {
    filename = filename.replace(new RegExp(' ', 'g'), '_');
    return filename + '.sav';
}
