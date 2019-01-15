/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import org.mindrot.jbcrypt.BCrypt;

public class Hashing {

    public static String createHash(String psw) {
        return BCrypt.hashpw(psw, BCrypt.gensalt());
    }

    public static boolean validatePassword(String psw, String hashPsw) {
        return BCrypt.checkpw(psw, hashPsw);
    }

}
