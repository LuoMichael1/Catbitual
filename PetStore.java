import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PetStore extends JScrollPane {
    
    public PetStore() {

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayout(0,2, 10, 10));
        
        for (int i=0; i<10; i++) {
            JPanel j = new JPanel();
            j.setBackground(Color.LIGHT_GRAY);
            scrollPanel.add(j);
        }
        this.setViewportView(scrollPanel);
        revalidate();
    }
}
