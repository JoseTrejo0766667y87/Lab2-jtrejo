package com.mycompany.lab2;

import javax.swing.*;
import java.awt.*;

public class ReinicioClave extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtNewPassword, txtConfirmPassword;
    private JLabel lblRequirements;
    private JButton btnReset, btnClear, btnClose;

    public ReinicioClave() {
        setTitle("Password Reset");
        setSize(430, 370);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(33, 97, 140));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel lbl = new JLabel("Password Reset");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        return panel;
    }


    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 15, 30));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 6, 7, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        Font f = new Font("Segoe UI", Font.PLAIN, 13);
        Dimension dim = new Dimension(220, 30);

        txtUsername        = new JTextField();     txtUsername.setFont(f);        txtUsername.setPreferredSize(dim);
        txtNewPassword     = new JPasswordField(); txtNewPassword.setFont(f);     txtNewPassword.setPreferredSize(dim);
        txtConfirmPassword = new JPasswordField(); txtConfirmPassword.setFont(f); txtConfirmPassword.setPreferredSize(dim);

    
        g.gridx = 0; g.gridy = 0; g.anchor = GridBagConstraints.WEST;
        JLabel l1 = new JLabel("Username:"); l1.setFont(f);
        panel.add(l1, g);
        g.gridx = 1; panel.add(txtUsername, g);

     
        g.gridx = 0; g.gridy = 1;
        JLabel l2 = new JLabel("New Password:"); l2.setFont(f);
        panel.add(l2, g);
        g.gridx = 1; panel.add(txtNewPassword, g);

       
        g.gridx = 0; g.gridy = 2;
        JLabel l3 = new JLabel("Confirm Password:"); l3.setFont(f);
        panel.add(l3, g);
        g.gridx = 1; panel.add(txtConfirmPassword, g);

      
        lblRequirements = new JLabel("<html><font color='gray'>Min. 13 characters, 1 uppercase letter and 1 special character.</font></html>");
        lblRequirements.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        g.insets = new Insets(2, 6, 10, 6);
        panel.add(lblRequirements, g);

   
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(Color.WHITE);

        btnReset = button("Reset",  new Color(39, 174, 96));
        btnClear = button("Clear",  new Color(127, 140, 141));
        btnClose = button("Close",  new Color(192, 57, 43));

        btnPanel.add(btnReset);
        btnPanel.add(btnClear);
        btnPanel.add(btnClose);

        g.gridx = 0; g.gridy = 4; g.gridwidth = 2;
        g.insets = new Insets(5, 0, 0, 0);
        panel.add(btnPanel, g);

     
        btnReset.addActionListener(e -> actionReset());
        btnClear.addActionListener(e -> clear());
        btnClose.addActionListener(e -> dispose());

     
        txtNewPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateRequirements(new String(txtNewPassword.getPassword()));
            }
        });

        return panel;
    }

   
    private void updateRequirements(String password) {
        boolean hasLength    = password.length() >= 13;
        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasSpecial   = password.chars().anyMatch(c -> "!@#$%^&*()_+-=[]{}|;':\",./<>?".indexOf(c) >= 0);

        String colorL = hasLength    ? "green" : "red";
        String colorU = hasUppercase ? "green" : "red";
        String colorS = hasSpecial   ? "green" : "red";

        lblRequirements.setText(String.format(
            "<html>" +
            "<font color='%s'>✔ Minimum 13 characters (%d)</font><br>" +
            "<font color='%s'>✔ At least 1 uppercase letter</font><br>" +
            "<font color='%s'>✔ At least 1 special character (!@#$...)</font>" +
            "</html>",
            colorL, password.length(), colorU, colorS
        ));
    }

  
    private void actionReset() {
        String username    = txtUsername.getText().trim();
        String newPassword = new String(txtNewPassword.getPassword());
        String confirm     = new String(txtConfirmPassword.getPassword());


        if (username.isEmpty() || newPassword.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Required fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

      
        if (newPassword.length() < 13) {
            JOptionPane.showMessageDialog(this, "Password must be at least 13 characters long.", "Invalid password", JOptionPane.ERROR_MESSAGE);
            return;
        }

    
        boolean hasUppercase = newPassword.chars().anyMatch(Character::isUpperCase);
        if (!hasUppercase) {
            JOptionPane.showMessageDialog(this, "Password must contain at least 1 uppercase letter.", "Invalid password", JOptionPane.ERROR_MESSAGE);
            return;
        }

     
        boolean hasSpecial = newPassword.chars().anyMatch(c -> "!@#$%^&*()_+-=[]{}|;':\",./<>?".indexOf(c) >= 0);
        if (!hasSpecial) {
            JOptionPane.showMessageDialog(this, "Password must contain at least 1 special character.", "Invalid password", JOptionPane.ERROR_MESSAGE);
            return;
        }

      
        if (!newPassword.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Confirmation error", JOptionPane.ERROR_MESSAGE);
            txtConfirmPassword.setText("");
            return;
        }

    
        JOptionPane.showMessageDialog(this,
                "Password for user '" + username + "' was reset successfully.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        clear();
    }

    private void clear() {
        txtUsername.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
        lblRequirements.setText("<html><font color='gray'>Min. 13 characters, 1 uppercase letter and 1 special character.</font></html>");
        txtUsername.requestFocus();
    }

    private JButton button(String text, Color color) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBackground(color); b.setForeground(Color.WHITE);
        b.setFocusPainted(false); b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(105, 30));
        return b;
    }
}