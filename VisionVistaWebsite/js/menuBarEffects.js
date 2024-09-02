function randomEffect() {
    const effects = ['solarize', 'bokeh', 'posterize', 'vibrance', 'rotate', 'pencilsketch'];
    const randomEffect = effects[Math.floor(Math.random() * effects.length)];
    applyEffect(randomEffect);
}

function resetEffects() {
    document.querySelector('.editor-mockup').style.filter = 'none';
    document.querySelector('.editor-mockup').style.transform = 'none';
}

function crazyMode() {
    const editorMockup = document.querySelector('.editor-mockup');
    editorMockup.style.animation = 'crazyMode 2s infinite';

    //Stop animation after 3 iterations (6 seconds)
    setTimeout(() => {
        editorMockup.style.animation = 'none';
        resetEffects(); 
    }, 6000);
}

function applyEffect(effect) {
    const editorMockup = document.querySelector('.editor-mockup');

    switch (effect) {
        case 'solarize':
            editorMockup.style.filter = 'invert(100%) hue-rotate(180deg)';
            break;
        case 'bokeh':
            editorMockup.style.filter = 'blur(5px)';
            break;
        case 'posterize':
            editorMockup.style.filter = 'contrast(200%) brightness(150%)';
            break;
        case 'vibrance':
            editorMockup.style.filter = 'saturate(2)';
            break;
        case 'rotate':
            editorMockup.style.transform = 'rotate(20deg)';
            break;
        case 'pencilsketch':
            editorMockup.style.filter = 'grayscale(100%) invert(100%) blur(1px) brightness(80%)';
            break;
        default:
            resetEffects();
            break;
    }
}