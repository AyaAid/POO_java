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
                // trierMail();
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
