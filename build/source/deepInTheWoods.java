import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.pdf.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class deepInTheWoods extends PApplet {

//================================================================//

                      //DEEP IN THE WOODS//

                  //by Joseph Rodrigues Marsh//

// My intention was to create a semi-realistic misty pine forest //

      //Inspired by Live Coding Session - Lior Ben-Gai//

//===============================================================//


PGraphicsPDF pdf;

//global variables
float phase = 0.0f;
int counter = 0;

public void setup()
{
    
    pdf = (PGraphicsPDF)beginRecord(PDF, "deepInTheWoods.pdf");
    beginRecord(pdf);
}

public void draw()
{
  //Draw one tree per frame, getting deeper and deeper and deeper until counter = 1000.
  while (counter < 1000)
  {
    counter++;
    forest();

    //Draw the mist
    if (counter % 10 == 0)
    {
      pushStyle();
      fill(200, 5);
      noStroke();
      rect(0, 0, width, height);
      popStyle();
    }
  }

  //draw some grain for texture
  grain();

  //end record and exit
  endRecord();
  exit();

}

public void branch(float length, float angle)
{
  pushMatrix();
  pushStyle();

  //randomise the initial length to create more natural growth
  float pos = random(length);
  phase = (sin(counter * 0.01f) + 1) / 2 * width;
  float phaseMapped = map(phase, 0, 1000, 120, 180);
  float colorLength = map(length, 0, height, 30, 100);
  float branchWidth = map(length, 10, 350, 1, 70);

  //match the stroke colour depending on length of the branch
  stroke(colorLength);

  //rotate using sine and randomise slightly again.
  rotate(angle + random(-0.02f, 0.02f));
  strokeWeight(sqrt(branchWidth));
  line(0, 0, pos, 0);
  translate(pos, 0);

  if ( length > 5.0f )
  {
    float newAngle = PI*0.5f * phaseMapped * 0.001f;
    branch(length*0.7f, -newAngle);
    branch(length*0.7f, newAngle);
  }
  else
  {
    //draw a leaf
    pushStyle();
    strokeWeight(sqrt(length / 10));
    //randomise leaf size for variation
    leaf(random(5, 10));
    popStyle();
  }
  popMatrix();
  popStyle();
}

public void leaf(float leaflength)
{
  ellipse(0, 0, leaflength, 1);
}

public void forest()
{
  pushMatrix();
  translate(random(width), height);
  float wind = (sin(counter * 0.02f) + 1) / 2 * width;
  float windMapped = map(wind, 0, 1000, .24f, .26f);
  rotate(-PI * windMapped);

  //again, add randomness to make it more natural, regardless of user input
  branch(random(10, 250), -PI*0.25f);
  popMatrix();



}

public void grain()

//semi random grain function for texture
{
  for(int x = -10; x < width; x += random(5, 10))
  {
    for (int y = -10; y < height; y += random(5, 10))
    {
      pushStyle();
      stroke(0, random(10, 30));
      point(x + random(10), y + random(9));
      popStyle();
    }
  }
}

public void keyPressed()
//record screenshot (only necessary for when we draw to screen)
{
  if (keyCode == ENTER)
  {
    saveFrame("deepinthewoods_####.jpg");
  }
}
  public void settings() {  size(3240, 1080); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "deepInTheWoods" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
