import java.awt.event.*;
import javax.swing.*;       
 
public class TestButtons{
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,400);
        
        //Display the window.
        frame.setVisible(true);
        
        JPanel panel = new JPanel();
        
        JButton button = new JButton("Exit");
        JButton button2 = new JButton("Check Button");
        panel.add(button);
        panel.add(button2);
        
        frame.add(panel);
        
        button.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		System.exit(0);
        	}
        });     
        
        button2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		JOptionPane.showMessageDialog(panel, "This button works");
        	}
        });
        
        ActionListener listen = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		JOptionPane.showMessageDialog(panel, "Multiple buttons work");
        	}
        };
        
        for(int i = 0; i < 100; i++){
        	JButton manyButtons = new JButton("Multiple Buttons");
        	manyButtons.addActionListener(listen);
        	panel.add(manyButtons);
        }
    }
 
    public static void main(String[] args) {
      createAndShowGUI();
    }
}