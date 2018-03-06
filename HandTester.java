import java.time.LocalTime;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HandTester {

	private final static double RADIANS = Math.PI*2;
	private final static double EPSILON = 1E-12;
   private final static int MAX = 60;
   private final static int HOUR_MAX = 12;

	public static void main(String[] args) {
		test_0();
      testCurrentHour();
      testCurrentMin();
      testCurrentSec();
      testColors();
      testAllAngles();
      testExists();
		System.err.println("PASS");
	}

	private static void test_0() {
		LocalTime time = LocalTime.MIN;
		Hand hours = new Hand(Hand.TimeUnit.HOUR, time);
		assert 0 == hours.angle;
	}

   private static void testCurrentHour() {//test for Hour uses current time to test
      LocalTime now = LocalTime.now();
      Hand mins = new Hand(Hand.TimeUnit.MINUTE, now);
      Hand hours = new Hand(Hand.TimeUnit.HOUR, now, mins);

      int currHour= now.getHour();   
      if (currHour >= HOUR_MAX )
         currHour -= HOUR_MAX;
      
      double expected = RADIANS / HOUR_MAX * currHour + mins.angle / HOUR_MAX;          
      assert expected == hours.angle;
   }
   private static void testCurrentMin() {//test for minutes uses current time to test
      LocalTime now = LocalTime.now();
      Hand sec = new Hand(Hand.TimeUnit.SECOND, now);
      Hand min = new Hand(Hand.TimeUnit.MINUTE, now, sec);

      int currMin = now.getMinute();
      
      double expected = RADIANS / MAX * currMin + sec.angle / MAX;
      
      assert expected == min.angle;
   }
   private static void testCurrentSec() {//test for seconds uses current time to test
      LocalTime now = LocalTime.now();
      Hand mill = new Hand(Hand.TimeUnit.MILLS, now);
      Hand sec = new Hand(Hand.TimeUnit.SECOND, now, mill);

      int currSec= now.getSecond();
      double expected = RADIANS / MAX * currSec + mill.angle / MAX;
      
      assert expected == sec.angle;
   }
   
   private static void testColors() {//checks to see if hand colors are correct

      BufferedImage buffer = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB_PRE);
      
		Graphics2D g2;
      g2=buffer.createGraphics();
      
      LocalTime now = LocalTime.now();
      
      Hand mill = new Hand(Hand.TimeUnit.MILLS, now);
      mill.draw(g2);
      assert Color.BLACK == g2.getColor();
      
      Hand sec = new Hand(Hand.TimeUnit.SECOND, now, mill);
      sec.draw(g2);
      assert Color.RED == g2.getColor();
      
      Hand min = new Hand(Hand.TimeUnit.MINUTE, now, sec);
      min.draw(g2);
      assert Color.BLACK == g2.getColor();
      
      Hand hour = new Hand(Hand.TimeUnit.HOUR, now , min);
      hour.draw(g2);
      assert Color.BLACK == g2.getColor();
   }


   private static void testAllAngles() {
      
      //test case 1 all 0 degree
      LocalTime now = LocalTime.of(0, 0, 0);
      Hand mill = new Hand(Hand.TimeUnit.MILLS, now);
      Hand sec = new Hand(Hand.TimeUnit.SECOND, now, mill);
      Hand min = new Hand(Hand.TimeUnit.MINUTE, now, sec);
      Hand hour = new Hand(Hand.TimeUnit.HOUR, now, min);

      assert 0 == sec.angle;
      assert 0 == min.angle;
      assert 0 == hour.angle;
      
      //test case 2 hour PI radians
      now = LocalTime.of(6, 0, 0);
     
      mill = new Hand(Hand.TimeUnit.MILLS, now);
      sec = new Hand(Hand.TimeUnit.SECOND, now, mill);
      min = new Hand(Hand.TimeUnit.MINUTE, now, sec);
      hour = new Hand(Hand.TimeUnit.HOUR, now, min);
      
      double expSec = 0;
      double expMin = 0;
      double expHour = Math.PI;
      
      assert expSec == sec.angle;
      assert expMin == min.angle;
      assert expHour == hour.angle;
      
     // test case 3, all hands between ticks
      now = LocalTime.of(10, 15, 20);

      mill = new Hand(Hand.TimeUnit.MILLS, now);
      sec = new Hand(Hand.TimeUnit.SECOND, now, mill);
      min = new Hand(Hand.TimeUnit.MINUTE, now, sec);
      hour = new Hand(Hand.TimeUnit.HOUR, now, min);
      
      expSec = RADIANS / MAX * now.getSecond() + mill.angle / MAX;
      expMin = RADIANS / MAX * now.getMinute() + sec.angle / MAX;
      expHour = RADIANS / HOUR_MAX * now.getHour() + min.angle / HOUR_MAX;
      
      assert expSec == sec.angle;
      assert expMin == min.angle;
      assert expHour == hour.angle;           
      
      
   }//Check return value of getHands if has 4 hands to see if they exist
   private static void testExists() {
      LocalTime now = LocalTime.of(0, 0, 0);
      boolean exists = false;
      
      Hand hands[] = Clock.getHands(now);
      int count=0;
      
      for (Hand hand : hands)
         count++;   
      
      if (count == 4)
         exists = true;
      
      assert exists == true;
      
   } 
	// ** Do Not Change Below this line ** //

	private static void assertEquals(double exp, double act) {
		assert Math.abs(exp - act) <= EPSILON :
			"\nexp: " + exp + "\nact: " + act + "\ndif: " + (exp-act);
	}

	static {
		boolean assertsEnabled = false;
		assert assertsEnabled = true; // Intentional side effect!!!
		if (!assertsEnabled) {
			throw new RuntimeException("Asserts must be enabled!!! java -ea");
		}
	}
}
