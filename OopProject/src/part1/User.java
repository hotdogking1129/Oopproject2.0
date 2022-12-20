package part1;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class User {
	private String userName;
	private String noMatrics;
	private String password;
	
	//constructor of User
	User(String userName,String noMatrics,String password){
		this.userName = userName;
		this.noMatrics = noMatrics;
		this.password = password;
	}
	
	//Setters
	public void setName(String userName){
		this.userName = userName;
	} 
	
	public void setNoMatrics(String noMatrics){
		this.noMatrics = noMatrics;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	//Getters
	public String getName() {
		return userName;
	}
	
	public String getNoMatrics() {
		return noMatrics;
	}
	
	public String getPassword() {
		return password;
	}
	
	//Encrypting password and return String of username, nomatric and encryted password
	public String toFile() {
		String encPassword = "";
		try 
        {
            String text = getPassword();
            String key = "Bar12345Bar12345"; // 128 bit key
            // Create key and cipher
            SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b: encrypted) {
                sb.append((char)b);
            }
            // the encrypted String
            encPassword = sb.toString();
        }catch(Exception e) {
            e.printStackTrace();
        }
    
		return getName() + "/" + getNoMatrics() + "/" + encPassword;
	}
	
}