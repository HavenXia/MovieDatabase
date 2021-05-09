package prefixTrie;

public class Term implements ITerm {
    
    String title;
    int id;
    
    // constructor
    public Term(String title, int id) {
        if (title == null || id < 0) {
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.id = id;
    }
    
    @Override
    public int compareTo(ITerm that) {
        // lexicographic order
        Term thatTerm = (Term) that;
        return this.title.compareTo(thatTerm.getTitle());
    }
    
    // setter and getters
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        // check reference
        if (this == obj) {
            return true;
        }
        // check instance
        if (obj instanceof Term) {
            Term o = (Term) obj;
            // return true when fields are same
            return (this.title.equals(o.getTitle()) && this.id == o.getId());
        }
        return false;
    }
    
    


}
