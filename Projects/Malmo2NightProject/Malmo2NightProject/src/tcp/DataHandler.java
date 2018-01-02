package tcp;

import java.util.ArrayList;

public class DataHandler {
    private String[] utestallenNamnLista;
    private String[] utestallenAddressLista;
    private String[] utestallenAldersgransLista;
    private String[] utestallenTypLista;
    private String[] utestalleLatitud;
    private String[] utestalleLongitud;
    private String[] systembolagetNamnLista;
    private String[] systembolagetAddressLista;
    private String[] systembolagetTelefonLista;
    private String[] systembolagetOppettiderLankLista;
    private String[] systembolagetLatitud;
    private String[] systembolagetLongitud;
    private String[] taxiNamnLista;
    private String[] taxiTelefonLista;
    private String[] taxiHemsidaLista;
    private String[] drinkreceptNamnLista;
    private String[] drinkreceptInstruktionLista;
    private String[] drinkreceptKategorierLista;
    private String[] olspelNamnLista;
    private String[] olspelInstruktionLista;
    private String[] olspelAntalspelareLista;
    private String[] olspelKravLista;
    private Object[] utestalleLogoLista;

    // Metoder för att importera data från servern
    public void setUtestallenNamnLista(String[] data) {
        this.utestallenNamnLista = data;
    }

    public void setUtestallenAddressLista(String[] data) {
        this.utestallenAddressLista = data;
    }

    public void setUtestallenAldergransLista(String[] data) {
        this.utestallenAldersgransLista = data;
    }

    public void setUtestallenTypLista(String[] data) {
        this.utestallenTypLista = data;
    }

    public void setUtestallenLatitud(String[] data) {
        this.utestalleLatitud = data;
    }

    public void setUtestallenLongitud(String[] data) {
        this.utestalleLongitud = data;
    }

    public void setSystembolagetNamnLista(String[] data) {
        this.systembolagetNamnLista = data;
    }

    public void setSystembolagetAddressLista(String[] data) {
        this.systembolagetAddressLista = data;
    }

    public void setSystembolageTelefonLista(String[] data) {
        this.systembolagetTelefonLista = data;
    }

    public void setSystembolagetOppettiderLankLista(String[] data) {
        this.systembolagetOppettiderLankLista = data;
    }

    public void setSystembolagetLatitud(String[] data) {
        this.systembolagetLatitud = data;
    }

    public void setSystembolagetLongitud(String[] data) {
        this.systembolagetLongitud = data;
    }

    public void setTaxiNamnLista(String[] data) {
        this.taxiNamnLista = data;
    }

    public void setTaxiTelefonLista(String[] data) {
        this.taxiTelefonLista = data;
    }

    public void setTaxiHemsidaLista(String[] data) {
        this.taxiHemsidaLista = data;
    }

    public void setDrinkreceptNamnLista(String[] data) {
        this.drinkreceptNamnLista = data;
    }

    public void setDrinkreceptInstruktionLista(String[] data) {
        this.drinkreceptInstruktionLista = data;
    }

    public void setDrinkreceptKategoriLista(String[] data) {
        this.drinkreceptKategorierLista = data;
    }

    public void setOlspelNamnLista(String[] data) {
        this.olspelNamnLista = data;
    }

    public void setOlspelInstruktionLista(String[] data) {
        this.olspelInstruktionLista = data;
    }

    public void setOlspelAntalSpelareLista(String[] data) {
        this.olspelAntalspelareLista = data;
    }

    public void setOlspelKravLista(String[] data) {
        this.olspelKravLista = data;
    }

    public void setUtestalleLogoLista(Object[] data) {
        this.utestalleLogoLista = data;
    }

    // Metoder för att hämta data
    public String[] getUtestallenNamnLista() {
        return utestallenNamnLista;
    }

    public String[] getUtestallenAddressLista() {
        return utestallenAddressLista;
    }

    public String[] getUtestallenAldergransLista() {
        return utestallenAldersgransLista;
    }

    public String[] getUtestallenTypLista() {
        return utestallenTypLista;
    }

    public String[] getUtestallenLatitud() {
        return utestalleLatitud;
    }

    public String[] getUtestallenLongitud() {
        return utestalleLongitud;
    }

    public String[] getSystembolagetNamnLista() {
        return systembolagetNamnLista;
    }

    public String[] getSystembolagetAddressLista() {
        return systembolagetAddressLista;
    }

    public String[] getSystembolageTelefonLista() {
        return systembolagetTelefonLista;
    }

    public String[] getSystembolagetOppettiderLankLista() {
        return systembolagetOppettiderLankLista;
    }

    public String[] getSystembolagetLatitud() {
        return systembolagetLatitud;
    }

    public String[] getSystembolagetLongitud() {
        return systembolagetLongitud;
    }

    public String[] getTaxiNamnLista() {
        return taxiNamnLista;
    }

    public String[] getTaxiTelefonLista() {
        return taxiTelefonLista;
    }

    public String[] getTaxiHemsidaLista() {
        return taxiHemsidaLista;
    }

    public String[] getDrinkreceptNamnLista() {
        return drinkreceptNamnLista;
    }

    public String[] getDrinkreceptInstruktionLista() {
        return drinkreceptInstruktionLista;
    }

    public String[] getDrinkreceptKategoriLista() {
        return drinkreceptKategorierLista;
    }

    public String[] getOlspelNamnLista() {
        return olspelNamnLista;
    }

    public String[] getOlspelInstruktionLista() {
        return olspelInstruktionLista;
    }

    public String[] getOlspelAntalSpelareLista() {
        return olspelAntalspelareLista;
    }

    public String[] getOlspelKravLista() {
        return olspelKravLista;
    }

    public Object[] getUtestalleLogoLista() {
        return utestalleLogoLista;
    }

    public int[] searchForUtestalle(String searchText) {
        int countSearchValues = 0;
        String lowerCaseSearchText = searchText.toLowerCase();
        // Räknar hur stor returarrayen ska vara som innehåller uteställe ID.
        for (int i = 0; i < utestallenNamnLista.length; i++) {
            String utestalleNamn = utestallenNamnLista[i].toLowerCase();
            if (utestalleNamn.contains(lowerCaseSearchText)) {
                countSearchValues++;
            }
        }
        // Om inga resultat hittas returneras -1
        if (countSearchValues == 0) {
            int[] searchValues = new int[1];
            searchValues[0] = -1;
            return searchValues;
        } else {
            int[] searchValues = new int[countSearchValues];
            int counter = 0;
            // Skriver in uteställe-ID i returarrayen.
            for (int i = 0; i < utestallenNamnLista.length; i++) {
                String utestalleNamn = utestallenNamnLista[i].toLowerCase();
                if (utestalleNamn.contains(lowerCaseSearchText)) {
                    searchValues[counter] = i;
                    counter++;
                }
            }
            return searchValues;
        }
    }

    public int[] searchForDrinkRecept(String searchText) {
        int countSearchValues = 0;
        String lowerCaseSearchText = searchText.toLowerCase();
        // Räknar hur stor returarrayen ska vara som innehåller uteställe ID.
        for (int i = 0; i < utestallenNamnLista.length; i++) {
            String utestalleNamn = utestallenNamnLista[i].toLowerCase();
            if (utestalleNamn.contains(lowerCaseSearchText)) {
                countSearchValues++;
            }
        }
        // Om inga resultat hittas returneras -1
        if (countSearchValues == 0) {
            int[] searchValues = new int[1];
            searchValues[0] = -1;
            return searchValues;
        } else {
            int[] searchValues = new int[countSearchValues];
            int counter = 0;
            // Skriver in uteställe-ID i returarrayen.
            for (int i = 0; i < drinkreceptNamnLista.length; i++) {
                String utestalleNamn = drinkreceptNamnLista[i].toLowerCase();
                if (utestalleNamn.contains(lowerCaseSearchText)) {
                    searchValues[counter] = i;
                    counter++;
                }
            }
            return searchValues;
        }
    }

    public int[] searchForOlspel(String searchText) {
        int countSearchValues = 0;
        String lowerCaseSearchText = searchText.toLowerCase();
        // Räknar hur stor returarrayen ska vara som innehåller uteställe ID.
        for (int i = 0; i < utestallenNamnLista.length; i++) {
            String utestalleNamn = utestallenNamnLista[i].toLowerCase();
            if (utestalleNamn.contains(lowerCaseSearchText)) {
                countSearchValues++;
            }
        }
        // Om inga resultat hittas returneras -1
        if (countSearchValues == 0) {
            int[] searchValues = new int[1];
            searchValues[0] = -1;
            return searchValues;
        } else {
            int[] searchValues = new int[countSearchValues];
            int counter = 0;
            // Skriver in uteställe-ID i returarrayen.
            for (int i = 0; i < olspelNamnLista.length; i++) {
                String utestalleNamn = olspelNamnLista[i].toLowerCase();
                if (utestalleNamn.contains(lowerCaseSearchText)) {
                    searchValues[counter] = i;
                    counter++;
                }
            }
            return searchValues;
        }
    }


    public String[] filterUtestallen(String typ, int searchAlder, int sortBy) {

        ArrayList templist = new ArrayList();

        if (!typ.equals("-1") && searchAlder != -1) { // Räknar hur många
            // uteställen det finns
            // med båda filterna
            // incheckade
            for (int i = 0; i < getUtestallenAldergransLista().length; i++) {
                int alder = Integer.parseInt(getUtestallenAldergransLista()[i]);
                if (alder >= searchAlder
                        && getUtestallenTypLista()[i].equals(typ)) {
                    templist.add(getUtestallenNamnLista()[i]);
                }
            }
        } else if (!typ.equals("-1")) { // Räknar hur många uteställen det finns
            // med typ incheckad
            for (int i = 0; i < getUtestallenAldergransLista().length; i++) {
                int alder = Integer.parseInt(getUtestallenAldergransLista()[i]);
                if (getUtestallenTypLista()[i].equals(typ)) {
                    templist.add(getUtestallenNamnLista()[i]);
                }
            }
        } else {// Räknar hur många uteställen det finns med ålder incheckad och
            // ifylld
            for (int i = 0; i < getUtestallenAldergransLista().length; i++) {
                int alder = Integer.parseInt(getUtestallenAldergransLista()[i]);
                if (alder >= searchAlder) {
                    templist.add(getUtestallenNamnLista()[i]);
                }
            }
        }
        String[] arr = new String[templist.size()];
        for (int i = 0; i < templist.size(); i++) {
            arr[i] = templist.get(i).toString();
            System.out.println(arr[i]);
        }

        return sortUtestallen(arr, sortBy);
    }

    // sortBy: 0=name, 1=age, 2=distance, 3=grade
    public String[] sortUtestallen(String[] arr, int sortBy) {

        switch (sortBy) {
            case 0:
                System.out.println("Namesort");
                break;
            case 1:
                System.out.println("Agesort");
                break;
            case 2:
                System.out.println("Distsort");
                break;
            case 3:
                System.out.println("Gradesort");
                break;
            default:
                System.out.println("Something's wrong with the switch-case in DataHandler!");
                break;
        }

        return arr;
    }

    public int describeContents() {
        return 0;
    }
}
