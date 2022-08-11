
public class Applicant implements Comparable<Applicant> {
	
	
	private String name;
	private int score;
	private int loanamount;
	private int education;
	private int experience;
	private int annualProfit[];

	// add the rest of fields (education, experience, annual profit)

	// Complete this constructor
	// you should calculate this.score based on estimated annual profit
	

	public Applicant(String name, int education, int experience, int annualProfit[], int loadamount) {
		super();
		this.score = 0;
		this.name = name;
		this.education = education;
		this.experience = experience;
		this.annualProfit = annualProfit;
		this.loanamount = loadamount;

		//calculate score
		double ap = 0;

		for (int i = 0; i < annualProfit.length; i++) {
			int p = annualProfit[i];
			ap += p * 1 / (i + 1);
		}
		score = (int) ap;
	}

	public int compareTo(Applicant other) {
		if (this.score > other.score)
			return 1;
		else if (this.score < other.score)
			return -1;
		else
			return 0;
		// you can just write it in one line:
		// return this.score - other.score
	}

	// this method will be used to find an applicant
	@Override
	public boolean equals(Object o) {
		Applicant other = (Applicant) o;
		return this.name.equals(other.name);
	}

	public String toString() {
	
		return getName() + "\t" + getLoanamount();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(int loanamount) {
		this.loanamount = loanamount;
	}

	public int getEducation() {
		return education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int[] getAnnualProfit() {
		return annualProfit;
	}

	public void setAnnualProfit(int[] annualProfit) {
		this.annualProfit = annualProfit;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	// add any other method you need
}
