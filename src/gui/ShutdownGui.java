package gui;

import gui.information.InfoScreen;
import hochberger.utilities.eventbus.Event;
import hochberger.utilities.eventbus.EventReceiver;
import hochberger.utilities.generic.listeners.GenericMouseListener;
import hochberger.utilities.gui.ImageLabel;
import hochberger.utilities.gui.PanelWrapper;
import hochberger.utilities.gui.lookandfeel.SetLookAndFeelTo;
import hochberger.utilities.images.loader.ImageLoader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.schneide.framework.gui.swing.EDT;

import controller.Main;
import controller.event.CancelCountDownEvent;
import controller.event.CommandExecutionErrorEvent;
import controller.event.CountDownEvent;
import controller.event.CountDownFinishedEvent;
import controller.event.StartCountDownEvent;

public class ShutdownGui implements EventReceiver {

    private static final Dimension FRAME_DIMENSION = new Dimension(300, 170);

    private final String applicationTitle;
    private JFrame frame;
    private JButton startButton;
    private JButton cancelButton;
    private CountDownLabel countDownLabel;

    private SettingsPanel settingsPanel;

    public ShutdownGui(final String applicationTitle) {
        super();
        this.applicationTitle = applicationTitle;
    }

    private void buildUI() {
        EDT.only();
        SetLookAndFeelTo.systemLookAndFeel();
        this.frame = buildFrame();
        this.settingsPanel = new SettingsPanel();
        this.frame.add(this.settingsPanel.getPanel(), BorderLayout.NORTH);
        this.frame.add(buildInformationPanel(), BorderLayout.CENTER);
        this.frame.add(buildLowerPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildInformationPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        this.countDownLabel = new CountDownLabel();
        panel.add(this.countDownLabel.getLabel());
        return panel;
    }

    private JPanel buildLowerPanel() {
        this.startButton = new JButton("Start");
        this.cancelButton = new JButton("Abbrechen");
        Dimension buttonDimension = new Dimension(150, 30);
        this.startButton.setPreferredSize(buttonDimension);
        this.cancelButton.setPreferredSize(buttonDimension);
        setCancelButtonEnabled(false);
        this.startButton.addActionListener(new StartCountDownActionListener());
        this.cancelButton.addActionListener(new StopCountDownActionListener());
        JPanel panel = new JPanel(new MigLayout("", "[60][fill][60, r]", "[b]"));
        panel.setBackground(Color.WHITE);
        panel.add(PanelWrapper.wrapWithBackground(Color.WHITE, this.startButton, this.cancelButton), "skip 1");
        ImageLabel infoImageLabel = new ImageLabel("graphics/info.png");
        infoImageLabel.addMouseListener(new GenericMouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                new InfoScreen(ShutdownGui.this.frame).show();
            }
        });
        panel.add(infoImageLabel);
        return panel;
    }

    private JFrame buildFrame() {
        JFrame result = new JFrame(this.applicationTitle);
        result.setPreferredSize(FRAME_DIMENSION);
        result.setMinimumSize(FRAME_DIMENSION);
        result.setResizable(false);
        result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        result.setLayout(new BorderLayout());
        Image image = ImageLoader.loadImage("graphics/shut_down.png");
        result.setIconImage(image);
        result.setLocationRelativeTo(null);
        return result;
    }

    public void show() {
        EDT.performBlocking(new Runnable() {
            @Override
            public void run() {
                if (null == ShutdownGui.this.frame) {
                    buildUI();
                }
                ShutdownGui.this.frame.setVisible(true);
            }
        });
    }

    private void setRemainingSeconds(final long seconds) {
        EDT.performBlocking(new Runnable() {
            @Override
            public void run() {
                ShutdownGui.this.countDownLabel.setRemainingSeconds(seconds);
            }
        });
    }

    @Override
    public <TYPE extends Event> void receive(final TYPE event) {
        if (event instanceof CountDownEvent) {
            final long remainingSeconds = ((CountDownEvent) event).getRemainingSeconds();
            setRemainingSeconds(remainingSeconds);
        }
        if (event instanceof StartCountDownEvent) {
            setStartButtonEnabled(false);
            setCancelButtonEnabled(true);
            setSettingsEnabled(false);
        }
        if (event instanceof CancelCountDownEvent) {
            setStartButtonEnabled(true);
            setCancelButtonEnabled(false);
            setSettingsEnabled(true);
        }
        if (event instanceof CountDownFinishedEvent) {
            indicateCountdownFinished();
        }
        if (event instanceof CommandExecutionErrorEvent) {
            indicateError();
        }
    }

    private void indicateCountdownFinished() {
        // TODO Auto-generated method stub
    }

    private void indicateError() {
        // TODO Auto-generated method stub
    }

    private void setSettingsEnabled(final boolean enabled) {
        EDT.performBlocking(new Runnable() {
            @Override
            public void run() {
                ShutdownGui.this.settingsPanel.setEnabled(enabled);
            }
        });
    }

    private void setStartButtonEnabled(final boolean enabled) {
        EDT.performBlocking(new Runnable() {
            @Override
            public void run() {
                ShutdownGui.this.startButton.setEnabled(enabled);
                ShutdownGui.this.startButton.setVisible(enabled);
            }
        });
    }

    private void setCancelButtonEnabled(final boolean enabled) {
        EDT.performBlocking(new Runnable() {
            @Override
            public void run() {
                ShutdownGui.this.cancelButton.setEnabled(enabled);
                ShutdownGui.this.cancelButton.setVisible(enabled);
            }
        });
    }

    protected class StartCountDownActionListener implements ActionListener {

        public StartCountDownActionListener() {
            super();
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            Main.EVENT_BUS.publishFromEDT(new StartCountDownEvent(ShutdownGui.this.settingsPanel.getSeconds()));
        }
    }

    protected class StopCountDownActionListener implements ActionListener {

        public StopCountDownActionListener() {
            super();
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            Main.EVENT_BUS.publishFromEDT(new CancelCountDownEvent());
        }
    }
}
