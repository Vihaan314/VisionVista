let pixelIndex = 0; //To keep track of the latest pixel that the desired effect is being applied to
let imageData;
let data;
let ctx; //The canvas
let canvas;
let isPaused = false;
let speed = 45; 

function startPixelManipulation() {
    //Get all inputs
    const img = document.getElementById('sampleImage');
    canvas = document.createElement('canvas');
    ctx = canvas.getContext('2d');
    const redFunc = document.getElementById('red-function').value;
    const greenFunc = document.getElementById('green-function').value;
    const blueFunc = document.getElementById('blue-function').value;

    //Scale down image to reduce pixels
    const scale = 0.5;
    canvas.width = img.width * scale;
    canvas.height = img.height * scale;
    ctx.drawImage(img, 0, 0, canvas.width, canvas.height);

    imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
    //Store the original image data for replay animation
    originalImageData = ctx.getImageData(0, 0, canvas.width, canvas.height); 
    data = imageData.data;

    pixelIndex = 0;
    isPaused = false;
    applyPixelManipulation(redFunc, greenFunc, blueFunc);
}

function applyPixelManipulation(redFunc, greenFunc, blueFunc) {
    //If completed or paused, don't return anything
    if (pixelIndex >= data.length || isPaused) return;

    //Compute amount of pixels to be applied in one iteration to avoid the animation being too slow, scaled by the speed parameter
    const batchSize = Math.max(1, Math.floor(1000 / speed)); 

    for (let i = 0; i < batchSize && pixelIndex < data.length; i++) {
        const red = data[pixelIndex];
        const green = data[pixelIndex + 1];
        const blue = data[pixelIndex + 2];
        
        //Evaluate the user inputs for the red, green, and blue functions
        data[pixelIndex] = eval(redFunc.replace(/red/g, red));
        data[pixelIndex + 1] = eval(greenFunc.replace(/green/g, green));
        data[pixelIndex + 2] = eval(blueFunc.replace(/blue/g, blue));

        pixelIndex += 4;
    }

    ctx.putImageData(imageData, 0, 0);
    document.getElementById('sampleImage').src = canvas.toDataURL();

    requestAnimationFrame(() => applyPixelManipulation(redFunc, greenFunc, blueFunc));
}

function pauseManipulation() {
    isPaused = true;
}

function resumeManipulation() {
    isPaused = false;
    const redFunc = document.getElementById('red-function').value;
    const greenFunc = document.getElementById('green-function').value;
    const blueFunc = document.getElementById('blue-function').value;
    applyPixelManipulation(redFunc, greenFunc, blueFunc);
}

function restartManipulation() {
    //Put the original image in the canvas again
    ctx.putImageData(originalImageData, 0, 0);

    imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
    data = imageData.data;

    pixelIndex = 0;
    isPaused = false;

    const redFunc = document.getElementById('red-function').value;
    const greenFunc = document.getElementById('green-function').value;
    const blueFunc = document.getElementById('blue-function').value;

    //Run the animation again
    applyPixelManipulation(redFunc, greenFunc, blueFunc);
}

function resetManipulation() {
    if (originalImageData) {
        //Put the original image data on the canvas
        ctx.putImageData(originalImageData, 0, 0); 
        document.getElementById('sampleImage').src = canvas.toDataURL();
    }
}

function adjustSpeed(newSpeed) {
    //The speed actually will represent the delay between the iterations, thus, the inversion of speed parameter
    speed = 90-parseInt(newSpeed);
}
