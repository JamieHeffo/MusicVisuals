# Music Visualiser Project

Name: Jamie Heffernan

Student Number: C20483462

# Description of the assignment
- This assignment is an interactive visualiser of an audio file using the Java Processing Library
- For this project I chose to create a landscape of a peaceful day in nature
- The music I chose is a Lo-Fi remix of songs from the video game series "The Legend of Zelda" a series I am very fond of
- Each element in this program is synced to music so that the move in time with the beat
- I undertook this assignment by alone as I wanted to challenge myself to create a visual I could be proud to call my own creation and to demonstrate my programming knowledge in future interviews in the Tech Industry

# Instructions
- To run the file load the entire project folder into VSC and press F5 to enter the debugger
- To change the time of day within the file, move the mouse up and down
- When the mouse is raised, the sun will rise and the sky will change to a ligher daytime colour
- When the mouse is lowered, the sun will set and the sky will change to night and stars will start show in the night sky

# How it works
- The program works by importing the Java Processing and Minim Libraries
- We use minim to pull data from the AudioStream and Processing to visualise it
- The movements of the elements are linked to the audio file. The draw method updates the frame every time a change is detected in the Audio Buffer

#1. Grass Method
- The grass method uses nexted loops and the built in function curve() to draw a blade of grass as a Spline Curve
- Each blade takes the x coordinates (the index in the current loop) and the y coordinate (passed in the function call) as well as the starting and ending point of the curve
- The tangent to the curve at the starting point is parallel to the line between the first control point and end of the curve
- The tangent to the curve at the ending point is parallel to the line between the start point and second control point
```Java
    //method to draw grass which will sync with audio
    //y parameter is passed in through method call
    public void drawGrass(int x){
        
        colorMode(RGB);
        float base = x;
        
        for (int j = -100; j < 500; j+=50){
            base += j;
            for(int i = 0 ; i < ab.size(); i ++)
            {
                smooth();  
                fill(95, 195, 20);
                //draw curved line 
                curve(j, (base + 40),
                //starting position of tip + audio buffer * sensitivity
                (j + 100) + ab.get(i) * 25 ,  (base + 60),
                (j + 100), (base + 100),
                (j + 60), (base + 120));

                j += 10;

            }//end nested for
        }//end for
    }//end method
```

#2. Sky Method
- The sky method works by first drawing a rectangle with a light blue colour
- We then draw a large yellow ellipse on this rectangle with the X coordinate being the right side of the screen and the Y coordinate being equal to the mouse Y position
- If the Y value of the sun lowers then the RGB value of the sky is changed to slowly fade from light blue, to dark blue and when the sun sets the colour will be black as it is nighttime
- Once the sun has gone to sleep we can draw stars at random points in the sky, since the canvas is updated each frame the stars will automatically sync to the music
- We also added a cloud of random ellipses with random colours to move right-to-left accross the sky
```Java
public void drawSky(){

        fill(0, g, b);
        noStroke();
        rect(0,0, 2800, 1200);

        //update background colour in accordance with sun position
        g = - mouseY + 189 * 4.5f;
        b = - mouseY + 254 * 4.5f;

        //if the sun goes away, the stars come out to play
        //Since the screen updates with the beat (thanks grass)
        //Stars will be drawn randomly in time with the song
        if(g <= 0 || b <= 0){
            fill(255);
            noStroke();
            //draw 50 stars in random positions each time the frame is updated
            for(int i = 0; i < 50; i++){
                ellipse(random(width), random(height), 4, 4);
            }
        }
        
        //Draw sun
        fill(250, 253, 15);
        int sunsize = 500;
        ellipse(sunX, mouseY, sunsize, sunsize);

        //draw clouds
        ellipse(cloudX, cloudY, 100, 100);
        for(int i = 0; i < 80; i++){
            float colour = random(245, 255);
            fill(colour);
             ellipse(random(cloudX - 200, cloudX + 200), random(cloudY - 30, cloudY + 30), cloudSize, cloudSize);
             cloudX -= 0.1f;

            if(cloudX <= - 20){
                 cloudX = width;
            } 
        }
        
    }
```
#3. Recursive Fractal Tree Method
- This method uses recursion to draw a tree using fractals until the space between branches is less than 5 pixels
- The method works by drawing an initial branch to a specified length
- The top of this branch then splits in two and rotates the angle to draw the next branch by 45 degrees and stores this state using the push() function
- Two more branches are then drawn mirrored to each other and then the pop() method is called to reset the position to draw the next branch to the end of the current branch at 45 degrees
- This process continues until there are less than 4 pixels between each branch as well as once there are less than 50 pixels between branches the branches are drawn in a green colour rather than brown and gradually get thinner
- Once all the branches have been generated we draw ellipses on these branches of varying sizes and green colours to give a rotating effect to the leaves
```Java
	public void drawTree(){

        translate (200, height);
        strokeWeight(thickness + 10);
        stroke(114, 92, 66);
        branch(450);
    }

    public void branch(float length){
        //draw tree trunk
        line(0, 0, 0, -length);
        //reposition 0
        translate(0, -length);
        //branch of tree
        if(length > 50){
            if(length > 4){
                stroke(114, 92, 66);
                push();
                rotate(rotation);
                strokeWeight(thickness + 2);
                branch(length * 0.5f);
                pop();
                push();
                rotate(-rotation);
                strokeWeight(thickness + 2);
                branch(length * 0.5f);     
                pop();
            }
        }
        else{
            //Leaves of tree
            if(length > 4){
                
                stroke(45, random(85,95), 39);
                //add extra tree to branch
                //store current state and return with push() and pop()
                push();
                rotate(rotation);
                strokeWeight(thickness - 4);
                branch(length * 0.6f);
                //return to state
                pop();
                //repeat on opposite side
                push();
                rotate(-rotation);
                strokeWeight(thickness - 4);
                branch(length * 0.6f);
                //draw rotating leaves of varying colour
                circle(random(length, length + 5), 0, random(200,250));
                pop();
            }
        }
    }
```
#4. Audio Buffer Method 
- The value of the audio buffer is calculated in a loop where it takes the current value of the audio buffer and lerps it to the index of the lerped buffer
- In a loop of the audio buffer size a various connected lines and circles are drawn each frame at the index to the current position of the lerped audio buffer multiplied by the audio buffer multiplied by the sensitivity
```Java
    //method to visualise audio stream from file
    public void drawBuffer(){
        for(int i = 0 ; i < ab.size(); i ++)
        {
            sum += abs(ab.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.2f);
        }

        strokeWeight(2);
        for(int j = 0; j < ab.size(); j+=20){
            float cc = map(j, 0, ab.size(), 0, 255);
            stroke(cc, 102, 0);
            float f = lerpedBuffer[j] * halfH * 4.0f;
            line(j, halfH, ab.size(), halfH);
            line(j, halfH + f, j + 20, halfH - f);
            line(j, halfH - f, j + 20, halfH + f);
            circle(j, halfH + f, 10);
            circle(j, halfH - f, 10);
        }
    }
```


# What I am most proud of in the assignment
- The element I am most proud of in this assignment is the grass which flows in time with the music. From first hearing the assignment description I knew I wanted to do something different and create a landscape of a peaceful day and having grass flow in
the wind was the first item which came to mind. Originally I wanted do draw triangles in three different states, facing left, neutral and facing right and was going to iterate through these three states using loops and arrays but the idea of drawing curves came to
me soon after which I thought was a much better solution.
- Implementing them was difficult as I had an idea of what I wanted the grass to look like and drawing curves with the processing environment was more difficult that I had originally anticipated with there being many mathematical functions involved but once I plotted this out 
on paper it was simply a matter of doing the calculations and tweaking the shapes until I was satisfied with the final product.
- I'm also quite proud of the sun tracking the colour of the sky as I had originally been trying to map the background colour from a black, to red, to blue colour and updating this based on the mapped mouse position but a much simpler approach was to find a nice sky blue colour with 
neutral red and green attributes which could simply be lowered and mapped to a rectangle drawn above the grass.


#Video Demonstration of the Assignment Running

[![YouTube]](https://youtu.be/Abt9Nh6MSZg)

