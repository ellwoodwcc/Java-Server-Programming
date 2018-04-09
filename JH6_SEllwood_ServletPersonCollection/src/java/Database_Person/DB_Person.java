/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database_Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB_Person {

    private String fname;
    private String lname;
    private String eyecolor;
    private String hcolor;
    private String height;
    int weight;//why should this be private and why is index not private?
    int index;

    public DB_Person(String fname, String lname, String ecolor, String hcolor, String height, int weight, int index) {
        this.fname = fname;
        this.lname = lname;
        this.eyecolor = ecolor;
        this.hcolor = hcolor;
        this.height = height;
        this.weight = weight;
        this.index = index;
    }
    //1118 i messed this up for testing it seems to worrk fin witth it so
    public DB_Person(String badname, String lastname, String ecolor, String hcolor, String height, int weight) {//if we submitdbperson wo index use this, but it just adds a -1.
        this(badname, lastname, ecolor, hcolor, height, weight, 99); // index shouldn't be used/is this called with error action? why is -1 first? while its last here
    }//why can't we use this method above instead of this.?//-1 inserted just to fill space, could use null means doesn't know index
    //need something in the last space, but he doesn't know if -1 is used in one of his m ethods to indicate a way of telling sql to autoassign
//why is this needed?  Why can't use fname(error in first build) Cannot referene fname until supertype constructorhas beencalled
    //not referring to top vars. referring to constructor above.
    public void setFname(String fn)//set is used to update the database ETL knows how to look it up without knowing thenameFName
    {//thses weren't used even in JH4 I don't think these are being used as I can mess up names an dprogram runs
        fname =fn;
    }
    public void setLname(String ln)//proper syntax alllower case except first letter after set or get for etl to work
    {
        lname =ln;
    }
    public void setEyecolor(String ec)
    {//javax.el.PropertyNotFoundException: The class 'Database_Person.DB_Person' does not have the property 'eyecolor'.First letter must be capitalized for EL in jsp!!
        eyecolor = ec;
    }
    public void setHcolor(String hc)
    {
        hcolor = hc;
    }
    public void setHeight(String ht)
    {
        height = ht;
    }
    public void setWeight(int wt)
    {
        weight = wt;
    }
    public String getFname()//get is used in all requests from where? this is called by ${person.fname}"
    {
        return fname;
    }
    public String getLname()
    {
        return lname;
    }
    public String getEyecolor()
    {
        return eyecolor;
    }
    public String getHcolor()
    {
        return hcolor;
    }
    public String getHeight()
    {
        return height;
    }
    public Integer getWeight()
    {
        return weight;
    }
    public int getIndex()
    {
        return index;
    }
//Why aren't we calling this anywere? Why can't we leave this out?
    public boolean equals(Object other) {//see java text 161  dbperson is more than an object not equals
        if (other == null) return false;//object is simply a universal class for all undefined objects
        if (other.getClass() != getClass()) 
            return false;
        DB_Person pother = (DB_Person) other;
        if (fname.equals(pother.fname) && lname.equals(pother.lname)
                && eyecolor.equals(pother.eyecolor) && 
                height.equals(pother.height)) {
              //weight == pother.weight){
            return true;
        } else {
            return false;
        }
    }

    public String update(int index, Statement statement) {//fname and all vars must match internal java vars not jsp vars
        String sql = "update PersonCollection2 set fname=" + q_surround(fname)
                + ", lname=" + q_surround(lname) 
                + ", ecolor=" + q_surround(eyecolor)
                + ", hcolor=" + q_surround(hcolor)
                + ", height=" + q_surround(height)
                + ", weight=" + weight
                + " where id=" + index;
        return executeUpdate(sql, statement);
    }//see bottom   update PersonCollection set title='a',author='b',isbn='c',edition='d' where id=2(index number);

    public String insert(Statement statement) {
//?? why need to do qsurround get error: "unclosd character
        // First find out if the person is already in the collection:
        String sql = "select fname from PersonCollection2 where fname="+ q_surround(fname)
                + "AND lname=" + q_surround(lname) + "AND ecolor=" + q_surround(eyecolor)
                + "AND hcolor=" + q_surround(hcolor)
                + "AND height=" + q_surround(height)
                + "AND weight=" + weight;
 //javax.el.PropertyNotFoundException: The class 'Database_Person.DB_Person' does not have the property 'fname'.       
        try
        {//2938
            ResultSet rs = statement.executeQuery(sql);//executing the statement above. Where is it defined?
            if (rs.next())//what is this calling? why aren't we using the equals method. Reads the first line of the return of the query
                return "Person already exists";
        }
        catch (SQLException e)
        {
            return e.toString();
        }
   //now we are inserting not selecting   
        sql = "insert into PersonCollection2 values(" + q_surround(fname) + ","
                + q_surround(lname) + "," + q_surround(eyecolor) + "," + q_surround(hcolor)+","
                + q_surround(height)+"," + weight + ", null)";//null will tell the db autonumber. if leave off get sql error
        System.out.println(sql);
        return executeUpdate(sql, statement);//??what is this statement referring to? The input from the jsp page?
    }
//will null cause problem? How will remove it? will it be caushg by index >=0
    //how use this with autoincrmenent
    // Note index =-1 will delete all rows
    public static String remove(int index, Statement statement) {
        String sql = "delete from PersonCollection2 ";
        if (index >= 0)//if give -1 index, it deletes everything. how?
            sql += " where id=" + index;//no where clause
        return executeUpdate(sql, statement);
    }
//below is the primary execute called from every method except the query one
    private static String executeUpdate(String sql, Statement statement) {//called by all the methods
        String error = "";//not same as null
        try {
            System.out.println("sql=" + sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            error = e.toString();
        }
        return error;
    }
//where create this arraylist
    public static String getPersons(Statement statement, ArrayList<DB_Person> persons) {//where is this arraylist getting established? How is it differetin from personCollection
        String error = "";//actually points to a memory spot versus null has no recrods
        try {
            String sql = "select * from PersonCollection2";
            System.out.println("sql="+sql);
            ResultSet rs = statement.executeQuery(sql);//execue the query
            persons.clear();//clear out array before starting filling it up with what's in databsae 
            //below values are coming FROM the sql database
            while (rs.next()) {//here we are walking through each ros of the result set at what is in the table
                String f = rs.getString("fname");
                String l = rs.getString("lname");
                String e = rs.getString("ecolor");//returns nll if we use "eye_color" as in sql if use ecolor get java.sql.SQLException: Column 'ecolor' not found.
                String h = rs.getString("hcolor");
                String ht = rs.getString("height");
                int wt = rs.getInt("weight");
                int ind = rs.getInt("id");
                DB_Person prsn = new DB_Person(f, l, e, h, ht, wt, ind);//here we add already existing person to array list
                persons.add(prsn);//this adds each person 1 by 1 from the sql table to our arraylist persons and holds it in? 
            }
        } catch (SQLException ex) {
            error = ex.toString();
        }
        return error;
    }

    // Surround with single quote
    private String q_surround(String s) {
        return "\'" + s + "\'";///puts single quotes around a string \means don't use this\ but the next character
    }
}