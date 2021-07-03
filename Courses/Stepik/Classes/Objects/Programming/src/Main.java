class Application {

  // write a field here
  String name;

  // write a constructor here
  public Application(String n){
    this.name = n;
  }

  public void run(String[] args) {
    // implement a method
    System.out.println(this.name);
    for(String str: args){
      System.out.println(str);
    }
  }
}