import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FocusScreenReward extends JPanel implements MouseListener{

    public FocusScreenReward() {
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.addMouseListener(this);

        JLabel text = new JLabel("Yippiee, time for a break", SwingConstants.CENTER);
        text.setFont(FontMaker.h1);
        this.add(text, BorderLayout.CENTER);
    }

    @Override
	public void mouseClicked(MouseEvent e) {
        Main.showCard("Menu");
    }
    @Override
	public void mousePressed(MouseEvent e) {
    }
    @Override
	public void mouseReleased(MouseEvent e) {
    }
    @Override
	public void mouseEntered(MouseEvent e) {
    }
    @Override
	public void mouseExited(MouseEvent e) {
    }

    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
