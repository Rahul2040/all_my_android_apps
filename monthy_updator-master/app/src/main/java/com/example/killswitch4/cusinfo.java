package com.example.killswitch4;

public class cusinfo {
    public String name,smc,cusid,acc;
    public int ob,pack,addon,total,cash,bal;
    public cusinfo(String cusid, String name, String smc, int ob, int pack, int addon, String acc) {
        this.name = name;
        this.cusid= cusid;
        this.acc=acc;
        this.addon=addon;
        this.ob=ob;
        this.pack=pack;
        this.smc=smc;
        this.total=ob+pack+addon;
        this.cash=0;
        this.bal=total;

    }
    public cusinfo(String cusid, String name, String smc, int ob, int pack, int addon, int cash, String acc) {
        this.name = name;
        this.cusid= cusid;
        this.acc=acc;
        this.addon=addon;
        this.ob=ob;
        this.pack=pack;
        this.smc=smc;
        this.total=ob+pack+addon;
        this.cash=cash;
        this.bal=total-cash;

    }
    public cusinfo(){}

    public String getSmc() {
        return smc;
    }

    public void setSmc(String smc) {
        this.smc = smc;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public int getOb() {
        return ob;
    }

    public void setOb(int ob) {
        this.ob = ob;
    }

    public int getPack() {
        return pack;
    }

    public void setPack(int pack) {
        this.pack = pack;
    }

    public int getAddon() {
        return addon;
    }

    public void setAddon(int addon) {
        this.addon = addon;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getBal() {
        return bal;
    }

    public void setBal(int bal) {
        this.bal = bal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String fetchcusinfo(int a){
        String r="";
        if(a==0) {
            r += "CusID:   " + cusid + "\n";
            r += "Name:   " + name + "\n";
            r += "SMC:      " + smc.replaceAll("Y","E")+ "\n";
            r += "OB:         " + ob + "\n";
            r += "Pack:     " + pack + "\n";
            r += "Addon:  " + addon + "\n";
            r += "Total:     " + total + "\n";
            r += "Cash:      " + cash + "\n";
            r += "Bal:        " + bal + "\n";
            r += "Acc:       " + acc + "\n";
            return r;
        }
        else{
            r += cusid + ",";
            r += name + ",";
            r += smc + ",";
            r += ob + ",";
            r += pack + ",";
            r += addon + ",";
            r +=  total + ",";
            r +=  cash + ",";
            r +=  bal + ",";
            r +=  acc + "\n";
            return r;
        }

    }




}
