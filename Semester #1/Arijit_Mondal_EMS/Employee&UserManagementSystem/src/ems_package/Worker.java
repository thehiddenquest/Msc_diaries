package ems_package;

import java.util.HashMap;

public class Worker extends Employee{
	public Worker(String name,String dept,String desg,int id,int salary) {
		super.dept=dept;
		super.designation=desg;
		super.name=name;
		super.id=id;
		super.salary=salary;
		
	}
	@Override
	public int getSalary() {
		// TODO Auto-generated method stub
		return super.salary;
	}
	@Override
	public void addEmployee(Employee e) {
		// TODO Auto-generated method stub
		//not to implement
	}
	@Override
	public Employee showDetails(Employee e, String dept,String Name) {
		// TODO Auto-generated method stub
		//not to implement
		return new Worker("n","n","n",1,1);
	}
	@Override
	public int getTotalSalary(Employee e, String dept) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public HashMap deptDetails(String dept2) {
		// TODO Auto-generated method stub
		return null;
	}

}
