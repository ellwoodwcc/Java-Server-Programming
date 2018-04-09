/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSPOneLiner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
/**
 *
 * @author Steve
 */
public class OneLinerUtils2 {

    ArrayList<String> oneLinerArr = new ArrayList<>();
    int nextIndex = 0;

        public  OneLinerUtils2(ServletContext sctx,ServletConfig scfg) {
        String oneLinerFilename = scfg.getInitParameter("oneLinerFilename");

        String fullPath = sctx.getRealPath("/WEB-INF/" + oneLinerFilename);
        System.out.println("fullPath=" + fullPath);
        try {
            File f = new File(fullPath);
            try (Scanner scan = new Scanner(f)) {
                while (scan.hasNextLine()) {
                    String s = scan.nextLine().trim();
                    if (s.length() > 0) {
                        oneLinerArr.add(s);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error in init(): " + e);
        }
    }
    
    public String getNext()
    {
        String retVal = oneLinerArr.get(nextIndex++);
        if (nextIndex >= oneLinerArr.size())
            nextIndex = 0;
        return retVal;
        
    }
    
    /**
     *
     * @param scfg
     * @param sctx
     * @return
     */
    public static String init(ServletConfig scfg, ServletContext sctx) {
    //ServletConfig sc = getServletConfig();
        //ServletContext sctx = getServletContext();
        String oneLinerFilename = sctx.getInitParameter("oneLinerFilename");

        String fullFilePath = sctx.getRealPath("/WEB-INF/" + oneLinerFilename);
        //System.out.println("fullPath="+ fullPath);
        return fullFilePath;
    }
}
    
    
    
 

