public class Person {
    private String id;
    private String nama;
    private String email;
    private int tahun;
    private String kategori;


    public Person(String id, String nama, int tahun, String kategori, String email) {
        this.id = id;
        this.nama = nama;
        this.tahun = tahun;
        this.kategori = kategori;
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public int getTahun() {
        return this.tahun;
    }

    public String getEmail() {
        return this.email;
    }

    public String getKategori() {
        return this.kategori;
    }
}