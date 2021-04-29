// import necessary Java packages for implementation
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Represents the social media platform and implements the interface SocialMediaPlatform
 * @Author Student 700043766
 * @Author Student 700074240
 * @Version 1.0
 * @Since 1.0
 */
public class SocialMedia implements SocialMediaPlatform {

	// instantiating SocialMedia class variables
	// ArrayLists to store objects that make up the platform (accounts, posts, comments and endorsements)
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
		// new instance of Account class
		Account a = new Account(handle);
		// check handle is valid
		if (handle.isBlank() || handle.length() > 30 || handle.contains(" ")) {
			throw new InvalidHandleException("Invalid Handle");
		}for (Account i : accounts) {
			// check handle does not already exist
			if (i.getHandle().equals(handle)) {
				throw new IllegalHandleException("Handle Already Exists");
			} else {
				// set handle for a
				a.setHandle(handle);
				// add a to ArrayList of all accounts in system
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
		// new instance of Account object
		Account b = new Account(handle, description);
		// check handle is valid
		if (handle.isBlank() || handle.length() > 30 || handle.contains(" ")) {
			throw new InvalidHandleException("Invalid Handle");
		}
		for (Account i : accounts) {
			// check handle does not already exist
			if (i.getHandle().equals(handle)) {
				throw new IllegalHandleException("Handle Already Exists");
			} else {
				// set handle and description field for b
				b.setHandle(handle);
				b.setDescriptionField(description);
				// add b to ArrayList of all accounts in system
				accounts.add(b);
			}
		} 
		return b.getId();
	}
	

	/**
	 * The method removes the account with the corresponding ID from the platform.
	 * @param id ID of the account.
	 * @throws AccountIDNotRecognisedException if the ID does not match to any
	 *                                         account in the system.
	 */
	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		for(Account a : accounts) {
			// check account ID exists
			if(a.getId() == id) {
				// remove account from ArrayList of all accounts
				accounts.remove(a);
				for(Post p: posts){
					if(p.getId() == id) {
						// remove account posts from ArrayList of all posts
						posts.remove(p);
					}
				}
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
			// check handle exists in the system
			if (a.getHandle().equals(handle)) {
				// remove account from ArrayList of all accounts in system
				accounts.remove(a);
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
		// check there is an account with the old handle
		if (!accounts.contains(oldHandle)) {
			throw new HandleNotRecognisedException("Handle Not Recognised");
		} 
		// check handle is valid
		else if (newHandle.isBlank() || newHandle.length() > 30 || newHandle.contains(" ")) {
			throw new InvalidHandleException("Invalid Handle");
		}  
		// check new handle does not already exist
		else if (accounts.contains(newHandle)) {
			throw new IllegalHandleException("Handle Already Exists");
		} 
		else {
			// set new account handle
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
			// check account with handle exists
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
		// new string to display result
		String result = """
				\t\t\tAccount Summary
				    
					 %s
				    
				""";
		for (Account a : accounts) {
			// check account with handle exists
			if (!a.getHandle().equals(handle)) {
				throw new HandleNotRecognisedException("Handle not recognised");
			} else {
				// format result as string
				result = String.format(result, toString());
			}
		}
		return result;
	}

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
	    // new instance of Post object
            Post p = new Post(handle, message);
            for (Post i : posts) {
		// check account with handle exists
                if (!i.getHandle().equals(handle)) {
                    throw new HandleNotRecognisedException("Handle not recognised");
                } 
		// check post message is valid
		else if (message == null || message.length() > 100) {
		    throw new InvalidPostException("Post message invalid.");
		} 
		else {
		    // set handle and message for p
                    p.setHandle(handle);
		    p.setMessage(message);
		    // add p to ArrayList of all posts in system
		    posts.add(p);
                }
	    }
            return p.getId();
        }
	
	
	/**
         * The method creates an endorsement post of an existing post, similar to a
         * retweet on Twitter. 
         * @param handle of the account endorsing a post.
         * @param id of the post being endorsed.
         * @return the sequential ID of the created post.
         * @throws HandleNotRecognisedException if the handle does not match to any
         * account in the system.
         * @throws PostIDNotRecognisedException if the ID does not match to any post in
         * the system.
         * @throws NotActionablePostException if the ID refers to a endorsement post.
         * Endorsement posts are not endorsable.
         * Endorsements are not transitive. For
         * instance, if post A is endorsed by post
         * B, and an account wants to endorse B, in
         * fact, the endorsement must refers to A.
         */
	@Override
        public int endorsePost(String handle, int id)
            throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
	    // new instance of Endorsement object
            Endorsement e = new Endorsement(new Account(handle), id);
            for(Post p : posts) {
		// check post ID exists in the system
                if(p.getId() != id) {
                    throw new PostIDNotRecognisedException("Post ID not recognised");
                }
                for (Account a : accounts) {
	            // check account handle exists in system
                    if(!a.getHandle().equals(handle)) {
                        throw new HandleNotRecognisedException("Handle not recognised");
                    }
                    for (Endorsement x : endorsements) {
			// check post is not an endorsement
                        if (x.getId() == id) {
                            throw new NotActionablePostException("Endorsements cannot be endorsed");
                        } else {
			    // set parameters
                            e.setHandle(handle);
                            e.setPostId(id);
		            // add endorsement to running total of all endorsements in platform
                            endorsements.add(e);
			    // add new endorsement to post it is associated with
			    p.postEndorsements.add(e);
                        }
                    }
                }
            }
            return e.getId();
        }
	
	
	/**
	 * The method creates a comment post referring to an existing post.
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
	        // new instance of Post object
		Comment c = new Comment(new Account(handle), message);
			for(Post p : posts) {
			    // check post ID exists in the system
			    if(p.getId() != id) { 
				throw new PostIDNotRecognisedException("Post ID not recognised");
			    }
			    for(Account a :accounts) {
				// check account handle exist in the system
		                if(!a.getHandle().equals(handle)) {
					throw new HandleNotRecognisedException("Handle not recognised");
				}
				for(Endorsement e : endorsements) {
					// check post is not an endorsement
					if (e.getId() == id) {
						throw new NotActionablePostException("Endorsements cannot be commented");
					} 
					// check comment message is valid
					else if (message == null || message.length() > 100) {
						throw new InvalidPostException("Invalid Post");
						} 
					        else {
						// set parameters
						c.setHandle(handle);
						c.setPostId(id);
						c.setMessage(message);
						// add comment to running total of all comments in platform
						comments.add(c); 
						// add new comment to post it is associated with
						p.postComments.add(c);
					} 
				 }
		        }
	       }
	       return c.getId();
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
		    // check post ID exists in the system
		    if (p.getPostId().equals(id)){
			    // remove p from ArrayList of all posts
			    posts.remove(p);
		    } else {
			    throw new PostIDNotRecognisedException("Post ID not recognised.");
		    }
		}
	}
	
	
	/**
         * The method generates a formated string containing the details of a single
         * post. 
         * <pre>
         * ID: [post ID]
         * Account: [account handle]
         * No. endorsements: [number of endorsements received by the post] | No. comments: [number of comments
          received by the post]
         * [post message]
         * </pre>
         *
         * @param id of the post to be shown.
         * @return a formatted string containing post’s details.
         * @throws PostIDNotRecognisedException if the ID does not match to any post in
         * the system.
         */
	 @Override
         public String showIndividualPost(int id) throws PostIDNotRecognisedException {
	     // new string to display result
             String result = new String();
             for (Post p : posts) {
		 // check post ID exists in the system
                 if(p.getId() != id) {
                     throw new PostIDNotRecognisedException("Post ID not recognised");
                 } else {
	             // format result as string
                     result =  p.toString();
                 }
             }
             return result;
         }
	
        /**
         * The method builds a StringBuilder showing the details of the current post and
         * all its children posts. The format is as follows:
         *
         * <pre>
         * {@link #showIndividualPost(int) showIndividualPost(id)}
         * |
         * [for reply: replies to the post sorted by ID]
         * | > {@link #showIndividualPost(int) showIndividualPost(reply)}
         * </pre>
         *
         * See an example:
         *
         * <pre>
         * ID: 1
         * Account: user1
         * No. endorsements: 2 | No. comments: 3
         * I like examples.
         * |
         * | > ID: 3
         * Account: user2
         * No. endorsements: 0 | No. comments: 1
         * No more than me...
         * |
         * | > ID: 5
         * Account: user1
         * No. endorsements: 0 | No. comments: 1
         * I can prove!
         * |
         * | > ID: 6
         * Account: user2
         * No. endorsements: 0 | No. comments: 0
         * prove it
         * | > ID: 4
         * Account: user3
         * No. endorsements: 4 | No. comments: 0
         * Can’t you do better than this?
         *
         * | > ID: 7
         * Account: user5
         * No. endorsements: 0 | No. comments: 1
         *where is the example?
         * |
         * | > ID: 10
         * Account: user1
         * No. endorsements: 0 | No. comments: 0
         * This is the example!
         * </pre>
         *
         * Continuing with the example, if the method is called for post ID=5
         * ({@code showIndividualPost(5)}), the return would be:
         *
         * <pre>
         * ID: 5
         * Account: user1
         * No. endorsements: 0 | No. comments: 1
         * I can prove!
         * |
         * | > ID: 6
         * Account: user2
         * No. endorsements: 0 | No. comments: 0
         * prove it
         * </pre>
         *
         * @param id of the post to be shown.
         * @return a formatted StringBuilder containing the details of the post and its
         * children.
         * @throws PostIDNotRecognisedException if the ID does not match to any post in
         * the system.
         * @throws NotActionablePostException if the ID refers to an endorsement post.
         * Endorsement posts do not have children
         * since they are not endorsable nor
         * commented.
         */
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

	
	/**
         * This method identifies and returns the post with the most number of
         * endorsements, a.k.a. the most popular post.
         *
         * @return the ID of the most popular post.
         */
        @Override
        public int getMostEndorsedPost() {
	    // empty Post object
            Post mostEndorsedPost = null;
	    // compare number of post endorsements
            for (Post i : posts) {
                for (Post j : posts) {
                    if (i.postEndorsements.size() > j.postEndorsements.size()) {
                        mostEndorsedPost = i;
                    } else {
                        mostEndorsedPost = j;
                    }
                }
            }
            return mostEndorsedPost.getId();
        }

	@Override
	public int getMostEndorsedAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	* This method clears all ArrayLists of posts, comments, endorsements and 
	* accounts, thereby erasing the platform.
	*/
	@Override
	public void erasePlatform() {
		posts.clear();
		comments.clear();
		endorsements.clear();
		accounts.clear();
	}

	
	/**
         * Method saves this SocialMediaPlatform’s contents into a serialised file, with
         * the filename given in the argument.
         *
         * @param filename location of the file to be saved
         * @throws IOException if there is a problem experienced when trying to save the
         * store contents to the file
         */
	 @Override
         public void savePlatform(String filename) throws IOException {
             try {
                 FileOutputStream fileOutputStream = new FileOutputStream(filename);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(FileOutputStream);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(BufferedOutputStream);
		 // save all ArrayLists
                 objectOutputStream.writeObject(posts);
                 objectOutputStream.writeObject(accounts);
                 objectOutputStream.writeObject(comments);
                 objectOutputStream.writeObject(endorsements);
                 bufferedOutputStream.close();
             } catch (IOException e) {
                 System.out.println("Problem saving contents to file.");
             }
         }

	
	/**
	 * Method should load and replace this SocialMediaPlatform's contents with the
	 * serialised contents stored in the file given in the argument.
	 * @param filename location of the file to be loaded
	 * @throws IOException            if there is a problem experienced when trying
	 *                                to load the store contents from the file
	 * @throws ClassNotFoundException if required class files cannot be found when
	 *                                loading
	 */
	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		try {
			FileInputStream fileInputStream = new FileInputStream(filename);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
			ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
			Object object = objectInputStream.readObject();
			objectInputStream.close();
		} catch (IOException e) {
			System.out.println("Problem loading content");
		} catch (ClassNotFoundException e) {
			System.out.println("Class cannot be found");
		}
	}
}
}
