import javax.swing.*;

/**
 * Created by jaco on 10/4/16.
 *
 */
public class PresenterUI {

    private JFrame frame;

    public PresenterUI() {

    }

    private void createComponents() {
        //Create the frame.
        frame = new JFrame("A window");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (UnsupportedLookAndFeelException e) {
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //TODO: Remove this as process needs to get killed by master
        frame.setSize(800, 800);

        JPanel panel = new JPanel();
        frame.add(panel);
        frame.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JComponent tc1 = makeTextPanel("TC1");
        tabbedPane.addTab("Tab 1", tc1);
        //tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        JComponent tc2 = makeTextPanel("TC2");
        tabbedPane.addTab("Tab 2", tc2);
        //tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        JComponent tc3 = makeTextPanel("TC3");
        tabbedPane.addTab("Tab 3", tc3);
        //tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        JComponent tc4 = makeTextPanel("TC4");
        tabbedPane.addTab("Tab 4", tc4);
        //tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        panel.add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);


    }

    private JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        panel.setLayout(null);
        panel.add(filler);
        panel.setSize(200,800);
        return panel;
    }

    public void createAndShow() {
        createComponents();
        this.frame.setVisible(true);
    }
}
