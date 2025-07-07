import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Contact {
    String name, phone, email, address;

    Contact(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
}

public class ContactManager extends JFrame {
    private ArrayList<Contact> contactList = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField nameField, phoneField, emailField, addressField, searchField;

    public ContactManager() {
        setTitle("Contact Manager");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Contact Details"));

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        addressField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);

        JButton addBtn = new JButton("Add Contact");
        JButton updateBtn = new JButton("Update Contact");
        formPanel.add(addBtn);
        formPanel.add(updateBtn);

        // Table to display contacts
        tableModel = new DefaultTableModel(new Object[]{"Name", "Phone", "Email", "Address"}, 0);
        table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        // Bottom panel: Search, Delete
        JPanel bottomPanel = new JPanel(new FlowLayout());

        searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        JButton deleteBtn = new JButton("Delete Selected");

        bottomPanel.add(new JLabel("Search:"));
        bottomPanel.add(searchField);
        bottomPanel.add(searchBtn);
        bottomPanel.add(deleteBtn);

        // Add components to frame
        add(formPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add Contact
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();

            if (!name.isEmpty() && !phone.isEmpty()) {
                Contact contact = new Contact(name, phone, email, address);
                contactList.add(contact);
                tableModel.addRow(new Object[]{name, phone, email, address});
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Name and Phone are required.");
            }
        });

        // Update Contact
        updateBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                Contact contact = contactList.get(selected);
                contact.name = nameField.getText().trim();
                contact.phone = phoneField.getText().trim();
                contact.email = emailField.getText().trim();
                contact.address = addressField.getText().trim();

                tableModel.setValueAt(contact.name, selected, 0);
                tableModel.setValueAt(contact.phone, selected, 1);
                tableModel.setValueAt(contact.email, selected, 2);
                tableModel.setValueAt(contact.address, selected, 3);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a contact to update.");
            }
        });

        // Delete Contact
        deleteBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                contactList.remove(selected);
                tableModel.removeRow(selected);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a contact to delete.");
            }
        });

        // Search Contact
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            tableModel.setRowCount(0); // Clear table

            for (Contact c : contactList) {
                if (c.name.toLowerCase().contains(keyword) || c.phone.contains(keyword)) {
                    tableModel.addRow(new Object[]{c.name, c.phone, c.email, c.address});
                }
            }
        });

        // Fill form when table row is selected
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selected = table.getSelectedRow();
                if (selected >= 0) {
                    nameField.setText(tableModel.getValueAt(selected, 0).toString());
                    phoneField.setText(tableModel.getValueAt(selected, 1).toString());
                    emailField.setText(tableModel.getValueAt(selected, 2).toString());
                    addressField.setText(tableModel.getValueAt(selected, 3).toString());
                }
            }
        });

        setVisible(true);
    }

    // Clear all form fields
    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
    }

    public static void main(String[] args) {
        new ContactManager();
    }
}
