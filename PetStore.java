import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PetStore extends JScrollPane {
    
    public PetStore() {
        
        this.setBorder(null);
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayout(0,2, 10, 10));
        scrollPanel.setBackground(Color.WHITE);
        
        for (int i=0; i<10; i++) {
            JPanel j = new JPanel();
            j.setBackground(new Color(240, 240, 240));
            scrollPanel.add(j);
        }
        this.setViewportView(scrollPanel);
        revalidate();
    }
}
