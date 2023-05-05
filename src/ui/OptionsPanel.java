package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.ValAction.INSTRUMENT_SIZE;

public class OptionsPanel extends JPanel implements ChangeListener, ActionListener {
    JSpinner spinner;
    ValActionHandler actionHandler;

    private JMenuItem fillMenuItem;
    private JMenuItem clearMenuItem;
    private JMenuItem pencilMenuItem;
    private JMenuItem brushMenuItem;
    private JMenuItem lineMenuItem;
    private JMenuItem rectMenuItem;
    private JMenuItem filledRectMenuItem;
    private JMenuItem ovalMenuItem;
    private JMenuItem filledOvalMenuItem;
    private JMenuItem colorMenuItem;
    private JMenuItem undoMenuItem, redoMenuItem;
    MenuActionHandler actionHandlerM;

    public OptionsPanel(ValActionHandler actionHandler, MenuActionHandler actionHandlerM) {
        this.actionHandler = actionHandler;
        this.actionHandlerM = actionHandlerM;

        JLabel label = new JLabel("Size:");
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 99, 1);

        spinner = new JSpinner(model);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        JFormattedTextField textField = editor.getTextField();
        DefaultFormatter formatter = (DefaultFormatter) textField.getFormatter();
        //editor.getTextField().setEditable(false);
        //editor.getTextField().setCaretPosition(1);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        spinner.getModel().addChangeListener(this);

        //menu items
        JLabel label_tools = new JLabel("Tools:");
        undoMenuItem = new JMenuItem("", new ImageIcon("src/ui/icons/undo.png"));
        undoMenuItem.addActionListener(this);
        undoMenuItem.setPreferredSize(new Dimension(20, undoMenuItem.getPreferredSize().height));
        redoMenuItem = new JMenuItem("", new ImageIcon("src/ui/icons/redo.png"));
        redoMenuItem.addActionListener(this);
        redoMenuItem.setPreferredSize(new Dimension(20, redoMenuItem.getPreferredSize().height));

        fillMenuItem = new JMenuItem("", new ImageIcon("src/ui/icons/fill.png"));
        fillMenuItem.addActionListener(this);
        fillMenuItem.setPreferredSize(new Dimension(20, fillMenuItem.getPreferredSize().height));
        pencilMenuItem = new JMenuItem("", new ImageIcon("src/ui/icons/pencil.png"));
        pencilMenuItem.addActionListener(this);
        pencilMenuItem.setPreferredSize(new Dimension(20, pencilMenuItem.getPreferredSize().height));
        brushMenuItem = new JMenuItem("", new ImageIcon("src/ui/icons/eraser.png"));
        brushMenuItem.addActionListener(this);
        brushMenuItem.setPreferredSize(new Dimension(20, brushMenuItem.getPreferredSize().height));
        lineMenuItem = new JMenuItem("", new ImageIcon("src/ui/icons/line.png"));
        lineMenuItem.addActionListener(this);
        lineMenuItem.setPreferredSize(new Dimension(20, lineMenuItem.getPreferredSize().height));
        rectMenuItem = new JMenuItem("", new ImageIcon("src/ui/icons/rect.png"));
        rectMenuItem.addActionListener(this);
        rectMenuItem.setPreferredSize(new Dimension(20, rectMenuItem.getPreferredSize().height));
        filledRectMenuItem = new JMenuItem("", new ImageIcon("src/ui/icons/rect_fill.png"));
        filledRectMenuItem.addActionListener(this);
        filledRectMenuItem.setPreferredSize(new Dimension(20, filledRectMenuItem.getPreferredSize().height));
        ovalMenuItem = new JMenuItem("", new ImageIcon("src/ui/icons/oval.png"));
        ovalMenuItem.addActionListener(this);
        ovalMenuItem.setPreferredSize(new Dimension(20, ovalMenuItem.getPreferredSize().height));
        filledOvalMenuItem = new JMenuItem("", new ImageIcon("src/ui/icons/oval_fill.png"));
        filledOvalMenuItem.addActionListener(this);
        filledOvalMenuItem.setPreferredSize(new Dimension(20, filledOvalMenuItem.getPreferredSize().height));
        clearMenuItem = new JMenuItem("", new ImageIcon("src/ui/icons/clear.png"));
        clearMenuItem.addActionListener(this);
        clearMenuItem.setPreferredSize(new Dimension(20, clearMenuItem.getPreferredSize().height));
        
        JLabel label_separator = new JLabel("|");
        JLabel label_separator_1 = new JLabel("|");
        colorMenuItem = new JMenuItem("");
        colorMenuItem.setName("colourchange");
        colorMenuItem.setBackground(Color.BLACK);
        colorMenuItem.setOpaque(true);
        colorMenuItem.addActionListener(this);

        add(undoMenuItem);
        add(redoMenuItem);
        add(label_tools);
        add(fillMenuItem);
        add(pencilMenuItem);
        add(brushMenuItem);
        add(lineMenuItem);
        add(rectMenuItem);
        add(filledRectMenuItem);
        add(ovalMenuItem);
        add(filledOvalMenuItem);
        add(clearMenuItem);
        add(label_separator);

        add(label);
        add(spinner);

        add(label_separator_1);
        add(colorMenuItem);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int value = (int) spinner.getValue();
        this.actionHandler.handleValAction(INSTRUMENT_SIZE, value);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == undoMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.UNDO);
        } else if (source == redoMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.REDO);
        } else if (source == clearMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CLEAR_CANVAS);
        } else if (source == fillMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_FILL);
        } else if (source == pencilMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_PENCIL);
        } else if (source == brushMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_BRUSH);
        } else if (source == lineMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_LINE);
        }  else if (source == rectMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_RECT);
        }  else if (source == filledRectMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_FILLED_RECT);
        }  else if (source == ovalMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_OVAL);
        }  else if (source == filledOvalMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_FILLED_OVAL);
        } else if (source == colorMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_COLOR);
        }
    }
}
