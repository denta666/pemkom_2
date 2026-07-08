package utility;

import javax.swing.JComboBox;
import java.util.Locale;

public class LanguageComboBox extends JComboBox<String> {

    private final String[] languageCodes = {"id", "en", "pt"};
    private final String[] languageLabels = {"Indonesia", "English", "Português"};

    public LanguageComboBox() {
        super();
        for (String label : languageLabels) {
            addItem(label);
        }

        // Set pilihan combobox sesuai locale aktif saat ini
        String currentCode = LanguageManager.getCurrentLocale().getLanguage();
        for (int i = 0; i < languageCodes.length; i++) {
            if (languageCodes[i].equals(currentCode)) {
                setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * Pasang listener yang akan dipanggil setiap kali bahasa diganti.
     * @param onLanguageChanged callback untuk refresh teks di halaman
     */
    public void onLanguageChange(Runnable onLanguageChanged) {
        addActionListener(e -> {
            int index = getSelectedIndex();
            LanguageManager.setLocale(new Locale(languageCodes[index]));
            onLanguageChanged.run();
        });
    }
}