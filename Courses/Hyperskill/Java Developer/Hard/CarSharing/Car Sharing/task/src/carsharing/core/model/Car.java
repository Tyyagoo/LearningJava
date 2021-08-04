package carsharing.core.model;

import java.util.Objects;

public class Car {
    private final int id;
    private final String name;
    private final int companyId;

    private Car(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (id != car.id) return false;
        if (companyId != car.companyId) return false;
        return Objects.equals(name, car.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + companyId;
        return result;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public static class Builder {
        private int id = 0;
        private String name;
        private int companyId;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCompanyId(int companyId) {
            this.companyId = companyId;
            return this;
        }

        public Car build() {
            return new Car(id, name, companyId);
        }
    }
}
