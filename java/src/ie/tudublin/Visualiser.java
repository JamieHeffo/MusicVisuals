package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import java.util.concurrent.TimeUnit;


public class Visualiser extends PApplet
{
    Minim minim;
    AudioPlayer audioPlayer;
    AudioInput ai;
    AudioBuffer ab;
    int mode = 0;
    float smoothedAmplitude = 0;
    float average = 0;
    float canvasX = width / 2;
    float canvasY = height / 2;
    float sum = 0;
    float[] lerpedBuffer;
    float halfH = height / 2;
    int option = 0;
    String song = "FairyFountain.wav";
    

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

    //Variables for tree functions
    float len = 100;
    float newLength = -len * 0.6f;
    float rotation = (PI / 4);
    float thickness = 20;

    //variables for sky functions
    int sunX = 1800;    
    float cloudX = 1000;
    float cloudY = 200;
    float cloudSize = 100;
    float r = 0;
    float g;// = 189;
    float b;// = 254;


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

    //Draw method for the tree
    public void drawTree(){

        translate (200, height);
        strokeWeight(thickness + 10);
        stroke(114, 92, 66);
        branch(450);
    }

    //Method to recursively draw a fractal tree
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
        //Once the branches reach a certain size make them green and draw leaves
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

    //Method for user to pause song with spacebar
    public void keyPressed() {
		if (key >= '0' && key <= '9') {
			mode = key - '0';
		}
		if (keyCode == ' ') {
            if (audioPlayer.isPlaying()) {
                audioPlayer.pause();
            } 
            else {
                audioPlayer.play();
            }
        }
	}

    public void settings()
    {
        //fullScreen(P3D, 1);
        size(2048, 1440, P3D);
    }

    public void setup()
    {
        //create object of the song and play
        minim = new Minim(this);
        audioPlayer = minim.loadFile(song, 2048);
        audioPlayer.play();
        ab = audioPlayer.mix;
        lerpedBuffer = new float[width];
    }

    public void draw()
    {
        //draw the sky
        drawSky();

        //draw the audio visualiser
        drawBuffer();

        //draw background for grass
        fill(66, 104, 4);
        noStroke();
        rect(0, 1080, 2800, 1000);

        //draw grass at different points accross the screen
        for(int i = 1100; i < 1500; i += 45){
            drawGrass(i);//i parameter passed to function for location to start drawing
        }
        
        //draw the tree
        drawTree();      

    }       
    
}
