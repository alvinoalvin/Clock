import java.awt.*;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.time.LocalTime;

public class ClockComponent extends JComponent {

   
   /** a method that shows things that need to be updated and/or redrawn after every tick called by a timer*/
	private void update() {
		this.requestFocusInWindow();
		repaint();
   }

  	private Timer[] newTimer = new Timer[1]; // array of all the timers

	public ClockComponent() {
  		newTimer[0] = new Timer(1000/80, (a) -> {this.update();});
	}

	public void paint(Graphics g) {
		// the frame's dimensions
		final int w = getWidth();  // width
		final int h = getHeight(); // height

		// clear the specified rectangle by filling it with the background colour
		// useful when resizing because we want to erase what there was before
		g.clearRect(0, 0, w, h);

		// set up our transform to be relative to a 200x200 box
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// move to centre
		g2.translate(w/2.0, h/2.0);
		// scale
		int min = Math.min(w, h);
		double scaleFactor = min/(double)Clock.SCALE;
		g2.scale(scaleFactor, scaleFactor);
		// move to top left of bound
		g2.translate(-Clock.CENTRE, -Clock.CENTRE);

		// draw the 200x200 box
		Clock c = new Clock();
		c.draw(g2);

		g2.dispose();
	}
   /** starts the Clock by starting all timers*/
	public void start() {
      for (Timer time : newTimer) {
         time.start();
      }
	}
}
