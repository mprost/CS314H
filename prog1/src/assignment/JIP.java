package assignment;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;


/**
  * This class provides a GUI interface and handles all sorts of low-level details.
  */
public class JIP extends JFrame implements ActionListener {
    public static void main(String[] args) {
        (new JIP()).setVisible(true);
    }

    public JIP() {
        effectMap = new HashMap<>();
        setupGUI();
    }

    protected void setupGUI() {
        // setup Frame elements
        setTitle("Java Image Processor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);
        menuItemOpenImage = new JMenuItem("Open image...");
        menuItemOpenImage.setMnemonic(KeyEvent.VK_O);
        menuItemOpenImage.addActionListener(this);
        menu.add(menuItemOpenImage);
        menuItemSaveImage = new JMenuItem("Save image...");
        menuItemSaveImage.setMnemonic(KeyEvent.VK_S);
        menuItemSaveImage.addActionListener(this);
        menu.add(menuItemSaveImage);
        menuItemExit = new JMenuItem("Exit");
        menuItemExit.setMnemonic(KeyEvent.VK_X);
        menuItemExit.addActionListener(this);
        menu.add(menuItemExit);
        effectsMenu = new JMenu("Effects");
        effectsMenu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(effectsMenu);

        // add known ImageEffects to effects menu
        for(ImageEffect ie : getFilters().values()) {
            addEffect(ie);
        }

        // add component to display image
        imagePanel = new JImagePanel();
        getContentPane().add(imagePanel);
        pack();

        // setup open file dialog
        String[] formats = ImageIO.getReaderFormatNames();
        int unique = 0, remaining = formats.length;
openOuter:
        for (; unique < remaining; unique++) {
            formats[unique] = formats[unique].toLowerCase();
            for (int j = 0; j < unique; j++) {
                if (formats[j].equals(formats[unique])) {
                    formats[unique--] = formats[--remaining];
                    continue openOuter;
                }
            }
        }
        Arrays.sort(formats, 0, unique);
        final String[] fReadableFormats = formats;
        final int fRUnique = unique;

        StringBuffer desc = new StringBuffer("Image files  [");
        if (fRUnique == 0) {
            desc.append("N/A");
        } else {
            desc.append(fReadableFormats[0].toUpperCase());
            for (int i = 1; i < fRUnique; i++) {
                desc.append(", " + fReadableFormats[i].toUpperCase());
            }
        }
        desc.append(']');
        final String fDesc = desc.toString();

        openFileChooser = new JFileChooser(".");
        openFileChooser.setDialogTitle("Open Image");
        openFileChooser.addChoosableFileFilter(new FileFilter() {
            public boolean accept(File file) {
                if (file.isDirectory()) {
                     return true;
                }

                String filenameExtension = getExtension(file).toLowerCase();
                for (int i = 0; i < fRUnique; i++) {
                    if (filenameExtension.equals(fReadableFormats[i])) {
                        return true;
                    }
                }
                return false;
            }
            public String getDescription() {
                return fDesc;
            }
        });

        // setup save file dialog
        formats = ImageIO.getWriterFormatNames();
        unique = 0;
        remaining = formats.length;
saveOuter:
        for (; unique < remaining; unique++) {
            formats[unique] = formats[unique].toLowerCase();
            for (int j = 0; j < unique; j++) {
                if (formats[j].equals(formats[unique])) {
                    formats[unique--] = formats[--remaining];
                    continue saveOuter;
                }
            }
        }
        Arrays.sort(formats, 0, unique);
        writableFormats = new String[unique];
        for (int i = 0; i < unique; i++) {
            writableFormats[i] = formats[i];
        }
        final String[] fWritableFormats = writableFormats;
        final int fWUnique = unique;

        saveFileChooser = new JFileChooser(".");
        saveFileChooser.setDialogTitle("Save Image");
        saveFileChooser.setAcceptAllFileFilterUsed(false);
        for (int i = 0; i < fWUnique; i++) {
            final int j = i;
            saveFileChooser.addChoosableFileFilter(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isDirectory()) {
                        return true;
                    }

                    String filenameExtension = getExtension(file).toLowerCase();
                    if (filenameExtension.equals(fWritableFormats[j])) {
                        return true;
                    }
                    return false;
                }
                public String getDescription() {
                    return fWritableFormats[j].toUpperCase();
                }
            });
        }
    }

    public void addEffect(ImageEffect ie) {
        JMenuItem menuItem = new JMenuItem(ie.getDescription());
        menuItem.addActionListener(this);

        int pos = 0;
        try {
            while (pos < effectsMenu.getMenuComponentCount() && ie.getDescription().compareTo(((JMenuItem)effectsMenu.getMenuComponent(pos)).getText()) >= 0) {
                    pos++;
            }
        } catch (ClassCastException e) {
        }

        effectsMenu.add(menuItem, pos);
        effectMap.put(menuItem, ie);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuItemOpenImage) {
            promptOpenImage();
            repaint();
        } else if (e.getSource() == menuItemSaveImage) {
            promptSaveImage();
        } else if (e.getSource() == menuItemExit) {
            System.exit(0);
        } else {
            ImageEffect effect = (ImageEffect) effectMap.get(e.getSource());
            ArrayList<ImageEffectParam> params = effect.getParameters();
            if (params != null) {
                try {
                    for (ImageEffectParam param : params) {
                        Object input = null;
                        do {
                            input = JOptionPane.showInputDialog(null,
                                    param.getDescription(), param.getName(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null, null, param.getDefaultValue());
                        } while(!parseAndVerifyInput(input.toString(),
                                param));
                    }
                } catch(NullPointerException ex) {
                    // This is caused by a window being closed
                    return;
                }
            }
            BufferedImage img = (BufferedImage)imagePanel.getBackgroundImage();
            if (effect != null && img  != null) {
                BufferedImage newImg = effect.apply(img, params);
                imagePanel.setBackgroundImage(newImg);
                if (newImg != null && (newImg.getWidth() != img.getWidth() ||
                        newImg.getHeight() != img.getHeight())) {
                    pack();
                } else {
                    repaint();
                }
            }
        }
    }

    public void promptOpenImage() {
        int action = openFileChooser.showOpenDialog(this);
        if (action != JFileChooser.APPROVE_OPTION) {
            return;
        }
        BufferedImage img = createBufferedImage(openFileChooser.getSelectedFile());
        if (img == null) {
            JOptionPane.showMessageDialog(this, "Error opening " + openFileChooser.getSelectedFile(), "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            imagePanel.setBackgroundImage(img);
            pack();
        }
    }

    public void promptSaveImage() {
        int action = saveFileChooser.showSaveDialog(this);
        if (action != JFileChooser.APPROVE_OPTION || imagePanel.getBackgroundImage() == null) {
            return;
        }
        File file = saveFileChooser.getSelectedFile();
        String format = getExtension(file).toLowerCase();
        int i = 0;
        for (; i < writableFormats.length; i++) {
            if (format.equals(writableFormats[i])) {
                break;
            }
        }
        if (i == writableFormats.length) {
            format = saveFileChooser.getFileFilter().getDescription().toLowerCase();
            file = new File(saveFileChooser.getCurrentDirectory(), file.getName() + "." + format);
        }

        try {
            if (!ImageIO.write((BufferedImage)imagePanel.getBackgroundImage(), format, file)) {
                JOptionPane.showMessageDialog(this, "Error saving " + file, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving " + file, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public BufferedImage createBufferedImage(File file) {
        BufferedImage img;
        try {
            img = ImageIO.read(file);
        } catch (Exception e) {
            return null;
        }
        if (img.getType() == BufferedImage.TYPE_INT_RGB) {
            return img;
        }
        BufferedImage nImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        nImg.createGraphics().drawImage(img, 0, 0, null);
        return nImg;
    }

    public static boolean parseAndVerifyInput(String input,
                                              ImageEffectParam param) {
        int value = -1;
        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            showErrorMessage("Invalid integer format.");
            return false;
        }

        if (value > param.getMaxValue() ||
            value < param.getMinValue()) {
            showErrorMessage("Integer value out of allowed range." +
                             " Min: " + param.getMinValue() +
                             " Max: " + param.getMaxValue());
            return false;
        }

        param.setValue(value);
        return true;
    }

    private static void showErrorMessage(String description) {
        JOptionPane.showMessageDialog(null, description, "ERROR",
            JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Kind of messy method, but it populates the HashMap effects with all the ImageEffects
     * it can find in the class paths given.
     */
    public static HashMap<String, ImageEffect> getFilters() {
        String s = ImageEffect.class.getResource(ImageEffect.class.getSimpleName() + ".class").getFile();
        s = s.substring(0, s.lastIndexOf(ImageEffect.class.getSimpleName()) - 1);
        String[] searchPaths = s.split(File.pathSeparator);
        HashMap<String, ImageEffect> effects = new HashMap<>();

        for(int i = 0; i < searchPaths.length; i++) {
            File path = new File(searchPaths[i]);

            if (!path.isDirectory()) {
                continue;
            }

            File[] classFiles = path.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    if(name.endsWith(".class")) {
                        return true;
                    }
                    return false;
                }
            });
            for(int j = 0; j < classFiles.length; j++) {
                String className = getBase(classFiles[j]);
                try {
                    Class<?> c = Class.forName(ImageEffect.class.getPackage().getName() + "." + className);
                    if(ImageEffect.class.isAssignableFrom(c)) {
                        Constructor construct = c.getDeclaredConstructor();
                        construct.setAccessible(true);
                        effects.put(className, (ImageEffect) construct.newInstance());
                    }
                } catch(Exception e) {
                }
            }
        }
        return effects;
    }

    private static String getExtension(File file) {
        if (file == null || file.isDirectory()) {
            return "";
        }

        int index = file.getName().lastIndexOf('.');
        if (index == -1) {
            return "";
        }
        return file.getName().substring(index + 1);
    }

    private static String getBase(File file) {
        if (file == null || file.isDirectory()) {
            return "";
        }

        int index = file.getName().lastIndexOf('.');
        if (index == -1) {
            return file.getName();
        }
        return file.getName().substring(0, index);
    }

    private JMenu effectsMenu;
    private JMenuItem menuItemOpenImage;
    private JMenuItem menuItemSaveImage;
    private JMenuItem menuItemExit;
    private JImagePanel imagePanel;
    private JFileChooser openFileChooser;
    private String[] writableFormats;
    private JFileChooser saveFileChooser;
    private Map<JMenuItem, ImageEffect> effectMap;
}

/**
 * A JPanel with an image for a background.
 */
class JImagePanel extends JPanel {
    public JImagePanel() {
        super();
    }

    public JImagePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public JImagePanel(LayoutManager layout) {
        super(layout);
    }

    public JImagePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image image) {
        backgroundImage = image;
        invalidate();
    }

    public void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            super.paintComponent(g);
        }
    }

    public Dimension getPreferredSize() {
        Dimension dim = super.getPreferredSize();
        if (backgroundImage != null) {
            dim.setSize(Math.max(dim.getWidth(), backgroundImage.getWidth(this)), Math.max(dim.getHeight(), backgroundImage.getHeight(this)));
        }
        return dim;
    }

    private Image backgroundImage;
}
