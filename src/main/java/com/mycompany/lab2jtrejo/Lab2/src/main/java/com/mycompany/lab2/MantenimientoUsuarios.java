package com.mycompany.lab2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MantenimientoUsuarios extends JFrame {

   
    private JTextField txtId, txtName, txtLastName, txtUsername;
    private JComboBox<String> cmbRole, cmbStatus;
    private JPasswordField txtPassword;


    private JTable table;
    private DefaultTableModel tableModel;

   
    private JButton btnAdd, btnModify, btnDeactivate, btnClear, btnClose;

  
    private ArrayList<String[]> userList = new ArrayList<>();
    private int idCounter = 1;

    public MantenimientoUsuarios() {
        setTitle("User Maintenance");
        setSize(750, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
        add(createTablePanel(), BorderLayout.SOUTH);

        loadSampleData();
    }

   
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(33, 97, 140));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel lbl = new JLabel("User Maintenance");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);
        return panel;
    }

   
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 210, 220)),
                BorderFactory.createEmptyBorder(15, 20, 10, 20)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 6, 5, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        Font f = new Font("Segoe UI", Font.PLAIN, 13);
        Dimension dim = new Dimension(160, 28);

        txtId       = field(f, dim); txtId.setEditable(false); txtId.setBackground(new Color(235, 235, 235));
        txtName     = field(f, dim);
        txtLastName = field(f, dim);
        txtUsername = field(f, dim);
        txtPassword = new JPasswordField(); txtPassword.setFont(f); txtPassword.setPreferredSize(dim);
        cmbRole     = new JComboBox<>(new String[]{"Administrator", "Operator", "Read-Only"});
        cmbRole.setFont(f);
        cmbStatus   = new JComboBox<>(new String[]{"Active", "Inactive"});
        cmbStatus.setFont(f);

       
        addRow(panel, g, f, 0, "ID:",        txtId,       "Name:",     txtName);
       
        addRow(panel, g, f, 1, "Last Name:", txtLastName, "Username:", txtUsername);
       
        addRow(panel, g, f, 2, "Password:",  txtPassword, "Role:",     cmbRole);
       
        addRow(panel, g, f, 3, "Status:",    cmbStatus,   null,        null);

        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        btnPanel.setBackground(Color.WHITE);

        btnAdd        = button("Add",        new Color(39, 174, 96));
        btnModify     = button("Modify",     new Color(41, 128, 185));
        btnDeactivate = button("Deactivate", new Color(230, 126, 34));
        btnClear      = button("Clear",      new Color(127, 140, 141));
        btnClose      = button("Close",      new Color(192, 57, 43));

        btnPanel.add(btnAdd);
        btnPanel.add(btnModify);
        btnPanel.add(btnDeactivate);
        btnPanel.add(btnClear);
        btnPanel.add(btnClose);

        g.gridx = 0; g.gridy = 4; g.gridwidth = 4; g.insets = new Insets(8, 0, 0, 0);
        panel.add(btnPanel, g);

      
        btnAdd.addActionListener(e        -> actionAdd());
        btnModify.addActionListener(e     -> actionModify());
        btnDeactivate.addActionListener(e -> actionDeactivate());
        btnClear.addActionListener(e      -> clear());
        btnClose.addActionListener(e      -> dispose());

        return panel;
    }

    private void addRow(JPanel p, GridBagConstraints g, Font f,
                        int row, String lbl1, JComponent comp1,
                        String lbl2, JComponent comp2) {
        g.gridy = row; g.gridwidth = 1;

        g.gridx = 0; g.anchor = GridBagConstraints.WEST;
        JLabel l1 = new JLabel(lbl1); l1.setFont(f);
        p.add(l1, g);

        g.gridx = 1;
        p.add(comp1, g);

        if (lbl2 != null) {
            g.gridx = 2;
            JLabel l2 = new JLabel(lbl2); l2.setFont(f);
            p.add(l2, g);
            g.gridx = 3;
            p.add(comp2, g);
        }
    }


    private JPanel createTablePanel() {
        String[] cols = {"ID", "Name", "Last Name", "Username", "Role", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(33, 97, 140));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(22);
        table.setSelectionBackground(new Color(173, 216, 230));

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) loadRow(row);
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(700, 150));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        panel.setBackground(new Color(240, 244, 248));
        panel.add(scroll);
        return panel;
    }


    private void actionAdd() {
        if (!validateFields()) return;

        String[] row = {
            String.valueOf(idCounter++),
            txtName.getText().trim(),
            txtLastName.getText().trim(),
            txtUsername.getText().trim(),
            (String) cmbRole.getSelectedItem(),
            (String) cmbStatus.getSelectedItem()
        };
        userList.add(row);
        tableModel.addRow(row);
        JOptionPane.showMessageDialog(this, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        clear();
    }

    private void actionModify() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user from the table.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validateFields()) return;

        tableModel.setValueAt(txtName.getText().trim(),     row, 1);
        tableModel.setValueAt(txtLastName.getText().trim(), row, 2);
        tableModel.setValueAt(txtUsername.getText().trim(), row, 3);
        tableModel.setValueAt(cmbRole.getSelectedItem(),    row, 4);
        tableModel.setValueAt(cmbStatus.getSelectedItem(),  row, 5);

        JOptionPane.showMessageDialog(this, "User updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        clear();
    }

    private void actionDeactivate() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user from the table.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Deactivate user '" + tableModel.getValueAt(row, 3) + "'?",
                "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setValueAt("Inactive", row, 5);
            JOptionPane.showMessageDialog(this, "User deactivated.", "Done", JOptionPane.INFORMATION_MESSAGE);
            clear();
        }
    }

    private boolean validateFields() {
        if (txtName.getText().trim().isEmpty() ||
            txtLastName.getText().trim().isEmpty() ||
            txtUsername.getText().trim().isEmpty() ||
            new String(txtPassword.getPassword()).trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Required fields", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void loadRow(int row) {
        txtId.setText(tableModel.getValueAt(row, 0).toString());
        txtName.setText(tableModel.getValueAt(row, 1).toString());
        txtLastName.setText(tableModel.getValueAt(row, 2).toString());
        txtUsername.setText(tableModel.getValueAt(row, 3).toString());
        cmbRole.setSelectedItem(tableModel.getValueAt(row, 4).toString());
        cmbStatus.setSelectedItem(tableModel.getValueAt(row, 5).toString());
    }

    private void clear() {
        txtId.setText(""); txtName.setText(""); txtLastName.setText("");
        txtUsername.setText(""); txtPassword.setText("");
        cmbRole.setSelectedIndex(0); cmbStatus.setSelectedIndex(0);
        table.clearSelection();
    }

    private void loadSampleData() {
        tableModel.addRow(new Object[]{"1", "John",  "Smith",  "jsmith",  "Administrator", "Active"});
        tableModel.addRow(new Object[]{"2", "Maria", "Lopez",  "mlopez",  "Operator",      "Active"});
        idCounter = 3;
    }

   
    private JTextField field(Font f, Dimension d) {
        JTextField t = new JTextField(); t.setFont(f); t.setPreferredSize(d); return t;
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