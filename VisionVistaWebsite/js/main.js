//For the morph-text bullet points reveal
document.addEventListener("DOMContentLoaded", function() {
    var ulPatterns = document.getElementById("patterns");
    var patterns = ulPatterns.getElementsByTagName("li");
    for (var i = 0; i < patterns.length; ++i) {
        patterns[i].style.setProperty('--delay', `${i * 0.15}s`); 
    }
});

//Animation for the download button
var animateButton = function(e) {
    e.preventDefault();
    e.target.classList.remove('animate');
    e.target.classList.add('animate');
    setTimeout(function(){
      e.target.classList.remove('animate');
    },700);
};

//When button clicked with event, call the animate button
document.addEventListener('click', function(e) {
    animateButton(e);
});


//For the collapsable sections
function toggleReadMore(element) {
    const moreText = element.previousElementSibling;
    const arrowIcon = element.querySelector('.arrow-icon');

    if (moreText.classList.contains('expanded')) {
        moreText.classList.remove('expanded');
        arrowIcon.style.transform = 'rotate(0deg)';
        element.querySelector('.toggle-text').textContent = 'Read More';
    } else {
        moreText.classList.add('expanded');
        arrowIcon.style.transform = 'rotate(90deg)';
        element.querySelector('.toggle-text').textContent = 'Read Less';
    }
}

function toggleSubSection(element) {
    const subSection = element.nextElementSibling;

    if (subSection.classList.contains('expanded')) {
        subSection.classList.remove('expanded');
    } else {
        subSection.classList.add('expanded');
    }
}

