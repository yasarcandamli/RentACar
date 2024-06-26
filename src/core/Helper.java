package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setTheme() {
        optionPaneTR();
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }

    public static void showMessage(String string) {
        String message;
        String title;
        switch (string) {
            case "fill":
                message = "Lütfen Tüm Alanları Doldurunuz!";
                title = "Hata!";
                break;
            case "done":
                message = "İşlem Başarılı!";
                title = "Sonuç";
                break;
            case "notFound":
                message = "Kayıt bulunamadı !";
                title = "Bulunamadı";
                break;
            case "error":
                message = "Hatalı işlem yaptınız !";
                title = "Hata!";
                break;
            default:
                message = string;
                title = "Mesaj";
        }
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String string) {
        String message;
        if (string.equals("Sure")) {
            message = "Bu işlemi yapmak istediğine emin misin?";
        } else {
            message = string;
        }
        return JOptionPane.showConfirmDialog(null, message, "Emin misin?", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    public static int getLocationPoint(String type, Dimension size) {
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static void optionPaneTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }
}
