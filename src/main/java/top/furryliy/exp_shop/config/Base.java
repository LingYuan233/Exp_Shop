package top.furryliy.exp_shop.config;

public class Base {
    private int id;
    private String item;
    private int exp;
    private int count;

    public Base(int id, String item, int lvl, int count){
        this.id = id;
        this.item = item;
        this.exp = lvl;
        this.count = count;
    }

    public String getItem() {
        return item;
    }

    public int getId() {
        return id;
    }

    public int getExp() {
        return exp;
    }

    public int getCount() {
        return count;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
