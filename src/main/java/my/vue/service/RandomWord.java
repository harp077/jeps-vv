package my.vue.service;

import java.io.File;
import java.util.Date;
import java.security.SecureRandom;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.JTextField;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
//import java.util.Random;

@Service
@Lazy(false)
public class RandomWord {

    //public static Random mygen = new Random(new Date().getTime());
    public SecureRandom mygen = new SecureRandom();
    public char[] alfavit;

    //@Inject
    //private EjbDaoJDBC ejbDaoJDBC;



    public String generate(Boolean useSpecial, String typeAlfavit, int numSymbols) {
        String rez = "";
        String result = "";
        int ti = 0;
        mygen.setSeed(new Date().getTime());
        mygen.setSeed(System.currentTimeMillis());
        if (!useSpecial) {
            alfavit = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            //System.out.println(alfavit.length);
        } else {
            alfavit = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '[', ']', '{', '}', '|', ':', ';', '"', ',', '<', '>', '.', '?', '/'};
            //System.out.println(alfavit.length);           
        }
        for (int k = 1; k <= numSymbols; k++) {
            ti = mygen.nextInt(alfavit.length);
            if ((k == 1) & (!useSpecial)) {
                ti = mygen.nextInt(alfavit.length - 10);
            }
            if ((k == 1) & (useSpecial)) {
                ti = mygen.nextInt(alfavit.length - 40);
            }
            String buf = new String(alfavit, ti, 1);
            if ((typeAlfavit.equals("aA-zZ_0-9")) && (mygen.nextInt(2) == 1)) {
                buf = buf.toUpperCase();
            }
            rez = rez + buf;
        }
        result = rez;
        if (typeAlfavit.equals("A-Z_0-9")) {
            result = result.toUpperCase();
        }
        //tf.setText(result);
        return result;
    }// private void

}
