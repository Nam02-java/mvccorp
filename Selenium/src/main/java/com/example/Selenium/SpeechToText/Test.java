package com.example.Selenium.SpeechToText;

interface DongVat {
    public static final String mau_long = "Vang";

    public void hanhDong();

    //  public void chucNang();

    public static void ancut() {
        System.out.println("oke");
    }
}

abstract class SucVat {

    String maulon = "maulon";

    void test() {
        System.out.println("ngu");
    }

    public String getColor(){
        return maulon;
    }


    abstract void noi();
}

class Meo extends SucVat{

    @Override
    void noi() {
        System.out.println("meo meo"+
        getColor());
    }
}

class testtorun{
    public static void main(String[] args) {
        Meo s = new Meo();
        s.test();
        s.noi();
    }
}