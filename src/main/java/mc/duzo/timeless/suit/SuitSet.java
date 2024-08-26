package mc.duzo.timeless.suit;

public class SuitSet {
	private final Suit suit;
	private final SuitItem head;
	private final SuitItem chest;
	private final SuitItem legs;
	private final SuitItem feet;

	public SuitSet(Suit suit, SuitItem head, SuitItem chest, SuitItem legs, SuitItem feet) {
		this.suit = suit;

		this.head = head;
		this.chest = chest;
		this.legs = legs;
		this.feet = feet;
	}
}
