package carsharing.core.model;

public class Company {
    private int id;
    private final String name;

    private Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private int id = 0;
        private String name = null;

        public Builder() {}

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Company build() {
            return new Company(id, name);
        }
    }
}
