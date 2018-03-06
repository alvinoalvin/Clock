import java.time.LocalTime;
import java.awt.*;
import java.awt.geom.*;//dunno why this wasnt imported with awt.*
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
/** A Clock that tells us the time and date at the instance the clock has been open
    @author Jeremy Hilliker @ Langara
    @author Alvin Ng
    @version 2017- 05 - 31 The Cake is a Lie
    @see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=50644&grpid=0&isprv=0&bp=0&ou=88736">a 04: Clock</a>
*/
public class Clock {

    public final static double RADIANS = 2 * Math.PI;// 2 pi 
    public final static int SCALE = 200;// border of drawing area
    public final static int CENTRE = SCALE / 2; // centre of drawing area
    private final static int TICK_LENGTH = SCALE / 10; // lengths of regular ticks

    private LocalTime time;// a time that can not be changed
   
   /**  Creates a new Clock module with the current time.
    */	
    public Clock() {
		this(LocalTime.now());
	}
   
   /**  Creates a new Clock module with a given time.
             @param aTime the time which the user wishes the clock to display
    */	
	public Clock(LocalTime aTime) {
		time = aTime;
	}

   /**  draws all parts of the clock 
           @param g an object of the Graphics 2D class which allows us to edit graphics properties and draw in the frame
    */	
	public void draw(Graphics2D g) {
      //Gradients   
		GradientPaint blackToWhite = new GradientPaint(CENTRE, CENTRE , Color.gray ,SCALE , SCALE-9, Color.black, true);
		GradientPaint whiteToBlack = new GradientPaint(CENTRE, CENTRE , Color.darkGray ,SCALE , SCALE-9, Color.lightGray, true);
      
      //rectangle frame for clock frame
		g.setPaint(blackToWhite);
      g.fill(new Rectangle2D.Double(5, 5, SCALE-10, SCALE-10));  
      
      //clock frame 
		g.setColor(Color.white);
      g.draw(new Ellipse2D.Double(9, 9, SCALE-18, SCALE-18));//white fill used for the thing white line
      g.setPaint(blackToWhite);    
		g.fill(new Ellipse2D.Double(9, 9, SCALE-18, SCALE-18));
      
      //clock ticks
      g.setPaint(Color.gray);
		drawTicks(g);
      
      //digital clock      
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MM dd");
      LocalDateTime now = LocalDateTime.now();
      //LocalTime now = LocalTime.now();
      g.setPaint(whiteToBlack);
      g.fill(new Rectangle2D.Double(60.5,130,80,20));
      g.setColor(Color.black);
      g.drawString(dtf.format(now), (float) 68.75, (float) 145);  
      //g.drawString(now.toString(), (float) 67.25, (float) 150);      
           
      
      //clock hands
      LocalTime time = LocalTime.now();
      g.setColor(Color.gray);
      Hand hands[] = getHands(time);
      for (Hand hand : hands)
         hand.draw(g);   
	}
   
   /**  draws all the ticks on the clock Longer and fatter ones for \n12, 3, 6, 9 Hours and shorter ones for the others
             @param g an object of the Graphics 2D class which allows us to edit graphics properties and draw in the frame
    */	
	public void drawTicks(Graphics2D g) {
		int length;
      g.setColor(Color.lightGray);
		
      for(int i  = 0; i < 60; i++) { // for 60 ticks 
			 if (i % 15 == 0) {// large ticks for multiples of pi/2
				g.setStroke(new BasicStroke(3));
				length=TICK_LENGTH / 2;
			 }
			 else if (i % 5 == 0) {// medium ticks for multiples of pi/6
				g.setStroke(new BasicStroke(1));
				length=TICK_LENGTH / 3;
			 }
			 else {
				g.setStroke(new BasicStroke(1)); //small ticks for the rest
				length=TICK_LENGTH / 8;
			 }
          
			 g.draw(new Line2D.Double(CENTRE, 12, CENTRE, 12 + length));
			 g.rotate(RADIANS / 60, CENTRE, CENTRE);
		 
	  }
	}
   /**  gets the millisecond, second, minute, and hour hands and stores them into an array
            @param the time passed into the parameter will get hands according to this time.
    */		
	public static Hand[] getHands(LocalTime time) {
      Hand hands[] = new Hand[4];
      hands[0] = new Hand(Hand.TimeUnit.MILLS, time);
      hands[1] = new Hand(Hand.TimeUnit.SECOND, time, hands[0]);
      hands[2] = new Hand(Hand.TimeUnit.MINUTE,time, hands[1]);
		hands[3] = new Hand(Hand.TimeUnit.HOUR, time, hands[2]);

		return hands;
	}
}