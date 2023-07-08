public class Country {
    private String name;
    private String passportRegex;
    private int masks;
    private int gloves;
    private int maskPrice;
    private int glovePrice;

    public Country(String name, String passportRegex) {
        this.name = name;
        this.passportRegex = passportRegex;

    }

    public void setMasks(int masks) {
        this.masks = masks;
    }

    public void setGloves(int gloves) {
        this.gloves = gloves;
    }

    public void setMaskPrice(int maskPrice) {
        this.maskPrice = maskPrice;
    }

    public void setGlovePrice(int glovePrice) {
        this.glovePrice = glovePrice;
    }

    public int getGloves() {
        return this.gloves;
    }

    public int getMask() {
        return this.masks;
    }

    public int placeOrder(String item, int quantity, String passportNum, boolean export) {
        int price = 0;
        if (item.equals("Mask")) {
            price += quantity * this.maskPrice;
            this.masks -= quantity;
        }
        else {
            price += quantity * this.glovePrice;
            this.gloves -= quantity;
        }

        if (export) {
            if (passportNum.matches(this.passportRegex)) {
                price += 320 * Math.ceil((double) quantity / 10);
            }
            else {
                price += 400 * Math.ceil((double) quantity / 10);
            }
        }
        return price;
    }

    public int getPrice(String item, int quantity, String passportNum, boolean export) {
        int price = 0;
        if (item.equals("Mask")) {
            price += quantity * this.maskPrice;
        }
        else {
            price += quantity * this.glovePrice;
        }

        if (export) {
            if (passportNum.matches(this.passportRegex)) {
                price += 320 * Math.ceil((double) quantity / 10);
            }
            else {
                price += 400 * Math.ceil((double) quantity / 10);
            }
        }
        return price;
    }
}
