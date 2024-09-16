import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calcu extends JFrame {
    JLabel display;
    JLabel memDisplay;
    final int WIDTH = 35, HEIGHT = 35;
    final int H_GAP = 10, V_GAP = 10;
    final int x = 13, y = 30;
    int tempx, tempy;
    boolean canClear = true;
    double num = 0;
    String op = null;
    Double memValue = null;


    //  buttonTexts
    String[] digitButtonText = {"7", "4", "1", "0", "8", "5", "2", "+/-", "9", "6", "3", "."};
    String[] opButtonText = {"/", "sqrt", "*", "%", "-", "1/X", "+", "="};
    String[] memButtonText = {"MC", "MR", "MS", "M+"};
    String[] specialButtonText = {"Backspc", "C", "CE"};

    DigitButton[] digitButton = new DigitButton[digitButtonText.length];
    OpButton[] opButton = new OpButton[opButtonText.length];
    MemButton[] memButton = new MemButton[memButtonText.length];
    SpecialButton[] specialButton = new  SpecialButton[specialButtonText.length];

    public Calcu(String appliname) {

        //  frame
        super(appliname);
        setResizable(false);
        setSize(300,500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        // display
        display = new JLabel("0",JLabel.RIGHT);
        display.setBounds(x,y,260,HEIGHT + 20);
        display.setForeground(new Color(224, 224, 224));
        display.setFont(new Font("Arial", Font.BOLD, 20));
        display.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.darkGray, 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 9)
        ));
        add(display);

        // memoryDisplay
        memDisplay = new JLabel("M", JLabel.LEFT);
        memDisplay.setBounds(x, y - 23, 227, 20);
        memDisplay.setForeground(Color.orange);
        memDisplay.setFont(new Font("Arial", Font.PLAIN,  12));
        memDisplay.setVisible(false);
        add(memDisplay);


        tempx = x;
        tempy = y + 4*(HEIGHT + V_GAP);

        for(int i = 0; i < memButtonText.length; ++i) {
            memButton[i] = new MemButton(memButtonText[i],tempx,tempy,WIDTH,HEIGHT,this);
            memButton[i].setFocusable(false);
            memButton[i].setFont(new Font("Arial", Font.BOLD, 12));
            memButton[i].setMargin(new Insets(0, 0, 0, 0));

            // Aesthetic improvements
            memButton[i].setBackground(new Color(74, 74, 74));
            memButton[i].setForeground(new Color(224, 224, 224));
            memButton[i].setOpaque(true);
            memButton[i].setBorderPainted(false);

            tempy += HEIGHT + V_GAP;
        }

        tempx +=  WIDTH + H_GAP;
        tempy = y + 4*(HEIGHT + V_GAP);

        for(int i = 0;i < digitButtonText.length; ++i) {
            digitButton[i] = new DigitButton(digitButtonText[i],tempx,tempy,WIDTH,HEIGHT,this);

            digitButton[i].setFocusable(false);
            digitButton[i].setFont(new Font("Arial", Font.BOLD, 15));
            digitButton[i].setMargin(new Insets(0, 0, 0, 0));

            // Aesthetic improvements
            digitButton[i].setBackground(new Color(44, 44, 44));
            digitButton[i].setForeground(new Color(224, 224, 224));
            digitButton[i].setOpaque(true);
            digitButton[i].setBorderPainted(false);

            tempy += HEIGHT + V_GAP;
            if((i+1) % 4 == 0) {
                tempx += WIDTH + H_GAP;
                tempy =  y + 4*(HEIGHT + V_GAP);
            }
        }


        tempy = y + 4*(HEIGHT + V_GAP);
        for(int i = 0; i < opButton.length; i++) {
            opButton[i] = new OpButton(opButtonText[i],tempx,tempy,WIDTH,HEIGHT,this);
            opButton[i].setFocusable(false);
            opButton[i].setFont(new Font("Arial", Font.BOLD, 12));
            opButton[i].setMargin(new Insets(0, 0, 0, 0));

            // Aesthetic improvements
            opButton[i].setBackground(new Color(255, 149, 0));
            opButton[i].setForeground(new Color(30, 30, 30));
            opButton[i].setOpaque(true);
            opButton[i].setBorderPainted(false);

            tempy += HEIGHT + V_GAP;
            if((i+1) % 4 == 0) {
                tempx += WIDTH + H_GAP;
                tempy =  y + 4*(HEIGHT + V_GAP);
            }
        }

        tempx = x;
        tempy =  y + 3*(HEIGHT + V_GAP);
        for(int i = 0; i < specialButton.length; ++i) {
            specialButton[i] = new SpecialButton(specialButtonText[i],tempx,tempy,WIDTH + 45,HEIGHT,this);
            specialButton[i].setFocusable(false);
            specialButton[i].setFont(new Font("Arial", Font.BOLD, 10));
            specialButton[i].setMargin(new Insets(0, 0, 0, 0));

            // Aesthetic improvements
            specialButton[i].setBackground(new Color(255, 105, 180));
            specialButton[i].setForeground(new Color(30, 30, 30));
            specialButton[i].setOpaque(true);
            specialButton[i].setBorderPainted(false);

             tempx += WIDTH + 45 + H_GAP;
        }
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static String getformatedResult(Double b) { // 34.0 getformatedResult -> 34
        String res = ""+b;
        if(res.endsWith(".0")) return res.substring(0,res.length() - 2);
        return res;
    }
    public static void main(String[] args) {
        new Calcu("Calculator");
    }
}

class DigitButton extends JButton implements ActionListener {
    Calcu cal;
    public DigitButton(String cap,int x, int y, int width ,int height, Calcu cal) {
        super(cap);
        this.cal = cal;
        setBounds(x,y,width,height);
        addActionListener(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(new Color( 77, 77, 77));
            }
            @Override
            public void mouseExited(MouseEvent e){
                e.getComponent().setBackground(new Color(44, 44, 44));
            }
        });
        cal.add(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String tempTxt = e.getActionCommand();

        if(tempTxt.equals(".")) {
            if(!cal.display.getText().contains(".")) {
               cal.display.setText( cal.display.getText() + ".");
               cal.canClear = false;
               return;
            }
            else return;
        }

        if(tempTxt.equals("0")) {
           if(cal.display.getText().equals("0")) return ;
        }

        if(cal.canClear) {
            cal.display.setText(tempTxt);
            cal.canClear = false;
        }
        else {
            cal.display.setText(cal.display.getText() + tempTxt);
        }
    }
}
class OpButton extends JButton implements ActionListener{
    Calcu cal;
    public OpButton(String cap,int x, int y, int width ,int height, Calcu cal) {
        super(cap);
        this.cal = cal;
        setBounds(x,y,width,height);
        addActionListener(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(new Color( 255, 179, 77));
            }
            @Override
            public void mouseExited(MouseEvent e){
                e.getComponent().setBackground(new Color(255, 149, 0));
            }
        });
        cal.add(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String opTxt = e.getActionCommand();
        String currentText = cal.display.getText();
        double currentValue;

        try {
            currentValue = Double.parseDouble(currentText);
        } catch (NumberFormatException ex) {
            cal.display.setText("Error");
            return;
        }

        if(opTxt.equals("sqrt")) {
            try {
                cal.display.setText(Calcu.getformatedResult(Math.sqrt(currentValue)));
            }
            catch(Exception ev) {
                cal.display.setText("Divide by 0.");
            }
            return;
        }

        if(opTxt.equals("1/x"))
        {
            try {
                cal.display.setText(Calcu.getformatedResult( 1 / currentValue));
            }
            catch(ArithmeticException excp) {
                cal.display.setText("Divide by 0.");
            }
            return;
        }


        cal.canClear = true;

        if (!opTxt.equals("=")) {
            if (cal.op != null) {
                switch (cal.op) {
                    case "+":
                        cal.num += currentValue;
                        break;
                    case "-":
                        cal.num -= currentValue;
                        break;
                    case "*":
                        cal.num *= currentValue;
                        break;
                    case "/":
                        try {
                            cal.num /= currentValue;
                        }
                        catch (ArithmeticException excep) {
                            cal.display.setText("Divide by 0.");
                            return;
                        }
                        break;
                    case "%":
                        try {
                            cal.num %= currentValue;
                        }
                        catch (ArithmeticException excp) {
                            cal.display.setText("Divide by 0.");
                            return;
                        }
                        break;
                }
            } else {
                cal.num = currentValue;
            }

            cal.op = opTxt;
            cal.display.setText(currentText + " " + opTxt + " ");
            return;
        }

        // Handle "=" press
        if (cal.op != null) {
            switch (cal.op) {
                case "+":
                    cal.num += currentValue;
                    break;
                case "-":
                    cal.num -= currentValue;
                    break;
                case "*":
                    cal.num *= currentValue;
                    break;
                case "/":
                    try {
                        cal.num /= currentValue;
                    } catch (ArithmeticException excep) {
                        cal.display.setText("Divide by 0.");
                        return;
                    }
                    break;
                case "%":
                    try {
                        cal.num %= currentValue;
                    } catch (ArithmeticException excp) {
                        cal.display.setText("Divide by 0.");
                        return;
                    }
                    break;
            }
        }
        cal.display.setText(Calcu.getformatedResult(cal.num));

        // Reset operator
        cal.op = null;
}}
class MemButton extends JButton implements ActionListener {
    Calcu cal;
    public MemButton(String cap,int x, int y,int width,int height,Calcu cal) {
        super(cap);
        this.cal = cal;
        setBounds(x,y,width,height);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(new Color(110, 110, 110 ));
            }
            @Override
            public void mouseExited(MouseEvent e){
                e.getComponent().setBackground(new Color( 74, 74, 74));
            }
        });
        addActionListener(this);
        cal.add(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
         String cmd = e.getActionCommand();
         cal.canClear = true;
         if(cmd.equals("MS")) {
             try {
                 cal.memValue = Double.parseDouble(cal.display.getText());
                 cal.memDisplay.setVisible(true);
             }
             catch(Exception ev) {
                 cal.display.setText(" ERROR:(");
             }
         }
         if(cal.memValue == null) return;
         if(cmd.equals("MC")) cal. memValue = null;
         if(cmd.equals("M+")) {
             try {
                 cal.memValue += Double.parseDouble(cal.display.getText());
             }
             catch(Exception ev) {
                 cal.display.setText(" ERROR:(");
             }
         }
         if(cmd.equals("MR")) {
             cal.display.setText(Calcu.getformatedResult(cal.memValue));
         }

    }
}
class SpecialButton extends JButton implements ActionListener{
    Calcu cal;
    public SpecialButton(String cap,int x, int y, int width ,int height, Calcu cal) {
        super(cap);
        this.cal = cal;
        setBounds(x,y,width,height);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(new Color( 255, 140, 200 ));
            }
            @Override
            public void mouseExited(MouseEvent e){
                e.getComponent().setBackground(new Color(255, 105, 180));
            }
        });
        addActionListener(this);
        cal.add(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd.equals("C")) {
            cal.memValue = null;
            cal.op = null;
            cal.num = 0;
            cal.canClear = true;
            cal.memDisplay.setVisible(false);
            cal.display.setText("0");
        }
        else if(cmd.equals("CE")) cal.display.setText("0");
        else {
            String currentValue = cal.display.getText();
            if(currentValue.length() < 2) return;
            cal.display.setText(currentValue.substring(0,currentValue.length() - 1));
        }
    }
}