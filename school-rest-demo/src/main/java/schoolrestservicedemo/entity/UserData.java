package schoolrestservicedemo.entity;

public class UserData {
	
	private int userId;
	private String type;
	private int typeId;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String isNewAccount;
	
	/*When user attempt to login, this class is using for exchanging between Rest and Client 
	 * and also using to show user first name and last name on the top of right after login
	 */
	
	public UserData(){
		
	}

	public UserData(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	
	
	public UserData(String type, String username, String password) {
		this.type = type;
		this.username = username;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsNewAccount() {
		return isNewAccount;
	}

	public void setIsNewAccount(String isNewAccount) {
		this.isNewAccount = isNewAccount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
