package states;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.timer.*;
import states.stopwatch.AbstractStopwatch;

class TimerTests {

	private static Context context;
	private ClockState current, newState;

	@BeforeEach
	void setup() {
        context = new Context(); // create the state machine context
        AbstractTimer.resetInitialValues();
	}
		
	@Test
	void testInitialState() {
		/* When initialising the context (see setup() method above)
		 * its currentState will be initialised with the initial state
		 * of timer, i.e. the IdleTimer state.
		 */
		current = context.currentState;
		
	    assertEquals(Mode.timer, current.getMode());
	    assertSame(IdleTimer.Instance(), current);
	    assertEquals(0, AbstractTimer.getTimer(),"For the value of timer we ");
	    assertEquals(0, AbstractTimer.getMemTimer(),"For the value of memTimer we ");
	}
	
	@Test
	void testInitialAbstractTimer() {
		// The initial state of composite state AbstractTimer should be IdleTimer
		assertSame(AbstractTimer.Instance(), IdleTimer.Instance());
	}
	
	@Test
	void testInitialActiveTimer() {
		// The initial state of composite state ActiveTimer should be RunningTimer
		assertSame(ActiveTimer.Instance(), RunningTimer.Instance());
	}
	
	@Test
	void testHistoryState() {
		current = AbstractTimer.Instance();
		// after processing the left() event, we should arrive in the initial state of AbstractStopwatch
		newState = current.left();
		assertEquals(AbstractStopwatch.Instance(), newState);
		/* after another occurrence of the left() event, we should return to the original state
		 * because we used history states		
		 */
		assertEquals(current, newState.left());
	}

	@Test
	void testRingingTimerDoIt() {
		current = RingingTimer.Instance();

		newState = current.doIt();

		// Verify that doIt() keeps the same state
		assertSame(current, newState, "doIt() should not change the state.");

		// Verify display message remains correct
		assertEquals("Time's up !", current.getDisplayString(), "getDisplayString() should return 'Time's up !'.");
	}

	@Test
	void testRunningTimerRight() {
		// Get instance of RunningTimer
		current = RunningTimer.Instance();

		// Call right()
		newState = current.right();

		// Verify transition to IdleTimer
		assertSame(IdleTimer.Instance(), newState, "right() should transition to IdleTimer.");

		// Verify the correct text label for right action
		assertEquals("stop", current.getRightText(), "getRightText() should return 'stop'.");
	}

	@Test
	void testSetTimerLeft() {
		// Get instance of RunningTimer
		current = SetTimer.Instance();

		current = current.doIt();

		assertEquals("memTimer = 1", current.getDisplayString(), "Get the value of memTimer");

		// Call left()
		newState = current.left();

		// Verify transition to IdleTimer
		assertSame(SetTimer.Instance(), newState, "left() should reset the timer");
		assertEquals("memTimer = 0", newState.getDisplayString(), "Get the value of memTimer");


		// Verify the correct text label for left action
		assertEquals(current.getDisplayString(), newState.getDisplayString(), "Get the value of memTimer should be the same");
	}

	@Test
	void testSetTimerUp() {
		// Get instance of RunningTimer
		current = SetTimer.Instance();

		current = current.doIt();

		assertEquals("memTimer = 1", current.getDisplayString(), "Get the value of memTimer");

		// Call left()
		newState = current.up();

		// Verify transition to IdleTimer
		assertSame(SetTimer.Instance(), newState, "left() should reset the timer");
		assertEquals("memTimer = 6", newState.getDisplayString(), "Get the value of memTimer");

		// Verify the correct text label for left action
		assertEquals(current.getDisplayString(), newState.getDisplayString(), "Get the value of memTimer should be the same");
	}

}
