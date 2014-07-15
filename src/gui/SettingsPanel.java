package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import edt.EDT;

public class SettingsPanel {

	private JPanel panel;
	private JSpinner hours;
	private JSpinner minutes;
	private JSpinner seconds;

	public SettingsPanel() {
		super();
		EDT.only();
		build();
	}

	private void build() {
		this.panel = new JPanel();
		this.panel.setBackground(Color.WHITE);
		this.hours = createSpinnerWithMaximum(99);
		this.minutes = createSpinnerWithMaximum(59);
		this.seconds = createSpinnerWithMaximum(59);
		this.panel.add(this.hours);
		this.panel.add(new JLabel("h"));
		this.panel.add(this.minutes);
		this.panel.add(new JLabel("min"));
		this.panel.add(this.seconds);
		this.panel.add(new JLabel("s"));
	}

	public JPanel getPanel() {
		return this.panel;
	}

	public long getSeconds() {
		long secondsFromHours = ((Integer) this.hours.getValue()).intValue() * 3600;
		long secondsFromMinutes = ((Integer) this.minutes.getValue()).intValue() * 60;
		long secondsFromSeconds = ((Integer) this.seconds.getValue()).intValue();
		return secondsFromHours + secondsFromMinutes + secondsFromSeconds;
	}

	private JSpinner createSpinnerWithMaximum(final int maximumValue) {
		SpinnerModel model = new ContinuousNumberModel(0, 0, maximumValue, 1);
		JSpinner result = new JSpinner(model);
		return result;
	}

	public void setEnabled(final boolean enabled) {
		for (Component component : this.panel.getComponents()) {
			component.setEnabled(enabled);
		}
	}

	protected class ContinuousNumberModel extends SpinnerNumberModel {

		private static final long serialVersionUID = -2384453139465854375L;

		public ContinuousNumberModel(final int value, final int minimum, final int maximum, final int stepSize) {
			super(value, minimum, maximum, stepSize);
		}

		@Override
		public Object getNextValue() {
			if (getMaximum().equals(getValue())) {
				return getMinimum();
			}
			return super.getNextValue();
		}

		@Override
		public Object getPreviousValue() {
			if (getMinimum().equals(getValue())) {
				return getMaximum();
			}
			return super.getPreviousValue();
		}
	}
}
