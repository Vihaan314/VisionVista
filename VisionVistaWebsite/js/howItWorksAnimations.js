gsap.registerPlugin(ScrollTrigger, TextPlugin);

//To prevent the animations from undoing when scrolling up again
gsap.utils.toArray('.content-area section').forEach(section => {
    gsap.from(section, {
        scrollTrigger: {
            trigger: section,
            start: 'top 75%',
            toggleActions: 'play pause resume none',
        },
        opacity: 0,
        y: 50,
        duration: 1
    });
});

/* Animations */

//PAINT BRUSH EFFECT
function paintBrushReveal() {
    const brush = document.querySelector('.brush');
    const header = document.querySelector('.brush-text h2');
    const bulletPoints = document.querySelectorAll('.brush-text ul li');

    //Get the width of the header and longest bullet point for the brush distance
    const canvas = document.createElement('canvas');
    const context = canvas.getContext('2d');
    const fontHeader = getComputedStyle(header).font;
    context.font = fontHeader;

    const headerWidth = context.measureText(header.textContent).width;
    const maxBulletWidth = Math.max(...Array.from(bulletPoints).map(item => {
        context.font = getComputedStyle(item).font;
        return context.measureText(item.textContent).width;
    }));

    const tl = gsap.timeline({
        scrollTrigger: {
            trigger: '#features',
            start: 'top 75%',
            toggleActions: 'play none none none' 
        }
    });
    
    //First brush in the header using the distances computed earlier
    tl.set(header, { visibility: 'visible' }) 
        .fromTo(brush, 
        { x: '-10%', y: header.offsetTop - 20 }, 
        { x: `${headerWidth + 20}px`, duration: 1, ease: 'power2.out', onUpdate: function() {
            const progress = this.progress();
            //The linear wipe effect to make it seem like it is being brushed in
            header.style.clipPath = `inset(0 ${100 - progress * 100}% 0 0)`;
        }}
    );

    tl.set(bulletPoints, { visibility: 'visible' }, '-=0.5')
        .fromTo(brush, 
        { x: '-10%', y: bulletPoints[0].offsetTop }, 
        { x: `${maxBulletWidth + 30}px`, duration: 2, ease: 'power2.out', onUpdate: function() {
            const progress = this.progress();
            bulletPoints.forEach(bullet => {
                bullet.style.clipPath = `inset(0 ${100 - progress * 100}% 0 0)`;
            });
        }},
        '-=0.5'
    );

    tl.to(brush, 
        { x: `${maxBulletWidth+20}px`, opacity: 0, duration: 1 }
    );
}

paintBrushReveal();


//NOTABLE LIBRARIES ANIMATION
function librariesAnimation() {
    const tl = gsap.timeline({
        scrollTrigger: {
            trigger: '#libraries',
            start: 'top 75%',
            toggleActions: 'play none none none',
        }
    });

    tl.from('#libraries h2', {
        scale: 0.5,
        opacity: 0,
        duration: 0.9
    }).from('#libraries ul li', {
        stagger: 0.2,
        x: -100,
        opacity: 0,
        duration: 0.8
    });
}

librariesAnimation();

//VISUALIZATION SECTION ANIMATION
function visualizationReveal() {
    const tl = gsap.timeline({
        scrollTrigger: {
            trigger: '#visualization',
            start: 'top 75%',
            toggleActions: 'play none none none',
        }
    });
    tl.from('#visualization h2', {
        x: -200,
        opacity: 0,
        duration: 1
    }).from('#visualization p:first-of-type', { 
        x: 200,
        opacity: 0,
        duration: 0.4
    }).from('#visualization li', {
        x: -200,
        opacity: 0,
        duration: 0.5,
        stagger: 0.2 
    }).from('#visualization p.last', { 
        x: 200,
        opacity: 0,
        duration: 0.5
    }).from('#visualization .interactive-demo', {
        scale: 0.8,
        opacity: 0,
        duration: 0.4
    });
}

visualizationReveal();