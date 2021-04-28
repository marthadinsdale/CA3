package socialmedia;

import java.io.IOException;
import java.util.ArrayList;


public class SocialMedia implements SocialMediaPlatform {

	private ArrayList<Account> accounts;
	private ArrayList<Post> posts;
	private ArrayList<Comment> comments;
	private ArrayList<Endorsement> endorsements;

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
	 * @param description account's description
	 * @throws IllegalHandleException if the handle already exists in the platform.
	 * @throws InvalidHandleException if the new handle is empty, has more than 30
	 *                                characters, or has white spaces.
	 * @return the ID of the created account.
	 *
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
	 * The method removes the account with the corresponding handle from the system.
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

	/**
	 * The method creates a post in the platform with the given handle and message.
	 *
	 * @param handle account's handle.
	 * @param message post's message.
	 * @throws HandleNotRecognisedException if the handle does not match to any account in the system.
	 * @throws InvalidPostException if the new message is empty, has more than 100 characters.
	 * @return the ID of the created account.
	 *
	 */
	@Override
        public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
            Post p = new Post(handle, message);
            for (Post i : posts) {
                if (!i.getHandle().equals(handle)) {
                    throw new HandleNotRecognisedException("Handle not recognised");
                } else if (message == null || message.length() > 100) {
		    throw new InvalidPostException("Post message invalid.");
		} else {
                    p.setHandle(handle);
		    p.setMessage(message);
		    posts.add(p);
                }
	    }
            return p.getId();
        }

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		Endorsement e = new Endorsement();
		endorsements.add(e);
		return postId;
	}

	/**
	 * The method creates a comment post referring to an existing post, similarly to
	 * a reply on Twitter. A comment post is a special post. It contains a reference
	 * to the post being commented upon.
	 * <p>
	 * The state of this SocialMediaPlatform must be be unchanged if any exceptions
	 * are thrown.
	 *
	 * @param handle  of the account commenting a post.
	 * @param id      of the post being commented.
	 * @param message the comment post message.
	 * @return the sequential ID of the created post.
	 * @throws HandleNotRecognisedException if the handle does not match to any
	 *                                      account in the system.
	 * @throws PostIDNotRecognisedException if the ID does not match to any post in
	 *                                      the system.
	 * @throws NotActionablePostException   if the ID refers to a endorsement post.
	 *                                      Endorsement posts are not endorsable.
	 *                                      Endorsements cannot be commented. For
	 *                                      instance, if post A is endorsed by post
	 *                                      B, and an account wants to comment B, in
	 *                                      fact, the comment must refers to A.
	 * @throws InvalidPostException         if the comment message is empty or has
	 *                                      more than 100 characters.
	 */
	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		Comment c = new Comment(new Account(handle), message);
			for(Post p : posts) {
			if(p.getId() != id) { 
				throw new PostIDNotRecognisedException("Post ID not recognised");
			}for(Account a :accounts) {
				 if(!a.getHandle().equals(handle)) {
					throw new HandleNotRecognisedException("Handle not recognised");
				}for(Endorsement e : endorsements) {
					if (e.getId() == id) {
						throw new NotActionablePostException("Endorsements cannot be commented");
					} else if (message == null || message.length() > 100) {
						throw new InvalidPostException("Invalid Post");
						} else {
						comments.add(c); } 
				 }
		}
	}return c.getId();
	}

	/**
	 * The method removes the post with the corresponding ID from the system.
	 * The state of this SocialMediaPlatform must be be unchanged if any exceptions
	 * are thrown.
	 *
	 * @param id post's id
	 * @throws PostIDNotRecognisedException if the ID does not match to any
	 *                                      post in the system.
	 */
	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		for (Post p : posts) {
		    if (p.getPostId().equals(id)){
			    posts.remove(p);
		    } else {
			    throw new PostIDNotRecognisedException("Post ID not recognised.");
		    }
		}
	}

	/**
	 * The method creates a formatted string showing an individual post identified
	 * by the given ID.
	 * @param id post ID.
	 * @return the post formatted as a string.
	 * @throws PostIDNotRecognisedException if the ID does not match to any
	 *                                      post in the system.
	 */
	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		String result = "";
		
		// ADD IN STRING!!!
		
		for (Post p : posts){
			if (!p.getPostId().equals(id)){
				throw new PostIDNotRecognisedException("Post ID not recognised.");
			} else {
				result = String.format(result, toString());
			}
		}
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

	/**
	 * This method returns the current total number of original posts present 
	 * in the platform. 
	 * @return the total number of original posts in the platform.
	 */
	@Override
	public int getTotalOriginalPosts() {
		return posts.size();
	}

	/**
	 * This method returns the current total number of endorsement posts present 
	 * in the platform. 
	 * @return the total number of original posts in the platform.
	 */
	@Override
	public int getTotalEndorsmentPosts() {
		return endorsements.size();
	}

	/**
	 * This method returns the current total number of comment posts present 
	 * in the platform. 
	 * @return the total number of comment posts in the platform.
	 */
	@Override
	public int getTotalCommentPosts() {
		return comments.size();
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

	/**
	* This method clears all ArrayLists of posts, comments, endorsements and 
	* accounts, thereby erasing the platform.
	* @return the erased platform (0)
	*/
	@Override
	public void erasePlatform() {
		posts.clear();
		comments.clear();
		endorsements.clear();
		accounts.clear();
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
