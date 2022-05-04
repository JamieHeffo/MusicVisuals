# Music Visualiser Project

Name: Jamie Heffernan

Student Number: C20483462

## Instructions
- Fork this repository and use it a starter project for your assignment
- Create a new package named your student number and put all your code in this package.
- You should start by creating a subclass of ie.tudublin.Visual
- There is an example visualiser called MyVisual in the example package
- Check out the WaveForm and AudioBandsVisual for examples of how to call the Processing functions from other classes that are not subclasses of PApplet

# Description of the assignment
- This assignment is an interactive visualiser of an audio file using the Java Processing Library
- For this project I chose to create a landscape of a peaceful day in nature
- The music I chose is a Lo-Fi remix of songs from the video game series "The Legend of Zelda" a series I am very fond of
- Each element in this program is synced to music so that the move in time with the beat

# Instructions
- To change the time of day within the file, move the mouse up and down
- When the mouse is raised, the sun will rise and the sky will change to a ligher daytime colour
- When the mouse is lowered, the sun will set and the sky will change to night and stars will start show in the night sky

# How it works
- The program works by importing the Java Processing and Minim Libraries
- We use minim to pull data from the AudioStream and Processing to visualise it
#1. Grass Method
- The grass method uses nexted loops and the built in function curve() to draw a blade of grass as a Spline Curve
- Each blade takes the x coordinates (the index in the current loop) and the y coordinate (passed in the function call) as well as the starting and ending point of the curve
- The tangent to the curve at the starting point is parallel to the line between the first control point and end of the curve
- The tangent to the curve at the ending point is parallel to the line between the start point and second control point
-
#2. Sky Method
- The sky method works by first drawing a rectangle with a light blue colour
- We then draw a large yellow ellipse on this rectangle with the X coordinate being the right side of the screen and the Y coordinate being equal to the mouse Y position
- If the Y value of the sun lowers then the RGB value of the sky is changed to slowly fade from light blue, to dark blue and when the sun sets the colour will be black as it is nighttime
- Once the sun has gone to sleep we can draw stars at random points in the sky, since the canvas is updated each frame the stars will automatically sync to the music
- We also added a cloud of random ellipses with random colours to move right-to-left accross the sky
- 
#3. Recursive Fractal Tree Method
- This method uses recursion to draw a tree using fractals until the space between branches is less than 5 pixels
- The method works by drawing an initial branch to a specified length
- The top of this branch then splits in two and rotates the angle to draw the next branch by 45 degrees and stores this state using the push() function
- Two more branches are then drawn mirrored to each other and then the pop() method is called to reset the position to draw the next branch to the end of the current branch at 45 degrees
- This process continues until there are less than 4 pixels between each branch as well as once there are less than 50 pixels between branches the branches are drawn in a green colour rather than brown and gradually get thinner
- Once all the branches have been generated we draw ellipses on these branches of varying sizes and green colours to give a rotating effect to the leaves
-
#4. Audio Buffer Method 
- The value of the audio buffer is calculated in a loop where it takes the current value of the audio buffer and lerps it to the index of the lerped buffer
- In a loop of the audio buffer size a various connected lines and circles are drawn each frame at the index to the current position of the lerped audio buffer multiplied by the audio buffer multiplied by the sensitivity


# What I am most proud of in the assignment
- The element I am most proud of in this assignment is the grass which flows in time with the music. From first hearing the assignment description I knew I wanted to do something different and create a landscape of a peaceful day and having grass flow in
the wind was the first item which came to mind. Originally I wanted do draw triangles in three different states, facing left, neutral and facing right and was going to iterate through these three states using loops and arrays but the idea of drawing curves came to
me soon after which I thought was a much better solution.
- Implementing them was difficult as I had an idea of what I wanted the grass to look like and drawing curves with the processing environment was more difficult that I had originally anticipated with there being many mathematical functions involved but once I plotted this out 
on paper it was simply a matter of doing the calculations and tweaking the shapes until I was satisfied with the final product.
- I'm also quite proud of the sun tracking the colour of the sky as I had originally been trying to map the background colour from a black, to red, to blue colour and updating this based on the mapped mouse position but a much simpler approach was to find a nice sky blue colour with 
neutral red and green attributes which could simply be lowered and mapped to a rectangle drawn above the grass.
# Markdown Tutorial

This is *emphasis*

This is a bulleted list

- Item
- Item

This is a numbered list

1. Item
1. Item

This is a [hyperlink](http://bryanduggan.org)

# Headings
## Headings
#### Headings
##### Headings

This is code:

```Java
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

So is this without specifying the language:

```
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

This is an image using a relative URL:

![An image](images/p8.png)

This is an image using an absolute URL:

![A different image](https://bryanduggandotorg.files.wordpress.com/2019/02/infinite-forms-00045.png?w=595&h=&zoom=2)

This is a youtube video:

[![YouTube](http://img.youtube.com/vi/J2kHSSFA4NU/0.jpg)](https://www.youtube.com/watch?v=J2kHSSFA4NU)

This is a table:

| Heading 1 | Heading 2 |
|-----------|-----------|
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |

