package gui;

import hochberger.utilities.gui.EnhancedLabel;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.JLabel;

import edt.EDT;

public class CountDownLabel {
	private final EnhancedLabel label;

	public CountDownLabel() {
		super();
		EDT.only();
		this.label = new EnhancedLabel("00:00:00");
		build();
	}

	private void build() {
		this.label.setText("00:00:00");
		this.label.setForeground(Color.LIGHT_GRAY);
		Font font = this.label.getFont().deriveFont(50f);
		this.label.setFont(font);
		this.label.setRightShadow(2, 2, Color.DARK_GRAY.brighter());
	}

	public JLabel getLabel() {
		return this.label;
	}

	public void setRemainingSeconds(final long totalSeconds) {
		long hours = totalSeconds / 3600;
		long minutes = (totalSeconds % 3600) / 60;
		long seconds = (totalSeconds % 60);
		DecimalFormat formatter = new DecimalFormat("00");
		this.label.setText(formatter.format(hours) + ":" + formatter.format(minutes) + ":" + formatter.format(seconds));
	}
}
