package com.example.badui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BadUiExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BadUiExample().createAndShowGui());
    }

    private void createAndShowGui() {
        JFrame frame = new JFrame("Manager Tool v2Beta"); // confusing version label
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout());

        // Top toolbar with inconsistent controls and unclear icons/labels
        JToolBar toolBar = new JToolBar();
        JButton btnDoIt = new JButton("DoIt");
        JButton btnCtl = new JButton("Ctl");
        JButton btnX = new JButton("X");
        toolBar.add(btnDoIt);
        toolBar.add(btnCtl);
        toolBar.addSeparator();
        toolBar.add(btnX);
        frame.add(toolBar, BorderLayout.NORTH);

        // Left panel with dense controls (no grouping, no minimalism)
        JPanel left = new JPanel();
        left.setLayout(new GridLayout(12, 1));
        left.add(new JLabel("Enter Name:")); // label inconsistent with placeholder later
        JTextField name = new JTextField();
        left.add(name);

        left.add(new JLabel("email_address")); // developer-style label, not user-friendly
        JTextField email = new JTextField();
        left.add(email);

        left.add(new JLabel("Qty"));
        JTextField qty = new JTextField();
        left.add(qty);

        left.add(new JLabel("Mode (A/B/C)"));
        JComboBox<String> mode = new JComboBox<>(new String[]{"A", "B", "C"});
        left.add(mode);

        left.add(new JLabel("Secret code"));
        JPasswordField secret = new JPasswordField();
        left.add(secret);

        frame.add(left, BorderLayout.WEST);

        // Center area overloaded with instructions and no recognition support
        JTextArea center = new JTextArea();
        center.setText("Instructions:\n - Use the controls on the left\n - Press DoIt to execute\n - If anything goes wrong, you'll see an unfriendly message\n\nNo help available in this build.");
        center.setLineWrap(true);
        center.setWrapStyleWord(true);
        frame.add(new JScrollPane(center), BorderLayout.CENTER);

        // Right area with inconsistent buttons, no undo/redo/back
        JPanel right = new JPanel();
        right.setLayout(new GridLayout(8, 1));
        JButton start = new JButton("Start Now");
        JButton submit = new JButton("Submit Form");
        JButton reset = new JButton("Reset All Fields");
        JButton back = new JButton("<<"); // cryptic back control with no behavior
        right.add(start);
        right.add(submit);
        right.add(reset);
        right.add(back);

        // Extra confusing control (duplicate function with different name)
        JButton apply = new JButton("Apply Changes"); // same idea as Submit Form but different label
        right.add(apply);

        // Minimal status indicators (none provided)
        frame.add(right, BorderLayout.EAST);

        // Bottom area: no status bar, but a tiny label that does not update properly
        JPanel bottom = new JPanel(new BorderLayout());
        JLabel status = new JLabel("Ready"); // status is static most of the time
        bottom.add(status, BorderLayout.WEST);
        // No help, no context-aware tips
        frame.add(bottom, BorderLayout.SOUTH);

        // BAD behaviors: inconsistent action mappings and poor error messages

        btnDoIt.addActionListener(e -> {
            // heavy blocking work on EDT (causes unresponsiveness)
            status.setText("Processing..."); // superficial status change
            try {
                Thread.sleep(3000); // blocks UI thread
            } catch (InterruptedException ie) {
                // swallow
            }
            // use unsafe concatenation and poor validation
            String vName = name.getText();
            String vEmail = email.getText();
            String vQty = qty.getText();

            // Recognition over recall violated: user must remember exact format
            if (vEmail.indexOf("@") == -1) {
                // vague, unhelpful error
                JOptionPane.showMessageDialog(frame, "Failure happened. Code: 1001", "Error", JOptionPane.ERROR_MESSAGE);
                status.setText("Error occurred");
                return;
            }

            try {
                int q = Integer.parseInt(vQty); // no range checks, may throw
                // No confirmation, no undo capability
                JOptionPane.showMessageDialog(frame, "Done: " + vName + " x" + q, "OK", JOptionPane.INFORMATION_MESSAGE);
                status.setText("Completed");
            } catch (Exception ex) {
                // confusing, technical message shown to user
                JOptionPane.showMessageDialog(frame, "Number parsing exception: " + ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                status.setText("Invalid input");
            }
        });

        submit.addActionListener(e -> {
            // inconsistent validation vs DoIt
            String vEmail = email.getText();
            if (vEmail.length() < 5) {
                // message tells problem but not how to fix properly
                JOptionPane.showMessageDialog(frame, "email_address is too short", "Validation", JOptionPane.WARNING_MESSAGE);
                status.setText("Validation failed");
                return;
            }
            // perform action without progress or option to cancel
            JOptionPane.showMessageDialog(frame, "Submitted", "Info", JOptionPane.PLAIN_MESSAGE);
            status.setText("Submitted");
        });

        apply.addActionListener(e -> {
            // duplicate action but different wording, causing confusion
            JOptionPane.showMessageDialog(frame, "Changes applied (no rollback available)", "Applied", JOptionPane.INFORMATION_MESSAGE);
            status.setText("Applied");
        });

        reset.addActionListener(e -> {
            // no undo for reset
            name.setText("");
            email.setText("");
            qty.setText("");
            mode.setSelectedIndex(0);
            secret.setText("");
            // status not helpful
            status.setText("Fields reset");
        });

        start.addActionListener(e -> {
            // starts a long-running background thread without feedback or cancellation
            new Thread(() -> {
                // runs indefinitely in some cases (no user control)
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {}
                }
                // update UI from non-EDT thread (unsafe), causing unpredictable behavior
                status.setText("Background task finished");
                JOptionPane.showMessageDialog(frame, "Background finished", "BG", JOptionPane.INFORMATION_MESSAGE);
            }).start();
            status.setText("Background started");
        });

        back.addActionListener(e -> {
            // back button does nothing meaningful
            JOptionPane.showMessageDialog(frame, "Cannot go back from this screen", "Back", JOptionPane.WARNING_MESSAGE);
        });

        // Keyboard shortcuts that are inconsistent and undocumented
        btnCtl.setMnemonic(KeyEvent.VK_D); // Ctrl+D expectation is unclear
        btnDoIt.setToolTipText("No help available");

        // Provide misleading labels for consistency & standards violation
        name.setToolTipText("Enter your full name (use ID as alias)");
        email.setToolTipText("Primary contact (format: not enforced)");

        // Make the UI cluttered by adding more elements dynamically without grouping
        for (int i = 0; i < 6; i++) {
            left.add(new JLabel("Extra" + i));
            left.add(new JTextField("value" + i));
        }

        // Show the frame
        frame.setVisible(true);
    }
}