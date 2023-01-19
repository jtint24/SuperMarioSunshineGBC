public class ActorLibrary {
    static class Enemy extends MovingActor {
        boolean isBenign = false;
        int maxX;
        int minX;

        public Enemy(String name, Point p, Environment e, int minX, int maxX, int speed) {
            super(p,e,speed);
            imageFetcher = () -> Images.getImage(name+Application.frameNumber());

            this.maxX = maxX;
            this.minX = minX;

            dx = 2;
            //accelRight();
        }

        @Override
        void move() {
            if (environment.player.location.distanceToSQ(location) < 256 && !isBenign) {
                if (environment.player.dz <= 0 && (environment.player.location.z < location.z || (environment.player.location.offsetZ > location.offsetZ))) {
                    environment.deleteActor(this);
                    // System.out.println("kill!");
                    isBenign = true;
                    environment.player.dz = 6;
                } else {
                    environment.player.damage();
                    // System.out.println("damage! "+environment.player.location+" "+location);

                }
            }

            if (location.x > maxX) {
                accelLeft();
            }
            if (location.x < minX) {
                accelRight();
            }


            super.move();
        }
    }
    static class Goop extends Actor {
        public Goop(Point p, Environment e) {
            super(p, e);
            imageFetcher = () -> Images.getImage("goop"+Application.frameNumber(320,4));
        }

        @Override
        void move() {
            for (Actor a : environment.actors) {
                if (a instanceof WaterDrop) {
                    if (a.location.distanceToSQ(location) < 200) {
                        environment.deleteActor(this);
                    }
                }
            }
            if (environment.player.location.distanceToSQ(location) < 256) {
                environment.player.damage();
            }
        }
    }
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

    static class WaterDrop extends MovingActor {

        public WaterDrop(Point p, Environment e, int dx, int dy) {
            super(p, e,100);

            imageFetcher = () -> Images.getImage("waterDrop");
            this.dz = 4;
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        void move() {
            applyGravity();
            super.move();
            this.updateOffsets();
            if (onSolidGround()) {
                environment.deleteActor(this);
            }
        }
    }
}
