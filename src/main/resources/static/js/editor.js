document.addEventListener("DOMContentLoaded", function() {
    const { Editor } = window.toastui;

    const editor = new Editor({
        el: document.querySelector('#editor'),
        height: '100%',
        initialEditType: 'markdown',
        previewStyle: 'vertical',
    });

    let $mainText = $('#mainText');
    const $publishedBtn = $('#publishedBtn');

    editor.getMarkdown();

    document.querySelector('#mainText').insertAdjacentHTML('afterbegin' ,editor.getHTML());
    $publishedBtn.click(function() {
        $mainText.val(editor.getHTML());
    })

    editor.setHTML($mainText[0].innerText);

})