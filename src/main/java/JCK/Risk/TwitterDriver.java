package JCK.Risk;
import twitter4j.Twitter;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterDriver {

	static String consumerKeyStr = "VZe14TMsVqwjhH6qVmWAAT9x8";
	static String consumerSecretStr = "0JWgAeWTL4A2aYSkG3iILe7xnCXHfThlCSmTlp3NRoz9sAnllH";
	static String accessTokenStr = "727691720939298816-WbCZKP32aWY1me35bseLJLBji1sq2aK";
	static String accessTokenSecretStr = "r1dHS7dVlQ0NF4rLQCluGMlza0zRO2GVmTqToRA2EZ2g0";

	public static void main(String[] args) {

		try {
			Twitter twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
			AccessToken accessToken = new AccessToken(accessTokenStr,
					accessTokenSecretStr);

			twitter.setOAuthAccessToken(accessToken);

			twitter.updateStatus("Post using Twitter4J Again");

			System.out.println("Successfully updated the status in Twitter.");
		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}

}