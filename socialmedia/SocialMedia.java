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
				for(Post p: posts){
					if(p.getId() == id) {
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
	
	/**
         * The method creates an endorsement post of an existing post, similar to a
         * retweet on Twitter. An endorsement post is a special post. It contains a
         * reference to the endorsed post and its message is formatted as:
         * <p>
         * <code>"EP@" + [endorsed account handle] + ": " + [endorsed message]</code>
         * <p>
         * The state of this SocialMediaPlatform must be be unchanged if any exceptions
         * are thrown.
         *
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
            Endorsement e = new Endorsement(new Account(handle), id);
            for(Post p : posts) {
                if(p.getId() != id) {
                    throw new PostIDNotRecognisedException("Post ID not recognised");
                }
                for (Account a : accounts) {
                    if(!a.getHandle().equals(handle)) {
                        throw new HandleNotRecognisedException("Handle not recognised");
                    }
                    for (Endorsement x : endorsements) {
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
		Comment c = new Comment(new Account(handle), message);
			for(Post p : posts) {
			    if(p.getId() != id) { 
				throw new PostIDNotRecognisedException("Post ID not recognised");
			    }
			    for(Account a :accounts) {
		                if(!a.getHandle().equals(handle)) {
					throw new HandleNotRecognisedException("Handle not recognised");
				}
				for(Endorsement e : endorsements) {
					if (e.getId() == id) {
						throw new NotActionablePostException("Endorsements cannot be commented");
					} else if (message == null || message.length() > 100) {
						throw new InvalidPostException("Invalid Post");
						} else {
						
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
		    if (p.getPostId().equals(id)){
			    posts.remove(p);
		    } else {
			    throw new PostIDNotRecognisedException("Post ID not recognised.");
		    }
		}
	}
	
	/**
         * The method generates a formated string containing the details of a single
         * post. The format is as follows:
         *
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
             String result = new String();
             for (Post p : posts) {
                 if(p.getId() != id) {
                     throw new PostIDNotRecognisedException("Post ID not recognised");
                 } else {
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

	@Override
	public int getMostEndorsedPost() {
		// iterate through postEndorsements ArrayList for each post and find
		// post with most endorsements
		return 0;
	}

	@Override
	public int getMostEndorsedAccount() {
		// iterate through postComments ArrayList for each post and find post
		// with most comments
		return 0;
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
		FileInputStream fileInputStream = new FileInputStream(filename);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
		Object object = objectInputStream.readObject();
		objectInputStream.close();

	}

}
