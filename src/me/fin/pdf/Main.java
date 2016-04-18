package me.fin.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {


    // COMMENT
    public static void main(String[] args) throws IOException, DocumentException {

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Picture2PDF");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Wähle Bilder");
        fileChooser.setApproveButtonText("PDF Erstellen");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Bilder", "jpg", "jpeg", "png", "gif", "bmp"));
        fileChooser.setDragEnabled(true);


        fileChooser.setMultiSelectionEnabled(true);
        File[] filesArray;
        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
            filesArray = fileChooser.getSelectedFiles();
        } else {
            return;
        }

        File file = null;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            file = new File("bilder" + i + ".pdf");
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return;
                }
                break;
            }
        }

        Document doc = new Document();
        FileOutputStream oFile = new FileOutputStream(file, false);
        PdfWriter.getInstance(doc, oFile);
        doc.open();

        List<File> files = Arrays.asList(filesArray);
        Collections.sort(files);

        for (File f : files) {
            doc.setMargins(0, 0, 0, 0);
            doc.newPage();
            Image img = Image.getInstance(f.getPath());
            if (img.getHeight() < img.getWidth()) {
                img.setRotationDegrees(90);
            }
            img.scaleToFit(595, 842);
            doc.add(img);
        }
        doc.close();
    }


}
