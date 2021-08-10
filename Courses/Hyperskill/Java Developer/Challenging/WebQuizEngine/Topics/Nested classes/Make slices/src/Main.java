class Apple {

    private String appleVariety;

    public Apple(String appleVariety) {
        this.appleVariety = appleVariety;
    }

    void cutApple() {

        // create local inner class Knife
        // create method makeSlices()
        class Knife {
            public void makeSlices() {
                System.out.printf("Apple %s is ready to be eaten!%n", appleVariety);
            }
        }


        Knife knife = new Knife();
        knife.makeSlices();
    }

    public static void main(String[] args) {
        Apple apple = new Apple("Gala");
        apple.cutApple();
    }
}