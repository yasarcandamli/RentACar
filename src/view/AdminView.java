package view;

import business.BrandManager;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scrl_brand;
    private JTable tbl_brand;
    private User user;
    private DefaultTableModel table_mdl_brand;
    private BrandManager brandManager;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.add(container);
        this.guiInitialize(1000, 500);
        this.user = user;
        if (this.user == null) dispose();

        this.lbl_welcome.setText("Hoşgeldiniz: " + this.user.getUsername());
    }

    Object[] col_brand = {"Marka ID", "Marka Adı"};
}
