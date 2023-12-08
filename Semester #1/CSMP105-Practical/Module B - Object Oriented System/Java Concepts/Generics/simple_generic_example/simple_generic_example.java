class gen<T>{
    private T o;
    gen(T o)
    {
        this.o = o;
    }
    public void setO(T o) {
        this.o = o;
    }
    public T getO()
    {
        return o;
    }
    public void displayClassType()
    {
        System.out.println("The object is "+ o + " with the class type " + (o != null? o.getClass().getName(): "unknown"));
    }

}

public class simple_generic_example {
    public static void main(String args[])
    {
        gen<Integer> ini= new gen<Integer>(10);
        ini.displayClassType();
        gen<String> un = new gen<>("Generic Example for unknown variable");
        un.displayClassType();
    }
}
