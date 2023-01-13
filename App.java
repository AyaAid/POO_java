import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import compare.Compare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import model.Contact;

/**Nous executons la fonction main au sein de notre classe App  qui execute  la fonction afficherMenu() :
*Cette fontion vide affiche  une chaine de caractère des choix disponibles :
*
*<p>Cette fonction initialise un tableau de chaine de caractère 
*La méthode add() est utilisé pour ajouter un élément dans un Set Collection, le tableau étant un element SetCollection tout va bien
*Nous faisons une boucle for qui pour chaque element de la liste symboliser par String menus l'affiche depuis menu qui est le tableau
*
*Afin de traiter les choix de l'utilisateur nous utiliseront l'instruction switch qui aura pour attribut ce que l'utilisateur écrira et qui est symbolisé par la varible choix;
* 
*Nous intitialisons une variable choix qui est une chaine de caractère afin que l'utilisateur puisse écrire (scanner)ses réponses à la ligne suivante (nextLine);
*La classe Scanner permet à un utilisateur d’écrire du texte et au programme de lire ce texte afin de pouvoir dans le cas exécuter les fonctions qui y sont associées
*</p>
*
*
*
*<h1> Fonction ajouterContact() </h1>
*
*
*<p></p>
*/

public class App {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        afficherMenu();
        String choix = scanner.nextLine();

        switch(choix){
            case "1" :
                ajouterContact();
                break;
            case "2" :
                listerContact();
                break;
            case "3":
                System.out.println("Quel est le mail du contact que vous voulez modifier ?");
                modifierContact(scanner.nextLine());
                break;
            case "4":
                System.out.println("Quel est le mail du contact que vous voulez supprimer ?");
                supprimerContact(scanner.nextLine());
                break;
            case "5":
                trierNom();
                break;
            case "6":
                trierDateNaissance();
                break;
            case "7":
                trierMail();
                break;
            case "8":
                System.out.println("Prenom recherché ?");
                rechercheParPrenom(scanner.nextLine());
                break;
            case "q" :
                return;
             default :
                System.out.println("...");
                break;
        }
    }

    /**
     * <h1> trierMail() </h1>
     * <p>
     * La méthode "trierMail()" est une méthode statique qui permet de trier la liste des objets "Contact" selon leur adresse mail. Elle utilise la méthode "lister()" de la classe Contact pour récupérer la liste des objets Contact stockés dans un fichier CSV.
     * Elle utilise ensuite la classe "Collections" pour trier la liste en utilisant un objet "Comparator" anonyme qui surcharge la méthode "compare()" pour trier les objets Contact par ordre alphabétique de leur adresse mail.
     * Enfin, elle utilise la méthode "println()" pour afficher la liste triée sous forme de chaîne de caractères en utilisant la méthode "toString()" de la classe "ArrayList".
     * La méthode gère également l'exception IOException pour gérer les erreurs de lecture ou d'écriture de fichier.
     * </p>

     */

    private static void trierMail() throws IOException{
        try{
        ArrayList<Contact> list = Contact.lister();
        Collections.sort(list, new Comparator<Contact>(){
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getMail().compareTo(c2.getMail());
            }
    });
        System.out.println(list.toString());}
        catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * 
     * <h1> rechercheParPrenom </h1>
     * 
     * <p>
     * La méthode "rechercheParPrenom(String prenom)" est une méthode statique qui permet de filtrer les objets "Contact" dont le prénom commence par la chaîne de caractères passée en paramètre. Elle utilise la méthode "lister()" de la classe Contact pour récupérer la liste des objets Contact stockés dans un fichier CSV.
     * Elle utilise ensuite les fonctionnalités de la classe Stream pour filtrer les objets Contact en utilisant la méthode "filter()" pour vérifier si le prénom de chaque objet Contact commence par la chaîne de caractères passée en paramètre. Les objets qui répondent à cette condition sont ajoutés à une nouvelle liste qui est ensuite affichée avec la méthode println.
     * La méthode "trierNom()" est une méthode statique qui permet de trier la liste des objets "Contact" selon leur nom. Elle utilise la méthode "lister()" de la classe Contact pour récupérer la liste des objets Contact stockés dans un fichier CSV. Elle utilise ensuite la classe "Collections" pour trier la liste en utilisant le comparator "ComparatorNom" défini précédemment dans la classe Contact.
     * Enfin, elle utilise une structure try-with-resources pour écrire les contacts triés dans le fichier "contacts.csv" en remplaçant son contenu précédent. Pour chaque contact de la liste triée, elle utilise la méthode "println()" pour afficher le contact et la méthode "toString()" pour écrire le contact dans le fichier.
     * </p>
     * 
     */

    private static void rechercheParPrenom(String prenom) throws IOException{
    ArrayList<Contact> list = Contact.lister();

    List<Contact> contactRecherche = list.stream()
        .filter((contact) -> contact.getPrenom().startsWith(prenom))
        .toList();
    System.out.println(contactRecherche);   
    }

    private static void trierNom() throws IOException {
    ArrayList<Contact> liste = Contact.lister();
    Collections.sort(liste, Contact.ComparatorNom);
    try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", false)))){
    for(Contact contact : liste){
        System.out.println(contact);
        pw.println(contact.toString());
    }
    }
    
    }

/**
 * 
 * <h1>trierDateNaissance<h1>
 * 
 * <p>
 * La méthode "trierDateNaissance()" est une méthode statique qui permet de trier la liste des objets "Contact" selon leur date de naissance. Elle utilise la méthode "lister()" de la classe Contact pour récupérer la liste des objets Contact stockés dans un fichier CSV. Elle crée également une instance de la classe "Compare" qui est utilisée pour faire le tri de la liste de contacts.
 * Elle utilise ensuite la classe "Collections" pour trier la liste en utilisant l'objet "compare" comme comparateur. Enfin, elle utilise une structure try-with-resources pour écrire les contacts triés dans le fichier "contacts.csv" en remplaçant son contenu précédent. Pour chaque contact de la liste triée, elle utilise la méthode "println()" pour afficher le contact et la méthode "toString()" pour écrire le contact dans le fichier.
 * </p>
 */

 private static void trierDateNaissance() throws IOException {
        Compare compare = new Compare();
        ArrayList<Contact> liste = Contact.lister();
        Collections.sort(liste, compare);
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", false)))){
            for(Contact contact : liste){
                System.out.println(contact);
                pw.println(contact.toString());
            }
        }catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * <h1>modifierConctact </h1>
     * 
     * <p>
     * La méthode "modifierContact" prend en paramètre une chaine de caractères "mail" qui correspond à l'adresse mail d'un contact à modifier. Elle crée une liste de contacts en utilisant la méthode "lister()" de la classe Contact. Ensuite, elle utilise un try-with-resources pour écrire dans un fichier "contacts.csv" en écrasant les données précédentes. Pour chaque contact dans la liste, si l'adresse mail correspond à celle passée en paramètre, elle utilise la classe Scanner pour demander à l'utilisateur de saisir de nouvelles valeurs pour le nom, prénom, adresse mail, numéro de téléphone et date de naissance. Elle utilise les setters correspondants pour mettre à jour les valeurs du contact. Si une exception "ParseException" est levée, elle affiche un message d'erreur pour indiquer que la valeur saisie est incorrecte. Enfin, elle écrit chaque contact modifié dans le fichier "contacts.csv" et affiche un message indiquant que le contact a été modifié.
     * 
     * </p>
     */
 private static void modifierContact(String mail) throws IOException{
        ArrayList<Contact> liste = Contact.lister();
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", false)))){
            for(Contact contact : liste){
                if(contact.getMail().equals(mail)){
                    System.out.println("Saisir le nom");
                    System.out.println(contact.getNom());
                    contact.setNom(scanner.nextLine());
                    System.out.println("Saisir le prenom");
                    System.out.println(contact.getPrenom());
                    contact.setPrenom(scanner.nextLine());
                    while(true){
                        try{
                            System.out.println("Saisir le mail");
                            System.out.println(contact.getMail());
                            String scan = scanner.nextLine();
                            if(scan.equals(null)){
                                contact.setMail(contact.getMail());
                            }else{
                                contact.setMail(scan);
                            }
                            
                            break;
                        } catch(ParseException e){
                            System.out.println("Format du mail incorrect");
                        }
                    }
                    while(true){
                        try{
                            System.out.println("Saisir le telephone");
                            System.out.println(contact.getTelephone());
                            contact.setTelephone(scanner.nextLine());
                            break;
                        } catch(ParseException e){
                            System.out.println("Numéro de téléphone incorrecte");
                        }
                    }

                    while(true){
                        try{
                            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                            System.out.println("Saisir date de naissance");
                            System.out.println(f.format(contact.getDateNaissance()));
                            contact.setDateNaissance(scanner.nextLine());
                            break;
                        } catch(ParseException e){
                            System.out.println("Date de naissance incorrecte");
                        }
                    }
                    
                }
                    pw.println(contact.toString());
            }
        }catch (Exception e) {
            System.out.println("Erreur pour modifier le contact");
        }
        System.out.println("Contact Modifié");

    }

 private static void supprimerContact(String mail){
        ArrayList<Contact> liste;
        try {
            liste = Contact.lister();
        } catch (IOException e1) {
           
            e1.printStackTrace();
            return;
        }
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", false)))){
            for(Contact contact : liste){
                if(!contact.getMail().equals(mail)){
                    pw.println(contact.toString());

                }
            }
        }catch (Exception e) {
            System.out.println("Erreur pour supprimer le contact");
        }
        System.out.println("contact supprimé");
    }

    private static void listerContact() {
        try {
            ArrayList<Contact> liste = Contact.lister();

            for (Contact contact : liste) {
                System.out.println(contact.getPrenom() + " " + contact.getNom());
            }
        } catch (IOException e) {
            System.out.println("Erreur pour lister les contacts");
        }

    }

    private static void ajouterContact() {
        Contact c = new Contact();
        System.out.println("Saisir le nom");
        c.setNom(scanner.nextLine());
        System.out.println("Saisir le prenom");
        c.setPrenom(scanner.nextLine());
        while(true){
            try{
                System.out.println("Saisir le mail");
                c.setMail(scanner.nextLine());
                break;
            } catch(ParseException e){
                System.out.println("Format du mail incorrect");
            }
        }
        while(true){
            try{
                System.out.println("Saisir le telephone");
                c.setTelephone(scanner.nextLine());
                break;
            } catch(ParseException e){
                System.out.println("Numéro de téléphone incorrecte");
            }
        }

        while(true){
            try{
                System.out.println("Saisir date de naissance");
                c.setDateNaissance(scanner.nextLine());
                break;
            } catch(ParseException e){
                System.out.println("Date de naissance incorrecte");
            }
        }
        try {
            c.enregistrer();
            System.out.println("Contact enregistré");
        } catch (IOException e) {
            System.out.println("Contact non enregistré");
        }
         
       
    }
    private static void afficherMenu(){
        ArrayList<String> menu = new ArrayList<>();
        menu.add("--Menu--");
        menu.add("1 - Ajouter un contact");
        menu.add("2 - Lister les contacts");
        menu.add("3 - Modifier un contact");
        menu.add("4 - Supprimer un contact");
        menu.add("5 - Trier les contacts par nom");
        menu.add("6 - Trier les contacts par date de naissance");
        menu.add("7 - Trier les contacts par mail");
        menu.add("8 - Rechercher un contact");
        menu.add("Q - Quitter");
        for(String menus : menu){
            System.out.println(menus);
        }
    };
}