import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Loan {
	
	private PriorityQueue<Applicant> activeList;
	private ArrayList<Applicant> approvedList;
	private ArrayList<Applicant> rejectedList;
	private int budget;
	private Scanner sc;

	public Loan() {
		activeList = new PriorityQueue<Applicant>();
		approvedList = new ArrayList<Applicant>();
		rejectedList = new ArrayList<Applicant>();
		budget = 0;
		sc = new Scanner(System.in);
	}

	public void run() {
		/*
		 * print the options for the Loan app and execute them
		 * 
		 * Possible operations: Load applications Set the budget Make a decision Print
		 * Update an application (there is a method find() in PriorityQueue that you can
		 * use to find the applicant you would like to update)
		 */

		showCommands();

	}

	//will show commands
	public void showCommands() {

		System.out.println("Welcome to Loan App. Please choose operation to perform");
		System.out.println("1. Load Applcation ");
		System.out.println("2. Add Budget");
		System.out.println("3. Make Decision");
		System.out.println("4. Print active, rejected and approved List");
		System.out.println("5. Update Application");
		System.out.println("6. Exit");
		System.out.println("--------------------------------");

		int choice = sc.nextInt();
		sc.nextLine();

		switch (choice) {
		case 1:
			loadApplications();
			System.out.println("--------------------------------");
			break;
		case 2:
			getBudget();
			System.out.println("--------------------------------");
			break;
		case 3:
			makeDecision();
			System.out.println("--------------------------------");
			break;
		case 4:
			printActive();
			printApproved();
			printRejected();
			System.out.println("--------------------------------");
			break;

		case 5:
			updateApplicant();
			System.out.println("--------------------------------");
			break;
		case 6:
			System.out.println("Software Stopped");
			System.exit(0);
			break;

		default:
			System.out.println("Invalid Input");
			System.out.println("-------------------------");

		}

		showCommands();

	}

	//read new file and update applicant details in active list
	public void updateApplicant() {
		try {

			System.out.println("Enter full Path or name(if it is in project folder directly) of file: ");
			String fname = sc.nextLine();

			File f = new File(fname);
			if (f.exists()) {
				FileReader fr = new FileReader(fname);
				BufferedReader br = new BufferedReader(fr);

				String s;
				while ((s = br.readLine()) != null) {
					String data[] = s.split("\t");
					String name = data[0];
					int loan = Integer.parseInt(data[1]);
					int edu = Integer.parseInt(data[2]);
					int exp = Integer.parseInt(data[3]);

					int ap_arr[] = new int[data.length - 4];

					int index = 0;
					for (int i = 4; i < data.length; i++) {
						ap_arr[index] = Integer.parseInt(data[i]);
						index++;
					}

					Applicant applicant = new Applicant(name, edu, exp, ap_arr, loan);

					if ((edu + exp) < 10) {

						rejectedList.add(applicant);
					} else {

						Applicant app = activeList.find(applicant);

						if (app != null) {
							app.setAnnualProfit(ap_arr);
							app.setEducation(edu);
							app.setExperience(exp);
							app.setLoanamount(loan);

						} else {
							activeList.push(applicant);
						}

					}

				}

				System.out.println("Applications updated in lists");
			} else {
				System.out.println("File Does not exist at given path. Wrong file Path/name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//check active list and put applicants in approved list which meet the criteria
	public void makeDecision() {

		PriorityQueue<Applicant> temp = new PriorityQueue<Applicant>();
		while (!activeList.isEmpty()) {
			Applicant apct = activeList.pop();

			if (apct.getLoanamount() <= budget) {
				approvedList.add(apct);
				budget -= apct.getLoanamount();
			} else {
				temp.push(apct);
			}

		}

		activeList = temp;

		System.out.println("***************************");
		System.out.println("DECISION MADE");
		System.out.println("***************************");

	}

	//read file and load applicants in lists
	public void loadApplications() {
		try {

			System.out
					.println("Enter full Path or name(if it is in project folder directly) of file: (application.txt)");
			String fname = sc.nextLine();

			File f = new File(fname);

			if (f.exists()) {
				FileReader fr = new FileReader(fname);
				BufferedReader br = new BufferedReader(fr);

				String s;
				while ((s = br.readLine()) != null) {
					String data[] = s.split("\t");
					String name = data[0];
					int loan = Integer.parseInt(data[1]);
					int edu = Integer.parseInt(data[2]);
					int exp = Integer.parseInt(data[3]);

					int ap_arr[] = new int[data.length - 4];

					int index = 0;
					for (int i = 4; i < data.length; i++) {
						ap_arr[index] = Integer.parseInt(data[i]);
						index++;
					}

					Applicant applicant = new Applicant(name, edu, exp, ap_arr, loan);

					
					//check if applicant is eligible
					if ((edu + exp) < 10) {

						rejectedList.add(applicant);
					} else {
						activeList.push(applicant);
					}

				}

				System.out.println("Applications Loaded in lists");
			}

			else {
				System.out.println("File Does not exist at given path. Wrong file Path/name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//print approved applicants in file
	public void printApproved() {
		try {
			FileWriter fw = new FileWriter("approved.txt");
			PrintWriter pw = new PrintWriter(fw);

			for (int i = 0; i < approvedList.size(); i++) {
				Applicant app = approvedList.get(i);
				pw.println(app.toString());
			}

			pw.flush();
			pw.close();

			File f = new File("approved.txt");

			System.out.println("Approved Applicants printed in approved.txt");
			System.out.println("Path of approved.txt in system: " + f.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//print rejected applicants in file
	public void printRejected() {
		try {
			FileWriter fw = new FileWriter("rejected.txt");
			PrintWriter pw = new PrintWriter(fw);

			for (int i = 0; i < rejectedList.size(); i++) {
				Applicant app = rejectedList.get(i);
				pw.println(app.toString());

			}

			pw.flush();
			pw.close();

			File f = new File("rejected.txt");

			System.out.println("Rejected Applicants printed in rejected.txt");
			System.out.println("Path of rejected.txt in system: " + f.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//print active applicants in file
	public void printActive() {

		try {

			FileWriter fw = new FileWriter("active.txt");
			PrintWriter pw = new PrintWriter(fw);

			PriorityQueue<Applicant> temp1 = new PriorityQueue<Applicant>();

			while (!activeList.isEmpty()) {
				Applicant app = activeList.pop();

				temp1.push(app);
				pw.println(app.toString() + "\t" + app.getScore());

			}
			while (!temp1.isEmpty()) {
				Applicant app = temp1.pop();
				activeList.push(app);
			}
			temp1 = null;

			pw.flush();
			pw.close();

			File f = new File("active.txt");

			System.out.println("Active Applicants printed in active.txt");
			System.out.println("Path of active.txt in system: " + f.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	//input budget from user
	public void getBudget() {

		System.out.println("Current Budget: " + budget);
		System.out.println("Enter Budget to add: ");
		budget += sc.nextInt();
		sc.nextLine();
		System.out.println("Budged Added");
		System.out.println("Current Budget: " + budget);

	}

}
