package socialmedia;

import java.io.IOException;
import java.util.ArrayList;


public class SocialMedia implements SocialMediaPlatform {

	private ArrayList<Account> accounts;

	/**
	 * The method creates an account in the platform with the given handle.
	 *
	 * @param handle account's handle.
	 * @throws IllegalHandleException if the handle already exists in the platform.
	 * @throws InvalidHandleException if the new handle is empty, has more than 30
	 *                                characters, or has white spaces.
	 * @return the ID of the created account.
	 *
	 */
	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		Account a = new Account(handle);
		if (handle.isBlank() || handle.length() > 30 || handle.contains(" ")) {
			throw new InvalidHandleException("Invalid Handle");
		}for (Account i : accounts) {
			if (i.getHandle().equals(handle)) {
				throw new IllegalHandleException("Handle Already Exists");
			} else {
				a.setHandle(handle);
				accounts.add(a);
			}
		}
		return a.getId();
	} 

	/**
	 * The method creates an account in the platform with the given handle and
	 * description.
	 *
	 * @param handle account's handle.
	 * @param description account's description.
	 * @throws IllegalHandleException if the handle already exists in the platform.
	 * @throws InvalidHandleException if the new handle is empty, has more than 30
	 *                                characters, or has white spaces.
	 * @return the ID of the created account.
	 */
	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		Account b = new Account(handle, description);
		if (handle.isBlank() || handle.length() > 30 || handle.contains(" ")) {
			throw new InvalidHandleException("Invalid Handle");
		}
		for (Account i : accounts) {
			if (i.getHandle().equals(handle)) {
				throw new IllegalHandleException("Handle Already Exists");
			} else {
				b.setHandle(handle);
				b.setDescriptionField(description);
				accounts.add(b);
			}
		} return b.getId();

	}
	

	/**
	 * The method removes the account with the corresponding ID from the platform.
	 * @param id ID of the account.
	 * @throws AccountIDNotRecognisedException if the ID does not match to any
	 *                                         account in the system.
	 */
	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		for(Account a :accounts) {
			if(a.getId() == id){
				accounts.remove(a);
				//remove posts and comments too
			} else {
				throw new AccountIDNotRecognisedException("Account ID not recognised");
			}
		}
		}


	/**
	 * The method removes the account with the corresponding handle from the
	 * The state of this SocialMediaPlatform must be be unchanged if any exceptions
	 * are thrown.
	 *
	 * @param handle account's handle.
	 * @throws HandleNotRecognisedException if the handle does not match to any
	 *                                      account in the system.
	 */
	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		for (Account a : accounts) {
			if (a.getHandle().equals(handle)) {
				accounts.remove(a);
				//remove posts and comments too
			} else {
				throw new HandleNotRecognisedException("Handle not recognised");
			}
		}
	}

	/**
	 * The method replaces the oldHandle of an account by the newHandle.
	 * @param oldHandle account's old handle.
	 * @param newHandle account's new handle.
	 * @throws HandleNotRecognisedException if the old handle does not match to any
	 *                                      account in the system.
	 * @throws IllegalHandleException       if the new handle already exists in the
	 *                                      platform.
	 * @throws InvalidHandleException       if the new handle is empty, has more
	 *                                      than 30 characters, or has white spaces.
	 */
	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		if (!accounts.contains(oldHandle)) {
			throw new HandleNotRecognisedException("Handle Not Recognised");
		} else if (newHandle.isBlank() || newHandle.length() > 30 || newHandle.contains(" ")) {
			throw new InvalidHandleException("Invalid Handle");
		}  else if (accounts.contains(newHandle)) {
			throw new IllegalHandleException("Handle Already Exists");
		} else {
			accounts.set(accounts.indexOf(oldHandle), new Account(newHandle));
		}
	}

	/**
	 * The method updates the description of the account with the respective handle.
	 * @param handle      handle to identify the account.
	 * @param description new text for description.
	 * @throws HandleNotRecognisedException if the handle does not match to any
	 *                                      account in the system.
	 */
	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		for (Account a : accounts) {
			if (a.getHandle().equals(handle)) {
				a.setDescriptionField(description);
			} else {
				throw new HandleNotRecognisedException("Handle not recognised");
			}
		}
	}
	
		/**
	 * The method creates a formatted string summarising the stats of the account
	 * identified by the given handle.
	 * @param handle handle to identify the account.
	 * @return the account formatted summary.
	 * @throws HandleNotRecognisedException if the handle does not match to any
	 *                                      account in the system.
	 */
	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		String result = """
				\t\t\tAccount Summary
				    
					 %s
				    
				""";
		for (Account a : accounts) {
			if (!a.getHandle().equals(handle)) {
				throw new HandleNotRecognisedException("Handle not recognised");
			} else {
				result = String.format(result, toString());
			}
		}
		return result;
	} // just need to add endorsements and posts in the toString when calculated 

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * This method returns the current total number of accounts present in the
	 * platform. 
	 * @return the total number of accounts in the platform.
	 */
	@Override
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	

	@Override
	public int getTotalOriginalPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCommentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedPost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedAccount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void erasePlatform() {
		// TODO Auto-generated method stub

	}

	@Override
	public void savePlatform(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}
