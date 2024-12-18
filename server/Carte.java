
/**
 * Classe Carte
 * 
 * Cette classe permet de créer des cartes qui contiennent un événement et une date
 * 
 * @author Guillaume & Rayane
 * @version 1.0
 */
class Carte{

  private String nom;
  private int date;
  private boolean dateVisible;

  /**
   * constructeur de carte à partir d'une chaine
   * format de la chaine: evenement:date
   * 
   * @param chaine chaine contenant l'evenement et la date
   */
  public Carte(String chaine){
    int separationIndex = -1;
    int i = 0;

    while(separationIndex == -1 && i < chaine.length()){
      if(chaine.charAt(i) == ':')
        separationIndex = i;
      else
        i++;
    }

    String evenement = chaine.substring(0, separationIndex);
    int date = Integer.parseInt(chaine.substring(separationIndex + 1));

    this.nom = evenement;
    this.date = date;
  }

  /**
   * Méthode getNom revoit le nom de l'évènement
   * 
   * @return le nom de l'évènement
   */
  public String getNom(){
    return this.nom;
  }

  /**
   * Méthode getDate revoit la date de l'évènement
   * 
   * @return la date de l'évènement
   */
  public int getDate(){
    return this.date;
  }

  /**
   * Méthode toString revoit une chaine de caractère contenant le nom de l'évènement et la date
   * 
   * @return une chaine de caractère contenant le nom de l'évènement et la date
   */
  public String toString(){
    if(this.dateVisible)
      return this.date + " -> " + this.nom;
    else
      return "??? -> " + this.nom;
  }

  /**
   * Méthode retourner permet de retourner la carte
   */
  public void retourner() {
      this.dateVisible = !this.dateVisible;
  }
}
