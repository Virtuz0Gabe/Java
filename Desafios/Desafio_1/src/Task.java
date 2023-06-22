import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    
    private String status;
    private String description;
    private Date date;

    // CONSTRUTOR DA TAREFA
    public Task(String description, Date date) {
        this.description = description;
        this.date = date;
        this.status = "Ativa";
        // feita // ativa
    }

    // ########################################## GETTERS ##########################################

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }
    
    // ########################################## SETTERS ##########################################
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    // ########################################## toString ##########################################
    @Override
    public String toString(){
        String beautyDate;
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        beautyDate = formater.format(date);
        return "[" + status + "]  | " + beautyDate + " | " + description;
    }
}
