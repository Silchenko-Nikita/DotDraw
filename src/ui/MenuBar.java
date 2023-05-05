package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar implements ActionListener {
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenuItem saveToFileMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu optionsMenu;
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
    MenuActionHandler actionHandler;

    MenuBar(MenuActionHandler actionHandler) {
        this.actionHandler = actionHandler;

        fileMenu = new JMenu("File");
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(this);
        saveToFileMenuItem = new JMenuItem("Save");
        saveToFileMenuItem.addActionListener(this);
        fileMenu.add(saveToFileMenuItem);
        fileMenu.add(exitMenuItem);

        /* TEMP HIDES MENUS */
        /*
        editMenu = new JMenu("Edit");
        undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.addActionListener(this);
        redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.addActionListener(this);
        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);

        optionsMenu = new JMenu("Options");
        fillMenuItem = new JMenuItem("Fill");
        fillMenuItem.addActionListener(this);
        pencilMenuItem = new JMenuItem("Pencil");
        pencilMenuItem.addActionListener(this);
        brushMenuItem = new JMenuItem("Eraser");
        brushMenuItem.addActionListener(this);
        lineMenuItem = new JMenuItem("Line");
        lineMenuItem.addActionListener(this);
        rectMenuItem = new JMenuItem("Rectangle");
        rectMenuItem.addActionListener(this);
        filledRectMenuItem = new JMenuItem("Filled rectangle");
        filledRectMenuItem.addActionListener(this);
        ovalMenuItem = new JMenuItem("Oval");
        ovalMenuItem.addActionListener(this);
        filledOvalMenuItem = new JMenuItem("Filled oval");
        filledOvalMenuItem.addActionListener(this);
        clearMenuItem = new JMenuItem("Clear");
        clearMenuItem.addActionListener(this);
        optionsMenu.add(fillMenuItem);
        optionsMenu.add(pencilMenuItem);
        optionsMenu.add(brushMenuItem);
        optionsMenu.add(lineMenuItem);
        optionsMenu.add(rectMenuItem);
        optionsMenu.add(filledRectMenuItem);
        optionsMenu.add(ovalMenuItem);
        optionsMenu.add(filledOvalMenuItem);
        optionsMenu.add(clearMenuItem);

        colorMenuItem = new JMenuItem("Color");
        colorMenuItem.addActionListener(this);
        optionsMenu.add(colorMenuItem); */

        add(fileMenu);/*
        add(editMenu);
        add(optionsMenu); */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == saveToFileMenuItem) {
            actionHandler.handleMenuAction(MenuAction.SAVE_TO_FILE);
        } else if (source == undoMenuItem) {
            actionHandler.handleMenuAction(MenuAction.UNDO);
        } else if (source == redoMenuItem) {
            actionHandler.handleMenuAction(MenuAction.REDO);
        } else if (source == exitMenuItem) {
            actionHandler.handleMenuAction(MenuAction.EXIT);
        } else if (source == clearMenuItem) {
            actionHandler.handleMenuAction(MenuAction.CLEAR_CANVAS);
        } else if (source == fillMenuItem) {
            actionHandler.handleMenuAction(MenuAction.CHOOSE_FILL);
        } else if (source == pencilMenuItem) {
            actionHandler.handleMenuAction(MenuAction.CHOOSE_PENCIL);
        } else if (source == brushMenuItem) {
            actionHandler.handleMenuAction(MenuAction.CHOOSE_BRUSH);
        } else if (source == lineMenuItem) {
            actionHandler.handleMenuAction(MenuAction.CHOOSE_LINE);
        }  else if (source == rectMenuItem) {
            actionHandler.handleMenuAction(MenuAction.CHOOSE_RECT);
        }  else if (source == filledRectMenuItem) {
            actionHandler.handleMenuAction(MenuAction.CHOOSE_FILLED_RECT);
        }  else if (source == ovalMenuItem) {
            actionHandler.handleMenuAction(MenuAction.CHOOSE_OVAL);
        }  else if (source == filledOvalMenuItem) {
            actionHandler.handleMenuAction(MenuAction.CHOOSE_FILLED_OVAL);
        } else if (source == colorMenuItem) {
            actionHandler.handleMenuAction(MenuAction.CHOOSE_COLOR);
        }
    }
}
