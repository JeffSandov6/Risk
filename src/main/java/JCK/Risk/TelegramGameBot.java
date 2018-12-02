package JCK.Risk;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import JCK.Risk.Extras.AmazonS3Replay;



public class TelegramGameBot extends TelegramLongPollingBot {
	
	private static String response = "";
	AmazonS3Replay s3Client;
	
    private String getResponse() {
		return response;
	}

	private void setResponse(String chatResponse) {
		response = chatResponse;
		s3Client.appendToLogger(chatResponse);
		
	}
	
	private void resetResponse() {
		response = "";
	}
	
	public TelegramGameBot() {
		s3Client = new AmazonS3Replay();
	}

	@Override
    public void onUpdateReceived(Update update) {
        //check if the update has a message and if the message has text
    	if(update.hasMessage() && update.getMessage().hasText())
    	{
    		String messageText = update.getMessage().getText();
    		long chatID = update.getMessage().getChatId();
    		this.setResponse(messageText);	    		
    	}
    }
    
	
	public void sendMessageToChat(String generalMessage) {
    	SendMessage message = new SendMessage()
				.setChatId("-198887148")  //TODO: set chatID per game
				.setText(generalMessage);
    	
    	s3Client.appendToLogger(generalMessage);
    	
		try {
			execute(message); //bot tried to send this message to the user
		} catch(TelegramApiException e) {
			e.printStackTrace();	
		}
		
		
	}
	
    public void gameFinished() throws InterruptedException, TelegramApiException, IOException {
    	
    	String finalResponse = sendMessageGetResponse
    			("Do you want a replay of your game, yes or no?")
    			.toLowerCase();
    	
    	if(Objects.equals(finalResponse, "yes")) {
    		sendMessageToChat("Okay, I'll be sending you a copy of the game shortly!");
    		sendMessageToChat("THANKS FOR PLAYING RISK, GOODBYE!");
        	s3Client.addGameToS3Bucket();
    		sendGameReplay(s3Client.retrieveGameToReplay());
    		
    		return;
    	}
    	
		sendMessageToChat("THANKS FOR PLAYING RISK, GOODBYE!");
    	s3Client.addGameToS3Bucket();

    }
    
    
    private void sendGameReplay(String replayFilePath) {
    	File replayFile = new File(replayFilePath);
    	
    	SendDocument sendDocReq = new SendDocument();
    	sendDocReq.setChatId("-198887148");
    	sendDocReq.setDocument(replayFile);
    	
    	try {
    		execute(sendDocReq);
    	} catch (TelegramApiException e) {
    		e.printStackTrace();
    	}
    	
    	replayFile.delete();
    }
	
    public String sendMessageGetResponse(String nextMessage) throws InterruptedException, TelegramApiException {
    	resetResponse();
    	SendMessage message = new SendMessage()
				.setChatId("-198887148")  //TODO: set chatID per game
				.setText(nextMessage);
    	
    	s3Client.appendToLogger(nextMessage);
    	
		try {
			execute(message); //bot tried to send this message to the user
		} catch(TelegramApiException e) {
			e.printStackTrace();	
		}
		
		if(checkMessageResponse() == true)
		{
			s3Client.appendToLogger(getResponse());
			return getResponse();
		}
		else
		{
			return "empty";
		}
		
    }
    
    
    private boolean checkMessageResponse() throws InterruptedException {
    	
    	int sleepCount = 0;
    	boolean receivedResponse = false;
    	
    	while(sleepCount < 6)
    	{
    		Thread.sleep(5000);
    		
    		if(!Objects.equals(getResponse(), ""))
    		{
    			receivedResponse = true;
    			break;
    		}
    		
    		
    		sleepCount++;
    	}
    	
    	return receivedResponse;
    }
    
    

    @Override
    public String getBotUsername() {
    	return "jckRisk_bot";
    }

    @Override
    public String getBotToken() {
        return "798372367:AAE0XQ_hDcfLjtAxKcwawsWY0MQ6j4hF2-E";
    }
}