package SampleGame;

import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import static javafx.scene.input.KeyCode.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Input {

	/**
	 * Bitset which registers if any {@link KeyCode} keeps being pressed or if it is
	 * released.
	 */
	private BitSet keyboardBitSet = new BitSet();

	private Scene scene = null;

	/**
	 * Constructor of the class Input
	 * @param scene
	 */
	public Input(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Add listeners in the scene
	 */
	public void addListeners() {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
		scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}

	/**
	 * Remove listeners from the scene
	 */
	public void removeListeners() {
		scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
		scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}
	
	/**
	 * "Key Pressed" handler for all input events: register pressed key in the
	 * bitset
	 */
	private EventHandler<KeyEvent> keyPressedEventHandler = event -> {
		// register key down
		keyboardBitSet.set(event.getCode().ordinal(), true);
		event.consume();
	};

	/**
	 * "Key Released" handler for all input events: unregister released key in the
	 * bitset
	 */
	private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			// register key up
			keyboardBitSet.set(event.getCode().ordinal(), false);
			event.consume();
		}
	};
	
	/**
	 * Check if the event e is a key pressed
	 * @param e the event to check 
	 * @return true if P is pressed else return false 
	 */
	public boolean pause(KeyEvent e) {
		return (e.getCode()==P);
	}
	
	private boolean is(KeyCode key) {
		return keyboardBitSet.get(key.ordinal());
	}
	
	// -------------------------------------------------
		// Evaluate bitset of pressed keys and return the player input.
		// If direction and its opposite direction are pressed simultaneously, then the
		// direction isn't handled.
		// -------------------------------------------------
	
	/**
	 * Check if the key ESCAPE is pressed
	 * @return true if ESCAPE is pressed else return false
	 */
	public boolean isExit() {
		return is(ESCAPE);
	}

	/**
	 * Check if the key P is pressed
	 * @return true if P is pressed else return false
	 */
	public boolean isPause() {
		return is(P);
	}

	/**
	 * Check if the key U is pressed
	 * @return true if U is pressed else return false
	 */
	public boolean isUnpause() {
		return is(U);
	}

}
