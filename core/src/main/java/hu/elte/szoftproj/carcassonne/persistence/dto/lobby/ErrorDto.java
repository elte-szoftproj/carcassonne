package hu.elte.szoftproj.carcassonne.persistence.dto.lobby;

/**
 * Created by Zsolt on 2014.12.07..
 */
public class ErrorDto {
    String status;

    String message;

    public ErrorDto(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
