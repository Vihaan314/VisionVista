import javax.swing.*;
import java.awt.event.ActionListener;
import java.text.Bidi;
import java.util.HashMap;
import java.util.Map;

public class MenuPanel {
    private JMenuBar menuBar;
    private Map<String, JMenuItem> menuItems = new HashMap<String, JMenuItem>();

    public MenuPanel() {
        menuBar = new JMenuBar();
    }

    public void addItemToMenu(String title, String menuItemTitle, ActionListener actionListener) {
        JMenu menu = getMenu(title); //Gets the sub-menu
        //If the sub-menu doesn't exist yet, it is created
        if (menu == null) {
            menu = new JMenu(title);
            menuBar.add(menu);
        }
        JMenuItem menuItem = new JMenuItem(menuItemTitle);
        menuItem.addActionListener(actionListener);
        menuItems.put(menuItemTitle, menuItem);
        menu.add(menuItem);
    }

    public JMenuItem getMenuItem(String menuItemTitle) {
        return menuItems.get(menuItemTitle);
    }

    public String getItemName(JMenuItem menuItem) {
        return "hi";
    }

    //Returns the specific sub-menu of a JMenu
    private JMenu getMenu(String title) {
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            if (menuBar.getMenu(i).getText().equals(title)) {
                return menuBar.getMenu(i);
            }
        }
        return null;
    }

    public JMenuBar getMenuBar() {
        return this.menuBar;
    }
}
