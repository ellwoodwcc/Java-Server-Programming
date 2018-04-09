/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database_Person;

//import Database_Person.DB_Person;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;


public class DB_PersonCollection {
    private static int convert2Int(String s)
    {
        int retval=-1;
        try
        {
            if (s != null)
            {
                retval = Integer.parseInt(s.trim());
            }
        }
        catch (NumberFormatException e)
        {
            // not much that you can do here
        }
        return retval;
    }
    public static String update(Statement statement, HttpServletRequest request)
    {//what is this pulling?
        String errorMessage="";
        String action = request.getParameter("action");//clicks button like add remove
        if (action != null ) {//why is this always null first time through?
            String fname = request.getParameter("firstname");//these are coming from jsp top of page only
            String lname = request.getParameter("lastname");
            String ecolor = request.getParameter("eyecolor");
            String hcolor = request.getParameter("haircolor");
            String height = request.getParameter("xyheight");
            Integer weight = convert2Int(request.getParameter("zweight"));
            //int weight = convert2Int(request.getParameter("weight"));
            DB_Person person = new DB_Person(fname, lname, ecolor, hcolor, height, weight);//construct class of dbperson named person

            String strIndex;
            int index;
//why error message?
            switch (action) {
                case "Clear List":
                    errorMessage= DB_Person.remove(-1, statement);//how does the -1 work?
                    break;//entering delete Table; with no where clause static method
                case "add"://nonstatic calls person just above with fname...person has all the paramters for insert and update
                    errorMessage = person.insert(statement);
                    break;
                case "remove"://if trying tor emove, has a static method that needs index
                    strIndex = request.getParameter("index");
                    index = Integer.parseInt(strIndex);//because it's all text 
                    errorMessage = person.remove(index, statement);//static method passes in index of what want to remove. Can use general class that lacks particular parameters because remove doesn't need to call a sql query
                    break;//creates a where clause in sql delete statement
                case "update":
                    strIndex = request.getParameter("index");
                    index = Integer.parseInt(strIndex);
                    errorMessage = person.update(index, statement);//index will tell ou wich book to delete
                    break;//update statement. index will id the row based on primary key int from person table
            }

        }//jump here if action=null but what action are we referring to since not gone to jsp?
        //is this the same array list as in dbperson?
        ArrayList<DB_Person> personCollection = new ArrayList<>();//empty for now Initializing just before goes to rs.next
        errorMessage += DB_Person.getPersons(statement, personCollection);//get the persons stored in the table
        //queries select * from table. this then gets added to arraylist/ array list will be filled from this method
        request.setAttribute("PersonCollectionA", personCollection);
        //get personCollection from above then stuffs it into request with  from jsp"PersonCollection"
        return errorMessage;
    }

}
//I did this in mysql russet:
//create table PersonCollection (
//      fname text,
//      lname text,
//      ecolor text,
//      hcolor text,
//      height text,
//      weight text,
//      id int Primary Key Auto_Increment);
//he couldn't use index for this auto-incremeant row becuase its reserved in sql for making a column efficienty accessed
//why doesn't my table autoincrement?


//create table PersonCollection2 (
//      fname text,
//      lname text,
//      ecolor text,
//      hcolor text,
//      height text,
//      weight int,
//      id int Primary Key Auto_Increment); can't use index because mysql reserves that word for indexing and quick access