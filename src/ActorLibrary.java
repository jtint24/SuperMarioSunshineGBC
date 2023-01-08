public class ActorLibrary {
    static class Coin extends Actor {
        public Coin(Point p, Environment e) {
            super(p, e);
            imageFetcher = () -> Images.getImage("coin"+Application.frameNumber());
        }

        @Override
        void move() {
            if (environment.player.location.distanceToSQ(location) < 256) {
                environment.deleteActor(this);
                environment.hud.meters.get("coin").increment();
                environment.hud.show("coin");
            }
        }
    }

    static class Shadow extends Actor {
        Actor owner;

        public Shadow(Actor owner, Environment e) {
            super(new Point(owner.location.x, owner.location.y, e.highestZAt(owner.location),owner.location.offsetX, owner.location.offsetY, 0), e);

            this.owner = owner;

            imageFetcher = () -> Images.getImage("shadow");
        }

        @Override
        void move() {
            this.location = new Point(owner.location.x, owner.location.y, environment.highestZAt(owner.location)+1,owner.location.offsetX, owner.location.offsetY, 0);
            if (!environment.actors.contains(owner)) {
                environment.deleteActor(this);
            }
        }

        @Override
        public void render(Point p, Canvas c) {
            if (!owner.onSolidGround()) {
                super.render(p,c);
            }
        }

    }

    static class WaterDrop extends Actor {
        int dx = 0;
        int dy = 0;

        public WaterDrop(Point p, Environment e, int dx, int dy) {
            super(p, e);

            imageFetcher = () -> Images.getImage("waterDrop");
            this.dz = 5;
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        void move() {
            applyGravity();
            this.location.offsetX += dx;
            this.location.offsetY += dy;
            this.updateOffsets();
            if (onSolidGround()) {
                environment.deleteActor(this);
            }
        }
    }
}
