import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
 
public class AccApplet extends Applet {
    Accumulator acc;
    TextField accField;
    TextField inputField;
 
    public void init() {
        acc = new Accumulator();
        accField = new TextField(10);
        accField.setEditable(false);
        inputField = new TextField(10);
        inputField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    inputAction();
                }
            });
        add(accField);
        add(inputField);
        validate();
    }
 
    void inputAction() {
        try {
            int x = Integer.parseInt(inputField.getText());
            acc.add(x);
        } catch (NumberFormatException e) {}
        inputField.selectAll();
        repaint();
    }
 
    public void paint(Graphics g) {
        accField.setText(Integer.toString(acc.value()));
    }
}
 
class Accumulator {
    int accumulator = 0;
 
    void add(int x) {
        accumulator += x;
    }
 
    int value() {
        return accumulator;
    }
}
