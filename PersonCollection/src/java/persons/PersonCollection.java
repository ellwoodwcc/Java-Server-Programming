package persons;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;


public class PersonCollection {

    private ArrayList<Person> pnArray = new ArrayList<Person>();
    String errorMessage="";

    public int size() {
        return pnArray.size();
    }

    public Person getPerson(int index) {
        return pnArray.get(index);
    }
    public String getErrorMessage()
    {
        return errorMessage;
    }
    static double getDoubleCarefully(String s) {
        double d = -1;
        try {
            d = Double.parseDouble(s.trim());
        } catch (RuntimeException e) {
             // Could get NumberFormatException or NullPointerException
        }
        return d;
    }
    public static PersonCollection update(PageContext pageContext) {
        HttpSession session = pageContext.getSession();
        PersonCollection bc = (PersonCollection) session.getAttribute("PersonCollection");
        if (bc == null) 
        {
            bc = new PersonCollection(); // Default Constructor provided from compiler
            session.setAttribute("PersonCollection", bc);//can pass it object & primitivces
        }

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String action = request.getParameter("action");
        if (action != null) 
        {
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String ecolor = request.getParameter("eyecolor");
            String hcolor = request.getParameter("haircolor");
            
            String height = request.getParameter("height");
//            Double ht = Double.parseDouble(height.trim());
            String weight = request.getParameter("weight");
            Double wt = getDoubleCarefully(weight);
            Person person = new Person(fname,lname,ecolor,hcolor,height,wt);
           
            //int wt = getIntegerCarefully(weight);
            if ("Clear List".equals(action))
            {
                bc.pnArray.clear();
            }
            else if ("add".equals(action)) {
                boolean found = false;
                // Check for uniqueness
                for (int i = 0; i < bc.pnArray.size(); i++) {
                    if (person.equals(bc.pnArray.get(i))) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    bc.pnArray.add(person);
                    bc.errorMessage="";
                }
                else
                    bc.errorMessage="Person already exists";
            }
            else if ("remove".equals(action)) {
                for (int i = 0; i < bc.pnArray.size(); i++) {
                    if (person.equals(bc.pnArray.get(i))) {
                        bc.pnArray.remove(i);
                    }
                }
            }
            else if ("update".equals(action)){
                String strIndex = request.getParameter("index");
                int index = Integer.parseInt(strIndex);
                Person pn = bc.pnArray.get(index);
                pn.setFName(fname);
                pn.setLName(lname);
                pn.setEColor(ecolor);
                pn.setHColor(hcolor);
                pn.setHeight(height);
                pn.setWeight(wt);
        }
            
        }

        return bc;
    }
}    