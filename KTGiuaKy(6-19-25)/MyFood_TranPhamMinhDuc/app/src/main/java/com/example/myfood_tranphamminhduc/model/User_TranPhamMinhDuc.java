package com.example.myfood_tranphamminhduc.model;

public class User_TranPhamMinhDuc {

    public User_TranPhamMinhDuc() {}

    public User_TranPhamMinhDuc(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }
    public User_TranPhamMinhDuc(String user, String pass, String rePass) {
        this.user = user;
        this.pass = pass;
        this.rePass = rePass;
    }
    private String user;
    private String pass;

    public String getRePass() {
        return rePass;
    }

    public void setRePass(String rePass) {
        this.rePass = rePass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getUser() {
        return user;
    }


    public void setUser(String user) {
        this.user = user;
    }

    private String rePass;

}

