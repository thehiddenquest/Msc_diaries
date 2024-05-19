/**
 * This class will create any arbitrary polynomial to work with. It uses linked link to 
 * store the terms of the polynomial.
 *  
 * @author Sunirmal Khatua
 *
 */
public class Polynomial {
    // ********** attributes ********************
	private Node head;
	private int degree;
	
    /*
     * Default constructor
     */
    public Polynomial() {
    	this.degree = 0;
    	head = null;
    }
    
    /*
     * Parameterized constructor
     */
    public Polynomial(double[] coeff) {           
    	this.degree = coeff.length-1;
    	for(int i = 0; i <= degree; ++i) {
    		insertSorted(coeff[i],i);
    	}
    	
    }
    
    /*
     * This is a utility method that can be used to insert a term in sorted order
     * in the current polynomial 
     */
    private void insertSorted(double coeff, int power) {   	
    	if(head==null) {
    		head = new Node(new Terms(coeff, power), null);
    	}else {
	    	Node curr = head.next;
	    	Node prev = head;
	    	while(curr != null && curr.term.power<power) {
	    		prev = curr;
	    		curr = curr.next;
	    	}
	    	if(curr!=null && curr.term.power == power) 
	    		curr.term.coeff += coeff;
	    	else
	    		prev.next = new Node(new Terms(coeff, power), curr);
    	}
    }
    
    /*
     *  subtract a polynomial q from the given polynomial p i.e.  p = p-q
     */
    public void subtract(Polynomial p) {
    	degree = Math.max(this.degree,p.degree); 
    	head = diff(this, p).head;
    }
    
    /*
     * returns value of p(x)
     */
    public double evaluate(double x) {
    	double temp = 0.0;
        Node curr = head;
        while (curr != null) {
            temp += curr.term.coeff * Math.pow(x, curr.term.power);
            curr = curr.next;
        }
        return temp;
    }
    
    /*
     *  add a polynomial q to the given polynomial p i.e. p = p+q
     */
    public void add(Polynomial p) {
    	degree = Math.max(this.degree,p.degree); 
    	head = sum(this, p).head;
    }
    
     /*
      *  multiply the given polynomial by a polynomial q i.e.  p = p*q
      */
    public void multiply(Polynomial q) {
    	     degree = this.degree+q.degree;
    	     head = product(this, q).head;
    	     
    }
    
     /*
      *  multiply the given polynomial by a constant a  i.e   p = a.p
      */
    public void scale(double a) {
    	Node curr = head;
        while (curr != null) {
            curr.term.coeff *= a;
            curr = curr.next;
        }
    }
    
    /*
     * prints a polynomial object
     */
    public String toString() {
            Node curr = head;
            String q = "";
            if(curr!=null) {
            	if(curr.term.power == 1)
            		q=curr.term.coeff + "x";
            	else if (curr.term.power == 0)
            		q = ""+curr.term.coeff;
            	else
            		q=curr.term.coeff + "x^" + curr.term.power;
            	curr = curr.next;
            }
            while(curr != null) {
            	if(curr.term.power == 1)
            		q=curr.term.coeff + "x"+"+"+q;
            	else
            		q = curr.term.coeff + "x^" + curr.term.power+"+"+q; 
            	curr = curr.next;
            } 
            return q;
    }
    
    /*
     * multiplies 2 polynomials  and creates a new polynomial 
     * without destroying the original i.e. r = p*q
     */
    public static Polynomial product(Polynomial p, Polynomial q) {
    	Polynomial temp = new Polynomial();
    	 Node pCurr = p.head;
    	    while (pCurr != null) {
    	        Node qCurr = q.head;
    	        while (qCurr != null) {
    	            temp.insertSorted(pCurr.term.coeff * qCurr.term.coeff, pCurr.term.power + qCurr.term.power);
    	            qCurr = qCurr.next;
    	        }
    	        pCurr = pCurr.next;
    	    }
        return temp;
    }
    
    /*
     *  adds 2 polynomials  and creates a new polynomial without destroying 
     *  the original i.e.  r = p+q
     */
    public static Polynomial sum(Polynomial p, Polynomial q) {
    	Polynomial temp = new Polynomial();
        Node pCurr = p.head;
        Node qCurr = q.head;

        while (pCurr != null || qCurr != null) {
            if (pCurr != null && (qCurr == null || pCurr.term.power > qCurr.term.power)) {
                temp.insertSorted(pCurr.term.coeff, pCurr.term.power);
                pCurr = pCurr.next;
            } else if (qCurr != null && (pCurr == null || pCurr.term.power < qCurr.term.power)) {
                temp.insertSorted(qCurr.term.coeff, qCurr.term.power);
                qCurr = qCurr.next;
            } else { // pCurr.term.power == qCurr.term.power
                temp.insertSorted(pCurr.term.coeff + qCurr.term.coeff, pCurr.term.power);
                pCurr = pCurr.next;
                qCurr = qCurr.next;
            }
        }
    	return temp;
    }
    
    /*
     *  subtracts 2 polynomials  and creates a new polynomial without destroying 
     *  the originals  i.e. r = p-q
     */
    public static Polynomial diff(Polynomial p, Polynomial q) {
    	Polynomial temp = new Polynomial();
    	Node pCurr = p.head;
        Node qCurr = q.head;

        while (pCurr != null || qCurr != null) {
            if (pCurr != null && (qCurr == null || pCurr.term.power > qCurr.term.power)) {
                temp.insertSorted(pCurr.term.coeff, pCurr.term.power);
                pCurr = pCurr.next;
            } else if (qCurr != null && (pCurr == null || pCurr.term.power < qCurr.term.power)) {
                temp.insertSorted(qCurr.term.coeff, qCurr.term.power);
                qCurr = qCurr.next;
            } else { // pCurr.term.power == qCurr.term.power
                temp.insertSorted(pCurr.term.coeff - qCurr.term.coeff, pCurr.term.power);
                pCurr = pCurr.next;
                qCurr = qCurr.next;
            }
        }
        return temp;
    }
    

}
