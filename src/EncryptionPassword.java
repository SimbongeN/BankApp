import java.math.BigInteger;
import java.security.*;
import java.util.Random;


public class EncryptionPassword {
    private String saltString;
    private String hashedPassword;
    private String password;

    public EncryptionPassword(String password){
        this.password = password;
        setSalt();
        setHashPassword();
    }

    public EncryptionPassword(String password, String SaltValue){
        this.password = password;
        saltString = SaltValue;
        setHashPassword();
    }

    public void setHashPassword(){
        String toEncrypt = this.saltString+this.password;
        try {
            MessageDigest SHA3 = MessageDigest.getInstance("SHA-384");
            byte[] messageDigest = SHA3.digest(toEncrypt.getBytes());
            BigInteger bigInt = new BigInteger(1,messageDigest);
            hashedPassword = bigInt.toString(16);
            
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setSalt(){
        String randomString= "";
        Random random = new Random();
        for (int i = 0; i < random.nextInt(5,10); i++) {
            Character randomCharacter = (char) random.nextInt(33,127);
            randomString += String.valueOf(randomCharacter);
        }
        saltString = randomString;
        System.out.println(randomString);
    }

    public String getEncryptPassword(){
        return hashedPassword;
    }
    public String getSalt(){
        return saltString;
    }
}
