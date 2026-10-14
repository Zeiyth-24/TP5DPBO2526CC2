import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PeopleMenu extends JFrame {

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua karyawan
    private ArrayList<Person> listPerson;
    private Database database;


    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunField;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private JTable personTable;
    private JTextField emailField;
    private JSpinner tanggalSpinner;

    public static void main(String[] args) {
        // buat object window
        PeopleMenu menu = new PeopleMenu();

        // atur ukuran window
        menu.setSize( 600, 500);

        // Letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // constructor
    public PeopleMenu() {
        // inisialisasi listPerson
        database = new Database();
        tanggalSpinner = new JSpinner(new SpinnerDateModel());

        // isi tabel karyawan
        personTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = { "Lainnya", "Full-time", "Part-time", "Contract", "Internship"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton. addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Apakah anda yakin akan menghapus data karyawan?");
                // TODO: tambahkan konfirmasi sebelum menghapus data
                deleteData();
            }
        });

        // saat tombol cancel ditekan
        cancelButton. addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        personTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = personTable.getSelectedRow();

                // Ambil data langsung dari baris tabel yang diklik
                String curId = personTable.getModel().getValueAt(selectedIndex, 0).toString();
                String curNama = personTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curTahun = personTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curKategori = personTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curEmail = personTable.getModel().getValueAt(selectedIndex, 4).toString();

                // Tampilkan ke kolom input form
                idField.setText(curId);
                namaField.setText(curNama);
                tahunField.setText(curTahun);
                kategoriComboBox.setSelectedItem(curKategori);
                emailField.setText(curEmail);

                // Ubah tombol 'Add' jadi 'Update' dan tampilkan tombol delete
                addUpdateButton.setText("Update");
                deleteButton.setVisible(true);
            }
        });

    }
    public final DefaultTableModel setTable() {
        // Tentukan kolom tabel (sesuaikan jika di TP4 kemarin ada kolom tambahan)
        Object[] cols = { "ID", "Nama", "Tahun Lahir", "Kategori", "Email" };
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM person");
            while (resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = resultSet.getString("id");
                row[1] = resultSet.getString("nama");
                row[2] = resultSet.getString("thn_lahir"); // sesuaikan dengan nama kolom di database
                row[3] = resultSet.getString("kategori");
                row[4] = resultSet.getString("email");
                tmp.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tmp;
    }

    public void insertData() {
        String id = idField.getText().trim();
        String nama = namaField.getText().trim();
        String tahunStr = tahunField.getText().trim();
        String kategori = (kategoriComboBox.getSelectedItem() != null) ? kategoriComboBox.getSelectedItem().toString() : "";
        String email = emailField.getText().trim();

        // 1. Validasi: Error jika ada kolom input yang kosong
        if (id.isEmpty() || nama.isEmpty() || tahunStr.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom input harus diisi!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Validasi: Error jika sudah ada ID yang sama di database
        try {
            ResultSet check = database.selectQuery("SELECT id FROM person WHERE id = '" + id + "'");
            if (check.next()) {
                JOptionPane.showMessageDialog(this, "ID " + id + " sudah terdaftar! Gunakan ID lain.", "Error ID Duplikat", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memeriksa ID: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Validasi angka untuk tahun lahir
        int tahun;
        try {
            tahun = Integer.parseInt(tahunStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tahun lahir harus berupa angka!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4. Masukkan ke database
        String sqlQuery = "INSERT INTO person VALUES ('" + id + "', '" + nama + "', " + tahun + ", '" + kategori + "', '" + email + "')";
        database.insertUpdateDeleteQuery(sqlQuery);

        // Refresh tabel dan bersihkan form
        personTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateData() {
        String id = idField.getText().trim();
        String nama = namaField.getText().trim();
        String tahunStr = tahunField.getText().trim();
        String kategori = (kategoriComboBox.getSelectedItem() != null) ? kategoriComboBox.getSelectedItem().toString() : "";
        String email = emailField.getText().trim();

        // Validasi: Error jika ada kolom input yang kosong
        if (id.isEmpty() || nama.isEmpty() || tahunStr.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom input harus diisi saat update!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tahun;
        try {
            tahun = Integer.parseInt(tahunStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tahun lahir harus berupa angka!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Query UPDATE berdasarkan ID
        String sqlQuery = "UPDATE person SET nama = '" + nama + "', thn_lahir = " + tahun + ", kategori = '" + kategori + "', email = '" + email + "' WHERE id = '" + id + "'";
        database.insertUpdateDeleteQuery(sqlQuery);

        // Refresh tabel dan bersihkan form
        personTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(this, "Data berhasil diubah!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deleteData() {
        String id = idField.getText().trim();

        String sqlQuery = "DELETE FROM person WHERE id = '" + id + "'";
        database.insertUpdateDeleteQuery(sqlQuery);

        personTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    public void clearForm() {
        idField.setText("");
        namaField.setText("");
        tahunField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        emailField.setText("");

        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        selectedIndex = -1;
    }

    private void populateList() {
        listPerson.add(new Person("EMP001", "Budi Santoso", 1992, "Full-time", "budi.santoso@company.com"));
        listPerson.add(new Person("EMP002", "Siti Aminah", 1997, "Full-time", "siti.aminah@company.com"));
        listPerson.add(new Person("EMP003", "Rian Pratama", 1995, "Contract", "rian.pratama@company.com"));
        listPerson.add(new Person("EMP004", "Dewi Lestari", 1998, "Part-time", "dewi.lestari@company.com"));
        listPerson.add(new Person("EMP005", "Ahmad Fauzi", 2001, "Internship", "ahmad.fauzi@company.com"));
        listPerson.add(new Person("EMP006", "Nadia Putri", 1994, "Full-time", "nadia.putri@company.com"));
        listPerson.add(new Person("EMP007", "Rizky Ramadhan", 1999, "Contract", "rizky.ramadhan@company.com"));
        listPerson.add(new Person("EMP008", "Maya Indah", 2002, "Internship", "maya.indah@company.com"));
        listPerson.add(new Person("EMP009", "Hendra Wijaya", 1990, "Full-time", "hendra.wijaya@company.com"));
        listPerson.add(new Person("EMP010", "Clarissa Yun", 1996, "Part-time", "clarissa.yun@company.com"));
        listPerson.add(new Person("EMP011", "Vera Malika", 1992, "Contract", "veramalika@company.com"));
        listPerson.add(new Person("EMP012", "Farel Nabil", 2002, "Internship", "farelnabil@company.com"));

    }
}