package hubris.potion;

public class PotionClipping extends PotionBase {

    public static final PotionClipping INSTANCE = new PotionClipping();

    public PotionClipping() {
        super("clipping", true, 0xf4c6ff);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}