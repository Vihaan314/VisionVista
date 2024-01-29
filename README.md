# VisionVista
Image editor program made with java.
Uses Java Swing for the UI and BufferedImage for 
image manipulation.

# Features:
<b>File</b>
<ul>
  <li>Open - opens local image</li>
  <li>Open URL - opens image given URL</li>
  <li>Create blank image (beta)</li>
  <li>Save - save edited image to directory of choice.</li>
  <li>Save with text - saves edited image as well as a log of all edits and file information.</li>
</ul>

<b>Apply</b>
<ul>
<li> Random - applies random effect to image.</li>
<li> Effect sequence - applies sequence of effects to the current image</li>
</ul>

<b>Image</b>
<ul>
<li> Save effect sequence - saves current sequence of effects independent from the image</li>
<li> Save project - saves the entire project of the sequence of images with the effects</li>
<li> Load project - loads a project containing the sequence of images and effects</li>
</ul>

<b>Edit</b>
<ul>
<li> Undo - Undos the most recent effect applied.</li>
<li> Redo - Redos the most recent effect applied.</li>
<li> Reset - Reverts the image to its original state.</li>
<li> Timeline - Displays a timeline of every effect applied with labels, and the ability navigate the timeline.</li>
</ul>

<details open>
  <summary><b>Supported effects</b></summary>

  <details style="margin-left: 20px;">
    <summary>Standard effects</summary>
    <ul>
      <li>Contrast</li>
      <li>Brightness</li>
      <li>Blur</li>
      <li>Gaussian Blur</li>
      <li>Saturation</li>
      <li>Vibrance</li>
      <li>Sharpen</li>
      <li>Hue</li>
    </ul>
  </details>

  <details style="margin-left: 20px;">
    <summary>Filters</summary>
    <ul>
      <li>Temperature</li>
      <li>Sepia</li>
      <li>Glow</li>
      <li>Vignette</li>
      <li>Pixelate</li>
      <li>Grayscale</li>
      <li>Negative</li>
      <li>Posterize</li>
      <li>Cross process</li>
      <li>Lomography</li>
      <li>Solarize</li>
      <li>Split tone</li>
      <li>Heat map</li>
      <li>Infrared</li>
      <li>Pencil sketch</li>
      <li>Tilt shift (beta)</li>
    </ul>
  </details>

  <details style="margin-left: 20px;">
    <summary>Transformations</summary>
    <ul>
      <li>Resize</li>
      <li>Flip vertical</li>
      <li>Flip horizontal</li>
    </ul>
  </details>
  
  <p>Effects are expanding</p>

</details>
