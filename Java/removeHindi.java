import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xwml.usermodel.*;

public class RemoveHindiFromDocx {

    public static void main(String[] args) throws IOException {
        String filePath = "path/to/your/file.docx"; // Replace with your file path
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try (FileInputStream inputStream = new FileInputStream(file);
             FileOutputStream outputStream = new FileOutputStream(new File(filePath + "_no_hindi.docx"))) {

            XWPFDocument docx = new XWPFDocument(inputStream);

            // Iterate through paragraphs and remove Hindi characters
            for (XWPFParagraph paragraph : docx.getParagraphs()) {
                StringBuilder newText = new StringBuilder();
                for (char c : paragraph.getText().toCharArray()) {
                    if (!isHindiCharacter(c)) {
                        newText.append(c);
                    }
                }
                paragraph.setText(newText.toString());
            }

            docx.write(outputStream);
            System.out.println("Hindi characters removed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isHindiCharacter(char c) {
        // Unicode range for Hindi characters
        return (int) c >= 0x0900 && (int) c <= 0x097F;
    }
}
