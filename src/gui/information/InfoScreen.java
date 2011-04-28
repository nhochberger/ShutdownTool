package gui.information;

import hochberger.utilities.images.loader.ImageLoader;
import hochberger.utilities.text.LoadText;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.schneide.framework.gui.swing.EDT;

public class InfoScreen {

    private static final Dimension DIALOG_DIMENSION = new Dimension(250, 170);
    private static final String INFORMATION_DIALOG_TITLE = "Scheduled Shutdown Info";

    private JDialog dialog;

    private final JFrame owner;

    public InfoScreen(final JFrame owner) {
        super();
        this.owner = owner;
    }

    private void buildUI() {
        EDT.only();
        this.dialog = buildDialog();
        this.dialog.add(new JScrollPane(buildTextPane()));
    }

    private JComponent buildTextPane() {
        JTextPane textPane = new JTextPane();
        StyledDocument document = textPane.getStyledDocument();
        MutableAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(attributes, "Arial");
        document.setParagraphAttributes(0, 0, attributes, true);
        textPane.setText(LoadText.from("text/info.txt"));
        textPane.setEditable(false);
        return textPane;
    }

    private JDialog buildDialog() {
        JDialog result = new JDialog(this.owner, true);
        result.setPreferredSize(DIALOG_DIMENSION);
        result.setMinimumSize(DIALOG_DIMENSION);
        result.setResizable(false);
        result.setLocationRelativeTo(this.owner);
        result.setTitle(INFORMATION_DIALOG_TITLE);
        result.setIconImage(ImageLoader.loadImage("graphics/info.png"));
        return result;
    }

    public void show() {
        EDT.performBlocking(new Runnable() {

            @Override
            public void run() {
                if (null == InfoScreen.this.dialog) {
                    buildUI();
                }
                InfoScreen.this.dialog.setVisible(true);

            }
        });
    }
}
