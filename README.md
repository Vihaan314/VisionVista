# VisionVista
Vision Vista is an innovative image editor that is designed to enhance your images through advanced effects, easy-to-use tools, and AI-powered styling.
<br>
It is made in Java, uses Java Swing for the UI and Java's BufferedImage class for image manipulation.

# To download Vision Vista and learn a lot about how it was made, visit the Vision Vista website!
# https://vihaan314.github.io/VisionVistaWebsite/

# Notable Features:
<b>File</b>
<ul>
  <li>Open - opens local image</li>
  <li>Open URL - opens image given URL</li>
  <li>Open Project - open serialized Vision Vista project</li>
  <li>Generate Image - generates image based on user prompt and opens in editor</li>
  <li>Save</li>
  <li>Save with text - saves edited image as well as a log of all edits and file information.</li>
</ul>

<b>Apply</b>
<ul>
<li> Random effect - applies random effect to image.</li>
<li> Random effect (multiple) - applies multiple number random effects specified by the user</li>
</ul>

<b>Image</b>
<ul>
<li> Save effect sequence - saves current sequence of effects independent from the image</li>
<li> Load effect sequence - load effect sequence</li>
<li> Save project - saves the entire project of the sequence of images with the effects</li>
<li> Load project - loads a project containing the sequence of images and effects</li>
</ul>

<b>Edit</b>
<ul>
<li> Undo - effects applied in an undo state will override the existing ones</li>
<li> Redo</li>
<li> Reset - reverts the image to its original state and removes all history</li>
<li> Timeline - displays a timeline of every effect applied with labels, and the ability to navigate the effects.</li>
</ul>

<b>Generate</b>
<ul>
<li> Style - Generates a sequence of effects to meet a desired style the user enters through a prompt.</li>
<li> Image - Generates image based on prompt and opens in editor.</li>
</ul>

<details open>
  <summary><b>Supported effects</b></summary>

  <details style="margin-left: 20px;">
    <summary>Standard effects</summary>
    <ul>
      <li>Contrast</li>
      <li>Brightness</li>
      <li>Saturation</li>
      <li>Vibrance</li>
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
      <li>Grayscale</li>
      <li>Negative</li>
      <li>Cross process</li>
      <li>Solarize</li>
      <li>Split tone</li>
      <li>Heat map</li>
      <li>Infrared</li>
	  <li>Halftone</li>
	  <li>Duotone (beta)</li>
    </ul>
  </details>
  
  <details style="margin-left: 20px;">
    <summary>Artistic</summary>
    <ul>
	  <li>Watercolor (beta)</li>
	  <li>Oil Painting</li>
	  <li>Cyberpunk</li>
	  <li>Pencil sketch</li>
	  <li>Posterize</li>
	  <li>Lomography</li>
	  <li>Color Splash</li>
    </ul>
  </details>
  
  <details style="margin-left: 20px;">
    <summary>Distort</summary>
    <ul>
	  <li>Pixel sort</li>
      <li>Pixelate</li>
	  <li>Chromatic Aberration</li>
	  <li>Anaglyph 3D</li>
    </ul>
  </details>
  
  <details style="margin-left: 20px;">
    <summary>Blur</summary>
    <ul>
      <li>Box Blur</li>
      <li>Gaussian Blur</li>
	  <li>Bokeh Blur</li>
	  <li>Tilt shift</li>
    </ul>
  </details>

  <details style="margin-left: 20px;">
    <summary>Transformations</summary>
    <ul>
      <li>Resize</li>
      <li>Flip vertical</li>
      <li>Flip horizontal</li>
	  <li>Rotate (beta)</li>
    </ul>
  </details>
  
   <details style="margin-left: 20px;">
    <summary>Enhance</summary>
    <ul>
      <li>Sharpen</li>
      <li>Edge Enhance</li>
    </ul>
  </details>
  
  <p>Effects are expanding</p>

</details>

# AI-Stylize feature:
This feature relies on an API request to one of OpenAI's APIs, and thus, you must provide an OpenAI API (<a target="_blank" href="https://platform.openai.com/docs/quickstart">how to create it</a>) key in the UI (when clicking on the stylize) in order for the AI-stylize to work.

# Procedures
<b> Adding an effect </b><br>
1. Create class in respective effect sub/class and inherit that superclass.<br>
2. Implement all superclass methods and the getRandomInstance for the random effect feature.<br>
3. Add to EffectType enum with classifications and parameters for UI type and bounds.<br>
4. (For AI Stylize) If the effect requires a parameter, add a @JsonProperty("value") directly before the parameter in the constructor of the effect. <br>
5. (For AI Stylize) In the Effect superclass, add this line (with every other effect) with respect to your effect: @JsonSubTypes.Type(value = {theneweffect}.class, name = "neweffectname"). <br>
<br>

<b> Adding an effect category </b><br>
1. Create new package in the effects directory.<br>
2. Create abstract super class for that category that extends Effect to make the effect identifiable under that category and have the opportunity to define any methods specific to that category.<br>
3. Just follow the procedure for <b> adding an effect </b> to create effects in that category.<br>
4. Make sure that these effects extend the category specific abstract superclass.<br>

