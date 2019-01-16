package security;

import java.security.SecureRandom;

public class SharedSecret {

    private static byte[] secret;
    public static byte[] getSharedKey() {
        System.out.println("******************* IMPORTANT ******************'");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("**** REMOVE FIXED SECRET BEFORE PRODUCTION *******");
        System.out.println("****      See security.SharedSecret        *******");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //REMOVE BEFORE PRODUCTION
        if (true) {
            return "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".getBytes();
        }
        if (secret == null) {
            secret = new byte[32];
            new SecureRandom().nextBytes(secret);
        }
        return secret;
    }

}
