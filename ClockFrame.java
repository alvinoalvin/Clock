import javax.swing.JFrame;

public class ClockFrame {

    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;


    public int getWidth(){
        return WIDTH;
    }
    public int getHeight(){
        return HEIGHT;
    }
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        final ClockComponent comp;
       
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("A Clock");         
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
        
        comp = new ClockComponent();
        frame.add(comp);
        frame.setVisible(true);
        comp.start();
    }
}
