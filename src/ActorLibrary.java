public class ActorLibrary {

    static class Sparkle extends MovingActor {
        public Sparkle(Point location, Environment environment) {
            super(location.randomize(), environment, 5);

            imageFetcher = () -> Images.getImage("sparkle");
            dz = 3;
            dy = (int) Math.round(6.0*Math.random()-3.0);
            dx = (int) Math.round(6.0*Math.random()-3.0);
        }

        @Override
        void move() {
            if (Application.frameCount % 2 == 0) {
                applyGravity();
            }
            super.move();
            this.updateOffsets();
            if (onSolidGround()) {
                environment.deleteActor(this);
            }
        }
    }
    static class Shine extends Actor {
        int initialZ;

        int initialX;
        int initialOffsetZ;
        int initialOffsetX;
        boolean collected = false;
        boolean active = false;

        Mission.Objective objective;
        public Shine(Point p, Environment e, Mission.Objective objective) {
            super(p,e);
            initialZ = p.z;
            initialX = p.x;
            initialOffsetX = p.offsetX;
            initialOffsetZ = p.offsetZ;
            imageFetcher = () -> Images.getImage("shineIcon");
            this.objective = objective;
        }
        @Override
        void move() {

            if (objective.completionCriteria(environment)) {
                active = true;
            }

            if (collected) {
                int offsetZ = (int) Math.round((4.0 * Math.sin(((float) Application.frameCount) / 10.0)));
                int offsetX = (int) Math.round((4.0 * Math.cos(((float) Application.frameCount) / 10.0)));

                location = Point.average(environment.player.location, this.location);
                location.offsetZ += offsetZ;
                location.offsetX += offsetX;
                location.z++;

            } else {
                int offset = (int) Math.round((8.0 * Math.sin(((float) Application.frameCount) / 10.0)));
                location.offsetZ = initialOffsetZ + offset;
                location.z = initialZ;
            }


            if (Application.frameCount % 10 == 0 && active) {
                environment.addActor(new Sparkle((Point) location.clone(), environment));
            }
            updateOffsets();


            if (location.distanceToSQ(environment.player.location) < 256 && active) {
                collected = true;
            }
        }

        @Override
        public void render(Point p, Canvas c) {
            if (active) {
                super.render(p,c);
            }
        }
    }
    static class Number {}
    static class Enemy extends MovingActor {
        boolean isBenign = false;
        int maxX;
        int minX;

        public Enemy(Number one) {super(null,null, 0);}

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
    static class BlueCoin extends Actor {
        public BlueCoin(Point p, Environment e) {
            super(p, e);
            imageFetcher = () -> Images.getImage("blueCoin"+Application.frameNumber());
        }

        @Override
        void move() {
            if (environment.player.location.distanceToSQ(location) < 256) {
                environment.deleteActor(this);
                environment.hud.meters.get("blueCoin").increment();
                environment.hud.show("blueCoin");
            }
        }
    }

    static class Pianta extends Actor {
        public Pianta(String color, Point p, Environment e) {
            super(p,e);
            imageFetcher = () -> Images.getImage(color+"Pianta");
        }
    }

    static class Shadow extends Actor {
        Actor owner;

        public Shadow(Actor owner, Environment e) {
            super(new Point(owner.location.x, owner.location.y, e.highestZAt(owner.location),owner.location.offsetX, owner.location.offsetY, 0), e);

            this.owner = owner;

            imageFetcher = () -> {
                int yDifference = getOwner().location.z - this.location.z;
                if (yDifference < 2) {
                    return Images.getImage("shadow");
                } else if (yDifference < 4) {
                    return Images.getImage("smallShadow");
                } else {
                    return Images.getImage("tinyShadow");
                }
            };
        }

        public Actor getOwner() {
            return owner;
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
            if (owner instanceof Shine) { // Makes sure invisible shines (that aren't supposed to be there yet) don't cast shadows
                if (!((Shine) owner).active) {
                    return;
                }
            }
            if (!owner.onSolidGround()) {
                super.render(p,c);
            }
        }

    }

    static class WaterDrop extends MovingActor {

        public WaterDrop(Point p, Environment e, int dx, int dy, int dz) {
            super(p, e,100);

            imageFetcher = () -> Images.getImage("waterDrop");
            this.dz = dz;
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
