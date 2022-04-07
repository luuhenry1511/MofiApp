package tdmu.edu.vn.mofi.modals;


public class Khoantien  {
    private String khoanthukhoanchi;
    private String phanloai;
    private String user;
    public Khoantien(String khoanthukhoanchi, String phanloai, String user) {
        this.khoanthukhoanchi = khoanthukhoanchi;
        this.phanloai = phanloai;
        this.user = user;
    }
    public Khoantien(){

    }
    public String getKhoanthukhoanchi() {
        return khoanthukhoanchi;
    }

    public void setKhoanthukhoanchi(String khoanthukhoanchi) {
        this.khoanthukhoanchi = khoanthukhoanchi;
    }

    public String getPhanloai() {
        return phanloai;
    }

    public void setPhanloai(String phanloai) {
        this.phanloai = phanloai;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
