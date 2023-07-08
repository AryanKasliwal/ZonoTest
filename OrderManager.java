import java.util.HashMap;

public class OrderManager {
    private static String ukRegex = "B\\d{3}[A-Za-z]{2}\\w{7}";
    private static String germanyRegex = "A[A-Za-z]{2}\\w{9}";
    private static OrderManager orderManagerInstance = null;
    private static HashMap <String, Country> countries = new HashMap<>();

    private OrderManager() {
        countries = resetCountries();
    }

    public static synchronized OrderManager getOrderManagerInstance () {
        if (orderManagerInstance == null) {
            orderManagerInstance = new OrderManager();
        }
        return orderManagerInstance;
    }

    public static String placeOrder(int numGloves, int numMasks, String purchaseCountry, String otherCountry, String passportNum) {
        int totalPrice = 0;
        if (numGloves > 150 || numMasks > 200) {
            countries = resetCountries();
            return "OUT OF STOCK:100:100:100:50";
        }
        else {
            while (numGloves > 0 || numMasks > 0) {
                if (numGloves > 0) {
                    int currentGloveOrder = Math.min(10, numGloves);
                    int tentativeGlovePrice1 = countries.get(purchaseCountry).getPrice("Gloves", currentGloveOrder, passportNum, false);
                    int tentativeGlovePrice2 = countries.get(otherCountry).getPrice("Gloves", currentGloveOrder, passportNum, true);
                    if (tentativeGlovePrice1 > tentativeGlovePrice2) {
                        int availableGloves = countries.get(otherCountry).getGloves();
                        if (availableGloves == 0) {
                            totalPrice += countries.get(purchaseCountry).placeOrder("Gloves", currentGloveOrder, passportNum, false);
                            numGloves -= currentGloveOrder;
                        }
                        else if (availableGloves < currentGloveOrder) {
                            tentativeGlovePrice1 = countries.get(purchaseCountry).getPrice("Gloves", availableGloves, passportNum, false);
                            tentativeGlovePrice2 = countries.get(otherCountry).getPrice("Gloves", availableGloves, passportNum, true);
                            if (tentativeGlovePrice1 <= tentativeGlovePrice2) {
                                totalPrice += countries.get(purchaseCountry).placeOrder("Gloves", currentGloveOrder, passportNum, false);
                                numGloves -= currentGloveOrder;
                            }
                            else {
                                totalPrice += countries.get(otherCountry).placeOrder("Gloves", availableGloves, passportNum, true);
                                numGloves -= availableGloves;
                            }
                        }
                        else {
                            totalPrice += countries.get(otherCountry).placeOrder("Gloves", currentGloveOrder, passportNum, true);
                            numGloves -= currentGloveOrder;
                        }
                    }
                    else {
                        int availableGloves = countries.get(purchaseCountry).getGloves();
                        if (availableGloves == 0) {
                            totalPrice += countries.get(otherCountry).placeOrder("Gloves", currentGloveOrder, passportNum, true);
                            numGloves -= currentGloveOrder;
                        }
                        else if (availableGloves < currentGloveOrder) {
                            tentativeGlovePrice1 = countries.get(purchaseCountry).getPrice("Gloves", availableGloves, passportNum, false);
                            tentativeGlovePrice2 = countries.get(otherCountry).getPrice("Gloves", availableGloves, passportNum, true);
                            if (tentativeGlovePrice1 <= tentativeGlovePrice2) {
                                totalPrice += countries.get(purchaseCountry).placeOrder("Gloves", availableGloves, passportNum, false);
                                numGloves -= availableGloves;
                            }
                            else {
                                totalPrice += countries.get(otherCountry).placeOrder("Gloves", currentGloveOrder, passportNum, true);
                                numGloves -= currentGloveOrder;
                            }
                        }
                        else {
                            totalPrice += countries.get(purchaseCountry).placeOrder("Gloves", currentGloveOrder, passportNum, false);
                            numGloves -= currentGloveOrder;
                        }
                    }
                }
                if (numMasks > 0) {
                    int currentMaskOrder = Math.min(10, numMasks);
                    int tentativeMaskPrice1 = countries.get(purchaseCountry).getPrice("Mask", currentMaskOrder, passportNum, false);
                    int tentativeMaskPrice2 = countries.get(otherCountry).getPrice("Mask", currentMaskOrder, passportNum, true);
                    if (tentativeMaskPrice1 > tentativeMaskPrice2) {
                        int availableMasks = countries.get(otherCountry).getMask();
                        if (availableMasks == 0) {
                            totalPrice += countries.get(purchaseCountry).placeOrder("Mask", currentMaskOrder, passportNum, false);
                            numMasks -= currentMaskOrder;
                        }
                        else if (availableMasks < currentMaskOrder) {
                            tentativeMaskPrice1 = countries.get(purchaseCountry).getPrice("Mask", availableMasks, passportNum, false);
                            tentativeMaskPrice2 = countries.get(otherCountry).getPrice("Mask", availableMasks, passportNum, true);
                            if (tentativeMaskPrice1 <= tentativeMaskPrice2) {
                                totalPrice += countries.get(purchaseCountry).placeOrder("Mask", currentMaskOrder, passportNum, false);
                                numMasks -= currentMaskOrder;
                            }
                            else {
                                totalPrice += countries.get(otherCountry).placeOrder("Mask", availableMasks, passportNum, true);
                                numMasks -= availableMasks;
                            }
                        }
                        else {
                            totalPrice += countries.get(otherCountry).placeOrder("Mask", currentMaskOrder, passportNum, true);
                            numMasks -= currentMaskOrder;
                        }
                    }
                    else {
                        int availableMasks = countries.get(purchaseCountry).getMask();
                        if (availableMasks == 0) {
                            totalPrice += countries.get(otherCountry).placeOrder("Mask", currentMaskOrder, passportNum, true);
                            numMasks -= currentMaskOrder;
                        }
                        else if (availableMasks < currentMaskOrder) {
                            tentativeMaskPrice1 = countries.get(purchaseCountry).getPrice("Mask", availableMasks, passportNum, false);
                            tentativeMaskPrice2 = countries.get(otherCountry).getPrice("Mask", availableMasks, passportNum, true);
                            if (tentativeMaskPrice1 <= tentativeMaskPrice2) {
                                totalPrice += countries.get(purchaseCountry).placeOrder("Mask", availableMasks, passportNum, false);
                                numMasks -= availableMasks;
                            }
                            else {
                                totalPrice += countries.get(otherCountry).placeOrder("Mask", currentMaskOrder, passportNum, true);
                                numMasks -= currentMaskOrder;
                            }
                        }
                        else {
                            totalPrice += countries.get(purchaseCountry).placeOrder("Mask", currentMaskOrder, passportNum, false);
                            numMasks -= currentMaskOrder;
                        }
                    }
                }
            }
            // int availableGloves = countries.get(purchaseCountry).getGloves();
            // int availableMasks = countries.get(purchaseCountry).getMask();

            // if (numGloves <= availableGloves) {
            //     totalPrice += countries.get(purchaseCountry).placeOrder("Gloves", numGloves, passportNum, false);
            // }
            // else {
            //     totalPrice += countries.get(purchaseCountry).placeOrder("Gloves", availableGloves, passportNum, false);
            //     totalPrice += countries.get(otherCountry).placeOrder("Gloves", numGloves - availableGloves, passportNum, true);
            // }
            // if (numMasks <= availableMasks) {
            //     totalPrice += countries.get(purchaseCountry).placeOrder("Mask", numMasks, passportNum, false);
            // }
            // else {
            //     totalPrice += countries.get(purchaseCountry).placeOrder("Mask", availableMasks, passportNum, false);
            //     totalPrice += countries.get(otherCountry).placeOrder("Mask", numMasks - availableMasks, passportNum, true);
            // }
        }
        String result = totalPrice + ":" + countries.get("UK").getMask() + ":" + countries.get("Germany").getMask() + ":" + countries.get("UK").getGloves() + ":" + countries.get("Germany").getGloves();
        countries = resetCountries();
        return result;
    }


    private static Country resetUK() {
        Country uk = new Country("UK", ukRegex);
        int masks = 100;
        int maskPrice = 65;
        int gloves = 100;
        int glovePrice = 100;
        uk.setMasks(masks);
        uk.setMaskPrice(maskPrice);
        uk.setGloves(gloves);
        uk.setGlovePrice(glovePrice);
        return uk;
    }

    private static Country resetGermany() {
        Country germany = new Country("Germany", germanyRegex);
        int masks = 100;
        int maskPrice = 100;
        int gloves = 50;
        int glovePrice = 150;
        germany.setMasks(masks);
        germany.setMaskPrice(maskPrice);
        germany.setGloves(gloves);
        germany.setGlovePrice(glovePrice);
        return germany;
    }

    private static HashMap<String, Country> resetCountries() {
        HashMap <String, Country> countries = new HashMap<>();
        countries.put("UK", resetUK());
        countries.put("Germany", resetGermany());
        return countries;
    }
}
