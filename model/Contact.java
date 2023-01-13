package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import compare.Compare;


/**
 * <h1>La classe contact </h1>
 * 
 * <p>Ont creer une classe contact qui implémente l'interface "Comparable" qui  définit une méthode appelée "compareTo" qui permet de comparer des objets entre eux. En utilisant "implements", la classe "Contact"  fournit une implémentation de la méthode "compareTo" qui permet de comparer des objets "Contact" entre eux.
 * On initialise des variables de chaine de caractère vide dont on a besoin pour le fonctionnement de notre app
 * On fait les différents getter et setter de ces variables afin de les récupérer ou de les modifier. 
 * Il y a également une propriété static final "SEPARATEUR" qui est utilisée pour séparer les différentes propriétés d'un objet "Contact" lors de l'enregistrement ou de la lecture des informations à partir du fichier CSV.

 * </p>
 * 
 * <p>
 * Pour setDateNaissance() setMail() setTelephone() throws ParseException signfie qu'elle peut lever une exception de type "ParseException" si une erreur se produit lors de l'exécution de la méthode.
 * 
 * 
 */
 

public class Contact implements Comparable<Contact>{
    private static final String SEPARATEUR = ";";
    private String nom;
    private String prenom;
    private String mail;
    private String telephone;
    private Date dateNaissance;

    public String getNom(){
        return this.nom;
    }

    public void setNom(String valeur){
        this.nom = valeur;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    /**
     * <h1>setMail()</h1>
    * <p>
    *  La méthode setMail permet de définir la propriété "mail" d'un objet "Contact". Elle prend en paramètre une chaîne de caractères qui représente l'adresse mail.
    La méthode  déclare un objet "Pattern" nommé "pat" en utilisant la méthode compile() avec une expression régulière qui vérifie si la chaîne passée en paramètre est un format valide d'adresse mail. Cette expression régulière est utilisée pour vérifier si la chaîne passée en paramètre contient un caractère "@" suivi de deux ou plus caractères, suivi d'un "." suivi de 2 à 10 caractères.
    Ensuite, l'objet "pat" est utilisé pour créer un objet "Matcher" nommé "matcher" qui est utilisé pour vérifier si la chaîne passée en paramètre correspond au format valide d'adresse mail défini par l'expression régulière.
    Si la chaîne passée en paramètre correspond au format valide, la propriété "mail" est définie avec la valeur passée en paramètre. Sinon, une exception "ParseException" est levée avec un message indiquant que le format de l'adresse mail est incorrecte.
    * </p>
     */


    public void setMail(String mail) throws ParseException{
        Pattern pat = Pattern.compile("^[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]{2,}\\.[a-zA-Z.]{2,10}$");
        Matcher matcher = pat.matcher(mail);
        if(matcher.matches()){
            this.mail = mail;
        }
        else{
            ParseException e = new ParseException("Le format du mail est incorrecte", 0);
            throw e;
        }
    }

    public String getTelephone() {
        return telephone;
    }

    /**  
     * <h1>setTelephone()</h1>
     * 
     * <p>
     * La méthode setTelephone() permet de définir la propriété telephone de l'objet Contact. Elle prend en paramètre une chaîne de caractères qui représente le numéro de téléphone.
     * La méthode commence par déclarer un objet "Pattern" nommé "pat" en utilisant la méthode compile() avec une expression régulière qui vérifie si la chaîne passée en paramètre est un format valide de numéro de téléphone. Cette expression régulière est utilisée pour vérifier si la chaîne passée en paramètre contient un "0" ou un "0033" ou "+33" suivi de d'un chiffre entre 1 et 9 et suivi de 8 chiffres.
     * Ensuite, l'objet "pat" est utilisé pour créer un objet "Matcher" nommé "matcher" qui est utilisé pour vérifier si la chaîne passée en paramètre correspond au format valide de numéro de téléphone défini par l'expression régulière.
     * Si la chaîne passée en paramètre correspond au format valide, la propriété "telephone" est définie avec la valeur passée en paramètre. Sinon, une exception "ParseException" est levée avec un message indiquant que le format du numéro est incorrecte.
     * 
     * </p>
     * */



    public void setTelephone(String telephone) throws ParseException{
        Pattern pat = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
        Matcher matcher = pat.matcher(telephone);
        if(matcher.matches()){
            this.telephone = telephone;
        }
        else{
            ParseException e = new ParseException("Le format du numéro est incorrecte", 0);
            throw e;
        }
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    /** * <h1>setDateNaissance</h1>
    * <p>
    * "SimpleDateFormat" est une classe de la bibliothèque de Java qui permet de formater et d'analyser les dates. Le constructeur "new SimpleDateFormat("dd/MM/yyyy")" permet de spécifier le format de la date souhaité.
    *  parse sert à convertir une chaîne de caractères passée en paramètre "dateNaissance" en un objet de type "Date"
    *  
    * </p> 
    */


    public void setDateNaissance(String dateNaissance) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.dateNaissance = format.parse(dateNaissance);
     
        
    }

    /** 
        * <h1> enregistrer() </h1>
        * <p>
        * La méthode enregistrer() permet d'enregistrer les informations de l'objet "Contact" courant dans un fichier CSV nommé "contacts.csv". Elle lève une exception "IOException" si il y a un problème lors de l'écriture dans le fichier.
        * La méthode commence par créer un objet "PrintWriter" nommé "pw" en utilisant un constructeur qui prend en paramètre un objet "BufferedWriter" qui est créé à son tour en utilisant un constructeur qui prend en paramètre un objet "FileWriter". Le constructeur de "FileWriter" prend en paramètre le nom du fichier csv et un booléen "true" qui indique que le fichier est ouvert en mode "append" pour permettre l'écriture à la fin du fichier sans écraser les données existantes.
        * La méthode utilise ensuite l'objet "pw" pour écrire dans le fichier CSV une ligne qui contient les informations de l'objet "Contact" courant en utilisant la méthode "toString()" de l'objet. Enfin, la méthode ferme le fichier en utilisant la méthode "close()" de l'objet "pw".
        * Cette méthode permet d'enregistrer les informations de l'objet courant dans un fichier CSV en utilisant la méthode toString() pour convertir les informations en chaine de caractère, dans un mode append pour éviter d'écraser les données déjà existantes dans le fichier.
        * </p>
    */

    public void enregistrer()throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", true)));
        try{
            pw.println(this.toString());
        } finally {
            pw.close();
        }
    }

    /*
    * <h1>lister()</h1>
    * <p>
    * La méthode "lister()" est une méthode statique de la classe "Contact" qui permet de lire toutes les lignes d'un fichier CSV nommé "contacts.csv" et de les stocker dans une ArrayList d'objets "Contact". Elle lève une exception "IOException" si il y a un problème lors de la lecture du fichier.
    * La méthode commence par déclarer une ArrayList d'objets "Contact" nommée "list" qui est utilisée pour stocker les objets "Contact" créés à partir des informations lues dans le fichier CSV.
    * Ensuite, elle crée un objet "BufferedReader" nommé "read" en utilisant un constructeur qui prend en paramètre un objet "FileReader" qui est créé à son tour en utilisant un constructeur qui prend en paramètre le nom du fichier CSV. Le constructeur de "FileReader" ouvre le fichier pour la lecture.
    * La méthode utilise ensuite une boucle "while" pour lire toutes les lignes du fichier CSV jusqu'à ce qu'il n'y en ait plus à lire. Pour chaque ligne lue, elle utilise la méthode "split()" pour séparer les informations de la ligne en utilisant le séparateur "SEPARATEUR" défini en tant que propriété statique de la classe "Contact". Ces informations sont ensuite utilisées pour créer un objet "Contact" nommé "c" et pour définir les propriétés de cet objet en utilisant les setters correspondants. Enfin, l'objet "c" est ajouté à la liste "list".
    * La méthode utilise également un bloc "try-catch" pour gérer les exceptions "ParseException" et "IOException". Si une exception "ParseException" est levée, cela signifie que la ligne lue est vide. Si une exception "IOException" est levée, cela signifie qu'il y a eu un problème lors de la lecture du fichier. Dans les deux cas, un message est affiché à l'utilisateur pour indiquer la nature de l'erreur.
    * Enfin, la méthode utilise un bloc "finally" pour fermer le fichier en utilisant la méthode "close()" de l'objet "read" une fois que toutes les lignes ont été lues ou que l'une des exceptions a été levée.
    * </p>
    */

    public static ArrayList<Contact> lister() throws IOException{
        ArrayList<Contact> list = new ArrayList<>();

        BufferedReader read = new BufferedReader(new FileReader("contacts.csv"));
        try {
            String ligne = null;
            while ((ligne = read.readLine()) != null) {
                String[] tab = ligne.split(SEPARATEUR);
                Contact c = new Contact();
                c.setNom(tab[0]);
                c.setPrenom(tab[1]);
                c.setMail(tab[2]);
                c.setTelephone(tab[3]);
                c.setDateNaissance(tab[4]);
                list.add(c);
            }
        } catch (ParseException e) {
            System.out.println("Ligne vide");
        } catch (IOException e) {
            System.out.println("Mauvaise lecture du fichier");
        } finally {
            read.close();
        }
        return list;
    }

    /**
     * <h1>Comparateur nom </h1>
     * 
     * <p>
     * 
     * La méthode "ComparatorNom" est une propriété statique de la classe "Contact" qui implémente l'interface "Comparator" pour la classe "Contact". Cette propriété est utilisée pour comparer les objets "Contact" en fonction de leur nom.
     * La propriété est déclarée comme étant un objet "Comparator" qui prend en paramètre deux objets "Contact" nommés "c1" et "c2". La méthode "compare" est utilisée pour comparer les noms des deux objets. Elle utilise la méthode "getNom()" pour récupérer le nom de chaque objet et la méthode "toLowerCase()" pour convertir les noms en minuscules avant de les comparer en utilisant la méthode "compareTo()".
     * La méthode "compare()" retourne un entier qui indique si l'objet "c1" doit être trié avant ou après l'objet "c2" en fonction de leur nom. Si la valeur de retour est négative, cela signifie que "c1" doit être trié avant "c2", si elle est positive, cela signifie que "c1" doit être trié après "c2" et si elle est nulle, cela signifie que les deux objets ont le même nom.
     * 
     * </p>
     * 
     * <h1>Override
     * <p>
     * @Override est une annotation en Java qui indique que la méthode qui suit est censée surcharger une méthode héritée d'une superclasse ou d'une interface. Cela permet de vérifier à la compilation que la méthode surchargée existe bien dans la superclasse ou l'interface et que la signature de cette méthode (c'est-à-dire son nom, son type de retour et ses paramètres) est correcte. Si la méthode surchargée ne respecte pas ces critères, une erreur de compilation sera générée.
     * En utilisant l'annotation @Override, pas d'erreurs de typographie ou de confusion dans les noms de méthodes, les méthodes surchargées fonctionnent comme prévu. 
     * </p>
     */
   
    public static Comparator<Contact> ComparatorNom = new Comparator<Contact>(){
        @Override
        public int compare(Contact c1, Contact c2){
            return c1.getNom().toLowerCase().compareTo(c2.getNom().toLowerCase());
        };
    };


    @Override
    public int compareTo(Contact c) {
        return this.getNom().compareTo(c.getNom());
    };

    /**
     * <h1>toString()</h1>
     * 
     * La classe StringBuilder permet de construire des chaînes de caractères en plusieurs étapes sans avoir à recréer une nouvelle chaîne à chaque fois. 
     * La méthode utilise un objet de la classe "StringBuilder". Elle utilise les méthodes get pour récupérer les informations de l'objet courant, nom, prénom, mail, téléphone et date de naissance. Elle utilise ensuite la méthode "append()" pour ajouter ces informations à l'objet "StringBuilder" séparées par ";".
     */

    @Override
    public String toString(){
        StringBuilder build = new StringBuilder();
        build.append(this.getNom());
        build.append(SEPARATEUR);
        build.append(this.getPrenom());
        build.append(SEPARATEUR);
        build.append(this.getMail());
        build.append(SEPARATEUR);
        build.append(this.getTelephone());
        build.append(SEPARATEUR);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        build.append(f.format(getDateNaissance()));
        return build.toString();
    }

    
}
    

