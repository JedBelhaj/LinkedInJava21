package com.fsb.linkedin.utils;

import com.fsb.linkedin.DAO.AccountDAO;
import com.fsb.linkedin.entities.Experience;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.entities.Project;
import com.fsb.linkedin.entities.Qualification;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CVGenerator {
    static final float MAIN_FONT_SIZE = 10;
    static final float TITLE_FONT_SIZE = 20;
    static final float LEADING = 16.0f;
    static final float OFFSET_X = 50;
    static PDDocument document = new PDDocument();
    static PDPage workingPage;
    static final int MAX_PAGE_LINES = 40;
    static int currentLines = 0;
    static PDPageContentStream contentStream;
    static PDFont helveticaFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    static PDFont helveticaFontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    public static void createCV() throws IOException {
        AccountDAO.loadUser("gojosatoru@gmail.com");
        PersonalAccount p = PersonalAccount.getInstance();

        PDPage newPage = new PDPage();
        document.addPage(newPage);
        workingPage = newPage;

        PDImageXObject profilePicture = PDImageXObject.createFromByteArray(document,p.getProfilePicture(), "profilePicture");

        contentStream = new PDPageContentStream(document,workingPage);
        contentStream.drawImage(profilePicture, workingPage.getBBox().getWidth()-100,workingPage.getBBox().getHeight()-80,50,50);

        contentStream.beginText();
        contentStream.setFont(helveticaFont, 18);
        contentStream.setLeading(LEADING);

        contentStream.newLineAtOffset(OFFSET_X, workingPage.getBBox().getHeight()-50);

        putNewLine();
        putTitle("Personal Information:");
        putNewLine();
        putLine("    First name: "+p.getFirstName());
        putLine("    Last name: "+p.getLastName());
        putLine("    Email: "+p.getEmail());
        putLine("    Phone Number: "+p.getPhoneNumber());
        putLine("    Date of Birth: "+formatDate(p.getDateOfBirth()));
        putLine("    Country of Origin:"+p.getCountry());
        putLine("    Gender:"+p.getGender());
        putNewLine(2);
        addQualifications(p.getQualifications());
        addExperiences(p.getExperiences());
        addProjects(p.getProjects());

        contentStream.endText();
        contentStream.close();

        document.save("C:\\Users\\jedbe\\Downloads\\cv.pdf");
        document.close();
    }
    public static void main(String[] args) throws IOException {
        createCV();
    }
    public static String formatDate(LocalDate date){
        return date.getDayOfMonth() + " " + date.getMonth().toString().toLowerCase()+ " " + date.getYear();
    }
    public static List<String> getLines(String paragraph) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] words = paragraph.split(" ");
        int lineLength = 90;
        int counter = 0;
        StringBuilder line = new StringBuilder("        ");
        for(int i = 0; i< words.length; i++ ){
            String word = words[i];
            counter += word.length();
            if(counter>lineLength || i == words.length-1){
                lines.add(line.toString());
                counter = word.length();
                line = new StringBuilder("        ");
            }
            line.append(word).append(" ");
        }
        return lines;
    }
    public static void newPage() throws IOException {
        contentStream.endText();
        contentStream.close();
        PDPage newPage = new PDPage();
        document.addPage(newPage);
        workingPage = newPage;
        contentStream = new PDPageContentStream(document, workingPage);
        contentStream.beginText();
        contentStream.setFont(helveticaFont, MAIN_FONT_SIZE);
        contentStream.setLeading(LEADING);
        contentStream.newLineAtOffset(OFFSET_X, workingPage.getBBox().getHeight()-70);

    }

    public static void putLine(String line) throws IOException {
        currentLines ++;
        if (currentLines>MAX_PAGE_LINES){
            newPage();
            currentLines = 1;
        }
        contentStream.showText(line);
        contentStream.newLine();
    }
    public static void putNewLine() throws IOException {
        putLine("");
    }
    public static void putNewLine(int n) throws IOException {
        for (int i = 0; i < n; i++){
            putLine("");
        }
    }
    public static void putTitle(String line) throws IOException {
        contentStream.setFont(helveticaFontBold, TITLE_FONT_SIZE);
        putLine(line);
        contentStream.setFont(helveticaFont, MAIN_FONT_SIZE);
    }
    public static void addQualifications(List<Qualification> qualifications) throws IOException {
        putTitle("Qualifications");
        for (Qualification q: qualifications){
            putNewLine();
            putLine("    Title: "+q.getTitle());
            putLine("    Institution: "+q.getInstitution());
            putLine("    Technology: "+q.getTechnology());
            putLine("    Start Date: "+formatDate(q.getStartDate()));
            putLine("    Finish Date: "+formatDate(q.getFinishDate()));
            putLine("    Description: ");
            for (String line : getLines(q.getDescription())){
                putLine(line);
            }
            putNewLine();
        }
        putNewLine();
    }
    public static void addExperiences(List<Experience> experiences) throws IOException {
        if (currentLines > 30) {
            newPage();
            currentLines = 0;
        }
        putTitle("Experiences");
        for (Experience e: experiences){
            putNewLine();
            putLine("    Title: "+e.getTitle());
            putLine("    Mission: "+e.getMission());
            putLine("    Type: "+e.getType());
            putLine("    Technology: "+e.getTechnology());
            putLine("    Start Date: "+e.getStartDate());
            putLine("    Finish Date: "+formatDate(e.getFinishDate()));
            putLine("    Description: ");
            for (String line : getLines(e.getDescription())){
                putLine(line);
            }
            putNewLine();
        }
        putNewLine();
    }

    public static void addProjects(List<Project> projects) throws IOException {
        if (currentLines > 30) {
            newPage();
            currentLines = 0;
        }
        putTitle("Projects");
        for (Project e: projects){
            putNewLine();
            putLine("    Title: "+e.getTitle());
            putLine("    Start Date: "+e.getStartDate());
            putLine("    Finish Date: "+formatDate(e.getFinishDate()));
            putLine("    Description: ");
            for (String line : getLines(e.getDescription())){
                putLine(line);
            }
            putNewLine();
        }
        putNewLine();
    }
}