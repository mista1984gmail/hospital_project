function generatePDF() {
    const element = document.getElementById('invoice');
    var one = document.getElementById('lastName').innerHTML;
    var two = '_invoice.pdf';
    var nameFile = one + two;

    html2pdf()
        .from(element)
        .save(nameFile);
}