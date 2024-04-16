import java.awt.*;
import javax.swing.*;

public class header extends Panel{
    public header(){
        setLayout(new FlowLayout());
        add(new JLabel("Mistakes: "+GameBoardPanel.mistake));
        add(new JLabel("Cells Left: "+GameBoardPanel.cellLeft));
    }
}