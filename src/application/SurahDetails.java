package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurahDetails {
    private int ayahNumber;
    private String arabicText;
    private String banglaText;
    private Connection conn;

    public SurahDetails(){
        conn = SqliteConnection.getConnection();
    }
    public long getAyahNumber() {
        return ayahNumber;
    }

    public void setAyahNumber(int ayahNumber) {
        this.ayahNumber = ayahNumber;
    }

    public String getArabicText() {
        return arabicText;
    }

    public void setArabicText(String arabicText) {
        this.arabicText = arabicText;
    }

    public String getBanglaText() {
        return banglaText;
    }

    public void setBanglaText(String banglaText) {
        this.banglaText = banglaText;
    }

    public List<SurahDetails> getSurahDetails(int surahId) {
        PreparedStatement prepareStatement = null;
        ResultSet result;
        String query = "select  surah_id, verse_id, arabic, bn_bayan from quran where surah_id = " + surahId;
        try {
            prepareStatement = conn.prepareStatement(query);
            result = prepareStatement.executeQuery();
            List<SurahDetails> nameList = new ArrayList<>();
            while (result.next()) {
                SurahDetails surah = new SurahDetails();

                int surahNumber = result.getInt("verse_id");
                String arabicText = result.getString("arabic");
                String banglaText = result.getString("bn_bayan");

                //System.out.println("Suran Name = " + banglaText);
                surah.setAyahNumber(surahNumber);
                surah.setArabicText(arabicText);
                surah.setBanglaText(banglaText);
                nameList.add(surah);
            }

            return nameList;

        } catch (Exception e) {

        }finally {

            if(prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

}
