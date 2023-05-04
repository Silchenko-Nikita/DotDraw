package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import static ui.ValAction.INSTRUMENT_SIZE;

public class OptionsPanel extends JPanel implements ChangeListener {
    JSpinner spinner;
    ValActionHandler actionHandler;

    public OptionsPanel(ValActionHandler actionHandler) {
        this.actionHandler = actionHandler;

        JLabel label = new JLabel("Instrument size:");
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 60, 1);

        spinner = new JSpinner(model);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        JFormattedTextField textField = editor.getTextField();
        DefaultFormatter formatter = (DefaultFormatter) textField.getFormatter();
        //editor.getTextField().setEditable(false);
        //editor.getTextField().setCaretPosition(1);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        spinner.getModel().addChangeListener(this);

        add(label);
        add(spinner);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int value = (int) spinner.getValue();
        this.actionHandler.handleValAction(INSTRUMENT_SIZE, value);
    }
}
