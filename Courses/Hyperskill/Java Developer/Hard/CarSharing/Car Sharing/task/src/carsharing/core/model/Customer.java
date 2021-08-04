package carsharing.core.model;

public class Customer {
    private int id;
    private String name;
    private Integer rentedCarId;

    private Customer(int id, String name, Integer rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRentedCarId() {
        return rentedCarId;
    }

    public static class Builder {
        private int id;
        private String name;
        private Integer rentedCarId;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCarId(Integer id) {
            this.rentedCarId = id;
            return this;
        }

        public Customer build() {
            return new Customer(id, name, rentedCarId);
        }
    }
}
