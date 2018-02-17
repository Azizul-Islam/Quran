package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Surah {
	private int surahId;
	private int ayahCount;
	private String surahName;
	private Connection conn;
	
	
	
	
	public int getSurahId() {
		return surahId;
	}


	public void setSurahId(int surahId) {
		this.surahId = surahId;
	}


	public int getAyahCount() {
		return ayahCount;
	}


	public void setAyahCount(int ayahCount) {
		this.ayahCount = ayahCount;
	}


	public String getSurahName() {
		return surahName;
	}


	public void setSurahName(String surahName) {
		this.surahName = surahName;
	}


	public Surah() {
		conn = SqliteConnection.getConnection();
		if(conn == null) {
			System.exit(1);
		}
	}
	
	
	public boolean isConnected() {
		try {
			if(conn.isClosed()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public List<Surah> getAllSurahName(){
		PreparedStatement prepareStatement = null;
		ResultSet result;
		String query = "select * from surah_name";
		try {
			prepareStatement = conn.prepareStatement(query);
			result = prepareStatement.executeQuery();
			List<Surah> nameList = new ArrayList<>();
			while(result.next()) {
				Surah surah = new Surah();
				
				int surahNumber = result.getInt("surah_no");
				String surahNmae = result.getString("name_bangla");
				int ayahCount = result.getInt("ayah_number");
				surah.setSurahId(surahNumber);
				surah.setAyahCount(ayahCount);
				surah.setSurahName(surahNmae);
				
				//System.out.println("Suran Name = "+surahNmae);
				nameList.add(surah);
			}
			
			return nameList;
			
		}catch(Exception e) {
			
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
