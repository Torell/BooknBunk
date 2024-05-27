document.addEventListener('DOMContentLoaded', function() {
    let sectionTitles = document.querySelectorAll('.section-title');
    sectionTitles.forEach(function(title) {
        title.addEventListener('click', function() {
            let sectionLinks = this.nextElementSibling;
            if (sectionLinks.style.display === 'none' || sectionLinks.style.display === '') {
                sectionLinks.style.display = 'block';
            } else {
                sectionLinks.style.display = 'none';
            }
        });
    });
});
