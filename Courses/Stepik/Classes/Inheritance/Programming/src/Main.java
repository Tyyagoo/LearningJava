class Employee {

  // write fields
    protected String name;
    protected String email;
    protected int experience;

  // write constructor
    Employee(String name, String email, int experience){
        this.name = name;
        this.email = email;
        this.experience = experience;
    }
  // write getters

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getExperience() {
        return experience;
    }
}

class Developer extends Employee {

  // write fields
    private String mainLanguage;
    private String[] skills;

  // write constructor
    Developer(String name, String email, int experience, String mainLanguage, String[] skills){
        super(name, email, experience);
        this.mainLanguage = mainLanguage;
        this.skills = skills;
    }

  // write getters


    public String getMainLanguage() {
        return mainLanguage;
    }

    public String[] getSkills() {
        return skills;
    }
}

class DataAnalyst extends Employee {

  // write fields
    private boolean PhD;
    private String[] methods;
  // write constructor
    DataAnalyst(String name, String email, int experience, boolean phd, String[] methods){
        super(name, email, experience);
        this.PhD = phd;
        this.methods = methods;
    }

  // write getters

    public boolean isPhD() {
        return PhD;
    }

    public String[] getMethods() {
        return methods;
    }
}