package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.ValAction.INSTRUMENT_SIZE;
public class ToolsPanel extends JToolBar implements ChangeListener, ActionListener {
    JSpinner spinner;
    ValActionHandler actionHandler;

    private JButton fillMenuItem;
    private JButton clearMenuItem;
    private JButton pencilMenuItem;
    private JButton brushMenuItem;
    private JButton lineMenuItem;
    private JButton rectMenuItem;
    private JButton filledRectMenuItem;
    private JButton ovalMenuItem;
    private JButton filledOvalMenuItem;
    private JButton textMenuItem;
    private JButton colorPickerMenuItem;
    private JButton colorMenuItem;
    private JButton undoMenuItem, redoMenuItem;
    MenuActionHandler actionHandlerM;

    private JButton createToolBarButton(String text, ImageIcon icon) {
        JButton button = new JButton(text, icon);

        button.setFocusable(false);
        button.addActionListener(this);

        return button;
    }

    public ToolsPanel(ValActionHandler actionHandler, MenuActionHandler actionHandlerM) {
        super();

        this.actionHandler = actionHandler;
        this.actionHandlerM = actionHandlerM;

        setBorderPainted(false);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        //spinner setup
        JLabel label = new JLabel("Size: ");
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 99, 1);
        spinner = new JSpinner(model);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        JFormattedTextField textField = editor.getTextField();
        DefaultFormatter formatter = (DefaultFormatter) textField.getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        spinner.getModel().addChangeListener(this);
        spinner.setMaximumSize(spinner.getPreferredSize());

        //tool buttons setup
        undoMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/undo.png"));
        redoMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/redo.png"));
        fillMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/fill.png"));
        pencilMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/pencil.png"));
        brushMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/eraser.png"));
        lineMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/line.png"));
        rectMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/rect.png"));
        filledRectMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/rect_fill.png"));
        ovalMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/oval.png"));
        filledOvalMenuItem =createToolBarButton("", new ImageIcon("src/ui/icons/oval_fill.png"));
        clearMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/clear.png"));
        textMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/type.png"));
        colorPickerMenuItem = createToolBarButton("", new ImageIcon("src/ui/icons/picker.png"));

        //setup tool bar layout
        colorMenuItem = createToolBarButton("", null);
        colorMenuItem.setName("colourchange");
        colorMenuItem.setBackground(Color.BLACK);
        colorMenuItem.setOpaque(true);

        add(Box.createHorizontalGlue());

        add(undoMenuItem);
        add(redoMenuItem);
        addSeparator();
        add(fillMenuItem);
        add(pencilMenuItem);
        add(brushMenuItem);
        add(lineMenuItem);
        add(rectMenuItem);
        add(filledRectMenuItem);
        add(ovalMenuItem);
        add(filledOvalMenuItem);
        add(fillMenuItem);
        add(colorPickerMenuItem);
        addSeparator();
        add(textMenuItem);
        addSeparator();
        add(clearMenuItem);
        addSeparator();
        add(label);
        add(spinner);
        addSeparator();
        add(colorMenuItem);

        add(Box.createHorizontalGlue());
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
        }  else if (source == textMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_TEXT);
        }  else if (source == colorMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_COLOR);
        }  else if (source == colorPickerMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_COLOR_PICKER);
        }
    }

    public JSpinner getSpinner() {
        return spinner;
    }

    public JButton getColorChooser() {
        return colorMenuItem;
    }
}
