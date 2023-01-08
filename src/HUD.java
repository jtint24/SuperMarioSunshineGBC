import java.awt.*;
import java.util.HashMap;

class HUD implements Renderable {
    HashMap<String, Meter> meters = new HashMap<>();
    int lastShowFrame = 0;
    Meter meterToShow;
    float waterLevel = 100;
    int lifeLevel = 8;

    public HUD() {}

    @Override
    public void render(Point p, Canvas c) {

        if (Application.frameCount < lastShowFrame + 100 && meterToShow != null) {
            int startingY = 0;
            if (Application.frameCount > lastShowFrame + 40) {
                startingY = ((2*(lastShowFrame-Application.frameCount)+80)/5)*5;
            }

            meterToShow.dispY = startingY;
            meterToShow.render(new Point(0, 0, 0), c);
        }

        int dispWaterLevel = (int) Math.min(10,Math.ceil(1+waterLevel/10.0));

        c.imagesToRender.push(new RenderedImage(Images.getImage("fludd"+dispWaterLevel).getScaledInstance(80,80,Image.SCALE_DEFAULT), 710, 600));
        c.imagesToRender.push(new RenderedImage(Images.getImage("h2oIcon").getScaledInstance(80,80,Image.SCALE_DEFAULT), 710, 510));
        c.imagesToRender.push(new RenderedImage(Images.getImage("life"+lifeLevel).getScaledInstance(80,80,Image.SCALE_DEFAULT), 710, 10));
    }

    void show(String name) {
        meterToShow = meters.get(name);
        lastShowFrame = Application.frameCount;
    }

    void addMeter(String name, Meter m) {
        meters.put(name, m);
    }

    static class Meter implements Renderable {
        private final Image icon;
        private int count = 0;
        private int dispY = 0;

        public Meter(Image icon) {
            this.icon = icon;
        }

        public void increment() {
            count++;
        }
        public void clear() {
            count = 0;
        }
        public void incrementBy(int a) {
            count += a;
        }
        private int getDigit1() {
            return count % 10;
        }
        private int getDigit2() {
            return count / 10;
        }

        @Override
        public void render(Point p, Canvas c) {
            Image scaledIcon = icon.getScaledInstance(80,80, Image.SCALE_DEFAULT);
            Image scaledX = Images.getImage("xIcon").getScaledInstance(80,80, Image.SCALE_DEFAULT);
            Image scaledDigit1 = Images.getImage(""+getDigit1()).getScaledInstance(80,80, Image.SCALE_DEFAULT);
            Image scaledDigit2 = Images.getImage(""+getDigit2()).getScaledInstance(80,80, Image.SCALE_DEFAULT);

            c.imagesToRender.push(new RenderedImage(scaledIcon, 0, dispY));
            c.imagesToRender.push(new RenderedImage(scaledX, 80, dispY));
            c.imagesToRender.push(new RenderedImage(scaledDigit2, 160, dispY));
            c.imagesToRender.push(new RenderedImage(scaledDigit1, 240, dispY));
        }
    }
}
