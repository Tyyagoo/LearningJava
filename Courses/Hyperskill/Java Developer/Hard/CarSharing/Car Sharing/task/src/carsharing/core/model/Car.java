package carsharing.core.model;

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
