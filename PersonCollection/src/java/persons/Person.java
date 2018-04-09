package persons;


public class Person {
    private String fname;
    private String lname;
    private String ecolor;
    private String hcolor;
    private String height;
    private Double weight;
    
    public Person(String fname, String lname, String ecolor, String hcolor, String height, Double weight)
    {
        this.fname = fname;
        this.lname = lname;
        this.ecolor = ecolor;
        this.hcolor = hcolor;
        this.height = height;
        this.weight = weight;
    }
    public void setFName(String fn)
    {
        fname =fn;
    }
    public void setLName(String ln)
    {
        lname =ln;
    }
    public void setEColor(String ec)
    {
        ecolor = ec;
    }
    public void setHColor(String hc)
    {
        hcolor = hc;
    }
     public void setHeight(String ht)
    {
        height = ht;
    }
      public void setWeight(Double wt)
    {
        weight = wt;
    }
    public String getFName()
    {
        return fname;
    }
    public String getLName()
    {
        return lname;
    }
    public String getEColor()
    {
        return ecolor;
    }
    public String getHColor()
    {
        return hcolor;
    }
    public String getHeight()
    {
        return height;
    }
    public Double getWeight()
    {
        return weight;
    }
    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (other.getClass() != getClass())
            return false;
        Person pother = (Person)other;
        if (height.equals(pother.height)  && fname.equals(pother.fname) && lname.equals(pother.lname) && 
                //weight==pother.weight)  this seems broken
                ecolor.equals(pother.ecolor))
            return true;
        else 
            return false;
    }
    
}
