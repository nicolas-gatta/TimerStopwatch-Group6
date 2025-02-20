package states;

import states.stopwatch.AbstractStopwatch;
import states.stopwatch.LaptimeStopwatch;
import states.stopwatch.ResetStopwatch;
import states.stopwatch.RunningStopwatch;
import states.timer.AbstractTimer;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class StopwatchTests {

	private static Context context;
	private ClockState current, newState;

	@BeforeEach
	void setup() {
        context = new Context(); // create the state machine context
        AbstractStopwatch.resetInitialValues();
        context.currentState = AbstractStopwatch.Instance();
	}
		
	@org.junit.jupiter.api.Test
	void testInitialState() {
		//context.tick(); //no tick() needed for this test;
		/* When initialising the context (see setup() method above)
		 * its currentState will be inialised with the initial state
		 * of timer, i.e. the IdleTimer state.
		 */
		current = context.currentState;
		
	    assertEquals(Mode.stopwatch, current.getMode());
	    assertSame(ResetStopwatch.Instance(), current);
	    assertEquals(0, AbstractStopwatch.getTotalTime(),"For the value of totalTime we ");
	    assertEquals(0, AbstractStopwatch.getLapTime(),"For the value of lapTime we ");
	}

	@org.junit.jupiter.api.Test
	void testInitialAbstractStopwatch() {
		// The initial state of composite state AbstractStopwatch should be ResetStopwatch
		assertSame(AbstractStopwatch.Instance(), ResetStopwatch.Instance());
	}
	
	@Test
	void testHistoryState() {
		current = AbstractStopwatch.Instance();
		// after processing the left() event, we should arrive in the initial state of AbstractStopwatch
		newState = current.left();
		assertEquals(AbstractTimer.Instance(), newState);
		/* after another occurrence of the left() event, we should return to the original state
		 * because we used history states		
		 */
		assertEquals(current, newState.left());
	}

	@Test
	void testRightTransition() {
		// Start in RunningStopwatch state
		current = RunningStopwatch.Instance();

		// Simulate pressing "right" button
		newState = current.right();

		// Verify that the transition is to ResetStopwatch
		assertSame(ResetStopwatch.Instance(), newState, "Pressing 'right' should transition to ResetStopwatch.");
	}

	@Test
	void testUpTransition() {
		// Start in LaptimeStopwatch state
		current = LaptimeStopwatch.Instance();

		// Simulate pressing "up" button
		newState = current.up();

		// Verify that the transition is to RunningStopwatch
		assertSame(RunningStopwatch.Instance(), newState, "Pressing 'up' should transition to RunningStopwatch.");
	}

	@Test
	void testGetUpText() {
		// Instance of LaptimeStopwatch
		current = LaptimeStopwatch.Instance();

		// Verify the text returned by getUpText()
		assertEquals("unsplit", current.getUpText(), "getUpText() should return 'unsplit'.");
	}

	@Test
	void testGetDisplayString() {
		// Instance of LaptimeStopwatch
		current = LaptimeStopwatch.Instance();

		// Update laptime and timeout = 5;
		current.entry();

		// Decrease timeout to 0 to get RunningStopwatch;
		current.doIt();
		current.doIt();
		current.doIt();
		current.doIt();

		newState = current.doIt();

		// Simulate the entry action that sets lapTime
		current.entry();

		assertEquals(RunningStopwatch.Instance(), newState, "Calling doIt() * 5 should return RunningStopwatch.");
		// Verify the output format
		assertEquals("lapTime = 5", current.getDisplayString(), "getDisplayString() should return 'lapTime = X' where X is the lap time.");
	}

}
