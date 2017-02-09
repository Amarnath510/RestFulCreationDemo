package com.amarnath.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.amarnath.model.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	
//	private final String filePath = "resources/Employee.dat";
	
	private List<Employee> employeeList = new ArrayList<Employee>();
	
	@Override
	public boolean saveEmployee(Employee employee) {
		int index = -1;
		readFromFile();
		for(int i = 0; i < employeeList.size(); i++) {
			if (employee.getId() == employeeList.get(i).getId()) {
				index = i;
				break;
			}
		}
		
		if(-1 == index) {
			employeeList.add(employee);
			writeToFile();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean deleteEmployee(int id) {
		int index = -1;
		readFromFile();
		for(int i = 0; i < employeeList.size(); i++) {
			if (id == employeeList.get(i).getId()) {
				index = i;
				break;
			}
		}
		
		if (-1 != index) {
			employeeList.remove(index);
			writeToFile();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		int index = -1;
		int id = employee.getId();
		readFromFile();
		for(int i = 0; i < employeeList.size(); i++) {
			if (id == employeeList.get(i).getId()) {
				index = i;
				break;
			}
		}
		
		if (-1 != index) {
			Employee updateEmp = employeeList.get(index);
			updateEmp.setfName(employee.getfName());
			updateEmp.setlName(employee.getlName());
			writeToFile();
			return true;
		}
		
		return false;
	}

	@Override
	public Employee getEmployee(int id) {
		int index = -1;
		readFromFile();
		for(int i = 0; i < employeeList.size(); i++) {
			if (id == employeeList.get(i).getId()) {
				index = i;
				break;
			}
		}
		
		if (-1 != index) {
			return employeeList.get(index);
		}
		
		return null;
	}

	@Override
	public List<Employee> getAllEmployees() {
		readFromFile();
		return employeeList;
	}
	
	private void writeToFile() {
		try {
			File file = new File("Employee.dat");
//			System.out.println(file.getAbsolutePath());
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(employeeList);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void readFromFile() {
		try {
			FileInputStream fis = new FileInputStream("Employee.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			employeeList = (List<Employee>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
