class Carte{

  private String nom;
  private int date;
  private boolean dateVisible;

  public Carte(int date, String nom, boolean dV){
    this.nom = nom;
    this.date = date;
    this.dateVisible = dV;
  }

  public String getNom(){
    return this.nom;
  }

  public int getDate(){
    return this.date;
  }

  public String toString(){
    if(this.dateVisible)
      return this.date + " -> " + this.nom;
    else
      return "??? -> " + this.nom;
  }

  public void retourner() {
      this.dateVisible = !this.dateVisible;
  }
}
