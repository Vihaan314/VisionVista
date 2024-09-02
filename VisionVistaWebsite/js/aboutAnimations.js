gsap.registerPlugin(ScrollTrigger);

//About Vision Vista section
gsap.from('#about', {
    scrollTrigger: {
        trigger: '#about',
        start: 'top 75%',
        toggleActions: 'play none none none'
    },
    opacity: 0,
    x: -100,
    duration: 1
});

//All controls section
gsap.from('#controls', {
    scrollTrigger: {
        trigger: '#controls',
        start: 'top 75%',
        toggleActions: 'play none none none'
    },
    opacity: 0,
    y: 100,
    duration: 1
});

//Supported effects section
gsap.from('#effects', {
    scrollTrigger: {
        trigger: '#effects',
        start: 'top 75%',
        toggleActions: 'play none none none'
    },
    opacity: 0,
    y: 90,
    duration: 1
});

//Procedures for modification section
gsap.from('#modification', {
    scrollTrigger: {
        trigger: '#modification',
        start: 'top 75%',
        toggleActions: 'play none none none'
    },
    opacity: 0,
    scale: 0.8,
    duration: 1
});

//How the project started + final notes section
gsap.from('#remarks', {
    scrollTrigger: {
        trigger: '#remarks',
        start: 'top 75%',
        toggleActions: 'play none none none'
    },
    opacity: 0,
    y: -80,
    duration: 1
});