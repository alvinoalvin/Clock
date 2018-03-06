import java.awt.*;
import java.awt.geom.Line2D;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
/** A Hand module for the Clock Class. Hands can have different angles
    @author Jeremy Hilliker @ Langara
    @author Alvin Ng
    @version 2017- 05 - 31 The Cake is a Lie
    @see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=50644&grpid=0&isprv=0&bp=0&ou=88736">a 04: Clock</a>
    Tester:  HandTester.java
*/
public class Hand {

	public final static double RADIANS = Clock.RADIANS;// 2pi
   public final static int CENTRE = Clock.CENTRE; // centre of screen
   
   /**  the time unit has properties of units Milliseconds, Seconds, Minutes, and hours \n 
      *       however you can only use timeUnit if the unit you are using matches one of these.
   */
	public enum TimeUnit {
		MILLS(ChronoField.MILLI_OF_SECOND, 0 , 0),
		SECOND(ChronoField.SECOND_OF_MINUTE, 80, 0.5f, Color.RED),
		MINUTE(ChronoField.MINUTE_OF_HOUR, 90, 1),
		HOUR(ChronoField.HOUR_OF_AMPM, 60, 2);

		public final ChronoField cf;
		public final int length;
		public final BasicStroke stroke;
		public final Color color;
      
      /**  creates a timeunit for the hands class a time unit has properties color, length,and width for drawing hands
                  @param  aCF a ChronoField
                  @param aLength the length of a clockhand
                  @param aWidth the width used for calculating stroke of the clockhand
             */
		TimeUnit(ChronoField aCF, int aLength, float aWidth) {
			this(aCF, aLength, aWidth, Color.BLACK);
		}
      
      /**  creates a timeunit for the hands class a time unit has properties color, length,and width for drawing hands
                  @param  aCF a ChronoField
                  @param aLength the length of a clockhand
                  @param aWidth the width used for calculating stroke of the clockhand
                  @param aColor the color of the hand if no color in the parameter the color will be black
             */
      TimeUnit(ChronoField aCF, int aLength, float aWidth, Color aColor) {
			cf = aCF;
			length = (Clock.SCALE * aLength) / (100 * 2);
			stroke = new BasicStroke(aWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

			color = aColor;
		}
	}

   
	private final TimeUnit unit; // ie: hour, minute, second, etc
	public final double angle; // the rotation of the hand in radians from 12 o'clock
   
   /**  constructor for the hands class creates a hand of the clock, which hand depends on the value of aUnit
            @param  aUnit the unit of time for the hand as well as properties that go with drawing the hand
            @param aTime the time at which we want to draw the hand of aUnit at
       */
	public Hand(TimeUnit aUnit, LocalTime aTime) {
			this(aUnit, aTime, null);
	}
   
   /**  constructor for the hands class creates a hand of the clock, which hand depends on the value of aUnit
            @param  aUnit the unit of time for the hand as well as properties that go with drawing the hand
            @param aTime the time at which we want to draw the hand of aUnit at
            @param prevHand the last hand for example for hour the prev. hand would be minute, for minute it'd be seconds etc. \n 
               used for calculating the space between each tick after calculating the angle.
       */   
	public Hand(TimeUnit aUnit, LocalTime aTime, Hand prevHand) {
		unit = aUnit;
		
		int timeUnit = aTime.get(unit.cf);
		long max = unit.cf.range().getMaximum() + 1;
      
      if (prevHand == null)
         angle = RADIANS / max * timeUnit;

      else
         angle = RADIANS/ max * timeUnit + prevHand.angle / max;

	}
   /**  draws the hand of the clock when called using the specified color, and stroke from the TimeUnit class \n 
               and the angle from the hand class
             @param g an object of the Graphics 2D class which allows us to edit graphics properties and draw in the frame
       */   
	public void draw(Graphics2D g) {
      g.setColor(unit.color);
      g.setStroke(unit.stroke);  
      g.rotate(angle, CENTRE, CENTRE);  
      g.draw(new Line2D.Double(CENTRE, CENTRE, CENTRE, CENTRE - unit.length));
      g.rotate(RADIANS-angle, CENTRE, CENTRE);
	}
}
