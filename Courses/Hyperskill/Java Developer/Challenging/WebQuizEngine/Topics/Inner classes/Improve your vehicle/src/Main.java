class Vehicle {

    private String name;

    // create constructor
    public Vehicle(String name) {
        this.name = name;
    }

    class Engine {

        // add field horsePower
        int horsePower;
        // create constructor
        public Engine(int horsePower) {
            this.horsePower = horsePower;
        }

        void start() {
            System.out.println("RRRrrrrrrr....");
        }

        // create method printHorsePower()
        public void printHorsePower() {
            System.out.printf("Vehicle %s has %d horsepower.%n", name, horsePower);
        }
    }
}

// this code should work
class EnjoyVehicle {

    public static void main(String[] args) {

        Vehicle vehicle = new Vehicle("Dixi");
        Vehicle.Engine engine = vehicle.new Engine(15);
        engine.printHorsePower();
    }
}