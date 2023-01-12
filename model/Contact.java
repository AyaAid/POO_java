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

public class Contact{
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

    public void setDateNaissance(String dateNaissance) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.dateNaissance = format.parse(dateNaissance);
     
        
    }

    public void enregistrer()throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", true)));
        try{
            pw.println(this.toString());
        } finally {
            pw.close();
        }
    }

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
