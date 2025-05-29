package traffic.light;

import com.yakindu.core.ITimerService;
import com.yakindu.core.TimerService;

import traffic.light.ui.CounterDisplay.Color;
import traffic.light.ui.TrafficLightFrame;

public class TrafficLightCtrlPanel extends TrafficLightFrame {

	private static final long serialVersionUID = -8909693541678814631L;

	protected TrafficLightCtrl statemachine;

	protected ITimerService timerService;
	
	public static void main(String[] args) {
		TrafficLightCtrlPanel application = new TrafficLightCtrlPanel();
		application.init();
		
		application.setupStatemachine();
		
		application.run();
	}

	protected void run() {
		statemachine.enter();
	}


	protected void setupStatemachine() {
		statemachine = new TrafficLightCtrl();
		timerService = new TimerService();
		statemachine.setTimerService(timerService);
		
		statemachine.trafficLight().getDisplayRed().subscribe((e) ->    setLights(true, false, false));
		statemachine.trafficLight().getDisplayYellow().subscribe((e) -> setLights(false, true, false));
		statemachine.trafficLight().getDisplayGreen().subscribe((e) ->  setLights(false, false, true));
		statemachine.trafficLight().getDisplayNone().subscribe((e) ->   setLights(false, false, false));
		
		statemachine.timer().getUpdateTimerValue().subscribe((value) -> {
			crossing.getCounterVis().setCounterValue(value);
			repaint();
		});
		statemachine.timer().getUpdateTimerColour().subscribe((value) -> {
			crossing.getCounterVis().setColor(value == "Red" ? Color.RED : Color.GREEN);
		});

		buttonPanel.getSwitchBlinkNormal().addActionListener(e -> statemachine.raiseToggleBlink());

		buttonPanel.getSwitchOnOff().addActionListener(e -> statemachine.raiseTogglePower());
	}
	
	private void setLights(boolean red, boolean yellow, boolean green) {
		crossing.getTrafficLightVis().setRed(red);
		crossing.getTrafficLightVis().setYellow(yellow);
		crossing.getTrafficLightVis().setGreen(green);
		repaint();
	}
}
