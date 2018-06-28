//================================================================//

                      //DEEP IN THE WOODS//

                  //by Joseph Rodrigues Marsh//

// My intention was to create a semi-realistic misty pine forest //

      //Inspired by Live Coding Session - Lior Ben-Gai//

//===============================================================//

import processing.pdf.*;
PGraphicsPDF pdf;

//global variables
float phase = 0.0;
int counter = 0;

void setup()
{
    size(3240, 1080);
    pdf = (PGraphicsPDF)beginRecord(PDF, "deepInTheWoods.pdf");
    beginRecord(pdf);
}

void draw()
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

void branch(float length, float angle)
{
  pushMatrix();
  pushStyle();

  //randomise the initial length to create more natural growth
  float pos = random(length);
  phase = (sin(counter * 0.01) + 1) / 2 * width;
  float phaseMapped = map(phase, 0, 1000, 120, 180);
  float colorLength = map(length, 0, height, 30, 100);
  float branchWidth = map(length, 10, 350, 1, 70);

  //match the stroke colour depending on length of the branch
  stroke(colorLength);

  //rotate using sine and randomise slightly again.
  rotate(angle + random(-0.02, 0.02));
  strokeWeight(sqrt(branchWidth));
  line(0, 0, pos, 0);
  translate(pos, 0);

  if ( length > 5.0 )
  {
    float newAngle = PI*0.5 * phaseMapped * 0.001;
    branch(length*0.7, -newAngle);
    branch(length*0.7, newAngle);
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

void leaf(float leaflength)
{
  ellipse(0, 0, leaflength, 1);
}

void forest()
{
  pushMatrix();
  translate(random(width), height);
  float wind = (sin(counter * 0.02) + 1) / 2 * width;
  float windMapped = map(wind, 0, 1000, .24, .26);
  rotate(-PI * windMapped);

  //again, add randomness to make it more natural, regardless of user input
  branch(random(10, 250), -PI*0.25);
  popMatrix();



}

void grain()

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

void keyPressed()
//record screenshot (only necessary for when we draw to screen)
{
  if (keyCode == ENTER)
  {
    saveFrame("deepinthewoods_####.jpg");
  }
}
