package ems_package;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("serial")
public abstract class Employee extends BaseEntity implements Serializable{
	String name,dept,designation;
	int id,salary;
	public abstract int getSalary();
	public abstract void addEmployee(Employee e);
	public abstract Employee showDetails(Employee e,String Dept,String Name);
	public abstract int getTotalSalary(Employee e, String dept);
	public abstract HashMap deptDetails(String dept2);
}
