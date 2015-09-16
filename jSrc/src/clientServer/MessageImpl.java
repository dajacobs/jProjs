package clientServer;

import clientServer.Message;
import java.io.Serializable;

public class MessageImpl implements Message, Serializable{
    private final String message;
    private int characterCount;
    private int digitCount;
    
    public MessageImpl(String message) {
        this.message = message;
    }

    @Override
    public void setCounts() {
        for(int i = 0; i < message.length(); i++) {
            if(Character.isDigit(message.charAt(i))) {
                ++digitCount;
            } else if(Character.isLetter(message.charAt(i))) {
                ++characterCount;
            }
        }
    }

    @Override
    public int getCharacterCount() {
        return characterCount;
    }

    @Override
    public int getDigitCount() {
        return digitCount;
    }
    @Override
    public String toString() {
        return message;
    }
}